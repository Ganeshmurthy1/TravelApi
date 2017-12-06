package com.tayyarah.common.exception;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BaseException extends RuntimeException implements Serializable {
	
	private static final long serialVersionUID = -8961905267911341174L;
	
	
	protected String orderId;
	protected String paymentId;
	protected String message;
	protected ErrorCodeEnum errorCode;
	protected String debugMessage;
	protected Map<String, String> messageArgs = new HashMap<String, String>();
	
	public String getMessage(){
		return this.message;
	}
	public void setMessage(String message){
		this.message = message;
	}
	
	public ErrorCodeEnum getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(ErrorCodeEnum errorCode) {
		this.errorCode = errorCode;
	}

	public String getDebugMessage() {
		return debugMessage;
	}
	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}
	
	public Map<String, String> getMessageArgs() {
		return messageArgs;
	}
	public void setMessageArgs(Map<String, String> messageArgs) {
		this.messageArgs = messageArgs;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public RestError transformException(int httpStatus) {
		RestError restError = new RestError();
		restError.setHttpStatus(httpStatus);
		restError.setServiceId(errorCode.getServiceId());
		restError.setErrorCode(errorCode.getErrorCode());
		restError.setDebugMessage(debugMessage);
		restError.setMessageArgs(messageArgs);
		restError.setOrderId(orderId);
		restError.setPaymentId(paymentId);
		return restError;
	}

}
