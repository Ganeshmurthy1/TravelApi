package com.tayyarah.flight.util.api.travelport;

import java.math.BigInteger;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;

import com.tayyarah.apiconfig.model.TravelportConfig;
import com.tayyarah.common.util.soap.MarshalUnmarshal;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.UapiAirSegment;
import com.travelport.api_v33.AirResponse.AirItinerary;
import com.travelport.api_v33.AirResponse.AirLegModifiers;
import com.travelport.api_v33.AirResponse.AirPriceReq;
import com.travelport.api_v33.AirResponse.AirPricingCommand;
import com.travelport.api_v33.AirResponse.AirPricingModifiers;
import com.travelport.api_v33.AirResponse.AirSearchModifiers;
import com.travelport.api_v33.AirResponse.AirSegmentPricingModifiers;
import com.travelport.api_v33.AirResponse.Airport;
import com.travelport.api_v33.AirResponse.BaseCoreReq;
import com.travelport.api_v33.AirResponse.BillingPointOfSaleInfo;
import com.travelport.api_v33.AirResponse.BrandModifiers;
import com.travelport.api_v33.AirResponse.CabinClass;
import com.travelport.api_v33.AirResponse.CityOrAirport;
import com.travelport.api_v33.AirResponse.Distance;
import com.travelport.api_v33.AirResponse.FormOfPayment;
import com.travelport.api_v33.AirResponse.PreferredCabins;
import com.travelport.api_v33.AirResponse.Provider;
import com.travelport.api_v33.AirResponse.SearchAirLeg;
import com.travelport.api_v33.AirResponse.SearchPassenger;
import com.travelport.api_v33.AirResponse.TypeBaseAirSegment;
import com.travelport.api_v33.AirResponse.TypeFlexibleTimeSpec;
import com.travelport.api_v33.AirResponse.TypeSearchLocation;
import com.travelport.api_v33.AirResponse.AirSearchModifiers.PreferredProviders;

public class AirPriceRequestBuilder {
	static final Logger logger = Logger.getLogger(AirPriceRequestBuilder.class);

	public static StringBuilder createAirpriceRequest(
			TravelportConfig travelportConfig, String flightindex,
			LinkedHashMap<String, List<UapiAirSegment>> UapiAirSegmentListMap,
			Flightsearch flightsearch) throws ClassNotFoundException,
			JAXBException {

		AirPriceReq airPriceReq = new AirPriceReq();
		airPriceReq.setTargetBranch(travelportConfig.getTargetBranch());
		addPointOfSale(airPriceReq, "uAPI");
		AirItinerary airItinerary = new AirItinerary();		
		List<TypeBaseAirSegment> airsegmentlist = new ArrayList<TypeBaseAirSegment>();
		List<String> fligtindexlist = getfligtindexlist(flightindex);		
		List<String> keylist = new ArrayList<String>();
		List<String> farebasiscodelist = new ArrayList<String>();
		for (String newflightindex : fligtindexlist) {			
			List<UapiAirSegment> uapiAirSegments = UapiAirSegmentListMap.get(newflightindex);			
			for (int i = 0; i < uapiAirSegments.size(); i++) {
				UapiAirSegment uapiAirSegment = uapiAirSegments.get(i);
				TypeBaseAirSegment typeBaseAirSegment = new TypeBaseAirSegment();
				typeBaseAirSegment.setKey(uapiAirSegment.getKey());
				typeBaseAirSegment.setAvailabilitySource(uapiAirSegment
						.getAvailabilitySource());
				typeBaseAirSegment.setAvailabilityDisplayType(uapiAirSegment
						.getAvailabilityDisplayType());
				typeBaseAirSegment.setGroup(uapiAirSegment.getGroup());
				typeBaseAirSegment.setCarrier(uapiAirSegment.getCarrier());
				typeBaseAirSegment.setFlightNumber(uapiAirSegment
						.getFlightNumber());
				typeBaseAirSegment.setOrigin(uapiAirSegment.getOrigin());
				typeBaseAirSegment.setDestination(uapiAirSegment
						.getDestination());
				typeBaseAirSegment.setDepartureTime(uapiAirSegment
						.getDepartureTime());
				typeBaseAirSegment.setArrivalTime(uapiAirSegment
						.getArrivalTime());
				typeBaseAirSegment
						.setFlightTime(uapiAirSegment.getFlightTime());
				typeBaseAirSegment.setDistance(uapiAirSegment.getDistance());
				typeBaseAirSegment.setProviderCode(uapiAirSegment
						.getProvidecode());
				typeBaseAirSegment.setClassOfService(uapiAirSegment
						.getClassOfService());
				airsegmentlist.add(typeBaseAirSegment);
				keylist.add(uapiAirSegment.getKey());
				farebasiscodelist.add(uapiAirSegment.getFarebasiscode());
				airItinerary.getAirSegment().add(typeBaseAirSegment);
			}
		}
		airPriceReq.setAirItinerary(airItinerary);
		AirPricingModifiers airPricingModifiers = new AirPricingModifiers();		
		BrandModifiers BM = new BrandModifiers();
		BM.setModifierType("FareFamilyDisplay");
		airPricingModifiers.setBrandModifiers(BM);
		airPriceReq.setAirPricingModifiers(airPricingModifiers);	
		List<SearchPassenger> searchPassengerlist = new ArrayList<SearchPassenger>();
		if (flightsearch.getAdult() > 0) {
			addPassengers(searchPassengerlist, flightsearch.getAdult(), "ADT",
					new BigInteger("40"), airPriceReq);
		}
		if (flightsearch.getKid() > 0) {
			addPassengers(searchPassengerlist, flightsearch.getKid(), "CNN",
					new BigInteger("8"), airPriceReq);
		}
		if (flightsearch.getAdult() > 0) {
			addPassengers(searchPassengerlist, flightsearch.getInfant(), "INF",
					new BigInteger("1"), airPriceReq);
		}

		AirPricingCommand airPricingCommand = new AirPricingCommand();
		int k = 0;
		for (String newflightindex : fligtindexlist) {
			List<UapiAirSegment> uapiAirSegments = UapiAirSegmentListMap.get(newflightindex);
			for (int i = 0; i < uapiAirSegments.size(); i++) {
				String farebasiscode = farebasiscodelist.get(k);
				String key = keylist.get(k);
				AirSegmentPricingModifiers airSegmentPricingModifiers = new AirSegmentPricingModifiers();
				airSegmentPricingModifiers.setAirSegmentRef(key);
				airSegmentPricingModifiers.setFareBasisCode(farebasiscode);
				airPricingCommand.getAirSegmentPricingModifiers().add(
						airSegmentPricingModifiers);
				k++;
			}
		}
		airPriceReq.getAirPricingCommand().add(airPricingCommand);
		FormOfPayment FOP = new FormOfPayment();
		FOP.setType("Credit");
		airPriceReq.getFormOfPayment().add(FOP);
		MarshalUnmarshal marshalUnmarshal = new MarshalUnmarshal();
		String requestStr = marshalUnmarshal.marshal(airPriceReq,
				AirPriceReq.class);
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
		String soapEnv = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<SOAP-ENV:Header/><SOAP-ENV:Body>";
		String closeSoapStr = " </SOAP-ENV:Body></SOAP-ENV:Envelope>";
		requestStr = requestStr.replace(xmlStr, soapEnv);
		requestStr += closeSoapStr;
		StringBuilder sdb = new StringBuilder(requestStr);
		logger.info("Price Request :\n" + sdb);
		return sdb;
	}
	
