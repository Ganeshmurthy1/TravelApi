package com.tayyarah.bus.model;

import java.io.Serializable;

public class Status implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	public static final int SUCCESSCODE = 1;
	public static final int FAILEDCODE = 0;
	private Integer code;
	private String message;
	
	public Integer getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
