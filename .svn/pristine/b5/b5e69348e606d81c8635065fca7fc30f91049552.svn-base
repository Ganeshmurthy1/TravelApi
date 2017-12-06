package com.tayyarah.hotel.reposit.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hotel_room_extra_fares database table.
 * 
 */
@Entity
@Table(name="hotel_room_extra_fares")
public class HotelRoomExtraFare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="vendorid")
	private int vendorid;
	@Column(name="room_aditionalcharges_desc")
	private String roomAditionalchargesDesc;
	@Column(name="room_aditionalcharges_or_pricechange")
	private String roomAditionalchargesOrPricechange;
	@Column(name="roomno")
	private int roomno;	
	@Column(name="with_breakfast")
	private String withBreakfast;


	public String getRoomAditionalchargesDesc() {
		return this.roomAditionalchargesDesc;
	}
	public void setRoomAditionalchargesDesc(String roomAditionalchargesDesc) {
		this.roomAditionalchargesDesc = roomAditionalchargesDesc;
	}
	public String getRoomAditionalchargesOrPricechange() {
		return this.roomAditionalchargesOrPricechange;
	}
	public void setRoomAditionalchargesOrPricechange(String roomAditionalchargesOrPricechange) {
		this.roomAditionalchargesOrPricechange = roomAditionalchargesOrPricechange;
	}
	public int getRoomno() {
		return this.roomno;
	}
	public void setRoomno(int roomno) {
		this.roomno = roomno;
	}
	public int getVendorid() {
		return this.vendorid;
	}
	public void setVendorid(int vendorid) {
		this.vendorid = vendorid;
	}
	public String getWithBreakfast() {
		return this.withBreakfast;
	}
	public void setWithBreakfast(String withBreakfast) {
		this.withBreakfast = withBreakfast;
	}
}