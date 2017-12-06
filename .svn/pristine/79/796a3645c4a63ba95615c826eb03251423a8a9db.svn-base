/**
@Author ilyas
07-12-2015 
BlueStarFareRuleRequestBuilder.java
 */
/**
 * 
 */
package com.tayyarah.flight.util.api.bluestar;

import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;
import com.tayyarah.apiconfig.model.BluestarConfig;

public class BlueStarFareRuleRequestBuilder {
	static final Logger logger=Logger.getLogger(BlueStarFareRuleRequestBuilder.class);	

	public static StringBuilder createFareRuleRequest(String trackNO) throws ClassNotFoundException, JAXBException
	{
		String headerSTR = setHeader();
		String soapEnv = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
				"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">" +
				headerSTR+"<soap:Body><tem:GetFareRule>   <tem:requestXML><![CDATA[";
		String body="<GetFareRuleRequest><TrackNo>"+trackNO+"</TrackNo></GetFareRuleRequest>";	
		String closeSoapStr = "]]></tem:requestXML></tem:GetFareRule> </soap:Body></soap:Envelope>";		
		StringBuilder sdb = new StringBuilder();
		sdb.append(soapEnv+body+closeSoapStr);
		logger.info("createSearchRequest sdb: "+sdb);
		return sdb;
	}	

	public static  String setHeader(){
		StringBuilder sb=new StringBuilder();
		BluestarConfig bluestarConfig = BluestarConfig.GetBluestarConfig(null);
		sb.append("<soap:Header><tem:Authenticate><tem:InterfaceCode>"+bluestarConfig.getInterface_Code()+"</tem:InterfaceCode><tem:InterfaceAuthKey>"+bluestarConfig.getInterface_Auth_Key()+"</tem:InterfaceAuthKey><tem:AgentCode>"+bluestarConfig.getAgent_Code()+"</tem:AgentCode></tem:Authenticate></soap:Header>");
		return sb.toString();
	}
}