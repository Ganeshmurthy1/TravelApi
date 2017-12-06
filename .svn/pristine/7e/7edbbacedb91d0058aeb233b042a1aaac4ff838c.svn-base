package com.tayyarah.hotel.exception;

/*public class HotelErrorCodeCustomerEnum implements HotelErrorCodeEnum {
	

	
	
	
	
	
	
	
	
	

}*/


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum HotelErrorCodeCustomerEnum implements HotelErrorCodeEnum {
	
	
	ClassNotFoundException(1, "UNKNOWN_ERROR", "customer.error.unknown"),
	SOAPException(2, "INVALID_PARAMS", "customer.error.params.invalid"),
	JAXBException(3, "NOT_FOUND", "customer.error.entity.notfound"),
	HibernateException(4, "INVALID_PARAMS", "customer.error.params.invalid"),
	NumberFormatException(5, "NOT_FOUND", "customer.error.entity.notfound"),
	Exception(6, "DUP_CUSTNAME", "customer.error.entity.dup.cust"),
	//FIXME: add more here
	
	;
	
	// lookup table to be used to find enum for conversion
	private static final Map<Integer,HotelErrorCodeCustomerEnum> lookup = new HashMap<Integer,HotelErrorCodeCustomerEnum>();
	static {
		for(HotelErrorCodeCustomerEnum e : EnumSet.allOf(HotelErrorCodeCustomerEnum.class))
			lookup.put(e.getErrorCode(), e);
	}
	
	private static HotelServiceEnum serviceEnum = HotelServiceEnum.CUSTOMER_SERVICE;
	private int errorCode;
	private String name;
	private String i18nKey;
	
	HotelErrorCodeCustomerEnum(int errorCode, String name, String i18nKey) {
		this.errorCode = errorCode;
		this.name = name;
		this.i18nKey = i18nKey;
	}
	
	public int getErrorCode() {
		return this.errorCode;
	}
	
	public String getName() {
		return this.name;
	}

	public int getServiceId() {
		return serviceEnum.getServiceId();
	}

	public String getMessageKey() {
		return i18nKey;
	}

	public String getDefaultMessage() {
		switch (this){
		case ClassNotFoundException:
			return "An unknown error has been encountered";
		case SOAPException:
			return "Invalid parameters were received";
		case JAXBException:
			return "Requested entity was not found";
		case Exception:
			return "Duplicate customer name used";
		
		//FIXME: add more here and can use resource bundle with i18nKey if desired 
		
		default: 
			return "An undefined error has been encountered";
		}
	}
	
	public static HotelErrorCodeCustomerEnum get(int errorCode) { 
		return lookup.get(errorCode); 
	}


}

