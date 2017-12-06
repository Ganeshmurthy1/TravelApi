package com.tayyarah.flight.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.api.flight.tbo.model.Baggage;
import com.tayyarah.api.flight.tbo.model.Meal;
import com.tayyarah.api.flight.tbo.model.MealDynamic;
import com.tayyarah.api.flight.tbo.model.Seat;
import com.tayyarah.api.flight.tbo.model.TboFlightAirpriceResponse;
import com.tayyarah.apiconfig.model.BluestarConfig;
import com.tayyarah.apiconfig.model.TboFlightConfig;
import com.tayyarah.apiconfig.model.TravelportConfig;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.model.CommissionDetails;
import com.tayyarah.common.notification.NotificationUtil;
import com.tayyarah.common.notification.dao.NotificationDao;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.GenerateID;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.common.util.enums.InventoryTypeEnum;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.flight.commission.model.AirlineCommision;
import com.tayyarah.flight.commission.model.AirlineLiteral;
import com.tayyarah.flight.commission.remarks.util.Constants;
import com.tayyarah.flight.dao.AirlineDAO;
import com.tayyarah.flight.dao.AirportDAO;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.entity.FlightBookingDetailsTemp;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.entity.FlightOrderCustomerPriceBreakup;
import com.tayyarah.flight.entity.FlightOrderCustomerSSR;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.entity.FlightOrderRowCommission;
import com.tayyarah.flight.entity.FlightOrderRowMarkup;
import com.tayyarah.flight.entity.FlightOrderTripDetail;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.BookingDetails;
import com.tayyarah.flight.model.Cabin;
import com.tayyarah.flight.model.Carrier;
import com.tayyarah.flight.model.Flight;
import com.tayyarah.flight.model.FlightBookingResponse;
import com.tayyarah.flight.model.FlightCustomerDetails;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.FlightSegments;
import com.tayyarah.flight.model.MarkupCommissionDetails;
import com.tayyarah.flight.model.PassengerDetails;
import com.tayyarah.flight.model.PassengerFareBreakUp;
import com.tayyarah.flight.model.Segments;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.flight.util.FlightWebServiceEndPointValidator;
import com.tayyarah.flight.util.api.lintas.LintasServiceCall;
import com.tayyarah.flight.util.api.tayyarah.TayyarahServiceCall;
import com.tayyarah.flight.util.api.tbo.TboServiceCall;
import com.tayyarah.flight.util.api.travelport.UapiServiceCall;
import com.tayyarah.flight.util.api.travelport.UmarkUpServiceCall;
import com.tayyarah.services.CommissionService;
import com.tayyarah.user.dao.UserWalletDAO;
import com.tayyarah.user.entity.WalletAmountTranferHistory;


@RestController
@RequestMapping("/holdbooking")
public class FlightHoldBookingController {

	@Autowired
	CompanyDao companyDao;
	@Autowired
	CompanyConfigDAO CmpconfgDao;
	@Autowired
	FlightTempAirSegmentDAO TempDAO; 
	@Autowired
	FlightBookingDao FBDAO;
	@Autowired
	CompanyDao CDAO;
	@Autowired
	EmailDao emaildao;
	@Autowired
	CommissionService commissionService;
	@Autowired
	ServletContext servletContext;
	@Autowired
	ApplicationContext applicationContext;
	@Autowired
	UserWalletDAO AWDAO;
	@Autowired
	AirlineDAO airlineDAO;
	@Autowired
	AirportDAO ADAO;	
	@Autowired
	NotificationDao NFDAO;

	private  FlightWebServiceEndPointValidator validator = new FlightWebServiceEndPointValidator();
	static final Logger logger = Logger.getLogger(FlightHoldBookingController.class);
	public static final int VERSION=1;
	private FlightDataBaseServices DBS = new FlightDataBaseServices();

	@RequestMapping(value = "/details", method = RequestMethod.POST,headers = {"Accept=application/json"})
	public @ResponseBody
	FlightBookingResponse HoldBookingDetails(@RequestBody FlightCustomerDetails flightCustomerDetails,HttpServletResponse response,HttpServletRequest request){
		logger.info("Hold Booking Method ");

		FlightBookingResponse flightBookingResponse = null;
		ResponseHeader.setResponse(response);
		flightCustomerDetails.setApp_key(AppControllerUtil.getDecryptedAppKey(CDAO, flightCustomerDetails.getApp_key())); 
		AppControllerUtil.validateTransactionKey(FBDAO,flightCustomerDetails.getTransactionkey());
		validator.bookingValidator(flightCustomerDetails.getPrice_key(),flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getUsername(),flightCustomerDetails.getUserid(),flightCustomerDetails.getPaymode());
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, flightCustomerDetails.getApp_key());
		if(appKeyVo==null)
		{
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
		}

