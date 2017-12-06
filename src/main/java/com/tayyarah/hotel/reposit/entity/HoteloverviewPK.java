package com.tayyarah.hotel.reposit.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the hoteloverview database table.
 * 
 */
@Embeddable
public class HoteloverviewPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int vendorID;
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVendorID() {
		return this.vendorID;
	}
	public void setVendorID(int vendorID) {
		this.vendorID = vendorID;
	}
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HoteloverviewPK)) {
			return false;
		}
		HoteloverviewPK castOther = (HoteloverviewPK)other;
		return 
			(this.id == castOther.id)
			&& (this.vendorID == castOther.vendorID);
	}
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.vendorID;
		
		return hash;
	}
}