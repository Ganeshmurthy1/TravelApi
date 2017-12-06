package com.tayyarah.flight.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.admin.analytics.lookbook.dao.LookBookDao;
import com.tayyarah.admin.analytics.lookbook.entity.FlightBook;
import com.tayyarah.api.flight.tbo.model.TboFlightAirpriceResponse;
import com.tayyarah.apiconfig.model.BluestarConfig;
import com.tayyarah.apiconfig.model.TboFlightConfig;
import com.tayyarah.apiconfig.model.TravelportConfig;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.exception.BaseException;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.exception.RestError;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.notification.NotificationUtil;
import com.tayyarah.common.notification.dao.NotificationDao;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.GenerateID;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.common.util.enums.InventoryTypeEnum;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.flight.dao.AirportDAO;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.BookingDetails;
import com.tayyarah.flight.model.FlightBookingResponse;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.flight.util.FlightWebServiceEndPointValidator;
import com.tayyarah.flight.util.api.lintas.LintasServiceCall;
import com.tayyarah.flight.util.api.tayyarah.TayyarahServiceCall;
import com.tayyarah.flight.util.api.tbo.TboServiceCall;
import com.tayyarah.flight.util.api.travelport.UapiServiceCall;
import com.tayyarah.services.EmailService;
import com.tayyarah.user.entity.WalletAmountTranferHistory;

//http://localhost:9080/LintasTravelAPI/payment/response?app_key=fgdf&refno=M&transaction_id=vvUM&payment_status=1&Content-Type=application/json
// http://localhost:9080/LintasTravelAPI/payment/response?app_key=B6cynYrcL6TwIH5GtMHhVH64cNvVTPKG6FXnf4Fpo9A=&refno=PG151029233002747&transaction_id=1234&payment_status=0&AuthCode=abcd&Content-Type=application/json


@RestController
@RequestMapping("/payment")
public class FlightPaymentController {

	private FlightWebServiceEndPointValidator validator = new FlightWebServiceEndPointValidator();
	private FlightDataBaseServices DBS = new FlightDataBaseServices();
	static final Logger logger = Logger.getLogger(FlightPaymentController.class);
	public static final int VERSION = 1;


	@Autowired
	FlightTempAirSegmentDAO TempDAO;
	@Autowired
	FlightBookingDao FBDAO;
	@Autowired
	CompanyDao companyDao;
	@Autowired
	EmailDao emaildao;
	@Autowired
	private EmailService emailService;
	@Autowired
	ServletContext servletContext;
	@Autowired
	ApplicationContext applicationContext;
	@Autowired
	AirportDAO ADAO;
	@Autowired
	NotificationDao NFDAO;	
	@SuppressWarnings("rawtypes")
	@Autowired
	LookBookDao lookBookDao;

