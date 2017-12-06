/**
@Author ilyas
15-12-2015 
TayyarahServiceCall.java
 */
/**
 * 
 */
package com.tayyarah.flight.util.api.tayyarah;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tayyarah.apiconfig.model.TayyarahConfig;
import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.FareFlightSegment;
import com.tayyarah.flight.model.FareRule;
import com.tayyarah.flight.model.FareRules;
import com.tayyarah.flight.model.FlightBookingResponse;
import com.tayyarah.flight.model.FlightCustomerDetails;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.FlightSegments;
import com.tayyarah.flight.model.FlightSegmentsGroup;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.SearchFlightResponse;
import com.tayyarah.flight.model.Segments;
import com.tayyarah.flight.model.UAPISearchFlightKeyMap;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.flight.util.FlightWebServiceEndPointValidator;
import com.tayyarah.flight.util.api.bluestar.BluestarUtil;
import com.tayyarah.flight.util.api.travelport.UmarkUpServiceCall;
import com.tayyarah.user.entity.WalletAmountTranferHistory;


public class TayyarahServiceCall {
	static final Logger logger = Logger.getLogger(TayyarahServiceCall.class);

	public static SearchFlightResponse callSearchService(
			Flightsearch flightsearch,
			Map<String,List<FlightMarkUpConfig>> markupMap,
			Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> airportMapList, MoneyExchangeDao moneydao,TayyarahConfig tayyarahConfig)
					throws Exception {
		SearchFlightResponse searchFlightResponseOLD=null;
		SearchFlightResponse searchFlightResponse=new SearchFlightResponse();
		ObjectMapper mapper = new ObjectMapper();		
		try {			
			String ArrvlDAte="20151216";
			{
				try{
					ArrvlDAte = FlightWebServiceEndPointValidator.getDateYYYYMMDD(flightsearch.getArvlDate());
				}catch(Exception e)
				{
					logger.error("Exception", e);
				}
			}

			String searchURL=tayyarahConfig.getUrl()+"/flight/search?adult="+flightsearch.getAdult()+"&airline="+flightsearch.getAirline()+"&app_key="+tayyarahConfig.getApp_key()+"&arvlDate="+ArrvlDAte+"&cabinClass="+flightsearch.getCabinClass()+"&currency="+tayyarahConfig.DEFAULT_CURRENCY+"&depDate="+FlightWebServiceEndPointValidator.getDateYYYYMMDD(flightsearch.getDepDate())+"&destination="+flightsearch.getDestination()+"&infant="+flightsearch.getInfant()+"&kid="+flightsearch.getKid()+"&origin="+flightsearch.getOrigin()+"&trips="+flightsearch.getTrips()+"&triptype="+flightsearch.getTripType();
			logger.info("searchURL  :"+searchURL);
			searchFlightResponseOLD = mapper.readValue(new URL(searchURL), SearchFlightResponse.class);
			logger.info("searchFlightResponseOLD  :"+searchFlightResponseOLD);
			//Pretty print
			String searchFlightResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(searchFlightResponseOLD);
			logger.info("searchFlightResponseInString  :"+searchFlightResponseInString);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		try {

			String apiCurrency = searchFlightResponseOLD.getBookedCurrency();
			Map<String, String> ClassMap = BluestarUtil.getCabinMap();
			Map<String, String> cityNameMap = null;
			Map<String, String> airportNameMap = null;
			if (airportMapList != null && airportMapList.size() > 0) {
				cityNameMap = airportMapList.get(0);
				airportNameMap = airportMapList.get(1);
			}
			UAPISearchFlightKeyMap uapiSearchFlightKeyMap = new UAPISearchFlightKeyMap();
			Map<String, FareFlightSegment> fareFlightSegmentMap = new HashMap<String, FareFlightSegment>();
			LinkedHashMap<String, FlightSegments> flightSegmentstMap = new LinkedHashMap<String, FlightSegments>();
			Map<String, FareRules> fareRulesMap = new HashMap<String, FareRules>();
			Map<String, Double> currencyrate= moneydao.getCurrencyRate(flightsearch.getCurrency(), apiCurrency);
			Double currencyValue=   currencyrate.get("value");
			BigDecimal curValue=new BigDecimal(currencyValue);
			currencyrate=  moneydao.getCurrencyRate(flightsearch.getBaseCurrency(), apiCurrency);
			Double currencyValue1= currencyrate.get("value");
			BigDecimal apiToBaseExchangeRate=new BigDecimal(currencyValue1);
			List<FareFlightSegment> fareFlightSegments = new ArrayList<FareFlightSegment>();		
			for(FareFlightSegment fareFlightSegmentOLD:searchFlightResponseOLD.getFareFlightSegment()){		

				FareFlightSegment fareFlightSegment = new FareFlightSegment();			
				fareFlightSegment.setBookingCurrency(flightsearch.getCurrency());
				fareFlightSegment.setExchangeRate(curValue);
				fareFlightSegment.setId(new UID().toString());						
				fareFlightSegment.setCurrency(apiCurrency);
				fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegmentOLD.getBasePrice()).multiply(apiToBaseExchangeRate)));
				fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegmentOLD.getTotalPrice()).multiply(apiToBaseExchangeRate)));
				fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegmentOLD.getTaxes()).multiply(apiToBaseExchangeRate)));
				fareFlightSegment.setApiCurrency(apiCurrency);
				fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
				fareFlightSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);
				fareFlightSegment.setApi_basePriceWithoutMarkup(fareFlightSegmentOLD.getBasePrice());
				fareFlightSegment.setApi_totalPriceWithoutMarkup(fareFlightSegmentOLD.getTotalPrice());
				fareFlightSegment.setApi_taxesWithoutMarkup(fareFlightSegmentOLD.getTaxes());
				UmarkUpServiceCall
				.getMarkupValues(
						flightsearch,
						markupMap,
						fareFlightSegmentOLD.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getCode(),
						fareFlightSegment,"ALL");

				fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));

				List<FlightSegmentsGroup> flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
				for(FlightSegmentsGroup flightSegmentsGroupOLD: fareFlightSegmentOLD.getFlightSegmentsGroups()){				
					FlightSegmentsGroup flightSegmentsGroup = new FlightSegmentsGroup();
					List<FlightSegments> FlightSegmentsList = new ArrayList<FlightSegments>();
					int i=0;
					for(FlightSegments flightSegmentsOLD:flightSegmentsGroupOLD.getFlightSegments())
					{
						FlightSegments flightSegments=new FlightSegments();
						List<Segments> segmentsList = new ArrayList<Segments>();
						for(Segments segmentsOLD:flightSegmentsOLD.getSegments()){
							segmentsList.add(segmentsOLD);
						}
						flightSegments.setSegments(segmentsList);
						flightSegments.setApiProvider("Tayyarah");					
						flightSegments.setBookingCurrency(flightsearch.getCurrency());
						flightSegments.setExchangeRate(curValue);					
						String flightindexForSegments="TAY"+flightSegmentsOLD.getFlightIndex();
						flightSegments.setFlightIndex(flightindexForSegments);
						FlightSegmentsList.add(flightSegments);
						flightSegmentstMap.put(flightindexForSegments,flightSegments);
						fareFlightSegmentMap.put(flightindexForSegments,fareFlightSegment);
					}
					flightSegmentsGroup.setFlightSegments(FlightSegmentsList);
					flightSegmentsGroups.add(flightSegmentsGroup);
				}
				fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);
				List<FareRules> fareRulesList = new ArrayList<FareRules>();
				for(FareRules fareRulesOLD:fareFlightSegmentOLD.getFareRules()){
					FareRules fareRules=new FareRules();
					List<FareRule> fareRuleList = new ArrayList<FareRule>();
					for(FareRule fareruleOLd:fareRulesOLD.getFareRule()){
						FareRule farerule=new FareRule();
						farerule=fareruleOLd;
						fareRuleList.add(farerule);
					}
					fareRules.setFareRule(fareRuleList);
					fareRules.setTravelerType(fareRulesOLD.getTravelerType());
					fareRulesList.add(fareRules);
				}
				fareFlightSegment.setFareRules(fareRulesList);
				fareFlightSegments.add(fareFlightSegment);
			}
			searchFlightResponse.setFareFlightSegment(fareFlightSegments);
			uapiSearchFlightKeyMap.setFareRulesMap(fareRulesMap);
			uapiSearchFlightKeyMap.setFareFlightSegmentMap(fareFlightSegmentMap);
			uapiSearchFlightKeyMap.setFlightSegmentstMap(flightSegmentstMap);			
			uapiSearchFlightKeyMap.setFlightsearch(flightsearch);
			uapiSearchFlightKeyMap.setFlightMarkUpConfiglistMap(markupMap);
			uapiSearchFlightKeyMap.setExchangeRate(curValue);
			searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap);
			flightsearch.setSearchKey(searchFlightResponseOLD.getSearchKey());
			flightsearch.setTransactionKey((searchFlightResponseOLD.getTransactionKey()));// collect this search key for later use
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return searchFlightResponse;
	}

	public static FlightBookingResponse callBookingService(FlightBookingResponse flightBookingResponse,OrderCustomer orderCustomer,FlightPriceResponse flightPriceResponse,List<FlightOrderCustomer> flightOrderCustomers,String orderId, FlightCustomerDetails flightCustomerDetails,FlightBookingDao FBDAO,EmailDao emaildao,String transactionkey, String paymode,WalletAmountTranferHistory walletAmountTranferHistory,int count)
			throws Exception
	{
		FlightDataBaseServices DBS = new FlightDataBaseServices();
		TayyarahConfig tayyarahConfig=TayyarahConfig.GetTayyarahConfig();		
		flightCustomerDetails.setPaymode(tayyarahConfig.getPaymode());
		flightCustomerDetails.setApp_key(tayyarahConfig.getApp_key());
		flightCustomerDetails.setUserid(tayyarahConfig.getUserid());
		flightCustomerDetails.setUsername(tayyarahConfig.getUsername());
		flightCustomerDetails.setPrice_key(flightCustomerDetails.getPrice_key().substring(4));//removing PTAy from the pricekey
		flightCustomerDetails.setTransactionkey(flightCustomerDetails.getTransactionkey());
		ObjectMapper mapper = new ObjectMapper();		
		String flightCustomerDetailsInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(flightCustomerDetails);
		String url = tayyarahConfig.getUrl()+"/booking/details";
		FlightBookingResponse flightBookingResponseOLD=null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			flightBookingResponseOLD = restTemplate.postForObject(url, flightCustomerDetails, FlightBookingResponse.class);
			String FlightBookingResponseInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(flightBookingResponseOLD);

		}catch (RestClientException Re) {
			logger.error("RestClientException", Re);
			throw new FlightException(ErrorCodeCustomerEnum.RestClientException,ErrorMessages.BOOKING_FAILED);

		} catch (Exception e) {
			logger.error("Exception", e);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.BOOKING_FAILED);
		}
		flightBookingResponse= TayyarahFlightBookingResponseParser.parseFlightBookingResponse(flightBookingResponse,flightBookingResponseOLD,orderId,FBDAO,emaildao,transactionkey,paymode,walletAmountTranferHistory,count);
		return flightBookingResponse;
	}
}