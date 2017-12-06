package com.tayyarah.common.exception;

import java.util.Map;



public class CommonException extends BaseException{	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommonException(ErrorCodeEnum errorCode) {
		super();
		this.setErrorCode(errorCode);
		this.setDebugMessage(errorCode.getMessageKey());
	}
	
	public CommonException(ErrorCodeEnum errorCode, String debugMessage) {
		super();
		this.setErrorCode(errorCode);
		this.setDebugMessage(debugMessage);
	}
	
	public CommonException(ErrorCodeEnum errorCode, String debugMessage,String orderID,String paymentID) {
		super();
		this.setErrorCode(errorCode);
		this.setDebugMessage(debugMessage);
		this.setOrderId(orderID);
		this.setPaymentId(paymentID);
	}
	
	public CommonException(ErrorCodeEnum errorCode, String debugMessage, 
			Map<String, String> messageArgs) {
		super();
		this.setErrorCode(errorCode);
		this.setDebugMessage(debugMessage);
		this.messageArgs = messageArgs;
	}
}
