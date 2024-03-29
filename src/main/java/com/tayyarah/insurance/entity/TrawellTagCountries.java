package com.tayyarah.insurance.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tayyarah.common.util.Timestampable;


@Entity
@Table(name = "trawelltag_countries")
public class TrawellTagCountries extends Timestampable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "code")
	private Integer code;
	@Column(name = "description")
	private String description;
	
	public Integer getCode() {
		return code;
	}
	public String getDescription() {
		return description;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
