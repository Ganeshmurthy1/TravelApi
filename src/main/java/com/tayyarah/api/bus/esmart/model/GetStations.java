package com.tayyarah.api.bus.esmart.model;


/*Created By Vimal Susai Raj S 
 * Date : 19-5-2017*/

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetStations {
	@JsonProperty("apiStatus")
	private ApiStatus apiStatus;
	@JsonProperty("stationList")
	private List<StationList> stationList = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("apiStatus")
	public ApiStatus getApiStatus() {
		return apiStatus;
	}

	@JsonProperty("apiStatus")
	public void setApiStatus(ApiStatus apiStatus) {
		this.apiStatus = apiStatus;
	}

	@JsonProperty("stationList")
	public List<StationList> getStationList() {
		return stationList;
	}

	@JsonProperty("stationList")
	public void setStationList(List<StationList> stationList) {
		this.stationList = stationList;
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
