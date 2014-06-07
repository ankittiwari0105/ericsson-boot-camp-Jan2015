package com.ericsson.raso.sef.smart.usecase;

import java.util.List;

import com.nsn.ossbss.charge_once.wsdl.entity.tis.xsd._1.EnumerationValueParameter;
import com.nsn.ossbss.charge_once.wsdl.entity.tis.xsd._1.LongParameter;
import com.nsn.ossbss.charge_once.wsdl.entity.tis.xsd._1.Operation;
import com.nsn.ossbss.charge_once.wsdl.entity.tis.xsd._1.ShortParameter;
import com.nsn.ossbss.charge_once.wsdl.entity.tis.xsd._1.StringParameter;

public class CreateOrWriteCustomerRequest extends SmartRequest {

	private String customerId;
	private String category;
	private int billCycleId;
	private long messageId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getBillCycleId() {
		return billCycleId;
	}

	public void setBillCycleId(int billCycleId) {
		this.billCycleId = billCycleId;
	}

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	@Override
	public void prepareRequest(Operation operation) {
		List<Object> parameters = operation.getParameterList().getParameterOrBooleanParameterOrByteParameter();
		for (Object param : parameters) {
			if (param instanceof StringParameter) {
				StringParameter parameter = (StringParameter) param;
				this.setCustomerId(parameter.getValue().trim());
			} else if(param instanceof EnumerationValueParameter) {
				EnumerationValueParameter parameter = (EnumerationValueParameter) param;
				this.setCategory(parameter.getValue().trim());
			} else if(param instanceof ShortParameter) {
				ShortParameter parameter = (ShortParameter) param;
				this.setBillCycleId(parameter.getValue());
			} else if (param instanceof LongParameter) {
				LongParameter parameter = (LongParameter) param;
				this.setMessageId(parameter.getValue());
			}
		}

	}

	@Override
	public String toString() {
		return "CreateOrWriteCustomerRequest [customerId=" + customerId
				+ ", category=" + category + ", billCycleId=" + billCycleId
				+ ", messageId=" + messageId + "]";
	}

}
