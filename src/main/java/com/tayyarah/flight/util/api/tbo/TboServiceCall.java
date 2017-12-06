
package com.tayyarah.flight.util.api.tbo;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tayyarah.api.flight.tbo.model.GetBookingDetailsRequest;
import com.tayyarah.api.flight.tbo.model.NonLCCTicketRequest;
import com.tayyarah.api.flight.tbo.model.TboAgencyBalanceRequest;
import com.tayyarah.api.flight.tbo.model.TboAgencyBalanceResponse;
import com.tayyarah.api.flight.tbo.model.TboBookResponse;
import com.tayyarah.api.flight.tbo.model.TboBookingDetailsResponse;
import com.tayyarah.api.flight.tbo.model.TboCalendarFareResponse;
import com.tayyarah.api.flight.tbo.model.TboCancelTicketRequest;
import com.tayyarah.api.flight.tbo.model.TboCancelTicketResponse;
import com.tayyarah.api.flight.tbo.model.TboCancellationStatusRequest;
import com.tayyarah.api.flight.tbo.model.TboCancellationStatusResponse;
import com.tayyarah.api.flight.tbo.model.TboFareRuleResponse;
import com.tayyarah.api.flight.tbo.model.TboFlightAirpriceRequest;
import com.tayyarah.api.flight.tbo.model.TboFlightAirpriceResponse;
import com.tayyarah.api.flight.tbo.model.TboFlightBookingRequest;
import com.tayyarah.api.flight.tbo.model.TboFlightCalendarFareRequest;
import com.tayyarah.api.flight.tbo.model.TboFlightFareRuleRequest;
import com.tayyarah.api.flight.tbo.model.TboFlightSSRRequest;
import com.tayyarah.api.flight.tbo.model.TboFlightSearchRequest;
import com.tayyarah.api.flight.tbo.model.TboFlightSearchResponse;
import com.tayyarah.api.flight.tbo.model.TboPriceBDRequest;
import com.tayyarah.api.flight.tbo.model.TboPriceBDResponse;
import com.tayyarah.api.flight.tbo.model.TboReleasePNRRequest;
import com.tayyarah.api.flight.tbo.model.TboReleasePNRResponse;
import com.tayyarah.api.flight.tbo.model.TboSRRResponse;
import com.tayyarah.api.flight.tbo.model.TboTicketRequestForLCC;
import com.tayyarah.api.flight.tbo.model.TboTicketResponse;
import com.tayyarah.apiconfig.model.TboFlightConfig;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.common.util.FileUtil;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.flight.cache.entity.FlightSearchCache;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.FareRuleResponse;
import com.tayyarah.flight.model.FlightBookingResponse;
import com.tayyarah.flight.model.FlightCalendarSearch;
import com.tayyarah.flight.model.FlightCalendarSearchResponse;
import com.tayyarah.flight.model.FlightCancelRequest;
import com.tayyarah.flight.model.FlightCustomerDetails;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.SearchFlightResponse;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.user.entity.WalletAmountTranferHistory;
import com.travelport.api_v33.AirResponse.FareInfo;


public class TboServiceCall {
	static final Logger logger = Logger.getLogger(TboServiceCall.class);

