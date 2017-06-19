package de.metas.i18n;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Stream;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Message;
import org.compiere.util.AmtInWords;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import lombok.NonNull;

/**
 * Reads all Messages and stores them in a HashMap
 *
 * @author metas-dev <dev@metasfresh.com>
 * @author based on initial version of Jorg Janke
 * 
 * @deprecated Please use {@link IMsgBL}
 */
@Deprecated
public final class Msg
{
	/** Initial size of HashMap */
	private static final int MAP_SIZE = 2200;
	/** Separator between Msg and optional Tip */
	private static final String SEPARATOR = Env.NL + Env.NL;

	/** Singleton */
	private static final Msg instance = new Msg();

	/** Logger */
	private static final Logger s_log = LogManager.getLogger(Msg.class);

	/**
	 * @return {@link Msg} singleton instance
	 */
	private static final Msg get()
	{
		return instance;
	}	// get

	/**************************************************************************
	 * Constructor
	 */
	private Msg()
	{
		super();
	}

	/** Messages cache: AD_Language to MessageValue to Translated message text */
	private final CCache<String, CCache<String, Message>> adLanguage2messages = new CCache<>("msg_lang", 2, 0); // expire=never

	/**
	 * Get Language specific Message Map
	 *
	 * @param adLanguage Language Key
	 * @return messages map or null of messages map could not be loaded.
	 */
	private CCache<String, Message> getMessagesForLanguage(final String adLanguage)
	{
		final String adLanguageToUse = notNullOrBaseLanguage(adLanguage);

		return adLanguage2messages.getOrLoad(adLanguageToUse, () -> retrieveMessagesCache(adLanguageToUse));
	}

	/** @return given adLanguage if not null or base language */
	private static final String notNullOrBaseLanguage(final String adLanguage)
	{
		return Check.isEmpty(adLanguage) ? Language.getBaseAD_Language() : adLanguage;
	}

	/**
	 * Retrieve messages map (cache).
	 *
	 * @param adLanguage Language
	 * @return messages cache map or null if initialization was postponed.
	 */
	private static CCache<String, Message> retrieveMessagesCache(final String adLanguage)
	{
		if (Adempiere.isUnitTestMode())
		{
			return new CCache<>(I_AD_Message.Table_Name, MAP_SIZE, 0); // expireMinutes=0=never
		}

		// If there is no database connection, postpone the messages loading
		if (!DB.isConnected())
		{
			// Log if there is no database connection.
			// We use FINE for logging because this can be a standard use case (e.g. on logout).
			// NOTE: we use RuntimeException instead of DBNoConnectionException to avoid trying to using Msg (which would introduce recursion).
			if (s_log.isDebugEnabled())
			{
				final RuntimeException ex = new RuntimeException("No DB Connection. Loading messages postponed.");
				s_log.debug(ex.getMessage(), ex);
			}
			return null;
		}

		final CCache<String, Message> msg = new CCache<>(I_AD_Message.Table_Name, MAP_SIZE, 0); // expireMinutes=0=never

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			if (adLanguage == null || adLanguage.length() == 0 || Env.isBaseLanguage(adLanguage, "AD_Language"))
			{
				pstmt = DB.prepareStatement("SELECT Value, MsgText, MsgTip FROM AD_Message", ITrx.TRXNAME_None);
			}
			else
			{
				pstmt = DB.prepareStatement("SELECT m.Value, t.MsgText, t.MsgTip "
						+ "FROM AD_Message_Trl t, AD_Message m "
						+ "WHERE m.AD_Message_ID=t.AD_Message_ID"
						+ " AND t.AD_Language=?", ITrx.TRXNAME_None);
				pstmt.setString(1, adLanguage);
			}
			rs = pstmt.executeQuery();

			// get values
			while (rs.next())
			{
				final String adMessage = rs.getString(1);
				final String msgText = rs.getString(2);
				final String msgTip = rs.getString(3);
				msg.put(adMessage, Message.ofTextAndTip(adMessage, msgText, msgTip));
			}
		}
		catch (final SQLException e)
		{
			s_log.error("initMsg", e);
			return null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		//
		if (msg.size() < 100)
		{
			final AdempiereException ex = new AdempiereException("Too few (" + msg.size() + ") Records found for " + adLanguage);
			s_log.error(ex.getMessage(), ex);
			return null;
		}

		s_log.debug("Loaded {} for '{}' language", msg.size(), adLanguage);
		return msg;
	}	// initMsg

