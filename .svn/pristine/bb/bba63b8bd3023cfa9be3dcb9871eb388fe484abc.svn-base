package com.tayyarah.hotel.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.admin.analytics.lookbook.dao.LookBookDao;
import com.tayyarah.admin.analytics.lookbook.entity.FetchIpAddress;
import com.tayyarah.admin.analytics.lookbook.entity.HotelBook;
import com.tayyarah.admin.analytics.lookbook.entity.HotelLookBook;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPHistory;
import com.tayyarah.admin.analytics.lookbook.entity.LookBookCustomerIPStatus;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.exception.CommonException;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.notification.NotificationUtil;
import com.tayyarah.common.notification.dao.NotificationDao;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.ApiResponseSaver;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.common.util.enums.InventoryTypeEnum;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.email.dao.EmailDaoImp;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.hotel.dao.HotelBookingDao;
import com.tayyarah.hotel.dao.HotelOrderDao;
import com.tayyarah.hotel.dao.HotelSearchRoomDetailDao;
import com.tayyarah.hotel.dao.HotelTransactionDao;
import com.tayyarah.hotel.entity.HotelBookingTemp;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.entity.HotelSearchRoomDetailTemp;
import com.tayyarah.hotel.entity.HotelTransactionTemp;
import com.tayyarah.hotel.entity.Islhotelmapping;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.HotelBookCommand;

import com.tayyarah.hotel.model.HotelSearchCommand;

import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelResRS;
import com.tayyarah.hotel.model.TotalType;
import com.tayyarah.hotel.model.UniqueIDType;
import com.tayyarah.hotel.util.BookService;
import com.tayyarah.hotel.util.HotelIdFactoryImpl;
import com.tayyarah.hotel.util.HotelObjectTransformer;
import com.tayyarah.hotel.util.api.concurrency.AsyncSupport;
import com.tayyarah.hotel.validator.HotelServiceEndPointValidator;
import com.tayyarah.user.dao.UserWalletDAO;


@RestController
@RequestMapping("/hotel/book")
public class HotelBookingController {
	@Autowired
	HotelObjectTransformer hotelObjectTransformer;
	@Autowired
	HotelTransactionDao hotelTransactionDao;

	@Autowired
	HotelOrderDao hotelOrderDao;
	@Autowired
	AsyncSupport asyncSupport;
	@Autowired
	FlightBookingDao FBDAO;
	@Autowired
	CompanyDao companyDao;
	@Autowired
	UserWalletDAO AWDAO;
	@Autowired
	HotelBookingDao hotelBookingDao;

	HotelIdFactoryImpl hotelIdFactory;	
	@Autowired
	EmailDaoImp emaildao;	

	@Autowired
	BookService hotelbookservice;	
	@Autowired
	HotelSearchRoomDetailDao hotelSearchRoomDetailDao;

	@Autowired
	NotificationDao NFDAO;

	@SuppressWarnings("rawtypes")
	@Autowired
	LookBookDao lookBookDao;

	public static final Logger logger = Logger.getLogger(HotelBookingController.class);	
	private  HotelServiceEndPointValidator validator = new HotelServiceEndPointValidator();

	@RequestMapping(value = "",method = RequestMethod.GET, headers = {"Accept=application/json"})
	public @ResponseBody APIHotelBook BookRooms(@RequestParam(value="appkey") String appkey, @RequestParam(value="refno") String refno,
			@RequestParam(value="payby", defaultValue="cash") String payby,
			@RequestParam(value="response_message", defaultValue="yet get") String response_message,@RequestParam(value="response_code", defaultValue="1") String response_code,@RequestParam(value="transaction_id") String transaction_id,@RequestParam(value="payment_status") String payment_status,@RequestParam(value="AuthCode") String AuthCode,HttpServletResponse response,HttpServletRequest request) 
	{
		//HotelBookCommand hbc = new HotelBookCommand(1);
		//ResponseHeader.setResponse(response);//Setting response header
		ResponseHeader.setPostResponse(response);//Setting response header
		logger.info("HotelBookingController----before appkey validation...:"+ appkey);
		validator.paymentValidator(refno,payment_status,transaction_id,AuthCode);
		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}

		hotelIdFactory = HotelIdFactoryImpl.getInstance();

