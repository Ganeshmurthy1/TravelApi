package com.tayyarah.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.tayyarah.hotel.entity.HotelOrderRoomInfo;
import com.tayyarah.hotel.entity.HotelOrderRow;

public class HotelReport implements Serializable{

	/**
	 * @author info raham
	 * created date:14-09-2015
	 */
	private static final long serialVersionUID = 1L;
	private HotelOrderRow hotelOrderRow;
	private List<HotelOrderRoomInfo> hotelOrderRoomInfo;
	private String companyId;
	private String orderRef;
	private String configId;
	private String createdBy;
	private BigDecimal total;
	private BigDecimal base_price;
	private BigDecimal tax;
	private BigDecimal servPrice;
	private BigDecimal discount;
	private BigDecimal fee_amount;
	private BigDecimal agentCom;
	private String curCode;
	private String lastname;
	private String firstname;
	private String agencyUsername;
	private String todayDate;
	private String yesterDayDate;
	private String user;
	private String booking_status;
	private String transcheckInDate;
	private String transcheckOutDate;
	private String bookingDate;
	private String transactionType;
	private String email;
	private String mobile;
	private String phone;
	private int guests;
	private String gender;
	private String paymentStatus;
	private String ref_code;
	private String hotelName;
	private String hotel_loc;
	private String hotel_cat;
	private String state;
	private String country;
	private String statusAction;
	private Long Id;
	private String hotelType;
	private Timestamp createdAt;

