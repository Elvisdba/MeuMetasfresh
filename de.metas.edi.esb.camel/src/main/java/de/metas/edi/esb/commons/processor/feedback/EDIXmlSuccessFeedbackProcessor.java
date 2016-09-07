package de.metas.edi.esb.commons.processor.feedback;

import static de.metas.edi.esb.commons.processor.feedback.helper.EDIXmlFeedbackHelper.createEDIFeedbackType;

import javax.xml.namespace.QName;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import de.metas.edi.esb.jaxb.EDIExportStatusEnum;

public class EDIXmlSuccessFeedbackProcessor<T> implements Processor
{
	private final Class<T> feedbackType;
	private final QName feedbackQName;
	private final String recordIdSetter;

	public EDIXmlSuccessFeedbackProcessor(final Class<T> feedbackType, final QName feedbackQName, final String recordIdSetter)
	{
		super();

		this.feedbackType = feedbackType;
		this.feedbackQName = feedbackQName;
		this.recordIdSetter = recordIdSetter;
	}

	@Override
	public void process(final Exchange exchange)
	{
		createEDIFeedbackType(exchange, EDIExportStatusEnum.Sent, feedbackType, feedbackQName, recordIdSetter);
	}
}
