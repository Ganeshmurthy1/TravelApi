package com.tayyarah.api.bus.esmart.model;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockTicketResponse {
	@JsonProperty("blockTicketKey")
	private String blockTicketKey;
	@JsonProperty("inventoryType")
	private Integer inventoryType;
	@JsonProperty("apiStatus")
	private ApiStatus apiStatus;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("blockTicketKey")
	public String getBlockTicketKey() {
		return blockTicketKey;
	}

	@JsonProperty("blockTicketKey")
	public void setBlockTicketKey(String blockTicketKey) {
		this.blockTicketKey = blockTicketKey;
	}

	@JsonProperty("inventoryType")
	public Integer getInventoryType() {
		return inventoryType;
	}

	@JsonProperty("inventoryType")
	public void setInventoryType(Integer inventoryType) {
		this.inventoryType = inventoryType;
	}

	@JsonProperty("apiStatus")
	public ApiStatus getApiStatus() {
		return apiStatus;
	}

	@JsonProperty("apiStatus")
	public void setApiStatus(ApiStatus apiStatus) {
		this.apiStatus = apiStatus;
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
