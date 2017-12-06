package com.tayyarah.hotel.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.mapping.Array;





public class HotelSearchType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Logger logger = Logger.getLogger(HotelSearchType.class);	
	@Override
	public String toString() {
		return "HotelSearchType [cachelevel=" + cachelevel + ", currency=" + currency + ", version=" + version
				+ ", lang=" + lang + ", city=" + city + ", countrycode=" + countrycode + ", country=" + country
				+ ", datestart=" + datestart + ", dateend=" + dateend + ", mode=" + mode + ", type=" + type + ", order="
				+ order + ", filter=" + filter + ", noofrooms=" + noofrooms + ", roomstext=" + roomstext + "]";
	}
	public String getCachelevel() {
		return cachelevel;
	}
	public void setCachelevel(String cachelevel) {
		this.cachelevel = cachelevel;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getVersion() {
		return version;
	}
	public void setVersion(BigDecimal version) {
		this.version = version;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDatestart() {
		return datestart;
	}
	public void setDatestart(String datestart) {
		this.datestart = datestart;
	}
	public String getDateend() {
		return dateend;
	}
	public void setDateend(String dateend) {
		this.dateend = dateend;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public int getFilter() {
		return filter;
	}
	public void setFilter(int filter) {
		this.filter = filter;
	}
	public int getNoofrooms() {
		return noofrooms;
	}
	public void setNoofrooms(int noofrooms) {
		this.noofrooms = noofrooms;
	}
	public String getRoomstext() {
		return roomstext;
	}
	public void setRoomstext(String roomstext) {
		this.roomstext = roomstext;
	}
	public List<GuestCount> getRoomGuests() throws NumberFormatException, Exception {
		//rooms=$roomid,noofadults,noofchild,ageofchild1,ageofchild2...
		//rooms=$0,2,1,10$1,3,1,8$2,1,2,8,10
		//10 for adults and 10 for children
		List<GuestCount> guestcounts =new ArrayList<GuestCount>();
		if(roomstext == null || roomstext.length() == 0)
			return guestcounts;
		else
		{
			String temp = this.roomstext;
			while(temp.length() >= 2 && temp.contains("$"))
			{
				int guesttextindex = temp.lastIndexOf("$");
				String guesttext = temp.substring(guesttextindex);
				String roomid = guesttext.substring(1, 2);
				int adultcount = Integer.valueOf(guesttext.substring(3, 4));				
				//adding adult quest without age
				GuestCount g = new GuestCount(-1, GuestCount.AGE_QUALIFYING_CODE_ADULT, adultcount, roomid);	
				guestcounts.add(g);
				int childcount = Integer.valueOf(guesttext.substring(5, 6));
				for(int i=0; i<childcount; i++)
				{
					int childithage = Integer.valueOf(guesttext.substring(((i*2)+7),((i*2)+8)));					
					GuestCount cg = new GuestCount(childithage, GuestCount.AGE_QUALIFYING_CODE_CHILD, 1, roomid);
					guestcounts.add(g);
				}				
				guestcounts.add(g);
			}
			
		}
		return guestcounts;
		
	}
	
	public HotelSearchType(String cachelevel, String currency, BigDecimal version, String lang, String city,
			String countrycode, String country, String datestart, String dateend, int mode, int type, String order,
			int filter, int noofrooms, String roomstext) {
		super();
		this.cachelevel = cachelevel;
		this.currency = currency;
		this.version = version;
		this.lang = lang;
		this.city = city;
		this.countrycode = countrycode;
		this.country = country;
		this.datestart = datestart;
		this.dateend = dateend;
		this.mode = mode;
		this.type = type;
		this.order = order;
		this.filter = filter;
		this.noofrooms = noofrooms;
		this.roomstext = roomstext;
	}
	public HotelSearchType(int mode2, int type2, String sortOrderPrice, int filterRate, String cacheLevelLive,
			String currencyInr, BigDecimal bigDecimal, String datestart2, String city2, String country2,
			String country3, String datestart3, String dateend2, int noofrooms2, String rooms) {
		// TODO Auto-generated constructor stub
	}
	private String cachelevel;//Live/VeryRecent
	private String currency;//INR, USD, EUR, GBP 
	private BigDecimal version;//0.0 
	private String lang;//0.0 
	
	private String city;
	private String countrycode;
	private String country;
	private String datestart;
	private String dateend;
	
	private int mode;
	private int type;
	private String order;
	private int filter;
	private int noofrooms;
	private String roomstext;	
	
	public static class GuestCount  implements Serializable{

		public static final String AGE_QUALIFYING_CODE_CHILD = "8";
		public static final String AGE_QUALIFYING_CODE_ADULT = "10";
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
		/*<GuestCount age="10" agequalifyingcode="8"/>*/		
		private int age;		
		private String agequalifyingcode;
		private int count;
		private String resGuestRPH;
	}
	
}
