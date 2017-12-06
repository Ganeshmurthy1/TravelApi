package com.tayyarah.common.entity;

import java.io.Serializable;

import javax.persistence.*;
 
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




/**
 * The persistent class for the airlinelist database table.
 * 
 */
@Entity
@Table(name = "airlinelist")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Airlinelist implements Serializable  {
 

 private static final long serialVersionUID = 1L;

 @Column(name="airlinecode")
 private String airlinecode;

 @Column(name="airlinename")
 private String airlinename;
 
 
 @Id
 @JsonIgnore
 @Column(name="airline")
 private String airline;

 @JsonIgnore
@Column(name="iatacode")
 private String iatacode;

 public Airlinelist() {
 }

 public String getAirlinecode() {
  return this.airlinecode;
 }

 public void setAirlinecode(String airlinecode) {
  this.airlinecode = airlinecode;
 }

public String getAirlinename() {
  return this.airlinename;
 }

 public void setAirlinename(String airlinename) {
  this.airlinename = airlinename;
 }

 public String getIatacode() {
  return this.iatacode;
 }

 public void setIatacode(String iatacode) {
  this.iatacode = iatacode;
 }
 public String getAirline() {
	return airline;
}

public void setAirline(String airline) {
	this.airline = airline;
}

@Override
public String toString() {
	return "Airlinelist [airlinecode=" + airlinecode + ", airlinename="
			+ airlinename + ", airline=" + airline + ", iatacode=" + iatacode
			+ "]";
}

}