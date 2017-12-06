package com.tayyarah.api.hotel.tbo.model;

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
	"ResultIndex",
	"HotelCode",
	"EndUserIp",
	"TokenId",
	"TraceId"
})
public class HotelInfoRequest {
	
	@JsonProperty("ResultIndex")
    private Integer ResultIndex;
	@JsonProperty("HotelCode")
	private String HotelCode;
	@JsonProperty("EndUserIp")
	private String EndUserIp;
	@JsonProperty("TokenId")
	private String TokenId;
	@JsonProperty("TraceId")
	private String TraceId;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public HotelInfoRequest() {
	}

	/**
	 * 
	 * @param TokenId
	 * @param HotelCode
	 * @param ResultIndex
	 * @param TraceId
	 * @param EndUserIp
	 */
	public HotelInfoRequest(Integer ResultIndex, String HotelCode, String EndUserIp, String TokenId, String TraceId) {
		this.ResultIndex = ResultIndex;
		this.HotelCode = HotelCode;
		this.EndUserIp = EndUserIp;
		this.TokenId = TokenId;
		this.TraceId = TraceId;
	}

	/**
	 * 
	 * @return
	 * The ResultIndex
	 */
	@JsonProperty("ResultIndex")
	public Integer getResultIndex() {
		return ResultIndex;
	}

	/**
	 * 
	 * @param ResultIndex
	 * The ResultIndex
	 */
	@JsonProperty("ResultIndex")
	public void setResultIndex(Integer ResultIndex) {
		this.ResultIndex = ResultIndex;
	}

	/**
	 * 
	 * @return
	 * The HotelCode
	 */
	@JsonProperty("HotelCode")
	public String getHotelCode() {
		return HotelCode;
	}

	/**
	 * 
	 * @param HotelCode
	 * The HotelCode
	 */
	@JsonProperty("HotelCode")
	public void setHotelCode(String HotelCode) {
		this.HotelCode = HotelCode;
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

	/**
	 * 
	 * @return
	 * The TraceId
	 */
	@JsonProperty("TraceId")
	public String getTraceId() {
		return TraceId;
	}

	/**
	 * 
	 * @param TraceId
	 * The TraceId
	 */
	@JsonProperty("TraceId")
	public void setTraceId(String TraceId) {
		this.TraceId = TraceId;
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