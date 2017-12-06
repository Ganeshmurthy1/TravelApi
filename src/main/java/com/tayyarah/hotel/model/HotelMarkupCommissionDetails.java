package com.tayyarah.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tayyarah.common.model.CommissionDetails;
import com.tayyarah.hotel.entity.HotelMarkup;



public class HotelMarkupCommissionDetails implements Serializable {
	
	
	public HotelMarkupCommissionDetails(String rateType, String companyId,
			Map<String,CommissionDetails> commissionDetailsMap) {
		super();
		this.rateType = rateType;
		this.companyId = companyId;
		this.commissionDetailsMap = commissionDetailsMap;
		this.markups = new HashMap<String,List<HotelMarkup>>();
	}
	public HotelMarkupCommissionDetails() {
		super();
		this.rateType = "Not Available";
		this.companyId = "-1";
		this.commissionDetailsMap = new HashMap<String,CommissionDetails>();
		this.markups = new HashMap<String,List<HotelMarkup>>();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rateType;
	private String companyId;

	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	private Map<String,CommissionDetails> commissionDetailsMap = new HashMap<String,CommissionDetails>();
	
	public Map<String, CommissionDetails> getCommissionDetailsMap() {
		return commissionDetailsMap;
	}
	public void setCommissionDetailsMap(Map<String, CommissionDetails> commissionDetailsMap) {
		this.commissionDetailsMap = commissionDetailsMap;
	}
	public void initCommissionDetailsMap(List<CommissionDetails> commissionDetailslist) {
		this.commissionDetailsMap = new HashMap<String,CommissionDetails>();
		for (CommissionDetails i : commissionDetailslist) commissionDetailsMap.put(i.getCompanyId(),i);	
	}
	//private List<CommissionDetails> commissionDetailslist=new ArrayList<CommissionDetails>();
	//public List<CommissionDetails> getCommissionDetailslist() {
	//	return commissionDetailslist;
	//}
	//public void setCommissionDetailslist(
	//		List<CommissionDetails> commissionDetailslist) {
	//	this.commissionDetailslist = commissionDetailslist;
	//}
	//private  Map<String, BigDecimal> compnayMarkupMap=new HashMap<String, BigDecimal>();
	private Map<String,List<HotelMarkup>> markups;
	
	public Map<String, List<HotelMarkup>> getMarkups() {
		return markups;
	}
	public void setMarkups(Map<String, List<HotelMarkup>> markups) {
		this.markups = markups;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}	
	
}
