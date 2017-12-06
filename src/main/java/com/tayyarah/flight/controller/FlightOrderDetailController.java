package com.tayyarah.flight.controller;


import java.util.ArrayList;
import java.util.Map;                                      

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.common.exception.BaseException;
import com.tayyarah.common.exception.RestError;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.flight.dao.AirportDAO;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.model.BookingDetails;
import com.tayyarah.flight.model.FlightBookingResponse;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.flight.util.FlightWebServiceEndPointValidator;
import com.tayyarah.flight.util.api.travelport.UapiServiceCall;


@RestController
@RequestMapping("/order")
public class FlightOrderDetailController {
	static final Logger logger = Logger.getLogger(FlightOrderDetailController.class);
	private  FlightWebServiceEndPointValidator validator = new FlightWebServiceEndPointValidator();
	private  FlightDataBaseServices DBS = new FlightDataBaseServices();		

	@Autowired
	FlightTempAirSegmentDAO TempDAO; 
	@Autowired
	FlightBookingDao FBDAO;
	@Autowired
	CompanyDao CDAO;
	@Autowired
	AirportDAO ADAO;	

	@RequestMapping(value="/detail",headers={"Accept=application/json"},produces={"application/json"})
	public @ResponseBody FlightBookingResponse getFareRuleInfo(@RequestParam(value="app_key") String app_key,@RequestParam(value="pg_id") String pg_id,HttpServletResponse response){

		ResponseHeader.setResponse(response);
		AppControllerUtil.validateAppKey(CDAO, app_key);       
		FlightBookingResponse flightBookingResponse = null; 
		String result="<orderid>invalid</orderid><count>0</count>";		
		try {
			result = UapiServiceCall.getOrderId(pg_id,FBDAO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception ",e);
		}		
		String orderid=result.substring(result.indexOf("<orderid>")+9, result.indexOf("</orderid>"));
		String count=result.substring(result.indexOf("<count>")+7, result.indexOf("</count>"));		
		BookingDetails bookingDetails = null;
		try {
			bookingDetails=UapiServiceCall.getBookingDetailsToDb(orderid,FBDAO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception ",e);
		}

		flightBookingResponse=new FlightBookingResponse();
		flightBookingResponse.setFareFlightSegment(bookingDetails.getFlightPriceResponse().getFareFlightSegment());
		flightBookingResponse.setPassengerFareBreakUps(bookingDetails.getFlightPriceResponse().getPassengerFareBreakUps());
		flightBookingResponse.setTransactionKey(bookingDetails.getTransactionkey());
		flightBookingResponse.setFlightsearch(bookingDetails.getFlightPriceResponse().getFlightsearch());
		flightBookingResponse.setCountry(bookingDetails.getCountrycode());
		flightBookingResponse.setFlightCustomerDetails(bookingDetails.getFlightCustomerDetails());
		flightBookingResponse.setCache(false);
		flightBookingResponse.setConfirmationNumber(orderid);
		flightBookingResponse.setPaymentStatus(false);
		flightBookingResponse.setPgID(pg_id);
		return flightBookingResponse;
	}
	
	@ExceptionHandler(BaseException.class)
	public @ResponseBody RestError handleCustomException (BaseException ex, HttpServletResponse response) {
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return ex.transformException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}

