package com.tayyarah.flight.util.api.travelport;

import java.math.BigInteger;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.tayyarah.apiconfig.model.TravelportConfig;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.util.soap.MarshalUnmarshal;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.model.FareFlightSegment;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.FlightSegments;
import com.tayyarah.flight.model.FlightSegmentsGroup;
import com.tayyarah.flight.model.Segments;
import com.travelport.api_v33.Universal.ActionStatus;
import com.travelport.api_v33.Universal.AirCreateReservationReq;
import com.travelport.api_v33.Universal.AirPricingSolution;
import com.travelport.api_v33.Universal.BillingPointOfSaleInfo;
import com.travelport.api_v33.Universal.BookingTraveler;
import com.travelport.api_v33.Universal.BookingTravelerName;
import com.travelport.api_v33.Universal.Email;
import com.travelport.api_v33.Universal.PhoneNumber;
import com.travelport.api_v33.Universal.State;
import com.travelport.api_v33.Universal.TypeStructuredAddress;
import com.travelport.api_v33.Universal.TypeBaseAirSegment;
import com.travelport.api_v33.Universal.FormOfPayment;


public class TicketBookingRequestBuilder {
	static final Logger logger = Logger.getLogger(TicketBookingRequestBuilder.class);

	public static StringBuilder createAirpriceRequest(TravelportConfig travelportConfig, OrderCustomer orderCustomer,FlightPriceResponse flightPriceResponse,List<FlightOrderCustomer> flightOrderCustomers,String CountryCode,int count) throws ClassNotFoundException,
	JAXBException {
		AirCreateReservationReq airCreateReservationReq = new AirCreateReservationReq();
		airCreateReservationReq.setTargetBranch(travelportConfig.getTargetBranch());
		addPointOfSale(airCreateReservationReq, "uAPI");		
		for(int i=0;i<flightOrderCustomers.size();i++){
			BookingTraveler bookingTraveler=new BookingTraveler();
			addBookingTraveler(bookingTraveler,orderCustomer,flightOrderCustomers.get(i),i,CountryCode);
			airCreateReservationReq.getBookingTraveler().add(bookingTraveler);
		}		
		AirPricingSolution  airPricingSolution=new AirPricingSolution();
		addAirPricingSolution(airPricingSolution,flightPriceResponse,count);
		airCreateReservationReq.setAirPricingSolution(airPricingSolution);
		ActionStatus  actionStatus =new ActionStatus();
		actionStatus.setProviderCode(flightPriceResponse.getFareFlightSegment().getFareRules().get(0).getFareRule().get(0).getFareProviderCode());
		actionStatus.setTicketDate(flightPriceResponse.getFareFlightSegment().getLatestTicketingTime());// what to set?
		if(count==1){
			actionStatus.setProviderCode(flightPriceResponse.getSpecialFareFlightSegment().getFareRules().get(0).getFareRule().get(0).getFareProviderCode());
			actionStatus.setTicketDate(flightPriceResponse.getSpecialFareFlightSegment().getLatestTicketingTime());// what to set?
		}
		actionStatus.setType("TAW");//what to set?
		airCreateReservationReq.getActionStatus().add(actionStatus);
		FormOfPayment FOP = new FormOfPayment();
		FOP.setType("Cash");
		airCreateReservationReq.getFormOfPayment().add(FOP);

		MarshalUnmarshal marshalUnmarshal = new MarshalUnmarshal();
		String requestStr = marshalUnmarshal.marshal(airCreateReservationReq,AirCreateReservationReq.class);
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
		String soapEnv = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<SOAP-ENV:Header/><SOAP-ENV:Body>";
		String closeSoapStr = " </SOAP-ENV:Body></SOAP-ENV:Envelope>";
		requestStr = requestStr.replace(xmlStr, soapEnv);
		requestStr += closeSoapStr;
		StringBuilder sdb = new StringBuilder(requestStr);
		logger.info("createAirpriceRequest sdb: "+sdb);
		return sdb;

	}