	public static StringBuilder createAirpriceRequestSpecial(
			TravelportConfig travelportConfig, String flightindex,
			LinkedHashMap<String, List<UapiAirSegment>> UapiAirSegmentListMap,
			Flightsearch flightsearch) throws ClassNotFoundException,
			JAXBException {

		AirPriceReq airPriceReq = new AirPriceReq();
		airPriceReq.setTargetBranch(travelportConfig.getTargetBranch());
		addPointOfSale(airPriceReq, "uAPI");
		AirItinerary airItinerary = new AirItinerary();		
		List<TypeBaseAirSegment> airsegmentlist = new ArrayList<TypeBaseAirSegment>();		
		List<String> keylist = new ArrayList<String>();
		List<String> farebasiscodelist = new ArrayList<String>();		
			List<UapiAirSegment> uapiAirSegments = UapiAirSegmentListMap.get(flightindex);
			for (int i = 0; i < uapiAirSegments.size(); i++) {
				UapiAirSegment uapiAirSegment = uapiAirSegments.get(i);
				TypeBaseAirSegment typeBaseAirSegment = new TypeBaseAirSegment();
				typeBaseAirSegment.setKey(uapiAirSegment.getKey());
				typeBaseAirSegment.setAvailabilitySource(uapiAirSegment
						.getAvailabilitySource());
				typeBaseAirSegment.setAvailabilityDisplayType(uapiAirSegment
						.getAvailabilityDisplayType());
				typeBaseAirSegment.setGroup(uapiAirSegment.getGroup());
				typeBaseAirSegment.setCarrier(uapiAirSegment.getCarrier());
				typeBaseAirSegment.setFlightNumber(uapiAirSegment
						.getFlightNumber());
				typeBaseAirSegment.setOrigin(uapiAirSegment.getOrigin());
				typeBaseAirSegment.setDestination(uapiAirSegment
						.getDestination());
				typeBaseAirSegment.setDepartureTime(uapiAirSegment
						.getDepartureTime());
				typeBaseAirSegment.setArrivalTime(uapiAirSegment
						.getArrivalTime());
				typeBaseAirSegment
						.setFlightTime(uapiAirSegment.getFlightTime());
				typeBaseAirSegment.setDistance(uapiAirSegment.getDistance());
				typeBaseAirSegment.setProviderCode(uapiAirSegment
						.getProvidecode());
				typeBaseAirSegment.setClassOfService(uapiAirSegment
						.getClassOfService());
				airsegmentlist.add(typeBaseAirSegment);
				keylist.add(uapiAirSegment.getKey());
				farebasiscodelist.add(uapiAirSegment.getFarebasiscode());
				airItinerary.getAirSegment().add(typeBaseAirSegment);
			}
		airPriceReq.setAirItinerary(airItinerary);
		AirPricingModifiers airPricingModifiers = new AirPricingModifiers();	
		BrandModifiers BM = new BrandModifiers();
		BM.setModifierType("FareFamilyDisplay");
		airPricingModifiers.setBrandModifiers(BM);
		airPriceReq.setAirPricingModifiers(airPricingModifiers);	
		List<SearchPassenger> searchPassengerlist = new ArrayList<SearchPassenger>();
		if (flightsearch.getAdult() > 0) {
			addPassengers(searchPassengerlist, flightsearch.getAdult(), "ADT",
					new BigInteger("40"), airPriceReq);
		}
		if (flightsearch.getKid() > 0) {
			addPassengers(searchPassengerlist, flightsearch.getKid(), "CNN",
					new BigInteger("8"), airPriceReq);
		}
		if (flightsearch.getAdult() > 0) {
			addPassengers(searchPassengerlist, flightsearch.getInfant(), "INF",
					new BigInteger("1"), airPriceReq);
		}

		AirPricingCommand airPricingCommand = new AirPricingCommand();
		int k = 0;	
			for (int i = 0; i < uapiAirSegments.size(); i++) {
				String farebasiscode = farebasiscodelist.get(k);
				String key = keylist.get(k);
				AirSegmentPricingModifiers airSegmentPricingModifiers = new AirSegmentPricingModifiers();
				airSegmentPricingModifiers.setAirSegmentRef(key);
				airSegmentPricingModifiers.setFareBasisCode(farebasiscode);
				airPricingCommand.getAirSegmentPricingModifiers().add(
						airSegmentPricingModifiers);
				k++;
			}		
		airPriceReq.getAirPricingCommand().add(airPricingCommand);
		FormOfPayment FOP = new FormOfPayment();
		FOP.setType("Credit");
		airPriceReq.getFormOfPayment().add(FOP);
		MarshalUnmarshal marshalUnmarshal = new MarshalUnmarshal();
		String requestStr = marshalUnmarshal.marshal(airPriceReq,
				AirPriceReq.class);
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
		String soapEnv = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<SOAP-ENV:Header/><SOAP-ENV:Body>";
		String closeSoapStr = " </SOAP-ENV:Body></SOAP-ENV:Envelope>";
		requestStr = requestStr.replace(xmlStr, soapEnv);
		requestStr += closeSoapStr;
		StringBuilder sdb = new StringBuilder(requestStr);
		logger.info("Price Request :\n" + sdb);
		return sdb;
	}

