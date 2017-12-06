package com.tayyarah.bus.util;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BusRestError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderId;
	private String paymentId;
	private int httpStatus;
	private int errorCode;
	private String errorMessage;
	
	public BusRestError(){
		super();
	}
	public BusRestError(String orderId,String paymentId,int httpStatus,int errorCode,String errorMessage){
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
		this.orderId = orderId;
		this.paymentId = paymentId;
	}
	
	
	public String getOrderId() {
		return orderId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public int getHttpStatus() {
		return httpStatus;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
