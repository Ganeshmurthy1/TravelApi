package com.tayyarah.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.bind.annotation.XmlAttribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tayyarah.hotel.entity.HotelSearchCity;
import com.tayyarah.hotel.model.HotelSearchCriterionType.RateRange;
import com.tayyarah.hotel.model.HotelSearchType.GuestCount;
import com.tayyarah.hotel.model.ItemSearchCriterionType.HotelRef;
import com.tayyarah.hotel.model.TPAExtensions.Promotion;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelSearchCommand implements Serializable, Cloneable {	
	
	private static final long serialVersionUID = 1L;
	
	public static final int MODE_SEARCH_SINGLE = 0X00;
	public static final int MODE_SEARCH_DETAILED = 0X01;
	public static final int MODE_SEARCH_MULTI = 0X02;
	public static final int TYPE_SEARCH_CITY = 0X04;
	public static final int TYPE_SEARCH_POI = 0X05;
	public static final int TYPE_SEARCH_AREA = 0X06;
	public static final int FILTER_RATE = 0X07;
	public static final int FILTER_NO = 0X00;
	public static final String PROMOTION_TYPE_HOTEL = "HOTEL";
	public static final String PROMOTION_TYPE_MERCHANT = "MERCHANT";
	public static final String PROMOTION_TYPE_BOTH = "BOTH";
	public static final String PROMOTION_NAME_ALL = "ALL Promotions";
	public static final String PROMOTION_NAME_STAY_PERIOD = "Stay Period";
	public static final String PROMOTION_NAME_BOOKING_WINDOW = "Booking Window";
	public static final String PROMOTION_NAME_LENGTHOFSTAY = "Length of Stay";
	public static final String PROMOTION_NAME_DAYOFWEEK = "Day Of Week";
	public static final String PROMOTION_NAME_LAST_MINUTE = "Last Minute Promotion";
	public static final String CACHE_LEVEL_LIVE = "Live";
	public static final String CACHE_LEVEL_VERY_RECENT = "VeryRecent";
	public static final String CURRENCY_INR = "INR";//INR, USD, EUR, GBP
	public static final String CURRENCY_USD ="USD";
	public static final String CURRENCY_EUR ="EUR";
	public static final String CURRENCY_GBP ="GBP";
	public static final String SORT_ORDER_TG_RANKING = "TG_RANKING";
	public static final String SORT_ORDER_PRICE = "PRICE";
	public static final String SORT_ORDER_GUEST_RATING = "GUEST_RATING";
	public static final String SORT_ORDER_STAR_RATING_ASCENDING = "STAR_RATING_ASCENDING";
	public static final String SORT_ORDER_STAR_RATING_DESCENDING = "STAR_RATING_DESCENDING";
	public static final String SORT_ORDER_DEALS = "DEALS";	

	private String cachelevel;//Live/VeryRecent
	private String currency;//INR, USD, EUR, GBP 
	private BigDecimal version;//0.0 
	private String lang;//0.0 
	private String city;
	private String countrycode;
	private String country;
	private String destinationType;
	private String endUserIp;
	private String tokenId;
	@JsonIgnore
	private HotelSearchCity searchCity;
	@JsonIgnore
	private String baseCurrency;
	private String datestart;
	private String dateend;
	private HotelPoi poi;
	private List<GuestCount> guestcounts;
	private List<RoomReqInfo> roomrequests;
	@JsonIgnore
	private List<HotelRef> hotelrefs;
	@JsonIgnore
	private List<RateRange> rateranges;
	@JsonIgnore
	private Pagination pagination;
	@JsonIgnore
	private Promotion promotion;
	@XmlAttribute(name = "HotelMarkupCommissionDetails")
	private HotelMarkupCommissionDetails hotelMarkupCommissionDetails;
	private int maxIndex;
	private int type;
	private String order;
	private int filter;
	private int noofrooms;
	@JsonIgnore
	private String roomstext;
	private String apikey;	
	private int mode;
	private int index;
	private boolean isCallableAgain;	
	
	@Override
	public String toString() {
		return "HotelSearchCommand [apikey=" + apikey + ", mode=" + mode + ", index=" + index + ", maxIndex=" + maxIndex
				+ ", type=" + type + ", order=" + order + ", filter=" + filter + ", noofrooms=" + noofrooms
				+ ", roomstext=" + roomstext + ", cachelevel=" + cachelevel + ", currency=" + currency + ", version="
				+ version + ", lang=" + lang + ", city=" + city + ", countrycode=" + countrycode + ", country="
				+ country + ", destinationType=" + destinationType + ", endUserIp=" + endUserIp + ", tokenId=" + tokenId
				+ ", searchCity=" + searchCity + ", datestart=" + datestart + ", dateend=" + dateend + ", poi=" + poi
				+ ", guestcounts=" + guestcounts + ", roomrequests=" + roomrequests + ", hotelrefs=" + hotelrefs
				+ ", rateranges=" + rateranges + ", pagination=" + pagination + ", promotion=" + promotion
				+ ", hotelMarkupCommissionDetails=" + hotelMarkupCommissionDetails + "]";
	}

	public HotelSearchCommand(int mode, int type, String order, int filter, String cachelevel, String currency,
			BigDecimal version, String lang, String city, String countrycode, String country, String datestart,
			String dateend, int noofrooms) throws NumberFormatException, Exception {
		super();
		this.apikey = "";
		this.mode = mode;
		this.type = type;
		this.order = order;
		this.filter = filter;
		this.cachelevel = cachelevel;
		this.currency = currency;
		this.version = version;
		this.lang = lang;
		this.city = city;
		this.countrycode = countrycode;
		this.country = country;
		this.datestart = datestart;
		this.dateend = dateend;
		this.noofrooms = noofrooms;
		this.hotelrefs = new ArrayList<HotelRef>();		
		this.rateranges = new ArrayList<RateRange>();
		this.pagination = new Pagination(false);
		this.promotion = new Promotion();
		this.searchCity = null;
		this.poi = new HotelPoi();
		this.hotelMarkupCommissionDetails = new HotelMarkupCommissionDetails();
		this.tokenId = "";
		this.endUserIp = "";
		this.destinationType = "ALL";

		switch (mode) {
		case HotelSearchCommand.MODE_SEARCH_SINGLE:
			switch (type) {
			case HotelSearchCommand.TYPE_SEARCH_CITY:
				//needed info is already set
				break;
			case HotelSearchCommand.TYPE_SEARCH_POI:
				this.poi.setPoi_Id(1);
				break;
			case HotelSearchCommand.TYPE_SEARCH_AREA:
				//loops for adding hotel ref of area id s
				List<HotelRef> hotelrefs = new ArrayList<HotelRef>();			
				for (int i=0;i<1;i++) {
					HotelRef hr = new HotelRef();
					hr.setAreaID("4564");
					hotelrefs.add(hr);
				}
				this.setHotelrefs(hotelrefs);
				break;
			default:
				break;
			}
			break;
		case HotelSearchCommand.MODE_SEARCH_DETAILED:
			//loops for adding hotel ref of area id s
			List<HotelRef> hotelrefs = new ArrayList<HotelRef>();			
			for (int i=0;i<1;i++) {
				String hc = "0000986"+i;
				HotelRef hr = new HotelRef();
				hr.setHotelCode(hc);
				hotelrefs.add(hr);
			}
			this.setHotelrefs(hotelrefs);
			break;	
		case HotelSearchCommand.MODE_SEARCH_MULTI:
			//loops for adding hotel ref of area id s
			List<HotelRef> hotelrefsm = new ArrayList<HotelRef>();			
			for (int i=0;i<5;i++) {
				String hc = "0000986"+i;
				HotelRef hr = new HotelRef();
				hr.setHotelCode(hc);
				hotelrefsm.add(hr);
			}
			this.setHotelrefs(hotelrefsm);
			break;	
		default:
			break;
		}
		
		//Filtering..
		switch (filter) {
		case HotelSearchCommand.FILTER_RATE :			
			List<RateRange> rateranges = new ArrayList<RateRange>();			
			for (int i=0;i<1;i++) {
				RateRange rr = new RateRange();
				rr.setMinRate(new BigDecimal("100"));
				rr.setMaxRate(new BigDecimal("2000"));
				rateranges.add(rr);
			}
			this.setRateranges(rateranges);
			break;
		default:
			break;
		}		

		this.pagination.setEnabled(false);
		pagination.setFrom(0);
		pagination.setTo(0);
		
		//add promotion if there..
		this.promotion.setType(PROMOTION_TYPE_BOTH);
		this.promotion.setName(PROMOTION_NAME_ALL);
		initRoomReqsAndGuests();
	}
	
	public HotelSearchCommand() {
		super();
	}
	
	public HotelSearchCommand(String apikey, int mode, int type, String order, int filter, String cachelevel, String currency,
			BigDecimal version, String lang, String city, String countrycode, String country, String datestart,
			String dateend, int noofrooms, String roomstext) throws NumberFormatException, Exception {
		super();
		this.apikey = apikey;
		this.mode = mode;
		this.type = type;
		this.order = order;
		this.filter = filter;
		this.cachelevel = cachelevel;
		this.currency = currency;
		this.version = version;
		this.lang = lang;
		this.city = city;
		this.countrycode = countrycode;
		this.country = country;
		this.datestart = datestart;
		this.dateend = dateend;
		this.noofrooms = noofrooms;
		this.hotelrefs = new ArrayList<HotelRef>();
		this.rateranges = new ArrayList<RateRange>();
		this.pagination = new Pagination(false);
		this.promotion = new Promotion();
		this.roomstext = roomstext;
		this.poi = new HotelPoi();
		this.searchCity = null;
		this.hotelMarkupCommissionDetails = new HotelMarkupCommissionDetails();
		this.tokenId = "";
		this.endUserIp = "";
		this.destinationType = "ALL";

		switch (mode) {
		case HotelSearchCommand.MODE_SEARCH_SINGLE:
			switch (type) {
			case HotelSearchCommand.TYPE_SEARCH_CITY:
				//needed info is already set
				break;
			case HotelSearchCommand.TYPE_SEARCH_POI:

				this.poi.setPoi_Id(1);				

				break;
			case HotelSearchCommand.TYPE_SEARCH_AREA:				
				List<HotelRef> hotelrefs = new ArrayList<HotelRef>();			
				for (int i=0;i<1;i++) {
					HotelRef hr = new HotelRef();
					hr.setAreaID("4564");
					hotelrefs.add(hr);
				}
				this.setHotelrefs(hotelrefs);
				break;
			default:
				break;
			}
			break;
		case HotelSearchCommand.MODE_SEARCH_DETAILED:			
			List<HotelRef> hotelrefs = new ArrayList<HotelRef>();			
			for (int i=0;i<1;i++) {
				String hc = "0000986"+i;
				HotelRef hr = new HotelRef();
				hr.setHotelCode(hc);
				hotelrefs.add(hr);
			}
			this.setHotelrefs(hotelrefs);
			break;			

		case HotelSearchCommand.MODE_SEARCH_MULTI:		
			List<HotelRef> hotelrefsm = new ArrayList<HotelRef>();			
			for (int i=0;i<5;i++) {
				String hc = "0000986"+i;
				HotelRef hr = new HotelRef();
				hr.setHotelCode(hc);
				hotelrefsm.add(hr);
			}
			this.setHotelrefs(hotelrefsm);
			break;	
		default:
			break;
		}
		
		//Filtering..
		switch (filter) {
		case HotelSearchCommand.FILTER_RATE :		
			List<RateRange> rateranges = new ArrayList<RateRange>();			
			for (int i=0;i<1;i++) {
				RateRange rr = new RateRange();
				rr.setMinRate(new BigDecimal("100"));
				rr.setMaxRate(new BigDecimal("2000"));
				rateranges.add(rr);
			}
			this.setRateranges(rateranges);
			break;
		default:
			break;
		}		

		this.pagination.setEnabled(false);
		pagination.setFrom(0);
		pagination.setTo(0);
		
		//add promotion if there..
		this.promotion.setType(PROMOTION_TYPE_BOTH);
		this.promotion.setName(PROMOTION_NAME_ALL);
		initRoomReqsAndGuests();
	}

	
	private void initRoomReqsAndGuests() throws NumberFormatException, Exception {
		
		//rooms=$roomid,noofadults,noofchild,ageofchild1,ageofchild2...
		//rooms=$0,2,1,10$1,3,1,8$2,1,2,8,10
		//10 for adults and 10 for children		
		this.roomrequests =new ArrayList<RoomReqInfo>();
		this.guestcounts =new ArrayList<GuestCount>();
		String delimeterRoom = "\\$";				
		String[] roomtexts = roomstext.split(delimeterRoom);
		for (int i = 0; i<roomtexts.length; i++) {			
			String delimeterSplit = "\\,";	
			String roomtext = roomtexts[i];
			String[] literals = roomtext.split(delimeterSplit);
			if(literals.length >= 3)
			{
				Integer roomindex = Integer.valueOf(literals[0]);
				Integer adultcount = Integer.valueOf(literals[1]);
				Integer childcount = Integer.valueOf(literals[2]);				
				List<Integer> childages = new ArrayList<Integer>();
				GuestCount g = new GuestCount(-1, GuestCount.AGE_QUALIFYING_CODE_ADULT, adultcount, literals[0]);	
				this.guestcounts.add(g);
				if((childcount>0) && (literals.length >= (2+ childcount)))
					for(int childageindex = 3; childageindex < (3 + childcount); childageindex++)
					{
						Integer childage = Integer.valueOf(literals[childageindex]);
						GuestCount cg = new GuestCount(childage, GuestCount.AGE_QUALIFYING_CODE_CHILD, 1, literals[0]);
						childages.add(childage);
						this.guestcounts.add(cg);
					}				
				RoomReqInfo roomReq = new RoomReqInfo(roomindex, adultcount, childcount, childages);	
				roomrequests.add(roomReq);
			}			
		}
	}

	public String getRoomstext() {
		return roomstext;
	}
	public void setRoomstext(String roomstext) {
		this.roomstext = roomstext;
	}
	/**
	 * @return the mode
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * @return the cachelevel
	 */
	public String getCachelevel() {
		return cachelevel;
	}

	/**
	 * @param cachelevel the cachelevel to set
	 */
	public void setCachelevel(String cachelevel) {
		this.cachelevel = cachelevel;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the version
	 */
	public BigDecimal getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(BigDecimal version) {
		this.version = version;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the countrycode
	 */
	public String getCountrycode() {
		return countrycode;
	}

	/**
	 * @param countrycode the countrycode to set
	 */
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the datestart
	 */
	public String getDatestart() {
		return datestart;
	}

	/**
	 * @param datestart the datestart to set
	 */
	public void setDatestart(String datestart) {
		this.datestart = datestart;
	}

	/**
	 * @return the dateend
	 */
	public String getDateend() {
		return dateend;
	}

	/**
	 * @param dateend the dateend to set
	 */
	public void setDateend(String dateend) {
		this.dateend = dateend;
	}

	/**
	 * @return the guestcounts
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public List<GuestCount> getGuestcounts() throws NumberFormatException, Exception {
		if(guestcounts == null || guestcounts.size() == 0)
			initRoomReqsAndGuests();
		return guestcounts;
	}

	/**
	 * @param guestcounts the guestcounts to set
	 */
	public void setGuestcounts(List<GuestCount> guestcounts) {
		this.guestcounts = guestcounts;
	}

	public List<RoomReqInfo> getRoomrequests() throws NumberFormatException, Exception {
		if(roomrequests == null || roomrequests.size() == 0)
			initRoomReqsAndGuests();
		return roomrequests;
	}


	public void setRoomrequests(List<RoomReqInfo> roomrequests) {
		this.roomrequests = roomrequests;
	}

	/**
	 * @return the hotelrefs
	 */
	public List<HotelRef> getHotelrefs() {
		return hotelrefs;
	}

	/**
	 * @param hotelrefs the hotelrefs to set
	 */
	public void setHotelrefs(List<HotelRef> hotelrefs) {
		this.hotelrefs = hotelrefs;
	}

	/**
	 * @return the rateranges
	 */
	public List<RateRange> getRateranges() {
		return rateranges;
	}

	/**
	 * @param rateranges the rateranges to set
	 */
	public void setRateranges(List<RateRange> rateranges) {
		this.rateranges = rateranges;
	}

	/**
	 * @return the pagination
	 */
	public Pagination getPagination() {
		return pagination;
	}

	/**
	 * @param pagination the pagination to set
	 */
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public static String getSortOrderRandom()
	{
		Random r = new Random();
		int index = r.nextInt(6);
		String sort_order = SORT_ORDER_PRICE;
		switch (index) {
		case 0:
			sort_order = SORT_ORDER_TG_RANKING;
			break;
		case 1:
			sort_order = SORT_ORDER_PRICE;
			break;
		case 2:
			sort_order = SORT_ORDER_GUEST_RATING;
			break;
		case 3:
			sort_order = SORT_ORDER_STAR_RATING_ASCENDING;
			break;
		case 4:
			sort_order = SORT_ORDER_STAR_RATING_DESCENDING;
			break;
		case 5:
			sort_order = SORT_ORDER_DEALS;
			break;	

		default:
			break;
		}
		return sort_order;
	}

	public static String getCurrencyRandom()
	{
		Random r = new Random();
		int index = r.nextInt(3);
		String currency = CURRENCY_INR;
		switch (index) {
		case 0:
			currency = CURRENCY_INR;
			break;
		case 1:
			currency = CURRENCY_USD;
			break;
		case 2:
			currency = CURRENCY_EUR;
			break;
		case 3:
			currency = CURRENCY_GBP;
			break;		
		default:
			break;
		}
		return currency;
	}

	public boolean isCallableAgain() {
		return isCallableAgain;
	}


	public void setCallableAgain(boolean isCallableAgain) {
		this.isCallableAgain = isCallableAgain;
	}

		

	public String getApikey() {
		return apikey;
	}


	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	/**
	 * @return the noofrooms
	 */
	public int getNoofrooms() {
		return noofrooms;
	}

	/**
	 * @param noofrooms the noofrooms to set
	 */
	public void setNoofrooms(int noofrooms) {
		this.noofrooms = noofrooms;
	}

	/**
	 * @return the filter
	 */
	public int getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(int filter) {
		this.filter = filter;
	}
	

	public String getDestinationType() {
		return destinationType;
	}


	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}

	
	public String getEndUserIp() {
		return endUserIp;
	}


	public void setEndUserIp(String endUserIp) {
		this.endUserIp = endUserIp;
	}


	public String getTokenId() {
		return tokenId;
	}


	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}


	public String getBaseCurrency() {
		return baseCurrency;
	}


	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}


	public HotelSearchCity getSearchCity() {
		return searchCity;
	}


	public void setSearchCity(HotelSearchCity searchCity) {
		this.searchCity = searchCity;
	}

	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public int getMaxIndex() {
		return maxIndex;
	}


	public void setMaxIndex(int maxIndex) {
		this.maxIndex = maxIndex;
	}
	
	/**
	 * @return the poi
	 */
	public HotelPoi getPoi() {
		return poi;
	}

	/**
	 * @param poi the poi to set
	 */
	public void setPoi(HotelPoi poi) {
		this.poi = poi;
	}

	public HotelMarkupCommissionDetails getHotelMarkupCommissionDetails() {
		if(hotelMarkupCommissionDetails == null)
			hotelMarkupCommissionDetails = new HotelMarkupCommissionDetails();
		return hotelMarkupCommissionDetails;
	}

	public void setHotelMarkupCommissionDetails(HotelMarkupCommissionDetails hotelMarkupCommissionDetails) {
		this.hotelMarkupCommissionDetails = hotelMarkupCommissionDetails;
	}

	/**
	 * @return the promotion
	 */
	public Promotion getPromotion() {
		return promotion;
	}

	/**
	 * @param promotion the promotion to set
	 */
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}


	public static class StayDateRange implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;		
		@SuppressWarnings("unused")
		private String start;
		@SuppressWarnings("unused")
		private String end;
		
		public StayDateRange(String start, String end) {
			super();
			this.start = start;
			this.end = end;			
		}		
	}

	public static class GuestCount  implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 3075867308800524576L;
		public static final String AGE_QUALIFYING_CODE_CHILD = "8";
		public static final String AGE_QUALIFYING_CODE_ADULT = "10";
		private int age;		
		private String agequalifyingcode;
		private int count;
		private String resGuestRPH;
		
		public GuestCount() {
			super();
			this.age = -1;
			this.agequalifyingcode = AGE_QUALIFYING_CODE_ADULT;
			this.count = 0;
			this.resGuestRPH = "0";
		}
		
		public GuestCount(int age, String agequalifyingcode, int count, String resGuestRPH) {
			super();
			this.age = age;
			this.agequalifyingcode = agequalifyingcode;
			this.count = count;
			this.resGuestRPH = resGuestRPH;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getAgequalifyingcode() {
			return agequalifyingcode;
		}
		public void setAgequalifyingcode(String agequalifyingcode) {
			this.agequalifyingcode = agequalifyingcode;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public String getResGuestRPH() {
			return resGuestRPH;
		}
		public void setResGuestRPH(String resGuestRPH) {
			this.resGuestRPH = resGuestRPH;
		}		
	}

	public static class RoomReqInfo  implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4786846445655558472L;
		private int roomindex;		
		private int noofAdult;
		private int noofChild;
		private List<Integer> childages;

		public RoomReqInfo() {
			super();
			this.roomindex = -1;
			this.noofAdult = -1;
			this.noofChild = -1;
			this.childages = new ArrayList<Integer>();
		}
		public RoomReqInfo(int roomindex, int noofAdult, int noofChild, List<Integer> childages) {
			super();
			this.roomindex = roomindex;
			this.noofAdult = noofAdult;
			this.noofChild = noofChild;
			this.childages = childages;
		}
		
		public int getRoomindex() {
			return roomindex;
		}
		public void setRoomindex(int roomindex) {
			this.roomindex = roomindex;
		}
		public int getNoofAdult() {
			return noofAdult;
		}
		public void setNoofAdult(int noofAdult) {
			this.noofAdult = noofAdult;
		}
		public int getNoofChild() {
			return noofChild;
		}
		public void setNoofChild(int noofChild) {
			this.noofChild = noofChild;
		}		
		public List<Integer> getChildages() {
			return childages;
		}
		public void setChildages(List<Integer> childages) {
			this.childages = childages;
		}
	}
	
	public static class Address implements Serializable{
		@SuppressWarnings("unused")
		private String city;
		@SuppressWarnings("unused")
		private String country;
		
		public Address(String city, String country) {
			super();
			this.city = city;
			this.country = country;
		}		
	}
	
	public static class Pagination implements Serializable{
		private boolean enabled;
		private int from;
		private int to;
		public Pagination(boolean enabled, int from, int to) {
			super();
			this.enabled = enabled;
			this.from = from;
			this.to = to;
		}

		public Pagination(boolean enabled) {
			super();
			this.enabled = enabled;
		}
		/*<Pagination enabled="true" hotelsFrom="01" hotelsTo="05"/> */

		/**
		 * @return the enabled
		 */
		public boolean isEnabled() {
			return enabled;
		}
		/**
		 * @param enabled the enabled to set
		 */
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
		/**
		 * @return the from
		 */
		public int getFrom() {
			return from;
		}
		/**
		 * @param from the from to set
		 */
		public void setFrom(int from) {
			this.from = from;
		}
		/**
		 * @return the to
		 */
		public int getTo() {
			return to;
		}
		/**
		 * @param to the to to set
		 */
		public void setTo(int to) {
			this.to = to;
		}
		
	}
}