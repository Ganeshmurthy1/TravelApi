package com.tayyarah.hotel.model;

import java.io.Serializable;

public class Area implements Serializable {
	@Override
	public String toString() {
		return "Area [id=" + id + ", name=" + name + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	private Integer id;
	//area, city, state, country(CC)...
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
