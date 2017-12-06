package com.tayyarah.hotel.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.tayyarah.api.hotel.travelguru.book.model.CompanyNameType;
import com.tayyarah.api.hotel.travelguru.book.model.EmailType;
import com.tayyarah.api.hotel.travelguru.book.model.GuaranteeType;
import com.tayyarah.api.hotel.travelguru.book.model.HotelReservationsType;
import com.tayyarah.api.hotel.travelguru.book.model.OTACancelRQ;
import com.tayyarah.api.hotel.travelguru.book.model.OTAHotelResRQ;
import com.tayyarah.api.hotel.travelguru.book.model.POSType;
import com.tayyarah.api.hotel.travelguru.book.model.ResGlobalInfoType;
import com.tayyarah.api.hotel.travelguru.book.model.SourceType;
import com.tayyarah.api.hotel.travelguru.book.model.TPAExtensions;
import com.tayyarah.api.hotel.travelguru.book.model.TransactionActionType;
import com.tayyarah.api.hotel.travelguru.book.model.VerificationType;
import com.tayyarah.api.hotel.travelguru.booking.model.Guarantee;
import com.tayyarah.api.hotel.travelguru.booking.model.HotelReservation;
import com.tayyarah.api.hotel.travelguru.booking.model.POS;
import com.tayyarah.api.hotel.travelguru.booking.model.RequestorID;
import com.tayyarah.api.hotel.travelguru.booking.model.ResGlobalInfo;
import com.tayyarah.api.hotel.travelguru.booking.model.Source;
import com.tayyarah.common.util.soap.MarshalUnmarshal;
import com.tayyarah.hotel.entity.HotelSearchTemp;
import com.tayyarah.hotel.entity.HotelTransactionTemp;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelSearchCommand;

import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.UniqueIDType;



public class HotelBookRequestBuilder {

	public static final String SOAP_HEADER_BOOK_CANCEL = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\">"
			+ "<soapenv:Header/>"
			+ "<soapenv:Body>";
	public static final String SOAP_FOOTER_BOOK_CANCEL = "</soapenv:Body></soapenv:Envelope>";

	public static final String SOAP_HEADER_PREBOOK_TG = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"			
			+ "<soap:Body>";
	public static final String SOAP_FOOTER_PREBOOK_TG = "</soap:Body></soap:Envelope>";


