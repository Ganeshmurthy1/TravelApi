
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
    "Status",
    "TokenId",
    "Error",
    "Member"
})
public class AuthenticateResponse {

    @JsonProperty("Status")
    private Integer Status;
    @JsonProperty("TokenId")
    private String TokenId;
    @JsonProperty("Error")
    @Valid
    private com.tayyarah.api.hotel.tbo.model.Error Error;
    @JsonProperty("Member")
    @Valid
    private com.tayyarah.api.hotel.tbo.model.Member Member;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public AuthenticateResponse() {
    }

    /**
     * 
     * @param TokenId
     * @param Status
     * @param Member
     * @param Error
     */
    public AuthenticateResponse(Integer Status, String TokenId, com.tayyarah.api.hotel.tbo.model.Error Error, com.tayyarah.api.hotel.tbo.model.Member Member) {
        this.Status = Status;
        this.TokenId = TokenId;
        this.Error = Error;
        this.Member = Member;
    }

    /**
     * 
     * @return
     *     The Status
     */
    @JsonProperty("Status")
    public Integer getStatus() {
        return Status;
    }

    /**
     * 
     * @param Status
     *     The Status
     */
    @JsonProperty("Status")
    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    /**
     * 
     * @return
     *     The TokenId
     */
    @JsonProperty("TokenId")
    public String getTokenId() {
        return TokenId;
    }

    /**
     * 
     * @param TokenId
     *     The TokenId
     */
    @JsonProperty("TokenId")
    public void setTokenId(String TokenId) {
        this.TokenId = TokenId;
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
     *     The Member
     */
    @JsonProperty("Member")
    public com.tayyarah.api.hotel.tbo.model.Member getMember() {
        return Member;
    }

    /**
     * 
     * @param Member
     *     The Member
     */
    @JsonProperty("Member")
    public void setMember(com.tayyarah.api.hotel.tbo.model.Member Member) {
        this.Member = Member;
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
