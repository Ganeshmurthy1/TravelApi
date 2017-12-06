/**
@Author yogeshsharma
15-Jul-2015 2015
SoapClient.java
 */
/**
 * 
 */
package com.tayyarah.common.util.soap;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import org.apache.axis.Message;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
import com.tayyarah.hotel.util.HotelApiCredentials;


public class SoapClient {
	public static final Logger logger = Logger.getLogger(SoapClient.class);
	private final static String SOAP_CONTENT_TYPE = "application/x-www-form-urlencoded";	

	public SOAPMessage sendSoapMessage(SOAPMessage requestMessage,
			String remoteHost) throws UnsupportedOperationException,
	SOAPException{
		SOAPMessage reply = null;
		try{
			SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnFactory.createConnection();
			reply = connection.call(requestMessage, remoteHost);
		}
		catch(SOAPException ex){
			logger.error("Searhing hotels: "+ex.getMessage());
			ex.printStackTrace();		
		}
		catch(Exception ex){
			logger.error("Searhing hotels: "+ex.getMessage());
			ex.printStackTrace();
		}
		return reply;
	}

	public static SOAPMessage sendSoapMessageTest(StringBuilder reqString,
			HotelApiCredentials api) throws UnsupportedOperationException,SOAPException{
		SOAPMessage reply = null;
		try{
			SOAPMessage requestMessage = buildSoapMsgFromStr(reqString);
			SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnFactory.createConnection();		
			reply = connection.call(requestMessage, api.getEndPointUrl());			
		}
		catch(SOAPException ex){
			logger.error("Searhing hotels: "+ex.getMessage());
			ex.printStackTrace();		
		}
		catch(Exception ex){
			logger.error("Searhing hotels: "+ex.getMessage());
			ex.printStackTrace();
		}
		return reply;
	}

	public static SOAPMessage sendSoapMessageTest(StringBuilder reqString,
			String url) throws UnsupportedOperationException,SOAPException{
		SOAPMessage reply = null;
		try{
			SOAPMessage requestMessage = buildSoapMsgFromStr(reqString);
			SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnFactory.createConnection();			
			reply = connection.call(requestMessage, url);
		}
		catch(SOAPException ex){
			logger.error("Searhing hotels: "+ex.getMessage());
			ex.printStackTrace();		
		}
		catch(Exception ex){
			logger.error("Searhing hotels: "+ex.getMessage());
			ex.printStackTrace();
		}
		return reply;
	}

	public static SOAPMessage sendSoapMessageTest(StringBuilder reqString,
			String url, boolean israwtext) throws UnsupportedOperationException,SOAPException{
		SOAPMessage reply = null;
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docFactory.setNamespaceAware(true);
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();			
			Document doc = docBuilder.parse(reqString.toString());			
			SOAPMessage requestMessage = toSOAPMessage(doc);
			SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnFactory.createConnection();
			reply = connection.call(requestMessage, url);
		}
		catch(SOAPException ex){
			logger.error("Searhing hotels: "+ex.getMessage());
			ex.printStackTrace();			
		}
		catch(Exception ex){
			logger.error("Searhing hotels: "+ex.getMessage());
			ex.printStackTrace();
		}
		return reply;
	}

	public static SOAPMessage buildSoapMsgFromStr(StringBuilder requestXml) throws SOAPException{
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage message = messageFactory.createMessage();
		SOAPPart soapPart =     message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		SOAPBody body =         envelope.getBody();
		byte[] buffer                 = requestXml.toString().getBytes();  
		ByteArrayInputStream stream   = new ByteArrayInputStream(buffer);  
		StreamSource source           = new StreamSource(stream);  
		soapPart.setContent(source);
		if(message.saveRequired())
			message.saveChanges();
		return message;
	}

	/**
	 * Convert a DOM Document into a soap message.
	 * <p/>
	 *
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public static SOAPMessage toSOAPMessage(Document doc) throws Exception {
		Canonicalizer c14n =
				Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_WITH_COMMENTS);
		byte[] canonicalMessage = c14n.canonicalizeSubtree(doc);
		ByteArrayInputStream in = new ByteArrayInputStream(canonicalMessage);
		MessageFactory factory = MessageFactory.newInstance();
		return factory.createMessage(null, in);
	}

	/**
	 * Update soap message.
	 * <p/>
	 *
	 * @param doc
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static SOAPMessage updateSOAPMessage(Document doc,
			SOAPMessage message)
					throws Exception {
		DOMSource domSource = new DOMSource(doc);
		message.getSOAPPart().setContent(domSource);
		return message;
	}
	/**
	 * Convert a SOAPNessage to String <p/>
	 * 
	 * @param soapMsg
	 * @return
	 * @throws Exception
	 */
	public static String toString(SOAPMessage soapMsg) throws Exception {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		soapMsg.writeTo(out);
		String soapMsgStr = new String(out.toByteArray());
		return soapMsgStr;
	}	

	/**
	 * Convert a DOM Document into an Axis message. <p/>
	 * 
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public static Message toAxisMessage(Document doc) throws Exception {
		Canonicalizer c14n = Canonicalizer
				.getInstance(Canonicalizer.ALGO_ID_C14N_WITH_COMMENTS);
		byte[] canonicalMessage = c14n.canonicalizeSubtree(doc);
		ByteArrayInputStream in = new ByteArrayInputStream(canonicalMessage);
		return new Message(in);
	}	

	/**
	 * Search within a SOAP message to get all the param values for a method.
	 * Return a map with all found param-value pairs. Return a empty map if
	 * nothing is found. <p/>
	 * 
	 * @param message
	 * @param methodName
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getMethodParameters(Message message,
			String methodName, List<String> params) throws Exception {
		Document doc = message.getSOAPEnvelope().getAsDocument();
		return getMethodParameters(doc, methodName, params);
	}
	/**
	 * @param doc
	 * @param methodName
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getMethodParameters(Document doc,
			String methodName, List<String> params) throws Exception {

		Map<String, String> paramMap = new HashMap<String, String>();
		if (doc == null) {
			return paramMap;
		}
		// Ensure the DOM view is same as it was saved
		doc.getDocumentElement().normalize();
		NodeList soapbody = doc.getElementsByTagName("soapenv:Body");
		Element soapBodyElement = (Element) soapbody.item(0);
		if (soapBodyElement == null) {
			throw new Exception("Wrong DOM Format: no soapenv:Body found!");
		}

		NodeList methods = soapBodyElement.getElementsByTagName(methodName);
		if (methods == null || methods.getLength() == 0) {
			return paramMap;
		}
		if (methods.getLength() > 1) {
			throw new Exception("Multiple methods with same name " + methodName
					+ " are found in a SOAP Message!");
		}
		Node method = methods.item(0);

		if (method.getNodeType() == Node.ELEMENT_NODE) {
			Element me = (Element) method;
			Element pe = null;
			Iterator<String> iterator = params.iterator();
			String param = null;
			String value = null;
			// loop over the param list and store param-value pair in the map
			while (iterator.hasNext()) {
				param = (String) iterator.next();
				pe = (Element) (me.getElementsByTagName(param).item(0));
				// ignore if the param can't be found in xml
				if (pe != null) {
					// get value of param(i)
					value = pe.getFirstChild().getNodeValue().trim();
					paramMap.put(param, value);
				}
			}
		}
		return paramMap;
	}
}