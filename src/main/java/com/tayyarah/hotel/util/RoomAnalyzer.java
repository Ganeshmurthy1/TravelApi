package com.tayyarah.hotel.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.dao.MoneyExchangeDaoImp;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.email.dao.EmailDaoImp;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.hotel.dao.HotelBookingDao;
import com.tayyarah.hotel.dao.HotelOrderDao;
import com.tayyarah.hotel.dao.HotelSearchDao;
import com.tayyarah.hotel.dao.HotelTransactionDao;
import com.tayyarah.hotel.model.BasicPropertyInfoType;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.model.RatePlanType;
import com.tayyarah.hotel.model.RoomCombination;
import com.tayyarah.hotel.model.RoomCombinations;
import com.tayyarah.hotel.model.RoomStayType;
import com.tayyarah.hotel.model.RoomStayType.RoomRates;
import com.tayyarah.hotel.model.RoomStayType.RoomRates.RoomRate;
import com.tayyarah.hotel.model.RoomStayType.RoomTypes;
import com.tayyarah.hotel.model.RoomTypeType;
import com.tayyarah.hotel.util.api.concurrency.AsyncSupport;
import com.tayyarah.user.dao.UserWalletDAO;



public class RoomAnalyzer {
	public static final Logger logger = Logger.getLogger(RoomAnalyzer.class);


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
	HotelIdFactoryImpl hotelIdFactory;
	@Autowired
	EmailDaoImp emaildao;	
	@Autowired
	MoneyExchangeDaoImp moneydao;
	@Autowired
	CurrencyManager currencyManager;


	public static OTAHotelAvailRS.RoomStays.RoomStay mergeRooms(OTAHotelAvailRS.RoomStays.RoomStay rs, int apiId)
	{

		OTAHotelAvailRS.RoomStays.RoomStay rsFinal = null;
		if((rs != null && rs.getRoomRates() != null && rs.getRoomRates().getRoomRates() !=null && rs.getRoomRates().getRoomRates().size()>0))		
		{
			logger.info("########## ---TBO only ");			
			logger.info("##########---TBO only ");	

			rsFinal = rs;
			List<RoomCombinations> supplierRoomGroupsTbo = rs.getSupplierRoomGroups();
			rsFinal.setSupplierRoomGroups(supplierRoomGroupsTbo);	
			Integer roomIndex = new Integer(1);	
			RoomRates troomrates = rsFinal.getRoomRates() == null? new RoomRates(): rsFinal.getRoomRates();
			List<RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<RoomStayType.RoomRates.RoomRate>();				
			logger.info("########## object transformation---: RoomRates. size "+lrratelist.size());			
			for (RoomRate rr : rsFinal.getRoomRates().getRoomRates()) {
				RoomRate lrr = rr;	
				lrr.setRoomIndex(roomIndex);
				lrr.setApiProvider(apiId);
				roomIndex ++;
				lrratelist.add(lrr);							
			}
			troomrates.setRoomRates(lrratelist);
			rsFinal.setRoomRates(troomrates);		
			roomIndex = new Integer(0);
			List<RoomCombinations> roomGroupsTarget = new ArrayList<RoomCombinations>();
			RoomCombinations roomCombinations = new RoomCombinations(); 
			List<RoomCombination> roomCombinationList = new ArrayList<RoomCombination>();	
			if(rs.getSupplierRoomGroups() !=null && rs.getSupplierRoomGroups().get(0) !=null && rs.getSupplierRoomGroups().get(0).getRoomCombination()!=null)
			{
				roomCombinations.setInfoSource((rs.getSupplierRoomGroups().get(0)!=null)?rs.getSupplierRoomGroups().get(0).getInfoSource():"Fixed Combination");
				for (RoomCombination roomCombination : rs.getSupplierRoomGroups().get(0).getRoomCombination()) {
					RoomCombination roomCombinationTarget = new RoomCombination();

					List<Integer> roomIndexList = new ArrayList<Integer>();
					int buffer = 0;
					for (Integer roomCount : roomCombination.getRoomIndex()) {
						roomIndex = roomIndex + 1;
						roomIndexList.add(roomIndex);
						//buffer += 1;
					}			
					roomCombinationTarget.setRoomIndex(roomIndexList);			
					roomCombinationList.add(roomCombinationTarget);				
				}
			}
			roomCombinations.setRoomCombination(roomCombinationList);	
			roomCombinations.setApiProvider(apiId);
			roomGroupsTarget.add(roomCombinations);	
			rsFinal.setRoomGroups(roomGroupsTarget);	
		}
		return rsFinal;
	}

