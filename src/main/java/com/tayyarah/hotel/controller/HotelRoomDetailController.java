package com.tayyarah.hotel.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.FileUtil;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.hotel.dao.ApiHotelMapStoreDao;
import com.tayyarah.hotel.dao.HotelCityDao;
import com.tayyarah.hotel.dao.HotelFacilityDao;
import com.tayyarah.hotel.dao.HotelMarkupDao;
import com.tayyarah.hotel.dao.HotelSearchDao;
import com.tayyarah.hotel.dao.HotelSearchRoomDetailDao;
import com.tayyarah.hotel.dao.HotelTransactionDao;
import com.tayyarah.hotel.dao.HotelimagesDao;
import com.tayyarah.hotel.dao.HotelinandaroundDao;
import com.tayyarah.hotel.dao.HoteloverviewDao;
import com.tayyarah.hotel.dao.HotelroomdescriptionDao;
import com.tayyarah.hotel.dao.HotelsecondaryareaDao;
import com.tayyarah.hotel.dao.IslhotelmappingDao;
import com.tayyarah.hotel.entity.HotelMarkup;
import com.tayyarah.hotel.entity.HotelSearchRoomDetailTemp;
import com.tayyarah.hotel.entity.HotelSearchTemp;
import com.tayyarah.hotel.entity.HotelTransactionTemp;
import com.tayyarah.hotel.entity.Islhotelmapping;
import com.tayyarah.hotel.model.APIHotelMap;
import com.tayyarah.hotel.model.APIRoomDetail;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.HotelAnalyzer;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.RoomAnalyzer;
import com.tayyarah.hotel.util.api.concurrency.AsyncSupport;
import com.tayyarah.hotel.util.api.concurrency.DesiyaPullerTask;
import com.tayyarah.hotel.util.api.concurrency.RezLivePullerTask;
import com.tayyarah.hotel.util.api.concurrency.RezNextPullerTask;
import com.tayyarah.hotel.util.api.concurrency.TBOPullerTask;
import com.tayyarah.hotel.util.api.concurrency.TayyarahPullerTask;


@RestController
@RequestMapping("/hotel/roomdetail")
public class HotelRoomDetailController {


	@Autowired
	HoteloverviewDao hotelDao;
	@Autowired
	HotelroomdescriptionDao hotelroomdescriptionDao;
	@Autowired
	HotelimagesDao hotelimagesDao;
	@Autowired
	HotelFacilityDao hotelFacilityDao;
	@Autowired
	IslhotelmappingDao islhotelmappingDao;
	@Autowired
	HotelinandaroundDao hotelinandaroundDao;
	@Autowired
	HotelsecondaryareaDao hotelsecondaryareaDao;
	@Autowired
	HotelCityDao hotelCityDao;
	@Autowired
	HotelObjectTransformer hotelObjectTransformer;
	@Autowired
	ApiHotelMapStoreDao apihotelstoredao;
	@Autowired
	HotelTransactionDao hotelTransactionDao;
	@Autowired
	HotelSearchDao hotelSearchDao;
	@Autowired
	HotelSearchRoomDetailDao hotelSearchRoomDetailDao;
	@Autowired
	HotelMarkupDao hotelMarkupDao;
	@Autowired
	CompanyDao companyDao;
	@Autowired
	AsyncSupport asyncSupport;
	@Autowired
	HotelAnalyzer hotelAnalyzer;

	public static final Logger logger = Logger.getLogger(HotelRoomDetailController.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/new",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	APIRoomDetail getRoomStayDetailsNew(@RequestParam(value="appkey") String appkey, @RequestParam(value="searchkey") Long searchkey,@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) {
		logger.info("Searhing roomdetails controller call: searchkey-"+searchkey+"---hotelcode-"+hotelcode);
		ResponseHeader.setPostResponse(response);//Setting response header
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}

