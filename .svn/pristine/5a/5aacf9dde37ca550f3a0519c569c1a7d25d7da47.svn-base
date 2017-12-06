package com.tayyarah.hotel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class CitySearchResponse implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private com.tayyarah.common.util.Status status;
	private String key;
	private Integer count;
	private Integer totalCount;
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	private ArrayList<Area> areas;	
	public CitySearchResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CitySearchResponse(com.tayyarah.common.util.Status status, String key, ArrayList<Area> areas) {
		super();
		this.status = status;
		this.key = key;
		this.areas = areas;
	}
	public com.tayyarah.common.util.Status getStatus() {
		return status;
	}
	public void setStatus(com.tayyarah.common.util.Status status) {
		this.status = status;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public ArrayList<Area> getAreas() {
		return areas;
	}
	public void setAreas(ArrayList<Area> areas) {
		this.areas = areas;
	}
	@Override
	public String toString() {
		return "CitySearchResponse [status=" + status + ", key=" + key + ", areas=" + areas + "]";
	}
}
