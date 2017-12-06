package com.tayyarah.hotel.exception;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HotelRestError implements Serializable {
	

	private static final long serialVersionUID = 1L;
	public HotelRestError()
	{	
		
	}
	public HotelRestError(int httpStatus, int serviceId, int errorCode,
			String debugMessage, Map<String, String> messageArgs) {
		super();
		this.httpStatus = httpStatus;
		this.serviceId = serviceId;
		this.errorCode = errorCode;
		this.debugMessage = debugMessage;
		this.messageArgs = messageArgs;
	}

	private int httpStatus;
	private int serviceId;
	private int errorCode;
	private String debugMessage;
	private Map<String, String> messageArgs = new HashMap<String, String>();
	
	public int getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getDebugMessage() {
		return debugMessage;
	}
	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}
	
	@Override
	public String toString() {
		return "RestError [httpStatus=" + httpStatus + ", serviceId="
				+ serviceId + ", errorCode=" + errorCode + ", debugMessage="
				+ debugMessage + ", messageArgs=" + messageArgs + "]";
	}
	public Map<String, String> getMessageArgs() {
		return messageArgs;
	}
	public void setMessageArgs(Map<String, String> messageArgs) {
		this.messageArgs = messageArgs;
	}
	
	public HotelBaseException transformRestError() {
		return HotelServiceEnum.createServiceException(this);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
