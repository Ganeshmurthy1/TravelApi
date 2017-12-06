package com.tayyarah.common.util.enums;

public enum TravelTypeEnum {	
	FLIGHT (0),   
	HOTEL (1);	
	
	private final int value;  
	public int getValue() {
		return value;
	}
	TravelTypeEnum(int value) {
		this.value = value;  
	}
	
}