	@RequestMapping(value = "/response", method = RequestMethod.GET, headers = { "Accept=application/json" }, produces = { "application/json" })
	public @ResponseBody
	FlightBookingResponse paymentResponse(
			@RequestParam(value = "app_key") String app_key,
			@RequestParam(value = "refno") String refno,
			@RequestParam(value = "response_message", defaultValue = "yet get") String response_message,
			@RequestParam(value = "response_code", defaultValue = "1") String response_code,
			@RequestParam(value = "transaction_id") String transaction_id,
			@RequestParam(value = "payment_status") String payment_status,
			@RequestParam(value = "AuthCode") String AuthCode,
			HttpServletResponse response,HttpServletRequest request) {
		logger.info("search method called : ");
		ResponseHeader.setResponse(response);
		AppControllerUtil.validatePaymentAppKey(companyDao, app_key,refno);
		validator.paymentValidator(refno, payment_status, transaction_id,
				AuthCode);
		FlightBookingResponse flightBookingResponse = null;
		String orderid1 ="invalid";
		String orderid2 ="invalid";

		String result = "<orderid1>invalid</orderid2><orderid2>invalid</orderid2><count>0</count>";

		try {
			result = UapiServiceCall.getOrderId(refno, FBDAO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new FlightException(ErrorCodeCustomerEnum.Exception,
					ErrorMessages.PAY_S_BOOKING_FAILED,orderid1,refno);
		}

		logger.info("result ID is : "+result);

		orderid1 = result.substring(result.indexOf("<orderid1>") + 10,
				result.indexOf("</orderid1>"));
		orderid2 = result.substring(result.indexOf("<orderid2>") + 10,
				result.indexOf("</orderid2>"));
		String count = result.substring(result.indexOf("<count>") + 7,
				result.indexOf("</count>"));


		logger.info("ORDER ID1 is : "+orderid1);
		logger.info("ORDER ID2 is : "+orderid2);

		List<String> orderIdList=new ArrayList<String>();


		if(orderid1.equals("invalid")){

			throw new FlightException(ErrorCodeCustomerEnum.Exception,
					ErrorMessages.INVALID_REFNO,ErrorMessages.PAY_S_BOOKING_FAILED,refno);
		}else{
			orderIdList.add(orderid1);
		}
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, app_key);
		if(appKeyVo==null)
		{
			throw new FlightException(ErrorCodeCustomerEnum.Exception,
					ErrorMessages.INVALID_APPKEY,ErrorMessages.INVALID_APPKEY,refno);
		}

		if(!orderid2.equals("invalid")){
			orderIdList.add(orderid2);
			flightBookingResponse=GetBookingStatusSpecial(appKeyVo,orderIdList,refno, response_message, response_code, transaction_id, payment_status, AuthCode,count,response,request);
		}else{
			flightBookingResponse = GetBookingStatus(appKeyVo,orderid1,refno, response_message, response_code, transaction_id, payment_status, AuthCode,count,response,request);
		}


		return flightBookingResponse;

	}
	public FlightBookingResponse GetBookingStatus(AppKeyVo appKeyVo,String orderid,String refno,String response_message,String response_code,String transaction_id,String payment_status,String AuthCode,String count,HttpServletResponse response,HttpServletRequest request){
		FlightBookingResponse flightBookingResponse = null;
		BookingDetails bookingDetails = null;
		try {
			bookingDetails = UapiServiceCall.getBookingDetailsToDb(orderid,
					FBDAO);
		} catch (Exception e) {
			// TODO Auto-generated catch block

			throw new FlightException(ErrorCodeCustomerEnum.Exception,
					ErrorMessages.INVALID_REFNO,orderid,refno);
		}

		// Get FlightOrderRow
		FlightOrderRow flightOrderRow = null;
		try {
			flightOrderRow = FBDAO.getflightorderrow(orderid);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (HibernateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FlightBook flightBook=new FlightBook();
		if (payment_status.equalsIgnoreCase("1")) {

			PaymentTransaction paymentTransaction = new PaymentTransaction();
			paymentTransaction.setIsPaymentSuccess(true);
			paymentTransaction.setTransactionId(transaction_id);
			paymentTransaction.setResponse_message("SUCCESS");
			paymentTransaction.setResponseCode(response_code);
			paymentTransaction.setPayment_status("SUCCESS");
			paymentTransaction.setRefno(refno);
			paymentTransaction.setAuthorizationCode(AuthCode);
			paymentTransaction.setApi_transaction_id(orderid);
			DBS.updatePaymentStatus(paymentTransaction, FBDAO);	
			try{
				flightBook.setIP(request.getRemoteAddr());
				flightBook.setAppkey(appKeyVo.getAppKey());
				flightBook.setSearchOnDateTime(new Timestamp(new Date().getTime()));
				flightBook.setTransactionKey(paymentTransaction.getTransactionId());
				flightBook.setSearchKey("");				
				lookBookDao.insertIntoTable(flightBook);
			}
			catch (Exception e) {
			}			
			try {

				if (orderid.startsWith("FTP")) {
					flightBookingResponse = UapiServiceCall.callBookingService(flightBookingResponse,
							TravelportConfig.GetTravelportConfig(),
							bookingDetails.getOrderCustomer(),
							bookingDetails.getFlightPriceResponse(),
							bookingDetails.getFlightOrderCustomers(), orderid,
							bookingDetails.getCountrycode(), FBDAO,emaildao,
							bookingDetails.getTransactionkey(), "CARD",
							new WalletAmountTranferHistory(),10);

				}
				if(orderid.startsWith("FBS")){// Bluestar booking call....
					BluestarConfig bluestarConfig = BluestarConfig.GetBluestarConfig(appKeyVo);
					flightBookingResponse= com.tayyarah.flight.util.api.bluestar.BluestarServiceCall.callBookingService(flightBookingResponse,bookingDetails.getOrderCustomer(),
							bookingDetails.getFlightPriceResponse
							(),bookingDetails.getFlightOrderCustomers
							(),orderid,bookingDetails
							.getCountrycode(),FBDAO,emaildao,bookingDetails
							.getTransactionkey(),"CARD",new
							WalletAmountTranferHistory(),10,false,bluestarConfig);
				}
				if(orderid.startsWith("FTAY")){//call tayyarah bookingAPI
					flightBookingResponse= TayyarahServiceCall.callBookingService(flightBookingResponse,bookingDetails.getOrderCustomer(),
							bookingDetails.getFlightPriceResponse
							(),bookingDetails.getFlightOrderCustomers
							(),orderid,bookingDetails		  
							.getFlightCustomerDetails(),FBDAO,emaildao,bookingDetails
							.getTransactionkey(),"CARD",new
							WalletAmountTranferHistory(),10);

				}  if(orderid.startsWith("FLIN")){//call lintas bookingAPI
					flightBookingResponse= LintasServiceCall.callBookingService(flightBookingResponse,bookingDetails.getOrderCustomer(),
							bookingDetails.getFlightPriceResponse
							(),bookingDetails.getFlightOrderCustomers
							(),orderid,bookingDetails		  
							.getFlightCustomerDetails(),FBDAO,emaildao,bookingDetails
							.getTransactionkey(),"CARD",new
							WalletAmountTranferHistory(),10);
				}
				if(orderid.startsWith("FTBO")){//call TBO bookingAPI
					TboFlightConfig tboFlightConfig = TboFlightConfig.GetTboConfig(appKeyVo);
					//cal TBO API booking API
					TboFlightAirpriceResponse	TboPriceResponse = UapiServiceCall.getTboFlightPriceResponse(bookingDetails.getFlightCustomerDetails().getPrice_key(),TempDAO,false);
					// LCC carrier Booking Request
					if(TboPriceResponse.getResponse().getResults().getIsLCC())
						flightBookingResponse= TboServiceCall.callLCCBookingService(flightBookingResponse,TboPriceResponse,bookingDetails.getOrderCustomer(),  bookingDetails.getFlightPriceResponse(),bookingDetails.getFlightOrderCustomers(),orderid,bookingDetails.getFlightCustomerDetails(),FBDAO,emaildao,bookingDetails.getTransactionkey(),"CARD",new	WalletAmountTranferHistory(),10,false,flightOrderRow,tboFlightConfig);
					else
						flightBookingResponse= TboServiceCall.callBookingService(flightBookingResponse,TboPriceResponse,bookingDetails.getOrderCustomer(),  bookingDetails.getFlightPriceResponse(),bookingDetails.getFlightOrderCustomers(),orderid,bookingDetails.getFlightCustomerDetails(),FBDAO,emaildao,bookingDetails.getTransactionkey(),"CARD",new	WalletAmountTranferHistory(),10,false,flightOrderRow,tboFlightConfig);
				}
				// insert notication after booking is successful
				new NotificationUtil().insertNotification(appKeyVo,flightOrderRow.getOrderId() , "Flight Ticket Booking", InventoryTypeEnum.FLIGHT_ORDER.getId(), true,NFDAO,companyDao); 

				// Update orderCustomer
				OrderCustomer orderCustomer = flightOrderRow.getCustomer();
				orderCustomer.setOrderId(flightOrderRow.getOrderId());		
				try {
					FBDAO.updateOrderCustomerDetails(orderCustomer);
				} catch (Exception e1) {
					logger.error("Exception", e1);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}
				// Update flightorderrow
				flightOrderRow.setCustomer(orderCustomer);
				try {
					FBDAO.updateFlightOrderRowDetails(flightOrderRow);
				} catch (Exception e) {
					logger.error("Exception", e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}


			} catch (ClassNotFoundException e) {
				logger.error("ClassNotFoundException ", e);

				throw new FlightException(ErrorCodeCustomerEnum.Exception,
						ErrorMessages.PAY_S_BOOKING_FAILED,orderid,refno);
			} catch (SOAPException e) {
				logger.error("SOAPException ", e);

				throw new FlightException(ErrorCodeCustomerEnum.Exception,
						ErrorMessages.PAY_S_BOOKING_FAILED,orderid,refno);
			} catch (JAXBException e) {
				logger.error("JAXBException ", e);

				throw new FlightException(ErrorCodeCustomerEnum.Exception,
						ErrorMessages.PAY_S_BOOKING_FAILED,orderid,refno);
			} catch (Exception e) {
				logger.error("Exception ", e);
				throw new FlightException(ErrorCodeCustomerEnum.Exception,
						ErrorMessages.PAY_S_BOOKING_FAILED,orderid,refno);

			}
			flightBookingResponse.setProcessingfee(flightOrderRow.getProcessingFees());
			flightBookingResponse.setFareFlightSegment(bookingDetails
					.getFlightPriceResponse().getFareFlightSegment());
			flightBookingResponse.setPassengerFareBreakUps(bookingDetails
					.getFlightPriceResponse().getPassengerFareBreakUps());
			flightBookingResponse.setTransactionKey(bookingDetails
					.getTransactionkey());
			flightBookingResponse.setFlightsearch(bookingDetails
					.getFlightPriceResponse().getFlightsearch());
			flightBookingResponse.setCountry(bookingDetails.getCountrycode());
			flightBookingResponse.setFlightCustomerDetails(bookingDetails
					.getFlightCustomerDetails());
			flightBookingResponse.setCache(false);
			flightBookingResponse.setConfirmationNumber(orderid);
			flightBookingResponse.setPaymentStatus(true);
			flightBookingResponse.setPgID(refno);

		} else {

			PaymentTransaction paymentTransaction = new PaymentTransaction();
			paymentTransaction.setIsPaymentSuccess(false);
			paymentTransaction.setTransactionId(transaction_id);
			paymentTransaction.setResponse_message(response_message);
			paymentTransaction.setResponseCode(response_code);
			paymentTransaction.setPayment_status("FAILED");
			paymentTransaction.setRefno(refno);
			paymentTransaction.setAuthorizationCode(AuthCode);
			paymentTransaction.setApi_transaction_id(orderid);
			flightBookingResponse = new FlightBookingResponse();			
			flightBookingResponse.setFareFlightSegment(bookingDetails
					.getFlightPriceResponse().getFareFlightSegment());
			flightBookingResponse.setPassengerFareBreakUps(bookingDetails
					.getFlightPriceResponse().getPassengerFareBreakUps());
			flightBookingResponse.setTransactionKey(bookingDetails
					.getTransactionkey());
			flightBookingResponse.setFlightsearch(bookingDetails
					.getFlightPriceResponse().getFlightsearch());
			flightBookingResponse.setCountry(bookingDetails.getCountrycode());
			flightBookingResponse.setFlightCustomerDetails(bookingDetails
					.getFlightCustomerDetails());
			flightBookingResponse.setCache(false);
			flightBookingResponse.setConfirmationNumber(orderid);
			flightBookingResponse.setPaymentStatus(false);
			String newpgid = "PGF" + new GenerateID().toString();
			flightBookingResponse.setPgID(refno);
			flightBookingResponse.setNewpgID(newpgid);
			flightBookingResponse
			.setPaymentCalledCount(Integer.parseInt(count));
			DBS.updatePaymentStatus(paymentTransaction, FBDAO);
			DBS.updatePNR("0#", orderid, FBDAO);
			PaymentTransaction paymentTransactionNew = new PaymentTransaction();
			paymentTransactionNew.setAmount(AmountRoundingModeUtil.roundingMode(new BigDecimal(bookingDetails
					.getFlightPriceResponse().getFareFlightSegment()
					.getTotalPrice())));
			paymentTransactionNew.setCurrency(bookingDetails
					.getFlightPriceResponse().getFareFlightSegment()
					.getCurrency());
			paymentTransactionNew.setRefno(newpgid);
			paymentTransactionNew.setIsPaymentSuccess(false);
			paymentTransactionNew.setCreatedAt(new java.sql.Timestamp(Calendar
					.getInstance().getTime().getTime()));
			paymentTransactionNew.setPayment_method(bookingDetails
					.getFlightCustomerDetails().getPaymode());
			paymentTransactionNew.setApi_transaction_id(orderid);
			paymentTransactionNew.setPayment_status("Pending");
			try {
				callPayment(refno, paymentTransactionNew, FBDAO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Exception", e);
				throw new FlightException(ErrorCodeCustomerEnum.Exception,
						ErrorMessages.PAY_S_BOOKING_FAILED,orderid,refno);
			}

		}		
		flightBookingResponse.setLastTicketingDate(bookingDetails
				.getFlightPriceResponse().getFareFlightSegment().getLatestTicketingTime());

		TravelportConfig travelportConfig=TravelportConfig.GetTravelportConfig();
		CommonConfig commonConfig = CommonConfig.GetCommonConfig();
		if(flightBookingResponse.isBookingStatus()&&flightBookingResponse.getConfirmationNumber().startsWith("FTP")&&commonConfig.isIs_lintas_enabled()&&!travelportConfig.isTest()){
			sendPNRViaMAil(bookingDetails.getFlightCustomerDetails().getUserid(),flightBookingResponse.getPnrSpecial(), request, response);
		}	
		flightBookingResponse.setGSTonMarkup(bookingDetails.getFlightPriceResponse().getGSTonMarkup());
		flightBookingResponse.setFinalPriceWithGST(bookingDetails.getFlightPriceResponse().getFinalPriceWithGST().add(flightOrderRow.getProcessingFees()));

		return flightBookingResponse;
	}
	public FlightBookingResponse GetBookingStatusSpecial(AppKeyVo appKeyVo,List<String> orderIdList,String refno,String response_message,String response_code,String transaction_id,String payment_status,String AuthCode,String count,HttpServletResponse response,HttpServletRequest request){
		FlightBookingResponse flightBookingResponse = null;
		String orderonward = "";
		String orderreturn = "";
		FlightOrderRow orderrowonward = null;
		FlightOrderRow orderrowreturn = null;
		BookingDetails bookingDetailsonward = null;
		BookingDetails bookingDetailsreturn = null;
		for(int i=0;i<orderIdList.size();i++){

			String orderid = orderIdList.get(i);
			BookingDetails bookingDetails = null;
			try {
				bookingDetails = UapiServiceCall.getBookingDetailsToDb(orderid,FBDAO);
			} catch (Exception e) {
				// TODO Auto-generated catch block

				throw new FlightException(ErrorCodeCustomerEnum.Exception,
						ErrorMessages.INVALID_REFNO,orderid,refno);
			}

			// Get FlightOrderRow
			FlightOrderRow flightOrderRow = null;
			try {
				flightOrderRow = FBDAO.getflightorderrow(orderid);
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (HibernateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (payment_status.equalsIgnoreCase("1")) {
				PaymentTransaction paymentTransaction = new PaymentTransaction();
				paymentTransaction.setIsPaymentSuccess(true);
				paymentTransaction.setTransactionId(transaction_id);
				paymentTransaction.setResponse_message(response_message);
				paymentTransaction.setResponseCode(response_code);
				paymentTransaction.setPayment_status("SUCCESS");
				paymentTransaction.setRefno(refno);
				paymentTransaction.setAuthorizationCode(AuthCode);
				paymentTransaction.setApi_transaction_id(orderid);
				DBS.updatePaymentStatus(paymentTransaction, FBDAO);

				try {

					if (orderid.startsWith("FTP")) {
						flightBookingResponse = UapiServiceCall.callBookingService(flightBookingResponse,
								TravelportConfig.GetTravelportConfig(),
								bookingDetails.getOrderCustomer(),
								bookingDetails.getFlightPriceResponse(),
								bookingDetails.getFlightOrderCustomers(), orderid,
								bookingDetails.getCountrycode(), FBDAO,emaildao,
								bookingDetails.getTransactionkey(), "CARD",
								new WalletAmountTranferHistory(),i);
					}
					if(orderid.startsWith("FBS")){// Bluestar booking call....
						BluestarConfig bluestarConfig = BluestarConfig.GetBluestarConfig(appKeyVo);
						flightBookingResponse= com.tayyarah.flight.util.api.bluestar.BluestarServiceCall.callBookingService(flightBookingResponse,bookingDetails.getOrderCustomer(),
								bookingDetails.getFlightPriceResponse
								(),bookingDetails.getFlightOrderCustomers
								(),orderid,bookingDetails
								.getCountrycode(),FBDAO,emaildao,bookingDetails
								.getTransactionkey(),"CARD",new
								WalletAmountTranferHistory(),i,true,bluestarConfig);

					}
					if(orderid.startsWith("FTAY")){//call tayyarah bookingAPI

						flightBookingResponse= TayyarahServiceCall.callBookingService(flightBookingResponse,bookingDetails.getOrderCustomer(),
								bookingDetails.getFlightPriceResponse
								(),bookingDetails.getFlightOrderCustomers
								(),orderid,bookingDetails		  
								.getFlightCustomerDetails(),FBDAO,emaildao,bookingDetails
								.getTransactionkey(),"CARD",new
								WalletAmountTranferHistory(),i);

					}  if(orderid.startsWith("FLIN")){//call lintas bookingAPI

						flightBookingResponse= LintasServiceCall.callBookingService(flightBookingResponse,bookingDetails.getOrderCustomer(),
								bookingDetails.getFlightPriceResponse
								(),bookingDetails.getFlightOrderCustomers
								(),orderid,bookingDetails		  
								.getFlightCustomerDetails(),FBDAO,emaildao,bookingDetails
								.getTransactionkey(),"CARD",new
								WalletAmountTranferHistory(),i);
					}
					if(orderid.startsWith("FTBO")){//call lintas bookingAPI
						TboFlightAirpriceResponse	TboPriceResponse = null;
						TboFlightConfig tboFlightConfig = TboFlightConfig.GetTboConfig(appKeyVo);
						//cal TBO API booking API
						if(i==0){						
							TboPriceResponse = UapiServiceCall.getTboFlightPriceResponse(bookingDetails.getFlightCustomerDetails().getPrice_key(),TempDAO,false);
						}else
						{
							TboPriceResponse = UapiServiceCall.getTboFlightPriceResponse(bookingDetails.getFlightCustomerDetails().getPrice_key(),TempDAO,true);
						}
						// LCC carrier Booking Request
						if(i==0){
							if(TboPriceResponse.getResponse().getResults().getIsLCC())
								flightBookingResponse= TboServiceCall.callLCCBookingService(flightBookingResponse,TboPriceResponse,bookingDetails.getOrderCustomer(),  bookingDetails.getFlightPriceResponse(),bookingDetails.getFlightOrderCustomers(),orderid,bookingDetails.getFlightCustomerDetails(),FBDAO,emaildao,bookingDetails.getTransactionkey(),"CARD",new	WalletAmountTranferHistory(),10,false,flightOrderRow,tboFlightConfig);
							else
								flightBookingResponse= TboServiceCall.callBookingService(flightBookingResponse,TboPriceResponse,bookingDetails.getOrderCustomer(),  bookingDetails.getFlightPriceResponse(),bookingDetails.getFlightOrderCustomers(),orderid,bookingDetails.getFlightCustomerDetails(),FBDAO,emaildao,bookingDetails.getTransactionkey(),"CARD",new	WalletAmountTranferHistory(),10,false,flightOrderRow,tboFlightConfig);
						}else{
							if(TboPriceResponse.getResponse().getResults().getIsLCC())
								flightBookingResponse= TboServiceCall.callLCCBookingService(flightBookingResponse,TboPriceResponse,bookingDetails.getOrderCustomer(),  bookingDetails.getFlightPriceResponse(),bookingDetails.getFlightOrderCustomers(),orderid,bookingDetails.getFlightCustomerDetails(),FBDAO,emaildao,bookingDetails.getTransactionkey(),"CARD",new	WalletAmountTranferHistory(),10,true,flightOrderRow,tboFlightConfig);
							else
								flightBookingResponse= TboServiceCall.callBookingService(flightBookingResponse,TboPriceResponse,bookingDetails.getOrderCustomer(),  bookingDetails.getFlightPriceResponse(),bookingDetails.getFlightOrderCustomers(),orderid,bookingDetails.getFlightCustomerDetails(),FBDAO,emaildao,bookingDetails.getTransactionkey(),"CARD",new	WalletAmountTranferHistory(),10,true,flightOrderRow,tboFlightConfig);

						}
					}

				} catch (ClassNotFoundException e) {
					logger.error("ClassNotFoundException ", e);

					throw new FlightException(ErrorCodeCustomerEnum.Exception,
							ErrorMessages.PAY_S_BOOKING_FAILED,orderid,refno);
				} catch (SOAPException e) {
					logger.error("SOAPException ", e);

					throw new FlightException(ErrorCodeCustomerEnum.Exception,
							ErrorMessages.PAY_S_BOOKING_FAILED,orderid,refno);
				} catch (JAXBException e) {
					logger.error("JAXBException ", e);

					throw new FlightException(ErrorCodeCustomerEnum.Exception,
							ErrorMessages.PAY_S_BOOKING_FAILED,orderid,refno);
				} catch (Exception e) {
					logger.error("Exception ", e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,
							ErrorMessages.PAY_S_BOOKING_FAILED,orderid,refno);

				}

				if(i == 0){
					orderonward = orderid;
					bookingDetailsonward =  bookingDetails;
					orderrowonward = flightOrderRow;
				}else{
					orderreturn = orderid;
					bookingDetailsreturn = bookingDetails;
					orderrowreturn = flightOrderRow;
				}

				// Update orderCustomer
				OrderCustomer orderCustomeronward  = orderrowonward.getCustomer();
				orderCustomeronward.setOrderId(orderrowonward.getOrderId());		
				try {
					FBDAO.updateOrderCustomerDetails(orderCustomeronward);
				} catch (Exception e1) {
					logger.error("Exception", e1);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}

				OrderCustomer orderCustomerreturn  = orderrowreturn.getCustomer();
				orderCustomerreturn.setOrderId(orderCustomerreturn.getOrderId());		
				try {
					FBDAO.updateOrderCustomerDetails(orderCustomerreturn);
				} catch (Exception e1) {
					logger.error("Exception", e1);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}
				// Update flightorderrow
				orderrowonward.setCustomer(orderCustomeronward);
				try {
					FBDAO.updateFlightOrderRowDetails(orderrowonward);
				} catch (Exception e) {
					logger.error("Exception", e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}
				orderrowreturn.setCustomer(orderCustomerreturn);
				try {
					FBDAO.updateFlightOrderRowDetails(orderrowreturn);
				} catch (Exception e) {
					logger.error("Exception", e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}



			} else {

				PaymentTransaction paymentTransaction = new PaymentTransaction();
				paymentTransaction.setIsPaymentSuccess(false);
				paymentTransaction.setTransactionId(transaction_id);
				paymentTransaction.setResponse_message(response_message);
				paymentTransaction.setResponseCode(response_code);
				paymentTransaction.setPayment_status("FAILED");
				paymentTransaction.setRefno(refno);
				paymentTransaction.setAuthorizationCode(AuthCode);

				flightBookingResponse = new FlightBookingResponse();

				flightBookingResponse.setTransactionKey(bookingDetails
						.getTransactionkey());
				flightBookingResponse.setFlightsearch(bookingDetails
						.getFlightPriceResponse().getFlightsearch());
				flightBookingResponse.setCountry(bookingDetails.getCountrycode());
				flightBookingResponse.setFlightCustomerDetails(bookingDetails
						.getFlightCustomerDetails());
				flightBookingResponse.setCache(false);

				flightBookingResponse.setPaymentStatus(false);
				String newpgid = "PGF" + new GenerateID().toString();

				flightBookingResponse.setPgID(refno);
				flightBookingResponse.setNewpgID(newpgid);
				flightBookingResponse
				.setPaymentCalledCount(Integer.parseInt(count));

				DBS.updatePaymentStatus(paymentTransaction, FBDAO);

				DBS.updatePNR("0#", orderid, FBDAO);

				PaymentTransaction paymentTransactionNew = new PaymentTransaction();
				paymentTransactionNew.setAmount(AmountRoundingModeUtil.roundingMode(new BigDecimal(bookingDetails
						.getFlightPriceResponse().getFareFlightSegment()
						.getTotalPrice())));
				paymentTransactionNew.setCurrency(bookingDetails
						.getFlightPriceResponse().getFareFlightSegment()
						.getCurrency());
				paymentTransactionNew.setRefno(newpgid);
				paymentTransactionNew.setIsPaymentSuccess(false);
				paymentTransactionNew.setCreatedAt(new java.sql.Timestamp(Calendar
						.getInstance().getTime().getTime()));
				paymentTransactionNew.setPayment_method(bookingDetails
						.getFlightCustomerDetails().getPaymode());
				paymentTransactionNew.setApi_transaction_id(orderid);
				paymentTransactionNew.setPayment_status("Pending");
				try {
					callPayment(refno, paymentTransactionNew, FBDAO);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Exception", e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,
							ErrorMessages.PAY_S_BOOKING_FAILED,orderid,refno);
				}

			}

			TravelportConfig travelportConfig=TravelportConfig.GetTravelportConfig();
			CommonConfig commonConfig=CommonConfig.GetCommonConfig();
			if(i==0&&flightBookingResponse.isBookingStatus()&&flightBookingResponse.getConfirmationNumber().startsWith("FTP")&&commonConfig.isIs_lintas_enabled()&&!travelportConfig.isTest()){
				sendPNRViaMAil(bookingDetails.getFlightCustomerDetails().getUserid(),flightBookingResponse.getPnr(), request, response);// logger.info("---------emailStatusIds SIZE--------"+emailStatusIds.size());

			}
			else if(i!=0&&flightBookingResponse.isBookingStatusSpecial()&&flightBookingResponse.getConfirmationNumberSpecial().startsWith("FTP")&&commonConfig.isIs_lintas_enabled()&&!travelportConfig.isTest()){
				sendPNRViaMAil(bookingDetails.getFlightCustomerDetails().getUserid(),flightBookingResponse.getPnrSpecial(), request, response);
			}

			flightBookingResponse.setGSTonMarkup(bookingDetails
					.getFlightPriceResponse().getGSTonMarkup());
			flightBookingResponse.setGSTonMarkupSpecial(bookingDetails
					.getFlightPriceResponse().getGSTonMarkupSpecial());
			flightBookingResponse.setFinalPriceWithGST(bookingDetails
					.getFlightPriceResponse().getFinalPriceWithGST());



		}
		flightBookingResponse.setConfirmationNumber(orderonward);
		flightBookingResponse.setConfirmationNumberSpecial(orderreturn);
		flightBookingResponse.setPnr(orderrowonward.getPnr());
		flightBookingResponse.setPnrSpecial(orderrowreturn.getPnr());
		flightBookingResponse.setBookingComments(orderrowonward.getStatusAction());		
		flightBookingResponse.setBookingCommentsSpecial(orderrowreturn.getStatusAction());
		if(orderrowonward.getStatusAction().equalsIgnoreCase("Confirmed"))
			flightBookingResponse.setBookingStatus(true);
		else
			flightBookingResponse.setBookingStatus(false);

		if(orderrowreturn.getStatusAction().equalsIgnoreCase("Confirmed"))
			flightBookingResponse.setBookingStatusSpecial(true);
		else
			flightBookingResponse.setBookingStatusSpecial(false);

		flightBookingResponse.setLastTicketingDate(bookingDetailsonward
				.getFlightPriceResponse().getFareFlightSegment().getLatestTicketingTime());
		flightBookingResponse.setLastTicketingDateSpecial(bookingDetailsreturn
				.getFlightPriceResponse().getSpecialFareFlightSegment().getLatestTicketingTime());

		flightBookingResponse.setFareFlightSegment(bookingDetailsonward
				.getFlightPriceResponse().getFareFlightSegment());
		flightBookingResponse.setPassengerFareBreakUps(bookingDetailsonward
				.getFlightPriceResponse().getPassengerFareBreakUps());				



		flightBookingResponse.setFareFlightSegmentSpecial(bookingDetailsreturn
				.getFlightPriceResponse().getSpecialFareFlightSegment());
		flightBookingResponse.setPassengerFareBreakUpsSpecial(bookingDetailsreturn
				.getFlightPriceResponse().getSpecialPassengerFareBreakUps());	

		// insert notication after booking is successful
		new NotificationUtil().insertNotification(appKeyVo,orderonward , "Flight Ticket Booking", InventoryTypeEnum.FLIGHT_ORDER.getId(), true,NFDAO,companyDao); 
		new NotificationUtil().insertNotification(appKeyVo,orderreturn , "Flight Ticket Booking", InventoryTypeEnum.FLIGHT_ORDER.getId(), true,NFDAO,companyDao); 


		flightBookingResponse.setProcessingfee(orderrowonward.getProcessingFees());
		flightBookingResponse.setPaymentStatus(true);
		flightBookingResponse.setCache(false);
		flightBookingResponse.setTransactionKey(bookingDetailsonward
				.getTransactionkey());
		flightBookingResponse.setFlightsearch(bookingDetailsonward
				.getFlightPriceResponse().getFlightsearch());
		flightBookingResponse.setCountry(bookingDetailsonward.getCountrycode());
		flightBookingResponse.setFlightCustomerDetails(bookingDetailsonward
				.getFlightCustomerDetails());

		flightBookingResponse.setPgID(refno);


		return flightBookingResponse;


	}


	@ExceptionHandler(BaseException.class)
	public @ResponseBody
	RestError handleCustomException(BaseException ex,
			HttpServletResponse response) {
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return ex
				.transformException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	public static void callPayment(String orderId,
			PaymentTransaction paymentTransaction, FlightBookingDao FBDAO)
					throws Exception {
		FlightDataBaseServices DBS = new FlightDataBaseServices();
		logger.info("callPayment method called : ");
		DBS.insertPaymentTransaction(paymentTransaction, FBDAO);
	}

	public void sendPNRViaMAil(String userid,String pnr,
			HttpServletRequest request, HttpServletResponse response) {	
		List<String> emailIdList=new ArrayList<String>();
		emailIdList.add("ilyasali85@gmail.com");
		final Locale locale = LocaleContextHolder.getLocale();
		for(String emailid:emailIdList){try {
			this.emailService.sendFlightPNR(userid,
					locale, request, response,
					servletContext, applicationContext,emailid,pnr);
			logger.info("EmailController send email call after emailService.sendSimpleMail----  ");

		} catch (Exception e) {

			e.printStackTrace();
		}

		}
	}
}
