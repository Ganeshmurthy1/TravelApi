package com.tayyarah.hotel.model;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the hotelimages database table.
 * 
 */
@Embeddable
public class HotelimagePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String vendorID;

	private String imageUrl;

	public HotelimagePK() {
	}
	public String getVendorID() {
		return this.vendorID;
	}
	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}
	public String getImageUrl() {
		return this.imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HotelimagePK)) {
			return false;
		}
		HotelimagePK castOther = (HotelimagePK)other;
		return 
			this.vendorID.equals(castOther.vendorID)
			&& this.imageUrl.equals(castOther.imageUrl);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.vendorID.hashCode();
		hash = hash * prime + this.imageUrl.hashCode();
		
		return hash;
	}
}