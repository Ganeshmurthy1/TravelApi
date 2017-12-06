package com.tayyarah.hotel.model;

import java.io.Serializable;

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
	"BedTypeCode",
	"BedTypeDescription"
})
public class BedType  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("BedTypeCode")
	private String bedTypeCode;
	@JsonProperty("BedTypeDescription")
	private String bedTypeDescription;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The bedTypeCode
	 */
	@JsonProperty("BedTypeCode")
	public String getBedTypeCode() {
		return bedTypeCode;
	}

	/**
	 * 
	 * @param bedTypeCode
	 * The BedTypeCode
	 */
	@JsonProperty("BedTypeCode")
	public void setBedTypeCode(String bedTypeCode) {
		this.bedTypeCode = bedTypeCode;
	}

	/**
	 * 
	 * @return
	 * The bedTypeDescription
	 */
	@JsonProperty("BedTypeDescription")
	public String getBedTypeDescription() {
		return bedTypeDescription;
	}

	/**
	 * 
	 * @param bedTypeDescription
	 * The BedTypeDescription
	 */
	@JsonProperty("BedTypeDescription")
	public void setBedTypeDescription(String bedTypeDescription) {
		this.bedTypeDescription = bedTypeDescription;
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