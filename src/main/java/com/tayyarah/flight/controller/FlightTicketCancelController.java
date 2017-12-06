package com.tayyarah.flight.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.api.flight.tbo.model.CancelTicketInfo;
import com.tayyarah.api.flight.tbo.model.TboBookingDetailsResponse;
import com.tayyarah.api.flight.tbo.model.TboCancelTicketResponse;
import com.tayyarah.api.flight.tbo.model.TboCancellationStatusResponse;
import com.tayyarah.api.flight.tbo.model.TboReleasePNRResponse;
import com.tayyarah.api.flight.tbo.model.TicketCRInfo;
import com.tayyarah.apiconfig.model.TboFlightConfig;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.notification.NotificationUtil;
import com.tayyarah.common.notification.dao.NotificationDao;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.common.util.enums.InventoryTypeEnum;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.dao.FlightCancellationDao;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.entity.FlightOrderRowCancellation;
import com.tayyarah.flight.entity.FlightOrderRowCancellationAPIResponse;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.CancelTicketResponse;
import com.tayyarah.flight.model.FlightCancelRequest;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.flight.util.FlightWebServiceEndPointValidator;
import com.tayyarah.flight.util.api.tbo.TboServiceCall;
import com.tayyarah.flight.util.api.tbo.TboCommonUtil;
import com.tayyarah.services.FlightCancellationServices;

/*  Sample Request
 * 
 * {"userId":"tayyarahhelp@intellicommsolutions.com","password":"tayyarah","appKey":"v+iPH5eGGaX8p50qOYkqQQ==","orderId":"FTBO160729105359678","remarks":"Test","methodtype":"1","requesttype":"1","cancellationtype":"3"}
 */
@RestController
@RequestMapping("/cancelticket")
public class FlightTicketCancelController {

	@Autowired
	CompanyDao companyDao;
	@Autowired
	FlightBookingDao flightBookingDao;
	@Autowired
	FlightCancellationDao flightCancellationDao;
	@Autowired
	EmailDao emaildao;	
	@Autowired
	NotificationDao notificationDao;
	@Autowired
	FlightCancellationServices flightCancellationServices;
	LocalTime startTime = null;

	private FlightWebServiceEndPointValidator validator = new FlightWebServiceEndPointValidator();
	private FlightOrderRowCancellationAPIResponse flightOrderRowCancellationAPIResponse = null;
	static final Logger logger = Logger.getLogger(FlightTicketCancelController.class);

