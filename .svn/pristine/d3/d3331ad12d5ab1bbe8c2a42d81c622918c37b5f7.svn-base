package com.tayyarah.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.ProtocolException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.email.dao.AllEmailDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.email.dao.EmailDaoImp;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.hotel.dao.HotelBookingDao;
import com.tayyarah.hotel.dao.HotelDetailsDAO;
import com.tayyarah.hotel.dao.HotelOrderDao;
import com.tayyarah.hotel.dao.HotelSearchDao;
import com.tayyarah.hotel.dao.HotelTransactionDao;
import com.tayyarah.hotel.entity.HotelDetails;
import com.tayyarah.hotel.entity.HotelRoomDetails;
import com.tayyarah.hotel.entity.TboCity;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.AddressType;
import com.tayyarah.hotel.model.AmountDeterminationType;
import com.tayyarah.hotel.model.BasicPropertyInfoType;
import com.tayyarah.hotel.model.BasicPropertyInfoType.ContactNumbers.ContactNumber;
import com.tayyarah.hotel.model.BasicPropertyInfoType.Position;
import com.tayyarah.hotel.model.CancelPenaltiesType;
import com.tayyarah.hotel.model.CancelPenaltyType;
import com.tayyarah.hotel.model.CountryNameType;
import com.tayyarah.hotel.model.Facility;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelReservationIDsType;
import com.tayyarah.hotel.model.HotelReservationsType;
import com.tayyarah.hotel.model.HotelReservationsType.HotelReservation;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.Hotelroomdescription;
import com.tayyarah.hotel.model.HotelsInfo;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.OTAHotelResRS;
import com.tayyarah.hotel.model.RatePlanType;
import com.tayyarah.hotel.model.RateType;
import com.tayyarah.hotel.model.RateType.Rate;
import com.tayyarah.hotel.model.ResGlobalInfoType;
import com.tayyarah.hotel.model.RoomCombination;
import com.tayyarah.hotel.model.RoomCombinations;
import com.tayyarah.hotel.model.RoomStayType;
import com.tayyarah.hotel.model.RoomStayType.RatePlans;
import com.tayyarah.hotel.model.RoomStayType.RoomRates;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate.GuestCounts;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate.GuestCounts.GuestCount;
import com.tayyarah.hotel.model.RoomStayType.RoomTypes;
import com.tayyarah.hotel.model.RoomTypeType;
import com.tayyarah.hotel.model.RoomTypeType.Occupancy;
import com.tayyarah.hotel.model.TPAExtensions;
import com.tayyarah.hotel.model.TPAExtensions.RoomType;
import com.tayyarah.hotel.reposit.entity.LintasConstantFactory;
import com.tayyarah.hotel.model.TaxType;
import com.tayyarah.hotel.model.TaxesType;
import com.tayyarah.hotel.model.TotalType;
import com.tayyarah.hotel.model.UniqueIDType;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.CurrencyManager;
import com.tayyarah.hotel.util.HotelIdFactoryImpl;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.RoomDetailConstantFactory;
import com.tayyarah.hotel.util.TayyarahRepositConstantFactory;
import com.tayyarah.hotel.util.api.concurrency.AsyncSupport;
import com.tayyarah.user.dao.UserWalletDAO;



public class HotelRepositService {
	@Autowired
	HotelObjectTransformer hotelObjectTransformer;
	@Autowired
	HotelTransactionDao hotelTransactionDao;
	@Autowired
	HotelSearchDao hotelSearchDao;
	@Autowired
	HotelOrderDao hotelOrderDao;
	@Autowired
	AsyncSupport asyncSupport;
	@Autowired
	FlightBookingDao FBDAO;
	@Autowired
	CompanyDao CDAO;
	@Autowired
	UserWalletDAO AWDAO;
	@Autowired
	HotelBookingDao hotelBookingDao;
	@Autowired
	AllEmailDao allEmailDao;
	@Autowired
	EmailDao emailDao;
	@Autowired
	HotelDetailsDAO hotelDetailsDAO;
	@Autowired
	CurrencyManager currencyManager;
	@Autowired
	EmailDaoImp emaildao;	
	private APIHotelBook apiHotelBook;	
	public static final Logger logger = Logger.getLogger(HotelRepositService.class);

