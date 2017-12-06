package com.tayyarah.hotel.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.tayyarah.api.hotel.reznext.model.OTAHotelResNotifRQ;
import com.tayyarah.api.hotel.reznext.model.OTAHotelResNotifRS;
import com.tayyarah.common.util.soap.HttpPostClient;
import com.tayyarah.common.util.soap.SoapClient;
import com.tayyarah.hotel.model.OTAHotelAvailRQ;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.util.api.concurrency.RezNextPullerTask;

import antlr.collections.List;

public class HotelSearchHandler {
	static final Logger logger = Logger.getLogger(HotelSearchHandler.class);

	private static final String OTAHotelAvailRQ_MESSAGE = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "<soap:Header><ns3:RequestServerVersion xmlns:ns3=\"http://schemas.microsoft.com/exchange/services/2006/types\" Version=\"Exchange2010_SP2\"/></soap:Header>"
			+ "<soap:Body>"
			+ "<OTA_HotelAvailRQ xmlns=\"http://www.opentravel.org/OTA/2003/05\" RequestedCurrency=\"INR\" SearchCacheLevel=\"Live\">"
			+ "<AvailRequestSegments>"
			+ "<AvailRequestSegment>"
			+ "<HotelSearchCriteria>"
			+ "<Criterion><Address>"
			+ "<CityName>Bangalore</CityName>"
			+ "<CountryName Code=\"INDIA\"/>"
			+ "</Address>"
			+ "<HotelRef/>"
			+ "<StayDateRange End=\"2015-07-30\" Start=\"2015-07-29\"/>"
			+ "<RoomStayCandidates>"
			+ "<RoomStayCandidate>"
			+ "<GuestCounts>"
			+ "<GuestCount AgeQualifyingCode=\"10\" Count=\"1\" />"
			+ "</GuestCounts>"
			+ "</RoomStayCandidate>"
			+ "</RoomStayCandidates>"
			+ "<TPA_Extensions>"
			+ "<Pagination enabled=\"false\"/>"
			+ "<HotelBasicInformation>"
			+ "<Reviews/>"
			+ "</HotelBasicInformation>"
			+ "<UserAuthentication password=\"t90za6\" propertyId=\"1300000141\" username=\"testnet\"/>"
			+ "<Promotion Type=\"HOTEL\" Name=\"All\" />"
			+ "</TPA_Extensions>"
			+ "</Criterion>"
			+ "</HotelSearchCriteria>"
			+ "</AvailRequestSegment>"
			+ "</AvailRequestSegments>"
			+ "</OTA_HotelAvailRQ>"
			+ "</soap:Body>"
			+ "</soap:Envelope>";
	private static final String OTAHotelAvailRQ_MESSAGE_POJO = "<OTA_HotelAvailRQ xmlns=\"http://www.opentravel.org/OTA/2003/05\" RequestedCurrency=\"INR\" SearchCacheLevel=\"Live\">"
			+ "<AvailRequestSegments>"
			+ "<AvailRequestSegment>"
			+ "<HotelSearchCriteria>"
			+ "<Criterion><Address>"
			+ "<CityName>Bangalore</CityName>"
			+ "<CountryName Code=\"INDIA\"/>"
			+ "</Address>"
			+ "<HotelRef/>"
			+ "<StayDateRange End=\"2015-07-30\" Start=\"2015-07-29\"/>"
			+ "<RoomStayCandidates>"
			+ "<RoomStayCandidate>"
			+ "<GuestCounts>"
			+ "<GuestCount AgeQualifyingCode=\"10\" Count=\"1\" />"
			+ "</GuestCounts>"
			+ "</RoomStayCandidate>"
			+ "</RoomStayCandidates>"
			+ "<TPA_Extensions>"
			+ "<Pagination enabled=\"false\"/>"
			+ "<HotelBasicInformation>"
			+ "<Reviews/>"
			+ "</HotelBasicInformation>"
			+ "<UserAuthentication password=\"t90za6\" propertyId=\"1300000141\" username=\"testnet\"/>"
			+ "<Promotion Type=\"HOTEL\" Name=\"All\" />"
			+ "</TPA_Extensions>"
			+ "</Criterion>"
			+ "</HotelSearchCriteria>"
			+ "</AvailRequestSegment>"
			+ "</AvailRequestSegments>"
			+ "</OTA_HotelAvailRQ>"	;




