package com.tayyarah.bus.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.tayyarah.api.orderrow.rm.structure.BusOrderRowRmConfigStruct;
import com.tayyarah.api.orderrow.rm.structure.HotelOrderRowRmConfigStruct;
import com.tayyarah.bus.dao.BusCommonDao;
import com.tayyarah.bus.entity.BusBlockedSeatTemp;
import com.tayyarah.bus.entity.BusOrderCustomerDetail;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.bus.entity.BusOrderRowCommission;
import com.tayyarah.bus.entity.BusOrderRowGstTax;
import com.tayyarah.bus.entity.BusOrderRowMarkup;
import com.tayyarah.bus.entity.BusOrderRowServiceTax;
import com.tayyarah.bus.entity.BusSearchTemp;
import com.tayyarah.bus.entity.BusSeatAvailableTemp;
import com.tayyarah.bus.model.BlockBusDetail;
import com.tayyarah.bus.model.BlockFareDetail;
import com.tayyarah.bus.model.BusBlockTicketRequest;
import com.tayyarah.bus.model.BusBlockTicketResponse;
import com.tayyarah.bus.model.BusMarkUpConfig;
import com.tayyarah.bus.model.BusMarkupCommissionDetails;
import com.tayyarah.bus.model.TayyarahBusSearchMap;
import com.tayyarah.bus.model.TayyarahBusSeatMap;
import com.tayyarah.bus.model.TayyarahSeatBlockedMap;
import com.tayyarah.bus.util.BusBaseException;
import com.tayyarah.bus.util.BusCommonUtil;
import com.tayyarah.bus.util.BusErrorMessages;
import com.tayyarah.bus.util.BusException;
import com.tayyarah.bus.util.BusMarkupHelper;
import com.tayyarah.bus.util.BusParamValidator;
import com.tayyarah.bus.util.BusRestError;
import com.tayyarah.bus.util.ErrorCodeCustomerEnum;
import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.dao.RmConfigDetailDAO;
import com.tayyarah.common.entity.OrderCustomer;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.entity.RmConfigModel;
import com.tayyarah.common.entity.RmConfigTripDetailsModel;
import com.tayyarah.common.gstconfig.entity.BusGstTaxConfig;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.common.servicetaxconfig.entity.BusServiceTaxConfig;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.GetFrontUserDetail;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.esmart.bus.util.EsmartBusConfig;
import com.tayyarah.esmart.bus.util.EsmartServiceCall;
import com.tayyarah.user.dao.FrontUserDao;
import com.tayyarah.user.entity.FrontUserDetail;

@RestController
@RequestMapping("/bus/block")
public class BusBlockTicketController {
	static final Logger logger = Logger.getLogger(BusBlockTicketController.class);
	private static BusParamValidator  busParamValidator = new BusParamValidator();
	@Autowired
	CompanyDao companyDAO;
	@Autowired
	BusCommonDao busCommonDao;
	@Autowired
	CompanyConfigDAO companyConfigDAO;
	@Autowired
	MoneyExchangeDao moneydao;
	@Autowired
	FrontUserDao frontUserDao;
	@Autowired
	EmailDao emaildao;
	@Autowired
	RmConfigDetailDAO rmConfigDetailDAO;
	
	
	@RequestMapping(value = "", method = RequestMethod.POST, headers = { "Accept=application/json" }, produces = { "application/json" })
	public @ResponseBody BusBlockTicketResponse getBlockTicketReponse(@RequestBody BusBlockTicketRequest busBlockTicketRequest,HttpServletResponse response,HttpServletRequest request){
		ResponseHeader.setResponse(response);
		// Check APP KEY
		if(busBlockTicketRequest.getApp_key()!=null && busBlockTicketRequest.getApp_key().equalsIgnoreCase(""))
		{
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NOTFOUND_APPKEY.getErrorMessage());
		}
		BusBlockTicketResponse busBlockTicketResponse = new BusBlockTicketResponse();
		busParamValidator.blockRequestValidator(busBlockTicketRequest);
		busParamValidator.blockRequestPaxDetailValidator(busBlockTicketRequest.getBusPaxDetails());

