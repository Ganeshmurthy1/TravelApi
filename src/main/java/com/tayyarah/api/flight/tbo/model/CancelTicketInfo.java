package com.tayyarah.api.flight.tbo.model;

public class CancelTicketInfo {

	private String status;
	private String statusmessage;	
	private Double refundedAmount;
	private Double cancellationCharge;
	private Double serviceTaxOnRAF;
	private String changerequeststatus;
	private String apichangerequestid;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusmessage() {
		return statusmessage;
	}
	public void setStatusmessage(String statusmessage) {
		this.statusmessage = statusmessage;
	}
	public Double getRefundedAmount() {
		return refundedAmount;
	}
	public void setRefundedAmount(Double refundedAmount) {
		this.refundedAmount = refundedAmount;
	}
	public Double getCancellationCharge() {
		return cancellationCharge;
	}
	public void setCancellationCharge(Double cancellationCharge) {
		this.cancellationCharge = cancellationCharge;
	}
	public Double getServiceTaxOnRAF() {
		return serviceTaxOnRAF;
	}
	public void setServiceTaxOnRAF(Double serviceTaxOnRAF) {
		this.serviceTaxOnRAF = serviceTaxOnRAF;
	}
	public String getChangerequeststatus() {
		return changerequeststatus;
	}
	public void setChangerequeststatus(String changerequeststatus) {
		this.changerequeststatus = changerequeststatus;
	}
	public String getApichangerequestid() {
		return apichangerequestid;
	}
	public void setApichangerequestid(String apichangerequestid) {
		this.apichangerequestid = apichangerequestid;
	}
	
}
