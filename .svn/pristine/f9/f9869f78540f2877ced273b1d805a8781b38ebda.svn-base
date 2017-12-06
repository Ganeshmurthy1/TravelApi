package com.tayyarah.flight.model;

import java.io.Serializable;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelport.api_v33.AirResponse.FareRule;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FareRuleResponse implements Serializable
{
	private static final long serialVersionUID = 1L;
	private List<NewFareRule> fareruleList;
	private String providerCode;
	private String fareInfoRef;

	public List<NewFareRule> getFareruleList() {
		return fareruleList;
	}
	public void setFareruleList(List<NewFareRule> fareruleList) {
		this.fareruleList = fareruleList;
	}
	public String getProviderCode() {
		return providerCode;
	}
	public void setProviderCode(String poroviderCode) {
		this.providerCode = poroviderCode;
	}
	public String getFareInfoRef() {
		return fareInfoRef;
	}
	public void setFareInfoRef(String fareInfoRef) {
		this.fareInfoRef = fareInfoRef;
	}
	@Override
	public String toString() {
		return "FareRuleResponse [fareruleList=" + fareruleList
				+ ", providerCode=" + providerCode + ", fareInfoRef="
				+ fareInfoRef + "]";
	}
}