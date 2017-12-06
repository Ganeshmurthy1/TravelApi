package com.tayyarah.hotel.quotation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tayyarah.common.util.Timestampable;



public class HotelQuotationSchema extends Timestampable implements Comparable<HotelQuotationSchema>{

	
	private int companyId;
	private String schemaData;	
	private String fieldName;	
	private String dataType;	
	private int positionNumber;	
	private String displayName;	
	private String data;
	private String fixedPosition;
	
	public HotelQuotationSchema(){
		super();

	}
	public HotelQuotationSchema(String fieldName, String dataType, int positionNumber, String fixedPosition) {
		super();
		this.fieldName = fieldName;
		this.dataType = dataType;
		this.positionNumber = positionNumber;
		this.fixedPosition = fixedPosition;
	}
	public HotelQuotationSchema(String fieldName, String dataType, String data, int positionNumber, String fixedPosition, String displayName) {
		super();
		this.fieldName = fieldName;
		this.dataType = dataType;
		this.data = data;
		this.positionNumber = positionNumber;
		this.fixedPosition = fixedPosition;
		this.displayName = displayName;
	}

	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getSchemaData() {
		return schemaData;
	}
	public void setSchemaData(String schemaData) {
		this.schemaData = schemaData;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public int getPositionNumber() {
		return positionNumber;
	}
	public void setPositionNumber(int positionNumber) {
		this.positionNumber = positionNumber;
	}
	public String getFixedPosition() {
		return fixedPosition;
	}
	public void setFixedPosition(String fixedPosition) {
		this.fixedPosition = fixedPosition;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}	
	@Override
	public int compareTo(HotelQuotationSchema o) {
		if (this.getPositionNumber()<o.getPositionNumber()){
			return -1;
		}else{
			return 1;
		}

	}
}