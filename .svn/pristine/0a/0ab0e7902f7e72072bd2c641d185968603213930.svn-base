package com.tayyarah.flight.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.tayyarah.api.flight.tbo.model.CancelTicketInfo;

import com.tayyarah.api.flight.tbo.model.TboCancelTicketResponse;
import com.tayyarah.apiconfig.model.TboFlightConfig;

import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.dao.FlightCancellationDao;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.entity.FlightOrderRowCancellation;
import com.tayyarah.flight.entity.FlightOrderRowCancellationAPIResponse;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.CancelTicketResponse;
import com.tayyarah.flight.model.Flightsearch;


public class TboFlightCommonUtil {
	public static final Logger logger = Logger.getLogger(TboFlightCommonUtil.class);

	public static void CreateFlightOrderRowCancellation(TboCancelTicketResponse tboCancelTicketResponse,CancelTicketResponse cancelTicketResponse,FlightOrderRow flightOrderRow,FlightCancellationDao FCDAO){
		Integer countOfattempt=null;
		FlightOrderRowCancellation flightOrderRowCancellation = new FlightOrderRowCancellation();
		for (CancelTicketInfo cancelTicketInfo : cancelTicketResponse.getCancelTicketInfo()) {
			flightOrderRowCancellation.setApichargeamount(cancelTicketInfo.getCancellationCharge());
			if(cancelTicketInfo.getCancellationCharge()==null){
				flightOrderRowCancellation.setApichargeamount(new Double("0.00"));
			}
			flightOrderRowCancellation.setApirefundamount(cancelTicketInfo.getRefundedAmount());
			if(cancelTicketInfo.getRefundedAmount()==null){
				flightOrderRowCancellation.setApirefundamount(new Double("0.00"));
			}
			flightOrderRowCancellation.setApiservicetaxonraf(cancelTicketInfo.getServiceTaxOnRAF());
			if(cancelTicketInfo.getServiceTaxOnRAF()==null){
				flightOrderRowCancellation.setApiservicetaxonraf(new Double("0.00"));
			}
			flightOrderRowCancellation.setApirequestid(cancelTicketInfo.getApichangerequestid());
		}
		flightOrderRowCancellation.setOrderid(flightOrderRow.getOrderId());
		flightOrderRowCancellation.setFlightOrderRow(flightOrderRow);
		flightOrderRowCancellation.setStatusmessage(cancelTicketResponse.getStatusmessage());
		flightOrderRowCancellation.setApistatuscode(cancelTicketResponse.getStatus());
		flightOrderRowCancellation.setChangeRequestStatus(cancelTicketResponse.getFlightOrderRowCancellationAPIResponse().getChangeRequestStatus());
		flightOrderRowCancellation.setPaymenttype(flightOrderRow.getPaidBy());
		flightOrderRowCancellation.setApitrace_id(tboCancelTicketResponse.getResponse().getTraceId());
		flightOrderRowCancellation.setOrderedAt(new Timestamp(new Date().getTime()));
		flightOrderRow.setCancelationMode(flightOrderRow.getBookingMode());
		flightOrderRowCancellation.setFlightOrderRow(flightOrderRow);
		if(flightOrderRowCancellation.getNoofAttempt()!=null && flightOrderRowCancellation.getNoofAttempt()>0){
			countOfattempt=flightOrderRowCancellation.getNoofAttempt() + new Integer(1);
			flightOrderRowCancellation.setNoofAttempt(countOfattempt);
		}else
			flightOrderRowCancellation.setNoofAttempt(1);
		FCDAO.insertFlightOrderRowCancellation(flightOrderRowCancellation);
		FlightOrderRowCancellationAPIResponse  flightOrderRowCancellationAPIResponse=cancelTicketResponse.getFlightOrderRowCancellationAPIResponse();
		flightOrderRowCancellationAPIResponse.setFlightOrderRowCancellation(flightOrderRowCancellation);
		FCDAO.saveSupplierResponse(flightOrderRowCancellationAPIResponse);

	}	
	
	public static void updateKeystatus(String transaction_key, FlightBookingDao FBDAO) {
		try {
			FBDAO.updateKeyStatus(transaction_key);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);			
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}
	}
	
	public static void updateMailstatus(String orderId, EmailDao emaildao ) {
		try {
			emaildao.insertEmail(orderId, 0, 2);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);			
		}
		catch (Exception e) {
			logger.error("Exception ",e);			
		}
	}
	
	public static CurrencyConversionMap buldCurrencyConversionMap(Flightsearch flightsearch,
			MoneyExchangeDao moneydao) {
		/// Get Default API Currency
		CurrencyConversionMap currencyConversionMap = new CurrencyConversionMap();
		String apiCurrency = TboFlightConfig.DEFAULT_CURRENCY;
		currencyConversionMap.setApiCurrency(apiCurrency);
		Map<String, Double> currencyrate = null;
		try {			
			currencyrate = moneydao.getCurrencyRate(flightsearch.getCurrency(), apiCurrency);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,FlightErrorMessages.NO_FLIGHT);
		}
		currencyConversionMap.setCurrencyrate(currencyrate);
		Double currencyValue =   currencyrate.get("value");
		BigDecimal curValue = new BigDecimal(currencyValue);
		currencyConversionMap.setCurrencyValue(currencyValue);
		currencyConversionMap.setCurValue(curValue);
		Map<String, Double> currencyrate1 = null;
		try {			
			currencyrate1 =  moneydao.getCurrencyRate(flightsearch.getBaseCurrency(), apiCurrency);
		} catch (Exception e) {
			logger.error(e);
			throw new FlightException(ErrorCodeCustomerEnum.HibernateException,FlightErrorMessages.NO_FLIGHT);
		}
		currencyConversionMap.setCurrencyrate1(currencyrate1);
		Double currencyValue1 = currencyrate1.get("value");
		currencyConversionMap.setCurrencyValue1(currencyValue1);
		return currencyConversionMap;
	}
	
	public static boolean isValidFormat(String format, String value) {
		Date date = null;
		boolean isformatcorrect = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
			if (date!=null) {
				isformatcorrect = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			isformatcorrect = false;
		}
		return isformatcorrect;
	}
}