	public static void addPointOfSale(BaseCoreReq req, String appName) {
		BillingPointOfSaleInfo posInfo = new BillingPointOfSaleInfo();
		posInfo.setOriginApplication(appName);
		req.setBillingPointOfSaleInfo(posInfo);
	}

	public static void addPassengers(List<SearchPassenger> searchPassengerlist,
			int passcount, String typeCode, BigInteger age,
			AirPriceReq airPriceReq) {
		for (int i = 0; i < passcount; ++i) {
			SearchPassenger searchPassenger = new SearchPassenger();
			searchPassenger.setCode(typeCode);
			searchPassenger.setAge(age);
			searchPassenger.setKey((new UID()).toString());
			searchPassenger.setBookingTravelerRef((new UID()).toString());
			searchPassengerlist.add(searchPassenger);
			airPriceReq.getSearchPassenger().add(searchPassenger);
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

	public static AirSearchModifiers createModifiersWithProviders(
			String... providerCode) {
		AirSearchModifiers airSearchModifiers = new AirSearchModifiers();
		PreferredProviders preferredProviders = new PreferredProviders();
		for (int i = 0; i < providerCode.length; ++i) {
			Provider provider = new Provider();
			provider.setCode(providerCode[i]);
			preferredProviders.getProvider().add(provider);
		}
		airSearchModifiers.setPreferredProviders(preferredProviders);
		return airSearchModifiers;
	}

	public static SearchAirLeg createSearchLeg(String originAirportCode,
			String destAirportCode) {
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

	
	public static void addSearchCabinPreferred(SearchAirLeg searchAirLeg,
			Flightsearch flightsearch) {
		if (!flightsearch.getCabinClass().equalsIgnoreCase("Economy")) {
			AirLegModifiers airLegModifiers = new AirLegModifiers();
			PreferredCabins preferredCabins = new PreferredCabins();
			CabinClass cabinClass = new CabinClass();
			cabinClass.setType(flightsearch.getCabinClass());
			preferredCabins.setCabinClass(cabinClass);
			airLegModifiers.setPreferredCabins(preferredCabins);
			searchAirLeg.setAirLegModifiers(airLegModifiers);
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
}