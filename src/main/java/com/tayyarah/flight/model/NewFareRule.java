package com.tayyarah.flight.model;

import java.io.Serializable;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class NewFareRule implements Serializable
{
   
	private static final long serialVersionUID = 1L;
	private BigInteger category;

    private String type;
    
    private String value;
    
    private String header;
    

	@Override
	public String toString() {
		return "NewFareRule [category=" + category + ", type=" + type
				+ ", value=" + value + ", header=" + header + "]";
	}

	public BigInteger getCategory() {
		return category;
	}

	public void setCategory(BigInteger bigInteger) {
		this.category = bigInteger;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

}