package com.tayyarah.esmart.bus.util;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tayyarah.api.bus.esmart.model.ApiCancelConfirmation;
import com.tayyarah.api.bus.esmart.model.ApiCancelResponse;
import com.tayyarah.api.bus.esmart.model.ApiConfirmTicket;
import com.tayyarah.api.bus.esmart.model.BlockTicketRequest;
import com.tayyarah.api.bus.esmart.model.BlockTicketResponse;
import com.tayyarah.api.bus.esmart.model.GetAvaiableSeats;
import com.tayyarah.api.bus.esmart.model.GetAvailableBus;
import com.tayyarah.api.bus.esmart.model.GetStations;
import com.tayyarah.bus.dao.BusCommonDao;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.bus.model.AvailableBuses;
import com.tayyarah.bus.model.AvailableSeats;
import com.tayyarah.bus.model.BusBlockTicketRequest;
import com.tayyarah.bus.model.BusBlockTicketResponse;
import com.tayyarah.bus.model.BusCancelRequest;
import com.tayyarah.bus.model.BusCancelResponse;
import com.tayyarah.bus.model.BusConfirmRequest;
import com.tayyarah.bus.model.BusConfirmResponse;
import com.tayyarah.bus.model.BusLayoutRequest;
import com.tayyarah.bus.model.BusPaymentRequest;
import com.tayyarah.bus.model.BusSearchRequest;
import com.tayyarah.bus.model.BusStations;
import com.tayyarah.bus.model.TayyarahBusSearchMap;
import com.tayyarah.bus.model.TayyarahBusSeatMap;
import com.tayyarah.bus.util.BusErrorMessages;
import com.tayyarah.bus.util.BusException;
import com.tayyarah.bus.util.ErrorCodeCustomerEnum;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.common.util.FileUtil;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.bus.model.BusMarkUpConfig;



public class EsmartServiceCall {
	static final Logger logger = Logger.getLogger(EsmartServiceCall.class);

	public static BusStations getStationsList(EsmartBusConfig esmartBusConfig){
		BusStations busStations = null; 
		try{
			ObjectMapper mapper = new ObjectMapper();
			String response = getESmartTravelsResponse(esmartBusConfig,EsmartBusConfig.METHOD_STATIONS);
			GetStations getStationsResponse = mapper.readValue(response, GetStations.class);
			busStations = EsmartResponseParser.busStationsParser(getStationsResponse);

		}catch(Exception e){
			logger.error("getStationsList Exception" +e.getMessage());
		}
		return busStations;
	}

	public static AvailableBuses getAvailableBusList(EsmartBusConfig esmartBusConfig,BusSearchRequest busSearchRequest,Map<String,List<BusMarkUpConfig>> markupMap,CurrencyConversionMap currencyConversionMap,String decAppKey,CompanyConfigDAO companyConfigDAO,String searchKey,CompanyDao CompanyDAO){
		AvailableBuses availableBuses = null;
		try{
			String requestString = EsmartRequestBuilder.getAvailableBusRequest(busSearchRequest.getOrigin(), busSearchRequest.getDestination(), busSearchRequest.getOnwardDate());
			// Save Request
			try{
				FileUtil.writeJson("bus", "ESmart Travels", "search", false, requestString,  String.valueOf(searchKey));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}
			ObjectMapper mapper = new ObjectMapper();
			String response = getESmartTravelsResponse(esmartBusConfig,EsmartBusConfig.METHOD_AVAILABLEBUSES+requestString);
			// Save Response
			try{
				FileUtil.writeJson("bus", "ESmart Travels", "search", true, response,  String.valueOf(searchKey));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}

			GetAvailableBus getAvailableBusResponse = mapper.readValue(response, GetAvailableBus.class);
			availableBuses = EsmartResponseParser.busAvailableParser(getAvailableBusResponse,markupMap,busSearchRequest,currencyConversionMap,decAppKey,companyConfigDAO,CompanyDAO);
			availableBuses.setOrigin(busSearchRequest.getOrigin());
			availableBuses.setDestination(busSearchRequest.getDestination());
			availableBuses.setOnwardDate(busSearchRequest.getOnwardDate());
			availableBuses.setCurrency(busSearchRequest.getCurrency());

		}catch(Exception e){
			logger.error("getAvailableBusList Exception" +e.getMessage());
		}
		return availableBuses;
	}

