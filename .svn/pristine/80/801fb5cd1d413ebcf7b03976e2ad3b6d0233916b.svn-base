package com.tayyarah.insurance.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tayyarah.api.orderrow.rm.structure.InsuranceOrderRowRmConfigStruct;
import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.dao.RmConfigDetailDAO;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.entity.RmConfigModel;
import com.tayyarah.common.exception.BaseException;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.exception.RestError;
import com.tayyarah.common.gstconfig.entity.InsuranceGstTaxConfig;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.AmountRoundingModeUtil;
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
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.insurance.dao.InsuranceCommonDao;
import com.tayyarah.insurance.entity.InsuranceOrderCustomerDetail;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.insurance.entity.InsuranceOrderRowCommission;
import com.tayyarah.insurance.entity.InsuranceOrderRowGstTax;
import com.tayyarah.insurance.entity.InsuranceOrderRowMarkup;
import com.tayyarah.insurance.entity.InsurancePolicyTemp;
import com.tayyarah.insurance.entity.TrawellTagPremiumChart;
import com.tayyarah.insurance.exception.InsuranceErrorMessages;
import com.tayyarah.insurance.exception.InsuranceException;
import com.tayyarah.insurance.model.CreateInsurancePolicyRequest;
import com.tayyarah.insurance.model.InsuranceMarkUp;
import com.tayyarah.insurance.model.InsuranceMarkupCommissionDetails;
import com.tayyarah.insurance.model.PolicyResponseData;
import com.tayyarah.insurance.model.PolicyResposne;
import com.tayyarah.insurance.model.Status;
import com.tayyarah.insurance.model.TayyarahInsuranceMap;
import com.tayyarah.insurance.model.TravellerDetails;
import com.tayyarah.insurance.util.InsuranceCommonUtil;
import com.tayyarah.insurance.util.InsuranceMarkUpHelper;
import com.tayyarah.insurance.util.InsuranceParamValidator;
import com.tayyarah.insurance.util.InsurancePolicyExecutorServiceTaskHelper;
import com.tayyarah.user.dao.UserWalletDAO;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.entity.WalletAmountTranferHistory;




@RestController
@RequestMapping("/insurance")
public class CreatePolicyController {
	static final Logger logger = Logger.getLogger(CreatePolicyController.class);
	private static  InsuranceParamValidator insuranceParamValidator = new InsuranceParamValidator();

	@Autowired
	CompanyDao companyDao;
	@Autowired
	MoneyExchangeDao moneydao;
	@Autowired
	CompanyConfigDAO companyConfigDAO;
	@Autowired
	InsuranceCommonDao insuranceCommonDao;
	@Autowired
	UserWalletDAO userWalletDAO;
	@Autowired
	EmailDao emaildao;
	@Autowired
	RmConfigDetailDAO rmConfigDetailDAO;
	@RequestMapping(value = "/createpolicy", method = RequestMethod.POST, headers = { "Accept=application/json" }, produces = { "application/json" })
	public @ResponseBody PolicyResponseData createpolicy(@RequestBody CreateInsurancePolicyRequest createInsurancePolicyRequest,HttpServletResponse response,HttpServletRequest request){
		PolicyResponseData policyResponseData = null;
		ResponseHeader.setResponse(response);
		// Check APP KEY
		if(createInsurancePolicyRequest.getApp_key()!=null && createInsurancePolicyRequest.getApp_key().equalsIgnoreCase(""))
		{
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.INVALID_APPKEY.getErrorMessage());
		}

