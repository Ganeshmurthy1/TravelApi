
package com.tayyarah.api.hotel.tbo.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "ResponseStatus",
    "Error",
    "TraceId",
    "HotelDetails"
})
public class HotelInfoResult {

    @JsonProperty("ResponseStatus")
    private Integer ResponseStatus;
    @JsonProperty("Error")
    @Valid
    private com.tayyarah.api.hotel.tbo.model.Error Error;
    @JsonProperty("TraceId")
    private String TraceId;
    @JsonProperty("HotelDetails")
    @Valid
    private com.tayyarah.api.hotel.tbo.model.HotelDetails HotelDetails;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public HotelInfoResult() {
    }

    /**
     * 
     * @param ResponseStatus
     * @param TraceId
     * @param Error
     * @param HotelDetails
     */
    public HotelInfoResult(Integer ResponseStatus, com.tayyarah.api.hotel.tbo.model.Error Error, String TraceId, com.tayyarah.api.hotel.tbo.model.HotelDetails HotelDetails) {
        this.ResponseStatus = ResponseStatus;
        this.Error = Error;
        this.TraceId = TraceId;
        this.HotelDetails = HotelDetails;
    }

    /**
     * 
     * @return
     *     The ResponseStatus
     */
    @JsonProperty("ResponseStatus")
    public Integer getResponseStatus() {
        return ResponseStatus;
    }

    /**
     * 
     * @param ResponseStatus
     *     The ResponseStatus
     */
    @JsonProperty("ResponseStatus")
    public void setResponseStatus(Integer ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    /**
     * 
     * @return
     *     The Error
     */
    @JsonProperty("Error")
    public com.tayyarah.api.hotel.tbo.model.Error getError() {
        return Error;
    }

    /**
     * 
     * @param Error
     *     The Error
     */
    @JsonProperty("Error")
    public void setError(com.tayyarah.api.hotel.tbo.model.Error Error) {
        this.Error = Error;
    }

    /**
     * 
     * @return
     *     The TraceId
     */
    @JsonProperty("TraceId")
    public String getTraceId() {
        return TraceId;
    }

    /**
     * 
     * @param TraceId
     *     The TraceId
     */
    @JsonProperty("TraceId")
    public void setTraceId(String TraceId) {
        this.TraceId = TraceId;
    }

    /**
     * 
     * @return
     *     The HotelDetails
     */
    @JsonProperty("HotelDetails")
    public com.tayyarah.api.hotel.tbo.model.HotelDetails getHotelDetails() {
        return HotelDetails;
    }

    /**
     * 
     * @param HotelDetails
     *     The HotelDetails
     */
    @JsonProperty("HotelDetails")
    public void setHotelDetails(com.tayyarah.api.hotel.tbo.model.HotelDetails HotelDetails) {
        this.HotelDetails = HotelDetails;
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
