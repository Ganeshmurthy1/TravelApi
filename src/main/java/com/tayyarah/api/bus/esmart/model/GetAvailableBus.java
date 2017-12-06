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
public class GetAvailableBus {

	@JsonProperty("apiStatus")
	private ApiStatus apiStatus;
	@JsonProperty("apiAvailableBuses")
	private List<ApiAvailableBus> apiAvailableBuses = null;
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

	@JsonProperty("apiAvailableBuses")
	public List<ApiAvailableBus> getApiAvailableBuses() {
	return apiAvailableBuses;
	}

	@JsonProperty("apiAvailableBuses")
	public void setApiAvailableBuses(List<ApiAvailableBus> apiAvailableBuses) {
	this.apiAvailableBuses = apiAvailableBuses;
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
