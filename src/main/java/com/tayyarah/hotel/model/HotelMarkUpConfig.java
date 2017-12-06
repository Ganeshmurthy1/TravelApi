package com.tayyarah.hotel.model;

import java.io.Serializable;

public class HotelMarkUpConfig implements Serializable {

	public enum MARKUP {
		CITY_NAME, COUNTRY, REGION, AREA, HOTEL_NAME, HOTEL_CHAIN, HOTEL_TYPE, HOTEL_CLASS, HOTEL_STAR,
	    CHECKIN_DATE, CHECKOUT_DATE, FESTIVAL 
	}
}
