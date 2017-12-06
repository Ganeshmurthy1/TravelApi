package com.tayyarah.flight.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PassengerFareBreakUp extends Passenger implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String basePrice;
	private String taxes;
	private String basePriceWithoutMarkup;
	private String taxesWithoutMarkup;
	private String totalPriceWithoutMarkup;
	private String totalPrice;
	private String currency;
    private String api_basePriceWithoutMarkup;
    private String taxDescription;
    private String api_taxesWithoutMarkup;
    private String api_totalPriceWithoutMarkup;
    private Discount apiDiscount;//discount which api gives.. hidden for third party responses..API CURRENCY
	private Discount systemDiscount;//discount which our system gives to customer.. hidden for third party responses..BASE CURRENCY
	private Discount discount;//final discount which is given to customer.. based on the system config .. mostly apidiscount + systemDiscount BOOKING CURRENCY
	private FlightTaxBreakUp flightTaxBreakUp;


	public FlightTaxBreakUp getFlightTaxBreakUp() {
		return flightTaxBreakUp;
	}
	public void setFlightTaxBreakUp(FlightTaxBreakUp flightTaxBreakUp) {
		this.flightTaxBreakUp = flightTaxBreakUp;
	}
	public String getTaxDescription() {
		return taxDescription;
	}
	public void setTaxDescription(String taxDescription) {
		this.taxDescription = taxDescription;
	}
    public String getApi_basePriceWithoutMarkup() {
  		return api_basePriceWithoutMarkup;
  	}
  	public void setApi_basePriceWithoutMarkup(String api_basePriceWithoutMarkup) {
  		this.api_basePriceWithoutMarkup = api_basePriceWithoutMarkup;
  	}
  	public String getApi_taxesWithoutMarkup() {
  		return api_taxesWithoutMarkup;
  	}
  	public void setApi_taxesWithoutMarkup(String api_taxesWithoutMarkup) {
  		this.api_taxesWithoutMarkup = api_taxesWithoutMarkup;
  	}
  	public String getApi_totalPriceWithoutMarkup() {
  		return api_totalPriceWithoutMarkup;
  	}
  	public void setApi_totalPriceWithoutMarkup(String api_totalPriceWithoutMarkup) {
  		this.api_totalPriceWithoutMarkup = api_totalPriceWithoutMarkup;
  	}

	/**
	 * @return the basePrice
	 */
	public String getBasePrice() {
		return basePrice;
	}

	/**
	 * @param basePrice the basePrice to set
	 */
	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}

	/**
	 * @return the taxes
	 */
	public String getTaxes() {
		return taxes;
	}

	/**
	 * @param taxes the taxes to set
	 */
	public void setTaxes(String taxes) {
		this.taxes = taxes;
	}

	/**
	 * @return the basePriceWithoutMarkup
	 */
	public String getBasePriceWithoutMarkup() {
		return basePriceWithoutMarkup;
	}

	/**
	 * @param basePriceWithoutMarkup the basePriceWithoutMarkup to set
	 */
	public void setBasePriceWithoutMarkup(String basePriceWithoutMarkup) {
		this.basePriceWithoutMarkup = basePriceWithoutMarkup;
	}

	/**
	 * @return the taxesWithoutMarkup
	 */
	public String getTaxesWithoutMarkup() {
		return taxesWithoutMarkup;
	}

	/**
	 * @param taxesWithoutMarkup the taxesWithoutMarkup to set
	 */
	public void setTaxesWithoutMarkup(String taxesWithoutMarkup) {
		this.taxesWithoutMarkup = taxesWithoutMarkup;
	}

	/**
	 * @return the totalPriceWithoutMarkup
	 */
	public String getTotalPriceWithoutMarkup() {
		return totalPriceWithoutMarkup;
	}

	/**
	 * @param totalPriceWithoutMarkup the totalPriceWithoutMarkup to set
	 */
	public void setTotalPriceWithoutMarkup(String totalPriceWithoutMarkup) {
		this.totalPriceWithoutMarkup = totalPriceWithoutMarkup;
	}

	/**
	 * @return the totalPrice
	 */
	public String getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Discount getApiDiscount() {
		return apiDiscount;
	}

	public void setApiDiscount(Discount apiDiscount) {
		this.apiDiscount = apiDiscount;
	}

	public Discount getSystemDiscount() {
		return systemDiscount;
	}

	public void setSystemDiscount(Discount systemDiscount) {
		this.systemDiscount = systemDiscount;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}
}