	public static final String SOAP_HEADER = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "<soap:Header><ns3:RequestServerVersion xmlns:ns3=\"http://schemas.microsoft.com/exchange/services/2006/types\" Version=\"Exchange2010_SP2\"/></soap:Header>"
			+ "<soap:Body>";
	public static final String SOAP_FOOTER = "</soap:Body>"
			+ "</soap:Envelope>";
	public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
	public static final String XML_HEADER_FORMATTED = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	public static String REZTNEXT_SOAP_CITY_SEARCH = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">"
			+ "<soapenv:Header><wsa:From><wsa:SystemId>Reznext</wsa:SystemId>"
			+ "<wsa:Credential>"
			+ "<wsa:UserName>Reznext123</wsa:UserName><wsa:Password>Reznext@123</wsa:Password>"
			+ "</wsa:Credential>"
			+ "</wsa:From>"
			+ "<wsa:TimeStamp>2015-08-24T15:10:48</wsa:TimeStamp>"
			+ "<wsa:Action>OTA_HotelAvailRQ</wsa:Action>"
			+ "</soapenv:Header>"
			+ "<soapenv:Body>"
			+ "<OTA_HotelAvailRQ Version=\"4.000\" EchoToken=\"12345\" TransactionIdentifier=\"1\" TimeStamp=\"2015-08-24T15:10:48\">"
			+ "<AvailRequestSegments>"
			+ "<AvailRequestSegment>"
			+ "<HotelSearchCriteria>"
			+ "<Criterion>"
			+ "<CityInfo CityName=\"Bengaluru\"/>"
			+ "<StayDateRange Start=\"2015-08-25\" End=\"2015-08-27\"/>"
			+ "<RoomInfo NumberOfRooms=\"1\"/>"
			+ "</Criterion>"
			+ "</HotelSearchCriteria>"
			+ "</AvailRequestSegment>"
			+ "</AvailRequestSegments>"
			+ "</OTA_HotelAvailRQ>"
			+ "</soapenv:Body>"
			+ "</soapenv:Envelope>";
	public static final Logger logger = Logger.getLogger(HotelBookRequestBuilder.class);	

	
	public static StringBuilder getFinalBookingReqPojo( HotelApiCredentials apicred, HotelTransactionTemp ht, HotelSearchTemp hs, HotelSearchCommand hsc, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay rs) throws NumberFormatException, Exception
	{
		/*
		<soap:Envelope
	    xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
	    <soap:Body>
	        <OTA_HotelResRQ
	            xmlns="http://www.opentravel.org/OTA/2003/05"  CorrelationID="sessionId" TransactionIdentifier="122121" Version="1.003">
	            <POS>
	                <Source ISOCurrency="INR">
	                    <RequestorID MessagePassword="xxxxxx" ID="xxxxxxxxxxxx">
	                        <CompanyName Code="xxxxxxxxxxxxx"></CompanyName>
	                    </RequestorID>
	                </Source>
	            </POS>
	            <UniqueID Type="23" ID="xxxxxxxxxxxxxxxx" />
	            <HotelReservations>
	                <HotelReservation>
	                    <ResGlobalInfo>
	                        <Guarantee GuaranteeType="PrePay" />
	                    </ResGlobalInfo>
	                </HotelReservation>
	            </HotelReservations>
	        </OTA_HotelResRQ>
	    </soap:Body>
		</soap:Envelope>
		 */



		com.tayyarah.api.hotel.travelguru.booking.model.OTAHotelResRQ otaHotelResRQ = new com.tayyarah.api.hotel.travelguru.booking.model.OTAHotelResRQ();
		otaHotelResRQ.setCorrelationID(String.valueOf(ht.getId()));
		otaHotelResRQ.setTransactionIdentifier(String.valueOf(ht.getId()));
		otaHotelResRQ.setVersion(new Float("1.003"));
		POS pos = new POS();
		Source source = new com.tayyarah.api.hotel.travelguru.booking.model.Source();
		source.setISOCurrency(apicred.getApiCurrency());

		RequestorID requestorID = new com.tayyarah.api.hotel.travelguru.booking.model.RequestorID();
		com.tayyarah.api.hotel.travelguru.booking.model.CompanyName companyName = new com.tayyarah.api.hotel.travelguru.booking.model.CompanyName();
		companyName.setCode(apicred.getUserName());
		requestorID.setCompanyName(companyName);
		requestorID.setMessagePassword(apicred.getPassword());
		requestorID.setID(apicred.getPropertyId());
		source.setRequestorID(requestorID);
		pos.setSource(source);
		otaHotelResRQ.setPOS(pos);

		com.tayyarah.api.hotel.travelguru.booking.model.UniqueID uniqueID = new com.tayyarah.api.hotel.travelguru.booking.model.UniqueID();
		uniqueID.setID("");
		uniqueID.setType("");
		otaHotelResRQ.setUniqueID(uniqueID);

		com.tayyarah.api.hotel.travelguru.booking.model.HotelReservations hotelReservations = new com.tayyarah.api.hotel.travelguru.booking.model.HotelReservations();				
		//List<com.travelguru.hotel.booking.HotelReservation> hotelReservationlist = new ArrayList<com.travelguru.hotel.booking.HotelReservation>();

		com.tayyarah.api.hotel.travelguru.booking.model.HotelReservation hotelReservation  = new HotelReservation();

		com.tayyarah.api.hotel.travelguru.booking.model.ResGlobalInfo resGlobalInfo = new ResGlobalInfo();
		com.tayyarah.api.hotel.travelguru.booking.model.Guarantee guarantee = new Guarantee();
		guarantee.setGuarantee("PrePay");
		resGlobalInfo.setGuarantee(guarantee);
		hotelReservation.setResGlobalInfo(resGlobalInfo);

		//hotelReservationlist.add(hotelReservation);
		hotelReservations.setHotelReservation(hotelReservation);
		otaHotelResRQ.setHotelReservations(hotelReservations);

		MarshalUnmarshal marshalUnmarshal=new MarshalUnmarshal();
		String requestStr = marshalUnmarshal.marshal(otaHotelResRQ,com.tayyarah.api.hotel.travelguru.booking.model.OTAHotelResRQ.class);			
		requestStr = requestStr.replace(XML_HEADER, SOAP_HEADER);
		requestStr+=SOAP_FOOTER;
		StringBuilder sdb = new StringBuilder(requestStr);
		System.out.println(sdb);	

		logger.info("OTAHotelResRQ - ---  reservation booking:--- sources added"+ sdb);		
		return sdb;
	}


