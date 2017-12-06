/**
 * 
 */
package com.tayyarah.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.tayyarah.hotel.entity.HotelOrderRow;



/**
 * @author info : Manish Samrat
 * @createdAt : 01/06/2017
 * @version : 1.0
 */

public class HotelOrderRowDetailResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	public HotelOrderRowDetailResponse(){
		
	}
	
	public HotelOrderRowDetailResponse(HotelOrderRow hotelOrderRow){
		this.orderReference=hotelOrderRow.getOrderReference();
		this.referenceCode=hotelOrderRow.getReferenceCode();
		this.cancelMode=hotelOrderRow.getCancelMode();
		this.apiProvoder=hotelOrderRow.getApiProvoder();
		this.apiPrice=hotelOrderRow.getApiPrice();
		this.finalPrice=hotelOrderRow.getFinalPrice();
		this.markupAmount=hotelOrderRow.getMarkupAmount();
		this.apiCurrency=hotelOrderRow.getApiCurrency();
		this.booking_currency=hotelOrderRow.getBooking_currency();
		this.baseCurrency=hotelOrderRow.getBaseCurrency();
		this.feeAmount=hotelOrderRow.getFeeAmount();
		this.discountAmount=hotelOrderRow.getDiscountAmount();
		this.taxes=hotelOrderRow.getTaxes();
		this.paymentStatus=hotelOrderRow.getPaymentStatus();
		this.statusAction=hotelOrderRow.getStatusAction();
		this.baseToBookingExchangeRate=hotelOrderRow.getBaseToBookingExchangeRate();
		this.apiToBaseExchangeRate=hotelOrderRow.getApiToBaseExchangeRate();
		this.bookingStatus=hotelOrderRow.getBookingStatus();
		this.refUniqueType=hotelOrderRow.getRefUniqueType();
		this.invoiceNo=hotelOrderRow.getInvoiceNo();
		this.createdBy=hotelOrderRow.getCreatedBy();
		this.checkInDate=hotelOrderRow.getCheckInDate();
		this.checkOutDate=hotelOrderRow.getCheckOutDate();
		this.totalGuest=hotelOrderRow.getTotalGuest();
		this.agencyUserName=hotelOrderRow.getAgencyUserName();
		this.apiComments=hotelOrderRow.getApiComments();
		this.companyId=hotelOrderRow.getCompanyId();
		this.configId=hotelOrderRow.getConfigId();
		this.userId=hotelOrderRow.getUserId();
		this.noOfRooms=hotelOrderRow.getNoOfRooms();
		this.userComments=hotelOrderRow.getUserComments();
		this.updatedBy=hotelOrderRow.getUpdatedBy();
		this.apiBookingId=hotelOrderRow.getApiBookingId();
		this.apiConfirmationNo=hotelOrderRow.getApiConfirmationNo();
		this.confirmationNo=hotelOrderRow.getConfirmationNo();
		this.searchKey=hotelOrderRow.getSearchKey();

		this.isCreditNoteIssued=hotelOrderRow.isCreditNoteIssued();
//		this.isOrderUpdated=hotelOrderRow.getorder;
//		
//		this.bookingMode=hotelOrderRow.get;
//		this.bookingDate=hotelOrderRow.get;
//		this.serviceTax=hotelOrderRow.get;
	}
	
	private String orderReference;
	private String referenceCode;
	private String cancelMode;
	private String apiProvoder;
	private BigDecimal apiPrice;
	private BigDecimal finalPrice;
	private BigDecimal markupAmount;
	private String apiCurrency;
	private String booking_currency;
	private String baseCurrency;
	private BigDecimal feeAmount;
	private BigDecimal discountAmount;
	private BigDecimal taxes;
	private String paymentStatus;
	private String statusAction;
	private BigDecimal baseToBookingExchangeRate;
	private BigDecimal apiToBaseExchangeRate;
	private String bookingStatus;
	private String refUniqueType;
	private String invoiceNo;
	private String createdBy;
	private Date checkInDate;
	private Date checkOutDate;
	private int totalGuest;
	private String agencyUserName;
	private String apiComments;
	private String companyId;
	private String configId;
	private String userId;
	private int noOfRooms;
	private String userComments;
	private String updatedBy;
	private String apiBookingId;
	private String apiConfirmationNo;
	private String confirmationNo;
	private Integer searchKey;

	private boolean isCreditNoteIssued;
	private boolean isOrderUpdated;

	private String bookingMode;
	private String bookingDate;
	private BigDecimal serviceTax;

	public String getOrderReference() {
		return orderReference;
	}

	public String getReferenceCode() {
		return referenceCode;
	}

	public String getCancelMode() {
		return cancelMode;
	}

	public String getApiProvoder() {
		return apiProvoder;
	}

	public BigDecimal getApiPrice() {
		return apiPrice;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	public BigDecimal getMarkupAmount() {
		return markupAmount;
	}

	public String getApiCurrency() {
		return apiCurrency;
	}

	public String getBooking_currency() {
		return booking_currency;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public BigDecimal getTaxes() {
		return taxes;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public String getStatusAction() {
		return statusAction;
	}

	public BigDecimal getBaseToBookingExchangeRate() {
		return baseToBookingExchangeRate;
	}

	public BigDecimal getApiToBaseExchangeRate() {
		return apiToBaseExchangeRate;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public String getRefUniqueType() {
		return refUniqueType;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public int getTotalGuest() {
		return totalGuest;
	}

	public String getAgencyUserName() {
		return agencyUserName;
	}

	public String getApiComments() {
		return apiComments;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getConfigId() {
		return configId;
	}

	public String getUserId() {
		return userId;
	}

	public int getNoOfRooms() {
		return noOfRooms;
	}

	public String getUserComments() {
		return userComments;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public String getApiBookingId() {
		return apiBookingId;
	}

	public String getApiConfirmationNo() {
		return apiConfirmationNo;
	}

	public String getConfirmationNo() {
		return confirmationNo;
	}

	public Integer getSearchKey() {
		return searchKey;
	}

	public boolean isCreditNoteIssued() {
		return isCreditNoteIssued;
	}

	public boolean isOrderUpdated() {
		return isOrderUpdated;
	}

	public String getBookingMode() {
		return bookingMode;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public BigDecimal getServiceTax() {
		return serviceTax;
	}

	public void setOrderReference(String orderReference) {
		this.orderReference = orderReference;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public void setCancelMode(String cancelMode) {
		this.cancelMode = cancelMode;
	}

	public void setApiProvoder(String apiProvoder) {
		this.apiProvoder = apiProvoder;
	}

	public void setApiPrice(BigDecimal apiPrice) {
		this.apiPrice = apiPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}

	public void setMarkupAmount(BigDecimal markupAmount) {
		this.markupAmount = markupAmount;
	}

	public void setApiCurrency(String apiCurrency) {
		this.apiCurrency = apiCurrency;
	}

	public void setBooking_currency(String booking_currency) {
		this.booking_currency = booking_currency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public void setTaxes(BigDecimal taxes) {
		this.taxes = taxes;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public void setStatusAction(String statusAction) {
		this.statusAction = statusAction;
	}

	public void setBaseToBookingExchangeRate(BigDecimal baseToBookingExchangeRate) {
		this.baseToBookingExchangeRate = baseToBookingExchangeRate;
	}

	public void setApiToBaseExchangeRate(BigDecimal apiToBaseExchangeRate) {
		this.apiToBaseExchangeRate = apiToBaseExchangeRate;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public void setRefUniqueType(String refUniqueType) {
		this.refUniqueType = refUniqueType;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public void setTotalGuest(int totalGuest) {
		this.totalGuest = totalGuest;
	}

	public void setAgencyUserName(String agencyUserName) {
		this.agencyUserName = agencyUserName;
	}

	public void setApiComments(String apiComments) {
		this.apiComments = apiComments;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setNoOfRooms(int noOfRooms) {
		this.noOfRooms = noOfRooms;
	}

	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setApiBookingId(String apiBookingId) {
		this.apiBookingId = apiBookingId;
	}

	public void setApiConfirmationNo(String apiConfirmationNo) {
		this.apiConfirmationNo = apiConfirmationNo;
	}

	public void setConfirmationNo(String confirmationNo) {
		this.confirmationNo = confirmationNo;
	}

	public void setSearchKey(Integer searchKey) {
		this.searchKey = searchKey;
	}

	public void setCreditNoteIssued(boolean isCreditNoteIssued) {
		this.isCreditNoteIssued = isCreditNoteIssued;
	}

	public void setOrderUpdated(boolean isOrderUpdated) {
		this.isOrderUpdated = isOrderUpdated;
	}

	public void setBookingMode(String bookingMode) {
		this.bookingMode = bookingMode;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public void setServiceTax(BigDecimal serviceTax) {
		this.serviceTax = serviceTax;
	}

}
