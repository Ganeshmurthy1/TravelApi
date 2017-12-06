package com.tayyarah.insurance.model;

import java.io.Serializable;
import java.util.List;

import com.tayyarah.common.entity.RmConfigTripDetailsModel;


public class CreateInsurancePolicyRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int  Smart_50K_Excluding = 1;
	public static final int  Smart_Domestic_Plan = 2;
	public static final int  Smart_50K_Including = 3;
	
	private String userId;
	private String userName;
	private String app_key;
	private String markupAmount;
	private String currency;
	private String departureDate;
	private String arrivalDate;
	private String noOfDays;
	private String origin;
	private String destination;
	private Integer planId;
	private List<TravellerDetails> travellerDetails;
	private int emulateByCompanyId;
	private String emulateByUserId;
	private Boolean isEmulateFlag;
	private Boolean isRmDetails;
	private String isQuotation;
	private String quotationId;
	/*private List<RmConfigTripDetailsModel> rmDataListDetails;*/
	private Boolean isCompanyEntity;
	private int companyEntityId;
	private String insuredProduct;
	private long insuredProductOrderRowId;
	private String insuredProductOrderId;
	
	public String getInsuredProductOrderId() {
		return insuredProductOrderId;
	}
	public void setInsuredProductOrderId(String insuredProductOrderId) {
		this.insuredProductOrderId = insuredProductOrderId;
	}
	public String getInsuredProduct() {
		return insuredProduct;
	}
	public long getInsuredProductOrderRowId() {
		return insuredProductOrderRowId;
	}
	public void setInsuredProduct(String insuredProduct) {
		this.insuredProduct = insuredProduct;
	}
	public void setInsuredProductOrderRowId(long insuredProductOrderRowId) {
		this.insuredProductOrderRowId = insuredProductOrderRowId;
	}
	public String getApp_key() {
		return app_key;
	}
	public String getMarkupAmount() {
		return markupAmount;
	}
	public String getCurrency() {
		return currency;
	}
	public String getDepartureDate() {
		return departureDate;
	}
	public String getArrivalDate() {
		return arrivalDate;
	}
	public String getNoOfDays() {
		return noOfDays;
	}
	
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	public void setMarkupAmount(String markupAmount) {
		this.markupAmount = markupAmount;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}
	
	public String getOrigin() {
		return origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public List<TravellerDetails> getTravellerDetails() {
		return travellerDetails;
	}
	public void setTravellerDetails(List<TravellerDetails> travellerDetails) {
		this.travellerDetails = travellerDetails;
	}
	public String getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public int getEmulateByCompanyId() {
		return emulateByCompanyId;
	}
	public String getEmulateByUserId() {
		return emulateByUserId;
	}
	public Boolean getIsEmulateFlag() {
		return isEmulateFlag;
	}
	public Boolean getIsRmDetails() {
		return isRmDetails;
	}
	public String getIsQuotation() {
		return isQuotation;
	}
	public String getQuotationId() {
		return quotationId;
	}
	/*public List<RmConfigTripDetailsModel> getRmDataListDetails() {
		return rmDataListDetails;
	}*/
	public Boolean getIsCompanyEntity() {
		return isCompanyEntity;
	}
	public int getCompanyEntityId() {
		return companyEntityId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setEmulateByCompanyId(int emulateByCompanyId) {
		this.emulateByCompanyId = emulateByCompanyId;
	}
	public void setEmulateByUserId(String emulateByUserId) {
		this.emulateByUserId = emulateByUserId;
	}
	public void setIsEmulateFlag(Boolean isEmulateFlag) {
		this.isEmulateFlag = isEmulateFlag;
	}
	public void setIsRmDetails(Boolean isRmDetails) {
		this.isRmDetails = isRmDetails;
	}
	public void setIsQuotation(String isQuotation) {
		this.isQuotation = isQuotation;
	}
	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}
	/*public void setRmDataListDetails(List<RmConfigTripDetailsModel> rmDataListDetails) {
		this.rmDataListDetails = rmDataListDetails;
	}*/
	public void setIsCompanyEntity(Boolean isCompanyEntity) {
		this.isCompanyEntity = isCompanyEntity;
	}
	public void setCompanyEntityId(int companyEntityId) {
		this.companyEntityId = companyEntityId;
	}
	/*public String getPaxId() {
		return paxId;
	}
	public void setPaxId(String paxId) {
		this.paxId = paxId;
	}*/
	
}
