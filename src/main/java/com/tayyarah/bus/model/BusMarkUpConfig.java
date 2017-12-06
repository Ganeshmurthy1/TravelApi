package com.tayyarah.bus.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BusMarkUpConfig implements Serializable{
	
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
	private String origin;
	private String destination;
	private String onwardDate;
	private String returnDate;
	private String promofareStartDate;
	private String promofareEndDate;
	private String busOperators;
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
	
	public String getOrigin() {
		return origin;
	}
	public String getDestination() {
		return destination;
	}
	public String getOnwardDate() {
		return onwardDate;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public String getPromofareStartDate() {
		return promofareStartDate;
	}
	public String getPromofareEndDate() {
		return promofareEndDate;
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
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public void setOnwardDate(String onwardDate) {
		this.onwardDate = onwardDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public void setPromofareStartDate(String promofareStartDate) {
		this.promofareStartDate = promofareStartDate;
	}
	public void setPromofareEndDate(String promofareEndDate) {
		this.promofareEndDate = promofareEndDate;
	}
	public void setMarkupparams(List<String> markupparams) {
		this.markupparams = markupparams;
	}
	public int getPositionOfMarkup() {
		return positionOfMarkup;
	}
	public void setPositionOfMarkup(int positionOfMarkup) {
		this.positionOfMarkup = positionOfMarkup;
	}
	public String getBusOperators() {
		return busOperators;
	}
	public boolean isMarkUpPerPassenger() {
		return isMarkUpPerPassenger;
	}
	public boolean isMarkUpOnTotal() {
		return isMarkUpOnTotal;
	}
	public void setBusOperators(String busOperators) {
		this.busOperators = busOperators;
	}
	public void setMarkUpPerPassenger(boolean isMarkUpPerPassenger) {
		this.isMarkUpPerPassenger = isMarkUpPerPassenger;
	}
	public void setMarkUpOnTotal(boolean isMarkUpOnTotal) {
		this.isMarkUpOnTotal = isMarkUpOnTotal;
	}
	
	

}
