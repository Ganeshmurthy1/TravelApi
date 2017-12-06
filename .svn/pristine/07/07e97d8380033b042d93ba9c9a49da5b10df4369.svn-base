
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
    "ClientId",
    "UserName",
    "Password",
    "EndUserIp"
})
public class AuthenticateRequest {

    @JsonProperty("ClientId")
    private String ClientId;
    @JsonProperty("UserName")
    private String UserName;
    @JsonProperty("Password")
    private String Password;
    @JsonProperty("EndUserIp")
    private String EndUserIp;
    
    
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public AuthenticateRequest() {
    }

    /**
     * 
     * @param Password
     * @param UserName
     * @param ClientId
     * @param EndUserIp
     */
    public AuthenticateRequest(String ClientId, String UserName, String Password, String EndUserIp) {
        this.ClientId = ClientId;
        this.UserName = UserName;
        this.Password = Password;
        this.EndUserIp = EndUserIp;
    }

    /**
     * 
     * @return
     *     The ClientId
     */
    @JsonProperty("ClientId")
    public String getClientId() {
        return ClientId;
    }

    /**
     * 
     * @param ClientId
     *     The ClientId
     */
    @JsonProperty("ClientId")
    public void setClientId(String ClientId) {
        this.ClientId = ClientId;
    }

    /**
     * 
     * @return
     *     The UserName
     */
    @JsonProperty("UserName")
    public String getUserName() {
        return UserName;
    }

    /**
     * 
     * @param UserName
     *     The UserName
     */
    @JsonProperty("UserName")
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    /**
     * 
     * @return
     *     The Password
     */
    @JsonProperty("Password")
    public String getPassword() {
        return Password;
    }

    /**
     * 
     * @param Password
     *     The Password
     */
    @JsonProperty("Password")
    public void setPassword(String Password) {
        this.Password = Password;
    }

    /**
     * 
     * @return
     *     The EndUserIp
     */
    @JsonProperty("EndUserIp")
    public String getEndUserIp() {
        return EndUserIp;
    }

    /**
     * 
     * @param EndUserIp
     *     The EndUserIp
     */
    @JsonProperty("EndUserIp")
    public void setEndUserIp(String EndUserIp) {
        this.EndUserIp = EndUserIp;
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
