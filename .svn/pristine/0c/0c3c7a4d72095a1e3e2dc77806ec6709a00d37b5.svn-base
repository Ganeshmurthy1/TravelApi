package com.tayyarah.hotel.exception;

public enum HotelServiceEnum {
	
	CUSTOMER_SERVICE(1, "HotelErrorCodeCustomerEnum"),
	;
	
	private int serviceId;
	private String serviceEnumName;
	
	
	HotelServiceEnum(int serviceId, String serviceEnumName) {
		this.serviceId = serviceId;
		this.serviceEnumName = serviceEnumName;
	}
	
	public int getServiceId() {
		return serviceId;
	}
	
	public String getServiceEnumName(){
		return serviceEnumName;
	}
	
	static public HotelBaseException createServiceException(HotelRestError restError) {
		switch (restError.getServiceId()){
		case 1:
			HotelErrorCodeCustomerEnum errorCodeEnum = HotelErrorCodeCustomerEnum.get(restError.getErrorCode());
			return new HotelException(errorCodeEnum, 
					restError.getDebugMessage(), restError.getMessageArgs());

		// FIXME: add other services here

		default:
			return null;
		}
		
	}

}
