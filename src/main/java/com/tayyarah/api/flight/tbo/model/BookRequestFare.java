package com.tayyarah.api.flight.tbo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookRequestFare implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("BaseFare")
	private BigDecimal baseFare;
	@JsonProperty("Tax")
	private BigDecimal tax;
	@JsonProperty("AdditionalTxnFeeOfrd")
	private BigDecimal additionalTxnFeeOfrd;
	@JsonProperty("AdditionalTxnFeePub")
	private BigDecimal additionalTxnFeePub;
	@JsonProperty("YQTax")
	private BigDecimal yQTax;
	@JsonProperty("AdditionalTxnFee")
	private BigDecimal additionalTxnFee;
	@JsonProperty("AirTransFee")
	private BigDecimal airTransFee;
	@JsonProperty("OtherCharges")
	private BigDecimal OtherCharges;
	@JsonProperty("Discount")
	private BigDecimal Discount;
	@JsonProperty("PublishedFare")
	private BigDecimal PublishedFare;
	@JsonProperty("OfferedFare")
	private BigDecimal OfferedFare;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The baseFare
	 */
	@JsonProperty("BaseFare")
	public BigDecimal getBaseFare() {
		return baseFare;
	}

	/**
	 * 
	 * @param baseFare
	 * The BaseFare
	 */
	@JsonProperty("BaseFare")
	public void setBaseFare(BigDecimal baseFare) {
		this.baseFare = baseFare;
	}

	/**
	 * 
	 * @return
	 * The tax
	 */
	@JsonProperty("Tax")
	public BigDecimal getTax() {
		return tax;
	}

	/**
	 * 
	 * @param tax
	 * The Tax
	 */
	@JsonProperty("Tax")
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	/**
	 * 
	 * @return
	 * The additionalTxnFeeOfrd
	 */
	@JsonProperty("AdditionalTxnFeeOfrd")
	public BigDecimal getAdditionalTxnFeeOfrd() {
		return additionalTxnFeeOfrd;
	}

	/**
	 * 
	 * @param additionalTxnFeeOfrd
	 * The AdditionalTxnFeeOfrd
	 */
	@JsonProperty("AdditionalTxnFeeOfrd")
	public void setAdditionalTxnFeeOfrd(BigDecimal additionalTxnFeeOfrd) {
		this.additionalTxnFeeOfrd = additionalTxnFeeOfrd;
	}

	/**
	 * 
	 * @return
	 * The additionalTxnFeePub
	 */
	@JsonProperty("AdditionalTxnFeePub")
	public BigDecimal getAdditionalTxnFeePub() {
		return additionalTxnFeePub;
	}

	/**
	 * 
	 * @param additionalTxnFeePub
	 * The AdditionalTxnFeePub
	 */
	@JsonProperty("AdditionalTxnFeePub")
	public void setAdditionalTxnFeePub(BigDecimal additionalTxnFeePub) {
		this.additionalTxnFeePub = additionalTxnFeePub;
	}

	/**
	 * 
	 * @return
	 * The yQTax
	 */
	@JsonProperty("YQTax")
	public BigDecimal getYQTax() {
		return yQTax;
	}

	/**
	 * 
	 * @param yQTax
	 * The YQTax
	 */
	@JsonProperty("YQTax")
	public void setYQTax(BigDecimal yQTax) {
		this.yQTax = yQTax;
	}

	/**
	 * 
	 * @return
	 * The additionalTxnFee
	 */
	@JsonProperty("AdditionalTxnFee")
	public BigDecimal getAdditionalTxnFee() {
		return additionalTxnFee;
	}

	/**
	 * 
	 * @param additionalTxnFee
	 * The AdditionalTxnFee
	 */
	@JsonProperty("AdditionalTxnFee")
	public void setAdditionalTxnFee(BigDecimal additionalTxnFee) {
		this.additionalTxnFee = additionalTxnFee;
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
	
	
	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
}
