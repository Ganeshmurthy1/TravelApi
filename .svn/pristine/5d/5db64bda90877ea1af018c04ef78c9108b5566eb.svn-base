/**
@Author ilyas
14-sep-2015
FlightBookingResponseParser.java
 */
/**
 *
 */
package com.tayyarah.flight.util.api.travelport;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.model.FlightBookingResponse;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.user.entity.WalletAmountTranferHistory;
import com.travelport.api_v33.AirResponse.TypeBaseAirSegment;
import com.travelport.api_v33.Universal.AirCreateReservationRsp;
import com.travelport.api_v33.Universal.AirReservation;


public class FlightBookingResponseParser {
	static Logger logger = Logger.getLogger(FlightBookingResponseParser.class);
	protected static Map<String, TypeBaseAirSegment> airSegMap;
	public static FlightBookingResponse parseFlightBookingResponseTesting(FlightBookingResponse flightBookingResponse,String orderId, FlightBookingDao FBDAO,EmailDao emaildao ,String transactionkey, String paymode, WalletAmountTranferHistory walletAmountTranferHistory ,int count) throws Exception {
		FlightDataBaseServices DBS = new FlightDataBaseServices();
		if(count!=1){
			flightBookingResponse=new FlightBookingResponse();
		}
		Random random = new Random();
		int randomInteger = random.nextInt();
		String flightPNR = String.valueOf(randomInteger);
		if(count == 0 || count == 10){
			flightBookingResponse.setPnr(flightPNR);
			flightBookingResponse.setBokingConditions("");
			flightBookingResponse.setBookingComments("Confirmed");
			flightBookingResponse.setBookingStatus(true);
		}else {
			flightBookingResponse.setPnrSpecial(flightPNR);
			flightBookingResponse.setBokingConditionsSpecial("");
			flightBookingResponse.setBookingCommentsSpecial("Confirmed");
			flightBookingResponse.setBookingStatusSpecial(true);
		}
		DBS.updatePNRandWallet(flightPNR, orderId, FBDAO,"API refrence dode");
		if(count==1||count==10){
			updateKeystatus(transactionkey,  FBDAO);
		}
		updateMailstatus(orderId, emaildao);
		return flightBookingResponse;
	}

	public static FlightBookingResponse parseFlightBookingResponse(FlightBookingResponse flightBookingResponse,AirCreateReservationRsp airCreateReservationRsp, String orderId, FlightBookingDao FBDAO,EmailDao emaildao ,String transactionkey, String paymode, WalletAmountTranferHistory walletAmountTranferHistory,int count) throws Exception {
		if(count!=1){
			flightBookingResponse=new FlightBookingResponse();
		}
		FlightDataBaseServices DBS = new FlightDataBaseServices();
		if(airCreateReservationRsp.getUniversalRecord()!=null){			
			List<AirReservation> airReservations = airCreateReservationRsp.getUniversalRecord().getAirReservation();
			String pnr = "NA";
			String apiReferenceCode = "API reference code";
			for(AirReservation airReservation:airReservations){
				pnr=airCreateReservationRsp .getUniversalRecord().getProviderReservationInfo().get(0).getLocatorCode();
				apiReferenceCode = airCreateReservationRsp.getTransactionId();
				if(count==0||count==10){
					flightBookingResponse.setPnr(pnr);
					flightBookingResponse.setBokingConditions("");
					flightBookingResponse.setBookingComments("Confirmed");
					flightBookingResponse.setBookingStatus(true);
				}else {
					flightBookingResponse.setPnrSpecial(pnr);
					flightBookingResponse.setBokingConditionsSpecial("");
					flightBookingResponse.setBookingCommentsSpecial("Confirmed");
					flightBookingResponse.setBookingStatusSpecial(true);
				}
			}
			DBS.updatePNRandWallet(pnr, orderId, FBDAO,"API reference code");
			if(count==1||count==10){
				updateKeystatus(transactionkey,  FBDAO);
			}
			updateMailstatus(orderId, emaildao);			
		}
		else{
			if(count==0||count==10){
				flightBookingResponse.setPnr("NA");
				flightBookingResponse.setBokingConditions(airCreateReservationRsp.getResponseMessage().get(0).getValue());
				flightBookingResponse.setBookingComments("Failed");
				flightBookingResponse.setBookingStatus(false);
			}else{
				flightBookingResponse.setPnrSpecial("NA");
				flightBookingResponse.setBokingConditionsSpecial(airCreateReservationRsp.getResponseMessage().get(0).getValue());
				flightBookingResponse.setBookingCommentsSpecial("Failed");
				flightBookingResponse.setBookingStatusSpecial(false);
			}
			DBS.updatePNR("0", orderId, FBDAO);
			if(paymode.equals("cash")){
				DBS.updateWalletBalanceIfFailed(walletAmountTranferHistory.getAmount(),walletAmountTranferHistory.getWalletId(), FBDAO,walletAmountTranferHistory);
			}
		}
		return flightBookingResponse;
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
}