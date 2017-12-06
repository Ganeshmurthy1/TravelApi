package com.tayyarah.common.util.soap;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class MarshalUnmarshal {

	public String marshal(Object object,Class className) throws JAXBException, ClassNotFoundException{
		JAXBContext jaxbContext = JAXBContext.newInstance(className);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		jaxbMarshaller.marshal(object, outStream);
		return outStream.toString();
	}

	public String marshalEncodingISO8859(Object object,Class className) throws JAXBException, ClassNotFoundException{
		JAXBContext jaxbContext = JAXBContext.newInstance(className);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		jaxbMarshaller.marshal(object, outStream);
		return outStream.toString();
	}

	public Object unMarshal(String packageName, SOAPMessage reply) throws JAXBException, SOAPException{
		JAXBContext jc = JAXBContext.newInstance(packageName);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Object response = (Object) unmarshaller.unmarshal(reply.getSOAPBody().getFirstChild()) ;
		return response;
	}
	public Object unMarshal(Class className, SOAPMessage reply) throws JAXBException, SOAPException{
		JAXBContext jc = JAXBContext.newInstance(className);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Object response = (Object) unmarshaller.unmarshal(reply.getSOAPBody().getFirstChild()) ; 
		return response;
	}

	public Object unMarshalError(String packageName, SOAPMessage reply) throws JAXBException, SOAPException{
		JAXBContext jc = JAXBContext.newInstance(packageName);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Object response = (Object) unmarshaller.unmarshal(reply.getSOAPBody().getFault().getDetail().getFirstChild()) ; //new File("hotelresp.xml"));

		return response;
	}
}
