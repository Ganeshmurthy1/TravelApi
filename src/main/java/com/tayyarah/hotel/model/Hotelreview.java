package com.tayyarah.hotel.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hotelreviews database table.
 * 
 */
@Entity
@Table(name="hotelreviews")
@NamedQuery(name="Hotelreview.findAll", query="SELECT h FROM Hotelreview h")
public class Hotelreview implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String avgGuestRating;

	private int cleanliness;

	private String comments;

	private String consumer_city;

	private String consumer_Country;

	private String customer_name;

	private int diningQuality;

	private int overallRating;

	@Temporal(TemporalType.TIMESTAMP)
	private Date post_Date;

	private int roomQuality;

	private int serviceQuality;

	//bi-directional many-to-one association to HotelOverview
	@ManyToOne
	@JoinColumn(name="VendorID")
	private HotelOverview hoteloverview;

	public Hotelreview() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAvgGuestRating() {
		return this.avgGuestRating;
	}

	public void setAvgGuestRating(String avgGuestRating) {
		this.avgGuestRating = avgGuestRating;
	}

	public int getCleanliness() {
		return this.cleanliness;
	}

	public void setCleanliness(int cleanliness) {
		this.cleanliness = cleanliness;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getConsumer_city() {
		return this.consumer_city;
	}

	public void setConsumer_city(String consumer_city) {
		this.consumer_city = consumer_city;
	}

	public String getConsumer_Country() {
		return this.consumer_Country;
	}

	public void setConsumer_Country(String consumer_Country) {
		this.consumer_Country = consumer_Country;
	}

	public String getCustomer_name() {
		return this.customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public int getDiningQuality() {
		return this.diningQuality;
	}

	public void setDiningQuality(int diningQuality) {
		this.diningQuality = diningQuality;
	}

	public int getOverallRating() {
		return this.overallRating;
	}

	public void setOverallRating(int overallRating) {
		this.overallRating = overallRating;
	}

	public Date getPost_Date() {
		return this.post_Date;
	}

	public void setPost_Date(Date post_Date) {
		this.post_Date = post_Date;
	}

	public int getRoomQuality() {
		return this.roomQuality;
	}

	public void setRoomQuality(int roomQuality) {
		this.roomQuality = roomQuality;
	}

	public int getServiceQuality() {
		return this.serviceQuality;
	}

	public void setServiceQuality(int serviceQuality) {
		this.serviceQuality = serviceQuality;
	}

	public HotelOverview getHoteloverview() {
		return this.hoteloverview;
	}

	public void setHoteloverview(HotelOverview hoteloverview) {
		this.hoteloverview = hoteloverview;
	}

}