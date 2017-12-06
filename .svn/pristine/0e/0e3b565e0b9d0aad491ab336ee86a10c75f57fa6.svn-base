package com.tayyarah.flight.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tayyarah.insurance.model.PlanResponse;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FlightPriceResponse implements Serializable
{
	private static final long serialVersionUID = 1L;
	private List<PassengerFareBreakUp> passengerFareBreakUps;
	private boolean cache;
	private FareFlightSegment  fareFlightSegment;
	private SpecialServiceRequest specialServiceRequest;
	private FareFlightSegment  specialFareFlightSegment;
	private SpecialServiceRequest returnspecialServiceRequest;
	private List<PassengerFareBreakUp> specialPassengerFareBreakUps;
	private MarkupCommissionDetails  markupCommissionDetails;
	private Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap;
	private BigDecimal GSTonMarkupSpecial;
	private boolean priceChange;
	private BigDecimal oldPrice;
	private BigDecimal newPrice;
	private BigDecimal GSTonMarkup;
	private BigDecimal GSTonFlights;
	private BigDecimal FinalPriceWithGST;
	private Flightsearch  flightsearch;
	private String transactionKey;
	private String priceKey;
	private String country;
	private BigDecimal totalPayableAmount;
	private PlanResponse insurancePlanResponse;
	private String gstNumber;

	public PlanResponse getInsurancePlanResponse() {
		return insurancePlanResponse;
	}
	public void setInsurancePlanResponse(PlanResponse insurancePlanResponse) {
		this.insurancePlanResponse = insurancePlanResponse;
	}
	public BigDecimal getTotalPayableAmount() {
		return totalPayableAmount;
	}
	public void setTotalPayableAmount(BigDecimal totalPayableAmount) {
		this.totalPayableAmount = totalPayableAmount;
	}
	public FareFlightSegment getSpecialFareFlightSegment() {
		return specialFareFlightSegment;
	}
	public void setSpecialFareFlightSegment(FareFlightSegment specialFareFlightSegment) {
		this.specialFareFlightSegment = specialFareFlightSegment;
	}
	public List<PassengerFareBreakUp> getSpecialPassengerFareBreakUps() {
		return specialPassengerFareBreakUps;
	}
	public void setSpecialPassengerFareBreakUps(
			List<PassengerFareBreakUp> specialPassengerFareBreakUps) {
		this.specialPassengerFareBreakUps = specialPassengerFareBreakUps;
	}
	public SpecialServiceRequest getReturnspecialServiceRequest() {
		return returnspecialServiceRequest;
	}
	public void setReturnspecialServiceRequest(SpecialServiceRequest returnspecialServiceRequest) {
		this.returnspecialServiceRequest = returnspecialServiceRequest;
	}
	public SpecialServiceRequest getSpecialServiceRequest() {
		return specialServiceRequest;
	}
	public void setSpecialServiceRequest(SpecialServiceRequest specialServiceRequest) {
		this.specialServiceRequest = specialServiceRequest;
	}
	public boolean isPriceChange() {
		return priceChange;
	}
	public void setPriceChange(boolean priceChange) {
		this.priceChange = priceChange;
	}
	public BigDecimal getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(BigDecimal oldPrice) {
		this.oldPrice = oldPrice;
	}
	public BigDecimal getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(BigDecimal newPrice) {
		this.newPrice = newPrice;
	}
	public BigDecimal getGSTonMarkup() {
		return GSTonMarkup;
	}
	public void setGSTonMarkup(BigDecimal gSTonMarkup) {
		GSTonMarkup = gSTonMarkup;
	}
	public BigDecimal getFinalPriceWithGST() {
		return FinalPriceWithGST;
	}
	public void setFinalPriceWithGST(BigDecimal finalPriceWithGST) {
		FinalPriceWithGST = finalPriceWithGST;
	}
	public BigDecimal getGSTonFlights() {
		return GSTonFlights;
	}
	public void setGSTonFlights(BigDecimal gSTonFlights) {
		GSTonFlights = gSTonFlights;
	}
	public BigDecimal getGSTonMarkupSpecial() {
		return GSTonMarkupSpecial;
	}
	public void setGSTonMarkupSpecial(BigDecimal gSTonMarkupSpecial) {
		GSTonMarkupSpecial = gSTonMarkupSpecial;
	}
	public Map<String, List<FlightMarkUpConfig>> getFlightMarkUpConfiglistMap() {
		return FlightMarkUpConfiglistMap;
	}
	public void setFlightMarkUpConfiglistMap(
			Map<String, List<FlightMarkUpConfig>> flightMarkUpConfiglistMap) {
		FlightMarkUpConfiglistMap = flightMarkUpConfiglistMap;
	}
	public MarkupCommissionDetails getMarkupCommissionDetails() {
		return markupCommissionDetails;
	}
	public void setMarkupCommissionDetails(
			MarkupCommissionDetails markupCommissionDetails) {
		this.markupCommissionDetails = markupCommissionDetails;
	}
	public Flightsearch getFlightsearch() {
		return flightsearch;
	}
	public void setFlightsearch(Flightsearch flightsearch) {
		this.flightsearch = flightsearch;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public boolean getCache ()
	{
		return cache;
	}
	public void setCache (boolean cache)
	{
		this.cache = cache;
	}
	public String getTransactionKey ()
	{
		return transactionKey;
	}
	public void setTransactionKey (String transactionKey)
	{
		this.transactionKey = transactionKey;
	}
	/**
	 * @return the passengerFareBreakUps
	 */
	public List<PassengerFareBreakUp> getPassengerFareBreakUps() {
		return passengerFareBreakUps;
	}

	/**
	 * @param passengerFareBreakUps the passengerFareBreakUps to set
	 */
	public void setPassengerFareBreakUps(
			List<PassengerFareBreakUp> passengerFareBreakUps) {
		this.passengerFareBreakUps = passengerFareBreakUps;
	}

	/**
	 * @return the fareFlightSegment
	 */
	public FareFlightSegment getFareFlightSegment() {
		return fareFlightSegment;
	}

	/**
	 * @param fareFlightSegment the fareFlightSegment to set
	 */
	public void setFareFlightSegment(FareFlightSegment fareFlightSegment) {
		this.fareFlightSegment = fareFlightSegment;
	}

	/**
	 * @return the priceKey
	 */
	public String getPriceKey() {
		return priceKey;
	}

	/**
	 * @param priceKey the priceKey to set
	 */
	public void setPriceKey(String priceKey) {
		this.priceKey = priceKey;
	}
	public String getGstNumber() {
		return gstNumber;
	}
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}
}