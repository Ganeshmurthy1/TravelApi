/**
@Author ilyas
05-10-2015 
UapiServiceCall.java
 */
/**
 * 
 */
package com.tayyarah.flight.util.api.bluestar;

import static javax.xml.bind.DatatypeConverter.printBase64Binary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.tayyarah.apiconfig.model.BluestarConfig;
import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.util.FileUtil;
import com.tayyarah.common.util.soap.SoapClient;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.entity.FlightAirPriceDetailsTemp;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.model.BluestarSearchData;
import com.tayyarah.flight.model.FlightBookingResponse;
import com.tayyarah.flight.model.FlightCustomerDetails;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.PassengerDetails;
import com.tayyarah.flight.model.SearchFlightResponse;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.flight.util.api.travelport.FlightBookingResponseParser;
import com.tayyarah.user.entity.WalletAmountTranferHistory;


public class BluestarServiceCall {
	static final Logger logger = Logger.getLogger(BluestarServiceCall.class);

	public static FlightBookingResponse callBookingService(FlightBookingResponse flightBookingResponse,
			OrderCustomer orderCustomer,
			FlightPriceResponse flightPriceResponse,
			List<FlightOrderCustomer> flightOrderCustomers, String orderId,
			String CountryCode, FlightBookingDao FBDAO,EmailDao emaildao, String transactionkey,
			String paymode,
			WalletAmountTranferHistory walletAmountTranferHistory,int count,boolean IsspecialRoundtrip,BluestarConfig bluestarConfig)
					throws Exception {		
		if(!bluestarConfig.isTest()){
			flightBookingResponse = FlightBookingResponseParser.parseFlightBookingResponseTesting(flightBookingResponse,orderId,FBDAO,emaildao,transactionkey,paymode,walletAmountTranferHistory,count);

		}else{
			StringBuilder requestData = BluestarBookTicketRequestBuilder
					.createAirpriceRequest(orderCustomer, flightPriceResponse,
							flightOrderCustomers,count,IsspecialRoundtrip,bluestarConfig);
			SOAPMessage soapMessageReq = SoapClient.buildSoapMsgFromStr(requestData);
			SOAPMessage soapMessageRes = callService(soapMessageReq,bluestarConfig);
			ByteArrayOutputStream in = new ByteArrayOutputStream();
			try {
				soapMessageRes.writeTo(in);
				logger.info("Price Response :" + in);
			} catch (IOException e) {
				logger.error("IOException", e);
			}
			String output = in.toString();			
			// output="<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><GetFlightAvailibilityResponse xmlns=\"http://tempuri.org/\"><GetFlightAvailibilityResult>&lt;GetFlightAvailibilityResponse&gt;&lt;FlightDetails&gt;[[SrNo,AirlineCode,FlightNo,FromAirportCode),ToAirportCode),DepDate,DepTime,ArrDate,ArrTime,FlightClass,FlightTime,TotalAmount,TaxAmount,Stops,ValCarrier,FromTerminal,ToTerminal,MainClass,FareBasis,AgencyCharge,FareType,AvailSeats,TrackNo],[1SAO,SG, 160,BOM,DEL,29/12/2015,06:05,29/12/2015,08:00,RS,115,7758,2498,0,SG,1B,1C,Y,CSAVER,0,R,0,0$22|34|1SAO],[2SAO,SG, 160,BOM,DEL,29/12/2015,06:05,29/12/2015,08:00,HF,115,15152,2890,0,SG,1B,1C,Y,CFLEX,0,R,0,0$22|34|2SAO],[3SAO,SG, 162,BOM,DEL,29/12/2015,07:35,29/12/2015,09:35,RS,120,7758,2498,0,SG,1B,1C,Y,CSAVER,0,R,0,0$22|34|3SAO],[4SAO,SG, 162,BOM,DEL,29/12/2015,07:35,29/12/2015,09:35,HF,120,15152,2890,0,SG,1B,1C,Y,CFLEX,0,R,0,0$22|34|4SAO],[5SAO,SG, 164,BOM,DEL,29/12/2015,11:15,29/12/2015,13:15,RS,120,7758,2498,0,SG,1B,1C,Y,CSAVER,0,R,0,0$22|34|5SAO],[6SAO,SG, 164,BOM,DEL,29/12/2015,11:15,29/12/2015,13:15,HF,120,15152,2890,0,SG,1B,1C,Y,CFLEX,0,R,0,0$22|34|6SAO],[7SAO,SG, 158,BOM,DEL,29/12/2015,18:20,29/12/2015,20:25,RS,125,7758,2498,0,SG,1B,1C,Y,CSAVER,0,R,0,0$22|34|7SAO],[8SAO,SG, 158,BOM,DEL,29/12/2015,18:20,29/12/2015,20:25,HF,125,15152,2890,0,SG,1B,1C,Y,CFLEX,0,R,0,0$22|34|8SAO]]&lt;/FlightDetails&gt;&lt;FareDetails&gt;[[SrNo,AdultBaseFare,ChildBaseFare,InfantBaseFare,AdultTax,ChildTax,InfantTax,AdultFuelCharges,ChildFuelCharges,InfantFuelCharges,AdultPassengerServiceFee,ChildPassengerServiceFee,InfantPassengerServiceFee,AdultTransactionFee,ChildTransactionFee,InfantTransactionFee,AdultServiceCharges,ChildServiceCharges,InfantServiceCharges,AdultAirportTax,ChildAirportTax,InfantAirportTax,AdultAirportDevelopmentFee,AdultCuteFee,AdultConvenienceFee,AdultSkyCafeMeals,ChildAirportDevelopmentFee,ChildCuteFee,ChildConvenienceFee,ChildSkyCafeMeals,InfantAirportDevelopmentFee,InfantCuteFee,InfantConvenienceFee,InfantSkyCafeMeals,TotalAmount,TotalFlightCommissionAmount,TDSAmount,ServiceTax,ServiceCharge,InfantFuelFeeMarkup,InfantCuteFeeMarkup],[1SAO,2038,2038,1184,640,640,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7758,100,10,12.36,0,0,0],[2SAO,5539,5539,1184,836,836,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15152,100,10,12.36,0,0,0],[3SAO,2038,2038,1184,640,640,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7758,100,10,12.36,0,0,0],[4SAO,5539,5539,1184,836,836,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15152,100,10,12.36,0,0,0],[5SAO,2038,2038,1184,640,640,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7758,100,10,12.36,0,0,0],[6SAO,5539,5539,1184,836,836,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15152,100,10,12.36,0,0,0],[7SAO,2038,2038,1184,640,640,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7758,100,10,12.36,0,0,0],[8SAO,5539,5539,1184,836,836,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15152,100,10,12.36,0,0,0]]&lt;/FareDetails&gt;&lt;AirlineList&gt;[[AirlineCode,AirlineName],[SG,Spice Jet]]&lt;/AirlineList&gt;&lt;AirportList&gt;[[AirportCode,AirportName],[BOM,Mumbai],[DEL,Delhi]]&lt;/AirportList&gt;&lt;/GetFlightAvailibilityResponse&gt;</GetFlightAvailibilityResult></GetFlightAvailibilityResponse></soap:Body></soap:Envelope>";
			output = output.replaceAll("&lt;", "<");
			output = output.replaceAll("&gt;", ">");	
			String BookTicketResponse = output.substring(
					output.indexOf("<BookTicketResponse>") + 20,
					output.indexOf("</BookTicketResponse>"));			
			try{
				FileUtil.writeSoap("flight", "bluestar", "booking", false, soapMessageReq, String.valueOf("booking_request"));
				FileUtil.writeSoap("flight", "bluestar", "booking", true, output.toString(), String.valueOf("booking_response"));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}
			flightBookingResponse = BluestarBookTicketResponseParser
					.parseFlightBookingResponse(flightBookingResponse,BookTicketResponse, orderId, FBDAO,emaildao,
							transactionkey, paymode, walletAmountTranferHistory,count);
		}
		return flightBookingResponse;
	}