	public static StringBuilder getFinalBookingReqBookPojo( HotelApiCredentials apicred, HotelTransactionTemp ht, HotelSearchTemp hs, HotelSearchCommand hsc, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay rs) throws NumberFormatException, Exception
	{
		/*
		<soap:Envelope
	    xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
	    <soap:Body>
	        <OTA_HotelResRQ
	            xmlns="http://www.opentravel.org/OTA/2003/05"  CorrelationID="sessionId" TransactionIdentifier="122121" Version="1.003">
	            <POS>
	                <Source ISOCurrency="INR">
	                    <RequestorID MessagePassword="xxxxxx" ID="xxxxxxxxxxxx">
	                        <CompanyName Code="xxxxxxxxxxxxx"></CompanyName>
	                    </RequestorID>
	                </Source>
	            </POS>
	            <UniqueID Type="23" ID="xxxxxxxxxxxxxxxx" />
	            <HotelReservations>
	                <HotelReservation>
	                    <ResGlobalInfo>
	                        <Guarantee GuaranteeType="PrePay" />
	                    </ResGlobalInfo>
	                </HotelReservation>
	            </HotelReservations>
	        </OTA_HotelResRQ>
	    </soap:Body>
		</soap:Envelope>
		 */

		OTAHotelResRQ otaHotelResRQ = new OTAHotelResRQ();
		otaHotelResRQ.setCorrelationID(String.valueOf(ht.getId()));
		otaHotelResRQ.setTransactionIdentifier(String.valueOf(ht.getId()));
		otaHotelResRQ.setVersion(new BigDecimal("1.003"));


		POSType pos = new POSType();
		List<SourceType> sources = new ArrayList<SourceType>();
		SourceType source = new SourceType();
		SourceType.RequestorID requestorID = new SourceType.RequestorID();
		CompanyNameType companyName = new CompanyNameType();
		companyName.setCode(apicred.getUserName());
		requestorID.setCompanyName(companyName);
		requestorID.setMessagePassword(apicred.getPassword());
		requestorID.setID(apicred.getPropertyId());
		source.setRequestorID(requestorID);
		sources.add(source);		
		pos.setSources(sources);
		otaHotelResRQ.setPOS(pos);


		List<com.tayyarah.api.hotel.travelguru.book.model.UniqueIDType> uniqueIDs = new ArrayList<com.tayyarah.api.hotel.travelguru.book.model.UniqueIDType>();
		com.tayyarah.api.hotel.travelguru.book.model.UniqueIDType uniqueID = new com.tayyarah.api.hotel.travelguru.book.model.UniqueIDType();
		uniqueID.setID("23");
		uniqueID.setType("asaasa");
		uniqueIDs.add(uniqueID);
		otaHotelResRQ.setUniqueIDs(uniqueIDs);


		HotelReservationsType hotelReservations = new HotelReservationsType();				

		List<HotelReservationsType.HotelReservation> hotelReservationlist = new ArrayList<HotelReservationsType.HotelReservation>();

		HotelReservationsType.HotelReservation hotelReservation  = new HotelReservationsType.HotelReservation();


		ResGlobalInfoType resGlobalInfo = new ResGlobalInfoType();
		GuaranteeType guarantee = new GuaranteeType();
		guarantee.setGuaranteeType("PrePay");
		resGlobalInfo.setGuarantee(guarantee);
		hotelReservation.setResGlobalInfo(resGlobalInfo);

		hotelReservationlist.add(hotelReservation);
		hotelReservations.setHotelReservations(hotelReservationlist);
		otaHotelResRQ.setHotelReservations(hotelReservations);

		MarshalUnmarshal marshalUnmarshal=new MarshalUnmarshal();
		String requestStr = marshalUnmarshal.marshal(otaHotelResRQ,OTAHotelResRQ.class);			
		requestStr = requestStr.replace(XML_HEADER, SOAP_HEADER_BOOK_CANCEL);
		requestStr+=SOAP_FOOTER_BOOK_CANCEL;
		StringBuilder sdb = new StringBuilder(requestStr);
		System.out.println(sdb);	

		logger.info("OTAHotelResRQ - ---  reservation booking:--- sources added"+ sdb);		
		return sdb;
	}




