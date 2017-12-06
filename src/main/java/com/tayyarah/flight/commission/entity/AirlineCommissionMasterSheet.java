package com.tayyarah.flight.commission.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Access(AccessType.FIELD)
@Table(name = "airline_commission_master_sheet")
@Component
@Scope("session")
@Proxy(lazy = false)
public class AirlineCommissionMasterSheet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="id") 
	private Long id;
	@Column(name="name",columnDefinition="VARCHAR(100)") 
	private String  name;
	@Column(name="description") 
	private String  description;
	@Column(name="is_active",columnDefinition="BIT(1) default 0") 
	private boolean  isActive;
	@Column(name="created_by_user_id") 
	private int  createdByUserID;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public int getCreatedByUserID() {
		return createdByUserID;
	}
	public void setCreatedByUserID(int createdByUserID) {
		this.createdByUserID = createdByUserID;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}