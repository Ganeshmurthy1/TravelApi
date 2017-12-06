package com.tayyarah.flight.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"CurrencyCode",
	"Value",
	"Type",
	"Amount",
	"CoupenCode",
	"Description"   
})
public class Discount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Discount() {
		super();
		this.type = TYPE_AMOUNT;
		// TODO Auto-generated constructor stub
	}
	@JsonProperty("CurrencyCode")
	private String currencyCode;   
	@JsonProperty("Value")
	private BigDecimal value;
	@JsonProperty("Type")
	private Integer type;
	@JsonProperty("Amount")
	private BigDecimal amount;
	@JsonProperty("CoupenCode")
	private String coupenCode;
	@JsonProperty("Description")
	private String description;
	
	public static final int TYPE_AMOUNT = 0;
	public static final int TYPE_PERCENTAGE = 1;

	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getCoupenCode() {
		return coupenCode;
	}
	public void setCoupenCode(String coupenCode) {
		this.coupenCode = coupenCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
