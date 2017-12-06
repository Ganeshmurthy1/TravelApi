package com.tayyarah.common.util;

public enum TayyarahGSTDetails {
	GSTCompanyAddress ("GSTCompanyAddress","19,The Oyster,Nandhidurga Road,Bangalore,Karnataka,560046"),
	GSTCompanyContactNumber("GSTCompanyContactNumber", "080- 42855555"),
	GSTCompanyEmail("GSTCompanyEmail", "care@tayyarah.com"),
	GSTCompanyName("GSTCompanyName", "Intellicomm Solutions And Enterprises Private Limited"),
	GSTNumber("GSTNumber", "29AABCI7895M1ZN");	
	
	private final String paramName;
	private final String paramValue;
	
	TayyarahGSTDetails(String paramName,String paramValue){
		this.paramName = paramName;
		this.paramValue = paramValue;
	}
	public static String getGstValue(String paramName){
		String value = "";
		for (TayyarahGSTDetails tayyarahGSTDetails : TayyarahGSTDetails.values()) {
			if(tayyarahGSTDetails.getParamName().equalsIgnoreCase(paramName)){
				value = tayyarahGSTDetails.getParamValue();
				break;
			}
		}
		return value;
	}
	public String getParamName() {
		return paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
}
