package com.tayyarah.hotel.model;
import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rsroomamenitiesback database table.
 * 
 */
@Entity
@NamedQuery(name="Rsroomamenitiesback.findAll", query="SELECT r FROM Rsroomamenitiesback r")
public class RzRoomAmenitiesBack implements Serializable {
	private static final long serialVersionUID = 1L;
@Id
	private byte amenityid;

	private String amenityname;

	private int id;

	private int roomid;

	public RzRoomAmenitiesBack() {
	}

	public byte getAmenityid() {
		return this.amenityid;
	}

	public void setAmenityid(byte amenityid) {
		this.amenityid = amenityid;
	}

	public String getAmenityname() {
		return this.amenityname;
	}

	public void setAmenityname(String amenityname) {
		this.amenityname = amenityname;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoomid() {
		return this.roomid;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

	@Override
	public String toString() {
		return "RzRoomAmenitiesBack [amenityid=" + amenityid + ", amenityname="
				+ amenityname + ", id=" + id + ", roomid=" + roomid + "]";
	}

}