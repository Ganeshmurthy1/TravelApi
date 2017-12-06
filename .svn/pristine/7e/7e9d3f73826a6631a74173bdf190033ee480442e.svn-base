package com.tayyarah.flight.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SearchFlightResponse implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String cabin;
	private String dest;
	private boolean cache;	
	private String type;
	private String transactionKey;
	private String searchKey;
	private String ori;
	private String country;
	private String deptDate;
	private String arvlDate;
	private String bookedCurrency;	
	private String baseCurrency;
	private BigDecimal baseToBookingExchangeRate;
	private List<Passenger> passenger;
	private List<FareFlightSegment>  fareFlightSegment = new ArrayList<FareFlightSegment>();
	@JsonIgnore
	private UAPISearchFlightKeyMap uapiSearchFlightKeyMap = new UAPISearchFlightKeyMap();
	@JsonIgnore
	FlightTempAirSegmentDAO flightTempAirSegmentDAO;


	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDeptDate() {
		return deptDate;
	}
	public void setDeptDate(String deptDate) {
		this.deptDate = deptDate;
	}
	public String getArvlDate() {
		return arvlDate;
	}
	public void setArvlDate(String arvlDate) {
		this.arvlDate = arvlDate;
	}
	public List<Passenger> getPassenger ()
	{
		return passenger;
	}
	public void setPassenger (List<Passenger> passenger)
	{
		this.passenger = passenger;
	}
	public String getCabin ()
	{
		return cabin;
	}
	public void setCabin (String cabin)
	{
		this.cabin = cabin;
	}
	public String getDest ()
	{
		return dest;
	}
	public void setDest (String dest)
	{
		this.dest = dest;
	}
	public boolean getCache ()
	{
		return cache;
	}
	public void setCache (boolean cache)
	{
		this.cache = cache;
	}
	public List<FareFlightSegment> getFareFlightSegment() {
		return fareFlightSegment;
	}
	public void setFareFlightSegment(List<FareFlightSegment> fareFlightSegment) {
		this.fareFlightSegment = fareFlightSegment;
	}
	public String getType ()
	{
		return type;
	}
	public void setType (String type)
	{
		this.type = type;
	}
	public String getTransactionKey ()
	{
		return transactionKey;
	}
	public void setTransactionKey (String transactionKey)
	{
		this.transactionKey = transactionKey;
	}	
	public String getOri ()
	{
		return ori;
	}
	public void setOri (String ori)
	{
		this.ori = ori;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getBookedCurrency() {
		return bookedCurrency;
	}
	public void setBookedCurrency(String bookedCurrency) {
		this.bookedCurrency = bookedCurrency;
	}
	public String getBaseCurrency() {
		return baseCurrency;
	}
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	public BigDecimal getBaseToBookingExchangeRate() {
		return baseToBookingExchangeRate;
	}
	public void setBaseToBookingExchangeRate(BigDecimal baseToBookingExchangeRate) {
		this.baseToBookingExchangeRate = baseToBookingExchangeRate;
	}
	
	/**
	 * @return the uapiSearchFlightKeyMap
	 */
	public UAPISearchFlightKeyMap getUapiSearchFlightKeyMap() {
		return uapiSearchFlightKeyMap;
	}

	/**
	 * @param uapiSearchFlightKeyMap the uapiSearchFlightKeyMap to set
	 */
	public void setUapiSearchFlightKeyMap(
			UAPISearchFlightKeyMap uapiSearchFlightKeyMap) {
		this.uapiSearchFlightKeyMap = uapiSearchFlightKeyMap;
	}
	@Override
	public String toString() {
		return "SearchFlightResponse [passenger=" + passenger + ", cabin="
				+ cabin + ", dest=" + dest +
				", bookingCurrency="
				/*+ bookingCurrency + ", exchangeRate=" + exchangeRate
					+ ", "*/
				+ "cache=" + cache + ", fareFlightSegment="
				+ fareFlightSegment + ", type=" + type
				+ ", transactionKey=" + transactionKey + ", searchKey="
				+ searchKey + ", ori=" + ori + ", country=" + country
				+ ", deptDate=" + deptDate + ", arvlDate=" + arvlDate + ", baseCurrency=" + baseCurrency + ", bookedCurrency=" + bookedCurrency + ", baseToBookingExchangeRate=" + baseToBookingExchangeRate + "]";
	}

}