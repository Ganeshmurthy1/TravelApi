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
"RequestType",
"Remarks",
"BookingId",
"EndUserIp",
"TokenId"
})
public class SendChangeRequest {

@JsonProperty("RequestType")
private Integer RequestType;
@JsonProperty("Remarks")
private String Remarks;
@JsonProperty("BookingId")
private BigInteger BookingId;
@JsonProperty("EndUserIp")
private String EndUserIp;
@JsonProperty("TokenId")
private String TokenId;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/**
* 
* @return
* The RequestType
*/
@JsonProperty("RequestType")
public Integer getRequestType() {
return RequestType;
}

/**
* 
* @param RequestType
* The RequestType
*/
@JsonProperty("RequestType")
public void setRequestType(Integer RequestType) {
this.RequestType = RequestType;
}

/**
* 
* @return
* The Remarks
*/
@JsonProperty("Remarks")
public String getRemarks() {
return Remarks;
}

/**
* 
* @param Remarks
* The Remarks
*/
@JsonProperty("Remarks")
public void setRemarks(String Remarks) {
this.Remarks = Remarks;
}

/**
* 
* @return
* The BookingId
*/
@JsonProperty("BookingId")
public BigInteger getBookingId() {
return BookingId;
}

/**
* 
* @param BookingId
* The BookingId
*/
@JsonProperty("BookingId")
public void setBookingId(BigInteger BookingId) {
this.BookingId = BookingId;
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
