package com.ericsson.raso.sef.core.cg.nsn.avp.mms;

import com.ericsson.pps.diameter.dccapi.avp.avpdatatypes.DccGrouped;
import com.ericsson.pps.diameter.rfcapi.base.avp.Avp;

public class ServiceInfoAvp extends DccGrouped {

	private static final long serialVersionUID = 1L;

	public static final int AVP_CODE = 873;
	
	public ServiceInfoAvp(Avp avp) {
		super(avp);
	}
	
	public PsInformationAvp getPsInformationAvp() {
		Avp avp = getSubAvp(PsInformationAvp.AVP_CODE);
		if(avp == null) return null;
		return new PsInformationAvp(avp);
	}
	
	public void addPsInformationAvp(PsInformationAvp avp) {
		this.addSubAvp(avp);
	}

	public MMSInformationAvp getMMSInformationAvp() {
		Avp avp = getSubAvp(MMSInformationAvp.AVP_CODE);
		if(avp == null) return null;
		return new MMSInformationAvp(avp);
	}
	
}
