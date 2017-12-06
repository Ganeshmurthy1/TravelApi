
package com.tayyarah.api.hotel.tbo.book.model;

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
    "BookResult"
})
public class HotelBookResponse {

    @JsonProperty("BookResult")
    @Valid
    private com.tayyarah.api.hotel.tbo.book.model.BookResult BookResult;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public HotelBookResponse() {
    }

    /**
     * 
     * @param BookResult
     */
    public HotelBookResponse(com.tayyarah.api.hotel.tbo.book.model.BookResult BookResult) {
        this.BookResult = BookResult;
    }

    /**
     * 
     * @return
     *     The BookResult
     */
    @JsonProperty("BookResult")
    public com.tayyarah.api.hotel.tbo.book.model.BookResult getBookResult() {
        return BookResult;
    }

    /**
     * 
     * @param BookResult
     *     The BookResult
     */
    @JsonProperty("BookResult")
    public void setBookResult(com.tayyarah.api.hotel.tbo.book.model.BookResult BookResult) {
        this.BookResult = BookResult;
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
