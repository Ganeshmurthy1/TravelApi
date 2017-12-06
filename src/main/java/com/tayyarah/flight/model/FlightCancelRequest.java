package com.tayyarah.flight.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tayyarah.api.flight.tbo.model.Sector;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlightCancelRequest {

	private String userId;		
	private String password;
	private String appKey;
	private String orderId;
	private String remarks;
	private String methodtype;
	private String cancellationtype;
	private String requesttype;
	private List<Integer> ticketId = new ArrayList<Integer>();
	private List<Sector> sectors = new ArrayList<Sector>();
	
	
	public static final String METHOD_RELEASEPNR = "0";
	public static final String METHOD_CANCELTICKET = "1";
	
	public static final String RequestType_NotSet = "0";
	public static final String RequestType_FullCancellation = "1";
	public static final String RequestType_PartialCancellation = "2";
	public static final String RequestType_Reissuance = "3";
	
	public static final String CancellationType_NotSet = "0";
	public static final String CancellationType_NoShow = "1";
	public static final String CancellationType_FlightCancelled = "2";
	public static final String CancellationType_Others = "3";
	
	public List<Integer> getTicketId() {
		return ticketId;
	}
	public void setTicketId(List<Integer> ticketId) {
		this.ticketId = ticketId;
	}

	public List<Sector> getSectors() {
		return sectors;
	}
	public void setSectors(List<Sector> sectors) {
		this.sectors = sectors;
	}
	
	
	public String getRequesttype() {
		return requesttype;
	}
	public void setRequesttype(String requesttype) {
		this.requesttype = requesttype;
	}
	public String getCancellationtype() {
		return cancellationtype;
	}
	public void setCancellationtype(String cancellationtype) {
		this.cancellationtype = cancellationtype;
	}
	
	public String getMethodtype() {
		return methodtype;
	}
	public void setMethodtype(String methodtype) {
		this.methodtype = methodtype;
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
	
	@Override
	public String toString() {
		return "FlightCancelRequest [userId=" + userId + ", password="
				+ password + ", appKey=" + appKey + ", orderId=" + orderId
				+ ", remarks=" + remarks + ", methodType=" + methodtype + ", cancellationtype=" + cancellationtype + ", requesttype=" + requesttype + "]";
	}
	/**
	 * 
	 */
	
}
