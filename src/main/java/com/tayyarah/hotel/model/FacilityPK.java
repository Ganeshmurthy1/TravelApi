package com.tayyarah.hotel.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the facilities database table.
 * 
 */
@Embeddable
public class FacilityPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String vendorID;

	private String amenity_id;

	public FacilityPK() {
	}
	public String getVendorID() {
		return this.vendorID;
	}
	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}
	public String getAmenity_id() {
		return this.amenity_id;
	}
	public void setAmenity_id(String amenity_id) {
		this.amenity_id = amenity_id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FacilityPK)) {
			return false;
		}
		FacilityPK castOther = (FacilityPK)other;
		return 
			this.vendorID.equals(castOther.vendorID)
			&& this.amenity_id.equals(castOther.amenity_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.vendorID.hashCode();
		hash = hash * prime + this.amenity_id.hashCode();
		
		return hash;
	}
	@Override
	public String toString() {
		return "FacilityPK [vendorID=" + vendorID + ", amenity_id="
				+ amenity_id + "]";
	}
	
}