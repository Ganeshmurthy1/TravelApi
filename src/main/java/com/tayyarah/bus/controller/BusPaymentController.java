package com.tayyarah.bus.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.agent.wallet.dao.AgentWalletDAO;
import com.tayyarah.bus.dao.BusCommonDao;
import com.tayyarah.bus.entity.BusBlockedSeatTemp;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.bus.model.BusConfirmResponse;
import com.tayyarah.bus.model.BusMarkUpConfig;
import com.tayyarah.bus.model.BusMarkupCommissionDetails;
import com.tayyarah.bus.model.BusPaymentRequest;
import com.tayyarah.bus.model.TayyarahSeatBlockedMap;
import com.tayyarah.bus.util.BusBaseException;
import com.tayyarah.bus.util.BusCommonUtil;
import com.tayyarah.bus.util.BusErrorMessages;
import com.tayyarah.bus.util.BusException;
import com.tayyarah.bus.util.BusParamValidator;
import com.tayyarah.bus.util.BusRestError;
import com.tayyarah.bus.util.ErrorCodeCustomerEnum;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.util.RandomConfigurationNumber;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.esmart.bus.util.EsmartBusConfig;
import com.tayyarah.esmart.bus.util.EsmartServiceCall;
import com.tayyarah.user.entity.WalletAmountTranferHistory;