	public static OTAHotelAvailRS searchHotelDesia(HotelApiCredentials api, StringBuilder soapmessage)
	{

		/*OTAHotelAvailRQ otaHotelAvailRQ = new OTAHotelAvailRQ();
		otaHotelAvailRQ.s
		otaHotelAvailRQ.setRequestedCurrency("INR");
		otaHotelAvailRQ.setSearchCacheLevel("Live");
		AvailRequestSegments availRequestSegments = otaHotelAvailRQ.getAvailRequestSegments();
		List<AvailRequestSegment> availRequestSegment = availRequestSegments.getAvailRequestSegment();
		HotelSearchCriteria hotelSearchCriteria = availRequestSegment.getHotelSearchCriteria();
		Criterion criterion = (Criterion) hotelSearchCriteria.getCriterion();
		Address address = criterion.getAddress();*/
		;
		OTAHotelAvailRS otaHotelAvailRS = null;
		OTAHotelAvailRQ otaHotelAvailRQ = null;
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(OTAHotelAvailRQ.class);
			Unmarshaller unmarshallerreq = jaxbContext.createUnmarshaller();

			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage smreq = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(OTAHotelAvailRQ_MESSAGE.getBytes(Charset.forName("UTF-8"))));
			otaHotelAvailRQ = (OTAHotelAvailRQ) unmarshallerreq.unmarshal(smreq.getSOAPBody().extractContentAsDocument());


			SOAPMessage sm = SoapClient.sendSoapMessageTest(soapmessage, api);

