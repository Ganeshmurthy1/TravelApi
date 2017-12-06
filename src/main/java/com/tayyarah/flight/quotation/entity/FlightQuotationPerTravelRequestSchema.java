package com.tayyarah.flight.quotation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tayyarah.common.util.Timestampable;



@Entity
@Table(name="Flight_quotation_per_travel_request_schema")
public class FlightQuotationPerTravelRequestSchema extends Timestampable{
	
	@Transient
	private String fieldName;
	@Transient
	private String dataType;
	@Transient
	private int positionNumber;
	@Transient
	private boolean fixedPosition;
	
	@Column(name="company_id")
	private int companyId;	
	@Column(name="schema_data",columnDefinition="text")
	private String schemaData;
	@Column(name="travel_request_id")
	private  long travelRequestId;
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getSchemaData() {
		return schemaData;
	}
	public void setSchemaData(String schemaData) {
		this.schemaData = schemaData;
	}
	public long getTravelRequestId() {
		return travelRequestId;
	}
	public void setTravelRequestId(long travelRequestId) {
		this.travelRequestId = travelRequestId;
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
	public boolean isFixedPosition() {
		return fixedPosition;
	}
	public void setFixedPosition(boolean fixedPosition) {
		this.fixedPosition = fixedPosition;
	}
}