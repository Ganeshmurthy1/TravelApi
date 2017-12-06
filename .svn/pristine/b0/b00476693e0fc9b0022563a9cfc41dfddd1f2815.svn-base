package com.tayyarah.hotel.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the hotelinandaround database table.
 * 
 */
@Embeddable
public class HotelinandaroundPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String vendorID;

	private String nameOfAttraction;

	private String distanceInKm;

	public HotelinandaroundPK() {
	}
	public String getVendorID() {
		return this.vendorID;
	}
	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}
	public String getNameOfAttraction() {
		return this.nameOfAttraction;
	}
	public void setNameOfAttraction(String nameOfAttraction) {
		this.nameOfAttraction = nameOfAttraction;
	}
	public String getDistanceInKm() {
		return this.distanceInKm;
	}
	public void setDistanceInKm(String distanceInKm) {
		this.distanceInKm = distanceInKm;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HotelinandaroundPK)) {
			return false;
		}
		HotelinandaroundPK castOther = (HotelinandaroundPK)other;
		return 
			this.vendorID.equals(castOther.vendorID)
			&& this.nameOfAttraction.equals(castOther.nameOfAttraction)
			&& this.distanceInKm.equals(castOther.distanceInKm);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.vendorID.hashCode();
		hash = hash * prime + this.nameOfAttraction.hashCode();
		hash = hash * prime + this.distanceInKm.hashCode();
		
		return hash;
	}
}