	public APIHotelBook preBook(APIHotelBook apiHotelBook, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay roomStay, HotelIdFactoryImpl hotelIdFactory) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{	
		HashMap<Long,Integer> roomIdMap = new HashMap<Long,Integer>();
		for (RoomRate roomRate : roomStay.getRoomRates().getRoomRates()) {
			Long roomIndex = Long.valueOf(roomRate.getRoomID());
			Integer roomCount = (roomIdMap.get(roomIndex) == null)?0:roomIdMap.get(roomIndex);
			roomCount += 1;
			roomIdMap.put(roomIndex, roomCount);
		}
		if(hotelDetailsDAO.canBookRooms(roomIdMap))
		{
			apiHotelBook = convertTbotoNativePreBookResponse(apiHotelBook, hbc, roomStay, hotelIdFactory);
		}
		else
		{
			OTAHotelResRS totaHotelResRS = new OTAHotelResRS();	
			APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Prebooking :: offline rooms are no more available , select some other room..." );			
			status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Prebook Error - Offline " ); 
			totaHotelResRS.setStatus(status);
			apiHotelBook.setStatus(status);
			apiHotelBook.setPreBookRes(totaHotelResRS);
		}
		return apiHotelBook;
	}

	public APIHotelBook doBook(APIHotelBook apiHotelBook, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay roomStay, HotelIdFactoryImpl hotelIdFactory) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		HashMap<Long,Integer> roomIdMap = new HashMap<Long,Integer>();
		for (RoomRate roomRate : roomStay.getRoomRates().getRoomRates()) {
			Long roomIndex = Long.valueOf(roomRate.getRoomID());
			Integer roomCount = (roomIdMap.get(roomIndex) == null)?0:roomIdMap.get(roomIndex);
			roomCount += 1;
			roomIdMap.put(roomIndex, roomCount);
		}
		if(hotelDetailsDAO.bookRooms(roomIdMap))
		{
			apiHotelBook = convertTbotoNativeBookResponse(apiHotelBook, hbc, roomStay, hotelIdFactory);
		}
		else
		{
			OTAHotelResRS totaHotelResRS = new OTAHotelResRS();	
			APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Booking :: offline rooms are no more available , select some other room..." );			
			status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Book Error - Offline" ); 
			totaHotelResRS.setStatus(status);
			apiHotelBook.setStatus(status);
			apiHotelBook.setBookRes(totaHotelResRS);
		}
		return apiHotelBook;
	}



	public APIHotelMap searchHotels(String cityCode, String companyUserId, HotelSearchCommand hsc, HotelApiCredentials api)
	{
		TreeMap<String, RoomStay> roomStaysMap = new TreeMap<String, RoomStay>();		
		APIHotelMap apiHotelMap = new APIHotelMap();
		apiHotelMap.setRoomStays(roomStaysMap);
		HotelsInfo thotelsinfo =  new HotelsInfo();
		List<HotelDetails> hotelDetailList;
		try {
			hotelDetailList = hotelDetailsDAO.getHotelDetails(cityCode, companyUserId);
			if(hotelDetailList == null || hotelDetailList.size()== 0)
				return apiHotelMap;		

			for (HotelDetails hotel : hotelDetailList) {
				RoomStay troomstay = new RoomStay();
				OTAHotelAvailRS.RoomStays.RoomStay trs = convertTayyarahReposittoNative(api, hsc, hotel);			
				roomStaysMap.put(trs.getBasicPropertyInfo().getHotelCode(), trs);
			}
			apiHotelMap.setRoomStays(roomStaysMap);
		} catch (HibernateException e) {
			roomStaysMap = new TreeMap<String, RoomStay>();		
			apiHotelMap = new APIHotelMap();
			apiHotelMap.setRoomStays(roomStaysMap);
			e.printStackTrace();
		} catch (IOException e) {
			roomStaysMap = new TreeMap<String, RoomStay>();		
			apiHotelMap = new APIHotelMap();
			apiHotelMap.setRoomStays(roomStaysMap);
			e.printStackTrace();
		} catch (Exception e) {
			roomStaysMap = new TreeMap<String, RoomStay>();		
			apiHotelMap = new APIHotelMap();
			apiHotelMap.setRoomStays(roomStaysMap);
			e.printStackTrace();
		}
		return apiHotelMap;


	}
	public RoomStay convertTayyarahReposittoNative(HotelApiCredentials api, HotelSearchCommand hsc, HotelDetails hotel) throws HibernateException, ClassNotFoundException, JAXBException, ParseException 
	{	
		BigDecimal minbase = new BigDecimal(0);
		int noofdays = CommonUtil.getNoofStayDays(hsc);
		RoomStay troomstay = new RoomStay();
		int noofrooms = hsc.getNoofrooms();		
		RoomTypes troomtypes = new RoomTypes();		
		List<RoomTypeType> lrtlist = new ArrayList<RoomTypeType>();	
		RatePlans trateplans = new RatePlans();
		List<RatePlanType> lplantlist = new ArrayList<RatePlanType>();
		RoomRates troomrates = new RoomRates();			
		List<RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<RoomStayType.RoomRates.RoomRate>();



		int roomreqindexmax  = 0; 
		Integer roomindex = 0;		
		List<RoomCombination> roomCombinationList = new ArrayList<RoomCombination>();
		logger.info("object transformation---: no of rooms : "+((hotel.getRooms()!=null) ? hotel.getRooms().size():0));
		for(int i = 0; i<noofrooms ; i++)
		{
			List<Integer> roomindexlist = new ArrayList<Integer>();	
			for (HotelRoomDetails hotelRoomDetails : hotel.getRooms()) {
				//roomindex++;

				Integer noofunits = Integer.valueOf(hotelRoomDetails.getAvailability());
				logger.info("object transformation---: room name : "+hotelRoomDetails.getName());
				roomindex += 1;
				roomindexlist.add(roomindex);
				String bookingkey = HotelIdFactoryImpl.getInstance().createLongId("TYREP");
				RoomTypeType lrt = new RoomTypeType();
				lrt.setFloor(TayyarahRepositConstantFactory.DEFAULT_FLOOR_NUMBER);
				lrt.setNonSmoking(TayyarahRepositConstantFactory.DEFAULT_IS_SMOKING);
				lrt.setRoomType(TayyarahRepositConstantFactory.DEFAULT_RATEPLAN_TYPE);
				lrt.setRoomTypeCode(hotelRoomDetails.getName());			
				List<Facility> facilitiesRoom = new ArrayList<Facility>();			
				lrt.setAmenities(facilitiesRoom);
				List<Occupancy> locclist = new ArrayList<RoomTypeType.Occupancy>();

				Hotelroomdescription lroomdes = new Hotelroomdescription();					
				lrt.setRoomType(hotelRoomDetails.getName());
				lrt.setRoomTypeCode(String.valueOf(hotelRoomDetails.getId()));	
				lrt.setNumberOfUnits(noofunits);					
				lroomdes.setId(1);
				lroomdes.setImagePath(TayyarahRepositConstantFactory.DEFAULT_ROOM_IMAGE_PATH);	
				lroomdes.setDescription(hotelRoomDetails.getDescription());						
				lrt.setRoomDescription(lroomdes);
				lrt.setTermsAndConditions(TayyarahRepositConstantFactory.DEFAULT_TERMSANDCONDITIONS);

				Occupancy locc = new Occupancy();
				locc.setAgeQualifyingCode(TayyarahRepositConstantFactory.AGE_QULAIFYING_CODE_ADULT);
				locc.setMaxOccupancy(hotelRoomDetails.getAdults());				
				locclist.add(locc);
				lrt.setOccupancies(locclist);
				TPAExtensions lext = new TPAExtensions();
				RoomType lextroomtype = new RoomType();			
				lextroomtype.setMaxAdult(new BigInteger(String.valueOf(hotelRoomDetails.getAdults())));
				lextroomtype.setMaxChild(new BigInteger(String.valueOf(hotelRoomDetails.getChilds())));
				lextroomtype.setMaxChildAge(new BigInteger(String.valueOf(8)));
				lextroomtype.setMaxGuest(new BigInteger(String.valueOf(hotelRoomDetails.getAdults())));
				lextroomtype.setMaxInfant(new BigInteger(String.valueOf(hotelRoomDetails.getInfants())));
				lextroomtype.setMinChildAge(new BigInteger(String.valueOf(2)));
				lextroomtype.setSmoking(String.valueOf(TayyarahRepositConstantFactory.DEFAULT_IS_SMOKING));			
				lext.setRoomType(lextroomtype);
				lrt.setTPAExtensions(lext);	
				lrtlist.add(lrt);


				RatePlanType lrp = new RatePlanType();
				lrp.setRatePlanType(hotelRoomDetails.getName());
				lrp.setRatePlanCode(String.valueOf(hotelRoomDetails.getId()));
				lrp.setRatePlanName(TayyarahRepositConstantFactory.DEFAULT_RATEPLAN_NAME);
				CancelPenaltiesType cancelpenalities = new CancelPenaltiesType();			
				List<CancelPenaltyType> cancellist = new ArrayList<CancelPenaltyType>();
				cancelpenalities.setCancelPenalties(cancellist);
				lrp.setCancelPenalties(cancelpenalities);			
				lplantlist.add(lrp);

				BigDecimal currentbase = hotelRoomDetails.getBasePrice();
				if(minbase.compareTo(new BigDecimal("0") ) == 0)
					minbase = currentbase;
				else if(currentbase.compareTo(minbase) == -1)
					minbase = currentbase;


				RoomStayType.RoomRates.RoomRate lrr = new RoomStayType.RoomRates.RoomRate();
				lrr.setRatePlanCode(String.valueOf(hotelRoomDetails.getId()));	
				lrr.setBookingCode(bookingkey);
				lrr.setRoomID(String.valueOf(hotelRoomDetails.getId()));
				lrr.setRoomTypeCode(String.valueOf(hotelRoomDetails.getId()));

				RateType lrates = new RateType();
				List<Rate> lratelist = new ArrayList<Rate>();


				Rate lrate = new Rate();
				lrate.setRoomIndex(roomindex);	
				lrate.setName(hotelRoomDetails.getName());
				lrate.setAdults(hotelRoomDetails.getAdults());
				lrate.setChildrenages("");
				lrate.setChildren(hotelRoomDetails.getChilds());
				//Total base
				TotalType ltot = new TotalType();
				ltot.setAmountBeforeTax(hotelRoomDetails.getBasePrice());

				BigDecimal amountaftertax = hotelRoomDetails.getBasePrice().add(hotelRoomDetails.getTaxPrice());				
				ltot.setAmountAfterTax(amountaftertax);
				//logger.info("object transformation---: tax parsing------ ");
				TaxesType ltax = new TaxesType();			
				List<TaxType> ltaxlist  = new ArrayList<TaxType>();
				TaxType lrtt = new TaxType();
				lrtt.setAmount(hotelRoomDetails.getTaxPrice());
				lrtt.setCode("");
				lrtt.setType(AmountDeterminationType.EXCLUSIVE);
				ltaxlist.add(lrtt);
				ltax.setAmount(hotelRoomDetails.getTaxPrice());
				ltax.setTaxes(ltaxlist);
				TaxesType ltaxmarkup = ltax;
				ltot.setTaxes(ltax);	

				//Single day base
				TotalType ltotperday = new TotalType();
				ltotperday.setAmountBeforeTax(hotelRoomDetails.getBasePrice());
				ltotperday.setAmountAfterTax(amountaftertax);
				//logger.info("object transformation---: tax parsing------ ");
				TaxesType ltaxperday = new TaxesType();			
				List<TaxType> ltaxlistperday  = new ArrayList<TaxType>();
				TaxType lrttperday = new TaxType();
				lrttperday.setAmount(hotelRoomDetails.getTaxPrice());
				lrttperday.setCode("");
				lrttperday.setType(AmountDeterminationType.EXCLUSIVE);
				ltaxlistperday.add(lrttperday);
				ltaxperday.setAmount(hotelRoomDetails.getTaxPrice());
				ltaxperday.setTaxes(ltaxlistperday);				
				ltotperday.setTaxes(ltaxperday);

				ltotperday.setTotalAmountPayable(amountaftertax.multiply(new BigDecimal(noofdays)));				

				TotalType ltotsinglebase = new TotalType();
				TotalType ltotsinglebasewithoutmarkup = new TotalType();
				TotalType ltotsinglebooking = new TotalType();				
				BeanUtils.copyProperties(ltotperday, ltotsinglebase);
				BeanUtils.copyProperties(ltotperday, ltotsinglebasewithoutmarkup);
				BeanUtils.copyProperties(ltotperday, ltotsinglebooking);				
				lrate.setApiPrice(ltotperday);
				lrate.setBase(ltotsinglebase);			
				lrate.setBaseWithoutMarkUp(ltotsinglebasewithoutmarkup);
				lrate.setBookingPrice(ltotsinglebooking);

				logger.info("\\\\\\\\*************************roomdetail -lrate.getBase()-"+lrate.getBase());	
				logger.info("\\\\\\\\*************************roomdetail -lrate.getBaseWithoutMarkUp()-"+lrate.getBaseWithoutMarkUp());	
				logger.info("\\\\\\\\*************************roomdetail -lrate.getApiPrice()-"+lrate.getApiPrice());	


				lratelist.add(lrate);			

				lrates.setRates(lratelist);
				GuestCounts gustcounts = new GuestCounts();
				List<GuestCount> gustcountslist = new ArrayList<GuestCount>();				
				gustcounts.setGuestCounts(gustcountslist);
				lrr.setGuestCounts(gustcounts);

				lrr.setRates(lrates);
				lrratelist.add(lrr);
			}

			RoomCombination roomCombination = new RoomCombination();
			roomCombination.setRoomIndex(roomindexlist);
			roomCombinationList.add(roomCombination);

		}
		troomtypes.setRoomTypes(lrtlist);
		troomstay.setRoomTypes(troomtypes);		
		trateplans.setRatePlen(lplantlist);
		troomstay.setRatePlans(trateplans);		

		RoomCombinations roomCombinations = new RoomCombinations(); 
		roomCombinations.setInfoSource("Open Combination");
		roomCombinations.setRoomCombination(roomCombinationList);
		roomCombinations.setApiProvider(HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL);
		List<RoomCombinations> supplierRoomGroups = new ArrayList<RoomCombinations>();
		supplierRoomGroups.add(roomCombinations);
		troomstay.setSupplierRoomGroups(supplierRoomGroups);
		troomrates.setRoomRates(lrratelist);
		troomstay.setRoomRates(troomrates);
		BasicPropertyInfoType tbasic = new BasicPropertyInfoType();	

		tbasic.setApiVendorID(hotel.getHotelCode());
		tbasic.setHotelCode(HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL+"-"+hotel.getHotelCode());
		tbasic.setHotelName(hotel.getName());
		tbasic.setImageurl(TayyarahRepositConstantFactory.DEFAULT_HOTEL_IMAGE_PATH);
		Position hpos = new Position();
		hpos.setLongitude((hotel.getLongitude() == null)?null:String.valueOf(hotel.getLongitude()));
		hpos.setLatitude((hotel.getLatitude() == null)?null:String.valueOf(hotel.getLatitude()));				
		tbasic.setPosition(hpos);
		AddressType laddress = new AddressType();
		String city = "";
		String state = "";
		String countryName = "";
		String countryCode = "";
		StringBuffer address = new StringBuffer();


		if(hsc.getSearchCity().getTboCity()!=null)
		{
			TboCity tboCity = hsc.getSearchCity().getTboCity();
			city = (tboCity.getDestination() != null && tboCity.getDestination().trim().length()>1)?tboCity.getDestination()+",":"";
			state = (tboCity.getStateprovince() != null && tboCity.getStateprovince().trim().length()>1)?tboCity.getStateprovince()+",":"";
			countryName = (tboCity.getCountry() != null && tboCity.getCountry().trim().length()>1)?tboCity.getCountry():"";
			countryCode = (tboCity.getCountrycode() != null && tboCity.getCountrycode().trim().length()>1)?tboCity.getCountrycode():"";										

			address.append((tboCity.getDestination() != null && tboCity.getDestination().trim().length()>1)?tboCity.getDestination()+",":"");
			address.append((tboCity.getStateprovince() != null && tboCity.getStateprovince().trim().length()>1)?tboCity.getStateprovince()+",":"");
			address.append((tboCity.getCountry() != null && tboCity.getCountry().trim().length()>1)?tboCity.getCountry()+((tboCity.getCountrycode() != null && tboCity.getCountrycode().trim().length()>1)?"("+tboCity.getCountrycode()+")":""):"");										

		}
		else
		{
			city = ((hsc.getSearchCity().getCity() != null && hsc.getSearchCity().getCity().trim().length()>1)?hsc.getSearchCity().getCity()+",":"");										
			state =  ((hsc.getSearchCity().getState() != null && hsc.getSearchCity().getState().trim().length()>1)?hsc.getSearchCity().getState()+",":"");
			countryName = "India";
			countryCode = "IN";

			address.append((hsc.getSearchCity().getCity() != null && hsc.getSearchCity().getCity().trim().length()>1)?hsc.getSearchCity().getCity()+",":"");										
			address.append((hsc.getSearchCity().getState() != null && hsc.getSearchCity().getState().trim().length()>1)?hsc.getSearchCity().getState()+",":"");
			address.append((hsc.getSearchCity().getTgCity() != null)?"India(IN)":(hsc.getSearchCity().getCountryCode() != null && hsc.getSearchCity().getCountryCode().trim().length()>1)?hsc.getSearchCity().getCountryCode():"");

		}

		laddress.setCityName(city);
		CountryNameType cn = new CountryNameType();
		cn.setCode(countryCode);
		cn.setValue(countryName);
		laddress.setCountryName(cn);
		List<String> addresslines = new ArrayList<String>();
		addresslines.add(address.toString());		
		laddress.setAddressLines(addresslines);

		List<ContactNumber> contactnos = new ArrayList<ContactNumber>();
		contactnos.add(new ContactNumber("80425555555"));

		tbasic.setAddress(laddress);
		tbasic.setContactNumbers(contactnos);
		tbasic.setReviewCount("2");
		tbasic.setReviewRating("3");
		//tbasic.setArea(ho.getArea());
		//tbasic.setArea_Seo_Id(ho.getArea_Seo_Id());
		//tbasic.setDefaultCheckInTime(ho.getDefaultCheckInTime());
		//tbasic.setDefaultCheckOutTime(ho.getDefaultCheckOutTime());
		tbasic.setHotel_Star(3);
		tbasic.setHotelClass("");
		tbasic.setWeekdayRank(3);
		tbasic.setWeekendRank(3);
		HashMap<Integer, String> apiProviderMap = new HashMap<Integer, String>();
		apiProviderMap.put(HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL, hotel.getHotelCode());
		tbasic.setApiProviderMap(apiProviderMap);


		tbasic.setApiPrice(minbase);
		tbasic.setBasePrice(minbase);
		tbasic.setBasePriceWithoutMarkup(minbase);
		tbasic.setBookingPrice(minbase);
		tbasic.setIsOfflineBooking(true);
		tbasic.setApiProvider(HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL);
		List<String> hotelimages = new ArrayList<String>();
		hotelimages.add(TayyarahRepositConstantFactory.DEFAULT_HOTEL_IMAGE_PATH);
		tbasic.setHotelimages(hotelimages);
		List<Facility> hotelAmenities = new ArrayList<Facility>();		
		Facility facility = new Facility();
		facility.setDescription("Television");
		facility.setAmenityType(LintasConstantFactory.FACILITY_TYPE);
		hotelAmenities.add(facility);
		tbasic.setHotelAmenities(hotelAmenities);
		troomstay.setBasicPropertyInfo(tbasic);

		return troomstay;
	}

	public APIHotelBook convertTbotoNativePreBookResponse(APIHotelBook apiHotelBook, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay roomStay, HotelIdFactoryImpl hotelIdFactory) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{
		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();	
		APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Prebooking ..." );		
		if(apiHotelBook.getPreBookRes() != null)
		{
			totaHotelResRS = apiHotelBook.getPreBookRes();
			status = totaHotelResRS.getStatus();
		}
		apiHotelBook.setRoomsummary(roomStay);
		apiHotelBook.initRate(roomStay, CommonUtil.getNoofStayDays(apiHotelBook.getSearch()),apiHotelBook.getSearch().getNoofrooms());
		status = new APIStatus(APIStatus.STATUS_CODE_SUCCESS, "Rooms has been blocked.." );

		totaHotelResRS.setCorrelationID(apiHotelBook.getBook().getCorrelationid());
		totaHotelResRS.setTransactionIdentifier(apiHotelBook.getBook().getOrderid());
		HotelReservationsType hotelReservationsType = new HotelReservationsType();
		List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
		HotelReservation thotelReservation = new HotelReservation();


		List<UniqueIDType> uniqueIDTypeList = new ArrayList<UniqueIDType>();
		UniqueIDType uniqueIDType = new UniqueIDType();
		uniqueIDType.setType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
		String blockid = HotelIdFactoryImpl.getInstance().createShortId("bl");
		String bookingcode = HotelIdFactoryImpl.getInstance().createShortId("bc");

		uniqueIDType.setID(blockid);
		totaHotelResRS.setBookingCode(bookingcode);
		//uniqueIDType.setCompanyName(uniqueid.getCompanyName());
		uniqueIDTypeList.add(uniqueIDType);
		thotelReservation.setUniqueIDs(uniqueIDTypeList);			

		ResGlobalInfoType tresGlobalinfo = new ResGlobalInfoType();
		HotelReservationIDsType thotelresIds = new HotelReservationIDsType();
		List<HotelReservationIDsType.HotelReservationID> thotelresidlist = new ArrayList<HotelReservationIDsType.HotelReservationID>();

		RoomStayType.RoomRates.RoomRate roomrate = roomStay.getRoomRates().getRoomRates().get(0);
		if(roomrate.getRates().getRates().size()>0)
		{
			for (Rate rate : roomrate.getRates().getRates()) {
				HotelReservationIDsType.HotelReservationID thotelresid = new HotelReservationIDsType.HotelReservationID();
				thotelresid.setResIDType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
				thotelresid.setResIDValue(bookingcode);//XHUB891565bookingId
				thotelresid.setResIDSourceContext("");
				thotelresid.setForGuest(true);
				thotelresidlist.add(thotelresid);
			}
		}
		else
		{
			//Add atleast one unique book id
			HotelReservationIDsType.HotelReservationID thotelresid = new HotelReservationIDsType.HotelReservationID();
			thotelresid.setResIDType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
			thotelresid.setResIDValue(bookingcode);//XHUB891565bookingId
			thotelresid.setResIDSourceContext("");
			thotelresid.setForGuest(true);
			thotelresidlist.add(thotelresid);
		}

		thotelresIds.setHotelReservationIDs(thotelresidlist);
		tresGlobalinfo.setHotelReservationIDs(thotelresIds);
		thotelReservation.setResGlobalInfo(tresGlobalinfo);
		hotelReservationslist.add(thotelReservation);	
		hotelReservationsType.setHotelReservations(hotelReservationslist);
		totaHotelResRS.setHotelReservations(hotelReservationsType);	

		BigDecimal newtotalrate = apiHotelBook.getApiRate().getPayableAmt();
		logger.info("tbo blocking----"+newtotalrate);
		totaHotelResRS.setApiFinalPrice(newtotalrate);
		totaHotelResRS.setBaseFinalPrice(newtotalrate);
		totaHotelResRS.setBaseFinalPriceWithoutMarkup(newtotalrate);
		totaHotelResRS.setBookingFinalPrice(newtotalrate);

		totaHotelResRS.setStatus(status);
		apiHotelBook.setStatus(status);
		apiHotelBook.setPreBookRes(totaHotelResRS);

		return apiHotelBook;


	}
	public APIHotelBook convertTbotoNativeBookResponse(APIHotelBook apiHotelBook, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay roomStay, HotelIdFactoryImpl hotelIdFactory) throws UnsupportedOperationException,SOAPException,JAXBException,IOException, ProtocolException, NumberFormatException, Exception
	{		
		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();	
		APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, "Booking ..." );
		if(apiHotelBook.getBookRes() != null)
		{
			totaHotelResRS = apiHotelBook.getBookRes();
			status = totaHotelResRS.getStatus();
		}
		apiHotelBook.setRoomsummary(roomStay);
		apiHotelBook.initRate(roomStay, CommonUtil.getNoofStayDays(apiHotelBook.getSearch()),apiHotelBook.getSearch().getNoofrooms());
		status = new APIStatus(APIStatus.STATUS_CODE_SUCCESS, "successfully booked" );

		totaHotelResRS.setCorrelationID(apiHotelBook.getBook().getCorrelationid());
		totaHotelResRS.setTransactionIdentifier(apiHotelBook.getBook().getOrderid());
		HotelReservationsType hotelReservationsType = new HotelReservationsType();
		List<HotelReservation> hotelReservationslist = new ArrayList<HotelReservation>();
		HotelReservation thotelReservation = new HotelReservation();

		List<UniqueIDType> uniqueIDTypeList = new ArrayList<UniqueIDType>();
		UniqueIDType uniqueIDType = new UniqueIDType();
		uniqueIDType.setType(RoomDetailConstantFactory.BOOKING_ID_TYPE);

		String bookId = HotelIdFactoryImpl.getInstance().createShortId("b");
		String bookingcode = HotelIdFactoryImpl.getInstance().createShortId("bc");



		uniqueIDType.setApiBookingId(bookId);
		uniqueIDType.setApiBookingCode(bookingcode);
		uniqueIDType.setApiConfirmationNo(bookId);
		uniqueIDType.setID(bookId);

		totaHotelResRS.setBookingCode(bookingcode);
		uniqueIDTypeList.add(uniqueIDType);
		thotelReservation.setUniqueIDs(uniqueIDTypeList);			

		ResGlobalInfoType tresGlobalinfo = new ResGlobalInfoType();
		HotelReservationIDsType thotelresIds = new HotelReservationIDsType();
		List<HotelReservationIDsType.HotelReservationID> thotelresidlist = new ArrayList<HotelReservationIDsType.HotelReservationID>();
		//Add atleast one unique book id
		HotelReservationIDsType.HotelReservationID thotelresid = new HotelReservationIDsType.HotelReservationID();
		thotelresid.setResIDType(RoomDetailConstantFactory.BOOKING_ID_TYPE);
		thotelresid.setResIDValue(bookId);//XHUB891565bookingId
		thotelresid.setResIDSourceContext(bookId);
		thotelresid.setForGuest(true);
		thotelresidlist.add(thotelresid);

		thotelresIds.setHotelReservationIDs(thotelresidlist);
		tresGlobalinfo.setHotelReservationIDs(thotelresIds);
		thotelReservation.setResGlobalInfo(tresGlobalinfo);
		hotelReservationslist.add(thotelReservation);	
		hotelReservationsType.setHotelReservations(hotelReservationslist);
		totaHotelResRS.setHotelReservations(hotelReservationsType);	

		BigDecimal newtotalrate = apiHotelBook.getApiRate().getPayableAmt();
		logger.info("tbo blocking----"+newtotalrate);
		totaHotelResRS.setApiFinalPrice(newtotalrate);
		totaHotelResRS.setBaseFinalPrice(newtotalrate);
		totaHotelResRS.setBaseFinalPriceWithoutMarkup(newtotalrate);
		totaHotelResRS.setBookingFinalPrice(newtotalrate);

		totaHotelResRS.setStatus(status);
		apiHotelBook.setStatus(status);
		apiHotelBook.setBookRes(totaHotelResRS);

		return apiHotelBook;

	}


}
