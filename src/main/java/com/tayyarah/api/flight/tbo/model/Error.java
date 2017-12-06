package com.tayyarah.api.flight.tbo.model;

import java.io.Serializable;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;



public class Error implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@JsonProperty("ErrorCode")
private Integer ErrorCode;
@JsonProperty("ErrorMessage")
private String ErrorMessage;

/**
* No args constructor for use in serialization
* 
*/
public Error() {
}

/**
* 
* @param ErrorMessage
* @param ErrorCode
*/
public Error(Integer ErrorCode, String ErrorMessage) {
this.ErrorCode = ErrorCode;
this.ErrorMessage = ErrorMessage;
}

/**
* 
* @return
* The ErrorCode
*/
@JsonProperty("ErrorCode")
public Integer getErrorCode() {
return ErrorCode;
}

/**
* 
* @param ErrorCode
* The ErrorCode
*/
@JsonProperty("ErrorCode")
public void setErrorCode(Integer ErrorCode) {
this.ErrorCode = ErrorCode;
}

/**
* 
* @return
* The ErrorMessage
*/
@JsonProperty("ErrorMessage")
public String getErrorMessage() {
return ErrorMessage;
}

/**
* 
* @param ErrorMessage
* The ErrorMessage
*/
@JsonProperty("ErrorMessage")
public void setErrorMessage(String ErrorMessage) {
this.ErrorMessage = ErrorMessage;
}
}