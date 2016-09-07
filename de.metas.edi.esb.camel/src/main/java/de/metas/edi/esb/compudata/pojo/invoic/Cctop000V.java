package de.metas.edi.esb.compudata.pojo.invoic;

/*
 * #%L
 * de.metas.edi.esb
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.Serializable;

public class Cctop000V implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = -8166470101903850154L;

	/*
	 * EDI-specific fields
	 */
	private String receiverGln;
	private String senderGln;
	private String interchangeReferenceNo;
	private String isTest;

	public String getReceiverGln()
	{
		return receiverGln;
	}

	public void setReceiverGln(final String receiverGln)
	{
		this.receiverGln = receiverGln;
	}

	public String getSenderGln()
	{
		return senderGln;
	}

	public void setSenderGln(final String senderGln)
	{
		this.senderGln = senderGln;
	}

	public String getInterchangeReferenceNo()
	{
		return interchangeReferenceNo;
	}

	public void setInterchangeReferenceNo(final String interchangeReferenceNo)
	{
		this.interchangeReferenceNo = interchangeReferenceNo;
	}

	public String getIsTest()
	{
		return isTest;
	}

	public void setIsTest(final String isTest)
	{
		this.isTest = isTest;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((interchangeReferenceNo == null) ? 0 : interchangeReferenceNo.hashCode());
		result = prime * result + ((isTest == null) ? 0 : isTest.hashCode());
		result = prime * result + ((receiverGln == null) ? 0 : receiverGln.hashCode());
		result = prime * result + ((senderGln == null) ? 0 : senderGln.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cctop000V other = (Cctop000V)obj;
		if (interchangeReferenceNo == null)
		{
			if (other.interchangeReferenceNo != null)
				return false;
		}
		else if (!interchangeReferenceNo.equals(other.interchangeReferenceNo))
			return false;
		if (isTest == null)
		{
			if (other.isTest != null)
				return false;
		}
		else if (!isTest.equals(other.isTest))
			return false;
		if (receiverGln == null)
		{
			if (other.receiverGln != null)
				return false;
		}
		else if (!receiverGln.equals(other.receiverGln))
			return false;
		if (senderGln == null)
		{
			if (other.senderGln != null)
				return false;
		}
		else if (!senderGln.equals(other.senderGln))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Cctop000V [receiverGln=" + receiverGln + ", senderGln=" + senderGln + ", interchangeReferenceNo=" + interchangeReferenceNo + ", isTest=" + isTest + "]";
	}

}
