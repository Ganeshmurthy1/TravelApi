package com.tayyarah.bus.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.bus.dao.BusCommonDao;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.bus.entity.BusOrderRowCancellation;
import com.tayyarah.bus.entity.BusOrderRowMarkup;
import com.tayyarah.bus.model.BusCancelRequest;
import com.tayyarah.bus.model.BusCancelResponse;
import com.tayyarah.bus.util.BusErrorMessages;
import com.tayyarah.bus.util.BusException;
import com.tayyarah.bus.util.BusParamValidator;
import com.tayyarah.bus.util.ErrorCodeCustomerEnum;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.esmart.bus.util.EsmartBusConfig;
import com.tayyarah.esmart.bus.util.EsmartServiceCall;

@RestController
@RequestMapping("/bus/cancel")
public class BusCancellationController {
	static final Logger logger = Logger.getLogger(BusBlockTicketController.class);
	private static BusParamValidator  busParamValidator = new BusParamValidator();
	@Autowired
	CompanyDao companyDAO;
	@Autowired
	BusCommonDao busCommonDao;
	@Autowired
	CompanyConfigDAO companyConfigDAO;
	@RequestMapping(value = "confirmation", method = RequestMethod.POST, headers = { "Accept=application/json" }, produces = { "application/json" })
	public @ResponseBody BusCancelResponse getBusCancelResponse(@RequestBody BusCancelRequest busCancelRequest,HttpServletResponse response,HttpServletRequest request){
		ResponseHeader.setResponse(response);
		// Check APP KEY
		if(busCancelRequest.getApp_key()!=null && busCancelRequest.getApp_key().equalsIgnoreCase(""))
		{
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NOTFOUND_APPKEY.getErrorMessage());
		}
		busParamValidator.cancelRequestValidator(busCancelRequest);
		BusCancelResponse busCancelResponse = new BusCancelResponse();
		try{
			String decryptedAppKey = busCommonDao.getDecryptedAppKey(companyDAO,busCancelRequest.getApp_key());
			BusOrderRow busOrderRow = busCommonDao.getBusOrderRow(busCancelRequest.getTransactionkey());
			if(busOrderRow!=null){
				BigDecimal totalAmt = busOrderRow.getTotalAmount();
				BigDecimal totalAmtPaid = new BigDecimal(0);
				String companyId = decryptedAppKey.substring(decryptedAppKey.indexOf("-")+1);				
				List<BusOrderRowMarkup> busOrderRowMarkupList = busCommonDao.getBusOrderRowMarkup(busOrderRow);
				for (BusOrderRowMarkup busOrderRowMarkup : busOrderRowMarkupList) {
					if(companyId.equalsIgnoreCase(busOrderRowMarkup.getCompanyId())){
						totalAmtPaid = totalAmt.subtract(busOrderRowMarkup.getMarkUp());
					}
				}
				EsmartBusConfig  esmartBusConfig = EsmartBusConfig.GetEsmartBusConfig(decryptedAppKey);
				busCancelResponse = EsmartServiceCall.getCancelConfirmationResponse(esmartBusConfig, busCancelRequest, busOrderRow,totalAmtPaid);	
			}
			else{
				throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.CANCELLATIONFAILED.getErrorMessage());
			}


		}catch(Exception e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.CANCELLATIONFAILED.getErrorMessage());
		}
		return busCancelResponse;
	}
	
	@RequestMapping(value = "cancelticket", method = RequestMethod.POST, headers = { "Accept=application/json" }, produces = { "application/json" })
	public @ResponseBody BusCancelResponse getBusCancelTicketResponse(@RequestBody BusCancelRequest busCancelRequest,HttpServletResponse response,HttpServletRequest request){
		ResponseHeader.setResponse(response);
		// Check APP KEY
		if(busCancelRequest.getApp_key()!=null && busCancelRequest.getApp_key().equalsIgnoreCase(""))
		{
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NOTFOUND_APPKEY.getErrorMessage());
		}
		busParamValidator.cancelRequestValidator(busCancelRequest);
		BusCancelResponse busCancelResponse = new BusCancelResponse();
		try{
			String decryptedAppKey = busCommonDao.getDecryptedAppKey(companyDAO,busCancelRequest.getApp_key());
			BusOrderRow busOrderRow = busCommonDao.getBusOrderRow(busCancelRequest.getTransactionkey());
			if(busOrderRow!=null){
				BigDecimal totalAmt = busOrderRow.getTotalAmount();
				BigDecimal totalAmtPaid = new BigDecimal(0);
				String companyId = decryptedAppKey.substring(decryptedAppKey.indexOf("-")+1);				
				List<BusOrderRowMarkup> busOrderRowMarkupList = busCommonDao.getBusOrderRowMarkup(busOrderRow);
				for (BusOrderRowMarkup busOrderRowMarkup : busOrderRowMarkupList) {
					if(companyId.equalsIgnoreCase(busOrderRowMarkup.getCompanyId())){
						totalAmtPaid = totalAmt.subtract(busOrderRowMarkup.getMarkUp());
					}
				}
				EsmartBusConfig  esmartBusConfig = EsmartBusConfig.GetEsmartBusConfig(decryptedAppKey);
				busCancelResponse = EsmartServiceCall.getCancelTicketResponse(esmartBusConfig, busCancelRequest, busOrderRow,totalAmtPaid);	
				if(busCancelResponse.getStatus().getCode() == 1){
					BusOrderRowCancellation busOrderRowCancellation = new BusOrderRowCancellation();
					busOrderRowCancellation.setAPIChargeAmount(busCancelResponse.getCancellationCharges());
					busOrderRowCancellation.setTotalAPITicketFare(busCancelResponse.getApiPrice());
					busOrderRowCancellation.setAPIConfirmationNumber(busCancelResponse.getApiConfirtmationNo());
					busOrderRowCancellation.setApiPrice(busCancelResponse.getApiPrice());
					busOrderRowCancellation.setTotalAmountPaid(busCancelResponse.getTotalAmountPaid());
					busOrderRowCancellation.setCancelChargesPercentage(busCancelResponse.getCancelChargesPercentage());
					busOrderRowCancellation.setIsCancellable(busCancelResponse.getIsCancellable());
					busOrderRowCancellation.setIsPartiallyCancellable(busCancelResponse.getIsPartiallyCancellable());
					busOrderRowCancellation.setAPICurrency("INR");
					busOrderRowCancellation.setAPIPaymentType("Online");
					//busOrderRowCancellation.setPaymentType("Percentage");
					busOrderRowCancellation.setChargeType("Percentage");
					busOrderRowCancellation.setAPIChargeType("Percentage");
					busOrderRowCancellation.setAPIReference(busCancelResponse.getApiConfirtmationNo());
					busOrderRowCancellation.setCreatedAt(new Timestamp(new Date().getTime()));
					busOrderRowCancellation.setAPIRefundAmount(busCancelResponse.getTotalRefundAmount());
					busOrderRowCancellation.setAPIStatusCode(Integer.toString(busCancelResponse.getStatus().getCode()));
					busOrderRowCancellation.setAPIStatusMessage("Success");
					busOrderRowCancellation.setStatusCode(Integer.toString(busCancelResponse.getStatus().getCode()));
					busOrderRowCancellation.setStatusMessage("Success");
					busOrderRow.setCancelMode("Online");
					busOrderRowCancellation.setBusOrderRow(busOrderRow);
					StringBuffer seatnos = new StringBuffer();
					int i = 0;
					for (String seatno : busCancelResponse.getSeatNbr()) {
						seatnos.append(seatno);
						if(i == busCancelResponse.getSeatNbr().size()){
							seatnos.append(",");
						}
						i++;
						
					}
					busOrderRowCancellation.setSeatNos(seatnos.toString());
					busOrderRowCancellation.setOrderId(busOrderRow.getOrderId());
					busCommonDao.saveBusOrderRowCancellation(busOrderRowCancellation);
						
				}
				
			}
			else{
				throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.CANCELLATIONFAILED.getErrorMessage());
			}


		}catch(Exception e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.CANCELLATIONFAILED.getErrorMessage());
		}
		return busCancelResponse;
	}
}