			//sm.writeTo(System.out);
			//String example =
			//        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header /><soapenv:Body><ns2:farm xmlns:ns2=\"http://adamish.com/example/farm\"><horse height=\"123\" name=\"glue factory\"/></ns2:farm></soapenv:Body></soapenv:Envelope>";
			//SOAPMessage message = MessageFactory.newInstance().createMessage(null,
			//        new ByteArrayInputStream(example.getBytes()));
			Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelAvailRS.class).createUnmarshaller();

			otaHotelAvailRS = (OTAHotelAvailRS)unmarshaller.unmarshal(sm.getSOAPBody().extractContentAsDocument());


		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return otaHotelAvailRS;
	}



	public static com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS searchHotelReztnext(HotelApiCredentials api, StringBuilder soapmessage) throws Exception
	{
		com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS otaHotelAvailRS = null;
		HttpPostClient postclient = new HttpPostClient(api);
		StringBuilder res = postclient.sendPost(soapmessage, RezNextPullerTask.ACTION_GETHOTELINFOBYCITY);
		MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);			
		SOAPMessage message = messageFactory.createMessage(
				null, new ByteArrayInputStream(res.toString().getBytes(Charset.forName("UTF-8"))));
		Unmarshaller unmarshaller = JAXBContext.newInstance(com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS.class).createUnmarshaller();
		otaHotelAvailRS = (com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS)unmarshaller.unmarshal(message.getSOAPBody().extractContentAsDocument());
		return otaHotelAvailRS;
	}



	public static com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS searchHotelReznextTest()
	{

		com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS otaHotelAvailRS = null;

		try {

			File file = new File("C:\\ram\\reznext-cityresponse.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS.class).createUnmarshaller();

			otaHotelAvailRS = (com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS)unmarshaller.unmarshal(file);


		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return otaHotelAvailRS;
	}
	public static com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRQ HotelSearchRequestTest()
	{

		com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRQ otaHotelAvailRS = null;

		try {

			File file = new File("C:\\ram\\HotelSearchRequest.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRQ.class).createUnmarshaller();

			otaHotelAvailRS = (com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRQ)unmarshaller.unmarshal(file);


		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return otaHotelAvailRS;
	}

	public static com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS HotelSearchResponseTest()
	{

		com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS otaHotelAvailRS = null;

		try {

			File file = new File("C:\\ram\\HotelSearchResponse.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS.class).createUnmarshaller();

			otaHotelAvailRS = (com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS)unmarshaller.unmarshal(file);


		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return otaHotelAvailRS;
	}
	public static OTAHotelResNotifRQ HotelSearchCancellationRequestTest()
	{

		OTAHotelResNotifRQ otaHotelAvailRS = null;

		try {

			File file = new File("C:\\ram\\CancellationRequest.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelResNotifRQ.class).createUnmarshaller();

			otaHotelAvailRS = (OTAHotelResNotifRQ)unmarshaller.unmarshal(file);


		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return otaHotelAvailRS;
	}


	public static OTAHotelResNotifRS  HotelSearchCancellationResponseTest()
	{

		OTAHotelResNotifRS otaHotelAvailRS = null;

		try {

			File file = new File("C:\\ram\\CancellationResponse.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelResNotifRS.class).createUnmarshaller();

			otaHotelAvailRS = (OTAHotelResNotifRS)unmarshaller.unmarshal(file);


		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return otaHotelAvailRS;
	}

	public static OTAHotelResNotifRS  HotelSearchBookingResponseTest()
	{

		OTAHotelResNotifRS otaHotelAvailRS = null;

		try {

			File file = new File("C:\\ram\\BookingResponse.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelResNotifRS.class).createUnmarshaller();

			otaHotelAvailRS = (OTAHotelResNotifRS)unmarshaller.unmarshal(file);


		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return otaHotelAvailRS;
	}


	public static OTAHotelResNotifRQ  HotelSearchBookingRequestTest()
	{

		OTAHotelResNotifRQ otaHotelAvailRS = null;

		try {

			File file = new File("C:\\ram\\BookingRequest.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelResNotifRQ.class).createUnmarshaller();

			otaHotelAvailRS = (OTAHotelResNotifRQ)unmarshaller.unmarshal(file);


		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return otaHotelAvailRS;
	}
	public static com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRQ  HotelCitySearchRequestTest()
	{

		com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRQ otaHotelAvailRS = null;

		try {

			File file = new File("C:\\ram\\CitySearchRequest.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRQ.class).createUnmarshaller();

			otaHotelAvailRS = (com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRQ)unmarshaller.unmarshal(file);


		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return otaHotelAvailRS;
	}
	/*public static com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS  HotelCitySearchResponseTest()
	{

		com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS otaHotelAvailRS = null;

		try {

			File file = new File("C:\\ram\\CitySearchRequest.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS.class).createUnmarshaller();

			otaHotelAvailRS = (com.tayyarah.api.hotel.reznext.model.OTAHotelAvailRS)unmarshaller.unmarshal(file);


		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return otaHotelAvailRS;
	}
	 */



	public static OTAHotelResNotifRQ HotelBookingModRequestTest()
	{

		OTAHotelResNotifRQ otaHotelAvailRS = null;

		try {

			File file = new File("C:\\ram\\BookingModificationRequest.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelResNotifRQ.class).createUnmarshaller();

			otaHotelAvailRS = (OTAHotelResNotifRQ)unmarshaller.unmarshal(file);


		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return otaHotelAvailRS;
	}

	public static OTAHotelResNotifRS HotelBookingModResponseTest()
	{

		OTAHotelResNotifRS otaHotelAvailRS = null;

		try {

			File file = new File("C:\\ram\\BookingModificationResponse.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelResNotifRS.class).createUnmarshaller();

			otaHotelAvailRS = (OTAHotelResNotifRS)unmarshaller.unmarshal(file);


		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return otaHotelAvailRS;
	}






	public static OTAHotelAvailRS searchHotelDesiaTest(HotelApiCredentials api, StringBuilder soapmessage)
	{

		/*OTAHotelAvailRQ otaHotelAvailRQ = new OTAHotelAvailRQ();
		otaHotelAvailRQ.s
		otaHotelAvailRQ.setRequestedCurrency("INR");
		otaHotelAvailRQ.setSearchCacheLevel("Live");
		AvailRequestSegments availRequestSegments = otaHotelAvailRQ.getAvailRequestSegments();
		List<AvailRequestSegment> availRequestSegment = availRequestSegments.getAvailRequestSegment();
		HotelSearchCriteria hotelSearchCriteria = availRequestSegment.getHotelSearchCriteria();
		Criterion criterion = (Criterion) hotelSearchCriteria.getCriterion();
		Address address = criterion.getAddress();*/
		;
		OTAHotelAvailRS otaHotelAvailRS = null;
		OTAHotelAvailRQ otaHotelAvailRQ = null;
		try {

			File file = new File("C:\\ram\\OTA_HotelAvailRS.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelAvailRS.class).createUnmarshaller();

			otaHotelAvailRS = (OTAHotelAvailRS)unmarshaller.unmarshal(file);


		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return otaHotelAvailRS;
	}


	public static OTAHotelAvailRS searchHotelDesiaTemp(Object jsonString)
	{

		/*OTAHotelAvailRQ otaHotelAvailRQ = new OTAHotelAvailRQ();
		otaHotelAvailRQ.s
		otaHotelAvailRQ.setRequestedCurrency("INR");
		otaHotelAvailRQ.setSearchCacheLevel("Live");
		AvailRequestSegments availRequestSegments = otaHotelAvailRQ.getAvailRequestSegments();
		List<AvailRequestSegment> availRequestSegment = availRequestSegments.getAvailRequestSegment();
		HotelSearchCriteria hotelSearchCriteria = availRequestSegment.getHotelSearchCriteria();
		Criterion criterion = (Criterion) hotelSearchCriteria.getCriterion();
		Address address = criterion.getAddress();*/

		OTAHotelAvailRS otaHotelAvailRS = null;
		OTAHotelAvailRQ otaHotelAvailRQ = null;
		try {

			File file = new File("C:\\ram\\OTA_HotelAvailRS-complete.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelAvailRS.class).createUnmarshaller();

			otaHotelAvailRS = (OTAHotelAvailRS)unmarshaller.unmarshal(file);


		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return otaHotelAvailRS;
	}


	public static OTAHotelAvailRS parseOTAHotelAvailRS()
	{	
		OTAHotelAvailRS otaHotelAvailRQ = null;		
		try {

			File file = new File("C:\\ram\\OTA_HotelAvailRS.xml");	
			Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelAvailRS.class).createUnmarshaller();
			otaHotelAvailRQ = (OTAHotelAvailRS)unmarshaller.unmarshal(file);			



		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return otaHotelAvailRQ;
	}
	public static OTAHotelAvailRQ parseOTAHotelAvailRQ()
	{	
		OTAHotelAvailRQ otaHotelAvailRQ = null;		
		try {

			File file = new File("C:\\ram\\OTA_HotelAvailRQ.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(OTAHotelAvailRQ.class).createUnmarshaller();
			otaHotelAvailRQ = (OTAHotelAvailRQ)unmarshaller.unmarshal(file);

		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return otaHotelAvailRQ;
	}


	public static OTAHotelAvailRQ getOTAHotelAvailRQ()
	{		
		OTAHotelAvailRQ otaHotelAvailRQ = null;
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(OTAHotelAvailRQ.class);
			Unmarshaller unmarshallerreq = jaxbContext.createUnmarshaller();
			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage sm = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(OTAHotelAvailRQ_MESSAGE.getBytes(Charset.forName("UTF-8"))));
			otaHotelAvailRQ = (OTAHotelAvailRQ) unmarshallerreq.unmarshal(sm.getSOAPBody().extractContentAsDocument());

		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(SOAPException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return otaHotelAvailRQ;
	}

	/*public static com.tayyarah.hotel.desiya.hotelreq.pojo.OTA_HotelAvailRQ getOTAHotelAvailRQPOJO()
	{		
		com.tayyarah.hotel.desiya.hotelreq.pojo.OTA_HotelAvailRQ otaHotelAvailRQ = null;
		try {			
			otaHotelAvailRQ = JAXB.unmarshal(OTAHotelAvailRQ_MESSAGE_POJO, com.tayyarah.hotel.desiya.hotelreq.pojo.OTA_HotelAvailRQ.class);			

		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return otaHotelAvailRQ;
	}*/


	/*public static OTAHotelAvailRQ generateOTAHotelAvailRQ(OTAHotelAvailRQ otaHotelAvailRQ, HotelSearchCommand jsonString)
	{		
		otaHotelAvailRQ.setRequestedCurrency("INR");
		otaHotelAvailRQ.setSearchCacheLevel("Live");
		AvailRequestSegment availRequestSegment = otaHotelAvailRQ.getAvailRequestSegments().getAvailRequestSegment().get(0);

		HotelSearchCriteria hotelSearchCriteria = availRequestSegment.getHotelSearchCriteria();
		Criterion criterion = hotelSearchCriteria.getCriterion().get(0);
		Address address = criterion.getAddress();
		//address.setAddressSearchScope(value);


		return otaHotelAvailRQ;
	}
	 */
}