		HotelBookingTemp hb = new HotelBookingTemp();		
		Islhotelmapping islhotelmapping= null;
		HotelTransactionTemp ht = new HotelTransactionTemp();
		HotelSearchCommand hsc = null;	
		HotelBookCommand hbc = null;
		HotelOrderRow hor = null;
		PaymentTransaction paymentTransaction = null;
		//List<PaymentTransaction> paymentTransactionList = null;
		int paymentAttemptedCount = 0;
		String orderid = null;
		OTAHotelResRS otaHotelResRS = new OTAHotelResRS();
		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();
		APIHotelBook apiHotelBook = new APIHotelBook();	

		UniqueIDType uniqueid = new UniqueIDType();
		try
		{	
			logger.info("HotelBookingController----relevent payment transaction before retrival: refno=="+ refno);			

			paymentTransaction = FBDAO.getPaymentTransactionDetail(refno);
			logger.info("HotelBookingController----relevent payment transaction:"+ paymentTransaction);			

			if(paymentTransaction == null)
			{
				APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR +" Try with valid payment ref");
				apiHotelBook.setStatus(status);
				return apiHotelBook;
			}
			paymentAttemptedCount = FBDAO.getPaymentTransactionsCount(orderid);
			logger.info("HotelBookingController----paymentAttemptedCount:"+ paymentAttemptedCount);			



			orderid = paymentTransaction.getApi_transaction_id();				

			hor = hotelOrderDao.getHotelOrderRow(orderid);
			logger.info("HotelBookingController----relevent hotel order from db: hor=="+ hor);			

			hb = hotelBookingDao.getHotelBooking(orderid);		
			logger.info("HotelBookingController----relevent hotel booking from db: hb=="+ hb);			

			OTAHotelAvailRS.RoomStays.RoomStay rs = (OTAHotelAvailRS.RoomStays.RoomStay) SerializationUtils.deserialize(hb.getRoomstay());
			logger.info("HotelBookingController----relevent room stay from db: hb=="+ rs);			

			totaHotelResRS = (OTAHotelResRS) SerializationUtils.deserialize(hb.getPrebook_res());
			logger.info("HotelBookingController----relevent prebook res from db: totaHotelResRS=="+ totaHotelResRS);			

			hbc = (HotelBookCommand) SerializationUtils.deserialize(hb.getHotelbook_cmd());		
			logger.info("HotelBookingController----relevent HotelBookCommand res from db: hbc=="+ hbc);			

			hbc.setPayBy(payby);

			//rs = hotelObjectTransformer.getRoomDetailsSummary(rs, hbc.getBookingCode());

			HotelSearchRoomDetailTemp hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(hbc.getSearchKey());


			hotelSearchRoomDetail =  hotelSearchRoomDetailDao.getHotelSearchRoomDetail(hotelSearchRoomDetail);
			if(hotelSearchRoomDetail == null || hotelSearchRoomDetail.getHotelsearch_cmd() == null)
				return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Room details Missing.. Search again.." )) ;

			hsc =  (HotelSearchCommand) SerializationUtils.deserialize(hotelSearchRoomDetail.getHotelsearch_cmd());

			logger.info("HotelBookingController----relevent HotelSearchCommand res from db: hsc=="+ hsc);			

			ht = hotelTransactionDao.getHotelTransaction(hbc.getSearchKey());			
			logger.info("HotelBookingController----relevent HotelTransaction res from db: ht=="+ ht);			

