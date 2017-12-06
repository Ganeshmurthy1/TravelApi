package com.tayyarah.hotel.exception;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.RestError;



public class HotelBaseException extends RuntimeException implements Serializable {
	
	private static final long serialVersionUID = -8961905267911341175L;
	
	
	protected String message;
	protected HotelErrorCodeEnum errorCode;
	protected ErrorCodeCustomerEnum errorCustomerCode;
	public ErrorCodeCustomerEnum getErrorCustomerCode() {
		return errorCustomerCode;
	}
	public void setErrorCustomerCode(ErrorCodeCustomerEnum errorCustomerCode) {
		this.errorCustomerCode = errorCustomerCode;
	}

	protected String debugMessage;
	protected Map<String, String> messageArgs = new HashMap<String, String>();
	
	public String getMessage(){
		return this.message;
	}
	public void setMessage(String message){
		this.message = message;
	}
	
	public HotelErrorCodeEnum getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(HotelErrorCodeEnum errorCode) {
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

	public RestError transformException(int httpStatus) {
		RestError restError = new RestError();
		restError.setHttpStatus(httpStatus);
		restError.setServiceId(errorCode.getServiceId());
		restError.setErrorCode(errorCode.getErrorCode());
		restError.setDebugMessage(debugMessage);
		restError.setMessageArgs(messageArgs);
		return restError;
	}

}