	public static StringBuilder getInitiateCancelReqBookPojo( HotelApiCredentials apicred) throws NumberFormatException, Exception
	{
		/*
		<soapenv:Envelope
    xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:ns="http://www.opentravel.org/OTA/2003/05">
    <soapenv:Header/>
    <soapenv:Body>
        <ns:OTA_CancelRQ CancelType="Initiate"  Version="1.0" >
            <ns:POS>
                <ns:Source>
                    <ns:RequestorID ID="xxxxxx" MessagePassword="xxxxxx">
                        <ns:CompanyName Code="xxxxxxxxx"/>
                    </ns:RequestorID>
                </ns:Source>
            </ns:POS>
            <ns:UniqueID ID="ABC0001395597"/>
            <ns:Verification>
                <ns:PersonName>
                    <ns:Surname>Tendulkar</ns:Surname>
                </ns:PersonName>
                <ns:Email>abc@gmail.com</ns:Email>
            </ns:Verification>
            <ns:TPA_Extensions>

                <ns:CancelDates/>
            </ns:TPA_Extensions>
        </ns:OTA_CancelRQ>
    </soapenv:Body>
	</soapenv:Envelope>
		 */

		OTACancelRQ otaCancelRQ = new OTACancelRQ();
		otaCancelRQ.setCancelType(TransactionActionType.INITIATE);
		otaCancelRQ.setVersion(new BigDecimal("1.0"));


		POSType pos = new POSType();

		List<SourceType> sources = new ArrayList<SourceType>();
		SourceType source = new SourceType();
		SourceType.RequestorID requestorID = new SourceType.RequestorID();
		CompanyNameType companyName = new CompanyNameType();
		companyName.setCode(apicred.getUserName());
		requestorID.setCompanyName(companyName);
		requestorID.setMessagePassword(apicred.getPassword());
		requestorID.setID(apicred.getPropertyId());
		source.setRequestorID(requestorID);
		sources.add(source);		
		pos.setSources(sources);
		otaCancelRQ.setPOS(pos);


		List<OTACancelRQ.UniqueID> uniqueIDs = new ArrayList<OTACancelRQ.UniqueID>();
		OTACancelRQ.UniqueID uniqueID = new OTACancelRQ.UniqueID();
		uniqueID.setID("");
		uniqueID.setType("");
		uniqueIDs.add(uniqueID);
		otaCancelRQ.setUniqueIDs(uniqueIDs);

		List<VerificationType> vaerfications = new ArrayList<VerificationType>();
		VerificationType verfication = new VerificationType();
		VerificationType.PersonName personname = new VerificationType.PersonName();
		personname.setSurname("shewag");	
		verfication.setPersonName(personname);
		EmailType email = new EmailType();
		email.setValue("shewag@web.com");
		verfication.setEmail(email);
		vaerfications.add(verfication);
		otaCancelRQ.setVerifications(vaerfications);

		TPAExtensions tpaExt = new TPAExtensions();
		TPAExtensions.CancelDates canceldates = new TPAExtensions.CancelDates();
		List<String> canceldatelist = new ArrayList<String>();			
		canceldates.setDates(canceldatelist);
		tpaExt.setCancelDates(canceldates);
		otaCancelRQ.setTPAExtensions(tpaExt);


		MarshalUnmarshal marshalUnmarshal=new MarshalUnmarshal();
		String requestStr = marshalUnmarshal.marshal(otaCancelRQ, OTACancelRQ.class);			
		requestStr = requestStr.replace(XML_HEADER, SOAP_HEADER_BOOK_CANCEL);
		requestStr+=SOAP_FOOTER_BOOK_CANCEL;
		StringBuilder sdb = new StringBuilder(requestStr);
		System.out.println(sdb);	

		logger.info("otaCancelRQ - ---  initiate cancel booking:--- sources added-----\n\n\n\n"+ sdb);		
		return sdb;
	}