			apiHotelBook = new APIHotelBook(hsc, hbc, new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR), totaHotelResRS, BigInteger.valueOf(ht.getSearch_key()),  BigInteger.valueOf(ht.getId()), new TotalType(), rs ) ;
			logger.info("HotelBookingController----relevent APIHotelBook res from db: apiHotelBook=="+ apiHotelBook);			
			apiHotelBook.setStatus(totaHotelResRS.getStatus());

			uniqueid = CommonUtil.getApiUniqueId(totaHotelResRS);
			logger.info("HotelBookingController----relevent APIHotelBook res from db: apiHotelBook=="+ apiHotelBook);			

			logger.info("Booking call..  booking system type----:"+apiHotelBook.getBook().getBookingSystemType());			

			//logger.info("booking over..  before book call--11----uniqueid:"+uniqueid);
			//logger.info("booking over..  before book call--11--apiHotelBook:"+apiHotelBook);
			//logger.info("booking over..  before book call--11--apiHotelBook.getBookRes():"+apiHotelBook.getBookRes());
			logger.info("booking over..  before book call--11--apiHotelBook.getBookRes().getStatus():"+apiHotelBook.getBookRes().getStatus());
			//logger.info("booking over..  before book call--11--apiHotelBook.getBookRes().getStatus() msg:"+apiHotelBook.getBookRes().getStatus().getMessage());	

			//logger.info("booking over..  before book call--11--apiHotelBook.getPreBookRes():"+apiHotelBook.getPreBookRes());
			logger.info("booking over..  before book call--11--apiHotelBook.getPreBookRes().getStatus():"+apiHotelBook.getPreBookRes().getStatus());
			//logger.info("booking over..  before book call--11--apiHotelBook.getPreBookRes().getStatus() msg:"+apiHotelBook.getPreBookRes().getStatus().getMessage());		

			logger.info("booking over..  before book call--11--apiHotelBook.getStatus():"+apiHotelBook.getStatus());
			//logger.info("booking over..  before book call--11--apiHotelBook.getStatus() status msg:"+apiHotelBook.getStatus().getMessage());

			//if(uniqueid != null && uniqueid.getID().length()>0)
			if((totaHotelResRS.getStatus().getCode().equals(APIStatus.STATUS_CODE_SUCCESS)))
			{

				if(apiHotelBook.getBook().getBookingSystemType().equalsIgnoreCase("api") && payby.equalsIgnoreCase("cash"))
				{

					int noofdays = CommonUtil.getNoofStayDays(apiHotelBook.getSearch());
					noofdays = CommonUtil.getNoofStayDays(hsc);
					int noofrooms = 1;
					noofrooms = apiHotelBook.getSearch().getNoofrooms();
					apiHotelBook.initRate(rs, noofdays,noofrooms);
					//apiHotelBook.setStatus(totaHotelResRS.getStatus());
					apiHotelBook = hotelbookservice.payWalletBooking(totaHotelResRS, ht, hb, hor, paymentTransaction, uniqueid, apiHotelBook, rs,appKeyVo);	
					logger.info("HotelPreBookController----payWalletBooking uniqueid:"+uniqueid);	



					/*	paymentTransaction.setIsPaymentSuccess(true);
					paymentTransaction.setTransactionId(transaction_id);
					paymentTransaction.setResponse_message(response_message);
					paymentTransaction.setResponseCode(response_code);
					paymentTransaction.setPayment_status("SUCCESS");
					paymentTransaction.setRefno(refno);
					paymentTransaction.setAuthorizationCode(AuthCode);
					updatePaymentTransaction(paymentTransaction);	
					logger.info("HotelBookingController----payment success updated:");					
					logger.info("HotelBookingController----before booking call:");			
					logger.info("HotelBookingController----before booking call rs:"+rs);			


					apiHotelBook = hotelbookservice.callAPIBook(totaHotelResRS,ht, hb, hor, uniqueid, apiHotelBook, rs);*/
					try{
						insertIntoHotelBook(appkey,String.valueOf(apiHotelBook.getTransactionKey()),request,String.valueOf(apiHotelBook.getSearchKey()), companyDao);
					}catch (Exception e) {
					}

					return apiHotelBook;	
				}
				else
				{
					if(payment_status.equalsIgnoreCase("1")){			

						paymentTransaction.setIsPaymentSuccess(true);
						paymentTransaction.setTransactionId(transaction_id);
						paymentTransaction.setResponse_message(response_message);
						paymentTransaction.setResponseCode(response_code);
						paymentTransaction.setPayment_status("SUCCESS");
						paymentTransaction.setRefno(refno);
						paymentTransaction.setAuthorizationCode(AuthCode);
						updatePaymentTransaction(paymentTransaction);	
						logger.info("HotelBookingController----payment success updated:");					
						logger.info("HotelBookingController----before booking call:");			
						logger.info("HotelBookingController----before booking call rs:"+rs);			

						int noofdays = CommonUtil.getNoofStayDays(apiHotelBook.getSearch());
						noofdays = CommonUtil.getNoofStayDays(hsc);
						int noofrooms = 1;
						noofrooms = apiHotelBook.getSearch().getNoofrooms();
						apiHotelBook.initRate(rs, noofdays,noofrooms);
						apiHotelBook = hotelbookservice.callAPIBook(totaHotelResRS,ht, hb, hor, uniqueid, apiHotelBook, rs,appKeyVo);
						// insert notication after booking is successful
						if(totaHotelResRS.getStatus().getCode() == APIStatus.STATUS_CODE_SUCCESS){							
							new NotificationUtil().insertNotification(appKeyVo,orderid , "Hotel Booking", InventoryTypeEnum.HOTEL_ORDER.getId(), true,NFDAO,companyDao); 
						}
						try{
							insertIntoHotelBook(appkey,String.valueOf(apiHotelBook.getTransactionKey()),request,String.valueOf(apiHotelBook.getSearchKey()), companyDao);
						}catch (Exception e) {
						}
						return apiHotelBook;				

					}else{

						paymentTransaction.setIsPaymentSuccess(false);
						paymentTransaction.setTransactionId(transaction_id);
						paymentTransaction.setResponse_message(response_message);
						paymentTransaction.setResponseCode(response_code);
						paymentTransaction.setPayment_status("FAILED");
						paymentTransaction.setRefno(refno);
						paymentTransaction.setAuthorizationCode(AuthCode);
						updatePaymentTransaction(paymentTransaction);
						logger.info("HotelBookingController----payment failed updated:");			

						String paymentidNew = hotelIdFactory.createLongId("PGH");
						PaymentTransaction paymentTransactionNew=new PaymentTransaction();
						paymentTransactionNew.setAmount(AmountRoundingModeUtil.roundingModeForHotel(paymentTransaction.getAmount()));
						paymentTransactionNew.setCurrency(paymentTransaction.getCurrency());
						paymentTransactionNew.setRefno(paymentidNew);
						paymentTransactionNew.setIsPaymentSuccess(false);
						paymentTransactionNew.setCreatedAt(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
						paymentTransactionNew.setPayment_method(apiHotelBook.getBook().getPayBy());	
						paymentTransactionNew.setApi_transaction_id(paymentTransaction.getApi_transaction_id());
						paymentTransactionNew.setPayment_status("PENDING");		
						paymentTransactionNew.setTransactionId(paymentTransaction.getTransactionId());
						paymentTransactionNew.setResponse_message("NA");
						paymentTransactionNew.setResponseCode("NA");
						paymentTransactionNew.setAuthorizationCode(paymentTransaction.getTransactionId());
						logger.info("HotelBookingController----new payment transaction created.. before inserting db..");			

						paymentTransactionNew = FBDAO.insertPaymentTransactionDetail(paymentTransactionNew);

						//updatePaymentTransaction(paymentTransactionNew);
						logger.info("HotelBookingController----created and inserted new payment feed--:paymentTransactionNew="+paymentTransactionNew);			

						///hor = CommonUtil.hotelOrderUpdationData(hotelOrderDao, hor, ht, hs, apiHotelBook ,"success", "success", "Booked");					

						APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " Try Payment process with the new refno");
						apiHotelBook.setStatus(status);

						hbc.setPayAttemptCount(paymentAttemptedCount);
						hbc.setPaymentid(paymentidNew);
						List<String> paymentIdHistory = new ArrayList<String>();
						paymentIdHistory.add(refno);
						hbc.setPaymentIdHistory(paymentIdHistory);
						logger.info("HotelBookingController----Hotel book response .. try again with new payment id="+paymentTransactionNew);
						apiHotelBook.setBook(hbc);
					}
				}


			}
			else
			{
				APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " No Valid PreBooking is done for the given ref no ");
				apiHotelBook.setStatus(status);
				logger.info("HotelBookingController----no valid reservation is done for the given payment ref no..=="+ apiHotelBook);			

				return apiHotelBook;
			}


			return apiHotelBook;
		} 
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}		
		catch (ClassNotFoundException e) {
			logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (JAXBException e) {
			logger.info("query Exception:JAXBException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (UnsupportedOperationException e) {
			logger.info("query Exception:UnsupportedOperationException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (SOAPException e) {
			logger.info("query Exception:SOAPException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (Exception e) {
			logger.info("query Exception:Exception:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}		

	}


	@RequestMapping(value = "/summary",method = RequestMethod.GET, headers = {"Accept=application/json"})
	public @ResponseBody APIHotelBook BookRoomsSummary(@RequestParam(value="appkey") String appkey, @RequestParam(value="refno") String refno, @RequestParam(value="refno_old") String refno_old,
			HttpServletResponse response) 
	{		
		hotelIdFactory = HotelIdFactoryImpl.getInstance();

		//HotelBookCommand hbc = new HotelBookCommand(1);
		//ResponseHeader.setResponse(response);//Setting response header
		ResponseHeader.setPostResponse(response);//Setting response header
		logger.info("HotelBookingController----before appkey validation...:"+ appkey);			

		AppKeyVo appKeyVo = AppControllerUtil.getDecryptedAppKeyObject(companyDao, appkey);
		if(appKeyVo==null)
		{
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR_APPKEY, APIStatus.STATUS_CODE_ERROR_APPKEY));
		}

		HotelSearchRoomDetailTemp hotelSearchRoomDetail = new HotelSearchRoomDetailTemp();
		HotelBookingTemp hb = new HotelBookingTemp();		
		HotelTransactionTemp ht = new HotelTransactionTemp();
		HotelSearchCommand hsc = null;	
		HotelBookCommand hbc = null;
		HotelOrderRow hor = null;
		PaymentTransaction paymentTransaction = null;
		//List<PaymentTransaction> paymentTransactionList = null;
		int paymentAttemptedCount = 0;
		String orderid = null;
		OTAHotelResRS totaHotelResRS = new OTAHotelResRS();
		APIHotelBook apiHotelBook = new APIHotelBook();
		UniqueIDType uniqueid = new UniqueIDType();
		try
		{	
			logger.info("HotelBookingController----relevent payment transaction before retrival: refno_old=="+ refno_old);	
			paymentTransaction = FBDAO.getPaymentTransactionDetail(refno_old);
			logger.info("HotelBookingController----relevent payment transaction:"+ paymentTransaction);	
			if(paymentTransaction == null)
			{
				APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR +" Try with valid payment ref");
				apiHotelBook.setStatus(status);
				return apiHotelBook;
			}
			paymentAttemptedCount = FBDAO.getPaymentTransactionsCount(orderid);
			logger.info("HotelBookingController----paymentAttemptedCount:"+ paymentAttemptedCount);	
			orderid = paymentTransaction.getApi_transaction_id();
			hor = hotelOrderDao.getHotelOrderRow(orderid);
			logger.info("HotelBookingController----relevent hotel order from db: hor=="+ hor);			

			hb = hotelBookingDao.getHotelBooking(orderid);		
			logger.info("HotelBookingController----relevent hotel booking from db: hb=="+ hb);			

			OTAHotelAvailRS.RoomStays.RoomStay rs = (OTAHotelAvailRS.RoomStays.RoomStay) SerializationUtils.deserialize(hb.getRoomstay());
			logger.info("HotelBookingController----relevent room stay from db: hb=="+ rs);			

			totaHotelResRS = (OTAHotelResRS) SerializationUtils.deserialize(hb.getPrebook_res());
			logger.info("HotelBookingController----relevent prebook res from db: totaHotelResRS=="+ totaHotelResRS);			

			hbc = (HotelBookCommand) SerializationUtils.deserialize(hb.getHotelbook_cmd());		
			logger.info("HotelBookingController----relevent HotelBookCommand res from db: hbc=="+ hbc);			

			hotelSearchRoomDetail.setSearch_key(hbc.getSearchKey());


			hotelSearchRoomDetail =  hotelSearchRoomDetailDao.getHotelSearchRoomDetail(hotelSearchRoomDetail);
			if(hotelSearchRoomDetail == null || hotelSearchRoomDetail.getHotelsearch_cmd() == null)
				return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Room details Missing.. Search again.." )) ;

			hsc =  (HotelSearchCommand) SerializationUtils.deserialize(hotelSearchRoomDetail.getHotelsearch_cmd());
			logger.info("HotelBookingController----relevent HotelSearchCommand res from db: hsc=="+ hsc);			

			ht = hotelTransactionDao.getHotelTransaction(hbc.getSearchKey());			
			logger.info("HotelBookingController----relevent HotelTransaction res from db: ht=="+ ht);			

			apiHotelBook = new APIHotelBook(hsc, hbc, new APIStatus(APIStatus.STATUS_CODE_SUCCESS, APIStatus.STATUS_MESSAGE_SUCCESS), totaHotelResRS, BigInteger.valueOf(ht.getSearch_key()),  BigInteger.valueOf(ht.getId()), new TotalType(), rs ) ;
			logger.info("HotelBookingController----relevent APIHotelBook res from db: apiHotelBook=="+ apiHotelBook);			

			uniqueid = CommonUtil.getApiUniqueId(totaHotelResRS);
			if(uniqueid == null || uniqueid.getID() == null || uniqueid.getID().length()<=0)
			{
				APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " No Valid PreBooking is done for the given ref no ");
				apiHotelBook.setStatus(status);
				logger.info("HotelBookingController----no valid reservation is done for the given payment ref no..=="+ apiHotelBook);			

				return apiHotelBook;
			}

			///hor = CommonUtil.hotelOrderUpdationData(hotelOrderDao, hor, ht, hs, apiHotelBook ,"success", "success", "Booked");					

			APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " Try Payment process with the new refno");
			apiHotelBook.setStatus(status);

			hbc.setPayAttemptCount(paymentAttemptedCount);
			hbc.setPaymentid(refno);
			List<String> paymentIdHistory = new ArrayList<String>();
			paymentIdHistory.add(refno_old);
			hbc.setPaymentIdHistory(paymentIdHistory);
			logger.info("HotelBookingController----Hotel book response .. try again with new payment id="+refno);			


			apiHotelBook.setBook(hbc);

			// Insert Hotel Api Response in the flight_hotel_book_api_response table
			ApiResponseSaver.saveHotelApiResponse(apiHotelBook,hor,FBDAO);


			return apiHotelBook;
		} 
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}		
		catch (ClassNotFoundException e) {
			logger.info("query Exception:ClassNotFoundException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (JAXBException e) {
			logger.info("query Exception:JAXBException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}
		catch (UnsupportedOperationException e) {
			logger.info("query Exception:UnsupportedOperationException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (SOAPException e) {
			logger.info("query Exception:SOAPException:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		} catch (Exception e) {
			logger.info("query Exception:Exception:"+ e.getMessage());
			return new APIHotelBook(new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + e.getMessage() )) ;

		}		

	}


	/*public APIHotelBook callAPIBookUnused(com.lintas.hotel.model.OTAHotelResRS totaHotelResRSPrebook, HotelTransaction ht, HotelSearch hs, HotelBooking hb, HotelOrderRow hor, com.lintas.hotel.model.UniqueIDType uniqueid, APIHotelBook apiHotelBook, com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs) throws Exception
	{
		HotelApiCredentials apiauth = null;

		OTAHotelResRS otaHotelResRS = new OTAHotelResRS();
		com.lintas.hotel.model.OTAHotelResRS totaHotelResRS = new com.lintas.hotel.model.OTAHotelResRS();

		List<com.lintas.hotel.model.RoomStayType.RoomRates.RoomRate> roomrates = hotelObjectTransformer.getRoomRatesBooking(rs, apiHotelBook.getBook().getRoomRateTypes());	
		logger.info("HotelPreBookController----roomrates to be booked--:"+ roomrates.size());


		int noofdays = CommonUtil.getNoofStayDays(apiHotelBook.getSearch());
		apiHotelBook.initRate(roomrates,  noofdays);


		switch (rs.getBasicPropertyInfo().getApiProvider()) {
		case HotelApiCredentials.API_DESIA_IND:
			apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND);						
			StringBuilder reqbook = HotelBookRequestBuilder.getFinalBookingReq(apiauth, ht, hs, apiHotelBook.getSearch(), apiHotelBook.getBook(), rs, uniqueid);
			logger.info("HotelBookingController---- reqbook:"+ reqbook);
			String reqbookxml = CommonUtil.format(reqbook.toString());
			logger.info("HotelBookingController---- reqbookxml:"+ reqbookxml);					
			reqbookxml = reqbookxml.replace(HotelBookRequestBuilder.XML_HEADER_FORMATTED, HotelBookRequestBuilder.SOAP_HEADER_PREBOOK_TG);
			reqbookxml+=HotelBookRequestBuilder.SOAP_FOOTER_PREBOOK_TG;
			StringBuilder sdb = new StringBuilder(reqbookxml);
			logger.info("HotelBookingController---- soap reqbookxml:"+ sdb.toString());					

			ByteArrayInputStream is = new ByteArrayInputStream(sdb.toString().getBytes());
			MimeHeaders header=new MimeHeaders();
			header.addHeader("Content-Type","application/soap+xml");
			SOAPMessage sm = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL).createMessage(header, is);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			sm.writeTo(baos);  
			logger.info("request soap message--request-"+baos);				
			otaHotelResRS = DesiyaPullerTask.finalBookingHotelDesia("http://stage-api.travelguru.com/services-2.0/tg-services/TGBookingServiceEndPoint", sm);
			logger.info("HotelBookingController---- otaHotelResRS:"+ otaHotelResRS);
			totaHotelResRS = hotelObjectTransformer.convertTGtoNativeFinalBookResponse(otaHotelResRS);
			apiHotelBook.setBookRes(totaHotelResRS);


			break;
		case HotelApiCredentials.API_REZNEXT_IND:

			List<String> roomTypeCodeList = CommonUtil.getRoomTypeCodeList(apiHotelBook.getBook().getRoomRateTypes());
			logger.info("Searhing roomdetails summary controller : roomtypecodes=="+roomTypeCodeList);			
			logger.info("Searhing roomdetails summary controller : no of roomtypecodes in list=="+roomTypeCodeList.size());					

			rs = hotelObjectTransformer.getReztRoomDetailsSummary(rs,roomTypeCodeList);
			//com.lintas.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = hotelObjectTransformer.convertTGtoNative(apiHotelMap.get(hotelcode));
			apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND);
			//int noofdays = CommonUtil.getNoofStayDays(apiHotelBook.getSearch());
			logger.info("HotelPreBookController----noofdays---:"+ noofdays);					



			reqbook = new RezNextRequestBuilder().getBookingReq(apiauth, ht, hs, apiHotelBook.getSearch(), apiHotelBook.getBook(), rs, apiHotelBook.getRateWithoutMarkUp(), hor, hotelIdFactory);
			//logger.info("HotelPreBookController---- reqbook:"+ reqbook);
			reqbookxml = CommonUtil.format(reqbook.toString());
			logger.info("HotelPreBookController---- reqbookxml:"+ reqbookxml);				

			totaHotelResRS = RezNextPullerTask.getModificationBookingRes(apiauth, hotelObjectTransformer, reqbook, apiHotelBook);				
			apiHotelBook.setBookRes(totaHotelResRS);



			break;
		case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
			apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL);
			RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(apiauth, apiHotelBook.getSearch(), "RazLive Api");

			rs = rezLivePullerTask.getRoomDetailSummaryBooking(rs, apiHotelBook.getBook());						
			noofdays = CommonUtil.getNoofStayDays(apiHotelBook.getSearch());
			//apiHotelBook.initRate(roomrates,  noofdays);

			logger.info("HotelPreBookController----rate calculation been over:"+ apiHotelBook.getRate().toString());	
			apiHotelBook = rezLivePullerTask.book(apiHotelBook, apiHotelBook.getBook(), totaHotelResRSPrebook, rs, hor, hotelIdFactory);					
			totaHotelResRS = apiHotelBook.getBookRes();	


		default:
			break;
		}	


		if(totaHotelResRS.getStatus() != null)
		{
			emaildao.insertEmail(apiHotelBook.getBook().getOrderid(), 0, Email.EMAIL_TYPE_HOTEL_INVOICE);

			if(uniqueid != null && apiHotelBook.getBook().getPayBy().equals("cash"))
			{
				//apiHotelBook.setStatus(totaHotelResRS.getStatus());
				//apiHotelBook = payWalletBooking(ht, hs, hb, hor, uniqueid, apiHotelBook, rs);	
				logger.info("HotelPreBookController----Booking over successfull uniqueid:"+uniqueid);			
				hor = CommonUtil.hotelOrderUpdationData(hotelOrderDao, hor, ht, hs, apiHotelBook, "Booked", "Paid", "Booked");	
				APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Booking succesful." );
				apiHotelBook.setStatus(status);
				//apiHotelBook = callAPIBook(ht, hs, hb, hor, uniqueid, apiHotelBook, rs);
			}
			else if(uniqueid == null && apiHotelBook.getBook().getPayBy().equals("cash"))
			{
				hor = CommonUtil.hotelOrderUpdationData(hotelOrderDao, hor, ht, hs, apiHotelBook, "Booking Failed", "Paid", "Booking");			
				APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Something wong with input details... Booking failed." );
				apiHotelBook.setStatus(status);
			}

		}
		else
		{
			hor = CommonUtil.hotelOrderUpdationData(hotelOrderDao, hor, ht, hs, apiHotelBook, "Booking Failed", "Paid", "Booking");				
			APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Something wong with input details...Booking failed." );
			apiHotelBook.setStatus(status);
		}


		return apiHotelBook;
	}
	 */
	public HotelBook insertIntoHotelBook(String appkey, String transactionKey,HttpServletRequest request,String searchKey,CompanyDao companyDao){
		HotelBook hotelBook=new HotelBook(); 
		HotelLookBook hotelLookBook=new HotelLookBook(); 
		LookBookCustomerIPStatus ipStatus=new LookBookCustomerIPStatus();
		LookBookCustomerIPHistory ipStatusHistory=new LookBookCustomerIPHistory();
		Timestamp currentDate=new Timestamp(new Date().getTime());
		String ip=null;
		try{
			ip=FetchIpAddress.getClientIpAddress(request);
			hotelBook.setAppkey(appkey);
			String NewAPP_Key = AppControllerUtil.getDecryptedAppKey(companyDao,appkey);
			String companyId = "-1";
			String configId = "-1";
			configId = NewAPP_Key.substring(0, NewAPP_Key.indexOf("-"));
			companyId = NewAPP_Key.substring(NewAPP_Key.indexOf("-") + 1);
			Company company = companyDao.getCompany(Integer.valueOf(companyId));

			hotelBook.setIP(ip);
			hotelBook.setSearchKey("searchKey");
			hotelBook.setSearchOnDateTime(currentDate);
			hotelBook.setTransactionKey(transactionKey);
			hotelBook.setCompanyId(Integer.valueOf(companyId));
			hotelBook.setConfigId(Integer.valueOf(configId));
			hotelBook.setCompanyName(company.getCompanyname());
			lookBookDao.insertIntoTable(hotelBook);
			hotelLookBook.setAppkey(appkey);
			hotelLookBook=lookBookDao.CheckAndFetchHotelLookBookByAppKey(hotelLookBook);

			if(hotelLookBook!=null && hotelLookBook.getId()>0){
				lookBookDao.updateIntoHotelTable(hotelLookBook, "booking");
			}
			else{
				hotelLookBook.setAppkey(appkey);
				hotelLookBook.setTotalBookedCount(1);
				hotelLookBook.setTotalSearchCount(0);
				hotelLookBook.setCompanyId(Integer.valueOf(companyId));
				hotelLookBook.setConfigId(Integer.valueOf(configId));
				hotelLookBook.setCompanyName(company.getCompanyname());
				lookBookDao.insertIntoTable(hotelLookBook);
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
				throw new CommonException(ErrorCodeCustomerEnum.LimitExceedException,ErrorMessages.USEREXCEEDSSEARCHLIMIT); 
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
		return hotelBook;
	}
	public APIHotelBook booking(OTAHotelResRS totaHotelResRSPrebook, HotelTransactionTemp ht, HotelBookingTemp hb, HotelOrderRow hor, UniqueIDType uniqueid, APIHotelBook apiHotelBook, OTAHotelAvailRS.RoomStays.RoomStay rs,AppKeyVo appKeyVo) throws Exception
	{		
		apiHotelBook = hotelbookservice.callAPIBook(totaHotelResRSPrebook, ht, hb, hor, uniqueid, apiHotelBook, rs,appKeyVo);	
		logger.info("HotelPreBookController----hor updated:"+ hor.getId());						
		//apiHotelBook = hotelObjectTransformer.getRoomDetailsSummaryBooking(apiHotelBook, rs);
		//payBooking(apiHotelBook, rs);
		byte[] book_resdata = SerializationUtils.serialize(apiHotelBook.getBookRes());
		hb.setBook_res(book_resdata);
		hb = hotelBookingDao.insertHotelBooking(hb);
		logger.info("HotelBookingController----transfored otaHotelResRS:"+ apiHotelBook.getBookRes()); 
		return apiHotelBook; 
	}


	public APIHotelBook bookingRezlive(OTAHotelResRS totaHotelResRSPrebook, HotelTransactionTemp ht, HotelBookingTemp hb, HotelOrderRow hor, UniqueIDType uniqueid, APIHotelBook apiHotelBook, OTAHotelAvailRS.RoomStays.RoomStay rs,AppKeyVo appKeyVo) throws Exception
	{		
		apiHotelBook = hotelbookservice.callAPIBook(totaHotelResRSPrebook, ht, hb, hor, uniqueid, apiHotelBook, rs,appKeyVo);	
		logger.info("HotelPreBookController----hor updated:"+ hor.getId());						
		//apiHotelBook = hotelObjectTransformer.getRoomDetailsSummaryBooking(apiHotelBook, rs);
		//payBooking(apiHotelBook, rs);
		byte[] book_resdata = SerializationUtils.serialize(apiHotelBook.getBookRes());
		hb.setBook_res(book_resdata);
		hb = hotelBookingDao.insertHotelBooking(hb);
		logger.info("HotelBookingController----transfored otaHotelResRS:"+ apiHotelBook.getBookRes());				


		return apiHotelBook;

	}

	public void updatePaymentTransaction(PaymentTransaction paymentTransaction) throws Exception {

		FlightDataBaseServices DBS = new FlightDataBaseServices();		
		DBS.insertPaymentTransaction(paymentTransaction, FBDAO);		
	}

}
