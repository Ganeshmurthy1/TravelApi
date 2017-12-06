package com.tayyarah.hotel.model;

import java.io.Serializable;
import java.math.BigInteger;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIHotelCancelResponse implements Serializable{

	
	public static final Logger logger = Logger.getLogger(APIHotelCancelResponse.class);		
	private static final long serialVersionUID = 1L;
	private Status status;	
	private BigInteger searchKey;
	private BigInteger transactionKey;
	private String invoiceNo;		
	private com.tayyarah.hotel.model.UniqueIDType uniqueId;	
	private com.tayyarah.hotel.model.UniqueIDType apiUniqueId;
	
	private CancelRuleType cancelRule;
	private CancelRuleType baseCancelRule;
	private CancelRuleType apiCancelRule;
	
	private APIStatus apiStatus;
	
	
	public APIHotelCancelResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BigInteger getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(BigInteger searchKey) {
		this.searchKey = searchKey;
	}
	public BigInteger getTransactionKey() {
		return transactionKey;
	}
	public void setTransactionKey(BigInteger transactionKey) {
		this.transactionKey = transactionKey;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public com.tayyarah.hotel.model.UniqueIDType getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(com.tayyarah.hotel.model.UniqueIDType uniqueId) {
		this.uniqueId = uniqueId;
	}
	public com.tayyarah.hotel.model.UniqueIDType getApiUniqueId() {
		return apiUniqueId;
	}
	public void setApiUniqueId(com.tayyarah.hotel.model.UniqueIDType apiUniqueId) {
		this.apiUniqueId = apiUniqueId;
	}
	public CancelRuleType getCancelRule() {
		return cancelRule;
	}
	public void setCancelRule(CancelRuleType cancelRule) {
		this.cancelRule = cancelRule;
	}
	public CancelRuleType getBaseCancelRule() {
		return baseCancelRule;
	}
	public void setBaseCancelRule(CancelRuleType baseCancelRule) {
		this.baseCancelRule = baseCancelRule;
	}
	public CancelRuleType getApiCancelRule() {
		return apiCancelRule;
	}
	public void setApiCancelRule(CancelRuleType apiCancelRule) {
		this.apiCancelRule = apiCancelRule;
	}
	public APIStatus getApiStatus() {
		return apiStatus;
	}
	public void setApiStatus(APIStatus apiStatus) {
		this.apiStatus = apiStatus;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
