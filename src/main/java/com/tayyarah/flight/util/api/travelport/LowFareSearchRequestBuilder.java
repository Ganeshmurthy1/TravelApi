/**
@Author yogeshsharma
15-Jul-2015 2015
LowFareSearchRequestBuilder.java
 */
/**
 * 
 */
package com.tayyarah.flight.util.api.travelport;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.tayyarah.apiconfig.model.TravelportConfig;
import com.tayyarah.common.util.soap.MarshalUnmarshal;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.MulticityFlightSearch;
import com.travelport.api_v33.AirResponse.AirLegModifiers;
import com.travelport.api_v33.AirResponse.AirPricingModifiers;
import com.travelport.api_v33.AirResponse.AirSearchModifiers;
import com.travelport.api_v33.AirResponse.AirSearchModifiers.PreferredProviders;
import com.travelport.api_v33.AirResponse.Airport;
import com.travelport.api_v33.AirResponse.BaseCoreReq;
import com.travelport.api_v33.AirResponse.BaseLowFareSearchReq;
import com.travelport.api_v33.AirResponse.BaseSearchReq;
import com.travelport.api_v33.AirResponse.BillingPointOfSaleInfo;
import com.travelport.api_v33.AirResponse.CabinClass;
import com.travelport.api_v33.AirResponse.CityOrAirport;
import com.travelport.api_v33.AirResponse.Distance;
import com.travelport.api_v33.AirResponse.LowFareSearchReq;
import com.travelport.api_v33.AirResponse.PreferredCabins;
import com.travelport.api_v33.AirResponse.PreferredCarriers;
import com.travelport.api_v33.AirResponse.Provider;
import com.travelport.api_v33.AirResponse.SearchAirLeg;
import com.travelport.api_v33.AirResponse.SearchPassenger;
import com.travelport.api_v33.AirResponse.TypeFaresIndicator;
import com.travelport.api_v33.AirResponse.TypeFlexibleTimeSpec;
import com.travelport.api_v33.AirResponse.TypeSearchLocation;


public class LowFareSearchRequestBuilder {
	static final Logger logger = Logger.getLogger(LowFareSearchRequestBuilder.class);
	
	public static StringBuilder createSearchRequest(TravelportConfig travelportConfig,Flightsearch flightsearch, String airlineFromList) throws ClassNotFoundException, JAXBException
	{
		LowFareSearchReq lowFareSearchReq = new LowFareSearchReq();
		lowFareSearchReq.setTargetBranch(travelportConfig.getTargetBranch());
		addPointOfSale(lowFareSearchReq,"uAPI");
		List<SearchAirLeg> searchAirLegs = lowFareSearchReq.getSearchAirLeg();
		if(flightsearch.getTripType().equalsIgnoreCase("O"))
		{
			SearchAirLeg searchAirLeg = createSearchLeg(flightsearch.getOrigin(),flightsearch.getDestination());
			addSearchDepartureDate(searchAirLeg, flightsearch.getDepDate(), flightsearch);
			addSearchCabinPreferred(searchAirLeg,flightsearch, airlineFromList);
			searchAirLegs.add(searchAirLeg);
		}else if(flightsearch.getTripType().equalsIgnoreCase("R"))
		{
			SearchAirLeg searchAirLeg = createSearchLeg(flightsearch.getOrigin(),flightsearch.getDestination());
			addSearchDepartureDate(searchAirLeg, flightsearch.getDepDate(), flightsearch);
			addSearchCabinPreferred(searchAirLeg,flightsearch, airlineFromList);
			searchAirLegs.add(searchAirLeg);

			SearchAirLeg searchAirLegOut = createSearchLeg(flightsearch.getDestination(),flightsearch.getOrigin());
			addSearchDepartureDate(searchAirLegOut, flightsearch.getArvlDate(), flightsearch);
			addSearchCabinPreferred(searchAirLegOut,flightsearch, airlineFromList);
			searchAirLegs.add(searchAirLegOut);
		}else if(flightsearch.getTripType().equalsIgnoreCase("M"))
		{
			MulticityFlightSearch MFS = flightsearch.getMFS();
			for(int i=1;i<=Integer.parseInt(flightsearch.getTrips());i++){	
				if(i==1){
					SearchAirLeg searchAirLeg = createSearchLeg(MFS.getOrigin1(),MFS.getDest1());
					addSearchDepartureDate(searchAirLeg, MFS.getDepartDate1(), flightsearch);
					addSearchCabinPreferred(searchAirLeg,flightsearch, airlineFromList);
					searchAirLegs.add(searchAirLeg);
				}else if(i==2){
					SearchAirLeg searchAirLeg = createSearchLeg(MFS.getOrigin2(),MFS.getDest2());
					addSearchDepartureDate(searchAirLeg, MFS.getDepartDate2(), flightsearch);
					addSearchCabinPreferred(searchAirLeg,flightsearch,airlineFromList);
					searchAirLegs.add(searchAirLeg);
				} else if(i==3){
					SearchAirLeg searchAirLeg = createSearchLeg(MFS.getOrigin3(),MFS.getDest3());
					addSearchDepartureDate(searchAirLeg, MFS.getDepartDate3(), flightsearch);
					addSearchCabinPreferred(searchAirLeg,flightsearch,airlineFromList);
					searchAirLegs.add(searchAirLeg);
				} else if(i==4){
					SearchAirLeg searchAirLeg = createSearchLeg(MFS.getOrigin4(),MFS.getDest4());
					addSearchDepartureDate(searchAirLeg, MFS.getDepartDate4(), flightsearch);
					addSearchCabinPreferred(searchAirLeg,flightsearch,airlineFromList);
					searchAirLegs.add(searchAirLeg);
				}
			}
		}	
		if(travelportConfig.isTest()){
			lowFareSearchReq.setAirSearchModifiers(createModifiersWithProviders("1G","ACH"));//for pre productin
		}else{
			AirPricingModifiers airPricingModifiers = new AirPricingModifiers();//for live
			airPricingModifiers.setFaresIndicator(TypeFaresIndicator.PUBLIC_AND_PRIVATE_FARES);
			lowFareSearchReq.setAirPricingModifiers(airPricingModifiers);
		}
		if(flightsearch.getAdult()>0)
			addPassengers(lowFareSearchReq, flightsearch.getAdult(), "ADT");
		if(flightsearch.getKid()>0)
			addPassengers(lowFareSearchReq, flightsearch.getKid(), "CHD");
		if(flightsearch.getInfant()>0)
			addPassengers(lowFareSearchReq, flightsearch.getInfant(), "INF");

		MarshalUnmarshal marshalUnmarshal=new MarshalUnmarshal();
		String requestStr= marshalUnmarshal.marshal(lowFareSearchReq,LowFareSearchReq.class);
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
		String soapEnv = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
				"<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
				"<SOAP-ENV:Header/><SOAP-ENV:Body>";
		String closeSoapStr = " </SOAP-ENV:Body></SOAP-ENV:Envelope>";
		requestStr = requestStr.replace(xmlStr, soapEnv);
		requestStr+=closeSoapStr;
		StringBuilder sdb = new StringBuilder(requestStr);
		logger.info("createSearchRequest sdb: "+sdb);
		return sdb;
	}
	
