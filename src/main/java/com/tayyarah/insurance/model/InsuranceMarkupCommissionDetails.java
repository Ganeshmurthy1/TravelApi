package com.tayyarah.insurance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tayyarah.common.model.CommissionDetails;



public class InsuranceMarkupCommissionDetails implements Serializable{
	private static final long serialVersionUID = 1L;
	private String rateType;
	private String companyId;
	private  Map<String, BigDecimal> companyMarkupMap=new HashMap<String, BigDecimal>();
	private  Map<String, BigDecimal> companyCommissionMap=new HashMap<String, BigDecimal>();

	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	private List<CommissionDetails> commissionDetailslist = new ArrayList<CommissionDetails>();
	public List<CommissionDetails> getCommissionDetailslist() {
		return commissionDetailslist;
	}
	public void setCommissionDetailslist(
			List<CommissionDetails> commissionDetailslist) {
		this.commissionDetailslist = commissionDetailslist;
	}	
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	
	
	public Map<String, BigDecimal> getCompanyCommissionMap() {
		return companyCommissionMap;
	}
	public void setCompanyCommissionMap(Map<String, BigDecimal> companyCommissionMap) {
		this.companyCommissionMap = companyCommissionMap;
	}
	public Map<String, BigDecimal> getCompanyMarkupMap() {
		return companyMarkupMap;
	}
	public void setCompanyMarkupMap(Map<String, BigDecimal> companyMarkupMap) {
		this.companyMarkupMap = companyMarkupMap;
	}
}