	public static AvailableSeats getAvailableSeats(EsmartBusConfig esmartBusConfig,BusLayoutRequest busLayoutRequest,Map<String,List<BusMarkUpConfig>> markupMap,CurrencyConversionMap currencyConversionMap,String decAppKey,CompanyConfigDAO companyConfigDAO,TayyarahBusSearchMap  busSearchMap,CompanyDao companyDAO){
		AvailableSeats availableSeats = null;
		try{
			String requestString =  EsmartRequestBuilder.getAvailableBusSeatsRequest(busLayoutRequest.getOrigin(), busLayoutRequest.getDestination(), busLayoutRequest.getOnwardDate(),busLayoutRequest.getInventoryType(),busLayoutRequest.getRouteScheduleId());
			// Save Request
			try{
				FileUtil.writeJson("bus", "ESmart Travels", "seatlayout", false, requestString,  String.valueOf(busLayoutRequest.getSearchkey()));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}
			ObjectMapper mapper = new ObjectMapper();
			String response = getESmartTravelsResponse(esmartBusConfig,EsmartBusConfig.METHOD_BUSLAYOUT+requestString);
			// Save Response
			try{
				FileUtil.writeJson("bus", "ESmart Travels", "seatlayout", true, response,  String.valueOf(busLayoutRequest.getSearchkey()));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}
			GetAvaiableSeats getAvaiableSeatsResponse = mapper.readValue(response, GetAvaiableSeats.class);
			availableSeats = EsmartResponseParser.availableSeatParser(getAvaiableSeatsResponse, markupMap, busLayoutRequest, currencyConversionMap, decAppKey, companyConfigDAO,busSearchMap,companyDAO);


		}catch(Exception e){
			logger.error("getAvailableSeats Exception" +e.getMessage());
		}
		return availableSeats;
	}

