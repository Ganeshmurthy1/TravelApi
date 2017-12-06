package com.tayyarah.insurance.model;

import java.math.BigDecimal;

public class PlanResponse {

	private Status status;
	private String planId;
	private String planName;
	private BigDecimal insuranceAmt;
	private Boolean isPolicyAvaiable;
	
	public String getPlanId() {
		return planId;
	}
	public String getPlanName() {
		return planName;
	}
	public BigDecimal getInsuranceAmt() {
		return insuranceAmt;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public void setInsuranceAmt(BigDecimal insuranceAmt) {
		this.insuranceAmt = insuranceAmt;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Boolean getIsPolicyAvaiable() {
		return isPolicyAvaiable;
	}
	public void setIsPolicyAvaiable(Boolean isPolicyAvaiable) {
		this.isPolicyAvaiable = isPolicyAvaiable;
	}
}