	public static void addPointOfSale(BaseSearchReq req, String appName ) {
		BillingPointOfSaleInfo posInfo = new BillingPointOfSaleInfo();
		posInfo.setOriginApplication(appName);
		req.setBillingPointOfSaleInfo(posInfo);
	}

	public static void addPointOfSale(BaseCoreReq req, String appName ) {
		BillingPointOfSaleInfo posInfo = new BillingPointOfSaleInfo();
		posInfo.setOriginApplication(appName);
		req.setBillingPointOfSaleInfo(posInfo);
	}

	public static void addPassengers(BaseLowFareSearchReq baseLowFareSearchReq, int paxCount,String typeCode) {
		for (int i = 0; i < paxCount; ++i) {
			SearchPassenger adult = new SearchPassenger();
			adult.setCode(typeCode);
			baseLowFareSearchReq.getSearchPassenger().add(adult);
		}
	}
	public static SearchAirLeg createLeg(String originAirportCode,
			String destAirportCode) {
		TypeSearchLocation originLoc = new TypeSearchLocation();
		TypeSearchLocation destLoc = new TypeSearchLocation();
		Airport origin = new Airport(), dest = new Airport();
		origin.setCode(originAirportCode);
		dest.setCode(destAirportCode);
		originLoc.setAirport(origin);
		destLoc.setAirport(dest);
		return createLeg(originLoc, destLoc);
	}
	public static SearchAirLeg createLeg(TypeSearchLocation originLoc,
			TypeSearchLocation destLoc) {
		SearchAirLeg leg = new SearchAirLeg();
		leg.getSearchDestination().add(destLoc);
		leg.getSearchOrigin().add(originLoc);
		return leg;
	}

	public static TypeSearchLocation createLocationNear(String cityOrAirportCode) {
		TypeSearchLocation result = new TypeSearchLocation();
		CityOrAirport place = new CityOrAirport();
		place.setCode(cityOrAirportCode);
		place.setPreferCity(true);
		result.setCityOrAirport(place);
		Distance dist = new Distance();
		dist.setUnits("mi");
		dist.setValue(BigInteger.valueOf(100));
		result.setDistance(dist);
		return result;
	}

	public static AirSearchModifiers createModifiersWithProviders(String ... providerCode) {
		AirSearchModifiers airSearchModifiers = new AirSearchModifiers();
		PreferredProviders preferredProviders = new PreferredProviders();
		for (int i=0; i<providerCode.length;++i) {
			Provider provider = new Provider();
			provider.setCode(providerCode[i]);
			preferredProviders.getProvider().add(provider);
		}
		airSearchModifiers.setPreferredProviders(preferredProviders);
		return airSearchModifiers;
	}