	/**
	 * Reset Message cache
	 */
	public void reset()
	{
		if (adLanguage2messages == null)
		{
			return;
		}

		// clear all languages
		final Iterator<CCache<String, Message>> iterator = adLanguage2messages.values().iterator();
		while (iterator.hasNext())
		{
			final CCache<String, Message> hm = iterator.next();
			hm.reset();
		}
		adLanguage2messages.clear();
	}   // reset
	
	public static void cacheReset()
	{
		get().reset();
	}

	/**
	 * Lookup term
	 *
	 * @param adLanguage language
	 * @param text text
	 * @return translated term or <code>null</code> if message term could not be found
	 */
	private Message lookup(final String adLanguage, final String text)
	{
		if (text == null)
		{
			return Message.EMPTY;
		}
		if (adLanguage == null || adLanguage.length() == 0)
		{
			return Message.ofMissingADMessage(text);
		}

		//
		// hardcoded trl
		if (text.equals("/") || text.equals("\\"))
		{
			return Message.ofMissingADMessage(File.separator);
		}
		if (text.equals(";") || text.equals(":"))
		{
			return Message.ofMissingADMessage(File.pathSeparator);
		}
		if (Ini.METASFRESH_HOME.equals(text) || Ini.ADEMPIERE_HOME.equals(text))
		{
			return Message.ofMissingADMessage(Ini.getMetasfreshHome());
		}
		if (text.equals("bat") || text.equals("sh"))
		{
			if (System.getProperty("os.name").startsWith("Win"))
			{
				return Message.ofMissingADMessage("bat");
			}
			return Message.ofMissingADMessage("sh");
		}
		if (text.equals("CopyRight"))
		{
			return Message.ofMissingADMessage(Adempiere.getCopyright());
		}

		// metas-tsa: if we are in unit test mode, don't load anything from database, just return same text
		if (org.compiere.Adempiere.isUnitTestMode())
		{
			return Message.ofMissingADMessage(text);
		}

		final CCache<String, Message> messages = getMessagesForLanguage(adLanguage);
		if (messages == null)
		{
			// messages map could not be loaded (i.e. no database connection)
			// => build a missing message an return it because if we would return null,
			// caller would interpret as missing message which could be wrong because atm we don't know if it's missing or not.
			return Message.ofMissingADMessage(text);
		}
		return messages.get(text);
	}   // lookup

	private Stream<Message> lookupForPrefix(@NonNull final String adLanguage, @NonNull final String prefix)
	{
		final CCache<String, Message> messages = getMessagesForLanguage(adLanguage);
		if (messages == null)
		{
			return Stream.empty();
		}

		return messages.values().stream()
				.filter(message -> message.getAD_Message().startsWith(prefix));
	}

	/**************************************************************************
	 * Get translated text for AD_Message
	 * 
	 * @param adLanguage - Language
	 * @param adMessage - Message Key
	 * @return translated text
	 */
	private static String getMsg(final String adLanguage, final String adMessage)
	{
		return getMessage(adLanguage, adMessage).getMsgTextAndTip();
	}

	private final static Message getMessage(final String adLanguage, final String adMessage)
	{
		if (adMessage == null || adMessage.length() == 0)
		{
			return Message.EMPTY;
		}

		final String adLanguageToUse = notNullOrBaseLanguage(adLanguage);
		final Message message = get().lookup(adLanguageToUse, adMessage);

		//
		if (message == null && Services.get(IDeveloperModeBL.class).isEnabled())
		{
			if (Services.get(IDeveloperModeBL.class).createMessageOrElement(adLanguageToUse, adMessage, true, false))
			{
				get().reset(); // avoid creating same message more then once
				return Message.ofMissingADMessage(adMessage);
			}
		}

		if (message == null)
		{
			s_log.warn("AD_Message not found: {}", adMessage);
			return Message.ofMissingADMessage(adMessage);
		}

		return message;
	}	// getMsg

