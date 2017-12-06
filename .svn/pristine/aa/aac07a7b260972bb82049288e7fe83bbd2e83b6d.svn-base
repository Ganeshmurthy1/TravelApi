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
"AgencyType",
"CashBalance",
"CreditBalance",
"Error",
"Status"
})
public class TboAgencyBalanceResponse {

@JsonProperty("AgencyType")
private Integer agencyType;
@JsonProperty("CashBalance")
private Integer cashBalance;
@JsonProperty("CreditBalance")
private Integer creditBalance;
@JsonProperty("Error")
private Error error;
@JsonProperty("Status")
private Integer status;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("AgencyType")
public Integer getAgencyType() {
return agencyType;
}

@JsonProperty("AgencyType")
public void setAgencyType(Integer agencyType) {
this.agencyType = agencyType;
}

@JsonProperty("CashBalance")
public Integer getCashBalance() {
return cashBalance;
}

@JsonProperty("CashBalance")
public void setCashBalance(Integer cashBalance) {
this.cashBalance = cashBalance;
}

@JsonProperty("CreditBalance")
public Integer getCreditBalance() {
return creditBalance;
}

@JsonProperty("CreditBalance")
public void setCreditBalance(Integer creditBalance) {
this.creditBalance = creditBalance;
}

@JsonProperty("Error")
public Error getError() {
return error;
}

@JsonProperty("Error")
public void setError(Error error) {
this.error = error;
}

@JsonProperty("Status")
public Integer getStatus() {
return status;
}

@JsonProperty("Status")
public void setStatus(Integer status) {
this.status = status;
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