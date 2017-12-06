package com.tayyarah.hotel.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * The persistent class for the rsroomamenities database table.
 * 
 */
@Entity
@Table(name="rsroomamenities")
@JsonInclude(JsonInclude.Include.NON_NULL)
//@NamedQuery(name="Rsroomamenity.findAll", query="SELECT r FROM Rsroomamenity r")
public class RzRoomAmenity implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private int id;
	@Id
	private byte amenityid;

	private String amenityname;

	private int roomid;

	public RzRoomAmenity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getRoomid() {
		return this.roomid;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

	@Override
	public String toString() {
		return "RzRoomAmenity [id=" + id + ", amenityid=" + amenityid
				+ ", amenityname=" + amenityname + ", roomid=" + roomid + "]";
	}

}