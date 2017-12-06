package com.tayyarah.common.exception;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RestError implements Serializable {
	

	private static final long serialVersionUID = 1L;
	public RestError()
	{	
		
	}
	public RestError(int httpStatus, int serviceId, int errorCode,
			String debugMessage, Map<String, String> messageArgs,String orderid,String paymentid) {
		super();
		this.httpStatus = httpStatus;
		this.serviceId = serviceId;
		this.errorCode = errorCode;
		this.debugMessage = debugMessage;
		this.messageArgs = messageArgs;
		this.OrderId = orderid;
		this.PaymentId = paymentid;
	}
	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getPaymentId() {
		return PaymentId;
	}
	public void setPaymentId(String paymentId) {
		PaymentId = paymentId;
	}

	private String OrderId;
	private String PaymentId;

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
				+ debugMessage + ", messageArgs=" + messageArgs +",OrderId="
						+ OrderId + ", PaymentId=" + PaymentId + "]";
	}
	public Map<String, String> getMessageArgs() {
		return messageArgs;
	}
	public void setMessageArgs(Map<String, String> messageArgs) {
		this.messageArgs = messageArgs;
	}
	
	public BaseException transformRestError() {
		return ServiceEnum.createServiceException(this);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
