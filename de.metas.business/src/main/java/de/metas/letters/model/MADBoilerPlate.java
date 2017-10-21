package de.metas.letters.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataSource;
import javax.annotation.Nullable;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserBL;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Attachment;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_A_Asset;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Campaign;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_RMA;
import org.compiere.model.I_R_Request;
import org.compiere.model.Lookup;
import org.compiere.model.MBPartner;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MOrderLine;
import org.compiere.model.MRequest;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.print.ReportEngine;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.attachments.IAttachmentBL;
import de.metas.email.EMail;
import de.metas.email.EMailAttachment;
import de.metas.email.EMailSentStatus;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfo;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

public final class MADBoilerPlate extends X_AD_BoilerPlate
{

	private static final long serialVersionUID = 5825759144157759944L;
	private static final Logger log = LogManager.getLogger(MADBoilerPlate.class);

	public static final String FunctionSeparator = "/";
	public static final String NamePattern = "[a-zA-Z0-9-_+]+";
	public static final String NamePatternWithFunctions = "[a-zA-Z0-9-_+" + FunctionSeparator + "]+";
	public static final String TagBegin = "@";
	public static final String TagEnd = "@";
	public static final Pattern NameTagPattern = Pattern.compile(TagBegin + "([ ]*" + NamePatternWithFunctions + "[ ]*)" + TagEnd, Pattern.CASE_INSENSITIVE);

	public static final String FUNCTION_stripWhitespaces = "stripWhitespaces";
	public static final String FUNCTION_trim = "trim";
	public static final String FUNCTION_urlEncode = "urlEncode";
	public static final String FUNCTION_upperCase = "upperCase";
	public static final String FUNCTION_lowerCase = "lowerCase";

	private static CCache<Integer, MADBoilerPlate> s_cache = new CCache<>(Table_Name, 20);

	public static MADBoilerPlate get(final Properties ctx, final int AD_BoilerPlate_ID)
	{
		if (AD_BoilerPlate_ID <= 0)
		{
			return null;
		}
		MADBoilerPlate bp = s_cache.get(AD_BoilerPlate_ID);
		if (bp != null)
		{
			return bp;
		}
		bp = new MADBoilerPlate(ctx, AD_BoilerPlate_ID, null);
		if (bp.getAD_BoilerPlate_ID() != AD_BoilerPlate_ID)
		{
			bp = null;
		}
		else
		{
			s_cache.put(AD_BoilerPlate_ID, bp);
		}
		return bp;
	}

	public static ReportEngine getReportEngine(final String html, final BoilerPlateContext context)
	{
		final Properties ctx = Env.getCtx();
		String text = parseText(ctx, html, false, context, ITrx.TRXNAME_None);
		text = text.replace("</", " </"); // we need to leave at least one space before closing tag, else jasper will not apply the effect of that tag
		
		final ProcessInfo pi = ProcessInfo.builder()
				.setCtx(ctx)
				.setAD_ProcessByClassname("de.metas.letters.report.AD_BoilerPlate_Report")
				.addParameter(I_T_BoilerPlate_Spool.COLUMNNAME_MsgText, text)
				//
				.buildAndPrepareExecution()
				.executeSync()
				.getProcessInfo();
		
		final ReportEngine re = ReportEngine.get(ctx, pi);
		return re;
	}

	public static File getPDF(final String fileNamePrefix, final String html, final BoilerPlateContext context)
	{
		File file = null;
		if (!Check.isEmpty(fileNamePrefix))
		{
			try
			{
				file = File.createTempFile(fileNamePrefix.trim(), ".pdf");
			}
			catch (final IOException e)
			{
				log.error(e.getLocalizedMessage(), e);
				file = null;
			}
		}
		//
		final ReportEngine re = getReportEngine(html, context);
		return re.getPDF(file);
	}

	public static void sendEMail(final IEMailEditor editor)
	{
		sendEMail(editor, true);
	}

	public static void sendEMail(final IEMailEditor editor, final boolean withRequest)
	{
		final SourceDocument baseObject = SourceDocument.toSourceDocumentOrNull(editor.getBaseObject());
		final BoilerPlateContext context = createEditorContext(baseObject);
		final I_AD_User from = context.getSalesRepUser();
		final String toEmail = context.getEMail();
		if (Check.isEmpty(toEmail, true))
		{
			throw new AdempiereException("@NotFound@ @EMail@");
		}
		final int AD_Table_ID = editor.getAD_Table_ID();
		final int Record_ID = editor.getRecord_ID();
		//
		final EMail email = editor.sendEMail(from, toEmail, "", context);
		if (withRequest)
		{
			createRequest(email, AD_Table_ID, Record_ID, context);
	}
	}

