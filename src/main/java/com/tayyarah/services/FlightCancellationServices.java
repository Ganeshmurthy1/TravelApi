package com.tayyarah.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tayyarah.api.flight.tbo.model.CancellationStatusResponse;
import com.tayyarah.api.flight.tbo.model.TboCancellationStatusResponse;
import com.tayyarah.flight.dao.FlightCancellationDao;
import com.tayyarah.flight.entity.FlightOrderRowCancellation;
import com.tayyarah.flight.entity.FlightOrderRowCancellationAPIResponse;
import com.tayyarah.flight.model.CancelTicketResponse;


@Service
public class FlightCancellationServices {

	@Autowired
	FlightCancellationDao FCDAO;


	private FlightOrderRowCancellationAPIResponse flightOrderRowCancellationAPIResponse=new FlightOrderRowCancellationAPIResponse();
	private CancellationStatusResponse cancellationStatusResponse=null;
	static final Logger logger = Logger.getLogger(FlightCancellationServices.class);

	public  FlightOrderRowCancellationAPIResponse intializeFlightOrderRowCancellationAPIResponse(TboCancellationStatusResponse tboCancellationStatusResponse){
		cancellationStatusResponse=tboCancellationStatusResponse.getResponse();

		try{
			if(cancellationStatusResponse.getResponseStatus() !=null)
				flightOrderRowCancellationAPIResponse.setResponseStatus(cancellationStatusResponse.getResponseStatus());
			if(cancellationStatusResponse.getTraceId()!=null)
				flightOrderRowCancellationAPIResponse.setTraceId(cancellationStatusResponse.getTraceId());
			if(cancellationStatusResponse.getChangeRequestId() !=null)
				flightOrderRowCancellationAPIResponse.setChangeRequestId(cancellationStatusResponse.getChangeRequestId());
			if(cancellationStatusResponse.getTicketId()!=null)
				flightOrderRowCancellationAPIResponse.setTicketId(cancellationStatusResponse.getTicketId());
			if(cancellationStatusResponse.getRefundedAmount() !=null)
				flightOrderRowCancellationAPIResponse.setRefundedAmount(new BigDecimal( cancellationStatusResponse.getRefundedAmount()));
			if(cancellationStatusResponse.getCancellationCharge()!=null)
				flightOrderRowCancellationAPIResponse.setCancellationCharge(new BigDecimal(cancellationStatusResponse.getCancellationCharge()));
			if(cancellationStatusResponse.getServiceTaxOnRAF() !=null)
				flightOrderRowCancellationAPIResponse.setServiceTaxOnRAF(new BigDecimal(cancellationStatusResponse.getServiceTaxOnRAF()));
			if(cancellationStatusResponse.getChangeRequestStatus() !=null)
				flightOrderRowCancellationAPIResponse.setChangeRequestStatus(cancellationStatusResponse.getChangeRequestStatus());
			if(cancellationStatusResponse.getSwachhBharatCess()!=null)
				flightOrderRowCancellationAPIResponse.setSwachhBharatCess(new BigDecimal(cancellationStatusResponse.getSwachhBharatCess()));
			if(cancellationStatusResponse.getCreditNoteNo()!=null)
				flightOrderRowCancellationAPIResponse.setCreditNoteNo(cancellationStatusResponse.getCreditNoteNo());
			if(cancellationStatusResponse.getKrishiKalyanCess()!=null)
				flightOrderRowCancellationAPIResponse.setKrishiKalyanCess(cancellationStatusResponse.getKrishiKalyanCess());
			if(cancellationStatusResponse.getCreditNoteCreatedOn()!=null) 
				flightOrderRowCancellationAPIResponse.setCreditNoteCreatedOn(cancellationStatusResponse.getCreditNoteCreatedOn());
		}
		catch (Exception e) {
			logger.info("CancelTicketResponse Exception" +e.getMessage());
		}		
		return flightOrderRowCancellationAPIResponse;
	}

	public void updateflightOrderRowCancellation(FlightOrderRowCancellation flightOrderRowCancellation
			,TboCancellationStatusResponse tboCancellationStatusResponse,FlightOrderRowCancellationAPIResponse flightOrderRowCancellationAPIResponse){
		cancellationStatusResponse=tboCancellationStatusResponse.getResponse();
		flightOrderRowCancellation.setUpdatedAt(new Timestamp(new Date().getTime()));
		flightOrderRowCancellation.setApistatuscode(String.valueOf(cancellationStatusResponse.getResponseStatus()));
		if(cancellationStatusResponse.getResponseStatus() == CancelTicketResponse.ResponseStatus_Successfull)
			flightOrderRowCancellation.setStatusmessage("Success");
		if(cancellationStatusResponse.getResponseStatus() == CancelTicketResponse.ResponseStatus_InValidCredentials)
			flightOrderRowCancellation.setStatusmessage("Failed");
		if(cancellationStatusResponse.getResponseStatus() == CancelTicketResponse.ResponseStatus_InValidRequest)
			flightOrderRowCancellation.setStatusmessage("Failed");
		if(cancellationStatusResponse.getResponseStatus() == CancelTicketResponse.ResponseStatus_InValidSession)
			flightOrderRowCancellation.setStatusmessage("Failed");
		if(cancellationStatusResponse.getResponseStatus() == CancelTicketResponse.ResponseStatus_Failed)
			flightOrderRowCancellation.setStatusmessage("Failed");
		if(cancellationStatusResponse.getResponseStatus() == CancelTicketResponse.ResponseStatus_NotSet)
			flightOrderRowCancellation.setStatusmessage("Failed");
		flightOrderRowCancellation.setApichargeamount(cancellationStatusResponse.getCancellationCharge());
		if(cancellationStatusResponse.getCancellationCharge()==null){
			flightOrderRowCancellation.setApichargeamount(new Double("0.00"));
		}
		flightOrderRowCancellation.setApirefundamount(cancellationStatusResponse.getRefundedAmount());
		if(cancellationStatusResponse.getRefundedAmount()==null){
			flightOrderRowCancellation.setApirefundamount(new Double("0.00"));
		}
		flightOrderRowCancellation.setApiservicetaxonraf(cancellationStatusResponse.getServiceTaxOnRAF());
		if(cancellationStatusResponse.getServiceTaxOnRAF()==null){
			flightOrderRowCancellation.setApiservicetaxonraf(new Double("0.00"));
		}
		flightOrderRowCancellation.setApitrace_id(cancellationStatusResponse.getTraceId());
		int noofattepmt = flightOrderRowCancellation.getNoofAttempt()!=null?flightOrderRowCancellation.getNoofAttempt():0;
		if(noofattepmt <= 5){
			noofattepmt = noofattepmt + 1;
			flightOrderRowCancellation.setNoofAttempt(noofattepmt);		
			flightOrderRowCancellationAPIResponse.setFlightOrderRowCancellation(flightOrderRowCancellation);
			try{
				FCDAO.insertFlightOrderRowCancellation(flightOrderRowCancellation);
				FCDAO.saveSupplierResponse(flightOrderRowCancellationAPIResponse);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
}
