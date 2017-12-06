package com.tayyarah.flight.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.apiconfig.model.TboFlightConfig;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.common.notification.NotificationUtil;
import com.tayyarah.common.notification.dao.NotificationDao;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.common.util.enums.CommonBookingStatusEnum;
import com.tayyarah.common.util.enums.InventoryTypeEnum;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.entity.FlightBookingKeysTemp;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.entity.FlightOrderCustomerSSR;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.ConfirmTicketRequest;
import com.tayyarah.flight.model.FlightBookingResponse;
import com.tayyarah.flight.model.FlightCustomerDetails;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.PassengerDetails;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.flight.util.FlightWebServiceEndPointValidator;
import com.tayyarah.flight.util.api.tbo.TboServiceCall;
import com.tayyarah.flight.util.api.travelport.UapiServiceCall;
import com.tayyarah.user.dao.UserWalletDAO;
import com.tayyarah.user.entity.WalletAmountTranferHistory;


@RestController
@RequestMapping("/confirmticket")
public class FlightBookHoldTicketingController {

	@Autowired
	CompanyDao companyDao;
	@Autowired
	FlightBookingDao flightBookingDao;
	@Autowired
	FlightTempAirSegmentDAO flightTempAirSegmentDAO;
	@Autowired
	EmailDao emaildao;
	@Autowired
	UserWalletDAO userWalletDAO;
	@Autowired
	NotificationDao notificationDao;

	private  FlightWebServiceEndPointValidator validator = new FlightWebServiceEndPointValidator();
	static final Logger logger = Logger.getLogger(FlightBookHoldTicketingController.class);
	public static final int VERSION = 1;
	private FlightDataBaseServices DBS = new FlightDataBaseServices();

	@RequestMapping(value = "/details", method = RequestMethod.POST,headers = {"Accept=application/json"})
	public @ResponseBody
	FlightBookingResponse ConfirmTicket(@RequestBody ConfirmTicketRequest confirmTicketRequest,HttpServletResponse response,HttpServletRequest request){
		FlightBookingResponse flightBookingResponse = null;
		ResponseHeader.setPostResponse(response);//Setting response header
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, confirmTicketRequest.getApp_key());
		if(appKeyVo==null)
		{
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
		}

		// Confirm Flight Ticket
		if(confirmTicketRequest.getRequesttype() == ConfirmTicketRequest.Type_ConfirmTicket){

			confirmTicketRequest.setApp_key(AppControllerUtil.getDecryptedAppKey(companyDao, confirmTicketRequest.getApp_key()));
			AppControllerUtil.validateTransactionKey(flightBookingDao,confirmTicketRequest.getTransactionkey());
			validator.bookingValidator(confirmTicketRequest.getPrice_key(),confirmTicketRequest.getTransactionkey(),confirmTicketRequest.getUsername(),confirmTicketRequest.getUserid(),confirmTicketRequest.getPaymode());
			FlightPriceResponse flightPriceResponse = null;
			try {
				flightPriceResponse = UapiServiceCall.getFlightPriceResponse(confirmTicketRequest.getPrice_key(),flightTempAirSegmentDAO);
			} catch (Exception e) {
				logger.error("Exception", e);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,FlightErrorMessages.INVALID_PRICEKEY);		}

