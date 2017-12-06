package com.tayyarah.hotel.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * The persistent class for the rsfacilities database table.
 * 
 */
@Entity
@Table(name="rsfacilities")
@JsonInclude(JsonInclude.Include.NON_NULL)
//@NamedQuery(name="Rsfacility.findAll", query="SELECT r FROM Rsfacility r")
public class RzFacility implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RzFacilityPK id;

	private String amenityType;

	private String description;

	public RzFacility() {
	}

	public RzFacilityPK getId() {
		return this.id;
	}

	public void setId(RzFacilityPK id) {
		this.id = id;
	}

	public String getAmenityType() {
		return this.amenityType;
	}

	public void setAmenityType(String amenityType) {
		this.amenityType = amenityType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "RzFacility [id=" + id + ", amenityType=" + amenityType
				+ ", description=" + description + "]";
	}

}