package com.tayyarah.bus.util;



public enum ServiceEnum {
	
	CUSTOMER_SERVICE(1, "ErrorCodeCustomerEnum");
	
	private int serviceId;
	private String serviceEnumName;
	
	
	ServiceEnum(int serviceId, String serviceEnumName) {
		this.serviceId = serviceId;
		this.serviceEnumName = serviceEnumName;
	}
	
	public int getServiceId() {
		return serviceId;
	}
	
	public String getServiceEnumName(){
		return serviceEnumName;
	}
	
	static public BusException createServiceException(BusRestError busRestError) {		
			ErrorCodeEnum errorCodeEnum = ErrorCodeCustomerEnum.get(busRestError.getErrorCode());
			return new BusException(errorCodeEnum,busRestError.getErrorMessage());	
	}

}
