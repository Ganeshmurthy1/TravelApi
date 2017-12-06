/**
@Author ilyas
12-oct-2015 
GetFlightAvailabilityResponseParser.java
 */
/**
 * 
 */
package com.tayyarah.flight.util.api.bluestar;

import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.tayyarah.apiconfig.model.BluestarConfig;
import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.util.APIProviderCommonConstant;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.BluestarSearchData;
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
import com.tayyarah.flight.util.FlightWebServiceEndPointValidator;
import com.tayyarah.flight.util.api.travelport.UmarkUpServiceCall;
import com.travelport.api_v33.AirResponse.FareInfo;
import com.travelport.api_v33.AirResponse.TypeBaseAirSegment;

public class BluestarGetFlightAvailabilityResponseParser {
	static Logger logger = Logger.getLogger(BluestarGetFlightAvailabilityResponseParser.class);
	protected static LinkedHashMap<String, TypeBaseAirSegment> airSegMap;
	protected static Map<String, FareInfo> fareInfolMap;

	public static SearchFlightResponse parseResponseRoundTrip(BluestarSearchData bluestarSearchData,
			Map<String, List<FlightMarkUpConfig>> markupMap,
			Flightsearch flightsearch, Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> airportMapList, MoneyExchangeDao moneydao) throws Exception {
		logger.debug("--------- Start---------");
		SearchFlightResponse searchFlightResponse = new SearchFlightResponse();
		try {

			Map<String, String[]> flightdetailMap = bluestarSearchData.getFlightDetailMap();
			Map<String, String[]> faredetailMap= bluestarSearchData.getFareDetailMap();
			TreeSet<String> uniqPriceSet = bluestarSearchData.getUniquePriceSet();
			List<String[]> flightdetailinArray = bluestarSearchData.getFlightDetailArray();
			Map<String, Integer> flightDetailsHeaderMap = bluestarSearchData.getFlightDetailsHeaderMap();

			String apiCurrency = BluestarConfig.DEFAULT_CURRENCY;
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

			Map<String, Double> currencyrate=null; 
			try {			
				currencyrate=  moneydao.getCurrencyRate(flightsearch.getCurrency(), apiCurrency);
			} catch (Exception e) {  
				e.printStackTrace();  
			}  
			Double currencyValue=currencyrate.get("value");
			BigDecimal curValue=new BigDecimal(currencyValue);
			Map<String, Double> currencyrate1=null; 
			try {				
				currencyrate1=  moneydao.getCurrencyRate(flightsearch.getBaseCurrency(), apiCurrency);
			} catch (Exception e) {  
				logger.error(e);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,	FlightErrorMessages.NO_FLIGHT);
			}  

			Double currencyValue1=   currencyrate1.get("value");
			BigDecimal apiToBaseExchangeRate=new BigDecimal(currencyValue1);
			List<FareFlightSegment> fareFlightSegments = new ArrayList<FareFlightSegment>();
			String flightindexForfarerules = (new UID()).toString();
			String flightindexForSegments = "";
			FareFlightSegment fareFlightSegment = null;
			List<FlightSegmentsGroup> flightSegmentsGroups =  null;
			String FlightSegmentsGroupId = new UID().toString();
			List<FareRules> fareRulesList = null;
			FlightSegmentsGroup flightSegmentsGroup = null;
			FlightSegmentsGroup flightSegmentsGroupReturn = null;
			List<FlightSegments> FlightSegmentsList = null;
			FareRules farerules =null;
			List<Segments> segmentsList = null;
			List<Segments> segmentsListreturn = null;
			List<FareRule> FareRuleList = null;
			FlightSegments flightSegments = null;
			String srid = "";			

			for (int i = 0; i < flightdetailinArray.size(); i++) {
				try{
					String[] flightdetail = flightdetailinArray.get(i);				
					String totalAmount=flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)];				
					if (!totalAmount.equals("0")) {	

						fareFlightSegment = new FareFlightSegment();
						fareRulesList = new ArrayList<FareRules>();
						flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
						fareFlightSegment.setBookingCurrency(flightsearch.getCurrency());
						fareFlightSegment.setExchangeRate(curValue);
						fareFlightSegment.setId(new UID().toString());

						////with base currency//////////////
						fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(totalAmount).multiply(apiToBaseExchangeRate)));
						fareFlightSegment.setApiCurrency(apiCurrency);
						fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
						fareFlightSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);
						fareFlightSegment.setApi_totalPriceWithoutMarkup(totalAmount);

						BigDecimal baseprice = new BigDecimal(totalAmount).subtract(new BigDecimal(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)]));
						fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(baseprice.multiply(apiToBaseExchangeRate)));
						fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)]).multiply(apiToBaseExchangeRate)));
						fareFlightSegment.setCurrency(apiCurrency);
						fareFlightSegment.setApi_basePriceWithoutMarkup(String.valueOf(baseprice));
						fareFlightSegment.setApi_taxesWithoutMarkup(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)]);

						//*****Adding markup*********

						UmarkUpServiceCall.getMarkupValues(flightsearch,markupMap,
								flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)],
								fareFlightSegment,"ALL");

						fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
						fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
						fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
						fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
						fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
						fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));

					}

					if(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)].endsWith("O")){
						if(!srid.equals(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)])){					

							flightSegmentsGroup = new FlightSegmentsGroup();
							flightSegments = new FlightSegments();
							FlightSegmentsList = new ArrayList<FlightSegments>();
							flightindexForSegments = "BSO"+ (new UID()).toString();
							flightindexForfarerules=flightindexForSegments; 
							FlightSegmentsGroupId=flightindexForSegments;
							farerules = new FareRules();
							segmentsList = new ArrayList<Segments>();
							FareRuleList = new ArrayList<FareRule>();
						}

						Segments segments = new Segments();
						// setting Stops
						if (flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.Stops)] != null	&& !flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.Stops)].equalsIgnoreCase("")) {
							segments.setStops(Integer.parseInt(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.Stops)]));
						} else {
							segments.setStops(0);
						}
						segments.setDuration(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FlightTime)]);
						segments.setOri(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]);
						segments.setDest(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]);
						segments.setOriAirportName(airportNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]));
						segments.setDestAirportName(airportNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]));
						segments.setOriName(cityNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]));
						segments.setDestName(cityNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]));
						segments.setTrackno(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TrackNo)]);					
						segments.setArrival(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrDate)])+ "T"+ flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrTime)]+ ":00.000+00:00");
						segments.setArrDate(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrDate)]));
						segments.setArrTime(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrTime)]);
						segments.setDepDate(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepDate)]));
						segments.setDepTime(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepTime)]);
						segments.setDepart(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepDate)])+ "T"+ flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepTime)]+ ":00.000+00:00");

						Flight flight = new Flight();
						flight.setDestTerminal(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToTerminal)]);
						flight.setOriTerminal(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromTerminal)]);						
						flight.setNumber(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FlightNo)]);
						segments.setFlight(flight);

						Carrier carrier = new Carrier();
						carrier.setCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)]);
						carrier.setName(AirlineNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)]));
						segments.setCarrier(carrier);

						Cabin cabin = new Cabin();
						cabin.setName(ClassMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.MainClass)]));
						cabin.setCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FlightClass)]);
						segments.setCabin(cabin);

						segments.setFareInfoRef(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]);
						segmentsList.add(segments);

						if ((i != flightdetailinArray.size() - 1)
								&& !flightdetailinArray.get(i + 1)[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]
										.equals(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]))
						{
							flightSegments.setSegments(segmentsList);
							flightSegments.setFlightIndex(flightindexForSegments);
							flightSegments.setApiProvider(APIProviderCommonConstant.BLUESTAR);							
							flightSegments.setBookingCurrency(flightsearch.getCurrency());
							flightSegments.setExchangeRate(curValue);						
							FlightSegmentsList.add(flightSegments);
							flightSegmentstMap.put(flightindexForSegments,flightSegments);
							fareFlightSegmentMap.put(FlightSegmentsGroupId,fareFlightSegment);
							flightSegmentsGroup.setFlightSegments(FlightSegmentsList);
							flightSegmentsGroups.add(flightSegmentsGroup);

							FareRule farerule = new FareRule();
							farerule.setAirlineCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)]);
							farerule.setArrCode(flightsearch.getDestination());
							farerule.setDepCode(flightsearch.getOrigin());
							farerule.setBasisCode(APIProviderCommonConstant.NO_DATA);
							farerule.setDepDate(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepDate)])+ "T"+ flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepTime)]+ ":00.000+00:00");
							farerule.setFareInfoRef(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]);
							farerule.setFareProviderCode("BS"+flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TrackNo)]);
							farerule.setFareValue(APIProviderCommonConstant.NO_DATA);
							FareRuleList.add(farerule);

							farerules.setTravelerType(flightsearch.getTripType());
							farerules.setFareRule(FareRuleList);
							fareRulesList.add(farerules);
							fareRulesMap.put(flightindexForfarerules, farerules);							
							srid = "";
						}else{
							srid=flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)];
						}
					}
					else
						if(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)].endsWith("R")){
							if(!srid.equals(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)])){
								flightSegmentsGroupReturn = new FlightSegmentsGroup();
								flightSegments = new FlightSegments();
								FlightSegmentsList = new ArrayList<FlightSegments>();
								flightindexForSegments = "BSR"+ (new UID()).toString();
								flightindexForfarerules=flightindexForSegments; 
								FlightSegmentsGroupId=flightindexForSegments;
								farerules = new FareRules();
								segmentsListreturn = new ArrayList<Segments>();
								FareRuleList = new ArrayList<FareRule>();
							}
							Segments segments = new Segments();
							// setting Stops
							if (flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.Stops)] != null
									&& !flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.Stops)]
											.equalsIgnoreCase("")) {
								segments.setStops(Integer.parseInt(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.Stops)]));
							} else {
								segments.setStops(0);
							}
							segments.setDuration(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FlightTime)]);
							segments.setOri(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]);
							segments.setDest(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]);
							segments.setOriAirportName(airportNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]));
							segments.setDestAirportName(airportNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]));
							segments.setOriName(cityNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]));
							segments.setDestName(cityNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]));
							segments.setTrackno(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TrackNo)]);

							segments.setArrival(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrDate)])+ "T"+ flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrTime)]+ ":00.000+00:00");
							segments.setArrDate(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrDate)]));
							segments.setArrTime(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrTime)]);
							segments.setDepDate(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepDate)]));
							segments.setDepTime(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepTime)]);
							segments.setDepart(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepDate)])+ "T"+ flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepTime)]+ ":00.000+00:00");

							Flight flight = new Flight();
							flight.setDestTerminal(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToTerminal)]);
							flight.setOriTerminal(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromTerminal)]);							
							flight.setNumber(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FlightNo)]);
							segments.setFlight(flight);

							Carrier carrier = new Carrier();
							carrier.setCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)]);
							carrier.setName(AirlineNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)]));
							segments.setCarrier(carrier);

							Cabin cabin = new Cabin();
							cabin.setName(ClassMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.MainClass)]));
							cabin.setCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FlightClass)]);
							segments.setCabin(cabin);

							segments.setFareInfoRef(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]);
							segmentsListreturn.add(segments);

							if ((i != flightdetailinArray.size() - 1)
									&& !flightdetailinArray.get(i + 1)[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]
											.equals(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)])) {

								flightSegments.setSegments(segmentsListreturn);
								flightSegments.setFlightIndex(flightindexForSegments);
								flightSegments.setApiProvider(APIProviderCommonConstant.BLUESTAR);								
								flightSegments.setBookingCurrency(flightsearch.getCurrency());
								flightSegments.setExchangeRate(curValue);			


								FlightSegmentsList.add(flightSegments);
								flightSegmentstMap.put(flightindexForSegments,flightSegments);
								fareFlightSegmentMap.put(FlightSegmentsGroupId,fareFlightSegment);
								flightSegmentsGroupReturn.setFlightSegments(FlightSegmentsList);
								flightSegmentsGroups.add(flightSegmentsGroupReturn);

								FareRule farerule = new FareRule();
								farerule.setAirlineCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)]);
								farerule.setArrCode(flightsearch.getOrigin());
								farerule.setDepCode(flightsearch.getDestination());
								farerule.setBasisCode(APIProviderCommonConstant.NO_DATA);
								farerule.setDepDate(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepDate)])+ "T"+ flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepTime)]+ ":00.000+00:00");

								farerule.setFareInfoRef(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]);
								farerule.setFareProviderCode("BS"+flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TrackNo)]);
								farerule.setFareValue(APIProviderCommonConstant.NO_DATA);
								FareRuleList.add(farerule);

								farerules.setTravelerType(flightsearch.getTripType());
								farerules.setFareRule(FareRuleList);
								fareRulesList.add(farerules);
								fareRulesMap.put(flightindexForfarerules, farerules);
								srid = "";
							}else{
								srid=flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)];
							}
						}
					if ((i != flightdetailinArray.size() - 1)&& !flightdetailinArray.get(i + 1)[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)].equals("0"))
					{						
						fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);
						fareFlightSegment.setFareRules(fareRulesList);
						fareFlightSegments.add(fareFlightSegment);
					} else if (i == flightdetailinArray.size() - 1) {					

						flightSegments.setSegments(segmentsListreturn);
						flightSegments.setFlightIndex(flightindexForSegments);
						flightSegments.setApiProvider(APIProviderCommonConstant.BLUESTAR);						
						flightSegments.setBookingCurrency(flightsearch.getCurrency());
						flightSegments.setExchangeRate(curValue);
						FlightSegmentsList.add(flightSegments);
						flightSegmentstMap.put(flightindexForSegments,flightSegments);
						fareFlightSegmentMap.put(FlightSegmentsGroupId,fareFlightSegment);
						flightSegmentsGroupReturn.setFlightSegments(FlightSegmentsList);
						flightSegmentsGroups.add(flightSegmentsGroupReturn);

						FareRule farerule = new FareRule();
						farerule.setAirlineCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)]);
						farerule.setArrCode(flightsearch.getOrigin());
						farerule.setDepCode(flightsearch.getDestination());
						farerule.setBasisCode(APIProviderCommonConstant.NO_DATA);
						farerule.setDepDate(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepDate)])+ "T"+ flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepTime)]+ ":00.000+00:00");
						farerule.setFareInfoRef(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]);
						farerule.setFareProviderCode("BS"+flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TrackNo)]);
						farerule.setFareValue(APIProviderCommonConstant.NO_DATA);
						FareRuleList.add(farerule);

						farerules.setTravelerType(flightsearch.getTripType());
						farerules.setFareRule(FareRuleList);
						fareRulesList.add(farerules);
						fareRulesMap.put(flightindexForfarerules, farerules);
						srid = "";

						fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);
						fareFlightSegment.setFareRules(fareRulesList);
						fareFlightSegments.add(fareFlightSegment);										
					}			

				}catch(Exception e){
					e.printStackTrace();
				}
			}
			searchFlightResponse.setFareFlightSegment(fareFlightSegments);
			uapiSearchFlightKeyMap.setFareRulesMap(fareRulesMap);
			uapiSearchFlightKeyMap.setFareFlightSegmentMap(fareFlightSegmentMap);
			uapiSearchFlightKeyMap.setFlightSegmentstMap(flightSegmentstMap);
			uapiSearchFlightKeyMap.setFaredetailMap(faredetailMap);
			uapiSearchFlightKeyMap.setFlightsearch(flightsearch);
			uapiSearchFlightKeyMap.setFlightMarkUpConfiglistMap(markupMap);
			uapiSearchFlightKeyMap.setExchangeRate(curValue);
			searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap);
		}catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return searchFlightResponse;
	}

	public static SearchFlightResponse parseResponseOneway(BluestarSearchData bluestarSearchData,
			Map<String, List<FlightMarkUpConfig>> markupMap,
			Flightsearch flightsearch, Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> airportMapList,
			MoneyExchangeDao moneydao) throws Exception {
		logger.debug("--------- Start---------");
		SearchFlightResponse searchFlightResponse = new SearchFlightResponse();
		try {
			Map<String, String[]> flightdetailMap = bluestarSearchData.getFlightDetailMap();
			Map<String, String[]> faredetailMap= bluestarSearchData.getFareDetailMap();
			TreeSet<String> uniqPriceSet = bluestarSearchData.getUniquePriceSet();
			List<String[]> flightdetailinArray = bluestarSearchData.getFlightDetailArray();
			Map<String, Integer> flightDetailsHeaderMap = bluestarSearchData.getFlightDetailsHeaderMap();

			String apiCurrency = BluestarConfig.DEFAULT_CURRENCY;
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


			Map<String, Double> currencyrate=null; 
			try {				
				currencyrate=  moneydao.getCurrencyRate(flightsearch.getCurrency(), apiCurrency);
			} catch (Exception e) {  
				e.printStackTrace();  
			}  
			Double currencyValue=currencyrate.get("value");
			BigDecimal curValue=new BigDecimal(currencyValue);
			Map<String, Double> currencyrate1=null; 
			try {				
				currencyrate1=  moneydao.getCurrencyRate(flightsearch.getBaseCurrency(), apiCurrency);

			} catch (Exception e) {  
				logger.error(e);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,
						FlightErrorMessages.NO_FLIGHT);
			}  

			Double currencyValue1 = currencyrate1.get("value");
			BigDecimal apiToBaseExchangeRate=new BigDecimal(currencyValue1);
			List<FareFlightSegment> fareFlightSegments = new ArrayList<FareFlightSegment>();
			String flightindexForfarerules = (new UID()).toString();
			String tempFlightindexForfarerules = "";
			String flightindexForSegments = "";

			for (String totalAmount : uniqPriceSet) {

				FareFlightSegment fareFlightSegment = new FareFlightSegment();
				fareFlightSegment.setBookingCurrency(flightsearch.getCurrency());
				fareFlightSegment.setExchangeRate(curValue);
				fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(totalAmount).multiply(apiToBaseExchangeRate)));
				fareFlightSegment.setApi_totalPriceWithoutMarkup(totalAmount);			
				fareFlightSegment.setApiCurrency(apiCurrency);
				fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
				fareFlightSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);
				fareFlightSegment.setId(new UID().toString());
				List<FlightSegmentsGroup> flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
				FlightSegmentsGroup flightSegmentsGroup = new FlightSegmentsGroup();
				String srid = "";
				List<FlightSegments> FlightSegmentsList = new ArrayList<FlightSegments>();
				String FlightSegmentsGroupId = new UID().toString();
				List<FareRules> fareRulesList = new ArrayList<FareRules>();				
				List<Segments> TempsegmentsList = new ArrayList<Segments>();

				int y = 0;
				int z = 0;
				for (int i = 0; i < flightdetailinArray.size(); i++) {

					String[] flightdetail = flightdetailinArray.get(i);				
					if (flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)].equals(totalAmount)
							|| srid.equals(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)])) {

						FlightSegments flightSegments = new FlightSegments();
						FareRules farerules = new FareRules();
						List<Segments> segmentsList = new ArrayList<Segments>();
						List<FareRule> FareRuleList = new ArrayList<FareRule>();
						if (z == 0) {

							BigDecimal baseprice = new BigDecimal(totalAmount).subtract(new BigDecimal(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)]));
							fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(baseprice.multiply(apiToBaseExchangeRate)));
							fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)]).multiply(apiToBaseExchangeRate)));
							fareFlightSegment.setCurrency(apiCurrency);
							fareFlightSegment.setApi_basePriceWithoutMarkup(String.valueOf(baseprice));
							fareFlightSegment.setApi_taxesWithoutMarkup(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)]);
						}

						if (y == 0) {
							UmarkUpServiceCall.getMarkupValues(flightsearch,markupMap,
									flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)],
									fareFlightSegment,"ALL");

							fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));
						}
						y++;

						if (srid.equals(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)])) {						
							segmentsList = TempsegmentsList;						
						}
						if (!srid.equals(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)])) {
							tempFlightindexForfarerules = "";
						}

						Segments segments = new Segments();
						if (flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.Stops)] != null	&& 
								!flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.Stops)].equalsIgnoreCase(""))
							segments.setStops(Integer.parseInt(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.Stops)]));// setting
						else
							segments.setStops(0);
						
						segments.setDuration(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FlightTime)]);
						segments.setOri(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]);
						segments.setDest(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]);
						segments.setOriAirportName(airportNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]));
						segments.setDestAirportName(airportNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]));
						segments.setOriName(cityNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]));
						segments.setDestName(cityNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]));
						segments.setTrackno(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TrackNo)]);						
						segments.setArrival(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrDate)])+ "T"+ flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrTime)]+ ":00.000+00:00");
						segments.setDepart(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepDate)])+ "T"+ flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepTime)]	+ ":00.000+00:00");

						segments.setArrDate(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrDate)]));
						segments.setArrTime(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrTime)]);
						segments.setDepDate(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepDate)]));
						segments.setDepTime(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepTime)]);

						Flight flight = new Flight();
						flight.setDestTerminal(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToTerminal)]);
						flight.setOriTerminal(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromTerminal)]);					
						flight.setNumber(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FlightNo)]);
						segments.setFlight(flight);
						
						Carrier carrier = new Carrier();
						carrier.setCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)]);
						carrier.setName(AirlineNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)]));
						segments.setCarrier(carrier);
						
						Cabin cabin = new Cabin();
						cabin.setName(ClassMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.MainClass)]));
						cabin.setCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FlightClass)]);
						segments.setCabin(cabin);
						
						segments.setFareInfoRef(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]);
						segmentsList.add(segments);
						TempsegmentsList = segmentsList;
						
						if (tempFlightindexForfarerules.equals("")) {
							flightindexForSegments = "BS"+ (new UID()).toString();
						}

						FlightSegmentsGroupId = flightindexForSegments;
						flightindexForfarerules = flightindexForSegments;

						if ((i != flightdetailinArray.size() - 1)
								&& !flightdetailinArray.get(i + 1)[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]
										.equals(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)])) {
							
							flightSegments.setFlightIndex(flightindexForSegments);
							flightSegments.setApiProvider(APIProviderCommonConstant.BLUESTAR);							
							flightSegments.setBookingCurrency(flightsearch.getCurrency());
							flightSegments.setExchangeRate(curValue);						
							flightSegments.setSegments(segmentsList);
							FlightSegmentsList.add(flightSegments);
							flightSegmentstMap.put(flightindexForSegments,flightSegments);
							fareFlightSegmentMap.put(FlightSegmentsGroupId,fareFlightSegment);
						}
						if ((i == flightdetailinArray.size() - 1)) {
							
							flightSegments.setFlightIndex(flightindexForSegments);
							flightSegments.setApiProvider(APIProviderCommonConstant.BLUESTAR);							
							flightSegments.setBookingCurrency(flightsearch.getCurrency());
							flightSegments.setExchangeRate(curValue);						
							flightSegments.setSegments(segmentsList);
							FlightSegmentsList.add(flightSegments);
							flightSegmentstMap.put(flightindexForSegments,flightSegments);
							fareFlightSegmentMap.put(FlightSegmentsGroupId,fareFlightSegment);
						}

						if (!srid.equals(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)])) {
							FareRule farerule = new FareRule();
							farerule.setAirlineCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)]);
							farerule.setArrCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]);
							farerule.setBasisCode(APIProviderCommonConstant.NO_DATA);
							farerule.setDepCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]);
							farerule.setDepDate(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepDate)])+ "T"+ flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepTime)]+ ":00.000+00:00");
							farerule.setFareInfoRef(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]);
							farerule.setFareProviderCode("BS"+flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TrackNo)]);
							farerule.setFareValue(APIProviderCommonConstant.NO_DATA);
							FareRuleList.add(farerule);

							farerules.setTravelerType(flightsearch.getTripType());
							farerules.setFareRule(FareRuleList);
							fareRulesList.add(farerules);
							fareRulesMap.put(flightindexForfarerules, farerules);
							tempFlightindexForfarerules = flightindexForfarerules;
						}
						srid = flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)];
						z++;
					}					
				}
				flightSegmentsGroup.setFlightSegments(FlightSegmentsList);
				flightSegmentsGroups.add(flightSegmentsGroup);
				fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);
				fareFlightSegment.setFareRules(fareRulesList);
				fareFlightSegments.add(fareFlightSegment);
			}

			searchFlightResponse.setFareFlightSegment(fareFlightSegments);
			uapiSearchFlightKeyMap.setFareRulesMap(fareRulesMap);
			uapiSearchFlightKeyMap.setFareFlightSegmentMap(fareFlightSegmentMap);
			uapiSearchFlightKeyMap.setFlightSegmentstMap(flightSegmentstMap);
			uapiSearchFlightKeyMap.setFaredetailMap(faredetailMap);
			uapiSearchFlightKeyMap.setFlightsearch(flightsearch);
			uapiSearchFlightKeyMap.setFlightMarkUpConfiglistMap(markupMap);
			uapiSearchFlightKeyMap.setExchangeRate(curValue);
			searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return searchFlightResponse;
	}


	public static SearchFlightResponse parseResponseOnewayDomestic(BluestarSearchData bluestarSearchData,
			Map<String, List<FlightMarkUpConfig>> markupMap,
			Flightsearch flightsearch, Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> airportMapList,
			MoneyExchangeDao moneydao) throws Exception {
		logger.debug("--------- Start---------");
		SearchFlightResponse searchFlightResponse = new SearchFlightResponse();
		try {
			Map<String, String[]> flightdetailMap = bluestarSearchData.getFlightDetailMap();
			Map<String, String[]> faredetailMap= bluestarSearchData.getFareDetailMap();
			TreeSet<String> uniqPriceSet = bluestarSearchData.getUniquePriceSet();
			List<String[]> flightdetailinArray = bluestarSearchData.getFlightDetailArray();
			Map<String, Integer> flightDetailsHeaderMap = bluestarSearchData.getFlightDetailsHeaderMap();
			Map<String, Integer> flightFareDetailsHeaderMap = bluestarSearchData.getFlightFareDetailsHeaderMap();

			String apiCurrency = BluestarConfig.DEFAULT_CURRENCY;
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


			Map<String, Double> currencyrate=null; 
			try { 				
				currencyrate=  moneydao.getCurrencyRate(flightsearch.getCurrency(), apiCurrency);
			} catch (Exception e) {  
				e.printStackTrace();  
			}  
			Double currencyValue=currencyrate.get("value");
			BigDecimal curValue=new BigDecimal(currencyValue);
			Map<String, Double> currencyrate1=null; 
			try { 				
				currencyrate1=  moneydao.getCurrencyRate(flightsearch.getBaseCurrency(), apiCurrency);
			} catch (Exception e) {  
				logger.error(e);
				throw new FlightException(ErrorCodeCustomerEnum.HibernateException,
						FlightErrorMessages.NO_FLIGHT);
			}  

			Double currencyValue1 = currencyrate1.get("value");
			BigDecimal apiToBaseExchangeRate=new BigDecimal(currencyValue1);
			List<FareFlightSegment> fareFlightSegments = new ArrayList<FareFlightSegment>();
			String flightindexForfarerules = (new UID()).toString();
			String tempFlightindexForfarerules = "";
			String flightindexForSegments = "";
			TreeSet<String> uniqueSnoSet = new TreeSet<String>();
			for (int i = 0; i < flightdetailinArray.size(); i++) {
				String[] flightdetail = flightdetailinArray.get(i);
				String srNo = flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)];
				uniqueSnoSet.add(srNo);
			}		

			for (String SrNo : uniqueSnoSet) {

				FareFlightSegment fareFlightSegment = new FareFlightSegment();
				fareFlightSegment.setBookingCurrency(flightsearch.getCurrency());
				fareFlightSegment.setExchangeRate(curValue);
				String[] fareDetail =	faredetailMap.get(SrNo);

				fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareDetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)]).multiply(apiToBaseExchangeRate)));
				fareFlightSegment.setApi_totalPriceWithoutMarkup(fareDetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)]);
				fareFlightSegment.setApiCurrency(apiCurrency);
				fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
				fareFlightSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);
				fareFlightSegment.setId(new UID().toString());
				List<FlightSegmentsGroup> flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
				FlightSegmentsGroup flightSegmentsGroup = new FlightSegmentsGroup();

				List<FlightSegments> FlightSegmentsList = new ArrayList<FlightSegments>();
				String FlightSegmentsGroupId = new UID().toString();
				List<FareRules> fareRulesList = new ArrayList<FareRules>();				
				List<Segments> TempsegmentsList = new ArrayList<Segments>();
				String srid = "";
				int y = 0;
				int z = 0;
				for (int i = 0; i < flightdetailinArray.size(); i++) {
					
					String[] flightdetail = flightdetailinArray.get(i);
					if (SrNo.equals(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)])) {	

						FlightSegments flightSegments = new FlightSegments();
						FareRules farerules = new FareRules();
						List<Segments> segmentsList = new ArrayList<Segments>();
						List<FareRule> FareRuleList = new ArrayList<FareRule>();
						if (z == 0) {
							BigDecimal baseprice = new BigDecimal(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)])
									.subtract(new BigDecimal(
											flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)]));

							fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(baseprice.multiply(apiToBaseExchangeRate)));
							fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)]).multiply(apiToBaseExchangeRate)));
							fareFlightSegment.setCurrency(apiCurrency);
							fareFlightSegment.setApi_basePriceWithoutMarkup(String.valueOf(baseprice));
							fareFlightSegment.setApi_taxesWithoutMarkup(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)]);
						}

						if (y == 0) {
							UmarkUpServiceCall
							.getMarkupValues(flightsearch,	markupMap,
									flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)],
									fareFlightSegment,"ALL");

							fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
							fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));
					}
						y++;

						if (srid.equals(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)])) {						
							segmentsList = TempsegmentsList;							
						}
						if (!srid.equals(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)])) {
							tempFlightindexForfarerules = "";
						}

						Segments segments = new Segments();
						if (flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.Stops)] != null	&& !flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.Stops)].equalsIgnoreCase(""))
							segments.setStops(Integer.parseInt(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.Stops)]));
						
						else
							segments.setStops(0);
						
						segments.setDuration(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FlightTime)]);
						segments.setOri(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]);
						segments.setDest(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]);
						segments.setOriAirportName(airportNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]));
						segments.setDestAirportName(airportNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]));
						segments.setOriName(cityNameMap	.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]));
						segments.setDestName(cityNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]));
						segments.setTrackno(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TrackNo)]);					
						segments.setArrival(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrDate)])+ "T"+ flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrTime)]+ ":00.000+00:00");
						segments.setDepart(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepDate)])	+ "T"+ flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepTime)]+ ":00.000+00:00");

						segments.setArrDate(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrDate)]));
						segments.setArrTime(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ArrTime)]);
						segments.setDepDate(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepDate)]));
						segments.setDepTime(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepTime)]);

						Flight flight = new Flight();
						flight.setDestTerminal(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToTerminal)]);
						flight.setOriTerminal(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromTerminal)]);						
						flight.setNumber(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FlightNo)]);
						segments.setFlight(flight);
						
						Carrier carrier = new Carrier();
						carrier.setCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)]);
						carrier.setName(AirlineNameMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)]));
						segments.setCarrier(carrier);
						
						Cabin cabin = new Cabin();
						cabin.setName(ClassMap.get(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.MainClass)]));
						cabin.setCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FlightClass)]);
						segments.setCabin(cabin);
						segments.setFareInfoRef(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]);
						segmentsList.add(segments);
						TempsegmentsList = segmentsList;
						if (tempFlightindexForfarerules.equals("")) {
							flightindexForSegments = "BS"+ (new UID()).toString();
						}

						FlightSegmentsGroupId = flightindexForSegments;
						flightindexForfarerules = flightindexForSegments;

						if ((i != flightdetailinArray.size() - 1)
								&& !flightdetailinArray.get(i + 1)[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]
										.equals(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)])) {
							flightSegments.setFlightIndex(flightindexForSegments);
							flightSegments.setApiProvider(APIProviderCommonConstant.BLUESTAR);					
							flightSegments.setBookingCurrency(flightsearch.getCurrency());
							flightSegments.setExchangeRate(curValue);				
							flightSegments.setSegments(segmentsList);
							FlightSegmentsList.add(flightSegments);
							flightSegmentstMap.put(flightindexForSegments,flightSegments);
							fareFlightSegmentMap.put(FlightSegmentsGroupId,fareFlightSegment);
						}
						if ((i == flightdetailinArray.size() - 1)) {
							flightSegments.setFlightIndex(flightindexForSegments);
							flightSegments.setApiProvider(APIProviderCommonConstant.BLUESTAR);						
							flightSegments.setBookingCurrency(flightsearch.getCurrency());
							flightSegments.setExchangeRate(curValue);							
							flightSegments.setSegments(segmentsList);
							FlightSegmentsList.add(flightSegments);
							flightSegmentstMap.put(flightindexForSegments,flightSegments);
							fareFlightSegmentMap.put(FlightSegmentsGroupId,fareFlightSegment);
						}
						if (!srid.equals(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)])) {
							FareRule farerule = new FareRule();
							farerule.setAirlineCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.AirlineCode)]);
							farerule.setArrCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]);
							farerule.setBasisCode(APIProviderCommonConstant.NO_DATA);
							farerule.setDepCode(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)]);
							farerule.setDepDate(FlightWebServiceEndPointValidator.convertBlueToTravel(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepDate)])+ "T"+ flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.DepTime)]+ ":00.000+00:00");
			             	farerule.setFareInfoRef(flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]);
							farerule.setFareProviderCode("BS"+flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TrackNo)]);
							farerule.setFareValue(APIProviderCommonConstant.NO_DATA);
							FareRuleList.add(farerule);

							farerules.setTravelerType(flightsearch.getTripType());
							farerules.setFareRule(FareRuleList);
							fareRulesList.add(farerules);
							fareRulesMap.put(flightindexForfarerules, farerules);
							tempFlightindexForfarerules = flightindexForfarerules;
						}
						srid = flightdetail[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)];
						z++;
					}
				}
				flightSegmentsGroup.setFlightSegments(FlightSegmentsList);
				flightSegmentsGroups.add(flightSegmentsGroup);
				fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);
				fareFlightSegment.setFareRules(fareRulesList);
				fareFlightSegments.add(fareFlightSegment);
			}

			searchFlightResponse.setFareFlightSegment(fareFlightSegments);
			uapiSearchFlightKeyMap.setFareRulesMap(fareRulesMap);
			uapiSearchFlightKeyMap.setFareFlightSegmentMap(fareFlightSegmentMap);
			uapiSearchFlightKeyMap.setFlightSegmentstMap(flightSegmentstMap);
			uapiSearchFlightKeyMap.setFaredetailMap(faredetailMap);
			uapiSearchFlightKeyMap.setFlightsearch(flightsearch);
			uapiSearchFlightKeyMap.setFlightMarkUpConfiglistMap(markupMap);
			uapiSearchFlightKeyMap.setExchangeRate(curValue);
			
			searchFlightResponse.setUapiSearchFlightKeyMap(uapiSearchFlightKeyMap);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return searchFlightResponse;
	}
}
