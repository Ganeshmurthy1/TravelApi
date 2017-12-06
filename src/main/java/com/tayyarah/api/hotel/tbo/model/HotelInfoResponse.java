
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
    "HotelInfoResult"
})
public class HotelInfoResponse {

    @JsonProperty("HotelInfoResult")
    @Valid
    private com.tayyarah.api.hotel.tbo.model.HotelInfoResult HotelInfoResult;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public HotelInfoResponse() {
    }

    /**
     * 
     * @param HotelInfoResult
     */
    public HotelInfoResponse(com.tayyarah.api.hotel.tbo.model.HotelInfoResult HotelInfoResult) {
        this.HotelInfoResult = HotelInfoResult;
    }

    /**
     * 
     * @return
     *     The HotelInfoResult
     */
    @JsonProperty("HotelInfoResult")
    public com.tayyarah.api.hotel.tbo.model.HotelInfoResult getHotelInfoResult() {
        return HotelInfoResult;
    }

    /**
     * 
     * @param HotelInfoResult
     *     The HotelInfoResult
     */
    @JsonProperty("HotelInfoResult")
    public void setHotelInfoResult(com.tayyarah.api.hotel.tbo.model.HotelInfoResult HotelInfoResult) {
        this.HotelInfoResult = HotelInfoResult;
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