	/*Search Call*/
	public static SearchFlightResponse callSearchService(
			Flightsearch flightsearch,
			Map<String,List<FlightMarkUpConfig>> markupMap,
			Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> airportMapList, CurrencyConversionMap currencyConversionMap,FlightTempAirSegmentDAO flightTempAirSegmentDAO,TboFlightConfig tboFlightConfig,AppKeyVo appKeyVo,CompanyConfigDAO companyConfigDAO,CompanyDao companyDao)
					throws Exception {

		SearchFlightResponse searchFlightResponse = null;
		TboFlightSearchRequest searchRequest = TboRequestBuilder.getTboRequestBuilder().createSearchBuilder(flightsearch,tboFlightConfig);
		String[] search_key = flightsearch.getTransactionKey().split(":");
		// Save Flight Search Request
		try{
			FileUtil.writeJson("flight", "TBO", "search", false, searchRequest,  String.valueOf(search_key[1]+search_key[2]));
		} catch (Exception e) {
			logger.error(" The filename, directory name ", e);
		}
		try {
			ObjectMapper mapper=new ObjectMapper();
			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(searchRequest);
			logger.info("---------------searchRequest:"+searchRequestInString);

			//Rest template
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);
			String searchURL = tboFlightConfig.getFlightUrlSearch();
			logger.info("---------------searchURL:"+searchURL);		
			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------response:"+response);

			TboFlightSearchResponse tboFlightSearchResponse = mapper.readValue(response, TboFlightSearchResponse.class);		
			if(tboFlightSearchResponse.getResponse()!= null && tboFlightSearchResponse.getResponse().getError()!= null && tboFlightSearchResponse.getResponse().getError().getErrorCode() == 0){	
				
				FlightSearchCache flightSearchCacheExists = flightTempAirSegmentDAO.getApiSearchCacheResponse(flightsearch);
				if(flightsearch.isCacheDataInDb())
				{
					if(flightSearchCacheExists==null)
					{
						flightSearchCacheExists = new FlightSearchCache();
					}
					flightSearchCacheExists.setAdult(flightsearch.getAdult());
					flightSearchCacheExists.setCreatedAt(new Timestamp(new Date().getTime()));
					flightSearchCacheExists.setCurrency(flightsearch.getCurrency());
					flightSearchCacheExists.setDepDate(flightsearch.getDepDate());
					flightSearchCacheExists.setDestination(flightsearch.getDestination());
					flightSearchCacheExists.setDomestic(flightsearch.isDomestic());
					flightSearchCacheExists.setInfant(flightsearch.getInfant());
					flightSearchCacheExists.setOrigin(flightsearch.getOrigin());
					flightSearchCacheExists.setKid(flightsearch.getKid());
					flightSearchCacheExists.setTripType(flightsearch.getTripType());
					flightSearchCacheExists.setSupplier("TBO");
					FlightDataBaseServices dbService = new FlightDataBaseServices();
					byte[] flightSearchReponseCache = null;
					try {
						flightSearchReponseCache = FlightDataBaseServices.convertObjectToByteArray(tboFlightSearchResponse);
					} catch (IOException e1) {						
						logger.error("IOException ",e1);
					}
					flightSearchCacheExists.setSearchData(flightSearchReponseCache);
					dbService.insertFlightSearchCacheApiResponses(flightSearchCacheExists, flightTempAirSegmentDAO);			
					return searchFlightResponse;
				}

				// this is to update cache when we call normal api search
				if(flightSearchCacheExists!=null && !flightsearch.isCacheDataInDb())
				{
					flightSearchCacheExists.setAdult(flightsearch.getAdult());
					flightSearchCacheExists.setArvlDate(flightsearch.getArvlDate());
					flightSearchCacheExists.setUpdatedAt(new Timestamp(new Date().getTime()));
					flightSearchCacheExists.setCurrency(flightsearch.getCurrency());
					flightSearchCacheExists.setDepDate(flightsearch.getDepDate());
					flightSearchCacheExists.setDestination(flightsearch.getDestination());
					flightSearchCacheExists.setDomestic(flightsearch.isDomestic());
					flightSearchCacheExists.setInfant(flightsearch.getInfant());
					flightSearchCacheExists.setOrigin(flightsearch.getOrigin());
					flightSearchCacheExists.setKid(flightsearch.getKid());
					flightSearchCacheExists.setTripType(flightsearch.getTripType());
					flightSearchCacheExists.setSupplier("TBO");
					FlightDataBaseServices dbService = new FlightDataBaseServices();
					byte[] flightSearchReponseCache = null;
					try {
						flightSearchReponseCache = FlightDataBaseServices.convertObjectToByteArray(tboFlightSearchResponse);
					} catch (IOException e1) {						
						logger.error("IOException ",e1);
					}
					flightSearchCacheExists.setSearchData(flightSearchReponseCache);
					dbService.insertFlightSearchCacheApiResponses(flightSearchCacheExists, flightTempAirSegmentDAO);			
				}
				// Save Flight Search Response
				try{
					FileUtil.writeJson("flight", "TBO", "search", true, tboFlightSearchResponse,  String.valueOf(search_key[1]+search_key[2]));
				} catch (Exception e) {
					logger.error(" The filename, directory name ", e);
				}		

				// TBO Oneway Parser
				if(flightsearch.getTripType().equalsIgnoreCase("O") && !flightsearch.isDomestic())
					searchFlightResponse= TboResponseParser.parseResponseOneway(tboFlightSearchResponse,markupMap,flightsearch,AirlineNameMap,currencyConversionMap,appKeyVo,companyConfigDAO,companyDao);

				// TBO Oneway Parser for Domestic
				if((flightsearch.getTripType().equalsIgnoreCase("O") && flightsearch.isDomestic()) || flightsearch.isSpecialSearch())
					searchFlightResponse= TboResponseParser.parseResponseOnewayDomestic(tboFlightSearchResponse,markupMap,flightsearch,AirlineNameMap,currencyConversionMap,appKeyVo,companyConfigDAO,companyDao);

				// TBO Round Trip Parser
				if(flightsearch.getTripType().equalsIgnoreCase("R"))
					searchFlightResponse= TboResponseParser.parseResponseRoundTrip(tboFlightSearchResponse,markupMap,flightsearch,AirlineNameMap,currencyConversionMap,appKeyVo,companyConfigDAO,companyDao);

				// TBO Round Trip Parser
				if(flightsearch.getTripType().equalsIgnoreCase("SR"))
					searchFlightResponse= TboResponseParser.parseResponseRoundSpecialDomestic(tboFlightSearchResponse,markupMap,flightsearch,AirlineNameMap,currencyConversionMap,appKeyVo,companyConfigDAO,companyDao);

				// Save TBO Search Response in DB
				if(!flightsearch.isDynamicMarkup()){
					if(flightsearch.isSpecialSearch()){
						FlightDataBaseServices dbService = new FlightDataBaseServices();
						byte[] FSR = null;
						try {
							FSR = FlightDataBaseServices.convertObjectToByteArray(tboFlightSearchResponse);
						} catch (IOException e1) {						
							logger.error("IOException ",e1);
						}
						dbService.storeFlightSearchApiResponses(flightsearch.getSearchKey(), FSR, flightTempAirSegmentDAO,flightsearch, "TBO");
					}else if(!flightsearch.isSpecialSearch()){

						if(!(flightTempAirSegmentDAO.CheckSearchKeyExists(flightsearch.getSearchKey(),"TBO"))){
							FlightDataBaseServices dbService = new FlightDataBaseServices();
							byte[] FSR = null;
							try {
								FSR = FlightDataBaseServices.convertObjectToByteArray(tboFlightSearchResponse);
							} catch (IOException e1) {						
								logger.error("IOException ",e1);
							}
							dbService.storeFlightSearchApiResponses(flightsearch.getSearchKey(), FSR, flightTempAirSegmentDAO,flightsearch,"TBO");
						}
					}
					else{
						logger.info("Search Response not inserted");
					}
				}
			}else{
				throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
			}


		} catch (JsonGenerationException e) {
			logger.info("-------------search flights : JsonGenerationException--"+e.getMessage());
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
		} catch (JsonMappingException e) {
			logger.info("------------- search flights  : JsonMappingException--"+e.getMessage());
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
		} catch (IOException e) {
			logger.info("------------- search flights  : IOException--"+e.getMessage());
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
		}
		catch (Exception e) {
			logger.info("------------- search flights  : Exception--"+e.getMessage());
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
		}
		return searchFlightResponse;
	}
	public static FareRuleResponse callFareRuleService(FareInfo fareInfo,TboFlightConfig tboFlightConfig) throws SOAPException, ClassNotFoundException, JAXBException
	{
		FareRuleResponse fareRuleResponse = null;
		TboFlightFareRuleRequest farerulerequest = TboRequestBuilder.getTboRequestBuilder().createFareruleBuilder(fareInfo);
		try {
			ObjectMapper mapper=new ObjectMapper();
			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(farerulerequest);
			logger.info("---------------farerulerequest :" +searchRequestInString);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);
			String searchURL = tboFlightConfig.getFlightUrlFarerule();
			logger.info("---------------searchURL:"+searchURL);

			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------response:"+response);
			
			TboFareRuleResponse tboFlightfareruleresponse =  mapper.readValue(response, TboFareRuleResponse.class);
			fareRuleResponse = TboResponseParser.parseFareRules(tboFlightfareruleresponse);

		}catch(Exception e){
			logger.info("-------------FareRuleService  : Exception--"+e.getMessage());
		}
		return fareRuleResponse;
	}

	public static TboFlightAirpriceResponse callAirpriceService(TboFlightAirpriceRequest airpricerequest,String transactionkey,String searchkey,TboFlightConfig tboFlightConfig) throws SOAPException, ClassNotFoundException, JAXBException
	{

		String[] search_key = transactionkey.split(":");

		// Save Airprice  Request
		try{
			FileUtil.writeJson("flight", "TBO", "Airprice", false, airpricerequest,  String.valueOf(search_key[1]+search_key[2]));
		} catch (Exception e) {
			logger.error(" The filename, directory name ", e);
		}
		TboFlightAirpriceResponse tboairpriceresponse = new TboFlightAirpriceResponse();
		try {
			ObjectMapper mapper=new ObjectMapper();
			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(airpricerequest);
			logger.info("---------------airpricerequest :" +searchRequestInString);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);			
			String searchURL = tboFlightConfig.getFlightUrlAirprice();
			logger.info("---------------searchURL:"+searchURL);

			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------airpriceresponse:"+response);
			tboairpriceresponse =  mapper.readValue(response, TboFlightAirpriceResponse.class);
			logger.info("---------------tboFlightairpriceresponse :"+tboairpriceresponse);

			// Save Airprice  Response
			try{
				FileUtil.writeJson("flight", "TBO", "Airprice", true, tboairpriceresponse,  String.valueOf(search_key[1]+search_key[2]));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}

		}catch(Exception e){
			logger.info("tboairpriceresponse ---------------Exception:"+e);
			tboairpriceresponse = null;
		}

		return tboairpriceresponse;
	}

	// Method for Get SSR Response
	public static TboSRRResponse callSSRService(TboFlightSSRRequest ssrrequest,String transactionkey,String searchkey,TboFlightConfig tboFlightConfig) throws SOAPException, ClassNotFoundException, JAXBException
	{
		TboSRRResponse tboSRRResponse = new TboSRRResponse();
		String[] search_key = transactionkey.split(":");

		// Save SSR  Request
		try{
			FileUtil.writeJson("flight", "TBO", "SSR", false, ssrrequest,  String.valueOf(search_key[1]+search_key[2]));
		} catch (Exception e) {
			logger.error(" The filename, directory name ", e);
		}

		try {
			ObjectMapper mapper=new ObjectMapper();

			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ssrrequest);
			logger.info("---------------ssrrequest :" +searchRequestInString);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);

			String searchURL = tboFlightConfig.getFlightUrlSsr();
			logger.info("---------------ssrrequest URL :"+searchURL);

			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------response:"+response);

			tboSRRResponse =  mapper.readValue(response, TboSRRResponse.class);
			logger.info("---------------tboSRRResponse :"+tboSRRResponse);

			// Save SSR  Response
			try{
				FileUtil.writeJson("flight", "TBO", "SSR", true, tboSRRResponse,  String.valueOf(search_key[1]+search_key[2]));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}

		}catch(Exception e){
			logger.info("---------------ssrrequest Exception :"+e);
		}
		return tboSRRResponse;
	}


	public static FlightBookingResponse callLCCBookingService(FlightBookingResponse flightBookingResponse,TboFlightAirpriceResponse  tboFlightAirpriceResponse, OrderCustomer orderCustomer,FlightPriceResponse flightPriceResponse,List<FlightOrderCustomer> flightOrderCustomers,String orderId, FlightCustomerDetails flightCustomerDetails,FlightBookingDao FBDAO,EmailDao emaildao,String transactionkey, String paymode,WalletAmountTranferHistory walletAmountTranferHistory,int count,boolean isSpecial,FlightOrderRow flightOrderRow,TboFlightConfig tboFlightConfig)
			throws Exception
	{

		TboTicketRequestForLCC request = TboRequestBuilder.getTboRequestBuilder().createFlightTicketRequestbuilder(flightBookingResponse,tboFlightAirpriceResponse,orderCustomer,flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails,isSpecial,tboFlightConfig);

		String[] search_key = transactionkey.split(":");
		// Save LCC Booking  Request
		try{
			FileUtil.writeJson("flight", "TBO", "LCCBook", false, request,  String.valueOf(search_key[1]+search_key[2]));
		} catch (Exception e) {
			logger.error(" The filename, directory name ", e);
		}

		try {
			ObjectMapper mapper=new ObjectMapper();
			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
			logger.info("---------------TBOBookrequest :" +searchRequestInString);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);
			String searchURL = tboFlightConfig.getFlightUrlTicket();
			logger.info("---------------TICKET_URL:"+searchURL);

			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------response:"+response);

			TboTicketResponse tboTicketResponse = mapper.readValue(response, TboTicketResponse.class);
			logger.info("---------------tboTicketResponse:"+tboTicketResponse);

			// Save LCC Booking  Request
			try{
				FileUtil.writeJson("flight", "TBO", "LCCBook", true, tboTicketResponse,  String.valueOf(search_key[1]+search_key[2]));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}

			flightBookingResponse = TboResponseParser.parseTicketResponse(flightBookingResponse,tboTicketResponse,orderId,FBDAO,emaildao,transactionkey,paymode,walletAmountTranferHistory,count,flightOrderCustomers,flightCustomerDetails,flightOrderRow) ;

		}catch(Exception e){
			System.out.println("Exception" + e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
		}
		return flightBookingResponse;
	}

	public static FlightBookingResponse callHoldBookingService(FlightBookingResponse flightBookingResponse,TboFlightAirpriceResponse  tboFlightAirpriceResponse, OrderCustomer orderCustomer,FlightPriceResponse flightPriceResponse,List<FlightOrderCustomer> flightOrderCustomers,String orderId, FlightCustomerDetails flightCustomerDetails,FlightBookingDao FBDAO,EmailDao emaildao,String transactionkey, String paymode,WalletAmountTranferHistory walletAmountTranferHistory,int count,boolean isSpecial,FlightOrderRow flightOrderRow,TboFlightConfig tboFlightConfig)
	{
		String[] search_key = transactionkey.split(":");
		try {
			TboFlightBookingRequest request = TboRequestBuilder.getTboRequestBuilder().createFlightBookRequestbuilder(flightBookingResponse,tboFlightAirpriceResponse,orderCustomer,flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails,isSpecial,tboFlightConfig);

			// Save NONLCC Booking  Request
			try{
				FileUtil.writeJson("flight", "TBO", "NONLCCBook", false, request,  String.valueOf(search_key[1]+search_key[2]));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}

			ObjectMapper mapper=new ObjectMapper();
			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
			logger.info("---------------TBOBookrequest :" +searchRequestInString);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);
			String searchURL=tboFlightConfig.getFlightUrlBook();
			logger.info("---------------BOOK_URL:"+searchURL);

			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------response:"+response);

			TboBookResponse nonLCCBookResponse =  mapper.readValue(response, TboBookResponse.class);

			// Save Hold Booking  Request
			try{
				FileUtil.writeJson("flight", "TBO", "NONLCCBook", true, nonLCCBookResponse,  String.valueOf(search_key[1]+search_key[2]));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}

			// Parse Hold Book Response
			flightBookingResponse = TboResponseParser.parseHoldBookResponse(flightBookingResponse,nonLCCBookResponse,orderId,FBDAO,emaildao,transactionkey,paymode,walletAmountTranferHistory,count,flightOrderCustomers,flightCustomerDetails,flightOrderRow);

		}catch(Exception e){
			System.out.println("Exception" + e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
		}
		return flightBookingResponse;
	}

	public static FlightBookingResponse callHoldTicketingService(FlightBookingResponse flightBookingResponse,String orderId,FlightBookingDao FBDAO,EmailDao emaildao,String transactionkey, String paymode,WalletAmountTranferHistory walletAmountTranferHistory,int count,List<FlightOrderCustomer> flightOrderCustomers,FlightCustomerDetails flightCustomerDetails,FlightOrderRow flightOrderRow,TboFlightConfig tboFlightConfig)
			throws Exception
	{
		NonLCCTicketRequest ticketrequest = TboRequestBuilder.getTboRequestBuilder().createFlightNonLccTicketAfterHoldRequestbuilder(flightOrderRow,tboFlightConfig);
		String[] search_key = transactionkey.split(":");
		// Save Ticket Request
		try{
			FileUtil.writeJson("flight", "TBO", "AfterHoldTicket", false, ticketrequest,  String.valueOf(search_key[1]+search_key[2]));
		} catch (Exception e) {
			logger.error(" The filename, directory name ", e);
		}
		try {
			ObjectMapper mapper=new ObjectMapper();
			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ticketrequest);
			logger.info("---------------TBOTicketrequest for NonLCC:" + searchRequestInString);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);
			String searchURL = tboFlightConfig.getFlightUrlTicket();
			logger.info("---------------Ticket_URL:"+searchURL);

			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------response:"+response);

			TboTicketResponse tboTicketResponse = mapper.readValue(response, TboTicketResponse.class);
			logger.info("---------------tboTicketResponse:"+tboTicketResponse);

			// Save Ticket  Response
			try{
				FileUtil.writeJson("flight", "TBO", "AfterHoldTicket", true, tboTicketResponse,  String.valueOf(search_key[1]+search_key[2]));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}

			if(tboTicketResponse.getResponse().getError().getErrorCode() == 0)
				flightBookingResponse = TboResponseParser.parseTicketResponse(flightBookingResponse,tboTicketResponse,orderId,FBDAO,emaildao,transactionkey,paymode,walletAmountTranferHistory,count,flightOrderCustomers,flightCustomerDetails,flightOrderRow) ;
			else
				throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);

		}catch(Exception e){
			//System.out.println("Exception" + e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);

		}
		return flightBookingResponse;
	}

	public static FlightBookingResponse callBookingService(FlightBookingResponse flightBookingResponse,TboFlightAirpriceResponse  tboFlightAirpriceResponse, OrderCustomer orderCustomer,FlightPriceResponse flightPriceResponse,List<FlightOrderCustomer> flightOrderCustomers,String orderId, FlightCustomerDetails flightCustomerDetails,FlightBookingDao FBDAO,EmailDao emaildao,String transactionkey, String paymode,WalletAmountTranferHistory walletAmountTranferHistory,int count,boolean isSpecial,FlightOrderRow flightOrderRow,TboFlightConfig tboFlightConfig)
	{
		String[] search_key = transactionkey.split(":");		
		try {
			TboFlightBookingRequest request = TboRequestBuilder.getTboRequestBuilder().createFlightBookRequestbuilder(flightBookingResponse,tboFlightAirpriceResponse,orderCustomer,flightPriceResponse,flightOrderCustomers,orderId,flightCustomerDetails,isSpecial,tboFlightConfig);

			// Save NONLCC Booking  Request
			try{
				FileUtil.writeJson("flight", "TBO", "NONLCCBook", false, request,  String.valueOf(search_key[1]+search_key[2]));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}
			ObjectMapper mapper=new ObjectMapper();
			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
			logger.info("---------------TBOBookrequest :" +searchRequestInString);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);
			String searchURL=tboFlightConfig.getFlightUrlBook();
			logger.info("---------------BOOK_URL:"+searchURL);

			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------response:"+response);

			TboBookResponse nonLCCBookResponse =  mapper.readValue(response, TboBookResponse.class);

			// Save NONLCC Booking  Request
			try{
				FileUtil.writeJson("flight", "TBO", "NONLCCBook", true, nonLCCBookResponse,  String.valueOf(search_key[1]+search_key[2]));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}

			// Call for Ticket NonLCC Book
			if(nonLCCBookResponse.getResponse().getError().getErrorCode() == 0)
				flightBookingResponse = callTicketingService(flightBookingResponse,nonLCCBookResponse,orderId,FBDAO,emaildao,transactionkey,paymode,walletAmountTranferHistory,count,flightOrderCustomers,flightCustomerDetails,flightOrderRow,tboFlightConfig);
			else
				throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);		

		}catch(Exception e){
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
		}
		return flightBookingResponse;
	}

	public static FlightBookingResponse callTicketingService(FlightBookingResponse flightBookingResponse,TboBookResponse nonLCCBookResponse,String orderId,FlightBookingDao FBDAO,EmailDao emaildao,String transactionkey, String paymode,WalletAmountTranferHistory walletAmountTranferHistory,int count,List<FlightOrderCustomer> flightOrderCustomers,FlightCustomerDetails flightCustomerDetails,FlightOrderRow flightOrderRow,TboFlightConfig tboFlightConfig)
			throws Exception
	{
		NonLCCTicketRequest ticketrequest = TboRequestBuilder.getTboRequestBuilder().createFlightNonLccTicketRequestbuilder(nonLCCBookResponse,tboFlightConfig);
		String[] search_key = transactionkey.split(":");
		
		// Save Ticket Request
		try{
			FileUtil.writeJson("flight", "TBO", "Ticket", false, ticketrequest,  String.valueOf(search_key[1]+search_key[2]));
		} catch (Exception e) {
			logger.error(" The filename, directory name ", e);
		}
		try {
			ObjectMapper mapper=new ObjectMapper();
			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ticketrequest);
			logger.info("---------------TBOTicketrequest for NonLCC:" + searchRequestInString);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);
			String searchURL = tboFlightConfig.getFlightUrlTicket();
			logger.info("---------------Ticket_URL:"+searchURL);

			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------response:"+response);

			TboTicketResponse tboTicketResponse = mapper.readValue(response, TboTicketResponse.class);
			logger.info("---------------tboTicketResponse:"+tboTicketResponse);

			// Save Ticket  Response
			try{
				FileUtil.writeJson("flight", "TBO", "Ticket", true, tboTicketResponse,  String.valueOf(search_key[1]+search_key[2]));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}

			if(tboTicketResponse.getResponse().getError().getErrorCode() == 0)
				flightBookingResponse = TboResponseParser.parseTicketResponse(flightBookingResponse,tboTicketResponse,orderId,FBDAO,emaildao,transactionkey,paymode,walletAmountTranferHistory,count,flightOrderCustomers,flightCustomerDetails,flightOrderRow) ;
			else
				throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);

			logger.info("---------------flightBookingResponse :"+flightBookingResponse);

		}catch(Exception e){
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
		}
		return flightBookingResponse;
	}

	public static TboBookingDetailsResponse callGetBookingDetailsService(String pnr,String bookingid,TboFlightConfig tboFlightConfig)	throws Exception
	{
		GetBookingDetailsRequest getBookingDetailsRequest = TboRequestBuilder.getTboRequestBuilder().createGetBookingDetailsRequestbuilder(pnr,bookingid,tboFlightConfig);
		TboBookingDetailsResponse tboBookingDetailsResponse = new TboBookingDetailsResponse();
		try {
			ObjectMapper mapper=new ObjectMapper();
			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getBookingDetailsRequest);
			logger.info("---------------GetBookingDetailsRequest :" + searchRequestInString);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);
			String searchURL = tboFlightConfig.getFlightUrlGetBookingDetail();
			logger.info("---------------GetBookingDetails URL :"+searchURL);

			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------response:"+response);

			tboBookingDetailsResponse = mapper.readValue(response, TboBookingDetailsResponse.class);
			logger.info("---------------tboBookingDetailsResponse :"+tboBookingDetailsResponse);			

		}catch(Exception e){			
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_REFNO);
		}
		return tboBookingDetailsResponse;
	}

	// Release PNR for NONLCC Carrier.
	public static TboReleasePNRResponse callReleasePNRService(TboBookingDetailsResponse tboBookingDetailsResponse,TboFlightConfig tboFlightConfig)	throws Exception
	{
		TboReleasePNRRequest tboCancellationRequest = TboRequestBuilder.getTboRequestBuilder().createReleasePNRRequestbuilder(tboBookingDetailsResponse,tboFlightConfig);
		TboReleasePNRResponse tboReleasePNRResponse = new TboReleasePNRResponse();
		try {
			ObjectMapper mapper=new ObjectMapper();

			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tboCancellationRequest);
			logger.info("---------------ReleasePNR :" + searchRequestInString);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);
			String searchURL = tboFlightConfig.getFlightUrlReleasePnr();
			logger.info("---------------ReleasePNR URL :"+searchURL);

			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------ReleasePNR response:"+response);


			tboReleasePNRResponse = mapper.readValue(response, TboReleasePNRResponse.class);
			logger.info("---------------tboReleasePNRResponse :"+tboReleasePNRResponse);

		}catch(Exception e){			
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.INVALID_BOOKINGDETAILS);
		}
		return tboReleasePNRResponse;
	}

	// Cancel Ticket Method
	public static TboCancelTicketResponse callCancelTicketService(FlightOrderRow flightOrderRow,FlightCancelRequest flightCancelRequest,List<FlightOrderCustomer> flightOrderCustomers,TboFlightConfig tboFlightConfig)	throws Exception
	{
		TboCancelTicketRequest tboCancelTicketRequest = TboRequestBuilder.getTboRequestBuilder().createCancellationRequestbuilder(flightOrderRow,flightCancelRequest,flightOrderCustomers,tboFlightConfig);
		TboCancelTicketResponse tboCancelTicketResponse = new TboCancelTicketResponse();
		try {
			ObjectMapper mapper=new ObjectMapper();
			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tboCancelTicketRequest);
			logger.info("---------------CANCELLATION :" + searchRequestInString);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);
			String searchURL = tboFlightConfig.getFlightUrlCancellation();
			logger.info("---------------CANCELLATION URL :"+searchURL);

			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------CANCELLATION response:"+response);

			tboCancelTicketResponse = mapper.readValue(response, TboCancelTicketResponse.class);
			logger.info("---------------tboCancelTicketResponse :"+tboCancelTicketResponse);

		}catch(Exception e){		
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
		}
		return tboCancelTicketResponse;
	}

	// Check Cancel Status Method
	public static TboCancellationStatusResponse callCancelTicketStatusService(String changerequestid,TboFlightConfig tboFlightConfig)	throws Exception
	{
		TboCancellationStatusRequest tboCancellationStatusRequest = TboRequestBuilder.getTboRequestBuilder().createCancellationStatusRequestbuilder(changerequestid,tboFlightConfig);
		TboCancellationStatusResponse tboCancellationStatusResponse = new TboCancellationStatusResponse();
		try {
			ObjectMapper mapper=new ObjectMapper();

			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tboCancellationStatusRequest);
			logger.info("---------------CANCELLATION Status :" + searchRequestInString);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);
			String searchURL = tboFlightConfig.getFlightUrlCancellationStatus();
			logger.info("---------------CANCELLATION Status URL :"+searchURL);

			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------CANCELLATION Status response:"+response);

			tboCancellationStatusResponse = mapper.readValue(response, TboCancellationStatusResponse.class);
			logger.info("---------------tboCancellationStatusResponse :"+tboCancellationStatusResponse);

		}catch(Exception e){		
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.CANCELLATION_FAILED);
		}
		return tboCancellationStatusResponse;
	}

	// Check Cancel Status Method
	public static TboPriceBDResponse callPriceBDService(Flightsearch flightsearch,TboFlightSearchResponse tboFlightSearchResponse,TboFlightConfig tboFlightConfig)	throws Exception
	{
		TboPriceBDRequest tboPriceBDRequest = TboRequestBuilder.getTboRequestBuilder().createPriceBDRequestbuilder(flightsearch,tboFlightSearchResponse,tboFlightConfig);
		TboPriceBDResponse tboPriceBDResponse = new TboPriceBDResponse();
		try {
			ObjectMapper mapper=new ObjectMapper();
			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tboPriceBDRequest);
			logger.info("---------------PRICERBD :" + searchRequestInString);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);
			String searchURL = tboFlightConfig.getFlightUrlPriceRbd();
			logger.info("---------------PRICERBD_URL  :"+searchURL);

			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------PRICERBD response:"+response);

			tboPriceBDResponse = mapper.readValue(response, TboPriceBDResponse.class);
			logger.info("---------------tboPriceBDResponse :"+ tboPriceBDResponse);			

		}catch(Exception e){			
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.CANCELLATION_FAILED);
		}

		return tboPriceBDResponse;

	}

	public static TboAgencyBalanceResponse callAgencyBalanceService(TboFlightConfig tboFlightConfig)  {
		TboAgencyBalanceRequest tboAgencyBalanceRequest = TboRequestBuilder.getTboRequestBuilder().createBalanceRequest(tboFlightConfig);
		TboAgencyBalanceResponse tboAgencyBalanceResponse = null;

		try {
			ObjectMapper mapper=new ObjectMapper();
			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tboAgencyBalanceRequest);
			logger.info("---------------AgencyBalance :" + searchRequestInString);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);
			String searchURL = tboFlightConfig.getGeneralUrlAgencyBalance();
			logger.info("---------------AgencyBalance_URL  :"+searchURL);

			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------AgencyBalance response:"+response);

			tboAgencyBalanceResponse = mapper.readValue(response, TboAgencyBalanceResponse.class);
			logger.info("---------------tboAgencyBalanceResponse :"+ tboAgencyBalanceResponse);

		}catch(Exception e){
			logger.error("TboAgencyBalanceResponse Exception" ,e);
		}
		return tboAgencyBalanceResponse;
	}

	public static FlightCalendarSearchResponse callCalendarFareService(FlightCalendarSearch flightCalendarSearch,TboFlightConfig tboFlightConfig,Map<String,List<FlightMarkUpConfig>> markupMap)  {
		TboFlightCalendarFareRequest tboFlightCalendarFareRequest = TboRequestBuilder.getTboRequestBuilder().createCalendarFareSearchBuilder(flightCalendarSearch,tboFlightConfig);
		TboCalendarFareResponse tboCalendarFareResponse = null;
		FlightCalendarSearchResponse flightCalendarSearchResponse = null;
		try {
			ObjectMapper mapper=new ObjectMapper();
			String searchRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tboFlightCalendarFareRequest);
			logger.info("---------------CalendarFare :" + searchRequestInString);

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(searchRequestInString,headers);
			String searchURL = tboFlightConfig.getFlightUrlCalendarSearch();
			logger.info("---------------CalendarFare_URL  :"+searchURL);

			String response = restTemplate.postForObject(searchURL, entity, String.class);
			logger.info("---------------CalendarFare response:"+response);

			tboCalendarFareResponse = mapper.readValue(response, TboCalendarFareResponse.class);
			logger.info("---------------CalendarFareResponse :"+ tboCalendarFareResponse);

			flightCalendarSearchResponse = TboResponseParser.parseCalendarFareResponse(tboCalendarFareResponse,flightCalendarSearch,markupMap);

		}catch(Exception e){
			logger.error("tboCalendarFareResponse Exception" ,e);
		}
		return flightCalendarSearchResponse;
	}
}