	public static StringBuilder getConfirmCancelReqBookPojo( HotelApiCredentials apicred) throws NumberFormatException, Exception
	{
		/*
		<soapenv:Envelope
    xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:ns="http://www.opentravel.org/OTA/2003/05">
    <soapenv:Header/>
    <soapenv:Body>
        <ns:OTA_CancelRQ CancelType="Initiate"  Version="1.0" >
            <ns:POS>
                <ns:Source>
                    <ns:RequestorID ID="xxxxxx" MessagePassword="xxxxxx">
                        <ns:CompanyName Code="xxxxxxxxx"/>
                    </ns:RequestorID>
                </ns:Source>
            </ns:POS>
            <ns:UniqueID ID="ABC0001395597"/>
            <ns:Verification>
                <ns:PersonName>
                    <ns:Surname>Tendulkar</ns:Surname>
                </ns:PersonName>
                <ns:Email>abc@gmail.com</ns:Email>
            </ns:Verification>
            <ns:TPA_Extensions>

                <ns:CancelDates/>
            </ns:TPA_Extensions>
        </ns:OTA_CancelRQ>
    </soapenv:Body>
	</soapenv:Envelope>
		 */

		OTACancelRQ otaCancelRQ = new OTACancelRQ();
		otaCancelRQ.setCancelType(TransactionActionType.CANCEL);
		otaCancelRQ.setVersion(new BigDecimal("1.0"));


		POSType pos = new POSType();

		List<SourceType> sources = new ArrayList<SourceType>();
		SourceType source = new SourceType();
		SourceType.RequestorID requestorID = new SourceType.RequestorID();
		CompanyNameType companyName = new CompanyNameType();
		companyName.setCode(apicred.getUserName());
		requestorID.setCompanyName(companyName);
		requestorID.setMessagePassword(apicred.getPassword());
		requestorID.setID(apicred.getPropertyId());
		source.setRequestorID(requestorID);
		sources.add(source);		
		pos.setSources(sources);
		otaCancelRQ.setPOS(pos);


		List<OTACancelRQ.UniqueID> uniqueIDs = new ArrayList<OTACancelRQ.UniqueID>();
		OTACancelRQ.UniqueID uniqueID = new OTACancelRQ.UniqueID();
		uniqueID.setID("");
		uniqueID.setType("");
		uniqueIDs.add(uniqueID);
		otaCancelRQ.setUniqueIDs(uniqueIDs);

		List<VerificationType> vaerfications = new ArrayList<VerificationType>();
		VerificationType verfication = new VerificationType();
		VerificationType.PersonName personname = new VerificationType.PersonName();
		personname.setSurname("shewag");	
		verfication.setPersonName(personname);
		EmailType email = new EmailType();
		email.setValue("shewag@web.com");
		verfication.setEmail(email);
		vaerfications.add(verfication);
		otaCancelRQ.setVerifications(vaerfications);

		TPAExtensions tpaExt = new TPAExtensions();
		TPAExtensions.CancelDates canceldates = new TPAExtensions.CancelDates();
		List<String> canceldatelist = new ArrayList<String>();			
		canceldates.setDates(canceldatelist);
		tpaExt.setCancelDates(canceldates);
		otaCancelRQ.setTPAExtensions(tpaExt);


		MarshalUnmarshal marshalUnmarshal=new MarshalUnmarshal();
		String requestStr = marshalUnmarshal.marshal(otaCancelRQ, OTACancelRQ.class);			
		requestStr = requestStr.replace(XML_HEADER, SOAP_HEADER_BOOK_CANCEL);
		requestStr+=SOAP_FOOTER_BOOK_CANCEL;
		StringBuilder sdb = new StringBuilder(requestStr);
		System.out.println(sdb);	

		logger.info("otaCancelRQ - ---  initiate cancel booking:--- sources added-----\n\n\n\n"+ sdb);		
		return sdb;
	}

	
	public static StringBuilder getFinalBookingReq( HotelApiCredentials apicred, HotelTransactionTemp ht, HotelSearchCommand hsc, HotelBookCommand hbc, OTAHotelAvailRS.RoomStays.RoomStay rs, UniqueIDType uniqueid) throws NumberFormatException, Exception
	{
		StringBuilder req  = new  StringBuilder();		
		req.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"	
				+ "<OTA_HotelResRQ CorrelationID=\""+hbc.getCorrelationid()+"\" TransactionIdentifier=\""+hbc.getTransactionid()+"\" Version=\"1.003\" "
				+ "xmlns=\"http://www.opentravel.org/OTA/2003/05\">"
				+ "<POS>"
				+ "<Source ISOCurrency=\""+apicred.getApiCurrency()+"\">"
				+ "<RequestorID MessagePassword=\""+apicred.getPassword()+"\" ID=\""+apicred.getPropertyId()+"\">"
				+ "<CompanyName Code=\""+apicred.getUserName()+"\" />"
				+ "</RequestorID>"
				+ "</Source>"
				+ "</POS>"
				+ "<UniqueID Type=\""+uniqueid.getType()+"\" ID=\""+uniqueid.getID()+"\"/>"
				+ "<HotelReservations>"
				+ "<HotelReservation>"			
				+ "<ResGlobalInfo>"
				+ "<Guarantee GuaranteeType=\"PrePay\"/>"
				+ "</ResGlobalInfo>"
				+ "</HotelReservation>"
				+ "</HotelReservations>"
				+ "</OTA_HotelResRQ>");