	public static BusBlockTicketResponse blockBusTicket(EsmartBusConfig esmartBusConfig,BusBlockTicketRequest busBlockTicketRequest,Map<String,List<BusMarkUpConfig>> markupMap,CurrencyConversionMap currencyConversionMap,String decAppKey,CompanyConfigDAO companyConfigDAO,TayyarahBusSeatMap tayyarahBusSeatMap,TayyarahBusSearchMap  busSearchMap){
		BusBlockTicketResponse busBlockTicketResponse = null;
		try{
			BlockTicketRequest blockTicketRequest =  EsmartRequestBuilder.getBlockSeatRequest(busBlockTicketRequest, tayyarahBusSeatMap);			
			ObjectMapper mapper = new ObjectMapper();
			String blockTicketRequestRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(blockTicketRequest);	
			// Save Request
			try{
				FileUtil.writeJson("bus", "ESmart Travels", "block", false, blockTicketRequestRequestInString,  String.valueOf(busBlockTicketRequest.getSearchkey()));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}
			String response = getESmartTravelsResponseInPost(esmartBusConfig, EsmartBusConfig.METHOD_BLOCKTICKET, blockTicketRequestRequestInString);
			// Save Response
			try{
				FileUtil.writeJson("bus", "ESmart Travels", "block", true, response,  String.valueOf(busBlockTicketRequest.getSearchkey()));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}
			BlockTicketResponse getBlockTicketResponse = mapper.readValue(response, BlockTicketResponse.class);
			busBlockTicketResponse = EsmartResponseParser.blockTicketResponseParser(getBlockTicketResponse, busBlockTicketRequest, markupMap, currencyConversionMap, decAppKey, companyConfigDAO, tayyarahBusSeatMap, busSearchMap);
		}catch(Exception e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BLOCKFAILED.getErrorMessage());
		}
		return busBlockTicketResponse;
	}
	public static BusConfirmResponse confirmBusTicket(EsmartBusConfig esmartBusConfig,BusConfirmRequest busConfirmRequest,BusBlockTicketResponse busBlockTicketResponse,BusOrderRow busOrderRow,BusCommonDao busCommonDao){
		BusConfirmResponse busConfirmResponse = new BusConfirmResponse();
		try{
			String confirmRequest =  EsmartRequestBuilder.getConfirmTicketRequest(busConfirmRequest.getBlockTicketKey());
			// Save Request
			try{
				FileUtil.writeJson("bus", "ESmart Travels", "confirm", false, confirmRequest,  String.valueOf(busConfirmRequest.getSearchkey()));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}
			ObjectMapper mapper = new ObjectMapper();
			String response = getESmartTravelsResponse(esmartBusConfig,EsmartBusConfig.METHOD_SEATBOOKING+confirmRequest);
			// Save Response
			try{
				FileUtil.writeJson("bus", "ESmart Travels", "confirm", true, response,  String.valueOf(busConfirmRequest.getSearchkey()));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}
			ApiConfirmTicket getApiConfirmTicket = mapper.readValue(response, ApiConfirmTicket.class);
			busConfirmResponse = EsmartResponseParser.confirmTicketResposeParser(getApiConfirmTicket, busBlockTicketResponse,busOrderRow,busCommonDao);

		}catch(Exception e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BOOKINGFAILED.getErrorMessage());
		}
		return busConfirmResponse;
	}
	public static BusConfirmResponse paymentconfirmBusTicket(EsmartBusConfig esmartBusConfig,BusPaymentRequest busPaymentRequest,BusBlockTicketResponse busBlockTicketResponse,BusOrderRow busOrderRow){
		BusConfirmResponse busConfirmResponse = new BusConfirmResponse();
		try{
			String confirmRequest =  EsmartRequestBuilder.getConfirmTicketRequest(busBlockTicketResponse.getBlockTicketKey());
			// Save Request
			try{
				FileUtil.writeJson("bus", "ESmart Travels", "paymentconfirm", false, confirmRequest,  String.valueOf(busBlockTicketResponse.getSearchkey()));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}
			ObjectMapper mapper = new ObjectMapper();
			String response = getESmartTravelsResponse(esmartBusConfig,EsmartBusConfig.METHOD_SEATBOOKING+confirmRequest);
			// Save Response
			try{
				FileUtil.writeJson("bus", "ESmart Travels", "paymentconfirm", true, response,  String.valueOf(busBlockTicketResponse.getSearchkey()));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}
			ApiConfirmTicket getApiConfirmTicket = mapper.readValue(response, ApiConfirmTicket.class);
			busConfirmResponse = EsmartResponseParser.paymentTicketResposeParser(getApiConfirmTicket, busBlockTicketResponse,busOrderRow);

		}catch(Exception e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BOOKINGFAILED.getErrorMessage());
		}
		return busConfirmResponse;
	}

	public static BusCancelResponse getCancelConfirmationResponse(EsmartBusConfig esmartBusConfig,BusCancelRequest busCancelRequest,BusOrderRow busOrderRow,BigDecimal totalAmtPaid){
		BusCancelResponse busCancelResponse = new BusCancelResponse();
		try{
			ObjectMapper mapper = new ObjectMapper();
			ApiCancelConfirmation apiCancelConfirmationRequest = EsmartRequestBuilder.getConfirmCancelTicketRequest(busCancelRequest, busOrderRow.getConfirmationNumber());
			String RequestRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiCancelConfirmationRequest);	

			// Save Request
			try{
				FileUtil.writeJson("bus", "ESmart Travels", "cancelconfirmation", false, RequestRequestInString,  String.valueOf(busCancelRequest.getSearchkey()));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}
			String response = getESmartTravelsResponseInPost(esmartBusConfig, EsmartBusConfig.METHOD_CANCELTICKETCONFIRMATION, RequestRequestInString);
			// Save Response
			try{
				FileUtil.writeJson("bus", "ESmart Travels", "cancelconfirmation", true, response,  String.valueOf(busCancelRequest.getSearchkey()));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}
			ApiCancelResponse getApiCancelResponse = mapper.readValue(response, ApiCancelResponse.class);
			busCancelResponse = EsmartResponseParser.cancellationResponseParser(getApiCancelResponse,busCancelRequest, busOrderRow,totalAmtPaid,apiCancelConfirmationRequest.getEtsTicketNo());
		}catch(Exception e){

		}
		return busCancelResponse;
	}
	
	public static BusCancelResponse getCancelTicketResponse(EsmartBusConfig esmartBusConfig,BusCancelRequest busCancelRequest,BusOrderRow busOrderRow,BigDecimal totalAmtPaid){
		BusCancelResponse busCancelResponse = new BusCancelResponse();
		try{
			ObjectMapper mapper = new ObjectMapper();
			ApiCancelConfirmation apiCancelConfirmationRequest = EsmartRequestBuilder.getConfirmCancelTicketRequest(busCancelRequest, busOrderRow.getConfirmationNumber());
			String RequestRequestInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiCancelConfirmationRequest);	

			// Save Request
			try{
				FileUtil.writeJson("bus", "ESmart Travels", "cancelticket", false, RequestRequestInString,  String.valueOf(busCancelRequest.getSearchkey()));
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}
			String response = getESmartTravelsResponseInPost(esmartBusConfig, EsmartBusConfig.METHOD_CANCELTICKET, RequestRequestInString);
			// Save Response
			try{
				if(response!=null && !response.trim().equalsIgnoreCase("")){
					
					FileUtil.writeJson("bus", "ESmart Travels", "cancelticket", true, response,  String.valueOf(busCancelRequest.getSearchkey()));
					ApiCancelResponse getApiCancelResponse = mapper.readValue(response, ApiCancelResponse.class);
					busCancelResponse = EsmartResponseParser.cancellationResponseParser(getApiCancelResponse,busCancelRequest, busOrderRow,totalAmtPaid,apiCancelConfirmationRequest.getEtsTicketNo());
					
				}else{
					throw new Exception("Response from api is coming empty "+response);
					
			}
				
			} catch (Exception e) {
				logger.error(" The filename, directory name ", e);
			}
			
		}catch(Exception e){

		}
		return busCancelResponse;
	}

	// Get Response From ESmart Travels Requests
	public static String getESmartTravelsResponse(EsmartBusConfig esmartBusConfig,String methodType){		
		String responseString = null; 
		try{			
			String url = esmartBusConfig.getUrl() + methodType;
			logger.info("---------------Bus url:"+ url);
			CredentialsProvider provider = new BasicCredentialsProvider();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(esmartBusConfig.getUsername(), esmartBusConfig.getPassword());
			provider.setCredentials(AuthScope.ANY, credentials);
			HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();			
			HttpResponse response = client.execute(new HttpGet(url));		
			responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
			logger.info("---------------Bus responseString:"+ responseString);
		}catch(Exception e){
			logger.error("getESmartTravelsResponse Exception" +e.getMessage());
		}
		return responseString;		
	}

	public static String getESmartTravelsResponseInPost(EsmartBusConfig esmartBusConfig,String methodType,String Request){
		String response = "";
		try{
			logger.info("---------------searchRequest:"+Request);
			String url = esmartBusConfig.getUrl() + methodType;
			CredentialsProvider provider = new BasicCredentialsProvider();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(esmartBusConfig.getUsername(), esmartBusConfig.getPassword());
			provider.setCredentials(AuthScope.ANY, credentials);
			HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();		
			HttpPost post = new HttpPost(url);
			StringEntity entity = new StringEntity(Request);			
			entity.setContentType("application/json;charset=UTF-8");
			post.setEntity(entity);
			HttpResponse responsetemp = client.execute(post);		
			response = EntityUtils.toString(responsetemp.getEntity(), "UTF-8");				
			logger.info("---------------response:"+response);

		}catch(Exception e){
			logger.error("getESmartTravelsResponseInPost Exception" +e.getMessage());
		}
		return response;
	}


}
