package com.tayyarah.flight.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;



@Entity
@Table(name="flight_order_row_cancellation")
public class FlightOrderRowCancellation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	
	@GeneratedValue
	private Long id;
	/*	@Column(name="id",columnDefinition="BIGINT(20) default '0'")*/
	@Column(name="api_charge_amount")
	private Double apichargeamount;
	@Column(name="api_refund_amount")
	private Double apirefundamount;
	@Column(name="api_servicetaxonraf")
	private Double apiservicetaxonraf;
	@Column(name="api_status_code")
	private String apistatuscode;
	@Column(name="payment_type")
	private String paymenttype;
	@Column(name="ordered_At")
	private Timestamp orderedAt;
	@Column(name="updated_At")
	private Timestamp updatedAt;
	@Column(name="status_message")
	private String statusmessage;
	@Column(name="api_request_id")
	private String apirequestid;
	@Column(name="api_trace_id")
	private String apitrace_id;
	@Column(name="order_id")
	private String orderid;
	@Column(name="Change_Request_Status")
	private Integer ChangeRequestStatus;

	
	@OneToOne(cascade = CascadeType.ALL, targetEntity = FlightOrderRow.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "flightOrderRow_id", referencedColumnName = "id")
	private FlightOrderRow flightOrderRow ;	
	
	@Column(name="noof_attempt")
	private Integer noofAttempt;
	
	/*@OneToMany(cascade = CascadeType.ALL, targetEntity = FlightOrderRowCancellationAPIResponse.class)
	@JoinColumn(name = "flightOrderRowCancellationAPIResponse_id", referencedColumnName = "id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<FlightOrderRowCancellationAPIResponse> flightOrderRowCancellationAPIResponse;*/
	
	public Double getApichargeamount() {
		return apichargeamount;
	}
	public void setApichargeamount(Double apichargeamount) {
		this.apichargeamount = apichargeamount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Double getApirefundamount() {
		return apirefundamount;
	}
	public void setApirefundamount(Double apirefundamount) {
		this.apirefundamount = apirefundamount;
	}
	public Double getApiservicetaxonraf() {
		return apiservicetaxonraf;
	}
	public void setApiservicetaxonraf(Double apiservicetaxonraf) {
		this.apiservicetaxonraf = apiservicetaxonraf;
	}
	public String getApistatuscode() {
		return apistatuscode;
	}
	public void setApistatuscode(String apistatuscode) {
		this.apistatuscode = apistatuscode;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public Timestamp getOrderedAt() {
		return orderedAt;
	}
	public void setOrderedAt(Timestamp orderedAt) {
		this.orderedAt = orderedAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getStatusmessage() {
		return statusmessage;
	}
	public void setStatusmessage(String statusmessage) {
		this.statusmessage = statusmessage;
	}
	public String getApirequestid() {
		return apirequestid;
	}
	public void setApirequestid(String apirequestid) {
		this.apirequestid = apirequestid;
	}
	public String getApitrace_id() {
		return apitrace_id;
	}
	public void setApitrace_id(String apitrace_id) {
		this.apitrace_id = apitrace_id;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public FlightOrderRow getFlightOrderRow() {
		return flightOrderRow;
	}
	public void setFlightOrderRow(FlightOrderRow flightOrderRow) {
		this.flightOrderRow = flightOrderRow;
	}
	/*public List<FlightOrderRowCancellationAPIResponse> getFlightOrderRowCancellationAPIResponse() {
		return flightOrderRowCancellationAPIResponse;
	}
	public void setFlightOrderRowCancellationAPIResponse(
			List<FlightOrderRowCancellationAPIResponse> flightOrderRowCancellationAPIResponse) {
		this.flightOrderRowCancellationAPIResponse = flightOrderRowCancellationAPIResponse;
	}*/
	public Integer getChangeRequestStatus() {
		return ChangeRequestStatus;
	}
	public void setChangeRequestStatus(Integer changeRequestStatus) {
		ChangeRequestStatus = changeRequestStatus;
	}
	public Integer getNoofAttempt() {
		return noofAttempt;
	}
	public void setNoofAttempt(Integer noofAttempt) {
		this.noofAttempt = noofAttempt;
	}
	
	
}
