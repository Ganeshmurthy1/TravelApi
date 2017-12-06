package com.tayyarah.flight.model;

public class ConfirmTicketRequest {

	private String userid;
	private String username;
	private String app_key;
	private String transactionkey;
	private String price_key;
	private String paymode;
	private String orderid;
	private String totalprice;
	private int requesttype;
	public static final int Type_ConfirmTicket = 0;
	public static final int Type_GetHoldTicket = 1;
	
	public int getRequesttype() {
		return requesttype;
	}
	public void setRequesttype(int requesttype) {
		this.requesttype = requesttype;
	}
	public String getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}
	public String getUserid() {
		return userid;
	}
	public String getUsername() {
		return username;
	}
	public String getApp_key() {
		return app_key;
	}
	public String getTransactionkey() {
		return transactionkey;
	}
	public String getPrice_key() {
		return price_key;
	}
	public String getPaymode() {
		return paymode;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	public void setTransactionkey(String transactionkey) {
		this.transactionkey = transactionkey;
	}
	public void setPrice_key(String price_key) {
		this.price_key = price_key;
	}
	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
}