	/*public static com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay mergeRoomsOfSuppliersHotels(HashMap<Integer, List<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>> roomStayMap)
	{
		List<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay> rsApiCompleteListDesiya =  roomStayMap.get(HotelApiCredentials.API_DESIA_IND) == null ? new ArrayList<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>():roomStayMap.get(HotelApiCredentials.API_DESIA_IND); 
		List<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay> rsApiCompleteListTBO =  roomStayMap.get(HotelApiCredentials.API_TBO_INTERNATIONAL) == null ? new ArrayList<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>():roomStayMap.get(HotelApiCredentials.API_TBO_INTERNATIONAL); 
		for
		return mergeRoomsOfSuppliersHotel()
	}*/

	public static OTAHotelAvailRS.RoomStays.RoomStay mergeRoomsOfSuppliersHotel(HashMap<Integer, OTAHotelAvailRS.RoomStays.RoomStay> roomStayMap)
	{

		OTAHotelAvailRS.RoomStays.RoomStay rsFinal = null;
		OTAHotelAvailRS.RoomStays.RoomStay rsDesiya = roomStayMap.get(HotelApiCredentials.API_DESIA_IND);
		OTAHotelAvailRS.RoomStays.RoomStay rsTBO = roomStayMap.get(HotelApiCredentials.API_TBO_INTERNATIONAL);

		if((rsDesiya != null && rsDesiya.getRoomRates() != null && rsDesiya.getRoomRates().getRoomRates() !=null && rsDesiya.getRoomRates().getRoomRates().size()>0)
				&&
				(rsTBO != null && rsTBO.getRoomRates() != null && rsTBO.getRoomRates().getRoomRates() !=null && rsTBO.getRoomRates().getRoomRates().size()>0)	)
		{

			logger.info("########## ---Desiya and TBO merging ");			
			logger.info("##########---Desiya and TBO merging ");	
			rsFinal = rsDesiya;

			BasicPropertyInfoType tbasic = rsFinal.getBasicPropertyInfo();
			BasicPropertyInfoType tbasicTBO = rsTBO.getBasicPropertyInfo();

			if(tbasic != null && tbasicTBO != null)
			{
				tbasic.setApiEndUserIp(tbasicTBO.getApiEndUserIp());
				tbasic.setApiTokenId(tbasicTBO.getApiTokenId());
				tbasic.setApiTraceId(tbasicTBO.getApiTraceId());
				tbasic.setApiResultIndex(tbasicTBO.getApiResultIndex());				
				
				
				HashMap<Integer, BasicPropertyInfoType> apiProviderBasicMap = new HashMap<Integer, BasicPropertyInfoType>();			
				apiProviderBasicMap.put(HotelApiCredentials.API_DESIA_IND, tbasic);
				apiProviderBasicMap.put(HotelApiCredentials.API_TBO_INTERNATIONAL, tbasicTBO);
				tbasic.setApiProviderBasicMap(apiProviderBasicMap);
				
				HashMap<Integer, String> apiProviderMap = tbasic.getApiProviderMap();				
				HashMap<Integer, String> apiProviderMapTBO = tbasicTBO.getApiProviderMap();
				for (Entry<Integer, String> entry : apiProviderMapTBO.entrySet()) {
					apiProviderMap.put(entry.getKey(), entry.getValue());
				}				
				tbasic.setApiProviderMap(apiProviderMap);
				
				
				
			}






			List<RoomCombinations> supplierRoomGroupsTarget = rsDesiya.getSupplierRoomGroups();		
			List<RoomCombinations> supplierRoomGroupsTbo = rsTBO.getSupplierRoomGroups();
			supplierRoomGroupsTarget.addAll(supplierRoomGroupsTbo);
			rsFinal.setSupplierRoomGroups(supplierRoomGroupsTarget);	
			Integer roomIndex = new Integer(1);	
			RoomRates troomrates = rsFinal.getRoomRates() == null? new RoomRates(): rsFinal.getRoomRates();
			List<RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<RoomStayType.RoomRates.RoomRate>();				
			logger.info("########## object transformation---: RoomRates. size "+lrratelist.size());			
			for (RoomRate rr : rsFinal.getRoomRates().getRoomRates()) {
				RoomRate lrr = rr;	
				lrr.setRoomIndex(roomIndex);
				lrr.setApiProvider(HotelApiCredentials.API_DESIA_IND);
				roomIndex ++;
				lrratelist.add(lrr);							
			}
			troomrates.setRoomRates(lrratelist);
			rsFinal.setRoomRates(troomrates);		
			roomIndex = new Integer(0);
			List<RoomCombinations> roomGroupsTarget = new ArrayList<RoomCombinations>();
			RoomCombinations roomCombinations = new RoomCombinations(); 
			//desiya room indexes are so linear in structure..
			//which will be given 1-x where x equal no of rooms available * no of rooms requested. 
			roomCombinations.setInfoSource("FixedCombination");
			List<RoomCombination> roomCombinationList = new ArrayList<RoomCombination>();	
			if(rsDesiya.getSupplierRoomGroups() !=null && rsDesiya.getSupplierRoomGroups().get(0) !=null && rsDesiya.getSupplierRoomGroups().get(0).getRoomCombination()!=null)
			{
				for (RoomCombination roomCombination : rsDesiya.getSupplierRoomGroups().get(0).getRoomCombination()) {
					RoomCombination roomCombinationTarget = new RoomCombination();

					List<Integer> roomIndexList = new ArrayList<Integer>();
					int buffer = 0;
					for (Integer roomCount : roomCombination.getRoomIndex()) {
						roomIndex = roomIndex + 1;
						roomIndexList.add(roomIndex);
						//buffer += 1;
					}			
					roomCombinationTarget.setRoomIndex(roomIndexList);			
					roomCombinationList.add(roomCombinationTarget);				
				}	
			}
			roomCombinations.setRoomCombination(roomCombinationList);	
			roomCombinations.setApiProvider(HotelApiCredentials.API_DESIA_IND);
			roomGroupsTarget.add(roomCombinations);		

			rsFinal = fillRoomStay(rsTBO, rsFinal, roomIndex);		

			//roomIndex = (rsDesiya != null && rsDesiya.getRoomRates()!=null && rsDesiya.getRoomRates().getRoomRates()!=null) ?rsDesiya.getRoomRates().getRoomRates().size() :0;

			if(rsTBO.getSupplierRoomGroups() !=null && rsTBO.getSupplierRoomGroups().get(0) !=null && rsTBO.getSupplierRoomGroups().get(0).getRoomCombination()!=null)
			{
				roomCombinations = new RoomCombinations();
				roomCombinations.setInfoSource((rsTBO.getSupplierRoomGroups().get(0)!=null)?rsTBO.getSupplierRoomGroups().get(0).getInfoSource():"Fixed Combination");
				roomCombinationList = new ArrayList<RoomCombination>();		
				for (RoomCombination roomCombination : rsTBO.getSupplierRoomGroups().get(0).getRoomCombination()) {
					RoomCombination roomCombinationTarget = new RoomCombination();

					List<Integer> roomIndexList = new ArrayList<Integer>();
					for (Integer rIndex : roomCombination.getRoomIndex()) {
						rIndex = rIndex + roomIndex;
						roomIndexList.add(rIndex);
					}			
					roomCombinationTarget.setRoomIndex(roomIndexList);
					roomCombinationList.add(roomCombinationTarget);				
				}				
				roomCombinations.setRoomCombination(roomCombinationList);
				roomCombinations.setApiProvider(HotelApiCredentials.API_TBO_INTERNATIONAL);
				roomGroupsTarget.add(roomCombinations);	
			}
			rsFinal.setRoomGroups(roomGroupsTarget);	
		}
		else if((rsDesiya != null && rsDesiya.getRoomRates() != null && rsDesiya.getRoomRates().getRoomRates() !=null && rsDesiya.getRoomRates().getRoomRates().size()>0))
		{
			logger.info("########## ---Desiya only ");			
			logger.info("##########---Desiya only ");	

			rsFinal = rsDesiya;
			List<RoomCombinations> supplierRoomGroupsTarget = rsDesiya.getSupplierRoomGroups();		
			rsFinal.setSupplierRoomGroups(supplierRoomGroupsTarget);	
			Integer roomIndex = new Integer(1);	
			RoomRates troomrates = rsFinal.getRoomRates() == null? new RoomRates(): rsFinal.getRoomRates();
			List<RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<RoomStayType.RoomRates.RoomRate>();				
				
			for (RoomRate rr : rsFinal.getRoomRates().getRoomRates()) {
				RoomRate lrr = rr;	
				lrr.setRoomIndex(roomIndex);
				lrr.setApiProvider(HotelApiCredentials.API_DESIA_IND);
				roomIndex ++;
				lrratelist.add(lrr);							
			}
			troomrates.setRoomRates(lrratelist);
			rsFinal.setRoomRates(troomrates);		
			roomIndex = new Integer(0);
			List<RoomCombinations> roomGroupsTarget = new ArrayList<RoomCombinations>();
			RoomCombinations roomCombinations = new RoomCombinations(); 
			//desiya room indexes are so linear in structure..
			//which will be given 1-x where x equal no of rooms available * no of rooms requested. 
			roomCombinations.setInfoSource("FixedCombination");
			List<RoomCombination> roomCombinationList = new ArrayList<RoomCombination>();
			if(rsDesiya.getSupplierRoomGroups() !=null && rsDesiya.getSupplierRoomGroups().get(0) !=null && rsDesiya.getSupplierRoomGroups().get(0).getRoomCombination()!=null)
			{
				for (RoomCombination roomCombination : rsDesiya.getSupplierRoomGroups().get(0).getRoomCombination()) {
					RoomCombination roomCombinationTarget = new RoomCombination();

					List<Integer> roomIndexList = new ArrayList<Integer>();
					int buffer = 0;
					for (Integer roomCount : roomCombination.getRoomIndex()) {
						roomIndex = roomIndex + 1;
						roomIndexList.add(roomIndex);
						//buffer += 1;
					}			
					roomCombinationTarget.setRoomIndex(roomIndexList);			
					roomCombinationList.add(roomCombinationTarget);				
				}
			}
			roomCombinations.setRoomCombination(roomCombinationList);	
			roomCombinations.setApiProvider(HotelApiCredentials.API_DESIA_IND);
			roomGroupsTarget.add(roomCombinations);	
			rsFinal.setRoomGroups(roomGroupsTarget);	
		}
		else if((rsTBO != null && rsTBO.getRoomRates() != null && rsTBO.getRoomRates().getRoomRates() !=null && rsTBO.getRoomRates().getRoomRates().size()>0))		
		{
			logger.info("########## ---TBO only ");			
			logger.info("##########---TBO only ");	

			rsFinal = rsTBO;
			List<RoomCombinations> supplierRoomGroupsTbo = rsTBO.getSupplierRoomGroups();
			rsFinal.setSupplierRoomGroups(supplierRoomGroupsTbo);	
			Integer roomIndex = new Integer(1);	
			RoomRates troomrates = rsFinal.getRoomRates() == null? new RoomRates(): rsFinal.getRoomRates();
			List<RoomStayType.RoomRates.RoomRate> lrratelist = new ArrayList<RoomStayType.RoomRates.RoomRate>();				
			logger.info("########## object transformation---: RoomRates. size "+lrratelist.size());			
			for (RoomRate rr : rsFinal.getRoomRates().getRoomRates()) {
				RoomRate lrr = rr;	
				lrr.setRoomIndex(roomIndex);
				lrr.setApiProvider(HotelApiCredentials.API_TBO_INTERNATIONAL);
				roomIndex ++;
				lrratelist.add(lrr);							
			}
			troomrates.setRoomRates(lrratelist);
			rsFinal.setRoomRates(troomrates);		
			roomIndex = new Integer(0);
			List<RoomCombinations> roomGroupsTarget = new ArrayList<RoomCombinations>();
			RoomCombinations roomCombinations = new RoomCombinations(); 
			List<RoomCombination> roomCombinationList = new ArrayList<RoomCombination>();	
			if(rsTBO.getSupplierRoomGroups() !=null && rsTBO.getSupplierRoomGroups().get(0) !=null && rsTBO.getSupplierRoomGroups().get(0).getRoomCombination()!=null)
			{
				roomCombinations.setInfoSource((rsTBO.getSupplierRoomGroups().get(0)!=null)?rsTBO.getSupplierRoomGroups().get(0).getInfoSource():"Fixed Combination");
				for (RoomCombination roomCombination : rsTBO.getSupplierRoomGroups().get(0).getRoomCombination()) {
					RoomCombination roomCombinationTarget = new RoomCombination();

					List<Integer> roomIndexList = new ArrayList<Integer>();
					int buffer = 0;
					for (Integer roomCount : roomCombination.getRoomIndex()) {
						roomIndex = roomIndex + 1;
						roomIndexList.add(roomIndex);
						//buffer += 1;
					}			
					roomCombinationTarget.setRoomIndex(roomIndexList);			
					roomCombinationList.add(roomCombinationTarget);				
				}
			}
			roomCombinations.setRoomCombination(roomCombinationList);	
			roomCombinations.setApiProvider(HotelApiCredentials.API_TBO_INTERNATIONAL);
			roomGroupsTarget.add(roomCombinations);	
			rsFinal.setRoomGroups(roomGroupsTarget);	
		}
		return rsFinal;
	}
	public static OTAHotelAvailRS.RoomStays.RoomStay fillRoomStay(OTAHotelAvailRS.RoomStays.RoomStay roomStaySrc,OTAHotelAvailRS.RoomStays.RoomStay roomStayTarget, Integer roomIndexBuffer)
	{		
		RoomTypes troomtypes = roomStayTarget.getRoomTypes() == null ? new RoomTypes():roomStayTarget.getRoomTypes();
		List<RoomTypeType> lrtlist = troomtypes.getRoomTypes()==null?new ArrayList<RoomTypeType>():troomtypes.getRoomTypes();
		for (RoomTypeType rt : roomStaySrc.getRoomTypes().getRoomTypes()) {
			RoomTypeType lrt = rt;					
			lrtlist.add(lrt);					
		}
		troomtypes.setRoomTypes(lrtlist);
		roomStayTarget.setRoomTypes(troomtypes);

		RoomRates troomrates = roomStayTarget.getRoomRates() == null? new RoomRates(): roomStayTarget.getRoomRates();
		List<RoomStayType.RoomRates.RoomRate> lrratelist = (List<RoomStayType.RoomRates.RoomRate>) (troomrates.getRoomRates()==null ? new ArrayList<RoomStayType.RoomRates.RoomRate>():troomrates.getRoomRates());				
		logger.info("object transformation---: RoomRates. size "+lrratelist.size());			
		for (RoomRate rr : roomStaySrc.getRoomRates().getRoomRates()) {
			RoomRate lrr = rr;	
			if(roomIndexBuffer != null)
			{
				lrr.setRoomIndex(lrr.getSupplierRoomIndex() + roomIndexBuffer);	
			}
			lrr.setApiProvider(roomStaySrc.getBasicPropertyInfo().getApiProvider());
			lrratelist.add(lrr);							
		}
		troomrates.setRoomRates(lrratelist);
		roomStayTarget.setRoomRates(troomrates);

		RoomStayType.RatePlans trateplans = roomStayTarget.getRatePlans() == null ? new RoomStayType.RatePlans():roomStayTarget.getRatePlans();
		List<RatePlanType> lplantlist = (List<RatePlanType>) (trateplans.getRatePlan()==null? new ArrayList<RatePlanType>():trateplans.getRatePlan());
		logger.info("object transformation---: RatePlanType. size "+lplantlist.size());			
		for (RatePlanType pt : roomStaySrc.getRatePlans().getRatePlan()) {					
			lplantlist.add(pt);								
		}
		trateplans.setRatePlen(lplantlist);
		roomStayTarget.setRatePlans(trateplans);
		return roomStayTarget;
	}