		// Get Flight Price Response from DB using price key
		FlightPriceResponse flightPriceResponse = null;
		try {
			flightPriceResponse = UapiServiceCall.getFlightPriceResponse(flightCustomerDetails.getPrice_key(),TempDAO);
		} catch (Exception e) {
			logger.error("Exception", e);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,FlightErrorMessages.INVALID_PRICEKEY);
		}

		if(flightPriceResponse.getFlightsearch().isSpecialSearch() && (flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("R") || flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("SR"))){

			double mealprice = 0;
			double baggageprice = 0;
			double flighttotalprice = Double.parseDouble(flightPriceResponse.getFareFlightSegment().getTotalPrice());
			double returnmealprice = 0;
			double returnbaggageprice = 0;
			double returnflighttotalprice = Double.parseDouble(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice());

			if(flightPriceResponse.getSpecialServiceRequest()!=null && flightPriceResponse.getSpecialServiceRequest().isIsLCC()){			
				for (PassengerDetails PassengerDetail : flightCustomerDetails.getPassengerdetailsList()) {				
					if(PassengerDetail.getMealcode()!=null && PassengerDetail.getMealcode()!=""){
						for (List<MealDynamic> mealtype : flightPriceResponse.getSpecialServiceRequest().getMealDynamic()) {
							for (MealDynamic mealDynamicobj : mealtype) {
								if(PassengerDetail.getMealcode().equalsIgnoreCase(mealDynamicobj.getCode())){
									if(flightPriceResponse.getFlightsearch().getOrigin().equalsIgnoreCase(mealDynamicobj.getOrigin())){
										mealprice += Double.parseDouble(String.valueOf(mealDynamicobj.getPrice())) ;
										PassengerDetail.setMealname(mealDynamicobj.getAirlineDescription());
									}
								}
							}
						}
					}
					if(PassengerDetail.getBaggagecode()!=null && PassengerDetail.getBaggagecode()!=""){
						for (List<Baggage> baggagetype : flightPriceResponse.getSpecialServiceRequest().getBaggage()) {
							for (Baggage baggageitem : baggagetype) {
								if(PassengerDetail.getBaggagecode().equalsIgnoreCase(baggageitem.getCode())){						
									baggageprice += baggageitem.getPrice();
									PassengerDetail.setBaggageweight(String.valueOf(baggageitem.getWeight()));
								}
							} 

						}
					}					
				}	
			}			
			if(flightPriceResponse.getReturnspecialServiceRequest()!=null && flightPriceResponse.getReturnspecialServiceRequest().isIsLCC()){			
				for (PassengerDetails PassengerDetail : flightCustomerDetails.getPassengerdetailsList()) {				
					if(PassengerDetail.getReturnmealcode()!=null && PassengerDetail.getReturnmealcode()!=""){
						for (List<MealDynamic> mealtype : flightPriceResponse.getReturnspecialServiceRequest().getMealDynamic()) {
							for (MealDynamic mealDynamicobj : mealtype) {
								if(PassengerDetail.getReturnmealcode().equalsIgnoreCase(mealDynamicobj.getCode())){
									if(flightPriceResponse.getFlightsearch().getOrigin().equalsIgnoreCase(mealDynamicobj.getDestination())){
										returnmealprice +=  Double.parseDouble(String.valueOf(mealDynamicobj.getPrice())) ;
										PassengerDetail.setReturnmealname(mealDynamicobj.getAirlineDescription());
									}
								}
							}
						}
					}
					if(PassengerDetail.getReturnbaggagecode()!=null && PassengerDetail.getReturnbaggagecode()!=""){
						for (List<Baggage> baggagetype : flightPriceResponse.getReturnspecialServiceRequest().getBaggage()) {
							for (Baggage baggageitem : baggagetype) {
								if(PassengerDetail.getReturnbaggagecode().equalsIgnoreCase(baggageitem.getCode())){						
									returnbaggageprice += baggageitem.getPrice();
									PassengerDetail.setReturnbaggageweight(String.valueOf(baggageitem.getWeight()));
								}
							} 

						}
					}					
				}	
			}	

			double finalflighttotalprice = flighttotalprice + mealprice + baggageprice;	
			flightPriceResponse.getFareFlightSegment().setExtraBaggagePrice(String.valueOf(baggageprice));
			flightPriceResponse.getFareFlightSegment().setExtraMealPrice(String.valueOf(mealprice));	
			flightPriceResponse.getFareFlightSegment().setTotalPrice(String.valueOf(finalflighttotalprice));
			double finalflighttotalpricewithoutmarkup = Double.parseDouble(flightPriceResponse.getFareFlightSegment().getTotalPriceWithoutMarkup()); 
			finalflighttotalpricewithoutmarkup = finalflighttotalpricewithoutmarkup + mealprice + baggageprice;	
			flightPriceResponse.getFareFlightSegment().setTotalPriceWithoutMarkup(String.valueOf(finalflighttotalpricewithoutmarkup));
			double returnfinalflighttotalprice = returnflighttotalprice + returnmealprice + returnbaggageprice;	
			flightPriceResponse.getSpecialFareFlightSegment().setExtraBaggagePrice(String.valueOf(returnbaggageprice));
			flightPriceResponse.getSpecialFareFlightSegment().setExtraMealPrice(String.valueOf(returnmealprice));	
			flightPriceResponse.getSpecialFareFlightSegment().setTotalPrice(String.valueOf(returnfinalflighttotalprice));
			double returnfinalflighttotalpricewithoutmarkup = Double.parseDouble(flightPriceResponse.getSpecialFareFlightSegment().getTotalPriceWithoutMarkup()); 
			finalflighttotalpricewithoutmarkup = finalflighttotalpricewithoutmarkup + returnmealprice + returnbaggageprice;	
			flightPriceResponse.getSpecialFareFlightSegment().setTotalPriceWithoutMarkup(String.valueOf(returnfinalflighttotalpricewithoutmarkup));
			flightPriceResponse.setFinalPriceWithGST(new BigDecimal(String.valueOf((finalflighttotalprice + returnfinalflighttotalprice))));

		}else{
			double mealprice = 0;
			double baggageprice = 0;
			double flighttotalprice = Double.parseDouble(flightPriceResponse.getFareFlightSegment().getTotalPrice());
			if(!flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("R")){
				if(flightPriceResponse.getSpecialServiceRequest()!=null && flightPriceResponse.getSpecialServiceRequest().isIsLCC()){			
					for (PassengerDetails PassengerDetail : flightCustomerDetails.getPassengerdetailsList()) {				
						if(PassengerDetail.getMealcode()!=null && PassengerDetail.getMealcode()!=""){
							for (List<MealDynamic> mealtype : flightPriceResponse.getSpecialServiceRequest().getMealDynamic()) {
								for (MealDynamic mealDynamicobj : mealtype) {
									if(PassengerDetail.getMealcode().equalsIgnoreCase(mealDynamicobj.getCode())){
										if(flightPriceResponse.getFlightsearch().getOrigin().equalsIgnoreCase(mealDynamicobj.getOrigin())){
											mealprice +=   Double.parseDouble(String.valueOf(mealDynamicobj.getPrice())) ;
											PassengerDetail.setMealname(mealDynamicobj.getAirlineDescription());
										}
									}
								}
							}
						}
						if(PassengerDetail.getBaggagecode()!=null && PassengerDetail.getBaggagecode()!=""){
							for (List<Baggage> baggagetype : flightPriceResponse.getSpecialServiceRequest().getBaggage()) {
								for (Baggage baggageitem : baggagetype) {
									if(PassengerDetail.getBaggagecode().equalsIgnoreCase(baggageitem.getCode())){						
										baggageprice += baggageitem.getPrice();
										PassengerDetail.setBaggageweight(String.valueOf(baggageitem.getWeight()));
									}
								} 
							}
						}					
					}	
				}
			}
			if(flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("R")){

				if(flightPriceResponse.getSpecialServiceRequest()!=null && flightPriceResponse.getSpecialServiceRequest().isIsLCC()){			
					for (PassengerDetails PassengerDetail : flightCustomerDetails.getPassengerdetailsList()) {				
						if(PassengerDetail.getMealcode()!=null && PassengerDetail.getMealcode()!=""){
							for (MealDynamic mealDynamicobj : flightPriceResponse.getSpecialServiceRequest().getMealDynamic().get(0)) {								
								if(PassengerDetail.getMealcode().equalsIgnoreCase(mealDynamicobj.getCode())){										
									mealprice +=   Double.parseDouble(String.valueOf(mealDynamicobj.getPrice())) ;
									PassengerDetail.setMealname(mealDynamicobj.getAirlineDescription());
								}
							}									
						}
						if(PassengerDetail.getReturnmealcode()!=null && PassengerDetail.getReturnmealcode()!=""){
							for (MealDynamic mealDynamicobj : flightPriceResponse.getSpecialServiceRequest().getMealDynamic().get(1)) {								
								if(PassengerDetail.getReturnmealcode().equalsIgnoreCase(mealDynamicobj.getCode())){											
									mealprice +=   Double.parseDouble(String.valueOf(mealDynamicobj.getPrice())) ;
									PassengerDetail.setReturnmealname(mealDynamicobj.getAirlineDescription());
								}

							}
						}
						if(PassengerDetail.getBaggagecode()!=null && PassengerDetail.getBaggagecode()!=""){
							for (Baggage baggageitem : flightPriceResponse.getSpecialServiceRequest().getBaggage().get(0)) {								
								if(PassengerDetail.getBaggagecode().equalsIgnoreCase(baggageitem.getCode())){						
									baggageprice += baggageitem.getPrice();
									PassengerDetail.setBaggageweight(String.valueOf(baggageitem.getWeight()));
								}


							}
						}
						if(PassengerDetail.getReturnbaggagecode()!=null && PassengerDetail.getReturnbaggagecode()!=""){
							for (Baggage baggageitem : flightPriceResponse.getSpecialServiceRequest().getBaggage().get(1)) {								
								if(PassengerDetail.getReturnbaggagecode().equalsIgnoreCase(baggageitem.getCode())){						
									baggageprice += baggageitem.getPrice();
									PassengerDetail.setReturnbaggageweight(String.valueOf(baggageitem.getWeight()));
								}							 

							}
						}						
					}	
				}
			}

			double finalflighttotalprice = flighttotalprice + mealprice + baggageprice;	
			flightPriceResponse.getFareFlightSegment().setExtraBaggagePrice(String.valueOf(baggageprice));
			flightPriceResponse.getFareFlightSegment().setExtraMealPrice(String.valueOf(mealprice));	
			flightPriceResponse.getFareFlightSegment().setTotalPrice(String.valueOf(finalflighttotalprice));
			double finalflighttotalpricewithoutmarkup = Double.parseDouble(flightPriceResponse.getFareFlightSegment().getTotalPriceWithoutMarkup()); 
			finalflighttotalpricewithoutmarkup = finalflighttotalpricewithoutmarkup + mealprice + baggageprice;	
			flightPriceResponse.getFareFlightSegment().setTotalPriceWithoutMarkup(String.valueOf(finalflighttotalpricewithoutmarkup));
			flightPriceResponse.setFinalPriceWithGST(new BigDecimal(String.valueOf(finalflighttotalprice)));
			flightBookingResponse = createFlightOrderCustomerData(flightCustomerDetails,TempDAO,flightPriceResponse,response,request,appKeyVo);
		}
		flightBookingResponse.setFlightCustomerDetails(flightCustomerDetails);
		return flightBookingResponse;
	}

	public FlightBookingResponse createFlightOrderCustomerData(FlightCustomerDetails flightCustomerDetails,FlightTempAirSegmentDAO TempDAO,FlightPriceResponse flightPriceResponse,HttpServletResponse response,HttpServletRequest request, AppKeyVo appKeyVo){
		List<PassengerDetails> passengerdetailsList=flightCustomerDetails.getPassengerdetailsList();
		logger.info("createFOC method called : ");
		//details of the first passenger
		OrderCustomer orderCustomer=new OrderCustomer();
		FlightOrderRow flightOrderRow=new FlightOrderRow();
		for(PassengerDetails passengerDetails:passengerdetailsList){
			orderCustomer.setFirstName(passengerDetails.getFirstName());
			orderCustomer.setLastName(passengerDetails.getLastName());
			orderCustomer.setBirthday(passengerDetails.getBirthday());
			orderCustomer.setGender(passengerDetails.getTitle());
			orderCustomer.setTitle(passengerDetails.getTitle());			
			break;
		}
		orderCustomer.setAddress(flightCustomerDetails.getAddress());
		orderCustomer.setAddress2(flightCustomerDetails.getAddress2());
		orderCustomer.setCity(flightCustomerDetails.getCity());
		orderCustomer.setCountryId(flightCustomerDetails.getCountryId());
		orderCustomer.setEmail(flightCustomerDetails.getEmail());
		orderCustomer.setMobile(flightCustomerDetails.getMobile());
		orderCustomer.setPhone(flightCustomerDetails.getPhone());
		orderCustomer.setZip(flightCustomerDetails.getZip());	
		orderCustomer.setVersion(VERSION);
		orderCustomer.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		orderCustomer.setState(flightCustomerDetails.getState());
		try {
			FBDAO.insertOrderCustomerDetails(orderCustomer);
		} catch (Exception e1) {
			logger.error("Exception", e1);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
		}

		flightOrderRow.setCustomer(orderCustomer);
		flightOrderRow.setFlightCustomer(orderCustomer);
		flightOrderRow.setPassengerCount(passengerdetailsList.size());
		BigDecimal apiToBaseExchangeRate=flightPriceResponse.getFareFlightSegment().getApiToBaseExchangeRate();
		BigDecimal baseToBookingExchangeRate=flightPriceResponse.getFlightsearch().getBaseToBookingExchangeRate();;
		flightOrderRow.setBooking_currency(flightPriceResponse.getFlightsearch().getBookedCurrency());//requested currency
		flightOrderRow.setApiCurrency(flightPriceResponse.getFareFlightSegment().getApiCurrency());//currency of the APi response
		flightOrderRow.setBaseCurrency(flightPriceResponse.getFlightsearch().getBaseCurrency());//currency of the system 
		flightOrderRow.setBaseToBookingExchangeRate(baseToBookingExchangeRate);
		flightOrderRow.setApiToBaseExchangeRate(apiToBaseExchangeRate);		
		flightOrderRow.setGst_on_markup(flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP));
		flightOrderRow.setGstOnFlights(flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP));
		String orderId="FTP"+new GenerateID().toString();
		flightOrderRow.setProviderAPI("Travelport");
		if(flightCustomerDetails.getPrice_key().startsWith("PBS")){
			orderId="FBS"+new GenerateID().toString();
			flightOrderRow.setProviderAPI("Bluestar");
		}
		if(flightCustomerDetails.getPrice_key().startsWith("PTAY")){
			orderId="FTAY"+new GenerateID().toString();
			flightOrderRow.setProviderAPI("Tayyarah");
		}
		if(flightCustomerDetails.getPrice_key().startsWith("PLIN")){
			orderId="FLIN"+new GenerateID().toString();
			flightOrderRow.setProviderAPI("Lintas");
		}
		if(flightCustomerDetails.getPrice_key().startsWith("PTB")){
			orderId="FTBO"+new GenerateID().toString();
			flightOrderRow.setProviderAPI("TBO");
		}
		flightOrderRow.setArrivalDate(flightPriceResponse.getFlightsearch().getArvlDate());
		flightOrderRow.setDepartureDate(flightPriceResponse.getFlightsearch().getDepDate());
		flightOrderRow.setDestination(flightPriceResponse.getFlightsearch().getDestination());
		flightOrderRow.setOrigin(flightPriceResponse.getFlightsearch().getOrigin());
		flightOrderRow.setTripType(flightPriceResponse.getFlightsearch().getTripType());
		flightOrderRow.setPaymentStatus("pending");
		flightOrderRow.setPaidBy(flightCustomerDetails.getPaymode());
		flightOrderRow.setStatusAction("Initiated");
		flightOrderRow.setVersion(VERSION);
		flightOrderRow.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		flightOrderRow.setOrderId(orderId);
		flightOrderRow.setTransaction_key(flightCustomerDetails.getTransactionkey());
		flightOrderRow.setConfigId(flightCustomerDetails.getApp_key().substring(0,flightCustomerDetails.getApp_key().indexOf("-")));
		flightOrderRow.setCompanyId(flightCustomerDetails.getApp_key().substring(flightCustomerDetails.getApp_key().indexOf("-")+1));
		flightOrderRow.setCreatedBy(flightCustomerDetails.getUsername());//
		flightOrderRow.setUserId(flightCustomerDetails.getUserid());

		MarkupCommissionDetails markupCommissionDetails = flightPriceResponse.getMarkupCommissionDetails();
		String airlineCode ="";
		String cabinCode ="";
		if(flightPriceResponse.getFareFlightSegment()!=null && flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups()!=null && flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().size()>0 && flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments()!=null && flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().size()>0 && flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0)!=null)
		{
			FlightSegments flightSegments = flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0);
			if(flightSegments.getSegments()!=null && flightSegments.getSegments().size()>0 && flightSegments.getSegments().get(0)!=null)
			{	
				Segments segments = flightSegments.getSegments().get(0);
				airlineCode=segments.getCarrier().getCode();
				if(segments.getCabin()!=null && segments.getCabin().getCode()!=null)
					cabinCode=segments.getCabin().getCode();
			}
		}
		String fareBasisCode="ALL";
		if(flightPriceResponse.getFareFlightSegment().getFareBasisCode()!=null){
			fareBasisCode=flightPriceResponse.getFareFlightSegment().getFareBasisCode();
		}
		try {
			UmarkUpServiceCall.getMarkupValuesForEachCOMpany(flightPriceResponse.getFlightsearch(), flightPriceResponse.getFlightMarkUpConfiglistMap(),airlineCode , flightPriceResponse.getFareFlightSegment(),markupCommissionDetails,fareBasisCode);
		} catch (Exception e1) {
			logger.error("MArkUpException", e1);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
		}
		List<FlightOrderRowMarkup> flightOrderRowMarkups = getMarkupDetail(markupCommissionDetails,flightOrderRow);
		flightOrderRow.setFlightOrderRowMarkupList(flightOrderRowMarkups);

		// check for deal sheet 
		List<FlightOrderRowCommission> flightOrderRowCommissions = null;
		int configId = Integer.valueOf(flightOrderRow.getConfigId());			
		CompanyConfig companyConfig = null;
		try {
			companyConfig = CmpconfgDao.getCompanyConfigByConfigId(configId);
		} catch (Exception e2) {
			e2.printStackTrace();
		}	

		logger.info("################## flight booking--createFlightOrderCustomerData-------");	

		if(companyConfig != null && companyConfig.isSheetMode())
		{
			logger.info("################## flight booking--createFlightOrderCustomerData-- SheetMode-----");					

			HashMap<Integer, AirlineLiteral> airlineLitralMap = new HashMap<Integer, AirlineLiteral>();
			airlineLitralMap.put(Constants.TYPE_FARE, new AirlineLiteral(Constants.TYPE_FARE, fareBasisCode, true));
			airlineLitralMap.put(Constants.TYPE_CLASS, new AirlineLiteral(Constants.TYPE_CLASS, cabinCode, true));

			// commented for testing purpose
			/*airlineLitralMap.put(Constants.TYPE_PASSENGER, new AirlineLiteral(Constants.TYPE_PASSENGER, "ALL", true));
			airlineLitralMap.put(Constants.TYPE_CITY, new AirlineLiteral(Constants.TYPE_CITY, "DEL", true));

			airlineLitralMap.put(Constants.TYPE_COUNTRY_CODE, new AirlineLiteral(Constants.TYPE_COUNTRY_CODE, "IN", true));
			airlineLitralMap.put(Constants.TYPE_TRAVEL_COUNTRY, new AirlineLiteral(Constants.TYPE_TRAVEL_COUNTRY, "IN", true));*/

			//use below for later use
			/*
			 airlineLitralMap.put(Constants.TYPE_DATE_IB, new AirlineLiteral(Constants.TYPE_DATE_IB, "22/09/2012", true));
			 airlineLitralMap.put(Constants.TYPE_DATE_OB, new AirlineLiteral(Constants.TYPE_DATE_OB, "22/09/2012", true));
			 airlineLitralMap.put(Constants.TYPE_SECTOR, new AirlineLiteral(Constants.TYPE_SECTOR, "22/09/2012", true));

			AirlineLiteral airline = new AirlineLiteral(Constants.TYPE_AIRLINE, "1a", true);
			ArrayList<AirlineLiteral> airlines = new ArrayList<AirlineLiteral>();
			airlines.add(airline);
			AirlineLiteral codeshare = new AirlineLiteral(Constants.TYPE_CODE_SHARE, "codeshare", true);
			codeshare.setItems(airlines);
			airlineLitralMap.put(Constants.TYPE_SOTO, new AirlineLiteral(Constants.TYPE_SOTO, "22/09/2012", true));
			airlineLitralMap.put(Constants.TYPE_ROUTE, new AirlineLiteral(Constants.TYPE_ROUTE, "22/09/2012", true));
			airlineLitralMap.put(Constants.TYPE_CODE_SHARE, codeshare, true);
			AirlineLiteral airline = new AirlineLiteral(Constants.TYPE_AIRLINE, "1a", true);
			ArrayList<AirlineLiteral> airlines = new ArrayList<AirlineLiteral>();
			airlines.add(airline);
			AirlineLiteral codeshare = new AirlineLiteral(Constants.TYPE_CODE_SHARE, "interline", true);
			codeshare.setItems(airlines);
			airlineLitralMap.put(Constants.TYPE_INTERLINE, new AirlineLiteral(Constants.TYPE_INTERLINE, "22/09/2012", true));*/


			Map<Integer, AirlineCommision> airlineCommisionMap = commissionService.getAirLineCommission(airlineCode, getIntValue(flightOrderRow.getCompanyId()), getIntValue(flightOrderRow.getConfigId()), airlineLitralMap);
			flightOrderRowCommissions = getCommissionDetails(airlineCommisionMap, flightOrderRow);
		}
		else
		{
			logger.info("################## flight booking--createFlightOrderCustomerData--normal commission mode-----");					
			flightOrderRowCommissions = getCommissionDetails(markupCommissionDetails, flightOrderRow);
		}

		try {
			UmarkUpServiceCall.getCommissionWithMarkupValuesForEachCOMpany(flightOrderRowCommissions,flightPriceResponse.getFlightsearch(), flightPriceResponse.getFlightMarkUpConfiglistMap(),airlineCode , flightPriceResponse.getFareFlightSegment(),markupCommissionDetails,fareBasisCode);
		} catch (Exception e1) {
			logger.error("MArkUpException", e1);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
		}

		flightOrderRow.setFlightOrderRowCommissionList(flightOrderRowCommissions);
		flightOrderRow.setFinalPrice(new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()));//???
		flightOrderRow.setProcessingFees(new BigDecimal(flightPriceResponse.getFareFlightSegment().getTaxes()));//???
		flightOrderRow.setPrice(new BigDecimal(flightPriceResponse.getFareFlightSegment().getBasePriceWithoutMarkup()));//???

		BigDecimal markup = getTotalMarkup( markupCommissionDetails);  
		flightOrderRow.setMarkUp(markup);//in base currency

		BigDecimal processingFees = new BigDecimal("0.0");//??in booked currency
		if(!flightCustomerDetails.getPaymode().equals("cash")){		
			processingFees = flightPriceResponse.getFinalPriceWithGST().divide(new BigDecimal("100")).multiply(new BigDecimal("2.0")) ;
		}
		flightOrderRow.setProcessingFees(processingFees);
		flightOrderRow.setPrice(new BigDecimal(flightPriceResponse.getFareFlightSegment().getBasePriceWithoutMarkup()));//???in api currency
		flightOrderRow.setTotalTaxes(new BigDecimal(flightPriceResponse.getFareFlightSegment().getApi_taxesWithoutMarkup()));//in api currency

		String totalPriceInBookedCurrency=flightPriceResponse.getFareFlightSegment().getTotalPrice();//in booked price
		BigDecimal totalPrice=new BigDecimal(totalPriceInBookedCurrency).add(flightOrderRow.getProcessingFees());//in booked currency
		flightOrderRow.setFinalPrice(totalPrice);
		flightOrderRow.setLastTicketingDate(flightPriceResponse.getFareFlightSegment().getLatestTicketingTime() );
		flightOrderRow.setExtramealprice(new BigDecimal(flightPriceResponse.getFareFlightSegment().getExtraMealPrice()));
		flightOrderRow.setExtrabaggageprice(new BigDecimal(flightPriceResponse.getFareFlightSegment().getExtraBaggagePrice()));

		try {
			FBDAO.insertFlightOrderRowDetails(flightOrderRow);
		} catch (Exception e) {
			logger.error("Exception", e);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
		}


		//details of all passengers
		List<FlightOrderCustomer> flightOrderCustomers=new ArrayList<FlightOrderCustomer>();		 
		createFlightOrderCustomerList(flightOrderCustomers,flightCustomerDetails,orderCustomer,flightOrderRow);
		flightOrderRow.setFlightOrderCustomers(flightOrderCustomers);

		List<FlightOrderCustomerSSR> flightOrderCustomersSSRlist = new ArrayList<FlightOrderCustomerSSR>();		 
		createFlightOrderCustomerSSRDetails(flightCustomerDetails,flightOrderCustomersSSRlist,flightPriceResponse,orderCustomer,flightOrderRow,false);
		flightOrderRow.setFlightOrderCustomerSSR(flightOrderCustomersSSRlist);

		List<FlightOrderCustomerPriceBreakup> flightOrderCustomerPriceBreakups=new ArrayList<FlightOrderCustomerPriceBreakup>();
		createFlightOrderCustomerPriceBreakupList(flightOrderCustomerPriceBreakups,flightPriceResponse,orderCustomer,flightOrderRow,0);
		flightOrderRow.setFlightOrderCustomerPriceBreakups(flightOrderCustomerPriceBreakups);

		//details of all segments
		List<FlightOrderTripDetail> flightOrderTripDetails=new ArrayList<FlightOrderTripDetail>();
		createFlightOrderTripDetailList(flightOrderTripDetails,flightPriceResponse,orderCustomer,flightOrderRow,0);
		flightOrderRow.setFlightOrderTripDetails(flightOrderTripDetails);
		FlightBookingResponse flightBookingResponse=null;

		//////////setting markup deatails/ commission
		if(markupCommissionDetails!=null &&markupCommissionDetails.getRateType()!=null){
			for(FlightOrderRowMarkup flightOrderRowMarkup:flightOrderRowMarkups)
				try {
					FBDAO.insertMarkupDetails(flightOrderRowMarkup);
				} catch (Exception e) {
					logger.error("HibernateException", e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}

			for(FlightOrderRowCommission flightOrderRowCommission:flightOrderRowCommissions)
				try {
					FBDAO.insertCommission(flightOrderRowCommission);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("HibernateException", e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}
		}


		/////////////////////////////
		for(FlightOrderCustomerPriceBreakup flightOrderCustomerPriceBreakup:flightOrderRow.getFlightOrderCustomerPriceBreakups()){
			try {
				FBDAO.insertFlightOrderCustomerPriceBreakupDetails(flightOrderCustomerPriceBreakup);
			} catch (Exception e) {
				logger.error("Exception", e);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}
		}
		for(FlightOrderCustomer flightOrderCustomer:flightOrderRow.getFlightOrderCustomers()){
			try {
				FBDAO.insertFlightOrderCustomerDetails(flightOrderCustomer);
			} catch (Exception e) {
				logger.error("Exception", e);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}
		}
		for(FlightOrderTripDetail flightOrderTripDetail:flightOrderRow.getFlightOrderTripDetails()){
			try {
				FBDAO.insertFlightOrderTripDetailDetails(flightOrderTripDetail);
			} catch (Exception e) {
				logger.error("Exception", e);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}
		}
		if(flightOrderCustomersSSRlist.size() > 0){
			for(FlightOrderCustomerSSR flightOrderCustomerSSR:flightOrderCustomersSSRlist){
				try {
					FBDAO.insertFlightOrderCustomerSSRDetails(flightOrderCustomerSSR);
				} catch (Exception e) {
					logger.error("Exception", e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}
			}
		}

		try {
			FBDAO.updateFlightOrderRowDetails(flightOrderRow);
		} catch (Exception e) {
			logger.error("Exception", e);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
		}

		PaymentTransaction paymentTransaction=new PaymentTransaction();
		paymentTransaction.setAmount(AmountRoundingModeUtil.roundingMode(flightPriceResponse.getFinalPriceWithGST().add(processingFees)));// in booking currency
		paymentTransaction.setCurrency(flightPriceResponse.getFlightsearch().getBookedCurrency());
		paymentTransaction.setRefno(orderId);
		paymentTransaction.setIsPaymentSuccess(false);
		paymentTransaction.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		paymentTransaction.setPayment_method(flightCustomerDetails.getPaymode());	
		paymentTransaction.setApi_transaction_id(orderId);
		paymentTransaction.setPayment_status("Pending");
		String newpgid="";

		if(flightCustomerDetails.getPaymode().equals("cash")){
			boolean result = false;
			WalletAmountTranferHistory walletAmountTranferHistory = new WalletAmountTranferHistory();
			/*WalletAmountTranferHistory walletAmountTranferHistory=new WalletAmountTranferHistory();
			try {
				walletAmountTranferHistory.setActionId(orderId);
				walletAmountTranferHistory.setCurrency(flightPriceResponse.getFareFlightSegment().getCurrency());
				result=AWDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()).divide(baseToBookingExchangeRate,2,RoundingMode.UP),walletAmountTranferHistory,"FlightBooking Initiated",flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP));
			} catch (Exception e1) {
				logger.error("Exception", e1);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}*/
			//if (result){
			//deduct the value from the DB
			try {
				//Bluestar booking call....
				if(flightCustomerDetails.getPrice_key().startsWith("PBS")){
					BluestarConfig bluestarConfig = BluestarConfig.GetBluestarConfig(appKeyVo);
					flightBookingResponse= com.tayyarah.flight.util.api.bluestar.BluestarServiceCall.callBookingService(flightBookingResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails.getCountryCode(),FBDAO,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,10,false,bluestarConfig);
				}else if(flightCustomerDetails.getPrice_key().startsWith("PTB")){						//cal TBO booking API
					TboFlightConfig tboFlightConfig = TboFlightConfig.GetTboConfig(appKeyVo);
					//cal TBO API booking API
					TboFlightAirpriceResponse	TboPriceResponse = UapiServiceCall.getTboFlightPriceResponse(flightCustomerDetails.getPrice_key(),TempDAO,false);
					// NONLCC carrier Booking Request						
					flightBookingResponse= TboServiceCall.callHoldBookingService(flightBookingResponse,TboPriceResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails,FBDAO,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,10,false,flightOrderRow,tboFlightConfig);

				}else if(flightCustomerDetails.getPrice_key().startsWith("PTAY")){
					//cal tayyarah booking API
					flightBookingResponse= TayyarahServiceCall.callBookingService(flightBookingResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails,FBDAO,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,10);
				}else if(flightCustomerDetails.getPrice_key().startsWith("PLIN")){
					//cal tayyarah booking API
					flightBookingResponse= LintasServiceCall.callBookingService(flightBookingResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails,FBDAO,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,10);
				}else{
					flightBookingResponse= UapiServiceCall.callBookingService(flightBookingResponse,TravelportConfig.GetTravelportConfig(), orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails.getCountryCode(),FBDAO,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,10);
				}

				// insert notication after booking is successful
				new NotificationUtil().insertNotification(appKeyVo,orderId , "Flight Ticket Hold", InventoryTypeEnum.FLIGHT_ORDER.getId(), true,NFDAO,CDAO); 


			}catch(ClassNotFoundException e){
				logger.error("ClassNotFoundException", e);
				//	DBS.updateWalletBalanceIfFailed(walletAmountTranferHistory.getAmount(),walletAmountTranferHistory.getWalletId(), FBDAO,walletAmountTranferHistory);
				DBS.updatePNR("0", orderId, FBDAO);

				throw new FlightException(ErrorCodeCustomerEnum.ClassNotFoundException,ErrorMessages.BOOKING_FAILED);
			}
			catch(SOAPException e){
				logger.error("SOAPException", e);
				//	DBS.updateWalletBalanceIfFailed(walletAmountTranferHistory.getAmount(),walletAmountTranferHistory.getWalletId(), FBDAO,walletAmountTranferHistory);
				DBS.updatePNR("0", orderId, FBDAO);
				throw new FlightException(ErrorCodeCustomerEnum.SOAPException, ErrorMessages.BOOKING_FAILED);
			}
			catch(JAXBException e){
				logger.error("JAXBException", e);
				//	DBS.updateWalletBalanceIfFailed(walletAmountTranferHistory.getAmount(),walletAmountTranferHistory.getWalletId(), FBDAO,walletAmountTranferHistory);
				DBS.updatePNR("0", orderId, FBDAO);
				throw new FlightException(ErrorCodeCustomerEnum.JAXBException,ErrorMessages.BOOKING_FAILED);
			}catch(Exception e){
				//	DBS.updateWalletBalanceIfFailed(walletAmountTranferHistory.getAmount(),walletAmountTranferHistory.getWalletId(), FBDAO,walletAmountTranferHistory);
				DBS.updatePNR("0", orderId, FBDAO);
				logger.error("Exception", e);
				flightBookingResponse.setBookingComments("API Error");
				flightBookingResponse.setBookingStatus(false);	
				throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
			}

			/*// Update Wallet History
				if(flightBookingResponse.isBookingStatus()){				
				try {
					walletAmountTranferHistory.setActionId(orderId);
					walletAmountTranferHistory.setCurrency(flightPriceResponse.getFareFlightSegment().getCurrency());
					AWDAO.SetBookingStatusInWallet(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()).divide(baseToBookingExchangeRate,2,RoundingMode.UP),walletAmountTranferHistory,"FlightBooking Success",flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP));
				} catch (Exception e1) {
					logger.error("Exception", e1);
					//throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}
				}*/

			paymentTransaction.setIsPaymentSuccess(false);
			paymentTransaction.setTransactionId(flightCustomerDetails.getTransactionkey());
			paymentTransaction.setResponse_message("NA");
			paymentTransaction.setResponseCode("NA");
			paymentTransaction.setPayment_status("Pending");
			paymentTransaction.setAuthorizationCode(flightCustomerDetails.getTransactionkey());
			try {
				newpgid = callPayment(paymentTransaction, FBDAO,0,"");
			} catch (Exception e) {
				logger.error("Exception", e);
				throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
			}
			flightBookingResponse.setPaymentStatus(false);
			///	}
			/*else
			{
				//insufficient wallet balance
				flightBookingResponse=new FlightBookingResponse();
				logger.info("insufficient wallet balance : ");
				flightBookingResponse.setPnr("NA");
				flightBookingResponse.setBokingConditions("Top up ur wallet");
				flightBookingResponse.setBookingComments("Unable to process your booking due to insufficient balance in the account");
				flightBookingResponse.setBookingStatus(false);	
				DBS.updatePNR("#0", orderId, FBDAO);   
			}*/
		}
		else
		{
			storeBookingDetails(orderCustomer,flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails);
			flightBookingResponse=new FlightBookingResponse();
			flightBookingResponse.setPnr("0#");
			flightBookingResponse.setBokingConditions("payment gateway has to be called");
			flightBookingResponse.setBookingComments("payment");
			flightBookingResponse.setBookingStatus(false);		
			DBS.updatePNR("0#", orderId, FBDAO);
			try {
				newpgid= callPayment(paymentTransaction, FBDAO,0,"");
			} catch (Exception e) {
				logger.error("Exception", e);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.DB_ERROR);
			}
		}

		flightBookingResponse.setFareFlightSegment(flightPriceResponse.getFareFlightSegment());
		flightBookingResponse.setPassengerFareBreakUps(flightPriceResponse.getPassengerFareBreakUps());
		flightBookingResponse.setTransactionKey(flightCustomerDetails.getTransactionkey());
		flightBookingResponse.setFlightsearch(flightPriceResponse.getFlightsearch());
		flightBookingResponse.setCountry(flightCustomerDetails.getCountryId());
		flightBookingResponse.setCache(false);
		flightBookingResponse.setConfirmationNumber(orderId);
		flightBookingResponse.setPgID(newpgid);
		flightBookingResponse.setNewpgID(newpgid);
		flightBookingResponse.setPaymentCalledCount(0);
		////////*** newly added on 10-1-2016
		flightBookingResponse.setLastTicketingDate(flightPriceResponse.getFareFlightSegment().getLatestTicketingTime());
		///////////** GST added on 19-1-16

		BigDecimal gstTotal= new BigDecimal(0);
		if(flightPriceResponse.getGSTonMarkup()!=null)
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
		flightBookingResponse.setFinalPriceWithGST(flightPriceResponse.getFinalPriceWithGST().add(processingFees));
		////////////////email//
		TravelportConfig travelportConfig=TravelportConfig.GetTravelportConfig();
		CommonConfig commonConfig=CommonConfig.GetCommonConfig();
		if(commonConfig.isIs_lintas_enabled()&&!travelportConfig.isTest()){
			if(flightCustomerDetails!=null && flightBookingResponse.isBookingStatus()&& orderId.startsWith("FTP")){
				//sendPNRViaMAil(flightCustomerDetails.getUserid(),flightBookingResponse.getPnr(), request, response);
			}
		}
		return flightBookingResponse;
	}
	public static List<FlightOrderRowMarkup> getMarkupDetail(MarkupCommissionDetails markupCommissionDetails,FlightOrderRow flightOrderRow)  {
		//FlightDataBaseServices DBS = new FlightDataBaseServices();

		logger.info("insertMarkupDetail method called : ");
		List<FlightOrderRowMarkup> flightOrderRowMarkups=new ArrayList<FlightOrderRowMarkup>();
		Set<String> compnyIdset=new HashSet<String>();
		compnyIdset=markupCommissionDetails.getCompanyMarkupMap().keySet();
		for(String companyId:compnyIdset){
			FlightOrderRowMarkup flightOrderRowMarkup=new FlightOrderRowMarkup();	
			BigDecimal markupAmount=markupCommissionDetails.getCompanyMarkupMap().get(companyId);
			flightOrderRowMarkup.setCompanyId(companyId);
			flightOrderRowMarkup.setMarkUp(markupAmount);
			flightOrderRowMarkup.setFlightOrderRow(flightOrderRow);
			flightOrderRowMarkups.add(flightOrderRowMarkup);
		}
		return flightOrderRowMarkups;
	}
	public static List<FlightOrderRowCommission> getCommissionDetails(MarkupCommissionDetails markupCommissionDetails,FlightOrderRow flightOrderRow) {

		FlightDataBaseServices DBS = new FlightDataBaseServices();
		List<FlightOrderRowCommission> flightOrderRowCommissions=new ArrayList<FlightOrderRowCommission>();
		logger.info("insertCommission method called normal commission mode: ");
		for(CommissionDetails commissionDetails:markupCommissionDetails.getCommissionDetailslist()){
			FlightOrderRowCommission flightOrderRowCommission=new FlightOrderRowCommission();
			flightOrderRowCommission.setCompanyId(commissionDetails.getCompanyId());
			flightOrderRowCommission.setFlightOrderRow(flightOrderRow);
			flightOrderRowCommission.setCommission(commissionDetails.getCommissionAmount());
			flightOrderRowCommission.setCommissionType(commissionDetails.getCommissionType());
			flightOrderRowCommission.setRateType(commissionDetails.getRateType());

			flightOrderRowCommission.setSheetMode(false);
			flightOrderRowCommission.setIataCommission(new BigDecimal(0));
			flightOrderRowCommission.setPlbCommission(new BigDecimal(0));

			flightOrderRowCommissions.add(flightOrderRowCommission);
		}
		return flightOrderRowCommissions;
	}
	public static List<FlightOrderRowCommission> getCommissionDetails(Map<Integer, AirlineCommision> airlineCommisionMap ,FlightOrderRow flightOrderRow) {
		List<FlightOrderRowCommission> flightOrderRowCommissions=new ArrayList<FlightOrderRowCommission>();
		logger.info("insertCommission method called sheet commission mode: ");

		for(Integer airlineCommissionKey:airlineCommisionMap.keySet()){
			AirlineCommision airlineCommision = airlineCommisionMap.get(airlineCommissionKey);
			if(airlineCommision!=null)
			{
				FlightOrderRowCommission flightOrderRowCommission=new FlightOrderRowCommission();
				flightOrderRowCommission.setCompanyId(airlineCommissionKey.toString());
				flightOrderRowCommission.setFlightOrderRow(flightOrderRow);
				/*BigDecimal totalCommission = (airlineCommision.getIataCommission()==null?new BigDecimal(0):airlineCommision.getIataCommission()).
						add(airlineCommision.getPlbCommission()==null?new BigDecimal(0):airlineCommision.getPlbCommission());
				 */
				flightOrderRowCommission.setCommission(airlineCommision.getCommissionTotal()==null?new BigDecimal(0):airlineCommision.getCommissionTotal());
				flightOrderRowCommission.setIataCommission(airlineCommision.getIataCommission()==null?new BigDecimal(0):airlineCommision.getIataCommission());
				flightOrderRowCommission.setPlbCommission(airlineCommision.getPlbCommission()==null?new BigDecimal(0):airlineCommision.getPlbCommission());

				/*flightOrderRowCommission.setCommission(airlineCommision.getIataCommission());
				if(flightOrderRowCommission.getCommission()!=null)
				{
					if(airlineCommision.getPlbCommission()!=null)
						flightOrderRowCommission.getCommission().add(airlineCommision.getPlbCommission());
				}
				else if(airlineCommision.getPlbCommission()!=null)
				{
					flightOrderRowCommission.setCommission(airlineCommision.getPlbCommission());
				}*/
				flightOrderRowCommission.setSheetMode(true);
				flightOrderRowCommission.setCommissionType(airlineCommision.getCommissionType());
				flightOrderRowCommission.setRateType(airlineCommision.getRateType());
				flightOrderRowCommissions.add(flightOrderRowCommission);
			}
		}
		return flightOrderRowCommissions;

	}
	private int getIntValue(String id) {
		if(id!=null && !id.equalsIgnoreCase(""))
			return Integer.valueOf(id).intValue();
		return 0;
	}
	public static BigDecimal getTotalMarkup(MarkupCommissionDetails markupCommissionDetails)  {
		logger.info("insertMarkupDetail method called : ");
		Set<String> compnyIdset=new HashSet<String>();
		compnyIdset = markupCommissionDetails.getCompanyMarkupMap().keySet();
		BigDecimal totalmarkupAmount=new BigDecimal("0");
		for(String companyId:compnyIdset){
			BigDecimal markupAmount=markupCommissionDetails.getCompanyMarkupMap().get(companyId);
			totalmarkupAmount=totalmarkupAmount.add(markupAmount);
		}
		return totalmarkupAmount;
	}
	private void createFlightOrderCustomerList(List<FlightOrderCustomer> flightOrderCustomers,FlightCustomerDetails flightCustomerDetails,OrderCustomer orderCustomer,FlightOrderRow flightOrderRow)
	{
		logger.info("createFlightOrderCustomerList method called : ");
		for(PassengerDetails passengerDetails:flightCustomerDetails.getPassengerdetailsList()){
			FlightOrderCustomer flightOrderCustomer=new FlightOrderCustomer();
			flightOrderCustomer.setBirthday(passengerDetails.getBirthday());
			flightOrderCustomer.setFirstName(passengerDetails.getFirstName());
			flightOrderCustomer.setFlightCustomer(orderCustomer);
			flightOrderCustomer.setGender(passengerDetails.getTitle());
			flightOrderCustomer.setMiddleName(passengerDetails.getMiddleName());
			flightOrderCustomer.setLastName(passengerDetails.getLastName());
			flightOrderCustomer.setPassengerTypeCode(passengerDetails.getPassengerTypeCode());
			flightOrderCustomer.setPassportExpiryDate(passengerDetails.getPassportExpiryDate());
			flightOrderCustomer.setTitle(passengerDetails.getTitle());
			flightOrderCustomer.setFlightOrderRow(flightOrderRow);//DO i need to ADD this Yogesh??
			flightOrderCustomer.setVersion(VERSION);
			flightOrderCustomer.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			flightOrderCustomer.setPassportNo(passengerDetails.getPassportNo());
			flightOrderCustomer.setPassportIssuingCountry(passengerDetails.getPassportIssuingCountry());
			flightOrderCustomer.setNationality(passengerDetails.getNationalityCountry());			
			flightOrderCustomers.add(flightOrderCustomer); 
		}
	}

	private void createFlightOrderCustomerSSRDetails(FlightCustomerDetails flightCustomerDetails,List<FlightOrderCustomerSSR> FlightOrderCustomerSSRlist,FlightPriceResponse flightPriceResponse,OrderCustomer orderCustomer,FlightOrderRow flightOrderRow,boolean IsSpecial)
	{
		if(flightPriceResponse.getSpecialServiceRequest() !=null){
			for (PassengerDetails PassengerDetail : flightCustomerDetails.getPassengerdetailsList()) {	
				FlightOrderCustomerSSR flightOrderCustomerSSR = new FlightOrderCustomerSSR();			

				if(!flightPriceResponse.getFlightsearch().isSpecialSearch()){
					if(PassengerDetail.getMealcode()!=null && PassengerDetail.getMealcode()!=""){
						if(flightPriceResponse.getSpecialServiceRequest().isIsLCC()){
							for (List<MealDynamic> mealtype : flightPriceResponse.getSpecialServiceRequest().getMealDynamic()) {
								for (MealDynamic mealDynamicobj : mealtype) {
									if(PassengerDetail.getMealcode().equalsIgnoreCase(mealDynamicobj.getCode())){
										if(flightPriceResponse.getFlightsearch().getOrigin().equalsIgnoreCase(mealDynamicobj.getOrigin())){
											flightOrderCustomerSSR.setMealType(PassengerDetail.getMealcode());
											flightOrderCustomerSSR.setMealname(mealDynamicobj.getAirlineDescription());
											flightOrderCustomerSSR.setMealPrice(String.valueOf(mealDynamicobj.getPrice()));
										}
									}
								}
							}
						}
						if(!flightPriceResponse.getSpecialServiceRequest().isIsLCC()){
							for (Meal mealtype : flightPriceResponse.getSpecialServiceRequest().getMeal()) {
								if(PassengerDetail.getMealcode().equalsIgnoreCase(mealtype.getCode())){
									flightOrderCustomerSSR.setMealType(PassengerDetail.getMealcode());
									flightOrderCustomerSSR.setMealname(mealtype.getDescription());				
								}
							}
						}
					}
					if(PassengerDetail.getBaggagecode()!=null && PassengerDetail.getBaggagecode()!=""){
						for (List<Baggage> baggagetype : flightPriceResponse.getSpecialServiceRequest().getBaggage()) {
							for (Baggage baggageitem : baggagetype) {
								if(PassengerDetail.getBaggagecode().equalsIgnoreCase(baggageitem.getCode())){						
									flightOrderCustomerSSR.setBaggageType(PassengerDetail.getBaggagecode());
									flightOrderCustomerSSR.setBaggageweight(String.valueOf(baggageitem.getWeight()));
									flightOrderCustomerSSR.setBaggagePrice(String.valueOf(baggageitem.getPrice()));
								}
							} 

						}

					}
					if(PassengerDetail.getSeatcode()!=null && PassengerDetail.getSeatcode()!=""){
						for (Seat seattype : flightPriceResponse.getSpecialServiceRequest().getSeatPreference()) {
							if(PassengerDetail.getSeatcode().equalsIgnoreCase(seattype.getCode())){						
								flightOrderCustomerSSR.setSeatType(seattype.getDescription());
							}
						}

					}
				}

				if(flightPriceResponse.getFlightsearch().isSpecialSearch()){
					if(!IsSpecial &&  flightPriceResponse.getSpecialServiceRequest()!=null){
						if(PassengerDetail.getMealcode()!=null && PassengerDetail.getMealcode()!=""){
							if(flightPriceResponse.getSpecialServiceRequest().isIsLCC()){
								for (List<MealDynamic> mealtype : flightPriceResponse.getSpecialServiceRequest().getMealDynamic()) {
									for (MealDynamic mealDynamicobj : mealtype) {
										if(PassengerDetail.getMealcode().equalsIgnoreCase(mealDynamicobj.getCode())){
											if(flightPriceResponse.getFlightsearch().getOrigin().equalsIgnoreCase(mealDynamicobj.getOrigin())){
												flightOrderCustomerSSR.setMealType(PassengerDetail.getMealcode());
												flightOrderCustomerSSR.setMealname(mealDynamicobj.getAirlineDescription());
												flightOrderCustomerSSR.setMealPrice(String.valueOf(mealDynamicobj.getPrice()));
											}
										}
									}
								}
							}
							if(!flightPriceResponse.getSpecialServiceRequest().isIsLCC()){
								for (Meal mealtype : flightPriceResponse.getSpecialServiceRequest().getMeal()) {
									if(PassengerDetail.getMealcode().equalsIgnoreCase(mealtype.getCode())){
										flightOrderCustomerSSR.setMealType(PassengerDetail.getMealcode());
										flightOrderCustomerSSR.setMealname(mealtype.getDescription());				
									}
								}
							}

						}
						if(PassengerDetail.getBaggagecode()!=null && PassengerDetail.getBaggagecode()!=""){
							for (List<Baggage> baggagetype : flightPriceResponse.getSpecialServiceRequest().getBaggage()) {
								for (Baggage baggageitem : baggagetype) {
									if(PassengerDetail.getBaggagecode().equalsIgnoreCase(baggageitem.getCode())){						
										flightOrderCustomerSSR.setBaggageType(PassengerDetail.getBaggagecode());
										flightOrderCustomerSSR.setBaggageweight(String.valueOf(baggageitem.getWeight()));
										flightOrderCustomerSSR.setBaggagePrice(String.valueOf(baggageitem.getPrice()));
									}
								} 

							}

						}
						if(PassengerDetail.getSeatcode()!=null && PassengerDetail.getSeatcode()!=""){
							for (Seat seattype : flightPriceResponse.getSpecialServiceRequest().getSeatPreference()) {
								if(PassengerDetail.getSeatcode().equalsIgnoreCase(seattype.getCode())){						
									flightOrderCustomerSSR.setSeatType(seattype.getDescription());
								}
							}

						}
					}
					if(IsSpecial && flightPriceResponse.getReturnspecialServiceRequest()!=null){
						if(PassengerDetail.getReturnmealcode()!=null && PassengerDetail.getReturnmealcode()!=""){
							if(flightPriceResponse.getReturnspecialServiceRequest().isIsLCC()){
								for (List<MealDynamic> mealtype : flightPriceResponse.getReturnspecialServiceRequest().getMealDynamic()) {
									for (MealDynamic mealDynamicobj : mealtype) {
										if(PassengerDetail.getReturnmealcode().equalsIgnoreCase(mealDynamicobj.getCode())){
											if(flightPriceResponse.getFlightsearch().getOrigin().equalsIgnoreCase(mealDynamicobj.getDestination())){
												flightOrderCustomerSSR.setMealType(PassengerDetail.getReturnmealcode());
												flightOrderCustomerSSR.setMealname(mealDynamicobj.getAirlineDescription());
												flightOrderCustomerSSR.setMealPrice(String.valueOf(mealDynamicobj.getPrice()));
											}
										}
									}
								}
							}
							if(!flightPriceResponse.getReturnspecialServiceRequest().isIsLCC()){
								for (Meal mealtype : flightPriceResponse.getReturnspecialServiceRequest().getMeal()) {
									if(PassengerDetail.getReturnmealcode().equalsIgnoreCase(mealtype.getCode())){
										flightOrderCustomerSSR.setMealType(PassengerDetail.getReturnmealcode());
										flightOrderCustomerSSR.setMealname(mealtype.getDescription());				
									}
								}
							}
						}
						if(PassengerDetail.getReturnbaggagecode()!=null && PassengerDetail.getReturnbaggagecode()!=""){
							for (List<Baggage> baggagetype : flightPriceResponse.getReturnspecialServiceRequest().getBaggage()) {
								for (Baggage baggageitem : baggagetype) {
									if(PassengerDetail.getReturnbaggagecode().equalsIgnoreCase(baggageitem.getCode())){						
										flightOrderCustomerSSR.setBaggageType(PassengerDetail.getReturnbaggagecode());
										flightOrderCustomerSSR.setBaggageweight(String.valueOf(baggageitem.getWeight()));
										flightOrderCustomerSSR.setBaggagePrice(String.valueOf(baggageitem.getPrice()));
									}
								} 

							}

						}
						if(PassengerDetail.getReturnseatcode()!=null && PassengerDetail.getReturnseatcode()!=""){
							for (Seat seattype : flightPriceResponse.getReturnspecialServiceRequest().getSeatPreference()) {
								if(PassengerDetail.getReturnseatcode().equalsIgnoreCase(seattype.getCode())){						
									flightOrderCustomerSSR.setSeatType(seattype.getDescription());
								}
							}

						}
					}
				}

				if(!flightPriceResponse.getFlightsearch().isSpecialSearch() && flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("R") && flightPriceResponse.getSpecialServiceRequest() !=null  && flightPriceResponse.getReturnspecialServiceRequest() !=null){


					if(PassengerDetail.getMealcode()!=null && PassengerDetail.getMealcode()!=""){
						if(flightPriceResponse.getSpecialServiceRequest().isIsLCC()){
							for (List<MealDynamic> mealtype : flightPriceResponse.getSpecialServiceRequest().getMealDynamic()) {
								for (MealDynamic mealDynamicobj : mealtype) {
									if(PassengerDetail.getMealcode().equalsIgnoreCase(mealDynamicobj.getCode())){
										if(flightPriceResponse.getFlightsearch().getOrigin().equalsIgnoreCase(mealDynamicobj.getOrigin())){
											flightOrderCustomerSSR.setMealType(PassengerDetail.getMealcode());
											flightOrderCustomerSSR.setMealname(mealDynamicobj.getAirlineDescription());
											flightOrderCustomerSSR.setMealPrice(String.valueOf(mealDynamicobj.getPrice()));
										}
									}
								}
							}
						}
						if(!flightPriceResponse.getSpecialServiceRequest().isIsLCC()){
							for (Meal mealtype : flightPriceResponse.getSpecialServiceRequest().getMeal()) {
								if(PassengerDetail.getMealcode().equalsIgnoreCase(mealtype.getCode())){
									flightOrderCustomerSSR.setMealType(PassengerDetail.getMealcode());
									flightOrderCustomerSSR.setMealname(mealtype.getDescription());				
								}
							}
						}
					}
					if(PassengerDetail.getBaggagecode()!=null && PassengerDetail.getBaggagecode()!=""){
						for (List<Baggage> baggagetype : flightPriceResponse.getSpecialServiceRequest().getBaggage()) {
							for (Baggage baggageitem : baggagetype) {
								if(PassengerDetail.getBaggagecode().equalsIgnoreCase(baggageitem.getCode())){						
									flightOrderCustomerSSR.setBaggageType(PassengerDetail.getBaggagecode());
									flightOrderCustomerSSR.setBaggageweight(String.valueOf(baggageitem.getWeight()));
									flightOrderCustomerSSR.setBaggagePrice(String.valueOf(baggageitem.getPrice()));
								}
							} 

						}

					}
					if(PassengerDetail.getSeatcode()!=null && PassengerDetail.getSeatcode()!=""){
						for (Seat seattype : flightPriceResponse.getSpecialServiceRequest().getSeatPreference()) {
							if(PassengerDetail.getSeatcode().equalsIgnoreCase(seattype.getCode())){						
								flightOrderCustomerSSR.setSeatType(seattype.getDescription());
							}
						}

					}

					if(PassengerDetail.getReturnmealcode()!=null && PassengerDetail.getReturnmealcode()!=""){
						if(flightPriceResponse.getReturnspecialServiceRequest().isIsLCC()){
							for (List<MealDynamic> mealtype : flightPriceResponse.getReturnspecialServiceRequest().getMealDynamic()) {
								for (MealDynamic mealDynamicobj : mealtype) {
									if(PassengerDetail.getReturnmealcode().equalsIgnoreCase(mealDynamicobj.getCode())){
										if(flightPriceResponse.getFlightsearch().getOrigin().equalsIgnoreCase(mealDynamicobj.getDestination())){
											flightOrderCustomerSSR.setMealType(PassengerDetail.getReturnmealcode());
											flightOrderCustomerSSR.setMealname(mealDynamicobj.getAirlineDescription());
											flightOrderCustomerSSR.setMealPrice(String.valueOf(mealDynamicobj.getPrice()));
										}
									}
								}
							}
						}
						if(!flightPriceResponse.getReturnspecialServiceRequest().isIsLCC()){
							for (Meal mealtype : flightPriceResponse.getReturnspecialServiceRequest().getMeal()) {
								if(PassengerDetail.getReturnmealcode().equalsIgnoreCase(mealtype.getCode())){
									flightOrderCustomerSSR.setMealType(PassengerDetail.getReturnmealcode());
									flightOrderCustomerSSR.setMealname(mealtype.getDescription());				
								}
							}
						}
					}
					if(PassengerDetail.getReturnbaggagecode()!=null && PassengerDetail.getReturnbaggagecode()!=""){
						for (List<Baggage> baggagetype : flightPriceResponse.getReturnspecialServiceRequest().getBaggage()) {
							for (Baggage baggageitem : baggagetype) {
								if(PassengerDetail.getReturnbaggagecode().equalsIgnoreCase(baggageitem.getCode())){						
									flightOrderCustomerSSR.setBaggageType(PassengerDetail.getReturnbaggagecode());
									flightOrderCustomerSSR.setBaggageweight(String.valueOf(baggageitem.getWeight()));
									flightOrderCustomerSSR.setBaggagePrice(String.valueOf(baggageitem.getPrice()));
								}
							} 

						}

					}
					if(PassengerDetail.getReturnseatcode()!=null && PassengerDetail.getReturnseatcode()!=""){
						for (Seat seattype : flightPriceResponse.getReturnspecialServiceRequest().getSeatPreference()) {
							if(PassengerDetail.getReturnseatcode().equalsIgnoreCase(seattype.getCode())){						
								flightOrderCustomerSSR.setSeatType(seattype.getDescription());
							}
						}

					}

				}

				if(!flightPriceResponse.getFlightsearch().isSpecialSearch() && flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("R") && flightPriceResponse.getSpecialServiceRequest()==null){
					if(flightPriceResponse.getSpecialServiceRequest()!=null && flightPriceResponse.getSpecialServiceRequest().isIsLCC()){
						if(PassengerDetail.getMealcode()!=null && PassengerDetail.getMealcode()!=""){
							for (MealDynamic mealDynamicobj : flightPriceResponse.getSpecialServiceRequest().getMealDynamic().get(0)) {	
								if(PassengerDetail.getMealcode().equalsIgnoreCase(mealDynamicobj.getCode())){
									flightOrderCustomerSSR.setMealType(PassengerDetail.getMealcode());
									flightOrderCustomerSSR.setMealname(mealDynamicobj.getAirlineDescription());
									flightOrderCustomerSSR.setMealPrice(String.valueOf(mealDynamicobj.getPrice()));
								}
							}
						}
						if(PassengerDetail.getReturnmealcode()!=null && PassengerDetail.getReturnmealcode()!=""){
							for (MealDynamic mealDynamicobj : flightPriceResponse.getSpecialServiceRequest().getMealDynamic().get(1)) {	
								if(PassengerDetail.getReturnmealcode().equalsIgnoreCase(mealDynamicobj.getCode())){
									flightOrderCustomerSSR.setReturnmealType(PassengerDetail.getReturnmealcode());
									flightOrderCustomerSSR.setReturnmealname(mealDynamicobj.getAirlineDescription());
									flightOrderCustomerSSR.setReturnmealPrice(String.valueOf(mealDynamicobj.getPrice()));
								}
							}
						}
						if(PassengerDetail.getBaggagecode()!=null && PassengerDetail.getBaggagecode()!=""){
							for (Baggage baggageitem : flightPriceResponse.getSpecialServiceRequest().getBaggage().get(0)) {
								if(PassengerDetail.getBaggagecode().equalsIgnoreCase(baggageitem.getCode()) ){						
									flightOrderCustomerSSR.setBaggageType(PassengerDetail.getReturnbaggagecode());
									flightOrderCustomerSSR.setBaggageweight(String.valueOf(baggageitem.getWeight()));
									flightOrderCustomerSSR.setBaggagePrice(String.valueOf(baggageitem.getPrice()));
								}
							}
						}
						if(PassengerDetail.getReturnbaggagecode()!=null && PassengerDetail.getReturnbaggagecode()!=""){
							for (Baggage baggageitem : flightPriceResponse.getSpecialServiceRequest().getBaggage().get(1)) {
								if(PassengerDetail.getReturnbaggagecode().equalsIgnoreCase(baggageitem.getCode()) ){						
									flightOrderCustomerSSR.setReturnbaggageType(PassengerDetail.getReturnbaggagecode());
									flightOrderCustomerSSR.setReturnbaggageweight(String.valueOf(baggageitem.getWeight()));
									flightOrderCustomerSSR.setReturnbaggagePrice(String.valueOf(baggageitem.getPrice()));
								}
							}
						}
					}
				}


				flightOrderCustomerSSR.setFlightCustomer(orderCustomer);
				flightOrderCustomerSSR.setFlightOrderRow(flightOrderRow);
				FlightOrderCustomerSSRlist.add(flightOrderCustomerSSR);
			}		
		}
	}

	private void createFlightOrderTripDetailList(List<FlightOrderTripDetail> flightOrderTripDetails,FlightPriceResponse flightPriceResponse,OrderCustomer orderCustomer,FlightOrderRow flightOrderRow,int count)
	{
		logger.info("createFlightOrderTripDetailList method called : ");
		List<Segments> segmentsList=flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments();
		if(count==1){
			segmentsList=flightPriceResponse.getSpecialFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments();
		}
		for(Segments segments:segmentsList)
		{
			FlightOrderTripDetail flightOrderTripDetail=new FlightOrderTripDetail();	
			flightOrderTripDetail.setArrDate(segments.getArrival());

			String arvl_date_time=segments.getArrival();			
			String ar_date = arvl_date_time.substring(0,10);
			String ar_time = arvl_date_time.substring(11,19);
			String ar_timestamp = ar_date+" "+ar_time;

			String dep_date_time=segments.getDepart();			
			String dep_date = dep_date_time.substring(0,10);
			String dep_time = dep_date_time.substring(11,19);
			String dep_timestamp = dep_date+" "+dep_time;

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfT = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat sdfTS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date arvl_date=null;
			Date arvl_time=null;
			Date arvl_timestamp=null;

			Date dept_date=null;
			Date dept_time=null;
			Date dept_timestamp=null;
			try {
				arvl_date = sdf.parse(ar_date);
				arvl_time = sdfT.parse(ar_time);
				arvl_timestamp = sdfTS.parse(ar_timestamp);
				dept_date = sdf.parse(dep_date);
				dept_time = sdfT.parse(dep_time);
				dept_timestamp = sdfTS.parse(dep_timestamp);

			} catch (ParseException e) {
				logger.error("ParseException", e);
				throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
			}

			//////////
			flightOrderTripDetail.setArrivalDate(arvl_date);
			flightOrderTripDetail.setArrivalTime(arvl_time);
			flightOrderTripDetail.setArrivalTimestamp(arvl_timestamp);
			flightOrderTripDetail.setDepDate(segments.getDepart());
			flightOrderTripDetail.setDepartureDate(dept_date);
			flightOrderTripDetail.setDepartureTime(dept_time);
			flightOrderTripDetail.setDepartureTimestamp(dept_timestamp);
			flightOrderTripDetail.setDestinationCode(segments.getDest());
			flightOrderTripDetail.setDestinationName(segments.getDestName());
			flightOrderTripDetail.setOriginCode(segments.getOri());
			flightOrderTripDetail.setOriginName(segments.getOriName());
			flightOrderTripDetail.setFlightDuration(segments.getDuration());

			Flight flight=segments.getFlight();
			flightOrderTripDetail.setFlightNumber(flight.getNumber()); 
			flightOrderTripDetail.setOriginTerminal(flight.getOriTerminal());
			flightOrderTripDetail.setDestinationTerminal(flight.getDestTerminal());

			Cabin cabin=segments.getCabin();
			flightOrderTripDetail.setClassOfService(cabin.getName());

			Carrier carrier=segments.getCarrier();   
			flightOrderTripDetail.setOperatedByName(carrier.getName());
			flightOrderRow.setAirline(carrier.getName());
			flightOrderTripDetail.setOperatedByCode(carrier.getCode());
			flightOrderTripDetail.setTrips(Integer.parseInt(flightPriceResponse.getFlightsearch().getTrips()));///??/ wt shoud b for ROundtrip special?

			flightOrderTripDetail.setTripType(flightPriceResponse.getFlightsearch().getTripType());///??/ wt shoud b for ROundtrip special?
			flightOrderTripDetail.setVersion(VERSION);
			flightOrderTripDetail.setFlightOrderRow(flightOrderRow);//DO i need to ADD this Yogesh??
			flightOrderTripDetail.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			flightOrderTripDetails.add(flightOrderTripDetail);
		}
	}
	private void createFlightOrderCustomerPriceBreakupList( List<FlightOrderCustomerPriceBreakup> flightOrderCustomerPriceBeakups,FlightPriceResponse flightPriceResponse,OrderCustomer orderCustomer,FlightOrderRow flightOrderRow,int count){
		logger.info("createFlightOrderCustomerPriceBreakupList method called : ");
		List<PassengerFareBreakUp> passengerFareBreakUps=flightPriceResponse.getPassengerFareBreakUps();
		if(count==1)
		{
			passengerFareBreakUps=flightPriceResponse.getSpecialPassengerFareBreakUps();
		}
		for(PassengerFareBreakUp passengerFareBreakUp:passengerFareBreakUps)
		{
			FlightOrderCustomerPriceBreakup flightOrderCustomerPriceBreakup=new FlightOrderCustomerPriceBreakup();
			flightOrderCustomerPriceBreakup.setFlightCustomer(orderCustomer);
			flightOrderCustomerPriceBreakup.setBaseFare(new BigDecimal(passengerFareBreakUp.getApi_basePriceWithoutMarkup()));		
			flightOrderCustomerPriceBreakup.setTax(new BigDecimal(passengerFareBreakUp.getApi_taxesWithoutMarkup()));
			flightOrderCustomerPriceBreakup.setTotal(new BigDecimal(passengerFareBreakUp.getApi_totalPriceWithoutMarkup()));
			flightOrderCustomerPriceBreakup.setDescription(passengerFareBreakUp.getType());
			flightOrderCustomerPriceBreakup.setFlightOrderRow(flightOrderRow);//DO i need to ADD this Yogesh??
			flightOrderCustomerPriceBreakup.setVersion(VERSION);
			flightOrderCustomerPriceBreakup.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));

			BigDecimal markup= new BigDecimal(passengerFareBreakUp.getBasePrice()).divide(flightPriceResponse.getFlightsearch().getBaseToBookingExchangeRate()).subtract(new BigDecimal(passengerFareBreakUp.getBasePriceWithoutMarkup()).divide(flightPriceResponse.getFlightsearch().getBaseToBookingExchangeRate()));
			flightOrderCustomerPriceBreakup.setMarkup(markup.toString());
			if(passengerFareBreakUp.getTaxDescription()!=null)//flightOrderRow.getOrderId().startsWith("PTP"))
			{
				String taxinAPI=passengerFareBreakUp.getTaxDescription();
				String temp=taxinAPI;
				StringBuilder sb=new StringBuilder();
				while(temp.length()>2){
					String fullValue=temp.substring(0,temp.indexOf(";"));
					String tax=fullValue.substring(fullValue.indexOf(":")+1);
					String taxCode=fullValue.substring(0,fullValue.indexOf(":"));
					BigDecimal taxINAPi=new BigDecimal(tax).divide(flightPriceResponse.getFlightsearch().getBaseToBookingExchangeRate()).divide(flightPriceResponse.getFareFlightSegment().getApiToBaseExchangeRate());
					if(count==1){
						taxINAPi=new BigDecimal(tax).divide(flightPriceResponse.getFlightsearch().getBaseToBookingExchangeRate()).divide(flightPriceResponse.getSpecialFareFlightSegment().getApiToBaseExchangeRate());
					}
					sb.append(taxCode+":"+taxINAPi+";");
					temp=temp.substring(fullValue.length()+1 );
				}
				flightOrderCustomerPriceBreakup.setTax_description(sb.toString());
			}
			flightOrderCustomerPriceBeakups.add(flightOrderCustomerPriceBreakup);
		}
	}
	public static String callPayment(PaymentTransaction paymentTransaction, FlightBookingDao FBDAO,int count,String OLDPg_ID) throws Exception {
		FlightDataBaseServices DBS = new FlightDataBaseServices();
		String Pg_ID="";
		logger.info("callPayment method called : ");
		try{
			if(count==0){
				Pg_ID="PGF"+new GenerateID().toString();
			}else{
				Pg_ID=OLDPg_ID;
			}
			paymentTransaction.setRefno(Pg_ID);
			DBS.insertPaymentTransaction(paymentTransaction, FBDAO);
		}catch(Exception e){
			Pg_ID="";
			if(count==0){
				Pg_ID="";
			}else{
				Pg_ID=OLDPg_ID;
			}
			logger.error("Exception", e);
		}finally{
			return Pg_ID;
		}
	}
	private void storeBookingDetails(OrderCustomer orderCustomer, FlightPriceResponse  flightPriceResponse, List<FlightOrderCustomer> flightOrderCustomers, String orderId,FlightCustomerDetails flightCustomerDetails)
	{
		logger.info("storeBookingDetails method called : ");
		BookingDetails bookingDetails=new BookingDetails();
		bookingDetails.setCountrycode(flightCustomerDetails.getCountryCode());
		bookingDetails.setFlightOrderCustomers(flightOrderCustomers);
		bookingDetails.setFlightPriceResponse(flightPriceResponse);
		bookingDetails.setOrderCustomer(orderCustomer);
		bookingDetails.setTransactionkey(flightCustomerDetails.getTransactionkey());
		bookingDetails.setFlightCustomerDetails(flightCustomerDetails);

		byte[] booking = null;
		try {
			booking =  FlightDataBaseServices.convertObjectToByteArray(bookingDetails);
		} catch (IOException e1) {
			logger.error("IOException", e1);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
		}
		FlightBookingDetailsTemp bookingDetailsToDb=new FlightBookingDetailsTemp();
		bookingDetailsToDb.setBooking_details(booking);
		bookingDetailsToDb.setOrder_id(orderId);
		bookingDetailsToDb.setDatetime(new Date());

		try {
			FBDAO.insertBookingDetails(bookingDetailsToDb);
		} catch (Exception e) {
			logger.error("Exception", e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
		}
	}
}
