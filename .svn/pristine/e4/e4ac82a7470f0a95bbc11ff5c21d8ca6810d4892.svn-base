package com.tayyarah.hotel.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;



public class HotelPriceComparator implements Comparator<String>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Logger logger = Logger.getLogger(HotelPriceComparator.class);
	Map<String, RoomStay> map;

	public HotelPriceComparator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HotelPriceComparator(Map<String, RoomStay> base) {
		this.map = base;
	}
	
	@Override
	public int compare(String hotelid1, String hotelid2) {

		BigDecimal o1amount = map.get(hotelid1).getBasicPropertyInfo().getBookingPrice();
		BigDecimal o2amount = map.get(hotelid2).getBasicPropertyInfo().getBookingPrice();
		logger.info("price sorting comparion.......call-hotel1..."+hotelid1+"---price--"+o1amount);
		logger.info("price sorting comparion.......call-hotel1..."+hotelid2+"---price--"+o2amount);
		logger.info("islesser.o1amount.compareTo(o2amount)."+(o1amount.compareTo(o2amount)));
		return ((o1amount.compareTo(o2amount) == 0)?-1:(o1amount.compareTo(o2amount)));
	}
}