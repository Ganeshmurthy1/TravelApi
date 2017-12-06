package com.tayyarah.common.exception;

public interface ErrorCodeEnum {	
	int getErrorCode();
	String getName();
	int getServiceId();
	String getMessageKey();
	String getDefaultMessage();
}
