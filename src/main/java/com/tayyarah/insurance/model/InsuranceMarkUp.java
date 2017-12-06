package com.tayyarah.insurance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InsuranceMarkUp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int markupId;
	private String name;
	private int companyId;
	private int configId;
	private boolean isAccumulative;
	private boolean isFixedAmount;
	private BigDecimal markupAmt;
	private int positionOfMarkup;
	private String travelType;
	private String country;
	private boolean isMarkUpPerPassenger;
	private boolean isMarkUpOnTotal;
	private List<String> markupparams = new ArrayList<String>();
	
	public int getMarkupId() {
		return markupId;
	}
	public String getName() {
		return name;
	}
	public int getCompanyId() {
		return companyId;
	}
	public int getConfigId() {
		return configId;
	}
	public boolean isAccumulative() {
		return isAccumulative;
	}
	public boolean isFixedAmount() {
		return isFixedAmount;
	}
	public BigDecimal getMarkupAmt() {
		return markupAmt;
	}
	public int getPositionOfMarkup() {
		return positionOfMarkup;
	}
	public String getTravelType() {
		return travelType;
	}
	public String getCountry() {
		return country;
	}
	public boolean isMarkUpPerPassenger() {
		return isMarkUpPerPassenger;
	}
	public boolean isMarkUpOnTotal() {
		return isMarkUpOnTotal;
	}
	public List<String> getMarkupparams() {
		return markupparams;
	}
	public void setMarkupId(int markupId) {
		this.markupId = markupId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public void setConfigId(int configId) {
		this.configId = configId;
	}
	public void setAccumulative(boolean isAccumulative) {
		this.isAccumulative = isAccumulative;
	}
	public void setFixedAmount(boolean isFixedAmount) {
		this.isFixedAmount = isFixedAmount;
	}
	public void setMarkupAmt(BigDecimal markupAmt) {
		this.markupAmt = markupAmt;
	}
	public void setPositionOfMarkup(int positionOfMarkup) {
		this.positionOfMarkup = positionOfMarkup;
	}
	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setMarkUpPerPassenger(boolean isMarkUpPerPassenger) {
		this.isMarkUpPerPassenger = isMarkUpPerPassenger;
	}
	public void setMarkUpOnTotal(boolean isMarkUpOnTotal) {
		this.isMarkUpOnTotal = isMarkUpOnTotal;
	}
	public void setMarkupparams(List<String> markupparams) {
		this.markupparams = markupparams;
	}
}
