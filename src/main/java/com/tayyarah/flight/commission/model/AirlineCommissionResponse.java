package com.tayyarah.flight.commission.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;

public class AirlineCommissionResponse implements Serializable {
	public AirlineCommissionResponse(int code, String message) {
		super();
		this.code = code;
		this.message = message;
		this.companyCommissionMap = new TreeMap<Integer, AirlineCommision>();
		this.totalIataCommission = new BigDecimal(0);
		this.totalPlbCommission = new BigDecimal(0);
	}
	public Map<Integer, AirlineCommision> getCompanyCommissionMap() {
		return companyCommissionMap;
	}
	public void setCompanyCommissionMap(
			Map<Integer, AirlineCommision> companyCommissionMap) {
		this.companyCommissionMap = companyCommissionMap;
	}
	public BigDecimal getTotalIataCommission() {
		return totalIataCommission;
	}
	public void setTotalIataCommission(BigDecimal totalIataCommission) {
		this.totalIataCommission = totalIataCommission;
	}
	public BigDecimal getTotalPlbCommission() {
		return totalPlbCommission;
	}
	public void setTotalPlbCommission(BigDecimal totalPlbCommission) {
		this.totalPlbCommission = totalPlbCommission;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private  Map<Integer, AirlineCommision> companyCommissionMap;
	private BigDecimal totalIataCommission;
	private BigDecimal totalPlbCommission;
	private int code;
	private String message;
}