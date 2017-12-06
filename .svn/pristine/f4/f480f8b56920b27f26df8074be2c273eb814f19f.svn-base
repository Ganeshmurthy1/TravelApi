
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
    "ErrorCode",
    "ErrorMessage"
})
public class Error {

    @JsonProperty("ErrorCode")
    private Integer ErrorCode;
    @JsonProperty("ErrorMessage")
    private String ErrorMessage;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Error() {
    }

    /**
     * 
     * @param ErrorMessage
     * @param ErrorCode
     */
    public Error(Integer ErrorCode, String ErrorMessage) {
        this.ErrorCode = ErrorCode;
        this.ErrorMessage = ErrorMessage;
    }

    /**
     * 
     * @return
     *     The ErrorCode
     */
    @JsonProperty("ErrorCode")
    public Integer getErrorCode() {
        return ErrorCode;
    }

    /**
     * 
     * @param ErrorCode
     *     The ErrorCode
     */
    @JsonProperty("ErrorCode")
    public void setErrorCode(Integer ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    /**
     * 
     * @return
     *     The ErrorMessage
     */
    @JsonProperty("ErrorMessage")
    public String getErrorMessage() {
        return ErrorMessage;
    }

    /**
     * 
     * @param ErrorMessage
     *     The ErrorMessage
     */
    @JsonProperty("ErrorMessage")
    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
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
