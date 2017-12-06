package com.tayyarah.flight.exception;

import java.util.Map;

import com.tayyarah.common.exception.BaseException;
import com.tayyarah.common.exception.ErrorCodeEnum;

public class FlightException extends BaseException {
	
	private static final long serialVersionUID = 8823356956725033191L;

	public FlightException(ErrorCodeEnum errorCode) {
		super();
		this.setErrorCode(errorCode);
		this.setDebugMessage(errorCode.getMessageKey());
	}
	
	public FlightException(ErrorCodeEnum errorCode, String debugMessage) {
		super();
		this.setErrorCode(errorCode);
		this.setDebugMessage(debugMessage);
	}
	
	public FlightException(ErrorCodeEnum errorCode, String debugMessage,String orderID,String paymentID) {
		super();
		this.setErrorCode(errorCode);
		this.setDebugMessage(debugMessage);
		this.setOrderId(orderID);
		this.setPaymentId(paymentID);
	}
	
	public FlightException(ErrorCodeEnum errorCode, String debugMessage, 
			Map<String, String> messageArgs) {
		super();
		this.setErrorCode(errorCode);
		this.setDebugMessage(debugMessage);
		this.messageArgs = messageArgs;
	}

}