			flightBookingResponse = confirmflightticket(confirmTicketRequest,flightPriceResponse,flightTempAirSegmentDAO,response,request,appKeyVo);
		}

		// Get Hold Ticket Details
		if(confirmTicketRequest.getRequesttype() == ConfirmTicketRequest.Type_GetHoldTicket){
			confirmTicketRequest.setApp_key(AppControllerUtil.getDecryptedAppKey(companyDao, confirmTicketRequest.getApp_key()));
			FlightOrderRow flightOrderRow = null;
			try {
				if(confirmTicketRequest.getOrderid().startsWith("FTBO")){
					flightOrderRow = flightBookingDao.getflightorderrow(confirmTicketRequest.getOrderid());
				}
				else{
					flightOrderRow = flightBookingDao.getflightorderrowbypnr(confirmTicketRequest.getOrderid());
				}
			}  catch (Exception e1) {
				logger.error("Exception", e1);
				flightBookingResponse = new FlightBookingResponse();
				flightBookingResponse.setBookingComments("Invalid Orderid or PNR");				
			}

			FlightBookingKeysTemp flightBookingKeysTemp = null;
			try{
				flightBookingKeysTemp = flightBookingDao.getpricekey(flightOrderRow.getTransaction_key());
				if(flightBookingKeysTemp!=null){
					if(flightBookingKeysTemp.isActive()){
						AppControllerUtil.validateTransactionKey(flightBookingDao,flightOrderRow.getTransaction_key());
						validator.bookingValidator(flightBookingKeysTemp.getPrice_key(),flightOrderRow.getTransaction_key(),flightOrderRow.getCreatedBy(),flightOrderRow.getUserId(),flightOrderRow.getPaidBy());
						FlightPriceResponse flightPriceResponse = null;
						try {
							flightPriceResponse = UapiServiceCall.getFlightPriceResponse(flightBookingKeysTemp.getPrice_key(),flightTempAirSegmentDAO);
						} catch (Exception e) {
							logger.error("Exception", e);
							throw new FlightException(ErrorCodeCustomerEnum.HibernateException,FlightErrorMessages.INVALID_PRICEKEY);
						}


						confirmTicketRequest.setPaymode(flightOrderRow.getPaidBy());
						confirmTicketRequest.setPrice_key(flightBookingKeysTemp.getPrice_key());
						confirmTicketRequest.setTransactionkey(flightOrderRow.getTransaction_key());
						confirmTicketRequest.setUserid(flightOrderRow.getUserId());
						confirmTicketRequest.setUsername(flightOrderRow.getCreatedBy());

						flightBookingResponse = getholdticketdetails(confirmTicketRequest,flightOrderRow,flightPriceResponse,flightTempAirSegmentDAO,response,request);


					}
					else{
						logger.info("Ticket Already Booked for this PNR");
						flightBookingResponse = new FlightBookingResponse();
						flightBookingResponse.setBookingComments("Ticket Already Booked/Released for this PNR");						
					}
				}

			}catch(Exception e){
				logger.error("Exception", e);
				flightBookingResponse = new FlightBookingResponse();
				flightBookingResponse.setBookingComments("Ticket Already Booked/Released for this PNR");				
			}
		}
		return flightBookingResponse;
	}

	// Get Hold Ticket Details
	public FlightBookingResponse getholdticketdetails(ConfirmTicketRequest confirmTicketRequest,FlightOrderRow flightOrderRow,FlightPriceResponse flightPriceResponse,FlightTempAirSegmentDAO TempDAO,HttpServletResponse response,HttpServletRequest request){
		FlightBookingResponse flightBookingResponse = new FlightBookingResponse();

		// Set final price
		flightPriceResponse.getFareFlightSegment().setTotalPrice(String.valueOf(flightOrderRow.getFinalPrice()));
		FlightCustomerDetails flightCustomerDetails = CreateFlightCustomerDetails(flightOrderRow,confirmTicketRequest);
		flightPriceResponse.getFareFlightSegment().setExtraMealPrice(String.valueOf(flightOrderRow.getExtramealprice()));
		flightPriceResponse.getFareFlightSegment().setExtraBaggagePrice(String.valueOf(flightOrderRow.getExtrabaggageprice()));
		flightBookingResponse.setFlightCustomerDetails(flightCustomerDetails);
		flightBookingResponse.setFareFlightSegment(flightPriceResponse.getFareFlightSegment());
		flightBookingResponse.setPassengerFareBreakUps(flightPriceResponse.getPassengerFareBreakUps());
		flightBookingResponse.setTransactionKey(confirmTicketRequest.getTransactionkey());
		flightBookingResponse.setFlightsearch(flightPriceResponse.getFlightsearch());
		flightBookingResponse.setCache(false);
		flightBookingResponse.setConfirmationNumber(confirmTicketRequest.getOrderid());
		flightBookingResponse.setPaymentCalledCount(0);
		flightBookingResponse.setLastTicketingDate(flightPriceResponse.getFareFlightSegment().getLatestTicketingTime());
		flightBookingResponse.setPnr(flightOrderRow.getPnr());
		flightBookingResponse.setBokingConditions("");
		flightBookingResponse.setBookingComments("Hold");
		flightBookingResponse.setBookingStatus(false);

		BigDecimal gstTotal= new BigDecimal(0);
		if(flightPriceResponse.getGSTonMarkup()!=null )
		{
			gstTotal = gstTotal.add(flightPriceResponse.getGSTonMarkup());
			flightBookingResponse.setGSTonMarkup(flightPriceResponse.getGSTonMarkup());
		}
		if(flightPriceResponse.getGSTonFlights()!=null)
		{
			gstTotal = gstTotal.add(flightPriceResponse.getGSTonFlights());
			flightBookingResponse.setGstOnMYArptTax(flightPriceResponse.getGSTonFlights());
		}
		flightBookingResponse.setGstTotal(gstTotal);

		// sending total gst in  markUPGst for testing purpose only
		flightBookingResponse.setGSTonMarkup(gstTotal);
		flightBookingResponse.setFinalPriceWithGST(new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()));
		return flightBookingResponse;

	}

	public FlightBookingResponse confirmflightticket(ConfirmTicketRequest confirmTicketRequest,FlightPriceResponse flightPriceResponse,FlightTempAirSegmentDAO TempDAO,HttpServletResponse response,HttpServletRequest request, AppKeyVo appKeyVo){
		FlightBookingResponse flightBookingResponse = null;
		// Get FlightOrderRow
		FlightOrderRow flightOrderRow = null;
		try {
			flightOrderRow = flightBookingDao.getflightorderrow(confirmTicketRequest.getOrderid());
		}  catch (Exception e1) {
			logger.error("Exception", e1);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
		}
		// Set final price
		flightPriceResponse.getFareFlightSegment().setTotalPrice(String.valueOf(flightOrderRow.getFinalPrice()));
		FlightCustomerDetails flightCustomerDetails = CreateFlightCustomerDetails(flightOrderRow,confirmTicketRequest);
		if(confirmTicketRequest.getPaymode().equals("cash")){
			boolean result = false;
			BigDecimal baseToBookingExchangeRate=flightPriceResponse.getFlightsearch().getBaseToBookingExchangeRate();;
			WalletAmountTranferHistory walletAmountTranferHistory=new WalletAmountTranferHistory();
			try {
				walletAmountTranferHistory.setActionId(confirmTicketRequest.getOrderid());
				walletAmountTranferHistory.setCurrency(flightPriceResponse.getFareFlightSegment().getCurrency());
				walletAmountTranferHistory.setAction("FlightBooking Initiated");
				walletAmountTranferHistory.setRemarks("Flight Hold Booking payment");
				result=userWalletDAO.getWalletStatus(confirmTicketRequest.getUserid(),new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()).divide(baseToBookingExchangeRate,2,RoundingMode.UP),walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_REMARKS.getMessage(),true);
			} catch (Exception e1) {
				logger.error("Exception", e1);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}
			if (result){
				try {
					TboFlightConfig tboFlightConfig = TboFlightConfig.GetTboConfig(appKeyVo);
					flightBookingResponse = TboServiceCall.callHoldTicketingService(flightBookingResponse, confirmTicketRequest.getOrderid(), flightBookingDao, emaildao, confirmTicketRequest.getTransactionkey(), confirmTicketRequest.getPaymode(), walletAmountTranferHistory, 10, flightOrderRow.getFlightOrderCustomers(), flightCustomerDetails, flightOrderRow,tboFlightConfig);

					// insert notication after booking is successful
					new NotificationUtil().insertNotification(appKeyVo,confirmTicketRequest.getOrderid() , "Flight Ticket Booking", InventoryTypeEnum.FLIGHT_ORDER.getId(), true,notificationDao,companyDao);

				} catch (Exception e) {
					logger.error("Exception", e);
					walletAmountTranferHistory.setRemarks("Flight Booking Failed");
					try {
						userWalletDAO.getWalletStatus(confirmTicketRequest.getUserid(),new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()).divide(baseToBookingExchangeRate,2,RoundingMode.UP),walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
					} catch (Exception e1) {					
						e1.printStackTrace();
					}
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);

				}
			}
		}
		flightPriceResponse.getFareFlightSegment().setExtraMealPrice(String.valueOf(flightOrderRow.getExtramealprice()));
		flightPriceResponse.getFareFlightSegment().setExtraBaggagePrice(String.valueOf(flightOrderRow.getExtrabaggageprice()));
		flightBookingResponse.setFlightCustomerDetails(flightCustomerDetails);
		flightBookingResponse.setFareFlightSegment(flightPriceResponse.getFareFlightSegment());
		flightBookingResponse.setPassengerFareBreakUps(flightPriceResponse.getPassengerFareBreakUps());
		flightBookingResponse.setTransactionKey(confirmTicketRequest.getTransactionkey());
		flightBookingResponse.setFlightsearch(flightPriceResponse.getFlightsearch());
		flightBookingResponse.setCache(false);
		flightBookingResponse.setConfirmationNumber(confirmTicketRequest.getOrderid());
		flightBookingResponse.setPaymentCalledCount(0);
		flightBookingResponse.setLastTicketingDate(flightPriceResponse.getFareFlightSegment().getLatestTicketingTime());

		BigDecimal gstTotal= new BigDecimal(0);
		if(flightPriceResponse.getGSTonMarkup()!=null )
		{
			gstTotal = gstTotal.add(flightPriceResponse.getGSTonMarkup());
			flightBookingResponse.setGSTonMarkup(flightPriceResponse.getGSTonMarkup());
		}
		if(flightPriceResponse.getGSTonFlights()!=null)
		{
			gstTotal = gstTotal.add(flightPriceResponse.getGSTonFlights());
			flightBookingResponse.setGstOnMYArptTax(flightPriceResponse.getGSTonFlights());
		}
		flightBookingResponse.setGstTotal(gstTotal);

		// sending total gst in  markUPGst for testing purpose only
		flightBookingResponse.setGSTonMarkup(gstTotal);
		flightBookingResponse.setFinalPriceWithGST(new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()));
		return flightBookingResponse;
	}

	private FlightCustomerDetails CreateFlightCustomerDetails(FlightOrderRow flightOrderRow,ConfirmTicketRequest confirmTicketRequest){
		FlightCustomerDetails flightCustomerDetails = new FlightCustomerDetails();
		flightCustomerDetails.setAddress(flightOrderRow.getFlightCustomer().getAddress());
		flightCustomerDetails.setCity(flightOrderRow.getFlightCustomer().getCity());
		flightCustomerDetails.setCountryCode(flightOrderRow.getFlightCustomer().getCountryId());
		flightCustomerDetails.setCountryId(flightOrderRow.getFlightCustomer().getCountryId());
		flightCustomerDetails.setEmail(flightOrderRow.getFlightCustomer().getEmail());
		flightCustomerDetails.setMobile(flightOrderRow.getFlightCustomer().getMobile());
		flightCustomerDetails.setPaymode(confirmTicketRequest.getPaymode());
		flightCustomerDetails.setPhone(flightOrderRow.getFlightCustomer().getPhone());
		flightCustomerDetails.setPrice_key(confirmTicketRequest.getPrice_key());
		flightCustomerDetails.setState(flightOrderRow.getFlightCustomer().getState());
		flightCustomerDetails.setTransactionkey(confirmTicketRequest.getTransactionkey());
		flightCustomerDetails.setUserid(confirmTicketRequest.getUserid());
		flightCustomerDetails.setUsername(confirmTicketRequest.getUsername());
		flightCustomerDetails.setZip(flightOrderRow.getFlightCustomer().getZip());

		List<PassengerDetails> passengerdetailslist = new ArrayList<PassengerDetails>();
		for (FlightOrderCustomer flightOrderCustomer : flightOrderRow.getFlightOrderCustomers()) {
			PassengerDetails passengerdetails = new PassengerDetails();
			passengerdetails.setTitle(flightOrderCustomer.getTitle());
			passengerdetails.setMiddleName(flightOrderCustomer.getMiddleName());
			passengerdetails.setFirstName(flightOrderCustomer.getFirstName());
			passengerdetails.setLastName(flightOrderCustomer.getLastName());
			passengerdetails.setNationalityCountry(flightOrderCustomer.getNationality());
			passengerdetails.setPassengerTypeCode(flightOrderCustomer.getPassengerTypeCode());
			passengerdetails.setPassportExpiryDate(flightOrderCustomer.getPassportExpiryDate());
			passengerdetails.setPassportNo(flightOrderCustomer.getPassportNo());
			passengerdetails.setPassportIssuingCountry(flightOrderCustomer.getPassportIssuingCountry());
			passengerdetails.setBirthday(flightOrderCustomer.getBirthday());
			passengerdetailslist.add(passengerdetails);
		}

		List<PassengerDetails> newpassengerdetailslist = new ArrayList<>();
		int i = 0;
		for (FlightOrderCustomerSSR ssr : flightOrderRow.getFlightOrderCustomerSSR()) {
			PassengerDetails passengerdetails = passengerdetailslist.get(i);
			passengerdetails.setMealname(ssr.getMealname());
			passengerdetails.setMealcode(ssr.getMealType());
			passengerdetails.setBaggagecode(ssr.getBaggageType());
			passengerdetails.setBaggageweight(ssr.getBaggageweight());
			passengerdetails.setSeatcode(ssr.getSeatType());
			passengerdetails.setSeatname(ssr.getSeatType());
			passengerdetails.setReturnmealcode(ssr.getReturnmealType());
			passengerdetails.setReturnmealname(ssr.getReturnmealname());
			passengerdetails.setReturnseatcode(ssr.getReturnseatType());
			passengerdetails.setReturnseatname(ssr.getReturnseatType());
			passengerdetails.setReturnbaggagecode(ssr.getReturnbaggageType());
			passengerdetails.setReturnbaggageweight(ssr.getReturnbaggageweight());
			newpassengerdetailslist.add(passengerdetails);
			i++;
		}
		flightCustomerDetails.setPassengerdetailsList(newpassengerdetailslist);
		return flightCustomerDetails;
	}
}