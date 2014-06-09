package com.ericsson.raso.sef.smart.subscriber.response;

import java.io.Serializable;
import java.util.Map;

import com.ericsson.raso.sef.core.db.model.ContractState;
import com.ericsson.sef.bes.api.entities.TransactionStatus;

public class SubscriberInfo extends AbstractSubscriberResponse implements Serializable {

	
	private static final long serialVersionUID = -339541771917173674L;

	
	private String msisdn;
	private ContractState remoteState;
	private ContractState localState;
	private boolean isLocked = false;
	private Map<String,String> metas;
	private TransactionStatus status;
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public ContractState getRemoteState() {
		return remoteState;
	}
	public void setRemoteState(ContractState remoteState) {
		this.remoteState = remoteState;
	}
	public ContractState getLocalState() {
		return localState;
	}
	public void setLocalState(ContractState localState) {
		this.localState = localState;
	}
	public boolean isLocked() {
		return isLocked;
	}
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	public Map<String, String> getMetas() {
		return metas;
	}
	public void setMetas(Map<String, String> metas) {
		this.metas = metas;
	}
	public TransactionStatus getStatus() {
		return status;
	}
	public void setStatus(TransactionStatus status) {
		this.status = status;
	}
}
