package com.tayyarah.flight.util.api.bluestar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.tayyarah.apiconfig.model.BluestarConfig;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.flight.entity.FlightOrderCustomer;
import com.tayyarah.flight.model.FlightPriceResponse;
import com.tayyarah.flight.model.Segments;
import com.tayyarah.flight.util.FlightWebServiceEndPointValidator;



public class BluestarBookTicketRequestBuilder {
	private static FlightWebServiceEndPointValidator validator = new FlightWebServiceEndPointValidator();
	static final Logger logger = Logger.getLogger(BluestarBookTicketRequestBuilder.class);

	public static StringBuilder createAirpriceRequest(OrderCustomer orderCustomer,FlightPriceResponse flightPriceResponse,List<FlightOrderCustomer> flightOrderCustomers,int count,boolean IsspecialRoundtrip,BluestarConfig bluestarConfig) throws ClassNotFoundException,
	JAXBException 
	{	
		String headerSTR = setHeader(bluestarConfig);
		String soapEnv = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
				"<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
				headerSTR+"<soapenv:Body><tem:BookTicket>   <tem:strRequestXML><![CDATA[";
		String TrackNo = "";
		if(!IsspecialRoundtrip){
			TrackNo = flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getTrackno();
		}
		// Set TrackNo Special Round Trip
		if(IsspecialRoundtrip){
			TrackNo = flightPriceResponse.getSpecialFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments().get(0).getTrackno();
		}
		String body="<BookTicketRequest><TrackNo>"+TrackNo+"</TrackNo><MobileNo>"+orderCustomer.getMobile()+"</MobileNo>"
				+ "<AltMobileNo>"+orderCustomer.getMobile()+"</AltMobileNo><Email>"+orderCustomer.getEmail()+"</Email><Address>"+orderCustomer.getAddress()+"</Address>";

		String passengerDetails=addPassengers(flightOrderCustomers,flightPriceResponse);
		String segmentsDetails=addSegments(flightPriceResponse,count,IsspecialRoundtrip);
		String restOfBody = "";
		if(!IsspecialRoundtrip){
			restOfBody="<TotalAmount>"+flightPriceResponse.getFareFlightSegment().getTotalPriceWithoutMarkup()+"</TotalAmount><PaymentTransactionPwd>"+bluestarConfig.getTransaction_Pwd()+"</PaymentTransactionPwd><IsTicketing>Yes</IsTicketing></BookTicketRequest>";
		}
		// Set Amount Details Special Round Trip
		if(IsspecialRoundtrip){
			restOfBody="<TotalAmount>"+flightPriceResponse.getSpecialFareFlightSegment().getTotalPriceWithoutMarkup()+"</TotalAmount><PaymentTransactionPwd>"+bluestarConfig.getTransaction_Pwd()+"</PaymentTransactionPwd><IsTicketing>Yes</IsTicketing></BookTicketRequest>";
		}
		
		body=body+passengerDetails+segmentsDetails+restOfBody;
		String closeSoapStr = "]]></tem:strRequestXML></tem:BookTicket> </soapenv:Body></soapenv:Envelope>";		
		StringBuilder sdb = new StringBuilder();
		sdb.append(soapEnv+body+closeSoapStr);		
		return sdb;
	}

	public static String addSegments(FlightPriceResponse flightPriceResponse,int count,boolean IsspecialRoundtrip){
		Map<String,String> cabinTypeMAp=new HashMap<String,String>();
		cabinTypeMAp.put("Economy", "Y");cabinTypeMAp.put("business", "C");cabinTypeMAp.put("First Class", "F");
		StringBuilder sb=new StringBuilder();
		sb.append("<Segments>");
		List<Segments> segmentsList = null;
		if(!IsspecialRoundtrip){
			segmentsList = flightPriceResponse.getFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments();
		}
		if(IsspecialRoundtrip){
			segmentsList = flightPriceResponse.getSpecialFareFlightSegment().getFlightSegmentsGroups().get(0).getFlightSegments().get(0).getSegments();
		}		
		int i = 1;
		for(Segments segments:segmentsList){
			sb.append("<Segment><SegmentSeqNo>"+i+"</SegmentSeqNo><AirlineCode>"+segments.getCarrier().getCode()+"</AirlineCode><FlightNo>"+segments.getFlight().getNumber()+"</FlightNo><FromAirportCode>"+segments.getOri()+"</FromAirportCode><ToAirportCode>"+segments.getDest()+"</ToAirportCode> "
					+ "<DepDate>"+validator.getBluestarDate(segments.getDepart().substring(0,10))+"</DepDate><DepTime>"+segments.getDepart().substring(11,16)+"</DepTime><ArrDate>"+validator.getBluestarDate(segments.getArrival().substring(0,10))+"</ArrDate><ArrTime>"+segments.getArrival().substring(11,16)+"</ArrTime><FlightClass>"+segments.getCabin().getCode()+"</FlightClass><MainClass>"+cabinTypeMAp.get(segments.getCabin().getName())+"</MainClass></Segment>");
			i++;
		}
		sb.append("</Segments>");	
		return sb.toString();
	}

	public static  String addPassengers(List<FlightOrderCustomer> flightOrderCustomers,FlightPriceResponse flightPriceResponse){
		Map<String,String> passTypeMAp=new HashMap<String,String>();
		passTypeMAp.put("ADT", "A");passTypeMAp.put("CHD", "C");passTypeMAp.put("INF", "I");
		Map<String,String> titleMAp=new HashMap<String,String>();
		titleMAp.put("Mr", "Mr");titleMAp.put("Ms", "Ms");titleMAp.put("Miss", "Miss");titleMAp.put("Mrs", "Mrs");
		StringBuilder sb=new StringBuilder();
		sb.append("<Passengers>");
		int k = 1;	
		for(FlightOrderCustomer flightOrderCustomer:flightOrderCustomers){

			sb.append("<Passenger><PaxSeqNo>"+k+"</PaxSeqNo><Title>"+titleMAp.get(flightOrderCustomer.getGender())+"</Title><FirstName>"+flightOrderCustomer.getFirstName()+"</FirstName><LastName>"+flightOrderCustomer.getLastName()+"</LastName><PassengerType>"+passTypeMAp.get(flightOrderCustomer.getPassengerTypeCode())+"</PassengerType><DateOfBirth>"+validator.getBluestarDate(flightOrderCustomer.getBirthday())+"</DateOfBirth>");
			if(flightPriceResponse.getFlightsearch().isIsInternational()){
				sb.append(" <PassportNo>"+flightOrderCustomer.getPassportNo()+"</PassportNo><PassportExpDate>"+validator.getBluestarDateFromDate(flightOrderCustomer.getPassportExpiryDate())+"</PassportExpDate><PassportIssuingCountry>"+flightOrderCustomer.getPassportIssuingCountry()+"</PassportIssuingCountry><NationalityCountry>"+flightOrderCustomer.getNationality()+"</NationalityCountry>");
			}
			sb.append("</Passenger>");
			k++;
		}
		sb.append("</Passengers>");	
		return sb.toString();
	}

	public static  String setHeader(BluestarConfig bluestarConfig){
		StringBuilder sb=new StringBuilder();		
		sb.append("<soapenv:Header><tem:Authenticate><tem:InterfaceCode>"+bluestarConfig.getInterface_Code()+"</tem:InterfaceCode><tem:InterfaceAuthKey>"+bluestarConfig.getInterface_Auth_Key()+"</tem:InterfaceAuthKey><tem:AgentCode>"+bluestarConfig.getAgent_Code()+"</tem:AgentCode></tem:Authenticate></soapenv:Header>");
		return sb.toString();
	}
}