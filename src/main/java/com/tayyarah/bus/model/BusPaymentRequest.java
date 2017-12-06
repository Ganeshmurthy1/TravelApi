package com.tayyarah.bus.model;

public class BusPaymentRequest {

	private String app_key;		
	private String paymentId;	
	private String paymentStatus;
	private String responseMessage;
	private String responseCode;
	
	public String getApp_key() {
		return app_key;
	}
	
	
	public String getPaymentId() {
		return paymentId;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}


}
