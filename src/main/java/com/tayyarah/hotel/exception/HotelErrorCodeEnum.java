package com.tayyarah.hotel.exception;

public interface HotelErrorCodeEnum {
	int getErrorCode();
	String getName();
	int getServiceId();
	String getMessageKey();
	String getDefaultMessage();
}
