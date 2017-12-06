package com.tayyarah.hotel.controller;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.notification.NotificationUtil;
import com.tayyarah.common.notification.dao.NotificationDao;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.common.util.enums.InventoryTypeEnum;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.hotel.dao.HotelOrderDao;
import com.tayyarah.hotel.dao.HotelSearchDao;
import com.tayyarah.hotel.dao.HotelTransactionDao;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRowCancellation;
import com.tayyarah.hotel.model.APIHotelCancelRequest;
import com.tayyarah.hotel.model.APIHotelCancelResponse;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.CancelRuleType;
import com.tayyarah.hotel.model.Status;
import com.tayyarah.hotel.model.UniqueIDType;
import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.CurrencyManager;
import com.tayyarah.hotel.util.HotelBookRequestBuilder;
import com.tayyarah.hotel.util.HotelIdFactoryImpl;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.api.concurrency.AsyncSupport;
import com.tayyarah.hotel.util.api.concurrency.TBOPullerTask;
import com.tayyarah.hotel.util.api.concurrency.TayyarahPullerTask;
import com.tayyarah.hotel.validator.HotelServiceEndPointValidator;
import com.tayyarah.user.dao.UserWalletDAO;

@RestController
@RequestMapping("/hotel/cancel")
public class HotelCancellationController {

	@Autowired
	HotelObjectTransformer hotelObjectTransformer;
	@Autowired
	HotelTransactionDao hotelTransactionDao;
	@Autowired
	HotelSearchDao hotelSearchDao;
	@Autowired
	HotelOrderDao hotelOrderDao;
	@Autowired
	AsyncSupport asyncSupport;
	@Autowired
	CurrencyManager currencyManager;
	@Autowired
	CompanyDao companyDao;	
	@Autowired
	UserWalletDAO AWDAO;	
	@Autowired
	NotificationDao NFDAO;

	private  HotelServiceEndPointValidator validator = new HotelServiceEndPointValidator();
	public static final Logger logger = Logger.getLogger(HotelCancellationController.class);

	@RequestMapping(value = "",method = RequestMethod.POST, headers = {"Accept=application/json"})
	public @ResponseBody APIHotelCancelResponse cancelHotel(@RequestBody APIHotelCancelRequest apiHotelCancelRequest, HttpServletRequest request, HttpServletResponse response) {	
		APIHotelCancelResponse apiHotelCancelResponse = new APIHotelCancelResponse();	
		Status status = new Status(APIStatus.STATUS_CODE_CANCEL_NOT_SET, "Cancellation Requested ..." );
		APIStatus apiStatus = new APIStatus(APIStatus.STATUS_CODE_CANCEL_NOT_SET, "Cancellation to be Requested ..." );
		try {
			apiHotelCancelRequest = validator.validate(apiHotelCancelRequest);
			AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, apiHotelCancelRequest.getAppKey());
			if(appKeyVo==null)
			{
				status = new Status(APIStatus.STATUS_CODE_ERROR_APPKEY, apiHotelCancelRequest.getAppKey());
				apiHotelCancelResponse.setStatus(status);
			}

			HotelOrderRow hor = null;
			HotelApiCredentials apiauth = null;
			HotelIdFactoryImpl hotelIdFactory = HotelIdFactoryImpl.getInstance();
			logger.info("####################---apiHotelCancelRequest=="+ apiHotelCancelRequest.toString());	
			hor = hotelOrderDao.getHotelOrderRow(apiHotelCancelRequest.getOrderId());
			logger.info("####################----relevent hotel order from db: hor=="+ hor);	


			if(apiHotelCancelRequest.getMethodType().equalsIgnoreCase(APIHotelCancelRequest.METHOD_INITIATE))
			{

				status = new Status(APIStatus.STATUS_CODE_CANCEL_NOT_SET, "Cancellation Requested ..." );
				apiStatus = new APIStatus(APIStatus.STATUS_CODE_CANCEL_NOT_SET, "Cancellation to be Requested ..." );
				apiHotelCancelResponse.setStatus(status);
				apiHotelCancelResponse.setApiStatus(apiStatus);

				if(hor!=null && hor.getBookingStatus()!=null && hor.getBookingStatus().equalsIgnoreCase("confirmed"))
				{
					logger.info("####################----relevent hotel order from db: hor=="+ hor.toString());	
					HotelOrderRowCancellation hotelOrderRowCancellation = hotelOrderDao.getUpdateHotelOrderRowCancellation(apiHotelCancelRequest.getOrderId());
					if(hotelOrderRowCancellation == null)	
					{						
						hotelOrderRowCancellation = new HotelOrderRowCancellation();
						hotelOrderRowCancellation = CommonUtil.insertOrUpdateHotelCancellation(hotelOrderDao, hor, hotelOrderRowCancellation, apiHotelCancelResponse, apiHotelCancelRequest.getMethodType());
					}

					logger.info("####################----relevent hotel order from db: apiprovider=="+ hor.getApiProvoder());	
					switch (Integer.valueOf(hor.getApiProvoder())) {
					case HotelApiCredentials.API_DESIA_IND:
						apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);

						break;
					case HotelApiCredentials.API_REZNEXT_IND:				
						apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND,appKeyVo);

						break;
					case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
						apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);

						break;
					case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:				
						apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL,appKeyVo);
						TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(apiauth, "Tayyarah API");
						apiHotelCancelResponse = tayyarahPullerTask.doCancel(apiHotelCancelRequest, apiHotelCancelResponse, hor, hotelIdFactory);

