package com.tayyarah.hotel.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rsfacilities database table.
 * 
 */
@Embeddable
public class RzFacilityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int vendorID;

	private int amenity_id;

	public RzFacilityPK() {
	}
	public int getVendorID() {
		return this.vendorID;
	}
	public void setVendorID(int vendorID) {
		this.vendorID = vendorID;
	}
	public int getAmenity_id() {
		return this.amenity_id;
	}
	public void setAmenity_id(int amenity_id) {
		this.amenity_id = amenity_id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RzFacilityPK)) {
			return false;
		}
		RzFacilityPK castOther = (RzFacilityPK)other;
		return 
			(this.vendorID == castOther.vendorID)
			&& (this.amenity_id == castOther.amenity_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.vendorID;
		hash = hash * prime + this.amenity_id;
		
		return hash;
	}
	@Override
	public String toString() {
		return "RzFacilityPK [vendorID=" + vendorID + ", amenity_id="
				+ amenity_id + "]";
	}
	
	
}