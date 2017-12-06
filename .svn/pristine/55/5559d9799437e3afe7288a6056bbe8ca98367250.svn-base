/**
@Author ilyas
16-oct-2015 
BluestarAirPriceResponseParser.java
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
import org.apache.log4j.Logger;

import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.model.BluestarPriceData;
import com.tayyarah.flight.model.FareFlightSegment;
import com.tayyarah.flight.model.FareRules;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.FlightSegments;
import com.tayyarah.flight.model.FlightSegmentsGroup;
import com.tayyarah.flight.model.FlightTaxBreakUp;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.MarkupCommissionDetails;
import com.tayyarah.flight.model.PassengerFareBreakUp;
import com.tayyarah.flight.model.Segments;
import com.tayyarah.flight.util.api.travelport.UmarkUpServiceCall;
import com.travelport.api_v33.AirResponse.AirPricingInfo;
import com.travelport.api_v33.AirResponse.TypeBaseAirSegment;

public class BluestarAirPriceResponseParser {	
	static Logger logger = Logger.getLogger(BluestarAirPriceResponseParser.class);	
	protected static Map<String, TypeBaseAirSegment> airSegMap;
	static List<AirPricingInfo> airPricingInfoList;
	
	public static void  parseAirPriceSpecial(Map<String, FareFlightSegment> FareFlightSegmentMap,
			LinkedHashMap<String, FlightSegments> FlightSegmentstMap,Map<String, FareRules> FareRulesMap,Map<String,List<FlightMarkUpConfig>> markupMap ,
			Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> MapList, Flightsearch flightsearch,
			String transaction_key, FlightTempAirSegmentDAO tempDAO,BigDecimal exchangeRate,List<String> fligtindexlist,BigDecimal apiToBaseExchangeRate,MarkupCommissionDetails markupCommissionDetails,FlightPriceResponse flightPriceResponse, BluestarPriceData bluestarPriceData) throws Exception {

		String[] flightdetailNew = bluestarPriceData.getFlightdeatilArray();
		Map<String ,String[]> faredetailMap = bluestarPriceData.getFaredetailMap();
		Map<String, Integer> fareDetailsHeaderMap = bluestarPriceData.getFareDetailsHeaderMap();
		Map<String, Integer> flightDetailsHeaderMap = bluestarPriceData.getFlightDetailsHeaderMap();

		Map<String, String> CityNameMap = new HashMap<String, String>();
		Map<String, String> AirportNameMap = new HashMap<String, String>();
		if (MapList.size() > 0) {
			CityNameMap = MapList.get(0);
			AirportNameMap = MapList.get(1);
		}
		logger.debug("--------- Start---------");	
		int passegers_for_marhup = flightsearch.getAdult()+ flightsearch.getKid();

		flightPriceResponse.setTransactionKey(transaction_key);
		flightPriceResponse.setCountry("Yet to get");
		flightPriceResponse.setFlightsearch(flightsearch);
		flightPriceResponse.setCountry("YET to get");
		List<FlightSegmentsGroup> flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();
		String flightindex=fligtindexlist.get(1);
		FareFlightSegment oldfareFlightSegment=FareFlightSegmentMap.get(flightindex);
		FareFlightSegment fareFlightSegment = new FareFlightSegment();
		fareFlightSegment.setId(new UID().toString());

		FlightSegmentsGroup flightSegmentsGroup = new FlightSegmentsGroup();

		flightPriceResponse.setSpecialFareFlightSegment(fareFlightSegment);
		fareFlightSegment.setCurrency(oldfareFlightSegment.getCurrency());
		fareFlightSegment.setApiCurrency(oldfareFlightSegment.getCurrency());
		fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
		fareFlightSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);

		String totalAmount=flightdetailNew[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.APTotalAmount)];
		BigDecimal baseprice = new BigDecimal(totalAmount)
				.subtract(new BigDecimal(
						flightdetailNew[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.APTaxAmount)]));

		fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(totalAmount).multiply(apiToBaseExchangeRate)));
		fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(baseprice.multiply(apiToBaseExchangeRate)));
		fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(flightdetailNew[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.APTaxAmount)]).multiply(apiToBaseExchangeRate)));
		fareFlightSegment.setApi_basePriceWithoutMarkup(String.valueOf(baseprice));
		fareFlightSegment.setApi_taxesWithoutMarkup(flightdetailNew[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.APTaxAmount)]);
		fareFlightSegment.setApi_totalPriceWithoutMarkup(totalAmount);


		try {
			UmarkUpServiceCall.getMarkupValuesFPR(flightPriceResponse,
					markupMap,
					oldfareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getCode(), fareFlightSegment,"ALL",null);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fareFlightSegment.setExchangeRate(oldfareFlightSegment.getExchangeRate());
		fareFlightSegment.setBookingCurrency(oldfareFlightSegment.getBookingCurrency());

		List<FlightSegments> flightSegmentsList = new ArrayList<FlightSegments>();
		List<PassengerFareBreakUp> passengerFareBreakUps = new ArrayList<PassengerFareBreakUp>();
		List<FareRules> fareRulesList = new ArrayList<FareRules>();

		FareRules farerules = FareRulesMap.get(flightindex);
		fareRulesList.add(farerules);
		if(!flightsearch.isSpecialSearch()){			
			if(fligtindexlist.size()>1){
				String tempflightIndex=fligtindexlist.get(1);				
				fareRulesList.get(0).setFareRule(FareRulesMap.get(tempflightIndex).getFareRule());
			}
		}
		FlightSegments flightSegments =FlightSegmentstMap.get(flightindex);
		if(!flightsearch.isSpecialSearch()){		
			if(fligtindexlist.size()>1){
				String tempflightIndex=fligtindexlist.get(1);
				FlightSegments tempflightSegments =FlightSegmentstMap.get(tempflightIndex);
				for(int i=0;i<tempflightSegments.getSegments().size();i++)
				{				
					flightSegments.getSegments().add(tempflightSegments.getSegments().get(i));

				}
			}
		}
		flightSegments.setFlightIndex(new UID().toString());
		flightSegments.setApiProvider("Bluestar");
		flightSegmentsList.add(flightSegments);
		flightSegmentsGroup.setFlightSegments(flightSegmentsList);
		String[] faredetails=faredetailMap.get(flightSegments.getSegments().get(0).getFareInfoRef());

		AddPassengerFareBreakUp(markupMap, passengerFareBreakUps,
				flightPriceResponse,farerules.getFareRule().get(0).getAirlineCode(), passegers_for_marhup,faredetails,flightsearch, apiToBaseExchangeRate,flightPriceResponse.getSpecialFareFlightSegment().getCurrency(),fareDetailsHeaderMap);

		flightPriceResponse.setSpecialPassengerFareBreakUps(passengerFareBreakUps);
		flightSegmentsGroups.add(flightSegmentsGroup);
		fareFlightSegment.setFareRules(fareRulesList);
		fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);
		fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
		fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
		fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
		fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
		fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
		fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));

		flightPriceResponse.setSpecialFareFlightSegment(fareFlightSegment);
		flightPriceResponse.setFlightMarkUpConfiglistMap(markupMap);
		flightPriceResponse.setMarkupCommissionDetails(markupCommissionDetails);

		//*** GST calculation///////////////////
		flightPriceResponse.setFinalPriceWithGST(new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice()).add(flightPriceResponse.getFinalPriceWithGST()));
		flightPriceResponse.setGSTonMarkupSpecial((new BigDecimal("0.00")));

		addNewFlightSegmentsGroups(flightPriceResponse,flightPriceResponse.getSpecialFareFlightSegment());		
	}
	
	public static FlightPriceResponse parseAirPrice(Map<String, FareFlightSegment> FareFlightSegmentMap,
			LinkedHashMap<String, FlightSegments> FlightSegmentstMap,Map<String, FareRules> FareRulesMap,Map<String,List<FlightMarkUpConfig>> markupMap ,
			Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> MapList, Flightsearch flightsearch,
			String transaction_key, FlightTempAirSegmentDAO tempDAO,BigDecimal exchangeRate,List<String> fligtindexlist,BigDecimal apiToBaseExchangeRate,MarkupCommissionDetails markupCommissionDetails, BluestarPriceData bluestarPriceData) throws Exception {

		String[] flightdetailNew = bluestarPriceData.getFlightdeatilArray();
		Map<String ,String[]> faredetailMap = bluestarPriceData.getFaredetailMap();
		Map<String, Integer> fareDetailsHeaderMap = bluestarPriceData.getFareDetailsHeaderMap();
		Map<String, Integer> flightDetailsHeaderMap = bluestarPriceData.getFlightDetailsHeaderMap();

		logger.debug("--------- Start---------");

		int passegers_for_marhup = flightsearch.getAdult()+ flightsearch.getKid();

		FlightPriceResponse flightPriceResponse = new FlightPriceResponse();
		flightPriceResponse.setTransactionKey(transaction_key);
		flightPriceResponse.setCountry("Yet to get");
		flightPriceResponse.setFlightsearch(flightsearch);
		flightPriceResponse.setCountry("YET to get");
		List<FlightSegmentsGroup> flightSegmentsGroups = new ArrayList<FlightSegmentsGroup>();		
		String flightindex=fligtindexlist.get(0);
		FareFlightSegment oldfareFlightSegment=FareFlightSegmentMap.get(flightindex);
		FareFlightSegment fareFlightSegment = new FareFlightSegment();
		fareFlightSegment.setId(new UID().toString());
		FlightSegmentsGroup flightSegmentsGroup = new FlightSegmentsGroup();	

		flightPriceResponse.setFareFlightSegment(fareFlightSegment);		

		fareFlightSegment.setCurrency(oldfareFlightSegment.getCurrency());
		fareFlightSegment.setApiCurrency(oldfareFlightSegment.getCurrency());
		fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
		fareFlightSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);
		String totalAmount=flightdetailNew[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.APTotalAmount)];
		BigDecimal baseprice = new BigDecimal(totalAmount)
				.subtract(new BigDecimal(
						flightdetailNew[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.APTaxAmount)]));

		fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(totalAmount).multiply(apiToBaseExchangeRate)));
		fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(baseprice.multiply(apiToBaseExchangeRate)));
		fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(flightdetailNew[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.APTaxAmount)]).multiply(apiToBaseExchangeRate)));
		fareFlightSegment.setApi_basePriceWithoutMarkup(String.valueOf(baseprice));
		fareFlightSegment.setApi_taxesWithoutMarkup(flightdetailNew[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.APTaxAmount)]);
		fareFlightSegment.setApi_totalPriceWithoutMarkup(totalAmount);


		try {
			UmarkUpServiceCall.getMarkupValuesFPR(flightPriceResponse,
					markupMap,
					oldfareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getCode(), fareFlightSegment,"ALL",null);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fareFlightSegment.setExchangeRate(oldfareFlightSegment.getExchangeRate());
		fareFlightSegment.setBookingCurrency(oldfareFlightSegment.getBookingCurrency());

		List<FlightSegments> flightSegmentsList = new ArrayList<FlightSegments>();
		List<PassengerFareBreakUp> passengerFareBreakUps = new ArrayList<PassengerFareBreakUp>();
		List<FareRules> fareRulesList = new ArrayList<FareRules>();

		FareRules farerules = FareRulesMap.get(flightindex);
		fareRulesList.add(farerules);
		if(!flightsearch.isSpecialSearch()){
			if(fligtindexlist.size()>1){
				String tempflightIndex=fligtindexlist.get(1);				
				fareRulesList.get(0).setFareRule(FareRulesMap.get(tempflightIndex).getFareRule());
			}
		}
		FlightSegments flightSegments =FlightSegmentstMap.get(flightindex);		
		for(int i=0;i<flightSegments.getSegments().size();i++)
		{	
			flightSegments.getSegments().get(i).setTripNo(1);
		}
		if(!flightsearch.isSpecialSearch()){	
			if(fligtindexlist.size()>1){

				String tempflightIndex=fligtindexlist.get(1);
				FlightSegments tempflightSegments =FlightSegmentstMap.get(tempflightIndex);
				for(int i=0;i<tempflightSegments.getSegments().size();i++)
				{
					tempflightSegments.getSegments().get(i).setTripNo(2);
					flightSegments.getSegments().add(tempflightSegments.getSegments().get(i));
				}
			}
		}
		flightSegments.setFlightIndex(new UID().toString());
		flightSegments.setApiProvider("Bluestar");
		flightSegmentsList.add(flightSegments);
		flightSegmentsGroup.setFlightSegments(flightSegmentsList);	
		String[] faredetails=faredetailMap.get(flightSegments.getSegments().get(0).getFareInfoRef());

		AddPassengerFareBreakUp(markupMap, passengerFareBreakUps,
				flightPriceResponse,farerules.getFareRule().get(0).getAirlineCode(), passegers_for_marhup,faredetails,flightsearch, apiToBaseExchangeRate,flightPriceResponse.getFareFlightSegment().getCurrency(),fareDetailsHeaderMap);

		flightPriceResponse.setPassengerFareBreakUps(passengerFareBreakUps);
		flightSegmentsGroups.add(flightSegmentsGroup);
		fareFlightSegment.setFareRules(fareRulesList);

		fareFlightSegment.setFlightSegmentsGroups(flightSegmentsGroups);

		fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
		fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
		fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
		fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
		fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
		fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));


		flightPriceResponse.setFareFlightSegment(fareFlightSegment);
		flightPriceResponse.setFlightMarkUpConfiglistMap(markupMap);
		flightPriceResponse.setMarkupCommissionDetails(markupCommissionDetails);

		//*** GST calculation///////////////////
		flightPriceResponse.setFinalPriceWithGST(new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()));
		flightPriceResponse.setGSTonMarkup(new BigDecimal("0.00"));
		flightPriceResponse.setGSTonFlights(new BigDecimal("0.00"));

		addNewFlightSegmentsGroups(flightPriceResponse,flightPriceResponse.getFareFlightSegment());

		return flightPriceResponse;
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
				//for(Segments segments:flightSegmentsList.getSegments()){
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
					m=segments.getTripNo();
				}
			}
		}

		fareFlightSegment.setNewFlightSegmentsGroups(newFlightSegmentsGroups);


	}

	private static void AddPassengerFareBreakUp(Map<String,List<FlightMarkUpConfig>> markupMap ,
			List<PassengerFareBreakUp> passengerFareBreakUps,
			FlightPriceResponse flightPriceResponse, String airlinename,
			int passegers_for_marhup,String[] faredetails, Flightsearch flightsearch,BigDecimal apiToBaseExchangeRate,String apicurrency, Map<String, Integer> fareDetailsHeaderMap) throws Exception {


		BigDecimal	baseToBookingExchangeRate=flightsearch.getBaseToBookingExchangeRate();		

		for(int i=0;i<flightsearch.getAdult();i++)
		{
			PassengerFareBreakUp passengerFareBreakUp = new PassengerFareBreakUp();
			passengerFareBreakUp.setCurrency(apicurrency);
			passengerFareBreakUp.setId(new UID().toString());
			passengerFareBreakUp.setType("ADT");

			String baseprice=String.valueOf(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultBaseFare)]).divide(new BigDecimal(flightsearch.getAdult())));

			String totaltax = String.valueOf((new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultTax)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultFuelCharges)])
					.add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultAirportTax)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultPassengerServiceFee)])
							.add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultTransactionFee)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultServiceCharges)])
									.add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultAirportDevelopmentFee)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultCuteFee)])
											.add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultConvenienceFee)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultSkyCafeMeals)]))))))))))));
			String taxprice=String.valueOf(new BigDecimal(totaltax).divide(new BigDecimal(flightsearch.getAdult())));
			String totalprice=String.valueOf(new BigDecimal(baseprice).add(new BigDecimal(taxprice)));

			passengerFareBreakUp.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(baseprice).multiply(apiToBaseExchangeRate)));
			passengerFareBreakUp.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(totalprice).multiply(apiToBaseExchangeRate)));
			passengerFareBreakUp.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(taxprice).multiply(apiToBaseExchangeRate)));
			passengerFareBreakUp.setApi_basePriceWithoutMarkup(baseprice);
			passengerFareBreakUp.setApi_totalPriceWithoutMarkup(totalprice);
			passengerFareBreakUp.setApi_taxesWithoutMarkup(taxprice);

			FlightTaxBreakUp flightTaxBreakUp = new FlightTaxBreakUp();
			
			flightTaxBreakUp.setYQ(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultFuelCharges)]));
			flightTaxBreakUp.setPSF(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultPassengerServiceFee)]));
			flightTaxBreakUp.setYR(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultTransactionFee)]));
			flightTaxBreakUp.setIN(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultAirportTax )]));
			flightTaxBreakUp.setUDF(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.AdultAirportDevelopmentFee )]));
			passengerFareBreakUp.setFlightTaxBreakUp(flightTaxBreakUp);

			UmarkUpServiceCall.getMarkupValuesForPassegers(flightPriceResponse,
					markupMap, airlinename, passengerFareBreakUp,
					passegers_for_marhup,"ALL");

			passengerFareBreakUp.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getBasePriceWithoutMarkup()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUp.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getTotalPriceWithoutMarkup()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUp.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getTaxesWithoutMarkup()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUp.setBasePrice(String.valueOf(new BigDecimal(passengerFareBreakUp.getBasePrice()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUp.setTotalPrice(String.valueOf(new BigDecimal(passengerFareBreakUp.getTotalPrice()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUp.setTaxes(String.valueOf(new BigDecimal(passengerFareBreakUp.getTaxes()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUps.add(passengerFareBreakUp);
		}
		for(int i=0;i<flightsearch.getKid();i++)
		{
			PassengerFareBreakUp passengerFareBreakUp = new PassengerFareBreakUp();
			passengerFareBreakUp.setCurrency(apicurrency);
			passengerFareBreakUp.setId(new UID().toString());
			passengerFareBreakUp.setType("CNN");

			String baseprice=String.valueOf(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildBaseFare)]).divide(new BigDecimal(flightsearch.getKid())));

			String totaltax=String.valueOf((new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildTax)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildFuelCharges)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildAirportTax)]).
					add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildPassengerServiceFee)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildTransactionFee)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildServiceCharges)])
							.add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildAirportDevelopmentFee)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildCuteFee)])
									.add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildConvenienceFee)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildSkyCafeMeals)]))))))))))));
			String taxprice=String.valueOf(new BigDecimal(totaltax).divide(new BigDecimal(flightsearch.getKid())));
			String totalprice=String.valueOf(new BigDecimal(baseprice).add(new BigDecimal(taxprice)));

			passengerFareBreakUp.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(baseprice).multiply(apiToBaseExchangeRate)));
			passengerFareBreakUp.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(totalprice).multiply(apiToBaseExchangeRate)));
			passengerFareBreakUp.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(taxprice).multiply(apiToBaseExchangeRate)));
			passengerFareBreakUp.setApi_basePriceWithoutMarkup(baseprice);
			passengerFareBreakUp.setApi_totalPriceWithoutMarkup(totalprice);
			passengerFareBreakUp.setApi_taxesWithoutMarkup(taxprice);
			
			FlightTaxBreakUp flightTaxBreakUp = new FlightTaxBreakUp();
			
			flightTaxBreakUp.setYQ(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildFuelCharges)]));
			flightTaxBreakUp.setPSF(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildPassengerServiceFee)]));
			flightTaxBreakUp.setYR(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildTransactionFee)]));
			flightTaxBreakUp.setIN(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildAirportTax )]));
			flightTaxBreakUp.setUDF(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.ChildAirportDevelopmentFee  )]));
			passengerFareBreakUp.setFlightTaxBreakUp(flightTaxBreakUp);

			UmarkUpServiceCall.getMarkupValuesForPassegers(flightPriceResponse,
					markupMap, airlinename, passengerFareBreakUp,
					passegers_for_marhup,"ALL");

			passengerFareBreakUp.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getBasePriceWithoutMarkup()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUp.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getTotalPriceWithoutMarkup()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUp.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getTaxesWithoutMarkup()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUp.setBasePrice(String.valueOf(new BigDecimal(passengerFareBreakUp.getBasePrice()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUp.setTotalPrice(String.valueOf(new BigDecimal(passengerFareBreakUp.getTotalPrice()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUp.setTaxes(String.valueOf(new BigDecimal(passengerFareBreakUp.getTaxes()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUps.add(passengerFareBreakUp);
		}
		for(int i=0;i<flightsearch.getInfant();i++)
		{
			PassengerFareBreakUp passengerFareBreakUp = new PassengerFareBreakUp();
			passengerFareBreakUp.setCurrency(apicurrency);
			passengerFareBreakUp.setId(new UID().toString());
			passengerFareBreakUp.setType("INF");

			String baseprice=String.valueOf(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantBaseFare)]).divide(new BigDecimal(flightsearch.getInfant())));

			String totaltax = String.valueOf((new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantTax)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantFuelCharges)]).
					add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantAirportTax)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantPassengerServiceFee)])
							.add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantTransactionFee)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantServiceCharges)])
									.add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantAirportDevelopmentFee)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantCuteFee)])
											.add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantConvenienceFee)]).add(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantSkyCafeMeals)]))))))))))));
			String taxprice=String.valueOf(new BigDecimal(totaltax).divide(new BigDecimal(flightsearch.getInfant())));
			String totalprice=String.valueOf(new BigDecimal(baseprice).add(new BigDecimal(taxprice)));

			passengerFareBreakUp.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(baseprice).multiply(apiToBaseExchangeRate)));
			passengerFareBreakUp.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(totalprice).multiply(apiToBaseExchangeRate)));
			passengerFareBreakUp.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(taxprice).multiply(apiToBaseExchangeRate)));
			passengerFareBreakUp.setApi_basePriceWithoutMarkup(baseprice);
			passengerFareBreakUp.setApi_totalPriceWithoutMarkup(totalprice);
			passengerFareBreakUp.setApi_taxesWithoutMarkup(taxprice);

			FlightTaxBreakUp flightTaxBreakUp = new FlightTaxBreakUp();
			flightTaxBreakUp.setYQ(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantFuelCharges)]));
			flightTaxBreakUp.setPSF(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantPassengerServiceFee)]));
			flightTaxBreakUp.setYR(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantTransactionFee)]));
			flightTaxBreakUp.setIN(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantAirportTax )]));
			flightTaxBreakUp.setUDF(new BigDecimal(faredetails[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.InfantAirportDevelopmentFee   )]));
			
			passengerFareBreakUp.setFlightTaxBreakUp(flightTaxBreakUp);
			UmarkUpServiceCall.getMarkupValuesForPassegers(flightPriceResponse,
					markupMap, airlinename, passengerFareBreakUp,
					passegers_for_marhup,"ALL");

			passengerFareBreakUp.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getBasePriceWithoutMarkup()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUp.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getTotalPriceWithoutMarkup()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUp.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUp.getTaxesWithoutMarkup()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUp.setBasePrice(String.valueOf(new BigDecimal(passengerFareBreakUp.getBasePrice()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUp.setTotalPrice(String.valueOf(new BigDecimal(passengerFareBreakUp.getTotalPrice()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUp.setTaxes(String.valueOf(new BigDecimal(passengerFareBreakUp.getTaxes()).multiply(baseToBookingExchangeRate)));
			passengerFareBreakUps.add(passengerFareBreakUp);
		}

	}

}
