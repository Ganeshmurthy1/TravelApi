package com.tayyarah.api.flight.tbo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fare implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("Currency")
	private String Currency;
	@JsonProperty("BaseFare")
	private BigDecimal BaseFare;
	@JsonProperty("Tax")
	private BigDecimal Tax;
	@JsonProperty("TaxBreakup")
	private List<TaxBreakup> taxBreakup = new ArrayList<TaxBreakup>();
	@JsonProperty("TransactionFee")
	private BigDecimal TransactionFee;
	@JsonProperty("YQTax")
	private BigDecimal YQTax;
	@JsonProperty("AdditionalTxnFee")
	private BigDecimal AdditionalTxnFee;
	@JsonProperty("AdditionalTxnFeeOfrd")
	private BigDecimal AdditionalTxnFeeOfrd;
	@JsonProperty("AdditionalTxnFeePub")
	private BigDecimal AdditionalTxnFeePub;
	@JsonProperty("OtherCharges")
	private BigDecimal OtherCharges;
	@JsonProperty("ChargeBU")
	private List<com.tayyarah.api.flight.tbo.model.ChargeBU> ChargeBU = new ArrayList<com.tayyarah.api.flight.tbo.model.ChargeBU>();
	@JsonProperty("Discount")
	private BigDecimal Discount;
	@JsonProperty("PublishedFare")
	private BigDecimal PublishedFare;
	@JsonProperty("CommissionEarned")
	private BigDecimal CommissionEarned;
	@JsonProperty("PLBEarned")
	private BigDecimal PLBEarned;
	@JsonProperty("IncentiveEarned")
	private BigDecimal IncentiveEarned;
	@JsonProperty("OfferedFare")
	private BigDecimal OfferedFare;
	@JsonProperty("TdsOnCommission")
	private BigDecimal TdsOnCommission;
	@JsonProperty("TdsOnPLB")
	private BigDecimal TdsOnPLB;
	@JsonProperty("TdsOnIncentive")
	private BigDecimal TdsOnIncentive;
	@JsonProperty("ServiceFee")
	private BigDecimal ServiceFee;
	@JsonProperty("AirTransFee")
	private BigDecimal airTransFee;
	@JsonProperty("TotalBaggageCharges")
	private BigDecimal TotalBaggageCharges;
	@JsonProperty("TotalMealCharges")
	private BigDecimal TotalMealCharges;
	@JsonProperty("TotalSeatCharges")
	private BigDecimal TotalSeatCharges;
	@JsonProperty("PGCharge")
	private BigDecimal PGCharge;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public Fare() {
	}

	/**
	 * 
	 * @param ChargeBU
	 * @param CommissionEarned
	 * @param OfferedFare
	 * @param Tax
	 * @param TdsOnCommission
	 * @param BaseFare
	 * @param PLBEarned
	 * @param AdditionalTxnFeePub
	 * @param ServiceFee
	 * @param TdsOnIncentive
	 * @param OtherCharges
	 * @param YQTax
	 * @param PublishedFare
	 * @param IncentiveEarned
	 * @param AdditionalTxnFee
	 * @param TdsOnPLB
	 * @param Discount
	 * @param Currency
	 * @param AdditionalTxnFeeOfrd
	 */
	public Fare(String Currency, BigDecimal BaseFare, BigDecimal Tax,BigDecimal TransactionFee, BigDecimal YQTax, BigDecimal AdditionalTxnFee, BigDecimal AdditionalTxnFeeOfrd, BigDecimal AdditionalTxnFeePub, BigDecimal OtherCharges, List<com.tayyarah.api.flight.tbo.model.ChargeBU> ChargeBU, BigDecimal Discount, BigDecimal PublishedFare, BigDecimal CommissionEarned, BigDecimal PLBEarned, BigDecimal IncentiveEarned, BigDecimal OfferedFare, BigDecimal TdsOnCommission, BigDecimal TdsOnPLB, BigDecimal TdsOnIncentive, BigDecimal ServiceFee) {
		this.Currency = Currency;
		this.BaseFare = BaseFare;
		this.Tax = Tax;
		this.TransactionFee = TransactionFee;
		this.YQTax = YQTax;
		this.AdditionalTxnFee = AdditionalTxnFee;
		this.AdditionalTxnFeeOfrd = AdditionalTxnFeeOfrd;
		this.AdditionalTxnFeePub = AdditionalTxnFeePub;
		this.OtherCharges = OtherCharges;
		this.ChargeBU = ChargeBU;
		this.Discount = Discount;
		this.PublishedFare = PublishedFare;
		this.CommissionEarned = CommissionEarned;
		this.PLBEarned = PLBEarned;
		this.IncentiveEarned = IncentiveEarned;
		this.OfferedFare = OfferedFare;
		this.TdsOnCommission = TdsOnCommission;
		this.TdsOnPLB = TdsOnPLB;
		this.TdsOnIncentive = TdsOnIncentive;
		this.ServiceFee = ServiceFee;
	}

	/**
	 * 
	 * @return
	 * The Currency
	 */
	@JsonProperty("Currency")
	public String getCurrency() {
		return Currency;
	}

	/**
	 * 
	 * @param Currency
	 * The Currency
	 */
	@JsonProperty("Currency")
	public void setCurrency(String Currency) {
		this.Currency = Currency;
	}

	/**
	 * 
	 * @return
	 * The BaseFare
	 */
	@JsonProperty("BaseFare")
	public BigDecimal getBaseFare() {
		return BaseFare;
	}

	/**
	 * 
	 * @param BaseFare
	 * The BaseFare
	 */
	@JsonProperty("BaseFare")
	public void setBaseFare(BigDecimal BaseFare) {
		this.BaseFare = BaseFare;
	}

	/**
	 * 
	 * @return
	 * The Tax
	 */
	@JsonProperty("Tax")
	public BigDecimal getTax() {
		return Tax;
	}

	/**
	 * 
	 * @param Tax
	 * The Tax
	 */
	@JsonProperty("Tax")
	public void setTax(BigDecimal Tax) {
		this.Tax = Tax;
	}
	/**
	 * 
	 * @return
	 * The TransactionFee
	 */
	@JsonProperty("TransactionFee")
	public BigDecimal getTransactionFee() {
		return TransactionFee;
	}

	/**
	 * 
	 * @param TransactionFee
	 * The TransactionFee
	 */
	@JsonProperty("TransactionFee")
	public void setTransactionFee(BigDecimal TransactionFee) {
		this.TransactionFee = TransactionFee;
	}
	/**
	 * 
	 * @return
	 * The YQTax
	 */
	@JsonProperty("YQTax")
	public BigDecimal getYQTax() {
		return YQTax;
	}

	/**
	 * 
	 * @param YQTax
	 * The YQTax
	 */
	@JsonProperty("YQTax")
	public void setYQTax(BigDecimal YQTax) {
		this.YQTax = YQTax;
	}

	/**
	 * 
	 * @return
	 * The AdditionalTxnFee
	 */
	@JsonProperty("AdditionalTxnFee")
	public BigDecimal getAdditionalTxnFee() {
		return AdditionalTxnFee;
	}

	/**
	 * 
	 * @param AdditionalTxnFee
	 * The AdditionalTxnFee
	 */
	@JsonProperty("AdditionalTxnFee")
	public void setAdditionalTxnFee(BigDecimal AdditionalTxnFee) {
		this.AdditionalTxnFee = AdditionalTxnFee;
	}

	/**
	 * 
	 * @return
	 * The AdditionalTxnFeeOfrd
	 */
	@JsonProperty("AdditionalTxnFeeOfrd")
	public BigDecimal getAdditionalTxnFeeOfrd() {
		return AdditionalTxnFeeOfrd;
	}

	/**
	 * 
	 * @param AdditionalTxnFeeOfrd
	 * The AdditionalTxnFeeOfrd
	 */
	@JsonProperty("AdditionalTxnFeeOfrd")
	public void setAdditionalTxnFeeOfrd(BigDecimal AdditionalTxnFeeOfrd) {
		this.AdditionalTxnFeeOfrd = AdditionalTxnFeeOfrd;
	}

	/**
	 * 
	 * @return
	 * The AdditionalTxnFeePub
	 */
	@JsonProperty("AdditionalTxnFeePub")
	public BigDecimal getAdditionalTxnFeePub() {
		return AdditionalTxnFeePub;
	}

	/**
	 * 
	 * @param AdditionalTxnFeePub
	 * The AdditionalTxnFeePub
	 */
	@JsonProperty("AdditionalTxnFeePub")
	public void setAdditionalTxnFeePub(BigDecimal AdditionalTxnFeePub) {
		this.AdditionalTxnFeePub = AdditionalTxnFeePub;
	}

	/**
	 * 
	 * @return
	 * The OtherCharges
	 */
	@JsonProperty("OtherCharges")
	public BigDecimal getOtherCharges() {
		return OtherCharges;
	}

	/**
	 * 
	 * @param OtherCharges
	 * The OtherCharges
	 */
	@JsonProperty("OtherCharges")
	public void setOtherCharges(BigDecimal OtherCharges) {
		this.OtherCharges = OtherCharges;
	}

	/**
	 * 
	 * @return
	 * The ChargeBU
	 */
	@JsonProperty("ChargeBU")
	public List<com.tayyarah.api.flight.tbo.model.ChargeBU> getChargeBU() {
		return ChargeBU;
	}

	/**
	 * 
	 * @param ChargeBU
	 * The ChargeBU
	 */
	@JsonProperty("ChargeBU")
	public void setChargeBU(List<com.tayyarah.api.flight.tbo.model.ChargeBU> ChargeBU) {
		this.ChargeBU = ChargeBU;
	}

	/**
	 * 
	 * @return
	 * The Discount
	 */
	@JsonProperty("Discount")
	public BigDecimal getDiscount() {
		return Discount;
	}

	/**
	 * 
	 * @param Discount
	 * The Discount
	 */
	@JsonProperty("Discount")
	public void setDiscount(BigDecimal Discount) {
		this.Discount = Discount;
	}

	/**
	 * 
	 * @return
	 * The PublishedFare
	 */
	@JsonProperty("PublishedFare")
	public BigDecimal getPublishedFare() {
		return PublishedFare;
	}

	/**
	 * 
	 * @param PublishedFare
	 * The PublishedFare
	 */
	@JsonProperty("PublishedFare")
	public void setPublishedFare(BigDecimal PublishedFare) {
		this.PublishedFare = PublishedFare;
	}

	/**
	 * 
	 * @return
	 * The CommissionEarned
	 */
	@JsonProperty("CommissionEarned")
	public BigDecimal getCommissionEarned() {
		return CommissionEarned;
	}

	/**
	 * 
	 * @param CommissionEarned
	 * The CommissionEarned
	 */
	@JsonProperty("CommissionEarned")
	public void setCommissionEarned(BigDecimal CommissionEarned) {
		this.CommissionEarned = CommissionEarned;
	}

	/**
	 * 
	 * @return
	 * The PLBEarned
	 */
	@JsonProperty("PLBEarned")
	public BigDecimal getPLBEarned() {
		return PLBEarned;
	}

	/**
	 * 
	 * @param PLBEarned
	 * The PLBEarned
	 */
	@JsonProperty("PLBEarned")
	public void setPLBEarned(BigDecimal PLBEarned) {
		this.PLBEarned = PLBEarned;
	}

	/**
	 * 
	 * @return
	 * The IncentiveEarned
	 */
	@JsonProperty("IncentiveEarned")
	public BigDecimal getIncentiveEarned() {
		return IncentiveEarned;
	}

	/**
	 * 
	 * @param IncentiveEarned
	 * The IncentiveEarned
	 */
	@JsonProperty("IncentiveEarned")
	public void setIncentiveEarned(BigDecimal IncentiveEarned) {
		this.IncentiveEarned = IncentiveEarned;
	}

	/**
	 * 
	 * @return
	 * The OfferedFare
	 */
	@JsonProperty("OfferedFare")
	public BigDecimal getOfferedFare() {
		return OfferedFare;
	}

	/**
	 * 
	 * @param OfferedFare
	 * The OfferedFare
	 */
	@JsonProperty("OfferedFare")
	public void setOfferedFare(BigDecimal OfferedFare) {
		this.OfferedFare = OfferedFare;
	}

	/**
	 * 
	 * @return
	 * The TdsOnCommission
	 */
	@JsonProperty("TdsOnCommission")
	public BigDecimal getTdsOnCommission() {
		return TdsOnCommission;
	}

	/**
	 * 
	 * @param TdsOnCommission
	 * The TdsOnCommission
	 */
	@JsonProperty("TdsOnCommission")
	public void setTdsOnCommission(BigDecimal TdsOnCommission) {
		this.TdsOnCommission = TdsOnCommission;
	}

	/**
	 * 
	 * @return
	 * The TdsOnPLB
	 */
	@JsonProperty("TdsOnPLB")
	public BigDecimal getTdsOnPLB() {
		return TdsOnPLB;
	}

	/**
	 * 
	 * @param TdsOnPLB
	 * The TdsOnPLB
	 */
	@JsonProperty("TdsOnPLB")
	public void setTdsOnPLB(BigDecimal TdsOnPLB) {
		this.TdsOnPLB = TdsOnPLB;
	}

	/**
	 * 
	 * @return
	 * The TdsOnIncentive
	 */
	@JsonProperty("TdsOnIncentive")
	public BigDecimal getTdsOnIncentive() {
		return TdsOnIncentive;
	}

	/**
	 * 
	 * @param TdsOnIncentive
	 * The TdsOnIncentive
	 */
	@JsonProperty("TdsOnIncentive")
	public void setTdsOnIncentive(BigDecimal TdsOnIncentive) {
		this.TdsOnIncentive = TdsOnIncentive;
	}

	/**
	 * 
	 * @return
	 * The ServiceFee
	 */
	@JsonProperty("ServiceFee")
	public BigDecimal getServiceFee() {
		return ServiceFee;
	}

	/**
	 * 
	 * @param ServiceFee
	 * The ServiceFee
	 */
	@JsonProperty("ServiceFee")
	public void setServiceFee(BigDecimal ServiceFee) {
		this.ServiceFee = ServiceFee;
	}

	/**
	 * 
	 * @return
	 * The airTransFee
	 */
	@JsonProperty("AirTransFee")
	public BigDecimal getAirTransFee() {
		return airTransFee;
	}

	/**
	 * 
	 * @param airTransFee
	 * The AirTransFee
	 */
	@JsonProperty("AirTransFee")
	public void setAirTransFee(BigDecimal airTransFee) {
		this.airTransFee = airTransFee;
	}

	public BigDecimal getTotalBaggageCharges() {
		return TotalBaggageCharges;
	}

	public void setTotalBaggageCharges(BigDecimal totalBaggageCharges) {
		TotalBaggageCharges = totalBaggageCharges;
	}

	public BigDecimal getTotalMealCharges() {
		return TotalMealCharges;
	}

	public void setTotalMealCharges(BigDecimal totalMealCharges) {
		TotalMealCharges = totalMealCharges;
	}

	public BigDecimal getTotalSeatCharges() {
		return TotalSeatCharges;
	}

	public void setTotalSeatCharges(BigDecimal totalSeatCharges) {
		TotalSeatCharges = totalSeatCharges;
	}

	public BigDecimal getPGCharge() {
		return PGCharge;
	}

	public void setPGCharge(BigDecimal pGCharge) {
		PGCharge = pGCharge;
	}

	public List<TaxBreakup> getTaxBreakup() {
		return taxBreakup;
	}

	public void setTaxBreakup(List<TaxBreakup> taxBreakup) {
		this.taxBreakup = taxBreakup;
	}
}
