package com.tayyarah.bus.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.tayyarah.admin.analytics.lookbook.dao.LookBookDao;
import com.tayyarah.admin.analytics.lookbook.entity.BusBook;
import com.tayyarah.admin.analytics.lookbook.entity.BusLookBook;
import com.tayyarah.admin.analytics.lookbook.entity.FetchIpAddress;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPHistory;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPStatus;
import com.tayyarah.agent.wallet.dao.AgentWalletDAO;
import com.tayyarah.bus.dao.BusCommonDao;
import com.tayyarah.bus.entity.BusBlockedSeatTemp;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.bus.entity.BusSearchTemp;
import com.tayyarah.bus.entity.BusSeatAvailableTemp;
import com.tayyarah.bus.model.BusConfirmRequest;
import com.tayyarah.bus.model.BusConfirmResponse;
import com.tayyarah.bus.model.BusMarkUpConfig;
import com.tayyarah.bus.model.BusMarkupCommissionDetails;
import com.tayyarah.bus.model.Status;
import com.tayyarah.bus.model.TayyarahBusSearchMap;
import com.tayyarah.bus.model.TayyarahBusSeatMap;
import com.tayyarah.bus.model.TayyarahSeatBlockedMap;
import com.tayyarah.bus.util.BusBaseException;
import com.tayyarah.bus.util.BusCommonUtil;
import com.tayyarah.bus.util.BusErrorMessages;
import com.tayyarah.bus.util.BusException;
import com.tayyarah.bus.util.BusParamValidator;
import com.tayyarah.bus.util.BusRestError;
import com.tayyarah.bus.util.ErrorCodeCustomerEnum;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.common.util.CutandPayModel;
import com.tayyarah.common.util.RandomConfigurationNumber;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.common.util.enums.CommonBookingStatusEnum;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.esmart.bus.util.EsmartBusConfig;
import com.tayyarah.esmart.bus.util.EsmartServiceCall;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.entity.WalletAmountTranferHistory;

@RestController
@RequestMapping("/bus/confirm")
public class BusConfirmController {
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
	@SuppressWarnings("rawtypes")
	@Autowired
	LookBookDao lookBookDao;

	@RequestMapping(value = "", method = RequestMethod.POST, headers = { "Accept=application/json" }, produces = { "application/json" })
	public @ResponseBody BusConfirmResponse getBusConfirmResponse(@RequestBody BusConfirmRequest busConfirmRequest,HttpServletResponse response,HttpServletRequest request){
		ResponseHeader.setResponse(response);
		// Check APP KEY
		if(busConfirmRequest.getApp_key()!=null && busConfirmRequest.getApp_key().equalsIgnoreCase(""))
		{
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NOTFOUND_APPKEY.getErrorMessage());
		}
		busParamValidator.confirmRequestValidator(busConfirmRequest);
		BusConfirmResponse busConfirmResponse = new BusConfirmResponse();

