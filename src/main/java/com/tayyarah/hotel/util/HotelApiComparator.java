package com.tayyarah.hotel.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.tayyarah.hotel.model.BasicPropertyInfoType.Position;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;


public class HotelApiComparator implements Comparator<Entry<String, RoomStay>> {

	public static final int COMPARE_BY_PRICE = 0X00;
	public static final int COMPARE_BY_FEAUTURES = 0X01;
	public static final int COMPARE_BY_RATINGS = 0X02;
	public static final int COMPARE_BY_GOOD_REVIEWS = 0X03;
	public static final int COMPARE_BY_NEARITY = 0X04;
	public static final int COMPARE_BY_BEST = 0X05;
	static final Logger logger = Logger.getLogger(HotelApiComparator.class);

	@Override
	public int compare(Entry<String, RoomStay> o1, Entry<String, RoomStay> o2) {
		BigDecimal o1amount = o1.getValue().getRoomRates().getRoomRates().get(0)
				.getRates().getRates().get(0).getBookingPrice().getAmountBeforeTax();
		BigDecimal o2amount = o2.getValue().getRoomRates().getRoomRates().get(0)
				.getRates().getRates().get(0).getBookingPrice().getAmountBeforeTax();

		return (o1amount.compareTo(o2amount));
	}
	
	public static int comparebyLessHotelPrice(RoomStay o1, RoomStay o2) {
		BigDecimal o1amount = o1.getBasicPropertyInfo().getBookingPrice();
		BigDecimal o2amount = o2.getBasicPropertyInfo().getBookingPrice();

		return (o1amount.compareTo(o2amount));
	}

	
	// if o1 is lesser -1, equal 0, more 1
	public int comparebyMoreFeautured(RoomStay o1, RoomStay o2) {
		BigDecimal o1amount = o1.getRoomRates().getRoomRates().get(0)
				.getRates().getRates().get(0).getBookingPrice().getAmountBeforeTax();
		BigDecimal o2amount = o2.getRoomRates().getRoomRates().get(0)
				.getRates().getRates().get(0).getBookingPrice().getAmountBeforeTax();

		return (o1amount.compareTo(o2amount));
	}

	// if o1 is lesser -1, equal 0, more 1
	public int comparebyGoodReviews(RoomStay o1, RoomStay o2) {
		BigDecimal o1amount = o1.getRoomRates().getRoomRates().get(0)
				.getRates().getRates().get(0).getBookingPrice().getAmountBeforeTax();
		BigDecimal o2amount = o2.getRoomRates().getRoomRates().get(0)
				.getRates().getRates().get(0).getBookingPrice().getAmountBeforeTax();

		return (o1amount.compareTo(o2amount));
	}

	// if o1 is lesser -1, equal 0, more 1
	public int comparebyGoodRating(RoomStay o1, RoomStay o2) {
		BigDecimal o1amount = o1.getRoomRates().getRoomRates().get(0)
				.getRates().getRates().get(0).getBookingPrice().getAmountBeforeTax();
		BigDecimal o2amount = o2.getRoomRates().getRoomRates().get(0)
				.getRates().getRates().get(0).getBookingPrice().getAmountBeforeTax();

		return (o1amount.compareTo(o2amount));
	}

	//
	/*# VendorName, Location, Address, Address1, Address2, Longitude, Latitude
	Adarsh Hamilton, Richmond Road, # 2/4, Langford Garden Road, Richmond Town, Richmond Road, BANGALORE, KARNATAKA, India, Pin-560025    , # 2/4, Langford Garden Road, Richmond Town, 77.59820848700000, 12.964276508800000
	Adarsh Hamilton, 560025, 2/4 Langford Garden RoadRichmond TownBengaluruKarnataka 560025, , , 77.59824604000000, 12.964446410000000
	Adarsh Hamilton, , , , , , 
*/
	// if o1 is lesser -1, equal 0, more 1
	public static boolean isSameHotel(RoomStay o1, RoomStay o2) {
		// TODO Auto-generated method stub
		boolean isSameHotelName = isSameHotelByName(o1, o2);
		boolean isSameHotelLocation = isSameHotelByLangnLat(o1, o2);
		boolean isSameHotelAddress = isSameHotelByAddress(o1, o2);
		if(isSameHotelName)
			logger.info("################################# Hotel Duplicate found--((SAME NAME))");	
		if(isSameHotelLocation)
			logger.info("################################# Hotel Duplicate found--((SAME LOCATION))");	
		if(isSameHotelAddress)
			logger.info("################################# Hotel Duplicate found--((SAME ADDRESS))");
		
		if((isSameHotelName && isSameHotelLocation) || 
				(isSameHotelName && isSameHotelAddress) || 
				(isSameHotelName && isSameHotelLocation && isSameHotelAddress)||
				(isSameHotelLocation && isSameHotelAddress))
			
			logger.info("################################# Hotel Duplicate considered--"+((isSameHotelName)?"((SAME NAME))":"")+"..."+((isSameHotelLocation)?"((SAME LOCATION))":"")+"..."+((isSameHotelAddress)?"((SAME ADDRESS))":""));
			;
		return ((isSameHotelName && isSameHotelLocation) || 
				(isSameHotelName && isSameHotelAddress) || 
				(isSameHotelName && isSameHotelLocation && isSameHotelAddress)||
				(isSameHotelLocation && isSameHotelAddress));
		
		
		
	}
	public static boolean isSameApiProvider(RoomStay o1, RoomStay o2) {
		// TODO Auto-generated method stub
		return ( o1.getBasicPropertyInfo().getApiProvider() == o2.getBasicPropertyInfo().getApiProvider());
	}
	
