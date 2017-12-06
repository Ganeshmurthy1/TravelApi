/**
@Author Vimal Susai Raj
20-apr-2017
UapiServiceCall.java
 */

package com.tayyarah.flight.util.api.travelport;

import static javax.xml.bind.DatatypeConverter.printBase64Binary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tayyarah.api.flight.tbo.model.TboFlightAirpriceRequest;
import com.tayyarah.api.flight.tbo.model.TboFlightAirpriceResponse;
import com.tayyarah.api.flight.tbo.model.TboFlightSSRRequest;
import com.tayyarah.api.flight.tbo.model.TboSRRResponse;
import com.tayyarah.apiconfig.model.BluestarConfig;
import com.tayyarah.apiconfig.model.LintasConfig;
import com.tayyarah.apiconfig.model.TayyarahConfig;
import com.tayyarah.apiconfig.model.TboFlightConfig;
import com.tayyarah.apiconfig.model.TravelportConfig;
import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.FileUtil;
import com.tayyarah.common.util.soap.MarshalUnmarshal;
import com.tayyarah.common.util.soap.SoapClient;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.entity.FlightAirPriceDetailsTemp;
import com.tayyarah.flight.entity.FlightBookingDetailsTemp;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.entity.FlightSearchDetailsTemp;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.BluestarPriceData;
import com.tayyarah.flight.model.BookingDetails;
import com.tayyarah.flight.model.FareFlightSegment;
import com.tayyarah.flight.model.FareRuleResponse;
import com.tayyarah.flight.model.FareRules;
import com.tayyarah.flight.model.FlightBookingResponse;
import com.tayyarah.flight.model.FlightCustomerDetails;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.FlightSegments;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.MarkupCommissionDetails;
import com.tayyarah.flight.model.PassengerDetails;
import com.tayyarah.flight.model.SearchFlightResponse;
import com.tayyarah.flight.model.SpecialServiceRequest;
import com.tayyarah.flight.model.UAPISearchFlightKeyMap;
import com.tayyarah.flight.model.UapiAirSegment;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.flight.util.api.bluestar.BlueStarFareRuleRequestBuilder;
import com.tayyarah.flight.util.api.bluestar.BluestarAirPriceResponseParser;
import com.tayyarah.flight.util.api.bluestar.BluestarGetFlightAvailibilityRequestBuilder;
import com.tayyarah.flight.util.api.bluestar.BluestarGetFlightPriceXMLObjectCoversion;
import com.tayyarah.flight.util.api.bluestar.BluestarServiceCall;
import com.tayyarah.flight.util.api.lintas.LintasAirPriceResponseParser;
import com.tayyarah.flight.util.api.tayyarah.TayyarahAirPriceResponseParser;
import com.tayyarah.flight.util.api.tbo.TboRequestBuilder;
import com.tayyarah.flight.util.api.tbo.TboResponseParser;
import com.tayyarah.flight.util.api.tbo.TboServiceCall;
import com.tayyarah.user.entity.WalletAmountTranferHistory;
import com.travelport.api_v33.AirResponse.AirFareRulesRsp;
import com.travelport.api_v33.AirResponse.AirPriceRsp;
import com.travelport.api_v33.AirResponse.FareInfo;
import com.travelport.api_v33.AirResponse.LowFareSearchRsp;
import com.travelport.api_v33.Universal.AirCreateReservationRsp;

public class UapiServiceCall {
	static final Logger logger = Logger.getLogger(UapiServiceCall.class);

	public static SearchFlightResponse callSearchService(Flightsearch flightsearch, Map<String,List<FlightMarkUpConfig>> markupMap,Map<String, String>AirlineNameMap,ArrayList<Map<String,String>> MapList,boolean isDomestic, String airlineFromList,MoneyExchangeDao moneydao,TravelportConfig travelportConfig) throws Exception
	{
		StringBuilder requestData= LowFareSearchRequestBuilder.createSearchRequest(travelportConfig, flightsearch,airlineFromList);
		SOAPMessage soapMessageReq = SoapClient.buildSoapMsgFromStr(requestData);
		soapMessageReq = addSecureHeader(soapMessageReq, travelportConfig);
		SOAPMessage soapMessageRes=callService(soapMessageReq, travelportConfig);
		ByteArrayOutputStream in = new ByteArrayOutputStream();
		SearchFlightResponse searchFlightResponse = null;
		String traskey = flightsearch.getTransactionKey().replaceAll(":", "_");
		FileUtil.writeSoap("flight", "travelport", "SearchFlightResponse", false, requestData.toString(), traskey);
		try {
			soapMessageRes.writeTo(in);
			logger.info("Search Response :"+in);
			FileUtil.writeSoap("flight", "travelport", "SearchFlightResponse", true, new String(in.toByteArray(),"UTF-8"), traskey);

		} catch (IOException e) {
			logger.error("IOException",e);
		}
		LowFareSearchRsp lowFareSearchRsp;
		lowFareSearchRsp = null;
		try {
			MarshalUnmarshal marshalUnmarshal=new MarshalUnmarshal();
			lowFareSearchRsp=(LowFareSearchRsp)marshalUnmarshal.unMarshal(LowFareSearchRsp.class, soapMessageRes);
		}catch (JAXBException e) {
			logger.error("JAXBException", e);
			throw new FlightException(ErrorCodeCustomerEnum.JAXBException,
					FlightErrorMessages.NO_FLIGHT);
		} catch (Exception e) {
			logger.error("Exception", e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,
					FlightErrorMessages.NO_FLIGHT);
		}
		if(flightsearch.getTripType().equalsIgnoreCase("R")&&isDomestic){		
			searchFlightResponse= LowFareSearchResponseParser.parseResponseRoundTripWithSingleGroup(lowFareSearchRsp,markupMap,flightsearch,AirlineNameMap,MapList,moneydao);
		}else{
			searchFlightResponse= LowFareSearchResponseParser.parseResponseOnewayRound(lowFareSearchRsp,markupMap,flightsearch,AirlineNameMap,MapList,moneydao);
		}
		return searchFlightResponse;
	}


