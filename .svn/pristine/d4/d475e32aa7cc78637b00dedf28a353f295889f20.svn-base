package com.tayyarah.hotel.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the rshoteloverview database table.
 * 
 */
@Embeddable
public class RzHoteloverviewPk implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int vendorID;

	private int id;

	public RzHoteloverviewPk() {
	}
	public int getVendorID() {
		return this.vendorID;
	}
	public void setVendorID(int vendorID) {
		this.vendorID = vendorID;
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RzHoteloverviewPk)) {
			return false;
		}
		RzHoteloverviewPk castOther = (RzHoteloverviewPk)other;
		return 
			(this.vendorID == castOther.vendorID)
			&& (this.id == castOther.id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.vendorID;
		hash = hash * prime + this.id;
		
		return hash;
	}
	@Override
	public String toString() {
		return "RzHoteloverviewPk [vendorID=" + vendorID + ", id=" + id + "]";
	}
	
	
}