	public static boolean isSameHotelByName(RoomStay o1, RoomStay o2) {
		 return RegExUtil.isSimilarLines(o1.getBasicPropertyInfo().getHotelName(), o2.getBasicPropertyInfo().getHotelName());		
	}
	public static boolean isSameHotelByAddress(RoomStay o1, RoomStay o2) {
		String address1 = (o1.getBasicPropertyInfo() != null && o1.getBasicPropertyInfo().getAddress() != null && o1.getBasicPropertyInfo().getAddress().getAddressLines() != null && o1.getBasicPropertyInfo().getAddress().getAddressLines().size()>0 && o1.getBasicPropertyInfo().getAddress().getAddressLines().get(0) != null)?o1.getBasicPropertyInfo().getAddress().getAddressLines().get(0):"";
		String address2 = (o2.getBasicPropertyInfo() != null && o2.getBasicPropertyInfo().getAddress() != null && o2.getBasicPropertyInfo().getAddress().getAddressLines() != null && o2.getBasicPropertyInfo().getAddress().getAddressLines().size()>0 && o2.getBasicPropertyInfo().getAddress().getAddressLines().get(0) != null)?o2.getBasicPropertyInfo().getAddress().getAddressLines().get(0):"";
		return RegExUtil.isSimilarLines(address1, address2);
	}
	

	public static boolean isSameHotelByLangnLat(RoomStay o1, RoomStay o2) {
		if(o1.getBasicPropertyInfo().getPosition() != null && o2.getBasicPropertyInfo().getPosition() != null)
		{
			return isSamePosition(o1.getBasicPropertyInfo().getPosition(), o2.getBasicPropertyInfo().getPosition());
		}
		else
			return false;
	}

	public static boolean isSamePosition(Position p1, Position p2) {
		if(p1.getLatitude() != null && p1.getLongitude() != null && p2.getLatitude() != null && p2.getLongitude() != null)
			return RegExUtil.isSamePosition(p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude());
		else
			return false;

	}

	private HashMap<String, RoomStay> populateMap(List<RoomStay> mainList) {
		HashMap<String, RoomStay> temp = new HashMap<String, RoomStay>();
		for (RoomStay rs : mainList) {
			temp.put(rs.getBasicPropertyInfo().getHotelName(), rs);
		}
		return temp;
	}

	public List<RoomStay> generateFinalList(List<RoomStay> mainList,
			List<RoomStay> listToBeMerged, int filterby) {
		HashMap<String, RoomStay> tempHotelMap = new HashMap<String, RoomStay>();
		tempHotelMap = populateMap(mainList);
		List<RoomStay> finalList = new ArrayList<RoomStay>();
		for (RoomStay ra : listToBeMerged) {

			boolean isfound = false;
			RoomStay rt = ra;
			for (RoomStay rm : mainList) {

				// check if same hotel. compare and repalce in map
				if (isSameHotel(rm, ra)) {
					switch (filterby) {
					case COMPARE_BY_PRICE:
						rt = (comparebyLessHotelPrice(rm, ra) <= 0 ? rm : ra);
						break;
					case COMPARE_BY_FEAUTURES:
						rt = (comparebyMoreFeautured(rm, ra) <= 0 ? rm : ra);
						break;
					case COMPARE_BY_GOOD_REVIEWS:
						rt = (comparebyGoodReviews(rm, ra) <= 0 ? rm : ra);
						break;
					case COMPARE_BY_RATINGS:
						rt = (comparebyGoodRating(rm, ra) <= 0 ? rm : ra);
						break;
					case COMPARE_BY_NEARITY:
						rt = (comparebyLessHotelPrice(rm, ra) <= 0 ? rm : ra);
						break;
					default:
						rt = (comparebyLessHotelPrice(rm, ra) <= 0 ? rm : ra);
						break;
					}

					isfound = true;
					break;
				}
			}
			// adds newly if room is not found..
			// replace the best on the basis of given criteria if room exist
			tempHotelMap.put(rt.getBasicPropertyInfo().getHotelName(), rt);

		}
		for (Map.Entry<String, RoomStay> entry : tempHotelMap.entrySet()) {
			finalList.add((RoomStay) entry.getValue());
			// Object value = entry.getValue();
			// ...
		}
		return finalList;
	}

	

}
