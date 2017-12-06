package com.tayyarah.api.flight.tbo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;




public class Response implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@JsonProperty("ResponseStatus")
private Integer ResponseStatus;
@JsonProperty("Error")
private com.tayyarah.api.flight.tbo.model.Error Error;
@JsonProperty("TraceId")
private String TraceId;
@JsonProperty("Origin")
private String Origin;
@JsonProperty("Destination")
private String Destination;
@JsonProperty("Results")
private List<List<Result>> Results = new ArrayList<List<Result>>();

/**
* No args constructor for use in serialization
* 
*/
public Response() {
}

/**
* 
* @param ResponseStatus
* @param TraceId
* @param Origin
* @param Results
* @param Destination
* @param Error
*/
public Response(Integer ResponseStatus, com.tayyarah.api.flight.tbo.model.Error Error, String TraceId, String Origin, String Destination, List<List<Result>> Results) {
this.ResponseStatus = ResponseStatus;
this.Error = Error;
this.TraceId = TraceId;
this.Origin = Origin;
this.Destination = Destination;
this.Results = Results;
}

/**
* 
* @return
* The ResponseStatus
*/
@JsonProperty("ResponseStatus")
public Integer getResponseStatus() {
return ResponseStatus;
}

/**
* 
* @param ResponseStatus
* The ResponseStatus
*/
@JsonProperty("ResponseStatus")
public void setResponseStatus(Integer ResponseStatus) {
this.ResponseStatus = ResponseStatus;
}

/**
* 
* @return
* The Error
*/
@JsonProperty("Error")
public com.tayyarah.api.flight.tbo.model.Error getError() {
return Error;
}

/**
* 
* @param Error
* The Error
*/
@JsonProperty("Error")
public void setError(com.tayyarah.api.flight.tbo.model.Error Error) {
this.Error = Error;
}

/**
* 
* @return
* The TraceId
*/
@JsonProperty("TraceId")
public String getTraceId() {
return TraceId;
}

/**
* 
* @param TraceId
* The TraceId
*/
@JsonProperty("TraceId")
public void setTraceId(String TraceId) {
this.TraceId = TraceId;
}

/**
* 
* @return
* The Origin
*/
@JsonProperty("Origin")
public String getOrigin() {
return Origin;
}

/**
* 
* @param Origin
* The Origin
*/
@JsonProperty("Origin")
public void setOrigin(String Origin) {
this.Origin = Origin;
}

/**
* 
* @return
* The Destination
*/
@JsonProperty("Destination")
public String getDestination() {
return Destination;
}

/**
* 
* @param Destination
* The Destination
*/
@JsonProperty("Destination")
public void setDestination(String Destination) {
this.Destination = Destination;
}

/**
* 
* @return
* The Results
*/
@JsonProperty("Results")
public List<List<Result>> getResults() {
return Results;
}

/**
* 
* @param Results
* The Results
*/
@JsonProperty("Results")
public void setResults(List<List<Result>> Results) {
this.Results = Results;
}
}