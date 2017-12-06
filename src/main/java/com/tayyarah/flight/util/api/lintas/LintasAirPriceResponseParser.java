/**
@Author ilyas
16-12-2015 
TayyarahAirPriceResponseParser.java
 */
/**
 * 
 */
package com.tayyarah.flight.util.api.lintas;

import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import com.tayyarah.flight.dao.FlightTempAirSegmentDAO;
import com.tayyarah.flight.model.FareFlightSegment;
import com.tayyarah.flight.model.FlightMarkUpConfig;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.FlightSegments;
import com.tayyarah.flight.model.FlightSegmentsGroup;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.MarkupCommissionDetails;
import com.tayyarah.flight.model.PassengerFareBreakUp;
import com.tayyarah.flight.model.Segments;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.flight.util.api.travelport.UmarkUpServiceCall;
import com.travelport.api_v33.AirResponse.AirPricingInfo;
import com.travelport.api_v33.AirResponse.TypeBaseAirSegment;

public class LintasAirPriceResponseParser {	
	static Logger logger = Logger.getLogger(LintasAirPriceResponseParser.class);
	protected static Map<String, TypeBaseAirSegment> airSegMap;
	static List<AirPricingInfo> airPricingInfoList;

	public static FlightPriceResponse parseAirPrice(FlightPriceResponse flightPriceResponseOLD,
			Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap,
			Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> MapList, Flightsearch flightsearch,
			String transaction_key, FlightTempAirSegmentDAO tempDAO,BigDecimal exchangeRate,BigDecimal apiToBaseExchangeRate,MarkupCommissionDetails markupCommissionDetails) throws Exception {

		FlightDataBaseServices DBS = new FlightDataBaseServices();
		FlightPriceResponse flightPriceResponse = new FlightPriceResponse();
		try {
			Map<String, String> CityNameMap = new HashMap<String, String>();
			Map<String, String> AirportNameMap = new HashMap<String, String>();
			if (MapList.size() > 0) {
				CityNameMap = MapList.get(0);
				AirportNameMap = MapList.get(1);
			}
			String pricekey = "PLIN" +flightPriceResponseOLD.getPriceKey();
			int passegers_for_marhup = flightsearch.getAdult()
					+ flightsearch.getKid();
			flightPriceResponse.setPriceKey(pricekey);
			flightPriceResponse.setTransactionKey(transaction_key);
			flightPriceResponse.setFlightsearch(flightsearch);
			flightPriceResponse.setCountry("YET to get");
			FareFlightSegment fareFlightSegment = new FareFlightSegment();
			FareFlightSegment fareFlightSegmentOLD=flightPriceResponseOLD.getFareFlightSegment();
			String apiCurrency = fareFlightSegmentOLD.getApiCurrency();
			fareFlightSegment=fareFlightSegmentOLD;
			fareFlightSegment.setId(new UID().toString());
			fareFlightSegment.setCurrency(apiCurrency);
			fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).setBookingCurrency(flightsearch.getCurrency());
			fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).setExchangeRate(exchangeRate);
			fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegmentOLD.getBasePrice()).multiply(apiToBaseExchangeRate)));
			fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegmentOLD.getTotalPrice()).multiply(apiToBaseExchangeRate)));
			fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegmentOLD.getTaxes()).multiply(apiToBaseExchangeRate)));
			fareFlightSegment.setBookingCurrency(flightsearch.getBookedCurrency());
			fareFlightSegment.setApiCurrency(apiCurrency);
			fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
			fareFlightSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);
			fareFlightSegment.setApi_basePriceWithoutMarkup(fareFlightSegmentOLD.getBasePrice());
			fareFlightSegment.setApi_totalPriceWithoutMarkup(fareFlightSegmentOLD.getTotalPrice());
			fareFlightSegment.setApi_taxesWithoutMarkup(fareFlightSegmentOLD.getTaxes());
			UmarkUpServiceCall.getMarkupValuesFPR(flightPriceResponse,
					FlightMarkUpConfiglistMap,
					fareFlightSegmentOLD.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getCode(), fareFlightSegment,"ALL",null);

			List<PassengerFareBreakUp> passengerFareBreakUps = new ArrayList<PassengerFareBreakUp>();
			List<PassengerFareBreakUp> passengerFareBreakUpsOLD=flightPriceResponseOLD.getPassengerFareBreakUps();
			for(PassengerFareBreakUp passengerFareBreakUp:passengerFareBreakUpsOLD){
				AddPassengerFareBreakUp(passengerFareBreakUp,
						FlightMarkUpConfiglistMap, passengerFareBreakUps,
						flightPriceResponse,
						fareFlightSegmentOLD.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getCode(),
						passengerFareBreakUp.getType(), passegers_for_marhup,flightsearch, apiToBaseExchangeRate);

			}
			flightPriceResponse.setPassengerFareBreakUps(passengerFareBreakUps);
			fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			flightPriceResponse.setFareFlightSegment(fareFlightSegment);
			flightPriceResponse.setFlightMarkUpConfiglistMap(FlightMarkUpConfiglistMap);
			flightPriceResponse.setMarkupCommissionDetails(markupCommissionDetails);
			flightPriceResponse.setFinalPriceWithGST(new BigDecimal(flightPriceResponse.getFareFlightSegment().getTotalPrice()));
			flightPriceResponse.setGSTonMarkup(new BigDecimal("0.00"));
			DBS.storeAirPriceDetails(flightPriceResponse, pricekey, flightsearch,
					tempDAO);
			DBS.insertPriceKey(transaction_key, pricekey, tempDAO);
		} catch (Exception e) {			
			logger.error("Exception",e);
			e.printStackTrace();
		}
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

	public static void parseAirPriceSpecial(FlightPriceResponse flightPriceResponseOLD,
			Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap,
			Map<String, String> AirlineNameMap,
			ArrayList<Map<String, String>> MapList, Flightsearch flightsearch,
			String transaction_key, FlightTempAirSegmentDAO tempDAO,BigDecimal exchangeRate,BigDecimal apiToBaseExchangeRate,MarkupCommissionDetails markupCommissionDetails,FlightPriceResponse flightPriceResponse) throws Exception {

		FlightDataBaseServices DBS = new FlightDataBaseServices();		
		try {
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
			flightPriceResponse.setCountry("YET to get");
			FareFlightSegment fareFlightSegment = new FareFlightSegment();
			FareFlightSegment fareFlightSegmentOLD=flightPriceResponseOLD.getFareFlightSegment();
			String apiCurrency = fareFlightSegmentOLD.getApiCurrency();
			fareFlightSegment=fareFlightSegmentOLD;
			fareFlightSegment.setId(new UID().toString());
			fareFlightSegment.setCurrency(apiCurrency);
			fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).setBookingCurrency(flightsearch.getCurrency());
			fareFlightSegment.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).setExchangeRate(exchangeRate);
			fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegmentOLD.getBasePrice()).multiply(apiToBaseExchangeRate)));
			fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegmentOLD.getTotalPrice()).multiply(apiToBaseExchangeRate)));
			fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegmentOLD.getTaxes()).multiply(apiToBaseExchangeRate)));
			fareFlightSegment.setBookingCurrency(flightsearch.getBookedCurrency());
			fareFlightSegment.setApiCurrency(apiCurrency);
			fareFlightSegment.setBaseCurrency(flightsearch.getBaseCurrency());
			fareFlightSegment.setApiToBaseExchangeRate(apiToBaseExchangeRate);
			fareFlightSegment.setApi_basePriceWithoutMarkup(fareFlightSegmentOLD.getBasePrice());
			fareFlightSegment.setApi_totalPriceWithoutMarkup(fareFlightSegmentOLD.getTotalPrice());
			fareFlightSegment.setApi_taxesWithoutMarkup(fareFlightSegmentOLD.getTaxes());
			UmarkUpServiceCall.getMarkupValuesFPR(flightPriceResponse,
					FlightMarkUpConfiglistMap,
					fareFlightSegmentOLD.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getCode(), fareFlightSegment,"ALL",null);

			List<PassengerFareBreakUp> passengerFareBreakUps = new ArrayList<PassengerFareBreakUp>();
			List<PassengerFareBreakUp> passengerFareBreakUpsOLD=flightPriceResponseOLD.getPassengerFareBreakUps();
			for(PassengerFareBreakUp passengerFareBreakUp:passengerFareBreakUpsOLD){
				AddPassengerFareBreakUp(passengerFareBreakUp,
						FlightMarkUpConfiglistMap, passengerFareBreakUps,
						flightPriceResponse,
						fareFlightSegmentOLD.getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getCarrier().getCode(),
						passengerFareBreakUp.getType(), passegers_for_marhup,flightsearch, apiToBaseExchangeRate);

			}
			flightPriceResponse.setSpecialPassengerFareBreakUps(passengerFareBreakUps);
			fareFlightSegment.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getBasePriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPriceWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(fareFlightSegment.getTaxesWithoutMarkup()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setBasePrice(String.valueOf(new BigDecimal(fareFlightSegment.getBasePrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTotalPrice(String.valueOf(new BigDecimal(fareFlightSegment.getTotalPrice()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			fareFlightSegment.setTaxes(String.valueOf(new BigDecimal(fareFlightSegment.getTaxes()).multiply(flightsearch.getBaseToBookingExchangeRate())));
			flightPriceResponse.setSpecialFareFlightSegment(fareFlightSegment);
			flightPriceResponse.setFlightMarkUpConfiglistMap(FlightMarkUpConfiglistMap);
			flightPriceResponse.setMarkupCommissionDetails(markupCommissionDetails);
			flightPriceResponse.setFinalPriceWithGST(new BigDecimal(flightPriceResponse.getSpecialFareFlightSegment().getTotalPrice()).add(flightPriceResponse.getFinalPriceWithGST()));
			flightPriceResponse.setGSTonMarkupSpecial((new BigDecimal("0.00")));
		} catch (Exception e) {		
			logger.error("Exception",e);
			e.printStackTrace();
		}
		addNewFlightSegmentsGroups(flightPriceResponse,flightPriceResponse.getSpecialFareFlightSegment());
	}	

	private static void AddPassengerFareBreakUp(PassengerFareBreakUp passengerFareBreakUpOLD,
			Map<String,List<FlightMarkUpConfig>> FlightMarkUpConfiglistMap,
			List<PassengerFareBreakUp> passengerFareBreakUps,
			FlightPriceResponse flightPriceResponse, String airlinename,
			String passengerType, int passegers_for_marhup,Flightsearch flightsearch,BigDecimal apiToBaseExchangeRate) throws Exception {
		PassengerFareBreakUp passengerFareBreakUp = new PassengerFareBreakUp();
		String apicurrency = flightsearch.getCurrency();
		BigDecimal baseToBookingExchangeRate=flightsearch.getBaseToBookingExchangeRate();	
		String taxinAPI=passengerFareBreakUpOLD.getTaxDescription();
		String temp=taxinAPI;
		StringBuilder sb1=new StringBuilder();
		while(temp.length()>2){
			String fullValue=temp.substring(0,temp.indexOf(";"));
			String tax=fullValue.substring(fullValue.indexOf(":")+1);
			String taxCode=fullValue.substring(0,fullValue.indexOf(":"));
			BigDecimal taxINAPi=new BigDecimal(tax).multiply(apiToBaseExchangeRate).multiply(baseToBookingExchangeRate);
			sb1.append(taxCode+":"+taxINAPi+";");
			temp=temp.substring(fullValue.length()+1 );
		}		
		passengerFareBreakUp.setTaxDescription(sb1.toString());		
		passengerFareBreakUp.setCurrency(apicurrency);
		passengerFareBreakUp.setId(new UID().toString());
		passengerFareBreakUp.setType(passengerType);
		passengerFareBreakUp.setBasePriceWithoutMarkup(passengerFareBreakUpOLD.getBasePrice());
		passengerFareBreakUp.setTotalPriceWithoutMarkup(passengerFareBreakUpOLD.getTotalPrice());
		passengerFareBreakUp.setTaxesWithoutMarkup(passengerFareBreakUpOLD.getTaxes());	
		passengerFareBreakUp.setBasePriceWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUpOLD.getBasePrice()).multiply(apiToBaseExchangeRate)));
		passengerFareBreakUp.setTotalPriceWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUpOLD.getTotalPrice()).multiply(apiToBaseExchangeRate)));
		passengerFareBreakUp.setTaxesWithoutMarkup(String.valueOf(new BigDecimal(passengerFareBreakUpOLD.getTaxes()).multiply(apiToBaseExchangeRate)));
		passengerFareBreakUp.setApi_basePriceWithoutMarkup(passengerFareBreakUpOLD.getBasePrice());
		passengerFareBreakUp.setApi_totalPriceWithoutMarkup(passengerFareBreakUpOLD.getTotalPrice());
		passengerFareBreakUp.setApi_taxesWithoutMarkup(passengerFareBreakUpOLD.getTaxes());
		UmarkUpServiceCall.getMarkupValuesForPassegers(flightPriceResponse,
				FlightMarkUpConfiglistMap, airlinename, passengerFareBreakUp,
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