	public static void  addAirPricingSolution(AirPricingSolution airPricingSolution,FlightPriceResponse flightPriceResponse,int count){
		FareFlightSegment fareFlightSegment = flightPriceResponse.getFareFlightSegment();
		if(count==1){
			fareFlightSegment=flightPriceResponse.getSpecialFareFlightSegment();
		}
		String Currency=fareFlightSegment.getCurrency();
		airPricingSolution.setApproximateBasePrice(Currency+fareFlightSegment.getBasePriceWithoutMarkup());
		airPricingSolution.setApproximateTotalPrice(Currency+fareFlightSegment.getTotalPriceWithoutMarkup());
		airPricingSolution.setBasePrice(Currency+fareFlightSegment.getBasePriceWithoutMarkup());
		airPricingSolution.setTotalPrice(Currency+fareFlightSegment.getTotalPriceWithoutMarkup());
		airPricingSolution.setKey(new UID().toString());
		for(FlightSegmentsGroup flightSegmentsGroup:fareFlightSegment.getFlightSegmentsGroups()){
			for(FlightSegments flightSegments:flightSegmentsGroup.getFlightSegments()){				
				for(Segments segments:flightSegments.getSegments()){
					TypeBaseAirSegment typeBaseAirSegment = new TypeBaseAirSegment();
					typeBaseAirSegment.setTravelTime(new BigInteger(segments.getDuration()));
					typeBaseAirSegment.setArrivalTime(segments.getArrival());
					typeBaseAirSegment.setDepartureTime(segments.getDepart());
					typeBaseAirSegment.setOrigin(segments.getOri());
					typeBaseAirSegment.setDestination(segments.getDest());
					//ChangeOfPlane="false" ClassOfService="R" FlightNumber="682" Carrier="AY" Group="0" Key="0T"/>
					typeBaseAirSegment.setChangeOfPlane(false);
					typeBaseAirSegment.setClassOfService(segments.getCabin().getCode());
					typeBaseAirSegment.setFlightNumber(segments.getFlight().getNumber());
					typeBaseAirSegment.setCarrier(segments.getCarrier().getCode());
					typeBaseAirSegment.setGroup(segments.getGroup());
					typeBaseAirSegment.setKey(new UID().toString());
					airPricingSolution.getAirSegment().add(typeBaseAirSegment);				
				}
			}
		}
	}	

	public static void addPointOfSale(AirCreateReservationReq airCreateReservationReq, String appName) {
		BillingPointOfSaleInfo posInfo = new BillingPointOfSaleInfo();
		posInfo.setOriginApplication(appName);
		airCreateReservationReq.setBillingPointOfSaleInfo(posInfo);
	}

	public static void addBookingTraveler(BookingTraveler bookingTraveler,OrderCustomer orderCustomer,FlightOrderCustomer flightOrderCustomer,int i,String CountryCode){
		bookingTraveler.setKey(new UID().toString());
		// bookingTraveler.setDOB(orderCustomer.getBirthday());	
		bookingTraveler.setTravelerType(flightOrderCustomer.getPassengerTypeCode());
		bookingTraveler.setGender(flightOrderCustomer.getGender());
		BookingTravelerName bookingTravelerName =new BookingTravelerName();
		bookingTravelerName.setFirst(flightOrderCustomer.getFirstName());
		bookingTravelerName.setLast(flightOrderCustomer.getLastName());
		bookingTravelerName.setPrefix(flightOrderCustomer.getTitle());
		bookingTraveler.setBookingTravelerName(bookingTravelerName);
		if(i==0){
			PhoneNumber phoneNumber = new PhoneNumber();
			// phoneNumber.setAreaCode("080");
			phoneNumber.setCountryCode(CountryCode);
			// phoneNumber.setLocation("IND");
			phoneNumber.setNumber(orderCustomer.getPhone());
			bookingTraveler.getPhoneNumber().add(phoneNumber);
			Email email=new Email();
			email.setEmailID(orderCustomer.getEmail());
			email.setType("Home");
			bookingTraveler.getEmail().add(email);
			/*Address address=new Address();
		      getAddress(address,orderCustomer);*/
			TypeStructuredAddress address = new TypeStructuredAddress();
			getAddress(address,orderCustomer);
			bookingTraveler.getAddress().add(address);
		}

	}
	private static void getAddress(TypeStructuredAddress address,OrderCustomer orderCustomer){
		address.setAddressName(orderCustomer.getFirstName());
		address.setCity(orderCustomer.getCity());
		address.setCountry(orderCustomer.getCountryId());
		address.setPostalCode(orderCustomer.getZip());
		State state=new State();
		state.setValue("Karnataka");//Not available
		address.setState(state);//Not available
		address.getStreet().add(orderCustomer.getAddress());
	}
}
