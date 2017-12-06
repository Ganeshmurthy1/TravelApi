/**
@Author ilyas
05-10-2015 S
LowFareSearchRequestBuilder.java
 */
/**
 * 
 */
package com.tayyarah.flight.util.api.bluestar;

import java.math.BigInteger;


import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.tayyarah.apiconfig.model.BluestarConfig;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.util.FlightWebServiceEndPointValidator;

import com.travelport.api_v33.AirResponse.AirSearchModifiers;
import com.travelport.api_v33.AirResponse.AirSearchModifiers.PreferredProviders;
import com.travelport.api_v33.AirResponse.Airport;
import com.travelport.api_v33.AirResponse.BaseCoreReq;
import com.travelport.api_v33.AirResponse.BaseLowFareSearchReq;
import com.travelport.api_v33.AirResponse.BaseSearchReq;
import com.travelport.api_v33.AirResponse.BillingPointOfSaleInfo;

import com.travelport.api_v33.AirResponse.CityOrAirport;
import com.travelport.api_v33.AirResponse.Distance;

import com.travelport.api_v33.AirResponse.Provider;
import com.travelport.api_v33.AirResponse.SearchAirLeg;
import com.travelport.api_v33.AirResponse.SearchPassenger;

import com.travelport.api_v33.AirResponse.TypeSearchLocation;


public class BluestarGetFlightAvailibilityRequestBuilder {

	static final Logger logger=Logger.getLogger(BluestarGetFlightAvailibilityRequestBuilder.class);



	public static StringBuilder createSearchRequest(Flightsearch flightsearch,BluestarConfig bluestarConfig) throws ClassNotFoundException, JAXBException
	{
		
		String headerSTR = setHeader(bluestarConfig);	
		String soapEnv = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
				"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
				headerSTR+"<soap:Body><tem:GetFlightAvailibility> <tem:strRequestXML><![CDATA[";
		String classtype = "Y";
		if(flightsearch.getCabinClass().equalsIgnoreCase("Economy")){
			classtype = "Y";
		}
		else if(flightsearch.getCabinClass().equalsIgnoreCase("Business")){
			classtype = "C";
		}
		else if(flightsearch.getCabinClass().equalsIgnoreCase("First")){
			classtype = "F";
		} 
		else{
			classtype="Y";
		}
		String TripType = "1";
		String arvlDAte = "";
		String SpecialFare = "0";
		if(flightsearch.getTripType().equalsIgnoreCase("R")){
			SpecialFare="1";
			TripType="2";
			arvlDAte = FlightWebServiceEndPointValidator.getBluestarDate(flightsearch.getArvlDate());
		}
		String body="<GetFlightAvailibilityRequest><NoofAdult>"+flightsearch.getAdult()+"</NoofAdult><NoofChild>"+flightsearch.getKid()+"</NoofChild><NoofInfant>"+flightsearch.getInfant()+"</NoofInfant><FromAirportCode>"+flightsearch.getOrigin()+"</FromAirportCode><ToAirportCode>"+flightsearch.getDestination()+"</ToAirportCode>"+
				"<DepartureDate>"+FlightWebServiceEndPointValidator.getBluestarDate(flightsearch.getDepDate())+"</DepartureDate><ReturnDate>"+arvlDAte+"</ReturnDate><TripType>"+TripType+"</TripType><FlightClass>"+classtype+"</FlightClass><SpecialFare>"+SpecialFare+"</SpecialFare></GetFlightAvailibilityRequest>";
		String closeSoapStr = "]]></tem:strRequestXML></tem:GetFlightAvailibility> </soap:Body></soap:Envelope>";
		StringBuilder sdb = new StringBuilder(soapEnv+body+closeSoapStr);
		logger.info("createSearchRequest sdb: "+sdb);
		return sdb;
	}


	public static StringBuilder createVerifyFlightDetailRequestOneway(String trackNO,BluestarConfig bluestarConfig) throws ClassNotFoundException, JAXBException
	{
		String headerSTR = setHeader(bluestarConfig);
		String soapEnv = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
				"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
				headerSTR+"<soap:Body><tem:VerifyFlightDetail>   <tem:requestXML><![CDATA[";
		String body="<VerifyFlightDetailRequest><TrackNo>"+trackNO+"</TrackNo></VerifyFlightDetailRequest>";	
		String closeSoapStr = "]]></tem:requestXML></tem:VerifyFlightDetail> </soap:Body></soap:Envelope>";
		StringBuilder sdb = new StringBuilder();
		sdb.append(soapEnv+body+closeSoapStr);
		logger.info("createSearchRequest sdb: "+sdb);
		return sdb;
	}
	
	public static StringBuilder createVerifyFlightDetailRequestOnewaySpecial(String trackNO,BluestarConfig bluestarConfig) throws ClassNotFoundException, JAXBException
	{	
		String headerSTR = setHeader(bluestarConfig);
		String soapEnv = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
				"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
				headerSTR+"<soap:Body><tem:VerifyFlightDetail>   <tem:requestXML><![CDATA[";
		String body="<VerifyFlightDetailRequest><TrackNo>"+trackNO+"</TrackNo></VerifyFlightDetailRequest>";	
		String closeSoapStr = "]]></tem:requestXML></tem:VerifyFlightDetail> </soap:Body></soap:Envelope>";
		StringBuilder sdb = new StringBuilder();
		sdb.append(soapEnv+body+closeSoapStr);
		logger.info("createSearchRequest sdb: "+sdb);
		return sdb;
	}
	
	public static  String setHeader(BluestarConfig bluestarConfig){
		StringBuilder sb=new StringBuilder();		
		sb.append("<soap:Header><tem:Authenticate><tem:InterfaceCode>"+bluestarConfig.getInterface_Code()+"</tem:InterfaceCode><tem:InterfaceAuthKey>"+bluestarConfig.getInterface_Auth_Key()+"</tem:InterfaceAuthKey><tem:AgentCode>"+bluestarConfig.getAgent_Code()+"</tem:AgentCode></tem:Authenticate></soap:Header>");
		return sb.toString();
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
}