package org.compiere.report.email.service.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.MDocType;
import org.compiere.model.MInOut;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.report.email.service.IEmailParameters;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.metas.bpartner.IBPartnerDAO;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.process.ProcessInfo;

/**
 * 
 * @author ts
 * 
 */
public final class DocumentEmailParams implements IEmailParameters {

	public static final String MSG_SEND_MAIL = "SendMail";
	public static final String MSG_ATTACHMENT_INVOICE_DOC_NO = "AttachmentInvoiceDocNo";
	public static final String MSG_SUBJECT_INVOICE_DOC_NO = "SubjectInvoiceDocNo";
	public static final String MSG_ATTACHMENT_SHIPMENT_DOC_NO = "AttachmentShipmentDocNo";
	public static final String MSG_SUBJECT_SHIPMENT_DOC_NO = "SubjectShipmentDocNo";
	public static final String MSG_ATTACHMENT_ORDER_DOC_NO = "AttachmentOrderDocNo";
	public static final String MSG_SUBJECT_ORDER_DOC_NO = "SubjectOrderDocNo";

	private final String subject;
	private final String to;
	private final String attachmentPrefix;
	private final I_AD_User from;

	private final static String EXPORT_FILE_PREFIX = null;

	private Integer defaultBoilerPlateId;

	public DocumentEmailParams(final ProcessInfo pi) {

		final int tableId = pi.getTable_ID();
		final boolean isOrder = I_C_Order.Table_ID == tableId;
		final boolean isInOut = I_M_InOut.Table_ID == tableId;
		final boolean isInvoice = I_C_Invoice.Table_ID == tableId;

		if (!isOrder && !isInOut && !isInvoice) {
			throw new IllegalArgumentException(
					"Process must belong to an order, a shipment or and invoice");
		}

		// get the email subject, the attachment file name and the
		// AD_User_IDs of possible email recipients.

		String documentNo = null;
		String subjectKey = null;
		String attachmentKey = null;
		Set<Integer> userIds = new LinkedHashSet<>();
		int targetDocTypeId = 0;

		if (isOrder)
		{
			MOrder order = new MOrder(Env.getCtx(), pi.getRecord_ID(), null);
			documentNo = order.getDocumentNo();
			subjectKey = MSG_SUBJECT_ORDER_DOC_NO;
			attachmentKey = MSG_ATTACHMENT_ORDER_DOC_NO;
			targetDocTypeId = order.getC_DocTypeTarget_ID();

			final int billUserId = order.getBill_User_ID();
			if (billUserId > 0)
			{
				userIds.add(billUserId);
			}
			
			final int billBPartnerId = order.getBill_BPartner_ID();
			if (billBPartnerId > 0)
			{
				final I_AD_User contact = Services.get(IBPartnerDAO.class).retrieveDefaultContactOrNull(billBPartnerId);
				if(contact != null)
				{
					userIds.add(contact.getAD_User_ID());
				}
			}
			
			final int userId = order.getAD_User_ID();
			if (userId > 0)
			{
				userIds.add(userId);
			}
			
			final int bPartnerId = order.getC_BPartner_ID();
			if (bPartnerId > 0)
			{
				final I_AD_User contact = Services.get(IBPartnerDAO.class).retrieveDefaultContactOrNull(bPartnerId);
				if(contact != null)
				{
					userIds.add(contact.getAD_User_ID());
				}
			}
		}
		else if (isInOut)
		{
			MInOut inOut = new MInOut(Env.getCtx(), pi.getRecord_ID(), null);
			documentNo = inOut.getDocumentNo();
			subjectKey = MSG_SUBJECT_SHIPMENT_DOC_NO;
			attachmentKey = MSG_ATTACHMENT_SHIPMENT_DOC_NO;
			targetDocTypeId = inOut.getC_DocType_ID();

			final int userId = inOut.getAD_User_ID();
			if (userId > 0)
			{
				userIds.add(userId);
			}
			
			final int bPartnerId = inOut.getC_BPartner_ID();
			if (bPartnerId > 0)
			{
				final I_AD_User contact = Services.get(IBPartnerDAO.class).retrieveDefaultContactOrNull(bPartnerId);
				if(contact != null)
				{
					userIds.add(contact.getAD_User_ID());
				}
			}
		}
		else if (isInvoice)
		{

			MInvoice invoice = new MInvoice(Env.getCtx(), pi.getRecord_ID(),
					null);
			documentNo = invoice.getDocumentNo();
			subjectKey = MSG_SUBJECT_INVOICE_DOC_NO;
			attachmentKey = MSG_ATTACHMENT_INVOICE_DOC_NO;
			targetDocTypeId = invoice.getC_DocTypeTarget_ID();

			final int userId = invoice.getAD_User_ID();
			if (userId > 0)
			{
				userIds.add(userId);
			}
			
			final int bPartnerId = invoice.getC_BPartner_ID();
			if (bPartnerId > 0)
			{
				final I_AD_User contact = Services.get(IBPartnerDAO.class).retrieveDefaultContactOrNull(bPartnerId);
				if(contact != null)
				{
					userIds.add(contact.getAD_User_ID());
				}
			}
		}

		subject = Msg.getMsg(Env.getCtx(), subjectKey, new Object[] { documentNo });
		attachmentPrefix = Msg.getMsg(Env.getCtx(), attachmentKey, new Object[] { documentNo });

		// attempt to figure out an email-address for the customer
		String toFound = "";
		for (int userId : userIds)
		{
			if (userId < 1)
			{
				continue;
			}
			final I_AD_User contact = Services.get(IUserDAO.class).retrieveUser(Env.getCtx(), userId);
			if(!Check.isEmpty(contact.getEMail(), true))
			{
				toFound = contact.getEMail();
				break;
			}
		}
		to = toFound;

		if (targetDocTypeId != 0)
		{
			final I_C_DocType docType = MDocType.get(Env.getCtx(), targetDocTypeId);
			defaultBoilerPlateId = docType.getAD_BoilerPlate_ID();

		}

		from = Services.get(IUserDAO.class).retrieveUser(Env.getCtx());
	}

	@Override
	public String getAttachmentPrefix(final String defaultValue) {
		if (attachmentPrefix == null) {
			return defaultValue;
		}
		return attachmentPrefix;
	}

	@Override
	public I_AD_User getFrom() {
		return from;
	}

	@Override
	public String getMessage() {
		return "";
	}

	@Override
	public String getSubject() {
		return subject;
	}

	@Override
	public String getTitle() {
		return Msg.getMsg(Env.getCtx(), MSG_SEND_MAIL);
	}

	@Override
	public String getTo() {
		return to;
	}

	@Override
	public String getExportFilePrefix() {
		return EXPORT_FILE_PREFIX;
	}

	@Override
	public MADBoilerPlate getDefaultTextPreset() {
		if (defaultBoilerPlateId != null && defaultBoilerPlateId > 0) {
			return new MADBoilerPlate(Env.getCtx(), defaultBoilerPlateId, null);
		}
		return null;
	}

}