	@RequestMapping(value="/response",headers={"Accept=application/json"},produces={"application/json"})
	public @ResponseBody CancelTicketResponse getCancelticketresponse(@RequestBody FlightCancelRequest flightCancelRequest,HttpServletResponse response) {

		ResponseHeader.setResponse(response);//Setting response header
		logger.info("getCancelticketresponse method called : ");		

		AppControllerUtil.validateAppKey(companyDao, flightCancelRequest.getAppKey());
		validator.cancelrequestValidator(flightCancelRequest);
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, flightCancelRequest.getAppKey());
		if(appKeyVo==null)
		{
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
		}
		FlightOrderRow flightOrderRow = null;
		try {
			flightOrderRow = flightBookingDao.GetFlightBookingDetails(flightCancelRequest.getOrderId());
		}catch(Exception e){
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.INVALID_ORDERID);
		}
		TboFlightConfig tboFlightConfig = TboFlightConfig.GetTboConfig(appKeyVo);
		CancelTicketResponse cancelTicketResponse = new CancelTicketResponse();
		TboReleasePNRResponse tboReleasePNRResponse = null;
		// Release PNR Call
		if(flightCancelRequest.getMethodtype().equalsIgnoreCase(FlightCancelRequest.METHOD_RELEASEPNR))
		{
			TboBookingDetailsResponse tboBookingDetailsResponse ;
			tboReleasePNRResponse = new TboReleasePNRResponse();
			try{
				tboBookingDetailsResponse = TboServiceCall.callGetBookingDetailsService(flightOrderRow.getPnr(),flightOrderRow.getApi_commit(),tboFlightConfig);
				tboReleasePNRResponse = TboServiceCall.callReleasePNRService(tboBookingDetailsResponse,tboFlightConfig);
				cancelTicketResponse.setPnr(flightOrderRow.getPnr());
				cancelTicketResponse.setOrderid(flightOrderRow.getOrderId());
				cancelTicketResponse.setStatus(String.valueOf(tboReleasePNRResponse.getResponse().getResponseStatus()));
				if(tboReleasePNRResponse.getResponse().getResponseStatus() == CancelTicketResponse.ResponseStatus_Successfull){
					FlightDataBaseServices DBS = new FlightDataBaseServices();

					// Update Flight OrderRow
					DBS.updateHoldPNRTBO(flightOrderRow.getPnr(), flightOrderRow.getOrderId(), flightBookingDao,flightOrderRow.getApi_commit(),flightOrderRow.getSupplierDiscount(),flightOrderRow.getSystemDiscount(),flightOrderRow.getPublishedDiscount(),tboReleasePNRResponse.getResponse().getTraceId(),"Released");
					TboCommonUtil.updateMailstatus(flightOrderRow.getOrderId(),emaildao );
					TboCommonUtil.updateKeystatus(flightOrderRow.getTransaction_key(),  flightBookingDao);
					cancelTicketResponse.setStatusmessage("Success");
					cancelTicketResponse.setBookstatus("Released");

					// insert notication after booking is successful
					new NotificationUtil().insertNotification(appKeyVo,flightOrderRow.getOrderId() , "Flight Ticket Release", InventoryTypeEnum.FLIGHT_ORDER.getId(), true,notificationDao,companyDao); 
				}
				else{
					cancelTicketResponse.setStatusmessage("Failed");
					cancelTicketResponse.setBookstatus("Not Released");
				}
			}catch(Exception e){
				throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.CANCELLATION_FAILED);
			}
		}
		TboCancelTicketResponse tboCancelTicketResponse = null;

		// CancelTicket/ChangeRequest  Call
		if(flightCancelRequest.getMethodtype().equalsIgnoreCase(FlightCancelRequest.METHOD_CANCELTICKET))
		{	
			try {
				tboCancelTicketResponse = TboServiceCall.callCancelTicketService(flightOrderRow,flightCancelRequest,flightOrderRow.getFlightOrderCustomers(),tboFlightConfig);
				TboCancellationStatusResponse tboCancellationStatusResponse = null;
				List<CancelTicketInfo> cancelTicketInfolist = new ArrayList<CancelTicketInfo>();
				for (TicketCRInfo ticketCRInfo : tboCancelTicketResponse.getResponse().getTicketCRInfo()) {					
					tboCancellationStatusResponse = TboServiceCall.callCancelTicketStatusService(String.valueOf(ticketCRInfo.getChangeRequestId()),tboFlightConfig);
					flightOrderRowCancellationAPIResponse=flightCancellationServices.intializeFlightOrderRowCancellationAPIResponse(tboCancellationStatusResponse);
					CancelTicketInfo cancelTicketInfo =  new CancelTicketInfo();

					cancelTicketInfo.setApichangerequestid(String.valueOf(ticketCRInfo.getChangeRequestId()));
					cancelTicketInfo.setCancellationCharge(tboCancellationStatusResponse.getResponse().getCancellationCharge());
					cancelTicketInfo.setRefundedAmount(tboCancellationStatusResponse.getResponse().getRefundedAmount());
					cancelTicketInfo.setServiceTaxOnRAF(tboCancellationStatusResponse.getResponse().getServiceTaxOnRAF());

					if(tboCancellationStatusResponse.getResponse().getChangeRequestStatus() == CancelTicketResponse.ChangeRequestStatus_NotSet)
						cancelTicketInfo.setChangerequeststatus("NotSet");
					if(tboCancellationStatusResponse.getResponse().getChangeRequestStatus() == CancelTicketResponse.ChangeRequestStatus_Acknowledged)
						cancelTicketInfo.setChangerequeststatus("Acknowledged");
					if(tboCancellationStatusResponse.getResponse().getChangeRequestStatus() == CancelTicketResponse.ChangeRequestStatus_Assigned)
						cancelTicketInfo.setChangerequeststatus("Assigned");
					if(tboCancellationStatusResponse.getResponse().getChangeRequestStatus() == CancelTicketResponse.ChangeRequestStatus_Closed)
						cancelTicketInfo.setChangerequeststatus("Closed");
					if(tboCancellationStatusResponse.getResponse().getChangeRequestStatus() == CancelTicketResponse.ChangeRequestStatus_Completed)
						cancelTicketInfo.setChangerequeststatus("Completed");
					if(tboCancellationStatusResponse.getResponse().getChangeRequestStatus() == CancelTicketResponse.ChangeRequestStatus_Other)
						cancelTicketInfo.setChangerequeststatus("Other");
					if(tboCancellationStatusResponse.getResponse().getChangeRequestStatus() == CancelTicketResponse.ChangeRequestStatus_Pending)
						cancelTicketInfo.setChangerequeststatus("Pending");
					if(tboCancellationStatusResponse.getResponse().getChangeRequestStatus() == CancelTicketResponse.ChangeRequestStatus_Rejected)
						cancelTicketInfo.setChangerequeststatus("Rejected");
					if(tboCancellationStatusResponse.getResponse().getChangeRequestStatus() == CancelTicketResponse.ChangeRequestStatus_Unassigned)
						cancelTicketInfo.setChangerequeststatus("Unassigned");

					cancelTicketInfo.setStatus(String.valueOf(tboCancellationStatusResponse.getResponse().getResponseStatus()));
					if(tboCancellationStatusResponse.getResponse().getResponseStatus() == CancelTicketResponse.ResponseStatus_Successfull)
						cancelTicketResponse.setStatusmessage("Success");
					if(tboCancellationStatusResponse.getResponse().getResponseStatus() == CancelTicketResponse.ResponseStatus_InValidCredentials)
						cancelTicketResponse.setStatusmessage("Failed");
					if(tboCancellationStatusResponse.getResponse().getResponseStatus() == CancelTicketResponse.ResponseStatus_InValidRequest)
						cancelTicketResponse.setStatusmessage("Failed");
					if(tboCancellationStatusResponse.getResponse().getResponseStatus() == CancelTicketResponse.ResponseStatus_InValidSession)
						cancelTicketResponse.setStatusmessage("Failed");
					if(tboCancellationStatusResponse.getResponse().getResponseStatus() == CancelTicketResponse.ResponseStatus_Failed)
						cancelTicketResponse.setStatusmessage("Failed");
					if(tboCancellationStatusResponse.getResponse().getResponseStatus() == CancelTicketResponse.ResponseStatus_NotSet)
						cancelTicketResponse.setStatusmessage("Failed");

					cancelTicketInfolist.add(cancelTicketInfo);
				}				

				cancelTicketResponse.setCancelTicketInfo(cancelTicketInfolist);
				cancelTicketResponse.setPnr(flightOrderRow.getPnr());
				cancelTicketResponse.setOrderid(flightOrderRow.getOrderId());
				cancelTicketResponse.setStatus(String.valueOf(tboCancelTicketResponse.getResponse().getResponseStatus()));
				cancelTicketResponse.setFlightOrderRowCancellationAPIResponse(flightOrderRowCancellationAPIResponse);

				if(tboCancelTicketResponse.getResponse().getResponseStatus() == CancelTicketResponse.ResponseStatus_Successfull)
					cancelTicketResponse.setStatusmessage("Success");
				if(tboCancelTicketResponse.getResponse().getResponseStatus() == CancelTicketResponse.ResponseStatus_InValidCredentials)
					cancelTicketResponse.setStatusmessage("Failed");
				if(tboCancelTicketResponse.getResponse().getResponseStatus() == CancelTicketResponse.ResponseStatus_InValidRequest)
					cancelTicketResponse.setStatusmessage("Failed");
				if(tboCancelTicketResponse.getResponse().getResponseStatus() == CancelTicketResponse.ResponseStatus_InValidSession)
					cancelTicketResponse.setStatusmessage("Failed");
				if(tboCancelTicketResponse.getResponse().getResponseStatus() == CancelTicketResponse.ResponseStatus_Failed)
					cancelTicketResponse.setStatusmessage("Failed");
				if(tboCancelTicketResponse.getResponse().getResponseStatus() == CancelTicketResponse.ResponseStatus_NotSet)
					cancelTicketResponse.setStatusmessage("Failed");

				TboCommonUtil.CreateFlightOrderRowCancellation(tboCancelTicketResponse,cancelTicketResponse,flightOrderRow,flightCancellationDao);

				// insert notication after booking is successful
				new NotificationUtil().insertNotification(appKeyVo ,flightOrderRow.getOrderId() , "Flight Ticket Cancel", InventoryTypeEnum.FLIGHT_ORDER.getId(), true,notificationDao,companyDao); 


			} catch (Exception e) {
				logger.info("CancelTicketResponse Exception" +e.getMessage());
				e.printStackTrace();
				cancelTicketResponse.setStatus("2");
				cancelTicketResponse.setStatusmessage("Fail");
				cancelTicketResponse.setPnr("");
				cancelTicketResponse.setCancelTicketInfo(new ArrayList<CancelTicketInfo>());
				throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.CANCELLATION_FAILED);
			}
		}
		return cancelTicketResponse;
	}
	//ADDED BY BASHA AND COMMENTED BY BASHA
