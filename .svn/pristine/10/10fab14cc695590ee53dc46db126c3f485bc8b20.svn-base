package com.tayyarah.hotel.reposit.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hotelmealtypes database table.
 * 
 */
@Entity
@Table(name="hotelmealtypes")
public class Hotelmealtype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="mealtypeid")
	private byte mealtypeid;
	@Column(name="meal_code")
	private String mealCode;
	private String mealdesc;	

	public byte getMealtypeid() {
		return this.mealtypeid;
	}
	public void setMealtypeid(byte mealtypeid) {
		this.mealtypeid = mealtypeid;
	}
	public String getMealCode() {
		return this.mealCode;
	}
	public void setMealCode(String mealCode) {
		this.mealCode = mealCode;
	}
	public String getMealdesc() {
		return this.mealdesc;
	}
	public void setMealdesc(String mealdesc) {
		this.mealdesc = mealdesc;
	}
}