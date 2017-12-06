package com.tayyarah.hotel.reposit.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the mealsfare database table.
 * 
 */
@Entity
@Table(name="mealsfare")
public class Mealsfare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="mealfareid")
	private String mealfareid;
	@Column(name="adult_fare")
	private float adultFare;
	@Column(name="child_fare")
	private float childFare;
	@Column(name="meal_additional_charges_pricechange")
	private String mealAdditionalChargesPricechange;
	@Column(name="meal_aditional_charges_desc")
	private String mealAditionalChargesDesc;
	@Column(name="meal_desc")
	private String mealDesc;
	private String mealcode;
	private BigInteger roomid;

	public String getMealfareid() {
		return this.mealfareid;
	}
	public void setMealfareid(String mealfareid) {
		this.mealfareid = mealfareid;
	}
	public float getAdultFare() {
		return this.adultFare;
	}
	public void setAdultFare(float adultFare) {
		this.adultFare = adultFare;
	}
	public float getChildFare() {
		return this.childFare;
	}
	public void setChildFare(float childFare) {
		this.childFare = childFare;
	}
	public String getMealAdditionalChargesPricechange() {
		return this.mealAdditionalChargesPricechange;
	}
	public void setMealAdditionalChargesPricechange(String mealAdditionalChargesPricechange) {
		this.mealAdditionalChargesPricechange = mealAdditionalChargesPricechange;
	}
	public String getMealAditionalChargesDesc() {
		return this.mealAditionalChargesDesc;
	}
	public void setMealAditionalChargesDesc(String mealAditionalChargesDesc) {
		this.mealAditionalChargesDesc = mealAditionalChargesDesc;
	}
	public String getMealDesc() {
		return this.mealDesc;
	}
	public void setMealDesc(String mealDesc) {
		this.mealDesc = mealDesc;
	}
	public String getMealcode() {
		return this.mealcode;
	}
	public void setMealcode(String mealcode) {
		this.mealcode = mealcode;
	}
	public BigInteger getRoomid() {
		return this.roomid;
	}
	public void setRoomid(BigInteger roomid) {
		this.roomid = roomid;
	}
}