	public static FareRuleResponse callFareRuleService(TravelportConfig travelportConfig,FareInfo fareInfo) throws SOAPException, ClassNotFoundException, JAXBException
	{
		FareRuleResponse fareRuleResponse = null;
		if(fareInfo.getFareRuleKey().getProviderCode().startsWith("BS")&&fareInfo.getFareRuleKey().getProviderCode().length()<15){
			BluestarConfig bluestarConfig = BluestarConfig.GetBluestarConfig(null);
			StringBuilder requestData = null;
			String ProviderCode=fareInfo.getFareRuleKey().getProviderCode();
			String trackNO=ProviderCode.substring(2);
			requestData = BlueStarFareRuleRequestBuilder.createFareRuleRequest(trackNO);

			SOAPMessage soapMessageReq =SoapClient.buildSoapMsgFromStr(requestData);
			SOAPMessage soapMessageRes = BluestarServiceCall.callService(soapMessageReq, bluestarConfig);
			ByteArrayOutputStream in = new ByteArrayOutputStream();
			try {
				soapMessageRes.writeTo(in);				
				logger.info("Search Response :"+in);
			} catch (IOException e) {
				logger.error("IOException",e);
			}
			String output = in.toString();		
			output = output.replaceAll("&lt;", "<");output=output.replaceAll("&gt;", ">");
			if(output.indexOf("<GetFareRuleResponse>")==-1){
				throw new FlightException(ErrorCodeCustomerEnum.JAXBException,FlightErrorMessages.NO_AIRPRICE);
			}else{
				String FareRuleResponse=output.substring(output.indexOf("<GetFareRuleResponse>")+21,output.indexOf("</GetFareRuleResponse>"));
				return fareRuleResponse;
			}
		}
		else{
			StringBuilder requestData= AirFareRuleRequestBuilder.buildFareRuleReq(travelportConfig, fareInfo);
			SOAPMessage soapMessageReq =SoapClient.buildSoapMsgFromStr(requestData);
			soapMessageReq = addSecureHeader(soapMessageReq, travelportConfig);
			SOAPMessage soapMessageRes=callService(soapMessageReq, travelportConfig);
			ByteArrayOutputStream in = new ByteArrayOutputStream();
			try {
				soapMessageRes.writeTo(in);					
			} catch (IOException e) {
				logger.error("IOException",e);
			}
			MarshalUnmarshal marshalUnmarshal=new MarshalUnmarshal();
			AirFareRulesRsp airFareRulesRsp = null;
			airFareRulesRsp=(AirFareRulesRsp)marshalUnmarshal.unMarshal(AirFareRulesRsp.class, soapMessageRes);
			fareRuleResponse= AirFareRuleResponseParser.parseFareRule(airFareRulesRsp);
		}
		return fareRuleResponse;
	}