/*
	@RequestMapping(value = "/periodicCheck", method = RequestMethod.GET, headers = "Accept=application/json")
	public synchronized void getPeriodicCancellationCheck(){

		if (startTime == null) {
			startTime = new LocalTime();
		}
		LocalTime executionTime = new LocalTime();
		if (executionTime.isAfter(startTime) || executionTime.equals(startTime)) {
			LocalTime afterThreeMinte = startTime.plusMinutes(3);
			TboCancellationStatusResponse tboCancellationStatusResponse = null;
			List<FlightOrderRowCancellation> orderRowCancellations = flightBookingDao.getAllFailedCancellations();
			if(orderRowCancellations!=null && orderRowCancellations.size()>0){
				Iterator<FlightOrderRowCancellation> iterator=orderRowCancellations.iterator();
				while(iterator.hasNext()){
					FlightOrderRowCancellation flightOrderRowCancellation=iterator.next();
					if(flightOrderRowCancellation.getFlightOrderRow().getProviderAPI().equalsIgnoreCase("TBO")){
						try {	
							String appkey=companyDao.getCompanyConfigUsingId(Integer.parseInt(flightOrderRowCancellation.getFlightOrderRow().getConfigId())).getAppKey();
							AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
							if(appKeyVo==null)
							{
								throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
							}
							TboFlightConfig tboFlightConfig = TboFlightConfig.GetTboConfig(appKeyVo);
							tboCancellationStatusResponse = TboServiceCall.callCancelTicketStatusService(flightOrderRowCancellation.getApirequestid(),tboFlightConfig);
							flightOrderRowCancellationAPIResponse = flightCancellationServices.intializeFlightOrderRowCancellationAPIResponse(tboCancellationStatusResponse);
							flightCancellationServices.updateflightOrderRowCancellation(flightOrderRowCancellation, tboCancellationStatusResponse,flightOrderRowCancellationAPIResponse);
						}
						catch(Exception e){
							e.printStackTrace();
							logger.info("CancelTicketStausResponse by schdeuler Exception" +e.getMessage());
						}
					}

				}
				startTime = afterThreeMinte;
			}
		}
	}*/
}