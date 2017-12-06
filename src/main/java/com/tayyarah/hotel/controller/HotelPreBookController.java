package com.tayyarah.hotel.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tayyarah.common.dao.RmConfigDetailDAO;
import com.tayyarah.common.entity.ApiProviderPaymentTransaction;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.entity.RmConfigTripDetailsModel;
import com.tayyarah.common.exception.BaseException;
import com.tayyarah.common.exception.RestError;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.notification.NotificationUtil;
import com.tayyarah.common.notification.dao.NotificationDao;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.ApiResponseSaver;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.common.util.FileUtil;
import com.tayyarah.common.util.GetFrontUserDetail;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.common.util.enums.InventoryTypeEnum;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.email.dao.EmailDaoImp;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.hotel.dao.HotelBookingDao;
import com.tayyarah.hotel.dao.HotelOrderDao;
import com.tayyarah.hotel.dao.HotelSearchRoomDetailDao;
import com.tayyarah.hotel.dao.HotelTransactionDao;
import com.tayyarah.hotel.entity.HotelBookingTemp;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.entity.HotelSearchRoomDetailTemp;
import com.tayyarah.hotel.entity.HotelTransactionTemp;
import com.tayyarah.hotel.entity.Islhotelmapping;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.HotelBookCommand;

import com.tayyarah.hotel.model.HotelSearchCommand;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelResRS;
import com.tayyarah.hotel.model.TotalType;
import com.tayyarah.hotel.model.UniqueIDType;
import com.tayyarah.hotel.quotation.dao.HotelTravelRequestDao;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.BookService;
import com.tayyarah.hotel.util.CurrencyManager;
import com.tayyarah.hotel.util.HotelBookRequestBuilder;
import com.tayyarah.hotel.util.HotelIdFactoryImpl;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.TGRequestBuilder;
import com.tayyarah.hotel.util.api.concurrency.AsyncSupport;
import com.tayyarah.hotel.util.api.concurrency.DesiyaPullerTask;
import com.tayyarah.hotel.util.api.concurrency.LintasHotelRepositPullerTask;
import com.tayyarah.hotel.util.api.concurrency.RezLivePullerTask;
import com.tayyarah.hotel.util.api.concurrency.RezNextPullerTask;
import com.tayyarah.hotel.util.api.concurrency.TBOPullerTask;
import com.tayyarah.hotel.util.api.concurrency.TayyarahPullerTask;
import com.tayyarah.hotel.validator.HotelServiceEndPointValidator;
import com.tayyarah.services.HotelRepositService;
import com.tayyarah.user.dao.FrontUserDao;
import com.tayyarah.user.dao.UserWalletDAO;
import com.tayyarah.user.entity.FrontUserDetail;


@RestController
@RequestMapping("/hotel/prebook")
public class HotelPreBookController {
	@Autowired
	HotelObjectTransformer hotelObjectTransformer;
	@Autowired
	HotelTransactionDao hotelTransactionDao;	
	@Autowired
	HotelOrderDao hotelOrderDao;
	@Autowired
	AsyncSupport asyncSupport;
	@Autowired
	CompanyConfigDAO companyConfigDAO;
	@Autowired
	FlightBookingDao flightBookingDao;
	@Autowired
	CompanyDao companyDao;
	@Autowired
	UserWalletDAO userWalletDAO;
	@Autowired
	HotelBookingDao hotelBookingDao;
	@Autowired
	HotelSearchRoomDetailDao hotelSearchRoomDetailDao;
	HotelIdFactoryImpl hotelIdFactory;
	@Autowired
	EmailDaoImp emaildao;
	@Autowired
	BookService hotelbookservice;
	@Autowired
	HotelRepositService hotelTayyarahRepositService;
	@Autowired
	CurrencyManager currencyManager;
	@Autowired
	NotificationDao NFDAO;
	@Autowired
	HotelTravelRequestDao hotelTravelRequestDao;
	@Autowired
	FrontUserDao frontUserDao;
	@Autowired
	RmConfigDetailDAO rmConfigDetailDAO;


	public static final Logger logger = Logger.getLogger(HotelPreBookController.class);
	private  HotelServiceEndPointValidator validator = new HotelServiceEndPointValidator();

