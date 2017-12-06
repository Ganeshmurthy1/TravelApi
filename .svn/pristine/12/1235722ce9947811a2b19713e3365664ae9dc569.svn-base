package com.tayyarah.bus.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bus_temp_blocked")
public class BusBlockedSeatTemp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "created_at")
	Timestamp createdAt;	
	@Column(name = "search_key", unique = true, nullable = false)
	private String searchKey;
	@Column(name = "transaction_key", unique = true, nullable = false)
	private String transactionKey;
	@Column(name = "bus_blocked_data" , columnDefinition = "LONGBLOB")
	private byte[] busBlockedData;
	
	public Long getId() {
		return id;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public String getTransactionKey() {
		return transactionKey;
	}
	public byte[] getBusBlockedData() {
		return busBlockedData;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public void setTransactionKey(String transactionKey) {
		this.transactionKey = transactionKey;
	}
	public void setBusBlockedData(byte[] busBlockedData) {
		this.busBlockedData = busBlockedData;
	}

}
