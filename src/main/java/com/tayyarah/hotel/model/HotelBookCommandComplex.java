package com.tayyarah.hotel.model;

import java.io.Serializable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotelBookCommandComplex implements Serializable{
	 public HotelBookCommandComplex() {
			super();
			// TODO Auto-generated constructor stub
		}
	
	public String getCorrelationid() {
		return correlationid;
	}
	public void setCorrelationid(String correlationid) {
		this.correlationid = correlationid;
	}
	@Override
	public String toString() {
		return "HotelBookCommand [orderid=" + orderid + ",correlationid=" + correlationid + ", userid=" + userid + ", password=" + password + ", payBy="
				+ payBy + ", searchKey=" + searchKey + ", appkey=" + appkey + ", hotelCode=" + hotelCode
				+ ", apiProvider=" + apiProvider + ", ratePlans=" + ratePlans + ", comments=" + comments
				+ ", roomTypes=" + roomTypes + ", profiles=" + profiles + "]";
	}
	public HotelBookCommandComplex(String orderid, String correlationid, String userid, String password, String payBy, long searchKey, String appkey,
			String hotelCode, int apiProvider, List<com.tayyarah.hotel.model.RatePlanType> ratePlanCodes, List<String> comments,
			List<com.tayyarah.hotel.model.RoomTypeType> roomTypes, List<Profile> profiles) {
		super();
		this.orderid = orderid;
		this.correlationid = correlationid;
		this.userid = userid;
		this.password = password;
		this.payBy = payBy;
		this.searchKey = searchKey;
		this.appkey = appkey;
		this.hotelCode = hotelCode;
		this.apiProvider = apiProvider;
		this.ratePlans = ratePlanCodes;
		this.comments = comments;
		this.roomTypes = roomTypes;
		this.profiles = profiles;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPayBy() {
		return payBy;
	}
	public void setPayBy(String payBy) {
		this.payBy = payBy;
	}
	public long getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(long searchKey) {
		this.searchKey = searchKey;
	}
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getHotelCode() {
		return hotelCode;
	}
	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}
	public int getApiProvider() {
		return apiProvider;
	}
	public void setApiProvider(int apiProvider) {
		this.apiProvider = apiProvider;
	}
	
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	
	public List<Profile> getProfiles() {
		return profiles;
	}
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
	
	private static final long serialVersionUID = 1L;
	


	public HotelBookCommandComplex(int isauto) {
		super();
		this.orderid = "";
		this.correlationid = "";
		this.userid = "";
		this.password = "";
		this.searchKey = 28;
		this.hotelCode = "00007068";
		this.appkey = "eererewdvcv";
		this.apiProvider = 1;
		this.ratePlans = new ArrayList<com.tayyarah.hotel.model.RatePlanType>();
		com.tayyarah.hotel.model.RatePlanType rateplan = new com.tayyarah.hotel.model.RatePlanType();
		rateplan.setRatePlanCode("0000100924");
		com.tayyarah.hotel.model.RatePlanType.MealsIncluded mealincluded = new com.tayyarah.hotel.model.RatePlanType.MealsIncluded();
		List<String> mealPlanCodes = new ArrayList<String>();
		mealPlanCodes.add("CP");
		mealincluded.setMealPlanCodes(mealPlanCodes);
		mealincluded.setMealPlanIndicator(true);
		rateplan.setMealsIncluded(mealincluded);
		this.ratePlans.add(rateplan);
		this.comments = new ArrayList<String>();
		this.comments.add("No comment..");		
		this.roomTypes = new ArrayList<com.tayyarah.hotel.model.RoomTypeType>();
		com.tayyarah.hotel.model.RoomTypeType roomtype = new com.tayyarah.hotel.model.RoomTypeType();
		roomtype.setRoomTypeCode("RGL");
		//roomtype.setApiRoomTypeCode("RGL");	
		roomtype.setNumberOfUnits(new Integer("2"));		
		roomTypes.add(roomtype);	
		this.roomRates = new ArrayList<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate>();
		com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate roomrate = new com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate();
		roomrate.setRoomTypeCode("RGL");
		roomrate.setRatePlanCode("BO1");		
		roomrate.setNumberOfUnits(new BigInteger("2"));		
		roomRates.add(roomrate);	
		
		this.profiles = new ArrayList<Profile>();		
		profiles.add(new Profile(isauto));		
		
	}
	
	
	protected String orderid;
	protected String correlationid;
	protected String userid;
	protected String password;
	protected String payBy;
	private long searchKey;
	private String appkey;
	private String hotelCode;	
	private int apiProvider;
	private List<com.tayyarah.hotel.model.RatePlanType> ratePlans; 	
	private List<String> comments; 
	private List<com.tayyarah.hotel.model.RoomTypeType> roomTypes; 
	private List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> roomRates; 		
	private List<Profile> profiles;
	
	

	/*public static class RoomType{				
		public String getRoomTypeCode() {
			return roomTypeCode;
		}
		public void setRoomTypeCode(String roomTypeCode) {
			this.roomTypeCode = roomTypeCode;
		}
		public int getNumberOfUnits() {
			return numberOfUnits;
		}
		public void setNumberOfUnits(int numberOfUnits) {
			this.numberOfUnits = numberOfUnits;
		}
		public RoomType(String roomTypeCode, int numberOfUnits) {
			super();
			this.roomTypeCode = roomTypeCode;
			this.numberOfUnits = numberOfUnits;
		}
		private String roomTypeCode;		
		private int numberOfUnits;
	}	*/
	
	public List<com.tayyarah.hotel.model.RatePlanType> getRatePlans() {
		return ratePlans;
	}
	public void setRatePlans(List<com.tayyarah.hotel.model.RatePlanType> ratePlans) {
		this.ratePlans = ratePlans;
	}
	public List<com.tayyarah.hotel.model.RoomTypeType> getRoomTypes() {
		return roomTypes;
	}
	public void setRoomTypes(List<com.tayyarah.hotel.model.RoomTypeType> roomTypes) {
		this.roomTypes = roomTypes;
	}
	public List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> getRoomRates() {
		return roomRates;
	}
	public void setRoomRates(List<com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate> roomRates) {
		this.roomRates = roomRates;
	}
	
	public static class RoomType implements Serializable{
		public RoomType() {
			super();
			// TODO Auto-generated constructor stub
		}
		public RoomType(String roomTypeCode, int numberOfUnits) {
			super();
			this.roomTypeCode = roomTypeCode;
			this.numberOfUnits = numberOfUnits;
		}
		@Override
		public String toString() {
			return "RoomType [roomTypeCode=" + roomTypeCode + ", numberOfUnits=" + numberOfUnits + "]";
		}
		public String getRoomTypeCode() {
			return roomTypeCode;
		}
		public void setRoomTypeCode(String roomTypeCode) {
			this.roomTypeCode = roomTypeCode;
		}
		public int getNumberOfUnits() {
			return numberOfUnits;
		}
		public void setNumberOfUnits(int numberOfUnits) {
			this.numberOfUnits = numberOfUnits;
		}
		private String roomTypeCode;		
		private int numberOfUnits;
	}
	
	
	public static class Profile implements Serializable
	{
		public Profile() {
			super();
			// TODO Auto-generated constructor stub
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Override
		public String toString() {
			return "Profile [customer=" + customer + ", profileType=" + profileType + "]";
		}
		public Profile(Customer customer, String profileType) {
			super();
			this.customer = customer;
			this.profileType = profileType;
		}
		public Profile(int isauto) {
			super();
			this.customer = new Customer(isauto);
			this.profileType = "1";
		}
		public Customer getCustomer() {
			return customer;
		}
		public void setCustomer(Customer customer) {
			this.customer = customer;
		}
		public String getProfileType() {
			return profileType;
		}
		public void setProfileType(String profileType) {
			this.profileType = profileType;
		}
		private Customer customer;
		private String profileType;		
	}
	public static class Customer implements Serializable
	{
	    public Customer() {
			super();
			// TODO Auto-generated constructor stub
		}
		@Override
		public String toString() {
			return "Customer [personName=" + personName + ", telephone=" + telephone + ", email=" + email + ", address="
					+ address + "]";
		}
		public Customer(PersonName personName, List<Telephone> telephone, String email, Address address) {
			super();
			this.personName = personName;
			this.telephone = telephone;
			this.email = email;
			this.address = address;
		}
		public Customer(int isauto) {
			super();
			this.personName = new PersonName(isauto);
			this.telephone = new ArrayList<Telephone>();
			this.telephone.add(new Telephone(isauto));
			this.telephone.add(new Telephone(isauto));
			this.email = "ramesha1987@gmail.com";
			this.address = new Address(isauto);
		}
		public PersonName getPersonName() {
			return personName;
		}
		public void setPersonName(PersonName personName) {
			this.personName = personName;
		}
		public List<Telephone> getTelephone() {
			return telephone;
		}
		public void setTelephone(List<Telephone> telephone) {
			this.telephone = telephone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public Address getAddress() {
			return address;
		}
		public void setAddress(Address address) {
			this.address = address;
		}
		private PersonName personName;
	    private List<Telephone> telephone;
	    private String email;
	    private Address address;
	    
	}
	
	public static class Address implements Serializable
	{
	    public List<String> getAddressLine() {
			return addressLine;
		}
		public void setAddressLine(List<String> addressLine) {
			this.addressLine = addressLine;
		}
		public Address() {
			super();
			// TODO Auto-generated constructor stub
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public Address(String countryName, String postalCode, String stateProv, String cityName, List<String> addressLine) {
			super();
			this.countryName = countryName;
			this.postalCode = postalCode;
			this.stateProv = stateProv;
			this.cityName = cityName;
			this.addressLine = addressLine;
		}
		public Address(int isauto) {
			super();
			this.countryName = "india";
			this.postalCode = "560001";
			this.stateProv = "KA";
			this.cityName = "bangalore";
			this.addressLine = new ArrayList<String>();				
			addressLine.add("no 33/3, 17 th cross, cmh road,");		
			addressLine.add("lakhmi puram, ulsoor");
			
		}
		
		public String getCountryName() {
			return countryName;
		}
		public void setCountryName(String countryName) {
			this.countryName = countryName;
		}
		public String getPostalCode() {
			return postalCode;
		}
		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}
		public String getStateProv() {
			return stateProv;
		}
		public void setStateProv(String stateProv) {
			this.stateProv = stateProv;
		}
		public String getCityName() {
			return cityName;
		}
		public void setCityName(String cityName) {
			this.cityName = cityName;
		}
		
		private String countryName;
	    private String postalCode;
	    private String stateProv;
	    private String cityName;
	    private List<String> addressLine;	    
	}
	
	public static class PersonName implements Serializable
	{
	    public PersonName() {
			super();
			// TODO Auto-generated constructor stub
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Override
		public String toString() {
			return "PersonName [middleName=" + middleName + ", namePrefix=" + namePrefix + ", givenName=" + givenName
					+ ", surname=" + surname + "]";
		}
		public PersonName(String middleName, String namePrefix, String givenName, String surname) {
			super();
			this.middleName = middleName;
			this.namePrefix = namePrefix;
			this.givenName = givenName;
			this.surname = surname;
		}
		public PersonName(int isauto) {
			super();	
			this.middleName = "";
			this.namePrefix = "Mr.";
			this.givenName = "Rakesh";
			this.surname = "kumar";
		}
		public String getMiddleName() {
			return middleName;
		}
		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}
		public String getNamePrefix() {
			return namePrefix;
		}
		public void setNamePrefix(String namePrefix) {
			this.namePrefix = namePrefix;
		}
		public String getGivenName() {
			return givenName;
		}
		public void setGivenName(String givenName) {
			this.givenName = givenName;
		}
		public String getSurname() {
			return surname;
		}
		public void setSurname(String surname) {
			this.surname = surname;
		}
		private String middleName;
	    private String namePrefix;
	    private String givenName;
	    private String surname;
	   
	}
	public static class Telephone implements Serializable
	{
	    public Telephone() {
			super();
			// TODO Auto-generated constructor stub
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Override
		public String toString() {
			return "Telephone [extension=" + extension + ", areaCityCode=" + areaCityCode + ", phoneTechType="
					+ phoneTechType + ", phoneNumber=" + phoneNumber + ", countryAccessCode=" + countryAccessCode + "]";
		}
		public Telephone(String extension, String areaCityCode, String phoneTechType, String phoneNumber,
				String countryAccessCode) {
			super();
			this.extension = extension;
			this.areaCityCode = areaCityCode;
			this.phoneTechType = phoneTechType;
			this.phoneNumber = phoneNumber;
			this.countryAccessCode = countryAccessCode;
		}
		public Telephone(int isauto) {
			super();
			 /* "areaCityCode": "80",
              "countryAccessCode": "91",
              "extension": "0",
              "phoneNumber": "8050459818",
              "phoneTechType": "1"*/
			this.extension = "0";
			this.areaCityCode = "80";
			this.phoneTechType = "1";
			this.phoneNumber = "8050459818";
			this.countryAccessCode = "91";
		}
		public String getExtension() {
			return extension;
		}
		public void setExtension(String extension) {
			this.extension = extension;
		}
		public String getAreaCityCode() {
			return areaCityCode;
		}
		public void setAreaCityCode(String areaCityCode) {
			this.areaCityCode = areaCityCode;
		}
		public String getPhoneTechType() {
			return phoneTechType;
		}
		public void setPhoneTechType(String phoneTechType) {
			this.phoneTechType = phoneTechType;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getCountryAccessCode() {
			return countryAccessCode;
		}
		public void setCountryAccessCode(String countryAccessCode) {
			this.countryAccessCode = countryAccessCode;
		}
		private String extension;
	    private String areaCityCode;
	    private String phoneTechType;
	    private String phoneNumber;
	    private String countryAccessCode;
	   
	}	


}
