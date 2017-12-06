package com.tayyarah.hotel.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hotelroomstays database table.
 * 
 */
@Entity
@Table(name="hotelroomstays")
//@NamedQuery(name="Hotelroomstay.findAll", query="SELECT h FROM Hotelroomstay h")
public class HotelRoomstay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Lob
	private byte[] id;

	@Lob
	private byte[] roomStayId;

	public HotelRoomstay(){
		
	}

	public byte[] getId() {
		return this.id;
	}

	public void setId(byte[] id) {
		this.id = id;
	}

	public byte[] getRoomStayId() {
		return this.roomStayId;
	}

	public void setRoomStayId(byte[] roomStayId) {
		this.roomStayId = roomStayId;
	}

}