	public static SearchAirLeg createSearchLeg(String originAirportCode, String destAirportCode) {
		TypeSearchLocation originLoc = new TypeSearchLocation();
		TypeSearchLocation destLoc = new TypeSearchLocation();
		Airport origin = new Airport(), dest = new Airport();
		origin.setCode(originAirportCode);
		dest.setCode(destAirportCode);
		originLoc.setAirport(origin);
		destLoc.setAirport(dest);
		return createSearchLeg(originLoc, destLoc);
	}

	private static SearchAirLeg createSearchLeg(TypeSearchLocation originLoc,
			TypeSearchLocation destLoc) {
		SearchAirLeg leg = new SearchAirLeg();
		leg.getSearchDestination().add(destLoc);
		leg.getSearchOrigin().add(originLoc);

		return leg;
	}

	public static void addSearchDepartureDate(SearchAirLeg searchAirLeg,
			String departureDate,Flightsearch flightsearch) {
		TypeFlexibleTimeSpec typeFlexibleTimeSpec = new TypeFlexibleTimeSpec();
		if(flightsearch.isCal_search()==true && !flightsearch.getTripType().equalsIgnoreCase("M")){
			com.travelport.api_v33.AirResponse.TypeFlexibleTimeSpec.SearchExtraDays searchExtraDays=new com.travelport.api_v33.AirResponse.TypeFlexibleTimeSpec.SearchExtraDays();
			searchExtraDays.setDaysBefore(3);
			searchExtraDays.setDaysAfter(3);
			typeFlexibleTimeSpec.setSearchExtraDays(searchExtraDays);
		}
		typeFlexibleTimeSpec.setPreferredTime(departureDate);
		searchAirLeg.getSearchDepTime().add(typeFlexibleTimeSpec);
	}

	public static void addSearchCabinPreferred(SearchAirLeg searchAirLeg, Flightsearch flightsearch,String airlineFromList) {
		AirLegModifiers airLegModifiers = new AirLegModifiers();
		PreferredCabins preferredCabins = new PreferredCabins();
		CabinClass cabinClass = new CabinClass();
		if(flightsearch.getCabinClass().equalsIgnoreCase("F"))
			cabinClass.setType("FIRST");
		else if(flightsearch.getCabinClass().equalsIgnoreCase("C"))
			cabinClass.setType("BUSINESS");
		else if(flightsearch.getCabinClass().equalsIgnoreCase("W"))
			cabinClass.setType("PREMIUM_ECONOMY");
		else  // E 
			cabinClass.setType("Economy");
		preferredCabins.setCabinClass(cabinClass);
		airLegModifiers.setPreferredCabins(preferredCabins);
		// set airline now 
		if(airlineFromList!=null && !airlineFromList.equalsIgnoreCase(""))
		{
			addSearchPreferredAirline(airLegModifiers, flightsearch,airlineFromList);
		}
		else if(!flightsearch.getAirline().equals("All"))
		{
			addSearchPreferredAirline(airLegModifiers, flightsearch,airlineFromList);		
		}
		searchAirLeg.setAirLegModifiers(airLegModifiers);
	}

	public static void addSearchPreferredAirline(AirLegModifiers airLegModifiers, Flightsearch flightsearch, String airlineFromList) {
		PreferredCarriers prePreferredCarriers  = new PreferredCarriers();
		if(airlineFromList!=null && !airlineFromList.equalsIgnoreCase(""))
		{
			com.travelport.api_v33.AirResponse.Carrier carrier=new com.travelport.api_v33.AirResponse.Carrier();
			carrier.setCode(airlineFromList);
			prePreferredCarriers.getCarrier().add(carrier);
		}		
		else if(!flightsearch.getAirline().equals("All")){
			if(flightsearch.getAirline().indexOf(",")==-1){
				com.travelport.api_v33.AirResponse.Carrier carrier=new com.travelport.api_v33.AirResponse.Carrier();
				carrier.setCode(flightsearch.getAirline());
				prePreferredCarriers.getCarrier().add(carrier);
			}else{
				String temp=flightsearch.getAirline();
				while(temp.length()>0){
					String AirlineCode=temp.substring(0,temp.indexOf(","));
					com.travelport.api_v33.AirResponse.Carrier carrier=new com.travelport.api_v33.AirResponse.Carrier();
					carrier.setCode(AirlineCode);
					prePreferredCarriers.getCarrier().add(carrier);
					temp=temp.substring(AirlineCode.length()+1);
					if(temp.indexOf(",")==-1){
						com.travelport.api_v33.AirResponse.Carrier carrier1=new com.travelport.api_v33.AirResponse.Carrier();
						carrier1.setCode(temp);
						prePreferredCarriers.getCarrier().add(carrier1);
						temp="";
					}
				}
			}
		}
		airLegModifiers.setPreferredCarriers(prePreferredCarriers);
	}
}