		insuranceParamValidator.createpolicyValidator(createInsurancePolicyRequest);
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, createInsurancePolicyRequest.getApp_key());
		if(appKeyVo==null)
		{
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
		}
		CompanyConfig companyConfig = new CompanyConfig();
		try {
			companyConfig = appKeyVo.getCompanyConfig();
		} catch (NumberFormatException e2) {
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.INVALID_APPKEY.getErrorMessage());
		} catch (Exception e2) {
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.INVALID_APPKEY.getErrorMessage());
		}

		Company company = appKeyVo.getCompany();
		Map<String,List<InsuranceMarkUp>> markupMap = new HashMap<String,List<InsuranceMarkUp>>();
		try {
			markupMap = companyDao.getInsuranceMarkUpMapByCompanyId(appKeyVo, markupMap);
		} catch (Exception e1) {
			logger.error("getBusMarkUpMapByCompanyId Exception", e1);
		}
		InsuranceMarkupCommissionDetails markupCommissionDetails = new InsuranceMarkupCommissionDetails();
		try {
			markupCommissionDetails = companyDao.getInsuranceMarkupCommissionDetailsByCompanyId(appKeyVo, markupCommissionDetails);
		} catch (Exception e1) {
			logger.error("getFlightMarkupCommissionDetailsByCompanyId Exception", e1);
		}
		ObjectMapper mapper = new ObjectMapper();
		String markuptext;
		String commissiontext;
		try {
			markuptext = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(markupMap);
			commissiontext = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(markupCommissionDetails);

		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			policyResponseData = getPolicyResponseData(createInsurancePolicyRequest, appKeyVo, markupMap, moneydao, companyDao, companyConfigDAO, insuranceCommonDao,markupCommissionDetails);		
		}catch(Exception e){
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.CREATEPOLICY_ERROR.getErrorMessage());
		}
		return policyResponseData;
	}

	public PolicyResponseData getPolicyResponseData(CreateInsurancePolicyRequest createInsurancePolicyRequest,AppKeyVo appKeyVo,Map<String,List<InsuranceMarkUp>> markupMap,MoneyExchangeDao moneydao,CompanyDao companyDao,CompanyConfigDAO companyConfigDAO,InsuranceCommonDao insuranceCommonDao,InsuranceMarkupCommissionDetails markupCommissionDetails){
		InsuranceOrderRow  insuranceOrderRow = null;
		PolicyResponseData policyResponseData = null;
		try{
			BigDecimal totalBasePrice = new BigDecimal(0);
			BigDecimal totalTaxes = new BigDecimal(0);
			BigDecimal totalBookingPrice = new BigDecimal(0);
			BigDecimal totalPayableAmout = new BigDecimal(0);

			for (TravellerDetails travellerDetails : createInsurancePolicyRequest.getTravellerDetails()) {
				TrawellTagPremiumChart trawellTagPremiumChart = insuranceCommonDao.getTrawellTagPremiumChart(createInsurancePolicyRequest.getNoOfDays(), travellerDetails.getAge(),createInsurancePolicyRequest.getPlanId());
				BigDecimal baseprice = trawellTagPremiumChart.getPremiumAmount().setScale(0, RoundingMode.UP); 
				BigDecimal serviceTax = trawellTagPremiumChart.getPremiumAmount().divide(new BigDecimal("1.18"),2,RoundingMode.HALF_UP);
				serviceTax = trawellTagPremiumChart.getPremiumAmount().subtract(serviceTax.setScale(0, RoundingMode.UP));
				serviceTax = serviceTax.setScale(0, RoundingMode.UP);				
				BigDecimal totalBaseCharges = trawellTagPremiumChart.getPremiumAmount().subtract(serviceTax);
				totalBaseCharges = totalBaseCharges.setScale(0, RoundingMode.UP);				
				BigDecimal totalCharges = totalBaseCharges.add(serviceTax);
				totalCharges = totalCharges.setScale(0, RoundingMode.UP);					

				totalBasePrice = totalBasePrice.add(totalBaseCharges);
				totalTaxes = totalTaxes.add(serviceTax);
				totalBookingPrice = totalBookingPrice.add(totalCharges);
			}			


			long lastrowid = insuranceCommonDao.getLastInsurancePolicyTempId();
			String transactionKey =  "ITK"+lastrowid;		
			insuranceOrderRow = new InsuranceOrderRow();
			insuranceOrderRow.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			insuranceOrderRow.setBookingCurrency(createInsurancePolicyRequest.getCurrency());
			insuranceOrderRow.setBookingMode("Online");
			insuranceOrderRow.setBaseToBookingExchangeRate(new BigDecimal(1));
			insuranceOrderRow.setApiToBaseExchangeRate(new BigDecimal(1));
			insuranceOrderRow.setConfigId(String.valueOf(appKeyVo.getConfigId()));
			insuranceOrderRow.setCompanyId(String.valueOf(appKeyVo.getCompanyId()));
			insuranceOrderRow.setCreatedBy(createInsurancePolicyRequest.getUserName());
			insuranceOrderRow.setUserId(InsuranceCommonUtil.checkGetEmulatedUserById(createInsurancePolicyRequest));
			insuranceOrderRow.setPaymentStatus("Pending");
			//insuranceOrderRow.setBookingDate(InsuranceCommonUtil.getInsuranceFormatDate());
			insuranceOrderRow.setInsuranceBookingDate(InsuranceCommonUtil.getInsuranceFormatDate());
			insuranceOrderRow.setCreatedBy(createInsurancePolicyRequest.getUserName());
			insuranceOrderRow.setEmpName(createInsurancePolicyRequest.getTravellerDetails().get(0).getFirstName()+" "+createInsurancePolicyRequest.getTravellerDetails().get(0).getLastName());
			insuranceOrderRow.setCreditNoteIssued(false);
			insuranceOrderRow.setOrderRequested(false);
			insuranceOrderRow.setOrderUpdated(false);
			insuranceOrderRow.setPaidBy("cash");
			insuranceOrderRow.setStatusAction("Pending");
			insuranceOrderRow.setTransactionKey(transactionKey);
			insuranceOrderRow.setRecievedAmount(new BigDecimal(0));
			insuranceOrderRow.setSupplierName("TrawellTag");
			insuranceOrderRow.setTravelDate(InsuranceCommonUtil.getInsuranceFormatDateFromString(createInsurancePolicyRequest.getDepartureDate()));
			insuranceOrderRow.setCancelMode("false");		
			insuranceOrderRow.setKnockOff(false);
			//addedby basha
			insuranceOrderRow.setServiceTax(new BigDecimal("0.0000"));
			insuranceOrderRow.setBasePrice(totalBasePrice);
			insuranceOrderRow.setTaxes(totalTaxes);
			RmConfigModel  rmConfigModel=rmConfigDetailDAO.getRmConfigModel(appKeyVo.getCompanyId());
			   if(rmConfigModel!=null){
			   InsuranceOrderRowRmConfigStruct insuranceOrderRowRmConfigStruct=new InsuranceOrderRowRmConfigStruct();
			   insuranceOrderRowRmConfigStruct.setRmDynamicData(rmConfigModel.getDynamicFieldsData());
			   insuranceOrderRow.setInsuranceOrderRowRmConfigStruct(insuranceOrderRowRmConfigStruct);
			   }
			//addedby basha
			insuranceOrderRow.setBookingDate(new Date());
			insuranceOrderRow.setSupplierPrice(totalBasePrice);
			insuranceOrderRow.setRemarks("Booking Sucessfull");
			insuranceOrderRow.setInsuredProduct(createInsurancePolicyRequest.getInsuredProduct());
			insuranceOrderRow.setInsuredProductOrderRowId(createInsurancePolicyRequest.getInsuredProductOrderRowId());

			if(createInsurancePolicyRequest.getIsCompanyEntity() != null && createInsurancePolicyRequest.getIsCompanyEntity()){
				Integer companyEntityId = createInsurancePolicyRequest.getCompanyEntityId();				
				insuranceOrderRow.setCompanyEntityId(companyEntityId.longValue());
			}


			// Insert insuranceOrderRow
			insuranceOrderRow = insuranceCommonDao.insertInsuranceOrderRowDetails(insuranceOrderRow);

			String orderId = "TYI" + insuranceOrderRow.getId();			
			insuranceOrderRow.setOrderId(orderId);			


			// Create Order Customer 
			OrderCustomer orderCustomer = InsuranceCommonUtil.createOrderCustomer(createInsurancePolicyRequest, appKeyVo,orderId);
			insuranceOrderRow.setOrderCustomer(orderCustomer);	


			// Create Insurance Customer Detail
			List<InsuranceOrderCustomerDetail>  InsuranceOrderCustomerDetailList = InsuranceCommonUtil.createInsuranceOrderCustomerDetailList(createInsurancePolicyRequest, insuranceOrderRow);
			for (InsuranceOrderCustomerDetail insuranceOrderCustomerDetail : InsuranceOrderCustomerDetailList) {
				insuranceCommonDao.insertInsuranceOrderCustomerDetails(insuranceOrderCustomerDetail);
			}
			insuranceOrderRow.setInsuranceOrderCustomerDetails(InsuranceOrderCustomerDetailList);			

			// Insert InsuranceOrderRowMarkup			 
			InsuranceMarkUpHelper.getMarkupAmtForEachCompany(markupMap, totalBookingPrice, createInsurancePolicyRequest, markupCommissionDetails);
			List<InsuranceOrderRowMarkup> insuranceOrderRowMarkupList = InsuranceCommonUtil.getInsuranceMarkupDetail(markupCommissionDetails, insuranceOrderRow);
			insuranceOrderRow.setInsuranceOrderRowMarkupList(insuranceOrderRowMarkupList);

			// Insert InsuranceOrderRowCommission
			List<InsuranceOrderRowCommission> insuranceOrderRowCommissions = new ArrayList<>();
			insuranceOrderRowCommissions = InsuranceCommonUtil.getCommissionDetails(markupCommissionDetails, insuranceOrderRow);
			BigDecimal markUp = InsuranceCommonUtil.getTotalMarkup(markupCommissionDetails);
			insuranceOrderRow.setMarkUpamount(markUp);


			BigDecimal processingFees = new BigDecimal("0.0");

			InsuranceMarkUpHelper.getCommissionWithMarkupValuesForEachCompany(insuranceOrderRowCommissions, markupMap, totalBookingPrice, markupCommissionDetails,createInsurancePolicyRequest);
			insuranceOrderRow.setInsuranceOrderRowCommissionList(insuranceOrderRowCommissions);




			int configId = Integer.valueOf(insuranceOrderRow.getConfigId());
			CompanyConfig companyConfig = null;
			try {
				companyConfig = companyConfigDAO.getCompanyConfigByConfigId(configId);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			Company	company = companyDao.getCompany(Integer.parseInt(insuranceOrderRow.getCompanyId()));
			Company parentCompany = null;
			try{
				parentCompany = companyDao.getParentCompany(company);
			}catch(Exception e){
				e.printStackTrace();
			}
			if(companyConfig != null )
			{	
				totalPayableAmout = InsuranceMarkUpHelper.applyMarkupBeforeCallAPI(totalBookingPrice, markupMap, companyConfig);
				if(companyConfig.getCompanyConfigType().isB2E()){	
					if(companyConfig.getTaxtype().equalsIgnoreCase("GST")){
						InsuranceOrderRowGstTax insuranceOrderRowGstTax = new InsuranceOrderRowGstTax();
						InsuranceGstTaxConfig insuranceGstTaxConfig = companyConfig.getInsuranceGstTaxConfig();
						insuranceOrderRowGstTax =  InsuranceCommonUtil.createInsuranceOrderRowGstTax(insuranceGstTaxConfig, insuranceOrderRowGstTax, company, parentCompany,createInsurancePolicyRequest.getTravellerDetails().size());
						insuranceOrderRow.setInsuranceOrderRowGstTax(insuranceOrderRowGstTax);
					}else{
						insuranceOrderRow.setInsuranceOrderRowServiceTax(null);
						insuranceOrderRow.setInsuranceOrderRowGstTax(null);
					}
				}
				else{
					insuranceOrderRow.setInsuranceOrderRowServiceTax(null);
					insuranceOrderRow.setInsuranceOrderRowGstTax(null);
				}
			}			

			insuranceOrderRow.setManagementFee(insuranceOrderRow.getInsuranceOrderRowGstTax()!= null?insuranceOrderRow.getInsuranceOrderRowGstTax().getManagementFee(): new BigDecimal(0));
			insuranceOrderRow.setConvenienceFee(insuranceOrderRow.getInsuranceOrderRowGstTax() != null?insuranceOrderRow.getInsuranceOrderRowGstTax().getConvenienceFee(): new BigDecimal(0));
			insuranceOrderRow.setProcessingFees(processingFees);
			insuranceOrderRow.setOtherTaxes(new BigDecimal(0));
			BigDecimal totalPrice = totalBookingPrice.add(processingFees);
			insuranceOrderRow.setTotalAmount(totalPrice);

			insuranceCommonDao.updateInsuranceOrderRowDetails(insuranceOrderRow);

			PaymentTransaction paymentTransaction = new PaymentTransaction();
			paymentTransaction.setAmount(AmountRoundingModeUtil.roundingMode(totalPayableAmout.add(processingFees)));
			paymentTransaction.setCurrency("INR");
			paymentTransaction.setRefno(orderId);
			paymentTransaction.setIsPaymentSuccess(false);
			paymentTransaction.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			paymentTransaction.setPayment_method("cash");
			paymentTransaction.setApi_transaction_id(orderId);
			paymentTransaction.setPayment_status("Pending");
			String pgId = "PGI" + insuranceOrderRow.getId();
			paymentTransaction.setRefno(pgId);

			insuranceCommonDao.insertPaymentTransactionDetails(paymentTransaction);	

			List<Company> companyListBottomToTop = new LinkedList<>();
			List<User> userListBottomToTop = new LinkedList<>();
			Map<Integer, CutandPayModel> cutAndPayUserMap = new LinkedHashMap<>();	

			boolean result = false;
			WalletAmountTranferHistory walletAmountTranferHistory = new WalletAmountTranferHistory();
			walletAmountTranferHistory.setActionId(insuranceOrderRow.getOrderId());
			walletAmountTranferHistory.setCurrency("INR");

			if(companyConfig!=null)
			{

				companyListBottomToTop = InsuranceCommonUtil.getParentCompanyBottomToTop(companyConfig.getCompany_id(),companyDao);
				if(companyListBottomToTop!=null && companyListBottomToTop.size()>0)
				{
					User currentUser = companyDao.getUserById(Integer.valueOf(insuranceOrderRow.getUserId()));
					userListBottomToTop = CommonUtil.getUsersAllWithUserModeBottomToTop(companyListBottomToTop,companyDao,currentUser);
				}
				cutAndPayUserMap = InsuranceCommonUtil.getCutandPayModelUsers(totalPayableAmout, new BigDecimal(createInsurancePolicyRequest.getTravellerDetails().size()), companyConfig, userListBottomToTop, markupMap, markupCommissionDetails.getCommissionDetailslist(), insuranceOrderRow);

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
								if(!userWalletDAO.checkWalletAmount(userInner.getId(), totalPayableAmount,new BigDecimal(0), new BigDecimal(0))){
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
									if(userWalletDAO.checkWalletAmount(userInner.getId(), totalPayableAmount,new BigDecimal(0), new BigDecimal(0)))
									{		
										userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,new BigDecimal(0), new BigDecimal(0),CommonBookingStatusEnum.INSURANCE_REMARKS.getMessage(),true);
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
													userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,new BigDecimal(0), new BigDecimal(0),CommonBookingStatusEnum.INSURANCE_FAILEDREMARKS.getMessage(),false);
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

			if(result){
				try{
					// Call Insurance Policy API
					policyResponseData = InsurancePolicyExecutorServiceTaskHelper.createPolicyService(createInsurancePolicyRequest, appKeyVo, markupMap, moneydao, companyDao, companyConfigDAO, insuranceCommonDao,insuranceOrderRow);

					if(policyResponseData.getStatus().getErrorCode() == Status.SUCCESSCODE){
						List<InsuranceOrderCustomerDetail> 	insuranceOrderCustomerDetailListUpdate = new ArrayList<>();
						List<InsuranceOrderCustomerDetail> insuranceOrderCustomerDetailList = insuranceOrderRow.getInsuranceOrderCustomerDetails();
						for (int i = 0; i < insuranceOrderCustomerDetailList.size(); i++) {
							InsuranceOrderCustomerDetail insuranceOrderCustomerDetail = insuranceOrderCustomerDetailList.get(i);
							PolicyResposne policyResposne = policyResponseData.getPolicyResposne().get(i);
							insuranceOrderCustomerDetail.setPolicyNumber(policyResposne.getPolicyNumber());
							insuranceOrderCustomerDetail.setClaimCode(policyResposne.getClaimCode());
							insuranceOrderCustomerDetail.setPaxId(insuranceOrderCustomerDetail.getPaxId());
							insuranceOrderCustomerDetail.setDocumentReferUrl(policyResposne.getDocumentUrl());
							insuranceOrderCustomerDetailListUpdate.add(insuranceOrderCustomerDetail);
						}
						insuranceOrderRow.setInsuranceOrderCustomerDetails(insuranceOrderCustomerDetailListUpdate);
						insuranceOrderRow.setTotalGstTax(policyResponseData.getPriceDetail().getInsurenceGstTax() != null?policyResponseData.getPriceDetail().getInsurenceGstTax().getTotalTax(): new BigDecimal(0));

						insuranceOrderRow.setStatusAction("Confirmed");
						insuranceOrderRow.setPaymentStatus("Success");
						insuranceOrderRow.setApiComments(policyResponseData.getReferance());
						insuranceOrderRow.setConfirmationNumber(policyResponseData.getPolicyResposne().get(0).getPolicyNumber());
						insuranceOrderRow.setTotInvoiceAmount(policyResponseData.getPriceDetail().getTotalPayableAmount());
						insuranceOrderRow.setInvoiceNo(RandomConfigurationNumber.generateInsurenceInvoiceNumber(insuranceOrderRow.getId()).toString());

						paymentTransaction.setIsPaymentSuccess(true);
						paymentTransaction.setPayment_status("SUCCESS");
						paymentTransaction.setTransactionId(insuranceOrderRow.getOrderId());
						paymentTransaction.setResponse_message("NA");
						paymentTransaction.setResponseCode("NA");

						// Update paymentTransaction
						insuranceCommonDao.updatePaymentTransactionDetails(paymentTransaction);


						userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(insuranceOrderRow.getOrderId(), insuranceOrderRow.getInvoiceNo());


						policyResponseData.setOrderId(orderId);
						policyResponseData.setTransactionKey(transactionKey);
					}
				}
				catch (Exception e) {
					insuranceOrderRow.setStatusAction("Failed");
					insuranceOrderRow.setPaymentStatus("Failed");
					insuranceOrderRow.setInvoiceNo("0"); 

					if(userListBottomToTop!=null && userListBottomToTop.size()>0)
					{
						for(User userInner : userListBottomToTop)
						{
							if(userInner.getAgentWallet()!=null)
							{
								if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
								{
									BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
									userWalletDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,new BigDecimal(0), new BigDecimal(0),CommonBookingStatusEnum.INSURANCE_FAILEDREMARKS.getMessage(),false);
								}
								else{
									result = false;
								}
							}
						}
					}	
					userWalletDAO.walletTransferHistoryUpdateWithInvoiceNo(insuranceOrderRow.getOrderId(), "0");


				}
				// Update insuranceOrderRow
				insuranceCommonDao.updateInsuranceOrderRowDetails(insuranceOrderRow);

			}else{
				policyResponseData = new PolicyResponseData();
				Status status = new Status();
				status.setErrorCode(Status.FAILEDCODE);
				status.setErrorMessage("Insufficient Balance");
				policyResponseData.setStatus(status);
				policyResponseData.setOrderId(orderId);
				policyResponseData.setTransactionKey(transactionKey);
			}

			// Insert Search Temp			
			TayyarahInsuranceMap tayyarahInsuranceMap = new TayyarahInsuranceMap();
			tayyarahInsuranceMap.setCreateInsurancePolicyRequest(createInsurancePolicyRequest);
			tayyarahInsuranceMap.setInsuranceMarkUpConfiglistMap(markupMap);
			tayyarahInsuranceMap.setPolicyResponseData(policyResponseData);
			tayyarahInsuranceMap.setMarkupCommissionDetails(markupCommissionDetails);
			byte[] insurancePolicyData = InsuranceCommonUtil.convertObjectToByteArray(tayyarahInsuranceMap);
			InsurancePolicyTemp insurancePolicyTemp = new InsurancePolicyTemp();
			insurancePolicyTemp.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			insurancePolicyTemp.setTransactionKey(transactionKey);
			insurancePolicyTemp.setInsurancePolicyData(insurancePolicyData);
			insuranceCommonDao.saveorupdateInsuranceSearchTemp(insurancePolicyTemp);

		}catch(Exception e){
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.CREATEPOLICY_ERROR.getErrorMessage());
		}
		return policyResponseData;
	}

	@ExceptionHandler(BaseException.class)
	public @ResponseBody
	RestError handleCustomException(BaseException ex,
			HttpServletResponse response) {
		response.setHeader("Content-Type", "application/json");
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return ex.transformException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
