package com.tayyarah.api.flight.tbo.model;


import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FareBreakdown implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@JsonProperty("Currency")
private String Currency;
@JsonProperty("PassengerType")
private Integer PassengerType;
@JsonProperty("PassengerCount")
private Integer PassengerCount;
@JsonProperty("BaseFare")
private BigDecimal BaseFare;
@JsonProperty("Tax")
private BigDecimal Tax;
@JsonProperty("YQTax")
private BigDecimal YQTax;
@JsonProperty("AdditionalTxnFee")
private BigDecimal AdditionalTxnFee;
@JsonProperty("AdditionalTxnFeeOfrd")
private BigDecimal AdditionalTxnFeeOfrd;
@JsonProperty("AdditionalTxnFeePub")
private BigDecimal AdditionalTxnFeePub;
@JsonProperty("PGCharge")
private BigDecimal PGCharge;

/**
* No args constructor for use in serialization
* 
*/
public FareBreakdown() {
}

/**
* 
* @param PassengerType
* @param YQTax
* @param AdditionalTxnFee
* @param Tax
* @param PassengerCount
* @param BaseFare
* @param Currency
* @param AdditionalTxnFeePub
* @param AdditionalTxnFeeOfrd
*/
public FareBreakdown(String Currency, Integer PassengerType, Integer PassengerCount, BigDecimal BaseFare, BigDecimal Tax, BigDecimal YQTax, BigDecimal AdditionalTxnFee, BigDecimal AdditionalTxnFeeOfrd, BigDecimal AdditionalTxnFeePub) {
this.Currency = Currency;
this.PassengerType = PassengerType;
this.PassengerCount = PassengerCount;
this.BaseFare = BaseFare;
this.Tax = Tax;
this.YQTax = YQTax;
this.AdditionalTxnFee = AdditionalTxnFee;
this.AdditionalTxnFeeOfrd = AdditionalTxnFeeOfrd;
this.AdditionalTxnFeePub = AdditionalTxnFeePub;
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
* The PassengerType
*/
@JsonProperty("PassengerType")
public Integer getPassengerType() {
return PassengerType;
}

/**
* 
* @param PassengerType
* The PassengerType
*/
@JsonProperty("PassengerType")
public void setPassengerType(Integer PassengerType) {
this.PassengerType = PassengerType;
}

/**
* 
* @return
* The PassengerCount
*/
@JsonProperty("PassengerCount")
public Integer getPassengerCount() {
return PassengerCount;
}

/**
* 
* @param PassengerCount
* The PassengerCount
*/
@JsonProperty("PassengerCount")
public void setPassengerCount(Integer PassengerCount) {
this.PassengerCount = PassengerCount;
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

public BigDecimal getPGCharge() {
	return PGCharge;
}

public void setPGCharge(BigDecimal pGCharge) {
	PGCharge = pGCharge;
}
}