	public static FlightPriceResponse callAirPriceService(String flightindex,String searchkey,Map<String,String> AirlineNameMap,ArrayList<Map<String,String>> MapList,FlightTempAirSegmentDAO tempDAO,AppKeyVo appKeyVo,CompanyConfigDAO companyConfigDAO,CompanyDao companyDao,boolean isLowFare,String lowFareFlightIndex1,String lowFareFlightIndex2,String reasonToSelect,boolean isLowFareReturn,String lowFareFlightIndexReturn1,String lowFareFlightIndexReturn2,String reasonToSelectReturn)
			throws Exception
	{
		FlightDataBaseServices DBS = new FlightDataBaseServices();
		FlightSearchDetailsTemp searchDetails = DBS.getUAPISearchFlightKeyMap(searchkey, tempDAO);
		byte[] abc = searchDetails.getUapiSearchFlightKeyMap();
		String transaction_key = searchDetails.getTransactionkey();
		UAPISearchFlightKeyMap uapiSearchFlightKeyMap = FlightDataBaseServices.convertByteArrayToUAPISearchFlightKeyMap(abc);
		LinkedHashMap<String, List<UapiAirSegment>> UapiAirSegmentListMap = uapiSearchFlightKeyMap.getUapiAirSegmentListMap();
		Flightsearch flightsearch=uapiSearchFlightKeyMap.getFlightsearch();
		List<String> fligtindexlist = getfligtindexlist(flightindex);
		FlightPriceResponse flightPriceResponse = null;
		LinkedHashMap<String, FlightSegments> FlightSegmentstMap = uapiSearchFlightKeyMap.getFlightSegmentstMap();
		MarkupCommissionDetails markupCommissionDetails = uapiSearchFlightKeyMap.getMarkupCommissionDetails();
		Map<String,List<FlightMarkUpConfig>> markupMap = uapiSearchFlightKeyMap.getFlightMarkUpConfiglistMap();
		String pricekey = "";
		// Used only for TBO Flight API
		TboFlightAirpriceResponse tboflightairpriceresponse = null;
		TboFlightAirpriceResponse tboflightairpriceresponsespecial = null;
		
		//***********************************************************************************************************************************************************************************************************

		if(fligtindexlist!=null && fligtindexlist.size()>0 && flightsearch.isSpecialSearch() && (flightsearch.getTripType().equalsIgnoreCase("R") )){//for special search
			if( fligtindexlist.get(0).startsWith("TP"))
			{
				pricekey = "PTP" + new UID().toString();

				TravelportConfig travelportConfig = TravelportConfig.GetTravelportConfig();
				StringBuilder requestData= AirPriceRequestBuilder.createAirpriceRequestSpecial(travelportConfig, fligtindexlist.get(0), UapiAirSegmentListMap,flightsearch);
				SOAPMessage soapMessageReq =SoapClient.buildSoapMsgFromStr(requestData);
				soapMessageReq = addSecureHeader(soapMessageReq, travelportConfig);
				SOAPMessage soapMessageRes=callService(soapMessageReq, travelportConfig);
				ByteArrayOutputStream in = new ByteArrayOutputStream();
				String trasKEy=transaction_key;
				FileUtil.writeSoap("flight", "travelport", "SearchFlightResponse", false, requestData.toString(), trasKEy);
				try {
					soapMessageRes.writeTo(in);
					FileUtil.writeSoap("flight", "travelport", "flightPriceResponse", true, new String(in.toByteArray(),"UTF-8"), trasKEy);

					logger.info("Price Response :"+in);
				} catch (IOException e) {
					logger.error("IOException",e);
				}
				MarshalUnmarshal marshalUnmarshal=new MarshalUnmarshal();
				AirPriceRsp aiPriceRsp = null;
				aiPriceRsp = (AirPriceRsp)marshalUnmarshal.unMarshal(AirPriceRsp.class, soapMessageRes);	
				flightPriceResponse = AirPriceResponseParser.parseAirPrice(aiPriceRsp, markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,UapiAirSegmentListMap.get(fligtindexlist.get(0)).get(0).getExchangeRate(),UapiAirSegmentListMap.get(fligtindexlist.get(0)).get(0).getApiToBaseExchangeRate(),markupCommissionDetails);

			}else if(fligtindexlist.get(0).startsWith("TAY")){

				pricekey = "PTAY" + new UID().toString();				
				ObjectMapper mapper = new ObjectMapper();
				FlightPriceResponse flightPriceResponseOLD = null;
				TayyarahConfig tayyarahConfig = TayyarahConfig.GetTayyarahConfig();
				try {
					String newURL=tayyarahConfig.getUrl()+"/airprice/response?app_key="+tayyarahConfig.getApp_key()+"&flightindex="+fligtindexlist.get(0)+"&searchkey="+searchkey;
					flightPriceResponseOLD = mapper.readValue(new URL(newURL), FlightPriceResponse.class);
					//Pretty print
					String flightPriceResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(flightPriceResponseOLD);
					logger.info("flightPriceResponseInString  :"+flightPriceResponseInString);

				} catch (JsonGenerationException e) {
					logger.error("JsonGenerationException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				} catch (JsonMappingException e) {
					logger.error("JsonMappingException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				} catch (IOException e) {
					logger.error("IOException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				}
				catch (Exception e) {
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
				}
				try {
					flightPriceResponse = TayyarahAirPriceResponseParser.parseAirPrice(flightPriceResponseOLD, markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,FlightSegmentstMap.get(fligtindexlist.get(0)).getExchangeRate(),uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(0)).getApiToBaseExchangeRate(),markupCommissionDetails);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
				}
			}else if(fligtindexlist.get(0).startsWith("LIN")){

				pricekey = "PLIN" + new UID().toString();				
				ObjectMapper mapper = new ObjectMapper();
				FlightPriceResponse flightPriceResponseOLD = null;
				LintasConfig lintasConfig=LintasConfig.GetLintasConfig();
				try {
					String newURL=lintasConfig.getUrl()+"/airprice/response?app_key="+lintasConfig.getApp_key()+"&flightindex="+fligtindexlist.get(0)+"&searchkey="+searchkey;
					flightPriceResponseOLD = mapper.readValue(new URL(newURL), FlightPriceResponse.class);
					//Pretty print
					String flightPriceResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(flightPriceResponseOLD);

				} catch (JsonGenerationException e) {
					logger.error("JsonGenerationException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				} catch (JsonMappingException e) {
					logger.error("JsonMappingException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				} catch (IOException e) {
					logger.error("IOException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				}
				catch (Exception e) {
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
				}
				try {
					flightPriceResponse = LintasAirPriceResponseParser.parseAirPrice(flightPriceResponseOLD, markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,
							FlightSegmentstMap.get(fligtindexlist.get(0)).getExchangeRate(),uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(0)).getApiToBaseExchangeRate(),markupCommissionDetails);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);

				}
			}else if(fligtindexlist.get(0).startsWith("TB")){
				try{
					TboFlightConfig tboconfig = TboFlightConfig.GetTboConfig(appKeyVo);
					pricekey = "PTBS" + new UID().toString();
					String tempflightIndex = fligtindexlist.get(0);

					Map<String, FareRules> faredetailMap = uapiSearchFlightKeyMap.getFareRulesMap();
					FareRules faredetail =  faredetailMap.get(tempflightIndex);

					TboFlightAirpriceRequest requestData = TboRequestBuilder.getTboRequestBuilder().createAirpriceRequestbuilder(faredetail,tboconfig);
					tboflightairpriceresponse = TboServiceCall.callAirpriceService(requestData,transaction_key,searchkey,tboconfig);
					flightPriceResponse = TboResponseParser.parseAirPrice(tboflightairpriceresponse,markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,
							FlightSegmentstMap.get(fligtindexlist.get(0)).getExchangeRate(),uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(0)),markupCommissionDetails,companyConfigDAO,appKeyVo,companyDao);
					TboFlightSSRRequest ssrrequest = TboRequestBuilder.getTboRequestBuilder().createSSRRequestBuilder(faredetail,tboconfig);
					TboSRRResponse tboSRRResponse = TboServiceCall.callSSRService(ssrrequest,transaction_key,searchkey,tboconfig);
					SpecialServiceRequest SSR = null;
					if(tboSRRResponse.getResponse().getError().getErrorCode() == 0){
						SSR = new SpecialServiceRequest();
						SSR.setIsLCC(tboflightairpriceresponse.getResponse().getResults().getIsLCC());
						SSR.setBaggage(tboSRRResponse.getResponse().getBaggage());
						SSR.setMealDynamic(tboSRRResponse.getResponse().getMealDynamic());
						SSR.setSeatPreference(tboSRRResponse.getResponse().getSeatPreference());
						SSR.setMeal(tboSRRResponse.getResponse().getMeal());
					}
					flightPriceResponse.setSpecialServiceRequest(SSR);
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
				}
			}
			else  if(fligtindexlist.get(0).startsWith("BS")){

				BluestarConfig bluestarConfig = BluestarConfig.GetBluestarConfig(appKeyVo);
				pricekey = "PBS" + new UID().toString();
				StringBuilder requestData = null;
				String tempflightIndex=fligtindexlist.get(0);
				requestData=BluestarGetFlightAvailibilityRequestBuilder.createVerifyFlightDetailRequestOneway(FlightSegmentstMap.get(tempflightIndex).getSegments().get(0).getTrackno(),bluestarConfig);

				SOAPMessage soapMessageReq =SoapClient.buildSoapMsgFromStr(requestData);
				SOAPMessage soapMessageRes=BluestarServiceCall.callService(soapMessageReq,bluestarConfig);			
				String[] search_key=null;
				try{
					search_key = flightsearch.getTransactionKey().split(":");
				} catch (Exception e) {
					search_key=new UID().toString().split(":");
				}

				try{
					FileUtil.writeSoap("flight", "bluestar", "pricing", false, requestData.toString(), String.valueOf(search_key[1]+search_key[2]));
				} catch (Exception e) {
					logger.error(" The filename, directory name ", e);
				}

				try{
					FileUtil.writeSoap("flight", "bluestar", "pricing", true, soapMessageRes,  String.valueOf(search_key[1]+search_key[2]));
				} catch (Exception e) {
					logger.error(" The filename, directory name ", e);
				}


				ByteArrayOutputStream flightPriceByteStream = new ByteArrayOutputStream();
				try {
					soapMessageRes.writeTo(flightPriceByteStream);					
					logger.info("Search Response :"+flightPriceByteStream);
				} catch (IOException e) {
					logger.error("IOException",e);
				}

				String flightPriceStringData=flightPriceByteStream.toString();				
				flightPriceStringData=flightPriceStringData.replaceAll("&lt;", "<");flightPriceStringData=flightPriceStringData.replaceAll("&gt;", ">");
				if(flightPriceStringData.indexOf("<FlightDetails>")==-1)
				{
					throw new FlightException(ErrorCodeCustomerEnum.JAXBException,FlightErrorMessages.NO_AIRPRICE);
				}
				else
				{
					BluestarPriceData bluestarPriceData = BluestarGetFlightPriceXMLObjectCoversion.createFlightPriceResponseFromXML(FlightSegmentstMap, flightPriceByteStream, flightsearch, uapiSearchFlightKeyMap,tempflightIndex);
					Map<String, FareRules> FareRulesMap = uapiSearchFlightKeyMap.getFareRulesMap();
					Map<String, FareFlightSegment> FareFlightSegmentMap = uapiSearchFlightKeyMap.getFareFlightSegmentMap();
					flightPriceResponse= BluestarAirPriceResponseParser.parseAirPrice(FareFlightSegmentMap,FlightSegmentstMap,FareRulesMap, markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,FlightSegmentstMap.get(fligtindexlist.get(0)).getExchangeRate(),fligtindexlist,uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(0)).getApiToBaseExchangeRate(),markupCommissionDetails,bluestarPriceData);
				}
			}
			else{
				throw new FlightException(ErrorCodeCustomerEnum.JAXBException,FlightErrorMessages.INVALID_FLIGHTINDEX);
			}	

			if(fligtindexlist.get(1).startsWith("TP")){

				pricekey = "PTP" + new UID().toString();
				TravelportConfig travelportConfig=TravelportConfig.GetTravelportConfig();				
				StringBuilder requestData= AirPriceRequestBuilder.createAirpriceRequestSpecial(travelportConfig, fligtindexlist.get(1), UapiAirSegmentListMap,flightsearch);
				SOAPMessage soapMessageReq =SoapClient.buildSoapMsgFromStr(requestData);
				soapMessageReq = addSecureHeader(soapMessageReq, travelportConfig);
				SOAPMessage soapMessageRes=callService(soapMessageReq, travelportConfig);
				ByteArrayOutputStream in = new ByteArrayOutputStream();
				String trasKEy=transaction_key;
				FileUtil.writeSoap("flight", "travelport", "flightPriceResponse", false, requestData.toString(), trasKEy);
				try {
					soapMessageRes.writeTo(in);
					FileUtil.writeSoap("flight", "travelport", "flightPriceResponse", true, new String(in.toByteArray(),"UTF-8"), trasKEy);
					logger.info("Price Response :"+in);
				} catch (IOException e) {
					logger.error("IOException",e);
				}
				MarshalUnmarshal marshalUnmarshal=new MarshalUnmarshal();
				AirPriceRsp aiPriceRsp = null;
				aiPriceRsp = (AirPriceRsp)marshalUnmarshal.unMarshal(AirPriceRsp.class, soapMessageRes);
				AirPriceResponseParser.parseAirPriceSpecial(aiPriceRsp, markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,UapiAirSegmentListMap.get(fligtindexlist.get(1)).get(0).getExchangeRate(),UapiAirSegmentListMap.get(fligtindexlist.get(1)).get(0).getApiToBaseExchangeRate(),markupCommissionDetails,flightPriceResponse);

			}else if(fligtindexlist.get(1).startsWith("TAY")){

				pricekey = "PTAY" + new UID().toString();				
				ObjectMapper mapper = new ObjectMapper();
				FlightPriceResponse flightPriceResponseOLD = null;
				TayyarahConfig tayyarahConfig = TayyarahConfig.GetTayyarahConfig();
				try {
					String newURL=tayyarahConfig.getUrl()+"/airprice/response?app_key="+tayyarahConfig.getApp_key()+"&flightindex="+fligtindexlist.get(1)+"&searchkey="+searchkey;
					flightPriceResponseOLD = mapper.readValue(new URL(newURL), FlightPriceResponse.class);
					//Pretty print
					String flightPriceResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(flightPriceResponseOLD);
					logger.info("flightPriceResponseInString  :"+flightPriceResponseInString);

				} catch (JsonGenerationException e) {
					logger.error("JsonGenerationException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				} catch (JsonMappingException e) {
					logger.error("JsonMappingException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				} catch (IOException e) {
					logger.error("IOException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				}
				catch (Exception e) {
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
				}
				try {
					TayyarahAirPriceResponseParser.parseAirPriceSpecial(flightPriceResponseOLD, markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,FlightSegmentstMap.get(fligtindexlist.get(1)).getExchangeRate(),uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(1)).getApiToBaseExchangeRate(),markupCommissionDetails,flightPriceResponse);
				} catch (Exception e) {
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
				}

			}else if(fligtindexlist.get(1).startsWith("LIN")){
				pricekey = "PLIN" + new UID().toString();
				ObjectMapper mapper = new ObjectMapper();
				FlightPriceResponse flightPriceResponseOLD = null;
				LintasConfig lintasConfig=LintasConfig.GetLintasConfig();
				try {
					String newURL=lintasConfig.getUrl()+"/airprice/response?app_key="+lintasConfig.getApp_key()+"&flightindex="+fligtindexlist.get(1)+"&searchkey="+searchkey;
					flightPriceResponseOLD = mapper.readValue(new URL(newURL), FlightPriceResponse.class);
					//Pretty print
					String flightPriceResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(flightPriceResponseOLD);
					logger.info("flightPriceResponseInString  :"+flightPriceResponseInString);

				} catch (JsonGenerationException e) {
					logger.error("JsonGenerationException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				} catch (JsonMappingException e) {
					logger.error("JsonMappingException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				} catch (IOException e) {
					logger.error("IOException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				}
				catch (Exception e) {
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
				}
				try {
					LintasAirPriceResponseParser.parseAirPriceSpecial(flightPriceResponseOLD, markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,
							FlightSegmentstMap.get(fligtindexlist.get(1)).getExchangeRate(),uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(1)).getApiToBaseExchangeRate(),markupCommissionDetails,flightPriceResponse);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);

				}
			}
			else if(fligtindexlist.get(1).startsWith("TB")){

				try{
					TboFlightConfig tboconfig = TboFlightConfig.GetTboConfig(appKeyVo);
					pricekey = "PTBS" + new UID().toString();
					String tempflightIndexnew = fligtindexlist.get(1);
					Map<String, FareRules> faredetailMap = uapiSearchFlightKeyMap.getFareRulesMap();
					FareRules faredetail =  faredetailMap.get(tempflightIndexnew);
					TboFlightAirpriceRequest requestData = TboRequestBuilder.getTboRequestBuilder().createAirpriceRequestbuilder(faredetail,tboconfig);
					tboflightairpriceresponsespecial = TboServiceCall.callAirpriceService(requestData,transaction_key,searchkey,tboconfig);
					TboResponseParser.parseAirPriceSpecial(tboflightairpriceresponsespecial,markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,
							FlightSegmentstMap.get(fligtindexlist.get(1)).getExchangeRate(),uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(1)),markupCommissionDetails,flightPriceResponse,companyConfigDAO,appKeyVo,companyDao);
					TboFlightSSRRequest ssrrequest = TboRequestBuilder.getTboRequestBuilder().createSSRRequestBuilder(faredetail,tboconfig);
					TboSRRResponse tboSRRResponse = TboServiceCall.callSSRService(ssrrequest,transaction_key,searchkey,tboconfig);
					SpecialServiceRequest SSR = null;
					if(tboSRRResponse.getResponse().getError().getErrorCode() == 0){
						SSR = new SpecialServiceRequest();
						SSR.setIsLCC(tboflightairpriceresponsespecial.getResponse().getResults().getIsLCC());
						SSR.setBaggage(tboSRRResponse.getResponse().getBaggage());
						SSR.setMealDynamic(tboSRRResponse.getResponse().getMealDynamic());
						SSR.setSeatPreference(tboSRRResponse.getResponse().getSeatPreference());
						SSR.setMeal(tboSRRResponse.getResponse().getMeal());
					}
					flightPriceResponse.setReturnspecialServiceRequest(SSR);
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
				}
			}
			else  if(fligtindexlist.get(1).startsWith("BS")){

				BluestarConfig bluestarConfig = BluestarConfig.GetBluestarConfig(appKeyVo);
				pricekey = "PBS" + new UID().toString();
				StringBuilder requestData = null;
				String tempflightIndex=fligtindexlist.get(1);
				requestData = BluestarGetFlightAvailibilityRequestBuilder.createVerifyFlightDetailRequestOnewaySpecial(FlightSegmentstMap.get(tempflightIndex).getSegments().get(0).getTrackno(),bluestarConfig);
				SOAPMessage soapMessageReq =SoapClient.buildSoapMsgFromStr(requestData);
				SOAPMessage soapMessageRes = BluestarServiceCall.callService(soapMessageReq,bluestarConfig);
				String[] search_key=null;
				try{
					search_key = flightsearch.getTransactionKey().split(":");
				} catch (Exception e) {
					search_key=new UID().toString().split(":");
				}
				try{
					FileUtil.writeSoap("flight", "bluestar", "pricing", false, requestData.toString(), String.valueOf(search_key[1]+search_key[2]));
				} catch (Exception e) {
					logger.error(" The filename, directory name ", e);
				}

				try{
					FileUtil.writeSoap("flight", "bluestar", "pricing", true, soapMessageRes,  String.valueOf(search_key[1]+search_key[2]));
				} catch (Exception e) {
					logger.error(" The filename, directory name ", e);
				}

				ByteArrayOutputStream flightPriceByteStream = new ByteArrayOutputStream();
				try {
					soapMessageRes.writeTo(flightPriceByteStream);
					// System.out.println("API Response \n"+in);
					logger.info("Search Response :"+flightPriceByteStream);
				} catch (IOException e) {
					logger.error("IOException",e);
				}

				String flightPriceStringData = flightPriceByteStream.toString();			
				flightPriceStringData=flightPriceStringData.replaceAll("&lt;", "<");flightPriceStringData=flightPriceStringData.replaceAll("&gt;", ">");
				if(flightPriceStringData.indexOf("<FlightDetails>")==-1)
				{
					throw new FlightException(ErrorCodeCustomerEnum.JAXBException,FlightErrorMessages.NO_AIRPRICE);
				}
				else
				{
					BluestarPriceData bluestarPriceData = BluestarGetFlightPriceXMLObjectCoversion.createFlightPriceResponseFromXML(FlightSegmentstMap, flightPriceByteStream, flightsearch, uapiSearchFlightKeyMap,tempflightIndex);
					Map<String, FareRules> FareRulesMap = uapiSearchFlightKeyMap.getFareRulesMap();
					Map<String, FareFlightSegment> FareFlightSegmentMap = uapiSearchFlightKeyMap.getFareFlightSegmentMap();
					BluestarAirPriceResponseParser.parseAirPriceSpecial(FareFlightSegmentMap,FlightSegmentstMap,FareRulesMap, markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,FlightSegmentstMap.get(tempflightIndex).getExchangeRate(),fligtindexlist,uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(tempflightIndex).getApiToBaseExchangeRate(),markupCommissionDetails,flightPriceResponse,bluestarPriceData);
				}
			}
		}
		//***********************************************************************************************************************************************************************************************************

		//////////// end of special search//////////
		else if(fligtindexlist!=null && fligtindexlist.size()>0 && flightsearch.isSpecialSearch() && flightsearch.getTripType().equalsIgnoreCase("SR")){


			try {
				TboFlightConfig tboconfig = TboFlightConfig.GetTboConfig(appKeyVo);
				pricekey = "PTB" + new UID().toString();
				String tempflightIndex = fligtindexlist.get(0);
				Map<String, FareRules> faredetailMap = uapiSearchFlightKeyMap.getFareRulesMap();
				FareRules faredetail =  faredetailMap.get(tempflightIndex);
				String tempflightIndexReturn = fligtindexlist.get(1);
				FareRules faredetailReturn =  faredetailMap.get(tempflightIndexReturn);
				TboFlightAirpriceRequest requestData = TboRequestBuilder.getTboRequestBuilder().createSpecialReturnAirpriceRequestbuilder(faredetail,faredetailReturn,tboconfig);
				tboflightairpriceresponse = TboServiceCall.callAirpriceService(requestData,transaction_key,searchkey,tboconfig);
				flightPriceResponse = TboResponseParser.parseSpecialReturnAirPrice(tboflightairpriceresponse,markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,
						FlightSegmentstMap.get(fligtindexlist.get(0)).getExchangeRate(),uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(0)),markupCommissionDetails,companyConfigDAO,appKeyVo,companyDao);
				TboFlightSSRRequest ssrrequest = TboRequestBuilder.getTboRequestBuilder().createSpecialReturnSSRRequestBuilder(tboflightairpriceresponse.getResponse().getResults().getResultIndex(),tboflightairpriceresponse.getResponse().getTraceId(),tboconfig);
				TboSRRResponse tboSRRResponse = TboServiceCall.callSSRService(ssrrequest,transaction_key,searchkey,tboconfig);
				SpecialServiceRequest SSR = null;
				if(tboSRRResponse.getResponse().getError().getErrorCode() == 0){
					SSR = new SpecialServiceRequest();
					SSR.setIsLCC(tboflightairpriceresponse.getResponse().getResults().getIsLCC());
					SSR.setBaggage(tboSRRResponse.getResponse().getBaggage());
					SSR.setMealDynamic(tboSRRResponse.getResponse().getMealDynamic());
					SSR.setSeatPreference(tboSRRResponse.getResponse().getSeatPreference());
					SSR.setMeal(tboSRRResponse.getResponse().getMeal());
				}
				flightPriceResponse.setSpecialServiceRequest(SSR);
			}
			catch (Exception e) {
				logger.error("Exception",e);
				throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
			}

		}		
		else
		{
			if(fligtindexlist.get(0).startsWith("TP")){
				pricekey = "PTP" + new UID().toString();
				TravelportConfig travelportConfig=TravelportConfig.GetTravelportConfig();				
				StringBuilder requestData= AirPriceRequestBuilder.createAirpriceRequest(travelportConfig, flightindex, UapiAirSegmentListMap,flightsearch );
				SOAPMessage soapMessageReq =SoapClient.buildSoapMsgFromStr(requestData);
				soapMessageReq = addSecureHeader(soapMessageReq, travelportConfig);
				SOAPMessage soapMessageRes=callService(soapMessageReq, travelportConfig);
				ByteArrayOutputStream in = new ByteArrayOutputStream();

				String trasKEy=transaction_key;
				FileUtil.writeSoap("flight", "travelport", "flightPriceResponse", false, requestData.toString(), trasKEy);
				try {
					soapMessageRes.writeTo(in);
					FileUtil.writeSoap("flight", "travelport", "flightPriceResponse", true, new String(in.toByteArray(),"UTF-8"), trasKEy);
					logger.info("Price Response :"+in);
				} catch (IOException e) {
					logger.error("IOException",e);
				}
				MarshalUnmarshal marshalUnmarshal=new MarshalUnmarshal();
				AirPriceRsp aiPriceRsp = null;
				aiPriceRsp = (AirPriceRsp)marshalUnmarshal.unMarshal(AirPriceRsp.class, soapMessageRes);
				flightPriceResponse= AirPriceResponseParser.parseAirPrice(aiPriceRsp, markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,UapiAirSegmentListMap.get(fligtindexlist.get(0)).get(0).getExchangeRate(),UapiAirSegmentListMap.get(fligtindexlist.get(0)).get(0).getApiToBaseExchangeRate(),markupCommissionDetails);

			}else if(fligtindexlist.get(0).startsWith("TAY")){
				pricekey = "PTAY" + new UID().toString();				
				ObjectMapper mapper = new ObjectMapper();
				FlightPriceResponse flightPriceResponseOLD = null;
				TayyarahConfig tayyarahConfig = TayyarahConfig.GetTayyarahConfig();
				try {					
					String flightindexNEW=getFlightIndex(fligtindexlist);
					String newURL=tayyarahConfig.getUrl()+"/airprice/response?app_key="+tayyarahConfig.getApp_key()+"&flightindex="+flightindexNEW+"&searchkey="+searchkey;
					flightPriceResponseOLD = mapper.readValue(new URL(newURL), FlightPriceResponse.class);
					//Pretty print
					String flightPriceResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(flightPriceResponseOLD);
					logger.info("flightPriceResponseInString  :"+flightPriceResponseInString);

				} catch (JsonGenerationException e) {
					logger.error("JsonGenerationException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				} catch (JsonMappingException e) {
					logger.error("JsonMappingException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				} catch (IOException e) {
					logger.error("IOException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				}
				catch (Exception e) {
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
				}
				try {
					flightPriceResponse = TayyarahAirPriceResponseParser.parseAirPrice(flightPriceResponseOLD, markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,FlightSegmentstMap.get(fligtindexlist.get(0)).getExchangeRate(),uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(0)).getApiToBaseExchangeRate(),markupCommissionDetails);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
				}

			}else if(fligtindexlist.get(0).startsWith("LIN")){

				pricekey = "PLIN" + new UID().toString();			
				ObjectMapper mapper = new ObjectMapper();
				FlightPriceResponse flightPriceResponseOLD = null;
				LintasConfig lintasConfig=LintasConfig.GetLintasConfig();
				try {				
					String flightindexNEW=getFlightIndex(fligtindexlist);
					String newURL=lintasConfig.getUrl()+"/airprice/response?app_key="+lintasConfig.getApp_key()+"&flightindex="+flightindexNEW+"&searchkey="+searchkey;
					flightPriceResponseOLD = mapper.readValue(new URL(newURL), FlightPriceResponse.class);
					//Pretty print
					String flightPriceResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(flightPriceResponseOLD);					

				} catch (JsonGenerationException e) {
					logger.error("JsonGenerationException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				} catch (JsonMappingException e) {
					logger.error("JsonMappingException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				} catch (IOException e) {
					logger.error("IOException",e);
					throw new FlightException(ErrorCodeCustomerEnum.RestClientException,FlightErrorMessages.NO_AIRPRICE);
				}
				catch (Exception e) {
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
				}
				try {
					flightPriceResponse = LintasAirPriceResponseParser.parseAirPrice(flightPriceResponseOLD, markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,
							FlightSegmentstMap.get(fligtindexlist.get(0)).getExchangeRate(),uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(0)).getApiToBaseExchangeRate(),markupCommissionDetails);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
				}

			}else if(fligtindexlist.get(0).startsWith("TB")){

				try {
					TboFlightConfig tboconfig = TboFlightConfig.GetTboConfig(appKeyVo);
					pricekey = "PTB" + new UID().toString();
					String tempflightIndexnew1 = fligtindexlist.get(0);
					Map<String, FareRules> faredetailMap = uapiSearchFlightKeyMap.getFareRulesMap();
					FareRules faredetail =  faredetailMap.get(tempflightIndexnew1);
					TboFlightAirpriceRequest requestData = TboRequestBuilder.getTboRequestBuilder().createAirpriceRequestbuilder(faredetail,tboconfig);
					tboflightairpriceresponse = TboServiceCall.callAirpriceService(requestData,transaction_key,searchkey,tboconfig);
					flightPriceResponse = TboResponseParser.parseAirPrice(tboflightairpriceresponse,markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,
							FlightSegmentstMap.get(fligtindexlist.get(0)).getExchangeRate(),uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(0)),markupCommissionDetails,companyConfigDAO,appKeyVo,companyDao);
					TboFlightSSRRequest ssrrequest = TboRequestBuilder.getTboRequestBuilder().createSSRRequestBuilder(faredetail,tboconfig);
					TboSRRResponse tboSRRResponse = TboServiceCall.callSSRService(ssrrequest,transaction_key,searchkey,tboconfig);
					SpecialServiceRequest SSR = null;
					if(tboSRRResponse.getResponse().getError().getErrorCode() == 0){
						SSR = new SpecialServiceRequest();
						SSR.setIsLCC(tboflightairpriceresponse.getResponse().getResults().getIsLCC());
						SSR.setBaggage(tboSRRResponse.getResponse().getBaggage());
						SSR.setMealDynamic(tboSRRResponse.getResponse().getMealDynamic());
						SSR.setSeatPreference(tboSRRResponse.getResponse().getSeatPreference());
						SSR.setMeal(tboSRRResponse.getResponse().getMeal());
					}
					flightPriceResponse.setSpecialServiceRequest(SSR);
				}
				catch (Exception e) {
					logger.error("Exception",e);
					throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_AIRPRICE);
				}
			}
			else{
				BluestarConfig bluestarConfig = BluestarConfig.GetBluestarConfig(appKeyVo);
				pricekey = "PBS" + new UID().toString();
				Map<String, FareFlightSegment> FareFlightSegmentMap =uapiSearchFlightKeyMap.getFareFlightSegmentMap();
				StringBuilder requestData = null;
				String tempflightIndex=fligtindexlist.get(0);
				requestData = BluestarGetFlightAvailibilityRequestBuilder.createVerifyFlightDetailRequestOneway(FlightSegmentstMap.get(tempflightIndex).getSegments().get(0).getTrackno(),bluestarConfig);
				SOAPMessage soapMessageReq = SoapClient.buildSoapMsgFromStr(requestData);
				SOAPMessage soapMessageRes = BluestarServiceCall.callService(soapMessageReq,bluestarConfig);
				ByteArrayOutputStream flightPriceByteStream = new ByteArrayOutputStream();
				try {
					soapMessageRes.writeTo(flightPriceByteStream);
				} catch (IOException e) {
					logger.error("IOException",e);
				}
				String[] search_key=null ;
				try{
					search_key= flightsearch.getTransactionKey().split(":");
				}catch(Exception e){
					search_key=new UID().toString().split(":");
				}
				try{
					FileUtil.writeSoap("flight", "bluestar", "pricing", false, requestData.toString(), String.valueOf(search_key[1]+search_key[2]));
					FileUtil.writeSoap("flight", "bluestar", "pricing", true, soapMessageRes,  String.valueOf(search_key[1]+search_key[2]));
				} catch (Exception e) {
					logger.error(" The filename, directory name ", e);
				}

				String flightPriceStringData=flightPriceByteStream.toString();				
				flightPriceStringData=flightPriceStringData.replaceAll("&lt;", "<");flightPriceStringData=flightPriceStringData.replaceAll("&gt;", ">");
				if(flightPriceStringData.indexOf("<FlightDetails>")==-1)
				{
					throw new FlightException(ErrorCodeCustomerEnum.JAXBException,FlightErrorMessages.NO_AIRPRICE);
				}
				else
				{
					BluestarPriceData bluestarPriceData = BluestarGetFlightPriceXMLObjectCoversion.createFlightPriceResponseFromXML(FlightSegmentstMap, flightPriceByteStream, flightsearch, uapiSearchFlightKeyMap,tempflightIndex);
					Map<String, FareRules> FareRulesMap = uapiSearchFlightKeyMap.getFareRulesMap();
					flightPriceResponse = BluestarAirPriceResponseParser.parseAirPrice(FareFlightSegmentMap,FlightSegmentstMap,FareRulesMap, markupMap,AirlineNameMap,MapList,flightsearch, transaction_key,tempDAO,FlightSegmentstMap.get(fligtindexlist.get(0)).getExchangeRate(),fligtindexlist,uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(0)).getApiToBaseExchangeRate(),markupCommissionDetails,bluestarPriceData);
				}
			}
		}

		flightPriceResponse.setMarkupCommissionDetails(markupCommissionDetails);	
		if(fligtindexlist.get(0).startsWith("TB")||fligtindexlist.size()>1 && (fligtindexlist.get(1).startsWith("TB"))){	

			BigDecimal newTotalPrice = new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice());
			BigDecimal oldTotalPrice = new BigDecimal(uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(0)).getTotalPrice());

			if(flightsearch.isSpecialSearch() && flightsearch.getTripType().equalsIgnoreCase("R")){
				BigDecimal newTotalPriceSpecial = new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice());
				BigDecimal oldTotalPriceSpecial = new BigDecimal(uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(1)).getTotalPrice());
				newTotalPrice = newTotalPrice.add(newTotalPriceSpecial);
				oldTotalPrice = oldTotalPrice.add(oldTotalPriceSpecial);
			}
			if(flightsearch.isSpecialSearch() && flightsearch.getTripType().equalsIgnoreCase("SR")){				
				BigDecimal oldTotalPriceSpecial = new BigDecimal(uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(1)).getTotalPrice());
				oldTotalPrice = oldTotalPrice.add(oldTotalPriceSpecial);
			}
			if(flightPriceResponse.getSpecialFareFlightSegment()!=null)
				flightPriceResponse.setTotalPayableAmount(AmountRoundingModeUtil.roundingMode(flightPriceResponse.getFareFlightSegment().getPayableAmount().add(flightPriceResponse.getSpecialFareFlightSegment().getPayableAmount())));
			else
				flightPriceResponse.setTotalPayableAmount(AmountRoundingModeUtil.roundingMode(flightPriceResponse.getFareFlightSegment().getPayableAmount()));

			flightPriceResponse.setNewPrice(AmountRoundingModeUtil.roundingMode(newTotalPrice));
			flightPriceResponse.setOldPrice(AmountRoundingModeUtil.roundingMode(oldTotalPrice));
			flightPriceResponse.setPriceChange(true);
			if(newTotalPrice.compareTo(oldTotalPrice)==0){
				flightPriceResponse.setPriceChange(false);
			}
			flightPriceResponse.setPriceKey(pricekey);
			String lowFareIndex1 = "";
			String lowFareIndex2 = "";
			String reason = "";
			String lowFareIndexReturn1 = "";
			String lowFareIndexReturn2 = "";
			String reasonReturn = "";
			if(!isLowFare){
				lowFareIndex1 = lowFareFlightIndex1;
				lowFareIndex2 = lowFareFlightIndex2;
				reason = reasonToSelect;
			}
			if(!isLowFareReturn){
				lowFareIndexReturn1 = lowFareFlightIndexReturn1;
				lowFareIndexReturn2 = lowFareFlightIndexReturn2;
				reasonReturn = reasonToSelectReturn;
			}
			DBS.storeTBOAirPriceDetails(flightPriceResponse, pricekey, flightsearch,
					tempDAO,tboflightairpriceresponse,tboflightairpriceresponsespecial,lowFareIndex1,lowFareIndex2,reason,lowFareIndexReturn1,lowFareIndexReturn2,reasonReturn);
			DBS.insertPriceKey(transaction_key, pricekey, tempDAO);			
		}
		if(fligtindexlist.get(0).startsWith("BS")||fligtindexlist.size()>1 && (fligtindexlist.get(1).startsWith("BS"))){

			BigDecimal newTotalPrice = new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice());
			BigDecimal oldTotalPrice = new BigDecimal(uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(0)).getTotalPrice());

			if(flightsearch.isSpecialSearch()&&flightsearch.getTripType().equalsIgnoreCase("R")){
				BigDecimal newTotalPriceSpecial=new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice());
				BigDecimal oldTotalPriceSpecial=new BigDecimal(uapiSearchFlightKeyMap.getFareFlightSegmentMap().get(fligtindexlist.get(1)).getTotalPrice());
				newTotalPrice=newTotalPrice.add(newTotalPriceSpecial);
				oldTotalPrice=oldTotalPrice.add(oldTotalPriceSpecial);
			}			
			flightPriceResponse.setNewPrice(newTotalPrice.setScale(2, BigDecimal.ROUND_FLOOR));
			flightPriceResponse.setOldPrice(oldTotalPrice);
			
			flightPriceResponse.setPriceChange(true);
			if(newTotalPrice.compareTo(oldTotalPrice)==0){
				flightPriceResponse.setPriceChange(false);
			}		
			flightPriceResponse.setPriceKey(pricekey);			
			DBS.storeAirPriceDetails(flightPriceResponse, pricekey, flightsearch,
					tempDAO);
			DBS.insertPriceKey(transaction_key, pricekey, tempDAO);
		}	
		return flightPriceResponse;
	}
	public static FlightBookingResponse callBookingService(FlightBookingResponse flightBookingResponse,TravelportConfig travelportConfig,OrderCustomer orderCustomer,FlightPriceResponse flightPriceResponse,List<FlightOrderCustomer> flightOrderCustomers,String orderId,String CountryCode,FlightBookingDao FBDAO,EmailDao emaildao,String transactionkey, String paymode,WalletAmountTranferHistory walletAmountTranferHistory,int count)
			throws Exception
	{
		if(travelportConfig.isTest()){
			flightBookingResponse= FlightBookingResponseParser.parseFlightBookingResponseTesting(flightBookingResponse,orderId,FBDAO,emaildao,transactionkey,paymode,walletAmountTranferHistory,count);

		}else{
			StringBuilder requestData= TicketBookingRequestBuilder.createAirpriceRequest(TravelportConfig.GetTravelportConfig(), orderCustomer,  flightPriceResponse,flightOrderCustomers,CountryCode,count);
			SOAPMessage soapMessageReq =SoapClient.buildSoapMsgFromStr(requestData);
			soapMessageReq = addSecureHeader(soapMessageReq, travelportConfig);
			SOAPMessage soapMessageRes=callService(soapMessageReq, travelportConfig);
			ByteArrayOutputStream in = new ByteArrayOutputStream();
			String trasKEy=transactionkey;
			FileUtil.writeSoap("flight", "travelport", "flightBookingResponse", false, requestData.toString(), trasKEy);
			try {
				soapMessageRes.writeTo(in);
				FileUtil.writeSoap("flight", "travelport", "flightBookingResponse", true, new String(in.toByteArray(),"UTF-8"), trasKEy);
				logger.info("Price Response :"+in);
			} catch (IOException e) {
				logger.error("IOException",e);
			}
			MarshalUnmarshal marshalUnmarshal=new MarshalUnmarshal();
			AirCreateReservationRsp airCreateReservationRsp = null;
			airCreateReservationRsp=(AirCreateReservationRsp)marshalUnmarshal.unMarshal(AirCreateReservationRsp.class, soapMessageRes);
			// create model for fare rule to be sent to API
			flightBookingResponse= FlightBookingResponseParser.parseFlightBookingResponse(flightBookingResponse,airCreateReservationRsp,orderId,FBDAO,emaildao,transactionkey,paymode,walletAmountTranferHistory,count);
		}
		return flightBookingResponse;
	}
	public static FlightAirPriceDetailsTemp getFlightAirPriceDetailsTemp(String pricekey, FlightTempAirSegmentDAO tempDAO)			
	{
		FlightAirPriceDetailsTemp airPriceDetails = null;
		try{
			FlightDataBaseServices DBS = new FlightDataBaseServices();
			airPriceDetails =  DBS.getAirPriceDetails(pricekey,tempDAO);		
		}catch(HibernateException e){
			logger.info("HibernateException getFlightAirPriceDetailsTemp :"+e);
		}catch (Exception e) {
			logger.info("Exception getFlightAirPriceDetailsTemp :"+e);
		}
		return airPriceDetails;
	}
	public static FlightPriceResponse getFlightPriceResponse(String pricekey, FlightTempAirSegmentDAO tempDAO)			
	{
		FlightPriceResponse flightPriceResponse = null;
		try{
			FlightDataBaseServices DBS = new FlightDataBaseServices();
			FlightAirPriceDetailsTemp  airPriceDetails =  DBS.getAirPriceDetails(pricekey,tempDAO);
			byte[] fpr = airPriceDetails.getFlightPriceResponse();
			flightPriceResponse = (FlightPriceResponse)FlightDataBaseServices.convertByteArrayToObject(fpr);
		}catch(HibernateException e){
			logger.info("HibernateException getFlightPriceResponse :"+e);
		}catch (Exception e) {
			logger.info("Exception getFlightPriceResponse :"+e);
		}
		return flightPriceResponse;
	}

	public static TboFlightAirpriceResponse getTboFlightPriceResponse(String pricekey, FlightTempAirSegmentDAO tempDAO,boolean Isspecial)
			throws Exception
	{
		FlightDataBaseServices DBS = new FlightDataBaseServices();
		FlightAirPriceDetailsTemp airPriceDetails =  DBS.getAirPriceDetails(pricekey,tempDAO);
		byte[] fpr = null;
		if(Isspecial){
			fpr = airPriceDetails.getTBOFlightPriceResponseSpecial();
		}
		if(!Isspecial){
			fpr = airPriceDetails.getTBOFlightPriceResponse();
		}
		TboFlightAirpriceResponse tboflightPriceResponse = (TboFlightAirpriceResponse)FlightDataBaseServices.convertByteArrayToObject(fpr);
		return tboflightPriceResponse;
	}
	public static BookingDetails getBookingDetailsToDb(String orderId, FlightBookingDao FBDAO)
			throws Exception
	{
		FlightDataBaseServices DBS = new FlightDataBaseServices();
		FlightBookingDetailsTemp bookingDetailsToDb =  DBS.getBookingDetailsToDb(orderId,FBDAO);
		byte[] BD = bookingDetailsToDb.getBooking_details();
		BookingDetails bookingDetails = (BookingDetails)FlightDataBaseServices.convertByteArrayToObject(BD);
		return bookingDetails;
	}

	public static String getOrderId(String pgID, FlightBookingDao FBDAO)
			throws Exception
	{
		String result =  FBDAO.getorderID(pgID);
		return result;
	}

	public static SOAPMessage callService(SOAPMessage reqSoapMessage,TravelportConfig travelportConfig) throws UnsupportedOperationException, SOAPException {
		String serviceURL= travelportConfig.getUrl();
		SoapClient client = new SoapClient();
		return client.sendSoapMessage(reqSoapMessage, serviceURL);
	}

	public static SOAPMessage addSecureHeader(SOAPMessage reqsoapMessage,TravelportConfig travelportConfig)
	{
		String userName = travelportConfig.getUsername();
		String password = travelportConfig.getPassword();
		String authorization = printBase64Binary(new String(userName + ":" + password).toString().getBytes());
		String contentType = printBase64Binary(new String("text/xml;charset=ISO-8859-1").toString().getBytes());
		reqsoapMessage.getMimeHeaders().addHeader("Authorization", "Basic " + authorization);
		reqsoapMessage.getMimeHeaders().addHeader("Content-Type", contentType);
		ByteArrayOutputStream in = new ByteArrayOutputStream();
		try {
			reqsoapMessage.writeTo(in);
		} catch (IOException e) {
			logger.error("IOException",e);
		} catch (SOAPException e) {
			logger.error("SOAPException",e);
		}
		return	reqsoapMessage ;
	}

	public static void createFlightCustomerDetail(FlightCustomerDetails FCD,Flightsearch flightsearch) {
		List<PassengerDetails> passengerdetailsList=new ArrayList<PassengerDetails>();
		addPassengers(passengerdetailsList,flightsearch);
		FCD.setPassengerdetailsList(passengerdetailsList);
	}

	private static void addPassengers( List<PassengerDetails> passengerdetailsList,Flightsearch flightsearch){
		for(int i=0;i<flightsearch.getAdult();i++)
		{
			PassengerDetails passengerDetails = new PassengerDetails();
			passengerDetails.setPassengerId((new UID()).toString());
			passengerDetails.setPassengerTypeCode("ADT");
			passengerDetails.setBirthday("yyyy-mm-dd");
			passengerDetails.setNationalityCountry("IND");
			passengerDetails.setPassportIssuingCountry("IND");
			passengerdetailsList.add(passengerDetails);
		}
		for(int i=0;i<flightsearch.getKid();i++)
		{
			PassengerDetails passengerDetails = new PassengerDetails();
			passengerDetails.setPassengerId((new UID()).toString());
			passengerDetails.setPassengerTypeCode("CHD");
			passengerdetailsList.add(passengerDetails);
		}
		for(int i=0;i<flightsearch.getInfant();i++)
		{
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

	public static String getFlightIndex(List<String> fligtindexlist){
		StringBuffer newFlightIndex=new StringBuffer();
		for(int i=0;i<fligtindexlist.size();i++){
			String FI = fligtindexlist.get(i);
			newFlightIndex.append(FI.substring(3));
			if(i != fligtindexlist.size()-1){
				newFlightIndex.append("_");
			}
		}
		return newFlightIndex.toString();
	}
}