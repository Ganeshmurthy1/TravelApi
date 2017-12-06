package com.tayyarah.api.flight.tbo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
"AirlineCode",
"AirlineName",
"FlightNumber",
"FareClass",
"OperatingCarrier"
})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airline implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@JsonProperty("AirlineCode")
private String AirlineCode;
@JsonProperty("AirlineName")
private String AirlineName;
@JsonProperty("FlightNumber")
private String FlightNumber;
@JsonProperty("FareClass")
private String FareClass;
@JsonProperty("OperatingCarrier")
private String OperatingCarrier;

/**
* No args constructor for use in serialization
* 
*/
public Airline() {
}

/**
* 
* @param AirlineName
* @param FlightNumber
* @param OperatingCarrier
* @param FareClass
* @param AirlineCode
*/
public Airline(String AirlineCode, String AirlineName, String FlightNumber, String FareClass, String OperatingCarrier) {
this.AirlineCode = AirlineCode;
this.AirlineName = AirlineName;
this.FlightNumber = FlightNumber;
this.FareClass = FareClass;
this.OperatingCarrier = OperatingCarrier;
}

/**
* 
* @return
* The AirlineCode
*/
@JsonProperty("AirlineCode")
public String getAirlineCode() {
return AirlineCode;
}

/**
* 
* @param AirlineCode
* The AirlineCode
*/
@JsonProperty("AirlineCode")
public void setAirlineCode(String AirlineCode) {
this.AirlineCode = AirlineCode;
}


/**
* 
* @return
* The AirlineName
*/
@JsonProperty("AirlineName")
public String getAirlineName() {
return AirlineName;
}

/**
* 
* @param AirlineName
* The AirlineName
*/
@JsonProperty("AirlineName")
public void setAirlineName(String AirlineName) {
this.AirlineName = AirlineName;
}


/**
* 
* @return
* The FlightNumber
*/
@JsonProperty("FlightNumber")
public String getFlightNumber() {
return FlightNumber;
}

/**
* 
* @param FlightNumber
* The FlightNumber
*/
@JsonProperty("FlightNumber")
public void setFlightNumber(String FlightNumber) {
this.FlightNumber = FlightNumber;
}


/**
* 
* @return
* The FareClass
*/
@JsonProperty("FareClass")
public String getFareClass() {
return FareClass;
}

/**
* 
* @param FareClass
* The FareClass
*/
@JsonProperty("FareClass")
public void setFareClass(String FareClass) {
this.FareClass = FareClass;
}


/**
* 
* @return
* The OperatingCarrier
*/
@JsonProperty("OperatingCarrier")
public String getOperatingCarrier() {
return OperatingCarrier;
}

/**
* 
* @param OperatingCarrier
* The OperatingCarrier
*/
@JsonProperty("OperatingCarrier")
public void setOperatingCarrier(String OperatingCarrier) {
this.OperatingCarrier = OperatingCarrier;
}



}