@RestController
@RequestMapping("/bus/payment")
public class BusPaymentController {
	static final Logger logger = Logger.getLogger(BusConfirmController.class);
	private static BusParamValidator  busParamValidator = new BusParamValidator();
	@Autowired
	CompanyDao companyDAO;
	@Autowired
	BusCommonDao busCommonDao;
	@Autowired
	CompanyConfigDAO companyConfigDAO;
	@Autowired
	AgentWalletDAO agentWalletDAO;
	@Autowired
	EmailDao emaildao;
	@RequestMapping(value = "", method = RequestMethod.POST, headers = { "Accept=application/json" }, produces = { "application/json" })
	public @ResponseBody BusConfirmResponse getBusConfirmResponse(@RequestBody BusPaymentRequest busPaymentRequest,HttpServletResponse response,HttpServletRequest request){
		ResponseHeader.setResponse(response);
		// Check APP KEY
		if(busPaymentRequest.getApp_key()!=null && busPaymentRequest.getApp_key().equalsIgnoreCase(""))
		{
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NOTFOUND_APPKEY.getErrorMessage());
		}
		busParamValidator.paymentRequestValidator(busPaymentRequest);
		BusConfirmResponse busConfirmResponse = new BusConfirmResponse();
		try{
			String decryptedAppKey = busCommonDao.getDecryptedAppKey(companyDAO,busPaymentRequest.getApp_key());
			EsmartBusConfig  esmartBusConfig = EsmartBusConfig.GetEsmartBusConfig(decryptedAppKey);
			PaymentTransaction paymentTransaction = busCommonDao.getPaymentTransactionUsingPgId(busPaymentRequest.getPaymentId());
			// Get BusOrderRow
			BusOrderRow busOrderRow = busCommonDao.getBusOrderRowUsingOrderId(paymentTransaction.getApi_transaction_id());
			// Get Bus Blocked Details
			BusBlockedSeatTemp busBlockedSeatTemp = busCommonDao.getBusBlockedSeatTempUsingTransKey(busOrderRow.getTransactionKey());
			busConfirmResponse = getConfirmResponse(esmartBusConfig,busOrderRow, busPaymentRequest,busBlockedSeatTemp);

		}catch(Exception e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BOOKINGFAILED.getErrorMessage());
		}
		return busConfirmResponse;
	}
	public BusConfirmResponse getConfirmResponse(EsmartBusConfig  esmartBusConfig,BusOrderRow busOrderRow,BusPaymentRequest busPaymentRequest,BusBlockedSeatTemp busBlockedSeatTemp){
		BusConfirmResponse busConfirmResponse = new BusConfirmResponse();
		try{
			TayyarahSeatBlockedMap tayyarahSeatBlockedMap = (TayyarahSeatBlockedMap) BusCommonUtil.convertByteArrayToObject(busBlockedSeatTemp.getBusBlockedData());
			Map<String,List<BusMarkUpConfig>> busMarkUpConfiglistMap = tayyarahSeatBlockedMap.getBusMarkUpConfiglistMap();
			BusMarkupCommissionDetails markupCommissionDetails = tayyarahSeatBlockedMap.getMarkupCommissionDetails();

		
			WalletAmountTranferHistory walletAmountTranferHistory = new WalletAmountTranferHistory();
			walletAmountTranferHistory.setActionId(busOrderRow.getOrderId());
			walletAmountTranferHistory.setCurrency("INR");


			// Get Payment Transaction data
			PaymentTransaction paymentTransaction = busCommonDao.getPaymentTransaction(busOrderRow.getOrderId());

			if(busPaymentRequest.getPaymentStatus().equalsIgnoreCase("Success")){
				try{
					//Call API
					busConfirmResponse = EsmartServiceCall.paymentconfirmBusTicket(esmartBusConfig, busPaymentRequest, tayyarahSeatBlockedMap.getBusBlockTicketResponse(), busOrderRow);

					// Update BusOrderRow
					busOrderRow.setCancellationPolicy(busConfirmResponse.getCancellationPolicy());
					busOrderRow.setConfirmationNumber(busConfirmResponse.getConfirmationNo());
					busOrderRow.setApiTripCode(busConfirmResponse.getTripCode());
					busOrderRow.setOperatorPnr(busConfirmResponse.getOperatorPnr());
					busOrderRow.setStatusAction("Confirmed");
					busOrderRow.setPaymentStatus("Success");
					busOrderRow.setInvoiceNo(RandomConfigurationNumber.generateBusInvoiceNumber(busOrderRow.getId()).toString()); 
					busCommonDao.updateBusOrderRowDetails(busOrderRow);

					busConfirmResponse.setInvoiceNo(busOrderRow.getInvoiceNo());

					// Update Payment Transaction

					paymentTransaction.setIsPaymentSuccess(true);
					paymentTransaction.setPayment_status("SUCCESS");
					paymentTransaction.setTransactionId(busOrderRow.getOrderId());
					paymentTransaction.setResponse_message("NA");
					paymentTransaction.setResponseCode("NA"); 
					// Insert Email
					emaildao.insertEmail(busOrderRow.getOrderId(), 0, 88); 
				}catch(Exception e){
					paymentTransaction.setTransactionId(busOrderRow.getOrderId()); 
					paymentTransaction.setPayment_status("FAILED");
					emaildao.insertEmail(busOrderRow.getOrderId(), 0, 88);
					agentWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(busOrderRow.getOrderId(), "0");	
					throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BOOKINGFAILED.getErrorMessage());
				}

			}else{
				// Update Payment Transaction
				paymentTransaction.setIsPaymentSuccess(true);
				paymentTransaction.setPayment_status("SUCCESS");
				paymentTransaction.setTransactionId(busOrderRow.getOrderId());
				paymentTransaction.setResponse_message("NA");
				paymentTransaction.setResponseCode("NA");
			}

			// Update Payment Transaction Data
			busCommonDao.updatePaymentTransactionDetails(paymentTransaction);

			// Update Invoice No in walletTransferHistory
			agentWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(busOrderRow.getOrderId(), busOrderRow.getInvoiceNo());





		}catch(Exception e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BOOKINGFAILED.getErrorMessage());
		}
		return busConfirmResponse;
	}

	@ExceptionHandler(BusBaseException.class)
	public @ResponseBody
	BusRestError handleCustomException(BusBaseException ex,
			HttpServletResponse response) {
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return ex.transformException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