	/**
	 * Get translated text message for AD_Message
	 * 
	 * @param ctx Context to retrieve language
	 * @param AD_Message - Message Key
	 * @return translated text
	 */
	public static String getMsg(final Properties ctx, final String AD_Message)
	{
		return getMsg(Env.getAD_Language(ctx), AD_Message);
	}   // getMeg

	/**
	 * Get translated text message for AD_Message
	 * 
	 * @param language Language
	 * @param AD_Message - Message Key
	 * @return translated text
	 */
	public static String getMsg(final Language language, final String AD_Message)
	{
		return getMsg(language.getAD_Language(), AD_Message);
	}   // getMeg

	/**
	 * Get translated text message for AD_Message
	 * 
	 * @param adLanguage - Language
	 * @param adMessage - Message Key
	 * @param getText if true only return Text, if false only return Tip
	 * @return translated text
	 */
	private static String getMsg(final String adLanguage, final String adMessage, final boolean getText)
	{
		final Message message = getMessage(adLanguage, adMessage);
		return getText ? message.getMsgText() : message.getMsgTip();
	}

	/**
	 * Get translated text message for AD_Message
	 * 
	 * @param ctx Context to retrieve language
	 * @param adMessage Message Key
	 * @param getText if true only return Text, if false only return Tip
	 * @return translated text
	 */
	public static String getMsg(final Properties ctx, final String adMessage, final boolean getText)
	{
		return getMsg(Env.getAD_Language(ctx), adMessage, getText);
	}   // getMsg

	/**
	 * Get clear text for AD_Message with parameters
	 * 
	 * @param ctx Context to retrieve language
	 * @param AD_Message Message key
	 * @param args MessageFormat arguments
	 * @return translated text
	 * @see java.text.MessageFormat for formatting options
	 */
	public static String getMsg(final Properties ctx, final String AD_Message, final Object[] args)
	{
		return getMsg(Env.getAD_Language(ctx), AD_Message, args);
	}	// getMsg

	/**
	 * Get clear text for AD_Message with parameters
	 * 
	 * @param language Language
	 * @param AD_Message Message key
	 * @param args MessageFormat arguments
	 * @return translated text
	 * @see java.text.MessageFormat for formatting options
	 */
	public static String getMsg(final Language language, final String AD_Message, final Object[] args)
	{
		return getMsg(language.getAD_Language(), AD_Message, args);
	}	// getMsg

	/**
	 * Get clear text for AD_Message with parameters
	 * 
	 * @param ad_language Language
	 * @param AD_Message Message key
	 * @param args MessageFormat arguments
	 * @return translated text
	 * @see java.text.MessageFormat for formatting options
	 */
	public static String getMsg(final String ad_language, final String AD_Message, final Object[] args)
	{
		final String msg = getMsg(ad_language, AD_Message);
		String retStr = msg;
		try
		{
			retStr = MessageFormat.format(msg, args);	// format string
		}
		catch (final Exception e)
		{
			s_log.error(msg, e);
		}
		return retStr;
	}	// getMsg

	public static Map<String, String> getMsgMap(final String adLanguage, final String prefix, boolean removePrefix)
	{
		final Function<Message, String> keyFunction;
		if (removePrefix)
		{
			final int beginIndex = prefix.length();
			keyFunction = message -> message.getAD_Message().substring(beginIndex);
		}
		else
		{
			keyFunction = Message::getAD_Message;
		}
		
		return get().lookupForPrefix(adLanguage, prefix)
				.collect(ImmutableMap.toImmutableMap(keyFunction, Message::getMsgText));
	}

