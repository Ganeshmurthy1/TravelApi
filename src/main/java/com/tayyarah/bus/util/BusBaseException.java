package com.tayyarah.bus.util;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class BusBaseException extends RuntimeException implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String orderId;
	protected String paymentId;
	protected String message;
	protected ErrorCodeEnum errorCode;
	protected String debugMessage;
	public String getOrderId() {
		return orderId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public String getMessage() {
		return message;
	}
	public ErrorCodeEnum getErrorCode() {
		return errorCode;
	}
	public String getDebugMessage() {
		return debugMessage;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setErrorCode(ErrorCodeEnum errorCode) {
		this.errorCode = errorCode;
	}
	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}
	public BusRestError transformException(int httpStatus) {
		BusRestError busRestError = new BusRestError();
		busRestError.setHttpStatus(httpStatus);		
		busRestError.setErrorCode(errorCode.getErrorCode());		
		busRestError.setErrorMessage(debugMessage);
		busRestError.setOrderId(orderId);
		busRestError.setPaymentId(paymentId);
		return busRestError;
	}
}
