package com.tayyarah.flight.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tayyarah.flight.entity.FlightOrderRowCancellationAPIResponse;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class CancelTicketResponse {

	private String Bookstatus;
	private String Status;
	private String Statusmessage;
	private String orderid;	
	private String pnr;
	private String eticketnumber;
	private List<com.tayyarah.api.flight.tbo.model.CancelTicketInfo> CancelTicketInfo = new ArrayList<com.tayyarah.api.flight.tbo.model.CancelTicketInfo>();
	@JsonIgnore
	private FlightOrderRowCancellationAPIResponse flightOrderRowCancellationAPIResponse ;	
	
	
	public static final int ResponseStatus_NotSet = 0;
	public static final int ResponseStatus_Successfull = 1;
	public static final int ResponseStatus_Failed = 2;
	public static final int ResponseStatus_InValidRequest = 3;
	public static final int ResponseStatus_InValidSession = 4;
	public static final int ResponseStatus_InValidCredentials = 5;	
	
	public static final int ChangeRequestStatus_NotSet = 0;
	public static final int ChangeRequestStatus_Unassigned = 1;
	public static final int ChangeRequestStatus_Assigned = 2;
	public static final int ChangeRequestStatus_Acknowledged = 3;
	public static final int ChangeRequestStatus_Completed = 4;
	public static final int ChangeRequestStatus_Rejected = 5;
	public static final int ChangeRequestStatus_Closed = 6;
	public static final int ChangeRequestStatus_Pending = 7;
	public static final int ChangeRequestStatus_Other = 8;	
	
	
	public String getBookstatus() {
		return Bookstatus;
	}
	public void setBookstatus(String bookstatus) {
		Bookstatus = bookstatus;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	public String getStatusmessage() {
		return Statusmessage;
	}
	public void setStatusmessage(String statusmessage) {
		Statusmessage = statusmessage;
	}
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public String getEticketnumber() {
		return eticketnumber;
	}
	public void setEticketnumber(String eticketnumber) {
		this.eticketnumber = eticketnumber;
	}
	public List<com.tayyarah.api.flight.tbo.model.CancelTicketInfo> getCancelTicketInfo() {
		return CancelTicketInfo;
	}
	public void setCancelTicketInfo(List<com.tayyarah.api.flight.tbo.model.CancelTicketInfo> cancelTicketInfo) {
		CancelTicketInfo = cancelTicketInfo;
	}
	public FlightOrderRowCancellationAPIResponse getFlightOrderRowCancellationAPIResponse() {
		return flightOrderRowCancellationAPIResponse;
	}
	public void setFlightOrderRowCancellationAPIResponse(
			FlightOrderRowCancellationAPIResponse flightOrderRowCancellationAPIResponses) {
		this.flightOrderRowCancellationAPIResponse = flightOrderRowCancellationAPIResponses;
	}
	
	
}
