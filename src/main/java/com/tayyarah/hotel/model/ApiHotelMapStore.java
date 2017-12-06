package com.tayyarah.hotel.model;

import java.io.Serializable;




import javax.persistence.*;


@Entity
@Table(name="HotelSearchResponseStore")
//@NamedQuery(name="Hotelsearchresponsestore.findAll", query="SELECT h FROM Hotelsearchresponsestore h")
public class ApiHotelMapStore implements Serializable {
	private static final long serialVersionUID = 1L;	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "search_key", unique = true, nullable = false)
	private int search_key;
	public int getSearch_key() {
		return search_key;
	}

	public void setSearch_key(int search_key) {
		this.search_key = search_key;
	}

	@Lob
	private byte[] hotelres_map;
	public byte[] getHotelres_map() {
		return hotelres_map;
	}

	public void setHotelres_map(byte[] hotelres_map) {
		this.hotelres_map = hotelres_map;
	}

	public byte[] getTg_hotelres_map() {
		return tg_hotelres_map;
	}

	public void setTg_hotelres_map(byte[] tg_hotelres_map) {
		this.tg_hotelres_map = tg_hotelres_map;
	}

	@Lob
	private byte[] tg_hotelres_map;

	public ApiHotelMapStore() {
	}


	

}
