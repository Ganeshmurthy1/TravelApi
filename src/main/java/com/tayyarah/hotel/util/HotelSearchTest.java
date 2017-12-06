package com.tayyarah.hotel.util;

import java.math.BigDecimal;
import java.util.Random;

import com.tayyarah.hotel.model.HotelSearchCommand;



public class HotelSearchTest {

	public static HotelSearchCommand getHotelSearchCommand(int apiid) throws NumberFormatException, Exception
	{
		Random r = new Random();
		int mode = r.nextInt(3);
		int type = r.nextInt(HotelSearchCommand.TYPE_SEARCH_CITY) + 3;
		HotelSearchCommand hs = new HotelSearchCommand(mode, type, HotelSearchCommand.getSortOrderRandom(),HotelSearchCommand.FILTER_RATE, HotelSearchCommand.CACHE_LEVEL_LIVE,HotelSearchCommand.getCurrencyRandom(), new BigDecimal("1.0"),"en", "Bangalore", "India", "India", "2015-08-20" , "2015-08-22",1 );
		return hs;
	}
	public static HotelSearchCommand getHotelSearchCommand() throws NumberFormatException, Exception
	{
		Random r = new Random();
		int mode = r.nextInt(3);
		int type = r.nextInt(HotelSearchCommand.TYPE_SEARCH_CITY) + 3;
		HotelSearchCommand hs = new HotelSearchCommand(0, 4, HotelSearchCommand.getSortOrderRandom(),HotelSearchCommand.FILTER_RATE, HotelSearchCommand.CACHE_LEVEL_LIVE,HotelSearchCommand.getCurrencyRandom(), new BigDecimal("1.0"),"en", "Bangalore", "India", "India", "2015-08-20" , "2015-08-22",1 );
		return hs;
	}
	
	
	
}
