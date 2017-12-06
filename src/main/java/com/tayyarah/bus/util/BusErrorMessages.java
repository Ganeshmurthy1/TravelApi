package com.tayyarah.bus.util;



public enum BusErrorMessages {
	DEFAULT(-1,"Error"),
	SUCCESS(0,"Success"),
	INVALID_APPKEY(1,"Invalid app_key"),
	NOTFOUND_APPKEY(2,"App_key blank or null"),
	NO_BUSAVAILABLE(3,"No Bus Available"),
	NO_STATIONAVAILABLE(4,"No Bus Stations Available"),
	DB_ERROR(5,"DataBase Error"),
	INVALID_PARAMVALUE(6,"Invalid Param Value"),
	PARAMVALUE_REQUIED(7,"Param value is requied"),
	INVALID_DATE_FORMAT(8,"Invalid date format.format should be in YYYY-MM-DD"),
	CUSTOM_ERROR(9,"Custom Errors"),
	HIBERNATE_ERROR(10,"Hibernate Exception"),
	NO_SEATAVAILABLE(11,"No Seats Available"),
	NO_BLOCKFAILED(12,"Unable to block the seat"),
	NO_BOOKINGFAILED(13,"Booking Failed"),
	CANCELLATIONFAILED(14,"Cancellation Failed");
	
	
	private final Integer errorCode;
	private final String errorMessage;
	
	BusErrorMessages(Integer code, String message){
		this.errorCode = code;
		this.errorMessage = message;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}	
	public BusErrorMessages getBusErrorMessage(int code){
		BusErrorMessages busErrorMessages = DEFAULT ;
		for (BusErrorMessages error : BusErrorMessages.values())
			if(error.errorCode ==  code){
				busErrorMessages = error;
		}
		return busErrorMessages;
	}
}
