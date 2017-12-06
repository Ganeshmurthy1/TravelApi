package com.tayyarah.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@Entity
@Table(name = "country")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Country implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")	
	private String id ;
	@Column(name="c_code")
	private String country_code;
	@Column(name="cur_code")
	private String currrency_code ;
	@Column(name="c_name")	
	private String country_name ;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getCurrrency_code() {
		return currrency_code;
	}
	public void setCurrrency_code(String currrency_code) {
		this.currrency_code = currrency_code;
	}
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	@Override
	public String toString() {
		return "Country [id=" + id + ", country_code=" + country_code
				+ ", currrency_code=" + currrency_code + ", country_name="
				+ country_name + "]";
	}	
	

}
