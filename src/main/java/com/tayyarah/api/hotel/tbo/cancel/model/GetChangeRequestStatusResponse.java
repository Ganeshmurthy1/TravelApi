package com.tayyarah.api.hotel.tbo.cancel.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"HotelChangeRequestStatusResult"
})
public class GetChangeRequestStatusResponse {

	@JsonProperty("HotelChangeRequestStatusResult")
	private com.tayyarah.api.hotel.tbo.cancel.model.HotelChangeRequestStatusResult HotelChangeRequestStatusResult;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The HotelChangeRequestStatusResult
	 */
	@JsonProperty("HotelChangeRequestStatusResult")
	public com.tayyarah.api.hotel.tbo.cancel.model.HotelChangeRequestStatusResult getHotelChangeRequestStatusResult() {
		return HotelChangeRequestStatusResult;
	}

	/**
	 * 
	 * @param HotelChangeRequestStatusResult
	 * The HotelChangeRequestStatusResult
	 */
	@JsonProperty("HotelChangeRequestStatusResult")
	public void setHotelChangeRequestStatusResult(com.tayyarah.api.hotel.tbo.cancel.model.HotelChangeRequestStatusResult HotelChangeRequestStatusResult) {
		this.HotelChangeRequestStatusResult = HotelChangeRequestStatusResult;
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