						break;
					case HotelApiCredentials.API_LINTAS_REPOSITORY:
						apiauth = HotelApiCredentials.getApiLintasReposit();

						break;
					case HotelApiCredentials.API_LINTAS_INTERNATIONAL:
						//Do Room availablity check of lintas api hotels				
						break;
					case HotelApiCredentials.API_TBO_INTERNATIONAL:
						HotelApiCredentials apitbo = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);
						TBOPullerTask tboPullerTask = new TBOPullerTask(apitbo, "TBO Api");		

						String endUserIp = request.getHeader("X-FORWARDED-FOR");  
						if (endUserIp == null) {  
							endUserIp = request.getRemoteAddr();  
						}
						if(endUserIp == null || endUserIp.contains("0:0:0:0:0"))
							endUserIp = "128.199.209.95";
						logger.info("####################----bebore cancel call=="+ hor.getApiProvoder());	

						apiHotelCancelResponse = tboPullerTask.doCancel(endUserIp, apiHotelCancelResponse, apiHotelCancelRequest, hor, hotelIdFactory,hotelOrderDao,hotelOrderRowCancellation);
						System.out.println("apiHotelCancelResponse"+apiHotelCancelResponse);
						break;
					default:
						break;
					}
					logger.info("####################---cancellation over ");	
					apiHotelCancelResponse = currencyManager.fillCurrencyOnCancelResponse(apiHotelCancelResponse, hor);
					hotelOrderRowCancellation = CommonUtil.insertOrUpdateHotelCancellation(hotelOrderDao, hor, hotelOrderRowCancellation, apiHotelCancelResponse, apiHotelCancelRequest.getMethodType());
					apiHotelCancelResponse.setStatus(apiHotelCancelResponse.getStatus());
					//hor = CommonUtil.hotelOrderInsertionData(hotelOrderDao, ht, hsc, hbc, rs, apiHotelBook);

					// insert notication after booking is successful											
					new NotificationUtil().insertNotification(appKeyVo,apiHotelCancelRequest.getOrderId() , "Hotel Cancel", InventoryTypeEnum.HOTEL_ORDER.getId(), true,NFDAO,companyDao); 

				}
				else
				{
					status = new Status(APIStatus.STATUS_CODE_ERROR, "Invalid order id ..." );				
					apiHotelCancelResponse.setStatus(status);
				}
			}
			else if(apiHotelCancelRequest.getMethodType().equalsIgnoreCase(APIHotelCancelRequest.METHOD_GET_STATUS))
			{
				logger.info("####################---cancellation METHOD_GET_STATUS call.... ");	
				HotelOrderRowCancellation hotelOrderRowCancellation = hotelOrderDao.getUpdateHotelOrderRowCancellation(apiHotelCancelRequest.getOrderId());
				logger.info("####################---cancellation METHOD_GET_STATUS call.... relevant hotelOrderRowCancellation-"+hotelOrderRowCancellation);	

				if(hotelOrderRowCancellation != null && hotelOrderRowCancellation.getAPIStatusCode() != null && hotelOrderRowCancellation.getStatusCode() != null)				
				{

					status = new Status(hotelOrderRowCancellation.getStatusCode(), hotelOrderRowCancellation.getStatusMessage() );
					apiStatus = new APIStatus(hotelOrderRowCancellation.getAPIStatusCode(), hotelOrderRowCancellation.getAPIStatusMessage() );
					apiHotelCancelResponse.setStatus(status);
					apiHotelCancelResponse.setApiStatus(apiStatus);

					logger.info("####################---cancellation METHOD_GET_STATUS call.... relevant apistatus code-"+hotelOrderRowCancellation.getAPIStatusCode());	

					if(hotelOrderRowCancellation.getAPIStatusCode().equalsIgnoreCase(APIStatus.STATUS_CODE_CANCEL_IN_PROCESS))
					{
						switch (Integer.valueOf(hor.getApiProvoder())) {
						case HotelApiCredentials.API_DESIA_IND:
							apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);

							break;
						case HotelApiCredentials.API_REZNEXT_IND:				
							apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND,appKeyVo);

							break;
						case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
							apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);

							break;
						case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:				
							apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL,appKeyVo);
							TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(apiauth, "Tayyarah API");
							apiHotelCancelResponse = tayyarahPullerTask.doCancel(apiHotelCancelRequest, apiHotelCancelResponse, hor, hotelIdFactory);

							break;
						case HotelApiCredentials.API_LINTAS_REPOSITORY:
							apiauth = HotelApiCredentials.getApiLintasReposit();

							break;
						case HotelApiCredentials.API_LINTAS_INTERNATIONAL:
							//Do Room availablity check of lintas api hotels				
							break;
						case HotelApiCredentials.API_TBO_INTERNATIONAL:
							HotelApiCredentials apitbo = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);
							TBOPullerTask tboPullerTask = new TBOPullerTask(apitbo, "TBO Api");		

							String endUserIp = request.getHeader("X-FORWARDED-FOR");  
							if (endUserIp == null) {  
								endUserIp = request.getRemoteAddr();  
							}
							if(endUserIp == null || endUserIp.contains("0:0:0:0:0"))
								endUserIp = "128.199.209.95";
							logger.info("####################----bebore cancel call=="+ hor.getApiProvoder());	

							apiHotelCancelResponse = tboPullerTask.doGetStatus(endUserIp, apiHotelCancelResponse, apiHotelCancelRequest, hor, hotelIdFactory, hotelOrderRowCancellation,hotelOrderDao);
							break;
						default:
							break;
						}
						logger.info("####################---cancellation over ");	
						apiHotelCancelResponse = currencyManager.fillCurrencyOnCancelResponse(apiHotelCancelResponse, hor);
						hotelOrderRowCancellation = CommonUtil.insertOrUpdateHotelCancellation(hotelOrderDao, hor, hotelOrderRowCancellation, apiHotelCancelResponse, apiHotelCancelRequest.getMethodType());
						apiHotelCancelResponse.setStatus(apiHotelCancelResponse.getStatus());
					}
					else
					{
						status = new Status(Status.STATUS_CODE_ERROR, "Cancellation is not in progress..." );				
						apiHotelCancelResponse.setStatus(status);
					}

				}				
				else
				{
					status = new Status(Status.STATUS_CODE_ERROR, "No relevant cancellation request found..." );				
					apiHotelCancelResponse.setStatus(status);
				}

			}


		} catch (HibernateException e) {
			status = new Status(Status.STATUS_CODE_ERROR, "Error : "+e.getMessage() );
			apiHotelCancelResponse.setStatus(status);
			logger.error(e);
		} catch (Exception e) {
			status = new Status(Status.STATUS_CODE_ERROR, "Error : "+e.getMessage() );
			apiHotelCancelResponse.setStatus(status);
			logger.error(e);
		}		



		return apiHotelCancelResponse;

	}	





	@RequestMapping(value = "/request",method = RequestMethod.POST, headers = {"Accept=application/json"})
	public @ResponseBody APIHotelCancelRequest cancelrequest(HttpServletResponse response) {	
		APIHotelCancelRequest apiHotelCancelRequest = new APIHotelCancelRequest();
		apiHotelCancelRequest.setAppKey("Edff446343jk%%%dfdfdfdsd");
		apiHotelCancelRequest.setMethodType("0");
		apiHotelCancelRequest.setOrderId("HOR2323332");
		apiHotelCancelRequest.setUserId("1");
		apiHotelCancelRequest.setPassword("dsdsdsdsdsd");	
		return apiHotelCancelRequest;
	}


	@RequestMapping(value = "/test",method = RequestMethod.POST, headers = {"Accept=application/json"})
	public @ResponseBody APIHotelCancelResponse cancelHotelTest(@RequestBody APIHotelCancelRequest apiHotelCancelRequest, HttpServletRequest request, HttpServletResponse response) {	
		APIHotelCancelResponse apiHotelCancelResponse = new APIHotelCancelResponse();	
		Status status = new Status(Status.STATUS_CODE_CANCEL_NOT_SET, "Cancellation Requested ..." );
		APIStatus apiStatus = new APIStatus(APIStatus.STATUS_CODE_CANCEL_NOT_SET, "Cancellation to be Requested ..." );
		apiHotelCancelResponse.setStatus(status);
		try {
			apiHotelCancelRequest = validator.validate(apiHotelCancelRequest);

			UniqueIDType apicanceluniqueid = new UniqueIDType();
			apicanceluniqueid.setApiConfirmationNo("213232");
			apicanceluniqueid.setApiBookingCode("213232");
			apicanceluniqueid.setApiBookingId("213232");
			apicanceluniqueid.setID("213232");				
			apiHotelCancelResponse.setApiUniqueId(apicanceluniqueid);

			UniqueIDType canceluniqueid = new UniqueIDType();
			BeanUtils.copyProperties(apicanceluniqueid, canceluniqueid);
			apiHotelCancelResponse.setUniqueId(canceluniqueid);		

			CancelRuleType apiCancelRule = new CancelRuleType();
			apiCancelRule.setAmount(new BigDecimal(500));
			apiCancelRule.setRefundAmount(new BigDecimal(2000));
			apiCancelRule.setType("Amount");
			apiCancelRule.setCurrencyCode("INR");


			CancelRuleType cancelRule = new CancelRuleType();
			BeanUtils.copyProperties(apiCancelRule, cancelRule);
			apiHotelCancelResponse.setApiCancelRule(apiCancelRule);
			apiHotelCancelResponse.setCancelRule(cancelRule);


			apiStatus = new APIStatus(APIStatus.STATUS_CODE_CANCEL_PROCESSED, APIStatus.STATUS_MESSAGE_CANCEL_PROCESSED );
			status = new Status(Status.STATUS_CODE_CANCEL_SUCCESSFULL, Status.STATUS_MESSAGE_CANCEL_SUCCESSFULL );
			apiHotelCancelResponse.setStatus(status);
			apiHotelCancelResponse.setApiStatus(apiStatus);
		} catch (Exception e) {
			status = new Status(Status.STATUS_CODE_ERROR, "Error : "+e.getMessage() );
			apiHotelCancelResponse.setStatus(status);
			logger.error(e);
		}		

		return apiHotelCancelResponse;
	}







	@RequestMapping(value = "/initiate",method = RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody
	StringBuilder initiatecancelTest(@RequestParam(value="appkey") String appkey, HttpServletResponse response) {	
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			// TODO Error 
		}
		HotelApiCredentials apidesiya = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);		
		StringBuilder reqbook = new StringBuilder();
		try {
			reqbook = HotelBookRequestBuilder.getInitiateCancelReqBookPojo(apidesiya);
		} catch (NumberFormatException e) {
			reqbook.append("error :"+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			reqbook.append("error :"+e.getMessage());
			e.printStackTrace();
		}
		logger.info("initiatecancel---- :"+ reqbook);
		return reqbook;
	}
	@RequestMapping(value = "/confirm",method = RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody
	StringBuilder confirmcancelTest(@RequestParam(value="appkey") String appkey, HttpServletResponse response) {
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			// TODO Error 
		}
		HotelApiCredentials apidesiya = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);		
		StringBuilder reqbook = new StringBuilder();
		try {
			reqbook = HotelBookRequestBuilder.getConfirmCancelReqBookPojo(apidesiya);
		} catch (NumberFormatException e) {
			reqbook.append("error :"+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			reqbook.append("error :"+e.getMessage());
			e.printStackTrace();
		}
		logger.info("initiatecancel---- :"+ reqbook);
		return reqbook;
	}

}
