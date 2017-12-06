package com.tayyarah.flight.controller;




import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.admin.analytics.lookbook.dao.LookBookDao;
import com.tayyarah.admin.analytics.lookbook.entity.FetchIpAddress;
import com.tayyarah.admin.analytics.lookbook.entity.FlightBook;
import com.tayyarah.admin.analytics.lookbook.entity.FlightLookBook;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPHistory;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPStatus;
import com.tayyarah.api.flight.tbo.model.Baggage;
import com.tayyarah.api.flight.tbo.model.Meal;
import com.tayyarah.api.flight.tbo.model.MealDynamic;
import com.tayyarah.api.flight.tbo.model.Seat;
import com.tayyarah.api.flight.tbo.model.TboFlightAirpriceResponse;
import com.tayyarah.api.orderrow.rm.structure.FlightOrderRowRmConfigStruct;
import com.tayyarah.apiconfig.model.BluestarConfig;
import com.tayyarah.apiconfig.model.TboFlightConfig;
import com.tayyarah.apiconfig.model.TravelportConfig;
import com.tayyarah.common.dao.RmConfigDetailDAO;
import com.tayyarah.common.entity.ApiProviderPaymentTransaction;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.entity.RmConfigModel;
import com.tayyarah.common.exception.BaseException;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.exception.RestError;
import com.tayyarah.common.gstconfig.entity.FlightDomesticGstTaxConfig;
import com.tayyarah.common.gstconfig.entity.FlightInternationalGstTaxConfig;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.model.CommissionDetails;
import com.tayyarah.common.notification.NotificationUtil;
import com.tayyarah.common.notification.dao.NotificationDao;
import com.tayyarah.common.servicetaxconfig.entity.FlightDomesticServiceTaxConfig;
import com.tayyarah.common.servicetaxconfig.entity.FlightInternationalServiceTaxConfig;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.common.util.CutandPayModel;
import com.tayyarah.common.util.GenerateID;
import com.tayyarah.common.util.GetFrontUserDetail;
import com.tayyarah.common.util.IndianUnionTerritories;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.common.util.encryptions;
import com.tayyarah.common.util.enums.CommonBookingStatusEnum;
import com.tayyarah.common.util.enums.InventoryTypeEnum;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.flight.commission.model.AirlineCommision;
import com.tayyarah.flight.commission.model.AirlineLiteral;
import com.tayyarah.flight.commission.remarks.util.Constants;
import com.tayyarah.flight.dao.AirportDAO;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.entity.FlightAirPriceDetailsTemp;
import com.tayyarah.flight.entity.FlightBookingDetailsTemp;
import com.tayyarah.flight.entity.FlightFareAlertConnectingFlight;
import com.tayyarah.flight.entity.FlightFareAlertDetail;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.entity.FlightOrderCustomerPriceBreakup;
import com.tayyarah.flight.entity.FlightOrderCustomerSSR;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.entity.FlightOrderRowCommission;
import com.tayyarah.flight.entity.FlightOrderRowGstTax;
import com.tayyarah.flight.entity.FlightOrderRowMarkup;
import com.tayyarah.flight.entity.FlightOrderRowServiceTax;
import com.tayyarah.flight.entity.FlightOrderTripDetail;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.BookingDetails;
import com.tayyarah.flight.model.Cabin;
import com.tayyarah.flight.model.Carrier;
import com.tayyarah.flight.model.FareFlightSegment;
import com.tayyarah.flight.model.Flight;
import com.tayyarah.flight.model.FlightBookingResponse;
import com.tayyarah.flight.model.FlightCustomerDetails;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.FlightSegments;
import com.tayyarah.flight.model.FlightSegmentsGroup;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.MarkupCommissionDetails;
import com.tayyarah.flight.model.PassengerDetails;
import com.tayyarah.flight.model.PassengerFareBreakUp;
import com.tayyarah.flight.model.Segments;
import com.tayyarah.flight.quotation.dao.FlightTravelRequestDao;
import com.tayyarah.flight.service.db.AirlineService;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.flight.util.FlightWebServiceEndPointValidator;
import com.tayyarah.flight.util.api.lintas.LintasServiceCall;
import com.tayyarah.flight.util.api.tayyarah.TayyarahServiceCall;
import com.tayyarah.flight.util.api.tbo.TboCommonUtil;
import com.tayyarah.flight.util.api.tbo.TboServiceCall;
import com.tayyarah.flight.util.api.travelport.UapiServiceCall;
import com.tayyarah.flight.util.api.travelport.UmarkUpServiceCall;
import com.tayyarah.insurance.controller.CreatePolicyController;
import com.tayyarah.insurance.dao.InsuranceCommonDao;
import com.tayyarah.insurance.entity.TrawellTagPremiumChart;
import com.tayyarah.insurance.model.CreateInsurancePolicyRequest;
import com.tayyarah.insurance.model.PolicyResponseData;
import com.tayyarah.insurance.model.Status;
import com.tayyarah.insurance.model.TravellerDetails;
import com.tayyarah.services.CommissionService;
import com.tayyarah.services.EmailService;
import com.tayyarah.user.dao.FrontUserDao;
import com.tayyarah.user.dao.UserWalletDAO;
import com.tayyarah.user.entity.FrontUserDetail;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.entity.WalletAmountTranferHistory;

@RestController
@RequestMapping("/booking")
public class FlightBookingController {

	@Autowired
	CompanyDao companyDao;
	@Autowired
	CompanyConfigDAO companyConfigDAO;
	@Autowired
	FlightTempAirSegmentDAO flightTempAirSegmentDAO;
	@Autowired
	FlightBookingDao flightBookingDao;
	@Autowired
	EmailDao emaildao;
	@Autowired
	private EmailService emailService;
	@Autowired
	private CommissionService commissionService;
	@Autowired
	ServletContext servletContext;
	@Autowired
	ApplicationContext applicationContext;
	@Autowired
	UserWalletDAO userWalletDAO;
	@Autowired
	AirlineService airlineService;
	@Autowired
	AirportDAO airportDAO;
	@Autowired
	NotificationDao notificationDao;
	@Autowired
	FlightTravelRequestDao flightTravelRequestDao;
	@SuppressWarnings("rawtypes")
	@Autowired
	LookBookDao lookBookDao;
	@Autowired
	FrontUserDao frontUserDao;
	@Autowired
	CreatePolicyController createPolicyController;
	@Autowired
	InsuranceCommonDao insuranceCommonDao;
	@Autowired
	RmConfigDetailDAO rmConfigDetailDAO;

	private  FlightWebServiceEndPointValidator validator = new FlightWebServiceEndPointValidator();
	static final Logger logger = Logger.getLogger(FlightBookingController.class);
	public static final int VERSION = 1;
	private FlightDataBaseServices DBS = new FlightDataBaseServices();

	@RequestMapping(value = "/details", method = RequestMethod.POST,headers = {"Accept=application/json"})
	public @ResponseBody
	FlightBookingResponse AddBookingDetails(@RequestBody FlightCustomerDetails flightCustomerDetails,HttpServletResponse response,HttpServletRequest request) {

		FlightBookingResponse flightBookingResponse = null;
		checkGetEmulatedUserById(flightCustomerDetails);
		ResponseHeader.setPostResponse(response);//Setting response header
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, flightCustomerDetails.getApp_key());
		flightCustomerDetails.setApp_key(AppControllerUtil.getDecryptedAppKey(companyDao, flightCustomerDetails.getApp_key()));
		AppControllerUtil.validateTransactionKey(flightBookingDao,flightCustomerDetails.getTransactionkey());
		validator.bookingValidator(flightCustomerDetails.getPrice_key(),flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getUsername(),checkGetEmulatedUserById(flightCustomerDetails),flightCustomerDetails.getPaymode());
		//AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, flightCustomerDetails.getApp_key());
		
		if(appKeyVo==null)
		{
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
		}

