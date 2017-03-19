package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * BPartner contact/login user model
 *
 * @author metas-dev <dev@metasfresh.com>
 * @author based on initial work of Jorg Janke, Teo Sarca
 */
@SuppressWarnings("serial")
public class MUser extends X_AD_User
{
	public MUser(final Properties ctx, final int AD_User_ID, final String trxName)
	{
		super(ctx, AD_User_ID, trxName);	// 0 is also System
		if (AD_User_ID == 0)
		{
			setIsFullBPAccess(true);
			setNotificationType(NOTIFICATIONTYPE_EMail);
		}
	}	// MUser

	public MUser(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}	// MUser

	/**
	 * Get Value - 7 bit lower case alpha numerics max length 8
	 *
	 * @return value
	 */
	@Override
	public String getValue()
	{
		final String s = super.getValue();
		if (s != null)
		{
			return s;
		}
		setValue(null);
		return super.getValue();
	}	// getValue

	/**
	 * Set Value - 7 bit lower case alpha numerics max length 8
	 *
	 * @param Value
	 */
	@Override
	public void setValue(String Value)
	{
		if (Value == null || Value.trim().length() == 0)
		{
			Value = getLDAPUser();
		}
		if (Value == null || Value.length() == 0)
		{
			Value = getName();
		}
		if (Value == null || Value.length() == 0)
		{
			Value = "noname";
		}
		//
		String result = cleanValue(Value);
		if (result.length() > 8)
		{
			final String first = getName(Value, true);
			final String last = getName(Value, false);
			if (last.length() > 0)
			{
				String temp = last;
				if (first.length() > 0)
				{
					temp = first.substring(0, 1) + last;
				}
				result = cleanValue(temp);
			}
			else
			{
				result = cleanValue(first);
			}
		}
		if (result.length() > 8)
		{
			result = result.substring(0, 8);
		}
		super.setValue(result);
	}	// setValue

	/**
	 * Clean Value
	 *
	 * @param value value
	 * @return lower case cleaned value
	 */
	private static String cleanValue(final String value)
	{
		final char[] chars = value.toCharArray();
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++)
		{
			char ch = chars[i];
			ch = Character.toLowerCase(ch);
			if (ch >= '0' && ch <= '9'		// digits
					|| ch >= 'a' && ch <= 'z')
			{
				sb.append(ch);
			}
		}
		return sb.toString();
	}	// cleanValue

	/**
	 * Get First/Last Name
	 *
	 * @param name name
	 * @param getFirst if true first name is returned
	 * @return first/last name
	 */
	private static String getName(final String name, final boolean getFirst)
	{
		if (name == null || name.length() == 0)
		{
			return "";
		}
		String first = null;
		String last = null;
		// Janke, Jorg R - Jorg R Janke
		// double names not handled gracefully nor titles
		// nor (former) aristrocratic world de/la/von
		final boolean lastFirst = name.indexOf(',') != -1;
		StringTokenizer st = null;
		if (lastFirst)
		{
			st = new StringTokenizer(name, ",");
		}
		else
		{
			st = new StringTokenizer(name, " ");
		}
		while (st.hasMoreTokens())
		{
			final String s = st.nextToken().trim();
			if (lastFirst)
			{
				if (last == null)
				{
					last = s;
				}
				else if (first == null)
				{
					first = s;
				}
			}
			else
			{
				if (first == null)
				{
					first = s;
				}
				else
				{
					last = s;
				}
			}
		}
		if (getFirst)
		{
			if (first == null)
			{
				return "";
			}
			return first.trim();
		}
		if (last == null)
		{
			return "";
		}
		return last.trim();
	}	// getName

	/**
	 * String Representation
	 *
	 * @return Info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MUser[")
				.append(get_ID())
				.append(",Name=").append(getName())
				.append(",EMailUserID=").append(getEMailUser())
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Set EMail - reset validation
	 *
	 * @param EMail email
	 */
	@Override
	public void setEMail(final String EMail)
	{
		super.setEMail(EMail);
		setEMailVerifyDate(null);
	}	// setEMail

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		// New Address invalidates verification
		if (!newRecord && is_ValueChanged(COLUMNNAME_EMail))
		{
			setEMailVerifyDate(null);
		}
		if (newRecord || super.getValue() == null || is_ValueChanged(COLUMNNAME_Value))
		{
			setValue(super.getValue());
		}
		return true;
	}	// beforeSave
}	// MUser