	private static void createRequest(final EMail email,
			final int parent_table_id, final int parent_record_id,
			final BoilerPlateContext context)
	{
		if (email == null)
		{
			return;
		}
		
		final EMailSentStatus emailSentStatus = email.getLastSentStatus();
		if (!emailSentStatus.isSentOK())
		{
			return;
		}

		final MRequestTypeService rtService = new MRequestTypeService(Env.getCtx());
		final Integer SalesRep_ID = context.getSalesRep_ID();
		final MRequest request = new MRequest(Env.getCtx(),
				SalesRep_ID == null ? 0 : SalesRep_ID.intValue(),
				rtService.getDefault(I_R_RequestType.COLUMNNAME_IsDefaultForEMail),
				email.getSubject(),
				false, // isSelfService,
				null // trxName
		);
		String message = getPlainText(email.getMessageHTML());
		if (Check.isEmpty(message, true))
		{
			message = email.getMessageCRLF();
		}
		request.setResult(message);
		updateRequestDetails(request, parent_table_id, parent_record_id, context);
		request.saveEx();
		
		//
		// Attach email attachments to this request
		try
		{
			final List<DataSource> attachmentDataSources = email.getAttachments().stream()
					.map(EMailAttachment::createDataSource)
					.collect(ImmutableList.toImmutableList());
			if (!attachmentDataSources.isEmpty())
			{
				final IAttachmentBL attachmentBL = Services.get(IAttachmentBL.class);
				final I_AD_Attachment requestAttachment = attachmentBL.getAttachment(request);
				attachmentBL.addEntriesFromDataSources(requestAttachment, attachmentDataSources);
			}
		}
		catch (final Exception ex)
		{
			log.warn("Failed attaching email attachments to request: {}", request, ex);
		}
	}

	public static void createRequest(final File pdf,
			final int parent_table_id, final int parent_record_id,
			final BoilerPlateContext context)
	{
		final MRequestTypeService rtService = new MRequestTypeService(Env.getCtx());
		final Integer SalesRep_ID = context.getSalesRep_ID();
		final MRequest request = new MRequest(Env.getCtx(),
				SalesRep_ID == null ? 0 : SalesRep_ID.intValue(),
				rtService.getDefault(I_R_RequestType.COLUMNNAME_IsDefaultForLetter),
				Services.get(IMsgBL.class).translate(Env.getCtx(), "de.metas.letter.RequestLetterSubject"),
				false, // isSelfService,
				ITrx.TRXNAME_ThreadInherited // trxName
		);
		updateRequestDetails(request, parent_table_id, parent_record_id, context);
		InterfaceWrapperHelper.save(request);
		
		//
		// Attach printed letter
		if(pdf != null)
		{
			final IAttachmentBL attachmentBL = Services.get(IAttachmentBL.class);
			final I_AD_Attachment requestAttachment = attachmentBL.getAttachment(request);
			attachmentBL.addEntry(requestAttachment, pdf);
		}
	}

	private static void updateRequestDetails(final I_R_Request rq,
			final int parent_table_id, final int parent_record_id,
			final BoilerPlateContext context)
	{
		final Integer C_BPartner_ID = context.getC_BPartner_ID();
		if (C_BPartner_ID != null && C_BPartner_ID > 0)
		{
			rq.setC_BPartner_ID(C_BPartner_ID);
		}
		final Integer AD_User_ID = context.getAD_User_ID();
		if (AD_User_ID != null && AD_User_ID > 0)
		{
			rq.setAD_User_ID(AD_User_ID);
		}

		rq.setAD_Table_ID(parent_table_id);
		rq.setRecord_ID(parent_record_id);
		if (parent_record_id <= 0)
		{
			return;
		}
		//
		if (parent_table_id == I_C_BPartner.Table_ID)
		{
			rq.setC_BPartner_ID(parent_record_id);
		}
		else if (parent_table_id == InterfaceWrapperHelper.getTableId(I_AD_User.class))
		{
			rq.setAD_User_ID(parent_record_id);
		}
		else if (parent_table_id == I_C_Project.Table_ID)
		{
			rq.setC_Project_ID(parent_record_id);
		}
		else if (parent_table_id == I_A_Asset.Table_ID)
		{
			rq.setA_Asset_ID(parent_record_id);
		}
		else if (parent_table_id == I_C_Order.Table_ID)
		{
			rq.setC_Order_ID(parent_record_id);
		}
		else if (parent_table_id == InterfaceWrapperHelper.getTableId(I_C_Invoice.class))
		{
			rq.setC_Invoice_ID(parent_record_id);
		}
		else if (parent_table_id == InterfaceWrapperHelper.getTableId(I_M_Product.class))
		{
			rq.setM_Product_ID(parent_record_id);
		}
		else if (parent_table_id == I_C_Payment.Table_ID)
		{
			rq.setC_Payment_ID(parent_record_id);
		}
		else if (parent_table_id == I_M_InOut.Table_ID)
		{
			rq.setM_InOut_ID(parent_record_id);
		}
		else if (parent_table_id == I_M_RMA.Table_ID)
		{
			rq.setM_RMA_ID(parent_record_id);
		}
		else if (parent_table_id == I_C_Campaign.Table_ID)
		{
			rq.setC_Campaign_ID(parent_record_id);
		}
		else if (parent_table_id == InterfaceWrapperHelper.getTableId(I_R_Request.class))
		{
			rq.setR_RequestRelated_ID(parent_record_id);
		}
		else if (parent_table_id == I_C_OrderLine.Table_ID)
		{
			final MOrderLine oLine = new MOrderLine(Env.getCtx(), parent_record_id, null);
			if (oLine != null)
			{
				rq.setC_Order_ID(oLine.getC_Order_ID());
			}
		}
	}

