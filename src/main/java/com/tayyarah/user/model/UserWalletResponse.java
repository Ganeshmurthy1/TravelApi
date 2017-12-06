/**
 * 
 */
package com.tayyarah.user.model;

import java.math.BigDecimal;

/**
 * @author info : Manish Samrat
 * @createdAt : 29/05/2017
 * @version : 1.0
 */
public class UserWalletResponse {
	private String currencyCode;
	private String transactionType;
	private BigDecimal walletBalance;
	private String walletType;
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public BigDecimal getWalletBalance() {
		return walletBalance;
	}
	public void setWalletBalance(BigDecimal walletBalance) {
		this.walletBalance = walletBalance;
	}
	public String getWalletType() {
		return walletType;
	}
	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}
	
}
