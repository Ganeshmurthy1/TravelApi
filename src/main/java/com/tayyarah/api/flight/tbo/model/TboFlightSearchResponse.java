package com.tayyarah.api.flight.tbo.model;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;



@JsonPropertyOrder({"Response"})
public class TboFlightSearchResponse  implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@JsonProperty("Response")
private com.tayyarah.api.flight.tbo.model.Response Response;

/**
* No args constructor for use in serialization
* 
*/
public TboFlightSearchResponse() {
}

/**
* 
* @param Response
*/
public TboFlightSearchResponse(com.tayyarah.api.flight.tbo.model.Response Response) {
this.Response = Response;
}

/**
* 
* @return
* The Response
*/
@JsonProperty("Response")
public com.tayyarah.api.flight.tbo.model.Response getResponse() {
return Response;
}

/**
* 
* @param Response
* The Response
*/
@JsonProperty("Response")
public void setResponse(com.tayyarah.api.flight.tbo.model.Response Response) {
this.Response = Response;
}
}