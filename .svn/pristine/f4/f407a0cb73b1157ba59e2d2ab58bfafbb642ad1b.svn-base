package com.tayyarah.hotel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class HotelBasicBookCommand implements Serializable {
	

	@Override
	public String toString() {
		return "HotelBasicBookCommand [searchKey=" + searchKey + ", appkey=" + appkey + ", hotelCode=" + hotelCode
				+ ", apiProvider=" + apiProvider + ", ratePlanCodes=" + ratePlanCodes + ", comments=" + comments
				+ ", customers=" + customers + ", roomTypes=" + roomTypes + "]";
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


	public List<String> getRatePlanCodes() {
		return ratePlanCodes;
	}


	public void setRatePlanCodes(List<String> ratePlanCodes) {
		this.ratePlanCodes = ratePlanCodes;
	}


	public List<String> getComments() {
		return comments;
	}


	public void setComments(List<String> comments) {
		this.comments = comments;
	}


	public List<Customer> getCustomers() {
		return customers;
	}


	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}


	public List<RoomType> getRoomTypes() {
		return roomTypes;
	}


	public void setRoomTypes(List<RoomType> roomTypes) {
		this.roomTypes = roomTypes;
	}


	private long searchKey;
	private String appkey;
	private String hotelCode;	
	private int apiProvider;
	private List<String> ratePlanCodes; 
	private List<String> comments; 
	private List<Customer> customers; 	
	private List<RoomType> roomTypes; 	
	
	
	
	public static class RoomType implements Serializable{				
		public RoomType() {
			super();
			// TODO Auto-generated constructor stub
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
		public RoomType(String roomTypeCode, int numberOfUnits) {
			super();
			this.roomTypeCode = roomTypeCode;
			this.numberOfUnits = numberOfUnits;
		}
		private String roomTypeCode;		
		private int numberOfUnits;
	}	
	
	
	public static class Customer implements Serializable
	{
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
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public String getAge() {
			return age;
		}
		public void setAge(String age) {
			this.age = age;
		}
		public String getSurname() {
			return surname;
		}
		public void setSurname(String surname) {
			this.surname = surname;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public List<Telephone> getTelephone() {
			return telephone;
		}
		public void setTelephone(List<Telephone> telephone) {
			this.telephone = telephone;
		}
		public Address getAddress() {
			return address;
		}
		public void setAddress(Address address) {
			this.address = address;
		}
		public String getProfileType() {
			return profileType;
		}
		public void setProfileType(String profileType) {
			this.profileType = profileType;
		}
		@Override
		public String toString() {
			return "Customer [middleName=" + middleName + ", namePrefix=" + namePrefix + ", givenName=" + givenName
					+ ", gender=" + gender + ", age=" + age + ", surname=" + surname + ", email=" + email
					+ ", telephone=" + telephone + ", address=" + address + ", profileType=" + profileType + "]";
		}
		public Customer() {
			super();
			// TODO Auto-generated constructor stub
		}
	    private String middleName;
	    private String namePrefix;
	    private String givenName;
	    private String gender;
	    private String age;
	    private String surname;
	    private String email;
	    private List<Telephone> telephone;	   
	    private Address address;
	    private String profileType;
	    
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
	


}
