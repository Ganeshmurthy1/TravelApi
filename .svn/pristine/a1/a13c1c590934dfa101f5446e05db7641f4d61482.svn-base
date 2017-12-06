package com.tayyarah.hotel.model;

import java.io.Serializable;

public class Status implements Serializable{
	public Status() {
		super();
		this.code = STATUS_CODE_ERROR;
		this.message = STATUS_MESSAGE_ERROR;
	}
	/**
	 * author:Basha
	 * created:01-06-2017
	 * for api response coming from api provider
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return "Status [code=" + code + ", message=" + message + "]";
	}
	
	
	public static final String STATUS_CODE_SUCCESS = "1";
	public static final String STATUS_CODE_ERROR= "0";
	public static final String STATUS_CODE_ERROR_APPKEY= "0";
	public static final String STATUS_MESSAGE_SUCCESS = "Success";
	public static final String STATUS_MESSAGE_ERROR= "Error:";
	public static final String STATUS_MESSAGE_APPKEY_NOTFOUND= "app key null or blank";
	
	
	
	
	/*
	NotSet = 0,
	Successfull = 1,
	Failed = 2,
	InValidRequest = 3,
	InValidSession = 4,
	InValidCredentials = 5
	
	*/
	
	public static final String STATUS_CODE_CANCEL_NOT_SET = "0";
	public static final String STATUS_CODE_CANCEL_SUCCESSFULL= "1";
	public static final String STATUS_CODE_CANCEL_FAILED= "2";
	public static final String STATUS_CODE_CANCEL_INVALID_REQUEST = "3";
	public static final String STATUS_CODE_CANCEL_INVALID_SESSION = "4";
	public static final String STATUS_CODE_CANCEL_INVALID_CREDENTIALS ="5";
	
	
	public static final String STATUS_MESSAGE_CANCEL_NOT_SET = "Cancellation Not set";
	public static final String STATUS_MESSAGE_CANCEL_SUCCESSFULL= "Cancellation Success";
	public static final String STATUS_MESSAGE_CANCEL_FAILED = "Cancellation Failed";
	public static final String STATUS_MESSAGE_CANCEL_INVALID_REQUEST = "Cancellation requuest Is Invalid";
	public static final String STATUS_MESSAGE_CANCEL_INVALID_SESSION = "Cancellation Request Session Is Invalid";
	public static final String STATUS_MESSAGE_CANCEL_INVALID_CREDENTIALS = "Cancellation Request Invalid Credentials.";
	public static final String STATUS_MESSAGE_SUPPLIER_ERROR = "Error from supplier end.";
	
	

	public Status(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private String code;
	private String message;
}

	