	/**************************************************************************
	 * Get Amount in Words
	 * 
	 * @param language language
	 * @param amount numeric amount (352.80)
	 * @return amount in words (three*five*two 80/100)
	 */
	public static String getAmtInWords(final Language language, final String amount)
	{
		if (amount == null || language == null)
		{
			return amount;
		}
		// Try to find Class
		String className = "org.compiere.util.AmtInWords_";
		try
		{
			className += language.getLanguageCode().toUpperCase();
			final Class<?> clazz = Class.forName(className);
			final AmtInWords aiw = (AmtInWords)clazz.newInstance();
			return aiw.getAmtInWords(amount);
		}
		catch (final ClassNotFoundException e)
		{
			s_log.trace("Class not found: {}", className);
		}
		catch (final Exception e)
		{
			s_log.error(className, e);
		}

		// Fallback
		final StringBuilder sb = new StringBuilder();
		int pos = amount.lastIndexOf('.');
		final int pos2 = amount.lastIndexOf(',');
		if (pos2 > pos)
		{
			pos = pos2;
		}
		for (int i = 0; i < amount.length(); i++)
		{
			if (pos == i)	// we are done
			{
				final String cents = amount.substring(i + 1);
				sb.append(' ').append(cents).append("/100");
				break;
			}
			else
			{
				final char c = amount.charAt(i);
				if (c == ',' || c == '.')
				{
					continue;
				}
				if (sb.length() > 0)
				{
					sb.append("*");
				}
				sb.append(getMsg(language, String.valueOf(c)));
			}
		}
		return sb.toString();
	}	// getAmtInWords

