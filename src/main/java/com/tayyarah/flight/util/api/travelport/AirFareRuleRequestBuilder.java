/**
@Author yogeshsharma
18-Aug-2015 2015
AirFareRuleRequestBuilder.java
 */
/**
 * 
 */
package com.tayyarah.flight.util.api.travelport;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.tayyarah.apiconfig.model.TravelportConfig;
import com.tayyarah.common.util.soap.MarshalUnmarshal;
import com.travelport.api_v33.AirResponse.AirFareRulesReq;
import com.travelport.api_v33.AirResponse.BillingPointOfSaleInfo;
import com.travelport.api_v33.AirResponse.FareInfo;
import com.travelport.api_v33.AirResponse.FareRuleKey;
import com.travelport.api_v33.AirResponse.TypeFareRuleType;

public class AirFareRuleRequestBuilder {
	static final Logger logger = Logger.getLogger(AirFareRuleRequestBuilder.class);
	
	public static StringBuilder buildFareRuleReq(TravelportConfig travelportConfig,FareInfo fareInfo) throws ClassNotFoundException, JAXBException{
		AirFareRulesReq airFareRulesReq = new AirFareRulesReq();
		BillingPointOfSaleInfo billingPointOfSaleInfo=new BillingPointOfSaleInfo();
		billingPointOfSaleInfo.setOriginApplication("uAPI");
		airFareRulesReq.setBillingPointOfSaleInfo(billingPointOfSaleInfo);
		airFareRulesReq.setTargetBranch(travelportConfig.getTargetBranch());
		airFareRulesReq.setAuthorizedBy("Test");
		airFareRulesReq.setFareRuleType(TypeFareRuleType.LONG);

		FareRuleKey fareRuleKey=new FareRuleKey();
		fareRuleKey.setFareInfoRef(fareInfo.getKey());
		fareRuleKey.setValue(fareInfo.getFareRuleKey().getValue());
		fareRuleKey.setProviderCode(fareInfo.getFareRuleKey().getProviderCode());
		airFareRulesReq.getFareRuleKey().add(fareRuleKey);

		MarshalUnmarshal marshalUnmarshal=new MarshalUnmarshal();
		String requestStr= marshalUnmarshal.marshal(airFareRulesReq,AirFareRulesReq.class);
		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
		String soapEnv = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
				"<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
				"<SOAP-ENV:Header/><SOAP-ENV:Body>";
		String closeSoapStr = " </SOAP-ENV:Body></SOAP-ENV:Envelope>";
		requestStr = requestStr.replace(xmlStr, soapEnv);
		requestStr+=closeSoapStr;
		StringBuilder sdb = new StringBuilder(requestStr);
		logger.info(" buildFareRuleReq sdb: "+sdb);
		return sdb;
	}
}