		boolean isLowfare = true;
		boolean isLowfareReturn = true;
		String lowFareFlightIndex1 = "";
		String lowFareFlightIndex2 = "";
		String reasonToSelect = "";
		String lowFareFlightIndexReturn1 = "";
		String lowFareFlightIndexReturn2 = "";
		String reasonToSelectReturn = "";
		FlightAirPriceDetailsTemp flightAirPriceDetailsTemp = null;
		FlightPriceResponse flightPriceResponse = null;
		try {
			flightAirPriceDetailsTemp = UapiServiceCall.getFlightAirPriceDetailsTemp(flightCustomerDetails.getPrice_key(),flightTempAirSegmentDAO);
			if(flightAirPriceDetailsTemp != null){
				byte[] fpr = flightAirPriceDetailsTemp.getFlightPriceResponse();
				flightPriceResponse = (FlightPriceResponse)FlightDataBaseServices.convertByteArrayToObject(fpr);

				if(flightAirPriceDetailsTemp.getLowFareFlightIndex1()!=null){
					lowFareFlightIndex1 = flightAirPriceDetailsTemp.getLowFareFlightIndex1();
					isLowfare = false;
				}
				if(flightAirPriceDetailsTemp.getLowFareFlightIndex2()!=null){
					lowFareFlightIndex2 = flightAirPriceDetailsTemp.getLowFareFlightIndex2();

				}
				if(flightAirPriceDetailsTemp.getReasonToSelect()!=null)
					reasonToSelect = flightAirPriceDetailsTemp.getReasonToSelect();

				if(flightAirPriceDetailsTemp.getLowFareFlightIndexReturn1()!=null){
					lowFareFlightIndexReturn1 = flightAirPriceDetailsTemp.getLowFareFlightIndexReturn1();
					isLowfareReturn = false;
				}
				if(flightAirPriceDetailsTemp.getLowFareFlightIndexReturn2()!=null){
					lowFareFlightIndexReturn2 = flightAirPriceDetailsTemp.getLowFareFlightIndexReturn2();
					isLowfareReturn = false;
				}
				if(flightAirPriceDetailsTemp.getReasonToSelectReturn()!=null)
					reasonToSelectReturn = flightAirPriceDetailsTemp.getReasonToSelectReturn();

			}else{
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,FlightErrorMessages.INVALID_PRICEKEY);
			}

		} catch (Exception e) {
			logger.error("Exception", e);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,FlightErrorMessages.INVALID_PRICEKEY);
		}

		if(flightPriceResponse.getFlightsearch().isSpecialSearch() && (flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("R"))){

			BigDecimal mealprice = new BigDecimal(0);
			BigDecimal baggageprice = new BigDecimal(0);
			BigDecimal flighttotalprice =  new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice());
			flighttotalprice = AmountRoundingModeUtil.roundingMode(flighttotalprice);
			BigDecimal returnmealprice = new BigDecimal(0);
			BigDecimal returnbaggageprice =  new BigDecimal(0);
			BigDecimal returnflighttotalprice = new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice());
			returnflighttotalprice = AmountRoundingModeUtil.roundingMode(returnflighttotalprice);

			if(flightPriceResponse.getSpecialServiceRequest()!=null && flightPriceResponse.getSpecialServiceRequest().isIsLCC()){
				for (PassengerDetails PassengerDetail : flightCustomerDetails.getPassengerdetailsList()) {
					if(PassengerDetail.getMealcode()!=null && PassengerDetail.getMealcode()!=""){
						for (List<MealDynamic> mealtype : flightPriceResponse.getSpecialServiceRequest().getMealDynamic()) {
							for (MealDynamic mealDynamicobj : mealtype) {
								if(PassengerDetail.getMealcode().equalsIgnoreCase(mealDynamicobj.getCode())){
									if(flightPriceResponse.getFlightsearch().getOrigin().equalsIgnoreCase(mealDynamicobj.getOrigin())){
										mealprice = mealprice.add(mealDynamicobj.getPrice()) ;
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
									baggageprice = baggageprice.add(new BigDecimal(baggageitem.getPrice()));
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
										returnmealprice = returnmealprice.add(mealDynamicobj.getPrice()) ;
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
									returnbaggageprice = returnbaggageprice.add(new BigDecimal(baggageitem.getPrice())) ;
									PassengerDetail.setReturnbaggageweight(String.valueOf(baggageitem.getWeight()));
								}
							}

						}
					}
				}
			}
			BigDecimal finalflighttotalprice = flighttotalprice.add(mealprice).add(baggageprice)  ;
			BigDecimal payableAmount = AmountRoundingModeUtil.roundingMode(flightPriceResponse.getFareFlightSegment().getPayableAmount());
			BigDecimal finalflightpayableprice = payableAmount.add(mealprice).add(baggageprice);
			finalflighttotalprice = AmountRoundingModeUtil.roundingMode(finalflighttotalprice);
			flightPriceResponse.getFareFlightSegment().setExtraBaggagePrice(String.valueOf(baggageprice));
			flightPriceResponse.getFareFlightSegment().setExtraMealPrice(String.valueOf(mealprice));
			flightPriceResponse.getFareFlightSegment().setTotalPrice(String.valueOf(finalflighttotalprice));
			flightPriceResponse.getFareFlightSegment().setPayableAmount(finalflightpayableprice);
			BigDecimal finalflighttotalpricewithoutmarkup = new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPriceWithoutMarkup()) ;
			finalflighttotalpricewithoutmarkup = finalflighttotalpricewithoutmarkup.add(mealprice).add(baggageprice) ;
			flightPriceResponse.getFareFlightSegment().setTotalPriceWithoutMarkup(String.valueOf(finalflighttotalpricewithoutmarkup));
			BigDecimal returnfinalflighttotalprice = returnflighttotalprice.add(returnmealprice).add(returnbaggageprice);
			BigDecimal returnpayableAmount = AmountRoundingModeUtil.roundingMode(flightPriceResponse.getSpecialFareFlightSegment().getPayableAmount());
			BigDecimal returnfinalflightpayableprice = returnpayableAmount.add(returnmealprice).add(returnbaggageprice);
			returnfinalflighttotalprice = AmountRoundingModeUtil.roundingMode(returnfinalflighttotalprice);
			flightPriceResponse.getSpecialFareFlightSegment().setExtraBaggagePrice(String.valueOf(returnbaggageprice));
			flightPriceResponse.getSpecialFareFlightSegment().setExtraMealPrice(String.valueOf(returnmealprice));
			flightPriceResponse.getSpecialFareFlightSegment().setTotalPrice(String.valueOf(returnfinalflighttotalprice));
			flightPriceResponse.getSpecialFareFlightSegment().setPayableAmount(returnfinalflightpayableprice);
			BigDecimal returnfinalflighttotalpricewithoutmarkup = new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPriceWithoutMarkup());
			finalflighttotalpricewithoutmarkup = finalflighttotalpricewithoutmarkup.add(returnmealprice).add(returnbaggageprice);
			flightPriceResponse.getSpecialFareFlightSegment().setTotalPriceWithoutMarkup(String.valueOf(returnfinalflighttotalpricewithoutmarkup));
			flightPriceResponse.setFinalPriceWithGST(finalflighttotalprice.add(returnfinalflighttotalprice));

			// With Low Fare Alert Detail
			flightBookingResponse = createFlightOrderCustomerDataForSpecialTrip(flightCustomerDetails,flightTempAirSegmentDAO,flightPriceResponse,response,request,lowFareFlightIndex1,lowFareFlightIndex2,reasonToSelect,lowFareFlightIndexReturn1,lowFareFlightIndexReturn2,reasonToSelectReturn,isLowfare,isLowfareReturn,appKeyVo);

		}else{
			BigDecimal mealprice = new BigDecimal(0);
			BigDecimal baggageprice = new BigDecimal(0);
			BigDecimal flighttotalprice =  new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice());
			flighttotalprice = AmountRoundingModeUtil.roundingMode(flighttotalprice);

			if(!flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("R")){
				if(flightPriceResponse.getSpecialServiceRequest()!=null && flightPriceResponse.getSpecialServiceRequest().isIsLCC()){
					for (PassengerDetails PassengerDetail : flightCustomerDetails.getPassengerdetailsList()) {
						if(PassengerDetail.getMealcode()!=null && PassengerDetail.getMealcode()!=""){
							for (List<MealDynamic> mealtype : flightPriceResponse.getSpecialServiceRequest().getMealDynamic()) {
								for (MealDynamic mealDynamicobj : mealtype) {
									if(PassengerDetail.getMealcode().equalsIgnoreCase(mealDynamicobj.getCode())){										
										for (FlightSegmentsGroup flightSegmentsGroups : flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups()) {
											for (FlightSegments flightSegments : flightSegmentsGroups.getFlightSegments()) {
												if(flightSegments.getSegments().size() > 1){
													for (Segments segments : flightSegments.getSegments()) {														
														if(segments.getOri().equalsIgnoreCase(mealDynamicobj.getOrigin())){
															mealprice =  mealprice.add(mealDynamicobj.getPrice()) ;
															PassengerDetail.setMealname(mealDynamicobj.getAirlineDescription());
														}
													}
												}else{
													if(flightPriceResponse.getFlightsearch().getOrigin().equalsIgnoreCase(mealDynamicobj.getOrigin())){
														mealprice = mealprice.add(mealDynamicobj.getPrice()) ;
														PassengerDetail.setMealname(mealDynamicobj.getAirlineDescription());
													}
												}

											}
										}					

									}
								}
							}
						}
						if(PassengerDetail.getBaggagecode()!=null && PassengerDetail.getBaggagecode()!=""){
							for (List<Baggage> baggagetype : flightPriceResponse.getSpecialServiceRequest().getBaggage()) {
								for (Baggage baggageitem : baggagetype) {
									if(PassengerDetail.getBaggagecode().equalsIgnoreCase(baggageitem.getCode())){
										baggageprice = baggageprice.add(new BigDecimal(baggageitem.getPrice()) );
										PassengerDetail.setBaggageweight(String.valueOf(baggageitem.getWeight()));
									}
								}

							}
						}
					}
				}
			}

			if(flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("SR")){
				if(flightPriceResponse.getSpecialServiceRequest()!=null && flightPriceResponse.getSpecialServiceRequest().isIsLCC()){
					for (PassengerDetails PassengerDetail : flightCustomerDetails.getPassengerdetailsList()) {
						if(PassengerDetail.getMealcode()!=null && PassengerDetail.getMealcode()!=""){
							for (List<MealDynamic> mealtype : flightPriceResponse.getSpecialServiceRequest().getMealDynamic()) {
								for (MealDynamic mealDynamicobj : mealtype) {
									if(PassengerDetail.getMealcode().equalsIgnoreCase(mealDynamicobj.getCode())){										
										for (FlightSegmentsGroup flightSegmentsGroups : flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups()) {
											for (FlightSegments flightSegments : flightSegmentsGroups.getFlightSegments()) {
												if(flightSegments.getSegments().size() > 1){
													for (Segments segments : flightSegments.getSegments()) {														
														if(segments.getOri().equalsIgnoreCase(mealDynamicobj.getOrigin())){
															mealprice = mealprice.add(mealDynamicobj.getPrice()) ;
															PassengerDetail.setMealname(mealDynamicobj.getAirlineDescription());
														}
													}
												}else{
													if(flightPriceResponse.getFlightsearch().getOrigin().equalsIgnoreCase(mealDynamicobj.getOrigin())){
														mealprice = mealprice.add(mealDynamicobj.getPrice()) ;
														PassengerDetail.setMealname(mealDynamicobj.getAirlineDescription());
													}
												}

											}
										}							

									}
								}
							}
						}
						if(PassengerDetail.getBaggagecode()!=null && PassengerDetail.getBaggagecode()!=""){
							for (List<Baggage> baggagetype : flightPriceResponse.getSpecialServiceRequest().getBaggage()) {
								for (Baggage baggageitem : baggagetype) {
									if(PassengerDetail.getBaggagecode().equalsIgnoreCase(baggageitem.getCode())){
										baggageprice = baggageprice.add(new BigDecimal(baggageitem.getPrice()));
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
									mealprice = mealprice.add(mealDynamicobj.getPrice()) ;
									PassengerDetail.setMealname(mealDynamicobj.getAirlineDescription());
								}
							}
						}
						if(PassengerDetail.getReturnmealcode()!=null && PassengerDetail.getReturnmealcode()!=""){
							for (MealDynamic mealDynamicobj : flightPriceResponse.getSpecialServiceRequest().getMealDynamic().get(1)) {
								if(PassengerDetail.getReturnmealcode().equalsIgnoreCase(mealDynamicobj.getCode())){
									mealprice =  mealprice.add(mealDynamicobj.getPrice()) ;
									PassengerDetail.setReturnmealname(mealDynamicobj.getAirlineDescription());
								}
							}
						}
						if(PassengerDetail.getBaggagecode()!=null && PassengerDetail.getBaggagecode()!=""){
							for (Baggage baggageitem : flightPriceResponse.getSpecialServiceRequest().getBaggage().get(0)) {
								if(PassengerDetail.getBaggagecode().equalsIgnoreCase(baggageitem.getCode())){
									baggageprice = baggageprice.add(new BigDecimal(baggageitem.getPrice()));
									PassengerDetail.setBaggageweight(String.valueOf(baggageitem.getWeight()));
								}
							}
						}
						if(PassengerDetail.getReturnbaggagecode()!=null && PassengerDetail.getReturnbaggagecode()!=""){
							for (Baggage baggageitem : flightPriceResponse.getSpecialServiceRequest().getBaggage().get(1)) {
								if(PassengerDetail.getReturnbaggagecode().equalsIgnoreCase(baggageitem.getCode())){
									baggageprice = baggageprice.add(new BigDecimal(baggageitem.getPrice()));
									PassengerDetail.setReturnbaggageweight(String.valueOf(baggageitem.getWeight()));
								}
							}
						}
					}
				}
			}

			BigDecimal finalflighttotalprice = flighttotalprice.add(mealprice).add(baggageprice);
			BigDecimal payableAmount = AmountRoundingModeUtil.roundingMode(flightPriceResponse.getFareFlightSegment().getPayableAmount());
			BigDecimal finalflightpayableprice = payableAmount.add(mealprice).add(baggageprice);

			finalflighttotalprice = AmountRoundingModeUtil.roundingMode(finalflighttotalprice);
			flightPriceResponse.getFareFlightSegment().setExtraBaggagePrice(String.valueOf(baggageprice));
			flightPriceResponse.getFareFlightSegment().setExtraMealPrice(String.valueOf(mealprice));
			flightPriceResponse.getFareFlightSegment().setTotalPrice(String.valueOf(finalflighttotalprice));
			flightPriceResponse.getFareFlightSegment().setPayableAmount(finalflightpayableprice);
			BigDecimal finalflighttotalpricewithoutmarkup = new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPriceWithoutMarkup());
			finalflighttotalpricewithoutmarkup = finalflighttotalpricewithoutmarkup.add(mealprice).add(baggageprice);

			flightPriceResponse.getFareFlightSegment().setTotalPriceWithoutMarkup(String.valueOf(finalflighttotalpricewithoutmarkup));
			flightPriceResponse.setFinalPriceWithGST(new BigDecimal(String.valueOf(finalflighttotalprice)));

			// With Low Fare Alert Detail
			flightBookingResponse = createFlightOrderCustomerData(flightCustomerDetails,flightTempAirSegmentDAO,flightPriceResponse,response,request,companyDao,isLowfare,lowFareFlightIndex1,lowFareFlightIndex2,reasonToSelect,isLowfareReturn,lowFareFlightIndexReturn1,lowFareFlightIndexReturn2,reasonToSelectReturn,appKeyVo);
		}
		try{
			insertIntoFlightBook(appKeyVo,flightBookingResponse.getTransactionKey(),request);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return flightBookingResponse;
	}

	private String checkGetEmulatedUserById(FlightCustomerDetails flightCustomerDetails) {
		if(flightCustomerDetails.isEmulateFlag())
		{
			return flightCustomerDetails.getEmulateByUserId();
		}
		return flightCustomerDetails.getUserid();
	}

	@ExceptionHandler(BaseException.class)
	public @ResponseBody RestError handleCustomException (BaseException ex, HttpServletResponse response) {
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return ex.transformException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	public FlightBookingResponse createFlightOrderCustomerDataForSpecialTrip(FlightCustomerDetails flightCustomerDetails,FlightTempAirSegmentDAO TempDAO,FlightPriceResponse flightPriceResponse,HttpServletResponse response,HttpServletRequest request,String lowFareFlightIndex1,String lowFareFlightIndex2,String reasonToSelect,String lowFareFlightIndexReturn1,String lowFareFlightIndexReturn2,String reasonToSelectReturn,boolean isLowFare,boolean isLowFareReturn, AppKeyVo appKeyVo){
		FlightBookingResponse flightBookingResponse = null;
		List<FlightOrderCustomer> flightOrderCustomers=null;
		OrderCustomer orderCustomer=null;
		FrontUserDetail frontUserDetail = null;
		BigDecimal processingFees=new BigDecimal("0.0");//??in booked currency
		BigDecimal baseToBookingExchangeRate=flightPriceResponse.getFlightsearch().getBaseToBookingExchangeRate();;

		String orderId="FTP"+new GenerateID().toString();
		String orderIdSpecial="FTP"+new GenerateID().toString();
		FlightOrderRow flightOrderRowonward = new FlightOrderRow() ;
		FlightOrderRow flightOrderRowreturn = new FlightOrderRow() ;
		CompanyConfig currentCompanyConfig = null;
		for(int i=0;i<2;i++){
			FlightOrderRow flightOrderRow=new FlightOrderRow();
			List<PassengerDetails> passengerdetailsList=flightCustomerDetails.getPassengerdetailsList();
			logger.info("createFOC method called : i.... "+i);
			//details of the first passenger
			orderCustomer=new OrderCustomer();
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

			frontUserDetail =  GetFrontUserDetail.getFrontUserDetailDetails(orderCustomer,frontUserDao);
			if(frontUserDetail != null){
				try{
					frontUserDetail = frontUserDao.insertFrontUserDetail(frontUserDetail);
				}catch(Exception e){
					logger.error("Exception", e);
				}
				if(frontUserDetail.getId() != null && frontUserDetail.getId() != 0 && i == 0){
					emaildao.insertEmail(String.valueOf(frontUserDetail.getId()), 0, Email.EMAIL_TYPE_FRONT_USER_REGISTRATION_BY_TAYYARAH);
				}
			}
			try {
				flightBookingDao.insertOrderCustomerDetails(orderCustomer);
			} catch (Exception e1) {
				logger.error("Exception", e1);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}		

			flightOrderRow.setCustomer(orderCustomer);
			flightOrderRow.setFlightCustomer(orderCustomer);
			flightOrderRow.setPassengerCount(passengerdetailsList.size());
			FareFlightSegment NewfareFlightSegment=flightPriceResponse.getFareFlightSegment();
			if(i==1){
				NewfareFlightSegment=flightPriceResponse.getSpecialFareFlightSegment();
			}
			BigDecimal apiToBaseExchangeRate=NewfareFlightSegment.getApiToBaseExchangeRate();
			flightOrderRow.setBooking_currency(flightPriceResponse.getFlightsearch().getBookedCurrency());//requested currency
			flightOrderRow.setApiCurrency(NewfareFlightSegment.getApiCurrency());//currency of the APi response
			flightOrderRow.setBaseCurrency(flightPriceResponse.getFlightsearch().getBaseCurrency());//currency of the system
			flightOrderRow.setBaseToBookingExchangeRate(baseToBookingExchangeRate);
			flightOrderRow.setApiToBaseExchangeRate(apiToBaseExchangeRate);
			flightOrderRow.setGst_on_markup(flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP));
			flightOrderRow.setGstOnFlights(flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP));

			if(i==0){
				flightOrderRow.setProviderAPI("Travelport");
				if(flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getApiProvider().equalsIgnoreCase("Bluestar")){
					orderId="FBS"+new GenerateID().toString();
					flightOrderRow.setProviderAPI("Bluestar");
				}
				if(flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getApiProvider().equalsIgnoreCase("Tayyarah")){
					orderId="FTAY"+new GenerateID().toString();
					flightOrderRow.setProviderAPI("Tayyarah");
				}
				if(flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getApiProvider().equalsIgnoreCase("Lintas")){
					orderId="FLIN"+new GenerateID().toString();
					flightOrderRow.setProviderAPI("Lintas");
				}
				if(flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getApiProvider().equalsIgnoreCase("TBO")){
					orderId="FTBO"+new GenerateID().toString();
					flightOrderRow.setProviderAPI("TBO");
				}
			}else
				if(i==1){
					flightOrderRow.setProviderAPI("Travelport");
					if(flightPriceResponse.getSpecialFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getApiProvider().equalsIgnoreCase("Bluestar")){
						orderIdSpecial="FBS"+new GenerateID().toString();
						flightOrderRow.setProviderAPI("Bluestar");
					}
					if(flightPriceResponse.getSpecialFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getApiProvider().equalsIgnoreCase("Tayyarah")){
						orderIdSpecial="FTAY"+new GenerateID().toString();
						flightOrderRow.setProviderAPI("Tayyarah");
					}
					if(flightPriceResponse.getSpecialFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getApiProvider().equalsIgnoreCase("Lintas")){
						orderIdSpecial="FLIN"+new GenerateID().toString();
						flightOrderRow.setProviderAPI("Lintas");
					}
					if(flightPriceResponse.getSpecialFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getApiProvider().equalsIgnoreCase("TBO")){
						orderIdSpecial="FTBO"+new GenerateID().toString();
						flightOrderRow.setProviderAPI("TBO");
					}
				}
			if(i==0){
				flightOrderRow.setArrivalDate(flightPriceResponse.getFlightsearch().getArvlDate());
				flightOrderRow.setDepartureDate(flightPriceResponse.getFlightsearch().getDepDate());
				flightOrderRow.setDestination(flightPriceResponse.getFlightsearch().getDestination());
				flightOrderRow.setOrigin(flightPriceResponse.getFlightsearch().getOrigin());
			}else{
				flightOrderRow.setDepartureDate(flightPriceResponse.getFlightsearch().getArvlDate());
				flightOrderRow.setArrivalDate(flightPriceResponse.getFlightsearch().getArvlDate());
				flightOrderRow.setOrigin(flightPriceResponse.getFlightsearch().getDestination());
				flightOrderRow.setDestination(flightPriceResponse.getFlightsearch().getOrigin());
			}
			flightOrderRow.setTripType("SR");
			flightOrderRow.setPaymentStatus("pending");
			flightOrderRow.setPaidBy(flightCustomerDetails.getPaymode());
			flightOrderRow.setStatusAction("Initiated");
			flightOrderRow.setVersion(VERSION);
			flightOrderRow.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			if(i==0){
				flightOrderRow.setOrderId(orderId);
			}else{
				flightOrderRow.setOrderId(orderIdSpecial);
			}
			flightOrderRow.setTransaction_key(flightCustomerDetails.getTransactionkey());

			MarkupCommissionDetails markupCommissionDetails = flightPriceResponse.getMarkupCommissionDetails();

			String airlineCode ="";
			String cabinCode ="";
			if(NewfareFlightSegment!=null && NewfareFlightSegment.getFlightSegmentsGroups()!=null && flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().size()>0 && flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments()!=null && flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().size()>0 && flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0)!=null)
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
				fareBasisCode=NewfareFlightSegment.getFareBasisCode();
			}
			try {
				UmarkUpServiceCall.getMarkupValuesForEachCOMpany(flightPriceResponse.getFlightsearch(), flightPriceResponse.getFlightMarkUpConfiglistMap(),airlineCode , NewfareFlightSegment,markupCommissionDetails,fareBasisCode);
			} catch (Exception e1) {
				logger.error("MArkUpException", e1);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}
			List<FlightOrderRowMarkup> flightOrderRowMarkups = getMarkupDetail(markupCommissionDetails,flightOrderRow);
			flightOrderRow.setFlightOrderRowMarkupList(flightOrderRowMarkups);


			flightOrderRow.setConfigId(String.valueOf(appKeyVo.getConfigId()));
			flightOrderRow.setCompanyId(String.valueOf(appKeyVo.getCompanyId()));
			flightOrderRow.setCreatedBy(flightCustomerDetails.getUsername());//
			flightOrderRow.setUserId(checkGetEmulatedUserById(flightCustomerDetails));

			// check for deal sheet
			List<FlightOrderRowCommission> flightOrderRowCommissions = null;
			logger.info("################## flight booking--createFlightOrderCustomerDataForSpecialTrip-------");
			try {
				int configId = Integer.valueOf(flightOrderRow.getConfigId());
				CompanyConfig companyConfig = companyConfigDAO.getCompanyConfigByConfigId(configId);
				currentCompanyConfig = companyConfig;
				logger.info("################## flight booking--createFlightOrderCustomerData-------");			

				Company	company = companyDao.getCompany(Integer.parseInt(flightOrderRow.getCompanyId()));
				Company parentCompany = null;
				try{
					parentCompany = companyDao.getParentCompany(company);
				}catch(Exception e){
					e.printStackTrace();
				}

				if(companyConfig != null )
				{
					if(companyConfig.getCompanyConfigType().isB2E()){
						if(companyConfig.getTaxtype()!= null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){

							if(flightPriceResponse.getFlightsearch().isDomestic())
							{
								FlightOrderRowGstTax flightOrderRowGstTax = new FlightOrderRowGstTax();
								FlightDomesticGstTaxConfig flightDomesticGstTaxConfig = companyConfig.getFlightDomesticGstTaxConfig();
								flightOrderRowGstTax = createFlightOrderRowGstTaxDomestic(flightDomesticGstTaxConfig,flightOrderRowGstTax,company,parentCompany,flightPriceResponse.getFlightsearch());
								flightOrderRow.setFlightOrderRowGstTax(flightOrderRowGstTax);
							}
							if(flightPriceResponse.getFlightsearch().isIsInternational())
							{
								FlightOrderRowGstTax flightOrderRowGstTax = new FlightOrderRowGstTax();
								FlightInternationalGstTaxConfig flightInternationalGstTaxConfig = companyConfig.getFlightInternationalGstTaxConfig();
								flightOrderRowGstTax = createFlightOrderRowGstTaxInternational(flightInternationalGstTaxConfig,flightOrderRowGstTax,company,parentCompany,flightPriceResponse.getFlightsearch());
								flightOrderRow.setFlightOrderRowGstTax(flightOrderRowGstTax);
							}
							flightOrderRow.setFlightOrderRowServiceTax(null);
						}else{
							if(flightPriceResponse.getFlightsearch().isDomestic())
							{
								FlightOrderRowServiceTax flightOrderRowServiceTax = new FlightOrderRowServiceTax();
								FlightDomesticServiceTaxConfig flightDomesticServiceTaxConfig = companyConfig.getFlightDomesticServiceTaxConfig();
								flightOrderRowServiceTax= createFlightOrderRowServiceTaxDomestic(flightDomesticServiceTaxConfig,flightOrderRowServiceTax);
								flightOrderRow.setFlightOrderRowServiceTax(flightOrderRowServiceTax);
							}
							if(flightPriceResponse.getFlightsearch().isIsInternational())
							{
								FlightOrderRowServiceTax flightOrderRowServiceTax = new FlightOrderRowServiceTax();
								FlightInternationalServiceTaxConfig flightInternationalServiceTaxConfig = companyConfig.getFlightInternationalServiceTaxConfig();
								flightOrderRowServiceTax= createFlightOrderRowServiceTaxInternational(flightInternationalServiceTaxConfig,flightOrderRowServiceTax);
								flightOrderRow.setFlightOrderRowServiceTax(flightOrderRowServiceTax);
							}
							flightOrderRow.setFlightOrderRowGstTax(null);
						}
					}
					else{
						flightOrderRow.setFlightOrderRowServiceTax(null);
						flightOrderRow.setFlightOrderRowGstTax(null);
					}
				}

				if(companyConfig != null && companyConfig.isSheetMode())
				{
					logger.info("################## flight booking--createFlightOrderCustomerDataForSpecialTrip--commission SheetMode-----");

					HashMap<Integer, AirlineLiteral> airlineLitralMap = new HashMap<Integer, AirlineLiteral>();
					airlineLitralMap.put(Constants.TYPE_FARE, new AirlineLiteral(Constants.TYPE_FARE, fareBasisCode, true));
					airlineLitralMap.put(Constants.TYPE_CLASS, new AirlineLiteral(Constants.TYPE_CLASS, "A", true));

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
					logger.info("################## flight booking--createFlightOrderCustomerDataForSpecialTrip--normal commission mode-----");
					flightOrderRowCommissions = getCommissionDetails(markupCommissionDetails, flightOrderRow);
				}
				try {
					UmarkUpServiceCall.getCommissionWithMarkupValuesForEachCOMpany(flightOrderRowCommissions,flightPriceResponse.getFlightsearch(), flightPriceResponse.getFlightMarkUpConfiglistMap(),airlineCode , NewfareFlightSegment,markupCommissionDetails,fareBasisCode);
				} catch (Exception e1) {
					logger.error("MArkUpException", e1);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}
				flightOrderRow.setFlightOrderRowCommissionList(flightOrderRowCommissions);
			} catch (Exception e2) {
				logger.info("################## Deal sheet retrival Exception---------"+e2.getMessage());
				logger.error("################## Deal sheet retrival Exception---------"+e2);
				e2.printStackTrace();
			}
			BigDecimal markup = getTotalMarkup(markupCommissionDetails, (passengerdetailsList != null?passengerdetailsList.size():0));
			flightOrderRow.setMarkUp(markup);//in base currency
			BigDecimal paymentprocessingFees = new BigDecimal("0.0");
			if(!flightCustomerDetails.getPaymode().equals("cash")){
				paymentprocessingFees = NewfareFlightSegment.getPayableAmount().divide(new BigDecimal("100")).multiply(new BigDecimal("2.0")) ;
				processingFees = processingFees.add(paymentprocessingFees);
			}
			flightOrderRow.setProcessingFees(paymentprocessingFees);
			flightOrderRow.setPrice(new BigDecimal(NewfareFlightSegment.getBasePriceWithoutMarkup()));//???in api currency
			flightOrderRow.setTotalTaxes(new BigDecimal(NewfareFlightSegment.getTaxesWithoutMarkup()));//in api currency
			flightOrderRow.setSupplierTds(NewfareFlightSegment.getSupplierTds());
			flightOrderRow.setSupplierPrice(new BigDecimal(NewfareFlightSegment.getApi_totalPriceWithoutMarkup()));
			flightOrderRow.setSupplierCommission(NewfareFlightSegment.getSupplierCommissionearned());
			String totalPriceInBookedCurrency = NewfareFlightSegment.getTotalPrice();//in booked price
			if(NewfareFlightSegment.getFlightServiceTax()!=null){
				// BigDecimal bookedprice  =  new BigDecimal(NewfareFlightSegment.getTotalPrice()).subtract(NewfareFlightSegment.getFlightServiceTax().getTotalServiceTax()).subtract(NewfareFlightSegment.getFlightServiceTax().getManagementFee());
				BigDecimal bookedprice  =  new BigDecimal(NewfareFlightSegment.getBasePrice()).add(new BigDecimal(NewfareFlightSegment.getTaxes()));
				totalPriceInBookedCurrency = bookedprice.toString();
			}
			if(NewfareFlightSegment.getFlightGstTax()!=null)
			{
				BigDecimal bookedprice  =  new BigDecimal(NewfareFlightSegment.getBasePrice()).add(new BigDecimal(NewfareFlightSegment.getTaxes()));
				totalPriceInBookedCurrency = bookedprice.toString();
			}
			BigDecimal totalPrice = new BigDecimal(totalPriceInBookedCurrency).add(flightOrderRow.getProcessingFees());//in booked currency
			totalPrice = AmountRoundingModeUtil.roundingMode(totalPrice);
			
			//added by basha
			try{
				RmConfigModel  rmConfigModel=rmConfigDetailDAO.getRmConfigModel(appKeyVo.getCompanyId());
				   if(rmConfigModel!=null){
				   FlightOrderRowRmConfigStruct flightOrderRowRmConfigStruct=new FlightOrderRowRmConfigStruct();
				   flightOrderRowRmConfigStruct.setRmDynamicData(rmConfigModel.getDynamicFieldsData());
				   flightOrderRow.setFlightOrderRowRmConfigStruct(flightOrderRowRmConfigStruct);
				   }
				} catch (Exception e) {
					logger.error("Exception", e);
					//throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}
				
			
			flightOrderRow.setExtramealprice(new BigDecimal(NewfareFlightSegment.getExtraMealPrice()));
			flightOrderRow.setExtrabaggageprice(new BigDecimal(NewfareFlightSegment.getExtraBaggagePrice()));
			flightOrderRow.setFinalPrice(totalPrice);
			NewfareFlightSegment.setTotalPrice(totalPrice.toString());
			//flightOrderRow.setCurrencyCode(flightPriceResponse.getPassengerFareBreakUps().get(0).getCurrency());
			flightOrderRow.setLastTicketingDate(NewfareFlightSegment.getLatestTicketingTime() );
			flightOrderRow.setBookingMode("Online");
			flightOrderRow.setTotInvoiceAmount(NewfareFlightSegment.getPayableAmount());
			flightOrderRow.setRecievedAmount(new BigDecimal(0));
			if(NewfareFlightSegment.getFlightServiceTax()!=null)
				flightOrderRow.setServiceTax(NewfareFlightSegment.getFlightServiceTax().getTotalServiceTax());
			else
				flightOrderRow.setServiceTax(new BigDecimal(0));

			if(NewfareFlightSegment.getFlightGstTax()!=null)
				flightOrderRow.setGstOnFlights(flightPriceResponse.getFareFlightSegment().getFlightGstTax().getTotalTax());
			else
				flightOrderRow.setGstOnFlights(new BigDecimal(0));

			if(flightCustomerDetails.getIsCompanyEntity()!=null && flightCustomerDetails.getIsCompanyEntity()){ 
				Integer companyEntityId = flightCustomerDetails.getCompanyEntityId();
				flightOrderRow.setCompanyEntityId(companyEntityId.longValue());
			}


			try {
				flightBookingDao.insertFlightOrderRowDetails(flightOrderRow);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Exception", e);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}

			//details of all passengers
			flightOrderCustomers=new ArrayList<FlightOrderCustomer>();
			createFlightOrderCustomerList(flightOrderCustomers,flightCustomerDetails,orderCustomer,flightOrderRow);
			flightOrderRow.setFlightOrderCustomers(flightOrderCustomers);


			List<FlightOrderCustomerPriceBreakup> flightOrderCustomerPriceBreakups=new ArrayList<FlightOrderCustomerPriceBreakup>();
			createFlightOrderCustomerPriceBreakupList(flightOrderCustomerPriceBreakups,flightPriceResponse,orderCustomer,flightOrderRow,i);
			flightOrderRow.setFlightOrderCustomerPriceBreakups(flightOrderCustomerPriceBreakups);

			//details of all segments
			List<FlightOrderTripDetail> flightOrderTripDetails=new ArrayList<FlightOrderTripDetail>();
			createFlightOrderTripDetailList(flightOrderTripDetails,flightPriceResponse,orderCustomer,flightOrderRow,i);
			flightOrderRow.setFlightOrderTripDetails(flightOrderTripDetails);



			List<FlightOrderCustomerSSR> flightOrderCustomersSSRlist = new ArrayList<FlightOrderCustomerSSR>();
			if(i==0)
				createFlightOrderCustomerSSRDetails(flightCustomerDetails,flightOrderCustomersSSRlist,flightPriceResponse,orderCustomer,flightOrderRow,false);
			if(i==1)
				createFlightOrderCustomerSSRDetails(flightCustomerDetails,flightOrderCustomersSSRlist,flightPriceResponse,orderCustomer,flightOrderRow,true);

			flightOrderRow.setFlightOrderCustomerSSR(flightOrderCustomersSSRlist);


			//////////setting markup deatails/ commission

			//System.out.println("flightPriceResponse.getMarkupCommissionDetails().getRateType() :"+flightPriceResponse.getMarkupCommissionDetails().getRateType());



			if(markupCommissionDetails!=null &&markupCommissionDetails.getRateType()!=null){

				//	if(markupCommissionDetails.getRateType().equalsIgnoreCase("NET")){

				//update markup table tabl


				for(FlightOrderRowMarkup flightOrderRowMarkup:flightOrderRowMarkups)
					try {
						flightBookingDao.insertMarkupDetails(flightOrderRowMarkup);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("HibernateException", e);
						throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
					}
				for(FlightOrderRowCommission flightOrderRowCommission:flightOrderRowCommissions)
					try {
						flightBookingDao.insertCommission(flightOrderRowCommission);
					} catch (Exception e) {
						logger.error("HibernateException", e);
						throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
					}
			}

			/////////////////////////////
			for(FlightOrderCustomerPriceBreakup flightOrderCustomerPriceBreakup:flightOrderRow.getFlightOrderCustomerPriceBreakups()){
				try {
					flightBookingDao.insertFlightOrderCustomerPriceBreakupDetails(flightOrderCustomerPriceBreakup);
				} catch (Exception e) {
					logger.error("Exception", e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}
			}
			for(FlightOrderCustomer flightOrderCustomer:flightOrderRow.getFlightOrderCustomers()){
				try {
					flightBookingDao.insertFlightOrderCustomerDetails(flightOrderCustomer);
				} catch (Exception e) {
					logger.error("Exception", e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}
			}
			if(flightOrderCustomersSSRlist.size() > 0){
				for(FlightOrderCustomerSSR flightOrderCustomerSSR:flightOrderCustomersSSRlist){
					try {
						flightBookingDao.insertFlightOrderCustomerSSRDetails(flightOrderCustomerSSR);
					} catch (Exception e) {
						logger.error("Exception", e);
						throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
					}
				}
			}
			for(FlightOrderTripDetail flightOrderTripDetail:flightOrderRow.getFlightOrderTripDetails()){
				try {
					flightBookingDao.insertFlightOrderTripDetailDetails(flightOrderTripDetail);
				} catch (Exception e) {
					logger.error("Exception", e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}
			}
			try {
				flightBookingDao.updateFlightOrderRowDetails(flightOrderRow);
			} catch (Exception e) {
				logger.error("Exception", e);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}
			if(i == 0)
				flightOrderRowonward = flightOrderRow;
			if(i == 1)
				flightOrderRowreturn = flightOrderRow;
		}

		List<Company> companyListBottomToTop= new LinkedList<>();
		List<User> userListBottomToTop= new LinkedList<>();
		Map<Integer, CutandPayModel> cutAndPayUserMap = new LinkedHashMap<>();	
		Map<Integer, CutandPayModel> cutAndPayUserMapSpecial = new LinkedHashMap<>();	
		String newpgid = "";
		if(flightCustomerDetails.getPaymode().equals("cash")){
			boolean result=false;
			WalletAmountTranferHistory walletAmountTranferHistory=new WalletAmountTranferHistory();
			WalletAmountTranferHistory walletAmountTranferHistorySpecial=new WalletAmountTranferHistory();
			try {
				walletAmountTranferHistory.setActionId(orderId);
				walletAmountTranferHistory.setCurrency(flightPriceResponse.getFareFlightSegment().getCurrency());
				walletAmountTranferHistory.setAmount((new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()).add(flightPriceResponse.getGSTonMarkup())).divide(baseToBookingExchangeRate,0,RoundingMode.UP));
				walletAmountTranferHistory.setRemarks("Flight Booking payment");
				walletAmountTranferHistorySpecial.setActionId(orderIdSpecial);
				walletAmountTranferHistorySpecial.setCurrency(flightPriceResponse.getSpecialFareFlightSegment().getCurrency());
				walletAmountTranferHistorySpecial.setAmount((new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice()).add(flightPriceResponse.getGSTonMarkupSpecial())).divide(baseToBookingExchangeRate,0,RoundingMode.UP));
				walletAmountTranferHistorySpecial.setRemarks("Flight Booking payment");


				if(currentCompanyConfig!=null)
				{
					companyListBottomToTop = CommonUtil.getParentCompanyBottomToTop(currentCompanyConfig.getCompany_id(),companyDao);
					if(companyListBottomToTop!=null && companyListBottomToTop.size()>0)
					{
						User currentUser = companyDao.getUserById(Integer.valueOf(flightCustomerDetails.getUserid()));
						userListBottomToTop = CommonUtil.getUsersAllWithUserModeBottomToTop(companyListBottomToTop,companyDao,currentUser);
					}
					cutAndPayUserMap = CommonUtil.getCutandPayModelUsersFlightSpecialRoundTrip(flightPriceResponse,currentCompanyConfig,userListBottomToTop,flightCustomerDetails,false);
					cutAndPayUserMapSpecial =  CommonUtil.getCutandPayModelUsersFlightSpecialRoundTrip(flightPriceResponse,currentCompanyConfig,userListBottomToTop,flightCustomerDetails,true);

					boolean checkBookingAmountEligibility = false;
					boolean checkBookingAmountEligibilitySpecial = false;
					if(userListBottomToTop!=null && userListBottomToTop.size()>0)
					{
						for(User userInner : userListBottomToTop)
						{
							if(userInner.getAgentWallet()!=null)
							{
								if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
								{
									BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
									if(!userWalletDAO.checkWalletAmount(userInner.getId(), totalPayableAmount,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0))){
										result = false;
										checkBookingAmountEligibility = false;
									}else{
										checkBookingAmountEligibility = true;
									}


								}
								if(cutAndPayUserMapSpecial!=null && cutAndPayUserMapSpecial.get(userInner.getId())!=null)
								{
									BigDecimal totalPayableAmount = cutAndPayUserMapSpecial.get(userInner.getId()).getPayableAmount();
									if(!userWalletDAO.checkWalletAmount(userInner.getId(), totalPayableAmount,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0))){
										result = false;
										checkBookingAmountEligibilitySpecial = false;
									}else{
										checkBookingAmountEligibilitySpecial = true;
									}


								}
							}
						}
					}	

					if(checkBookingAmountEligibility && checkBookingAmountEligibilitySpecial)
					{
						Map<Integer,Boolean> userMapBottomToTop= new LinkedHashMap<>();
						if(userListBottomToTop!=null && userListBottomToTop.size()>0)
						{
							for(User userInner : userListBottomToTop)
							{
								if(userInner.getAgentWallet()!=null)
								{
									if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
									{
										BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
										if(userWalletDAO.checkWalletAmount(userInner.getId(), totalPayableAmount,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0)))
										{		
											userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_REMARKS.getMessage(),true);
											userMapBottomToTop.put(userInner.getId(),true);

										}
										else{
											if(userMapBottomToTop!=null && userMapBottomToTop.size()>0)
											{
												for(Entry<Integer,Boolean>  userMap :userMapBottomToTop.entrySet())
												{
													if(userMap.getValue())
													{
														totalPayableAmount = cutAndPayUserMap.get(userMap.getKey()).getPayableAmount();
														userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
													}
												}
											}
											result = false;
											break;
										}
									}
									else{
										result = false;
									}

									if(cutAndPayUserMapSpecial!=null && cutAndPayUserMapSpecial.get(userInner.getId())!=null)
									{
										BigDecimal totalPayableAmount = cutAndPayUserMapSpecial.get(userInner.getId()).getPayableAmount();
										if(userWalletDAO.checkWalletAmount(userInner.getId(), totalPayableAmount,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0)))
										{		
											userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_REMARKS.getMessage(),true);
											userMapBottomToTop.put(userInner.getId(),true);

										}
										else{
											if(userMapBottomToTop!=null && userMapBottomToTop.size()>0)
											{
												for(Entry<Integer,Boolean>  userMap :userMapBottomToTop.entrySet())
												{
													if(userMap.getValue())
													{
														totalPayableAmount = cutAndPayUserMapSpecial.get(userMap.getKey()).getPayableAmount();
														userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistorySpecial,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
													}
												}
											}
											result = false;
											break;
										}
									}
									else{
										result = false;
									}

								}
							}
							result = true;
						}	
					}else{
						result = false;
					}			
				}		


				//	result = userWalletDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()).divide(baseToBookingExchangeRate,0,RoundingMode.UP),walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_REMARKS.getMessage(),true);
				//	result = userWalletDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice()).divide(baseToBookingExchangeRate,0,RoundingMode.UP),walletAmountTranferHistorySpecial,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_REMARKS.getMessage(),true);

				//result = userWalletDAO.getWalletStatusForSpecial(flightCustomerDetails.getUserid(),flightPriceResponse.getFinalPriceWithGST().divide(baseToBookingExchangeRate,2,RoundingMode.HALF_UP),walletAmountTranferHistory,walletAmountTranferHistorySpecial,"FlightBooking Initiated");
			} catch (Exception e1) {
				logger.error("Exception", e1);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}

			PaymentTransaction paymentTransaction = new PaymentTransaction();
			paymentTransaction.setAmount(AmountRoundingModeUtil.roundingMode(flightPriceResponse.getFareFlightSegment().getPayableAmount().add(processingFees)));// in booking currency
			paymentTransaction.setCurrency(flightPriceResponse.getFlightsearch().getBookedCurrency());
			paymentTransaction.setRefno(flightOrderRowonward.getOrderId());
			paymentTransaction.setIsPaymentSuccess(false);
			paymentTransaction.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			paymentTransaction.setPayment_method(flightCustomerDetails.getPaymode());
			paymentTransaction.setApi_transaction_id(flightOrderRowonward.getOrderId());
			paymentTransaction.setPayment_status("Pending");

			PaymentTransaction SpecialpaymentTransaction = new PaymentTransaction();
			SpecialpaymentTransaction.setAmount(AmountRoundingModeUtil.roundingMode(flightPriceResponse.getSpecialFareFlightSegment().getPayableAmount().add(processingFees)));// in booking currency
			SpecialpaymentTransaction.setCurrency(flightPriceResponse.getFlightsearch().getBookedCurrency());
			SpecialpaymentTransaction.setRefno(flightOrderRowreturn.getOrderId());
			SpecialpaymentTransaction.setIsPaymentSuccess(false);
			SpecialpaymentTransaction.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			SpecialpaymentTransaction.setPayment_method(flightCustomerDetails.getPaymode());
			SpecialpaymentTransaction.setApi_transaction_id(flightOrderRowreturn.getOrderId());
			SpecialpaymentTransaction.setPayment_status("Pending");


			paymentTransaction.setPayment_status("Pending");
			if (result){
				//deduct the value from the DB
				for(int i=0;i<2;i++){

					try {
						//Bluestar booking call....
						if(i==0){
							if(orderId.startsWith("FBS")){
								BluestarConfig bluestarConfig = BluestarConfig.GetBluestarConfig(appKeyVo);
								flightBookingResponse= com.tayyarah.flight.util.api.bluestar.BluestarServiceCall.callBookingService(flightBookingResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails.getCountryCode(),flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,i,false,bluestarConfig);

								insertIntoFlightBook(appKeyVo,flightBookingResponse.getTransactionKey(),request);

							}else if(orderId.startsWith("FTAY")){
								//cal tqayyarah booking API
								flightBookingResponse= TayyarahServiceCall.callBookingService(flightBookingResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails,flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,i);
								insertIntoFlightBook(appKeyVo,flightBookingResponse.getTransactionKey(),request);

							}else if(orderId.startsWith("FLIN")){
								//cal tqayyarah booking API
								flightBookingResponse= LintasServiceCall.callBookingService(flightBookingResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails,flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,i);
								insertIntoFlightBook(appKeyVo,flightBookingResponse.getTransactionKey(),request);

							}else if(orderId.startsWith("FTBO")){
								TboFlightConfig tboconfig = TboFlightConfig.GetTboConfig(appKeyVo);
								//cal TBO API booking API
								TboFlightAirpriceResponse	TboPriceResponse = UapiServiceCall.getTboFlightPriceResponse(flightCustomerDetails.getPrice_key(),TempDAO,false);
								// LCC carrier Booking Request
								if(TboPriceResponse.getResponse().getResults().getIsLCC())
									flightBookingResponse= TboServiceCall.callLCCBookingService(flightBookingResponse,TboPriceResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails,flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,i,false,flightOrderRowonward,tboconfig);
								else
									flightBookingResponse= TboServiceCall.callBookingService(flightBookingResponse,TboPriceResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails,flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,i,false,flightOrderRowonward,tboconfig);

								insertIntoFlightBook(appKeyVo,flightBookingResponse.getTransactionKey(),request);
							}
							else{
								flightBookingResponse= UapiServiceCall.callBookingService(flightBookingResponse,TravelportConfig.GetTravelportConfig(), orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails.getCountryCode(),flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,i);
								insertIntoFlightBook(appKeyVo,flightBookingResponse.getTransactionKey(),request);
							}

							// Insurance for whole trip
							if(flightCustomerDetails.getIsInsuranceAvailable() && flightCustomerDetails.getPlanId()!=null && flightCustomerDetails.getPlanId() != -1)
								if(flightBookingResponse.isBookingStatus()){
									SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
									SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyyy");
									Date onwarddate = format.parse(flightPriceResponse.getFlightsearch().getDepDate());
									String departdate = format1.format(onwarddate);				

									//Date returndate = format.parse(flightOrderRow.getFlightOrderTripDetails().get(0).getArrDate());
									//String arridate = format1.format(returndate);
									SimpleDateFormat formatter1 = new SimpleDateFormat("dd MMM yyyy");
									Date returndate = formatter1.parse(flightOrderRowonward.getFlightOrderTripDetails().get(0).getArrDate());

									String arridate = format1.format(returndate);

									encryptions enc = new encryptions();
									CreateInsurancePolicyRequest createInsurancePolicyRequest = new CreateInsurancePolicyRequest();
									createInsurancePolicyRequest.setApp_key(appKeyVo.getAppKey());
									createInsurancePolicyRequest.setArrivalDate(arridate);
									createInsurancePolicyRequest.setDepartureDate(departdate);
									createInsurancePolicyRequest.setCompanyEntityId(flightCustomerDetails.getCompanyEntityId());
									createInsurancePolicyRequest.setCurrency(flightPriceResponse.getFlightsearch().getCurrency());
									createInsurancePolicyRequest.setDestination(flightPriceResponse.getFlightsearch().getOrigin());
									createInsurancePolicyRequest.setEmulateByCompanyId(flightCustomerDetails.getEmulateByCompanyId());
									createInsurancePolicyRequest.setEmulateByUserId(flightCustomerDetails.getEmulateByUserId());
									createInsurancePolicyRequest.setIsCompanyEntity(flightCustomerDetails.getIsCompanyEntity());
									createInsurancePolicyRequest.setIsEmulateFlag(flightCustomerDetails.isEmulateFlag());
									createInsurancePolicyRequest.setIsQuotation(flightCustomerDetails.getIsQuotation());
									createInsurancePolicyRequest.setIsRmDetails(flightCustomerDetails.getIsRmDetails());
									createInsurancePolicyRequest.setMarkupAmount("0");
									//int noofdays = InsuranceCommonUtil.getNoofStayDays(flightPriceResponse.getFlightsearch().getDepDate(), flightPriceResponse.getFlightsearch().getArvlDate());
									createInsurancePolicyRequest.setNoOfDays("1");
									createInsurancePolicyRequest.setOrigin(flightPriceResponse.getFlightsearch().getOrigin());
									createInsurancePolicyRequest.setPlanId( flightCustomerDetails.getPlanId());
									createInsurancePolicyRequest.setQuotationId(flightCustomerDetails.getQuotationid());
									//createInsurancePolicyRequest.setRmDataListDetails(flightCustomerDetails.getRmDataListDetails());
									createInsurancePolicyRequest.setUserId(flightCustomerDetails.getUserid());
									createInsurancePolicyRequest.setUserName(flightCustomerDetails.getUsername());
									List<TravellerDetails> travellerDetailsList = new ArrayList<>();
									int customerIndex = 0;
									for (PassengerDetails passengerDetails : flightCustomerDetails.getPassengerdetailsList()) {
										TravellerDetails travellerDetails = new TravellerDetails();	
										long customerId = flightOrderRowonward.getFlightOrderCustomers().get(customerIndex).getId();
										travellerDetails.setCustomerId(String.valueOf(customerId));
										//added by basha
										travellerDetails.setPaxId(passengerDetails.getPaxId());
										travellerDetails.setAddress(flightCustomerDetails.getAddress());
										travellerDetails.setAge(passengerDetails.getAge());
										travellerDetails.setCity(flightCustomerDetails.getCity());
										travellerDetails.setCountry(flightCustomerDetails.getCountryCode()!=null && flightCustomerDetails.getCountryCode().equalsIgnoreCase("91")?"india":flightCustomerDetails.getCountryCode());
										travellerDetails.setDateOfBirth(passengerDetails.getBirthday());
										travellerDetails.setDistrict(flightCustomerDetails.getDistrict());
										travellerDetails.setEmailAddress(flightCustomerDetails.getEmail());
										travellerDetails.setFirstName(passengerDetails.getFirstName());
										travellerDetails.setLastName(passengerDetails.getLastName());
										travellerDetails.setMobile(flightCustomerDetails.getMobile());
										travellerDetails.setNominee(passengerDetails.getIsSelfInsurance()!=null && passengerDetails.getIsSelfInsurance()?"self":passengerDetails.getNomineeName());
										travellerDetails.setPassportNumber(passengerDetails.getPassportNo());
										travellerDetails.setPinCode(flightCustomerDetails.getPincode());
										travellerDetails.setRelationShipWithNominee(passengerDetails.getIsSelfInsurance()!=null && passengerDetails.getIsSelfInsurance()?passengerDetails.getNomineeRelationShip():"");
										travellerDetails.setState(flightCustomerDetails.getState());
										travellerDetails.setTitle(passengerDetails.getTitle());
										travellerDetailsList.add(travellerDetails);
										customerIndex++;
									}
									createInsurancePolicyRequest.setTravellerDetails(travellerDetailsList);
									createInsurancePolicyRequest.setInsuredProduct("Flight");
									createInsurancePolicyRequest.setInsuredProductOrderRowId(flightOrderRowonward.getId());
									createInsurancePolicyRequest.setInsuredProductOrderId(flightOrderRowonward.getOrderId());
									PolicyResponseData policyResponseData = createPolicyController.createpolicy(createInsurancePolicyRequest, response, request);
									if(policyResponseData.getStatus().getErrorCode() == Status.SUCCESSCODE){
										flightOrderRowonward.setIsInsuranceAdded(true);
										flightOrderRowreturn.setIsInsuranceAdded(true);
										long insuranceOrderRowID = insuranceCommonDao.getInsuranceOrderRowIdByOrderId(policyResponseData.getOrderId());
										flightOrderRowonward.setInsuranceOrderRowId(insuranceOrderRowID);
										flightOrderRowreturn.setInsuranceOrderRowId(insuranceOrderRowID);
									}else{
										flightOrderRowonward.setIsInsuranceAdded(false);
										flightOrderRowreturn.setIsInsuranceAdded(false);
									}
								}



							// insert notication after booking is successful
							new NotificationUtil().insertNotification(appKeyVo,orderId , "Flight Ticket Booking", InventoryTypeEnum.FLIGHT_ORDER.getId(), true,notificationDao,companyDao);


						}else{
							if(orderIdSpecial.startsWith("FBS")){
								BluestarConfig bluestarConfig = BluestarConfig.GetBluestarConfig(appKeyVo);
								flightBookingResponse= com.tayyarah.flight.util.api.bluestar.BluestarServiceCall.callBookingService(flightBookingResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderIdSpecial,flightCustomerDetails.getCountryCode(),flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistorySpecial,i,true,bluestarConfig);
								insertIntoFlightBook(appKeyVo,flightBookingResponse.getTransactionKey(),request);
							}else if(orderIdSpecial.startsWith("FTAY")){
								//cal tqayyarah booking API
								flightBookingResponse= TayyarahServiceCall.callBookingService(flightBookingResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderIdSpecial,flightCustomerDetails,flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistorySpecial,i);
								insertIntoFlightBook(appKeyVo,flightBookingResponse.getTransactionKey(),request);
							}else if(orderIdSpecial.startsWith("FLIN")){
								//cal tqayyarah booking API
								flightBookingResponse= LintasServiceCall.callBookingService(flightBookingResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderIdSpecial,flightCustomerDetails,flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistorySpecial,i);
								insertIntoFlightBook(appKeyVo,flightBookingResponse.getTransactionKey(),request);
							}else if(orderIdSpecial.startsWith("FTBO")){

								TboFlightConfig tboconfig = TboFlightConfig.GetTboConfig(appKeyVo);
								//cal TBO API booking API
								TboFlightAirpriceResponse	TboPriceResponsespeical = UapiServiceCall.getTboFlightPriceResponse(flightCustomerDetails.getPrice_key(),TempDAO,true);
								// LCC carrier Booking Request

								if(TboPriceResponsespeical.getResponse().getResults().getIsLCC())
									flightBookingResponse= TboServiceCall.callLCCBookingService(flightBookingResponse,TboPriceResponsespeical,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderIdSpecial,flightCustomerDetails,flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,i,true,flightOrderRowreturn,tboconfig);
								else
									flightBookingResponse= TboServiceCall.callBookingService(flightBookingResponse,TboPriceResponsespeical,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderIdSpecial,flightCustomerDetails,flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,i,true,flightOrderRowreturn,tboconfig);

								insertIntoFlightBook(appKeyVo,flightBookingResponse.getTransactionKey(),request);
							}else{
								flightBookingResponse= UapiServiceCall.callBookingService(flightBookingResponse,TravelportConfig.GetTravelportConfig(), orderCustomer,  flightPriceResponse,flightOrderCustomers,orderIdSpecial,flightCustomerDetails.getCountryCode(),flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistorySpecial,i);
								insertIntoFlightBook(appKeyVo,flightBookingResponse.getTransactionKey(),request);
							}




							// insert notication after booking is successful
							new NotificationUtil().insertNotification(appKeyVo,orderIdSpecial , "Flight Ticket Booking", InventoryTypeEnum.FLIGHT_ORDER.getId(), true,notificationDao,companyDao);

							// Update orderCustomer
							OrderCustomer orderCustomeronward  = flightOrderRowonward.getCustomer();
							orderCustomeronward.setOrderId(flightOrderRowonward.getOrderId());		
							try {
								flightBookingDao.updateOrderCustomerDetails(orderCustomeronward);
							} catch (Exception e1) {
								logger.error("Exception", e1);
								throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
							}

							OrderCustomer orderCustomerreturn  = flightOrderRowreturn.getCustomer();
							orderCustomerreturn.setOrderId(orderCustomerreturn.getOrderId());		
							try {
								flightBookingDao.updateOrderCustomerDetails(orderCustomerreturn);
							} catch (Exception e1) {
								logger.error("Exception", e1);
								throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
							}
							// Update flightorderrow
							flightOrderRowonward.setCustomer(orderCustomeronward);
							try {
								flightBookingDao.updateFlightOrderRowDetails(flightOrderRowonward);
							} catch (Exception e) {
								logger.error("Exception", e);
								throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
							}
							flightOrderRowreturn.setCustomer(orderCustomerreturn);
							try {
								flightBookingDao.updateFlightOrderRowDetails(flightOrderRowreturn);
							} catch (Exception e) {
								logger.error("Exception", e);
								throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
							}


						}
					}catch(ClassNotFoundException e){
						logger.error("ClassNotFoundException", e);
						walletAmountTranferHistory.setRemarks("Flight Booking Failed");
						walletAmountTranferHistorySpecial.setRemarks("Flight Booking Failed");
						//DBS.updateWalletBalanceIfFailed(walletAmountTranferHistory.getAmount(),walletAmountTranferHistory.getWalletId(), flightBookingDao,walletAmountTranferHistory);
						//DBS.updateWalletBalanceIfFailed(walletAmountTranferHistorySpecial.getAmount(),walletAmountTranferHistorySpecial.getWalletId(), flightBookingDao,walletAmountTranferHistorySpecial);
						try {

							if(userListBottomToTop!=null && userListBottomToTop.size()>0)
							{
								for(User userInner : userListBottomToTop)
								{
									if(userInner.getAgentWallet()!=null)
									{
										if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
										{
											BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
											userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
										}
										else{
											result = false;
										}
										if(cutAndPayUserMapSpecial!=null && cutAndPayUserMapSpecial.get(userInner.getId())!=null)
										{
											BigDecimal totalPayableAmount = cutAndPayUserMapSpecial.get(userInner.getId()).getPayableAmount();
											userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
										}
										else{
											result = false;
										}
									}
								}
							}						
							userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(flightOrderRowonward.getOrderId(), flightBookingResponse.getInvoiceNumber());
							userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(flightOrderRowreturn.getOrderId(), flightBookingResponse.getInvoiceNumberSpecial());


							//userWalletDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()).divide(baseToBookingExchangeRate,2,RoundingMode.UP),walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
							//userWalletDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice()).divide(baseToBookingExchangeRate,2,RoundingMode.UP),walletAmountTranferHistorySpecial,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						DBS.updatePNR("0", orderIdSpecial, flightBookingDao);
						DBS.updatePNR("0", orderId, flightBookingDao);

						throw new FlightException(ErrorCodeCustomerEnum.ClassNotFoundException,ErrorMessages.BOOKING_FAILED);
					}
					catch(SOAPException e){
						logger.error("SOAPException", e);
						walletAmountTranferHistory.setRemarks("Flight Booking Failed");
						walletAmountTranferHistorySpecial.setRemarks("Flight Booking Failed");
						try {

							if(userListBottomToTop!=null && userListBottomToTop.size()>0)
							{
								for(User userInner : userListBottomToTop)
								{
									if(userInner.getAgentWallet()!=null)
									{
										if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
										{
											BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
											userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
										}
										else{
											result = false;
										}
										if(cutAndPayUserMapSpecial!=null && cutAndPayUserMapSpecial.get(userInner.getId())!=null)
										{
											BigDecimal totalPayableAmount = cutAndPayUserMapSpecial.get(userInner.getId()).getPayableAmount();
											userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistorySpecial,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
										}
										else{
											result = false;
										}
									}
								}
							}		
							userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(flightOrderRowonward.getOrderId(), flightBookingResponse.getInvoiceNumber());
							userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(flightOrderRowreturn.getOrderId(), flightBookingResponse.getInvoiceNumberSpecial());

							//userWalletDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()).divide(baseToBookingExchangeRate,2,RoundingMode.UP),walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
							//userWalletDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice()).divide(baseToBookingExchangeRate,2,RoundingMode.UP),walletAmountTranferHistorySpecial,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						//DBS.updateWalletBalanceIfFailed(walletAmountTranferHistory.getAmount(),walletAmountTranferHistory.getWalletId(), flightBookingDao,walletAmountTranferHistory);
						DBS.updatePNR("0", orderId, flightBookingDao);
						//DBS.updateWalletBalanceIfFailed(walletAmountTranferHistorySpecial.getAmount(),walletAmountTranferHistorySpecial.getWalletId(), flightBookingDao,walletAmountTranferHistorySpecial);
						DBS.updatePNR("0", orderIdSpecial, flightBookingDao);

						throw new FlightException(ErrorCodeCustomerEnum.SOAPException, ErrorMessages.BOOKING_FAILED);
					}
					catch(JAXBException e){
						logger.error("JAXBException", e);
						walletAmountTranferHistory.setRemarks("Flight Booking Failed");
						walletAmountTranferHistorySpecial.setRemarks("Flight Booking Failed");
						try {
							if(userListBottomToTop!=null && userListBottomToTop.size()>0)
							{
								for(User userInner : userListBottomToTop)
								{
									if(userInner.getAgentWallet()!=null)
									{
										if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
										{
											BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
											userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
										}
										else{
											result = false;
										}
										if(cutAndPayUserMapSpecial!=null && cutAndPayUserMapSpecial.get(userInner.getId())!=null)
										{
											BigDecimal totalPayableAmount = cutAndPayUserMapSpecial.get(userInner.getId()).getPayableAmount();
											userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistorySpecial,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
										}
										else{
											result = false;
										}
									}
								}
							}	
							userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(flightOrderRowonward.getOrderId(), flightBookingResponse.getInvoiceNumber());
							userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(flightOrderRowreturn.getOrderId(), flightBookingResponse.getInvoiceNumberSpecial());


							//userWalletDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()).divide(baseToBookingExchangeRate,2,RoundingMode.UP),walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
							//userWalletDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice()).divide(baseToBookingExchangeRate,2,RoundingMode.UP),walletAmountTranferHistorySpecial,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						//DBS.updateWalletBalanceIfFailed(walletAmountTranferHistory.getAmount(),walletAmountTranferHistory.getWalletId(), flightBookingDao,walletAmountTranferHistory);
						DBS.updatePNR("0", orderId, flightBookingDao);
						//DBS.updateWalletBalanceIfFailed(walletAmountTranferHistorySpecial.getAmount(),walletAmountTranferHistorySpecial.getWalletId(), flightBookingDao,walletAmountTranferHistorySpecial);
						DBS.updatePNR("0", orderIdSpecial, flightBookingDao);

						throw new FlightException(ErrorCodeCustomerEnum.JAXBException,ErrorMessages.BOOKING_FAILED);
					}catch(Exception e){
						walletAmountTranferHistory.setRemarks("Flight Booking Failed");
						walletAmountTranferHistorySpecial.setRemarks("Flight Booking Failed");
						try {

							if(userListBottomToTop!=null && userListBottomToTop.size()>0)
							{
								for(User userInner : userListBottomToTop)
								{
									if(userInner.getAgentWallet()!=null)
									{
										if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
										{
											BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
											userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
										}
										else{
											result = false;
										}
										if(cutAndPayUserMapSpecial!=null && cutAndPayUserMapSpecial.get(userInner.getId())!=null)
										{
											BigDecimal totalPayableAmount = cutAndPayUserMapSpecial.get(userInner.getId()).getPayableAmount();
											userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistorySpecial,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP), new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
										}
										else{
											result = false;
										}
									}
								}
							}		
							userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(flightOrderRowonward.getOrderId(), flightBookingResponse.getInvoiceNumber());
							userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(flightOrderRowreturn.getOrderId(), flightBookingResponse.getInvoiceNumberSpecial());
							//userWalletDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()).divide(baseToBookingExchangeRate,2,RoundingMode.UP),walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
							//userWalletDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice()).divide(baseToBookingExchangeRate,2,RoundingMode.UP),walletAmountTranferHistorySpecial,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						//DBS.updateWalletBalanceIfFailed(walletAmountTranferHistory.getAmount(),walletAmountTranferHistory.getWalletId(), flightBookingDao,walletAmountTranferHistory);
						DBS.updatePNR("0", orderId, flightBookingDao);
						//DBS.updateWalletBalanceIfFailed(walletAmountTranferHistorySpecial.getAmount(),walletAmountTranferHistorySpecial.getWalletId(), flightBookingDao,walletAmountTranferHistorySpecial);
						DBS.updatePNR("0", orderIdSpecial, flightBookingDao);

						logger.error("Exception", e);
						throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
					}


					paymentTransaction.setIsPaymentSuccess(true);
					paymentTransaction.setTransactionId(flightCustomerDetails.getTransactionkey());
					paymentTransaction.setResponse_message("NA");
					paymentTransaction.setResponseCode("NA");
					paymentTransaction.setPayment_status("SUCCESS");
					paymentTransaction.setAuthorizationCode(flightCustomerDetails.getTransactionkey());
					try {
						newpgid = callPayment(paymentTransaction, flightBookingDao,0,"");
					} catch (Exception e) {
						logger.error("Exception", e);
						throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
					}

					SpecialpaymentTransaction.setIsPaymentSuccess(true);
					SpecialpaymentTransaction.setTransactionId(flightCustomerDetails.getTransactionkey());
					SpecialpaymentTransaction.setResponse_message("NA");
					SpecialpaymentTransaction.setResponseCode("NA");
					SpecialpaymentTransaction.setPayment_status("SUCCESS");
					SpecialpaymentTransaction.setAuthorizationCode(flightCustomerDetails.getTransactionkey());
					try {
						callPayment(SpecialpaymentTransaction, flightBookingDao,0,"");
					} catch (Exception e) {
						logger.error("Exception", e);
						throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
					}

					flightBookingResponse.setPaymentStatus(true);


					////////////////email//
					TravelportConfig travelportConfig=TravelportConfig.GetTravelportConfig();
					CommonConfig commonConfig=CommonConfig.GetCommonConfig();
					if(i==0&&flightBookingResponse.isBookingStatus()&&orderId.startsWith("FTP")&&commonConfig.isIs_lintas_enabled()&&!travelportConfig.isTest()){
						sendPNRViaMAil(checkGetEmulatedUserById(flightCustomerDetails),flightBookingResponse.getPnr(), request, response);// logger.info("---------emailStatusIds SIZE--------"+emailStatusIds.size());
					}
					else if(i!=0&&flightBookingResponse.isBookingStatusSpecial()&&orderId.startsWith("FTP")&&commonConfig.isIs_lintas_enabled()&&!travelportConfig.isTest()){
						sendPNRViaMAil(checkGetEmulatedUserById(flightCustomerDetails),flightBookingResponse.getPnrSpecial(), request, response);
					}
				}
			}else{//insufficient wallet balance
				for(int i=0;i<2;i++){
					flightBookingResponse=new FlightBookingResponse();
					logger.info("insufficient wallet balance : ");
					flightBookingResponse.setPnr("NA");
					flightBookingResponse.setBokingConditions("Top up ur wallet");
					flightBookingResponse.setBookingComments("Unable to process your booking due to insufficient balance in the account");
					flightBookingResponse.setBookingStatus(false);
					if(i==0){
						DBS.updatePNR("#0", orderId, flightBookingDao);   }else{
							flightBookingResponse.setPnrSpecial("NA");
							flightBookingResponse.setBokingConditionsSpecial("Top up ur wallet");
							flightBookingResponse.setBookingCommentsSpecial("Unable to process your booking due to insufficient balance in the account");
							flightBookingResponse.setBookingStatusSpecial(false);
							DBS.updatePNR("#0", orderIdSpecial, flightBookingDao);
						}
				}

			}
			flightBookingResponse.setPaymentStatus(true);
		}else{
			for(int i=0;i<2;i++){
				FareFlightSegment fareflightsegment = null;

				if(i == 0)
					fareflightsegment = flightPriceResponse.getFareFlightSegment();
				else
					fareflightsegment = flightPriceResponse.getSpecialFareFlightSegment();

				PaymentTransaction paymentTransaction=new PaymentTransaction();
				paymentTransaction.setAmount(AmountRoundingModeUtil.roundingMode(new BigDecimal(fareflightsegment.getTotalPrice())));// in booking currency
				paymentTransaction.setCurrency(flightPriceResponse.getFlightsearch().getBookedCurrency());

				paymentTransaction.setIsPaymentSuccess(false);
				paymentTransaction.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
				paymentTransaction.setPayment_method(flightCustomerDetails.getPaymode());

				paymentTransaction.setPayment_status("Pending");

				flightBookingResponse=new FlightBookingResponse();
				flightBookingResponse.setPnr("0#");
				flightBookingResponse.setBokingConditions("payment gateway has to be called");
				flightBookingResponse.setBookingComments("payment");
				flightBookingResponse.setBookingStatus(false);

				if(i==0){
					paymentTransaction.setApi_transaction_id(orderId);
					paymentTransaction.setRefno(orderId);
					storeBookingDetails(orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails);
					DBS.updatePNR("0#", orderId, flightBookingDao);				

				}else{
					flightBookingResponse.setPnrSpecial("0#");
					flightBookingResponse.setBokingConditionsSpecial("payment gateway has to be called");
					flightBookingResponse.setBookingCommentsSpecial("payment");
					flightBookingResponse.setBookingStatusSpecial(false);
					paymentTransaction.setApi_transaction_id(orderIdSpecial);
					paymentTransaction.setRefno(orderIdSpecial);
					storeBookingDetails(orderCustomer,  flightPriceResponse,flightOrderCustomers,orderIdSpecial,flightCustomerDetails);
					DBS.updatePNR("0#", orderIdSpecial, flightBookingDao);
				}
				try {
					newpgid= callPayment(paymentTransaction, flightBookingDao,i,newpgid);
				} catch (Exception e) {
					logger.error("Exception", e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.DB_ERROR);
				}
			}
		}



		if(flightCustomerDetails.getIsQuotation().equalsIgnoreCase("true")){
			flightTravelRequestDao.flightRequestQuotationUpdate(Long.parseLong(flightCustomerDetails.getQuotationid()),flightOrderRowonward,flightOrderRowreturn);
			ApiProviderPaymentTransaction apiProviderPaymentTransaction = new ApiProviderPaymentTransaction();
			apiProviderPaymentTransaction.setAmount(new BigDecimal(flightPriceResponse.getFareFlightSegment().getApi_totalPriceWithoutMarkup()));
			apiProviderPaymentTransaction.setApi_transaction_id(flightOrderRowonward.getOrderId());
			apiProviderPaymentTransaction.setCurrency(flightOrderRowonward.getApiCurrency());
			apiProviderPaymentTransaction.setCreatedAt(flightOrderRowonward.getCreatedAt());
			apiProviderPaymentTransaction.setPayment_system("Full");
			apiProviderPaymentTransaction.setPayment_status(flightOrderRowonward.getPaymentStatus());
			apiProviderPaymentTransaction.setIsPaymentSuccess(true);
			flightTravelRequestDao.insertSupplierPaymentTransactionInfo(apiProviderPaymentTransaction);

			ApiProviderPaymentTransaction specialApiProviderPaymentTransaction = new ApiProviderPaymentTransaction();
			specialApiProviderPaymentTransaction.setAmount(new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getApi_totalPriceWithoutMarkup()));
			specialApiProviderPaymentTransaction.setApi_transaction_id(flightOrderRowreturn.getOrderId());
			specialApiProviderPaymentTransaction.setCurrency(flightOrderRowreturn.getApiCurrency());
			specialApiProviderPaymentTransaction.setCreatedAt(flightOrderRowreturn.getCreatedAt());
			specialApiProviderPaymentTransaction.setPayment_system("Full");
			specialApiProviderPaymentTransaction.setPayment_status(flightOrderRowreturn.getPaymentStatus());
			specialApiProviderPaymentTransaction.setIsPaymentSuccess(true);
			flightTravelRequestDao.insertSupplierPaymentTransactionInfo(specialApiProviderPaymentTransaction);
		}

		/*if(flightCustomerDetails.getIsRmDetails()){
			for (RmConfigTripDetailsModel rmConfigTripDetailsModel : flightCustomerDetails.getRmDataListDetails()) {
				rmConfigTripDetailsModel.setOrdertype("Flight");
				rmConfigTripDetailsModel.setOrderId(flightOrderRowonward.getOrderId()+","+flightOrderRowreturn.getOrderId());
				try{
					companyDao.insertRMConfigTripDetails(rmConfigTripDetailsModel);
				}catch(Exception e){

				}
			}
		}		
*/
		// After booking Successful we are inserting Email 
		if(flightOrderRowonward != null && flightOrderRowonward.getStatusAction().equalsIgnoreCase("Confirmed"))
			TboCommonUtil.updateMailstatus(flightOrderRowonward.getOrderId(),emaildao);
		if(flightOrderRowreturn != null && flightOrderRowreturn.getStatusAction().equalsIgnoreCase("Confirmed"))
			TboCommonUtil.updateMailstatus(flightOrderRowreturn.getOrderId(),emaildao);

		flightBookingResponse.setFareFlightSegment(flightPriceResponse.getFareFlightSegment());
		flightBookingResponse.setPassengerFareBreakUps(flightPriceResponse.getPassengerFareBreakUps());
		flightBookingResponse.setTransactionKey(flightCustomerDetails.getTransactionkey());
		flightBookingResponse.setFlightsearch(flightPriceResponse.getFlightsearch());
		flightBookingResponse.setCountry(flightCustomerDetails.getCountryId());
		flightBookingResponse.setCache(false);
		flightBookingResponse.setConfirmationNumber(orderId);


		//For spectial roundtrip
		flightBookingResponse.setFareFlightSegmentSpecial(flightPriceResponse.getSpecialFareFlightSegment());
		flightBookingResponse.setPassengerFareBreakUpsSpecial(flightPriceResponse.getSpecialPassengerFareBreakUps());
		flightBookingResponse.setConfirmationNumberSpecial(orderIdSpecial);
		flightBookingResponse.setLastTicketingDateSpecial(flightPriceResponse.getSpecialFareFlightSegment().getLatestTicketingTime());
		flightBookingResponse.setGSTonMarkupSpecial(flightPriceResponse.getGSTonMarkupSpecial());

		flightBookingResponse.setPgID(newpgid);
		flightBookingResponse.setNewpgID(newpgid);
		flightBookingResponse.setPaymentCalledCount(0);


		////////*** newly added on 10-1-2016
		flightBookingResponse.setLastTicketingDate(flightPriceResponse.getFareFlightSegment().getLatestTicketingTime());

		///////////** GST added on 19-1-16
		///////////** GST added on 19-1-16
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

		flightBookingResponse.setGSTonMarkupSpecial(flightPriceResponse.getGSTonMarkupSpecial());

		flightBookingResponse.setProcessingfee(processingFees);
		flightBookingResponse.setFinalPriceWithGST(AmountRoundingModeUtil.roundingMode(flightPriceResponse.getFinalPriceWithGST().add(processingFees)));


		if(flightCustomerDetails.getIsInsuranceAvailable() && flightCustomerDetails.getPlanId()!=null && flightCustomerDetails.getPlanId() != -1){
			BigDecimal insuranceTotalAmount = new BigDecimal(0);
			for (PassengerDetails passengerDetails : flightCustomerDetails.getPassengerdetailsList()) {
				TrawellTagPremiumChart trawellTagPremiumChart = insuranceCommonDao.getTrawellTagPremiumChart("1", passengerDetails.getAge(),flightCustomerDetails.getPlanId());
				insuranceTotalAmount = insuranceTotalAmount.add(trawellTagPremiumChart.getPremiumAmount());
				insuranceTotalAmount = insuranceTotalAmount.setScale(0, RoundingMode.UP);
			}
			BigDecimal finalPrice = flightBookingResponse.getFinalPriceWithGST().add(insuranceTotalAmount).setScale(2, RoundingMode.UP);
			flightBookingResponse.setFinalPriceWithGST(finalPrice);
			flightBookingResponse.getFareFlightSegment().setTotalPrice(finalPrice.toString());
		}



		flightBookingResponse.setFlightCustomerDetails(flightCustomerDetails);

		try{
			userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(flightOrderRowonward.getOrderId(), flightBookingResponse.getInvoiceNumber());
			userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(flightOrderRowreturn.getOrderId(), flightBookingResponse.getInvoiceNumberSpecial());
		}catch(Exception e){
			logger.error("walletTransferHistoryUpdateWithInvoiceNo Exception", e);
		}



		if(!isLowFare){
			// Set Flight Fare Alert Detail for First Recommended  Flight
			Map<FlightFareAlertDetail, List<Segments>> flightFareAlertDetailMap = getFlightFareAlertDetailOneway(flightCustomerDetails, lowFareFlightIndex1, flightBookingResponse, flightOrderRowonward,reasonToSelect);
			if(flightFareAlertDetailMap.size() > 0){
				for (FlightFareAlertDetail flightFareAlertDetail : flightFareAlertDetailMap.keySet()) {				   
					FlightFareAlertDetail flightFareAlertDetailupdated = flightBookingDao.insertLowFareFlightDetail(flightFareAlertDetail); 
					if(flightFareAlertDetailupdated.getIsConnFlightAvailable()){
						List<Segments> Segments =  flightFareAlertDetailMap.get(flightFareAlertDetail);
						List<FlightFareAlertConnectingFlight> flightFareAlertConnectingFlightList =  getFlightFareAlertConnectingFlight(flightFareAlertDetailupdated, Segments);
						List<FlightFareAlertConnectingFlight> flightFareAlertConnectingFlightListUpdated = new ArrayList<>();
						for (FlightFareAlertConnectingFlight flightFareAlertConnectingFlight : flightFareAlertConnectingFlightList) {
							FlightFareAlertConnectingFlight flightFareAlertConnectingFlightupdated = flightBookingDao.insertFlightFareAlertConnectingFlight(flightFareAlertConnectingFlight);
							flightFareAlertConnectingFlightListUpdated.add(flightFareAlertConnectingFlightupdated);
						}
						flightFareAlertDetailupdated.setFlightFareAlertConnectingFlightList(flightFareAlertConnectingFlightListUpdated);
						flightBookingDao.updateLowFareFlightDetail(flightFareAlertDetailupdated); 
					}
				}

			}
			if(!lowFareFlightIndex2.equalsIgnoreCase("")){
				Map<FlightFareAlertDetail, List<Segments>> flightFareAlertDetailMapSecond = getFlightFareAlertDetailOneway(flightCustomerDetails, lowFareFlightIndexReturn1, flightBookingResponse, flightOrderRowonward,reasonToSelect);
				if(flightFareAlertDetailMapSecond.size() > 0){
					for (FlightFareAlertDetail flightFareAlertDetail : flightFareAlertDetailMapSecond.keySet()) {				   
						FlightFareAlertDetail flightFareAlertDetailupdated = flightBookingDao.insertLowFareFlightDetail(flightFareAlertDetail); 
						if(flightFareAlertDetailupdated.getIsConnFlightAvailable()){
							List<Segments> Segments =  flightFareAlertDetailMapSecond.get(flightFareAlertDetail);
							List<FlightFareAlertConnectingFlight> flightFareAlertConnectingFlightList =  getFlightFareAlertConnectingFlight(flightFareAlertDetailupdated, Segments);
							List<FlightFareAlertConnectingFlight> flightFareAlertConnectingFlightListUpdated = new ArrayList<>();
							for (FlightFareAlertConnectingFlight flightFareAlertConnectingFlight : flightFareAlertConnectingFlightList) {
								FlightFareAlertConnectingFlight flightFareAlertConnectingFlightupdated = flightBookingDao.insertFlightFareAlertConnectingFlight(flightFareAlertConnectingFlight);
								flightFareAlertConnectingFlightListUpdated.add(flightFareAlertConnectingFlightupdated);
							}
							flightFareAlertDetailupdated.setFlightFareAlertConnectingFlightList(flightFareAlertConnectingFlightListUpdated);
							flightBookingDao.updateLowFareFlightDetail(flightFareAlertDetailupdated); 
						}
					}

				}
			}

		}

		if(!isLowFareReturn){
			// Set Flight Fare Alert Detail for First Recommended  Flight
			Map<FlightFareAlertDetail, List<Segments>> flightFareAlertDetailMap = getFlightFareAlertDetailOneway(flightCustomerDetails, lowFareFlightIndexReturn1, flightBookingResponse, flightOrderRowreturn,reasonToSelectReturn);
			if(flightFareAlertDetailMap.size() > 0){
				for (FlightFareAlertDetail flightFareAlertDetail : flightFareAlertDetailMap.keySet()) {				   
					FlightFareAlertDetail flightFareAlertDetailupdated = flightBookingDao.insertLowFareFlightDetail(flightFareAlertDetail); 
					if(flightFareAlertDetailupdated.getIsConnFlightAvailable()){
						List<Segments> Segments =  flightFareAlertDetailMap.get(flightFareAlertDetail);
						List<FlightFareAlertConnectingFlight> flightFareAlertConnectingFlightList =  getFlightFareAlertConnectingFlight(flightFareAlertDetailupdated, Segments);
						List<FlightFareAlertConnectingFlight> flightFareAlertConnectingFlightListUpdated = new ArrayList<>();
						for (FlightFareAlertConnectingFlight flightFareAlertConnectingFlight : flightFareAlertConnectingFlightList) {
							FlightFareAlertConnectingFlight flightFareAlertConnectingFlightupdated = flightBookingDao.insertFlightFareAlertConnectingFlight(flightFareAlertConnectingFlight);
							flightFareAlertConnectingFlightListUpdated.add(flightFareAlertConnectingFlightupdated);
						}
						flightFareAlertDetailupdated.setFlightFareAlertConnectingFlightList(flightFareAlertConnectingFlightListUpdated);
						flightBookingDao.updateLowFareFlightDetail(flightFareAlertDetailupdated); 
					}
				}

			}
			if(!lowFareFlightIndexReturn2.equalsIgnoreCase("")){
				Map<FlightFareAlertDetail, List<Segments>> flightFareAlertDetailMapSecond = getFlightFareAlertDetailOneway(flightCustomerDetails, lowFareFlightIndexReturn2, flightBookingResponse, flightOrderRowreturn,reasonToSelectReturn);
				if(flightFareAlertDetailMapSecond.size() > 0){
					for (FlightFareAlertDetail flightFareAlertDetail : flightFareAlertDetailMapSecond.keySet()) {				   
						FlightFareAlertDetail flightFareAlertDetailupdated = flightBookingDao.insertLowFareFlightDetail(flightFareAlertDetail); 
						if(flightFareAlertDetailupdated.getIsConnFlightAvailable()){
							List<Segments> Segments =  flightFareAlertDetailMapSecond.get(flightFareAlertDetail);
							List<FlightFareAlertConnectingFlight> flightFareAlertConnectingFlightList =  getFlightFareAlertConnectingFlight(flightFareAlertDetailupdated, Segments);
							List<FlightFareAlertConnectingFlight> flightFareAlertConnectingFlightListUpdated = new ArrayList<>();
							for (FlightFareAlertConnectingFlight flightFareAlertConnectingFlight : flightFareAlertConnectingFlightList) {
								FlightFareAlertConnectingFlight flightFareAlertConnectingFlightupdated = flightBookingDao.insertFlightFareAlertConnectingFlight(flightFareAlertConnectingFlight);
								flightFareAlertConnectingFlightListUpdated.add(flightFareAlertConnectingFlightupdated);
							}
							flightFareAlertDetailupdated.setFlightFareAlertConnectingFlightList(flightFareAlertConnectingFlightListUpdated);
							flightBookingDao.updateLowFareFlightDetail(flightFareAlertDetailupdated); 
						}
					}

				}
			}

		}	

		return flightBookingResponse;
	}


	public FlightBookingResponse createFlightOrderCustomerData(FlightCustomerDetails flightCustomerDetails,FlightTempAirSegmentDAO TempDAO,FlightPriceResponse flightPriceResponse,HttpServletResponse response,HttpServletRequest request, CompanyDao companyDAO2,boolean isLowFare,String lowFareFlightIndex1,String lowFareFlightIndex2,String reasons,boolean isLowFareReturn,String lowFareFlightIndexReturn1,String lowFareFlightIndexReturn2,String reasonsReturn, AppKeyVo appKeyVo) 
	//public FlightBookingResponse createFlightOrderCustomerData(FlightCustomerDetails flightCustomerDetails,FlightTempAirSegmentDAO TempDAO,FlightPriceResponse flightPriceResponse,HttpServletResponse response,HttpServletRequest request, CompanyDao companyDAO2)

	{
		List<PassengerDetails> passengerdetailsList=flightCustomerDetails.getPassengerdetailsList();
		logger.info("createFOC method called : ");

		//String decapp_key = AppControllerUtil.getDecryptedAppKey(CDAO,appKeyVo);

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
		orderCustomer.setCompanyId(appKeyVo.getCompanyId());
		orderCustomer.setConfigId(appKeyVo.getConfigId());
		orderCustomer.setBookingType("Flight");
		orderCustomer.setCreatedByUserId(Integer.parseInt(checkGetEmulatedUserById(flightCustomerDetails)) );


		FrontUserDetail frontUserDetail =  GetFrontUserDetail.getFrontUserDetailDetails(orderCustomer,frontUserDao);
		if(frontUserDetail != null){
			try{
				frontUserDetail = frontUserDao.insertFrontUserDetail(frontUserDetail);
			}catch(Exception e){
				logger.error("Exception", e);
			}

			if(frontUserDetail.getId() != null && frontUserDetail.getId() != 0){
				emaildao.insertEmail(String.valueOf(frontUserDetail.getId()), 0, Email.EMAIL_TYPE_FRONT_USER_REGISTRATION_BY_TAYYARAH);
			}
		}

		try {
			flightBookingDao.insertOrderCustomerDetails(orderCustomer);
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
		if(flightPriceResponse.getFlightsearch().getTripType().equalsIgnoreCase("SR") && flightPriceResponse.getFlightsearch().isSpecialSearch())
			flightOrderRow.setTripType("SRR");
		else
			flightOrderRow.setTripType(flightPriceResponse.getFlightsearch().getTripType());

		flightOrderRow.setPaymentStatus("pending");
		flightOrderRow.setPaidBy(flightCustomerDetails.getPaymode());
		flightOrderRow.setStatusAction("Initiated");
		flightOrderRow.setVersion(VERSION);
		flightOrderRow.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		flightOrderRow.setOrderId(orderId);
		flightOrderRow.setTransaction_key(flightCustomerDetails.getTransactionkey());
		flightOrderRow.setConfigId(String.valueOf(appKeyVo.getConfigId()));
		flightOrderRow.setCompanyId(String.valueOf(appKeyVo.getCompanyId()));
		flightOrderRow.setCreatedBy(flightCustomerDetails.getUsername());//
		flightOrderRow.setUserId(checkGetEmulatedUserById(flightCustomerDetails));


		MarkupCommissionDetails markupCommissionDetails=flightPriceResponse.getMarkupCommissionDetails();
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
			companyConfig = companyConfigDAO.getCompanyConfigByConfigId(configId);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		Company company = null;
		try {
			company = companyDao.getCompany(Integer.parseInt(flightOrderRow.getCompanyId()));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		Company parentCompany = null;
		try{
			parentCompany = companyDao.getParentCompany(company);
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("################## flight booking--createFlightOrderCustomerData-------");

		if(companyConfig != null )
		{
			if(companyConfig.getCompanyConfigType().isB2E()){
				if(companyConfig.getTaxtype()!= null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){

					if(flightPriceResponse.getFlightsearch().isDomestic())
					{
						FlightOrderRowGstTax flightOrderRowGstTax = new FlightOrderRowGstTax();
						FlightDomesticGstTaxConfig flightDomesticGstTaxConfig = companyConfig.getFlightDomesticGstTaxConfig();
						flightOrderRowGstTax = createFlightOrderRowGstTaxDomestic(flightDomesticGstTaxConfig,flightOrderRowGstTax,company,parentCompany,flightPriceResponse.getFlightsearch());
						flightOrderRow.setFlightOrderRowGstTax(flightOrderRowGstTax);
					}
					if(flightPriceResponse.getFlightsearch().isIsInternational())
					{
						FlightOrderRowGstTax flightOrderRowGstTax = new FlightOrderRowGstTax();
						FlightInternationalGstTaxConfig flightInternationalGstTaxConfig = companyConfig.getFlightInternationalGstTaxConfig();
						flightOrderRowGstTax = createFlightOrderRowGstTaxInternational(flightInternationalGstTaxConfig,flightOrderRowGstTax,company,parentCompany,flightPriceResponse.getFlightsearch());
						flightOrderRow.setFlightOrderRowGstTax(flightOrderRowGstTax);
					}
					flightOrderRow.setFlightOrderRowServiceTax(null);
				}else{
					if(flightPriceResponse.getFlightsearch().isDomestic())
					{
						FlightOrderRowServiceTax flightOrderRowServiceTax = new FlightOrderRowServiceTax();
						FlightDomesticServiceTaxConfig flightDomesticServiceTaxConfig = companyConfig.getFlightDomesticServiceTaxConfig();
						flightOrderRowServiceTax= createFlightOrderRowServiceTaxDomestic(flightDomesticServiceTaxConfig,flightOrderRowServiceTax);
						flightOrderRow.setFlightOrderRowServiceTax(flightOrderRowServiceTax);
					}
					if(flightPriceResponse.getFlightsearch().isIsInternational())
					{
						FlightOrderRowServiceTax flightOrderRowServiceTax = new FlightOrderRowServiceTax();
						FlightInternationalServiceTaxConfig flightInternationalServiceTaxConfig = companyConfig.getFlightInternationalServiceTaxConfig();
						flightOrderRowServiceTax= createFlightOrderRowServiceTaxInternational(flightInternationalServiceTaxConfig,flightOrderRowServiceTax);
						flightOrderRow.setFlightOrderRowServiceTax(flightOrderRowServiceTax);
					}
					flightOrderRow.setFlightOrderRowGstTax(null);
				}
			}
			else{
				flightOrderRow.setFlightOrderRowServiceTax(null);
				flightOrderRow.setFlightOrderRowGstTax(null);
			}
		}

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

		BigDecimal finalprice = new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice());
		if(flightPriceResponse.getFareFlightSegment().getFlightServiceTax()!=null){
			//	finalprice =  new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()).subtract(flightPriceResponse.getFareFlightSegment().getFlightServiceTax().getTotalServiceTax()).subtract(flightPriceResponse.getFareFlightSegment().getFlightServiceTax().getManagementFee());
			finalprice =  new BigDecimal(flightPriceResponse.getFareFlightSegment().getBasePrice()).add(new BigDecimal(flightPriceResponse.getFareFlightSegment().getTaxes()));

		}
		if(flightPriceResponse.getFareFlightSegment().getFlightGstTax()!=null){
			//	finalprice =  new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()).subtract(flightPriceResponse.getFareFlightSegment().getFlightServiceTax().getTotalServiceTax()).subtract(flightPriceResponse.getFareFlightSegment().getFlightServiceTax().getManagementFee());
			finalprice =  new BigDecimal(flightPriceResponse.getFareFlightSegment().getBasePrice()).add(new BigDecimal(flightPriceResponse.getFareFlightSegment().getTaxes()));

		}


		flightOrderRow.setFlightOrderRowCommissionList(flightOrderRowCommissions);
		if(flightOrderRowCommissions!=null && flightOrderRowCommissions.size()>0)
		{
			Map<String, BigDecimal> companyCommissionMap=new HashMap<String, BigDecimal>();
			for(FlightOrderRowCommission flightOrderRowCommissionInner : flightOrderRowCommissions)
			{
				companyCommissionMap.put(flightOrderRowCommissionInner.getCompanyId(),flightOrderRowCommissionInner.getCommissionAmountValue());
			}
		}

		getFlightOrderCommisionMap(flightOrderRowCommissions,markupCommissionDetails);

		BigDecimal markup = getTotalMarkup( markupCommissionDetails,((passengerdetailsList != null)?passengerdetailsList.size():0));
		flightOrderRow.setMarkUp(markup);//in base currency

		BigDecimal processingFees = new BigDecimal("0.0");//??in booked currency
		if(!flightCustomerDetails.getPaymode().equals("cash")){
			processingFees = flightPriceResponse.getTotalPayableAmount().divide(new BigDecimal("100")).multiply(new BigDecimal("2.0")) ;
		}
		flightOrderRow.setProcessingFees(processingFees);
		flightOrderRow.setPrice(new BigDecimal(flightPriceResponse.getFareFlightSegment().getBasePriceWithoutMarkup()));//???in api currency
		flightOrderRow.setTotalTaxes(new BigDecimal(flightPriceResponse.getFareFlightSegment().getTaxesWithoutMarkup()));//in api currency
		flightOrderRow.setSupplierTds(flightPriceResponse.getFareFlightSegment().getSupplierTds());
		flightOrderRow.setSupplierPrice(new BigDecimal(flightPriceResponse.getFareFlightSegment().getApi_totalPriceWithoutMarkup()));
		flightOrderRow.setSupplierCommission(flightPriceResponse.getFareFlightSegment().getSupplierCommissionearned());
		String totalPriceInBookedCurrency = finalprice.toString();//in booked price
		BigDecimal totalPrice = new BigDecimal(totalPriceInBookedCurrency).add(flightOrderRow.getProcessingFees());//in booked currency
		totalPrice = AmountRoundingModeUtil.roundingMode(totalPrice);
		flightPriceResponse.getFareFlightSegment().setTotalPrice(totalPrice.toString());

		flightOrderRow.setFinalPrice(totalPrice);
		flightOrderRow.setLastTicketingDate(flightPriceResponse.getFareFlightSegment().getLatestTicketingTime() );
		flightOrderRow.setExtramealprice(new BigDecimal(flightPriceResponse.getFareFlightSegment().getExtraMealPrice()));
		flightOrderRow.setExtrabaggageprice(new BigDecimal(flightPriceResponse.getFareFlightSegment().getExtraBaggagePrice()));
		flightOrderRow.setBookingMode("Online");
		flightOrderRow.setTotInvoiceAmount(flightPriceResponse.getFareFlightSegment().getPayableAmount());
		flightOrderRow.setRecievedAmount(new BigDecimal(0));
		if(flightPriceResponse.getFareFlightSegment().getFlightServiceTax()!=null)
			flightOrderRow.setServiceTax(flightPriceResponse.getFareFlightSegment().getFlightServiceTax().getTotalServiceTax());
		else
			flightOrderRow.setServiceTax(new BigDecimal(0));

		if(flightPriceResponse.getFareFlightSegment().getFlightGstTax()!=null)
			flightOrderRow.setGstOnFlights(flightPriceResponse.getFareFlightSegment().getFlightGstTax().getTotalTax());
		else
			flightOrderRow.setGstOnFlights(new BigDecimal(0));

		if(flightCustomerDetails.getIsCompanyEntity()!=null && flightCustomerDetails.getIsCompanyEntity()){ 
			Integer companyEntityId = flightCustomerDetails.getCompanyEntityId();
			flightOrderRow.setCompanyEntityId(companyEntityId.longValue());
		}
		//added by basha 
		try{
		RmConfigModel  rmConfigModel=rmConfigDetailDAO.getRmConfigModel(appKeyVo.getCompanyId());
		   if(rmConfigModel!=null){
		   FlightOrderRowRmConfigStruct flightOrderRowRmConfigStruct=new FlightOrderRowRmConfigStruct();
		   flightOrderRowRmConfigStruct.setRmDynamicData(rmConfigModel.getDynamicFieldsData());
		   flightOrderRow.setFlightOrderRowRmConfigStruct(flightOrderRowRmConfigStruct);
		   }
		} catch (Exception e) {
			logger.error("Exception", e);
			//throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
		}
		
		
		try {
			flightBookingDao.insertFlightOrderRowDetails(flightOrderRow);
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



		FlightBookingResponse flightBookingResponse = null;

		//////////setting markup deatails/ commission
		if(markupCommissionDetails!=null &&markupCommissionDetails.getRateType()!=null){
			for(FlightOrderRowMarkup flightOrderRowMarkup:flightOrderRowMarkups)
				try {
					flightBookingDao.insertMarkupDetails(flightOrderRowMarkup);
				} catch (Exception e) {
					logger.error("HibernateException", e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}

			for(FlightOrderRowCommission flightOrderRowCommission:flightOrderRowCommissions)
				try {
					flightBookingDao.insertCommission(flightOrderRowCommission);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("HibernateException", e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}
		}


		/////////////////////////////
		for(FlightOrderCustomerPriceBreakup flightOrderCustomerPriceBreakup:flightOrderRow.getFlightOrderCustomerPriceBreakups()){
			try {
				flightBookingDao.insertFlightOrderCustomerPriceBreakupDetails(flightOrderCustomerPriceBreakup);
			} catch (Exception e) {
				logger.error("Exception", e);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}
		}
		for(FlightOrderCustomer flightOrderCustomer:flightOrderRow.getFlightOrderCustomers()){
			try {
				flightBookingDao.insertFlightOrderCustomerDetails(flightOrderCustomer);
			} catch (Exception e) {
				logger.error("Exception", e);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}
		}
		for(FlightOrderTripDetail flightOrderTripDetail:flightOrderRow.getFlightOrderTripDetails()){
			try {
				flightBookingDao.insertFlightOrderTripDetailDetails(flightOrderTripDetail);
			} catch (Exception e) {
				logger.error("Exception", e);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}
		}
		if(flightOrderCustomersSSRlist.size() > 0){
			for(FlightOrderCustomerSSR flightOrderCustomerSSR:flightOrderCustomersSSRlist){
				try {
					flightBookingDao.insertFlightOrderCustomerSSRDetails(flightOrderCustomerSSR);
				} catch (Exception e) {
					logger.error("Exception", e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
				}
			}
		}

		try {
			flightBookingDao.updateFlightOrderRowDetails(flightOrderRow);
		} catch (Exception e) {
			logger.error("Exception", e);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
		}

		PaymentTransaction paymentTransaction=new PaymentTransaction();
		paymentTransaction.setAmount(AmountRoundingModeUtil.roundingMode(flightPriceResponse.getTotalPayableAmount().add(processingFees)));// in booking currency
		paymentTransaction.setCurrency(flightPriceResponse.getFlightsearch().getBookedCurrency());
		paymentTransaction.setRefno(orderId);
		paymentTransaction.setIsPaymentSuccess(false);
		paymentTransaction.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		paymentTransaction.setPayment_method(flightCustomerDetails.getPaymode());
		paymentTransaction.setApi_transaction_id(orderId);
		paymentTransaction.setPayment_status("Pending");
		String newpgid="";

		List<Company> companyListBottomToTop= new LinkedList<>();
		List<User> userListBottomToTop= new LinkedList<>();
		Map<Integer, CutandPayModel> cutAndPayUserMap = new LinkedHashMap<>();	

		BigDecimal gst=new BigDecimal("0.0");

		if(flightCustomerDetails.getPaymode().equals("cash")){

			boolean result=false;
			WalletAmountTranferHistory walletAmountTranferHistory=new WalletAmountTranferHistory();
			try {
				walletAmountTranferHistory.setActionId(orderId);
				walletAmountTranferHistory.setCurrency(flightPriceResponse.getFareFlightSegment().getCurrency());
				walletAmountTranferHistory.setAction("FlightBooking Initiated");
				walletAmountTranferHistory.setRemarks("Flight Booking payment");
				walletAmountTranferHistory.setAmount(AmountRoundingModeUtil.roundingMode(finalprice));

				if(companyConfig!=null)
				{
					companyListBottomToTop = CommonUtil.getParentCompanyBottomToTop(companyConfig.getCompany_id(),companyDao);
					if(companyListBottomToTop!=null && companyListBottomToTop.size()>0)
					{
						User currentUser = companyDao.getUserById(Integer.valueOf(flightCustomerDetails.getUserid()));
						userListBottomToTop = CommonUtil.getUsersAllWithUserModeBottomToTop(companyListBottomToTop,companyDao,currentUser);
					}
					cutAndPayUserMap = CommonUtil.getCutandPayModelUsersFlight(flightPriceResponse,companyConfig,userListBottomToTop,flightCustomerDetails);

					boolean checkBookingAmountEligibility= false;
					if(userListBottomToTop!=null && userListBottomToTop.size()>0)
					{
						for(User userInner : userListBottomToTop)
						{
							if(userInner.getAgentWallet()!=null)
							{
								if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
								{
									BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
									if(!userWalletDAO.checkWalletAmount(userInner.getId(), totalPayableAmount,gst, new BigDecimal(0))){
										result = false;
										checkBookingAmountEligibility = false;
									}else{
										checkBookingAmountEligibility = true;
									}
								}
							}
						}
					}	

					if(checkBookingAmountEligibility)
					{
						Map<Integer,Boolean> userMapBottomToTop= new LinkedHashMap<>();
						if(userListBottomToTop!=null && userListBottomToTop.size()>0)
						{
							for(User userInner : userListBottomToTop)
							{
								if(userInner.getAgentWallet()!=null)
								{
									if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
									{
										BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
										if(userWalletDAO.checkWalletAmount(userInner.getId(), totalPayableAmount,gst, new BigDecimal(0)))
										{		
											userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,gst, new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_REMARKS.getMessage(),true);
											userMapBottomToTop.put(userInner.getId(),true);

										}
										else{
											if(userMapBottomToTop!=null && userMapBottomToTop.size()>0)
											{
												for(Entry<Integer,Boolean>  userMap :userMapBottomToTop.entrySet())
												{
													if(userMap.getValue())
													{
														totalPayableAmount = cutAndPayUserMap.get(userMap.getKey()).getPayableAmount();
														userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,gst, new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
													}
												}
											}
											result = false;
											break;
										}
									}
									else{
										result = false;
									}
								}
							}
							result = true;
						}	
					}else{
						result = false;
					}			
				}

				//result = userWalletDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()).divide(baseToBookingExchangeRate,2,RoundingMode.UP),walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_REMARKS.getMessage(),true);

			} catch (Exception e1) {
				logger.error("Exception", e1);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
			}
			if (result){
				//deduct the value from the DB
				try {
					//Bluestar booking call....
					if(flightCustomerDetails.getPrice_key().startsWith("PBS")){
						BluestarConfig bluestarConfig = BluestarConfig.GetBluestarConfig(appKeyVo);
						flightBookingResponse= com.tayyarah.flight.util.api.bluestar.BluestarServiceCall.callBookingService(flightBookingResponse,orderCustomer,flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails.getCountryCode(),flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,10,false,bluestarConfig);
					}else if(flightCustomerDetails.getPrice_key().startsWith("PTB")){						//cal TBO booking API
						// Call TBO Config
						TboFlightConfig tboconfig = TboFlightConfig.GetTboConfig(appKeyVo);
						//cal TBO API booking API
						TboFlightAirpriceResponse	TboPriceResponse = UapiServiceCall.getTboFlightPriceResponse(flightCustomerDetails.getPrice_key(),TempDAO,false);
						// LCC carrier Booking Request
						if(TboPriceResponse.getResponse().getResults().getIsLCC())
							flightBookingResponse= TboServiceCall.callLCCBookingService(flightBookingResponse,TboPriceResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails,flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,10,false,flightOrderRow,tboconfig);
						else
							flightBookingResponse= TboServiceCall.callBookingService(flightBookingResponse,TboPriceResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails,flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,10,false,flightOrderRow,tboconfig);

					}else if(flightCustomerDetails.getPrice_key().startsWith("PTAY")){
						//cal tayyarah booking API
						flightBookingResponse= TayyarahServiceCall.callBookingService(flightBookingResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails,flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,10);
					}else if(flightCustomerDetails.getPrice_key().startsWith("PLIN")){
						//cal tayyarah booking API
						flightBookingResponse= LintasServiceCall.callBookingService(flightBookingResponse,orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails,flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,10);
					}else{
						flightBookingResponse= UapiServiceCall.callBookingService(flightBookingResponse,TravelportConfig.GetTravelportConfig(), orderCustomer,  flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails.getCountryCode(),flightBookingDao,emaildao,flightCustomerDetails.getTransactionkey(),flightCustomerDetails.getPaymode(),walletAmountTranferHistory,10);
					}

					// insert notication after booking is successful
					new NotificationUtil().insertNotification(appKeyVo,orderId , "Flight Ticket Booking", InventoryTypeEnum.FLIGHT_ORDER.getId(), true,notificationDao,companyDao);

					paymentTransaction.setIsPaymentSuccess(true);
					paymentTransaction.setTransactionId(flightCustomerDetails.getTransactionkey());
					paymentTransaction.setResponse_message("NA");
					paymentTransaction.setResponseCode("NA");
					paymentTransaction.setPayment_status("SUCCESS");
					paymentTransaction.setAuthorizationCode(flightCustomerDetails.getTransactionkey());
					try {
						newpgid=callPayment(paymentTransaction, flightBookingDao,0,"");
					} catch (Exception e) {
						logger.error("Exception", e);
						throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
					}
					flightBookingResponse.setPaymentStatus(true);
					if(flightCustomerDetails.getIsInsuranceAvailable() && flightCustomerDetails.getPlanId()!=null && flightCustomerDetails.getPlanId() != -1)
						if(flightBookingResponse.isBookingStatus()){
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyyy");
							Date onwarddate = format.parse(flightPriceResponse.getFlightsearch().getDepDate());
							String departdate = format1.format(onwarddate);				

							//Date returndate = format.parse(flightOrderRow.getFlightOrderTripDetails().get(0).getArrDate());
							//String arridate = format1.format(returndate);
							SimpleDateFormat formatter1 = new SimpleDateFormat("dd MMM yyyy");
							Date returndate = formatter1.parse(flightOrderRow.getFlightOrderTripDetails().get(0).getArrDate());

							String arridate = format1.format(returndate);

							CreateInsurancePolicyRequest createInsurancePolicyRequest = new CreateInsurancePolicyRequest();
							createInsurancePolicyRequest.setApp_key(appKeyVo.getAppKey());
							createInsurancePolicyRequest.setArrivalDate(arridate);
							createInsurancePolicyRequest.setDepartureDate(departdate);
							createInsurancePolicyRequest.setCompanyEntityId(flightCustomerDetails.getCompanyEntityId());
							createInsurancePolicyRequest.setCurrency(flightPriceResponse.getFlightsearch().getCurrency());
							createInsurancePolicyRequest.setDestination(flightPriceResponse.getFlightsearch().getOrigin());
							createInsurancePolicyRequest.setEmulateByCompanyId(flightCustomerDetails.getEmulateByCompanyId());
							createInsurancePolicyRequest.setEmulateByUserId(flightCustomerDetails.getEmulateByUserId());
							createInsurancePolicyRequest.setIsCompanyEntity(flightCustomerDetails.getIsCompanyEntity());
							createInsurancePolicyRequest.setIsEmulateFlag(flightCustomerDetails.isEmulateFlag());
							createInsurancePolicyRequest.setIsQuotation(flightCustomerDetails.getIsQuotation());
							createInsurancePolicyRequest.setIsRmDetails(flightCustomerDetails.getIsRmDetails());
							createInsurancePolicyRequest.setMarkupAmount("0");
							//int noofdays = InsuranceCommonUtil.getNoofStayDays(flightPriceResponse.getFlightsearch().getDepDate(), flightPriceResponse.getFlightsearch().getArvlDate());
							createInsurancePolicyRequest.setNoOfDays("1");
							createInsurancePolicyRequest.setOrigin(flightPriceResponse.getFlightsearch().getOrigin());
							createInsurancePolicyRequest.setPlanId( flightCustomerDetails.getPlanId());
							createInsurancePolicyRequest.setQuotationId(flightCustomerDetails.getQuotationid());
							//createInsurancePolicyRequest.setRmDataListDetails(flightCustomerDetails.getRmDataListDetails());
							createInsurancePolicyRequest.setUserId(flightCustomerDetails.getUserid());
							createInsurancePolicyRequest.setUserName(flightCustomerDetails.getUsername());
							List<TravellerDetails> travellerDetailsList = new ArrayList<>();
							int customerIndex = 0;
							for (PassengerDetails passengerDetails : flightCustomerDetails.getPassengerdetailsList()) {
								TravellerDetails travellerDetails = new TravellerDetails();	
								long customerId = flightOrderRow.getFlightOrderCustomers().get(customerIndex).getId();
								travellerDetails.setCustomerId(String.valueOf(customerId));
								travellerDetails.setAddress(flightCustomerDetails.getAddress());
								travellerDetails.setPaxId(passengerDetails.getPaxId());//added by basha
								travellerDetails.setAge(passengerDetails.getAge());
								travellerDetails.setCity(flightCustomerDetails.getCity());
								travellerDetails.setCountry(flightCustomerDetails.getCountryCode()!=null && flightCustomerDetails.getCountryCode().equalsIgnoreCase("91")?"india":flightCustomerDetails.getCountryCode());
								travellerDetails.setDateOfBirth(passengerDetails.getBirthday());
								travellerDetails.setDistrict(flightCustomerDetails.getDistrict());
								travellerDetails.setEmailAddress(flightCustomerDetails.getEmail());
								travellerDetails.setFirstName(passengerDetails.getFirstName());
								travellerDetails.setLastName(passengerDetails.getLastName());
								travellerDetails.setMobile(flightCustomerDetails.getMobile());
								travellerDetails.setNominee(passengerDetails.getIsSelfInsurance()!=null && passengerDetails.getIsSelfInsurance()?"self":passengerDetails.getNomineeName());
								travellerDetails.setPassportNumber(passengerDetails.getPassportNo());
								travellerDetails.setPinCode(flightCustomerDetails.getPincode());
								travellerDetails.setRelationShipWithNominee(passengerDetails.getIsSelfInsurance()!=null && passengerDetails.getIsSelfInsurance()?"":passengerDetails.getNomineeRelationShip());
								travellerDetails.setState(flightCustomerDetails.getState());
								travellerDetails.setTitle(passengerDetails.getTitle());
								travellerDetailsList.add(travellerDetails);
								customerIndex++;
							}
							createInsurancePolicyRequest.setTravellerDetails(travellerDetailsList);
							createInsurancePolicyRequest.setInsuredProduct("Flight");
							createInsurancePolicyRequest.setInsuredProductOrderRowId(flightOrderRow.getId());
							createInsurancePolicyRequest.setInsuredProductOrderId(flightOrderRow.getOrderId());
							PolicyResponseData policyResponseData = createPolicyController.createpolicy(createInsurancePolicyRequest, response, request);
							if(policyResponseData.getStatus().getErrorCode() == Status.SUCCESSCODE){
								flightOrderRow.setIsInsuranceAdded(true);
								long insuranceOrderRowID = insuranceCommonDao.getInsuranceOrderRowIdByOrderId(policyResponseData.getOrderId());
								flightOrderRow.setInsuranceOrderRowId(insuranceOrderRowID);
							}else{
								flightOrderRow.setIsInsuranceAdded(false);

							}
						}


					// Update orderCustomer
					orderCustomer.setOrderId(flightOrderRow.getOrderId());		
					try {
						flightBookingDao.updateOrderCustomerDetails(orderCustomer);
						// Update flightorderrow
						//flightOrderRow.setVersion(1);
						flightOrderRow.setCustomer(orderCustomer);
						flightBookingDao.updateFlightOrderRowDetails(flightOrderRow);
					} catch (Exception e1) {
						logger.error("Exception", e1);
						throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
					}





				}catch(ClassNotFoundException e){
					logger.error("ClassNotFoundException", e);
					walletAmountTranferHistory.setRemarks("Flight Booking Failed");
					//DBS.updateWalletBalanceIfFailed(walletAmountTranferHistory.getAmount(),walletAmountTranferHistory.getWalletId(), flightBookingDao,walletAmountTranferHistory);
					try {
						if(userListBottomToTop!=null && userListBottomToTop.size()>0)
						{
							for(User userInner : userListBottomToTop)
							{
								if(userInner.getAgentWallet()!=null)
								{
									if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
									{
										BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
										userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,gst, new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
									}
									else{
										result = false;
									}
								}
							}
						}	


						userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(flightOrderRow.getOrderId(), "0");




						//userWalletDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()),walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					DBS.updatePNR("0", orderId, flightBookingDao);

					throw new FlightException(ErrorCodeCustomerEnum.ClassNotFoundException,ErrorMessages.BOOKING_FAILED);
				}
				catch(SOAPException e){
					logger.error("SOAPException", e);
					walletAmountTranferHistory.setRemarks("Flight Booking Failed");
					//DBS.updateWalletBalanceIfFailed(walletAmountTranferHistory.getAmount(),walletAmountTranferHistory.getWalletId(), flightBookingDao,walletAmountTranferHistory);
					try {
						if(userListBottomToTop!=null && userListBottomToTop.size()>0)
						{
							for(User userInner : userListBottomToTop)
							{
								if(userInner.getAgentWallet()!=null)
								{
									if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
									{
										BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
										userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,gst, new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
									}
									else{
										result = false;
									}
								}
							}
						}	
						userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(flightOrderRow.getOrderId(), "0");

						//userWalletDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()),walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					DBS.updatePNR("0", orderId, flightBookingDao);
					throw new FlightException(ErrorCodeCustomerEnum.SOAPException, ErrorMessages.BOOKING_FAILED);
				}
				catch(JAXBException e){
					logger.error("JAXBException", e);
					walletAmountTranferHistory.setRemarks("Flight Booking Failed");
					//DBS.updateWalletBalanceIfFailed(walletAmountTranferHistory.getAmount(),walletAmountTranferHistory.getWalletId(), flightBookingDao,walletAmountTranferHistory);
					try {
						if(userListBottomToTop!=null && userListBottomToTop.size()>0)
						{
							for(User userInner : userListBottomToTop)
							{
								if(userInner.getAgentWallet()!=null)
								{
									if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
									{
										BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
										userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,gst, new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
									}
									else{
										result = false;
									}
								}
							}
						}	
						userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(flightOrderRow.getOrderId(),  "0");

						//userWalletDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()),walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					DBS.updatePNR("0", orderId, flightBookingDao);
					throw new FlightException(ErrorCodeCustomerEnum.JAXBException,ErrorMessages.BOOKING_FAILED);
				}catch(Exception e){
					walletAmountTranferHistory.setRemarks("Flight Booking Failed");
					//DBS.updateWalletBalanceIfFailed(walletAmountTranferHistory.getAmount(),walletAmountTranferHistory.getWalletId(), flightBookingDao,walletAmountTranferHistory);
					try {
						if(userListBottomToTop!=null && userListBottomToTop.size()>0)
						{
							for(User userInner : userListBottomToTop)
							{
								if(userInner.getAgentWallet()!=null)
								{
									if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
									{
										BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
										userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,gst, new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
									}
									else{
										result = false;
									}
								}
							}
						}	

						userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(flightOrderRow.getOrderId(), "0");

						//userWalletDAO.getWalletStatus(flightCustomerDetails.getUserid(),new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()),walletAmountTranferHistory,flightPriceResponse.getGSTonMarkup().divide(baseToBookingExchangeRate,2,RoundingMode.UP),flightPriceResponse.getGSTonFlights().divide(baseToBookingExchangeRate,2,RoundingMode.UP),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					DBS.updatePNR("0", orderId, flightBookingDao);

					logger.error("Exception", e);
					//flightBookingResponse.setBookingComments("API Error");
					//flightBookingResponse.setBookingStatus(false);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
				}



			}
			else
			{
				//insufficient wallet balance
				flightBookingResponse=new FlightBookingResponse();
				logger.info("insufficient wallet balance : ");
				flightBookingResponse.setPnr("NA");
				flightBookingResponse.setBokingConditions("Top up ur wallet");
				flightBookingResponse.setBookingComments("Unable to process your booking due to insufficient balance in the account");
				flightBookingResponse.setBookingStatus(false);
				DBS.updatePNR("#0", orderId, flightBookingDao);
			}
		}
		else
		{		

			storeBookingDetails(orderCustomer,flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails);
			flightBookingResponse=new FlightBookingResponse();
			flightBookingResponse.setPnr("0#");
			flightBookingResponse.setBokingConditions("payment gateway has to be called");
			flightBookingResponse.setBookingComments("payment");
			flightBookingResponse.setBookingStatus(false);
			DBS.updatePNR("0#", orderId, flightBookingDao);
			try {
				newpgid = callPayment(paymentTransaction, flightBookingDao,0,"");
			} catch (Exception e) {
				logger.error("Exception", e);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.DB_ERROR);
			}				

		}



		flightBookingResponse.setFlightCustomerDetails(flightCustomerDetails);
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
		flightBookingResponse.setProcessingfee(processingFees);
		flightBookingResponse.setFinalPriceWithGST(AmountRoundingModeUtil.roundingMode(flightPriceResponse.getFinalPriceWithGST().add(processingFees)));


		if(flightCustomerDetails.getIsInsuranceAvailable() && flightCustomerDetails.getPlanId()!=null && flightCustomerDetails.getPlanId() != -1){
			BigDecimal insuranceTotalAmount = new BigDecimal(0);
			for (PassengerDetails passengerDetails : flightCustomerDetails.getPassengerdetailsList()) {
				TrawellTagPremiumChart trawellTagPremiumChart = insuranceCommonDao.getTrawellTagPremiumChart("1", passengerDetails.getAge(),flightCustomerDetails.getPlanId());
				insuranceTotalAmount = insuranceTotalAmount.add(trawellTagPremiumChart.getPremiumAmount());
				insuranceTotalAmount = insuranceTotalAmount.setScale(0, RoundingMode.UP);
			}
			BigDecimal finalPrice = flightBookingResponse.getFinalPriceWithGST().add(insuranceTotalAmount).setScale(2, RoundingMode.UP);
			flightBookingResponse.setFinalPriceWithGST(finalPrice);
			flightBookingResponse.getFareFlightSegment().setTotalPrice(finalPrice.toString());
		}

		if(flightCustomerDetails.getIsQuotation().equalsIgnoreCase("true")){
			flightTravelRequestDao.flightRequestQuotationUpdate(Long.parseLong(flightCustomerDetails.getQuotationid()),flightOrderRow,null);
			ApiProviderPaymentTransaction apiProviderPaymentTransaction = new ApiProviderPaymentTransaction();
			apiProviderPaymentTransaction.setAmount(AmountRoundingModeUtil.roundingMode(new BigDecimal(flightPriceResponse.getFareFlightSegment().getApi_totalPriceWithoutMarkup())));
			apiProviderPaymentTransaction.setApi_transaction_id(flightOrderRow.getOrderId());
			apiProviderPaymentTransaction.setCurrency(flightOrderRow.getApiCurrency());
			apiProviderPaymentTransaction.setCreatedAt(flightOrderRow.getCreatedAt());
			apiProviderPaymentTransaction.setPayment_system("Full");
			apiProviderPaymentTransaction.setPayment_status(flightOrderRow.getPaymentStatus());
			apiProviderPaymentTransaction.setIsPaymentSuccess(true);
			flightTravelRequestDao.insertSupplierPaymentTransactionInfo(apiProviderPaymentTransaction);
		}

		/*if(flightCustomerDetails.getIsRmDetails()){
			for (RmConfigTripDetailsModel rmConfigTripDetailsModel : flightCustomerDetails.getRmDataListDetails()) {
				rmConfigTripDetailsModel.setOrdertype("Flight");
				rmConfigTripDetailsModel.setOrderId(flightOrderRow.getOrderId());
				try{
					companyDao.insertRMConfigTripDetails(rmConfigTripDetailsModel);
				}catch(Exception e){

				}
			}
		}*/
		if(!isLowFare){
			// Set Flight Fare Alert Detail for First Recommended  Flight
			Map<FlightFareAlertDetail, List<Segments>> flightFareAlertDetailMap = getFlightFareAlertDetailOneway(flightCustomerDetails, lowFareFlightIndex1, flightBookingResponse, flightOrderRow,reasons);
			if(flightFareAlertDetailMap.size() > 0){
				for (FlightFareAlertDetail flightFareAlertDetail : flightFareAlertDetailMap.keySet()) {				   
					FlightFareAlertDetail flightFareAlertDetailupdated = flightBookingDao.insertLowFareFlightDetail(flightFareAlertDetail); 
					if(flightFareAlertDetailupdated.getIsConnFlightAvailable()){
						List<Segments> Segments =  flightFareAlertDetailMap.get(flightFareAlertDetail);
						List<FlightFareAlertConnectingFlight> flightFareAlertConnectingFlightList =  getFlightFareAlertConnectingFlight(flightFareAlertDetailupdated, Segments);
						List<FlightFareAlertConnectingFlight> flightFareAlertConnectingFlightListUpdated = new ArrayList<>();
						for (FlightFareAlertConnectingFlight flightFareAlertConnectingFlight : flightFareAlertConnectingFlightList) {
							FlightFareAlertConnectingFlight flightFareAlertConnectingFlightupdated = flightBookingDao.insertFlightFareAlertConnectingFlight(flightFareAlertConnectingFlight);
							flightFareAlertConnectingFlightListUpdated.add(flightFareAlertConnectingFlightupdated);
						}
						flightFareAlertDetailupdated.setFlightFareAlertConnectingFlightList(flightFareAlertConnectingFlightListUpdated);
						flightBookingDao.updateLowFareFlightDetail(flightFareAlertDetailupdated); 
					}
				}
			}
			if(!lowFareFlightIndex2.equalsIgnoreCase("")){
				Map<FlightFareAlertDetail, List<Segments>> flightFareAlertDetailMapSecond = getFlightFareAlertDetailOneway(flightCustomerDetails, lowFareFlightIndex1, flightBookingResponse, flightOrderRow,reasons);
				if(flightFareAlertDetailMapSecond.size() > 0){
					for (FlightFareAlertDetail flightFareAlertDetail : flightFareAlertDetailMapSecond.keySet()) {				   
						FlightFareAlertDetail flightFareAlertDetailupdated = flightBookingDao.insertLowFareFlightDetail(flightFareAlertDetail); 
						if(flightFareAlertDetailupdated.getIsConnFlightAvailable()){
							List<Segments> Segments =  flightFareAlertDetailMapSecond.get(flightFareAlertDetail);
							List<FlightFareAlertConnectingFlight> flightFareAlertConnectingFlightList =  getFlightFareAlertConnectingFlight(flightFareAlertDetailupdated, Segments);
							List<FlightFareAlertConnectingFlight> flightFareAlertConnectingFlightListUpdated = new ArrayList<>();
							for (FlightFareAlertConnectingFlight flightFareAlertConnectingFlight : flightFareAlertConnectingFlightList) {
								FlightFareAlertConnectingFlight flightFareAlertConnectingFlightupdated = flightBookingDao.insertFlightFareAlertConnectingFlight(flightFareAlertConnectingFlight);
								flightFareAlertConnectingFlightListUpdated.add(flightFareAlertConnectingFlightupdated);
							}
							flightFareAlertDetailupdated.setFlightFareAlertConnectingFlightList(flightFareAlertConnectingFlightListUpdated);
							flightBookingDao.updateLowFareFlightDetail(flightFareAlertDetailupdated); 
						}
					}
				}
			}
		}

		// After booking Successful we are inserting Email 
		if(flightOrderRow != null && flightOrderRow.getStatusAction().equalsIgnoreCase("Confirmed"))
			TboCommonUtil.updateMailstatus(orderId,emaildao);

		TravelportConfig travelportConfig=TravelportConfig.GetTravelportConfig();
		CommonConfig commonConfig=CommonConfig.GetCommonConfig();
		if(commonConfig.isIs_lintas_enabled()&&!travelportConfig.isTest()){
			if(flightCustomerDetails!=null && flightBookingResponse.isBookingStatus()&& orderId.startsWith("FTP")){
				sendPNRViaMAil(checkGetEmulatedUserById(flightCustomerDetails),flightBookingResponse.getPnr(), request, response);
			}
		}
		try{
			userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(flightOrderRow.getOrderId(), flightBookingResponse.getInvoiceNumber());
		}catch(Exception e){
			logger.error("walletTransferHistoryUpdateWithInvoiceNo Exception", e);
		}
		return flightBookingResponse;
	}

	private void getFlightOrderCommisionMap(List<FlightOrderRowCommission> flightOrderRowCommissions, MarkupCommissionDetails markupCommissionDetails) {
		if(flightOrderRowCommissions!=null && flightOrderRowCommissions.size()>0)
		{
			Map<String, BigDecimal> companyCommissionMap=new HashMap<String, BigDecimal>();
			for(FlightOrderRowCommission flightOrderRowCommissionInner : flightOrderRowCommissions)
			{
				companyCommissionMap.put(flightOrderRowCommissionInner.getCompanyId(),flightOrderRowCommissionInner.getCommissionAmountValue());
			}
			markupCommissionDetails.setCompanyCommissionMap(companyCommissionMap);
		}

	}

	private FlightOrderRowServiceTax createFlightOrderRowServiceTaxInternational(
			FlightInternationalServiceTaxConfig flightInternationalServiceTaxConfig, FlightOrderRowServiceTax flightOrderRowServiceTax) {
		flightOrderRowServiceTax.setApplicableFare(flightInternationalServiceTaxConfig.getApplicableFare());
		flightOrderRowServiceTax.setBasicTax(flightInternationalServiceTaxConfig.getBasicTax());
		flightOrderRowServiceTax.setConvenienceFee(flightInternationalServiceTaxConfig.getConvenienceFee());
		flightOrderRowServiceTax.setKrishiKalyanCess(flightInternationalServiceTaxConfig.getKrishiKalyanCess());
		flightOrderRowServiceTax.setManagementFee(flightInternationalServiceTaxConfig.getManagementFee());
		flightOrderRowServiceTax.setServiceType(flightInternationalServiceTaxConfig.getServiceType());
		flightOrderRowServiceTax.setSwatchBharathCess(flightInternationalServiceTaxConfig.getSwatchBharathCess());
		flightOrderRowServiceTax.setTotalTax(flightInternationalServiceTaxConfig.getTotalTax());
		flightOrderRowServiceTax.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		return flightOrderRowServiceTax;
	}

	private FlightOrderRowServiceTax createFlightOrderRowServiceTaxDomestic(
			FlightDomesticServiceTaxConfig flightDomesticServiceTaxConfig, FlightOrderRowServiceTax flightOrderRowServiceTax) {
		flightOrderRowServiceTax.setApplicableFare(flightDomesticServiceTaxConfig.getApplicableFare());
		flightOrderRowServiceTax.setBasicTax(flightDomesticServiceTaxConfig.getBasicTax());
		flightOrderRowServiceTax.setConvenienceFee(flightDomesticServiceTaxConfig.getConvenienceFee());
		flightOrderRowServiceTax.setKrishiKalyanCess(flightDomesticServiceTaxConfig.getKrishiKalyanCess());
		flightOrderRowServiceTax.setManagementFee(flightDomesticServiceTaxConfig.getManagementFee());
		flightOrderRowServiceTax.setServiceType(flightDomesticServiceTaxConfig.getServiceType());
		flightOrderRowServiceTax.setSwatchBharathCess(flightDomesticServiceTaxConfig.getSwatchBharathCess());
		flightOrderRowServiceTax.setTotalTax(flightDomesticServiceTaxConfig.getTotalTax());
		flightOrderRowServiceTax.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		return flightOrderRowServiceTax;
	}
	private FlightOrderRowGstTax createFlightOrderRowGstTaxDomestic(
			FlightDomesticGstTaxConfig flightDomesticGstTaxConfig, FlightOrderRowGstTax flightOrderRowGstTax,Company company,Company parentCompany,Flightsearch flightsearch) {

		BigDecimal totalPassenger = new BigDecimal(flightsearch.getAdult()).add(new BigDecimal(flightsearch.getKid())).add(new BigDecimal(flightsearch.getInfant()));
		BigDecimal CGST = new BigDecimal("0.0");
		BigDecimal SGST = new BigDecimal("0.0");
		BigDecimal IGST = new BigDecimal("0.0");
		BigDecimal UGST = new BigDecimal("0.0");
		BigDecimal totalGst = new BigDecimal("0.0");
		BigDecimal managementFee  = new BigDecimal("0.0");
		boolean isParentCompanyUT = IndianUnionTerritories.isUnionter(parentCompany.getBillingstate().trim());
		boolean isBillingCompanyUT = IndianUnionTerritories.isUnionter(company.getBillingstate().trim());
		managementFee = flightDomesticGstTaxConfig.getManagementFee().multiply(totalPassenger);

		if(isParentCompanyUT && isBillingCompanyUT){
			CGST = flightDomesticGstTaxConfig.getCGST();
			UGST =  flightDomesticGstTaxConfig.getUGST();
		}
		if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
			CGST = flightDomesticGstTaxConfig.getCGST();
			UGST =  flightDomesticGstTaxConfig.getUGST();
		}
		if(!isParentCompanyUT && !isBillingCompanyUT){
			if(company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim())){
				CGST = flightDomesticGstTaxConfig.getCGST();
				SGST =  flightDomesticGstTaxConfig.getSGST();				
			}
		}
		if(isParentCompanyUT && !isBillingCompanyUT){
			if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && !IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
				IGST =  flightDomesticGstTaxConfig.getIGST();		
			}
		}				
		totalGst = CGST.add(SGST).add(IGST).add(UGST);	
		flightOrderRowGstTax.setCGST(CGST);
		flightOrderRowGstTax.setSGST(SGST);
		flightOrderRowGstTax.setIGST(IGST);
		flightOrderRowGstTax.setUGST(UGST);
		flightOrderRowGstTax.setVersion(1);
		flightOrderRowGstTax.setManagementFee(managementFee);
		flightOrderRowGstTax.setConvenienceFee(flightDomesticGstTaxConfig.getConvenienceFee());
		flightOrderRowGstTax.setTotalGst(totalGst);
		flightOrderRowGstTax.setApplicableFare(flightDomesticGstTaxConfig.getApplicableFare());
		flightOrderRowGstTax.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
		return flightOrderRowGstTax;
	}
	private FlightOrderRowGstTax createFlightOrderRowGstTaxInternational(
			FlightInternationalGstTaxConfig  flightInternationalGstTaxConfig, FlightOrderRowGstTax flightOrderRowGstTax,Company company,Company parentCompany,Flightsearch flightsearch) {

		BigDecimal totalPassenger = new BigDecimal(flightsearch.getAdult()).add(new BigDecimal(flightsearch.getKid())).add(new BigDecimal(flightsearch.getInfant()));
		BigDecimal CGST = new BigDecimal("0.0");
		BigDecimal SGST = new BigDecimal("0.0");
		BigDecimal IGST = new BigDecimal("0.0");
		BigDecimal UGST = new BigDecimal("0.0");
		BigDecimal totalGst = new BigDecimal("0.0");
		BigDecimal managementFee  = new BigDecimal("0.0");
		boolean isParentCompanyUT = IndianUnionTerritories.isUnionter(parentCompany.getBillingstate().trim());
		boolean isBillingCompanyUT = IndianUnionTerritories.isUnionter(company.getBillingstate().trim());
		managementFee = flightInternationalGstTaxConfig.getManagementFee().multiply(totalPassenger);

		if(isParentCompanyUT && isBillingCompanyUT){
			CGST = flightInternationalGstTaxConfig.getCGST();
			UGST =  flightInternationalGstTaxConfig.getUGST();
		}
		if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
			CGST = flightInternationalGstTaxConfig.getCGST();
			UGST =  flightInternationalGstTaxConfig.getUGST();
		}
		if(!isParentCompanyUT && !isBillingCompanyUT){
			if(company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim())){
				CGST = flightInternationalGstTaxConfig.getCGST();
				SGST =  flightInternationalGstTaxConfig.getSGST();				
			}
		}
		if(isParentCompanyUT && !isBillingCompanyUT){
			if(!company.getBillingstate().trim().equalsIgnoreCase(parentCompany.getBillingstate().trim()) && !IndianUnionTerritories.isUnionter(company.getBillingstate().trim())){
				IGST =  flightInternationalGstTaxConfig.getIGST();		
			}
		}	
		totalGst = CGST.add(SGST).add(IGST).add(UGST);	
		flightOrderRowGstTax.setCGST(CGST);
		flightOrderRowGstTax.setSGST(SGST);
		flightOrderRowGstTax.setIGST(IGST);
		flightOrderRowGstTax.setUGST(UGST);
		flightOrderRowGstTax.setVersion(1);
		flightOrderRowGstTax.setManagementFee(managementFee);
		flightOrderRowGstTax.setConvenienceFee(flightInternationalGstTaxConfig.getConvenienceFee());
		flightOrderRowGstTax.setApplicableFare(flightInternationalGstTaxConfig.getApplicableFare());
		flightOrderRowGstTax.setTotalGst(totalGst);
		flightOrderRowGstTax.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));		

		return flightOrderRowGstTax;
	}

	private int getIntValue(String id) {
		if(id!=null && !id.equalsIgnoreCase(""))
			return Integer.valueOf(id).intValue();
		return 0;
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
			flightBookingDao.insertBookingDetails(bookingDetailsToDb);
		} catch (Exception e) {
			logger.error("Exception", e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
		}
	}


	private void createFlightOrderCustomerPriceBreakupList( List<FlightOrderCustomerPriceBreakup> flightOrderCustomerPriceBeakups,FlightPriceResponse flightPriceResponse,OrderCustomer orderCustomer,FlightOrderRow flightOrderRow,int count){
		logger.info("createFlightOrderCustomerPriceBreakupList method called count : " +count);
		List<PassengerFareBreakUp> passengerFareBreakUps=flightPriceResponse.getPassengerFareBreakUps();
		if(count==1)
		{
			passengerFareBreakUps=flightPriceResponse.getSpecialPassengerFareBreakUps();
		}
		for(PassengerFareBreakUp passengerFareBreakUp:passengerFareBreakUps)
		{
			FlightOrderCustomerPriceBreakup flightOrderCustomerPriceBreakup=new FlightOrderCustomerPriceBreakup();
			flightOrderCustomerPriceBreakup.setFlightCustomer(orderCustomer);
			flightOrderCustomerPriceBreakup.setBaseFare(new BigDecimal(passengerFareBreakUp.getBasePriceWithoutMarkup()));
			flightOrderCustomerPriceBreakup.setTax(new BigDecimal(passengerFareBreakUp.getTaxesWithoutMarkup()));
			flightOrderCustomerPriceBreakup.setTotal(AmountRoundingModeUtil.roundingMode(new BigDecimal(passengerFareBreakUp.getTotalPriceWithoutMarkup())));
			flightOrderCustomerPriceBreakup.setDescription(passengerFareBreakUp.getType());
			flightOrderCustomerPriceBreakup.setFlightOrderRow(flightOrderRow);//DO i need to ADD this Yogesh??
			flightOrderCustomerPriceBreakup.setVersion(VERSION);
			flightOrderCustomerPriceBreakup.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			flightOrderCustomerPriceBreakup.setYQTax(passengerFareBreakUp.getFlightTaxBreakUp().getYQ());
			flightOrderCustomerPriceBreakup.setYRTax(passengerFareBreakUp.getFlightTaxBreakUp().getYR()!= null?passengerFareBreakUp.getFlightTaxBreakUp().getYR():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setPSFTax(passengerFareBreakUp.getFlightTaxBreakUp().getPSF()!= null?passengerFareBreakUp.getFlightTaxBreakUp().getPSF():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setUDFTax(passengerFareBreakUp.getFlightTaxBreakUp().getUDF()!= null?passengerFareBreakUp.getFlightTaxBreakUp().getUDF():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setJNTax(passengerFareBreakUp.getFlightTaxBreakUp().getJN()!= null?passengerFareBreakUp.getFlightTaxBreakUp().getJN():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setINTax(passengerFareBreakUp.getFlightTaxBreakUp().getIN()!= null?passengerFareBreakUp.getFlightTaxBreakUp().getIN():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setWOTax(passengerFareBreakUp.getFlightTaxBreakUp().getWO() != null?passengerFareBreakUp.getFlightTaxBreakUp().getWO():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setTransactionFee(passengerFareBreakUp.getFlightTaxBreakUp().getTransactionFee() != null?passengerFareBreakUp.getFlightTaxBreakUp().getTransactionFee():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setG1Tax(passengerFareBreakUp.getFlightTaxBreakUp().getG1() != null?passengerFareBreakUp.getFlightTaxBreakUp().getG1():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setF2Tax(passengerFareBreakUp.getFlightTaxBreakUp().getF2() != null?passengerFareBreakUp.getFlightTaxBreakUp().getF2():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setF6Tax(passengerFareBreakUp.getFlightTaxBreakUp().getF6() != null?passengerFareBreakUp.getFlightTaxBreakUp().getF6():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setZRTax(passengerFareBreakUp.getFlightTaxBreakUp().getZR() != null?passengerFareBreakUp.getFlightTaxBreakUp().getZR():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setYCTax(passengerFareBreakUp.getFlightTaxBreakUp().getYC() != null?passengerFareBreakUp.getFlightTaxBreakUp().getYC():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setUSTax(passengerFareBreakUp.getFlightTaxBreakUp().getUS() != null?passengerFareBreakUp.getFlightTaxBreakUp().getUS():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setXATax(passengerFareBreakUp.getFlightTaxBreakUp().getXA() != null?passengerFareBreakUp.getFlightTaxBreakUp().getXA():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setXYTax(passengerFareBreakUp.getFlightTaxBreakUp().getXY() != null?passengerFareBreakUp.getFlightTaxBreakUp().getXY():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setAYTax(passengerFareBreakUp.getFlightTaxBreakUp().getAY() != null?passengerFareBreakUp.getFlightTaxBreakUp().getAY():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setXFTax(passengerFareBreakUp.getFlightTaxBreakUp().getXF() != null?passengerFareBreakUp.getFlightTaxBreakUp().getXF():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setK3Tax(passengerFareBreakUp.getFlightTaxBreakUp().getK3() != null?passengerFareBreakUp.getFlightTaxBreakUp().getK3():new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setPublishedDiscount(new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setSupplierDiscount(new BigDecimal("0.00"));
			flightOrderCustomerPriceBreakup.setSystemDiscount(new BigDecimal("0.00"));

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

	private void createFlightOrderCustomerList(List<FlightOrderCustomer> flightOrderCustomers,FlightCustomerDetails flightCustomerDetails,OrderCustomer orderCustomer,FlightOrderRow flightOrderRow)
	{
		logger.info("createFlightOrderCustomerList method called : ");
		for(PassengerDetails passengerDetails:flightCustomerDetails.getPassengerdetailsList()){
			FlightOrderCustomer flightOrderCustomer=new FlightOrderCustomer();
			flightOrderCustomer.setPaxId(passengerDetails.getPaxId());
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
			flightOrderTripDetail.setCraft(segments.getCraft());
			flightOrderTripDetail.setFareBasisCode(flightPriceResponse.getFareFlightSegment().getFareRules().get(0).getFareRule().get(0).getBasisCode());
			flightOrderTripDetail.setFareClass(segments.getFareClass());

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

	@RequestMapping(value = "/response", method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody FlightCustomerDetails getFareRuleInfo()
	{
		FlightCustomerDetails flightCustomerDetails=new FlightCustomerDetails();
		Flightsearch flightsearch=new Flightsearch();
		try {
			System.out.println("tewsing :"+companyDao.getBillingCoyuntry());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			flightsearch.setAdult(3);
			flightsearch.setInfant(2);
			flightsearch.setKid(2);
			///
			flightCustomerDetails.setTransactionkey("transactionkey");
			flightCustomerDetails.setUserid("201");
			flightCustomerDetails.setUsername("DirectUser");
			UapiServiceCall.createFlightCustomerDetail(flightCustomerDetails, flightsearch);
			for(int i=0;i<100;i++){
				System.out.println("start :"+i);
				Map<String, String> airlineNameMap;
				ArrayList<Map<String, String>> airportMapList = new ArrayList<Map<String, String>>();
				try {
					airportMapList = airportDAO.getAirportMap();
					airlineNameMap = airlineService.getAirlineNameMap();
				} catch (HibernateException e) {
					logger.error("HibernateException", e);
					throw new FlightException(ErrorCodeCustomerEnum.HibernateException,
							FlightErrorMessages.NO_FLIGHT);
				} catch (Exception e) {
					logger.error("Exception", e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,
							FlightErrorMessages.NO_FLIGHT);
				}
				System.out.println("end :"+i);
			}
			// FRR=UapiServiceCall.callFareRuleService(new TravelportConfig(), fareInfo);
		}
		catch(Exception  e){
			logger.error("Exception", e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
		}
		return flightCustomerDetails;
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

	public static List<FlightOrderRowMarkup> getMarkupDetail(MarkupCommissionDetails markupCommissionDetails,FlightOrderRow flightOrderRow)  {
		//FlightDataBaseServices DBS = new FlightDataBaseServices();

		logger.info("insertMarkupDetail method called : ");
		List<FlightOrderRowMarkup> flightOrderRowMarkups=new ArrayList<FlightOrderRowMarkup>();
		Set<String> companyIdset = new HashSet<String>();
		companyIdset = markupCommissionDetails.getCompanyMarkupMap().keySet();
		for(String companyId:companyIdset){
			FlightOrderRowMarkup flightOrderRowMarkup=new FlightOrderRowMarkup();
			BigDecimal markupAmount = markupCommissionDetails.getCompanyMarkupMap().get(companyId);
			flightOrderRowMarkup.setCompanyId(companyId);
			flightOrderRowMarkup.setMarkUp(markupAmount);
			flightOrderRowMarkup.setFlightOrderRow(flightOrderRow);
			flightOrderRowMarkups.add(flightOrderRowMarkup);
		}
		return flightOrderRowMarkups;
	}

	public static BigDecimal getTotalMarkup(MarkupCommissionDetails markupCommissionDetails, int passengerCount)  {
		logger.info("insertMarkupDetail method called : ");
		Set<String> compnyIdset=new HashSet<String>();
		compnyIdset = markupCommissionDetails.getCompanyMarkupMap().keySet();
		BigDecimal totalmarkupAmount=new BigDecimal("0");
		for(String companyId:compnyIdset){
			BigDecimal markupAmount=markupCommissionDetails.getCompanyMarkupMap().get(companyId);
			markupAmount = markupAmount.multiply(new BigDecimal(passengerCount));
			totalmarkupAmount=totalmarkupAmount.add(markupAmount);
		}
		return totalmarkupAmount;
	}

	@RequestMapping(value = "/sendPNR", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody void sendFailedMails(
			@RequestParam(value = "email") String email,@RequestParam(value = "userid") String userid,@RequestParam(value = "pnr") String pnr,
			HttpServletRequest request, HttpServletResponse response) {

		ResponseHeader.setResponse(response);// Setting response header
		final Locale locale = LocaleContextHolder.getLocale();

		try {
			logger.info("list failed emails : ");



			this.emailService.sendFlightPNR(userid,
					locale, request, response,
					servletContext, applicationContext,email,pnr);
			// logger.info("---------emailStatusIds SIZE--------"+emailStatusIds.size());
		} catch (Exception e) {
			logger.info("failed emails retrival...Exception " + e.getMessage());
			e.printStackTrace();
		}


	}
	public void sendPNRViaMAil(String userid,String pnr,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> emailIdList=new ArrayList<String>();
		emailIdList.add("ilyasali85@gmail.com");
		emailIdList.add("yogiwebs@gmail.com");
		emailIdList.add("fahed@intellicommsolutions.com");
		///	emailIdList.add("ssarah@lintastravel.com");
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
	public FlightBook insertIntoFlightBook(AppKeyVo appKeyVo, String transactionKey,HttpServletRequest request){
		FlightBook flightBook = new FlightBook(); 
		FlightLookBook flightLookBook = new FlightLookBook(); 
		LookBookCustomerIPStatus ipStatus = new LookBookCustomerIPStatus();
		LookBookCustomerIPHistory ipStatusHistory = new LookBookCustomerIPHistory();
		Timestamp currentDate = new Timestamp(new Date().getTime());
		String ip = null;
		try{
			ip = FetchIpAddress.getClientIpAddress(request);
			Company company = appKeyVo.getCompany();

			flightBook.setIP(ip);
			flightBook.setSearchOnDateTime(currentDate);
			flightBook.setTransactionKey(transactionKey);
			flightBook.setCompanyId(appKeyVo.getCompanyId());
			flightBook.setConfigId(appKeyVo.getConfigId());
			flightBook.setCompanyName(company.getCompanyname());
			lookBookDao.insertIntoTable(flightBook);
			flightLookBook.setAppkey(appKeyVo.getAppKey());
			flightLookBook=lookBookDao.CheckAndFetchFlightLookBookByAppKey(flightLookBook);

			if(flightLookBook!=null && flightLookBook.getId()>0){
				lookBookDao.updateIntoTable(flightLookBook, "booking");
			}
			else{
				flightLookBook.setAppkey(appKeyVo.getAppKey());
				flightLookBook.setTotalBookedCount(1);
				flightLookBook.setTotalSearchCount(0);
				flightLookBook.setCompanyId(appKeyVo.getCompanyId());
				flightLookBook.setConfigId(appKeyVo.getConfigId());
				flightLookBook.setCompanyName(company.getCompanyname());
				lookBookDao.insertIntoTable(flightLookBook);
			}
		}
		catch (Exception e) {
		}
		try{
			ipStatus=lookBookDao.CheckAndFetchIpStatus(ip);
			ipStatusHistory=lookBookDao.CheckAndfetchIpHistory(ip);
		}
		catch (Exception e) {
		}

		if(ipStatus!=null && ipStatus.getId()>0){
			if( ipStatus.isBlockStatus() || ipStatus.getTotalBookedCount()>=100){
				throw new FlightException(ErrorCodeCustomerEnum.LimitExceedException,ErrorMessages.USEREXCEEDSSEARCHLIMIT); 
			} 
			else{
				ipStatus.setLastDate(currentDate);
				ipStatus.setTotalBookedCount(ipStatus.getTotalBookedCount()+1);
				if(ipStatus.getTotalSearchCount()==100)
					ipStatus.setBlockStatus(true);
				try{
					lookBookDao.updateIpStatus(ipStatus);
				}
				catch (Exception e) {
				}
			}
		}
		if(ipStatusHistory!=null && ipStatusHistory.getId()>0){
			ipStatusHistory.setLastDate(currentDate);
			ipStatusHistory.setTotalBookedCount(ipStatusHistory.getTotalBookedCount()+1);
			try{
				lookBookDao.updateIpHistory(ipStatusHistory);
			}
			catch (Exception e) {
			}
		}

		return flightBook;
	}

	public Map<FlightFareAlertDetail,List<Segments>> getFlightFareAlertDetailOneway(FlightCustomerDetails flightCustomerDetails,String lowFareFlightIndex,FlightBookingResponse flightBookingResponse,FlightOrderRow flightOrderRow,String reasons){
		Map<FlightFareAlertDetail,List<Segments>> flightFareAlertDetailList = new LinkedHashMap<>();		
		try{

			List<String> fligtindexlist = UapiServiceCall.getfligtindexlist(lowFareFlightIndex);
			if(fligtindexlist.size() > 0 && fligtindexlist.size() == 1){
				String flightIndex = fligtindexlist.get(fligtindexlist.size() - 1);
				FareFlightSegment fareFlightSegment = flightBookingDao.getLowPriceFlightFareSegment(flightCustomerDetails.getTransactionkey(), flightIndex, flightTempAirSegmentDAO);
				if(fareFlightSegment != null){
					FlightFareAlertDetail flightFareAlertDetail = new FlightFareAlertDetail();
					flightFareAlertDetail.setAirlineName(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getName());
					flightFareAlertDetail.setArrivalTime(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getArrTime());
					flightFareAlertDetail.setDepartureTime(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getDepTime());
					flightFareAlertDetail.setDestinationcode(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getDest());
					flightFareAlertDetail.setOriginCode(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getOri());
					flightFareAlertDetail.setFareClass(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getFareClass());
					flightFareAlertDetail.setFlightNo(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getFlight().getNumber());

					// Check Connecting Flight is Available 
					if(fareFlightSegment.getFlightSegmentsGroups().get(fareFlightSegment.getFlightSegmentsGroups().size() - 1).getFlightSegments().get(fareFlightSegment.getFlightSegmentsGroups().size() - 1).getSegments().size() == 1){
						flightFareAlertDetail.setIsConnFlightAvailable(false);
					}else{
						flightFareAlertDetail.setIsConnFlightAvailable(true);						
					}

					flightFareAlertDetail.setTotalFare(new BigDecimal(fareFlightSegment.getTotalPrice()));
					flightFareAlertDetail.setCompanyId(Integer.parseInt(flightOrderRow.getCompanyId()));
					flightFareAlertDetail.setConfigId(Integer.parseInt(flightOrderRow.getConfigId()));
					flightFareAlertDetail.setCreatedbyuserid(flightOrderRow.getUserId());
					flightFareAlertDetail.setOrderid(flightOrderRow.getOrderId());
					flightFareAlertDetail.setReasons(reasons);
					flightFareAlertDetail.setTravelDate(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getDepDate());
					flightFareAlertDetail.setBookingDate(flightOrderRow.getBookingDate());
					// Add to Map
					flightFareAlertDetailList.put(flightFareAlertDetail, fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments());

				}
			}
		}catch(Exception e){
			logger.info("getFlightFareAlertDetailOneway Exception----  " +e);
		}
		return flightFareAlertDetailList;		
	}

	public Map<FlightFareAlertDetail,List<Segments>> getFlightFareAlertDetailRoundTrip(FlightCustomerDetails flightCustomerDetails,String lowFareFlightIndex,FlightBookingResponse flightBookingResponse,FlightOrderRow flightOrderRow,String reasons){
		Map<FlightFareAlertDetail,List<Segments>> flightFareAlertDetailList = new LinkedHashMap<>();
		try{


			List<String> fligtindexlist = UapiServiceCall.getfligtindexlist(lowFareFlightIndex);			
			if(fligtindexlist.size() > 0 && fligtindexlist.size() > 1){
				String firstFlightIndex = fligtindexlist.get(0);
				FareFlightSegment fareFlightSegmentonward = flightBookingDao.getLowPriceFlightFareSegment(flightCustomerDetails.getTransactionkey(), firstFlightIndex, flightTempAirSegmentDAO);
				if(fareFlightSegmentonward != null){
					FlightFareAlertDetail flightFareAlertDetail = new FlightFareAlertDetail();

					flightFareAlertDetail.setAirlineName(fareFlightSegmentonward.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getName());
					flightFareAlertDetail.setArrivalTime(fareFlightSegmentonward.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getArrTime());
					flightFareAlertDetail.setDepartureTime(fareFlightSegmentonward.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getDepTime());
					flightFareAlertDetail.setDestinationcode(fareFlightSegmentonward.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getDest());
					flightFareAlertDetail.setOriginCode(fareFlightSegmentonward.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getOri());
					flightFareAlertDetail.setFareClass(fareFlightSegmentonward.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getFareClass());
					flightFareAlertDetail.setFlightNo(fareFlightSegmentonward.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getFlight().getNumber());

					// Check Connecting Flight is Available 
					if(fareFlightSegmentonward.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().size() == 1){
						flightFareAlertDetail.setIsConnFlightAvailable(false);
					}else{
						flightFareAlertDetail.setIsConnFlightAvailable(true);						
					}
					flightFareAlertDetail.setTotalFare(new BigDecimal(fareFlightSegmentonward.getTotalPrice()));
					flightFareAlertDetail.setCompanyId(Integer.parseInt(flightOrderRow.getCompanyId()));
					flightFareAlertDetail.setConfigId(Integer.parseInt(flightOrderRow.getConfigId()));
					flightFareAlertDetail.setCreatedbyuserid(flightOrderRow.getUserId());
					flightFareAlertDetail.setOrderid(flightOrderRow.getOrderId());
					flightFareAlertDetail.setReasons(reasons);
					flightFareAlertDetail.setTravelDate(fareFlightSegmentonward.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getDepDate());
					flightFareAlertDetail.setBookingDate(flightOrderRow.getBookingDate());
					// Add to Map
					flightFareAlertDetailList.put(flightFareAlertDetail, fareFlightSegmentonward.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments());

				}

				String secondFlightIndex = fligtindexlist.get(0);
				FareFlightSegment fareFlightSegment = flightBookingDao.getLowPriceFlightFareSegment(flightCustomerDetails.getTransactionkey(), secondFlightIndex, flightTempAirSegmentDAO);
				if(fareFlightSegment != null){
					FlightFareAlertDetail flightFareAlertDetail = new FlightFareAlertDetail();

					flightFareAlertDetail.setAirlineName(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getName());
					flightFareAlertDetail.setArrivalTime(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getArrTime());
					flightFareAlertDetail.setDepartureTime(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getDepTime());
					flightFareAlertDetail.setDestinationcode(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getDest());
					flightFareAlertDetail.setOriginCode(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getOri());
					flightFareAlertDetail.setFareClass(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getFareClass());
					flightFareAlertDetail.setFlightNo(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getFlight().getNumber());

					// Check Connecting Flight is Available 
					if(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().size() == 1){
						flightFareAlertDetail.setIsConnFlightAvailable(false);
					}else{
						flightFareAlertDetail.setIsConnFlightAvailable(true);						
					}
					flightFareAlertDetail.setTotalFare(new BigDecimal(fareFlightSegment.getTotalPrice()));
					flightFareAlertDetail.setCompanyId(Integer.parseInt(flightOrderRow.getCompanyId()));
					flightFareAlertDetail.setConfigId(Integer.parseInt(flightOrderRow.getConfigId()));
					flightFareAlertDetail.setCreatedbyuserid(flightOrderRow.getUserId());
					flightFareAlertDetail.setOrderid(flightOrderRow.getOrderId());
					flightFareAlertDetail.setReasons(reasons);
					flightFareAlertDetail.setTravelDate(fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getDepDate());
					flightFareAlertDetail.setBookingDate(flightOrderRow.getBookingDate());
					// Add to Map
					flightFareAlertDetailList.put(flightFareAlertDetail, fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments());

				}

			}


		}catch(Exception e){
			logger.info("getFlightFareAlertDetail Exception----  " +e);
		}
		return flightFareAlertDetailList;		
	}

	public static List<FlightFareAlertConnectingFlight> getFlightFareAlertConnectingFlight(FlightFareAlertDetail flightFareAlertDetail,List<Segments> segmentsList){
		List<FlightFareAlertConnectingFlight> flightFareAlertConnectingFlightList = new ArrayList<>();
		for(int i = 1; i<segmentsList.size(); ){
			FlightFareAlertConnectingFlight flightFareAlertConnectingFlight = new FlightFareAlertConnectingFlight();
			Segments Segment = segmentsList.get(i);
			flightFareAlertConnectingFlight.setAirlineName(Segment.getCarrier().getName());
			flightFareAlertConnectingFlight.setArrivalTime(Segment.getArrTime());
			flightFareAlertConnectingFlight.setDepartureTime(Segment.getDepTime());
			flightFareAlertConnectingFlight.setDestinationcode(Segment.getDest());
			flightFareAlertConnectingFlight.setOriginCode(Segment.getOri());
			flightFareAlertConnectingFlight.setFareClass(Segment.getFareClass());
			flightFareAlertConnectingFlight.setFlightNo(Segment.getFlight().getNumber());
			flightFareAlertConnectingFlight.setFlightFareAlertDetail(flightFareAlertDetail);
			flightFareAlertConnectingFlightList.add(flightFareAlertConnectingFlight);
			i++;
		}		
		return flightFareAlertConnectingFlightList;
	}

}


