package com.tayyarah.hotel.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The persistent class for the rshotelroomimages database table.
 * 
 */
@Entity
@Table(name="rshotelroomimages")

//@NamedQuery(name="Rshotelroomimage.findAll", query="SELECT r FROM Rshotelroomimage r")
public class RzHotelRoomImage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="content_title")
	private String contentTitle;

	private String imageurl;

	private int roomid;

	private byte vendorid;

	public RzHotelRoomImage() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContentTitle() {
		return this.contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getImageurl() {
		return this.imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public int getRoomid() {
		return this.roomid;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

	public byte getVendorid() {
		return this.vendorid;
	}

	public void setVendorid(byte vendorid) {
		this.vendorid = vendorid;
	}

	@Override
	public String toString() {
		return "RzHotelRoomImage [id=" + id + ", contentTitle=" + contentTitle
				+ ", imageurl=" + imageurl + ", roomid=" + roomid
				+ ", vendorid=" + vendorid + "]";
	}

}