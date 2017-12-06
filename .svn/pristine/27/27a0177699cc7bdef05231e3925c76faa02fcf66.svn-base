package com.tayyarah.api.bus.esmart.model;

/*Created By Vimal Susai Raj S 
 * Date : 19-5-2017*/

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class StationList {

@JsonProperty("stationId")
private Integer stationId;
@JsonProperty("stationName")
private String stationName;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("stationId")
public Integer getStationId() {
return stationId;
}

@JsonProperty("stationId")
public void setStationId(Integer stationId) {
this.stationId = stationId;
}

@JsonProperty("stationName")
public String getStationName() {
return stationName;
}

@JsonProperty("stationName")
public void setStationName(String stationName) {
this.stationName = stationName;
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

