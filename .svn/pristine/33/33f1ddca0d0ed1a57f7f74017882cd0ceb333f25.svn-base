package com.tayyarah.common.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tayyarah.common.util.Timestampable;


@Entity
@Table(name = "payment_transaction_APG")
public class PaymentTransaction extends Timestampable
{
	
	@Transient
	private BigDecimal balance;	
	
	
	@Column(name = "amount", columnDefinition="decimal(20,10) default '0.0'")//total 
	private BigDecimal amount;

	@Column(name = "is_payment_success")//false
	private Boolean isPaymentSuccess;

	@Column(name = "currency")//
	private String	currency;

	@Column(name = "response_message")
	private	String	response_message;//payment gateway

	@Column(name = "transaction_id")
	private	String	transactionId;//
	
	@Column(name = "api_transaction_id")
	private	String	api_transaction_id;//

	@Column(name = "refno")//from p
	private	String	refno;

	@Column(name = "authorization_code")//auth code
	private	String	authorizationCode;

	@Column(name = "response_code")//pay
	private	String	responseCode;

	@Column(name = "expy")//
	private	String	expy;

	@Column(name="payment_status")
	private String payment_status;

	@Column(name="payment_method")
	private String payment_method;//cash /credit

	@Column(name="payment_information")//
	private String payment_information;

	@Column(name="payment_system")//
	private String payment_system;
	 
	
	public Boolean getIsPaymentSuccess()
	{
		return isPaymentSuccess;
	}

	public void setIsPaymentSuccess(Boolean isPaymentSuccess)
	{
		this.isPaymentSuccess = isPaymentSuccess;
	}
	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getResponse_message()
	{
		return response_message;
	}

	public void setResponse_message(String response_message)
	{
		this.response_message = response_message;
	}

	public String getRefno()
	{
		return refno;
	}

	public void setRefno(String refno)
	{
		this.refno = refno;
	}


	public String getAuthorizationCode()
	{
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode)
	{
		this.authorizationCode = authorizationCode;
	}
	public String getResponseCode()
	{
		return responseCode;
	}

	public void setResponseCode(String responseCode)
	{
		this.responseCode = responseCode;
	}

	public String getExpy()
	{
		return expy;
	}

	public void setExpy(String expy)
	{
		this.expy = expy;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public String getPayment_information() {
		return payment_information;
	}

	public void setPayment_information(String payment_information) {
		this.payment_information = payment_information;
	}

	public String getPayment_system() {
		return payment_system;
	}

	public void setPayment_system(String payment_system) {
		this.payment_system = payment_system;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getApi_transaction_id() {
		return api_transaction_id;
	}

	public void setApi_transaction_id(String api_transaction_id) {
		this.api_transaction_id = api_transaction_id;
	}

}
