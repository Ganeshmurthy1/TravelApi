package com.tayyarah.hotel.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.common.util.CutandPayModel;
import com.tayyarah.common.util.RandomConfigurationNumber;
import com.tayyarah.common.util.enums.CommonBookingStatusEnum;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.dao.AllEmailDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.email.dao.EmailDaoImp;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.service.db.FlightDataBaseServices;
import com.tayyarah.hotel.dao.HotelBookingDao;
import com.tayyarah.hotel.dao.HotelOrderDao;
import com.tayyarah.hotel.dao.HotelSearchDao;
import com.tayyarah.hotel.dao.HotelTransactionDao;
import com.tayyarah.hotel.entity.HotelBookingTemp;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.entity.HotelTransactionTemp;
import com.tayyarah.hotel.model.APIHotelBook;
import com.tayyarah.hotel.model.APIStatus;
import com.tayyarah.hotel.model.HotelBookCommand;
import com.tayyarah.hotel.model.HotelBookCommand.Profile;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.model.OTAHotelResRS;
import com.tayyarah.hotel.model.UniqueIDType;
import com.tayyarah.hotel.util.api.concurrency.AsyncSupport;
import com.tayyarah.hotel.util.api.concurrency.DesiyaPullerTask;
import com.tayyarah.hotel.util.api.concurrency.LintasHotelRepositPullerTask;
import com.tayyarah.hotel.util.api.concurrency.RezLivePullerTask;
import com.tayyarah.hotel.util.api.concurrency.RezNextPullerTask;
import com.tayyarah.hotel.util.api.concurrency.TBOPullerTask;
import com.tayyarah.hotel.util.api.concurrency.TayyarahPullerTask;
import com.tayyarah.user.dao.UserWalletDAO;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.entity.WalletAmountTranferHistory;



public class BookService {

	@Autowired
	HotelObjectTransformer hotelObjectTransformer;
	@Autowired
	HotelTransactionDao hotelTransactionDao;
	@Autowired
	HotelSearchDao hotelSearchDao;
	HotelOrderDao hotelOrderDao;
	@Autowired
	AsyncSupport asyncSupport;
	@Autowired
	FlightBookingDao FBDAO;
	@Autowired
	CompanyDao CDAO;
	@Autowired
	UserWalletDAO AWDAO;
	@Autowired
	HotelBookingDao hotelBookingDao;
	@Autowired
	AllEmailDao allEmailDao;
	@Autowired
	EmailDao emailDao;
	@Autowired
	CompanyConfigDAO companyConfigDAO;
	@Autowired
	CurrencyManager currencyManager;	
	@Autowired
	EmailDaoImp emaildao;

	public static final Logger logger = Logger.getLogger(BookService.class);


	public APIHotelBook callAPIBook(OTAHotelResRS totaHotelResRS, HotelTransactionTemp ht, HotelBookingTemp hb, HotelOrderRow hor, UniqueIDType uniqueid, APIHotelBook apiHotelBook, OTAHotelAvailRS.RoomStays.RoomStay rs, AppKeyVo appKeyVo) throws Exception
	{
		HotelIdFactoryImpl hotelIdFactory = HotelIdFactoryImpl.getInstance();
		HotelApiCredentials apiauth = null;
		OTAHotelResRS totaHotelResRSBook = new OTAHotelResRS();
		logger.info("HotelBookController----rs--"+ rs);
		logger.info("HotelBookController----rs.getBasicPropertyInfo()--"+ rs.getBasicPropertyInfo());
		logger.info("HotelBookController----rs.getBasicPropertyInfo()--"+ rs.getBasicPropertyInfo().getApiProvider());
		logger.info("HotelBookController----rs.getBasicPropertyInfo()--"+ rs.getBasicPropertyInfo().getApiVendorID());
		logger.info("HotelBookController----rs.getBasicPropertyInfo()--"+ rs.getBasicPropertyInfo().getHotelCode());

		Profile leaderprofile = apiHotelBook.getBook().getProfiles().get(0);
		String emailLeadPassenger = "";
		try{
			for (Profile p : apiHotelBook.getBook().getProfiles()) {
				if(p.getProfileType().equals("1"))
				{
					leaderprofile = p;
					emailLeadPassenger = leaderprofile.getCustomer().getEmail();
					break;
				}
			}
		}
		catch(Exception ex)
		{

		}
		switch (rs.getBasicPropertyInfo().getApiProvider()) {
		case HotelApiCredentials.API_REZLIVE_INTERNATIONAL:
			apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZLIVE_INTERNATIONAL,appKeyVo);
			RezLivePullerTask rezLivePullerTask = new RezLivePullerTask(hotelObjectTransformer, null, null, null, null, null, null, null , apiauth, apiHotelBook.getSearch(), "RazLive Api");
			logger.info("HotelBookController----rate calculation been over:"+ apiHotelBook.getBookingRate().toString());
			apiHotelBook = rezLivePullerTask.book(apiHotelBook, apiHotelBook.getBook(), totaHotelResRS, rs, hor, hotelIdFactory);
			totaHotelResRSBook = apiHotelBook.getBookRes();
			break;
		case HotelApiCredentials.API_DESIA_IND:
			apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_DESIA_IND,appKeyVo);

