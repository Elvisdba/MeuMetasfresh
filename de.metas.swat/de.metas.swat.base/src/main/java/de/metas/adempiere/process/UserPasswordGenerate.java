/**
 * 
 */
package de.metas.adempiere.process;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.Query;

import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;

/**
 * @author teo_sarca
 *
 */
public class UserPasswordGenerate extends JavaProcess
{
	private int p_AD_Org_ID = -1;
	private int p_AD_User_ID = -1;
	private boolean p_IsOverridePassword = false;
	
	
	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = para.getParameterAsInt();
			else if (name.equals("AD_User_ID"))
				p_AD_User_ID = para.getParameterAsInt();
			else if (name.equals("IsOverridePassword"))
				p_IsOverridePassword = para.getParameterAsBoolean();
			else
				log.error("Unknown Parameter: " + name);
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (p_AD_Org_ID <= 0)
			throw new FillMandatoryException(I_AD_User.COLUMNNAME_AD_Org_ID);
		
		int cnt_ok = 0;
		int cnt_error = 0;
		
		Iterator<I_AD_User> it = getUsers();
		while(it.hasNext())
		{
			I_AD_User user = it.next();
			try
			{
				resetPassword(user);
				cnt_ok++;
			}
			catch(Exception e)
			{
				addLog(getUserDesc(user)+" : @Error@ - "+e.getLocalizedMessage());
				cnt_error++;
			}
		}
		return "@Updated@ (OK="+cnt_ok+", @Error@="+cnt_error+")";
	}
	
	private Iterator<I_AD_User> getUsers()
	{
		StringBuffer whereClause = new StringBuffer("1=1");
		List<Object> params = new ArrayList<Object>();
		
		if (p_AD_User_ID > 0)
		{
			whereClause.append(" AND "+I_AD_User.COLUMNNAME_AD_User_ID+"=?");
			params.add(p_AD_User_ID);
		}
		else
		{
			whereClause.append(" AND "+I_AD_User.COLUMNNAME_AD_Org_ID+"=?");
			params.add(p_AD_Org_ID);
		}
		
		if (!p_IsOverridePassword)
		{
			whereClause.append(" AND "+I_AD_User.COLUMNNAME_Password+" IS NULL");
		}
		
		Iterator<I_AD_User> it = new Query(getCtx(), I_AD_User.Table_Name, whereClause.toString(), get_TrxName())
		.setParameters(params)
		.setClient_ID()
		.setOrderBy(I_AD_User.COLUMNNAME_AD_User_ID)
		.iterate(I_AD_User.class);
		return it;
	}
	
	private void resetPassword(I_AD_User user)
	{
		if (!p_IsOverridePassword && !Check.isEmpty(user.getPassword(),true))
		{
			throw new AdempiereException("Override password is not allowed");
		}
		final String newPassword = Services.get(IUserBL.class).generatePassword();
		user.setPassword(newPassword);
		InterfaceWrapperHelper.save(user);
		
		// Log
		addLog(getUserDesc(user)+" : "+newPassword);
		
		//rollback();
	}

	private String getUserDesc(I_AD_User user)
	{
		String name = user.getEMail();
		if (Check.isEmpty(name, true))
			name = user.getName();
		return name;
	}
}
