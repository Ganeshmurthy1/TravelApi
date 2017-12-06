package com.tayyarah.flight.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.*;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement(name = "flightsearch")
@XmlAccessorType(XmlAccessType.NONE)
@Entity
@Table(name="flightsearch")
public class Flightsearch implements Serializable

{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	@GeneratedValue
	private int id;
	private String trips;
	private String airline;
	private Set<String> airlineList;
	private boolean isDomestic;
	@XmlElement
	private String bookedCurrency;
	@XmlElement
	private String baseCurrency;
	@XmlElement
	private BigDecimal baseToBookingExchangeRate;
	@XmlElement
	private String origin;
	@XmlElement
	private String destination;
	@XmlElement
	private String depDate;	
	@XmlElement
	private String tripType;
	@XmlElement
	private String arvlDate;
	@XmlElement
	private int adult;
	@XmlElement
	private int kid;
	@XmlElement
	private int infant;
	@XmlElement
	private String cabinClass;
	@XmlElement
	private String currency;
	@XmlElement
	private String country;
	@XmlElement
	private String app_key;
	@XmlElement
	private String searchKey;
	@XmlElement
	private String transactionKey;
	@XmlElement
	private boolean cal_search = false;
	@XmlElement
	private boolean isSpecialSearch;
	@XmlElement
	private boolean isDynamicMarkup;	
	@XmlElement
	private boolean isCacheData;	
	private boolean isCacheDataInDb;
	private MulticityFlightSearch MFS;
	private String flightType;
	boolean IsInternational;
	public Flightsearch(){
		
	}
	public Flightsearch(String trips, String origin, String destination,
			String depDate,  int adult,
			int kid, int infant, String cabinClass, String currency,String tripType, String arvlDate,
			String app_key,MulticityFlightSearch mFS , boolean cal_search,String airline,boolean isInternational,String baseCurrency, String bookedCurrency, BigDecimal baseToBookingExchangeRate,boolean isSpecialSearch,boolean isDomestic,boolean isDynamicMarkup,boolean isCacheData,boolean isCacheDataInDb) {
		super();
		this.trips = trips;
		this.origin = origin;
		this.destination = destination;
		this.depDate = depDate;
		this.tripType = tripType;
		this.arvlDate = arvlDate;
		this.adult = adult;
		this.kid = kid;
		this.infant = infant;
		this.cabinClass = cabinClass;
		this.currency = currency;
		this.app_key = app_key;
		this.MFS=mFS;
		this.cal_search = cal_search;
		this.airline=airline;
		IsInternational = isInternational;
		this.baseCurrency=baseCurrency;
		this.bookedCurrency = bookedCurrency;
		this.baseToBookingExchangeRate=baseToBookingExchangeRate;
		this.isSpecialSearch=isSpecialSearch;
		this.isDomestic = isDomestic;
		this.isDynamicMarkup = isDynamicMarkup;
		this.isCacheData = isCacheData;
		this.isCacheDataInDb = isCacheDataInDb;		
	}

	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
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
	public boolean isDynamicMarkup() {
		return isDynamicMarkup;
	}
	public void setDynamicMarkup(boolean isDynamicMarkup) {
		this.isDynamicMarkup = isDynamicMarkup;
	}
	public boolean isSpecialSearch() {
		return isSpecialSearch;
	}
	public void setSpecialSearch(boolean isSpecialSearch) {
		this.isSpecialSearch = isSpecialSearch;
	}
	public String getApp_key() {
		return app_key;
	}
	public void setApp_keyxx(String app_key) {
		this.app_key = app_key;
	}
	public MulticityFlightSearch getMFS() {
		return MFS;
	}
	public void setMFS(MulticityFlightSearch mFS) {
		MFS = mFS;
	}
	public boolean isIsInternational() {
		return IsInternational;
	}
	public void setIsInternational(boolean isInternational) {
		IsInternational = isInternational;
	}
	public String getFlightType() {
		return flightType;
	}
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}
	
	

	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDepDate() {
		return depDate;
	}
	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}
	public String getCabinClass() {
		return cabinClass;
	}
	public void setCabinClass(String cabinClass) {
		this.cabinClass = cabinClass;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getTripType() {
		return tripType;
	}
	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
	public String getArvlDate() {
		return arvlDate;
	}
	public void setArvlDate(String arvlDate) {
		this.arvlDate = arvlDate;
	}
	public String getTrips() {
		return trips;
	}
	public void setTrips(String trips) {
		this.trips = trips;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the adult
	 */
	public int getAdult() {
		return adult;
	}

	/**
	 * @param adult the adult to set
	 */
	public void setAdult(int adult) {
		this.adult = adult;
	}

	/**
	 * @return the kid
	 */
	public int getKid() {
		return kid;
	}

	/**
	 * @param kid the kid to set
	 */
	public void setKid(int kid) {
		this.kid = kid;
	}

	/**
	 * @return the infant
	 */
	public int getInfant() {
		return infant;
	}

	/**
	 * @param infant the infant to set
	 */
	public void setInfant(int infant) {
		this.infant = infant;
	}

	/**
	 * @return the cal_search
	 */
	public boolean isCal_search() {
		return cal_search;
	}

	/**
	 * @param cal_search the cal_search to set
	 */
	public void setCal_search(boolean cal_search) {
		this.cal_search = cal_search;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the searchKey
	 */
	public String getSearchKey() {
		return searchKey;
	}

	/**
	 * @param searchKey the searchKey to set
	 */
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	/**
	 * @return the transactionKey
	 */
	public String getTransactionKey() {
		return transactionKey;
	}

	/**
	 * @param transactionKey the transactionKey to set
	 */
	public void setTransactionKey(String transactionKey) {
		this.transactionKey = transactionKey;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	public Set<String> getAirlineList() {
		return airlineList;
	}

	public void setAirlineList(Set<String> airlineList) {
		this.airlineList = airlineList;
	}

	public boolean isDomestic() {
		return isDomestic;
	}

	public void setDomestic(boolean isDomestic) {
		this.isDomestic = isDomestic;
	}

	public boolean isCacheData() {
		return isCacheData;
	}

	public void setCacheData(boolean isCacheData) {
		this.isCacheData = isCacheData;
	}

	public boolean isCacheDataInDb() {
		return isCacheDataInDb;
	}

	public void setCacheDataInDb(boolean isCacheDataInDb) {
		this.isCacheDataInDb = isCacheDataInDb;
	}
	@Override
	public String toString() {
		return "Flightsearch [id=" + id + ", trips=" + trips + ", airline="
				+ airline + ", origin=" + origin + ", destination="
				+ destination + ", depDate=" + depDate + ", tripType="
				+ tripType + ", arvlDate=" + arvlDate + ", adult=" + adult
				+ ", kid=" + kid + ", infant=" + infant + ", cabinClass="
				+ cabinClass + ", currency=" + currency + ", app_key="
				+ app_key + ", cal_search=" + cal_search + ", MFS=" + MFS + ", IsInternational=" + IsInternational + ", baseCurrency=" + baseCurrency + ", bookedCurrency=" + bookedCurrency + ", baseToBookingExchangeRate=" + baseToBookingExchangeRate + ",, isSpecialSearch=" + isSpecialSearch + "]";
	}
}