	/**************************************************************************
	 * Get Translation for Element
	 * 
	 * @param adLanguage language
	 * @param ColumnName column name
	 * @param isSOTrx if false PO terminology is used (if exists)
	 * @return Name of the Column or "" if not found
	 */
	public static String getElement(final String adLanguage, String ColumnName, final boolean isSOTrx)
	{
		if (ColumnName == null || ColumnName.equals(""))
		{
			return "";
		}

		final String adLanguageToUse = notNullOrBaseLanguage(adLanguage);

		// metas: begin
		String displayColumnName = I_AD_Element.COLUMNNAME_Name;
		{
			final int idx = ColumnName.indexOf("/");
			if (idx > 0)
			{
				final String display = ColumnName.substring(idx + 1);
				if ("Name".equalsIgnoreCase(display))
				{
					displayColumnName = I_AD_Element.COLUMNNAME_Name;
				}
				else if ("PrintName".equalsIgnoreCase(display))
				{
					displayColumnName = I_AD_Element.COLUMNNAME_PrintName;
				}
				else if ("Description".equalsIgnoreCase(display))
				{
					displayColumnName = I_AD_Element.COLUMNNAME_Description;
				}
				else
				{
					s_log.warn("Unknow element field {} in {}", display, ColumnName, new Exception());
				}
				ColumnName = ColumnName.substring(0, idx);
			}
		}
		// metas: end

		// Check AD_Element
		String retStr = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			try
			{
				if (adLanguageToUse == null || adLanguageToUse.length() == 0 || Env.isBaseLanguage(adLanguageToUse, "AD_Element"))
				{
					pstmt = DB.prepareStatement("SELECT Name, PO_Name, PrintName, PO_PrintName, Description, PO_Description FROM AD_Element WHERE UPPER(ColumnName)=?", null);
				}
				else
				{
					pstmt = DB.prepareStatement("SELECT t.Name, t.PO_Name, t.PrintName, t.PO_PrintName, t.Description, t.PO_Description FROM AD_Element_Trl t, AD_Element e "
							+ "WHERE t.AD_Element_ID=e.AD_Element_ID AND UPPER(e.ColumnName)=? "
							+ "AND t.AD_Language=?", null);
					pstmt.setString(2, adLanguageToUse);
				}
			}
			catch (final Exception e)
			{
				return ColumnName;
			}
			finally
			{
				DB.close(rs);
				rs = null;
			}
			pstmt.setString(1, ColumnName.toUpperCase());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				retStr = rs.getString(displayColumnName);
				if (!isSOTrx)
				{
					final String temp = rs.getString("PO_" + displayColumnName);
					if (temp != null && temp.length() > 0)
					{
						retStr = temp;
					}
				}
			}
		}
		catch (final SQLException e)
		{
			s_log.error("getElement", e);
			return "";
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		if (retStr != null)
		{
			return retStr.trim();
		}
		return retStr;
	}   // getElement

	/**
	 * Get Translation for Element using Sales terminology
	 * 
	 * @param ctx context
	 * @param ColumnName column name
	 * @return Name of the Column or "" if not found
	 */
	public static String getElement(final Properties ctx, final String ColumnName)
	{
		return getElement(Env.getAD_Language(ctx), ColumnName, true);
	}   // getElement

	/**
	 * Get Translation for Element
	 * 
	 * @param ctx context
	 * @param ColumnName column name
	 * @param isSOTrx sales transaction
	 * @return Name of the Column or "" if not found
	 */
	public static String getElement(final Properties ctx, final String ColumnName, final boolean isSOTrx)
	{
		return getElement(Env.getAD_Language(ctx), ColumnName, isSOTrx);
	}   // getElement

	/**************************************************************************
	 * "Translate" text.
	 * 
	 * <pre>
	 *		- Check AD_Message.AD_Message 	->	MsgText
	 *		- Check AD_Element.ColumnName	->	Name
	 * </pre>
	 * 
	 * If checking AD_Element, the SO terminology is used.
	 * 
	 * @param adLanguage Language
	 * @param isSOTrx sales order context
	 * @param text Text - AD_Message or Element Name
	 * @return translated text or original text if not found
	 */
	public static String translate(final String adLanguage, final boolean isSOTrx, final String text)
	{
		if (text == null || text.equals(""))
		{
			return "";
		}
		final String adLanguageToUse = notNullOrBaseLanguage(adLanguage);

		// Check AD_Message
		final Message message = get().lookup(adLanguageToUse, text);
		if (message != null)
		{
			return message.getMsgTextAndTip();
		}

		// Check AD_Element
		String retStr = getElement(adLanguageToUse, text, isSOTrx);
		if (!Check.isEmpty(retStr))
		{
			return retStr.trim();
		}

		// metas: begin: try to find tablename primary key
		// e.g. if we query for "C_Invoice" then "C_Invoice_ID" shall be returned
		if (!text.startsWith("*") && !text.endsWith("_ID"))
		{
			retStr = getElement(adLanguageToUse, text + "_ID", isSOTrx);
			if (!Check.isEmpty(retStr))
			{
				return retStr.trim();
			}
		}
		// metas: end

		if (!text.startsWith("*") && Services.get(IDeveloperModeBL.class).isEnabled())
		{
			if (Services.get(IDeveloperModeBL.class).createMessageOrElement(adLanguageToUse, text, true, true))
			{
				get().reset(); // avoid creating same message more then once
				return text;
			}
		}

		// Nothing found
		if (!text.startsWith("*"))
		{
			s_log.warn("NOT found: {}", text);
		}
		return text;
	}	// translate

	/***
	 * "Translate" text (SO Context).
	 * 
	 * <pre>
	 *		- Check AD_Message.AD_Message 	->	MsgText
	 *		- Check AD_Element.ColumnName	->	Name
	 * </pre>
	 * 
	 * If checking AD_Element, the SO terminology is used.
	 * 
	 * @param ad_language Language
	 * @param text Text - MsgText or Element Name
	 * @return translated text or original text if not found
	 */
	public static String translate(final String ad_language, final String text)
	{
		return translate(ad_language, true, text);
	}	// translate

	/**
	 * "Translate" text.
	 * 
	 * <pre>
	 *		- Check AD_Message.AD_Message 	->	MsgText
	 *		- Check AD_Element.ColumnName	->	Name
	 * </pre>
	 * 
	 * @param ctx Context
	 * @param text Text - MsgText or Element Name
	 * @return translated text or original text if not found
	 */
	public static String translate(final Properties ctx, final String text)
	{
		final boolean isSOTrx = Env.isSOTrx(ctx);
		return translate(ctx, text, isSOTrx);
	}

	public static String translate(final Properties ctx, final String text, final boolean isSOTrx)
	{
		if (text == null || text.length() == 0)
		{
			return text;
		}
		final String s = ctx.getProperty(text);
		if (s != null && s.length() > 0)
		{
			return s;
		}
		return translate(Env.getAD_Language(ctx), isSOTrx, text);
	}   // translate

	/**
	 * "Translate" text.
	 * 
	 * <pre>
	 *		- Check AD_Message.AD_Message 	->	MsgText
	 *		- Check AD_Element.ColumnName	->	Name
	 * </pre>
	 * 
	 * @param language Language
	 * @param text Text
	 * @return translated text or original text if not found
	 */
	public static String translate(final Language language, final String text)
	{
		final boolean isSOTrx = false;
		return translate(language.getAD_Language(), isSOTrx, text);
	}   // translate

	/**
	 * Translate elements enclosed in "@" (at sign)
	 * 
	 * @param ctx Context
	 * @param text Text
	 * @return translated text or original text if not found
	 */
	public static String parseTranslation(final Properties ctx, final String text)
	{
		if (text == null || text.length() == 0)
		{
			return text;
		}

		String inStr = text;
		String token;
		final StringBuilder outStr = new StringBuilder();

		int i = inStr.indexOf('@');
		while (i != -1)
		{
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i + 1, inStr.length());	// from first @

			final int j = inStr.indexOf('@');						// next @
			if (j < 0)										// no second tag
			{
				inStr = "@" + inStr;
				break;
			}

			token = inStr.substring(0, j);
			// metas: begin: "@@" shall be interpretated as "@"
			if (token.isEmpty())
			{
				outStr.append("@");
			}
			else
			{
				// metas: end
				outStr.append(translate(ctx, token));			// replace context
			}

			inStr = inStr.substring(j + 1, inStr.length());	// from second @
			i = inStr.indexOf('@');
		}

		outStr.append(inStr);           					// add remainder
		return outStr.toString();
	}   // parseTranslation

	/** Internal translated message representation (immutable) */
	private static final class Message
	{
		/** Empty message */
		public static final Message EMPTY = ofMissingADMessage("");

		/** @return instance for given message text and tip */
		public static final Message ofTextAndTip(final String adMessage, final String msgText, final String msgTip)
		{
			return new Message(adMessage, msgText, msgTip);
		}

		/** @return instance of given missing adMessage */
		public static final Message ofMissingADMessage(final String adMessage)
		{
			final String msgText = adMessage;
			final String msgTip = null; // no tip
			return new Message(adMessage, msgText, msgTip);
		}

		private final String adMessage;
		private final String msgText;
		private final String msgTip;
		private final String msgTextAndTip;

		private Message(@NonNull final String adMessage, final String msgText, final String msgTip)
		{
			super();
			this.adMessage = adMessage;
			this.msgText = msgText == null ? "" : msgText;
			this.msgTip = msgTip == null ? "" : msgTip;

			final StringBuilder msgTextAndTip = new StringBuilder();
			msgTextAndTip.append(this.msgText);
			if (!Check.isEmpty(this.msgTip, true))			// messageTip on next line, if exists
			{
				msgTextAndTip.append(" ").append(SEPARATOR).append(this.msgTip);
			}
			this.msgTextAndTip = msgTextAndTip.toString();
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("adMessage", adMessage)
					.add("msgText", msgText)
					.add("msgTip", msgTip)
					.toString();
		}

		public String getAD_Message()
		{
			return adMessage;
		}

		public String getMsgText()
		{
			return msgText;
		}

		public String getMsgTip()
		{
			return msgTip;
		}

		public String getMsgTextAndTip()
		{
			return msgTextAndTip;
		}
	}
}	// Msg
