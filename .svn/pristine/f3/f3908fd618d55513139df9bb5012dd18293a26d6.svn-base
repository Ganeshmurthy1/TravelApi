package com.tayyarah.api.bus.esmart.model;

/*Created By Vimal Susai Raj S 
 * Date : 19-5-2017*/

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiStatus {

@JsonProperty("message")
private String message;
@JsonProperty("success")
private Boolean success;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("message")
public String getMessage() {
return message;
}

@JsonProperty("message")
public void setMessage(String message) {
this.message = message;
}

@JsonProperty("success")
public Boolean getSuccess() {
return success;
}

@JsonProperty("success")
public void setSuccess(Boolean success) {
this.success = success;
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
