package com.tayyarah.common.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tayyarah.common.util.Timestampable;


@Entity
@Table(name = "api_provider_payment_transaction_detail")
public class ApiProviderPaymentTransactionDetail extends Timestampable
{

	@Transient
	private String createdDate;
	@Transient
	private BigDecimal balance;
	@Transient
	private Long supplierCardHolderId;
	
	@Column(name = "amount", columnDefinition="decimal(20,10) default '0.0'")
	private BigDecimal amount;

	@Column(name = "is_payment_success")
	private Boolean isPaymentSuccess;

	@Column(name = "currency")
	private String	currency;

	@Column(name = "response_message")
	private	String	responseMessage;

	@Column(name = "transaction_id")
	private	String	transactionId;

	// success, failed, pending,refund
	@Column(name="payment_status")
	private String paymentStatus;

	//partial, complete
	@Column(name="payment_collection_type")
	private String paymentCollectionType;

	//card, cash, credit,cheque
	@Column(name="payment_method")
	private String paymentMethod;

	//some info
	@Column(name="payment_information")
	private String paymentInformation;

	//C
	@Column(name="paymentPaidBy")
	private String paymentPaidBy;

	@Column(name = "api_transaction_id")
	private String apiTransactionId; 	

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "api_payment_card_info_id" )
	private PaymentCardDetailsConfig apiProviderPaymentCardInfo;
 
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "api_payment_transaction_id", referencedColumnName = "id")
	private ApiProviderPaymentTransaction apiProviderPaymentTransaction;

	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "company_id")
	private int companyId;

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentCollectionType() {
		return paymentCollectionType;
	}

	public void setPaymentCollectionType(String paymentCollectionType) {
		this.paymentCollectionType = paymentCollectionType;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentInformation() {
		return paymentInformation;
	}

	public void setPaymentInformation(String paymentInformation) {
		this.paymentInformation = paymentInformation;
	}

	public String getPaymentPaidBy() {
		return paymentPaidBy;
	}

	public void setPaymentPaidBy(String paymentPaidBy) {
		this.paymentPaidBy = paymentPaidBy;
	}

	public String getApiTransactionId() {
		return apiTransactionId;
	}

	public void setApiTransactionId(String apiTransactionId) {
		this.apiTransactionId = apiTransactionId;
	}	 

	public ApiProviderPaymentTransaction getApiProviderPaymentTransaction() {
		return apiProviderPaymentTransaction;
	}

	public void setApiProviderPaymentTransaction(
			ApiProviderPaymentTransaction apiProviderPaymentTransaction) {
		this.apiProviderPaymentTransaction = apiProviderPaymentTransaction;
	}
	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public PaymentCardDetailsConfig getApiProviderPaymentCardInfo() {
		return apiProviderPaymentCardInfo;
	}

	public void setApiProviderPaymentCardInfo(PaymentCardDetailsConfig apiProviderPaymentCardInfo) {
		this.apiProviderPaymentCardInfo = apiProviderPaymentCardInfo;
	}

	public Long getSupplierCardHolderId() {
		return supplierCardHolderId;
	}

	public void setSupplierCardHolderId(Long supplierCardHolderId) {
		this.supplierCardHolderId = supplierCardHolderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
}