			if(!apiauth.isTesting() && CommonConfig.GetCommonConfig().isIs_dev_mode())
			{
				if(!emailLeadPassenger.equalsIgnoreCase("srikanth@tayyarah.com"))
					throw new Exception("Live Booking is not allowed in dev mode");
				//else continue booking
			}
			StringBuilder reqbook = HotelBookRequestBuilder.getFinalBookingReq(apiauth, ht, apiHotelBook.getSearch(), apiHotelBook.getBook(), rs, uniqueid);
			logger.info("HotelBookingController---- reqbook:"+ reqbook);
			String reqbookxml = CommonUtil.format(reqbook.toString());
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
			DesiyaPullerTask desiyaPullerTask = new DesiyaPullerTask(null, hotelObjectTransformer, null, null, null, null, null, null, null , apiauth, apiHotelBook.getSearch(), "Desiya Api", null);
			apiHotelBook = desiyaPullerTask.finalBookingHotelDesia(apiHotelBook, apiauth.getEndPointUrl()+"TGBookingServiceEndPoint", sm);
			totaHotelResRSBook = apiHotelBook.getBookRes();
			break;
		case HotelApiCredentials.API_REZNEXT_IND:
			apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_REZNEXT_IND,appKeyVo);
			reqbook = new RezNextRequestBuilder().getBookingReq(apiauth, ht, apiHotelBook.getSearch(), apiHotelBook.getBook(), rs, apiHotelBook.getApiRate(), hor, hotelIdFactory);
			logger.info("HotelPreBookController---- reqbook:"+ reqbook);
			reqbookxml = CommonUtil.format(reqbook.toString());
			RezNextPullerTask rezNextPullerTask = new RezNextPullerTask(hotelObjectTransformer, null, null, null, null, null, null, null , apiauth, apiHotelBook.getSearch(), "Desiya Api");
			apiHotelBook = rezNextPullerTask.getModificationBookingRes(apiHotelBook, apiauth, hotelObjectTransformer, reqbook);
			totaHotelResRSBook = apiHotelBook.getBookRes();
			break;
		case HotelApiCredentials.API_TAYYARAH_INTERNATIONAL:
			apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL,appKeyVo);
			TayyarahPullerTask tayyarahPullerTask = new TayyarahPullerTask(apiHotelBook.getSearchKey(), apiauth, apiHotelBook.getSearch(), "Tayyarah Api");
			apiHotelBook = tayyarahPullerTask.doBook(apiHotelBook);
			totaHotelResRSBook = apiHotelBook.getBookRes();
			break;
		case HotelApiCredentials.API_LINTAS_REPOSITORY:
			apiauth = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TAYYARAH_INTERNATIONAL,appKeyVo);
			LintasHotelRepositPullerTask lintasHotelRepositPullerTask = new LintasHotelRepositPullerTask(apiauth, apiHotelBook.getSearch(), apiauth.getApiProviderName());
			apiHotelBook = lintasHotelRepositPullerTask.doBook(apiHotelBook);
			totaHotelResRSBook = apiHotelBook.getBookRes();
			break;
		case HotelApiCredentials.API_LINTAS_INTERNATIONAL:
			break;
		case HotelApiCredentials.API_TBO_INTERNATIONAL:

			HotelApiCredentials apitbo = HotelApiCredentials.getApiCredentials(HotelApiCredentials.API_TBO_INTERNATIONAL,appKeyVo);
			if(!apitbo.isTesting() && CommonConfig.GetCommonConfig().isIs_dev_mode())
			{
				if(!emailLeadPassenger.equalsIgnoreCase("srikanth@tayyarah.com"))
					throw new Exception("Live Booking is not allowed in dev mode");
				//else continue booking
			}
			TBOPullerTask tboPullerTask = new TBOPullerTask(null, hotelObjectTransformer, null, null, null, null, null, null, null , apitbo, apiHotelBook.getSearch(), "TBO Api", null);
			apiHotelBook = tboPullerTask.doBook(apiHotelBook, apiHotelBook.getBook(), apiHotelBook.getRoomsummary(), hotelIdFactory,CDAO);
			totaHotelResRSBook = apiHotelBook.getBookRes();
			break;
		default:
			break;

		}

		if((apiHotelBook.getBookRes().getStatus().getCode().equals(APIStatus.STATUS_CODE_SUCCESS)))
			apiHotelBook = currencyManager.fillCurrencyOnBookResponse(apiHotelBook,hor);

		totaHotelResRSBook = apiHotelBook.getBookRes();



		logger.info("booking over..  before book call--11----uniqueid:"+uniqueid);
		logger.info("booking over..  before book call--11--apiHotelBook:"+apiHotelBook);
		logger.info("booking over..  before book call--11--apiHotelBook.getBookRes():"+apiHotelBook.getBookRes());
		//logger.info("booking over..  before book call--11--apiHotelBook.getBookRes().getStatus():"+apiHotelBook.getBookRes().getStatus());
		logger.info("booking over..  before book call--11--apiHotelBook.getBookRes().getStatus() msg:"+apiHotelBook.getBookRes().getStatus().getMessage());

		logger.info("booking over..  before book call--11--apiHotelBook.getPreBookRes():"+apiHotelBook.getPreBookRes());
		//logger.info("booking over..  before book call--11--apiHotelBook.getPreBookRes().getStatus():"+apiHotelBook.getPreBookRes().getStatus());
		logger.info("booking over..  before book call--11--apiHotelBook.getPreBookRes().getStatus() msg:"+apiHotelBook.getPreBookRes().getStatus().getMessage());

		//logger.info("booking over..  before book call--11--apiHotelBook.getStatus():"+apiHotelBook.getStatus());
		logger.info("booking over..  before book call--11--apiHotelBook.getStatus() status msg:"+apiHotelBook.getStatus().getMessage());


		if(totaHotelResRSBook.getStatus() != null)
		{
			logger.info("booking over..  before book call--11--apiHotelBook.getStatus() status msg:"+apiHotelBook.getStatus().getMessage());

			if(apiHotelBook.getBook().getBookingSystemType().equalsIgnoreCase("ibe"))
			{

				try{
					User bokingUser = allEmailDao.getUserByUserId(apiHotelBook.getBook().getUserid());
					Company bookingCompany = allEmailDao.getCompanyByCompanyId(String.valueOf(apiHotelBook.getBook().getCompanyId()));
					if(bokingUser != null && bookingCompany != null)
					{
						if(bookingCompany.getCompanyRole().isAgent() || bookingCompany.getCompanyRole().isDistributor())
						{
							logger.info("########################## Distributor / agent booked ticket....");
							logger.info("########################## booked user role..."+bokingUser.getUserrole_id().toString());

							Company parentCompany = emailDao.getParentCompany(bookingCompany);
							parentCompany.initLogoDisplayable();

							if(parentCompany != null)
							{
								if(parentCompany.getCompanyRole().isDistributor() && (bookingCompany.getCompanyid()!=parentCompany.getCompanyid()))
								{
									logger.info("########################## Distributor to agent....");
									emaildao.insertEmail(apiHotelBook.getBook().getOrderid(), 0, Email.EMAIL_TYPE_HOTEL_INVOICE_DISTRIBUTOR_TO_OTHERS);
									//get commission details of company..
									//send invoice mail from parent to child...
								}
								else if(!parentCompany.getCompanyRole().isDistributor() && !parentCompany.getCompanyRole().isAgent())
								{
									//emaildao.insertEmail(apiHotelBook.getBook().getOrderid(), 0, Email.EMAIL_TYPE_HOTEL_INVOICE_SUPER_TO_OTHERS);
								}
							}

						}
					}

				}
				catch(Exception e)
				{
					logger.info("########################## Invoice mail insertion error.........");
					logger.info("########################## Invoice mail insertion error.......e.."+e.getMessage());
				}


				//Invoice mail insertion




				//emaildao.insertEmail(apiHotelBook.getBook().getOrderid(), 0, Email.EMAIL_TYPE_HOTEL_INVOICE_SUPER_TO_OTHERS);
				//emaildao.insertEmail(apiHotelBook.getBook().getOrderid(), 0, Email.EMAIL_TYPE_HOTEL_INVOICE_DISTRIBUTOR_TO_OTHERS);
			}
			else
			{
				//emaildao.insertEmail(apiHotelBook.getBook().getOrderid(), 0, Email.EMAIL_TYPE_HOTEL_INVOICE_SUPER_TO_OTHERS);
			}

			logger.info("booking over..  before book call--11--uniqueid:"+uniqueid);
			logger.info("booking over..  before book call--11--uniqueid.getID():"+uniqueid.getID());
			logger.info("booking over..  before book call--11--totaHotelResRSBook.getStatus().getCode():"+totaHotelResRSBook.getStatus().getCode());


			//if(uniqueid != null && uniqueid.getID().length()>0 && totaHotelResRSBook.getStatus().getCode().equals(APIStatus.STATUS_CODE_SUCCESS))
			if(totaHotelResRSBook.getStatus().getCode().equals(APIStatus.STATUS_CODE_SUCCESS))
			{
				emaildao.insertEmail(apiHotelBook.getBook().getOrderid(), 0, Email.EMAIL_TYPE_HOTEL_VOUCHER);


				//apiHotelBook.setStatus(totaHotelResRS.getStatus());
				//apiHotelBook = payWalletBooking(ht, hs, hb, hor, uniqueid, apiHotelBook, rs);
				//logger.info("HotelPreBookController----Booking over successfull uniqueid:"+uniqueid);

				//**************Old code for tayyarah own invice number....
				/*	String invoiceNo = hotelIdFactory.createShortId(CommonConfig.GetCommonConfig().getInvoice_hotel_prefix());*/
				String invoiceNo = RandomConfigurationNumber.generateHotelOfflineInvoiceNumber(hor.getId()).toString();
				hor.setInvoiceNo(invoiceNo);
				totaHotelResRSBook.setInvoiceNo(invoiceNo);
				//Modified for hotel confirmation....


				hor = CommonUtil.hotelOrderUpdationDataBook(hotelOrderDao, hor, ht, apiHotelBook, "Confirmed", "Paid", "Confirmed", 1, FBDAO);

				totaHotelResRSBook.setConfirmationNo(hor.getConfirmationNo());


				//apiHotelBook = callAPIBook(ht, hs, hb, hor, uniqueid, apiHotelBook, rs);
			}
			else
			{
				//	emaildao.insertEmail(apiHotelBook.getBook().getOrderid(), 0, Email.EMAIL_TYPE_FAILS_HOTEL_BOOK);
				emaildao.insertEmail(apiHotelBook.getBook().getOrderid(), 0, Email.EMAIL_TYPE_HOTEL_VOUCHER);

				hor = CommonUtil.hotelOrderUpdationDataBook(hotelOrderDao, hor, ht, apiHotelBook, "Failed", "Paid", "Booking", -1, FBDAO);
				//CommonUtil.repayWallet(walletAmountTranferHistory, FBDAO);
				//APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Something wong with input details... Booking failed." );
				//apiHotelBook.setStatus(status);

				totaHotelResRSBook = new OTAHotelResRS();
				//totaHotelResRSBook.setStatus(status);
				//apiHotelBook.setStatus(status);
				//apiHotelBook.setBookRes(totaHotelResRSBook);
			}
		}
		else
		{
			APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Something wrong with input details...Booking failed." );
			apiHotelBook.setStatus(status);
			totaHotelResRSBook = new OTAHotelResRS();
			totaHotelResRSBook.setStatus(status);
			//apiHotelBook.setStatus(status);
			apiHotelBook.setBookRes(totaHotelResRSBook);
		}


		return apiHotelBook;

	}

	public APIHotelBook callAPIBookTayyarah(OTAHotelResRS totaHotelResRS, HotelTransactionTemp ht, HotelBookingTemp hb, HotelOrderRow hor, UniqueIDType uniqueid, APIHotelBook apiHotelBook, OTAHotelAvailRS.RoomStays.RoomStay rs, HotelBookCommand hbcapi, WalletAmountTranferHistory walletAmountTranferHistory, FlightBookingDao FBDAO) throws Exception
	{
		HotelApiCredentials apiauth = null;
		OTAHotelResRS totaHotelResRSBook = new OTAHotelResRS();
		logger.info("HotelBookController----rs--"+ rs);


		logger.info("HotelBookController----rs.getBasicPropertyInfo()--"+ rs.getBasicPropertyInfo());
		logger.info("HotelBookController----rs.getBasicPropertyInfo()--"+ rs.getBasicPropertyInfo().getApiProvider());
		logger.info("HotelBookController----rs.getBasicPropertyInfo()--"+ rs.getBasicPropertyInfo().getApiVendorID());
		logger.info("HotelBookController----rs.getBasicPropertyInfo()--"+ rs.getBasicPropertyInfo().getHotelCode());

		switch (rs.getBasicPropertyInfo().getApiProvider()) {
		default:
			break;
		}
		if(totaHotelResRSBook.getStatus() != null)
		{
			if(apiHotelBook.getBook().getBookingSystemType().equalsIgnoreCase("ibe"))
			{
				emaildao.insertEmail(apiHotelBook.getBook().getOrderid(), 0, Email.EMAIL_TYPE_HOTEL_VOUCHER);
				emaildao.insertEmail(apiHotelBook.getBook().getOrderid(), 0, Email.EMAIL_TYPE_HOTEL_INVOICE_SUPER_TO_OTHERS);
				emaildao.insertEmail(apiHotelBook.getBook().getOrderid(), 0, Email.EMAIL_TYPE_HOTEL_INVOICE_DISTRIBUTOR_TO_OTHERS);
			}
			else
			{
			}
			if(uniqueid != null && uniqueid.getID().length()>0 && totaHotelResRSBook.getStatus().getCode().equals(APIStatus.STATUS_CODE_SUCCESS))
			{
				hor = CommonUtil.hotelOrderUpdationDataBook(hotelOrderDao, hor, ht, apiHotelBook, "Confirmed", "Paid", "Confirmed", 1, FBDAO);
			}
			else
			{
				hor = CommonUtil.hotelOrderUpdationDataBook(hotelOrderDao, hor, ht, apiHotelBook, "Failed", "Paid", "Booking", -1, FBDAO);
				CommonUtil.repayWallet(walletAmountTranferHistory, FBDAO);
				totaHotelResRSBook = new OTAHotelResRS();
			}
		}
		else
		{
			APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + "Something wong with input details...Booking failed." );
			apiHotelBook.setStatus(status);
			totaHotelResRSBook = new OTAHotelResRS();
			totaHotelResRSBook.setStatus(status);			
			apiHotelBook.setBookRes(totaHotelResRSBook);
		}
		return apiHotelBook;
	}

	public APIHotelBook payWalletBooking(OTAHotelResRS totaHotelResRS, HotelTransactionTemp ht, HotelBookingTemp hb, HotelOrderRow hor, PaymentTransaction paymentTransaction, UniqueIDType uniqueid, APIHotelBook apiHotelBook, OTAHotelAvailRS.RoomStays.RoomStay rs, AppKeyVo appKeyVo) throws Exception
	{

		BigDecimal gst = new BigDecimal("0.0");// no gst for hotel
		boolean result = false;
		BigDecimal totalpricewithservicetax = new BigDecimal("0.00");
		totalpricewithservicetax = apiHotelBook.getBookingRate().getTotalPayableAmt();

		WalletAmountTranferHistory walletAmountTranferHistory = new WalletAmountTranferHistory();
		walletAmountTranferHistory.setActionId(apiHotelBook.getBook().getOrderid());

		List<Company> companyListBottomToTop= new LinkedList<>();
		List<User> userListBottomToTop= new LinkedList<>();
		Map<Integer, CutandPayModel> cutAndPayUserMap = new LinkedHashMap<>();	

		try {
			CompanyConfig companyConfig = null;
			try {
				companyConfig = appKeyVo.getCompanyConfig();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			if(companyConfig!=null)
			{
				companyListBottomToTop = CommonUtil.getParentCompanyBottomToTop(companyConfig.getCompany_id(),CDAO);
				if(companyListBottomToTop!=null && companyListBottomToTop.size()>0)
				{
					User currentUser = CDAO.getUserById(Integer.valueOf(apiHotelBook.getBook().getUserid()));
					userListBottomToTop = CommonUtil.getUsersAllWithUserModeBottomToTop(companyListBottomToTop,CDAO,currentUser);
				}
				cutAndPayUserMap = CommonUtil.getCutandPayModelUsersHotel(apiHotelBook, companyConfig,userListBottomToTop);

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
								if(!AWDAO.checkWalletAmount(userInner.getId(), totalPayableAmount,gst, new BigDecimal(0))){
									result = false;
									checkBookingAmountEligibility = false;
									break;
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
									if(AWDAO.checkWalletAmount(userInner.getId(), totalPayableAmount,gst, new BigDecimal(0)))
									{		
										AWDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,gst, new BigDecimal(0),CommonBookingStatusEnum.HOTEL_REMARKS.getMessage(),true);
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
													AWDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,gst, new BigDecimal(0),CommonBookingStatusEnum.HOTEL_FAILEDREMARKS.getMessage(),false);
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
			logger.error("Wallet status----"+ result);

		} catch (Exception e1) {

			logger.error("Exception", e1);
			paymentTransaction.setIsPaymentSuccess(false);
			paymentTransaction.setResponse_message("wallet payment failed");
			paymentTransaction.setResponseCode("NA");
			paymentTransaction.setPayment_status("FAILED");
			updatePaymentTransaction(paymentTransaction);

			hor = CommonUtil.hotelOrderUpdationDataBook(hotelOrderDao, hor, ht, apiHotelBook, "Reserved", "wallet payment failed", "Booking", 0, FBDAO);

			APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " wallet payment failed--"+e1.getMessage());
			OTAHotelResRS totaHotelResRSBook = new OTAHotelResRS();
			totaHotelResRSBook.setStatus(status);
			apiHotelBook.setStatus(status);
			apiHotelBook.setBookRes(totaHotelResRSBook);
		}
		if (result){

			logger.error("Wallet status----"+ result);
			logger.error("paymentTransaction---"+ paymentTransaction);

			paymentTransaction.setIsPaymentSuccess(true);
			paymentTransaction.setResponse_message("NA");
			paymentTransaction.setResponseCode("NA");
			paymentTransaction.setPayment_status("SUCCESS");
			updatePaymentTransaction(paymentTransaction);
			hor = CommonUtil.hotelOrderUpdationDataBook(hotelOrderDao, hor, ht, apiHotelBook, "Reserved", "wallet payment success", "Booking", 0, FBDAO);


			apiHotelBook = callAPIBook(totaHotelResRS,ht, hb, hor, uniqueid, apiHotelBook, rs,appKeyVo);

			if(!(apiHotelBook.getBookRes().getStatus().getCode().equals(APIStatus.STATUS_CODE_SUCCESS)))
			{
				if(userListBottomToTop!=null && userListBottomToTop.size()>0)
				{
					for(User userInner : userListBottomToTop)
					{
						if(userInner.getAgentWallet()!=null)
						{
							if(cutAndPayUserMap!=null && cutAndPayUserMap.get(userInner.getId())!=null)
							{
								BigDecimal totalPayableAmount = cutAndPayUserMap.get(userInner.getId()).getPayableAmount();
								AWDAO.getWalletStatus(String.valueOf(userInner.getId()), totalPayableAmount,walletAmountTranferHistory,gst, new BigDecimal(0),CommonBookingStatusEnum.HOTEL_FAILEDREMARKS.getMessage(),false);
							}
							else{
								result = false;
							}
						}
					}
				}	

			}
			byte[] book_resdata = SerializationUtils.serialize(apiHotelBook.getBookRes());
			hb.setBook_res(book_resdata);
			hb = hotelBookingDao.insertHotelBooking(hb);
		}else{//insufficient wallet balance

			paymentTransaction.setIsPaymentSuccess(false);
			paymentTransaction.setResponse_message("insufficient wallet balance");
			paymentTransaction.setResponseCode("NA");
			paymentTransaction.setPayment_status("FAILED");
			updatePaymentTransaction(paymentTransaction);

			hor = CommonUtil.hotelOrderUpdationDataBook(hotelOrderDao, hor, ht, apiHotelBook, "Reserved", "wallet payment failed", "Booking", 0, FBDAO);
			//logger.info("HotelBookingController----insufficient wallet balance:  hor updated"+ hor);


			APIStatus status = new APIStatus(APIStatus.STATUS_CODE_ERROR, APIStatus.STATUS_MESSAGE_ERROR + " insufficient wallet balance");
			OTAHotelResRS totaHotelResRSBook = new OTAHotelResRS();
			totaHotelResRSBook.setStatus(status);
			apiHotelBook.setStatus(status);
			apiHotelBook.setBookRes(totaHotelResRSBook);
			//DBS.updatePNR("#0", apiHotelBook.getBook().getOrderid(), FBDAO);
		}


		return apiHotelBook;

	}

	public void updatePaymentTransaction(PaymentTransaction paymentTransaction) throws Exception {

		FlightDataBaseServices DBS = new FlightDataBaseServices();
		DBS.insertPaymentTransaction(paymentTransaction, FBDAO);
	}


}