	public MADBoilerPlate(final Properties ctx, final int AD_BoilerPlate_ID, final String trxName)
	{
		super(ctx, AD_BoilerPlate_ID, trxName);
	}

	public MADBoilerPlate(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public static Query getByNameQuery(final Properties ctx, final String name, final String trxName)
	{
		final String whereClause = COLUMNNAME_Name + "=? AND AD_Client_ID=?";
		return new Query(ctx, Table_Name, whereClause, trxName)
				.setParameters(new Object[] { name, Env.getAD_Client_ID(ctx) });
	}

	public static int getIdByName(final Properties ctx, final String name, final String trxName)
	{
		return getByNameQuery(ctx, name, trxName).firstIdOnly();
	}

	public static MADBoilerPlate getByName(final Properties ctx, final String name, final String trxName)
	{
		return getByNameQuery(ctx, name, trxName).firstOnly();
	}

	public static KeyNamePair[] getDependsOn(final Properties ctx, final int AD_BoilerPlate_ID, final String trxName)
	{
		final String sql = "SELECT " + COLUMNNAME_AD_BoilerPlate_ID + "," + COLUMNNAME_Name
				+ " FROM " + Table_Name
				+ " WHERE EXISTS (SELECT 1 FROM " + I_AD_BoilerPlate_Ref.Table_Name + " r"
				+ " WHERE r." + I_AD_BoilerPlate_Ref.COLUMNNAME_Ref_BoilerPlate_ID + "=" + Table_Name + "." + COLUMNNAME_AD_BoilerPlate_ID
				+ " AND r." + I_AD_BoilerPlate_Ref.COLUMNNAME_AD_BoilerPlate_ID + "=?)";
		return DB.getKeyNamePairs(trxName, sql, false, AD_BoilerPlate_ID);
	}

	public KeyNamePair toKeyNamePair()
	{
		return new KeyNamePair(getAD_BoilerPlate_ID(), getName());
	}

	public static KeyNamePair[] getAllSnippetsKeyNamePair()
	{
		final List<MADBoilerPlate> allSnippets = getAll(Env.getCtx());
		final KeyNamePair[] result = new KeyNamePair[allSnippets.size()];
		final int i = 0;
		for (final MADBoilerPlate bp : allSnippets)
		{
			s_cache.put(bp.getAD_BoilerPlate_ID(), bp);
			result[i] = bp.toKeyNamePair();
		}
		return result;
	}

	public static Lookup getAllLookup(final int windowNo)
	{
		final Lookup lookup;
		try
		{
			final Properties ctx = Env.getCtx();

			lookup = MLookupFactory.get(ctx,
					windowNo,
					0, // Column_ID
					DisplayType.TableDir,
					I_AD_BoilerPlate.COLUMNNAME_AD_BoilerPlate_ID,
					0, // AD_Reference_Value_ID,
					false, // IsParent,
					IValidationRule.AD_Val_Rule_ID_Null); // ValidationCode
		}
		catch (final Exception e)
		{
			throw new AdempiereException(e);
		}
		return lookup;
	}

	/**
	 * 
	 * @return all snippets, ordered by name
	 */
	public static SortedMap<String, String> getAllSnippetsMap()
	{
		final SortedMap<String, String> result = new TreeMap<>();

		for (final MADBoilerPlate boilerPlate : getAll(Env.getCtx()))
		{
			if (boilerPlate.getTextSnippext() != null)
			{
				result.put(boilerPlate.getName(), boilerPlate.getTextSnippext());
			}
			else
			{
				log.warn("boilerPlate entry '" + boilerPlate.getName() + "' has null value as snippet");
				continue;
			}
		}
		return result;
	}

	// TODO: cache it
	public static List<MADBoilerPlate> getAll(final Properties ctx)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_BoilerPlate.class, ctx, ITrx.TRXNAME_None)
				.addOnlyContextClientOrSystem()
				.orderBy()
				.addColumn(I_AD_BoilerPlate.COLUMNNAME_Name)
				.addColumn(I_AD_BoilerPlate.COLUMNNAME_AD_Client_ID, Direction.Descending, Nulls.Last)
				.endOrderBy()
				.create()
				.list(MADBoilerPlate.class);
	}

	private void checkCycles(int AD_BoilerPlate_ID, Collection<KeyNamePair> trace)
	{
		if (trace == null)
		{
			trace = new ArrayList<>();
		}
		if (AD_BoilerPlate_ID <= 0)
		{
			trace.add(toKeyNamePair());
			AD_BoilerPlate_ID = getAD_BoilerPlate_ID();
		}
		for (final KeyNamePair dependOn : MADBoilerPlate.getDependsOn(getCtx(), AD_BoilerPlate_ID, get_TrxName()))
		{

			if (trace.contains(dependOn))
			{
				throw new AdempiereException("@de.metas.letters.AD_BoilerPlate.CycleDetectedError@ - " + dependOn);
			}
			final Collection<KeyNamePair> trace2 = new ArrayList<>(trace);
			trace2.add(dependOn);
			checkCycles(dependOn.getKey(), trace2);
		}
	}

	public String getTextSnippetPlain()
	{
		return getPlainText(getTextSnippext());
	}

	public static String getPlainText(String html)
	{
		if (Check.isEmpty(html, true))
		{
			return html;
		}
		//
		final String brMarker = "[br]" + System.currentTimeMillis();
		html = html.replace("<br>", brMarker);
		final HTMLEditorKit editorKit = new HTMLEditorKit();
		final Reader r = new StringReader(html);
		final Document doc = editorKit.createDefaultDocument();
		String text = html;
		try
		{
			editorKit.read(r, doc, 0);
			text = doc.getText(0, doc.getEndPosition().getOffset()).trim();
		}
		catch (final Exception e)
		{
			throw new AdempiereException(e);
		}
		text = text.replace(brMarker, "\r\n");
		return text;
	}

	public String getTextSnippext(String AD_Language)
	{
		if (AD_Language == null)
		{
			AD_Language = Env.getAD_Language(getCtx());
		}
		return get_Translation(COLUMNNAME_TextSnippext, AD_Language);
	}

	/**
	 * Get Parsed Text
	 * 
	 * @param ctx
	 * @param text
	 * @param AD_Language
	 * @param isEmbeded will this text be embeded (i.e. shoud we strip html, head, body tags?
	 * @param attrs variables map. If null, no variable replacement will be made
	 * @param trxName
	 * @return
	 */
	public static String parseText(final Properties ctx, final String text,
			final boolean isEmbeded,
			final BoilerPlateContext context,
			final String trxName)
	{
		if (text == null)
		{
			return null;
		}
		//
		String textFixed = text;
		if (isEmbeded)
		{
			textFixed = textFixed.replace("<html>", "")
					.replace("</html>", "")
					.replace("<head>", "")
					.replace("</head>", "")
					.replace("<body>", "")
					.replace("</body>", "")
					.trim();
		}
		final Matcher m = NameTagPattern.matcher(textFixed);
		final StringBuffer sb = new StringBuffer();
		while (m.find())
		{
			final String[] functions = getTagNameAndFunctions(m);
			final String refName = functions[0];
			functions[0] = null;

			final MADBoilerPlateVar var = MADBoilerPlateVar.get(ctx, refName);

			String replacement;
			if (context != null && context.containsKey(refName))
			{
				final Object attrValue = context.get(refName);
				replacement = attrValue == null ? "" : attrValue.toString();
			}
			else if (var != null)
			{
				try
				{
					replacement = var.evaluate(context);
				}
				catch (final Exception e)
				{
					log.warn(e.getLocalizedMessage(), e);
					replacement = m.group();
				}
			}
			else
			{
				final MADBoilerPlate ref = getByName(ctx, refName, trxName);
				if (ref == null)
				{
					throw new AdempiereException("@NotFound@ @AD_BoilerPlate_ID@ (@Name@:" + refName + ")");
				}
				replacement = ref.getTextSnippetParsed(true, context);
			}
			if (replacement == null)
			{
				replacement = "";
			}
			replacement = applyTagFunctions(replacement, functions);
			m.appendReplacement(sb, replacement);
		}
		m.appendTail(sb);
		return sb.toString();

	}

	private static String applyTagFunctions(String text, final String[] functions)
	{
		if (text == null)
		{
			text = "";
		}
		for (final String function : functions)
		{
			if (Check.isEmpty(function, true))
			{
				continue;
			}
			text = applyTagFunction(text, function.trim());
		}
		return text;
	}

	private static String applyTagFunction(final String text, final String function)
	{
		if (FUNCTION_stripWhitespaces.equalsIgnoreCase(function))
		{
			return text.replaceAll("\\s", "");
		}
		else if (FUNCTION_trim.equalsIgnoreCase(function))
		{
			return text.trim();
		}
		else if (FUNCTION_urlEncode.equalsIgnoreCase(function))
		{
			try
			{
				return URLEncoder.encode(text, "UTF-8");
			}
			catch (final UnsupportedEncodingException e)
			{
				throw new AdempiereException(e);
			}
		}
		else if (FUNCTION_upperCase.equalsIgnoreCase(function))
		{
			return text.toUpperCase();
		}
		else if (FUNCTION_lowerCase.equalsIgnoreCase(function))
		{
			return text.toLowerCase();
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @Function@ " + function);
		}
	}

	/**
	 * 
	 * @param AD_Language
	 * @param isEmbeded
	 * @param attrs
	 * @return
	 * @see #getTextSnippetParsed(String, boolean, Map) and consider isEmbeded = true
	 */
	public String getTextSnippetParsed(final BoilerPlateContext context)
	{
		return getTextSnippetParsed(false, context);
	}

	/**
	 * Get Parsed Text
	 * 
	 * @param AD_Language
	 * @param attrs variables map. If null, no variable repacement will be made
	 * @param isEmbeded will this text be embeded (i.e. shoud we strip html, head, body tags?
	 * @return parsed text
	 */
	public String getTextSnippetParsed(final boolean isEmbeded, final BoilerPlateContext context)
	{
		final String AD_Language = getAD_Language(Env.getCtx(), context);
		final String text = getTextSnippext(AD_Language);
		return parseText(getCtx(), text, isEmbeded, context, get_TrxName());
	}

	public String getTagString()
	{
		return createTag(getName());
	}

	public static String createTag(final String name)
	{
		if (Check.isEmpty(name, true))
		{
			return "";
		}
		return TagBegin + name.trim() + TagEnd;
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		final String name = getName();
		if (MADBoilerPlateVar.get(getCtx(), name) != null)
		{
			throw new AdempiereException("@Invalid@ - " + name);
		}
		if (!name.matches("^" + NamePattern + "$"))
		{
			throw new AdempiereException("@Invalid@ - " + name);
		}
		return true;
	}

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (!success)
		{
			return false;
		}

		rebuildReferences();
		//
		return true;
	}

	public Collection<String> parseNeededReferences()
	{
		final String text = getTextSnippetPlain();
		final Matcher m = NameTagPattern.matcher(text);
		final Collection<String> neededReferences = new TreeSet<>();
		while (m.find())
		{
			final String refName = getTagName(m);
			if (MADBoilerPlateVar.exists(getCtx(), refName))
			{
				continue;
			}
			neededReferences.add(refName);
		}
		return neededReferences;
	}

	protected static String getTagString(final Matcher m)
	{
		String refName;
		if (m.groupCount() >= 1)
		{
			refName = m.group(1);
		}
		else
		{
			refName = m.group();
		}
		refName = refName.trim();
		return refName;
	}

	protected static String getTagName(final Matcher m)
	{
		final String[] parts = getTagString(m).split(FunctionSeparator);
		return parts != null && parts.length > 0 ? parts[0] : "";
	}

	protected static String[] getTagNameAndFunctions(final Matcher m)
	{
		final String[] parts = getTagString(m).split(FunctionSeparator);
		return parts;
	}

	public void rebuildReferences()
	{
		DB.executeUpdateEx("DELETE FROM " + I_AD_BoilerPlate_Ref.Table_Name
				+ " WHERE " + I_AD_BoilerPlate_Ref.COLUMNNAME_AD_BoilerPlate_ID + "=?",
				new Object[] { getAD_BoilerPlate_ID() },
				get_TrxName());
		for (final String refName : parseNeededReferences())
		{
			final MADBoilerPlateRef ref = new MADBoilerPlateRef(this, refName);
			ref.saveEx();
		}
		checkCycles(-1, null);
	}

	/**
	 * Create Context
	 */
	public static BoilerPlateContext createEditorContext(final SourceDocument sourceDocument)
	{
		final Properties ctx = Env.getCtx();

		final BoilerPlateContext.Builder attributesBuilder = BoilerPlateContext.builder();
		attributesBuilder.setWindowNo(sourceDocument != null ? sourceDocument.getWindowNo() : Env.WINDOW_MAIN);
		attributesBuilder.setSourceDocument(sourceDocument);

		final I_AD_User salesRep = Services.get(IUserDAO.class).retrieveUserOrNull(Env.getCtx(), Env.getAD_User_ID(Env.getCtx()));
		if (salesRep != null)
		{
			attributesBuilder.setSalesRep(salesRep);
		}

		int C_BPartner_ID = -1;
		int C_BPartner_Location_ID = -1;
		I_AD_User user = null;
		final int AD_User_ID = sourceDocument != null ? sourceDocument.getFieldValueAsInt("AD_User_ID", -1) : -1;
		String email = null;
		//
		if (AD_User_ID > 0)
		{
			user = Services.get(IUserDAO.class).retrieveUserOrNull(ctx, AD_User_ID);
			attributesBuilder.setUser(user);
			if(Services.get(IUserBL.class).isEMailValid(user))
			{
				email = user.getEMail();
				attributesBuilder.setEmail(email);
			}
			C_BPartner_ID = user.getC_BPartner_ID();
			C_BPartner_Location_ID = user.getC_BPartner_Location_ID();
		}
		if (C_BPartner_ID <= 0)
		{
			C_BPartner_ID = sourceDocument != null ? sourceDocument.getFieldValueAsInt("C_BPartner_ID", -1) : -1;
		}
		if (C_BPartner_ID > 0)
		{
			attributesBuilder.setC_BPartner_ID(C_BPartner_ID);

			final MBPartner bp = MBPartner.get(ctx, C_BPartner_ID);
			if (email == null)
			{
				final I_AD_User contact = getDefaultContactOrFirstWithValidEMail(bp);
				if (contact != null)
				{
					attributesBuilder.setUser(contact);
					if(Services.get(IUserBL.class).isEMailValid(contact))
					{
						email = contact.getEMail();
						attributesBuilder.setEmail(email);
					}
				}
			}
		}
		
		if(C_BPartner_Location_ID <= 0)
		{
			C_BPartner_Location_ID = sourceDocument != null ? sourceDocument.getFieldValueAsInt("C_BPartner_Location_ID", -1) : -1;
		}
		if(C_BPartner_Location_ID > 0)
		{
			attributesBuilder.setC_BPartner_Location_ID(C_BPartner_Location_ID);
		}
		
		//
		// Language
		String AD_Language = Env.getAD_Language(ctx);
		if (C_BPartner_ID > 0)
		{
			final MBPartner bp = MBPartner.get(ctx, C_BPartner_ID);
			if (bp != null)
			{
				AD_Language = bp.getAD_Language();
			}
		}
		attributesBuilder.setAD_Language(AD_Language);
		
		int adOrgId = sourceDocument != null ? sourceDocument.getFieldValueAsInt("AD_Org_ID", -1) : -1;
		if(adOrgId < 0)
		{
			adOrgId = Env.getAD_Org_ID(ctx);
		}
		attributesBuilder.setAD_Org_ID(adOrgId);

		
		//
		//
		// attrs.put(BoilerPlateContext.VAR_Phone, null);
		// attrs.put(BoilerPlateContext.VAR_Phone2, null);
		// attrs.put(BoilerPlateContext.VAR_Fax, null);
		// if (user != null)
		// {
		// attrs.put(BoilerPlateContext.VAR_Phone, user.getPhone());
		// attrs.put(BoilerPlateContext.VAR_Phone2, user.getPhone2());
		// attrs.put(BoilerPlateContext.VAR_Fax, user.getFax());
		// }
		// //
		// attrs.put(BoilerPlateContext.VAR_BPValue, null);
		// attrs.put(BoilerPlateContext.VAR_BPName, null);
		// if (C_BPartner_ID > 0)
		// {
		// MBPartner bp = MBPartner.get(ctx, C_BPartner_ID);
		// if (bp != null)
		// {
		// attrs.put(BoilerPlateContext.VAR_BPValue, bp.getValue());
		// attrs.put(BoilerPlateContext.VAR_BPName, bp.get_Translation(MBPartner.COLUMNNAME_Name, AD_Language));
		// }
		// }
		//
		return attributesBuilder.build();
	}
	
	private static I_AD_User getDefaultContactOrFirstWithValidEMail(final MBPartner bpartner)
	{
		final IUserBL userBL = Services.get(IUserBL.class);
		
		I_AD_User firstContact = null;
		I_AD_User firstValidContact = null;
		for (final I_AD_User contact : bpartner.getContacts(false))
		{
			if(contact.isDefaultContact())
			{
				return contact;
			}
			
			if(firstContact == null)
			{
				firstContact = contact;
			}

			if (userBL.isEMailValid(contact))
			{
				if(firstValidContact == null)
				{
					firstValidContact = contact;
				}
			}
		}
		
		if(firstValidContact != null)
		{
			return firstValidContact;
		}
		
		return firstContact;
	}

	/**
	 * Get Language from attributes
	 * 
	 * @param attributes
	 * @return
	 */
	public static String getAD_Language(final Properties ctx, final BoilerPlateContext context)
	{
		if (context != null)
		{
			final String adLanguage = context.getAD_Language();
			if (adLanguage != null)
			{
				return adLanguage;
		}
		}
		return Env.getAD_Language(ctx);
	}

	@Override
	public String toString()
	{
		final StringBuilder result = new StringBuilder();
		result.append(super.toString());
		result.append(' ');
		result.append(getName());
		result.append(" (");
		result.append(getTextSnippext().substring(0, 20));
		result.append("...)");
		return result.toString();
	}

	/**
	 * Create record into T_BoilerPlate_Spool table
	 * 
	 * @param ctx
	 * @param AD_Client_ID
	 * @param AD_PInstance_ID
	 * @param text
	 * @param trxName
	 */
	public static void createSpoolRecord(final Properties ctx, final int AD_Client_ID, final int AD_PInstance_ID, final String text, final String trxName)
	{
		final String sql = "INSERT INTO " + I_T_BoilerPlate_Spool.Table_Name + "("
				+ " " + I_T_BoilerPlate_Spool.COLUMNNAME_AD_Client_ID
				+ "," + I_T_BoilerPlate_Spool.COLUMNNAME_AD_Org_ID
				+ "," + I_T_BoilerPlate_Spool.COLUMNNAME_AD_PInstance_ID
				+ "," + I_T_BoilerPlate_Spool.COLUMNNAME_SeqNo
				+ "," + I_T_BoilerPlate_Spool.COLUMNNAME_MsgText
				+ ") VALUES (?,?,?,?,?)";
		DB.executeUpdateEx(sql,
				new Object[] { AD_Client_ID, 0, AD_PInstance_ID, 10, text },
				trxName);
	}

	@ToString
	public static final class BoilerPlateContext
	{
		public static final Builder builder()
		{
			return new Builder(ImmutableMap.of());
}

		public static final BoilerPlateContext EMPTY = new BoilerPlateContext(ImmutableMap.of());

		private static final String VAR_WindowNo = "WindowNo";
		private static final String VAR_SalesRep = "SalesRep";
		private static final String VAR_SalesRep_ID = "SalesRep_ID";
		private static final String VAR_EMail = "EMail";
		private static final String VAR_AD_User_ID = "AD_User_ID";
		private static final String VAR_AD_User = "AD_User";
		private static final String VAR_C_BPartner_ID = "C_BPartner_ID";
		private static final String VAR_C_BPartner_Location_ID = "C_BPartner_Location_ID";
		private static final String VAR_AD_Org_ID = "AD_Org_ID";
		private static final String VAR_AD_Language = "AD_Language";
		/** Source document. Usually it's of type {@link SourceDocument} */
		private static final String VAR_SourceDocument = SourceDocument.NAME;

		private final Map<String, Object> attributes;

		private BoilerPlateContext(@NonNull final Map<String, Object> attributes)
		{
			this.attributes = attributes;
		}

		public Builder toBuilder()
		{
			return new Builder(attributes);
		}

		public boolean containsKey(final String attributeName)
		{
			return attributes.containsKey(attributeName);
		}

		public Object get(final String attributeName)
		{
			return attributes.get(attributeName);
		}

		@Nullable
		public Integer getSalesRep_ID()
		{
			return (Integer)get(VAR_SalesRep_ID);
		}

		public I_AD_User getSalesRepUser()
		{
			return InterfaceWrapperHelper.create(get(VAR_SalesRep), I_AD_User.class);
		}

		public String getEMail()
		{
			return (String)get(VAR_EMail);
		}

		@Nullable
		public Integer getC_BPartner_ID()
		{
			return (Integer)get(VAR_C_BPartner_ID);
		}

		@Nullable
		public Integer getC_BPartner_ID(final Integer defaultValue)
		{
			final Integer bpartnerId = getC_BPartner_ID();
			return bpartnerId != null ? bpartnerId : defaultValue;
		}
		
		@Nullable
		public Integer getC_BPartner_Location_ID()
		{
			return (Integer)get(VAR_C_BPartner_Location_ID);
		}

		@Nullable
		public Integer getC_BPartner_Location_ID(final Integer defaultValue)
		{
			final Integer bpartnerLocationId = getC_BPartner_Location_ID();
			return bpartnerLocationId != null ? bpartnerLocationId : defaultValue;
		}


		@Nullable
		public Integer getAD_User_ID()
		{
			return (Integer)get(VAR_AD_User_ID);
		}

		@Nullable
		public Integer getAD_User_ID(final Integer defaultValue)
		{
			final Integer adUserId = getAD_User_ID();
			return adUserId != null ? adUserId : defaultValue;
		}

		@Nullable
		public Integer getAD_Org_ID(final Integer defaultValue)
		{
			final Integer adOrgId = (Integer)get(VAR_AD_Org_ID);
			return adOrgId != null ? adOrgId : defaultValue;
		}

		@Nullable
		public String getAD_Language()
		{
			final Object adLanguageObj = get(VAR_AD_Language);
			return adLanguageObj != null ? adLanguageObj.toString() : null;
		}

		public int getWindowNo()
		{
			final Object windowNoObj = get(VAR_WindowNo);
			if (windowNoObj instanceof Number)
			{
				return ((Number)windowNoObj).intValue();
			}
			else
			{
				return Env.WINDOW_MAIN;
			}
		}

		public SourceDocument getSourceDocumentOrNull()
		{
			return SourceDocument.toSourceDocumentOrNull(get(VAR_SourceDocument));
		}

		public static class Builder
		{
			private final Map<String, Object> attributes;

			private Builder(final Map<String, Object> attributes)
			{
				this.attributes = attributes != null && !attributes.isEmpty() ? new HashMap<>(attributes) : new HashMap<>();
			}

			public BoilerPlateContext build()
			{
				if(attributes.isEmpty())
				{
					return EMPTY;
				}

				return new BoilerPlateContext(ImmutableMap.copyOf(attributes));
			}

			private void setAttribute(final String attributeName, final Object value)
			{
				if (value == null)
				{
					attributes.remove(attributeName);
				}
				else
				{
					attributes.put(attributeName, value);
				}
			}

			public Builder setWindowNo(final int windowNo)
			{
				setAttribute(VAR_WindowNo, windowNo);
				return this;
			}

			public Builder setSourceDocument(final SourceDocument sourceDocument)
			{
				setAttribute(VAR_SourceDocument, sourceDocument);
				return this;
			}

			public Builder setAD_Language(final String adLanguage)
			{
				setAttribute(VAR_AD_Language, adLanguage);
				return this;
			}

			public Builder setSourceDocumentFromObject(final Object sourceDocumentObj)
			{
				final SourceDocument sourceDocument = SourceDocument.toSourceDocumentOrNull(sourceDocumentObj);
				setSourceDocument(sourceDocument);
				return this;
			}

			public Builder setSalesRep(final I_AD_User salesRepUser)
			{
				setAttribute(VAR_SalesRep, salesRepUser);
				setAttribute(VAR_SalesRep_ID, salesRepUser != null ? salesRepUser.getAD_User_ID() : null);
				return this;
			}

			public Builder setC_BPartner_ID(final int bpartnerId)
			{
				setAttribute(VAR_C_BPartner_ID, bpartnerId);
				return this;
			}

			public Builder setC_BPartner_Location_ID(final int bpartnerLocationId)
			{
				setAttribute(VAR_C_BPartner_Location_ID, bpartnerLocationId);
				return this;
			}

			public Builder setUser(final I_AD_User user)
			{
				setAttribute(VAR_AD_User, user);
				setAttribute(VAR_AD_User_ID, user != null ? user.getAD_User_ID() : null);
				return this;
			}

			public Builder setEmail(final String email)
			{
				setAttribute(VAR_EMail, email);
				return this;
			}
			
			public Builder setAD_Org_ID(final int adOrgId)
			{
				setAttribute(VAR_AD_Org_ID, adOrgId);
				return this;
			}


			public Builder setCustomAttribute(final String attributeName, final Object value)
			{
				setAttribute(attributeName, value);
				return this;
			}
		}
	}

	public static interface SourceDocument
	{
		String NAME = "__SourceDocument";

		default int getWindowNo()
		{
			return Env.WINDOW_None;
		}

		boolean hasFieldValue(String fieldName);

		Object getFieldValue(String fieldName);

		default int getFieldValueAsInt(final String fieldName, final int defaultValue)
		{
			final Object value = getFieldValue(fieldName);
			return value != null ? (int)value : defaultValue;
		}

		static SourceDocument toSourceDocumentOrNull(final Object obj)
		{
			if (obj == null)
			{
				return null;
			}

			if (obj instanceof SourceDocument)
			{
				return (SourceDocument)obj;
			}

			final PO po = InterfaceWrapperHelper.getPO(obj);
			return new POSourceDocument(po);
		}
	}

	@AllArgsConstructor
	private static final class POSourceDocument implements SourceDocument
	{
		@NonNull
		private final PO po;

		@Override
		public boolean hasFieldValue(final String fieldName)
		{
			return po.get_ColumnIndex(fieldName) >= 0;
		}

		@Override
		public Object getFieldValue(final String fieldName)
		{
			return po.get_Value(fieldName);
		}
	}

	@AllArgsConstructor
	private static final class GridTabSourceDocument implements SourceDocument
	{
		@NonNull
		private final GridTab gridTab;

		@Override
		public boolean hasFieldValue(final String fieldName)
		{
			return gridTab.getField(fieldName) != null;
		}

		@Override
		public Object getFieldValue(final String fieldName)
		{
			return gridTab.getValue(fieldName);
		}
	}

}
