package com.tayyarah.hotel.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIHotelCancelRequest implements Serializable {

	
	@Override
	public String toString() {
		return "APIHotelCancelRequest [userId=" + userId + ", password="
				+ password + ", appKey=" + appKey + ", orderId=" + orderId
				+ ", remarks=" + remarks + ", methodType=" + methodType + "]";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;	
	private String password;
	private String appKey;
	private String orderId;
	private String remarks;
	
	private String methodType;
	
	public static final String METHOD_INITIATE = "0";
	public static final String METHOD_GET_STATUS = "1";
	
	
	public APIHotelCancelRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getMethodType() {
		return methodType;
	}
	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}
}
