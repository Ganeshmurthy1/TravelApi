package com.tayyarah.flight.util.api.tbo;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tayyarah.api.flight.tbo.model.AuthenticateResponse;
import com.tayyarah.apiconfig.model.TboFlightConfig;


public class TboGetToken {
	private static String token;	
	static final Logger logger = Logger.getLogger(TboGetToken.class);
	
	public static String getToken(boolean value,TboFlightConfig tboFlightConfig){		
		if(token==null||value==false){		
			try {
				GetTokenvalue(tboFlightConfig);
			} catch (Exception e) {			
				logger.error("Exception -----"+ e);
			}
		}			
		return token;		
	}

	public static AuthenticateResponse getAuthResponse(TboFlightConfig tboFlightConfig) throws Exception{
		return GetTokenvalue(tboFlightConfig);
	}
	
	private static  AuthenticateResponse GetTokenvalue(TboFlightConfig tboFlightConfig)
			throws Exception {		
		AuthenticateResponse authenticateResponse = new AuthenticateResponse();
		TboAuthenticateRequest tboAuthenticateRequest = new TboAuthenticateRequest();		
		tboAuthenticateRequest.setClientId(tboFlightConfig.getClientid());
		tboAuthenticateRequest.setEndUserIp(tboFlightConfig.getEnduserip());
		tboAuthenticateRequest.setUserName(tboFlightConfig.getUsername());
		tboAuthenticateRequest.setPassword(tboFlightConfig.getPassword());
		ObjectMapper resmapper = new ObjectMapper();

		String URL_AUTHENTICATE=tboFlightConfig.getAuthUrlFlight();
		logger.info("URL_AUTHENTICATE -----"+URL_AUTHENTICATE);
		try {

			RestTemplate restTemplate = new RestTemplate();	
			String authenticateResponseInString1 = resmapper.writerWithDefaultPrettyPrinter().writeValueAsString(tboAuthenticateRequest);
			logger.info("-------------(((((--authenticateRequest pretty :"+authenticateResponseInString1);		

			//String requestJson = "{ \"ClientId\": \"ApiIntegration\", \"UserName\": \"intelli\", \"Password\": \"intelli@123\", \"EndUserIp\": \"192.168.11.120\" }";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(authenticateResponseInString1,headers);

			String answer = restTemplate.postForObject(tboFlightConfig.getAuthUrlFlight(), entity, String.class);
			logger.info("-------------response answer:"+answer);	

			authenticateResponse = resmapper.readValue(answer, AuthenticateResponse.class);
			logger.info("-------------(((((--authenticateResponse  :"+authenticateResponse);			

			authenticateResponseInString1 = resmapper.writerWithDefaultPrettyPrinter().writeValueAsString(authenticateResponse);
			logger.info("-------------(((((--authenticateResponse pretty :"+authenticateResponseInString1);			
			
			token = authenticateResponse.getTokenId();
			
		} catch (JsonGenerationException e) {
			logger.info(" authenticate hotel : JsonGenerationException--"+e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.info(" authenticate hotel : JsonMappingException--"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info(" authenticate hotel : IOException--"+e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			logger.info(" authenticate hotel : Exception--"+e.getMessage());
			e.printStackTrace();
		}		
		
		return authenticateResponse;
	}
}