		try{
			String decryptedAppKey = busCommonDao.getDecryptedAppKey(companyDAO,busConfirmRequest.getApp_key());
			// get Bus Details
			BusSearchTemp busSearchTemp = busCommonDao.getBusSearchTemp(busConfirmRequest.getSearchkey());
			TayyarahBusSearchMap  busSearchMap = (TayyarahBusSearchMap) BusCommonUtil.convertByteArrayToObject(busSearchTemp.getBusSearchData());
			// get Seat Details
			BusSeatAvailableTemp busSeatAvailableTemp = busCommonDao.getBusSeatAvailableTemp(busConfirmRequest.getSearchkey());
			TayyarahBusSeatMap tayyarahBusSeatMap  = (TayyarahBusSeatMap) BusCommonUtil.convertByteArrayToObject(busSeatAvailableTemp.getBusSeatData());
			Map<String,List<BusMarkUpConfig>> markupMap = tayyarahBusSeatMap.getBusMarkUpConfiglistMap();
			EsmartBusConfig  esmartBusConfig = EsmartBusConfig.GetEsmartBusConfig(decryptedAppKey);

			// Get Bus Blocked Details
			BusBlockedSeatTemp busBlockedSeatTemp = busCommonDao.getBusBlockedSeatTemp(busConfirmRequest.getSearchkey());

			// Get BusOrderRow
			BusOrderRow busOrderRow = busCommonDao.getBusOrderRow(busConfirmRequest.getTransactionkey());
			busConfirmResponse = getConfirmResponse(esmartBusConfig,busOrderRow, busConfirmRequest,busBlockedSeatTemp,request);

		}catch(Exception e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BOOKINGFAILED.getErrorMessage());
		}
		return busConfirmResponse;
	}

	public BusConfirmResponse getConfirmResponse(EsmartBusConfig  esmartBusConfig,BusOrderRow busOrderRow,BusConfirmRequest busConfirmRequest,BusBlockedSeatTemp busBlockedSeatTemp,HttpServletRequest request){
		BusConfirmResponse busConfirmResponse = new BusConfirmResponse();
		try{
			TayyarahSeatBlockedMap tayyarahSeatBlockedMap = (TayyarahSeatBlockedMap) BusCommonUtil.convertByteArrayToObject(busBlockedSeatTemp.getBusBlockedData());
			Map<String,List<BusMarkUpConfig>> busMarkUpConfiglistMap = tayyarahSeatBlockedMap.getBusMarkUpConfiglistMap();
			BusMarkupCommissionDetails markupCommissionDetails = tayyarahSeatBlockedMap.getMarkupCommissionDetails();

			List<Company> companyListBottomToTop = new LinkedList<>();
			List<User> userListBottomToTop = new LinkedList<>();
			Map<Integer, CutandPayModel> cutAndPayUserMap = new LinkedHashMap<>();	
			if(busConfirmRequest.getPayMode().equalsIgnoreCase("cash")){

				boolean result = false;
				WalletAmountTranferHistory walletAmountTranferHistory = new WalletAmountTranferHistory();
				walletAmountTranferHistory.setActionId(busOrderRow.getOrderId());
				walletAmountTranferHistory.setCurrency("INR");

				int configId = Integer.valueOf(busOrderRow.getConfigId());
				CompanyConfig companyConfig = null;
				try {
					companyConfig = companyConfigDAO.getCompanyConfigByConfigId(configId);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				if(companyConfig!=null)
				{
					companyListBottomToTop = BusCommonUtil.getParentCompanyBottomToTop(companyConfig.getCompany_id(),companyDAO);
					if(companyListBottomToTop!=null && companyListBottomToTop.size()>0)
					{
						User currentUser = companyDAO.getUserById(Integer.valueOf(busOrderRow.getUserId()));
						userListBottomToTop = CommonUtil.getUsersAllWithUserModeBottomToTop(companyListBottomToTop,companyDAO,currentUser);
					}
					cutAndPayUserMap = BusCommonUtil.getCutandPayModelUsers(tayyarahSeatBlockedMap.getBusBlockTicketResponse(), companyConfig, userListBottomToTop, busMarkUpConfiglistMap, markupCommissionDetails.getCommissionDetailslist(), busOrderRow);

					boolean checkBookingAmountEligibility= false;
					if(userListBottomToTop!=null && userListBottomToTop.size()>0)
					{
						for(User userInner : userListBottomToTop)
						{
							if(userInner.getAgentWallet()!=null)
							{
								if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
								{
									BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
									if(!agentWalletDAO.checkWalletAmount(userInner.getId(), totalPayableAmount,new BigDecimal(0), new BigDecimal(0))){
										result = false;
										checkBookingAmountEligibility = false;
									}else{
										checkBookingAmountEligibility = true;
									}
								}
							}
						}
					}	

					if(checkBookingAmountEligibility)
					{
						Map<Integer,Boolean> userMapBottomToTop= new LinkedHashMap<>();
						if(userListBottomToTop!=null && userListBottomToTop.size()>0)
						{
							for(User userInner : userListBottomToTop)
							{
								if(userInner.getAgentWallet()!=null)
								{
									if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
									{
										BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
										if(agentWalletDAO.checkWalletAmount(userInner.getId(), totalPayableAmount,new BigDecimal(0), new BigDecimal(0)))
										{		
											agentWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,new BigDecimal(0), new BigDecimal(0),CommonBookingStatusEnum.BUS_REMARKS.getMessage(),true);
											userMapBottomToTop.put(userInner.getId(),true);

										}
										else{
											if(userMapBottomToTop!=null && userMapBottomToTop.size()>0)
											{
												for(Entry<Integer,Boolean>  userMap :userMapBottomToTop.entrySet())
												{
													if(userMap.getValue())
													{
														totalPayableAmount = cutAndPayUserMap.get(userMap.getKey()).getPayableAmount();
														agentWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,new BigDecimal(0), new BigDecimal(0),CommonBookingStatusEnum.FLIGHT_FAILEDREMARKS.getMessage(),false);
													}
												}
											}
											result = false;
											break;
										}
									}
									else{
										result = false;
									}
								}
							}
							result = true;
						}	
					}else{
						result = false;
					}			
				}

				// Get Payment Transaction data
				PaymentTransaction paymentTransaction = busCommonDao.getPaymentTransaction(busOrderRow.getOrderId());

				if(result){
					try{
						//Call API
						busConfirmResponse = EsmartServiceCall.confirmBusTicket(esmartBusConfig, busConfirmRequest, tayyarahSeatBlockedMap.getBusBlockTicketResponse(), busOrderRow,busCommonDao);

						// Update BusOrderRow
						if(busConfirmResponse.getStatus().getCode() == Status.SUCCESSCODE){
						busOrderRow.setCancellationPolicy(busConfirmResponse.getCancellationPolicy());
						busOrderRow.setConfirmationNumber(busConfirmResponse.getConfirmationNo());
						busOrderRow.setApiTripCode(busConfirmResponse.getTripCode());
						busOrderRow.setOperatorPnr(busConfirmResponse.getOperatorPnr());
						busOrderRow.setStatusAction("Confirmed");
						busOrderRow.setPaymentStatus("Success");
						busOrderRow.setInvoiceNo(RandomConfigurationNumber.generateBusInvoiceNumber(busOrderRow.getId()).toString()); 
						
						// Update Payment Transaction

						paymentTransaction.setIsPaymentSuccess(true);
						paymentTransaction.setPayment_status("SUCCESS");
						paymentTransaction.setTransactionId(busOrderRow.getOrderId());
						paymentTransaction.setResponse_message("NA");
						paymentTransaction.setResponseCode("NA");

					
						}else{
							busOrderRow.setStatusAction("Failed");
							busOrderRow.setPaymentStatus("Failed");
							busOrderRow.setInvoiceNo("0"); 
							
							if(userListBottomToTop!=null && userListBottomToTop.size()>0)
							{
								for(User userInner : userListBottomToTop)
								{
									if(userInner.getAgentWallet()!=null)
									{
										if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
										{
											BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
											agentWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,new BigDecimal(0), new BigDecimal(0),CommonBookingStatusEnum.BUS_FAILEDREMARKS.getMessage(),false);
										}
										else{
											result = false;
										}
									}
								}
							}	
							agentWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(busOrderRow.getOrderId(), "0");
							
							
						}
						busCommonDao.updateBusOrderRowDetails(busOrderRow);

						busConfirmResponse.setInvoiceNo(busOrderRow.getInvoiceNo());

						// Insert Email
						emaildao.insertEmail(busOrderRow.getOrderId(), 0, 88);


					}catch(Exception e){
						// Refund Amount
						try {
							if(userListBottomToTop!=null && userListBottomToTop.size()>0)
							{
								for(User userInner : userListBottomToTop)
								{
									if(userInner.getAgentWallet()!=null)
									{
										if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
										{
											BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
											agentWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,new BigDecimal(0), new BigDecimal(0),CommonBookingStatusEnum.BUS_FAILEDREMARKS.getMessage(),false);
										}
										else{
											result = false;
										}
									}
								}
							}	
							agentWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(busOrderRow.getOrderId(), "0");		

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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
				if(paymentTransaction.getIsPaymentSuccess()){
					try{
						insertIntoBusBook(busConfirmRequest.getApp_key(),busConfirmResponse.getTransactionkey(),request,busConfirmRequest.getSearchkey());
					}catch (Exception e) {
					}
				}

				// Update Invoice No in walletTransferHistory
				agentWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(busOrderRow.getOrderId(), busOrderRow.getInvoiceNo());
				
				/*// Save Bus Low Fare Detail
				if(busBlockedSeatTemp.getLowFareRouteScheduleId()!=null){
					BusFareAlertDetail  busFareAlertDetail = BusCommonUtil.getBusFareAlertDetail(busBlockedSeatTemp, tayyarahSeatBlockedMap.getBusBlockTicketRequest(), busCommonDao, busBlockedSeatTemp.getLowFareRouteScheduleId(), busOrderRow,busBlockedSeatTemp.getReasonToSelect());
					busCommonDao.saveBusLowFareDetail(busFareAlertDetail);
				}*/
			}


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
	public BusBook insertIntoBusBook(String appkey, String transactionKey,HttpServletRequest request,String searchKey){
		BusBook busBook=new BusBook(); 
		BusLookBook busLookBook=new BusLookBook(); 
		LookBookCustomerIPStatus ipStatus=new LookBookCustomerIPStatus();
		LookBookCustomerIPHistory ipStatusHistory=new LookBookCustomerIPHistory();
		Timestamp currentDate=new Timestamp(new Date().getTime());
		String ip=null;
		try{
			ip=FetchIpAddress.getClientIpAddress(request);
			busBook.setAppkey(appkey);
			String NewAPP_Key = AppControllerUtil.getDecryptedAppKey(companyDAO,appkey);
			String companyId = "-1";
			String configId = "-1";
			configId = NewAPP_Key.substring(0, NewAPP_Key.indexOf("-"));
			companyId = NewAPP_Key.substring(NewAPP_Key.indexOf("-") + 1);
			Company company = companyDAO.getCompany(Integer.valueOf(companyId));

			busBook.setIP(ip);
			busBook.setSearchKey(searchKey);
			busBook.setSearchOnDateTime(currentDate);
			busBook.setTransactionKey(transactionKey);
			busBook.setCompanyId(Integer.valueOf(companyId));
			busBook.setConfigId(Integer.valueOf(configId));
			busBook.setCompanyName(company.getCompanyname());
			lookBookDao.insertIntoTable(busBook);
			busLookBook.setAppkey(appkey);
			busLookBook=lookBookDao.CheckAndFetchBusLookBookByAppKey(busLookBook);

			if(busLookBook!=null && busLookBook.getId()>0){
				lookBookDao.updateIntoBusTable(busLookBook, "booking");
			}
			else{
				busLookBook.setAppkey(appkey);
				busLookBook.setCompanyId(Integer.valueOf(companyId));
				busLookBook.setConfigId(Integer.valueOf(configId));
				busLookBook.setCompanyName(company.getCompanyname());
				busLookBook.setTotalBookedCount(1);
				busLookBook.setTotalSearchCount(0);
				lookBookDao.insertIntoTable(busLookBook);
			}
		}
		catch (Exception e) {
		}
		
		try{
			ipStatus=lookBookDao.CheckAndFetchIpStatus(ip);
			ipStatusHistory=lookBookDao.CheckAndfetchIpHistory(ip);
		}
		catch (Exception e) {
		}

		if(ipStatus!=null && ipStatus.getId()>0){
			if( ipStatus.isBlockStatus() || ipStatus.getTotalBookedCount()>=100){
				throw new BusException(ErrorCodeCustomerEnum.LimitExceedException,ErrorMessages.USEREXCEEDSSEARCHLIMIT); 
			} 
			else{
				ipStatus.setLastDate(currentDate);
				ipStatus.setTotalBookedCount(ipStatus.getTotalBookedCount()+1);
				if(ipStatus.getTotalSearchCount()==100)
					ipStatus.setBlockStatus(true);
				try{
					lookBookDao.updateIpStatus(ipStatus);
				}
				catch (Exception e) {
				}
			}
		}
		if(ipStatusHistory!=null && ipStatusHistory.getId()>0){
			ipStatusHistory.setLastDate(currentDate);
			ipStatusHistory.setTotalBookedCount(ipStatusHistory.getTotalBookedCount()+1);
				try{
					lookBookDao.updateIpHistory(ipStatusHistory);
				}
				catch (Exception e) {
				}
		}

		return busBook;
	}
}
