/**
@Author ilyas
17-dec-2015 
FlightBookingResponseParser.java
 */
/**
 * 
 */
package com.tayyarah.flight.util.api.tayyarah;

import java.util.List;
import java.util.Map;

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

public class TayyarahFlightBookingResponseParser {	
	static Logger logger = Logger.getLogger(TayyarahFlightBookingResponseParser.class);
	protected static Map<String, TypeBaseAirSegment> airSegMap;	

	public static FlightBookingResponse parseFlightBookingResponse(FlightBookingResponse flightBookingResponse,FlightBookingResponse flightBookingResponseOLD, String orderId, FlightBookingDao FBDAO,EmailDao emaildao ,String transactionkey, String paymode, WalletAmountTranferHistory walletAmountTranferHistory ,int count) throws Exception {
		if(count != 1){
			flightBookingResponse=new FlightBookingResponse();			
		}
		FlightDataBaseServices DBS = new FlightDataBaseServices();		
		if(flightBookingResponseOLD.isBookingStatus()){			
			if(count==0||count==10){
				flightBookingResponse.setPnr(flightBookingResponseOLD.getPnr());
				flightBookingResponse.setBokingConditions("");
				flightBookingResponse.setBookingComments("Confirmed");
				flightBookingResponse.setBookingStatus(true);
			}else {
				flightBookingResponse.setPnrSpecial(flightBookingResponseOLD.getPnr());
				flightBookingResponse.setBokingConditionsSpecial("");
				flightBookingResponse.setBookingCommentsSpecial("Confirmed");
				flightBookingResponse.setBookingStatusSpecial(true);
			}			
			DBS.updatePNRandWallet(flightBookingResponseOLD.getPnr(), orderId, FBDAO,"API refrence dode");
			if(count==1||count==10){
				updateKeystatus(transactionkey,  FBDAO);
			}
			updateMailstatus(orderId, emaildao);	
		}
		else{
			if(count==0||count==10){
				flightBookingResponse.setPnr("NA");	
				flightBookingResponse.setBokingConditions(flightBookingResponseOLD.getBookingComments());
				flightBookingResponse.setBookingComments("Failed");
				flightBookingResponse.setBookingStatus(false);	
			}else{
				flightBookingResponse.setPnrSpecial("NA");	
				flightBookingResponse.setBokingConditionsSpecial(flightBookingResponseOLD.getBookingComments());
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