	@RequestMapping(value = "/updateroom",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	String insertemail(HttpServletRequest request,HttpServletResponse response) {
		hotelOrderDao.updateRoomsBookStatus("HO14484150035180", "Booked");
		return "success";
	}

	@RequestMapping(value = "/bookagain",method = RequestMethod.GET, headers = {"Accept=application/json"})
	public @ResponseBody APIHotelBook preBookRoomsAfterPriceChanged(@RequestParam(value="appkey") String appkey, @RequestParam(value="searchkey") Long searchkey,@RequestParam(value="hotelcode") String hotelcode, @RequestParam(value="orderid") String orderid,HttpServletResponse response) {

		logger.info("HotelPreBookController call: searchkey-"+searchkey+"---hotelcode-"+hotelcode);
		ResponseHeader.setPostResponse(response);//Setting response header
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}
		hotelIdFactory = HotelIdFactoryImpl.getInstance();
		HotelBookingTemp hotelBookingTemp = new HotelBookingTemp();
		HotelSearchRoomDetailTemp hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(searchkey);
		TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay> apiHotelMap = new TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay>();
		Islhotelmapping islhotelmapping= null;
		HotelTransactionTemp hotelTransactionTemp = new HotelTransactionTemp();
		HotelSearchCommand hotelSearchCommand = null;
		OTAHotelResRS otaHotelResRS = new OTAHotelResRS();
		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();
		APIHotelBook apiHotelBook = new APIHotelBook();

		String correlationid = "";
		String transactionid = "";
		String paymentid = "";

		try
		{
			HotelBookCommand hotelBookCommand = null;
			HotelOrderRow hotelOrderRow = null;
			hotelOrderRow = hotelOrderDao.getHotelOrderRow(orderid);
			logger.info("HotelBookingController----relevent hotel order from db: hor=="+ hotelOrderRow);

			hotelBookingTemp = hotelBookingDao.getHotelBooking(orderid);
			//logger.info("HotelBookingController----relevent hotel booking from db: hb=="+ hb);

			OTAHotelAvailRS.RoomStays.RoomStay rs = (OTAHotelAvailRS.RoomStays.RoomStay) SerializationUtils.deserialize(hotelBookingTemp.getRoomstay());
			logger.info("HotelBookingController----relevent room stay from db: hb=="+ rs);

			totaHotelResRS = (OTAHotelResRS) SerializationUtils.deserialize(hotelBookingTemp.getPrebook_res());
			logger.info("HotelBookingController----relevent prebook res from db: totaHotelResRS=="+ totaHotelResRS);

			hotelBookCommand = (HotelBookCommand) SerializationUtils.deserialize(hotelBookingTemp.getHotelbook_cmd());
			logger.info("HotelBookingController----relevent HotelBookCommand res from db: hbc=="+ hotelBookCommand);

			hotelSearchRoomDetail =  hotelSearchRoomDetailDao.getHotelSearchRoomDetail(hotelSearchRoomDetail);
			if(hotelSearchRoomDetail == null || hotelSearchRoomDetail.getHotelsearch_cmd() == null)
				return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Room details Missing.. Search again.." )) ;

			hotelSearchCommand =  (HotelSearchCommand) SerializationUtils.deserialize(hotelSearchRoomDetail.getHotelsearch_cmd());
			//	com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = (com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay) SerializationUtils.deserialize(hotelSearchRoomDetail.getRoomstay());


			hotelTransactionTemp = hotelTransactionDao.getHotelTransaction(hotelBookCommand.getSearchKey());
			logger.info("HotelPreBookController : ht---"+hotelTransactionTemp);
			logger.info("HotelPreBookController : total rs---"+rs);

			hotelBookCommand = HotelBookCommand.initRoomGuestProfilesUniques(hotelBookCommand, hotelIdFactory);
			String delimeterbookingkey = "\\,";
			String[] bookingkeys = hotelBookCommand.getBookingCode().split(delimeterbookingkey);

			logger.info("HotelPreBookController----bookingkeys----:"+Arrays.toString(bookingkeys));

			rs = hotelObjectTransformer.getRoomDetailsSummary(rs, bookingkeys);

			ObjectMapper mapper = new ObjectMapper();
			String roomsummary = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rs);
			logger.info("-------------(((((--roomsummary:"+roomsummary);

			FileUtil.writeJson("hotel", "tbo", "roomsummary", false, rs, String.valueOf(hotelBookCommand.getSearchKey()));

			//rs = hotelObjectTransformer.getRoomDetailsSummary(rs, hbc.getBookingCode());

			logger.info("HotelPreBookController----initRoomGuestProfilesUniques----:");



			int noofdays = 1;
			int noofrooms = 1;
			noofrooms = hotelSearchCommand.getNoofrooms();
			HotelApiCredentials apiauth = null;
			//	HotelOrderRow hor = null;

			switch (rs.getBasicPropertyInfo().getApiProvider()) {
			case HotelApiCredentials.API_DESIA_IND:
				logger.info("HotelPreBookController----desiya prebooking----:");
				//rs = hotelObjectTransformer.getRoomStayBooking(rs,hbc.getRoomRateTypes());
				logger.info("HotelPreBookController----desiya prebooking----:rs--"+rs);

				boolean canReserveRooms = true;
				TotalType revisedRate = null;

				int bookingCountAttempt = 1;
				while(canReserveRooms)
				{
					logger.info("HotelPreBookController---booking TG attempt:"+ bookingCountAttempt);

					//com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = hotelObjectTransformer.convertTGtoNative(apiHotelMap.get(hotelcode));
					apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);
					apiHotelBook = new APIHotelBook(hotelSearchCommand, hotelBookCommand, new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS), null, BigInteger.valueOf(hotelTransactionTemp.getSearch_key()),  BigInteger.valueOf(hotelTransactionTemp.getId()), new TotalType(), rs ) ;
					noofdays = CommonUtil.getNoofStayDays(hotelSearchCommand);
					apiHotelBook.initRate(rs, noofdays,noofrooms);
					logger.info("HotelPreBookController----rate calculation been over:"+ apiHotelBook.getBookingRate().toString());

					FileUtil.writeJson("hotel", "desiya", "api-hotel-book-", false, apiHotelBook, String.valueOf(hotelBookCommand.getSearchKey()));

					revisedRate = hotelObjectTransformer.getReviseRateTotal(totaHotelResRS);

					/*if(bookingCountAttempt == 1)
						hor = CommonUtil.hotelOrderInsertionData(hotelOrderDao, ht, hsc, hbc, rs, apiHotelBook);
					logger.info("HotelPreBookController----hor inserted:"+ hor.getId());*/

					StringBuilder reqbook = TGRequestBuilder.getProvisionalBookingReq(apiauth, hotelTransactionTemp, hotelSearchCommand, hotelBookCommand, rs, apiHotelBook.getApiRate(), hotelOrderRow, revisedRate);
					//logger.info("HotelPreBookController---- reqbook:"+ reqbook);
					String reqbookxml = CommonUtil.format(reqbook.toString());
					logger.info("HotelPreBookController---- reqbookxml:"+ reqbookxml);
					reqbookxml = reqbookxml.replace(HotelBookRequestBuilder.XML_HEADER_FORMATTED, HotelBookRequestBuilder.SOAP_HEADER_PREBOOK_TG);
					reqbookxml+=HotelBookRequestBuilder.SOAP_FOOTER_PREBOOK_TG;
					StringBuilder sdb = new StringBuilder(reqbookxml);
					logger.info("HotelPreBookController---- soap reqbookxml:"+ sdb.toString());
					ByteArrayInputStream is = new ByteArrayInputStream(sdb.toString().getBytes());
					MimeHeaders header=new MimeHeaders();
					header.addHeader("Content-Type","application/soap+xml");
					SOAPMessage sm = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL).createMessage(header, is);
					logger.info("HotelPreBookController---- otaHotelResRS:"+ otaHotelResRS.toString());

					//totaHotelResRS = hotelObjectTransformer.convertTGtoNativePreBookResponse(otaHotelResRS);
					DesiyaPullerTask desiyaPullerTask = new DesiyaPullerTask(null, hotelObjectTransformer, null, null, null, null, null, null, null , apiauth, apiHotelBook.getSearch(), "Desiya Api", null);
					apiHotelBook = desiyaPullerTask.provisionalBookingHotelDesia(revisedRate, apiHotelBook, rs,apiauth.getEndPointUrl()+"TGBookingServiceEndPoint", sm);
					totaHotelResRS = apiHotelBook.getPreBookRes();



					if(revisedRate != null)
						logger.info("HotelPreBookController before prebook response----transformed revisedRate:"+ revisedRate.toString());

					logger.info("HotelPreBookController----transformed otaHotelResRS:"+ totaHotelResRS);
					//revisedRate = hotelObjectTransformer.getNewRateTotal(apiHotelBook.getPreBookRes());