	private String createdDate;
	private String status;
	private String mealType;
	private Integer cancellationDay;
	private BigDecimal feeAmount;
	private String startDate;
	private String endDate;
	private String remarks;
	private String formattedFeeAmount;
	private Date fromDate;
	private String feeType;
	private String apiComments;
	private String userComments;
	private String userId;
	private String filterCompanyType;
	private BigDecimal commissionPercentage;
	private BigDecimal agentCommByRate;
	private BigDecimal totAmountSpent;
	private BigDecimal totAgentComm;
	private boolean creditNoteIssued;
	private BigDecimal cancellationFees;
	private  BigDecimal markUp;
	private  boolean orderUpdated;
	private BigDecimal finalPrice ;
	private String creditNoteActiontype;
	private String referenceCode;
	//private ApiProvider apiProvider;
	private  String companysGstOn;
	private  BigDecimal convenienceFees ;
	private boolean orderRequested;
	private  BigDecimal apiChargeAmount;
	private boolean isCancelInitiated;
	private String confirmNo;
	private String  salesPersonName;
	private BigDecimal netAmnt;
	private String  apiResponseMessage;
	private Date checkInDate;
	private Date checkOutDate;
	 
	
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMealType() {
		return mealType;
	}
	public void setMealType(String mealType) {
		this.mealType = mealType;
	}
	public Integer getCancellationDay() {
		return cancellationDay;
	}
	public void setCancellationDay(Integer cancellationDay) {
		this.cancellationDay = cancellationDay;
	}
	public BigDecimal getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFormattedFeeAmount() {
		return formattedFeeAmount;
	}
	public void setFormattedFeeAmount(String formattedFeeAmount) {
		this.formattedFeeAmount = formattedFeeAmount;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public String getApiComments() {
		return apiComments;
	}
	public void setApiComments(String apiComments) {
		this.apiComments = apiComments;
	}
	public String getUserComments() {
		return userComments;
	}
	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFilterCompanyType() {
		return filterCompanyType;
	}
	public void setFilterCompanyType(String filterCompanyType) {
		this.filterCompanyType = filterCompanyType;
	}
	public BigDecimal getCommissionPercentage() {
		return commissionPercentage;
	}
	public void setCommissionPercentage(BigDecimal commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}
	public BigDecimal getAgentCommByRate() {
		return agentCommByRate;
	}
	public void setAgentCommByRate(BigDecimal agentCommByRate) {
		this.agentCommByRate = agentCommByRate;
	}
	public BigDecimal getTotAmountSpent() {
		return totAmountSpent;
	}
	public void setTotAmountSpent(BigDecimal totAmountSpent) {
		this.totAmountSpent = totAmountSpent;
	}
	public BigDecimal getTotAgentComm() {
		return totAgentComm;
	}
	public void setTotAgentComm(BigDecimal totAgentComm) {
		this.totAgentComm = totAgentComm;
	}
	public boolean isCreditNoteIssued() {
		return creditNoteIssued;
	}
	public void setCreditNoteIssued(boolean creditNoteIssued) {
		this.creditNoteIssued = creditNoteIssued;
	}
	public BigDecimal getCancellationFees() {
		return cancellationFees;
	}
	public void setCancellationFees(BigDecimal cancellationFees) {
		this.cancellationFees = cancellationFees;
	}
	public BigDecimal getMarkUp() {
		return markUp;
	}
	public void setMarkUp(BigDecimal markUp) {
		this.markUp = markUp;
	}
	public boolean isOrderUpdated() {
		return orderUpdated;
	}
	public void setOrderUpdated(boolean orderUpdated) {
		this.orderUpdated = orderUpdated;
	}
	public BigDecimal getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}
	public String getCreditNoteActiontype() {
		return creditNoteActiontype;
	}
	public void setCreditNoteActiontype(String creditNoteActiontype) {
		this.creditNoteActiontype = creditNoteActiontype;
	}
	public String getReferenceCode() {
		return referenceCode;
	}
	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}
	public String getCompanysGstOn() {
		return companysGstOn;
	}
	public void setCompanysGstOn(String companysGstOn) {
		this.companysGstOn = companysGstOn;
	}
	public BigDecimal getConvenienceFees() {
		return convenienceFees;
	}
	public void setConvenienceFees(BigDecimal convenienceFees) {
		this.convenienceFees = convenienceFees;
	}
	public boolean isOrderRequested() {
		return orderRequested;
	}
	public void setOrderRequested(boolean orderRequested) {
		this.orderRequested = orderRequested;
	}
	public BigDecimal getApiChargeAmount() {
		return apiChargeAmount;
	}
	public void setApiChargeAmount(BigDecimal apiChargeAmount) {
		this.apiChargeAmount = apiChargeAmount;
	}
	public boolean isCancelInitiated() {
		return isCancelInitiated;
	}
	public void setCancelInitiated(boolean isCancelInitiated) {
		this.isCancelInitiated = isCancelInitiated;
	}
	public String getConfirmNo() {
		return confirmNo;
	}
	public void setConfirmNo(String confirmNo) {
		this.confirmNo = confirmNo;
	}
	public String getSalesPersonName() {
		return salesPersonName;
	}
	public void setSalesPersonName(String salesPersonName) {
		this.salesPersonName = salesPersonName;
	}
	public BigDecimal getNetAmnt() {
		return netAmnt;
	}
	public void setNetAmnt(BigDecimal netAmnt) {
		this.netAmnt = netAmnt;
	}
	public String getApiResponseMessage() {
		return apiResponseMessage;
	}
	public void setApiResponseMessage(String apiResponseMessage) {
		this.apiResponseMessage = apiResponseMessage;
	}
	public String getHotel_loc() {
		return hotel_loc;
	}
	public void setHotel_loc(String hotel_loc) {
		this.hotel_loc = hotel_loc;
	}
	public String getHotel_cat() {
		return hotel_cat;
	}
	public void setHotel_cat(String hotel_cat) {
		this.hotel_cat = hotel_cat;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getBase_price() {
		return base_price;
	}
	public void setBase_price(BigDecimal base_price) {
		this.base_price = base_price;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getServPrice() {
		return servPrice;
	}
	public void setServPrice(BigDecimal servPrice) {
		this.servPrice = servPrice;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public BigDecimal getAgentCom() {
		return agentCom;
	}
	public void setAgentCom(BigDecimal bigDecimal) {
		this.agentCom = bigDecimal;
	}
	public String getCurCode() {
		return curCode;
	}
	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getAgencyUsername() {
		return agencyUsername;
	}
	public void setAgencyUsername(String agencyUsername) {
		this.agencyUsername = agencyUsername;
	}
	public String getTodayDate() {
		return todayDate;
	}
	public void setTodayDate(String todayDate) {
		this.todayDate = todayDate;
	}
	public String getYesterDayDate() {
		return yesterDayDate;
	}
	public void setYesterDayDate(String yesterDayDate) {
		this.yesterDayDate = yesterDayDate;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getBooking_status() {
		return booking_status;
	}
	public void setBooking_status(String booking_status) {
		this.booking_status = booking_status;
	}
	public Date getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(Date date) {
		this.checkInDate = date;
	}
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getGuests() {
		return guests;
	}
	public void setGuests(int guests) {
		this.guests = guests;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	/**
	 * @return the ref_code
	 */
	public String getRef_code() {
		return ref_code;
	}
	/**
	 * @param ref_code the ref_code to set
	 */
	public void setRef_code(String ref_code) {
		this.ref_code = ref_code;
	}
	/**
	 * @return the hotelName
	 */
	public String getHotelName() {
		return hotelName;
	}
	/**
	 * @param hotelName the hotelName to set
	 */
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	/**
	 * @return the fee_amount
	 */
	public BigDecimal getFee_amount() {
		return fee_amount;
	}
	/**
	 * @param fee_amount the fee_amount to set
	 */
	public void setFee_amount(BigDecimal fee_amount) {
		this.fee_amount = fee_amount;
	}
	public String getTranscheckInDate() {
		return transcheckInDate;
	}
	public void setTranscheckInDate(String transcheckInDate) {
		this.transcheckInDate = transcheckInDate;
	}
	public String getTranscheckOutDate() {
		return transcheckOutDate;
	}
	public void setTranscheckOutDate(String transcheckOutDate) {
		this.transcheckOutDate = transcheckOutDate;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getStatusAction() {
		return statusAction;
	}
	public void setStatusAction(String statusAction) {
		this.statusAction = statusAction;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getOrderRef() {
		return orderRef;
	}
	public void setOrderRef(String orderRef) {
		this.orderRef = orderRef;
	}
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getHotelType() {
		return hotelType;
	}
	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public HotelOrderRow getHotelOrderRow() {
		return hotelOrderRow;
	}
	public void setHotelOrderRow(HotelOrderRow hotelOrderRow) {
		this.hotelOrderRow = hotelOrderRow;
	}
	public List<HotelOrderRoomInfo> getHotelOrderRoomInfo() {
		return hotelOrderRoomInfo;
	}
	public void setHotelOrderRoomInfo(List<HotelOrderRoomInfo> hotelOrderRoomInfo) {
		this.hotelOrderRoomInfo = hotelOrderRoomInfo;
	}
	
	
 }
