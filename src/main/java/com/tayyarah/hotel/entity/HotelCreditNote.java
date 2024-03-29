package com.tayyarah.hotel.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name = "hotel_credit_note")
public class HotelCreditNote implements Serializable{

	/**
	 * @author raham
	 * Date:04/01/2016
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	@Transient
	@Column(name="convertDate")
	private String convertDate;
	@Transient
	private String cancelMode;
	
	@Column(name = "total_booking_amount", columnDefinition="decimal(20,10) default '0.00'")
	private BigDecimal totalBookingAmount;
	@Column(name = "refunded_amount", columnDefinition="decimal(20,10) default '0.00'")
	private BigDecimal refundedAmount;
	@Column(name = "convenience_fees", columnDefinition="decimal(20,10) default '0.00'")
	private BigDecimal convenienceFees;
	@Column(name = "cancellation_fees", columnDefinition="decimal(20,10) default '0.00'")
	private BigDecimal cancellationFees;
	@Column(name = "management_fees", columnDefinition="decimal(20,10) default '0.00'")
	private BigDecimal managementFees;
	@Column(name = "gst_amount", columnDefinition="decimal(20,10) default '0.00'")
	private BigDecimal gstAmount;
	@Column(name = "company_id")
	private String companyId="0";
	@Column(name = "user_id")
	private String userId="0";
	@Column(name="ordered_At")
	private Timestamp orderedAt;
	@Column(name="row_id")
	private int rowId;
	@Column(name="before_status")
	private String beforeStatus;
	@Column(name="after_status")
	private String afterStatus;
	private String alterBy;
	@Column(name="action_type")
	private String actionType;
	@Column(name="before_pay_status")
	private String befPayStatus;
	@Column(name="after_pay_status")
	private String afterPayStatus;
	@Column(name="cn_invoice_no")
	private String CNINumber;
	@Column(name="issued_at")
	private Timestamp issuedAt;
	
	@Column(name="is_order_update",columnDefinition="BIT(1) default 0")
	private boolean isOrderUpdated;
	@Column(name="is_creditnote_issue",columnDefinition="BIT(1) default 0")
	private boolean isCreditnoteIssued;
	/*@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_row_id", referencedColumnName = "id")
	private FlightOrderRow flightOrderRow;*/
	 /**
	 * @return the totalBookingAmount
	 */
	public BigDecimal getTotalBookingAmount() {
		return totalBookingAmount;
	}
	/**
	 * @param totalBookingAmount the totalBookingAmount to set
	 */
	public void setTotalBookingAmount(BigDecimal totalBookingAmount) {
		this.totalBookingAmount = totalBookingAmount;
	}
	/**
	 * @return the refundedAmount
	 */
	public BigDecimal getRefundedAmount() {
		return refundedAmount;
	}
	/**
	 * @param refundedAmount the refundedAmount to set
	 */
	public void setRefundedAmount(BigDecimal refundedAmount) {
		this.refundedAmount = refundedAmount;
	}
	/**
	 * @return the convenienceFees
	 */
	public BigDecimal getConvenienceFees() {
		return convenienceFees;
	}
	/**
	 * @param convenienceFees the convenienceFees to set
	 */
	public void setConvenienceFees(BigDecimal convenienceFees) {
		this.convenienceFees = convenienceFees;
	}
	/**
	 * @return the cancellationFees
	 */
	public BigDecimal getCancellationFees() {
		return cancellationFees;
	}
	/**
	 * @param cancellationFees the cancellationFees to set
	 */
	public void setCancellationFees(BigDecimal cancellationFees) {
		this.cancellationFees = cancellationFees;
	}
	/**
	 * @return the gstAmount
	 */
	public BigDecimal getGstAmount() {
		return gstAmount;
	}
	/**
	 * @param gstAmount the gstAmount to set
	 */
	public void setGstAmount(BigDecimal gstAmount) {
		this.gstAmount = gstAmount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/*public FlightOrderRow getFlightOrderRow() {
		return flightOrderRow;
	}
	public void setFlightOrderRow(FlightOrderRow flightOrderRow) {
		this.flightOrderRow = flightOrderRow;
	}*/
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public String getBeforeStatus() {
		return beforeStatus;
	}
	public void setBeforeStatus(String beforeStatus) {
		this.beforeStatus = beforeStatus;
	}
	public String getAfterStatus() {
		return afterStatus;
	}
	public void setAfterStatus(String afterStatus) {
		this.afterStatus = afterStatus;
	}
	public String getAlterBy() {
		return alterBy;
	}
	public void setAlterBy(String alterBy) {
		this.alterBy = alterBy;
	}
	public Timestamp getOrderedAt() {
		return orderedAt;
	}
	public void setOrderedAt(Timestamp orderedAt) {
		this.orderedAt = orderedAt;
	}
	public String getConvertDate() {
		return convertDate;
	}
	public void setConvertDate(String convertDate) {
		this.convertDate = convertDate;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getBefPayStatus() {
		return befPayStatus;
	}
	public void setBefPayStatus(String befPayStatus) {
		this.befPayStatus = befPayStatus;
	}
	public String getAfterPayStatus() {
		return afterPayStatus;
	}
	public void setAfterPayStatus(String afterPayStatus) {
		this.afterPayStatus = afterPayStatus;
	}
	public String getCNINumber() {
		return CNINumber;
	}
	public void setCNINumber(String cNINumber) {
		CNINumber = cNINumber;
	}
	public Timestamp getIssuedAt() {
		return issuedAt;
	}
	public void setIssuedAt(Timestamp issuedAt) {
		this.issuedAt = issuedAt;
	}
	public String getCancelMode() {
		return cancelMode;
	}
	public void setCancelMode(String cancelMode) {
		this.cancelMode = cancelMode;
	}
	public boolean isOrderUpdated() {
		return isOrderUpdated;
	}
	public void setOrderUpdated(boolean isOrderUpdated) {
		this.isOrderUpdated = isOrderUpdated;
	}
	public boolean isCreditnoteIssued() {
		return isCreditnoteIssued;
	}
	public void setCreditnoteIssued(boolean isCreditnoteIssued) {
		this.isCreditnoteIssued = isCreditnoteIssued;
	}
	public BigDecimal getManagementFees() {
		return managementFees;
	}
	public void setManagementFees(BigDecimal managementFees) {
		this.managementFees = managementFees;
	}
	 

	 
}
