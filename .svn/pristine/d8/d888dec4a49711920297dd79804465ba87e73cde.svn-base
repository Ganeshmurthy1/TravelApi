package com.tayyarah.flight.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class FlightSegments implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Segments> segments;

	private String flightIndex;

	private String apiProvider;

	private String bookingCurrency;

	private BigDecimal exchangeRate;

	public String getApiProvider() {
		return apiProvider;
	}

	public void setApiProvider(String apiProvider) {
		this.apiProvider = apiProvider;
	}

	public List<Segments> getSegments() {
		return segments;
	}

	public void setSegments(List<Segments> segments) {
		this.segments = segments;
	}

	public String getFlightIndex() {
		return flightIndex;
	}

	public void setFlightIndex(String flightIndex) {
		this.flightIndex = flightIndex;
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
	@Override
	public String toString() {
		return "FlightSegments [segments=" + segments + ", flightIndex="
				+ flightIndex + ", apiProvider=" + apiProvider
				+ ", bookingCurrency=" + bookingCurrency + ", exchangeRate="
				+ exchangeRate + "]";
	}

}
