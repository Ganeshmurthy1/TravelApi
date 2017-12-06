package com.tayyarah.hotel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tayyarah.common.entity.RmConfigTripDetailsModel;
import com.tayyarah.hotel.util.HotelIdFactoryImpl;


public class HotelBookCommand implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String password;
	protected String payBy;
	private long searchKey;
	private String appkey;
	private String hotelCode;
	private int apiProvider;
	private int companyId;
	private int configId;
	private String bookingCode;
	private int numberOfUnits;
	private String bookingSystemType;
	private boolean isQuotation;
	private int quotationId ;
	private int emulateByCompanyId;
	private String emulateByUserId;
	private boolean isEmulateFlag;
	private Boolean isRmDetails;
	private List<RmConfigTripDetailsModel> rmDataListDetails;
	private Boolean isCompanyEntity;
	private int companyEntityId;

	public Boolean getIsCompanyEntity() {
		return isCompanyEntity;
	}
	public int getCompanyEntityId() {
		return companyEntityId;
	}
	public void setIsCompanyEntity(Boolean isCompanyEntity) {
		this.isCompanyEntity = isCompanyEntity;
	}
	public void setCompanyEntityId(int companyEntityId) {
		this.companyEntityId = companyEntityId;
	}
	 public List<RoomRateType> getRoomRateTypesxx() {
		return roomRateTypes;
	}
	 public HotelBookCommand(HotelBookCommand another) {
			super();
			this.orderid = another.orderid;
			this.correlationid = another.correlationid;
			this.userid = another.userid;
			this.password = another.password;
			this.username = another.username;
			this.payBy = another.payBy;
			this.appkey = another.appkey;
			this.bookingCode = another.bookingCode;
			this.searchKey = another.searchKey;
			this.hotelCode = another.hotelCode;
			this.numberOfUnits = another.numberOfUnits;
			this.apiProvider = another.apiProvider;
			this.comments = another.comments;
			this.profiles = another.profiles;
			this.comments = another.comments;
			this.bookingSystemType = another.bookingSystemType;
	}

	public void setRoomRateTypes(List<RoomRateType> roomRateTypes) {
		this.roomRateTypes = roomRateTypes;
	}

	public HotelBookCommand() {
			super();
			// TODO Auto-generated constructor stub
		}

	public String getCorrelationid() {
		return correlationid;
	}
	public void setCorrelationid(String correlationid) {
		this.correlationid = correlationid;
	}

	public HotelBookCommand(String orderid, String correlationid, String userid, String password, String payBy, long searchKey, String appkey,
			String hotelCode, int apiProvider,List<String> comments,
			List<RoomRateType> roomRateTypes, List<Profile> profiles) {
		super();
		this.orderid = orderid;
		this.correlationid = correlationid;
		this.userid = "1";
		this.username = "lintasAdmin";
		this.password = password;
		this.payBy = payBy;
		this.searchKey = searchKey;
		this.appkey = appkey;
		this.hotelCode = hotelCode;
		this.apiProvider = apiProvider;
		this.comments = comments;
		this.roomRateTypes = roomRateTypes;
		this.profiles = profiles;
		this.bookingCode = "dfdfdfds";
		this.bookingSystemType = "ibe";
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

	public boolean isQuotation() {
		return isQuotation;
	}
	public void setQuotation(boolean isQuotation) {
		this.isQuotation = isQuotation;
	}
	public int getQuotationId() {
		return quotationId;
	}
	public void setQuotationId(int quotationId) {
		this.quotationId = quotationId;
	}
	public HotelBookCommand(int isauto) {
		super();
		this.orderid = "";
		this.correlationid = "";
		this.transactionid = "";
		this.userid = "1";
		this.username = "lintasAdmin";
		this.password = "";
		this.searchKey = 28;
		this.hotelCode = "00007068";
		this.appkey = "eererewdvcv";
		this.apiProvider = 1;
		this.roomRateTypes = new ArrayList<RoomRateType>();
		this.roomRateTypes.add(new RoomRateType("RGL", "0000100924", "ddfdsfdf4434ddf", 1));
		this.profiles = new ArrayList<Profile>();
		profiles.add(new Profile(isauto));
		this.bookingCode = "dfdfdfds";
		this.bookingSystemType = "ibe";
	}


	public String getPaymentid() {
		return paymentid;
	}

	public void setPaymentid(String paymentid) {
		this.paymentid = paymentid;
	}

	public List<String> getPaymentIdHistory() {
		return paymentIdHistory;
	}

	public void setPaymentIdHistory(List<String> paymentIdHistory) {
		this.paymentIdHistory = paymentIdHistory;
	}

	public int getPayAttemptCount() {
		return payAttemptCount;
	}

	public void setPayAttemptCount(int payAttemptCount) {
		this.payAttemptCount = payAttemptCount;
	}

	protected String orderid;




	protected String paymentid;
	protected List<String> paymentIdHistory;
	protected int payAttemptCount;

	protected String correlationid;
	protected String transactionid;
	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	protected String userid;
	protected String username;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getBookingSystemType() {
		if(this.bookingSystemType == null)
			this.bookingSystemType = "ibe";
		return this.bookingSystemType;
	}
	public void setBookingSystemType(String bookingSystemType) {
		this.bookingSystemType = bookingSystemType;
	}
	/*private String pgResponseMessage;
	public String getPgResponseMessage() {
		return pgResponseMessage;
	}
	public void setPgResponseMessage(String pgResponseMessage) {
		this.pgResponseMessage = pgResponseMessage;
	}
	public String getPgResponseCode() {
		return pgResponseCode;
	}
	public void setPgResponseCode(String pgResponseCode) {
		this.pgResponseCode = pgResponseCode;
	}
	public String getPgTransactionId() {
		return pgTransactionId;
	}
	public void setPgTransactionId(String pgTransactionId) {
		this.pgTransactionId = pgTransactionId;
	}
	public String getPgpaymentStatus() {
		return pgpaymentStatus;
	}
	public void setPgpaymentStatus(String pgpaymentStatus) {
		this.pgpaymentStatus = pgpaymentStatus;
	}
	public String getPgAuthCode() {
		return pgAuthCode;
	}
	public void setPgAuthCode(String pgAuthCode) {
		this.pgAuthCode = pgAuthCode;
	}

	private String pgResponseCode;
	private String pgTransactionId;
	private String pgpaymentStatus;
	private String pgAuthCode;

*/
	public int getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(int numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}

	public String getBookingCode() {
		return bookingCode;
	}

	public void setBookingCode(String bookingCode) {
		this.bookingCode = bookingCode;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	private List<String> comments;
	private List<RoomRateType> roomRateTypes;
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

	/*
	public List<com.lintas.hotel.model.RatePlanType> getRatePlans(com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs) {
		return ratePlans;
	}
	public List<com.lintas.hotel.model.RoomTypeType> getRoomTypes(com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs) {
		return roomTypes;
	}
	public List<com.lintas.hotel.model.RoomStayType.RoomRates.RoomRate> getRoomRates(com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs) {
		return roomRates;
	}
	*/




	public static class RoomRateType implements Serializable{

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		@Override
		public String toString() {
			return "RoomRateType [roomTypeCode=" + roomTypeCode + ", ratePlanCode=" + ratePlanCode + ", numberOfUnits="
					+ numberOfUnits + "]";
		}
		/*@Override
		public String toString() {
			return "RoomRateType [roomTypeCode=" + roomTypeCode + ", ratePlanCode=" + ratePlanCode + ", bookingCode="
					+ bookingCode + ", numberOfUnits=" + numberOfUnits + "]";
		}*/
		public RoomRateType(String roomTypeCode, String ratePlanCode, String bookingCode, int numberOfUnits) {
			super();
			this.roomTypeCode = roomTypeCode;
			this.ratePlanCode = ratePlanCode;
			//this.bookingCode = bookingCode;
			this.numberOfUnits = numberOfUnits;
		}
		/*public String getBookingCode() {
			return bookingCode;
		}
		public void setBookingCode(String bookingCode) {
			this.bookingCode = bookingCode;
		}*/
		public String getRoomTypeCode() {
			return roomTypeCode;
		}
		public void setRoomTypeCode(String roomTypeCode) {
			this.roomTypeCode = roomTypeCode;
		}
		public String getRatePlanCode() {
			return ratePlanCode;
		}
		public void setRatePlanCode(String ratePlanCode) {
			this.ratePlanCode = ratePlanCode;
		}
		public int getNumberOfUnits() {
			return numberOfUnits;
		}
		public void setNumberOfUnits(int numberOfUnits) {
			this.numberOfUnits = numberOfUnits;
		}
		public RoomRateType() {
			super();
			// TODO Auto-generated constructor stub
		}

		private String roomTypeCode;
		private String ratePlanCode;
		//private String bookingCode;
		private int numberOfUnits;//always one.....
		//two same rooms will be added with same data twice..
	}




	public static class Profile implements Serializable
	{

		@Override
		public String toString() {
			return "Profile [customer=" + customer + ", profileType=" + profileType + ", id=" + id + ", resGuestRPH="
					+ resGuestRPH + ", isAdult=" + isAdult + "]";
		}
		public Profile(Customer customer, String profileType, String id, String resGuestRPH, boolean isAdult) {
			super();
			this.customer = customer;
			this.profileType = profileType;
			this.id = id;
			this.resGuestRPH = resGuestRPH;
			this.isAdult = isAdult;
		}
		public Profile() {
			super();
			// TODO Auto-generated constructor stub
		}
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public Profile(int isauto) {
			super();
			this.customer = new Customer(isauto);
			this.profileType = "1";
			this.resGuestRPH = "0";
			this.isAdult = true;
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
		public String getResGuestRPH() {
			return resGuestRPH;
		}
		public void setResGuestRPH(String resGuestRPH) {
			this.resGuestRPH = resGuestRPH;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public boolean isAdult() {
			return isAdult;
		}
		public void setAdult(boolean isAdult) {
			this.isAdult = isAdult;
		}
		private Customer customer;
		private String profileType;
		private String id;
		private String resGuestRPH;
		private boolean isAdult;


	}
	public static class Customer implements Serializable
	{


		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		@Override
		public String toString() {
			return "Customer [personName=" + personName + ", telephone=" + telephone + ", email=" + email + ", address="
					+ address + ", age=" + age + ", dob=" + dob + ", gender=" + gender + "]";
		}
		public Customer(PersonName personName, List<Telephone> telephone, String email, Address address, String age,
				String dob, String gender) {
			super();
			this.personName = personName;
			this.telephone = telephone;
			this.email = email;
			this.address = address;
			this.age = age;
			this.dob = dob;
			this.gender = gender;
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
		 public String getAge() {
				return age;
			}

			public void setAge(String age) {
				this.age = age;
			}

			public String getDob() {
				return dob;
			}

			public void setDob(String dob) {
				this.dob = dob;
			}

			public String getGender() {
				return gender;
			}

			public void setGender(String gender) {
				this.gender = gender;
			}

			public Customer() {
				super();
				// TODO Auto-generated constructor stub
			}
		private PersonName personName;
	    private List<Telephone> telephone;
	    private String email;
	    private Address address;

	    private String age;
	    private String dob;
	    private String gender;


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
		
		
		public PersonName(String paxId, String middleName, String namePrefix, String givenName, String surname) {
			super();
			this.paxId = paxId;
			this.middleName = middleName;
			this.namePrefix = namePrefix;
			this.givenName = givenName;
			this.surname = surname;
		}
		public PersonName(int isauto) {
			super();
			this.paxId = "Paxid8798685";
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
		private String paxId;
		private String middleName;
	    private String namePrefix;
	    private String givenName;
	    private String surname;
		public String getPaxId() {
			return paxId;
		}
		public void setPaxId(String paxId) {
			this.paxId = paxId;
		}
		@Override
		public String toString() {
			return "PersonName [paxId=" + paxId + ", middleName=" + middleName + ", namePrefix=" + namePrefix
					+ ", givenName=" + givenName + ", surname=" + surname + "]";
		}

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


	public static List<Profile> getRoomGuestProfiles(HotelBookCommand hbc, int roomindex)
	{

		List<Profile> profilelist = new ArrayList<Profile>();
		for (Profile p : hbc.getProfiles()) {
			if(p.getResGuestRPH().equals(String.valueOf(roomindex)))
			{
				profilelist.add(p);
			}
		}
		return profilelist;
	}
	public static HotelBookCommand  initRoomGuestProfilesUniques(HotelBookCommand hbc, HotelIdFactoryImpl hotelIdFactory)
	{

		List<Profile> profilelist = new ArrayList<Profile>();
		for (Profile p : hbc.getProfiles()) {
			String uniqueid = hotelIdFactory.createShortId("Cus");
			p.setId(uniqueid);
			profilelist.add(p);

		}
		hbc.setProfiles(profilelist);
		return hbc;
	}
	public int getEmulateByCompanyId() {
		return emulateByCompanyId;
	}
	public String getEmulateByUserId() {
		return emulateByUserId;
	}
	public boolean isEmulateFlag() {
		return isEmulateFlag;
	}
	public List<RoomRateType> getRoomRateTypes() {
		return roomRateTypes;
	}
	public void setEmulateByCompanyId(int emulateByCompanyId) {
		this.emulateByCompanyId = emulateByCompanyId;
	}
	public void setEmulateByUserId(String emulateByUserId) {
		this.emulateByUserId = emulateByUserId;
	}
	public void setEmulateFlag(boolean isEmulateFlag) {
		this.isEmulateFlag = isEmulateFlag;
	}
	public Boolean getIsRmDetails() {
		return isRmDetails;
	}
	public void setIsRmDetails(Boolean isRmDetails) {
		this.isRmDetails = isRmDetails;
	}
	public List<RmConfigTripDetailsModel> getRmDataListDetails() {
		return rmDataListDetails;
	}
	public void setRmDataListDetails(List<RmConfigTripDetailsModel> rmDataListDetails) {
		this.rmDataListDetails = rmDataListDetails;
	}


}
