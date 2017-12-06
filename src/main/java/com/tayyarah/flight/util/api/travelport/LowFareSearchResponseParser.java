/**
@Author yogeshsharma
15-Jul-2015 2015
LowFareSearchResponseParser.java
 */
/**
 * 
 */
package com.tayyarah.flight.util.api.travelport;

import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.Cabin;
import com.tayyarah.flight.model.Carrier;
import com.tayyarah.flight.model.FareFlightSegment;
import com.tayyarah.flight.model.FareRule;
import com.tayyarah.flight.model.FareRules;
import com.tayyarah.flight.model.Flight;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.FlightSegments;
import com.tayyarah.flight.model.FlightSegmentsGroup;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.SearchFlightResponse;
import com.tayyarah.flight.model.Segments;
import com.tayyarah.flight.model.UAPISearchFlightKeyMap;
import com.tayyarah.flight.model.UapiAirSegment;
import com.travelport.api_v33.AirResponse.AirPricePoint;
import com.travelport.api_v33.AirResponse.AirPricingInfo;
import com.travelport.api_v33.AirResponse.AirSegmentList;
import com.travelport.api_v33.AirResponse.BookingInfo;
import com.travelport.api_v33.AirResponse.FareInfo;
import com.travelport.api_v33.AirResponse.FareInfoList;
import com.travelport.api_v33.AirResponse.FlightDetails;
import com.travelport.api_v33.AirResponse.FlightDetailsList;
import com.travelport.api_v33.AirResponse.FlightDetailsRef;
import com.travelport.api_v33.AirResponse.FlightOption;
import com.travelport.api_v33.AirResponse.LowFareSearchRsp;
import com.travelport.api_v33.AirResponse.Option;
import com.travelport.api_v33.AirResponse.TypeBaseAirSegment;

public class LowFareSearchResponseParser {
	static Logger logger = Logger.getLogger(LowFareSearchResponseParser.class);
	protected static LinkedHashMap<String, TypeBaseAirSegment> airSegMap;
	protected static Map<String, FareInfo> fareInfolMap;