		logger.info("OTAHotelResRQ - ---  reservation booking:--- sources added"+ req);		
		return req;
	}



	public static StringBuilder getInitiateCancelReq( HotelApiCredentials apicred, String uniqueid, String name, String email) throws NumberFormatException, Exception
	{
		/*
		<soapenv:Envelope
    xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:ns="http://www.opentravel.org/OTA/2003/05">
    <soapenv:Header/>
    <soapenv:Body>
        <ns:OTA_CancelRQ CancelType="Initiate"  Version="1.0" >
            <ns:POS>
                <ns:Source>
                    <ns:RequestorID ID="xxxxxx" MessagePassword="xxxxxx">
                        <ns:CompanyName Code="xxxxxxxxx"/>
                    </ns:RequestorID>
                </ns:Source>
            </ns:POS>
            <ns:UniqueID ID="ABC0001395597"/>
            <ns:Verification>
                <ns:PersonName>
                    <ns:Surname>Tendulkar</ns:Surname>
                </ns:PersonName>
                <ns:Email>abc@gmail.com</ns:Email>
            </ns:Verification>
            <ns:TPA_Extensions>

                <ns:CancelDates/>
            </ns:TPA_Extensions>
        </ns:OTA_CancelRQ>
    </soapenv:Body>
	</soapenv:Envelope>
		 */
		StringBuilder req  = new  StringBuilder();		
		req.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<ns:OTA_CancelRQ CancelType=\"Initiate\"  Version=\"1.0\" >"
				+ "<ns:POS>"
				+ "<ns:Source>"
				+ "<ns:RequestorID MessagePassword=\""+apicred.getPassword()+"\" ID=\""+apicred.getPropertyId()+"\">"
				+ "<ns:CompanyName Code=\""+apicred.getUserName()+"\" />"
				+ "</ns:RequestorID>"
				+ "</ns:Source>"
				+ "</ns:POS>"
				+ "<ns:UniqueID ID=\""+uniqueid+"\"/>"
				+ "<ns:Verification>"
				+ "<ns:PersonName>"
				+ "<ns:Surname>"+name+"</ns:Surname>"
				+ "</ns:PersonName>"
				+ "<ns:Email>"+email+"</ns:Email>"
				+ "</ns:Verification>"
				+ "<ns:TPA_Extensions>"
				+ "<ns:CancelDates/>"
				+ "</ns:TPA_Extensions>"
				+ "</ns:OTA_CancelRQ>"			
				);



		logger.info("ns:OTA_CancelRQ - ---  initiate cancel:--- sources added"+ req);		
		return req;

	}

	public static StringBuilder getConfirmCancelReq( HotelApiCredentials apicred, String uniqueid, String name, String email) throws NumberFormatException, Exception
	{
		/*
		<soapenv:Envelope
    xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
    xmlns:ns="http://www.opentravel.org/OTA/2003/05">
    <soapenv:Header/>
    <soapenv:Body>
        <ns:OTA_CancelRQ CancelType="Cancel"  Version="1.0" >
            <ns:POS>
                <ns:Source>
                    <ns:RequestorID ID="xxxxxx" MessagePassword="xxxxxx">
                        <ns:CompanyName Code="xxxxxxxxx"/>
                    </ns:RequestorID>
                </ns:Source>
            </ns:POS>
            <ns:UniqueID ID="ABC0001395597"/>
            <ns:Verification>
                <ns:PersonName>
                    <ns:Surname>Tendulkar</ns:Surname>
                </ns:PersonName>
                <ns:Email>abc@gmail.com</ns:Email>
            </ns:Verification>
            <ns:TPA_Extensions>

                <ns:CancelDates/>
            </ns:TPA_Extensions>
        </ns:OTA_CancelRQ>
    </soapenv:Body>
	</soapenv:Envelope>
		 */
		StringBuilder req  = new  StringBuilder();		
		req.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<ns:OTA_CancelRQ CancelType=\"Cancel\"  Version=\"1.0\" >"
				+ "<ns:POS>"
				+ "<ns:Source>"
				+ "<ns:RequestorID MessagePassword=\""+apicred.getPassword()+"\" ID=\""+apicred.getPropertyId()+"\">"
				+ "<ns:CompanyName Code=\""+apicred.getUserName()+"\" />"
				+ "</ns:RequestorID>"
				+ "</ns:Source>"
				+ "</ns:POS>"
				+ "<ns:UniqueID ID=\""+uniqueid+"\"/>"
				+ "<ns:Verification>"
				+ "<ns:PersonName>"
				+ "<ns:Surname>"+name+"</ns:Surname>"
				+ "</ns:PersonName>"
				+ "<ns:Email>"+email+"</ns:Email>"
				+ "</ns:Verification>"
				+ "<ns:TPA_Extensions>"
				+ "<ns:CancelDates/>"
				+ "</ns:TPA_Extensions>"
				+ "</ns:OTA_CancelRQ>"			
				);



		logger.info("ns:OTA_CancelRQ - ---  initiate cancel:--- sources added"+ req);		
		return req;

	}




}
