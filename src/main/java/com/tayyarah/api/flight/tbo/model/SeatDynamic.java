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

public class SeatDynamic implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("SegmentSeat")
	private List<SegmentSeat> segmentSeat = new ArrayList<SegmentSeat>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	* 
	* @return
	* The segmentSeat
	*/
	@JsonProperty("SegmentSeat")
	public List<SegmentSeat> getSegmentSeat() {
	return segmentSeat;
	}

	/**
	* 
	* @param segmentSeat
	* The SegmentSeat
	*/
	@JsonProperty("SegmentSeat")
	public void setSegmentSeat(List<SegmentSeat> segmentSeat) {
	this.segmentSeat = segmentSeat;
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