	public static SearchFlightResponse parseResponseOnewayRound(
			LowFareSearchRsp lowFareSearchRsp,
			Map<String,List<FlightMarkUpConfig>> markupMap,
			Flightsearch flightsearch, Map<String, String> AirlineNameMap,
			ArrayList<Map<String,String>> airportMapList,MoneyExchangeDao moneydao) throws Exception {
		Set<String> airlineList = new TreeSet<String>();		
		Map<String,String> CityNameMap = new HashMap<String, String>();
		Map<String,String> AirportNameMap = new HashMap<String, String>();
		if(airportMapList.size()>0){
			CityNameMap = airportMapList.get(0);
			AirportNameMap = airportMapList.get(1);
		}
		SearchFlightResponse searchFlightResponse = new SearchFlightResponse();
		String apiCurrency = lowFareSearchRsp.getCurrencyType(); 
		FareInfoList fareInfoList = lowFareSearchRsp.getFareInfoList();
		fareInfolMap = new LinkedHashMap<String, FareInfo>();
		for (FareInfo fareInfo : fareInfoList.getFareInfo()) {
			fareInfolMap.put(fareInfo.getKey(), fareInfo);
		}	
		Map<String, FareFlightSegment> fareFlightSegmentMap = new HashMap<String, FareFlightSegment>();
		FlightDetailsList flightDetailsList = lowFareSearchRsp.getFlightDetailsList();
		LinkedHashMap<String, FlightDetails> flightDetailSMap = new LinkedHashMap<String, FlightDetails>();
		for (FlightDetails flightDetails : flightDetailsList.getFlightDetails()) {			
			flightDetailSMap.put(flightDetails.getKey(), flightDetails);
		}	
		AirSegmentList airSegmentList = lowFareSearchRsp.getAirSegmentList();
		airSegMap = new LinkedHashMap<String, TypeBaseAirSegment>();
		for (TypeBaseAirSegment airSegment : airSegmentList.getAirSegment()) {
			airSegMap.put(airSegment.getKey(), airSegment);
		}
		Map<String, Option> optionMap = new HashMap<String, Option>();
		UAPISearchFlightKeyMap uapiSearchFlightKeyMap = new UAPISearchFlightKeyMap();
		LinkedHashMap<String, List<UapiAirSegment>> uapiAirSegmentListMap = new LinkedHashMap<String, List<UapiAirSegment>>();
		List<AirPricePoint> airPricePointList = lowFareSearchRsp.getAirPricePointList().getAirPricePoint();		
		Map<String, Double> currencyrate=null; 
		try { 		
			currencyrate =  moneydao.getCurrencyRate(flightsearch.getCurrency(), apiCurrency);
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		Double currencyValue = currencyrate.get("value");
		BigDecimal curValue = new BigDecimal(currencyValue);
		Map<String, Double> currencyrate1=null; 
		try { 		
			currencyrate1=  moneydao.getCurrencyRate(flightsearch.getBaseCurrency(), apiCurrency);
		} catch (Exception e) {  
			logger.error(e);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,
					FlightErrorMessages.NO_FLIGHT);
		}  
		Double currencyValue1 = currencyrate1.get("value");
		BigDecimal apiToBaseExchangeRate = new BigDecimal(currencyValue1);
		List<FareFlightSegment> fareFlightSegments = new ArrayList<FareFlightSegment>();
		for (AirPricePoint airPricePoint : airPricePointList) {
			if (airPricePoint.isCompleteItinerary()) {
				FareFlightSegment fareFlightSegment = new FareFlightSegment();				
				fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
						airPricePoint.getApproximateBasePrice(), apiCurrency)).multiply(apiToBaseExchangeRate)));
				fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
						airPricePoint.getApproximateTotalPrice(), apiCurrency)).multiply(apiToBaseExchangeRate)));
				fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
						airPricePoint.getApproximateTaxes(), apiCurrency)).multiply(apiToBaseExchangeRate)));
				fareFlightSegment.setApiCurrency(apiCurrency);
				fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
				fareFlightSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);
				fareFlightSegment.setApi_basePriceWithoutMarkup(replaceCurrency(
						airPricePoint.getApproximateBasePrice(), apiCurrency));
				fareFlightSegment.setApi_totalPriceWithoutMarkup(replaceCurrency(
						airPricePoint.getApproximateTotalPrice(), apiCurrency));
				fareFlightSegment.setApi_taxesWithoutMarkup(replaceCurrency(
						airPricePoint.getApproximateTaxes(), apiCurrency));
				fareFlightSegment.setId((new UID()).toString());
				fareFlightSegment.setBookingCurrency(flightsearch.getCurrency());
				fareFlightSegment.setExchangeRate(curValue);
				fareFlightSegment.setCurrency(apiCurrency); 
				Set<String> fareBasisCodeSet = new HashSet<String>();
				String airlineCode="ALL";
				if (airPricePoint.getAirPricingInfo() != null
						&& airPricePoint.getAirPricingInfo().size() > 0) {
					for (AirPricingInfo airPricingInfo : airPricePoint
							.getAirPricingInfo())
					{
						if (airPricingInfo.getFlightOptionsList() != null
								&& airPricingInfo.getFlightOptionsList()
								.getFlightOption() != null
								&& airPricingInfo.getFlightOptionsList()
								.getFlightOption().size() > 0) {
							List<FlightSegmentsGroup> flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
							List<FareRules> fareRulesList = new ArrayList<FareRules>();
							int i = 0;
							for (FlightOption flightOption : airPricingInfo
									.getFlightOptionsList().getFlightOption()) {
								FlightSegmentsGroup flightSegmentsGroup = new FlightSegmentsGroup();
								if (flightOption.getOption() != null
										&& flightOption.getOption().size() > 0) {
									List<FlightSegments> flightSegmentsList = new ArrayList<FlightSegments>();
									for (Option option : flightOption
											.getOption()) {
										FlightSegments flightSegments = new FlightSegments();
										FareRules farerules = new FareRules();
										String flightindex="TP"+(new UID()).toString();									
										flightSegments.setFlightIndex(flightindex);
										flightSegments.setApiProvider("Travelport");										
										flightSegments.setBookingCurrency(flightsearch.getCurrency());
										flightSegments.setExchangeRate(curValue);
										optionMap.put(flightindex, option);
										List<UapiAirSegment> uapiAirSegments = new ArrayList<UapiAirSegment>();
										if (option.getBookingInfo() != null
												&& option.getBookingInfo()
												.size() > 0) {
											List<Segments> segmentsList = new ArrayList<Segments>();
											List<FareRule> FareRuleList = new ArrayList<FareRule>();
											for (BookingInfo bookingInfo : option
													.getBookingInfo()) {
												Segments segments = new Segments();
												TypeBaseAirSegment typeBaseAirSegment = airSegMap
														.get(bookingInfo
																.getSegmentRef());
												FareInfo fareinfo = fareInfolMap
														.get(bookingInfo
																.getFareInfoRef());											
												UapiAirSegment uapiAirSegment = new UapiAirSegment();		
												getUapiAirSegment(uapiAirSegment,typeBaseAirSegment,fareinfo,bookingInfo.getBookingCode());		
												uapiAirSegment.setBookingCurrency(flightsearch.getCurrency());
												uapiAirSegment.setExchangeRate(curValue);
												uapiAirSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);												
												uapiAirSegments.add(uapiAirSegment);
												if (typeBaseAirSegment
														.getFlightDetailsRef() != null
														&& typeBaseAirSegment
														.getFlightDetailsRef()
														.size() > 0) {
													for (FlightDetailsRef flightDetailsRef : typeBaseAirSegment
															.getFlightDetailsRef())
													{
														FareRule farerule = new FareRule();
														FlightDetails flightDetails = flightDetailSMap
																.get(flightDetailsRef
																		.getKey());														
														segments.setStops(option.getBookingInfo().size());														
														segments.setDuration(String
																.valueOf(flightDetails
																		.getFlightTime()));
														segments.setOri(flightDetails
																.getOrigin());
														segments.setDest(flightDetails
																.getDestination());
														segments.setOriAirportName(AirportNameMap
																.get(flightDetails
																		.getOrigin()));
														segments.setDestAirportName(AirportNameMap
																.get(flightDetails
																		.getDestination()));
														segments.setOriName(CityNameMap
																.get(flightDetails
																		.getOrigin()));
														segments.setDestName(CityNameMap
																.get(flightDetails
																		.getDestination()));													
														if(typeBaseAirSegment
																.getArrivalTime()!=null && !typeBaseAirSegment
																.getArrivalTime().equalsIgnoreCase("")){
															segments.setArrival(typeBaseAirSegment
																	.getArrivalTime());
															segments.setArrTime(segments.getArrival().substring(11, 16));
															segments.setArrDate(segments.getArrival().substring(0, 10));
														}
														if(typeBaseAirSegment
																.getDepartureTime()!=null && ! typeBaseAirSegment
																.getDepartureTime().equalsIgnoreCase("")){
															segments.setDepart(typeBaseAirSegment
																	.getDepartureTime());
															segments.setDepTime(segments.getDepart().substring(11, 16));
															segments.setDepDate(segments.getDepart().substring(0, 10));	
														}
														Flight flight = new Flight();
														flight.setDestTerminal(flightDetails
																.getDestinationTerminal());
														flight.setOriTerminal(flightDetails
																.getOriginTerminal());
														flight.setEquipment(flightDetails
																.getEquipment());
														flight.setNumber(typeBaseAirSegment
																.getFlightNumber());
														segments.setFlight(flight);
														Carrier carrier = new Carrier();
														carrier.setCode(typeBaseAirSegment
																.getCarrier());
														airlineList.add(typeBaseAirSegment.getCarrier());
														carrier.setName(AirlineNameMap
																.get(typeBaseAirSegment
																		.getCarrier()));
														segments.setCarrier(carrier);
														Cabin cabin = new Cabin();
														cabin.setName(bookingInfo
																.getCabinClass());
														cabin.setCode(bookingInfo
																.getBookingCode());
														segments.setCabin(cabin);
														segments.setFareInfoRef(bookingInfo.getFareInfoRef());
														segmentsList.add(segments);
														if (fareinfo.getOrigin() != null) {
															fareBasisCodeSet.add(fareinfo.getFareBasis());
															farerule.setAirlineCode(typeBaseAirSegment
																	.getCarrier());
															farerule.setArrCode(fareinfo
																	.getDestination());
															farerule.setBasisCode(fareinfo
																	.getFareBasis());
															farerule.setDepCode(fareinfo
																	.getOrigin());
															farerule.setDepDate(fareinfo
																	.getEffectiveDate());
															if (fareinfo.getFareRuleKey() != null)
															{
																farerule.setFareInfoRef(fareinfo.getFareRuleKey().getFareInfoRef());
																farerule.setFareProviderCode(fareinfo.getFareRuleKey().getProviderCode());
																farerule.setFareValue(fareinfo.getFareRuleKey().getValue());
															}
															if (fareinfo.getBaggageAllowance() != null)
															{
																farerule.setBagAllowanceUnit(fareinfo.getBaggageAllowance().getMaxWeight().getUnit());
																farerule.setBagAllowanceValue(fareinfo.getBaggageAllowance().getMaxWeight().getValue());
															}
															FareRuleList
															.add(farerule);
															farerules
															.setTravelerType(flightsearch
																	.getTripType());
															farerules
															.setFareRule(FareRuleList);
															fareRulesList
															.add(farerules);															
														}													
														if(i==0){															
															airlineCode=typeBaseAirSegment.getCarrier();															
														}
													}
												}												
											}  
											flightSegments
											.setSegments(segmentsList);
											fareFlightSegment
											.setFareRules(fareRulesList);
										}
										flightSegmentsList.add(flightSegments);
										fareFlightSegmentMap.put(flightindex,
												fareFlightSegment);
										uapiAirSegmentListMap.put(flightindex, uapiAirSegments);
									}
									flightSegmentsGroup
									.setFlightSegments(flightSegmentsList);
								}
								flightSegmentsGroups.add(flightSegmentsGroup);
								i++;
							}
							fareFlightSegment
							.setFlightSegmentsGroups(flightSegmentsGroups);
						}
					}
				}			
				String fareBasisCode="ALL";
				if(fareBasisCodeSet.size()==1){
					fareBasisCode=fareBasisCodeSet.iterator().next();
				}
				UmarkUpServiceCall.getMarkupValues(flightsearch,
						markupMap,airlineCode,fareFlightSegment,fareBasisCode);
				fareFlightSegment.setFareBasisCode(fareBasisCode);			
				fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));
				fareFlightSegments.add(fareFlightSegment);
			}
		}
		uapiSearchFlightKeyMap.setUapiAirSegmentListMap(uapiAirSegmentListMap);
		uapiSearchFlightKeyMap.setFlightsearch(flightsearch);
		uapiSearchFlightKeyMap.setFlightMarkUpConfiglistMap(markupMap);
		uapiSearchFlightKeyMap.setExchangeRate(curValue);
		uapiSearchFlightKeyMap.setFareFlightSegmentMap(fareFlightSegmentMap);	
		uapiSearchFlightKeyMap.setAirlineList(airlineList);
		searchFlightResponse.setFareFlightSegment(fareFlightSegments);
		searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap);		
		return searchFlightResponse;
	}

	private static String replaceCurrency(String data, String currency) {
		return data.replace(currency, "");
	}

	public static void getUapiAirSegment(UapiAirSegment uapiAirSegment,TypeBaseAirSegment typeBaseAirSegment,FareInfo fareinfo,String classofservices){
		uapiAirSegment.setKey(typeBaseAirSegment.getKey());
		uapiAirSegment.setArrivalTime(typeBaseAirSegment.getArrivalTime());
		uapiAirSegment.setAvailabilityDisplayType(typeBaseAirSegment.getAvailabilityDisplayType());		
		uapiAirSegment.setKey(typeBaseAirSegment.getKey());
		uapiAirSegment.setAvailabilitySource(typeBaseAirSegment
				.getAvailabilitySource());
		uapiAirSegment.setAvailabilityDisplayType(typeBaseAirSegment
				.getAvailabilityDisplayType());
		uapiAirSegment.setGroup(typeBaseAirSegment.getGroup());
		uapiAirSegment.setCarrier(typeBaseAirSegment.getCarrier());
		uapiAirSegment
		.setFlightNumber(typeBaseAirSegment.getFlightNumber());
		uapiAirSegment.setOrigin(typeBaseAirSegment.getOrigin());
		uapiAirSegment.setDestination(typeBaseAirSegment.getDestination());
		uapiAirSegment.setDepartureTime(typeBaseAirSegment
				.getDepartureTime());
		uapiAirSegment.setArrivalTime(typeBaseAirSegment.getArrivalTime());
		uapiAirSegment.setFlightTime(typeBaseAirSegment.getFlightTime());
		uapiAirSegment.setDistance(typeBaseAirSegment.getDistance());
		uapiAirSegment.setFarebasiscode(fareinfo.getFareBasis());
		uapiAirSegment.setProvidecode(fareinfo.getFareRuleKey().getProviderCode());
		uapiAirSegment.setClassOfService(classofservices);
	}

	public static SearchFlightResponse parseResponseRoundTripWithSingleGroup(
			LowFareSearchRsp lowFareSearchRsp,
			Map<String, List<FlightMarkUpConfig>> markupMap,
			Flightsearch flightsearch, Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> airportMapList, MoneyExchangeDao moneydao) throws Exception {		
		Map<String, String> CityNameMap = new HashMap<String, String>();
		Map<String, String> AirportNameMap = new HashMap<String, String>();
		if (airportMapList.size() > 0) {
			CityNameMap = airportMapList.get(0);
			AirportNameMap = airportMapList.get(1);
		}
		SearchFlightResponse searchFlightResponse = new SearchFlightResponse();
		String apiCurrency = lowFareSearchRsp.getCurrencyType();		
		FareInfoList fareInfoList = lowFareSearchRsp.getFareInfoList();
		fareInfolMap = new LinkedHashMap<String, FareInfo>();
		for (FareInfo fareInfo : fareInfoList.getFareInfo()) {
			fareInfolMap.put(fareInfo.getKey(), fareInfo);
		}
		Map<String, FareFlightSegment> fareFlightSegmentMap = new HashMap<String, FareFlightSegment>();
		FlightDetailsList flightDetailsList = lowFareSearchRsp
				.getFlightDetailsList();
		LinkedHashMap<String, FlightDetails> flightDetailSMap = new LinkedHashMap<String, FlightDetails>();
		for (FlightDetails flightDetails : flightDetailsList.getFlightDetails()) {			
			flightDetailSMap.put(flightDetails.getKey(), flightDetails);
		}
		AirSegmentList airSegmentList = lowFareSearchRsp.getAirSegmentList();
		airSegMap = new LinkedHashMap<String, TypeBaseAirSegment>();
		for (TypeBaseAirSegment airSegment : airSegmentList.getAirSegment()) {
			airSegMap.put(airSegment.getKey(), airSegment);
		}
		Map<String, Option> optionMap = new HashMap<String, Option>();
		UAPISearchFlightKeyMap uapiSearchFlightKeyMap = new UAPISearchFlightKeyMap();
		LinkedHashMap<String, List<UapiAirSegment>> uapiAirSegmentListMap = new LinkedHashMap<String, List<UapiAirSegment>>();		
		List<AirPricePoint> airPricePointList = lowFareSearchRsp.getAirPricePointList().getAirPricePoint();
		Map<String, Double> currencyrate = null; 
		try { 	
			currencyrate=  moneydao.getCurrencyRate(flightsearch.getCurrency(), apiCurrency);
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		Double currencyValue = currencyrate.get("value");
		BigDecimal curValue = new BigDecimal(currencyValue);
		Map<String, Double> currencyrate1=null; 
		try {			
			currencyrate1=  moneydao.getCurrencyRate(flightsearch.getBaseCurrency(), apiCurrency);
		} catch (Exception e) {  
			logger.error(e);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,
					FlightErrorMessages.NO_FLIGHT);
		} 
		Double currencyValue1 = currencyrate1.get("value");
		BigDecimal apiToBaseExchangeRate = new BigDecimal(currencyValue1);
		List<FareFlightSegment> fareFlightSegments = new ArrayList<FareFlightSegment>();		
		for (AirPricePoint airPricePoint : airPricePointList) {
			if (airPricePoint.isCompleteItinerary()) {
				if (airPricePoint.getAirPricingInfo() != null
						&& airPricePoint.getAirPricingInfo().size() > 0) {
					for (AirPricingInfo airPricingInfo : airPricePoint
							.getAirPricingInfo())

					{
						if (airPricingInfo.getFlightOptionsList() != null
								&& airPricingInfo.getFlightOptionsList()
								.getFlightOption() != null
								&& airPricingInfo.getFlightOptionsList()
								.getFlightOption().size() > 0) {
							if (2 == airPricingInfo.getFlightOptionsList()
									.getFlightOption().size()) {
								FlightOption flightOption1 = airPricingInfo
										.getFlightOptionsList()
										.getFlightOption().get(0);

								if (flightOption1.getOption() != null
										&& flightOption1.getOption().size() > 0) {
									for (int j = 0; j < flightOption1
											.getOption().size(); j++) {
										Option option1 = flightOption1
												.getOption().get(j);
										FlightSegmentsGroup flightSegmentsGroup = new FlightSegmentsGroup();
										List<FlightSegments> flightSegmentsList1 = new ArrayList<FlightSegments>();
										FlightSegments flightSegments1 = new FlightSegments();
										FareRules farerules1 = new FareRules();
										String flightindex1 = "TP"
												+ (new UID()).toString();									
										flightSegments1
										.setFlightIndex(flightindex1);
										flightSegments1
										.setApiProvider("Travelport");
										flightSegments1
										.setBookingCurrency(flightsearch
												.getCurrency());
										flightSegments1
										.setExchangeRate(curValue);
										optionMap.put(flightindex1, option1);
										List<UapiAirSegment> uapiAirSegments1 = new ArrayList<UapiAirSegment>();
										if (option1.getBookingInfo() != null
												&& option1.getBookingInfo()
												.size() > 0) {
											List<Segments> segmentsList1 = new ArrayList<Segments>();
											List<FareRule> FareRuleList1 = new ArrayList<FareRule>();
											for (BookingInfo bookingInfo1 : option1
													.getBookingInfo()) {
												Segments segments1 = new Segments();
												TypeBaseAirSegment typeBaseAirSegment1 = airSegMap
														.get(bookingInfo1
																.getSegmentRef());
												FareInfo fareinfo1 = fareInfolMap
														.get(bookingInfo1
																.getFareInfoRef());												
												UapiAirSegment uapiAirSegment1 = new UapiAirSegment();
												getUapiAirSegment(
														uapiAirSegment1,
														typeBaseAirSegment1,
														fareinfo1,
														bookingInfo1
														.getBookingCode());
												uapiAirSegment1
												.setBookingCurrency(flightsearch
														.getCurrency());
												uapiAirSegment1
												.setExchangeRate(curValue);									
												uapiAirSegment1
												.setApiToBaseExchangeRate(apiToBaseExchangeRate);
												uapiAirSegments1
												.add(uapiAirSegment1);
												if (typeBaseAirSegment1
														.getFlightDetailsRef() != null
														&& typeBaseAirSegment1
														.getFlightDetailsRef()
														.size() > 0) {
													int i = 0;
													for (FlightDetailsRef flightDetailsRef1 : typeBaseAirSegment1
															.getFlightDetailsRef()) {
														FareRule farerule1 = new FareRule();
														FlightDetails flightDetails1 = flightDetailSMap
																.get(flightDetailsRef1
																		.getKey());													
														segments1
														.setStops(option1
																.getBookingInfo()
																.size());// settign
														// stops
														segments1
														.setDuration(String
																.valueOf(flightDetails1
																		.getFlightTime()));
														segments1
														.setOri(flightDetails1
																.getOrigin());
														segments1
														.setDest(flightDetails1
																.getDestination());
														segments1
														.setOriAirportName(AirportNameMap
																.get(flightDetails1
																		.getOrigin()));
														segments1
														.setDestAirportName(AirportNameMap
																.get(flightDetails1
																		.getDestination()));
														segments1
														.setOriName(CityNameMap
																.get(flightDetails1
																		.getOrigin()));
														segments1
														.setDestName(CityNameMap
																.get(flightDetails1
																		.getDestination()));														
														if (flightDetails1
																.getArrivalTime() != null
																&& !flightDetails1
																.getArrivalTime()
																.equalsIgnoreCase(
																		"")) {
															segments1
															.setArrival(flightDetails1
																	.getArrivalTime());
															segments1
															.setArrTime(segments1
																	.getArrival()
																	.substring(
																			11,
																			16));
															segments1
															.setArrDate(segments1
																	.getArrival()
																	.substring(
																			0,
																			10));
														}
														if (flightDetails1
																.getDepartureTime() != null
																&& !flightDetails1
																.getDepartureTime()
																.equalsIgnoreCase(
																		"")) {
															segments1
															.setDepart(flightDetails1
																	.getDepartureTime());
															segments1
															.setDepTime(segments1
																	.getDepart()
																	.substring(
																			11,
																			16));
															segments1
															.setDepDate(segments1
																	.getDepart()
																	.substring(
																			0,
																			10));
														}

														if (typeBaseAirSegment1
																.getArrivalTime() != null
																&& !typeBaseAirSegment1
																.getArrivalTime()
																.equalsIgnoreCase(
																		"")) {
															segments1
															.setArrival(typeBaseAirSegment1
																	.getArrivalTime());
															segments1
															.setArrTime(segments1
																	.getArrival()
																	.substring(
																			11,
																			16));
															segments1
															.setArrDate(segments1
																	.getArrival()
																	.substring(
																			0,
																			10));
														}
														if (typeBaseAirSegment1
																.getDepartureTime() != null
																&& !typeBaseAirSegment1
																.getDepartureTime()
																.equalsIgnoreCase(
																		"")) {
															segments1
															.setDepart(typeBaseAirSegment1
																	.getDepartureTime());
															segments1
															.setDepTime(segments1
																	.getDepart()
																	.substring(
																			11,
																			16));
															segments1
															.setDepDate(segments1
																	.getDepart()
																	.substring(
																			0,
																			10));
														}

														Flight flight1 = new Flight();
														flight1.setDestTerminal(flightDetails1
																.getDestinationTerminal());
														flight1.setOriTerminal(flightDetails1
																.getOriginTerminal());
														flight1.setEquipment(flightDetails1
																.getEquipment());
														flight1.setNumber(typeBaseAirSegment1
																.getFlightNumber());
														segments1
														.setFlight(flight1);
														Carrier carrier = new Carrier();
														carrier.setCode(typeBaseAirSegment1
																.getCarrier());
														carrier.setName(AirlineNameMap
																.get(typeBaseAirSegment1
																		.getCarrier()));
														segments1
														.setCarrier(carrier);
														Cabin cabin = new Cabin();
														cabin.setName(bookingInfo1
																.getCabinClass());
														cabin.setCode(bookingInfo1
																.getBookingCode());
														segments1
														.setCabin(cabin);
														segments1
														.setFareInfoRef(bookingInfo1
																.getFareInfoRef());
														segmentsList1
														.add(segments1);

														if (fareinfo1
																.getOrigin() != null) {

															farerule1
															.setAirlineCode(typeBaseAirSegment1
																	.getCarrier());
															farerule1
															.setArrCode(fareinfo1
																	.getDestination());
															farerule1
															.setBasisCode(fareinfo1
																	.getFareBasis());
															farerule1
															.setDepCode(fareinfo1
																	.getOrigin());
															farerule1
															.setDepDate(fareinfo1
																	.getEffectiveDate());
															if (fareinfo1
																	.getFareRuleKey() != null) {
																farerule1
																.setFareInfoRef(fareinfo1
																		.getFareRuleKey()
																		.getFareInfoRef());
																farerule1
																.setFareProviderCode(fareinfo1
																		.getFareRuleKey()
																		.getProviderCode());
																farerule1
																.setFareValue(fareinfo1
																		.getFareRuleKey()
																		.getValue());
															}
															if (fareinfo1
																	.getBaggageAllowance() != null) {
																farerule1
																.setBagAllowanceUnit(fareinfo1
																		.getBaggageAllowance()
																		.getMaxWeight()
																		.getUnit());
																farerule1
																.setBagAllowanceValue(fareinfo1
																		.getBaggageAllowance()
																		.getMaxWeight()
																		.getValue());
															}
															FareRuleList1
															.add(farerule1);
															farerules1
															.setTravelerType(flightsearch
																	.getTripType());
															farerules1
															.setFareRule(FareRuleList1);

															// /////////////////////////BaggageAllowance////////
															// BaggageAllowance
															// BA=fareinfo.getBaggageAllowance();
															// //////////
														}
													}

												}

											}
											flightSegments1
											.setSegments(segmentsList1);

										}

										flightSegmentsList1
										.add(flightSegments1);

										uapiAirSegmentListMap.put(flightindex1,
												uapiAirSegments1);
										flightSegmentsGroup
										.setFlightSegments(flightSegmentsList1);

										FlightOption flightOption2 = airPricingInfo
												.getFlightOptionsList()
												.getFlightOption().get(1);

										Set<String> fareBasisCodeSet = new HashSet<String>();
										String airlineCode="ALL";


										if (flightOption2.getOption() != null
												&& flightOption2.getOption()
												.size() > 0) {

											for (int k = 0; k < flightOption2
													.getOption().size(); k++) {
												Option option = flightOption2
														.getOption().get(k);

												FareFlightSegment fareFlightSegment = new FareFlightSegment();

												// //with base
												// currency//////////////
												fareFlightSegment
												.setBasePriceWithoutMarkup(String
														.valueOf(new BigDecimal(
																replaceCurrency(
																		airPricePoint
																		.getApproximateBasePrice(),
																		apiCurrency))
																.multiply(apiToBaseExchangeRate)));
												fareFlightSegment
												.setTotalPriceWithoutMarkup(String
														.valueOf(new BigDecimal(
																replaceCurrency(
																		airPricePoint
																		.getApproximateTotalPrice(),
																		apiCurrency))
																.multiply(apiToBaseExchangeRate)));
												fareFlightSegment
												.setTaxesWithoutMarkup(String
														.valueOf(new BigDecimal(
																replaceCurrency(
																		airPricePoint
																		.getApproximateTaxes(),
																		apiCurrency))
																.multiply(apiToBaseExchangeRate)));

												fareFlightSegment
												.setApiCurrency(apiCurrency);
												fareFlightSegment
												.setBaseCurrency(flightsearch
														.getBaseCurrency());
												fareFlightSegment
												.setApiToBaseExchangeRate(apiToBaseExchangeRate);
												fareFlightSegment
												.setApi_basePriceWithoutMarkup(replaceCurrency(
														airPricePoint
														.getApproximateBasePrice(),
														apiCurrency));
												fareFlightSegment
												.setApi_totalPriceWithoutMarkup(replaceCurrency(
														airPricePoint
														.getApproximateTotalPrice(),
														apiCurrency));
												fareFlightSegment
												.setApi_taxesWithoutMarkup(replaceCurrency(
														airPricePoint
														.getApproximateTaxes(),
														apiCurrency));
												fareFlightSegment
												.setId((new UID())
														.toString());
												fareFlightSegment
												.setBookingCurrency(flightsearch
														.getCurrency());
												fareFlightSegment
												.setExchangeRate(curValue);

												fareFlightSegment
												.setCurrency(apiCurrency);

												List<FlightSegments> flightSegmentsList = new ArrayList<FlightSegments>();
												FlightSegmentsGroup flightSegmentsGroup2 = new FlightSegmentsGroup();

												List<FlightSegmentsGroup> flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
												List<FareRules> fareRulesList = new ArrayList<FareRules>();
												flightSegmentsGroups
												.add(flightSegmentsGroup);
												fareRulesList.add(farerules1);
												FlightSegments flightSegments = new FlightSegments();
												FareRules farerules = new FareRules();
												String flightindex = "TP"
														+ (new UID())
														.toString();
												// Below for flight search key
												flightSegments
												.setFlightIndex(flightindex);
												flightSegments
												.setApiProvider("Travelport");											
												flightSegments
												.setBookingCurrency(flightsearch
														.getCurrency());
												flightSegments
												.setExchangeRate(curValue);										

												optionMap.put(flightindex,
														option);
												List<UapiAirSegment> uapiAirSegments = new ArrayList<UapiAirSegment>();
												if (option.getBookingInfo() != null
														&& option
														.getBookingInfo()
														.size() > 0) {
													// add here
													List<Segments> segmentsList = new ArrayList<Segments>();
													List<FareRule> FareRuleList = new ArrayList<FareRule>();
													System.out
													.println(k
															+ "option.getKey() :"
															+ option.getKey()
															+ "option.getBookingInfo().size :"
															+ option.getBookingInfo()
															.size());
													for (BookingInfo bookingInfo : option
															.getBookingInfo()) {
														Segments segments = new Segments();
														TypeBaseAirSegment typeBaseAirSegment = airSegMap
																.get(bookingInfo
																		.getSegmentRef());
														FareInfo fareinfo = fareInfolMap
																.get(bookingInfo
																		.getFareInfoRef());												
														UapiAirSegment uapiAirSegment = new UapiAirSegment();
														getUapiAirSegment(
																uapiAirSegment,
																typeBaseAirSegment,
																fareinfo,
																bookingInfo
																.getBookingCode());
														uapiAirSegment
														.setBookingCurrency(flightsearch
																.getCurrency());
														uapiAirSegment
														.setExchangeRate(curValue);
														uapiAirSegment
														.setApiToBaseExchangeRate(apiToBaseExchangeRate);
														uapiAirSegments
														.add(uapiAirSegment);
														if (typeBaseAirSegment
																.getFlightDetailsRef() != null
																&& typeBaseAirSegment
																.getFlightDetailsRef()
																.size() > 0) {
															int i = 0;
															for (FlightDetailsRef flightDetailsRef : typeBaseAirSegment
																	.getFlightDetailsRef()) {
																FareRule farerule = new FareRule();
																FlightDetails flightDetails = flightDetailSMap
																		.get(flightDetailsRef
																				.getKey());
																segments.setStops(option
																		.getBookingInfo()
																		.size());// settign
																// stops
																segments.setDuration(String
																		.valueOf(flightDetails
																				.getFlightTime()));
																segments.setOri(flightDetails
																		.getOrigin());
																segments.setDest(flightDetails
																		.getDestination());
																segments.setOriAirportName(AirportNameMap
																		.get(flightDetails
																				.getOrigin()));
																segments.setDestAirportName(AirportNameMap
																		.get(flightDetails
																				.getDestination()));
																segments.setOriName(CityNameMap
																		.get(flightDetails
																				.getOrigin()));
																segments.setDestName(CityNameMap
																		.get(flightDetails
																				.getDestination()));
																// 2015-12-24T18:35:00.000+05:30
																if (flightDetails
																		.getArrivalTime() != null
																		&& !flightDetails
																		.getArrivalTime()
																		.equalsIgnoreCase(
																				"")) {
																	segments.setArrival(flightDetails
																			.getArrivalTime());
																	segments.setArrTime(segments
																			.getArrival()
																			.substring(
																					11,
																					16));
																	segments.setArrDate(segments
																			.getArrival()
																			.substring(
																					0,
																					10));
																}
																if (flightDetails
																		.getDepartureTime() != null
																		&& !flightDetails
																		.getDepartureTime()
																		.equalsIgnoreCase(
																				"")) {
																	segments.setDepart(flightDetails
																			.getDepartureTime());
																	segments.setDepTime(segments
																			.getDepart()
																			.substring(
																					11,
																					16));
																	segments.setDepDate(segments
																			.getDepart()
																			.substring(
																					0,
																					10));
																}

																if (typeBaseAirSegment
																		.getArrivalTime() != null
																		&& !typeBaseAirSegment
																		.getArrivalTime()
																		.equalsIgnoreCase(
																				"")) {
																	segments.setArrival(typeBaseAirSegment
																			.getArrivalTime());
																	segments.setArrTime(segments
																			.getArrival()
																			.substring(
																					11,
																					16));
																	segments.setArrDate(segments
																			.getArrival()
																			.substring(
																					0,
																					10));
																}
																if (typeBaseAirSegment
																		.getDepartureTime() != null
																		&& !typeBaseAirSegment
																		.getDepartureTime()
																		.equalsIgnoreCase(
																				"")) {
																	segments.setDepart(typeBaseAirSegment
																			.getDepartureTime());
																	segments.setDepTime(segments
																			.getDepart()
																			.substring(
																					11,
																					16));
																	segments.setDepDate(segments
																			.getDepart()
																			.substring(
																					0,
																					10));
																}

																Flight flight = new Flight();
																flight.setDestTerminal(flightDetails
																		.getDestinationTerminal());
																flight.setOriTerminal(flightDetails
																		.getOriginTerminal());
																flight.setEquipment(flightDetails
																		.getEquipment());
																flight.setNumber(typeBaseAirSegment
																		.getFlightNumber());
																segments.setFlight(flight);
																Carrier carrier = new Carrier();
																carrier.setCode(typeBaseAirSegment
																		.getCarrier());
																carrier.setName(AirlineNameMap
																		.get(typeBaseAirSegment
																				.getCarrier()));
																segments.setCarrier(carrier);
																Cabin cabin = new Cabin();
																cabin.setName(bookingInfo
																		.getCabinClass());
																cabin.setCode(bookingInfo
																		.getBookingCode());
																segments.setCabin(cabin);
																segments.setFareInfoRef(bookingInfo
																		.getFareInfoRef());
																segmentsList
																.add(segments);
																String fareBasisCode="ALL";
																if (fareinfo
																		.getOrigin() != null) {
																	fareBasisCode=fareinfo.getFareBasis();

																	farerule.setAirlineCode(typeBaseAirSegment
																			.getCarrier());
																	farerule.setArrCode(fareinfo
																			.getDestination());
																	farerule.setBasisCode(fareinfo
																			.getFareBasis());
																	farerule.setDepCode(fareinfo
																			.getOrigin());
																	farerule.setDepDate(fareinfo
																			.getEffectiveDate());
																	if (fareinfo
																			.getFareRuleKey() != null) {
																		farerule.setFareInfoRef(fareinfo
																				.getFareRuleKey()
																				.getFareInfoRef());
																		farerule.setFareProviderCode(fareinfo
																				.getFareRuleKey()
																				.getProviderCode());
																		farerule.setFareValue(fareinfo
																				.getFareRuleKey()
																				.getValue());
																	}
																	if (fareinfo
																			.getBaggageAllowance() != null) {
																		farerule.setBagAllowanceUnit(fareinfo
																				.getBaggageAllowance()
																				.getMaxWeight()
																				.getUnit());
																		farerule.setBagAllowanceValue(fareinfo
																				.getBaggageAllowance()
																				.getMaxWeight()
																				.getValue());
																	}
																	FareRuleList
																	.add(farerule);
																	farerules
																	.setTravelerType(flightsearch
																			.getTripType());
																	farerules
																	.setFareRule(FareRuleList);
																	fareRulesList
																	.add(farerules);																	
																}															
																if(i == 0){																
																	airlineCode=typeBaseAirSegment.getCarrier();			
																}
																i++;
															}
														}
													}
													fareFlightSegment
													.setBasePriceWithoutMarkup(String
															.valueOf(new BigDecimal(
																	fareFlightSegment
																	.getBasePriceWithoutMarkup())
																	.multiply(
																			flightsearch
																			.getBaseToBookingExchangeRate())
																	.setScale(
																			2,
																			BigDecimal.ROUND_UP)));
													fareFlightSegment
													.setTotalPriceWithoutMarkup(String
															.valueOf(new BigDecimal(
																	fareFlightSegment
																	.getTotalPriceWithoutMarkup())
																	.multiply(
																			flightsearch
																			.getBaseToBookingExchangeRate())
																	.setScale(
																			2,
																			BigDecimal.ROUND_UP)));
													fareFlightSegment
													.setTaxesWithoutMarkup(String
															.valueOf(new BigDecimal(
																	fareFlightSegment
																	.getTaxesWithoutMarkup())
																	.multiply(
																			flightsearch
																			.getBaseToBookingExchangeRate())
																	.setScale(
																			2,
																			BigDecimal.ROUND_UP)));
													fareFlightSegment
													.setBasePrice(String
															.valueOf(new BigDecimal(
																	fareFlightSegment
																	.getBasePrice())
																	.multiply(
																			flightsearch
																			.getBaseToBookingExchangeRate())
																	.setScale(
																			2,
																			BigDecimal.ROUND_UP)));
													fareFlightSegment
													.setTotalPrice(String
															.valueOf(new BigDecimal(
																	fareFlightSegment
																	.getTotalPrice())
																	.multiply(
																			flightsearch
																			.getBaseToBookingExchangeRate())
																	.setScale(
																			2,
																			BigDecimal.ROUND_UP)));
													fareFlightSegment
													.setTaxes(String
															.valueOf(new BigDecimal(
																	fareFlightSegment
																	.getTaxes())
																	.multiply(
																			flightsearch
																			.getBaseToBookingExchangeRate())
																	.setScale(
																			2,
																			BigDecimal.ROUND_UP)));
													flightSegments
													.setSegments(segmentsList);
													fareFlightSegment
													.setFareRules(fareRulesList);
												}
												flightSegmentsList
												.add(flightSegments);
												uapiAirSegmentListMap.put(
														flightindex,
														uapiAirSegments);
												flightSegmentsGroup2
												.setFlightSegments(flightSegmentsList);
												flightSegmentsGroups
												.add(flightSegmentsGroup2);
												fareFlightSegment
												.setFlightSegmentsGroups(flightSegmentsGroups);
												//adde on 7/3/1026	
												String fareBasisCode="ALL";
												if(fareBasisCodeSet.size()==1){
													fareBasisCode=fareBasisCodeSet.iterator().next();
												}
												UmarkUpServiceCall.getMarkupValues(flightsearch,
														markupMap,airlineCode,fareFlightSegment,fareBasisCode);
												fareFlightSegment.setFareBasisCode(fareBasisCode);											
												fareFlightSegmentMap.put(
														flightindex1,
														fareFlightSegment);
												fareFlightSegmentMap.put(
														flightindex,
														fareFlightSegment);
												fareFlightSegments
												.add(fareFlightSegment);
											}										
										}
									}
								}

							}

						}
					}
				}
			}
			uapiSearchFlightKeyMap
			.setUapiAirSegmentListMap(uapiAirSegmentListMap);
			uapiSearchFlightKeyMap.setFlightsearch(flightsearch);
			uapiSearchFlightKeyMap.setFlightMarkUpConfiglistMap(markupMap);
			uapiSearchFlightKeyMap.setExchangeRate(curValue);
			uapiSearchFlightKeyMap
			.setFareFlightSegmentMap(fareFlightSegmentMap);			
		}
		searchFlightResponse.setFareFlightSegment(fareFlightSegments);
		searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap);
		return searchFlightResponse;
	}
}