package com.tayyarah.api.hotel.tbo.cancel.model;

import java.math.BigInteger;
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
	"ChangeRequestId",
	"EndUserIp",
	"TokenId"
})
public class GetChangeRequestStatus {

	@JsonProperty("ChangeRequestId")
	private Long ChangeRequestId;
	@JsonProperty("EndUserIp")
	private String EndUserIp;
	@JsonProperty("TokenId")
	private String TokenId;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The ChangeRequestId
	 */
	@JsonProperty("ChangeRequestId")
	public Long getChangeRequestId() {
		return ChangeRequestId;
	}

	/**
	 * 
	 * @param ChangeRequestId
	 * The ChangeRequestId
	 */
	@JsonProperty("ChangeRequestId")
	public void setChangeRequestId(Long ChangeRequestId) {
		this.ChangeRequestId = ChangeRequestId;
	}

	/**
	 * 
	 * @return
	 * The EndUserIp
	 */
	@JsonProperty("EndUserIp")
	public String getEndUserIp() {
		return EndUserIp;
	}

	/**
	 * 
	 * @param EndUserIp
	 * The EndUserIp
	 */
	@JsonProperty("EndUserIp")
	public void setEndUserIp(String EndUserIp) {
		this.EndUserIp = EndUserIp;
	}

	/**
	 * 
	 * @return
	 * The TokenId
	 */
	@JsonProperty("TokenId")
	public String getTokenId() {
		return TokenId;
	}

	/**
	 * 
	 * @param TokenId
	 * The TokenId
	 */
	@JsonProperty("TokenId")
	public void setTokenId(String TokenId) {
		this.TokenId = TokenId;
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