	public static FlightPriceResponse getFlightPriceResponse(String pricekey,
			FlightTempAirSegmentDAO tempDAO) throws Exception {
		FlightDataBaseServices DBS = new FlightDataBaseServices();
		FlightAirPriceDetailsTemp airPriceDetails = DBS.getAirPriceDetails(pricekey,
				tempDAO);
		byte[] fpr = airPriceDetails.getFlightPriceResponse();
		FlightPriceResponse flightPriceResponse = (FlightPriceResponse) FlightDataBaseServices
				.convertByteArrayToObject(fpr);
		return flightPriceResponse;
	}

	public static SOAPMessage callService(SOAPMessage reqSoapMessage,
			BluestarConfig bluestarConfig)
					throws UnsupportedOperationException, SOAPException {
		String serviceURL = bluestarConfig.getUrl();
		SoapClient client = new SoapClient();
		return client.sendSoapMessage(reqSoapMessage, serviceURL);
	}

	public static SOAPMessage addSecureHeader(SOAPMessage reqsoapMessage,
			BluestarConfig bluestarConfig) throws SOAPException {
		String userName = "username";
		String password = "pass";
		String authorization = printBase64Binary(new String(userName + ":"
				+ password).toString().getBytes());
		String contentType = printBase64Binary(new String(
				"text/xml;charset=ISO-8859-1").toString().getBytes());
		reqsoapMessage.getMimeHeaders().addHeader("Authorization",
				"Basic " + authorization);
		reqsoapMessage.getMimeHeaders().addHeader("Content-Type", contentType);
		ByteArrayOutputStream in = new ByteArrayOutputStream();
		try {
			reqsoapMessage.writeTo(in);
			// System.out.println("soap header  :"+in);
		} catch (IOException e) {
			logger.error("IOException", e);
		} catch (SOAPException e) {
			logger.error("SOAPException", e);
		}
		return reqsoapMessage;
	}

