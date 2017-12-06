package com.tayyarah.api.flight.tbo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSRResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("Meal")
	private List<Meal> meal = new ArrayList<Meal>();
	@JsonProperty("SeatPreference")
	private List<Seat> seatPreference = new ArrayList<Seat>();
	@JsonProperty("ResponseStatus")
	private Integer responseStatus;
	@JsonProperty("Error")
	private Error error;
	@JsonProperty("TraceId")
	private String traceId;
	@JsonProperty("Baggage")
	private List<List<Baggage>> baggage = new ArrayList<List<Baggage>>();
	@JsonProperty("MealDynamic")
	private List<List<MealDynamic>> mealDynamic = new ArrayList<List<MealDynamic>>();
	@JsonProperty("SeatDynamic")
	private List<SeatDynamic> seatDynamic = new ArrayList<SeatDynamic>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	* 
	* @return
	* The meal
	*/
	@JsonProperty("Meal")
	public List<Meal> getMeal() {
	return meal;
	}

	/**
	* 
	* @param meal
	* The Meal
	*/
	@JsonProperty("Meal")
	public void setMeal(List<Meal> meal) {
	this.meal = meal;
	}

	/**
	* 
	* @return
	* The seatPreference
	*/
	@JsonProperty("SeatPreference")
	public List<Seat> getSeatPreference() {
	return seatPreference;
	}

	/**
	* 
	* @param seatPreference
	* The SeatPreference
	*/
	@JsonProperty("SeatPreference")
	public void setSeatPreference(List<Seat> seatPreference) {
	this.seatPreference = seatPreference;
	}

	/**
	* 
	* @return
	* The responseStatus
	*/
	@JsonProperty("ResponseStatus")
	public Integer getResponseStatus() {
	return responseStatus;
	}

	/**
	* 
	* @param responseStatus
	* The ResponseStatus
	*/
	@JsonProperty("ResponseStatus")
	public void setResponseStatus(Integer responseStatus) {
	this.responseStatus = responseStatus;
	}

	/**
	* 
	* @return
	* The error
	*/
	@JsonProperty("Error")
	public Error getError() {
	return error;
	}

	/**
	* 
	* @param error
	* The Error
	*/
	@JsonProperty("Error")
	public void setError(Error error) {
	this.error = error;
	}

	/**
	* 
	* @return
	* The traceId
	*/
	@JsonProperty("TraceId")
	public String getTraceId() {
	return traceId;
	}

	/**
	* 
	* @param traceId
	* The TraceId
	*/
	@JsonProperty("TraceId")
	public void setTraceId(String traceId) {
	this.traceId = traceId;
	}

	
	/**
	* 
	* @return
	* The baggage
	*/
	@JsonProperty("Baggage")
	public List<List<Baggage>> getBaggage() {
	return baggage;
	}

	/**
	* 
	* @param baggage
	* The Baggage
	*/
	@JsonProperty("Baggage")
	public void setBaggage(List<List<Baggage>> baggage) {
	this.baggage = baggage;
	}
	
	/**
	* 
	* @return
	* The mealDynamic
	*/
	@JsonProperty("MealDynamic")
	public List<List<MealDynamic>> getMealDynamic() {
	return mealDynamic;
	}

	/**
	* 
	* @param mealDynamic
	* The MealDynamic
	*/
	@JsonProperty("MealDynamic")
	public void setMealDynamic(List<List<MealDynamic>> mealDynamic) {
	this.mealDynamic = mealDynamic;
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
