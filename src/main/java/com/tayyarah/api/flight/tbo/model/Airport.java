package com.tayyarah.api.flight.tbo.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;



@JsonPropertyOrder({
"AirportCode",
"AirportName",
"Terminal",
"CityCode",
"CityName",
"CountryCode",
"CountryName"
})
public class Airport implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@JsonProperty("AirportCode")
private String AirportCode;
@JsonProperty("AirportName")
private String AirportName;
@JsonProperty("Terminal")
private String Terminal;
@JsonProperty("CityCode")
private String CityCode;
@JsonProperty("CityName")
private String CityName;
@JsonProperty("CountryCode")
private String CountryCode;
@JsonProperty("CountryName")
private String CountryName;

/**
* No args constructor for use in serialization
* 
*/
public Airport() {
}

/**
* 
* @param CountryName
* @param Terminal
* @param CityName
* @param CityCode
* @param CountryCode
* @param AirportCode
* @param AirportName
*/
public Airport(String AirportCode, String AirportName, String Terminal, String CityCode, String CityName, String CountryCode, String CountryName) {
this.AirportCode = AirportCode;
this.AirportName = AirportName;
this.Terminal = Terminal;
this.CityCode = CityCode;
this.CityName = CityName;
this.CountryCode = CountryCode;
this.CountryName = CountryName;
}

/**
* 
* @return
* The AirportCode
*/
@JsonProperty("AirportCode")
public String getAirportCode() {
return AirportCode;
}

/**
* 
* @param AirportCode
* The AirportCode
*/
@JsonProperty("AirportCode")
public void setAirportCode(String AirportCode) {
this.AirportCode = AirportCode;
}

public Airport withAirportCode(String AirportCode) {
this.AirportCode = AirportCode;
return this;
}

/**
* 
* @return
* The AirportName
*/
@JsonProperty("AirportName")
public String getAirportName() {
return AirportName;
}

/**
* 
* @param AirportName
* The AirportName
*/
@JsonProperty("AirportName")
public void setAirportName(String AirportName) {
this.AirportName = AirportName;
}

public Airport withAirportName(String AirportName) {
this.AirportName = AirportName;
return this;
}

/**
* 
* @return
* The Terminal
*/
@JsonProperty("Terminal")
public String getTerminal() {
return Terminal;
}

/**
* 
* @param Terminal
* The Terminal
*/
@JsonProperty("Terminal")
public void setTerminal(String Terminal) {
this.Terminal = Terminal;
}

public Airport withTerminal(String Terminal) {
this.Terminal = Terminal;
return this;
}

/**
* 
* @return
* The CityCode
*/
@JsonProperty("CityCode")
public String getCityCode() {
return CityCode;
}

/**
* 
* @param CityCode
* The CityCode
*/
@JsonProperty("CityCode")
public void setCityCode(String CityCode) {
this.CityCode = CityCode;
}

public Airport withCityCode(String CityCode) {
this.CityCode = CityCode;
return this;
}

/**
* 
* @return
* The CityName
*/
@JsonProperty("CityName")
public String getCityName() {
return CityName;
}

/**
* 
* @param CityName
* The CityName
*/
@JsonProperty("CityName")
public void setCityName(String CityName) {
this.CityName = CityName;
}

public Airport withCityName(String CityName) {
this.CityName = CityName;
return this;
}

/**
* 
* @return
* The CountryCode
*/
@JsonProperty("CountryCode")
public String getCountryCode() {
return CountryCode;
}

/**
* 
* @param CountryCode
* The CountryCode
*/
@JsonProperty("CountryCode")
public void setCountryCode(String CountryCode) {
this.CountryCode = CountryCode;
}

public Airport withCountryCode(String CountryCode) {
this.CountryCode = CountryCode;
return this;
}

/**
* 
* @return
* The CountryName
*/
@JsonProperty("CountryName")
public String getCountryName() {
return CountryName;
}

/**
* 
* @param CountryName
* The CountryName
*/
@JsonProperty("CountryName")
public void setCountryName(String CountryName) {
this.CountryName = CountryName;
}

public Airport withCountryName(String CountryName) {
this.CountryName = CountryName;
return this;
}



}