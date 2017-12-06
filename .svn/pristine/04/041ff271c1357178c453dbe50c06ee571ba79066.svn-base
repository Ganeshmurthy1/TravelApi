/**
@Author ilyas
31-aug-2015 
AirPriceResponseParser.java
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
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.model.Cabin;
import com.tayyarah.flight.model.Carrier;
import com.tayyarah.flight.model.FareFlightSegment;
import com.tayyarah.flight.model.FareRule;
import com.tayyarah.flight.model.FareRules;
import com.tayyarah.flight.model.Flight;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.FlightSegments;
import com.tayyarah.flight.model.FlightSegmentsGroup;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.MarkupCommissionDetails;
import com.tayyarah.flight.model.PassengerFareBreakUp;
import com.tayyarah.flight.model.Segments;
import com.travelport.api_v33.AirResponse.AirItinerary;
import com.travelport.api_v33.AirResponse.AirPriceResult;
import com.travelport.api_v33.AirResponse.AirPriceRsp;
import com.travelport.api_v33.AirResponse.AirPricingInfo;
import com.travelport.api_v33.AirResponse.AirPricingSolution;
import com.travelport.api_v33.AirResponse.BookingInfo;
import com.travelport.api_v33.AirResponse.FareInfo;
import com.travelport.api_v33.AirResponse.PassengerType;
import com.travelport.api_v33.AirResponse.TypeBaseAirSegment;
import com.travelport.api_v33.AirResponse.TypeTaxInfo;

public class AirPriceResponseParser {	
	static Logger logger = Logger.getLogger(AirPriceResponseParser.class);
	protected static Map<String, TypeBaseAirSegment> airSegMap;
	static List<AirPricingInfo> airPricingInfoList;

	public static FlightPriceResponse parseAirPrice(AirPriceRsp airPriceRsp,
			Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap,
			Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> MapList, Flightsearch flightsearch,
			String transaction_key, FlightTempAirSegmentDAO tempDAO,BigDecimal exchangeRate,BigDecimal apiToBaseExchangeRate,MarkupCommissionDetails markupCommissionDetails) throws Exception {

		Map<String, String> CityNameMap = new HashMap<String, String>();
		Map<String, String> AirportNameMap = new HashMap<String, String>();
		if (MapList.size() > 0) {
			CityNameMap = MapList.get(0);
			AirportNameMap = MapList.get(1);
		}
		int passegers_for_marhup = flightsearch.getAdult()
				+ flightsearch.getKid();
		FlightPriceResponse flightPriceResponse = new FlightPriceResponse();		
		flightPriceResponse.setTransactionKey(transaction_key);
		flightPriceResponse.setFlightsearch(flightsearch);
		AirItinerary aiItinerary = airPriceRsp.getAirItinerary();
		List<TypeBaseAirSegment> typeBaseAirSegmentList = aiItinerary
				.getAirSegment();
		airSegMap = new HashMap<String, TypeBaseAirSegment>();
		for (int m = 0; m < typeBaseAirSegmentList.size(); m++) {		
			airSegMap.put(typeBaseAirSegmentList.get(m).getKey(),
					typeBaseAirSegmentList.get(m));
		}
		flightPriceResponse.setCountry("YET to get");
		List<AirPriceResult> AirPriceResultList = airPriceRsp
				.getAirPriceResult();
		List<FlightSegmentsGroup> flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
		List<FlightSegmentsGroup> newFlightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
		String apiCurrency = AirPriceResultList.get(0).getAirPricingSolution().get(0).getApproximateTotalPrice().substring(0, 3);
		for (int i = 0; i < AirPriceResultList.size(); i++) {
			AirPriceResult airPriceResult = AirPriceResultList.get(i);
			List<AirPricingSolution> airPricingSolutionList = airPriceResult
					.getAirPricingSolution();
			FareFlightSegment fareFlightSegment = new FareFlightSegment();
			fareFlightSegment.setId(new UID().toString());
			if (airPricingSolutionList.size()>0) {
				AirPricingSolution airPricingSolution = airPricingSolutionList
						.get(airPricingSolutionList.size()-1);
				FlightSegmentsGroup flightSegmentsGroup = new FlightSegmentsGroup();				
				flightPriceResponse.setFareFlightSegment(fareFlightSegment);
				String key = airPricingSolution.getAirSegmentRef().get(0)
						.getKey();
				String airlinecode = airSegMap.get(key).getCarrier();
				fareFlightSegment.setCurrency(apiCurrency);
				fareFlightSegment.setBookingCurrency(flightsearch.getCurrency());
				fareFlightSegment.setExchangeRate(exchangeRate);			
				fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
						airPricingSolution.getApproximateBasePrice(), apiCurrency)).multiply(apiToBaseExchangeRate)));
				fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
						airPricingSolution.getApproximateTotalPrice(), apiCurrency)).multiply(apiToBaseExchangeRate)));
				fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
						airPricingSolution.getApproximateTaxes(), apiCurrency)).multiply(apiToBaseExchangeRate)));
				fareFlightSegment.setApiCurrency(apiCurrency);
				fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
				fareFlightSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);
				fareFlightSegment.setApi_basePriceWithoutMarkup(replaceCurrency(
						airPricingSolution.getApproximateBasePrice(), apiCurrency));
				fareFlightSegment.setApi_totalPriceWithoutMarkup(replaceCurrency(
						airPricingSolution.getApproximateTotalPrice(), apiCurrency));
				fareFlightSegment.setApi_taxesWithoutMarkup(replaceCurrency(
						airPricingSolution.getApproximateTaxes(), apiCurrency));
				List<AirPricingInfo> airPricingInfoList = airPricingSolution
						.getAirPricingInfo();
				List<FlightSegments> flightSegmentsList = new ArrayList<FlightSegments>();
				List<PassengerFareBreakUp> passengerFareBreakUps = new ArrayList<PassengerFareBreakUp>();
				List<FareRules> fareRulesList = new ArrayList<FareRules>();
				FareRules farerules = new FareRules();
				Set<String> fareBasisCodeSet = new HashSet<String>();				
				String fareBasisCode = "ALL";
				int v = 0;
				flightPriceResponse.setGSTonFlights(new BigDecimal("0.00"));
				for (AirPricingInfo airPricingInfo : airPricingInfoList) {
					HashMap<String, FareInfo> FareInfoMap = new HashMap<String, FareInfo>();
					List<FareInfo> fareinfolist = airPricingInfo.getFareInfo();
					airPricingInfo.getLatestTicketingTime();
					List<FareRule> FareRuleList = new ArrayList<FareRule>();

					fareFlightSegment.setLatestTicketingTime(airPricingInfo
							.getLatestTicketingTime());

					for (FareInfo fareinfo : fareinfolist) {

						FareRule farerule = new FareRule();
						farerule.setAirlineCode(airlinecode);
						farerule.setArrCode(fareinfo.getDestination());
						farerule.setBasisCode(fareinfo.getFareBasis());
						farerule.setDepCode(fareinfo.getOrigin());
						farerule.setDepDate(fareinfo.getEffectiveDate());
						fareBasisCodeSet.add(fareinfo.getFareBasis());
						if (fareinfo.getFareRuleKey() != null) {

							farerule.setFareInfoRef(fareinfo.getFareRuleKey()
									.getFareInfoRef());
							farerule.setFareProviderCode(fareinfo
									.getFareRuleKey().getProviderCode());
							farerule.setFareValue(fareinfo.getFareRuleKey()
									.getValue());

						}
						if (fareinfo.getBaggageAllowance() != null) {

							farerule.setBagAllowanceUnit(fareinfo
									.getBaggageAllowance().getMaxWeight()
									.getUnit());
							farerule.setBagAllowanceValue(fareinfo
									.getBaggageAllowance().getMaxWeight()
									.getValue());

						}

						FareRuleList.add(farerule);

						farerules.setTravelerType(flightsearch.getTripType());
						farerules.setFareRule(FareRuleList);

					}
					fareRulesList.add(farerules);
					if (v == 0) {
						List<BookingInfo> bookinginfoList = airPricingInfo
								.getBookingInfo();
						List<Segments> segmentsList = new ArrayList<Segments>();
						FlightSegments flightSegments = new FlightSegments();
						flightSegments.setFlightIndex(new UID().toString());
						flightSegments.setApiProvider("Travelport");
						flightSegments.setBookingCurrency(flightsearch.getCurrency());
						flightSegments.setExchangeRate(exchangeRate);
						int m=0;
						String mainfareinfo="";
						for (BookingInfo bookingInfo : bookinginfoList) {							
							if(!mainfareinfo.equals(bookingInfo.getFareInfoRef())){
								mainfareinfo=bookingInfo.getFareInfoRef();
								m++;
							}						
							TypeBaseAirSegment typeBaseAirSegment = airSegMap
									.get(bookingInfo.getSegmentRef());
							Segments segments = new Segments();
							if (typeBaseAirSegment.getArrivalTime() != null) {							
								segments.setTripNo(m); 
								segments.setGroup(typeBaseAirSegment.getGroup());
								segments.setDuration(String
										.valueOf(typeBaseAirSegment
												.getFlightTime()));
								segments.setOri(typeBaseAirSegment.getOrigin());
								segments.setDest(typeBaseAirSegment
										.getDestination());
								segments.setOriAirportName(AirportNameMap
										.get(typeBaseAirSegment.getOrigin()));
								segments.setDestAirportName(AirportNameMap
										.get(typeBaseAirSegment
												.getDestination()));
								segments.setOriName(CityNameMap
										.get(typeBaseAirSegment.getOrigin()));
								segments.setDestName(CityNameMap
										.get(typeBaseAirSegment
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
								flight.setDestTerminal(typeBaseAirSegment.getFlightDetails().get(0).getDestinationTerminal());//
								flight.setOriTerminal(typeBaseAirSegment.getFlightDetails().get(0).getOriginTerminal());//
								flight.setEquipment(typeBaseAirSegment
										.getEquipment());
								flight.setNumber(typeBaseAirSegment
										.getFlightNumber());
								segments.setFlight(flight);
								Carrier carrier = new Carrier();
								carrier.setCode(typeBaseAirSegment.getCarrier());
								carrier.setName(AirlineNameMap
										.get(typeBaseAirSegment.getCarrier()));
								segments.setCarrier(carrier);
								Cabin cabin = new Cabin();
								cabin.setName(bookingInfo.getCabinClass());
								cabin.setCode(bookingInfo.getBookingCode());
								segments.setCabin(cabin);
								segments.setFareInfoRef(bookingInfo.getFareInfoRef());//
								segmentsList.add(segments);
							}
						}
						flightSegments.setSegments(segmentsList);
						flightSegmentsList.add(flightSegments);
						flightSegmentsGroup
						.setFlightSegments(flightSegmentsList);
					}
					v++;
					if(fareBasisCodeSet.size()==1){
						fareBasisCode=fareBasisCodeSet.iterator().next();
					}		

					for (PassengerType passengerType : airPricingInfo
							.getPassengerType()) {
						AddPassengerFareBreakUp(airPricingInfo,
								FlightMarkUpConfiglistMap, passengerFareBreakUps,
								flightPriceResponse,
								airlinecode,
								passengerType.getCode(), passegers_for_marhup,fareFlightSegment,flightsearch.getBaseToBookingExchangeRate(),fareBasisCode,flightsearch.getFlightType());
					}
				}			
				try {
					UmarkUpServiceCall.getMarkupValuesFPR(flightPriceResponse,
							FlightMarkUpConfiglistMap,
							airlinecode, fareFlightSegment,fareBasisCode,null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fareFlightSegment.setFareBasisCode(fareBasisCode);
				flightPriceResponse
				.setPassengerFareBreakUps(passengerFareBreakUps);
				flightSegmentsGroups.add(flightSegmentsGroup);
				fareFlightSegment.setFareRules(fareRulesList);
			}
			fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);
			fareFlightSegment.setNewFlightSegmentsGroups(newFlightSegmentsGroups);
			fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			flightPriceResponse.setFareFlightSegment(fareFlightSegment);
		}
		flightPriceResponse.setFlightMarkUpConfiglistMap(FlightMarkUpConfiglistMap);
		flightPriceResponse.setMarkupCommissionDetails(markupCommissionDetails);

		//*** GST calculation///////////////////
		flightPriceResponse.setFinalPriceWithGST(new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()));
		flightPriceResponse.setGSTonMarkup(new BigDecimal("0.00"));
		if(!flightsearch.isIsInternational()){
			BigDecimal totalprice=new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice());
			BigDecimal Markup=new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()).subtract(new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPriceWithoutMarkup()));
			addGST(flightPriceResponse,totalprice,Markup);
		}
		addNewFlightSegmentsGroups(flightPriceResponse,flightPriceResponse.getFareFlightSegment());
		addGSTForFlights(flightPriceResponse,flightsearch);
		return flightPriceResponse;
	}

	private static void addGSTForFlights(FlightPriceResponse flightPriceResponse,Flightsearch flightsearch){
		BigDecimal totalprice = new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPriceWithoutMarkup());
		BigDecimal GSTinpercentage = new BigDecimal("6.00");
		BigDecimal finalPriceWithGST = flightPriceResponse.getFinalPriceWithGST();
		BigDecimal GST = totalprice;
		if(flightsearch.getFlightType().equalsIgnoreCase("Domestic")){
			GST = totalprice
					.multiply(GSTinpercentage)
					.divide(new BigDecimal("100"));
		}else{
			GST = flightPriceResponse.getGSTonFlights()
					.multiply(GSTinpercentage)
					.divide(new BigDecimal("100"));
		}
		finalPriceWithGST = finalPriceWithGST.add(GST);
		flightPriceResponse.setGSTonFlights(GST);
		flightPriceResponse.setFinalPriceWithGST(finalPriceWithGST);
	}

	static void addNewFlightSegmentsGroups(FlightPriceResponse flightPriceResponse,FareFlightSegment fareFlightSegment){
		List<FlightSegmentsGroup> newFlightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
		for(FlightSegmentsGroup flightSegmentsGroup:fareFlightSegment.getFlightSegmentsGroups()){
			for(FlightSegments flightSegmentsList:flightSegmentsGroup.getFlightSegments()){
				int m=-1;
				FlightSegmentsGroup flightSegmentsGroup1 =null;
				List<FlightSegments> flightSegmentsList1 = null;
				List<Segments> segmentsList = null;
				FlightSegments flightSegments = null;				
				for(int l=0;l<flightSegmentsList.getSegments().size();l++)
				{	
					Segments segments=flightSegmentsList.getSegments().get(l);
					if(l!=0&&m!=segments.getTripNo()){
						flightSegments.setSegments(segmentsList);
						flightSegmentsList1.add(flightSegments);
						flightSegmentsGroup1
						.setFlightSegments(flightSegmentsList1);
						newFlightSegmentsGroups.add(flightSegmentsGroup1);	
					}
					if(m!=segments.getTripNo()){			
						flightSegmentsGroup1 = new FlightSegmentsGroup();
						segmentsList = new ArrayList<Segments>();
						flightSegments = new FlightSegments();							 
						flightSegmentsList1 = new ArrayList<FlightSegments>();
						flightSegments.setFlightIndex(flightSegmentsList.getFlightIndex());
						flightSegments.setApiProvider(flightSegmentsList.getApiProvider());
						flightSegments.setBookingCurrency(flightSegmentsList.getBookingCurrency());
						flightSegments.setExchangeRate(flightSegmentsList.getExchangeRate());
					}
					segmentsList.add(segments);
					if(l==flightSegmentsList.getSegments().size()-1){
						flightSegments.setSegments(segmentsList);
						flightSegmentsList1.add(flightSegments);
						flightSegmentsGroup1
						.setFlightSegments(flightSegmentsList1);
						newFlightSegmentsGroups.add(flightSegmentsGroup1);
					}
					m = segments.getTripNo();
				}
			}
		}
		fareFlightSegment.setNewFlightSegmentsGroups(newFlightSegmentsGroups);
	}

	public static void  parseAirPriceSpecial(AirPriceRsp airPriceRsp,
			Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap,
			Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> MapList, Flightsearch flightsearch,
			String transaction_key, FlightTempAirSegmentDAO tempDAO,BigDecimal exchangeRate,BigDecimal apiToBaseExchangeRate,MarkupCommissionDetails markupCommissionDetails,FlightPriceResponse flightPriceResponse) throws Exception {
		Map<String, String> CityNameMap = new HashMap<String, String>();
		Map<String, String> AirportNameMap = new HashMap<String, String>();
		if (MapList.size() > 0) {
			CityNameMap = MapList.get(0);
			AirportNameMap = MapList.get(1);
		}
		int passegers_for_marhup = flightsearch.getAdult()
				+ flightsearch.getKid();	
		flightPriceResponse.setTransactionKey(transaction_key);
		flightPriceResponse.setFlightsearch(flightsearch);
		AirItinerary aiItinerary = airPriceRsp.getAirItinerary();
		List<TypeBaseAirSegment> typeBaseAirSegmentList = aiItinerary
				.getAirSegment();
		airSegMap = new HashMap<String, TypeBaseAirSegment>();
		for (int m = 0; m < typeBaseAirSegmentList.size(); m++) {		
			airSegMap.put(typeBaseAirSegmentList.get(m).getKey(),
					typeBaseAirSegmentList.get(m));
		}
		flightPriceResponse.setCountry("YET to get");
		List<AirPriceResult> AirPriceResultList = airPriceRsp
				.getAirPriceResult();
		List<FlightSegmentsGroup> flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
		String apiCurrency = AirPriceResultList.get(0).getAirPricingSolution().get(0).getApproximateTotalPrice().substring(0, 3);
		for (int i = 0; i < AirPriceResultList.size(); i++) {
			AirPriceResult airPriceResult = AirPriceResultList.get(i);
			List<AirPricingSolution> airPricingSolutionList = airPriceResult
					.getAirPricingSolution();
			FareFlightSegment fareFlightSegment = new FareFlightSegment();
			fareFlightSegment.setId(new UID().toString());			
			if (airPricingSolutionList.size()>0) {
				AirPricingSolution airPricingSolution = airPricingSolutionList
						.get(airPricingSolutionList.size()-1);
				FlightSegmentsGroup flightSegmentsGroup = new FlightSegmentsGroup();
				String key = airPricingSolution.getAirSegmentRef().get(0).getKey();
				String airlinecode = airSegMap.get(key).getCarrier();
				fareFlightSegment.setCurrency(apiCurrency);
				fareFlightSegment.setBookingCurrency(flightsearch.getCurrency());
				fareFlightSegment.setExchangeRate(exchangeRate);			
				fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
						airPricingSolution.getApproximateBasePrice(), apiCurrency)).multiply(apiToBaseExchangeRate)));
				fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
						airPricingSolution.getApproximateTotalPrice(), apiCurrency)).multiply(apiToBaseExchangeRate)));
				fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
						airPricingSolution.getApproximateTaxes(), apiCurrency)).multiply(apiToBaseExchangeRate)));
				fareFlightSegment.setApiCurrency(apiCurrency);
				fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
				fareFlightSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);
				fareFlightSegment.setApi_basePriceWithoutMarkup(replaceCurrency(
						airPricingSolution.getApproximateBasePrice(), apiCurrency));
				fareFlightSegment.setApi_totalPriceWithoutMarkup(replaceCurrency(
						airPricingSolution.getApproximateTotalPrice(), apiCurrency));
				fareFlightSegment.setApi_taxesWithoutMarkup(replaceCurrency(
						airPricingSolution.getApproximateTaxes(), apiCurrency));
				List<AirPricingInfo> airPricingInfoList = airPricingSolution
						.getAirPricingInfo();
				List<FlightSegments> flightSegmentsList = new ArrayList<FlightSegments>();
				List<PassengerFareBreakUp> passengerFareBreakUps = new ArrayList<PassengerFareBreakUp>();
				List<FareRules> fareRulesList = new ArrayList<FareRules>();
				FareRules farerules = new FareRules();
				Set<String> fareBasisCodeSet = new HashSet<String>();				
				String fareBasisCode = "ALL";
				int v = 0;
				for (AirPricingInfo airPricingInfo : airPricingInfoList) {
					List<FareInfo> fareinfolist = airPricingInfo.getFareInfo();
					airPricingInfo.getLatestTicketingTime();
					List<FareRule> FareRuleList = new ArrayList<FareRule>();
					fareFlightSegment.setLatestTicketingTime(airPricingInfo
							.getLatestTicketingTime());
					for (FareInfo fareinfo : fareinfolist) {
						fareBasisCodeSet.add(fareinfo.getFareBasis());
						FareRule farerule = new FareRule();
						farerule.setAirlineCode(airlinecode);
						farerule.setArrCode(fareinfo.getDestination());
						farerule.setBasisCode(fareinfo.getFareBasis());
						farerule.setDepCode(fareinfo.getOrigin());
						farerule.setDepDate(fareinfo.getEffectiveDate());
						if (fareinfo.getFareRuleKey() != null) {
							farerule.setFareInfoRef(fareinfo.getFareRuleKey()
									.getFareInfoRef());
							farerule.setFareProviderCode(fareinfo
									.getFareRuleKey().getProviderCode());
							farerule.setFareValue(fareinfo.getFareRuleKey()
									.getValue());
						}
						if (fareinfo.getBaggageAllowance() != null) {
							farerule.setBagAllowanceUnit(fareinfo
									.getBaggageAllowance().getMaxWeight()
									.getUnit());
							farerule.setBagAllowanceValue(fareinfo
									.getBaggageAllowance().getMaxWeight()
									.getValue());
						}
						FareRuleList.add(farerule);
						farerules.setTravelerType(flightsearch.getTripType());
						farerules.setFareRule(FareRuleList);
					}
					fareRulesList.add(farerules);
					if (v == 0) {
						List<BookingInfo> bookinginfoList = airPricingInfo
								.getBookingInfo();
						List<Segments> segmentsList = new ArrayList<Segments>();
						FlightSegments flightSegments = new FlightSegments();
						flightSegments.setFlightIndex(new UID().toString());
						flightSegments.setApiProvider("Travelport");
						flightSegments.setBookingCurrency(flightsearch.getCurrency());
						flightSegments.setExchangeRate(exchangeRate);
						for (BookingInfo bookingInfo : bookinginfoList) {
							TypeBaseAirSegment typeBaseAirSegment = airSegMap
									.get(bookingInfo.getSegmentRef());
							Segments segments = new Segments();
							if (typeBaseAirSegment.getArrivalTime() != null) {								
								segments.setGroup(typeBaseAirSegment.getGroup());
								segments.setDuration(String
										.valueOf(typeBaseAirSegment
												.getFlightTime()));
								segments.setOri(typeBaseAirSegment.getOrigin());
								segments.setDest(typeBaseAirSegment
										.getDestination());
								segments.setOriAirportName(AirportNameMap
										.get(typeBaseAirSegment.getOrigin()));
								segments.setDestAirportName(AirportNameMap
										.get(typeBaseAirSegment
												.getDestination()));

								segments.setOriName(CityNameMap
										.get(typeBaseAirSegment.getOrigin()));
								segments.setDestName(CityNameMap
										.get(typeBaseAirSegment
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
								flight.setDestTerminal(typeBaseAirSegment.getFlightDetails().get(0).getDestinationTerminal());//
								flight.setOriTerminal(typeBaseAirSegment.getFlightDetails().get(0).getOriginTerminal());//
								flight.setEquipment(typeBaseAirSegment
										.getEquipment());
								flight.setNumber(typeBaseAirSegment.getFlightNumber());
								segments.setFlight(flight);
								Carrier carrier = new Carrier();
								carrier.setCode(typeBaseAirSegment.getCarrier());
								carrier.setName(AirlineNameMap
										.get(typeBaseAirSegment.getCarrier()));
								segments.setCarrier(carrier);
								Cabin cabin = new Cabin();
								cabin.setName(bookingInfo.getCabinClass());
								cabin.setCode(bookingInfo.getBookingCode());
								segments.setCabin(cabin);
								segments.setFareInfoRef(bookingInfo.getFareInfoRef());//
								segmentsList.add(segments);
							}
						}
						flightSegments.setSegments(segmentsList);
						flightSegmentsList.add(flightSegments);
						flightSegmentsGroup
						.setFlightSegments(flightSegmentsList);
					}
					v++;
					if(fareBasisCodeSet.size()==1){
						fareBasisCode=fareBasisCodeSet.iterator().next();
					}
					for (PassengerType passengerType : airPricingInfo
							.getPassengerType()) {
						AddPassengerFareBreakUp(airPricingInfo,
								FlightMarkUpConfiglistMap, passengerFareBreakUps,
								flightPriceResponse,
								airlinecode,
								passengerType.getCode(), passegers_for_marhup,fareFlightSegment,flightsearch.getBaseToBookingExchangeRate(), fareBasisCode,flightsearch.getFlightType());
					}
				}
				try {
					UmarkUpServiceCall.getMarkupValuesFPR(flightPriceResponse,
							FlightMarkUpConfiglistMap,
							airlinecode, fareFlightSegment, fareBasisCode,null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				fareFlightSegment.setFareBasisCode(fareBasisCode);				
				flightPriceResponse.setSpecialPassengerFareBreakUps(passengerFareBreakUps);
				flightSegmentsGroups.add(flightSegmentsGroup);
				fareFlightSegment.setFareRules(fareRulesList);
			}
			fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);
			fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			flightPriceResponse.setSpecialFareFlightSegment(fareFlightSegment);
		}
		flightPriceResponse.setFlightMarkUpConfiglistMap(FlightMarkUpConfiglistMap);
		flightPriceResponse.setMarkupCommissionDetails(markupCommissionDetails);	
		flightPriceResponse.setFinalPriceWithGST(new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice()).add(flightPriceResponse.getFinalPriceWithGST()));
		flightPriceResponse.setGSTonMarkupSpecial(new BigDecimal("0.00"));
		if(!flightsearch.isIsInternational()){
			flightPriceResponse.setFinalPriceWithGST(flightPriceResponse.getFinalPriceWithGST().subtract(new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice())));//for time being
			BigDecimal totalprice=new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice());
			BigDecimal Markup=new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice()).subtract(new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPriceWithoutMarkup()));			
			addGSTSpecial(flightPriceResponse,totalprice,Markup);
		}
		addNewFlightSegmentsGroups(flightPriceResponse,flightPriceResponse.getSpecialFareFlightSegment());
	}

	/**
	 * @param data
	 *            ,String currency
	 * @return
	 * @throws Exception
	 * 
	 */
	private static void addGST(FlightPriceResponse flightPriceResponse,BigDecimal totalprice,BigDecimal Markup){
		BigDecimal GSTinpercentage=new BigDecimal("6.00");
		BigDecimal finalPriceWithGST=totalprice;
		BigDecimal GST=totalprice;
		GST = Markup
				.multiply(GSTinpercentage)
				.divide(new BigDecimal("100"));
		finalPriceWithGST = finalPriceWithGST.add(GST);
		flightPriceResponse.setFinalPriceWithGST(finalPriceWithGST);
		flightPriceResponse.setGSTonMarkup(GST);
	}

	private static void addGSTSpecial(FlightPriceResponse flightPriceResponse,BigDecimal totalprice,BigDecimal Markup){
		BigDecimal GSTinpercentage=new BigDecimal("6.00");
		BigDecimal finalPriceWithGST=totalprice;
		BigDecimal GST=totalprice;
		GST=Markup
				.multiply(GSTinpercentage)
				.divide(new BigDecimal("100"));
		finalPriceWithGST = finalPriceWithGST.add(GST).add(flightPriceResponse.getFinalPriceWithGST());
		flightPriceResponse.setFinalPriceWithGST(finalPriceWithGST);
		flightPriceResponse.setGSTonMarkupSpecial(GST);
	}

	private static void AddPassengerFareBreakUp(AirPricingInfo airPricingInfo,
			Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap,
			List<PassengerFareBreakUp> passengerFareBreakUps,
			FlightPriceResponse flightPriceResponse, String airlinename,
			String passengerType, int passegers_for_marhup,FareFlightSegment fareFlightSegment,BigDecimal baseToBookingExchangeRate,String fareBasisCode,String airtype) throws Exception {
		PassengerFareBreakUp passengerFareBreakUp = new PassengerFareBreakUp();
		String apiCurrency = airPricingInfo.getApproximateTotalPrice().substring(0, 3);		
		passengerFareBreakUp.setId(new UID().toString());
		passengerFareBreakUp.setType(passengerType);
		BigDecimal apiToBaseExchangeRate=fareFlightSegment.getApiToBaseExchangeRate();
		List<TypeTaxInfo> taxInfoList=airPricingInfo.getTaxInfo();
		StringBuilder sb=new StringBuilder();
		BigDecimal  gstOnFlights=flightPriceResponse.getGSTonFlights();	
		for(TypeTaxInfo typeTaxInfo:taxInfoList){
			BigDecimal tax=new BigDecimal(replaceCurrency(typeTaxInfo.getAmount(), apiCurrency));
			BigDecimal taxINBooking=(tax.multiply(apiToBaseExchangeRate)).multiply(baseToBookingExchangeRate);
			if((!airtype.equalsIgnoreCase("Domestic"))&&(typeTaxInfo.getCategory().equalsIgnoreCase("MY"))){
				gstOnFlights=gstOnFlights.add(taxINBooking);
			}
			sb.append(typeTaxInfo.getCategory()+":"+taxINBooking+";");
		}
		passengerFareBreakUp.setTaxDescription(sb.toString());			
		passengerFareBreakUp.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
				airPricingInfo.getApproximateBasePrice(), apiCurrency)).multiply(apiToBaseExchangeRate)));
		passengerFareBreakUp.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
				airPricingInfo.getApproximateTotalPrice(), apiCurrency)).multiply(apiToBaseExchangeRate)));
		passengerFareBreakUp.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(replaceCurrency(
				airPricingInfo.getApproximateTaxes(), apiCurrency)).multiply(apiToBaseExchangeRate)));
		passengerFareBreakUp.setApi_basePriceWithoutMarkup(replaceCurrency(
				airPricingInfo.getApproximateBasePrice(), apiCurrency));
		passengerFareBreakUp.setApi_totalPriceWithoutMarkup(replaceCurrency(
				airPricingInfo.getApproximateTotalPrice(), apiCurrency));
		passengerFareBreakUp.setApi_taxesWithoutMarkup(replaceCurrency(
				airPricingInfo.getApproximateTaxes(), apiCurrency));
		passengerFareBreakUp.setCurrency(apiCurrency);
		UmarkUpServiceCall.getMarkupValuesForPassegers(flightPriceResponse, 
				FlightMarkUpConfiglistMap, airlinename, passengerFareBreakUp,
				passegers_for_marhup,fareBasisCode);
		passengerFareBreakUp.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getBasePriceWithoutMarkup()).multiply(baseToBookingExchangeRate)));
		passengerFareBreakUp.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getTotalPriceWithoutMarkup()).multiply(baseToBookingExchangeRate)));
		passengerFareBreakUp.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getTaxesWithoutMarkup()).multiply(baseToBookingExchangeRate)));
		passengerFareBreakUp.setBasePrice(String.valueOf(new BigDecimal(passengerFareBreakUp.getBasePrice()).multiply(baseToBookingExchangeRate)));
		passengerFareBreakUp.setTotalPrice(String.valueOf(new BigDecimal(passengerFareBreakUp.getTotalPrice()).multiply(baseToBookingExchangeRate)));
		passengerFareBreakUp.setTaxes(String.valueOf(new BigDecimal(passengerFareBreakUp.getTaxes()).multiply(baseToBookingExchangeRate)));
		passengerFareBreakUps.add(passengerFareBreakUp);
		flightPriceResponse.setGSTonFlights(gstOnFlights);
	}

	private static String replaceCurrency(String data, String currency) {
		return data.replace(currency, "");
	}
}