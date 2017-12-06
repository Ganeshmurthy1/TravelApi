/**
@Author yogeshsharma
01-Aug-2015 2015
FlightMarkUpConfig.java
 */
/**
 * 
 */
package com.tayyarah.flight.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;




public class FlightMarkUpConfig implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String MARKUP_AIRLINENAME = "1";
	public static final String MARKUP_DEPARTURE_DATE = "2";
	public static final String MARKUP_ARRIVAL_DATE = "3";
	public static final String MARKUP_ORIGIN = "4";
	public static final String MARKUP_DESITINATION = "5";
	public static final String MARKUP_COUNTRY = "6";
	public static final String MARKUP_CABIN = "7";
	private String markupId;
	private String name;
	private String companyId;
	private String configId;
	private boolean accumulative;
	private boolean fixedAmount;
	private String markup;
	private String positionOfMarkup;
	private String airline;
	private String classOfService;
	private String country;	
	private String origin;
	private String destination;
	private String arrvDate;
	private String deptDate;
	private String destinationType;
	private String promofareStartDate;
	private List<String> markupparams = new ArrayList<String>();
	
	public List<String> getMarkupparams() {
		return markupparams;
	}
	public void setMarkupparams(List<String> markupparams) {
		this.markupparams = markupparams;
	}
	public String getPromofareStartDate() {
		return promofareStartDate;
	}
	public void setPromofareStartDate(String promofareStartDate) {
		this.promofareStartDate = promofareStartDate;
	}
	private String promofareEndDate;


	public String getPromofareEndDate() {
		return promofareEndDate;
	}
	public void setPromofareEndDate(String promofareEndDate) {
		this.promofareEndDate = promofareEndDate;
	}
	public String getDestinationType() {
		return destinationType;
	}
	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}
	private String fareBasisCode;	
	public String getFareBasisCode() {
		return fareBasisCode;
	}
	public void setFareBasisCode(String fareBasisCode) {
		this.fareBasisCode = fareBasisCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getArrvDate() {
		return arrvDate;
	}
	public void setArrvDate(String arrvDate) {
		this.arrvDate = arrvDate;
	}
	public String getDeptDate() {
		return deptDate;
	}
	public void setDeptDate(String deptDate) {
		this.deptDate = deptDate;
	}
	public String getMarkupId() {
		return markupId;
	}	
	public void setMarkupId(String markupId) {
		this.markupId = markupId;
	}	
	public String getName() {
		return name;
	}	
	public void setName(String name) {
		this.name = name;
	}	
	public String getCompanyId() {
		return companyId;
	}	
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}	
	public String getConfigId() {
		return configId;
	}	
	public void setConfigId(String configId) {
		this.configId = configId;
	}	
	public boolean isAccumulative() {
		return accumulative;
	}	
	public void setAccumulative(boolean accumulative) {
		this.accumulative = accumulative;
	}	
	public boolean isFixedAmount() {
		return fixedAmount;
	}	
	public void setFixedAmount(boolean fixedAmount) {
		this.fixedAmount = fixedAmount;
	}	
	public String getMarkup() {
		return markup;
	}	
	public void setMarkup(String markup) {
		this.markup = markup;
	}	
	public String getPositionOfMarkup() {
		return positionOfMarkup;
	}	
	public void setPositionOfMarkup(String positionOfMarkup) {
		this.positionOfMarkup = positionOfMarkup;
	}	
	public String getAirline() {
		return airline;
	}	
	public void setAirline(String airline) {
		this.airline = airline;
	}	
	public String getClassOfService() {
		return classOfService;
	}	
	public void setClassOfService(String classOfService) {
		this.classOfService = classOfService;
	}
}
