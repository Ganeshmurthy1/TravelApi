package com.tayyarah.api.flight.tbo.model;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"ClientId",
"EndUserIp",
"TokenAgencyId",
"TokenMemberId",
"TokenId"
})
public class TboAgencyBalanceRequest {

@JsonProperty("ClientId")
private String clientId;
@JsonProperty("EndUserIp")
private String endUserIp;
@JsonProperty("TokenAgencyId")
private Integer tokenAgencyId;
@JsonProperty("TokenMemberId")
private Integer tokenMemberId;
@JsonProperty("TokenId")
private String tokenId;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("ClientId")
public String getClientId() {
return clientId;
}

@JsonProperty("ClientId")
public void setClientId(String clientId) {
this.clientId = clientId;
}

@JsonProperty("EndUserIp")
public String getEndUserIp() {
return endUserIp;
}

@JsonProperty("EndUserIp")
public void setEndUserIp(String endUserIp) {
this.endUserIp = endUserIp;
}

@JsonProperty("TokenAgencyId")
public Integer getTokenAgencyId() {
return tokenAgencyId;
}

@JsonProperty("TokenAgencyId")
public void setTokenAgencyId(Integer tokenAgencyId) {
this.tokenAgencyId = tokenAgencyId;
}

@JsonProperty("TokenMemberId")
public Integer getTokenMemberId() {
return tokenMemberId;
}

@JsonProperty("TokenMemberId")
public void setTokenMemberId(Integer tokenMemberId) {
this.tokenMemberId = tokenMemberId;
}

@JsonProperty("TokenId")
public String getTokenId() {
return tokenId;
}

@JsonProperty("TokenId")
public void setTokenId(String tokenId) {
this.tokenId = tokenId;
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