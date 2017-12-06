package com.tayyarah.flight.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tayyarah.common.gstconfig.model.FlightGstTax;
import com.tayyarah.common.servicetaxconfig.model.FlightServiceTax;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FareFlightSegment implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String id;
	private String apiCurrency;
	private String baseCurrency;
	private BigDecimal apiToBaseExchangeRate;
	private String bookingCurrency;
	private BigDecimal exchangeRate;
	private List<FlightSegmentsGroup>  flightSegmentsGroups;
	private List<FlightSegmentsGroup>  newFlightSegmentsGroups;
	private String basePrice;
	private String taxes;
	private String totalPrice;
	private String currency;
	private String latestTicketingTime;
	private String basePriceWithoutMarkup;
	private String taxesWithoutMarkup;
	private String totalPriceWithoutMarkup;
	private String api_basePriceWithoutMarkup;
	private String api_taxesWithoutMarkup;
	private String api_totalPriceWithoutMarkup;
	private List<FareRules>  fareRules;
	private String fareBasisCode;
	private Discount apiDiscount;//discount which api gives.. hidden for third party responses..API CURRENCY
	private Discount systemDiscount;//discount which our system gives to customer.. hidden for third party responses..BASE CURRENCY
	private Discount discount;//final discount which is given to customer.. based on the system config .. mostly apidiscount + systemDiscount BOOKING CURRENCY
	private String extraMealPrice;
	private String extraBaggagePrice;
	private boolean isRefundable;
	private boolean isLCC;
	private FlightServiceTax flightServiceTax;
	private BigDecimal payableAmount;
	private BigDecimal supplierCommissionearned;
	private BigDecimal supplierTds;
	private FlightGstTax flightGstTax;


	public FlightGstTax getFlightGstTax() {
		return flightGstTax;
	}
	public void setFlightGstTax(FlightGstTax flightGstTax) {
		this.flightGstTax = flightGstTax;
	}
	public BigDecimal getSupplierCommissionearned() {
		return supplierCommissionearned;
	}
	public BigDecimal getSupplierTds() {
		return supplierTds;
	}
	public void setSupplierCommissionearned(BigDecimal supplierCommissionearned) {
		this.supplierCommissionearned = supplierCommissionearned;
	}
	public void setSupplierTds(BigDecimal supplierTds) {
		this.supplierTds = supplierTds;
	}
	public BigDecimal getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(BigDecimal payableAmount) {
		this.payableAmount = payableAmount;
	}
	public FlightServiceTax getFlightServiceTax() {
		return flightServiceTax;
	}
	public void setFlightServiceTax(FlightServiceTax flightServiceTax) {
		this.flightServiceTax = flightServiceTax;
	}
	public String getApiCurrency() {
		return apiCurrency;
	}
	public void setApiCurrency(String apiCurrency) {
		this.apiCurrency = apiCurrency;
	}
	public String getBaseCurrency() {
		return baseCurrency;
	}
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	public BigDecimal getApiToBaseExchangeRate() {
		return apiToBaseExchangeRate;
	}
	public void setApiToBaseExchangeRate(BigDecimal apiToBaseExchangeRate) {
		this.apiToBaseExchangeRate = apiToBaseExchangeRate;
	}
	public String getBookingCurrency() {
		return bookingCurrency;
	}
	public void setBookingCurrency(String bookingCurrency) {
		this.bookingCurrency = bookingCurrency;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public List<FlightSegmentsGroup> getNewFlightSegmentsGroups() {
		return newFlightSegmentsGroups;
	}
	public void setNewFlightSegmentsGroups(
			List<FlightSegmentsGroup> newFlightSegmentsGroups) {
		this.newFlightSegmentsGroups = newFlightSegmentsGroups;
	}
	public String getLatestTicketingTime() {
		return latestTicketingTime;
	}
	public void setLatestTicketingTime(String latestTicketingTime) {
		this.latestTicketingTime = latestTicketingTime;
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
	public String getId ()
	{
		return id;
	}
	public void setId (String id)
	{
		this.id = id;
	}
	public List<FareRules> getFareRules() {
		return fareRules;
	}
	public void setFareRules(List<FareRules> fareRules) {
		this.fareRules = fareRules;
	}
	public String getTotalPrice ()
	{
		return totalPrice;
	}
	public void setTotalPrice (String totalPrice)
	{
		this.totalPrice = totalPrice;
	}
	public String getCurrency ()
	{
		return currency;
	}
	public void setCurrency (String currency)
	{
		this.currency = currency;
	}
	public String getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}
	public String getTaxes() {
		return taxes;
	}
	public void setTaxes(String taxes) {
		this.taxes = taxes;
	}
	public String getBasePriceWithoutMarkup() {
		return basePriceWithoutMarkup;
	}
	public void setBasePriceWithoutMarkup(String basePriceWithoutMarkup) {
		this.basePriceWithoutMarkup = basePriceWithoutMarkup;
	}
	public String getTaxesWithoutMarkup() {
		return taxesWithoutMarkup;
	}
	public void setTaxesWithoutMarkup(String taxesWithoutMarkup) {
		this.taxesWithoutMarkup = taxesWithoutMarkup;
	}
	public String getTotalPriceWithoutMarkup() {
		return totalPriceWithoutMarkup;
	}
	public void setTotalPriceWithoutMarkup(String totalPriceWithoutMarkup) {
		this.totalPriceWithoutMarkup = totalPriceWithoutMarkup;
	}
	public List<FlightSegmentsGroup> getFlightSegmentsGroups() {
		return flightSegmentsGroups;
	}
	public void setFlightSegmentsGroups(
			List<FlightSegmentsGroup> flightSegmentsGroups) {
		this.flightSegmentsGroups = flightSegmentsGroups;
	}
	public String getFareBasisCode() {
		return fareBasisCode;
	}

	public void setFareBasisCode(String fareBasisCode) {
		this.fareBasisCode = fareBasisCode;
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

	public String getExtraMealPrice() {
		return extraMealPrice;
	}
	public void setExtraMealPrice(String extraMealPrice) {
		this.extraMealPrice = extraMealPrice;
	}
	public String getExtraBaggagePrice() {
		return extraBaggagePrice;
	}
	public void setExtraBaggagePrice(String extraBaggagePrice) {
		this.extraBaggagePrice = extraBaggagePrice;
	}
	public boolean isRefundable() {
		return isRefundable;
	}
	public void setRefundable(boolean isRefundable) {
		this.isRefundable = isRefundable;
	}
	public boolean isLCC() {
		return isLCC;
	}
	public void setLCC(boolean isLCC) {
		this.isLCC = isLCC;
	}
	@Override
	public String toString() {
		return "FareFlightSegment [id=" + id + ", apiCurrency=" + apiCurrency
				+ ", baseCurrency=" + baseCurrency + ", apiToBaseExchangeRate="
				+ apiToBaseExchangeRate + ", bookingCurrency="
				+ bookingCurrency + ", exchangeRate=" + exchangeRate
				+ ", flightSegmentsGroups=" + flightSegmentsGroups
				+ ", newFlightSegmentsGroups=" + newFlightSegmentsGroups
				+ ", basePrice=" + basePrice + ", taxes=" + taxes
				+ ", totalPrice=" + totalPrice + ", currency=" + currency
				+ ", latestTicketingTime=" + latestTicketingTime
				+ ", basePriceWithoutMarkup=" + basePriceWithoutMarkup
				+ ", taxesWithoutMarkup=" + taxesWithoutMarkup
				+ ", totalPriceWithoutMarkup=" + totalPriceWithoutMarkup
				+ ", fareRules=" + fareRules + ", fareBasisCode=" + fareBasisCode + "]";
	}
}

