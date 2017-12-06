/**
@Author ilyas
14-sep-2015 
FlightBookingResponseParser.java
 */
/**
 * 
 */
package com.tayyarah.flight.util.api.bluestar;

import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.model.FlightBookingResponse;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.user.entity.WalletAmountTranferHistory;
import com.travelport.api_v33.AirResponse.TypeBaseAirSegment;



public class BluestarBookTicketResponseParser {
	static Logger logger = Logger.getLogger(BluestarBookTicketResponseParser.class);
	protected static Map<String, TypeBaseAirSegment> airSegMap;

	public static FlightBookingResponse parseFlightBookingResponse(FlightBookingResponse flightBookingResponse,String BookTicketResponse, String orderId, FlightBookingDao FBDAO,EmailDao emaildao, String transactionkey, String paymode, WalletAmountTranferHistory walletAmountTranferHistory,int count ) throws Exception {
		FlightDataBaseServices DBS = new FlightDataBaseServices();
		if(count!=1){
			flightBookingResponse=new FlightBookingResponse();
		}

		if(BookTicketResponse.contains("<IsFareChange>") && BookTicketResponse.indexOf("<IsFareChange>")==-1){
			String AirlinePNRNumber = "NA";
			String RefNo=BookTicketResponse.substring(BookTicketResponse.indexOf("<RefNo>")+7,BookTicketResponse.indexOf("</RefNo>"));
			String Status=BookTicketResponse.substring(BookTicketResponse.indexOf("<Status>")+8,BookTicketResponse.indexOf("</Status>"));
			if((BookTicketResponse.indexOf("<AirlinePNRNumber>")!=-1)&&(Status.equalsIgnoreCase("Booking Succeed")))
			{
				AirlinePNRNumber=BookTicketResponse.substring(BookTicketResponse.indexOf("<AirlinePNRNumber>")+18,BookTicketResponse.indexOf("</AirlinePNRNumber>"));
			}
			if(count == 0 || count == 10){
				flightBookingResponse.setPnr(AirlinePNRNumber);	
				flightBookingResponse.setBokingConditions("");
				flightBookingResponse.setBookingComments("Confirmed");
				flightBookingResponse.setBookingStatus(true);	
			}else {
				flightBookingResponse.setPnrSpecial(AirlinePNRNumber);	
				flightBookingResponse.setBokingConditionsSpecial("");
				flightBookingResponse.setBookingCommentsSpecial("Confirmed");
				flightBookingResponse.setBookingStatusSpecial(true);	
			}			
			DBS.updatePNRandWallet(AirlinePNRNumber, orderId, FBDAO,RefNo);
			if(count==1||count==10){
				updateKeystatus(transactionkey,  FBDAO);
			}
			updateMailstatus(orderId,emaildao );			
		}
		else{
			if(count == 0 || count == 10){
				flightBookingResponse.setPnr("NA");	
				flightBookingResponse.setBokingConditions("Fare changed, wants to continue? or try with new flight");
				flightBookingResponse.setBookingComments("Booking Failed");
				flightBookingResponse.setBookingStatus(false);	
			}else{
				flightBookingResponse.setPnrSpecial("NA");	
				flightBookingResponse.setBokingConditionsSpecial("Fare changed, wants to continue? or try with new flight");
				flightBookingResponse.setBookingCommentsSpecial("Booking Failed");
				flightBookingResponse.setBookingStatusSpecial(false);	
			}
			DBS.updatePNR("0", orderId, FBDAO);
			if(paymode.equals("cash")){
				DBS.updateWalletBalanceIfFailed(walletAmountTranferHistory.getAmount(),walletAmountTranferHistory.getWalletId(), FBDAO,walletAmountTranferHistory);
			}
			updateMailstatus(orderId,emaildao);
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

	public static void updateMailstatus(String orderId, EmailDao emaildao) {
		try {
			emaildao.insertEmail(orderId, 0, Email.EMAIL_TYPE_FLIGHT_VOUCHER);
		} catch (HibernateException e) {
			logger.error("HibernateException ",e);
		}
		catch (Exception e) {
			logger.error("Exception ",e);
		}
	}
	
	public static void main(String[] args) {
		String BookTicketResponse="nfdn<RefNo>hello</RefNo>";
		String RefNo=BookTicketResponse.substring(BookTicketResponse.indexOf("<RefNo>")+7,BookTicketResponse.indexOf("</RefNo>"));
	}
}