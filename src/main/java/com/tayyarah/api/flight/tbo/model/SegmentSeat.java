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

public class SegmentSeat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("RowSeats")
	private List<RowSeat> rowSeats = new ArrayList<RowSeat>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	* 
	* @return
	* The rowSeats
	*/
	@JsonProperty("RowSeats")
	public List<RowSeat> getRowSeats() {
	return rowSeats;
	}

	/**
	* 
	* @param rowSeats
	* The RowSeats
	*/
	@JsonProperty("RowSeats")
	public void setRowSeats(List<RowSeat> rowSeats) {
	this.rowSeats = rowSeats;
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
