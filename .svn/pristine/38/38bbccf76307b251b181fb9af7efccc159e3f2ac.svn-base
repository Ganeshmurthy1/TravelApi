package com.tayyarah.insurance.exception;


public enum InsuranceErrorMessages {
	DEFAULT(-1,"Error"),
	SUCCESS(0,"Success"),
	INVALID_APPKEY(1,"Invalid app_key"),
	NOTFOUND_APPKEY(2,"App_key blank or null"),	
	DB_ERROR(5,"DataBase Error"),
	INVALID_PARAMVALUE(6,"Invalid Param Value"),
	PARAMVALUE_REQUIED(7,"Param value is requied"),
	INVALID_DATE_FORMAT(8,"Invalid date format.format should be in DD-MM-YYYY"),
	CUSTOM_ERROR(9,"Custom Errors"),
	HIBERNATE_ERROR(10,"Hibernate Exception"),
	CREATEPOLICY_ERROR(11,"Create Policy Error");
	
	
	private final Integer errorCode;
	private final String errorMessage;
	
	InsuranceErrorMessages(Integer code, String message){
		this.errorCode = code;
		this.errorMessage = message;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}	
	public InsuranceErrorMessages getInsuranceErrorMessages(int code){
		InsuranceErrorMessages insuranceErrorMessages = DEFAULT ;
		for (InsuranceErrorMessages error : InsuranceErrorMessages.values())
			if(error.errorCode ==  code){
				insuranceErrorMessages = error;
		}
		return insuranceErrorMessages;
	}
}