	public static OTAHotelAvailRS.RoomStays.RoomStay mergeRoomsMultiHotels(HashMap<Integer, List<OTAHotelAvailRS.RoomStays.RoomStay>> roomStayMap)
	{
		OTAHotelAvailRS.RoomStays.RoomStay rsFinal = null;
		List<OTAHotelAvailRS.RoomStays.RoomStay> rsApiCompleteListDesiya =  roomStayMap.get(HotelApiCredentials.API_DESIA_IND) == null ? new ArrayList<OTAHotelAvailRS.RoomStays.RoomStay>():roomStayMap.get(HotelApiCredentials.API_DESIA_IND); 
		List<OTAHotelAvailRS.RoomStays.RoomStay> rsApiCompleteListTBO =  roomStayMap.get(HotelApiCredentials.API_TBO_INTERNATIONAL) == null ? new ArrayList<OTAHotelAvailRS.RoomStays.RoomStay>():roomStayMap.get(HotelApiCredentials.API_TBO_INTERNATIONAL); 
		Integer roomIndex = new Integer(1);	
		for (RoomStay rsDesiya : rsApiCompleteListDesiya) {
			if((rsDesiya != null && rsDesiya.getRoomRates() != null && rsDesiya.getRoomRates().getRoomRates() !=null && rsDesiya.getRoomRates().getRoomRates().size()>0))
			{				
				if(rsFinal == null)
					rsFinal = rsDesiya;				
				rsFinal = fillRoomStayMultiHotels(rsDesiya, rsFinal, roomIndex);
				List<RoomCombinations> supplierRoomGroupsTarget = rsDesiya.getSupplierRoomGroups();		
				rsFinal.setSupplierRoomGroups(supplierRoomGroupsTarget);				
				RoomRates troomrates = rsFinal.getRoomRates() == null? new RoomRates(): rsFinal.getRoomRates();
				List<RoomStayType.RoomRates.RoomRate> lrratelist = (rsFinal.getRoomRates()!=null && rsFinal.getRoomRates().getRoomRates() != null)?rsFinal.getRoomRates().getRoomRates():new ArrayList<RoomStayType.RoomRates.RoomRate>();				
				logger.info("##########DEsiya room merging---: RoomRates. size "+lrratelist.size());			
				for (RoomRate rr : rsDesiya.getRoomRates().getRoomRates()) {
					RoomRate lrr = rr;	
					lrr.setRoomIndex(roomIndex);
					lrr.setApiProvider(HotelApiCredentials.API_DESIA_IND);
					lrr.setSupplierHotelCode(rsDesiya.getBasicPropertyInfo().getApiVendorID());
					roomIndex ++;
					lrratelist.add(lrr);							
				}
				troomrates.setRoomRates(lrratelist);
				rsFinal.setRoomRates(troomrates);				
			}
		}
		for (RoomStay rsTBO : rsApiCompleteListTBO) {
			if((rsTBO != null && rsTBO.getRoomRates() != null && rsTBO.getRoomRates().getRoomRates() !=null && rsTBO.getRoomRates().getRoomRates().size()>0))
			{
				if(rsFinal == null)
					rsFinal = rsTBO;				
				rsFinal = fillRoomStayMultiHotels(rsTBO, rsFinal, roomIndex);	
				List<RoomCombinations> supplierRoomGroupsTarget = rsTBO.getSupplierRoomGroups();		
				rsFinal.setSupplierRoomGroups(supplierRoomGroupsTarget);			
				RoomRates troomrates = rsFinal.getRoomRates() == null? new RoomRates(): rsFinal.getRoomRates();
				List<RoomStayType.RoomRates.RoomRate> lrratelist = (rsFinal.getRoomRates()!=null && rsFinal.getRoomRates().getRoomRates() != null)?rsFinal.getRoomRates().getRoomRates():new ArrayList<RoomStayType.RoomRates.RoomRate>();				
				logger.info("##########TBO room merging---: RoomRates. size "+lrratelist.size());			
				for (RoomRate rr : rsTBO.getRoomRates().getRoomRates()) {
					RoomRate lrr = rr;	
					lrr.setRoomIndex(roomIndex);
					lrr.setApiProvider(HotelApiCredentials.API_TBO_INTERNATIONAL);
					lrr.setSupplierHotelCode(rsTBO.getBasicPropertyInfo().getApiVendorID());
					roomIndex ++;
					lrratelist.add(lrr);							
				}
				troomrates.setRoomRates(lrratelist);
				rsFinal.setRoomRates(troomrates);				
			}
		}
		return rsFinal;
	}
	public static OTAHotelAvailRS.RoomStays.RoomStay fillRoomStayMultiHotels(OTAHotelAvailRS.RoomStays.RoomStay roomStaySrc, OTAHotelAvailRS.RoomStays.RoomStay roomStayTarget, Integer roomIndexBuffer)
	{		
		RoomTypes troomtypes = roomStayTarget.getRoomTypes() == null ? new RoomTypes():roomStayTarget.getRoomTypes();
		List<RoomTypeType> lrtlist = troomtypes.getRoomTypes()==null?new ArrayList<RoomTypeType>():troomtypes.getRoomTypes();
		logger.info("object transformation---: RoomTypes. size "+lrtlist.size());			
		if(roomStaySrc != null && roomStaySrc.getRoomTypes() != null && roomStaySrc.getRoomTypes().getRoomTypes()!= null)
			for (RoomTypeType rt : roomStaySrc.getRoomTypes().getRoomTypes()) {
				RoomTypeType lrt = rt;					
				lrtlist.add(lrt);					
			}
		troomtypes.setRoomTypes(lrtlist);
		roomStayTarget.setRoomTypes(troomtypes);

		RoomRates troomrates = roomStayTarget.getRoomRates() == null? new RoomRates(): roomStayTarget.getRoomRates();
		List<RoomStayType.RoomRates.RoomRate> lrratelist = (List<RoomStayType.RoomRates.RoomRate>) (troomrates.getRoomRates()==null ? new ArrayList<RoomStayType.RoomRates.RoomRate>():troomrates.getRoomRates());				
		logger.info("object transformation---: RoomRates. size "+lrratelist.size());			
		if(roomStaySrc != null && roomStaySrc.getRoomRates() != null && roomStaySrc.getRoomRates().getRoomRates()!= null)			
			for (RoomRate rr : roomStaySrc.getRoomRates().getRoomRates()) {
				RoomRate lrr = rr;	
				if(roomIndexBuffer != null)
				{
					lrr.setRoomIndex(lrr.getSupplierRoomIndex() + roomIndexBuffer);	
				}
				lrr.setApiProvider(roomStaySrc.getBasicPropertyInfo().getApiProvider());
				lrratelist.add(lrr);							
			}
		troomrates.setRoomRates(lrratelist);
		roomStayTarget.setRoomRates(troomrates);

		RoomStayType.RatePlans trateplans = roomStayTarget.getRatePlans() == null ? new RoomStayType.RatePlans():roomStayTarget.getRatePlans();
		List<RatePlanType> lplantlist = (List<RatePlanType>) (trateplans.getRatePlan()==null? new ArrayList<RatePlanType>():trateplans.getRatePlan());
		logger.info("object transformation---: RatePlanType. size "+lplantlist.size());			
		if(roomStaySrc != null && roomStaySrc.getRatePlans() != null && roomStaySrc.getRatePlans().getRatePlan()!= null)			
			for (RatePlanType pt : roomStaySrc.getRatePlans().getRatePlan()) {					
				lplantlist.add(pt);								
			}
		trateplans.setRatePlen(lplantlist);
		roomStayTarget.setRatePlans(trateplans);		


		return roomStayTarget;
	}



}