	public static void createFlightCustomerDetails(FlightCustomerDetails FCD,
			Flightsearch flightsearch) {
		List<PassengerDetails> passengerdetailsList = new ArrayList<PassengerDetails>();
		buildPassengerDetails(passengerdetailsList, flightsearch);
		FCD.setPassengerdetailsList(passengerdetailsList);
	}

	private static void buildPassengerDetails(
			List<PassengerDetails> passengerdetailsList,
			Flightsearch flightsearch) {
		for (int i = 0; i < flightsearch.getAdult(); i++) {
			PassengerDetails passengerDetails = new PassengerDetails();
			passengerDetails.setPassengerId((new UID()).toString());
			passengerDetails.setPassengerTypeCode("ADT");
			passengerdetailsList.add(passengerDetails);
		}
		for (int i = 0; i < flightsearch.getKid(); i++) {
			PassengerDetails passengerDetails = new PassengerDetails();
			passengerDetails.setPassengerId((new UID()).toString());
			passengerDetails.setPassengerTypeCode("CHD");
			passengerdetailsList.add(passengerDetails);
		}
		for (int i = 0; i < flightsearch.getInfant(); i++) {
			PassengerDetails passengerDetails = new PassengerDetails();
			passengerDetails.setPassengerId((new UID()).toString());
			passengerDetails.setPassengerTypeCode("INF");
			passengerdetailsList.add(passengerDetails);
		}
	}

	public static List<String> getfligtindexlist(String flightindex) {
		List<String> fligtindexlist = new ArrayList<String>();
		String Tempflightindex = flightindex;
		while (Tempflightindex.length() > 0) {
			if (Tempflightindex.indexOf("_") != -1) {
				String Newflightindex = Tempflightindex.substring(0,
						Tempflightindex.indexOf("_"));
				fligtindexlist.add(Newflightindex);
				Tempflightindex = Tempflightindex.substring(Newflightindex
						.length() + 1);
			} else {
				fligtindexlist.add(Tempflightindex);
				break;
			}
		}
		return fligtindexlist;
	}

