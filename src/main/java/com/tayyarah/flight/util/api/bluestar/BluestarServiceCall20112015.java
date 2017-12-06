package com.tayyarah.flight.util.api.bluestar;
/**
@Author ilyas
05-10-2015 
UapiServiceCall.java
 *//*
*//**
 * 
 *//*
package com.lintas.flight.util.api.bluestar;

import static javax.xml.bind.DatatypeConverter.printBase64Binary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.lintas.common.entity.model.OrderCustomer;
import com.lintas.common.util.email.EmailDao;
import com.lintas.common.util.helper.SoapClient;
import com.lintas.flight.model.FlightCustomerDetails;
import com.lintas.flight.model.PassengerDetails;
import com.lintas.flight.bluestar.config.BluestarConfig;
import com.lintas.flight.bluestar.config.BluestarConstants;
import com.lintas.flight.dao.FlightBookingDao;
import com.lintas.flight.dao.TempAirSegmentDAO;
import com.lintas.flight.exception.ErrorCodeCustomerEnum;
import com.lintas.flight.exception.ErrorMessages;
import com.lintas.flight.exception.FlightException;
import com.lintas.flight.entity.model.AirPriceDetails;
import com.lintas.flight.entity.model.FlightOrderCustomer;
import com.lintas.flight.entity.model.SearchDetails;
import com.lintas.flight.entity.model.WalletAmountTranferHistory;
import com.lintas.flight.model.FareFlightSegment;
import com.lintas.flight.model.FareRules;
import com.lintas.flight.model.FlightBookingResponse;
import com.lintas.flight.model.FlightMarkUpConfig;
import com.lintas.flight.model.FlightPriceResponse;
import com.lintas.flight.model.FlightSegments;
import com.lintas.flight.model.Flightsearch;
import com.lintas.flight.model.MarkupCommissionDetails;
import com.lintas.flight.model.Passenger;
import com.lintas.flight.model.SearchFlightResponse;
import com.lintas.flight.model.UAPISearchFlightKeyMap;
import com.lintas.flight.util.api.travelport.DataBaseServices;

public class BluestarServiceCall20112015 {
	static final Logger logger = Logger.getLogger(BluestarServiceCall20112015.class);

	public static FlightPriceResponse callAirPriceService(String flightindex,
			String searchkey, Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> MapList, FlightTempAirSegmentDAO tempDAO)
			throws Exception {
		FlightDataBaseServices DBS = new FlightDataBaseServices();
		SearchDetails searchDetails = DBS.getUAPISearchFlightKeyMap(searchkey,
				tempDAO);
		byte[] abc = searchDetails.getUapiSearchFlightKeyMap();
		String transaction_key = searchDetails.getTransactionkey();
		UAPISearchFlightKeyMap uapiSearchFlightKeyMap = FlightDataBaseServices
				.convertByteArrayToUAPISearchFlightKeyMap(abc);
		Flightsearch flightsearch = uapiSearchFlightKeyMap.getFlightsearch();
		List<String> fligtindexlist = getfligtindexlist(flightindex);
		Map<String, FareFlightSegment> FareFlightSegmentMap = uapiSearchFlightKeyMap
				.getFareFlightSegmentMap();
		LinkedHashMap<String, FlightSegments> FlightSegmentstMap = uapiSearchFlightKeyMap
				.getFlightSegmentstMap();
		Map<String, String[]> faredetailMap = uapiSearchFlightKeyMap
				.getFaredetailMap();
		StringBuilder requestData = null;
		String tempflightIndex = fligtindexlist.get(0);
		requestData = BluestarGetFlightAvailibilityRequestBuilder
				.createVerifyFlightDetailRequestOneway(FlightSegmentstMap
						.get(tempflightIndex).getSegments().get(0).getTrackno());
		SOAPMessage soapMessageReq = SoapClient
				.buildSoapMsgFromStr(requestData);
		SOAPMessage soapMessageRes = callService(soapMessageReq,
				new BluestarConfig());
		ByteArrayOutputStream in = new ByteArrayOutputStream();
		try {
			soapMessageRes.writeTo(in);
			logger.info("Search Response :" + in);
		} catch (IOException e) {
			logger.error("IOException", e);
		}

		String output = in.toString();
		// String
		// output="<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><GetFlightAvailibilityResponse xmlns=\"http://tempuri.org/\"><GetFlightAvailibilityResult>&lt;GetFlightAvailibilityResponse&gt;&lt;FlightDetails&gt;[[SrNo,AirlineCode,FlightNo,FromAirportCode,ToAirportCode,DepDate,DepTime,ArrDate,ArrTime,FlightClass,FlightTime,TotalAmount,TaxAmount,Stops,ValCarrier,FromTerminal,ToTerminal,MainClass,FareBasis,AgencyCharge,FareType,AvailSeats,TrackNo],[1SAO,SG, 160,BOM,DEL,29/12/2015,06:05,29/12/2015,08:00,RS,115,7758,2498,0,SG,1B,1C,Y,CSAVER,0,R,0,0$22|34|1SAO],[2SAO,SG, 160,BOM,DEL,29/12/2015,06:05,29/12/2015,08:00,HF,115,15152,2890,0,SG,1B,1C,Y,CFLEX,0,R,0,0$22|34|2SAO],[3SAO,SG, 162,BOM,DEL,29/12/2015,07:35,29/12/2015,09:35,RS,120,7758,2498,0,SG,1B,1C,Y,CSAVER,0,R,0,0$22|34|3SAO],[4SAO,SG, 162,BOM,DEL,29/12/2015,07:35,29/12/2015,09:35,HF,120,15152,2890,0,SG,1B,1C,Y,CFLEX,0,R,0,0$22|34|4SAO],[5SAO,SG, 164,BOM,DEL,29/12/2015,11:15,29/12/2015,13:15,RS,120,7758,2498,0,SG,1B,1C,Y,CSAVER,0,R,0,0$22|34|5SAO],[6SAO,SG, 164,BOM,DEL,29/12/2015,11:15,29/12/2015,13:15,HF,120,15152,2890,0,SG,1B,1C,Y,CFLEX,0,R,0,0$22|34|6SAO],[7SAO,SG, 158,BOM,DEL,29/12/2015,18:20,29/12/2015,20:25,RS,125,7758,2498,0,SG,1B,1C,Y,CSAVER,0,R,0,0$22|34|7SAO],[8SAO,SG, 158,BOM,DEL,29/12/2015,18:20,29/12/2015,20:25,HF,125,15152,2890,0,SG,1B,1C,Y,CFLEX,0,R,0,0$22|34|8SAO]]&lt;/FlightDetails&gt;&lt;FareDetails&gt;[[SrNo,AdultBaseFare,ChildBaseFare,InfantBaseFare,AdultTax,ChildTax,InfantTax,AdultFuelCharges,ChildFuelCharges,InfantFuelCharges,AdultPassengerServiceFee,ChildPassengerServiceFee,InfantPassengerServiceFee,AdultTransactionFee,ChildTransactionFee,InfantTransactionFee,AdultServiceCharges,ChildServiceCharges,InfantServiceCharges,AdultAirportTax,ChildAirportTax,InfantAirportTax,AdultAirportDevelopmentFee,AdultCuteFee,AdultConvenienceFee,AdultSkyCafeMeals,ChildAirportDevelopmentFee,ChildCuteFee,ChildConvenienceFee,ChildSkyCafeMeals,InfantAirportDevelopmentFee,InfantCuteFee,InfantConvenienceFee,InfantSkyCafeMeals,TotalAmount,TotalFlightCommissionAmount,TDSAmount,ServiceTax,ServiceCharge,InfantFuelFeeMarkup,InfantCuteFeeMarkup],[1SAO,2038,2038,1184,640,640,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7758,100,10,12.36,0,0,0],[2SAO,5539,5539,1184,836,836,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15152,100,10,12.36,0,0,0],[3SAO,2038,2038,1184,640,640,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7758,100,10,12.36,0,0,0],[4SAO,5539,5539,1184,836,836,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15152,100,10,12.36,0,0,0],[5SAO,2038,2038,1184,640,640,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7758,100,10,12.36,0,0,0],[6SAO,5539,5539,1184,836,836,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15152,100,10,12.36,0,0,0],[7SAO,2038,2038,1184,640,640,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7758,100,10,12.36,0,0,0],[8SAO,5539,5539,1184,836,836,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15152,100,10,12.36,0,0,0]]&lt;/FareDetails&gt;&lt;AirlineList&gt;[[AirlineCode,AirlineName],[SG,Spice Jet]]&lt;/AirlineList&gt;&lt;AirportList&gt;[[AirportCode,AirportName],[BOM,Mumbai],[DEL,Delhi]]&lt;/AirportList&gt;&lt;/GetFlightAvailibilityResponse&gt;</GetFlightAvailibilityResult></GetFlightAvailibilityResponse></soap:Body></soap:Envelope>";

		output = output.replaceAll("&lt;", "<");
		output = output.replaceAll("&gt;", ">");
		if (output.indexOf("<FlightDetails>") == -1) {
			throw new FlightException(ErrorCodeCustomerEnum.JAXBException,
					ErrorMessages.NO_AIRPRICE);
		} else {
			String flightdetails = output.substring(
					output.indexOf("<FlightDetails>") + 16,
					output.indexOf("</FlightDetails>") - 1);
			List<String> flightdetaillist = new ArrayList<String>();
			String temp = flightdetails;
			while (temp.length() > 3) {
				String flightdetail = temp.substring(1, temp.indexOf("]"));
				if (temp.indexOf("" + flightdetail + "],") != -1) {
					temp = temp.substring(flightdetail.length() + 3);
				} else {
					temp = "";
				}
				flightdetaillist.add(flightdetail);
			}
			String flightdeatil = flightdetaillist.get(1);
			String trackNo = flightdeatil.substring(flightdeatil
					.lastIndexOf(",") + 1);
			FlightSegmentstMap.get(tempflightIndex).getSegments().get(0)
					.setTrackno(trackNo);
		}
		Map<String, FareRules> FareRulesMap = uapiSearchFlightKeyMap
				.getFareRulesMap();
		Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap = uapiSearchFlightKeyMap
				.getFlightMarkUpConfiglistMap();
		FlightPriceResponse flightPriceResponse = null;
		flightPriceResponse = BluestarAirPriceResponseParser.parseAirPrice(
				FareFlightSegmentMap, FlightSegmentstMap, FareRulesMap,
				FlightMarkUpConfiglistMap, AirlineNameMap, MapList, flightsearch,
				transaction_key, tempDAO,
				uapiSearchFlightKeyMap.getExchangeRate(), fligtindexlist,
				faredetailMap,new MarkupCommissionDetails());
		return flightPriceResponse;
	}

	public static SearchFlightResponse callSearchServiceOld(
			Flightsearch flightsearch,
			Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap,
			Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> MapList, FlightTempAirSegmentDAO tempDAO)
			throws Exception {
		StringBuilder requestData = BluestarGetFlightAvailibilityRequestBuilder
				.createSearchRequest(flightsearch);
		logger.info("requestData :" + requestData);
		SOAPMessage soapMessageReq = SoapClient
				.buildSoapMsgFromStr(requestData);
		SOAPMessage soapMessageRes = callService(soapMessageReq,
				new BluestarConfig());
		ByteArrayOutputStream in = new ByteArrayOutputStream();
		try {
			soapMessageRes.writeTo(in);
			logger.info("Search Response :" + in);
		} catch (IOException e) {
			logger.error("IOException", e);
		}

		String output = in.toString();
		output = output.replaceAll("&lt;", "<");
		output = output.replaceAll("&gt;", ">");
		// String
		// output="<GetFlightAvailibilityResult><GetFlightAvailibilityResponse><FlightDetails>[[SrNo,AirlineCode,FlightNo,FromAirportCode,ToAirportCode,DepDate,DepTime,ArrDate,ArrTime,FlightClass,FlightTime,TotalAmount,TaxAmount,Stops,ValCarrier,FromTerminal,ToTerminal,MainClass,FareBasis,AgencyCharge,FareType,AvailSeats,TrackNo],[1AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,20937,8067,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|1AO],[1AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|1AO],[1AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|1AR],[1AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|1AR],[2AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,20937,8067,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|2AO],[2AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|2AO],[2AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|2AR],[2AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|2AR],[3AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,21717,9367,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|3AO],[3AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|3AO],[3AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|3AR],[3AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|3AR],[4AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,21717,9367,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|4AO],[4AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|4AO],[4AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|4AR],[4AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|4AR],[5AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,21717,9367,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|5AO],[5AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|5AO],[5AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|5AR],[5AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|5AR],[6AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,23457,9367,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|6AO],[6AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|6AO],[6AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|6AR],[6AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|6AR],[7AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,23457,9367,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|7AO],[7AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|7AO],[7AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|7AR],[7AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|7AR],[8AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,22242,9267,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|8AO],[8AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|8AO],[8AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|8AR],[8AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|8AR],[9AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,22242,9267,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|9AO],[9AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|9AO],[9AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|9AR],[9AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|9AR],[10AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,23022,10567,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|10AO],[10AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|10AO],[10AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|10AR],[10AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|10AR],[11AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,23022,10567,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|11AO],[11AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|11AO],[11AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|11AR],[11AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|11AR],[12AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,23022,10567,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|12AO],[12AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|12AO],[12AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|12AR],[12AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|12AR],[13AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,24762,10567,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|13AO],[13AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|13AO],[13AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|13AR],[13AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|13AR],[14AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,24762,10567,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|14AO],[14AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|14AO],[14AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|14AR],[14AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|14AR],[15AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|15AO],[15AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|15AO],[15AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|15AR],[15AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|15AR],[16AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|16AO],[16AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|16AO],[16AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|16AR],[16AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|16AR],[17AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|17AO],[17AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|17AO],[17AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|17AR],[17AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|17AR],[18AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|18AO],[18AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|18AO],[18AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|18AR],[18AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|18AR],[19AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|19AO],[19AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|19AO],[19AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|19AR],[19AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|19AR],[20AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|20AO],[20AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|20AO],[20AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|20AR],[20AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|20AR],[21AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|21AO],[21AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|21AO],[21AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|21AR],[21AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|21AR],[22AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,23637,9367,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|22AO],[22AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|22AO],[22AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|22AR],[22AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|22AR],[23AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,23637,9367,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|23AO],[23AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|23AO],[23AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|23AR],[23AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|23AR],[24AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|24AO],[24AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|24AO],[24AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|24AR],[24AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|24AR],[25AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|25AO],[25AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|25AO],[25AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|25AR],[25AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|25AR],[26AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|26AO],[26AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|26AO],[26AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|26AR],[26AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|26AR],[27AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,26157,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|27AO],[27AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|27AO],[27AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|27AR],[27AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|27AR],[28AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,26157,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|28AO],[28AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|28AO],[28AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|28AR],[28AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|28AR],[29AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|29AO],[29AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|29AO],[29AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|29AR],[29AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|29AR],[30AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|30AO],[30AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|30AO],[30AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|30AR],[30AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|30AR],[31AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|31AO],[31AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|31AO],[31AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|31AR],[31AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|31AR],[32AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|32AO],[32AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|32AO],[32AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|32AR],[32AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|32AR],[33AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|33AO],[33AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|33AO],[33AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|33AR],[33AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|33AR],[34AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|34AO],[34AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|34AO],[34AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|34AR],[34AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|34AR],[35AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|35AO],[35AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|35AO],[35AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|35AR],[35AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|35AR],[36AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|36AO],[36AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|36AO],[36AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|36AR],[36AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|36AR],[37AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|37AO],[37AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|37AO],[37AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|37AR],[37AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|37AR],[38AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|38AO],[38AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|38AO],[38AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|38AR],[38AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|38AR],[39AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|39AO],[39AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|39AO],[39AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|39AR],[39AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|39AR],[40AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|40AO],[40AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|40AO],[40AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|40AR],[40AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|40AR],[41AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|41AO],[41AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|41AO],[41AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|41AR],[41AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|41AR],[42AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|42AO],[42AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|42AO],[42AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|42AR],[42AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|42AR],[43AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,23637,9367,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|43AO],[43AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|43AO],[43AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|43AR],[43AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|43AR],[44AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,23637,9367,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|44AO],[44AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|44AO],[44AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|44AR],[44AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|44AR],[45AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|45AO],[45AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|45AO],[45AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|45AR],[45AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|45AR],[46AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|46AO],[46AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|46AO],[46AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|46AR],[46AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|46AR],[47AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|47AO],[47AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|47AO],[47AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|47AR],[47AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|47AR],[48AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,26157,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|48AO],[48AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|48AO],[48AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|48AR],[48AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|48AR],[49AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,26157,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|49AO],[49AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|49AO],[49AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|49AR],[49AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|49AR],[50AO,9W,736,MAA,DEL,24/10/2015,11:05,24/10/2015,13:45,K,160,40712,1127,0,9W,1,3,Y,K2CFA,0,R,7,0$1|4|50AO],[50AO,9W,721,DEL,GAU,24/10/2015,14:40,24/10/2015,16:55,K,135,0,0,0,9W,3,,Y,K2CFA,0,R,7,0$1|4|50AO],[50AR,9W,722,GAU,DEL,28/10/2015,17:35,28/10/2015,20:05,Q,150,0,0,0,9W,,3,Y,Q2CFA,0,R,7,0$1|4|50AR],[50AR,9W,739,DEL,MAA,28/10/2015,20:35,28/10/2015,23:25,Q,170,0,0,0,9W,3,1,Y,Q2CFA,0,R,7,0$1|4|50AR],[51AO,9W,820,MAA,DEL,24/10/2015,06:45,24/10/2015,09:25,K,160,40712,1127,0,9W,1,3,Y,K2CFA,0,R,7,0$1|4|51AO],[51AO,9W,721,DEL,GAU,24/10/2015,14:40,24/10/2015,16:55,K,135,0,0,0,9W,3,,Y,K2CFA,0,R,7,0$1|4|51AO],[51AR,9W,722,GAU,DEL,28/10/2015,17:35,28/10/2015,20:05,Q,150,0,0,0,9W,,3,Y,Q2CFA,0,R,7,0$1|4|51AR],[51AR,9W,739,DEL,MAA,28/10/2015,20:35,28/10/2015,23:25,Q,170,0,0,0,9W,3,1,Y,Q2CFA,0,R,7,0$1|4|51AR]]</FlightDetails><FareDetails>"+
		// "[[SrNo,AdultBaseFare,ChildBaseFare,InfantBaseFare,AdultTax,ChildTax,InfantTax,AdultFuelCharges,ChildFuelCharges,InfantFuelCharges,AdultPassengerServiceFee,ChildPassengerServiceFee,InfantPassengerServiceFee,AdultTransactionFee,ChildTransactionFee,InfantTransactionFee,AdultServiceCharges,ChildServiceCharges,InfantServiceCharges,AdultAirportTax,ChildAirportTax,InfantAirportTax,AdultAirportDevelopmentFee,AdultCuteFee,AdultConvenienceFee,AdultSkyCafeMeals,ChildAirportDevelopmentFee,ChildCuteFee,ChildConvenienceFee,ChildSkyCafeMeals,InfantAirportDevelopmentFee,InfantCuteFee,InfantConvenienceFee,InfantSkyCafeMeals,TotalAmount,TotalFlightCommissionAmount,TDSAmount,ServiceTax,ServiceCharge,InfantFuelFeeMarkup,InfantCuteFeeMarkup],[1AO,12870,0,0,0,0,0,7200,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,20937,0,0,0,0,0,0],[2AO,12870,0,0,0,0,0,7200,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,20937,0,0,0,0,0,0],[3AO,12350,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,21717,0,0,0,0,0,0],[4AO,12350,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,21717,0,0,0,0,0,0],[5AO,12350,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,21717,0,0,0,0,0,0],[6AO,14090,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23457,0,0,0,0,0,0],[7AO,14090,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23457,0,0,0,0,0,0],[8AO,12975,0,0,0,0,0,8400,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,22242,0,0,0,0,0,0],[9AO,12975,0,0,0,0,0,8400,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,22242,0,0,0,0,0,0],[10AO,12455,0,0,0,0,0,9700,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23022,0,0,0,0,0,0],[11AO,12455,0,0,0,0,0,9700,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23022,0,0,0,0,0,0],[12AO,12455,0,0,0,0,0,9700,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23022,0,0,0,0,0,0],[13AO,14195,0,0,0,0,0,9700,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24762,0,0,0,0,0,0],[14AO,14195,0,0,0,0,0,9700,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24762,0,0,0,0,0,0],[15AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[16AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[17AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[18AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[19AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[20AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[21AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[22AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[23AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[24AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[25AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[26AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[27AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[28AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[29AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[30AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[31AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[32AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[33AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[34AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[35AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[36AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[37AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[38AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[39AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[40AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[41AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[42AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[43AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[44AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[45AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[46AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[47AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[48AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[49AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[50AO,39585,0,0,0,0,0,0,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,260,0,0,0,0,0,0,0,0,0,0,40712,0,0,0,0,0,0],[51AO,39585,0,0,0,0,0,0,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,260,0,0,0,0,0,0,0,0,0,0,40712,0,0,0,0,0,0]]</FareDetails><AirlineList>[[AirlineCode,AirlineName],[9W,Jet Airways],[AI,Air India]]</AirlineList><AirportList>[[AirportCode,AirportName],[CCU,Kolkata],[DEL,Delhi],[GAU,Guwahati],[MAA,Chennai]]</AirportList></GetFlightAvailibilityResponse></GetFlightAvailibilityResult></GetFlightAvailibilityResponse></soap:Body></soap:Envelope>";
		// System.out.println("output  :"+output);
		logger.info("output :" + output);
		String flightdetails = output.substring(
				output.indexOf("<FlightDetails>") + 16,
				output.indexOf("</FlightDetails>") - 1);
		String FareDetails = output.substring(
				output.indexOf("<FareDetails>") + 14,
				output.indexOf("</FareDetails>") - 1);
		List<String> flightdetaillist = new ArrayList<String>();
		String temp = flightdetails;
		while (temp.length() > 3) {
			String flightdetail = temp.substring(1, temp.indexOf("]"));
			if (temp.indexOf("" + flightdetail + "],") != -1) {
				temp = temp.substring(flightdetail.length() + 3);
			} else {
				temp = "";
			}
			flightdetaillist.add(flightdetail);
		}
		Map<String, String[]> flightdetailMap = new HashMap<String, String[]>();
		List<String> keyList = new ArrayList<String>();
		TreeSet<String> uniqPriceSet = new TreeSet<String>(
				new PriceComparator());
		List<String[]> flightdetailinArray = new ArrayList<String[]>();
		for (int i = 1; i < flightdetaillist.size(); i++) {
			String flightdeatil = flightdetaillist.get(i);
			logger.info("flightdeatil :" + flightdeatil);
			String key = flightdeatil.substring(0, flightdeatil.indexOf(","));
			String[] flightdeatilArray = flightdeatil.split(",");
			flightdetailMap.put(key, flightdeatilArray);
			if (!flightdeatilArray[BluestarConstants.TotalAmount].equals("0")) {
				uniqPriceSet.add(flightdeatilArray[BluestarConstants.TotalAmount]);
			}
			flightdetailinArray.add(flightdeatilArray);
			keyList.add(key);
		}
		List<String> faredetaillist = new ArrayList<String>();
		String temp1 = FareDetails;
		while (temp1.length() > 3) {
			String faredetail = temp1.substring(1, temp1.indexOf("]"));
			if (temp1.indexOf("" + faredetail + "],") != -1) {
				temp1 = temp1.substring(faredetail.length() + 3);
			} else {
				temp1 = "";
			}
			faredetaillist.add(faredetail);
		}
		Map<String, String[]> faredetailMap = new HashMap<String, String[]>();
		List<String> farekeyList = new ArrayList<String>();
		for (int i = 1; i < faredetaillist.size(); i++) {
			String faredeatil = faredetaillist.get(i);
			String key = faredeatil.substring(0, faredeatil.indexOf(","));
			String[] faredeatilArray = faredeatil.split(",");
			faredetailMap.put(key, faredeatilArray);
			farekeyList.add(key);
		}
		logger.info(flightdetailMap.size() + "is th size , flightdetailMap :"
				+ flightdetailMap);
		logger.info(faredetailMap.size() + "is th size ,faredetailMap :"
				+ faredetailMap);
		logger.info(uniqPriceSet.size() + "is th size ,uniqPriceSet :"
				+ uniqPriceSet);
		logger.info(flightdetailinArray.size()
				+ "is th size ,flightdetailinArray :" + flightdetailinArray);
		SearchFlightResponse searchFlightResponse = null;
		try {
			if (flightsearch.getTripType().equalsIgnoreCase("O")) {
				searchFlightResponse = BluestarGetFlightAvailabilityResponseParser
						.parseResponseOneway(flightdetailMap, faredetailMap,
								uniqPriceSet, FlightMarkUpConfiglistMap,
								flightsearch, AirlineNameMap, MapList,
								flightdetailinArray);
			} else {
				searchFlightResponse = BluestarGetFlightAvailabilityResponseParser
						.parseResponseRoundTrip(flightdetailMap, faredetailMap,
								uniqPriceSet, FlightMarkUpConfiglistMap,
								flightsearch, AirlineNameMap, MapList,
								flightdetailinArray);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		List<Passenger> passengers = new ArrayList<Passenger>();
		buildPassengerList(flightsearch,passengers);
		searchFlightResponse.setPassenger(passengers);

		// ////////////////

		return searchFlightResponse;
	}

	public static FlightBookingResponse callBookingService(
			OrderCustomer orderCustomer,
			FlightPriceResponse flightPriceResponse,
			List<FlightOrderCustomer> flightOrderCustomers, String orderId,
			String CountryCode, FlightBookingDao FBDAO,EmailDao emaildao, String transactionkey,
			String paymode,
			WalletAmountTranferHistory walletAmountTranferHistory)
			throws Exception {
		FlightDataBaseServices DBS = new FlightDataBaseServices();
		StringBuilder requestData = BluestarBookTicketRequestBuilder
				.createAirpriceRequest(orderCustomer, flightPriceResponse,
						flightOrderCustomers);
		SOAPMessage soapMessageReq = SoapClient
				.buildSoapMsgFromStr(requestData);
		SOAPMessage soapMessageRes = callService(soapMessageReq,
				new BluestarConfig());
		ByteArrayOutputStream in = new ByteArrayOutputStream();
		try {
			soapMessageRes.writeTo(in);
			logger.info("Price Response :" + in);
		} catch (IOException e) {
			logger.error("IOException", e);
		}
		String output = in.toString();
		// String
		// output="<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><GetFlightAvailibilityResponse xmlns=\"http://tempuri.org/\"><GetFlightAvailibilityResult>&lt;GetFlightAvailibilityResponse&gt;&lt;FlightDetails&gt;[[SrNo,AirlineCode,FlightNo,FromAirportCode,ToAirportCode,DepDate,DepTime,ArrDate,ArrTime,FlightClass,FlightTime,TotalAmount,TaxAmount,Stops,ValCarrier,FromTerminal,ToTerminal,MainClass,FareBasis,AgencyCharge,FareType,AvailSeats,TrackNo],[1SAO,SG, 160,BOM,DEL,29/12/2015,06:05,29/12/2015,08:00,RS,115,7758,2498,0,SG,1B,1C,Y,CSAVER,0,R,0,0$22|34|1SAO],[2SAO,SG, 160,BOM,DEL,29/12/2015,06:05,29/12/2015,08:00,HF,115,15152,2890,0,SG,1B,1C,Y,CFLEX,0,R,0,0$22|34|2SAO],[3SAO,SG, 162,BOM,DEL,29/12/2015,07:35,29/12/2015,09:35,RS,120,7758,2498,0,SG,1B,1C,Y,CSAVER,0,R,0,0$22|34|3SAO],[4SAO,SG, 162,BOM,DEL,29/12/2015,07:35,29/12/2015,09:35,HF,120,15152,2890,0,SG,1B,1C,Y,CFLEX,0,R,0,0$22|34|4SAO],[5SAO,SG, 164,BOM,DEL,29/12/2015,11:15,29/12/2015,13:15,RS,120,7758,2498,0,SG,1B,1C,Y,CSAVER,0,R,0,0$22|34|5SAO],[6SAO,SG, 164,BOM,DEL,29/12/2015,11:15,29/12/2015,13:15,HF,120,15152,2890,0,SG,1B,1C,Y,CFLEX,0,R,0,0$22|34|6SAO],[7SAO,SG, 158,BOM,DEL,29/12/2015,18:20,29/12/2015,20:25,RS,125,7758,2498,0,SG,1B,1C,Y,CSAVER,0,R,0,0$22|34|7SAO],[8SAO,SG, 158,BOM,DEL,29/12/2015,18:20,29/12/2015,20:25,HF,125,15152,2890,0,SG,1B,1C,Y,CFLEX,0,R,0,0$22|34|8SAO]]&lt;/FlightDetails&gt;&lt;FareDetails&gt;[[SrNo,AdultBaseFare,ChildBaseFare,InfantBaseFare,AdultTax,ChildTax,InfantTax,AdultFuelCharges,ChildFuelCharges,InfantFuelCharges,AdultPassengerServiceFee,ChildPassengerServiceFee,InfantPassengerServiceFee,AdultTransactionFee,ChildTransactionFee,InfantTransactionFee,AdultServiceCharges,ChildServiceCharges,InfantServiceCharges,AdultAirportTax,ChildAirportTax,InfantAirportTax,AdultAirportDevelopmentFee,AdultCuteFee,AdultConvenienceFee,AdultSkyCafeMeals,ChildAirportDevelopmentFee,ChildCuteFee,ChildConvenienceFee,ChildSkyCafeMeals,InfantAirportDevelopmentFee,InfantCuteFee,InfantConvenienceFee,InfantSkyCafeMeals,TotalAmount,TotalFlightCommissionAmount,TDSAmount,ServiceTax,ServiceCharge,InfantFuelFeeMarkup,InfantCuteFeeMarkup],[1SAO,2038,2038,1184,640,640,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7758,100,10,12.36,0,0,0],[2SAO,5539,5539,1184,836,836,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15152,100,10,12.36,0,0,0],[3SAO,2038,2038,1184,640,640,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7758,100,10,12.36,0,0,0],[4SAO,5539,5539,1184,836,836,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15152,100,10,12.36,0,0,0],[5SAO,2038,2038,1184,640,640,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7758,100,10,12.36,0,0,0],[6SAO,5539,5539,1184,836,836,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15152,100,10,12.36,0,0,0],[7SAO,2038,2038,1184,640,640,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,7758,100,10,12.36,0,0,0],[8SAO,5539,5539,1184,836,836,66,0,0,0,149,149,0,0,0,0,427,427,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15152,100,10,12.36,0,0,0]]&lt;/FareDetails&gt;&lt;AirlineList&gt;[[AirlineCode,AirlineName],[SG,Spice Jet]]&lt;/AirlineList&gt;&lt;AirportList&gt;[[AirportCode,AirportName],[BOM,Mumbai],[DEL,Delhi]]&lt;/AirportList&gt;&lt;/GetFlightAvailibilityResponse&gt;</GetFlightAvailibilityResult></GetFlightAvailibilityResponse></soap:Body></soap:Envelope>";
		output = output.replaceAll("&lt;", "<");
		output = output.replaceAll("&gt;", ">");
		// System.out.println("output  :"+output);
		String BookTicketResponse = output.substring(
				output.indexOf("<BookTicketResponse>") + 20,
				output.indexOf("</BookTicketResponse>"));
		FlightBookingResponse flightBookingResponse = null;
		flightBookingResponse = BluestarBookTicketResponseParser
				.parseFlightBookingResponse(BookTicketResponse, orderId, FBDAO,emaildao,
						transactionkey, paymode, walletAmountTranferHistory);
		return flightBookingResponse;
	}

	public static FlightPriceResponse getFlightPriceResponse(String pricekey,
			FlightTempAirSegmentDAO tempDAO) throws Exception {
		FlightDataBaseServices DBS = new FlightDataBaseServices();
		AirPriceDetails airPriceDetails = DBS.getAirPriceDetails(pricekey,
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

	public static void createFCD(FlightCustomerDetails FCD,
			Flightsearch flightsearch) {

		List<PassengerDetails> passengerdetailsList = new ArrayList<PassengerDetails>();
		addPassengers(passengerdetailsList, flightsearch);
		FCD.setPassengerdetailsList(passengerdetailsList);

	}

	private static void addPassengers(
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
			Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap,
			Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> MapList, FlightTempAirSegmentDAO tempDAO)
			throws Exception
	{

		StringBuilder requestData = BluestarGetFlightAvailibilityRequestBuilder
				.createSearchRequest(flightsearch);
		logger.info("requestData :" + requestData);
		SOAPMessage soapMessageReq = SoapClient
				.buildSoapMsgFromStr(requestData);
		SOAPMessage soapMessageRes = callService(soapMessageReq,
				new BluestarConfig());
		ByteArrayOutputStream in = new ByteArrayOutputStream();
		try {
			soapMessageRes.writeTo(in);
			logger.info("Search Response :" + in);
		} catch (IOException e) {
			logger.error("IOException", e);
		}

		String output = in.toString();

		output = output.replaceAll("&lt;", "<");
		output = output.replaceAll("&gt;", ">");

		// String
		// output="<GetFlightAvailibilityResult><GetFlightAvailibilityResponse><FlightDetails>[[SrNo,AirlineCode,FlightNo,FromAirportCode,ToAirportCode,DepDate,DepTime,ArrDate,ArrTime,FlightClass,FlightTime,TotalAmount,TaxAmount,Stops,ValCarrier,FromTerminal,ToTerminal,MainClass,FareBasis,AgencyCharge,FareType,AvailSeats,TrackNo],[1AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,20937,8067,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|1AO],[1AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|1AO],[1AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|1AR],[1AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|1AR],[2AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,20937,8067,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|2AO],[2AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|2AO],[2AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|2AR],[2AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|2AR],[3AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,21717,9367,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|3AO],[3AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|3AO],[3AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|3AR],[3AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|3AR],[4AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,21717,9367,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|4AO],[4AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|4AO],[4AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|4AR],[4AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|4AR],[5AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,21717,9367,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|5AO],[5AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|5AO],[5AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|5AR],[5AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|5AR],[6AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,23457,9367,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|6AO],[6AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|6AO],[6AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|6AR],[6AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|6AR],[7AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,23457,9367,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|7AO],[7AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|7AO],[7AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|7AR],[7AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|7AR],[8AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,22242,9267,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|8AO],[8AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|8AO],[8AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|8AR],[8AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|8AR],[9AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,22242,9267,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|9AO],[9AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|9AO],[9AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|9AR],[9AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|9AR],[10AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,23022,10567,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|10AO],[10AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|10AO],[10AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|10AR],[10AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|10AR],[11AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,23022,10567,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|11AO],[11AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|11AO],[11AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|11AR],[11AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|11AR],[12AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,23022,10567,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|12AO],[12AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|12AO],[12AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|12AR],[12AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|12AR],[13AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,24762,10567,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|13AO],[13AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|13AO],[13AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|13AR],[13AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|13AR],[14AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,24762,10567,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|14AO],[14AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|14AO],[14AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|14AR],[14AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|14AR],[15AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|15AO],[15AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|15AO],[15AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|15AR],[15AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|15AR],[16AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|16AO],[16AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|16AO],[16AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|16AR],[16AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|16AR],[17AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|17AO],[17AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|17AO],[17AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|17AR],[17AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|17AR],[18AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|18AO],[18AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|18AO],[18AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|18AR],[18AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|18AR],[19AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|19AO],[19AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|19AO],[19AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|19AR],[19AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|19AR],[20AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|20AO],[20AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|20AO],[20AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|20AR],[20AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|20AR],[21AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|21AO],[21AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|21AO],[21AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|21AR],[21AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|21AR],[22AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,23637,9367,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|22AO],[22AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|22AO],[22AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|22AR],[22AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|22AR],[23AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,23637,9367,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|23AO],[23AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|23AO],[23AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|23AR],[23AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|23AR],[24AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|24AO],[24AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|24AO],[24AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|24AR],[24AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|24AR],[25AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|25AO],[25AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|25AO],[25AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|25AR],[25AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|25AR],[26AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|26AO],[26AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|26AO],[26AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|26AR],[26AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|26AR],[27AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,26157,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|27AO],[27AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|27AO],[27AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|27AR],[27AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|27AR],[28AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,26157,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|28AO],[28AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|28AO],[28AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|28AR],[28AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|28AR],[29AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|29AO],[29AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|29AO],[29AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|29AR],[29AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|29AR],[30AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|30AO],[30AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|30AO],[30AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|30AR],[30AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|30AR],[31AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|31AO],[31AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|31AO],[31AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|31AR],[31AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|31AR],[32AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|32AO],[32AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|32AO],[32AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|32AR],[32AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|32AR],[33AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|33AO],[33AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|33AO],[33AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|33AR],[33AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|33AR],[34AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|34AO],[34AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|34AO],[34AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|34AR],[34AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|34AR],[35AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|35AO],[35AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|35AO],[35AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|35AR],[35AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|35AR],[36AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|36AO],[36AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|36AO],[36AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|36AR],[36AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|36AR],[37AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|37AO],[37AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|37AO],[37AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|37AR],[37AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|37AR],[38AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|38AO],[38AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|38AO],[38AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|38AR],[38AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|38AR],[39AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|39AO],[39AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|39AO],[39AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|39AR],[39AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|39AR],[40AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|40AO],[40AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|40AO],[40AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|40AR],[40AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|40AR],[41AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|41AO],[41AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|41AO],[41AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|41AR],[41AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|41AR],[42AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|42AO],[42AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|42AO],[42AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|42AR],[42AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|42AR],[43AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,23637,9367,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|43AO],[43AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|43AO],[43AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|43AR],[43AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|43AR],[44AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,23637,9367,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|44AO],[44AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|44AO],[44AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|44AR],[44AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|44AR],[45AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|45AO],[45AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|45AO],[45AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|45AR],[45AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|45AR],[46AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|46AO],[46AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|46AO],[46AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|46AR],[46AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|46AR],[47AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|47AO],[47AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|47AO],[47AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|47AR],[47AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|47AR],[48AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,26157,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|48AO],[48AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|48AO],[48AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|48AR],[48AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|48AR],[49AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,26157,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|49AO],[49AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|49AO],[49AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|49AR],[49AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|49AR],[50AO,9W,736,MAA,DEL,24/10/2015,11:05,24/10/2015,13:45,K,160,40712,1127,0,9W,1,3,Y,K2CFA,0,R,7,0$1|4|50AO],[50AO,9W,721,DEL,GAU,24/10/2015,14:40,24/10/2015,16:55,K,135,0,0,0,9W,3,,Y,K2CFA,0,R,7,0$1|4|50AO],[50AR,9W,722,GAU,DEL,28/10/2015,17:35,28/10/2015,20:05,Q,150,0,0,0,9W,,3,Y,Q2CFA,0,R,7,0$1|4|50AR],[50AR,9W,739,DEL,MAA,28/10/2015,20:35,28/10/2015,23:25,Q,170,0,0,0,9W,3,1,Y,Q2CFA,0,R,7,0$1|4|50AR],[51AO,9W,820,MAA,DEL,24/10/2015,06:45,24/10/2015,09:25,K,160,40712,1127,0,9W,1,3,Y,K2CFA,0,R,7,0$1|4|51AO],[51AO,9W,721,DEL,GAU,24/10/2015,14:40,24/10/2015,16:55,K,135,0,0,0,9W,3,,Y,K2CFA,0,R,7,0$1|4|51AO],[51AR,9W,722,GAU,DEL,28/10/2015,17:35,28/10/2015,20:05,Q,150,0,0,0,9W,,3,Y,Q2CFA,0,R,7,0$1|4|51AR],[51AR,9W,739,DEL,MAA,28/10/2015,20:35,28/10/2015,23:25,Q,170,0,0,0,9W,3,1,Y,Q2CFA,0,R,7,0$1|4|51AR]]</FlightDetails><FareDetails>"+
		// "[[SrNo,AdultBaseFare,ChildBaseFare,InfantBaseFare,AdultTax,ChildTax,InfantTax,AdultFuelCharges,ChildFuelCharges,InfantFuelCharges,AdultPassengerServiceFee,ChildPassengerServiceFee,InfantPassengerServiceFee,AdultTransactionFee,ChildTransactionFee,InfantTransactionFee,AdultServiceCharges,ChildServiceCharges,InfantServiceCharges,AdultAirportTax,ChildAirportTax,InfantAirportTax,AdultAirportDevelopmentFee,AdultCuteFee,AdultConvenienceFee,AdultSkyCafeMeals,ChildAirportDevelopmentFee,ChildCuteFee,ChildConvenienceFee,ChildSkyCafeMeals,InfantAirportDevelopmentFee,InfantCuteFee,InfantConvenienceFee,InfantSkyCafeMeals,TotalAmount,TotalFlightCommissionAmount,TDSAmount,ServiceTax,ServiceCharge,InfantFuelFeeMarkup,InfantCuteFeeMarkup],[1AO,12870,0,0,0,0,0,7200,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,20937,0,0,0,0,0,0],[2AO,12870,0,0,0,0,0,7200,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,20937,0,0,0,0,0,0],[3AO,12350,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,21717,0,0,0,0,0,0],[4AO,12350,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,21717,0,0,0,0,0,0],[5AO,12350,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,21717,0,0,0,0,0,0],[6AO,14090,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23457,0,0,0,0,0,0],[7AO,14090,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23457,0,0,0,0,0,0],[8AO,12975,0,0,0,0,0,8400,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,22242,0,0,0,0,0,0],[9AO,12975,0,0,0,0,0,8400,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,22242,0,0,0,0,0,0],[10AO,12455,0,0,0,0,0,9700,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23022,0,0,0,0,0,0],[11AO,12455,0,0,0,0,0,9700,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23022,0,0,0,0,0,0],[12AO,12455,0,0,0,0,0,9700,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23022,0,0,0,0,0,0],[13AO,14195,0,0,0,0,0,9700,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24762,0,0,0,0,0,0],[14AO,14195,0,0,0,0,0,9700,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24762,0,0,0,0,0,0],[15AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[16AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[17AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[18AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[19AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[20AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[21AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[22AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[23AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[24AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[25AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[26AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[27AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[28AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[29AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[30AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[31AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[32AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[33AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[34AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[35AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[36AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[37AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[38AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[39AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[40AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[41AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[42AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[43AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[44AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[45AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[46AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[47AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[48AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[49AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[50AO,39585,0,0,0,0,0,0,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,260,0,0,0,0,0,0,0,0,0,0,40712,0,0,0,0,0,0],[51AO,39585,0,0,0,0,0,0,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,260,0,0,0,0,0,0,0,0,0,0,40712,0,0,0,0,0,0]]</FareDetails><AirlineList>[[AirlineCode,AirlineName],[9W,Jet Airways],[AI,Air India]]</AirlineList><AirportList>[[AirportCode,AirportName],[CCU,Kolkata],[DEL,Delhi],[GAU,Guwahati],[MAA,Chennai]]</AirportList></GetFlightAvailibilityResponse></GetFlightAvailibilityResult></GetFlightAvailibilityResponse></soap:Body></soap:Envelope>";

		// System.out.println("output  :"+output);
		logger.info("output :" + output);
		String flightdetails = output.substring(
				output.indexOf("<FlightDetails>") + 16,
				output.indexOf("</FlightDetails>") - 1);

		String FareDetails = output.substring(
				output.indexOf("<FareDetails>") + 14,
				output.indexOf("</FareDetails>") - 1);

		List<String> flightdetaillist = new ArrayList<String>();
		String tempFlightDetails = flightdetails;
		while (tempFlightDetails.length() > 3) {
			String flightdetail = tempFlightDetails.substring(1,
					tempFlightDetails.indexOf("]"));

			if (tempFlightDetails.indexOf("" + flightdetail + "],") != -1) {
				tempFlightDetails = tempFlightDetails.substring(flightdetail
						.length() + 3);
			} else {
				tempFlightDetails = "";
			}
			String[] flightdeatilArrayTemp = flightdetail.split(",");

			if (flightsearch.getTripType().equalsIgnoreCase("O")) {
				flightdetaillist.add(flightdetail);
			} else if (flightsearch.getTripType().equalsIgnoreCase("R")
					&& (!flightdeatilArrayTemp[BluestarConstants.AirlineCode]
							.equalsIgnoreCase("SG") && !flightdeatilArrayTemp[BluestarConstants.AirlineCode]
							.equals("G8"))) {
				flightdetaillist.add(flightdetail);
			} else if (flightsearch.getTripType().equalsIgnoreCase("R")
					&& (flightdeatilArrayTemp[BluestarConstants.AirlineCode]
							.equals("SG") || flightdeatilArrayTemp[BluestarConstants.AirlineCode]
							.equals("G8"))) {
				String flightdetailTEmp = tempFlightDetails.substring(1,
						tempFlightDetails.indexOf("]"));
				if (tempFlightDetails.indexOf("" + flightdetailTEmp + "],") != -1) {
					tempFlightDetails = tempFlightDetails
							.substring(flightdetailTEmp.length() + 3);
				} else {
					tempFlightDetails = "";
				}

				String[] flightdeatilArrayTEmp2 = flightdetailTEmp.split(",");

				List<String> tempflightlist = new ArrayList<String>();

				if (flightdeatilArrayTemp[BluestarConstants.SrNo]
						.equals(flightdeatilArrayTEmp2[BluestarConstants.SrNo])) {

					tempflightlist.add(flightdetailTEmp);

					String roundSegmemt = "";

					while (tempFlightDetails.length() > 0) {

						String flightdetailTEmp3 = tempFlightDetails.substring(
								1, tempFlightDetails.indexOf("]"));
						// System.out.println("flightdetailTEmp3  :"+flightdetailTEmp3);
						if (tempFlightDetails.indexOf("" + flightdetailTEmp3
								+ "],") != -1) {

							tempFlightDetails = tempFlightDetails
									.substring(flightdetailTEmp3.length() + 3);

						} else {
							tempFlightDetails = "";
						}

						String[] flightdeatilArrayTEmp3 = flightdetailTEmp3
								.split(",");
						if (flightdeatilArrayTemp[BluestarConstants.SrNo]
								.equals(flightdeatilArrayTEmp3[BluestarConstants.SrNo])) {
							tempflightlist.add(flightdetailTEmp3);
						} else {

							roundSegmemt = flightdetailTEmp3;
							break;
						}

					}

					List<String> flightdetaillistTEMP = getNewFlightDetail3(
							flightdetail, tempflightlist, roundSegmemt);
					for (String NewFlightDetailNEW : flightdetaillistTEMP) {
						flightdetaillist.add(NewFlightDetailNEW);

					}

					// //////////////////
					String[] flightdeatilArrayTEmp3 = roundSegmemt.split(",");
					if (!flightdeatilArrayTEmp3[BluestarConstants.ToAirportCode]
							.equals(flightdeatilArrayTemp[BluestarConstants.FromAirportCode])) {
						String flightdetailTEmp4 = tempFlightDetails.substring(
								1, tempFlightDetails.indexOf("]"));
						if (tempFlightDetails.indexOf("" + flightdetailTEmp4
								+ "],") != -1) {

							tempFlightDetails = tempFlightDetails
									.substring(flightdetailTEmp4.length() + 3);

						} else {
							tempFlightDetails = "";
						}
						flightdetaillist.add(flightdetailTEmp4);

						String[] flightdeatilArrayTEmp4 = flightdetailTEmp4
								.split(",");
						if (!flightdeatilArrayTEmp4[BluestarConstants.ToAirportCode]
								.equals(flightdeatilArrayTemp[BluestarConstants.FromAirportCode])) {
							String flightdetailTEmp5 = tempFlightDetails
									.substring(1,
											tempFlightDetails.indexOf("]"));
							if (tempFlightDetails.indexOf(""
									+ flightdetailTEmp5 + "],") != -1) {

								tempFlightDetails = tempFlightDetails
										.substring(flightdetailTEmp5.length() + 3);

							} else {
								tempFlightDetails = "";
							}
							flightdetaillist.add(flightdetailTEmp5);
							String[] flightdeatilArrayTEmp5 = flightdetailTEmp5
									.split(",");
							if (!flightdeatilArrayTEmp5[BluestarConstants.ToAirportCode]
									.equals(flightdeatilArrayTemp[BluestarConstants.FromAirportCode])) {
								String flightdetailTEmp6 = tempFlightDetails
										.substring(1,
												tempFlightDetails.indexOf("]"));
								if (tempFlightDetails.indexOf(""
										+ flightdetailTEmp6 + "],") != -1) {

									tempFlightDetails = tempFlightDetails
											.substring(flightdetailTEmp6
													.length() + 3);

								} else {
									tempFlightDetails = "";
								}
								flightdetaillist.add(flightdetailTEmp6);
								String[] flightdeatilArrayTEmp6 = flightdetailTEmp6
										.split(",");
								if (!flightdeatilArrayTEmp6[BluestarConstants.ToAirportCode]
										.equals(flightdeatilArrayTemp[BluestarConstants.FromAirportCode])) {
									String flightdetailTEmp7 = tempFlightDetails
											.substring(1, tempFlightDetails
													.indexOf("]"));
									if (tempFlightDetails.indexOf(""
											+ flightdetailTEmp7 + "],") != -1) {

										tempFlightDetails = tempFlightDetails
												.substring(flightdetailTEmp7
														.length() + 3);

									} else {
										tempFlightDetails = "";
									}
									flightdetaillist.add(flightdetailTEmp7);

								}
							}
						}
					}

				} else {

					List<String> flightdetaillistTEMP = getNewFlightDetail(
							flightdetail, flightdetailTEmp);
					for (String NewFlightDetailNEW : flightdetaillistTEMP) {
						flightdetaillist.add(NewFlightDetailNEW);

					}

					if (!flightdeatilArrayTEmp2[BluestarConstants.ToAirportCode]
							.equals(flightdeatilArrayTemp[BluestarConstants.FromAirportCode])) {
						String flightdetailTEmp4 = tempFlightDetails.substring(
								1, tempFlightDetails.indexOf("]"));
						if (tempFlightDetails.indexOf("" + flightdetailTEmp4
								+ "],") != -1) {

							tempFlightDetails = tempFlightDetails
									.substring(flightdetailTEmp4.length() + 3);

						} else {
							tempFlightDetails = "";
						}
						flightdetaillist.add(flightdetailTEmp4);
						String[] flightdeatilArrayTEmp4 = flightdetailTEmp4
								.split(",");

						if (!flightdeatilArrayTEmp4[BluestarConstants.ToAirportCode]
								.equals(flightdeatilArrayTemp[BluestarConstants.FromAirportCode])) {
							String flightdetailTEmp5 = tempFlightDetails
									.substring(1,
											tempFlightDetails.indexOf("]"));
							if (tempFlightDetails.indexOf(""
									+ flightdetailTEmp5 + "],") != -1) {

								tempFlightDetails = tempFlightDetails
										.substring(flightdetailTEmp5.length() + 3);

							} else {
								tempFlightDetails = "";
							}
							flightdetaillist.add(flightdetailTEmp5);
							String[] flightdeatilArrayTEmp5 = flightdetailTEmp5
									.split(",");

							if (!flightdeatilArrayTEmp5[BluestarConstants.ToAirportCode]
									.equals(flightdeatilArrayTemp[BluestarConstants.FromAirportCode])) {
								String flightdetailTEmp6 = tempFlightDetails
										.substring(1,
												tempFlightDetails.indexOf("]"));
								if (tempFlightDetails.indexOf(""
										+ flightdetailTEmp6 + "],") != -1) {

									tempFlightDetails = tempFlightDetails
											.substring(flightdetailTEmp6
													.length() + 3);

								} else {
									tempFlightDetails = "";
								}
								flightdetaillist.add(flightdetailTEmp6);
							}
						}

					}

				}

				// ////////////////
			}

		}
		Map<String, String[]> flightdetailMap = new HashMap<String, String[]>();

		List<String> keyList = new ArrayList<String>();

		// /LinkedHashSet<String> uniqPriceSet=new LinkedHashSet<String>();
		TreeSet<String> uniqPriceSet = new TreeSet<String>(
				new PriceComparator());
		List<String[]> flightdetailinArray = new ArrayList<String[]>();
		// //System.out.println("flightdetaillist :"+flightdetaillist.size());

		for (int i = 1; i < flightdetaillist.size(); i++) {
			String flightdeatil = flightdetaillist.get(i);
			logger.info("flightdeatil :" + flightdeatil);
			String key = flightdeatil.substring(0, flightdeatil.indexOf(","));
			String[] flightdeatilArray = flightdeatil.split(",");
			flightdetailMap.put(key, flightdeatilArray);
			if (!flightdeatilArray[BluestarConstants.TotalAmount].equals("0")) {
				uniqPriceSet.add(flightdeatilArray[BluestarConstants.TotalAmount]);
			}
			flightdetailinArray.add(flightdeatilArray);
			keyList.add(key);
		}
		// //System.out.println("flightdetailinArray :"+flightdetailinArray.size());

		List<String> faredetaillist = new ArrayList<String>();
		String temp1 = FareDetails;
		while (temp1.length() > 3) {
			String faredetail = temp1.substring(1, temp1.indexOf("]"));
			if (temp1.indexOf("" + faredetail + "],") != -1) {
				temp1 = temp1.substring(faredetail.length() + 3);
			} else {
				temp1 = "";
			}
			faredetaillist.add(faredetail);
		}
		Map<String, String[]> faredetailMap = new HashMap<String, String[]>();

		for (int i = 1; i < faredetaillist.size(); i++) {
			String faredeatil = faredetaillist.get(i);
			if (flightsearch.getTripType().equalsIgnoreCase("O")) {
				String key = faredeatil.substring(0, faredeatil.indexOf(","));
				String[] faredeatilArray = faredeatil.split(",");
				faredetailMap.put(key, faredeatilArray);
			} else
			if (flightsearch.getTripType().equalsIgnoreCase("R")) {
				String key = faredeatil.substring(0, faredeatil.indexOf(","));
				if (i != faredetaillist.size() - 1) {
					String Nextkey = faredetaillist.get(i + 1).substring(0,
							faredeatil.indexOf(","));
					if (key.substring(0, key.length() - 1).equals(
							Nextkey.substring(0, Nextkey.length() - 1))) {
						String faredetailTEMP = getNewFareDetail(faredeatil,
								faredetaillist.get(i + 1));
						// System.out.println("faredetailTEMP  :"+faredetailTEMP);
						String[] faredeatilArray = faredetailTEMP.split(",");
						faredetailMap.put(key, faredeatilArray);
						i++;
					} else {
						String[] faredeatilArray = faredeatil.split(",");
						faredetailMap.put(key, faredeatilArray);
					}
				} else {
					String[] faredeatilArray = faredeatil.split(",");
					faredetailMap.put(key, faredeatilArray);
				}
			}
		}

		logger.info(flightdetailMap.size() + "is th size , flightdetailMap :"
				+ flightdetailMap);
		logger.info(faredetailMap.size() + "is th size ,faredetailMap :"
				+ faredetailMap);
		logger.info(uniqPriceSet.size() + "is th size ,uniqPriceSet :"
				+ uniqPriceSet);
		logger.info(flightdetailinArray.size()
				+ "is th size ,flightdetailinArray :" + flightdetailinArray);

		SearchFlightResponse searchFlightResponse = null;
		try {
			if (flightsearch.getTripType().equalsIgnoreCase("O")) {
				searchFlightResponse = BluestarGetFlightAvailabilityResponseParser
						.parseResponseOneway(flightdetailMap, faredetailMap,
								uniqPriceSet, FlightMarkUpConfiglistMap,
								flightsearch, AirlineNameMap, MapList,
								flightdetailinArray);
			} else {
				searchFlightResponse = BluestarGetFlightAvailabilityResponseParser
						.parseResponseRoundTrip(flightdetailMap, faredetailMap,
								uniqPriceSet, FlightMarkUpConfiglistMap,
								flightsearch, AirlineNameMap, MapList,
								flightdetailinArray);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}

		// ///////
		List<Passenger> passengers = new ArrayList<Passenger>();
		buildPassengerList(flightsearch,passengers);
		searchFlightResponse.setPassenger(passengers);
		return searchFlightResponse;
	}

	*//**
	 * @param flightsearch 
	 * @param passengers 
	 * 
	 *//*
	private static void buildPassengerList(Flightsearch flightsearch, List<Passenger> passengers) {
		for (int i = 0; i < flightsearch.getAdult(); i++) {
			Passenger passenger = new Passenger();
			passenger.setId((new UID()).toString());
			passenger.setType("ADT");
			passengers.add(passenger);
		}
		for (int i = 0; i < flightsearch.getKid(); i++) {
			Passenger passenger = new Passenger();
			passenger.setId((new UID()).toString());
			passenger.setType("CHD");
			passengers.add(passenger);
		}
		for (int i = 0; i < flightsearch.getInfant(); i++) {
			Passenger passenger = new Passenger();
			passenger.setId((new UID()).toString());
			passenger.setType("INF");
			passengers.add(passenger);
		}
		
	}

	public static void callSearchServiceForMerge(Flightsearch flightsearch,
			List<FlightMarkUpConfig> FlightMarkUpConfiglist,
			Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> MapList, FlightTempAirSegmentDAO tempDAO,
			List<FareFlightSegment> fareFlightSegments,
			UAPISearchFlightKeyMap uapiSearchFlightKeyMap) throws Exception {

		StringBuilder requestData = BluestarGetFlightAvailibilityRequestBuilder
				.createSearchRequest(flightsearch);
		logger.info("requestData :" + requestData);
		SOAPMessage soapMessageReq = SoapClient
				.buildSoapMsgFromStr(requestData);
		SOAPMessage soapMessageRes = callService(soapMessageReq,
				new BluestarConfig());
		ByteArrayOutputStream in = new ByteArrayOutputStream();
		try {
			soapMessageRes.writeTo(in);
			logger.info("Search Response :" + in);
		} catch (IOException e) {
			logger.error("IOException", e);
		}

		String output = in.toString();

		output = output.replaceAll("&lt;", "<");
		output = output.replaceAll("&gt;", ">");

		// String
		// output="<GetFlightAvailibilityResult><GetFlightAvailibilityResponse><FlightDetails>[[SrNo,AirlineCode,FlightNo,FromAirportCode,ToAirportCode,DepDate,DepTime,ArrDate,ArrTime,FlightClass,FlightTime,TotalAmount,TaxAmount,Stops,ValCarrier,FromTerminal,ToTerminal,MainClass,FareBasis,AgencyCharge,FareType,AvailSeats,TrackNo],[1AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,20937,8067,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|1AO],[1AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|1AO],[1AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|1AR],[1AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|1AR],[2AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,20937,8067,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|2AO],[2AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|2AO],[2AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|2AR],[2AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|2AR],[3AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,21717,9367,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|3AO],[3AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|3AO],[3AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|3AR],[3AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|3AR],[4AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,21717,9367,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|4AO],[4AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|4AO],[4AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|4AR],[4AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|4AR],[5AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,21717,9367,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|5AO],[5AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|5AO],[5AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|5AR],[5AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|5AR],[6AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,23457,9367,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|6AO],[6AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|6AO],[6AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|6AR],[6AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|6AR],[7AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,23457,9367,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|7AO],[7AO,AI,9739,CCU,GAU,25/10/2015,05:50,25/10/2015,07:25,T,95,0,0,0,AI,2,,Y,TRT2,0,R,4,0$1|4|7AO],[7AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|7AR],[7AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|7AR],[8AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,22242,9267,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|8AO],[8AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|8AO],[8AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|8AR],[8AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|8AR],[9AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,22242,9267,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|9AO],[9AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|9AO],[9AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|9AR],[9AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|9AR],[10AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,23022,10567,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|10AO],[10AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|10AO],[10AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|10AR],[10AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|10AR],[11AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,23022,10567,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|11AO],[11AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|11AO],[11AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|11AR],[11AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|11AR],[12AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,23022,10567,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|12AO],[12AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|12AO],[12AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|12AR],[12AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|12AR],[13AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,24762,10567,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|13AO],[13AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|13AO],[13AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|13AR],[13AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|13AR],[14AO,AI,766,MAA,CCU,24/10/2015,16:50,24/10/2015,19:00,L,130,24762,10567,0,AI,1,2,Y,LIPRT,0,R,8,0$1|4|14AO],[14AO,AI,729,CCU,GAU,25/10/2015,09:50,25/10/2015,10:55,U,65,0,0,0,AI,2,,Y,UIPRT,0,R,9,0$1|4|14AO],[14AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|14AR],[14AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|14AR],[15AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|15AO],[15AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|15AO],[15AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|15AR],[15AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|15AR],[16AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|16AO],[16AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|16AO],[16AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|16AR],[16AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|16AR],[17AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|17AO],[17AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|17AO],[17AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|17AR],[17AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|17AR],[18AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|18AO],[18AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|18AO],[18AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|18AR],[18AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|18AR],[19AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|19AO],[19AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|19AO],[19AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|19AR],[19AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|19AR],[20AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|20AO],[20AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|20AO],[20AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|20AR],[20AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|20AR],[21AO,AI,440,MAA,DEL,24/10/2015,06:40,24/10/2015,09:25,T,165,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|21AO],[21AO,AI,889,DEL,GAU,24/10/2015,10:50,24/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|21AO],[21AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|21AR],[21AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|21AR],[22AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,23637,9367,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|22AO],[22AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|22AO],[22AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|22AR],[22AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|22AR],[23AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,23637,9367,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|23AO],[23AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|23AO],[23AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|23AR],[23AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|23AR],[24AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|24AO],[24AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|24AO],[24AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|24AR],[24AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|24AR],[25AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|25AO],[25AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|25AO],[25AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|25AR],[25AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|25AR],[26AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|26AO],[26AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|26AO],[26AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|26AR],[26AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|26AR],[27AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,26157,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|27AO],[27AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|27AO],[27AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|27AR],[27AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|27AR],[28AO,AI,43,MAA,DEL,24/10/2015,21:00,24/10/2015,23:45,T,165,26157,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|28AO],[28AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|28AO],[28AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|28AR],[28AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|28AR],[29AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|29AO],[29AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|29AO],[29AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|29AR],[29AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|29AR],[30AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|30AO],[30AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|30AO],[30AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|30AR],[30AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|30AR],[31AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|31AO],[31AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|31AO],[31AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|31AR],[31AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|31AR],[32AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|32AO],[32AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|32AO],[32AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|32AR],[32AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|32AR],[33AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|33AO],[33AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|33AO],[33AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|33AR],[33AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|33AR],[34AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|34AO],[34AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|34AO],[34AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|34AR],[34AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|34AR],[35AO,AI,539,MAA,DEL,24/10/2015,17:30,24/10/2015,20:05,T,155,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|35AO],[35AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|35AO],[35AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|35AR],[35AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|35AR],[36AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|36AO],[36AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|36AO],[36AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|36AR],[36AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|36AR],[37AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,23637,9367,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|37AO],[37AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|37AO],[37AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|37AR],[37AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|37AR],[38AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|38AO],[38AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|38AO],[38AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|38AR],[38AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|38AR],[39AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|39AO],[39AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|39AO],[39AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|39AR],[39AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|39AR],[40AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,24417,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|40AO],[40AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|40AO],[40AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|40AR],[40AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|40AR],[41AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|41AO],[41AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|41AO],[41AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|41AR],[41AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|41AR],[42AO,AI,430,MAA,DEL,24/10/2015,10:45,24/10/2015,13:30,T,165,26157,10667,0,AI,1,3,Y,TRT2,0,R,9,0$1|4|42AO],[42AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|42AO],[42AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|42AR],[42AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|42AR],[43AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,23637,9367,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|43AO],[43AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|43AO],[43AR,AI,730,GAU,CCU,28/10/2015,11:40,28/10/2015,12:50,T,70,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|43AR],[43AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|43AR],[44AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,23637,9367,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|44AO],[44AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|44AO],[44AR,AI,9740,GAU,CCU,28/10/2015,10:25,28/10/2015,11:55,T,90,0,0,0,AI,,2,Y,TRT2,0,R,2,0$1|4|44AR],[44AR,AI,765,CCU,MAA,28/10/2015,18:05,28/10/2015,20:35,U,150,0,0,0,AI,2,1,Y,UIPRT,0,R,9,0$1|4|44AR],[45AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|45AO],[45AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|45AO],[45AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|45AR],[45AR,AI,540,DEL,MAA,28/10/2015,20:55,28/10/2015,23:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|45AR],[46AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|46AO],[46AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|46AO],[46AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|46AR],[46AR,AI,439,DEL,MAA,29/10/2015,06:55,29/10/2015,09:45,S,170,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|46AR],[47AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,24417,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|47AO],[47AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|47AO],[47AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|47AR],[47AR,AI,429,DEL,MAA,29/10/2015,10:30,29/10/2015,13:15,S,165,0,0,0,AI,3,1,Y,SIPFS,0,R,9,0$1|4|47AR],[48AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,26157,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|48AO],[48AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|48AO],[48AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|48AR],[48AR,AI,142,DEL,MAA,29/10/2015,12:35,29/10/2015,15:15,T,160,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|48AR],[49AO,AI,143,MAA,DEL,24/10/2015,08:45,24/10/2015,11:30,T,165,26157,10667,0,AI,4,3,Y,TRT2,0,R,9,0$1|4|49AO],[49AO,AI,889,DEL,GAU,25/10/2015,10:50,25/10/2015,13:10,T,140,0,0,0,AI,3,,Y,TRT2,0,R,9,0$1|4|49AO],[49AR,AI,890,GAU,DEL,28/10/2015,17:05,28/10/2015,19:35,T,150,0,0,0,AI,,3,Y,TRT2,0,R,9,0$1|4|49AR],[49AR,AI,42,DEL,MAA,29/10/2015,17:15,29/10/2015,20:00,T,165,0,0,0,AI,3,4,Y,TRT7,0,R,9,0$1|4|49AR],[50AO,9W,736,MAA,DEL,24/10/2015,11:05,24/10/2015,13:45,K,160,40712,1127,0,9W,1,3,Y,K2CFA,0,R,7,0$1|4|50AO],[50AO,9W,721,DEL,GAU,24/10/2015,14:40,24/10/2015,16:55,K,135,0,0,0,9W,3,,Y,K2CFA,0,R,7,0$1|4|50AO],[50AR,9W,722,GAU,DEL,28/10/2015,17:35,28/10/2015,20:05,Q,150,0,0,0,9W,,3,Y,Q2CFA,0,R,7,0$1|4|50AR],[50AR,9W,739,DEL,MAA,28/10/2015,20:35,28/10/2015,23:25,Q,170,0,0,0,9W,3,1,Y,Q2CFA,0,R,7,0$1|4|50AR],[51AO,9W,820,MAA,DEL,24/10/2015,06:45,24/10/2015,09:25,K,160,40712,1127,0,9W,1,3,Y,K2CFA,0,R,7,0$1|4|51AO],[51AO,9W,721,DEL,GAU,24/10/2015,14:40,24/10/2015,16:55,K,135,0,0,0,9W,3,,Y,K2CFA,0,R,7,0$1|4|51AO],[51AR,9W,722,GAU,DEL,28/10/2015,17:35,28/10/2015,20:05,Q,150,0,0,0,9W,,3,Y,Q2CFA,0,R,7,0$1|4|51AR],[51AR,9W,739,DEL,MAA,28/10/2015,20:35,28/10/2015,23:25,Q,170,0,0,0,9W,3,1,Y,Q2CFA,0,R,7,0$1|4|51AR]]</FlightDetails><FareDetails>"+
		// "[[SrNo,AdultBaseFare,ChildBaseFare,InfantBaseFare,AdultTax,ChildTax,InfantTax,AdultFuelCharges,ChildFuelCharges,InfantFuelCharges,AdultPassengerServiceFee,ChildPassengerServiceFee,InfantPassengerServiceFee,AdultTransactionFee,ChildTransactionFee,InfantTransactionFee,AdultServiceCharges,ChildServiceCharges,InfantServiceCharges,AdultAirportTax,ChildAirportTax,InfantAirportTax,AdultAirportDevelopmentFee,AdultCuteFee,AdultConvenienceFee,AdultSkyCafeMeals,ChildAirportDevelopmentFee,ChildCuteFee,ChildConvenienceFee,ChildSkyCafeMeals,InfantAirportDevelopmentFee,InfantCuteFee,InfantConvenienceFee,InfantSkyCafeMeals,TotalAmount,TotalFlightCommissionAmount,TDSAmount,ServiceTax,ServiceCharge,InfantFuelFeeMarkup,InfantCuteFeeMarkup],[1AO,12870,0,0,0,0,0,7200,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,20937,0,0,0,0,0,0],[2AO,12870,0,0,0,0,0,7200,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,20937,0,0,0,0,0,0],[3AO,12350,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,21717,0,0,0,0,0,0],[4AO,12350,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,21717,0,0,0,0,0,0],[5AO,12350,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,21717,0,0,0,0,0,0],[6AO,14090,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23457,0,0,0,0,0,0],[7AO,14090,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23457,0,0,0,0,0,0],[8AO,12975,0,0,0,0,0,8400,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,22242,0,0,0,0,0,0],[9AO,12975,0,0,0,0,0,8400,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,22242,0,0,0,0,0,0],[10AO,12455,0,0,0,0,0,9700,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23022,0,0,0,0,0,0],[11AO,12455,0,0,0,0,0,9700,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23022,0,0,0,0,0,0],[12AO,12455,0,0,0,0,0,9700,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23022,0,0,0,0,0,0],[13AO,14195,0,0,0,0,0,9700,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24762,0,0,0,0,0,0],[14AO,14195,0,0,0,0,0,9700,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24762,0,0,0,0,0,0],[15AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[16AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[17AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[18AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[19AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[20AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[21AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[22AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[23AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[24AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[25AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[26AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[27AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[28AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[29AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[30AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[31AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[32AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[33AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[34AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[35AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[36AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[37AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[38AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[39AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[40AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[41AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[42AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[43AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[44AO,14270,0,0,0,0,0,8500,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23637,0,0,0,0,0,0],[45AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[46AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[47AO,13750,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,24417,0,0,0,0,0,0],[48AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[49AO,15490,0,0,0,0,0,9800,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,0,0,0,0,0,0,0,0,0,0,0,26157,0,0,0,0,0,0],[50AO,39585,0,0,0,0,0,0,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,260,0,0,0,0,0,0,0,0,0,0,40712,0,0,0,0,0,0],[51AO,39585,0,0,0,0,0,0,0,0,298,0,0,0,0,0,0,0,0,569,0,0,0,260,0,0,0,0,0,0,0,0,0,0,40712,0,0,0,0,0,0]]</FareDetails><AirlineList>[[AirlineCode,AirlineName],[9W,Jet Airways],[AI,Air India]]</AirlineList><AirportList>[[AirportCode,AirportName],[CCU,Kolkata],[DEL,Delhi],[GAU,Guwahati],[MAA,Chennai]]</AirportList></GetFlightAvailibilityResponse></GetFlightAvailibilityResult></GetFlightAvailibilityResponse></soap:Body></soap:Envelope>";

		// System.out.println("output  :"+output);
		logger.info("output :" + output);
		String flightdetails = output.substring(
				output.indexOf("<FlightDetails>") + 16,
				output.indexOf("</FlightDetails>") - 1);

		String FareDetails = output.substring(
				output.indexOf("<FareDetails>") + 14,
				output.indexOf("</FareDetails>") - 1);

		List<String> flightdetaillist = new ArrayList<String>();
		String temp = flightdetails;
		while (temp.length() > 3) {
			String flightdetail = temp.substring(1, temp.indexOf("]"));

			if (temp.indexOf("" + flightdetail + "],") != -1) {

				temp = temp.substring(flightdetail.length() + 3);

			} else {
				temp = "";
			}

			String[] flightdeatilArrayTEmp = flightdetail.split(",");

			if (flightsearch.getTripType().equalsIgnoreCase("O")) {
				flightdetaillist.add(flightdetail);
			} else if (flightsearch.getTripType().equalsIgnoreCase("R")
					&& (!flightdeatilArrayTEmp[BluestarConstants.AirlineCode]
							.equals("SG") && !flightdeatilArrayTEmp[BluestarConstants.AirlineCode]
							.equals("G8"))) {
				flightdetaillist.add(flightdetail);
			} else

			if (flightsearch.getTripType().equalsIgnoreCase("R")
					&& (flightdeatilArrayTEmp[BluestarConstants.AirlineCode]
							.equals("SG") || flightdeatilArrayTEmp[BluestarConstants.AirlineCode]
							.equals("G8"))) {
				// ///////////////////
				String flightdetailTEmp = temp.substring(1, temp.indexOf("]"));
				if (temp.indexOf("" + flightdetailTEmp + "],") != -1) {

					temp = temp.substring(flightdetailTEmp.length() + 3);

				} else {
					temp = "";
				}

				String[] flightdeatilArrayTEmp2 = flightdetailTEmp.split(",");

				List<String> tempflightlist = new ArrayList<String>();

				if (flightdeatilArrayTEmp[BluestarConstants.SrNo]
						.equals(flightdeatilArrayTEmp2[BluestarConstants.SrNo])) {

					tempflightlist.add(flightdetailTEmp);

					String roundSegmemt = "";

					while (temp.length() > 0) {

						String flightdetailTEmp3 = temp.substring(1,
								temp.indexOf("]"));
						// System.out.println("flightdetailTEmp3  :"+flightdetailTEmp3);
						if (temp.indexOf("" + flightdetailTEmp3 + "],") != -1) {

							temp = temp
									.substring(flightdetailTEmp3.length() + 3);

						} else {
							temp = "";
						}

						String[] flightdeatilArrayTEmp3 = flightdetailTEmp3
								.split(",");
						if (flightdeatilArrayTEmp[BluestarConstants.SrNo]
								.equals(flightdeatilArrayTEmp3[BluestarConstants.SrNo])) {
							tempflightlist.add(flightdetailTEmp3);
						} else {

							roundSegmemt = flightdetailTEmp3;
							break;
						}

					}

					List<String> flightdetaillistTEMP = getNewFlightDetail3(
							flightdetail, tempflightlist, roundSegmemt);
					for (String NewFlightDetailNEW : flightdetaillistTEMP) {
						flightdetaillist.add(NewFlightDetailNEW);

					}

					// //////////////////
					String[] flightdeatilArrayTEmp3 = roundSegmemt.split(",");
					if (!flightdeatilArrayTEmp3[BluestarConstants.ToAirportCode]
							.equals(flightdeatilArrayTEmp[BluestarConstants.FromAirportCode])) {
						String flightdetailTEmp4 = temp.substring(1,
								temp.indexOf("]"));
						if (temp.indexOf("" + flightdetailTEmp4 + "],") != -1) {

							temp = temp
									.substring(flightdetailTEmp4.length() + 3);

						} else {
							temp = "";
						}
						flightdetaillist.add(flightdetailTEmp4);

						String[] flightdeatilArrayTEmp4 = flightdetailTEmp4
								.split(",");
						if (!flightdeatilArrayTEmp4[BluestarConstants.ToAirportCode]
								.equals(flightdeatilArrayTEmp[BluestarConstants.FromAirportCode])) {
							String flightdetailTEmp5 = temp.substring(1,
									temp.indexOf("]"));
							if (temp.indexOf("" + flightdetailTEmp5 + "],") != -1) {

								temp = temp.substring(flightdetailTEmp5
										.length() + 3);

							} else {
								temp = "";
							}
							flightdetaillist.add(flightdetailTEmp5);
							String[] flightdeatilArrayTEmp5 = flightdetailTEmp5
									.split(",");
							if (!flightdeatilArrayTEmp5[BluestarConstants.ToAirportCode]
									.equals(flightdeatilArrayTEmp[BluestarConstants.FromAirportCode])) {
								String flightdetailTEmp6 = temp.substring(1,
										temp.indexOf("]"));
								if (temp.indexOf("" + flightdetailTEmp6 + "],") != -1) {

									temp = temp.substring(flightdetailTEmp6
											.length() + 3);

								} else {
									temp = "";
								}
								flightdetaillist.add(flightdetailTEmp6);
								String[] flightdeatilArrayTEmp6 = flightdetailTEmp6
										.split(",");
								if (!flightdeatilArrayTEmp6[BluestarConstants.ToAirportCode]
										.equals(flightdeatilArrayTEmp[BluestarConstants.FromAirportCode])) {
									String flightdetailTEmp7 = temp.substring(
											1, temp.indexOf("]"));
									if (temp.indexOf("" + flightdetailTEmp7
											+ "],") != -1) {

										temp = temp.substring(flightdetailTEmp7
												.length() + 3);

									} else {
										temp = "";
									}
									flightdetaillist.add(flightdetailTEmp7);

								}
							}
						}
					}

				} else {

					List<String> flightdetaillistTEMP = getNewFlightDetail(
							flightdetail, flightdetailTEmp);
					for (String NewFlightDetailNEW : flightdetaillistTEMP) {
						flightdetaillist.add(NewFlightDetailNEW);

					}

					if (!flightdeatilArrayTEmp2[BluestarConstants.ToAirportCode]
							.equals(flightdeatilArrayTEmp[BluestarConstants.FromAirportCode])) {
						String flightdetailTEmp4 = temp.substring(1,
								temp.indexOf("]"));
						if (temp.indexOf("" + flightdetailTEmp4 + "],") != -1) {

							temp = temp
									.substring(flightdetailTEmp4.length() + 3);

						} else {
							temp = "";
						}
						flightdetaillist.add(flightdetailTEmp4);
						String[] flightdeatilArrayTEmp4 = flightdetailTEmp4
								.split(",");

						if (!flightdeatilArrayTEmp4[BluestarConstants.ToAirportCode]
								.equals(flightdeatilArrayTEmp[BluestarConstants.FromAirportCode])) {
							String flightdetailTEmp5 = temp.substring(1,
									temp.indexOf("]"));
							if (temp.indexOf("" + flightdetailTEmp5 + "],") != -1) {

								temp = temp.substring(flightdetailTEmp5
										.length() + 3);

							} else {
								temp = "";
							}
							flightdetaillist.add(flightdetailTEmp5);
							String[] flightdeatilArrayTEmp5 = flightdetailTEmp5
									.split(",");

							if (!flightdeatilArrayTEmp5[BluestarConstants.ToAirportCode]
									.equals(flightdeatilArrayTEmp[BluestarConstants.FromAirportCode])) {
								String flightdetailTEmp6 = temp.substring(1,
										temp.indexOf("]"));
								if (temp.indexOf("" + flightdetailTEmp6 + "],") != -1) {

									temp = temp.substring(flightdetailTEmp6
											.length() + 3);

								} else {
									temp = "";
								}
								flightdetaillist.add(flightdetailTEmp6);
							}
						}

					}

				}

				// ////////////////
			}

		}
		Map<String, String[]> flightdetailMap = new HashMap<String, String[]>();

		List<String> keyList = new ArrayList<String>();

		// /LinkedHashSet<String> uniqPriceSet=new LinkedHashSet<String>();
		TreeSet<String> uniqPriceSet = new TreeSet<String>(
				new PriceComparator());
		List<String[]> flightdetailinArray = new ArrayList<String[]>();
		// //System.out.println("flightdetaillist :"+flightdetaillist.size());

		for (int i = 1; i < flightdetaillist.size(); i++) {

			String flightdeatil = flightdetaillist.get(i);
			logger.info("flightdeatil :" + flightdeatil);
			String key = flightdeatil.substring(0, flightdeatil.indexOf(","));

			String[] flightdeatilArray = flightdeatil.split(",");

			flightdetailMap.put(key, flightdeatilArray);
			if (!flightdeatilArray[BluestarConstants.TotalAmount].equals("0")) {
				uniqPriceSet.add(flightdeatilArray[BluestarConstants.TotalAmount]);

			}
			flightdetailinArray.add(flightdeatilArray);
			keyList.add(key);

		}
		// //System.out.println("flightdetailinArray :"+flightdetailinArray.size());

		List<String> faredetaillist = new ArrayList<String>();
		String temp1 = FareDetails;
		while (temp1.length() > 3) {
			String faredetail = temp1.substring(1, temp1.indexOf("]"));

			if (temp1.indexOf("" + faredetail + "],") != -1) {
				temp1 = temp1.substring(faredetail.length() + 3);
			} else {
				temp1 = "";
			}

			faredetaillist.add(faredetail);

		}
		Map<String, String[]> faredetailMap = new HashMap<String, String[]>();

		for (int i = 1; i < faredetaillist.size(); i++) {

			String faredeatil = faredetaillist.get(i);

			if (flightsearch.getTripType().equalsIgnoreCase("O")) {

				String key = faredeatil.substring(0, faredeatil.indexOf(","));

				String[] faredeatilArray = faredeatil.split(",");
				faredetailMap.put(key, faredeatilArray);
			} else

			if (flightsearch.getTripType().equalsIgnoreCase("R")) {

				String key = faredeatil.substring(0, faredeatil.indexOf(","));
				if (i != faredetaillist.size() - 1) {
					String Nextkey = faredetaillist.get(i + 1).substring(0,
							faredeatil.indexOf(","));

					if (key.substring(0, key.length() - 1).equals(
							Nextkey.substring(0, Nextkey.length() - 1))) {

						String faredetailTEMP = getNewFareDetail(faredeatil,
								faredetaillist.get(i + 1));

						// System.out.println("faredetailTEMP  :"+faredetailTEMP);
						String[] faredeatilArray = faredetailTEMP.split(",");
						faredetailMap.put(key, faredeatilArray);
						i++;
					} else {
						String[] faredeatilArray = faredeatil.split(",");
						faredetailMap.put(key, faredeatilArray);

					}
				} else {

					String[] faredeatilArray = faredeatil.split(",");
					faredetailMap.put(key, faredeatilArray);
				}

			}

		}

		logger.info(flightdetailMap.size() + "is th size , flightdetailMap :"
				+ flightdetailMap);
		logger.info(faredetailMap.size() + "is th size ,faredetailMap :"
				+ faredetailMap);
		logger.info(uniqPriceSet.size() + "is th size ,uniqPriceSet :"
				+ uniqPriceSet);
		logger.info(flightdetailinArray.size()
				+ "is th size ,flightdetailinArray :" + flightdetailinArray);

		SearchFlightResponse searchFlightResponse = null;
		try {

			if (flightsearch.getTripType().equalsIgnoreCase("O")) {
				// System.out.println("flightsearch.getTripType()  :"+flightsearch.getTripType());

				BluestarGetFlightAvailabilityResponseParser
						.parseResponseOnewayMerge(flightdetailMap,
								faredetailMap, uniqPriceSet,
								FlightMarkUpConfiglist, flightsearch,
								AirlineNameMap, MapList, flightdetailinArray,
								tempDAO, fareFlightSegments,
								uapiSearchFlightKeyMap);
			} else {

				// System.out.println("flightsearch.getTripType()  :"+flightsearch.getTripType());
				BluestarGetFlightAvailabilityResponseParser
						.parseResponseRoundTripMerge(flightdetailMap,
								faredetailMap, uniqPriceSet,
								FlightMarkUpConfiglist, flightsearch,
								AirlineNameMap, MapList, flightdetailinArray,
								tempDAO, fareFlightSegments,
								uapiSearchFlightKeyMap);
				;

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static List<String> getNewFlightDetail(String flightdetail,
			String flightdetailTEmp) {

		List<String> flightdetaillist = new ArrayList<String>();

		String[] flightdeatilArray1 = flightdetail.split(",");
		String[] flightdeatilArray2 = flightdetailTEmp.split(",");

		String newtax2 = "0";

		BigDecimal newtotal = new BigDecimal(
				flightdeatilArray1[BluestarConstants.TotalAmount]).add(new BigDecimal(
				flightdeatilArray2[BluestarConstants.TotalAmount]));
		BigDecimal newtax = new BigDecimal(
				flightdeatilArray1[BluestarConstants.TaxAmount]).add(new BigDecimal(
				flightdeatilArray2[BluestarConstants.TaxAmount]));

		String newtotalamount1 = newtotal.toString();
		String newtotalamount2 = "0";
		String newtax1 = newtax.toString();
		flightdeatilArray1[BluestarConstants.TotalAmount] = newtotalamount1;
		flightdeatilArray1[BluestarConstants.TaxAmount] = newtax1;

		flightdeatilArray2[BluestarConstants.TotalAmount] = newtotalamount2;
		flightdeatilArray2[BluestarConstants.TaxAmount] = newtax2;

		StringBuilder sb1 = new StringBuilder();
		for (int i = 0; i < flightdeatilArray1.length; i++) {
			sb1.append(flightdeatilArray1[i]);
			if (i < flightdeatilArray1.length - 1) {
				sb1.append(",");
			}

		}
		StringBuilder sb2 = new StringBuilder();
		for (int i = 0; i < flightdeatilArray2.length; i++) {
			sb2.append(flightdeatilArray2[i]);
			if (i < flightdeatilArray2.length - 1) {
				sb2.append(",");
			}
		}
		flightdetaillist.add(sb1.toString());

		flightdetaillist.add(sb2.toString());
		// System.out.println(" temp only two flightdetaillist  :"+flightdetaillist);
		return flightdetaillist;
	}

	public static List<String> getNewFlightDetail3(String flightdetail,
			List<String> flightdetailTEmpMiddle, String flightdetailTEmp) {

		logger.info("flightdetail :" + flightdetail);
		logger.info("flightdetailTEmpMiddle :" + flightdetailTEmpMiddle);
		logger.info("flightdetailTEmp :" + flightdetailTEmp);

		List<String> flightdetaillist = new ArrayList<String>();

		String[] flightdeatilArray1 = flightdetail.split(",");
		String[] flightdeatilArray2 = flightdetailTEmp.split(",");

		String newtax2 = "0";

		BigDecimal newtotal = new BigDecimal(
				flightdeatilArray1[BluestarConstants.TotalAmount]).add(new BigDecimal(
				flightdeatilArray2[BluestarConstants.TotalAmount]));
		BigDecimal newtax = new BigDecimal(
				flightdeatilArray1[BluestarConstants.TaxAmount]).add(new BigDecimal(
				flightdeatilArray2[BluestarConstants.TaxAmount]));

		String newtotalamount1 = newtotal.toString();
		String newtotalamount2 = "0";
		String newtax1 = newtax.toString();
		flightdeatilArray1[BluestarConstants.TotalAmount] = newtotalamount1;
		flightdeatilArray1[BluestarConstants.TaxAmount] = newtax1;

		flightdeatilArray2[BluestarConstants.TotalAmount] = newtotalamount2;
		flightdeatilArray2[BluestarConstants.TaxAmount] = newtax2;

		StringBuilder sb1 = new StringBuilder();
		for (int i = 0; i < flightdeatilArray1.length; i++) {
			sb1.append(flightdeatilArray1[i]);
			if (i < flightdeatilArray1.length - 1) {
				sb1.append(",");
			}

		}
		StringBuilder sb2 = new StringBuilder();
		for (int i = 0; i < flightdeatilArray2.length; i++) {
			sb2.append(flightdeatilArray2[i]);
			if (i < flightdeatilArray2.length - 1) {
				sb2.append(",");
			}
		}
		flightdetaillist.add(sb1.toString());
		for (String tempflight : flightdetailTEmpMiddle) {

			flightdetaillist.add(tempflight);
		}
		flightdetaillist.add(sb2.toString());
		// System.out.println(" temp flightdetaillist  :"+flightdetaillist);

		return flightdetaillist;
	}

	public static String getNewFareDetail(String flightdetail,
			String flightdetailTEmp) {

		String[] flightdeatilArray1 = flightdetail.split(",");
		String[] flightdeatilArray2 = flightdetailTEmp.split(",");

		StringBuilder sb1 = new StringBuilder();

		for (int i = 0; i < flightdeatilArray1.length; i++) {

			if (i != 0) {
				BigDecimal newtotal = new BigDecimal(flightdeatilArray1[i])
						.add(new BigDecimal(flightdeatilArray2[i]));

				sb1.append(newtotal.toString());
			} else {
				sb1.append(flightdeatilArray1[i]);
			}
			if (i < flightdeatilArray1.length - 1) {
				sb1.append(",");
			}

		}

		return sb1.toString();
	}
}
*/