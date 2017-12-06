package com.tayyarah.hotel.exception;

import java.util.Map;

import com.tayyarah.common.exception.ErrorCodeCustomerEnum;




public class HotelException extends HotelBaseException {
	
	private static final long serialVersionUID = 8823356956725033191L;

	public HotelException(HotelErrorCodeEnum errorCode) {
		super();
		this.setErrorCode(errorCode);
		this.setDebugMessage(errorCode.getMessageKey());
	}
	
	public HotelException(ErrorCodeCustomerEnum errorCode,String debugMessage) {
		this.setErrorCustomerCode(errorCode);
		this.setDebugMessage(errorCode.getMessageKey());
	}
	
	public HotelException(HotelErrorCodeEnum errorCode, String debugMessage) {
		super();
		this.setErrorCode(errorCode);
		this.setDebugMessage(debugMessage);
	}
	
	public HotelException(HotelErrorCodeEnum errorCode, String debugMessage, 
			Map<String, String> messageArgs) {
		super();
		this.setErrorCode(errorCode);
		this.setDebugMessage(debugMessage);
		this.messageArgs = messageArgs;
	}

}