	public static SearchFlightResponse callSearchService(
			Flightsearch flightsearch,
			Map<String,List<FlightMarkUpConfig>> markupMap,
			Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> MapList, MoneyExchangeDao moneydao,FlightTempAirSegmentDAO flightTempAirSegmentDAO,BluestarConfig bluestarConfig)
					throws Exception {
		StringBuilder requestData = BluestarGetFlightAvailibilityRequestBuilder.createSearchRequest(flightsearch,bluestarConfig);
		logger.info("requestData :" + requestData);
		SOAPMessage soapMessageReq = SoapClient.buildSoapMsgFromStr(requestData);
		/*editing by Manish*/
		String[] search_key=null;
		try{
			search_key = flightsearch.getTransactionKey().split(":");
		} catch (Exception e) {
			search_key=new UID().toString().split(":");
			e.printStackTrace();
		}
		try{
			FileUtil.writeSoap("flight", "bluestar", "search", false, requestData.toString(), String.valueOf(search_key[1]+search_key[2]));
		} catch (Exception e) {
			logger.error(" The filename, directory name ", e);
		}

		SOAPMessage soapMessageRes = callService(soapMessageReq,bluestarConfig);
		try{
			FileUtil.writeSoap("flight", "bluestar", "search", true, soapMessageRes,  String.valueOf(search_key[1]+search_key[2]));
		} catch (Exception e) {
			logger.error(" The filename, directory name ", e);
		}

		ByteArrayOutputStream flightSearchXMlDataStream = new ByteArrayOutputStream();
		try {
			soapMessageRes.writeTo(flightSearchXMlDataStream);
			logger.info("Search Response :" + flightSearchXMlDataStream);
		} catch (IOException e) {
			logger.error("IOException", e);
		}		
		SearchFlightResponse searchFlightResponse = null;
		BluestarSearchData  bluestarSearchData = BluestarGetFlightAvailabilityXMLObjectCoversion.createSearchFlightResponseFromXML(flightSearchXMlDataStream,flightsearch);	
		try {
			if(flightsearch.getTripType().equalsIgnoreCase("O") && !flightsearch.isDomestic())  {
				searchFlightResponse = BluestarGetFlightAvailabilityResponseParser
						.parseResponseOneway(bluestarSearchData, markupMap,
								flightsearch, AirlineNameMap, MapList,
								moneydao);

			}	if((flightsearch.getTripType().equalsIgnoreCase("O") && flightsearch.isDomestic()) || flightsearch.isSpecialSearch()) {
				searchFlightResponse = BluestarGetFlightAvailabilityResponseParser
						.parseResponseOnewayDomestic(bluestarSearchData, markupMap,
								flightsearch, AirlineNameMap, MapList,
								moneydao);
			}	if(flightsearch.getTripType().equalsIgnoreCase("R")) {
				searchFlightResponse = BluestarGetFlightAvailabilityResponseParser
						.parseResponseRoundTrip(bluestarSearchData, markupMap,
								flightsearch, AirlineNameMap, MapList,
								moneydao);
			}			

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}		
		try{
			/*Saving Bluestar Search Response in DB*/
			if(!flightsearch.isDynamicMarkup()){			
				if(flightsearch.isSpecialSearch()){
					FlightDataBaseServices dbService = new FlightDataBaseServices();
					byte[] FSR = null;
					try {
						FSR = FlightDataBaseServices.convertObjectToByteArray(bluestarSearchData);
					} catch (IOException e1) {					
						logger.error("IOException ",e1);
					}
					dbService.storeFlightSearchApiResponses(flightsearch.getSearchKey(), FSR, flightTempAirSegmentDAO,flightsearch,"BlueStar");
				}else if(!flightsearch.isSpecialSearch()){
					if(!(flightTempAirSegmentDAO.CheckSearchKeyExists(flightsearch.getSearchKey(),"BlueStar"))){					
						FlightDataBaseServices dbService = new FlightDataBaseServices();
						byte[] FSR = null;
						try {            			
							FSR = FlightDataBaseServices.convertObjectToByteArray(bluestarSearchData);
						} catch (IOException e1) {						
							logger.error("IOException ",e1);
						}
						dbService.storeFlightSearchApiResponses(flightsearch.getSearchKey(), FSR, flightTempAirSegmentDAO,flightsearch,"BlueStar");
					}
				}
				else{
					logger.info("Search Response not inserted");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}		
		return searchFlightResponse;
	}
}
