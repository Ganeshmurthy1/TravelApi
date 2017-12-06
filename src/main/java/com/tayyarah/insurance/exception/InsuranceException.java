package com.tayyarah.insurance.exception;

import com.tayyarah.common.exception.BaseException;
import com.tayyarah.common.exception.ErrorCodeEnum;

public class InsuranceException  extends BaseException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InsuranceException(){
		super();
	}
	public InsuranceException(ErrorCodeEnum errorCodeEnum,String busErrorMessages){
		super();
		this.setDebugMessage(busErrorMessages);
		this.setErrorCode(errorCodeEnum);	
	}   
}