					logger.info("HotelPreBookController----revisedRate ="+ revisedRate);
					if(revisedRate != null)
						canReserveRooms = false;
					else
						canReserveRooms = false;
					bookingCountAttempt++;
				}


				break;
			case HotelApiCredentials.API_REZNEXT_IND:
				//com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = hotelObjectTransformer.convertTGtoNative(apiHotelMap.get(hotelcode));
				apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND,appKeyVo);
				apiHotelBook = new APIHotelBook(hotelSearchCommand, hotelBookCommand, new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS), new OTAHotelResRS(), BigInteger.valueOf(hotelTransactionTemp.getSearch_key()),  BigInteger.valueOf(hotelTransactionTemp.getId()), new TotalType(), rs ) ;
				noofdays = CommonUtil.getNoofStayDays(hotelSearchCommand);
				apiHotelBook.initRate(rs, noofdays,noofrooms);

				logger.info("HotelPreBookController----rate calculation been over:"+ apiHotelBook.getBookingRate().toString());

				//	hor = CommonUtil.hotelOrderInsertionData(hotelOrderDao, ht, hsc, hbc, rs, apiHotelBook);
				logger.info("HotelPreBookController----hor inserted:"+ hotelOrderRow.getId());
				RezNextPullerTask rezNextPullerTask = new RezNextPullerTask(hotelObjectTransformer, null, null, null, null, null, null, null , apiauth, apiHotelBook.getSearch(), "Desiya Api");
				apiHotelBook = rezNextPullerTask.getMockPreBookingRes(apiauth, hotelObjectTransformer, apiHotelBook);
				totaHotelResRS = apiHotelBook.getPreBookRes();

				break;
			case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
				apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);
				//RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(apiauth, hsc, "RazLive Api");
				RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, null, null, null, null, null, null, null , apiauth, hotelSearchCommand, "RazLive Api");

				apiHotelBook = new APIHotelBook(hotelSearchCommand, hotelBookCommand, new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR), null, BigInteger.valueOf(hotelTransactionTemp.getSearch_key()),  BigInteger.valueOf(hotelTransactionTemp.getId()), new TotalType(), rs ) ;
				logger.info("HotelPreBookController---final apiHotelBook:"+ apiHotelBook.toString());

				noofdays = CommonUtil.getNoofStayDays(hotelSearchCommand);
				logger.info("HotelPreBookController----noofdays:"+ noofdays);

				apiHotelBook.initRate(rs, noofdays,noofrooms);

				//	hor = CommonUtil.hotelOrderInsertionData(hotelOrderDao, ht, hsc, hbc, rs, apiHotelBook);

				logger.info("HotelPreBookController----noofdays:"+ noofdays);

				apiHotelBook = rezLivePullerTask.preBook(apiHotelBook, hotelBookCommand, rs, hotelIdFactory);
				totaHotelResRS = apiHotelBook.getPreBookRes();

				break;
			case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:
				apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL,appKeyVo);

				apiHotelBook = new APIHotelBook(hotelSearchCommand, hotelBookCommand, new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR), null, BigInteger.valueOf(hotelTransactionTemp.getSearch_key()),  BigInteger.valueOf(hotelTransactionTemp.getId()), new TotalType(), rs ) ;
				logger.info("HotelPreBookController---final apiHotelBook:"+ apiHotelBook.toString());

				noofdays = CommonUtil.getNoofStayDays(hotelSearchCommand);
				logger.info("HotelPreBookController----noofdays:"+ noofdays);

				apiHotelBook.initRate(rs, noofdays,noofrooms);

				//	hor = CommonUtil.hotelOrderInsertionData(hotelOrderDao, ht, hsc, hbc, rs, apiHotelBook);

				logger.info("HotelPreBookController----noofdays:"+ noofdays);


				//TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apitayyarah, hsc, "Tayyarah Api");
				TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(BigInteger.valueOf(hotelBookCommand.getSearchKey()), apiauth, hotelSearchCommand, "Tayyarah Api");
				apiHotelBook = tayyarahPullerTask.doPrebook(apiHotelBook);
				totaHotelResRS = apiHotelBook.getPreBookRes();
				break;
			case HotelApiCredentials.API_LINTAS_REPOSITORY:
				apiauth = HotelApiCredentials.getApiLintasReposit();
				//RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(apiauth, hsc, "RazLive Api");
				LintasHotelRepositPullerTask lintasHotelRepositPullerTask = new LintasHotelRepositPullerTask(apiauth, hotelSearchCommand, apiauth.getApiProviderName());

				apiHotelBook = new APIHotelBook(hotelSearchCommand, hotelBookCommand, new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR), null, BigInteger.valueOf(hotelTransactionTemp.getSearch_key()),  BigInteger.valueOf(hotelTransactionTemp.getId()), new TotalType(), rs ) ;
				logger.info("HotelPreBookController---final apiHotelBook:"+ apiHotelBook.toString());

				noofdays = CommonUtil.getNoofStayDays(hotelSearchCommand);
				logger.info("HotelPreBookController----noofdays:"+ noofdays);

				apiHotelBook.initRate(rs, noofdays,noofrooms);

				//	hor = CommonUtil.hotelOrderInsertionData(hotelOrderDao, ht, hsc, hbc, rs, apiHotelBook);

				logger.info("HotelPreBookController----noofdays:"+ noofdays);

				apiHotelBook = lintasHotelRepositPullerTask.doPrebook(apiHotelBook);
				totaHotelResRS = apiHotelBook.getPreBookRes();

				break;
			case HotelApiCredentials.API_LINTAS_INTERNATIONAL:
				//Do Room availablity check of lintas api hotels
				break;
			case HotelApiCredentials.API_TBO_INTERNATIONAL:
				HotelApiCredentials apitbo = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);
				TBOPullerTask tboPullerTask = new TBOPullerTask(null, hotelObjectTransformer, null, null, null, null, null, null, null , apitbo, hotelSearchCommand, "TBO Api", null);

				apiHotelBook = new APIHotelBook(hotelSearchCommand, hotelBookCommand, new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR), null, BigInteger.valueOf(hotelTransactionTemp.getSearch_key()),  BigInteger.valueOf(hotelTransactionTemp.getId()), new TotalType(), rs ) ;
				logger.info("HotelPreBookController---final apiHotelBook:"+ apiHotelBook.toString());

				noofdays = CommonUtil.getNoofStayDays(hotelSearchCommand);
				logger.info("HotelPreBookController----noofdays:"+ noofdays);

				apiHotelBook.initRate(rs, noofdays,noofrooms);



				//	hor = CommonUtil.hotelOrderInsertionData(hotelOrderDao, ht, hsc, hbc, rs, apiHotelBook);

				logger.info("HotelPreBookController----noofdays...:"+ noofdays);

				apiHotelBook = tboPullerTask.preBook(apiHotelBook, hotelBookCommand, rs, hotelIdFactory,companyDao);
				totaHotelResRS = apiHotelBook.getPreBookRes();



				break;

			case HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL:
				HotelApiCredentials apiTayyarahRepost = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL,appKeyVo);

				apiHotelBook = new APIHotelBook(hotelSearchCommand, hotelBookCommand, new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR), null, BigInteger.valueOf(hotelTransactionTemp.getSearch_key()),  BigInteger.valueOf(hotelTransactionTemp.getId()), new TotalType(), rs ) ;
				logger.info("HotelPreBookController---final apiHotelBook:"+ apiHotelBook.toString());
				noofdays = CommonUtil.getNoofStayDays(hotelSearchCommand);
				logger.info("HotelPreBookController----noofdays:"+ noofdays);
				apiHotelBook.initRate(rs, noofdays,noofrooms);
				hotelOrderRow = CommonUtil.hotelOrderRowInsertionData(hotelOrderDao, hotelTransactionTemp, hotelSearchCommand, hotelBookCommand, rs, apiHotelBook,companyConfigDAO ,appKeyVo,companyDao,frontUserDao,rmConfigDetailDAO);
				logger.info("HotelPreBookController----noofdays...:"+ noofdays);
				apiHotelBook = hotelTayyarahRepositService.preBook(apiHotelBook, hotelBookCommand, rs, hotelIdFactory);
				totaHotelResRS = apiHotelBook.getPreBookRes();

				break;

			default:
				break;
			}


			String apibook = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			logger.info("-------------(((((--apiHotelBook:"+apiHotelBook);

			FileUtil.writeJson("hotel", "tbo", "apibook-prebook", false, apiHotelBook, String.valueOf(hotelBookCommand.getSearchKey()));


			//apiHotelBook.setPreBookRes(totaHotelResRS);
			//apiHotelBook = hotelObjectTransformer.getRoomDetailsSummaryBooking(apiHotelBook, rs);
			//apiHotelBook = hotelObjectTransformer.getRoomDetailsSummaryBooking(apiHotelBook, rs);
			//payBooking(apiHotelBook, rs);
			if((apiHotelBook.getPreBookRes().getStatus().getCode().equals(APIStatus.STATUS_CODE_SUCCESS)))
				apiHotelBook = currencyManager.fillCurrencyOnPreBookResponse(apiHotelBook,hotelOrderRow);
			FileUtil.writeJson("hotel", "tbo", "apibook-prebook-after-markup", false, apiHotelBook, String.valueOf(hotelBookCommand.getSearchKey()));


			totaHotelResRS = apiHotelBook.getPreBookRes();


			byte[] hotelbook_cmddata = SerializationUtils.serialize(hotelBookCommand);
			byte[] roomstaydata = SerializationUtils.serialize(apiHotelBook.getRoomsummary());
			byte[] prebook_resdata = SerializationUtils.serialize(totaHotelResRS);

			hotelBookingTemp.setOrderId(orderid);
			hotelBookingTemp.setHotelbook_cmd(hotelbook_cmddata);
			hotelBookingTemp.setRoomstay(roomstaydata);
			hotelBookingTemp.setPrebook_res(prebook_resdata);
			hotelBookingTemp.setBook_res(null);

			hotelBookingTemp = hotelBookingDao.insertHotelBooking(hotelBookingTemp);

			UniqueIDType uniqueid = new UniqueIDType();
			uniqueid = CommonUtil.getApiUniqueId(totaHotelResRS);
			hotelOrderRow = CommonUtil.hotelOrderUpdationDataPreBook(hotelOrderDao, hotelOrderRow, hotelTransactionTemp, apiHotelBook, "Reservation Failed", "Pending", "Reservation");

			logger.info("prebooking over..  booking system type----:"+apiHotelBook.getBook().getBookingSystemType());
			logger.info("prebooking over..  before book call--11----uniqueid:"+uniqueid);
			logger.info("prebooking over..  before book call--11--apiHotelBook:"+apiHotelBook);
			logger.info("prebooking over..  before book call--11--apiHotelBook.getBookRes():"+apiHotelBook.getBookRes());
			logger.info("prebooking over..  before book call--11--apiHotelBook.getBookRes().getStatus():"+apiHotelBook.getBookRes().getStatus());
			logger.info("prebooking over..  before book call--11--apiHotelBook.getBookRes().getStatus() msg:"+apiHotelBook.getBookRes().getStatus().getMessage());

			logger.info("prebooking over..  before book call--11--apiHotelBook.getPreBookRes():"+apiHotelBook.getPreBookRes());
			logger.info("prebooking over..  before book call--11--apiHotelBook.getPreBookRes().getStatus():"+apiHotelBook.getPreBookRes().getStatus());
			logger.info("prebooking over..  before book call--11--apiHotelBook.getPreBookRes().getStatus() msg:"+apiHotelBook.getPreBookRes().getStatus().getMessage());

			logger.info("prebooking over..  before book call--11--apiHotelBook.getStatus():"+apiHotelBook.getStatus());
			logger.info("prebooking over..  before book call--11--apiHotelBook.getStatus() status msg:"+apiHotelBook.getStatus().getMessage());



			//if((uniqueid != null) && (uniqueid.getID().length()>0) && (totaHotelResRS.getStatus().getCode().equals(APIStatus.STATUS_CODE_SUCCESS)))

			// Check is price changes and price is not changed allow to book
			if((totaHotelResRS.getStatus().getCode().equals(APIStatus.STATUS_CODE_SUCCESS)) && !totaHotelResRS.isPriceChanged())
			{
				PaymentTransaction paymentTransaction=new PaymentTransaction();
				paymentTransaction.setAmount(AmountRoundingModeUtil.roundingModeForHotel(apiHotelBook.getBookingRate().getPayableAmt().add(hotelOrderRow.getFeeAmount())));
				paymentTransaction.setCurrency(apiHotelBook.getSearch().getCurrency());
				paymentTransaction.setRefno(apiHotelBook.getBook().getPaymentid());
				paymentTransaction.setIsPaymentSuccess(false);
				paymentTransaction.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
				paymentTransaction.setPayment_method(apiHotelBook.getBook().getPayBy());
				paymentTransaction.setApi_transaction_id(apiHotelBook.getBook().getOrderid());
				paymentTransaction.setPayment_status("Pending");
				paymentTransaction.setTransactionId(apiHotelBook.getBook().getOrderid());
				paymentTransaction.setResponse_message("NA");
				paymentTransaction.setResponseCode("NA");
				paymentTransaction.setAuthorizationCode(apiHotelBook.getBook().getTransactionid());

				//storing paymentgateway transaction in db
				hotelbookservice.updatePaymentTransaction(paymentTransaction);

				hotelOrderRow = CommonUtil.hotelOrderUpdationDataPreBook(hotelOrderDao, hotelOrderRow, hotelTransactionTemp, apiHotelBook, "Reserved", "Pending", "Reservation");

				if(apiHotelBook.getBook().getBookingSystemType().equalsIgnoreCase("ibe"))
				{					
					if( apiHotelBook.getBook().getPayBy().equals("cash"))
					{						
						apiHotelBook = hotelbookservice.payWalletBooking(totaHotelResRS, hotelTransactionTemp, hotelBookingTemp, hotelOrderRow, paymentTransaction, uniqueid, apiHotelBook, rs,appKeyVo);
						logger.info("HotelPreBookController----payWalletBooking uniqueid:"+uniqueid);
						if(totaHotelResRS.getStatus().getCode() == APIStatus.STATUS_CODE_SUCCESS){
							new NotificationUtil().insertNotification(appKeyVo,orderid , "Hotel Booking", InventoryTypeEnum.HOTEL_ORDER.getId(), true,NFDAO,companyDao);
						}
					}
					else
					{
						APIStatus status = new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS + " Try Payment through card..");
						OTAHotelResRS totaHotelResRSBook = new OTAHotelResRS();
						totaHotelResRSBook.setStatus(status);
						apiHotelBook.setStatus(status);
						apiHotelBook.setBookRes(totaHotelResRSBook);
					}
				}
				else
				{
					logger.info("prebooking over..  booking system type----:"+apiHotelBook.getBook().getBookingSystemType());

				}

				//apiHotelBook = callAPIBook(ht, hs, hb, hor, uniqueid, apiHotelBook, rs);
			}
			else
			{
				APIStatus status = null;
				if(totaHotelResRS.isPriceChanged())
					status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " Sorry,the rates of the room you selected has changed." );
				else
					status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Something wrong with input details... Reservation failed." );

				OTAHotelResRS totaHotelResRSBook = new OTAHotelResRS();
				totaHotelResRSBook.setStatus(status);
				apiHotelBook.setStatus(status);
				apiHotelBook.setBookRes(totaHotelResRSBook);
				//apiHotelBook.setStatus(status);
				//apiHotelBook.setBookRes(totaHotelResRSBook);
			}

			// Insert Hotel Api Response in the flight_hotel_book_api_response table
			ApiResponseSaver.saveHotelApiResponse(apiHotelBook,hotelOrderRow,flightBookingDao);

			// if quotation is true
			if(apiHotelBook.getBookRes().getStatus().getCode() == APIStatus.STATUS_CODE_SUCCESS){
				if(hotelBookCommand.isQuotation()) {
					hotelTravelRequestDao.updateHotelRequestQuotationWithOrderId(hotelOrderRow.getId(),Long.valueOf(hotelBookCommand.getQuotationId()));
					ApiProviderPaymentTransaction apiProviderPaymentTransaction = new ApiProviderPaymentTransaction();
					apiProviderPaymentTransaction.setAmount(hotelOrderRow.getFinalPrice());
					apiProviderPaymentTransaction.setApi_transaction_id(hotelOrderRow.getOrderReference());
					apiProviderPaymentTransaction.setCurrency(hotelOrderRow.getApiCurrency());
					apiProviderPaymentTransaction.setCreatedAt(hotelOrderRow.getCreatedAt());
					apiProviderPaymentTransaction.setPayment_system("Full");
					apiProviderPaymentTransaction.setPayment_status(hotelOrderRow.getPaymentStatus());
					apiProviderPaymentTransaction.setIsPaymentSuccess(true);
					hotelTravelRequestDao.insertSupplierPaymentTransactionInfo(apiProviderPaymentTransaction);
				}

				try{
					userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(hotelOrderRow.getOrderReference(), apiHotelBook.getBookRes().getInvoiceNo());
				}catch(Exception e){
					logger.error("walletTransferHistoryUpdateWithInvoiceNo Exception", e);
				}
			}


			//return apiHotelBook;

		}
		catch (HibernateException e) {
			//logger.info("query Exception:HibernateException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (IOException e) {
			//logger.info("query Exception:IOException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (ClassNotFoundException e) {
			//logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (JAXBException e) {
			//logger.info("query Exception:JAXBException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (UnsupportedOperationException e) {
			//logger.info("query Exception:UnsupportedOperationException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (SOAPException e) {
			//logger.info("query Exception:SOAPException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (Exception e) {
			//logger.info("Exception:Exception:"+ e.getMessage());
			//logger.info("Exception:Exception:localised"+ e.getLocalizedMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}


		return apiHotelBook;
	}


	@RequestMapping(value = "",method = RequestMethod.POST, headers = {"Accept=application/json"})
	public @ResponseBody APIHotelBook preBookRooms(@RequestBody HotelBookCommand hotelBookCommand, HttpServletResponse response) {

		logger.info("HotelPreBookController call: searchkey-"+hotelBookCommand.getSearchKey()+"---hotelcode-"+hotelBookCommand.getHotelCode());
		//HotelBookCommand hbc = new HotelBookCommand(1);
		ResponseHeader.setPostResponse(response);//Setting response header
		hotelBookCommand = validator.validate(hotelBookCommand);
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, hotelBookCommand.getAppkey());
		if(appKeyVo==null)
		{
			new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}
		if(hotelBookCommand.getQuotationId() != -1){
			hotelBookCommand.setQuotation(true);
		}

		hotelIdFactory = HotelIdFactoryImpl.getInstance();
		//ResponseHeader.setResponse(response);//Setting response header
		HotelBookingTemp hotelBookingTemp = new HotelBookingTemp();
		//HotelSearch hs = new HotelSearch();
		HotelSearchRoomDetailTemp hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(hotelBookCommand.getSearchKey());
		TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay> apiHotelMap = new TreeMap<String, OTAHotelAvailRS.RoomStays.RoomStay>();
		Islhotelmapping islhotelmapping= null;
		HotelTransactionTemp hotelTransactionTemp = new HotelTransactionTemp();
		HotelSearchCommand hotelSearchCommand = null;
		OTAHotelResRS otaHotelResRS = new OTAHotelResRS();
		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();
		APIHotelBook apiHotelBook = new APIHotelBook();
		String orderid = "";
		String correlationid = "";
		String transactionid = "";
		String paymentid = "";
		HotelOrderRow hor = null;

		try
		{
			hotelBookCommand.setCompanyId(appKeyVo.getCompanyId());
			hotelBookCommand.setConfigId(appKeyVo.getConfigId());

			orderid = hotelIdFactory.createLongId("HO");
			paymentid = hotelIdFactory.createLongId("PGH");
			correlationid = hotelIdFactory.createShortId("");
			transactionid = hotelIdFactory.createShortId("");
			hotelBookCommand.setOrderid(orderid);
			hotelBookCommand.setPaymentid(paymentid);
			hotelBookCommand.setCorrelationid(correlationid);
			hotelBookCommand.setTransactionid(transactionid);
			hotelBookCommand.setPayAttemptCount(0);

			logger.info("HotelPreBookController : "+hotelBookCommand.getSearchKey());
			//hs = hotelSearchDao.getHotelSearch(BigInteger.valueOf(hbc.getSearchKey()));

			hotelSearchRoomDetail =  hotelSearchRoomDetailDao.getHotelSearchRoomDetail(hotelSearchRoomDetail);
			if(hotelSearchRoomDetail == null || hotelSearchRoomDetail.getHotelsearch_cmd() == null)
				return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Room details Missing.. Search again.." )) ;

			hotelSearchCommand =  (HotelSearchCommand) SerializationUtils.deserialize(hotelSearchRoomDetail.getHotelsearch_cmd());
			OTAHotelAvailRS.RoomStays.RoomStay rs = (OTAHotelAvailRS.RoomStays.RoomStay) SerializationUtils.deserialize(hotelSearchRoomDetail.getRoomstay());


			hotelTransactionTemp = hotelTransactionDao.getHotelTransaction(hotelBookCommand.getSearchKey());
			logger.info("HotelPreBookController : ht---"+hotelTransactionTemp);
			logger.info("HotelPreBookController : total rs---"+rs);

			hotelBookCommand = HotelBookCommand.initRoomGuestProfilesUniques(hotelBookCommand, hotelIdFactory);
			String delimeterbookingkey = "\\,";
			String[] bookingkeys = hotelBookCommand.getBookingCode().split(delimeterbookingkey);

			logger.info("HotelPreBookController----bookingkeys----:"+Arrays.toString(bookingkeys));

			rs = hotelObjectTransformer.getRoomDetailsSummary(rs, bookingkeys);

			ObjectMapper mapper = new ObjectMapper();
			String roomsummary = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rs);
			logger.info("-------------(((((--roomsummary:"+roomsummary);

			FileUtil.writeJson("hotel", "tbo", "roomsummary", false, rs, String.valueOf(hotelBookCommand.getSearchKey()));

			//rs = hotelObjectTransformer.getRoomDetailsSummary(rs, hbc.getBookingCode());

			logger.info("HotelPreBookController----initRoomGuestProfilesUniques----:");



			int noofdays = 1;
			int noofrooms = 1;

			HotelApiCredentials apiauth = null;


			switch (rs.getBasicPropertyInfo().getApiProvider()) {
			case HotelApiCredentials.API_DESIA_IND:
				logger.info("HotelPreBookController----desiya prebooking----:");
				//rs = hotelObjectTransformer.getRoomStayBooking(rs,hbc.getRoomRateTypes());
				logger.info("HotelPreBookController----desiya prebooking----:rs--"+rs);

				boolean canReserveRooms = true;
				TotalType revisedRate = null;

				int bookingCountAttempt = 1;
				while(canReserveRooms)
				{
					logger.info("HotelPreBookController---booking TG attempt:"+ bookingCountAttempt);

					//com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = hotelObjectTransformer.convertTGtoNative(apiHotelMap.get(hotelcode));
					apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);
					apiHotelBook = new APIHotelBook(hotelSearchCommand, hotelBookCommand, new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS), null, BigInteger.valueOf(hotelTransactionTemp.getSearch_key()),  BigInteger.valueOf(hotelTransactionTemp.getId()), new TotalType(), rs ) ;
					noofdays = CommonUtil.getNoofStayDays(hotelSearchCommand);
					noofrooms = hotelSearchCommand.getNoofrooms();
					apiHotelBook.initRate(rs, noofdays,noofrooms);
					logger.info("HotelPreBookController----rate calculation been over:"+ apiHotelBook.getBookingRate().toString());

					FileUtil.writeJson("hotel", "desiya", "api-hotel-book-", false, apiHotelBook, String.valueOf(hotelBookCommand.getSearchKey()));


					if(bookingCountAttempt == 1)
						hor = CommonUtil.hotelOrderRowInsertionData(hotelOrderDao, hotelTransactionTemp, hotelSearchCommand, hotelBookCommand, rs, apiHotelBook,companyConfigDAO,appKeyVo,companyDao,frontUserDao,rmConfigDetailDAO);
					logger.info("HotelPreBookController----hor inserted:"+ hor.getId());

					StringBuilder reqbook = TGRequestBuilder.getProvisionalBookingReq(apiauth, hotelTransactionTemp, hotelSearchCommand, hotelBookCommand, rs, apiHotelBook.getApiRate(), hor, revisedRate);
					//logger.info("HotelPreBookController---- reqbook:"+ reqbook);
					String reqbookxml = CommonUtil.format(reqbook.toString());
					logger.info("HotelPreBookController---- reqbookxml:"+ reqbookxml);
					reqbookxml = reqbookxml.replace(HotelBookRequestBuilder.XML_HEADER_FORMATTED, HotelBookRequestBuilder.SOAP_HEADER_PREBOOK_TG);
					reqbookxml+=HotelBookRequestBuilder.SOAP_FOOTER_PREBOOK_TG;
					StringBuilder sdb = new StringBuilder(reqbookxml);
					logger.info("HotelPreBookController---- soap reqbookxml:"+ sdb.toString());
					ByteArrayInputStream is = new ByteArrayInputStream(sdb.toString().getBytes());
					MimeHeaders header=new MimeHeaders();
					header.addHeader("Content-Type","application/soap+xml");
					SOAPMessage sm = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL).createMessage(header, is);
					//ByteArrayOutputStream baos = new ByteArrayOutputStream();
					//sm.writeTo(baos);
					//otaHotelResRS = DesiyaPullerTask.provisionalBookingHotelDesia("http://stage-api.travelguru.com/services-2.0/tg-services/TGBookingServiceEndPoint", sm);
					logger.info("HotelPreBookController---- otaHotelResRS:"+ otaHotelResRS.toString());

					//totaHotelResRS = hotelObjectTransformer.convertTGtoNativePreBookResponse(otaHotelResRS);
					DesiyaPullerTask desiyaPullerTask = new DesiyaPullerTask(null, hotelObjectTransformer, null, null, null, null, null, null, null , apiauth, apiHotelBook.getSearch(), "Desiya Api", null);
					apiHotelBook = desiyaPullerTask.provisionalBookingHotelDesia(revisedRate, apiHotelBook, rs,apiauth.getEndPointUrl()+"TGBookingServiceEndPoint", sm);
					totaHotelResRS = apiHotelBook.getPreBookRes();



					if(revisedRate != null)
						logger.info("HotelPreBookController before prebook response----transformed revisedRate:"+ revisedRate.toString());

					logger.info("HotelPreBookController----transformed otaHotelResRS:"+ totaHotelResRS);
					revisedRate = hotelObjectTransformer.getNewRateTotal(apiHotelBook.getPreBookRes());

					logger.info("HotelPreBookController----revisedRate ="+ revisedRate);
					if(revisedRate != null)
						canReserveRooms = false;
					else
						canReserveRooms = false;
					bookingCountAttempt++;
				}


				break;
			case HotelApiCredentials.API_REZNEXT_IND:
				//com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = hotelObjectTransformer.convertTGtoNative(apiHotelMap.get(hotelcode));
				apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND,appKeyVo);
				apiHotelBook = new APIHotelBook(hotelSearchCommand, hotelBookCommand, new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS), new OTAHotelResRS(), BigInteger.valueOf(hotelTransactionTemp.getSearch_key()),  BigInteger.valueOf(hotelTransactionTemp.getId()), new TotalType(), rs ) ;
				noofdays = CommonUtil.getNoofStayDays(hotelSearchCommand);
				noofrooms = hotelSearchCommand.getNoofrooms();
				apiHotelBook.initRate(rs, noofdays,noofrooms);

				logger.info("HotelPreBookController----rate calculation been over:"+ apiHotelBook.getBookingRate().toString());

				hor = CommonUtil.hotelOrderRowInsertionData(hotelOrderDao, hotelTransactionTemp, hotelSearchCommand, hotelBookCommand, rs, apiHotelBook,companyConfigDAO,appKeyVo,companyDao,frontUserDao,rmConfigDetailDAO);
				logger.info("HotelPreBookController----hor inserted:"+ hor.getId());
				RezNextPullerTask rezNextPullerTask = new RezNextPullerTask(hotelObjectTransformer, null, null, null, null, null, null, null , apiauth, apiHotelBook.getSearch(), "Desiya Api");
				apiHotelBook = rezNextPullerTask.getMockPreBookingRes(apiauth, hotelObjectTransformer, apiHotelBook);
				totaHotelResRS = apiHotelBook.getPreBookRes();

				break;
			case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
				apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);
				//RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(apiauth, hsc, "RazLive Api");
				RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, null, null, null, null, null, null, null , apiauth, hotelSearchCommand, "RazLive Api");

				apiHotelBook = new APIHotelBook(hotelSearchCommand, hotelBookCommand, new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR), null, BigInteger.valueOf(hotelTransactionTemp.getSearch_key()),  BigInteger.valueOf(hotelTransactionTemp.getId()), new TotalType(), rs ) ;
				logger.info("HotelPreBookController---final apiHotelBook:"+ apiHotelBook.toString());

				noofdays = CommonUtil.getNoofStayDays(hotelSearchCommand);
				logger.info("HotelPreBookController----noofdays:"+ noofdays);
				noofrooms = hotelSearchCommand.getNoofrooms();
				apiHotelBook.initRate(rs, noofdays,noofrooms);

				hor = CommonUtil.hotelOrderRowInsertionData(hotelOrderDao, hotelTransactionTemp, hotelSearchCommand, hotelBookCommand, rs, apiHotelBook,companyConfigDAO,appKeyVo,companyDao,frontUserDao,rmConfigDetailDAO);

				logger.info("HotelPreBookController----noofdays:"+ noofdays);

				apiHotelBook = rezLivePullerTask.preBook(apiHotelBook, hotelBookCommand, rs, hotelIdFactory);
				totaHotelResRS = apiHotelBook.getPreBookRes();

				break;
			case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:
				apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL,appKeyVo);

				apiHotelBook = new APIHotelBook(hotelSearchCommand, hotelBookCommand, new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR), null, BigInteger.valueOf(hotelTransactionTemp.getSearch_key()),  BigInteger.valueOf(hotelTransactionTemp.getId()), new TotalType(), rs ) ;
				logger.info("HotelPreBookController---final apiHotelBook:"+ apiHotelBook.toString());

				noofdays = CommonUtil.getNoofStayDays(hotelSearchCommand);
				logger.info("HotelPreBookController----noofdays:"+ noofdays);
				noofrooms = hotelSearchCommand.getNoofrooms();
				apiHotelBook.initRate(rs, noofdays,noofrooms);

				hor = CommonUtil.hotelOrderRowInsertionData(hotelOrderDao, hotelTransactionTemp, hotelSearchCommand, hotelBookCommand, rs, apiHotelBook,companyConfigDAO,appKeyVo,companyDao,frontUserDao,rmConfigDetailDAO);
				logger.info("HotelPreBookController----noofdays:"+ noofdays);

				//TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(hotelObjectTransformer, hotelDao, hotelroomdescriptionDao, hotelimagesDao, hotelFacilityDao, islhotelmappingDao, hotelinandaroundDao, hotelsecondaryareaDao , apitayyarah, hsc, "Tayyarah Api");
				TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(BigInteger.valueOf(hotelBookCommand.getSearchKey()), apiauth, hotelSearchCommand, "Tayyarah Api");
				apiHotelBook = tayyarahPullerTask.doPrebook(apiHotelBook);
				totaHotelResRS = apiHotelBook.getPreBookRes();
				break;
			case HotelApiCredentials.API_LINTAS_REPOSITORY:
				apiauth = HotelApiCredentials.getApiLintasReposit();
				//RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(apiauth, hsc, "RazLive Api");
				LintasHotelRepositPullerTask lintasHotelRepositPullerTask = new LintasHotelRepositPullerTask(apiauth, hotelSearchCommand, apiauth.getApiProviderName());

				apiHotelBook = new APIHotelBook(hotelSearchCommand, hotelBookCommand, new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR), null, BigInteger.valueOf(hotelTransactionTemp.getSearch_key()),  BigInteger.valueOf(hotelTransactionTemp.getId()), new TotalType(), rs ) ;
				logger.info("HotelPreBookController---final apiHotelBook:"+ apiHotelBook.toString());
				noofrooms = hotelSearchCommand.getNoofrooms();
				noofdays = CommonUtil.getNoofStayDays(hotelSearchCommand);
				logger.info("HotelPreBookController----noofdays:"+ noofdays);
				apiHotelBook.initRate(rs, noofdays,noofrooms);
				hor = CommonUtil.hotelOrderRowInsertionData(hotelOrderDao, hotelTransactionTemp, hotelSearchCommand, hotelBookCommand, rs, apiHotelBook,companyConfigDAO,appKeyVo,companyDao,frontUserDao,rmConfigDetailDAO);
				logger.info("HotelPreBookController----noofdays:"+ noofdays);
				apiHotelBook = lintasHotelRepositPullerTask.doPrebook(apiHotelBook);
				totaHotelResRS = apiHotelBook.getPreBookRes();
				break;

			case HotelApiCredentials.API_LINTAS_INTERNATIONAL:
				//Do Room availablity check of lintas api hotels
				break;
			case HotelApiCredentials.API_TBO_INTERNATIONAL:
				HotelApiCredentials apitbo = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);
				TBOPullerTask tboPullerTask = new TBOPullerTask(null, hotelObjectTransformer, null, null, null, null, null, null, null , apitbo, hotelSearchCommand, "TBO Api", null);

				apiHotelBook = new APIHotelBook(hotelSearchCommand, hotelBookCommand, new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR), null, BigInteger.valueOf(hotelTransactionTemp.getSearch_key()),  BigInteger.valueOf(hotelTransactionTemp.getId()), new TotalType(), rs ) ;
				logger.info("HotelPreBookController---final apiHotelBook:"+ apiHotelBook.toString());
				noofrooms = hotelSearchCommand.getNoofrooms();
				noofdays = CommonUtil.getNoofStayDays(hotelSearchCommand);
				logger.info("HotelPreBookController----noofdays:"+ noofdays);

				apiHotelBook.initRate(rs, noofdays,noofrooms);
				hor = CommonUtil.hotelOrderRowInsertionData(hotelOrderDao, hotelTransactionTemp, hotelSearchCommand, hotelBookCommand, rs, apiHotelBook,companyConfigDAO,appKeyVo,companyDao,frontUserDao,rmConfigDetailDAO);
				logger.info("HotelPreBookController----noofdays...:"+ noofdays);
				apiHotelBook = tboPullerTask.preBook(apiHotelBook, hotelBookCommand, rs, hotelIdFactory,companyDao);
				totaHotelResRS = apiHotelBook.getPreBookRes();
				break;

			case HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL:
				HotelApiCredentials apiTayyarahRepost = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_REPOSIT_INTERNATIONAL,appKeyVo);

				apiHotelBook = new APIHotelBook(hotelSearchCommand, hotelBookCommand, new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR), null, BigInteger.valueOf(hotelTransactionTemp.getSearch_key()),  BigInteger.valueOf(hotelTransactionTemp.getId()), new TotalType(), rs ) ;
				logger.info("HotelPreBookController---final apiHotelBook:"+ apiHotelBook.toString());
				noofdays = CommonUtil.getNoofStayDays(hotelSearchCommand);
				noofrooms = hotelSearchCommand.getNoofrooms();
				logger.info("HotelPreBookController----noofdays:"+ noofdays);
				apiHotelBook.initRate(rs, noofdays,noofrooms);
				hor = CommonUtil.hotelOrderRowInsertionData(hotelOrderDao, hotelTransactionTemp, hotelSearchCommand, hotelBookCommand, rs, apiHotelBook,companyConfigDAO,appKeyVo,companyDao,frontUserDao,rmConfigDetailDAO);
				logger.info("HotelPreBookController----noofdays...:"+ noofdays);
				apiHotelBook = hotelTayyarahRepositService.preBook(apiHotelBook, hotelBookCommand, rs, hotelIdFactory);
				totaHotelResRS = apiHotelBook.getPreBookRes();
				break;
			default:
				break;
			}


			String apibook = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiHotelBook);
			logger.info("-------------(((((--apiHotelBook:"+apiHotelBook);

			FileUtil.writeJson("hotel", "tbo", "apibook-prebook", false, apiHotelBook, String.valueOf(hotelBookCommand.getSearchKey()));


			//apiHotelBook.setPreBookRes(totaHotelResRS);
			//apiHotelBook = hotelObjectTransformer.getRoomDetailsSummaryBooking(apiHotelBook, rs);
			//apiHotelBook = hotelObjectTransformer.getRoomDetailsSummaryBooking(apiHotelBook, rs);
			//payBooking(apiHotelBook, rs);
			//if((apiHotelBook.getPreBookRes().getStatus().getCode().equals(APIStatus.STATUS_CODE_SUCCESS)))

			apiHotelBook = currencyManager.fillCurrencyOnPreBookResponse(apiHotelBook,hor);
			FileUtil.writeJson("hotel", "tbo", "apibook-prebook-after-markup", false, apiHotelBook, String.valueOf(hotelBookCommand.getSearchKey()));


			totaHotelResRS = apiHotelBook.getPreBookRes();


			byte[] hotelbook_cmddata = SerializationUtils.serialize(hotelBookCommand);
			byte[] roomstaydata = SerializationUtils.serialize(apiHotelBook.getRoomsummary());
			byte[] prebook_resdata = SerializationUtils.serialize(totaHotelResRS);

			hotelBookingTemp.setOrderId(orderid);
			hotelBookingTemp.setHotelbook_cmd(hotelbook_cmddata);
			hotelBookingTemp.setRoomstay(roomstaydata);
			hotelBookingTemp.setPrebook_res(prebook_resdata);
			hotelBookingTemp.setBook_res(null);

			hotelBookingTemp = hotelBookingDao.insertHotelBooking(hotelBookingTemp);

			UniqueIDType uniqueid = new UniqueIDType();
			uniqueid = CommonUtil.getApiUniqueId(totaHotelResRS);
			hor = CommonUtil.hotelOrderUpdationDataPreBook(hotelOrderDao, hor, hotelTransactionTemp, apiHotelBook, "Reservation Failed", "Pending", "Reservation");

			logger.info("prebooking over..  booking system type----:"+apiHotelBook.getBook().getBookingSystemType());
			logger.info("prebooking over..  before book call--11----uniqueid:"+uniqueid);
			logger.info("prebooking over..  before book call--11--apiHotelBook:"+apiHotelBook);
			logger.info("prebooking over..  before book call--11--apiHotelBook.getBookRes():"+apiHotelBook.getBookRes());
			logger.info("prebooking over..  before book call--11--apiHotelBook.getBookRes().getStatus():"+apiHotelBook.getBookRes().getStatus());
			logger.info("prebooking over..  before book call--11--apiHotelBook.getBookRes().getStatus() msg:"+apiHotelBook.getBookRes().getStatus().getMessage());

			logger.info("prebooking over..  before book call--11--apiHotelBook.getPreBookRes():"+apiHotelBook.getPreBookRes());
			logger.info("prebooking over..  before book call--11--apiHotelBook.getPreBookRes().getStatus():"+apiHotelBook.getPreBookRes().getStatus());
			logger.info("prebooking over..  before book call--11--apiHotelBook.getPreBookRes().getStatus() msg:"+apiHotelBook.getPreBookRes().getStatus().getMessage());

			logger.info("prebooking over..  before book call--11--apiHotelBook.getStatus():"+apiHotelBook.getStatus());
			logger.info("prebooking over..  before book call--11--apiHotelBook.getStatus() status msg:"+apiHotelBook.getStatus().getMessage());



			//if((uniqueid != null) && (uniqueid.getID().length()>0) && (totaHotelResRS.getStatus().getCode().equals(APIStatus.STATUS_CODE_SUCCESS)))

			// Check is price changes and price is not changed allow to book
			if((totaHotelResRS.getStatus().getCode().equals(APIStatus.STATUS_CODE_SUCCESS)) && !totaHotelResRS.isPriceChanged())
			{
				PaymentTransaction paymentTransaction=new PaymentTransaction();
				paymentTransaction.setAmount(AmountRoundingModeUtil.roundingModeForHotel(apiHotelBook.getBookingRate().getTotalPayableAmt().add(hor.getFeeAmount())));
				paymentTransaction.setCurrency(apiHotelBook.getSearch().getCurrency());
				paymentTransaction.setRefno(apiHotelBook.getBook().getPaymentid());
				paymentTransaction.setIsPaymentSuccess(false);
				paymentTransaction.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
				paymentTransaction.setPayment_method(apiHotelBook.getBook().getPayBy());
				paymentTransaction.setApi_transaction_id(apiHotelBook.getBook().getOrderid());
				paymentTransaction.setPayment_status("Pending");
				paymentTransaction.setTransactionId(apiHotelBook.getBook().getOrderid());
				paymentTransaction.setResponse_message("NA");
				paymentTransaction.setResponseCode("NA");
				paymentTransaction.setAuthorizationCode(apiHotelBook.getBook().getTransactionid());

				//storing paymentgateway transaction in db
				hotelbookservice.updatePaymentTransaction(paymentTransaction);
				if(hotelBookCommand.getIsCompanyEntity()!=null && hotelBookCommand.getIsCompanyEntity()){ 
					Integer companyEntityId = hotelBookCommand.getCompanyEntityId();
					hor.setCompanyEntityId(companyEntityId.longValue());
				}
				hor = CommonUtil.hotelOrderUpdationDataPreBook(hotelOrderDao, hor, hotelTransactionTemp, apiHotelBook, "Reserved", "Pending", "Reservation");
				FrontUserDetail frontUserDetail =  GetFrontUserDetail.getFrontUserDetailDetails(hor.getOrderCustomer(),frontUserDao);
				try{
					frontUserDetail = frontUserDao.insertFrontUserDetail(frontUserDetail);
				}catch(Exception e){
					logger.error("Exception", e);
				}

				if(frontUserDetail.getId() != null && frontUserDetail.getId() != 0){
					emaildao.insertEmail(String.valueOf(frontUserDetail.getId()), 0, Email.EMAIL_TYPE_FRONT_USER_REGISTRATION_BY_TAYYARAH);
				}

				if(apiHotelBook.getBook().getBookingSystemType().equalsIgnoreCase("ibe"))
				{
					if( apiHotelBook.getBook().getPayBy().equals("cash"))
					{
						//apiHotelBook.setStatus(totaHotelResRS.getStatus());
						apiHotelBook = hotelbookservice.payWalletBooking(totaHotelResRS, hotelTransactionTemp, hotelBookingTemp, hor, paymentTransaction, uniqueid, apiHotelBook, rs,appKeyVo);
						logger.info("HotelPreBookController----payWalletBooking uniqueid:"+uniqueid);
						if(totaHotelResRS.getStatus().getCode() == APIStatus.STATUS_CODE_SUCCESS){
							new NotificationUtil().insertNotification(appKeyVo,orderid , "Hotel Booking", InventoryTypeEnum.HOTEL_ORDER.getId(), true,NFDAO,companyDao);
						}
					}
					else
					{
						APIStatus status = new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS + " Try Payment through card..");
						OTAHotelResRS totaHotelResRSBook = new OTAHotelResRS();
						totaHotelResRSBook.setStatus(status);
						apiHotelBook.setStatus(status);
						apiHotelBook.setBookRes(totaHotelResRSBook);
					}
				}
				else
				{
					logger.info("prebooking over..  booking system type----:"+apiHotelBook.getBook().getBookingSystemType());

				}

				//apiHotelBook = callAPIBook(ht, hs, hb, hor, uniqueid, apiHotelBook, rs);
			}
			else
			{
				APIStatus status = null;
				if(totaHotelResRS.isPriceChanged())
					status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " Sorry,the rates of the room you selected has changed." );
				else
					status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Something wrong with input details... Reservation failed." );

				OTAHotelResRS totaHotelResRSBook = new OTAHotelResRS();
				totaHotelResRSBook.setStatus(status);
				apiHotelBook.setStatus(status);
				apiHotelBook.setBookRes(totaHotelResRSBook);
				//apiHotelBook.setStatus(status);
				//apiHotelBook.setBookRes(totaHotelResRSBook);
			}

			// Insert Hotel Api Response in the flight_hotel_book_api_response table
			ApiResponseSaver.saveHotelApiResponse(apiHotelBook,hor,flightBookingDao);

			// if quotation is true
			if(apiHotelBook.getBookRes().getStatus().getCode() == APIStatus.STATUS_CODE_SUCCESS){
				if(hotelBookCommand.isQuotation()) {
					hotelTravelRequestDao.updateHotelRequestQuotationWithOrderId(hor.getId(),Long.valueOf(hotelBookCommand.getQuotationId()));
					ApiProviderPaymentTransaction apiProviderPaymentTransaction = new ApiProviderPaymentTransaction();
					apiProviderPaymentTransaction.setAmount(hor.getFinalPrice());
					apiProviderPaymentTransaction.setApi_transaction_id(hor.getOrderReference());
					apiProviderPaymentTransaction.setCurrency(hor.getApiCurrency());
					apiProviderPaymentTransaction.setCreatedAt(hor.getCreatedAt());
					apiProviderPaymentTransaction.setPayment_system("Full");
					apiProviderPaymentTransaction.setPayment_status(hor.getPaymentStatus());
					apiProviderPaymentTransaction.setIsPaymentSuccess(true);
					hotelTravelRequestDao.insertSupplierPaymentTransactionInfo(apiProviderPaymentTransaction);
				}				

			}

			/*if(hotelBookCommand.getIsRmDetails()){
				for (RmConfigTripDetailsModel rmConfigTripDetailsModel : hotelBookCommand.getRmDataListDetails()) {
					rmConfigTripDetailsModel.setOrdertype("Hotel");
					rmConfigTripDetailsModel.setOrderId(hor.getOrderReference());
					try{
						companyDao.insertRMConfigTripDetails(rmConfigTripDetailsModel);
					}catch(Exception e){

					}
				}
			}
*/
			try{
				userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(hor.getOrderReference(), hor.getInvoiceNo());
			}catch(Exception e){
				logger.error("walletTransferHistoryUpdateWithInvoiceNo Exception", e);
			}


			return apiHotelBook;

		}
		catch (HibernateException e) {
			try {
				if(hor!=null)
					userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(hor.getOrderReference(), hor.getInvoiceNo());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//logger.info("query Exception:HibernateException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (IOException e) {
			try {
				if(hor!=null)
					userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(hor.getOrderReference(), hor.getInvoiceNo());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//logger.info("query Exception:IOException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (ClassNotFoundException e) {
			try {
				if(hor!=null)
					userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(hor.getOrderReference(), hor.getInvoiceNo());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (JAXBException e) {
			try {
				if(hor!=null)
					userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(hor.getOrderReference(), hor.getInvoiceNo());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//logger.info("query Exception:JAXBException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (UnsupportedOperationException e) {
			try {
				if(hor!=null)
					userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(hor.getOrderReference(), hor.getInvoiceNo());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//logger.info("query Exception:UnsupportedOperationException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (SOAPException e) {
			try {
				if(hor!=null)
					userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(hor.getOrderReference(), hor.getInvoiceNo());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//logger.info("query Exception:SOAPException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (Exception e) {
			try {
				if(hor!=null)
					userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(hor.getOrderReference(), hor.getInvoiceNo());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//logger.info("Exception:Exception:"+ e.getMessage());
			//logger.info("Exception:Exception:localised"+ e.getLocalizedMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}

	}




	@ExceptionHandler(BaseException.class)
	public @ResponseBody RestError handleCustomException (BaseException ex, HttpServletResponse response) {
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return ex.transformException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}




	public boolean isRateChangedError(OTAHotelResRS totaHotelResRS)
	{
		boolean isRateChanged = false;
		/*if(totaHotelResRS.getStatus() != null)
		{
			if(uniqueid != null && apiHotelBook.getBook().getPayBy().equals("cash"))
			{
				//apiHotelBook.setStatus(totaHotelResRS.getStatus());
				apiHotelBook = payWalletBooking(ht, hs, hb, hor, uniqueid, apiHotelBook, rs);
				//logger.info("HotelPreBookController----payWalletBooking uniqueid:"+uniqueid);

				//apiHotelBook = callAPIBook(ht, hs, hb, hor, uniqueid, apiHotelBook, rs);
			}
			else if(uniqueid == null)
			{
				APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Something wong with input details... Reservation failed." );
				apiHotelBook.setStatus(status);
			}

		}
		else
		{
			APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Something wong with input details... Reservation failed." );
			apiHotelBook.setStatus(status);
		}*/
		return isRateChanged;
	}





}