		try{
			String decryptedAppKey = busCommonDao.getDecryptedAppKey(companyDAO,busBlockTicketRequest.getApp_key());
			// get Bus Details
			BusSearchTemp busSearchTemp = busCommonDao.getBusSearchTemp(busBlockTicketRequest.getSearchkey());
			TayyarahBusSearchMap  busSearchMap = (TayyarahBusSearchMap) BusCommonUtil.convertByteArrayToObject(busSearchTemp.getBusSearchData());
			// get Seat Details
			BusSeatAvailableTemp busSeatAvailableTemp = busCommonDao.getBusSeatAvailableTemp(busBlockTicketRequest.getSearchkey());
			TayyarahBusSeatMap tayyarahBusSeatMap  = (TayyarahBusSeatMap) BusCommonUtil.convertByteArrayToObject(busSeatAvailableTemp.getBusSeatData());
			Map<String,List<BusMarkUpConfig>> markupMap = tayyarahBusSeatMap.getBusMarkUpConfiglistMap();
			CurrencyConversionMap currencyConversionMap = BusCommonUtil.buildCurrencyConversionMap(busSearchMap.getBusSearchRequest().getCurrency(),moneydao);
			EsmartBusConfig  esmartBusConfig = EsmartBusConfig.GetEsmartBusConfig(decryptedAppKey);
			BusCommonUtil.checkGetEmulatedUserById(busBlockTicketRequest);

			// Save Block Details in DB and Get Response from Provider
			busBlockTicketResponse = saveAndGetBlockResponse(esmartBusConfig,busBlockTicketRequest, markupMap, currencyConversionMap, decryptedAppKey, companyConfigDAO, tayyarahBusSeatMap, busSearchMap,busBlockTicketRequest.getApp_key());

		}catch(Exception e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BLOCKFAILED.getErrorMessage());
		}
		return busBlockTicketResponse;
	}

	public BusBlockTicketResponse saveAndGetBlockResponse(EsmartBusConfig  esmartBusConfig,BusBlockTicketRequest busBlockTicketRequest,Map<String,List<BusMarkUpConfig>> markupMap,CurrencyConversionMap currencyConversionMap,String decryptedAppKey,CompanyConfigDAO companyConfigDAO,TayyarahBusSeatMap tayyarahBusSeatMap,TayyarahBusSearchMap  busSearchMap,String app_key){
		BusBlockTicketResponse busBlockTicketResponse = new BusBlockTicketResponse();
		try{
			BusOrderRow busOrderRow = new BusOrderRow();

			// Insert OrderCustomer
			String appkey = AppControllerUtil.getDecryptedAppKey(companyDAO, busBlockTicketRequest.getApp_key());
			OrderCustomer orderCustomer = BusCommonUtil.createOrderCustomer(busBlockTicketRequest,appkey);
			busCommonDao.insertOrderCustomerDetails(orderCustomer);

			// Insert FrontUserDetail
			FrontUserDetail frontUserDetail =  GetFrontUserDetail.getFrontUserDetailDetails(orderCustomer,frontUserDao);
			if(frontUserDetail != null){
			try{
				frontUserDetail = frontUserDao.insertFrontUserDetail(frontUserDetail);
			}catch(Exception e){
				logger.error("Exception", e);
			}
			
			// Send Email
			if(frontUserDetail.getId()!=null && frontUserDetail.getId() != 0 ){
				emaildao.insertEmail(String.valueOf(frontUserDetail.getId()), 0, Email.EMAIL_TYPE_FRONT_USER_REGISTRATION_BY_TAYYARAH);
			}
			}
			// Create BusOrderRow

			BlockBusDetail blockBusDetail = BusCommonUtil.getBlockedBusDetail(busSearchMap, busBlockTicketRequest.getSearchkey(), busBlockTicketRequest.getRouteScheduleId(), busBlockTicketRequest);
			BlockFareDetail blockFareDetail = BusCommonUtil.getBlockedTicketFareDetail(tayyarahBusSeatMap, busBlockTicketRequest);
			busOrderRow.setDepartureTime(blockBusDetail.getDepartureTime());
			busOrderRow.setArrivalTime(blockBusDetail.getArrivalTime());
			busOrderRow.setBasePrice(blockFareDetail.getBasePrice());
			busOrderRow.setTaxes(blockFareDetail.getTaxes());
			busOrderRow.setTotalAmount(blockFareDetail.getBookingPrice());
			busOrderRow.setBusCompanyName(blockBusDetail.getBusOperator());
			busOrderRow.setBusType(blockBusDetail.getBusType());
			busOrderRow.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
			busOrderRow.setOrigin(busBlockTicketRequest.getOrigin());
			busOrderRow.setDestination(busBlockTicketRequest.getDestination());
			busOrderRow.setPickUp(blockBusDetail.getBoardingPoint()!=null?blockBusDetail.getBoardingPoint().getLoc():busBlockTicketRequest.getOrigin());
			busOrderRow.setDestination(blockBusDetail.getDroppingPoint()!=null && !blockBusDetail.getDroppingPoint().getLoc().equalsIgnoreCase("")?blockBusDetail.getDroppingPoint().getLoc():busBlockTicketRequest.getDestination());
			busOrderRow.setEmpName(busBlockTicketRequest.getBusPaxDetails().get(0).getFirstName()+" "+busBlockTicketRequest.getBusPaxDetails().get(0).getLastName());
			busOrderRow.setLocation(busBlockTicketRequest.getOrigin());
			busOrderRow.setOrderCustomer(orderCustomer);
			busOrderRow.setPaidBy(busBlockTicketRequest.getPayMode());
			busOrderRow.setSupplierName("ESmart Travels");
			busOrderRow.setSupplierPrice(blockFareDetail.getApiPrice());
			busOrderRow.setTravelDate(BusCommonUtil.getBusFormatDateFromString(busBlockTicketRequest.getOnwardDate()));		
			busOrderRow.setBusBookingDate(BusCommonUtil.getBusFormatDate());
			busOrderRow.setBookingCurrency("INR");
			busOrderRow.setBookingMode("Online");
			busOrderRow.setConfigId(decryptedAppKey.substring(0,decryptedAppKey.indexOf("-")));
			busOrderRow.setCompanyId(decryptedAppKey.substring(decryptedAppKey.indexOf("-")+1));
			busOrderRow.setCreatedBy(busBlockTicketRequest.getUserName());
			busOrderRow.setUserId(BusCommonUtil.checkGetEmulatedUserById(busBlockTicketRequest));
			busOrderRow.setPaymentStatus("Pending");
			busOrderRow.setStatusAction("Pending");
			busOrderRow.setApiToBaseExchangeRate(new BigDecimal(1));
			busOrderRow.setBaseToBookingExchangeRate(new BigDecimal(1));
			busOrderRow.setServiceTax(blockFareDetail.getBusServiceTax()!=null?blockFareDetail.getBusServiceTax().getTotalServiceTax():new BigDecimal(0));
			busOrderRow.setTotalGstTax(blockFareDetail.getBusGstTax()!=null?blockFareDetail.getBusGstTax().getTotalTax().multiply(new BigDecimal(busBlockTicketRequest.getBusPaxDetails().size())):new BigDecimal(0));
			busOrderRow.setTotInvoiceAmount(blockFareDetail.getTotalPayableAmount());
			busOrderRow.setRecievedAmount(new BigDecimal(0));
			if(busBlockTicketRequest.getIsCompanyEntity() != null && busBlockTicketRequest.getIsCompanyEntity()){
				Integer companyEntityId = busBlockTicketRequest.getCompanyEntityId();				
				busOrderRow.setCompanyEntityId(companyEntityId.longValue());
			}

			// Insert BusOrderRowMarkup
			BusMarkupCommissionDetails busMarkupCommissionDetails = tayyarahBusSeatMap.getMarkupCommissionDetails();
			BusMarkupHelper.getMarkupAmtForEachCompany(markupMap, blockFareDetail, busBlockTicketRequest, busMarkupCommissionDetails);
			List<BusOrderRowMarkup> busOrderRowMarkupList = BusCommonUtil.getBusMarkupDetail(busMarkupCommissionDetails, busOrderRow);
			busOrderRow.setBusOrderRowMarkupList(busOrderRowMarkupList);

			// Insert BusOrderRowCommission
			List<BusOrderRowCommission> busOrderRowCommissions = new ArrayList<>();
			busOrderRowCommissions = BusCommonUtil.getCommissionDetails(busMarkupCommissionDetails, busOrderRow);
			BigDecimal markUp = BusCommonUtil.getTotalMarkup(busMarkupCommissionDetails);
			busOrderRow.setMarkUp(markUp);
			BigDecimal processingFees = new BigDecimal("0.0");
			if(!busBlockTicketRequest.getPayMode().equals("cash")){
				processingFees = blockFareDetail.getTotalPayableAmount().divide(new BigDecimal("100")).multiply(new BigDecimal("2.0")) ;
			}
			BusMarkupHelper.getCommissionWithMarkupValuesForEachCompany(busOrderRowCommissions, markupMap, blockFareDetail, busMarkupCommissionDetails, busBlockTicketRequest);
			busOrderRow.setBusOrderRowCommissionList(busOrderRowCommissions);

			int configId = Integer.valueOf(busOrderRow.getConfigId());
			CompanyConfig companyConfig = null;
			try {
				companyConfig = companyConfigDAO.getCompanyConfigByConfigId(configId);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			Company	company = companyDAO.getCompany(Integer.parseInt(busOrderRow.getCompanyId()));
			Company parentCompany = null;
			try{
				parentCompany = companyDAO.getParentCompany(company);
			}catch(Exception e){
				e.printStackTrace();
			}
			if(companyConfig != null )
			{
				if(companyConfig.getCompanyConfigType().isB2E()){	
					if(companyConfig.getTaxtype().equalsIgnoreCase("GST")){
						BusOrderRowGstTax busOrderRowGstTax = new BusOrderRowGstTax();
						BusGstTaxConfig busGstTaxConfig = companyConfig.getBusGstTaxConfig();
						busOrderRowGstTax =  BusCommonUtil.createBusOrderRowGstTax(busGstTaxConfig, busOrderRowGstTax, company, parentCompany,busBlockTicketRequest.getBusPaxDetails().size());
						busOrderRow.setBusOrderRowGstTax(busOrderRowGstTax);
					}else{
						BusOrderRowServiceTax busOrderRowServiceTax = new BusOrderRowServiceTax();
						BusServiceTaxConfig busServiceTaxConfig = companyConfig.getBusServiceTaxConfig();
						busOrderRowServiceTax = BusCommonUtil.createBusOrderRowServiceTax(busServiceTaxConfig,busOrderRowServiceTax);
						busOrderRow.setBusOrderRowServiceTax(busOrderRowServiceTax);
					}
				}
				else{
					busOrderRow.setBusOrderRowServiceTax(null);
					busOrderRow.setBusOrderRowGstTax(null);
				}
			}

			// Insert BusOrderRow
			busOrderRow.setManagementFee(busOrderRow.getBusOrderRowServiceTax() != null?busOrderRow.getBusOrderRowServiceTax().getManagementFee(): new BigDecimal(0));
			busOrderRow.setConvenienceFee(busOrderRow.getBusOrderRowServiceTax() != null?busOrderRow.getBusOrderRowServiceTax().getConvenienceFee(): new BigDecimal(0));
			busOrderRow.setProcessingFees(processingFees);
			busOrderRow.setOtherTaxes(new BigDecimal(0));
			BigDecimal totalPrice =  blockFareDetail.getBookingPrice().add(processingFees);
			busOrderRow.setTotalAmount(totalPrice);
			AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDAO, app_key);
			//added by basha for
			//added by basha  for getHotelOrderRowRmConfigStruct dynamic model inserion 
			if(busOrderRow.getBusOrderRowRmConfigStruct()==null ){
				try{
					RmConfigModel  rmConfigModel=rmConfigDetailDAO.getRmConfigModel(appKeyVo.getCompanyId());
					   if(rmConfigModel!=null){
						  BusOrderRowRmConfigStruct busOrderRowRmConfigStruct=new BusOrderRowRmConfigStruct();
						  busOrderRowRmConfigStruct.setRmDynamicData(rmConfigModel.getDynamicFieldsData());
						  busOrderRow.setBusOrderRowRmConfigStruct(busOrderRowRmConfigStruct);
					   }
					} catch (Exception e) {
						logger.error("Exception", e);
						//throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.BOOKING_FAILED);
					}
					
			}
				
			busCommonDao.insertBusOrderRowDetails(busOrderRow);
	
			// Insert BusOrderCustomerDetail
			List<BusOrderCustomerDetail> busOrderCustomerDetailList = BusCommonUtil.getBusOrderCustomerDetail(busBlockTicketRequest, busOrderRow,tayyarahBusSeatMap);
			for (BusOrderCustomerDetail busOrderCustomerDetail : busOrderCustomerDetailList) {
				busCommonDao.insertBusOrderCustomerDetails(busOrderCustomerDetail);
			}
			busOrderRow.setBusOrderCustomerDetails(busOrderCustomerDetailList);

			String orderId = "TYBU" + busOrderRow.getId();
			busOrderRow.setOrderId(orderId);			
			String transcationKey = BusCommonUtil.getTransactionkey(busSearchMap, busBlockTicketRequest.getSearchkey());
			busOrderRow.setTransactionKey(transcationKey);
			String routeScheduleId = BusCommonUtil.getRouteScheduleId(tayyarahBusSeatMap, busBlockTicketRequest.getSearchkey());
			String cancellationPolicy = BusCommonUtil.getCancellationPolicy(busSearchMap, busBlockTicketRequest.getSearchkey(), routeScheduleId);
			OrderCustomer orderCustomerBus = busOrderRow.getOrderCustomer();
			orderCustomerBus.setOrderId(orderId);
			orderCustomerBus = busCommonDao.updateOrderCustomerDetails(orderCustomerBus);
			busOrderRow.setOrderCustomer(orderCustomerBus);
			// Call API 
			busBlockTicketResponse = EsmartServiceCall.blockBusTicket(esmartBusConfig, busBlockTicketRequest, markupMap, currencyConversionMap, decryptedAppKey, companyConfigDAO, tayyarahBusSeatMap, busSearchMap);

			if(busBlockTicketResponse.getStatus().getCode() == 1){			
				busBlockTicketResponse.setOrderId(orderId);
				busBlockTicketResponse.setSearchkey(busBlockTicketRequest.getSearchkey());
				busBlockTicketResponse.setTransactionkey(transcationKey);

				for (BusOrderCustomerDetail busOrderCustomerDetail : busOrderCustomerDetailList) {
					busOrderCustomerDetail.setEticketnumber(busBlockTicketResponse.getBlockTicketKey());
				}
				for (BusOrderCustomerDetail busOrderCustomerDetail : busOrderCustomerDetailList) {
					busCommonDao.insertBusOrderCustomerDetails(busOrderCustomerDetail);
				}
				busOrderRow.setBusOrderCustomerDetails(busOrderCustomerDetailList);

				// Insert PaymentTransaction
				PaymentTransaction paymentTransaction = new PaymentTransaction();
				paymentTransaction.setAmount(AmountRoundingModeUtil.roundingMode(busBlockTicketResponse.getBlockFareDetail().getTotalPayableAmount().add(processingFees)));
				paymentTransaction.setCurrency("INR");
				paymentTransaction.setRefno(orderId);
				paymentTransaction.setIsPaymentSuccess(false);
				paymentTransaction.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
				paymentTransaction.setPayment_method(busBlockTicketRequest.getPayMode());
				paymentTransaction.setApi_transaction_id(orderId);
				paymentTransaction.setPayment_status("Pending");

				String pgId = "PGB" + busOrderRow.getId();
				paymentTransaction.setRefno(pgId);
				busBlockTicketResponse.setPgRefNo(pgId);
				Company newCompany=companyDAO.getCompany(appKeyVo.getCompanyId());
				busBlockTicketResponse.setGstNumber(newCompany!=null && newCompany.getCompanyGstIn()!=null?newCompany.getCompanyGstIn():null);
				
				busCommonDao.insertPaymentTransactionDetails(paymentTransaction);

				// Save Response
				TayyarahSeatBlockedMap tayyarahSeatBlockedMap = new TayyarahSeatBlockedMap();
				tayyarahSeatBlockedMap.setBusBlockTicketRequest(busBlockTicketRequest);
				tayyarahSeatBlockedMap.setBusBlockTicketResponse(busBlockTicketResponse);
				tayyarahSeatBlockedMap.setBusMarkUpConfiglistMap(markupMap);
				tayyarahSeatBlockedMap.setMarkupCommissionDetails(busMarkupCommissionDetails);
				byte[] busblockedData = BusCommonUtil.convertObjectToByteArray(tayyarahSeatBlockedMap);
				BusBlockedSeatTemp busBlockedSeatTemp = new BusBlockedSeatTemp();
				busBlockedSeatTemp.setBusBlockedData(busblockedData);
				busBlockedSeatTemp.setCreatedAt(new Timestamp(new Date().getTime()));
				busBlockedSeatTemp.setSearchKey(busBlockTicketRequest.getSearchkey());
				busBlockedSeatTemp.setTransactionKey(BusCommonUtil.getTransactionkey(busSearchMap, busBlockTicketRequest.getSearchkey()));
				/*if(!busBlockTicketRequest.getIsLowFare()){
					busBlockedSeatTemp.setLowFareRouteScheduleId(busBlockTicketRequest.getLowFareRouteScheduleId());
					busBlockedSeatTemp.setReasonToSelect(busBlockTicketRequest.getReasonToSelect());
				}*/
				busCommonDao.saveorupdateBusBlockedSeatTemp(busBlockedSeatTemp);
			}
			// Update Bus OrderRow
			busCommonDao.updateBusOrderRowDetails(busOrderRow);

			// Insert Bus Order Rm Details
			/*if(busBlockTicketRequest.getIsRmDetails()){
				for (RmConfigTripDetailsModel rmConfigTripDetailsModel : busBlockTicketRequest.getRmDataListDetails()) {
					rmConfigTripDetailsModel.setOrdertype("Bus");
					rmConfigTripDetailsModel.setOrderId(busOrderRow.getOrderId());
					try{
						companyDAO.insertRMConfigTripDetails(rmConfigTripDetailsModel);
					}catch(Exception e){

					}
				}
			}*/


		}catch(Exception e){
			throw new BusException(ErrorCodeCustomerEnum.Exception,BusErrorMessages.NO_BLOCKFAILED.getErrorMessage());
		}
		return busBlockTicketResponse;
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
