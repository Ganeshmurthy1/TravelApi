package com.tayyarah.common.util;

import org.apache.log4j.Logger;

import com.tayyarah.common.exception.CommonException;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;

public class CommonValidator {
	static final Logger logger=Logger.getLogger(CommonValidator.class);
	public void validateString(String param, String paramName) {
		if (param == null || param.isEmpty()) {
			throw new CommonException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_PARAMVALUE);
		}
	}
	public void currencyconverterValidator(String from,String to){
		validateString(from, "from");
		validateString(to, "to");
	}
}