		ObjectMapper mapper = new ObjectMapper();
		//hbc.setAppkey();
		HotelSearchTemp hs = new HotelSearchTemp();
		HotelSearchRoomDetailTemp hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(searchkey);
		Company newCompany=companyDao.getCompany(appKeyVo.getCompanyId());
		String gstNo=newCompany!=null && newCompany.getCompanyGstIn()!=null?newCompany.getCompanyGstIn():null;
		HotelTransactionTemp ht = new HotelTransactionTemp();
		TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay> roomStays = new TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay>();
		Islhotelmapping islhotelmapping= null;
		HotelSearchCommand hsc = null;
		try
		{
			logger.info("Searhing roomdetails searchkey : "+searchkey);
			hs = hotelSearchDao.getHotelSearch(searchkey);
			ht = hotelTransactionDao.getHotelTransaction(searchkey);
			hotelSearchRoomDetail =  hotelSearchRoomDetailDao.getHotelSearchRoomDetail(hotelSearchRoomDetail);
			if(hotelSearchRoomDetail == null)
			{
				hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(searchkey);
			}			
			hsc =  (HotelSearchCommand) SerializationUtils.deserialize(hs.getHotelsearch_cmd());
			APIHotelMap apiHotelMap = (APIHotelMap) SerializationUtils.deserialize(hs.getHotelres_map());
			if(apiHotelMap == null)
			{
				return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "No Search found, Try again"));
			}
			logger.info("apiHotelMap.getRoomStays() size : "+apiHotelMap.getRoomStays().size());
			TreeMap<String, HashMap<Integer, HashMap<String, Boolean>>>  apiProviderMap = (apiHotelMap.getApiProviderMap() == null)? new TreeMap<String, HashMap<Integer, HashMap<String, Boolean>>>():apiHotelMap.getApiProviderMap();
			roomStays = apiHotelMap.getRoomStays() == null?new TreeMap<String, RoomStay>():apiHotelMap.getRoomStays() ;
			logger.info("roomStays size : "+roomStays.size());

			//ArrayList<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay> rsList = new ArrayList<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>();

			//:::::::::::::::for multiple hotels starts..
			//HashMap<Integer, List<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>> roomStayMap = new HashMap<Integer, List<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>>();
			//:::::::::::::::for multiple hotels ends..
			HashMap<Integer, OTAHotelAvailRS.RoomStays.RoomStay> roomStayMap = new HashMap<Integer, OTAHotelAvailRS.RoomStays.RoomStay>();



			OTAHotelAvailRS.RoomStays.RoomStay rs = null;
			List<OTAHotelAvailRS.RoomStays.RoomStay> rsApiCompleteList = new ArrayList<OTAHotelAvailRS.RoomStays.RoomStay>();
			OTAHotelAvailRS.RoomStays.RoomStay rsApiComplete = null;
			OTAHotelAvailRS.RoomStays.RoomStay rsComplete = null;
			List<HotelSearchCommand.RoomReqInfo> roomReqs = null;

			HashMap<Integer, HashMap<String, Boolean>> apiHotelIdMap = (apiProviderMap.get(hotelcode)!=null)?apiProviderMap.get(hotelcode):new HashMap<Integer, HashMap<String,Boolean>>();

			int roomCollectCount = 1;
			Boolean isOfflinePull = false;
			for (Entry<Integer, HashMap<String, Boolean>> entryApi : apiHotelIdMap.entrySet()) {
				HashMap<String, Boolean> apiHotelId = entryApi.getValue();
				Integer apiProvider = entryApi.getKey();
				switch (apiProvider) {
				case HotelApiCredentials.API_DESIA_IND:
					for (Entry<String, Boolean> entryApiHotelId : apiHotelId.entrySet()) {
						String key = entryApiHotelId.getKey();
						rs = roomStays.get(key);
						if(rs!=null)
						{
							//F:\logs\tayyarah\hotel-tbo-50-prebook-response.json
							//byte[] jsonData = Files.readAllBytes(Paths.get("D:\\opt\\devtravelapi\\provider_req_res\\tayyarah\\hotel\\common\\"+searchkey+"-rs-desiya-request.json"));
							//rsApiComplete = this.mapper.readValue(jsonData, com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay.class);
							rsApiComplete = hotelObjectTransformer.getTGRoomDetails(rs);


							HotelApiCredentials apidesiya = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);
							DesiyaPullerTask desiyaPullerTask = new DesiyaPullerTask(hs, hotelObjectTransformer, null, null, null, null, null, null, null , apidesiya, hsc, "TBO Api", rs.getCity());
							//rsApiComplete = desiyaPullerTask.searchHotelDetail(rsApiComplete);


							FileUtil.writeJson("hotel", "common", "rs-desiya-"+roomCollectCount, false, rsApiComplete, String.valueOf(searchkey));
							logger.info("###################################");
							logger.info("############## API_DESIA_IND room collection ---: "+roomCollectCount);
							logger.info("############## room collection ---rsApiComplete : "+rsApiComplete);


							roomCollectCount += 1;
							if(rsApiComplete != null && rsApiComplete.getRoomRates() != null && rsApiComplete.getRoomRates().getRoomRates() != null && rsApiComplete.getRoomRates().getRoomRates().size()>0)
							{
								//:::::::::::::::for multiple hotels starts..
								//rsApiCompleteList =  roomStayMap.get(HotelApiCredentials.API_DESIA_IND) == null ? new ArrayList<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>():roomStayMap.get(HotelApiCredentials.API_DESIA_IND);
								//rsApiCompleteList.add(rsApiComplete);
								//roomStayMap.put(HotelApiCredentials.API_DESIA_IND, rsApiCompleteList);
								//:::::::::::::::for multiple hotels ends..
								roomStayMap.put(HotelApiCredentials.API_DESIA_IND, rsApiComplete);

								//rsList.add(rsApiComplete);
							}
						}


						break;
					}

					break;
				case HotelApiCredentials.API_REZNEXT_IND:

					break;
				case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:

					break;
				case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:
					/*
					HotelApiCredentials apitayyarah = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL);
					//TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apitayyarah, hsc, "Tayyarah Api");
					TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(searchkey, apitayyarah, hsc, "Tayyarah Api");
					APIRoomDetail tayyarahroomdetail = tayyarahPullerTask.searchHotelRooms(rsApiInComplete);
					rsApiInComplete = tayyarahroomdetail.getRs();
					//HotelApiCredentials apireztlive = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL);
					//RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apireztlive, hsc, "RazLive Api");
					//rs = rezLivePullerTask.getRoomDetail(rs, bookingkey);
					 */
					break;

				case HotelApiCredentials.API_LINTAS_REPOSITORY:
					//Do Room availablity check of lintas Rposit hotels
					break;
				case HotelApiCredentials.API_LINTAS_INTERNATIONAL:
					//Do Room availablity check of lintas api hotels
					break;

				case HotelApiCredentials.API_TBO_INTERNATIONAL:

					for (Entry<String, Boolean> entryApiHotelId : apiHotelId.entrySet()) {
						String key = entryApiHotelId.getKey();
						rs = roomStays.get(key);
						if(rs!=null)
						{
							roomReqs = hsc.getRoomrequests();
							logger.info("Searhing roomReqs size : "+roomReqs.size());
							//byte[] jsonData = Files.readAllBytes(Paths.get("D:\\opt\\devtravelapi\\provider_req_res\\tayyarah\\hotel\\common\\"+searchkey+"-rs-tbo-request.json"));
							//rsApiComplete = this.mapper.readValue(jsonData, com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay.class);
							HotelApiCredentials apitbo = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);
							TBOPullerTask tboRoomPullerTask = new TBOPullerTask(hs, hotelObjectTransformer, null, null, null, null, null, null, null , apitbo, hsc, "TBO Api", rs.getCity());
							rsApiComplete = tboRoomPullerTask.searchHotelRooms(rs);
							logger.info("Searhing after room cancellation policy: "+rsApiComplete);
							FileUtil.writeJson("hotel", "common", "rs-tbo-"+roomCollectCount, false, rsApiComplete, String.valueOf(searchkey));
							logger.info("###################################");
							logger.info("############## API_TBO_INTERNATIONAL room collection ---: "+roomCollectCount);
							logger.info("############## room collection ---rsApiComplete : "+rsApiComplete);
							roomCollectCount += 1;
							if(rsApiComplete != null && rsApiComplete.getRoomRates() != null && rsApiComplete.getRoomRates().getRoomRates() != null && rsApiComplete.getRoomRates().getRoomRates().size()>0)
							{
								//roomStayMap.put(HotelApiCredentials.API_TBO_INTERNATIONAL, rsApiComplete);
								//rsList.add(rsApiComplete);
								//:::::::::::::::for multiple hotels starts..
								//rsApiCompleteList =  roomStayMap.get(HotelApiCredentials.API_TBO_INTERNATIONAL) == null ? new ArrayList<com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay>():roomStayMap.get(HotelApiCredentials.API_TBO_INTERNATIONAL);
								//rsApiCompleteList.add(rsApiComplete);
								//roomStayMap.put(HotelApiCredentials.API_TBO_INTERNATIONAL, rsApiCompleteList);
								//:::::::::::::::for multiple hotels ends..
								roomStayMap.put(HotelApiCredentials.API_TBO_INTERNATIONAL, rsApiComplete);


							}
						}
						break;

					}
					rsComplete = RoomAnalyzer.mergeRooms(rsApiComplete, HotelApiCredentials.API_TBO_INTERNATIONAL);
					break;
				case HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL:

					for (Entry<String, Boolean> entryApiHotelId : apiHotelId.entrySet()) {
						String key = entryApiHotelId.getKey();
						rs = roomStays.get(key);
						rsComplete = rs;

					}
					isOfflinePull = true;
					rsComplete = RoomAnalyzer.mergeRooms(rsComplete, HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL);

					break;


				default:
					break;
				}
			}
			logger.info("###################### suppliers count in room search---------:"+ roomStayMap.size());
			//rsComplete = rsApiComplete;

			//:::::::::::::::for multiple hotels starts..
			//rsComplete = RoomAnalyzer.mergeRoomsOfSuppliersHotels(roomStayMap);
			//:::::::::::::::for multiple hotels ends..
			if(!isOfflinePull)
				rsComplete = RoomAnalyzer.mergeRoomsOfSuppliersHotel(roomStayMap);
			//byte[] jsonData = Files.readAllBytes(Paths.get("D:\\opt\\devtravelapi\\provider_req_res\\tayyarah\\hotel\\common\\"+searchkey+"-rs-tbo-request.json"));
			//rsApiComplete = this.mapper.readValue(jsonData, com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay.class);


			//FileUtil.writeJson("hotel", "common", "rs-combined", false, rsComplete, String.valueOf(searchkey));

			//markups = hotelMarkupDao.getHotelMarkups(companyId);

			if(rsComplete == null || rsComplete.getRoomRates() == null || rsComplete.getRoomRates().getRoomRates() == null || rsComplete.getRoomRates().getRoomRates().size()==0)
			{
				APIRoomDetail apiRoomDetail = new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "No Room Details found, Try some other hotel"));
				apiRoomDetail.setRoomStayMap(roomStayMap);
				return apiRoomDetail;
				//return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "No Room Details found, Try some other hotel"));
			}
			Map<String,List<HotelMarkup>> markups = hsc.getHotelMarkupCommissionDetails().getMarkups();
			logger.info("HotelSearchCommand- no of markups----------:"+ markups.size()+"- for company id--"+appKeyVo.getCompanyId());
			rs = hotelAnalyzer.reDefineRoomDetailResponse(hsc, markups, rsComplete);
			//rs = HotelMarkUpUtil.applyMarkUpOnHotelRoomDetail(hsc, markups, rs);
			logger.info("HotelSearchCommand- mark up applied for roomdetail....----------:"+ markups.size()+"- for company id--"+appKeyVo.getCompanyId());
			//apiHotelMap.put(hotelcode, rs);
			logger.info("Room details have been updated for  -- "+hotelcode+"----------:");


			byte[] roomStaysdata = SerializationUtils.serialize(rs);
			hotelSearchRoomDetail.setRoomstay(roomStaysdata);
			hotelSearchRoomDetail.setApi_provider(Integer.valueOf(rs.getBasicPropertyInfo().getApiProvider()));
			hotelSearchRoomDetail.setHotelsearch_cmd(hs.getHotelsearch_cmd());
			hotelSearchRoomDetail = hotelSearchRoomDetailDao.insertOrupdateHotelSearchRoomDetail(hotelSearchRoomDetail);
			logger.info("Searhing roomdetails controller call: searchkey-"+searchkey+"---hotelSearchRoomDetail temp upda"
					+ "ted...-");
			return new APIRoomDetail(hsc, rs, new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS),gstNo, BigInteger.valueOf(ht.getSearch_key()), BigInteger.valueOf(ht.getId()) ) ;

		}
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Exception:HibernateException--"+e.getMessage() )) ;

		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "IOException--"+e.getMessage() )) ;

		}
		catch (ClassNotFoundException e) {
			logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "ClassNotFoundException--"+e.getMessage() )) ;

		} catch (JAXBException e) {
			logger.info("query Exception:JAXBException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "JAXBException--"+e.getMessage() )) ;

		}
		catch (UnsupportedOperationException e) {
			logger.info("query Exception:UnsupportedOperationException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "UnsupportedOperationException--"+e.getMessage() )) ;

		} catch (SOAPException e) {
			logger.info("query Exception:SOAPException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "SOAPException--"+e.getMessage() )) ;

		} catch (Exception e) {
			logger.info("query Exception:Exception:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Exception--"+e.getMessage() )) ;

		}

	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	APIRoomDetail getRoomStayDetails(@RequestParam(value="appkey") String appkey, @RequestParam(value="searchkey") Long searchkey,@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) {
		logger.info("Searhing roomdetails controller call: searchkey-"+searchkey+"---hotelcode-"+hotelcode);
		ResponseHeader.setPostResponse(response);//Setting response header
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}
		HotelSearchTemp hs = new HotelSearchTemp();
		HotelSearchRoomDetailTemp hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(searchkey);
		HotelTransactionTemp ht = new HotelTransactionTemp();
		TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay> apiHotelMap = new TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay>();
		Islhotelmapping islhotelmapping= null;
		HotelSearchCommand hsc = null;
		Company newCompany=companyDao.getCompany(appKeyVo.getCompanyId());
		String gstNo=newCompany!=null && newCompany.getCompanyGstIn()!=null?newCompany.getCompanyGstIn():null;
		try
		{
			/*islhotelmapping = islhotelmappingDao.getHotelByISLVendorID(hotelcode);
			//compare rate and replace the roomstay item
			if(islhotelmapping != null && islhotelmapping.getTGVendorID() != null)
			{
				logger.info("Searhing roomdetails controller : "+searchkey);
				apiHotelMap =apihotelstoredao.getTGRoomStaysMap(searchkey);
				return apiHotelMap.get(islhotelmapping.getTGVendorID());
				//apiHotelMap = (APIHotelMap) ois.readObject();
			}
			 */
			logger.info("Searhing roomdetails searchkey : "+searchkey);
			hs = hotelSearchDao.getHotelSearch(searchkey);
			ht = hotelTransactionDao.getHotelTransaction(searchkey);
			hotelSearchRoomDetail =  hotelSearchRoomDetailDao.getHotelSearchRoomDetail(hotelSearchRoomDetail);
			if(hotelSearchRoomDetail == null)
			{
				hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(searchkey);
			}
			logger.info("Searhing roomdetails controller call: searchkey-"+searchkey+"---hotelSearchRoomDetail temp-"+hotelSearchRoomDetail.toString());

			hsc =  (HotelSearchCommand) SerializationUtils.deserialize(hs.getHotelsearch_cmd());
			apiHotelMap =  (TreeMap<String, RoomStay>) SerializationUtils.deserialize(hs.getHotelres_map());
			logger.info("Searhing roomdetails hotels history temp map : "+apiHotelMap);

			OTAHotelAvailRS.RoomStays.RoomStay rs = apiHotelMap.get(hotelcode);
			OTAHotelAvailRS.RoomStays.RoomStay rsApiInComplete = apiHotelMap.get(hotelcode);


			ObjectMapper mapper = new ObjectMapper();
			String roomstaytext = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rs);
			logger.info("-------------(((((-roomstaytext:"+roomstaytext);
			logger.info("stored rs : "+rs);
			logger.info("stored rsApiInComplete : "+rsApiInComplete);


			List<HotelSearchCommand.RoomReqInfo> roomReqs = null;
			//com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = hotelObjectTransformer.convertTGtoNative(apiHotelMap.get(hotelcode));
			//List<HotelMarkup> markups = new ArrayList<HotelMarkup>();

			switch (rs.getBasicPropertyInfo().getApiProvider()) {
			case HotelApiCredentials.API_DESIA_IND:
				rsApiInComplete = hotelObjectTransformer.getTGRoomDetails(rs);
				break;
			case HotelApiCredentials.API_REZNEXT_IND:
				roomReqs = hsc.getRoomrequests();
				logger.info("Searhing roomReqs size : "+roomReqs.size());
				StringBuilder roominfo = new StringBuilder("<RoomInfo NumberOfRooms=\""+roomReqs.size()+"\">");
				for (HotelSearchCommand.RoomReqInfo roomReq : roomReqs) {
					roominfo.append("<Rooms Room=\""+roomReq.getRoomindex()+"\" Adult=\""+roomReq.getNoofAdult()+"\" Child=\""+roomReq.getNoofChild()+"\" />");
				}
				roominfo.append("</RoomInfo>");
				logger.info("Searhing roomReqs roominfo : "+roominfo);


				HotelApiCredentials apirezt = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND,appKeyVo);
				RezNextPullerTask reznextPullerTask = new RezNextPullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apirezt, hsc, "RazNext Api");
				reznextPullerTask.setRoomStay(rsApiInComplete);
				rsApiInComplete = reznextPullerTask.GetHotelRoomDetail(rsApiInComplete);



				break;
			case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
				/*roomReqs = hsc.getRoomReqs();
				logger.info("Searhing roomReqs size : "+roomReqs.size());
				logger.info("Searhing roomReqs roominfo : "+roominfo);

				HotelApiCredentials apireztlive = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL);
				RezNextPullerTask reznextPullerTask = new RezNextPullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apirezt, hsc, "RazNext Api");
				reznextPullerTask.setHotelCode(hotelcode);
				rs = reznextPullerTask.GetHotelRoomDetail(rs);
				 */

				roomReqs = hsc.getRoomrequests();
				logger.info("Searhing roomReqs size : "+roomReqs.size());

				HotelApiCredentials apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);
				RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, null, null, null, null, null, null, null , apiauth, hsc, "RazLive Api");
				rsApiInComplete = rezLivePullerTask.getHotelRoomDetails(rsApiInComplete);
				logger.info("Searhing after room detail fill call: "+rsApiInComplete);
				rsApiInComplete = rezLivePullerTask.getCancelPolicyHotel(rsApiInComplete);
				logger.info("Searhing after room cancellation policy: "+rsApiInComplete);


				//HotelApiCredentials apireztlive = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL);
				//RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apireztlive, hsc, "RazLive Api");
				//rs = rezLivePullerTask.getRoomDetail(rs, bookingkey);
				break;
			case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:

				HotelApiCredentials apitayyarah = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL,appKeyVo);
				//TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apitayyarah, hsc, "Tayyarah Api");
				TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(BigInteger.valueOf(searchkey), apitayyarah, hsc, "Tayyarah Api");
				APIRoomDetail tayyarahroomdetail = tayyarahPullerTask.searchHotelRooms(rsApiInComplete);
				rsApiInComplete = tayyarahroomdetail.getRs();
				//HotelApiCredentials apireztlive = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL);
				//RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apireztlive, hsc, "RazLive Api");
				//rs = rezLivePullerTask.getRoomDetail(rs, bookingkey);
				break;

			case HotelApiCredentials.API_LINTAS_REPOSITORY:
				//Do Room availablity check of lintas Rposit hotels
				break;
			case HotelApiCredentials.API_LINTAS_INTERNATIONAL:
				//Do Room availablity check of lintas api hotels
				break;

			case HotelApiCredentials.API_TBO_INTERNATIONAL:
				roomReqs = hsc.getRoomrequests();
				logger.info("Searhing roomReqs size : "+roomReqs.size());

				HotelApiCredentials apitbo = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);
				TBOPullerTask tboPullerTask = new TBOPullerTask(hs, hotelObjectTransformer, null, null, null, null, null, null, null , apitbo, hsc, "TBO Api", hsc.getSearchCity());
				rsApiInComplete = tboPullerTask.searchHotelRooms(rs);
				//rsApiInComplete = searchHotelInfo(rsApiInComplete);
				logger.info("Searhing after room cancellation policy: "+rsApiInComplete);

				break;


			default:
				break;
			}

			//markups = hotelMarkupDao.getHotelMarkups(companyId);

			if(rsApiInComplete == null || rsApiInComplete.getRoomRates() == null || rsApiInComplete.getRoomRates().getRoomRates() == null || rsApiInComplete.getRoomRates().getRoomRates().size()==0)
			{
				return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "No Room Details found, Try some other hotel"));
			}
			Map<String,List<HotelMarkup>> markups = hsc.getHotelMarkupCommissionDetails().getMarkups();
			logger.info("HotelSearchCommand- no of markups----------:"+ markups.size()+"- for company id--"+appKeyVo.getCompanyId());
			rs = hotelAnalyzer.reDefineRoomDetailResponse(hsc, markups, rsApiInComplete);

			//rs = HotelMarkUpUtil.applyMarkUpOnHotelRoomDetail(hsc, markups, rs);
			logger.info("HotelSearchCommand- mark up applied for roomdetail....----------:"+ markups.size()+"- for company id--"+appKeyVo.getCompanyId());

			//apiHotelMap.put(hotelcode, rs);
			logger.info("Room details have been updated for  -- "+hotelcode+"----------:");

			byte[] roomStaysdata = SerializationUtils.serialize(rs);
			hotelSearchRoomDetail.setRoomstay(roomStaysdata);
			hotelSearchRoomDetail.setApi_provider(Integer.valueOf(rs.getBasicPropertyInfo().getApiProvider()));
			hotelSearchRoomDetail.setHotelsearch_cmd(hs.getHotelsearch_cmd());
			hotelSearchRoomDetail = hotelSearchRoomDetailDao.insertOrupdateHotelSearchRoomDetail(hotelSearchRoomDetail);
			//byte[] roomStaysMapdata = SerializationUtils.serialize(apiHotelMap);
			//hs.setHotelres_map(roomStaysMapdata);
			//hs = hotelSearchDao.insertOrUpdateHotelSearch(hs);
			logger.info("Searhing roomdetails controller call: searchkey-"+searchkey+"---hotelSearchRoomDetail temp updated...-"+hotelSearchRoomDetail.toString());


			return new APIRoomDetail(hsc, rs, new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS),gstNo, BigInteger.valueOf(ht.getSearch_key()), BigInteger.valueOf(ht.getId()) ) ;

		}
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Exception:HibernateException--"+e.getMessage() )) ;

		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "IOException--"+e.getMessage() )) ;

		}
		catch (ClassNotFoundException e) {
			logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "ClassNotFoundException--"+e.getMessage() )) ;

		} catch (JAXBException e) {
			logger.info("query Exception:JAXBException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "JAXBException--"+e.getMessage() )) ;

		}
		catch (UnsupportedOperationException e) {
			logger.info("query Exception:UnsupportedOperationException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "UnsupportedOperationException--"+e.getMessage() )) ;

		} catch (SOAPException e) {
			logger.info("query Exception:SOAPException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "SOAPException--"+e.getMessage() )) ;

		} catch (Exception e) {
			logger.info("query Exception:Exception:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Exception--"+e.getMessage() )) ;

		}

	}



	@RequestMapping(value = "/summarynew",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	APIRoomDetail getRoomStayDetailsSummaryNew(@RequestParam(value="appkey") String appkey, @RequestParam(value="searchkey") Long searchkey,@RequestParam(value="hotelcode") String hotelcode, @RequestParam(value="bookingkey") String bookingkey, HttpServletResponse response) {
		logger.info("Searhing roomdetails controller call: searchkey-"+searchkey+"---hotelcode-"+hotelcode);
		ResponseHeader.setPostResponse(response);//Setting response header
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}
		HotelTransactionTemp ht = new HotelTransactionTemp();
		Islhotelmapping islhotelmapping= null;
		HotelSearchCommand hsc = null;
		HotelSearchRoomDetailTemp hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(searchkey);
		Company newCompany=companyDao.getCompany(appKeyVo.getCompanyId());
		String gstNo=newCompany!=null && newCompany.getCompanyGstIn()!=null?newCompany.getCompanyGstIn():null;
		try
		{
			logger.info("Searhing roomdetails summary controller : "+searchkey);
			hotelSearchRoomDetail =  hotelSearchRoomDetailDao.getHotelSearchRoomDetail(hotelSearchRoomDetail);
			//logger.info("Searhing roomdetails summary controller call: searchkey-"+searchkey+"---hotelSearchRoomDetail temp-"+hotelSearchRoomDetail.toString());
			if(hotelSearchRoomDetail == null || hotelSearchRoomDetail.getHotelsearch_cmd() == null)
				return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Room details Missing.. Search again.." )) ;

			hsc =  (HotelSearchCommand) SerializationUtils.deserialize(hotelSearchRoomDetail.getHotelsearch_cmd());
			ht = hotelTransactionDao.getHotelTransaction(searchkey);
			OTAHotelAvailRS.RoomStays.RoomStay rs = (OTAHotelAvailRS.RoomStays.RoomStay) SerializationUtils.deserialize(hotelSearchRoomDetail.getRoomstay());
			//com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = hotelObjectTransformer.convertTGtoNative(apiHotelMap.get(hotelcode));

			//List<String> roomTypeCodeList = getRoomTypeCodeList(roomtypecodes);
			//logger.info("Searhing roomdetails summary controller : roomtypecodes=="+roomtypecodes);
			//logger.info("Searhing roomdetails summary controller : no of roomtypecodes in list=="+roomTypeCodeList.size());


			String delimeter = "\\,";
			String[] bookingkeys = bookingkey.split(delimeter);

			logger.info("Searhing roomdetails summary controller : booking key== "+bookingkey);
			logger.info("Searhing roomdetails summary controller : bookingkeys== "+bookingkeys);

			rs = hotelObjectTransformer.getRoomDetailsSummary(rs, bookingkeys);

			//int apiProvider = (rs!=null && rs.getRoomRates()!=null && rs.getRoomRates().getRoomRates() !=null && rs!=null && rs.getRoomRates()!=null && rs.getRoomRates().getRoomRates().get(0)!=null)? rs.getRoomRates().getRoomRates().get(0).getApiProvider():-1;

			HotelApiCredentials apiauth = null;

			switch (rs.getBasicPropertyInfo().getApiProvider()) {
			case HotelApiCredentials.API_DESIA_IND:
				//
				break;
			case HotelApiCredentials.API_REZNEXT_IND:
				//
				break;
			case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
				apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);
				RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, null, null, null, null, null, null, null , apiauth, hsc, "RazLive Api");
				rs = rezLivePullerTask.getCancelPolicyRoomDetail(rs);
				break;
			case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:

				HotelApiCredentials apitayyarah = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL,appKeyVo);
				//TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apitayyarah, hsc, "Tayyarah Api");
				TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(BigInteger.valueOf(searchkey), apitayyarah, hsc, "Tayyarah Api");
				APIRoomDetail tayyarahroomdetail = tayyarahPullerTask.searchHotelRoomSummary(rs, bookingkey);
				rs = tayyarahroomdetail.getRs();
			case HotelApiCredentials.API_LINTAS_REPOSITORY:
				//Do Room availablity check of lintas Rposit hotels
				break;
			case HotelApiCredentials.API_LINTAS_INTERNATIONAL:
				//Do Room availablity check of lintas api hotels
				break;
			case HotelApiCredentials.API_TBO_INTERNATIONAL:
				//Do Room availablity check of lintas api hotels
				break;

			case HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL:
				break;

			default:
				break;
			}

			//markups = hotelMarkupDao.getHotelMarkups(companyId);
			Map<String,List<HotelMarkup>> markups = hsc.getHotelMarkupCommissionDetails().getMarkups();
			logger.info("HotelSearchCommand- no of markups----------:"+ markups.size()+"- for company id--"+appKeyVo.getCompanyId());
			rs = hotelAnalyzer.reDefineRoomDetailResponse(hsc, markups, rs);

			//rs = HotelMarkUpUtil.applyMarkUpOnHotelRoomDetail(hsc, markups, rs);
			logger.info("HotelSearchCommand- mark up applied for roomdetail....----------:"+ markups.size()+"- for company id--"+appKeyVo.getCompanyId());



			return new APIRoomDetail(hsc, rs, new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS),gstNo,BigInteger.valueOf(ht.getSearch_key()), BigInteger.valueOf(ht.getId()) ) ;

		}
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (ClassNotFoundException e) {
			logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (JAXBException e) {
			logger.info("query Exception:JAXBException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (UnsupportedOperationException e) {
			logger.info("query Exception:UnsupportedOperationException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (SOAPException e) {
			logger.info("query Exception:SOAPException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (Exception e) {
			logger.info("query Exception:Exception:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}

	}



	@RequestMapping(value = "/summary",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	APIRoomDetail getRoomStayDetailsSummary(@RequestParam(value="appkey") String appkey, @RequestParam(value="searchkey") Long searchkey,@RequestParam(value="hotelcode") String hotelcode, @RequestParam(value="bookingkey") String bookingkey, HttpServletResponse response) {
		logger.info("Searhing roomdetails controller call: searchkey-"+searchkey+"---hotelcode-"+hotelcode);
		ResponseHeader.setPostResponse(response);//Setting response header

		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}

		HotelTransactionTemp ht = new HotelTransactionTemp();
		Islhotelmapping islhotelmapping= null;
		HotelSearchCommand hsc = null;
		HotelSearchRoomDetailTemp hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(searchkey);
		Company newCompany=companyDao.getCompany(appKeyVo.getCompanyId());
		String gstNo=newCompany!=null && newCompany.getCompanyGstIn()!=null?newCompany.getCompanyGstIn():null;

		try
		{
			logger.info("Searhing roomdetails summary controller : "+searchkey);

			hotelSearchRoomDetail =  hotelSearchRoomDetailDao.getHotelSearchRoomDetail(hotelSearchRoomDetail);
			logger.info("Searhing roomdetails summary controller call: searchkey-"+searchkey+"---hotelSearchRoomDetail temp-"+hotelSearchRoomDetail.toString());
			if(hotelSearchRoomDetail == null || hotelSearchRoomDetail.getHotelsearch_cmd() == null)
				return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Room details Missing.. Search again.." )) ;

			hsc =  (HotelSearchCommand) SerializationUtils.deserialize(hotelSearchRoomDetail.getHotelsearch_cmd());
			ht = hotelTransactionDao.getHotelTransaction(searchkey);
			OTAHotelAvailRS.RoomStays.RoomStay rs = (OTAHotelAvailRS.RoomStays.RoomStay) SerializationUtils.deserialize(hotelSearchRoomDetail.getRoomstay());

			//com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = hotelObjectTransformer.convertTGtoNative(apiHotelMap.get(hotelcode));

			//List<String> roomTypeCodeList = getRoomTypeCodeList(roomtypecodes);
			//logger.info("Searhing roomdetails summary controller : roomtypecodes=="+roomtypecodes);
			//logger.info("Searhing roomdetails summary controller : no of roomtypecodes in list=="+roomTypeCodeList.size());


			String delimeter = "\\,";
			String[] bookingkeys = bookingkey.split(delimeter);

			logger.info("Searhing roomdetails summary controller : booking key== "+bookingkey);
			logger.info("Searhing roomdetails summary controller : bookingkeys== "+bookingkeys);

			rs = hotelObjectTransformer.getRoomDetailsSummary(rs, bookingkeys);
			HotelApiCredentials apiauth = null;

			switch (rs.getBasicPropertyInfo().getApiProvider()) {
			case HotelApiCredentials.API_DESIA_IND:
				//
				break;
			case HotelApiCredentials.API_REZNEXT_IND:
				//
				break;
			case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
				apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);
				RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, null, null, null, null, null, null, null , apiauth, hsc, "RazLive Api");
				rs = rezLivePullerTask.getCancelPolicyRoomDetail(rs);
				break;
			case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:

				HotelApiCredentials apitayyarah = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL,appKeyVo);
				//TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apitayyarah, hsc, "Tayyarah Api");
				TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(BigInteger.valueOf(searchkey), apitayyarah, hsc, "Tayyarah Api");
				APIRoomDetail tayyarahroomdetail = tayyarahPullerTask.searchHotelRoomSummary(rs, bookingkey);
				rs = tayyarahroomdetail.getRs();
			case HotelApiCredentials.API_LINTAS_REPOSITORY:
				//Do Room availablity check of lintas Rposit hotels
				break;
			case HotelApiCredentials.API_LINTAS_INTERNATIONAL:
				//Do Room availablity check of lintas api hotels
				break;
			case HotelApiCredentials.API_TBO_INTERNATIONAL:
				//Do Room availablity check of lintas api hotels
				break;
			default:
				break;
			}

			//markups = hotelMarkupDao.getHotelMarkups(companyId);
			Map<String,List<HotelMarkup>> markups = hsc.getHotelMarkupCommissionDetails().getMarkups();
			logger.info("HotelSearchCommand- no of markups----------:"+ markups.size()+"- for company id--"+appKeyVo.getCompanyId());
			rs = hotelAnalyzer.reDefineRoomDetailResponse(hsc, markups, rs);

			//rs = HotelMarkUpUtil.applyMarkUpOnHotelRoomDetail(hsc, markups, rs);
			logger.info("HotelSearchCommand- mark up applied for roomdetail....----------:"+ markups.size()+"- for company id--"+appKeyVo.getCompanyId());



			return new APIRoomDetail(hsc, rs, new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS),gstNo,BigInteger.valueOf(ht.getSearch_key()), BigInteger.valueOf(ht.getId()) ) ;

		}
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (ClassNotFoundException e) {
			logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (JAXBException e) {
			logger.info("query Exception:JAXBException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (UnsupportedOperationException e) {
			logger.info("query Exception:UnsupportedOperationException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (SOAPException e) {
			logger.info("query Exception:SOAPException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (Exception e) {
			logger.info("query Exception:Exception:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}

	}






	@RequestMapping(value = "/cancelpolicy",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	APIRoomDetail getRoomStayCancellationPolicies(@RequestParam(value="appkey") String appkey, @RequestParam(value="searchkey") Long searchkey,@RequestParam(value="hotelcode") String hotelcode, @RequestParam(value="bookingkey") String bookingkey, HttpServletResponse response) {

		logger.info("Searhing roomdetails controller call: searchkey-"+searchkey+"---hotelcode-"+hotelcode);
		ResponseHeader.setPostResponse(response);//Setting response header
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}


		HotelTransactionTemp ht = new HotelTransactionTemp();
		Islhotelmapping islhotelmapping= null;
		HotelSearchCommand hsc = null;
		HotelSearchRoomDetailTemp hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(searchkey);
		Company newCompany=companyDao.getCompany(appKeyVo.getCompanyId());
		String gstNo=newCompany!=null && newCompany.getCompanyGstIn()!=null?newCompany.getCompanyGstIn():null;

		try
		{
			logger.info("Searhing roomdetails summary controller : "+searchkey);

			hotelSearchRoomDetail =  hotelSearchRoomDetailDao.getHotelSearchRoomDetail(hotelSearchRoomDetail);
			logger.info("Searhing roomdetails summary controller call: searchkey-"+searchkey+"---hotelSearchRoomDetail temp-"+hotelSearchRoomDetail.toString());
			if(hotelSearchRoomDetail == null || hotelSearchRoomDetail.getHotelsearch_cmd() == null)
				return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Room details Missing.. Search again.." )) ;

			hsc =  (HotelSearchCommand) SerializationUtils.deserialize(hotelSearchRoomDetail.getHotelsearch_cmd());
			ht = hotelTransactionDao.getHotelTransaction(searchkey);
			OTAHotelAvailRS.RoomStays.RoomStay rs = (OTAHotelAvailRS.RoomStays.RoomStay) SerializationUtils.deserialize(hotelSearchRoomDetail.getRoomstay());
			//com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = hotelObjectTransformer.convertTGtoNative(apiHotelMap.get(hotelcode));

			//List<String> roomTypeCodeList = getRoomTypeCodeList(roomtypecodes);
			//logger.info("Searhing roomdetails summary controller : roomtypecodes=="+roomtypecodes);
			//logger.info("Searhing roomdetails summary controller : no of roomtypecodes in list=="+roomTypeCodeList.size());
			logger.info("Searhing roomdetails summary controller : booking key=="+bookingkey);
			rs = hotelObjectTransformer.getRoomDetailsSummary(rs,bookingkey);


			switch (rs.getBasicPropertyInfo().getApiProvider()) {
			case HotelApiCredentials.API_DESIA_IND:
				break;
			case HotelApiCredentials.API_REZNEXT_IND:
				break;
			case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
				HotelApiCredentials apireztlive = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);
				RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apireztlive, hsc, "RazLive Api");
				//rezLivePullerTask.getCancelPolicyRoomDetail(rs);

				break;
			default:
				break;
			}



			return new APIRoomDetail(hsc, rs, new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS),gstNo,BigInteger.valueOf(ht.getSearch_key()), BigInteger.valueOf(ht.getId()) ) ;

		}
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (ClassNotFoundException e) {
			logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (JAXBException e) {
			logger.info("query Exception:JAXBException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (UnsupportedOperationException e) {
			logger.info("query Exception:UnsupportedOperationException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (SOAPException e) {
			logger.info("query Exception:SOAPException:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (Exception e) {
			logger.info("query Exception:Exception:"+ e.getMessage());
			return new APIRoomDetail(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}

	}



	@RequestMapping(value = "/test",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	TreeMap<String, RoomStay> getRoomStayDetailsList(@RequestParam(value="appkey") String appkey, @RequestParam(value="searchkey") Long searchkey, HttpServletResponse response) {

		//Enable the below line to validat app key
		//String encryptedkey = AppControllerUtil.getDecryptedAppKey(CDAO, appkey);


		ResponseHeader.setResponse(response);//Setting response header
		HotelSearchTemp hs = new HotelSearchTemp();
		TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay> apiHotelMap = new TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay>();
		Islhotelmapping islhotelmapping= null;
		HotelSearchCommand hsc = null;

		try
		{
			/*islhotelmapping = islhotelmappingDao.getHotelByISLVendorID(hotelcode);
			//compare rate and replace the roomstay item
			if(islhotelmapping != null && islhotelmapping.getTGVendorID() != null)
			{
				logger.info("Searhing roomdetails controller : "+searchkey);
				apiHotelMap =apihotelstoredao.getTGRoomStaysMap(searchkey);
				return apiHotelMap.get(islhotelmapping.getTGVendorID());
				//apiHotelMap = (APIHotelMap) ois.readObject();
			}
			 */
			logger.info("Searhing roomdetails summary controller : "+searchkey);
			hs = hotelSearchDao.getHotelSearch(searchkey);
			hsc =  (HotelSearchCommand) SerializationUtils.deserialize(hs.getHotelsearch_cmd());
			apiHotelMap = (TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay>) SerializationUtils.deserialize(hs.getHotelres_map());


			return apiHotelMap;

		}
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
			return null;

		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
			return null;

		}

	}





	public static List<String> getRoomTypeCodeList(String roomtypecodetext)
	{
		//roomtypecodetext=13456,13457,13458..
		List<String> roomTypeCodeList =new ArrayList<String>();
		if(roomtypecodetext == null || roomtypecodetext.length() == 0)
			return roomTypeCodeList;
		else
		{
			String temp = roomtypecodetext;
			while(temp.length() >= 2)
			{
				if(!temp.contains(","))
				{
					roomTypeCodeList.add(temp);
					temp = "";
					break;
				}
				else
				{
					int roomTypeCodeEndindex = temp.indexOf(",");
					String roomTypeCode = temp.substring(0,roomTypeCodeEndindex-1);
					temp = temp.substring(roomTypeCodeEndindex+1);
					roomTypeCodeList.add(roomTypeCode);
				}
			}

		}
		return roomTypeCodeList;
	}



}
