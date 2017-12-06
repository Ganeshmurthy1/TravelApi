package com.tayyarah.insurance.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TayyarahInsuranceMap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,List<InsuranceMarkUp>> insuranceMarkUpConfiglistMap = new HashMap<String,List<InsuranceMarkUp>>();
	private CreateInsurancePolicyRequest createInsurancePolicyRequest;
	private InsuranceMarkupCommissionDetails markupCommissionDetails;
	private PolicyResponseData  policyResponseData;
	
	public Map<String, List<InsuranceMarkUp>> getInsuranceMarkUpConfiglistMap() {
		return insuranceMarkUpConfiglistMap;
	}
	public CreateInsurancePolicyRequest getCreateInsurancePolicyRequest() {
		return createInsurancePolicyRequest;
	}
	public InsuranceMarkupCommissionDetails getMarkupCommissionDetails() {
		return markupCommissionDetails;
	}
	public PolicyResponseData getPolicyResponseData() {
		return policyResponseData;
	}
	public void setInsuranceMarkUpConfiglistMap(Map<String, List<InsuranceMarkUp>> insuranceMarkUpConfiglistMap) {
		this.insuranceMarkUpConfiglistMap = insuranceMarkUpConfiglistMap;
	}
	public void setCreateInsurancePolicyRequest(CreateInsurancePolicyRequest createInsurancePolicyRequest) {
		this.createInsurancePolicyRequest = createInsurancePolicyRequest;
	}
	public void setMarkupCommissionDetails(InsuranceMarkupCommissionDetails markupCommissionDetails) {
		this.markupCommissionDetails = markupCommissionDetails;
	}
	public void setPolicyResponseData(PolicyResponseData policyResponseData) {
		this.policyResponseData = policyResponseData;
	}
}
