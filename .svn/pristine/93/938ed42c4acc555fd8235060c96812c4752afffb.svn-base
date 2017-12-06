package com.tayyarah.hotel.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;


/**
 * The persistent class for the hoteltransactions database table.
 * 
 */
@Entity
@Table(name="hotel_temp_transactions")
//@NamedQuery(name="Hoteltransaction.findAll", query="SELECT h FROM Hoteltransaction h")
public class HotelTransactionTemp implements Serializable {
	
	private static final long serialVersionUID = 1L;
/*
	  `id` bigint(20) NOT NULL AUTO_INCREMENT,
	  `search_key` bigint(20) DEFAULT NULL,
	  `order_id` bigint(20) DEFAULT NULL,
	  `api_key` bigint(20) DEFAULT NULL,*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	private Long search_key;
	private Long order_id;
	private Long api_key;
	@Column(name = "created_at")
	Timestamp createdAt;
	
	@Column(name = "updated_at",
			insertable = false,
			columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL")
	Timestamp updatedAt;

	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSearch_key() {
		return search_key;
	}
	public void setSearch_key(Long search_key) {
		this.search_key = search_key;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public Long getApi_key() {
		return api_key;
	}
	public void setApi_key(Long api_key) {
		this.api_key = api_key;
	}
	public HotelTransactionTemp(Long search_key, Long order_id, Long api_key) {
		super();
		this.search_key = search_key;
		this.order_id = order_id;
		this.api_key = api_key;
	}
	public HotelTransactionTemp() {
		super();
		this.search_key = Long.valueOf(-1);
		this.order_id = Long.valueOf(-1);
		this.api_key = Long.valueOf(-1);
	}
	

}
