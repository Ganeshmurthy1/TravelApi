package com.tayyarah.email.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.spring3.context.SpringWebContext;

import com.itextpdf.text.DocumentException;
import com.tayyarah.common.dao.NotificationDAO;
import com.tayyarah.common.dao.RmConfigDetailDAO;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.entity.PaymentTransactionDetail;
import com.tayyarah.common.entity.RmConfigModel;
import com.tayyarah.common.entity.RmConfigTripDetailsModel;
import com.tayyarah.common.entity.StateInfo;
import com.tayyarah.common.notification.NotificationDetail;
import com.tayyarah.common.notification.dao.HotelAndFlightPaymentTransctionDAO;
import com.tayyarah.common.util.ArithmeticUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.common.util.DateConversion;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyEntity;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.dao.AllEmailDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.email.dao.EmailNotificationDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.email.entity.model.EmailNotification;
import com.tayyarah.hotel.dao.HotelCreditNoteDao;
import com.tayyarah.hotel.dao.HotelOrderRowEmailDao;
import com.tayyarah.hotel.dao.HotelSearchRoomDetailDao;
import com.tayyarah.hotel.entity.HotelCreditNote;
import com.tayyarah.hotel.entity.HotelOrderCancellationPolicy;
import com.tayyarah.hotel.entity.HotelOrderGuest;
import com.tayyarah.hotel.entity.HotelOrderRoomInfo;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRowMarkup;
import com.tayyarah.hotel.entity.HotelPaymentTxDetailHistory;
import com.tayyarah.hotel.entity.HotelSearchRoomDetailTemp;
import com.tayyarah.hotel.quotation.dao.HotelTravelRequestDao;
import com.tayyarah.hotel.quotation.entity.HotelTravelRequestQuotation;
import com.tayyarah.services.EmailContentService;
import com.tayyarah.services.EmailService;
import com.tayyarah.user.entity.User;

@Component
public class HotelEmailHelper {

	public static final Logger logger = Logger.getLogger(HotelEmailHelper.class);

	public void sendHotelInvoiceEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, HotelOrderRowEmailDao hotelOrderRowEmailDao, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		if (email != null) {
			HotelOrderRow hotelOrderRow = null;
			Company bookingCompany = null;
			User bookingUser = null;
			CompanyEntity companyEntity=null;
			Company parentCompany = null;
			User parentUser = null;
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String orderId = email.getOrderId();
			HttpSession session = request.getSession();			
			hotelOrderRow = hotelOrderRowEmailDao.hotelOrderRowByOrderId(orderId);
			if (hotelOrderRow != null) {
				BigDecimal beforeGstTot = hotelOrderRow.getFinalPrice();
				BigDecimal withtax = hotelOrderRow.getTaxes();				
				BigDecimal gstToBookingCurrency = new BigDecimal(0);
				BigDecimal totWithGst = hotelOrderRow.getFinalPrice().add(gstToBookingCurrency);
				BigDecimal totalwithtax = hotelOrderRow.getTaxes();
				String invoiceNo = hotelOrderRow.getInvoiceNo();
				BigDecimal GSTfinalpricewithoutTax = totWithGst.subtract(withtax);			
				String checkIn = formatter.format(hotelOrderRow.getCheckInDate());
				String checkOut = formatter.format(hotelOrderRow.getCheckOutDate());
				DateTime dt1 = new DateTime(checkIn);
				DateTime dt2 = new DateTime(checkOut);				
				int numberOfNights = Days.daysBetween(dt1, dt2).getDays();
				List<HotelOrderRoomInfo> roomDeatailsList = hotelOrderRowEmailDao.roomInfoDeatails(hotelOrderRow);
				List<HotelOrderGuest> guestList = hotelOrderRowEmailDao.guesInformationDeatails(roomDeatailsList);
				List<HotelOrderCancellationPolicy> cancelpolicies = hotelOrderRowEmailDao
						.getCancelPolicies(hotelOrderRow);
				List<HotelOrderCancellationPolicy> cancelpoliciesRefined = new ArrayList<HotelOrderCancellationPolicy>();
				for (HotelOrderCancellationPolicy hotelOrderCancellationPolicy : cancelpolicies) {
					hotelOrderCancellationPolicy.roundOffFeeAmount();
					cancelpoliciesRefined.add(hotelOrderCancellationPolicy);
				}
				// to be converted into booking price...
				try {
					BigDecimal finalprice = hotelOrderRow.getFinalPrice();
					hotelOrderRow.setFinalPrice(finalprice.setScale(2, RoundingMode.UP));
					BigDecimal taxes = hotelOrderRow.getTaxes();
					taxes = taxes.multiply(hotelOrderRow.getApiToBaseExchangeRate());
					taxes = taxes.multiply(hotelOrderRow.getBaseToBookingExchangeRate());
					hotelOrderRow.setTaxes(taxes.setScale(2, RoundingMode.UP));
					BigDecimal basefare = finalprice.subtract(taxes);
					BigDecimal servicecharges = hotelOrderRow.getFeeAmount();
					hotelOrderRow.setFeeAmount(servicecharges.setScale(2, RoundingMode.UP));
					BigDecimal proFees = finalprice.subtract(servicecharges).setScale(2, RoundingMode.UP);
					session.setAttribute("basefare", proFees);
				} catch (Exception e) {
					logger.info("taxes amount to booking currency....Exception----  ");

				}
				try {
					bookingCompany = allEmailDao.getCompanyByCompanyId(hotelOrderRow.getCompanyId());				
					Company superUserObj = allEmailDao.getSuperCompanyByCompanyUserId(bookingCompany.getSuper_company_userid());
					if(bookingCompany.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()==null){
						bookingCompany.setTemsandcondtions("NA");
						superUserObj.setTemsandcondtions("NA");
					}else if(bookingCompany.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()!=null){
						bookingCompany.setTemsandcondtions(superUserObj.getTemsandcondtions());
					}else if(bookingCompany.getTemsandcondtions()==null){
						bookingCompany.setTemsandcondtions("NA");
					}
					//added
					if(hotelOrderRow.getCompanyEntityId()!=null && hotelOrderRow.getCompanyEntityId() >0){
						companyEntity=allEmailDao.getCompanyEntityByCompanyId(hotelOrderRow.getCompanyEntityId());
						bookingCompany=companyEntity.getCompany();
					}
					bookingUser = allEmailDao.getUserByEmail(bookingCompany.getEmail());
					if (bookingUser != null && bookingCompany != null) {

						bookingUser.initLogoDisplayable();
						bookingCompany.initLogoDisplayable();

						if (bookingCompany.getCompanyRole().isAgent()
								|| bookingCompany.getCompanyRole().isDistributor()) {							
							parentCompany = emailDao.getParentCompany(bookingCompany);
							parentCompany.initLogoDisplayable();
							if (parentCompany != null) {
								parentUser = emailDao.getCompanyWalletUser(parentCompany);
								if (parentUser != null) {
									parentCompany.initLogoDisplayable();
									parentUser.initLogoDisplayable();
									session.setAttribute("userid", bookingUser.getId());
									session.setAttribute("parentUserid", parentUser.getId());
									emailDao.getHotelOrderInvoiceDetails(session, orderId, email.getType(),
											bookingUser.getId(), parentUser.getId());
									session.setAttribute("address",
											bookingCompany.getBillingaddress().replaceAll("[2C%-+.^:,]", " "));
									session.setAttribute("hotelOrderRowId", hotelOrderRow);
									session.setAttribute("roomDeatailsList", roomDeatailsList.get(0));
									session.setAttribute("guestList", guestList);
									session.setAttribute("cancelpolicies", cancelpoliciesRefined.get(0));
									session.setAttribute("numberOfNights", numberOfNights);

									Map<String, Object> variables = new HashMap<String, Object>();
									variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
									variables.put("hotelorder", hotelOrderRow);
									variables.put("user", bookingUser);
									variables.put("company", bookingCompany);
									variables.put("companyEntity", companyEntity);
									variables.put("companylogo", CommonConfig.GetCommonConfig().getImage_path()
											+ bookingCompany.getCompanyid());
									variables.put("parentUser", parentUser);
									variables.put("parentComapny", parentCompany);
									variables.put("baseUrl", request.getScheme() + "://" + request.getServerName() + ":"
											+ request.getServerPort());
									final IWebContext ctx = new SpringWebContext(request, response, servletContext,
											locale, variables, applicationContext);

									String htmlContent = emailContentService.createHotelOrderInvoiceMail(bookingCompany,ctx);
									emailService.sendHotelOrderInvoiceMail(hotelOrderRow, bookingUser,
											bookingCompany, MediaType.IMAGE_PNG_VALUE, locale, request, response,
											servletContext, applicationContext, parentUser, parentCompany, email,
											htmlContent);

									emailNotificationDao.insertEmailNotification(parentCompany, parentCompany,
											bookingCompany, bookingUser, EmailNotification.ACTION_PARENT_TO_CHILD,
											email);

								} else {
									logger.error("########################## invoice error::Parent user is missing.");
									throw new Exception("Parent user is missing...");
								}
							}
						}
					} else {
						logger.error("########################## invoice error::Booking user / company is missing....");
						throw new Exception("Booking user / company is missing...");
					}

				} catch (Exception e) {
					logger.error("########################## invoice error::Booking user / company is missing....", e);
					throw new Exception("Booking user / company is missing...");
				}

			} else {
				throw new Exception("Hotel order not found");
			}

		}
	}

	public String sendHotelVoucherEmail(Boolean isFailed, Email email, HttpServletRequest request,
			HttpServletResponse response, Locale locale, HotelSearchRoomDetailDao hotelSearchRoomDetailDao, HotelTravelRequestDao hotelTravelRequestDao, HotelOrderRowEmailDao hotelOrderRowEmailDao, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService) throws MessagingException, MailException, NullPointerException,
	UnsupportedEncodingException, DocumentException, IOException, Exception {
		String htmlContent = null;
		if (email != null) {
			HotelOrderRow hotelOrderRow = null;
			Company company = null;
			CompanyEntity companyEntity=null;
			User user = null;
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String orderId = email.getOrderId();
			HttpSession session = request.getSession();
			logger.info("flight order id : " + orderId);
			hotelOrderRow = hotelOrderRowEmailDao.hotelOrderRowByOrderId(orderId);
			company = allEmailDao.getCompanyByCompanyId(hotelOrderRow.getCompanyId());		
			Company superUserObj=allEmailDao.getSuperCompanyByCompanyUserId(company.getSuper_company_userid());
			if(company.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()==null){
				company.setTemsandcondtions("NA");
				superUserObj.setTemsandcondtions("NA");
			}else if(company.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()!=null){
				company.setTemsandcondtions(superUserObj.getTemsandcondtions());
			}else if(company.getTemsandcondtions()==null){
				company.setTemsandcondtions("NA");
			}
			if(hotelOrderRow.getCompanyEntityId()!=null && hotelOrderRow.getCompanyEntityId() >0){
				companyEntity=allEmailDao.getCompanyEntityByCompanyId(hotelOrderRow.getCompanyEntityId());
				company=companyEntity.getCompany();
			}
			user = allEmailDao.getUserByUserId(hotelOrderRow.getUserId());			
			String checkIn = formatter.format(hotelOrderRow.getCheckInDate());
			String checkOut = formatter.format(hotelOrderRow.getCheckOutDate());
			DateTime dt1 = new DateTime(checkIn);
			DateTime dt2 = new DateTime(checkOut);
			int numberOfNights = Days.daysBetween(dt1, dt2).getDays();
			List<HotelOrderRoomInfo> roomDeatailsList = hotelOrderRowEmailDao.roomInfoDeatails(hotelOrderRow);
			List<HotelOrderRoomInfo> roomDeatailsListRefined = new ArrayList<HotelOrderRoomInfo>();			
			for (HotelOrderRoomInfo roominfo : roomDeatailsList) {
				List<HotelOrderCancellationPolicy> cancelpoliciesRefined = new ArrayList<HotelOrderCancellationPolicy>();
				for (HotelOrderCancellationPolicy hotelOrderCancellationPolicy : roominfo
						.getHotelOrderCancellationPolicies()) {
					hotelOrderCancellationPolicy.roundOffFeeAmount();
					cancelpoliciesRefined.add(hotelOrderCancellationPolicy);
				}
				roominfo.setHotelOrderCancellationPolicies(cancelpoliciesRefined);
				roomDeatailsListRefined.add(roominfo);

			}
			List<HotelOrderGuest> guestList = hotelOrderRowEmailDao.guesInformationDeatails(roomDeatailsList);
			List<HotelOrderGuest> refinedGuestList = new ArrayList<>();
			if(guestList.size() > 0){
				for (HotelOrderGuest hotelOrderGuest : guestList) {
					if(hotelOrderGuest.getLeader().booleanValue()){
						refinedGuestList.add(hotelOrderGuest);
					}
				}
			}

			session.setAttribute("isCorporate", company.getCompanyRole().isCorporate());
			session.setAttribute("bookingmode", hotelOrderRow.getBookingMode());
			if(hotelOrderRow.getBookingMode()!=null && !hotelOrderRow.getBookingMode().equalsIgnoreCase("")){
				if(hotelOrderRow.getBookingMode().equalsIgnoreCase("Offline")){
					HotelTravelRequestQuotation hotelTravelRequestQuotation = hotelTravelRequestDao.getHotelQuotationDetails(hotelOrderRow.getId());
					session.setAttribute("cancellationpolicy", hotelTravelRequestQuotation.getCancellationPolicy());
				}
			}

			List<HotelOrderCancellationPolicy> cancelpolicies = hotelOrderRowEmailDao.getCancelPolicies(hotelOrderRow);
			List<HotelOrderCancellationPolicy> cancelpoliciesRefined = new ArrayList<HotelOrderCancellationPolicy>();
			for (HotelOrderCancellationPolicy hotelOrderCancellationPolicy : cancelpolicies) {
				hotelOrderCancellationPolicy.roundOffFeeAmount();
				cancelpoliciesRefined.add(hotelOrderCancellationPolicy);
			}
			// to be converted into booking price...
			try {
				BigDecimal finalprice = hotelOrderRow.getFinalPrice();
				hotelOrderRow.setFinalPrice(finalprice.setScale(2, RoundingMode.UP));

				BigDecimal taxes = hotelOrderRow.getTaxes();
				taxes = taxes.multiply(hotelOrderRow.getApiToBaseExchangeRate());
				taxes = taxes.multiply(hotelOrderRow.getBaseToBookingExchangeRate());
				hotelOrderRow.setTaxes(taxes.setScale(2, RoundingMode.UP));
				BigDecimal basefare = finalprice.subtract(taxes);
				basefare = basefare.setScale(2, RoundingMode.UP);
				BigDecimal servicecharges = hotelOrderRow.getFeeAmount();
				hotelOrderRow.setFeeAmount(servicecharges.setScale(2, RoundingMode.UP));

				session.setAttribute("basefare", basefare);
			} catch (Exception e) {
				logger.info("taxes amount to booking currency....Exception----  ");
			}
			String combinationType="";
			if(hotelOrderRow.getSearchKey()!=null){
				HotelSearchRoomDetailTemp hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(hotelOrderRow.getSearchKey().longValue());
				if(hotelOrderRow.getSearchKey()!=null){
					hotelSearchRoomDetail = hotelSearchRoomDetailDao.getHotelSearchRoomDetail(hotelSearchRoomDetail);
					com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = (com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay) SerializationUtils
							.deserialize(hotelSearchRoomDetail.getRoomstay());
					combinationType = rs.getSupplierRoomGroups().get(rs.getSupplierRoomGroups().size() - 1)
							.getInfoSource();
					logger.info("HotelSearchRoomDetailTemp combinationType " + combinationType);

				}
			}

			if(user.getEmail().equalsIgnoreCase("DirectUser@intellicommsolutions.com")){
				session.setAttribute("IsB2C", true);
			}else{
				session.setAttribute("IsB2C", false);
			}

			if (combinationType.equalsIgnoreCase("OpenCombination")) {
				session.setAttribute("roomDeatailsList", roomDeatailsListRefined);
			} else if (combinationType.equalsIgnoreCase("FixedCombination")) {
				session.setAttribute("roomDeatailsList", roomDeatailsListRefined.get(0));
			} else {
				session.setAttribute("roomDeatailsList", roomDeatailsListRefined.get(0));
			}
			session.setAttribute("combinationType", combinationType);
			session.setAttribute("hotelOrderRowId", hotelOrderRow);
			session.setAttribute("guestList", refinedGuestList);
			session.setAttribute("numberOfNights", numberOfNights);

			String agencyemail = user.getEmail();
			String customeremail = hotelOrderRow.getOrderCustomer().getEmail();

			// Prepare the evaluation context
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
			variables.put("hotelorder", hotelOrderRow);
			variables.put("user", user);
			variables.put("company", company);
			variables.put("companyEntity", companyEntity);
			variables.put("companylogo", CommonConfig.GetCommonConfig().getImage_path()+company.getCompanyid());
			variables.put("isFailed", isFailed);
			variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
			final IWebContext ctxVoucher = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);
			htmlContent = emailContentService.sendHotelOrderMailVoucher(isFailed, true, hotelOrderRow, user, company,
					MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext, applicationContext,ctxVoucher);
			if(!email.isOnlyHtmlContent())
			{
				if (isFailed)
					emailService.sendHotelOrderMailVoucher(isFailed, true, hotelOrderRow, user, company,
							MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext, applicationContext,htmlContent,email);
				else if (agencyemail != null && customeremail != null) {
					emailService.sendHotelOrderMailVoucher(false, true, hotelOrderRow, user, company,
							MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext, applicationContext,htmlContent,email);
					logger.info("E Voucher Email sent to customer for " + customeremail);
					if(!customeremail.equalsIgnoreCase("")){
						if (!agencyemail.equalsIgnoreCase(customeremail)) {
							logger.info("########################## agency and customer emails are different ");
							emailService.sendHotelOrderMailVoucher(false, false, hotelOrderRow, user, company,
									MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext, applicationContext,htmlContent,email);
							logger.info("E Voucher Email sent to agency for " + agencyemail);
						}
					}
					emailNotificationDao.insertEmailNotification(company, company, company, user,
							EmailNotification.ACTION_PARENT_TO_CHILD, email);
				}
			}
		}
		return htmlContent;
	}

	public String sendHotelOffline_OnlineInvoice(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, HotelSearchRoomDetailDao hotelSearchRoomDetailDao, HotelTravelRequestDao hotelTravelRequestDao, HotelOrderRowEmailDao hotelOrderRowEmailDao, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService,RmConfigDetailDAO rmConfigDetailDAO) throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException,
	DocumentException, IOException, Exception {
		String htmlContent = null;
		boolean isRmStruct=false;
		if (email != null) {
			HotelOrderRow hotelOrderRow = null;
			Company bookingCompany = null;
			CompanyEntity companyEntity=null;
			User bookingUser = null;
			Company parentCompany = null;
			User parentUser = null;
			RmConfigTripDetailsModel rmConfigTripDetailsModel = null;
			List<String> fieldNameArray = new ArrayList<String>();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String orderId = email.getOrderId();
			HttpSession session = request.getSession();		
			hotelOrderRow = hotelOrderRowEmailDao.hotelOrderRowByOrderId(orderId);			
			if (hotelOrderRow != null) {				
				bookingCompany = allEmailDao.getCompanyByCompanyId(hotelOrderRow.getCompanyId());				
				Company superUserObj=allEmailDao.getSuperCompanyByCompanyUserId(bookingCompany.getSuper_company_userid());
				if(bookingCompany.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()==null){
					bookingCompany.setTemsandcondtions("NA");
					superUserObj.setTemsandcondtions("NA");
				}else if(bookingCompany.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()!=null){
					bookingCompany.setTemsandcondtions(superUserObj.getTemsandcondtions());
				}else if(bookingCompany.getTemsandcondtions()==null){
					bookingCompany.setTemsandcondtions("NA");
				}
				StateInfo stateInfo = allEmailDao.getStateInfo(bookingCompany.getBillingstate());	 
				if(stateInfo !=null)
					bookingCompany.setStateCode(stateInfo.getStateCode());

				if(hotelOrderRow.getCompanyEntityId()!=null ){
					companyEntity = allEmailDao.getCompanyEntityByCompanyId(hotelOrderRow.getCompanyEntityId());					
				}
				bookingUser = allEmailDao.getUserByEmail(bookingCompany.getEmail());
				rmConfigTripDetailsModel = rmConfigDetailDAO.getRmConfigDetail(hotelOrderRow.getOrderReference());
				RmConfigModel rmConfigModel = rmConfigDetailDAO.getRmConfigModel(bookingCompany.getCompanyid());
				String manualField1rmconfig=null;
				String manualField2rmconfig=null;
				String manualField3rmconfig=null;
				String manualField4rmconfig=null;
				String manualField5rmconfig=null;
				String manualField1=null;
				String manualField2=null;
				String manualField3=null;
				String manualField4=null;
				String manualField5=null;

				if(rmConfigTripDetailsModel!=null){
					if(rmConfigTripDetailsModel.getManualField1()!=null && !rmConfigTripDetailsModel.getManualField1().trim().equalsIgnoreCase("")){
						manualField1rmconfig=rmConfigTripDetailsModel.getManualField1();
					}
					if(rmConfigTripDetailsModel.getManualField2()!=null && !rmConfigTripDetailsModel.getManualField2().trim().equalsIgnoreCase("")){
						manualField2rmconfig=rmConfigTripDetailsModel.getManualField2();
					}
					if(rmConfigTripDetailsModel.getManualField3()!=null && !rmConfigTripDetailsModel.getManualField3().trim().equalsIgnoreCase("")){
						manualField3rmconfig=rmConfigTripDetailsModel.getManualField3();
					}
					if(rmConfigTripDetailsModel.getManualField4()!=null && !rmConfigTripDetailsModel.getManualField4().trim().equalsIgnoreCase("")){
						manualField4rmconfig=rmConfigTripDetailsModel.getManualField4();
					}
					if(rmConfigTripDetailsModel.getManualField5()!=null && !rmConfigTripDetailsModel.getManualField5().trim().equalsIgnoreCase("")){
						manualField5rmconfig=rmConfigTripDetailsModel.getManualField5();
					}
				}
				
				if(hotelOrderRow.getHotelOrderRowRmConfigStruct()!=null) 
					 isRmStruct=true;
				String dynamicSchema=rmConfigModel!=null?rmConfigModel.getDynamicFieldsData():"";
				if(isRmStruct) 
					dynamicSchema=hotelOrderRow.getHotelOrderRowRmConfigStruct().getRmDynamicData();
				String manualStringFields[] = dynamicSchema!=null && !dynamicSchema.trim().equalsIgnoreCase("") ?dynamicSchema.split(","):null;
					if(manualStringFields!=null && manualStringFields.length>0){
						for(String oneField:manualStringFields){
							if(!oneField.trim().equalsIgnoreCase("")){
								String fieldsName[]=oneField.split(":");
								fieldNameArray.add(fieldsName[0]);
							}

						}
					}
				 

				if(fieldNameArray!=null && fieldNameArray.size()>0){
					if(fieldNameArray.get(0)!=null && !fieldNameArray.get(0).trim().equalsIgnoreCase("")){
						manualField1=fieldNameArray.get(0);
					}
					if(fieldNameArray.size()>1 && fieldNameArray.get(1)!=null && !fieldNameArray.get(1).trim().equalsIgnoreCase("")){
						manualField2=fieldNameArray.get(1);
					}
					if(fieldNameArray.size()>2 && fieldNameArray.get(2)!=null && !fieldNameArray.get(2).trim().equalsIgnoreCase("")){
						manualField3=fieldNameArray.get(2);
					}
					if(fieldNameArray.size()>3 && fieldNameArray.get(3)!=null && !fieldNameArray.get(3).trim().equalsIgnoreCase("")){
						manualField4=fieldNameArray.get(3);
					}
					if(fieldNameArray.size()>4 && fieldNameArray.get(4)!=null && !fieldNameArray.get(4).trim().equalsIgnoreCase("")){
						manualField5=fieldNameArray.get(4);
					}

				}

				BigDecimal beforeGstTot = hotelOrderRow.getFinalPrice();
				BigDecimal withtax = hotelOrderRow.getTaxes();				
				BigDecimal gstToBookingCurrency = new BigDecimal(0);
				BigDecimal totWithGst = hotelOrderRow.getFinalPrice().add(gstToBookingCurrency);
				BigDecimal totalwithtax = hotelOrderRow.getTaxes();
				String invoiceNo = hotelOrderRow.getInvoiceNo();
				BigDecimal GSTfinalpricewithoutTax = totWithGst.subtract(withtax);				
				String checkIn = formatter.format(hotelOrderRow.getCheckInDate());
				String checkOut = formatter.format(hotelOrderRow.getCheckOutDate());
				DateTime dt1 = new DateTime(checkIn);
				DateTime dt2 = new DateTime(checkOut);				
				int numberOfNights = Days.daysBetween(dt1, dt2).getDays();
				List<HotelOrderRoomInfo> roomDeatailsList = hotelOrderRowEmailDao.roomInfoDeatails(hotelOrderRow);
				List<HotelOrderRoomInfo> roomDeatailsListRefined = new ArrayList<HotelOrderRoomInfo>();				
				for (HotelOrderRoomInfo roominfo : roomDeatailsList) {					
					List<HotelOrderCancellationPolicy> cancelpoliciesRefined = new ArrayList<HotelOrderCancellationPolicy>();
					for (HotelOrderCancellationPolicy hotelOrderCancellationPolicy : roominfo
							.getHotelOrderCancellationPolicies()) {
						hotelOrderCancellationPolicy.roundOffFeeAmount();
						cancelpoliciesRefined.add(hotelOrderCancellationPolicy);
					}
					roominfo.setHotelOrderCancellationPolicies(cancelpoliciesRefined);
					roomDeatailsListRefined.add(roominfo);
				}

				List<HotelOrderGuest> guestList = hotelOrderRowEmailDao.guesInformationDeatails(roomDeatailsList);
				List<HotelOrderGuest> refinedGuestList = new ArrayList<>();
				if(guestList.size() > 0){
					for (HotelOrderGuest hotelOrderGuest : guestList) {
						if(hotelOrderGuest.getLeader().booleanValue()){
							refinedGuestList.add(hotelOrderGuest);
						}
					}
				}

				List<HotelOrderCancellationPolicy> cancelpolicies = hotelOrderRowEmailDao
						.getCancelPolicies(hotelOrderRow);
				List<HotelOrderCancellationPolicy> cancelpoliciesRefined = new ArrayList<HotelOrderCancellationPolicy>();
				for (HotelOrderCancellationPolicy hotelOrderCancellationPolicy : cancelpolicies) {
					hotelOrderCancellationPolicy.roundOffFeeAmount();
					cancelpoliciesRefined.add(hotelOrderCancellationPolicy);
				}
				BigDecimal basefare = new BigDecimal("0.0");
				BigDecimal taxes  = new BigDecimal("0.0");
				// to be converted into booking price...
				try {
					BigDecimal finalprice = hotelOrderRow.getFinalPrice();
					hotelOrderRow.setFinalPrice(finalprice.setScale(0, RoundingMode.UP));

					taxes = hotelOrderRow.getTaxes();
					taxes = taxes.multiply(hotelOrderRow.getApiToBaseExchangeRate());
					taxes = taxes.multiply(hotelOrderRow.getBaseToBookingExchangeRate());
					hotelOrderRow.setTaxes(taxes.setScale(0, RoundingMode.UP));
					basefare = finalprice.subtract(taxes);
					BigDecimal servicecharges = hotelOrderRow.getFeeAmount();
					hotelOrderRow.setFeeAmount(servicecharges.setScale(2, RoundingMode.UP));
					BigDecimal proFees = finalprice.subtract(servicecharges).setScale(2, RoundingMode.UP);
					session.setAttribute("basefare", basefare.setScale(2, RoundingMode.UP));
					session.setAttribute("taxes", taxes.setScale(2, RoundingMode.UP));


				} catch (Exception e) {
					logger.info("taxes amount to booking currency....Exception----  ");

				}
				String combinationType="";

				if(hotelOrderRow.getSearchKey()!=null){
					HotelSearchRoomDetailTemp hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(hotelOrderRow.getSearchKey().longValue());
					if(hotelOrderRow.getSearchKey()!=null){
						hotelSearchRoomDetail = hotelSearchRoomDetailDao.getHotelSearchRoomDetail(hotelSearchRoomDetail);
						com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = (com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay) SerializationUtils
								.deserialize(hotelSearchRoomDetail.getRoomstay());
						combinationType = rs.getSupplierRoomGroups().get(rs.getSupplierRoomGroups().size() - 1)
								.getInfoSource();
					}
				}

				if (combinationType.equalsIgnoreCase("OpenCombination")) {
					session.setAttribute("roomDeatailsList", roomDeatailsListRefined);
				} else if (combinationType.equalsIgnoreCase("FixedCombination")) {
					session.setAttribute("roomDeatailsList", roomDeatailsListRefined.get(0));
				} else {
					session.setAttribute("roomDeatailsList", roomDeatailsListRefined.get(0));
				}
				session.setAttribute("combinationType", combinationType);
				hotelOrderRow.setBookingDate(DateConversion.getBluestarDateddMMMYYYY(hotelOrderRow.getBookingDate()));
				try {

					if (bookingUser != null && bookingCompany != null) {
						bookingUser.initLogoDisplayable();
						bookingCompany.initLogoDisplayable();
						session.setAttribute("isCorporate", bookingCompany.getCompanyRole().isCorporate());
						parentCompany = emailDao.getParentCompany(bookingCompany);
						parentCompany.initLogoDisplayable();
						if (parentCompany != null) {
							parentUser = emailDao.getCompanyWalletUser(parentCompany);
							if (parentUser != null) {
								parentCompany.initLogoDisplayable();
								parentUser.initLogoDisplayable();
								BigDecimal finalPrice = new BigDecimal("0.00");

								if(hotelOrderRow.getHotelOrderRowServiceTax() != null){
									session.setAttribute("isServiceTax",true);
									session.setAttribute("isGstTax",false);

									finalPrice = hotelOrderRow.getFinalPrice().add(hotelOrderRow.getHotelOrderRowServiceTax()
											.getManagementFee());

									BigDecimal BaseServiceTax = new BigDecimal("0.00");
									BaseServiceTax = finalPrice
											.divide(new BigDecimal("100.0"))
											.multiply(hotelOrderRow.getHotelOrderRowServiceTax().getBasicTax() != null
											? hotelOrderRow.getHotelOrderRowServiceTax().getBasicTax() : new BigDecimal(0));
									BigDecimal SBC = new BigDecimal("0.00");
									SBC = finalPrice
											.divide(new BigDecimal("100.0"))
											.multiply(hotelOrderRow.getHotelOrderRowServiceTax().getSwatchBharathCess() != null
											? hotelOrderRow.getHotelOrderRowServiceTax().getSwatchBharathCess()
													: new BigDecimal(0));
									BigDecimal KKC = new BigDecimal("0.00");
									KKC = finalPrice
											.divide(new BigDecimal("100.0"))
											.multiply(hotelOrderRow.getHotelOrderRowServiceTax().getKrishiKalyanCess() != null
											? hotelOrderRow.getHotelOrderRowServiceTax().getKrishiKalyanCess()
													: new BigDecimal(0));
									BigDecimal TotalServiceTax = new BigDecimal("0.00");
									TotalServiceTax = finalPrice
											.divide(new BigDecimal("100.0"))
											.multiply(hotelOrderRow.getHotelOrderRowServiceTax().getTotalTax() != null
											? hotelOrderRow.getHotelOrderRowServiceTax().getTotalTax() : new BigDecimal(0));

									session.setAttribute("BaseServiceTax", BaseServiceTax.setScale(2, RoundingMode.UP));
									session.setAttribute("SBC", SBC.setScale(2, RoundingMode.UP));
									session.setAttribute("KKC", KKC.setScale(2, RoundingMode.UP));
									session.setAttribute("TotalServiceTax", TotalServiceTax.setScale(2, RoundingMode.UP));
									session.setAttribute("ConvenienceFee", hotelOrderRow.getHotelOrderRowServiceTax()
											.getConvenienceFee().setScale(2, RoundingMode.UP));
									session.setAttribute("ManagementFee", hotelOrderRow.getHotelOrderRowServiceTax()
											.getManagementFee().setScale(2, RoundingMode.UP));


									// If corporate true add all tax and management fees
									if (bookingCompany.getCompanyRole().isCorporate())
										totWithGst = totWithGst.add(TotalServiceTax
												.add(hotelOrderRow.getHotelOrderRowServiceTax().getManagementFee())
												.add(hotelOrderRow.getHotelOrderRowServiceTax().getConvenienceFee()));
								}else if(hotelOrderRow.getHotelOrderRowGstTax() != null){
									session.setAttribute("isServiceTax",false);
									session.setAttribute("isGstTax",true);

									BigDecimal ManagementFee = new BigDecimal("0.00");
									ManagementFee = hotelOrderRow.getHotelOrderRowGstTax().getManagementFee()!=null?hotelOrderRow.getHotelOrderRowGstTax().getManagementFee():new BigDecimal("0.00");
									BigDecimal CGST = new BigDecimal("0.00");
									CGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(hotelOrderRow.getHotelOrderRowGstTax().getCGST()!=null?hotelOrderRow.getHotelOrderRowGstTax().getCGST():new BigDecimal("0.00"));
									BigDecimal SGST = new BigDecimal("0.00");
									SGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(hotelOrderRow.getHotelOrderRowGstTax().getSGST()!=null?hotelOrderRow.getHotelOrderRowGstTax().getSGST():new BigDecimal("0.00"));
									BigDecimal IGST = new BigDecimal("0.00");
									IGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(hotelOrderRow.getHotelOrderRowGstTax().getIGST()!=null?hotelOrderRow.getHotelOrderRowGstTax().getIGST():new BigDecimal("0.00"));
									BigDecimal UGST = new BigDecimal("0.00");
									UGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(hotelOrderRow.getHotelOrderRowGstTax().getUGST()!=null?hotelOrderRow.getHotelOrderRowGstTax().getUGST():new BigDecimal("0.00"));
									BigDecimal TotalGST = new BigDecimal("0.00");
									TotalGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(hotelOrderRow.getHotelOrderRowGstTax().getTotalGst()!=null?hotelOrderRow.getHotelOrderRowGstTax().getTotalGst():new BigDecimal("0.00"));

									BigDecimal convenienceFee =  hotelOrderRow.getHotelOrderRowGstTax().getConvenienceFee()!=null?hotelOrderRow.getHotelOrderRowGstTax().getConvenienceFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");

									session.setAttribute("CGSTPercentage", hotelOrderRow.getHotelOrderRowGstTax().getCGST().setScale(2, RoundingMode.UP));
									session.setAttribute("SGSTPercentage", hotelOrderRow.getHotelOrderRowGstTax().getSGST().setScale(2, RoundingMode.UP));
									session.setAttribute("IGSTPercentage", hotelOrderRow.getHotelOrderRowGstTax().getIGST().setScale(2, RoundingMode.UP));
									session.setAttribute("UGSTPercentage", hotelOrderRow.getHotelOrderRowGstTax().getUGST().setScale(2, RoundingMode.UP));

									session.setAttribute("CGST", CGST.setScale(2, RoundingMode.UP));
									session.setAttribute("SGST", SGST.setScale(2, RoundingMode.UP));
									session.setAttribute("IGST", IGST.setScale(2, RoundingMode.UP));
									session.setAttribute("UGST", UGST.setScale(2, RoundingMode.UP));
									session.setAttribute("TotalGST", TotalGST.setScale(2, RoundingMode.UP));
									session.setAttribute("ConvenienceFee",convenienceFee.setScale(2, RoundingMode.UP));
									session.setAttribute("ManagementFee",ManagementFee.setScale(2, RoundingMode.UP));
									// If corporate true add all tax and management fees
									if (bookingCompany.getCompanyRole().isCorporate())
										totWithGst = totWithGst.add(TotalGST.add(ManagementFee).add(convenienceFee));

								}else{
									session.setAttribute("isServiceTax",false);
									session.setAttribute("isGstTax",false);
									session.setAttribute("ConvenienceFee",new BigDecimal("0.00"));
									session.setAttribute("ManagementFee",new BigDecimal("0.00"));
								}

								BigDecimal baseWithTax = new BigDecimal("0.0");
								baseWithTax = basefare.add(taxes);
								BigDecimal singlePassCost = new BigDecimal("0.0");
								singlePassCost = ArithmeticUtil.divideTo2Decimal(baseWithTax, (new BigDecimal(refinedGuestList.size())));     //baseWithTax.divide(new BigDecimal(refinedGuestList.size())).setScale(0,RoundingMode.UP);
								session.setAttribute("singlePassCost", singlePassCost.setScale(2,RoundingMode.UP));
								session.setAttribute("baseWithTax", baseWithTax.setScale(2,RoundingMode.UP));
								session.setAttribute("totWithGst", totWithGst.setScale(2,RoundingMode.UP));
								session.setAttribute("amountinwords",ArithmeticUtil.DecimalValueCheacker(totWithGst.setScale(0, RoundingMode.UP).toString()));

								session.setAttribute("userid", bookingUser.getId());
								session.setAttribute("parentUserid", parentUser.getId());

								emailDao.getHotelOrderInvoiceDetails(session, orderId, email.getType(),
										bookingUser.getId(), parentUser.getId());

								session.setAttribute("address",
										bookingCompany.getBillingaddress().replaceAll("[2C%-+.^:,]", " "));
								session.setAttribute("hotelOrderRowId", hotelOrderRow);
								if (roomDeatailsList != null && roomDeatailsList.size() > 0)
									session.setAttribute("roomDeatailsList", roomDeatailsList.get(0));
								session.setAttribute("guestList", refinedGuestList);
								if (cancelpoliciesRefined != null && cancelpoliciesRefined.size() > 0)
									session.setAttribute("cancelpolicies", cancelpoliciesRefined.get(0));
								session.setAttribute("numberOfNights", numberOfNights);
								session.setAttribute("manualfield1",manualField1 );
								session.setAttribute("manualfield2",manualField2 );
								session.setAttribute("manualfield3",manualField3 );
								session.setAttribute("manualfield4",manualField4 );
								session.setAttribute("manualfield5", manualField5);

								Map<String, Object> variables = new HashMap<String, Object>();
								variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
								variables.put("hotelorder", hotelOrderRow);
								variables.put("rmdetail", rmConfigTripDetailsModel);
								variables.put("isRmStruct",isRmStruct);
								//added by basha
								variables.put("manualrmfield1", manualField1rmconfig);
								variables.put("manualrmfield2", manualField2rmconfig);
								variables.put("manualrmfield3", manualField3rmconfig);
								variables.put("manualrmfield4", manualField4rmconfig);
								variables.put("manualrmfield5", manualField5rmconfig);
								//ended by basha
								variables.put("user", bookingUser);
								variables.put("company", bookingCompany);
								variables.put("companyEntity", companyEntity);
								variables.put("companylogo", CommonConfig.GetCommonConfig().getImage_path()
										+ bookingCompany.getCompanyid());
								variables.put("parentUser", parentUser);
								variables.put("parentComapny", parentCompany);
								variables.put("baseUrl", request.getScheme() + "://" + request.getServerName() + ":"
										+ request.getServerPort());
								final IWebContext ctx = new SpringWebContext(request, response, servletContext,
										locale, variables, applicationContext);
								htmlContent = emailContentService.createHotelOrderInvoiceMail(bookingCompany, ctx);

								if (!email.isOnlyHtmlContent()) {
									emailService.sendHotelOrderInvoiceMail(hotelOrderRow, bookingUser,
											bookingCompany, MediaType.IMAGE_PNG_VALUE, locale, request, response,
											servletContext, applicationContext, parentUser, parentCompany, email,
											htmlContent);
									// make it null after email is sent so
									// as to avoid the cache return data
									htmlContent = null;
								}
							} else {
								logger.error("########################## invoice error::Parent user is missing.");
								throw new Exception("Parent user is missing...");
							}
						} else {
							logger.error("########################## invoice error::Parent company is missing.");
							throw new Exception("Parent company is missing...");
						}


					} else {
						logger.error("########################## invoice error::Booking user / company is missing....");
						throw new Exception("Booking user / company is missing...");

					}

				} catch (Exception e) {
					logger.error("########################## invoice error::Booking user / company is missing....", e);
					throw new Exception("Booking user / company is missing...");

				}

			} else {
				throw new Exception("Hotel order not found");
			}

		}
		return htmlContent;
	}

	public static String  sendCreditNoteIssueMailHotelOld(Email email, HttpServletRequest request,
			HttpServletResponse response, Locale locale, int emailTypeSuperInvoiceToOthers, HotelOrderRowEmailDao hotelOrderRowEmailDao, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService, HotelCreditNoteDao hotelCreditNoteDao) throws MessagingException, MailException, NullPointerException,
	UnsupportedEncodingException, DocumentException, IOException, Exception {
		String htmlContent = null;
		if (email != null) {
			String creditNoteId = email.getOrderId();
			HotelCreditNote hotelCreditNote = hotelCreditNoteDao.getHotelCreditNoteByRowId(creditNoteId);
			if (hotelCreditNote != null) {
				HotelOrderRow hotelOrderRow = hotelOrderRowEmailDao
						.hotelOrderRowByRowId(String.valueOf(hotelCreditNote.getRowId()));
				if (hotelOrderRow != null) {
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Company company = null; 					
					Company childCompany = null;
					User user = null;
					BigDecimal totalAmount = new BigDecimal("0.0");
					HttpSession session = request.getSession();
					String checkIn = formatter.format(hotelOrderRow.getCheckInDate());
					String checkOut = formatter.format(hotelOrderRow.getCheckOutDate());
					DateTime dt1 = new DateTime(checkIn);
					DateTime dt2 = new DateTime(checkOut);				
					int numberOfNights = Days.daysBetween(dt1, dt2).getDays();
					List<HotelOrderRoomInfo> roomDeatailsList = hotelOrderRowEmailDao
							.roomInfoDeatails(hotelOrderRow);
					List<HotelOrderGuest> guestList = hotelOrderRowEmailDao
							.guesInformationDeatails(roomDeatailsList);
					List<HotelOrderCancellationPolicy> cancelpolicies = hotelOrderRowEmailDao
							.getCancelPolicies(hotelOrderRow);
					// to be converted into booking price...
					try {
						BigDecimal finalprice = hotelOrderRow.getFinalPrice();
						hotelOrderRow.setFinalPrice(finalprice.setScale(2, BigDecimal.ROUND_HALF_UP));
						BigDecimal taxes = hotelOrderRow.getTaxes();
						taxes = taxes.multiply(hotelOrderRow.getApiToBaseExchangeRate());
						taxes = taxes.multiply(hotelOrderRow.getBaseToBookingExchangeRate());
						hotelOrderRow.setTaxes(taxes.setScale(2, BigDecimal.ROUND_HALF_UP));
						BigDecimal basefare = finalprice.subtract(taxes);
						BigDecimal servicecharges = hotelOrderRow.getFeeAmount();
						hotelOrderRow.setFeeAmount(servicecharges.setScale(2, BigDecimal.ROUND_HALF_UP));
						BigDecimal proFees = finalprice.subtract(servicecharges).setScale(2,
								BigDecimal.ROUND_HALF_UP);
						session.setAttribute("basefare", proFees);
					} catch (Exception e) {
						logger.info("taxes amount to booking currency....Exception----  ");

					}
					if(roomDeatailsList!=null && roomDeatailsList.size()>0)
						session.setAttribute("roomDeatailsList", roomDeatailsList.get(0));
					session.setAttribute("guestList", guestList);
					if(cancelpolicies!=null && cancelpolicies.size()>0)
						session.setAttribute("cancelpolicies", cancelpolicies.get(0));
					else
						session.setAttribute("cancelpolicies", new HotelOrderCancellationPolicy());
					session.setAttribute("numberOfNights", numberOfNights);

					user = allEmailDao.getUserByUserId(hotelCreditNote.getUserId());
					if (user != null) {
						user.initLogoDisplayable();						
						company = emailDao.getUserCompany(user);
						if (company != null) {
							company.initLogoDisplayable();
							childCompany = emailDao.getImmediateChildCompanyBooked(company,
									Integer.valueOf(hotelOrderRow.getUserId()));
							if (childCompany != null) {
								childCompany.initLogoDisplayable();
								session.setAttribute("cname",
										childCompany.getCompanyname().replaceAll("[2C%-+.^:,]", " "));
								session.setAttribute("cemail", childCompany.getEmail());
								session.setAttribute("cphone", childCompany.getPhone());
								session.setAttribute("caddress", childCompany.getAddress() != null
										? childCompany.getAddress().replaceAll("[2C%-+.^:,]", " ") : "");
								session.setAttribute("ccity", childCompany.getCity() != null
										? childCompany.getCity().replaceAll("[2C%-+.^:,]", " ") : "");
								session.setAttribute("ccountry", childCompany.getCountryname() != null
										? childCompany.getCountryname().replaceAll("[2C%-+.^:,]", " ") : "");
								session.setAttribute("cweb", childCompany.getWebsite() != null
										? childCompany.getWebsite().replaceAll("[2C%-+.^:,]", " ") : "");

								session.setAttribute("pcname",
										company.getCompanyname().replaceAll("[2C%-+.^:,]", " "));
								session.setAttribute("pcemail", company.getEmail());
								session.setAttribute("pcphone", company.getPhone());
								session.setAttribute("pcaddress", company.getAddress() != null
										? company.getAddress().replaceAll("[2C%-+.^:,]", " ") : "");
								session.setAttribute("pccity", company.getCity() != null
										? company.getCity().replaceAll("[2C%-+.^:,]", " ") : "");
								session.setAttribute("pccountry", company.getCountryname() != null
										? company.getCountryname().replaceAll("[2C%-+.^:,]", " ") : "");
								session.setAttribute("pcweb", company.getWebsite() != null
										? company.getWebsite().replaceAll("[2C%-+.^:,]", " ") : "");

								session.setAttribute("cancelfee",
										(hotelCreditNote.getCancellationFees() != null ? hotelCreditNote
												.getCancellationFees().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("convinfee",
										(hotelCreditNote.getConvenienceFees() != null ? hotelCreditNote
												.getConvenienceFees().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("bookamount",
										(hotelCreditNote.getTotalBookingAmount() != null ? hotelCreditNote
												.getTotalBookingAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("refundamount",
										(hotelCreditNote.getRefundedAmount() != null ? hotelCreditNote
												.getRefundedAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("gstamount",
										(hotelCreditNote.getGstAmount() != null
										? hotelCreditNote.getGstAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("currency", hotelOrderRow.getBookedCurrency());							

								Map<String, Object> variables = new HashMap<String, Object>();
								variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
								variables.put("hotelcreditnote", hotelCreditNote);
								variables.put("hotelorder", hotelOrderRow);
								variables.put("user", user);
								variables.put("company", childCompany);
								variables.put("parentcompany", company);
								@SuppressWarnings("unchecked")
								HashMap<String, String> datetimemap = CommonUtil.getDateTimeMapFromTimestamp(hotelCreditNote.getIssuedAt());
								variables.put("requestdate", datetimemap.get("date"));
								variables.put("requesttime", datetimemap.get("time"));
								variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());

								final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale, variables,
										applicationContext);

								htmlContent = emailContentService.sendCreditNoteIssueHotel(company, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
										applicationContext, ctx);
								if (!email.isOnlyHtmlContent()) {
									emailService.sendCreditNoteIssueHotel(user,
											childCompany, company, locale, request, response, servletContext,
											applicationContext,htmlContent, email, hotelOrderRow);
									emailNotificationDao.insertEmailNotification(company, company, childCompany,
											user, EmailNotification.ACTION_PARENT_TO_CHILD, email);

									logger.info("############## credit issue note  sent--  ");
								}								
							} else {
								logger.info("credit note request parent compnay not found  :");
								throw new Exception(
										"############## credit note request parent Company not found for credit note");
							}
						} else {
							logger.info("credit note request compnay not found  :");
							throw new Exception(
									"############## credit note request Company not found for credit note");
						}

					} else {
						logger.info("credit note request user not found  :");
						throw new Exception("############## credit note request User not found for credit note");
					}

				} else {
					throw new Exception(
							"############## hotel credit note request hotel order not found for credit note");
				}
			} else {
				throw new Exception(
						"############## hotel credit note request Creditnote not found for credit note");
			}

		}
		return htmlContent;
	}

	public static void sendCreditNoteRequestMailHotel(Email email, HttpServletRequest request,
			HttpServletResponse response, Locale locale, int emailTypeSuperInvoiceToOthers, HotelOrderRowEmailDao hotelOrderRowEmailDao, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService, HotelCreditNoteDao hotelCreditNoteDao) throws MessagingException, MailException, NullPointerException,
	UnsupportedEncodingException, DocumentException, IOException, Exception {
		if (email != null) {
			String creditNoteId = email.getOrderId();
			HotelCreditNote hotelCreditNote = hotelCreditNoteDao.getHotelCreditNoteByRowId(creditNoteId);
			if (hotelCreditNote != null) {
				HotelOrderRow hotelOrderRow = hotelOrderRowEmailDao
						.hotelOrderRowByRowId(String.valueOf(hotelCreditNote.getRowId()));
				if (hotelOrderRow != null) {
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Company company = null;
					Company parentCompany = null;
					User user = null;
					BigDecimal totalAmount = new BigDecimal("0.0");
					HttpSession session = request.getSession();
					String checkIn = formatter.format(hotelOrderRow.getCheckInDate());
					String checkOut = formatter.format(hotelOrderRow.getCheckOutDate());
					DateTime dt1 = new DateTime(checkIn);
					DateTime dt2 = new DateTime(checkOut);				
					int numberOfNights = Days.daysBetween(dt1, dt2).getDays();
					List<HotelOrderRoomInfo> roomDeatailsList = hotelOrderRowEmailDao
							.roomInfoDeatails(hotelOrderRow);
					List<HotelOrderGuest> guestList = hotelOrderRowEmailDao
							.guesInformationDeatails(roomDeatailsList);
					List<HotelOrderCancellationPolicy> cancelpolicies = hotelOrderRowEmailDao
							.getCancelPolicies(hotelOrderRow);
					// to be converted into booking price...
					try {
						BigDecimal finalprice = hotelOrderRow.getFinalPrice();
						hotelOrderRow.setFinalPrice(finalprice.setScale(2, BigDecimal.ROUND_HALF_UP));
						BigDecimal taxes = hotelOrderRow.getTaxes();
						taxes = taxes.multiply(hotelOrderRow.getApiToBaseExchangeRate());
						taxes = taxes.multiply(hotelOrderRow.getBaseToBookingExchangeRate());
						hotelOrderRow.setTaxes(taxes.setScale(2, BigDecimal.ROUND_HALF_UP));
						BigDecimal basefare = finalprice.subtract(taxes);
						BigDecimal servicecharges = hotelOrderRow.getFeeAmount();
						hotelOrderRow.setFeeAmount(servicecharges.setScale(2, BigDecimal.ROUND_HALF_UP));
						BigDecimal proFees = finalprice.subtract(servicecharges).setScale(2,
								BigDecimal.ROUND_HALF_UP);
						session.setAttribute("basefare", proFees);
					} catch (Exception e) {
						logger.info("taxes amount to booking currency....Exception----  ");

					}
					session.setAttribute("roomDeatailsList", roomDeatailsList.get(0));
					session.setAttribute("guestList", guestList);
					if(cancelpolicies.size() > 0)
						session.setAttribute("cancelpolicies", cancelpolicies.get(0));
					session.setAttribute("numberOfNights", numberOfNights);
					company = emailDao.getCompanyByCompanyUserId(hotelCreditNote.getCompanyId());
					parentCompany = emailDao.getParentCompany(company);
					user = allEmailDao.getUserByUserId(hotelCreditNote.getUserId());

					user = allEmailDao.getUserByUserId(hotelCreditNote.getUserId());
					if (user != null) {
						user.initLogoDisplayable();						
						company = emailDao.getUserCompany(user);
						if (company != null) {							
							company.initLogoDisplayable();
							parentCompany = emailDao.getParentCompany(company);
							if (parentCompany != null) {								
								parentCompany.initLogoDisplayable();
								session.setAttribute("cname",
										company.getCompanyname().replaceAll("[2C%-+.^:,]", " "));
								session.setAttribute("cemail", company.getEmail());
								session.setAttribute("cphone", company.getPhone());
								session.setAttribute("caddress", company.getAddress() != null
										? company.getAddress().replaceAll("[2C%-+.^:,]", " ") : "");
								session.setAttribute("ccity", company.getCity() != null
										? company.getCity().replaceAll("[2C%-+.^:,]", " ") : "");
								session.setAttribute("ccountry", company.getCountryname() != null
										? company.getCountryname().replaceAll("[2C%-+.^:,]", " ") : "");
								session.setAttribute("cweb", company.getWebsite() != null
										? company.getWebsite().replaceAll("[2C%-+.^:,]", " ") : "");

								session.setAttribute("pcname",
										parentCompany.getCompanyname().replaceAll("[2C%-+.^:,]", " "));
								session.setAttribute("pcemail", parentCompany.getEmail());
								session.setAttribute("pcphone", parentCompany.getPhone());
								session.setAttribute("pcaddress", parentCompany.getAddress() != null
										? parentCompany.getAddress().replaceAll("[2C%-+.^:,]", " ") : "");
								session.setAttribute("pccity", parentCompany.getCity() != null
										? parentCompany.getCity().replaceAll("[2C%-+.^:,]", " ") : "");
								session.setAttribute("pccountry", parentCompany.getCountryname() != null
										? parentCompany.getCountryname().replaceAll("[2C%-+.^:,]", " ") : "");
								session.setAttribute("pcweb", parentCompany.getWebsite() != null
										? parentCompany.getWebsite().replaceAll("[2C%-+.^:,]", " ") : "");

								session.setAttribute("cancelfee",
										(hotelCreditNote.getCancellationFees() != null ? hotelCreditNote
												.getCancellationFees().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("convinfee",
										(hotelCreditNote.getConvenienceFees() != null ? hotelCreditNote
												.getConvenienceFees().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("bookamount",
										(hotelCreditNote.getTotalBookingAmount() != null ? hotelCreditNote
												.getTotalBookingAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("refundamount",
										(hotelCreditNote.getRefundedAmount() != null ? hotelCreditNote
												.getRefundedAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("gstamount",
										(hotelCreditNote.getGstAmount() != null
										? hotelCreditNote.getGstAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("currency", hotelOrderRow.getBookedCurrency());

								if (company.getCompanyid() != parentCompany.getCompanyid()) {
									emailService.sendCreditNoteRequestHotel(hotelOrderRow, hotelCreditNote,
											user, company, parentCompany, locale, request, response, servletContext,
											applicationContext);
									logger.info("############## hotel credit note request sent--  ");				

								} else {
									logger.info(
											"############## hotel  credit note request same company internal request : "
													+ company.getCompanyname());
								}

							} else {
								logger.info("credit note request parent compnay not found  :");
								throw new Exception(
										"############## credit note request parent Company not found for credit note");
							}
						} else {
							logger.info("credit note request compnay not found  :");
							throw new Exception(
									"############## credit note request Company not found for credit note");
						}
					} else {
						logger.info("credit note request user not found  :");
						throw new Exception("############## credit note request User not found for credit note");
					}
				} else {
					throw new Exception(
							"############## hotel credit note request hotel order not found for credit note");
				}
			} else {
				throw new Exception(
						"############## hotel credit note request Creditnote not found for credit note");
			}
		}
	}

	public static  String  sendCreditNoteIssueMailHotel(Email email, HttpServletRequest request,
			HttpServletResponse response, Locale locale, int emailTypeSuperInvoiceToOthers, HotelOrderRowEmailDao hotelOrderRowEmailDao, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService, HotelCreditNoteDao hotelCreditNoteDao,HotelSearchRoomDetailDao hotelSearchRoomDetailDao,RmConfigDetailDAO rmConfigDetailDAO, CompanyDao companyDao) throws MessagingException, MailException, NullPointerException,
	UnsupportedEncodingException, DocumentException, IOException, Exception {
		String htmlContent = null;
		boolean isRmStruct=false;
		if (email != null) {
			String creditNoteId = email.getOrderId();

			HotelCreditNote creditNote = hotelCreditNoteDao.getHotelCreditNoteByRowId(creditNoteId);

			if (creditNote != null) {
				HotelOrderRow hotelOrderRow = null;
				Company bookingCompany = null;
				CompanyEntity companyEntity=null;
				User bookingUser = null;
				RmConfigTripDetailsModel rmConfigTripDetailsModel = null;
				List<String> fieldNameArray = new ArrayList<String>();

				Company parentCompany = null;
				User parentUser = null;

				Company loginCompanyObj = allEmailDao.getCompanyByCompanyId(creditNote.getCompanyId());

				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String orderId = email.getOrderId();
				HttpSession session = request.getSession();
				hotelOrderRow = hotelOrderRowEmailDao.hotelOrderRowByRowId(String.valueOf(creditNote.getRowId()));
				if (hotelOrderRow != null) 
				{
					LinkedList<Company> companyListBottomToTop= new LinkedList<>();
					Company reciverCompany = null;	

					LinkedHashMap<String, HotelOrderRowMarkup> hotelOrderRowMarkupMap = new LinkedHashMap<>();
					LinkedHashMap<String, HotelCreditNote> creditNoteMarkupMap = new LinkedHashMap<>();
					companyListBottomToTop = CommonUtil.getParentCompanyBottomToTop(Integer.parseInt(creditNote.getCompanyId()),companyDao);
					if(companyListBottomToTop!=null && companyListBottomToTop.size()>0)
					{

						if(hotelOrderRow.getHotelOrderRowMarkupList()!=null && hotelOrderRow.getHotelOrderRowMarkupList().size()>0)
						{
							for(HotelOrderRowMarkup hotelOrderRowMarkup:hotelOrderRow.getHotelOrderRowMarkupList())
							{
								hotelOrderRowMarkupMap.put(hotelOrderRowMarkup.getCompanyId(), hotelOrderRowMarkup);
							}
						}
					}

					bookingCompany = allEmailDao.getCompanyByCompanyId(hotelOrderRow.getCompanyId());
					//ADDED BY BASHA FOR TERMS AND CONDITIONS ERROR WHILE GETTING INVOICE OR CREDITnOTE
					Company superUserObj=allEmailDao.getSuperCompanyByCompanyUserId(bookingCompany.getSuper_company_userid());
					if(bookingCompany.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()==null){
						bookingCompany.setTemsandcondtions("NA");
						superUserObj.setTemsandcondtions("NA");
					}else if(bookingCompany.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()!=null){
						bookingCompany.setTemsandcondtions(superUserObj.getTemsandcondtions());
					}else if(bookingCompany.getTemsandcondtions()==null){
						bookingCompany.setTemsandcondtions("NA");
					}
					StateInfo stateInfo = allEmailDao.getStateInfo(bookingCompany.getBillingstate());	 
					if(stateInfo !=null)
						bookingCompany.setStateCode(stateInfo.getStateCode());

					if(hotelOrderRow.getCompanyEntityId()!=null ){
						companyEntity = allEmailDao.getCompanyEntityByCompanyId(hotelOrderRow.getCompanyEntityId());
						bookingCompany=companyEntity.getCompany();
					}
					bookingUser = allEmailDao.getUserByEmail(bookingCompany.getEmail());
					rmConfigTripDetailsModel = rmConfigDetailDAO.getRmConfigDetail(hotelOrderRow.getOrderReference());
					RmConfigModel rmConfigModel = rmConfigDetailDAO.getRmConfigModel(bookingCompany.getCompanyid());
					String manualField1rmconfig=null;
					String manualField2rmconfig=null;
					String manualField3rmconfig=null;
					String manualField4rmconfig=null;
					String manualField5rmconfig=null;
					String manualField1=null;
					String manualField2=null;
					String manualField3=null;
					String manualField4=null;
					String manualField5=null;

					if(rmConfigTripDetailsModel!=null){
						if(rmConfigTripDetailsModel.getManualField1()!=null && !rmConfigTripDetailsModel.getManualField1().trim().equalsIgnoreCase("")){
							manualField1rmconfig=rmConfigTripDetailsModel.getManualField1();
						}
						if(rmConfigTripDetailsModel.getManualField2()!=null && !rmConfigTripDetailsModel.getManualField2().trim().equalsIgnoreCase("")){
							manualField2rmconfig=rmConfigTripDetailsModel.getManualField2();
						}
						if(rmConfigTripDetailsModel.getManualField3()!=null && !rmConfigTripDetailsModel.getManualField3().trim().equalsIgnoreCase("")){
							manualField3rmconfig=rmConfigTripDetailsModel.getManualField3();
						}
						if(rmConfigTripDetailsModel.getManualField4()!=null && !rmConfigTripDetailsModel.getManualField4().trim().equalsIgnoreCase("")){
							manualField4rmconfig=rmConfigTripDetailsModel.getManualField4();
						}
						if(rmConfigTripDetailsModel.getManualField5()!=null && !rmConfigTripDetailsModel.getManualField5().trim().equalsIgnoreCase("")){
							manualField5rmconfig=rmConfigTripDetailsModel.getManualField5();
						}
					}
					if(hotelOrderRow.getHotelOrderRowRmConfigStruct()!=null) 
						 isRmStruct=true;
					String dynamicSchema=rmConfigModel!=null?rmConfigModel.getDynamicFieldsData():"";
					if(isRmStruct) 
						dynamicSchema=hotelOrderRow.getHotelOrderRowRmConfigStruct().getRmDynamicData();
					String manualStringFields[] = dynamicSchema!=null && !dynamicSchema.trim().equalsIgnoreCase("") ?dynamicSchema.split(","):null;
						if(manualStringFields!=null && manualStringFields.length>0){
							for(String oneField:manualStringFields){
								if(!oneField.trim().equalsIgnoreCase("")){
									String fieldsName[]=oneField.split(":");
									fieldNameArray.add(fieldsName[0]);
								}

							}
						}
					 

					if(fieldNameArray!=null && fieldNameArray.size()>0){
						if(fieldNameArray.get(0)!=null && !fieldNameArray.get(0).trim().equalsIgnoreCase("")){
							manualField1=fieldNameArray.get(0);
						}
						if(fieldNameArray.size()>1 && fieldNameArray.get(1)!=null && !fieldNameArray.get(1).trim().equalsIgnoreCase("")){
							manualField2=fieldNameArray.get(1);
						}
						if(fieldNameArray.size()>2 && fieldNameArray.get(2)!=null && !fieldNameArray.get(2).trim().equalsIgnoreCase("")){
							manualField3=fieldNameArray.get(2);
						}
						if(fieldNameArray.size()>3 && fieldNameArray.get(3)!=null && !fieldNameArray.get(3).trim().equalsIgnoreCase("")){
							manualField4=fieldNameArray.get(3);
						}
						if(fieldNameArray.size()>4 && fieldNameArray.get(4)!=null && !fieldNameArray.get(4).trim().equalsIgnoreCase("")){
							manualField5=fieldNameArray.get(4);
						}

					}

					List<HotelCreditNote>   newCreditNoteList= hotelCreditNoteDao.getCreditNoteListByOrderRowID(hotelOrderRow.getId());

					BigDecimal totManagementFees=new BigDecimal("0.00");
					BigDecimal totConvenienceFee=new BigDecimal("0.00");
					BigDecimal totCancellationFee=new BigDecimal("0.00");
					BigDecimal totRefundAmt = new BigDecimal("0.00");

					String creditNoteNumber = null;
					Timestamp creditNoteDate= null;
					if(hotelOrderRow!=null && hotelOrderRow.isCreditNoteIssued())
					{
						if(creditNote!=null){
							if(newCreditNoteList!=null && newCreditNoteList.size()>0)
							{
								for(HotelCreditNote creditNoteInner:newCreditNoteList)
								{
									creditNoteMarkupMap.put(creditNoteInner.getCompanyId(), creditNoteInner);
								}
							} 
						}
						java.util.Iterator<Company> coIterator = companyListBottomToTop.descendingIterator();
						while(coIterator.hasNext())
						{
							Company companyInner = coIterator.next();
							HotelCreditNote creditNoteInner =creditNoteMarkupMap.get(String.valueOf(companyInner.getCompanyid()));
							Company creditNoteGerateCompany = allEmailDao.getCompanyByUserId(creditNoteInner.getUserId());
							if(creditNoteInner!=null)
							{
								if(bookingCompany.getCompanyRole().isSuperUser() && String.valueOf(companyInner.getCompanyid()).equals(String.valueOf(bookingCompany.getCompanyid())))
								{
									totConvenienceFee = totConvenienceFee.add(creditNoteInner.getConvenienceFees().setScale(2, RoundingMode.UP));
									totCancellationFee = totCancellationFee.add(creditNoteInner.getCancellationFees().setScale(2, RoundingMode.UP));
									totManagementFees = totManagementFees.add(creditNoteInner.getManagementFees().setScale(2, RoundingMode.UP));
									totRefundAmt = totRefundAmt.add(creditNoteInner.getRefundedAmount());
									break;
								}
								else{

									if(String.valueOf(companyInner.getCompanyid()).equals(creditNote.getCompanyId()))
									{
										totConvenienceFee = totConvenienceFee.add(creditNoteInner.getConvenienceFees().setScale(2, RoundingMode.UP));
										totCancellationFee = totCancellationFee.add(creditNoteInner.getCancellationFees().setScale(2, RoundingMode.UP));
										totManagementFees = totManagementFees.add(creditNoteInner.getManagementFees().setScale(2, RoundingMode.UP));
										totRefundAmt = totRefundAmt.add(creditNoteInner.getRefundedAmount());
										break;
									}
									if(creditNoteGerateCompany.getCompanyRole().isSuperUser()){
										totConvenienceFee = totConvenienceFee.add(creditNoteInner.getConvenienceFees().setScale(2, RoundingMode.UP));
										totCancellationFee = totCancellationFee.add(creditNoteInner.getCancellationFees().setScale(2, RoundingMode.UP));
										//added by basha
										totManagementFees = totManagementFees.add(creditNoteInner.getManagementFees().setScale(2, RoundingMode.UP));

									}
								}
							}

						}
						creditNoteNumber = creditNote.getCNINumber();
						creditNoteDate = creditNote.getIssuedAt();

					}     
					session.setAttribute("refundAmt", totRefundAmt.setScale(2, RoundingMode.UP));
					session.setAttribute("totManagementFees", totManagementFees.setScale(2, RoundingMode.UP));
					session.setAttribute("totConvenienceFee", totConvenienceFee.setScale(2, RoundingMode.UP));
					session.setAttribute("totCancellationFee", totCancellationFee.setScale(2, RoundingMode.UP));
					session.setAttribute("creditNoteNumber", creditNoteNumber);
					session.setAttribute("creditNoteDate", creditNoteDate);

					BigDecimal gstToBookingCurrency = new BigDecimal(0);
					BigDecimal totWithGst = hotelOrderRow.getFinalPrice().add(gstToBookingCurrency);

					logger.info("Hotel order row id----- : " + hotelOrderRow.getCheckInDate());
					String checkIn = formatter.format(hotelOrderRow.getCheckInDate());
					String checkOut = formatter.format(hotelOrderRow.getCheckOutDate());
					DateTime dt1 = new DateTime(checkIn);
					DateTime dt2 = new DateTime(checkOut);
					logger.info(Days.daysBetween(dt1, dt2).getDays() + " Nights, ");
					int numberOfNights = Days.daysBetween(dt1, dt2).getDays();
					List<HotelOrderRoomInfo> roomDeatailsList = hotelOrderRowEmailDao.roomInfoDeatails(hotelOrderRow);

					List<HotelOrderRoomInfo> roomDeatailsListRefined = new ArrayList<HotelOrderRoomInfo>();
					logger.info("----------Room DeatailsList----- : " + roomDeatailsList.size());
					for (HotelOrderRoomInfo roominfo : roomDeatailsList) {
						logger.info("----------guest Get ID---- : " + roominfo.getId());
						logger.info("-----------guest Meal Type----- : " + roominfo.getMealType());

						List<HotelOrderCancellationPolicy> cancelpoliciesRefined = new ArrayList<HotelOrderCancellationPolicy>();
						for (HotelOrderCancellationPolicy hotelOrderCancellationPolicy : roominfo
								.getHotelOrderCancellationPolicies()) {
							hotelOrderCancellationPolicy.roundOffFeeAmount();
							cancelpoliciesRefined.add(hotelOrderCancellationPolicy);
						}
						roominfo.setHotelOrderCancellationPolicies(cancelpoliciesRefined);
						roomDeatailsListRefined.add(roominfo);
					}

					List<HotelOrderGuest> guestList = hotelOrderRowEmailDao.guesInformationDeatails(roomDeatailsList);
					List<HotelOrderGuest> refinedGuestList = new ArrayList<>();
					if(guestList.size() > 0){
						for (HotelOrderGuest hotelOrderGuest : guestList) {
							if(hotelOrderGuest.getLeader().booleanValue()){
								refinedGuestList.add(hotelOrderGuest);
							}
						}
					}

					List<HotelOrderCancellationPolicy> cancelpolicies = hotelOrderRowEmailDao
							.getCancelPolicies(hotelOrderRow);
					List<HotelOrderCancellationPolicy> cancelpoliciesRefined = new ArrayList<HotelOrderCancellationPolicy>();
					for (HotelOrderCancellationPolicy hotelOrderCancellationPolicy : cancelpolicies) {
						hotelOrderCancellationPolicy.roundOffFeeAmount();
						cancelpoliciesRefined.add(hotelOrderCancellationPolicy);
					}
					// to be converted into booking price..
					BigDecimal basefare = new BigDecimal("0.0");
					BigDecimal taxes  = new BigDecimal("0.0");
					try {
						BigDecimal finalprice = hotelOrderRow.getFinalPrice();
						hotelOrderRow.setFinalPrice(finalprice.setScale(0, RoundingMode.UP));

						taxes = hotelOrderRow.getTaxes();
						taxes = taxes.multiply(hotelOrderRow.getApiToBaseExchangeRate());
						taxes = taxes.multiply(hotelOrderRow.getBaseToBookingExchangeRate());
						hotelOrderRow.setTaxes(taxes.setScale(0, RoundingMode.UP));
						basefare = finalprice.subtract(taxes);
						BigDecimal servicecharges = hotelOrderRow.getFeeAmount();
						hotelOrderRow.setFeeAmount(servicecharges.setScale(2, RoundingMode.UP));
						BigDecimal proFees = finalprice.subtract(servicecharges).setScale(2, RoundingMode.UP);
						session.setAttribute("basefare", basefare.setScale(2, RoundingMode.UP));
						session.setAttribute("taxes", taxes.setScale(2, RoundingMode.UP));


					} catch (Exception e) {
						logger.info("taxes amount to booking currency....Exception----  ");

					}
					String combinationType="";

					if(hotelOrderRow.getSearchKey()!=null){
						HotelSearchRoomDetailTemp hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(hotelOrderRow.getSearchKey().longValue());
						if(hotelOrderRow.getSearchKey()!=null){
							hotelSearchRoomDetail = hotelSearchRoomDetailDao.getHotelSearchRoomDetail(hotelSearchRoomDetail);
							com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay rs = (com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay) SerializationUtils
									.deserialize(hotelSearchRoomDetail.getRoomstay());
							combinationType = rs.getSupplierRoomGroups().get(rs.getSupplierRoomGroups().size() - 1)
									.getInfoSource();
						}
					}

					if (combinationType.equalsIgnoreCase("OpenCombination")) {
						session.setAttribute("roomDeatailsList", roomDeatailsListRefined);
					} else if (combinationType.equalsIgnoreCase("FixedCombination")) {
						session.setAttribute("roomDeatailsList", roomDeatailsListRefined.get(0));
					} else {
						session.setAttribute("roomDeatailsList", roomDeatailsListRefined.get(0));
					}
					session.setAttribute("combinationType", combinationType);

					try {
						if (bookingUser != null && bookingCompany != null) {

							bookingUser.initLogoDisplayable();
							bookingCompany.initLogoDisplayable();
							session.setAttribute("isCorporate", bookingCompany.getCompanyRole().isCorporate());

							if(bookingCompany!=null && !bookingCompany.getCompanyRole().isSuperUser())
							{
								parentCompany = emailDao.getParentCompany(bookingCompany);
							}
							else
								parentCompany= bookingCompany;
							parentCompany.initLogoDisplayable();
							if (parentCompany != null) {
								parentUser = emailDao.getCompanyWalletUser(parentCompany);
								if (parentUser != null) {
									parentCompany.initLogoDisplayable();
									parentUser.initLogoDisplayable();
								}
							}
							BigDecimal finalPrice = new BigDecimal("0.00");

							if(hotelOrderRow.getHotelOrderRowServiceTax() != null){
								session.setAttribute("isServiceTax",true);
								session.setAttribute("isGstTax",false);

								finalPrice = hotelOrderRow.getFinalPrice();
								//gstOrServiceTaxManagementfee=gstOrServiceTaxManagementfee.add(hotelOrderRow.getHotelOrderRowServiceTax().getManagementFee()!=null?hotelOrderRow.getHotelOrderRowServiceTax().getManagementFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00"));

								BigDecimal BaseServiceTax = new BigDecimal("0.00");
								BaseServiceTax = finalPrice
										.divide(new BigDecimal("100.0"))
										.multiply(hotelOrderRow.getHotelOrderRowServiceTax().getBasicTax() != null
										? hotelOrderRow.getHotelOrderRowServiceTax().getBasicTax() : new BigDecimal(0));
								BigDecimal SBC = new BigDecimal("0.00");
								SBC = finalPrice
										.divide(new BigDecimal("100.0"))
										.multiply(hotelOrderRow.getHotelOrderRowServiceTax().getSwatchBharathCess() != null
										? hotelOrderRow.getHotelOrderRowServiceTax().getSwatchBharathCess()
												: new BigDecimal(0));
								BigDecimal KKC = new BigDecimal("0.00");
								KKC = finalPrice
										.divide(new BigDecimal("100.0"))
										.multiply(hotelOrderRow.getHotelOrderRowServiceTax().getKrishiKalyanCess() != null
										? hotelOrderRow.getHotelOrderRowServiceTax().getKrishiKalyanCess()
												: new BigDecimal(0));
								BigDecimal TotalServiceTax = new BigDecimal("0.00");
								TotalServiceTax = finalPrice
										.divide(new BigDecimal("100.0"))
										.multiply(hotelOrderRow.getHotelOrderRowServiceTax().getTotalTax() != null
										? hotelOrderRow.getHotelOrderRowServiceTax().getTotalTax() : new BigDecimal(0));

								session.setAttribute("BaseServiceTax", BaseServiceTax.setScale(2, RoundingMode.UP));
								session.setAttribute("SBC", SBC.setScale(2, RoundingMode.UP));
								session.setAttribute("KKC", KKC.setScale(2, RoundingMode.UP));
								session.setAttribute("TotalServiceTax", TotalServiceTax.setScale(2, RoundingMode.UP));
								session.setAttribute("ConvenienceFee", hotelOrderRow.getHotelOrderRowServiceTax()
										.getConvenienceFee().setScale(2, RoundingMode.UP));
								session.setAttribute("ManagementFee", hotelOrderRow.getHotelOrderRowServiceTax()
										.getManagementFee().setScale(2, RoundingMode.UP));


								// If corporate true add all tax and management fees
								if (bookingCompany.getCompanyRole().isCorporate())
									totWithGst = totWithGst.add(TotalServiceTax
											.add(hotelOrderRow.getHotelOrderRowServiceTax().getManagementFee())
											.add(hotelOrderRow.getHotelOrderRowServiceTax().getConvenienceFee()));
							}else if(hotelOrderRow.getHotelOrderRowGstTax() != null){
								session.setAttribute("isServiceTax",false);
								session.setAttribute("isGstTax",true);

								BigDecimal ManagementFee = new BigDecimal("0.00");								
								BigDecimal convenienceFee  = new BigDecimal("0.00");
								BigDecimal CGST = new BigDecimal("0.00");
								BigDecimal SGST = new BigDecimal("0.00");
								BigDecimal IGST = new BigDecimal("0.00");
								BigDecimal UGST = new BigDecimal("0.00");
								BigDecimal TotalGST = new BigDecimal("0.00");

								//ManagementFee = hotelOrderRow.getHotelOrderRowGstTax().getManagementFee()!=null?hotelOrderRow.getHotelOrderRowGstTax().getManagementFee():new BigDecimal("0.00");

								/*CGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(hotelOrderRow.getHotelOrderRowGstTax().getCGST()!=null?hotelOrderRow.getHotelOrderRowGstTax().getCGST():new BigDecimal("0.00"));
								SGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(hotelOrderRow.getHotelOrderRowGstTax().getSGST()!=null?hotelOrderRow.getHotelOrderRowGstTax().getSGST():new BigDecimal("0.00"));
								IGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(hotelOrderRow.getHotelOrderRowGstTax().getIGST()!=null?hotelOrderRow.getHotelOrderRowGstTax().getIGST():new BigDecimal("0.00"));
								UGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(hotelOrderRow.getHotelOrderRowGstTax().getUGST()!=null?hotelOrderRow.getHotelOrderRowGstTax().getUGST():new BigDecimal("0.00"));
								TotalGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(hotelOrderRow.getHotelOrderRowGstTax().getTotalGst()!=null?hotelOrderRow.getHotelOrderRowGstTax().getTotalGst():new BigDecimal("0.00"));
							    convenienceFee =  hotelOrderRow.getHotelOrderRowGstTax().getConvenienceFee()!=null?hotelOrderRow.getHotelOrderRowGstTax().getConvenienceFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
								 */	
								session.setAttribute("CGSTPercentage", hotelOrderRow.getHotelOrderRowGstTax().getCGST().setScale(2, RoundingMode.UP));
								session.setAttribute("SGSTPercentage", hotelOrderRow.getHotelOrderRowGstTax().getSGST().setScale(2, RoundingMode.UP));
								session.setAttribute("IGSTPercentage", hotelOrderRow.getHotelOrderRowGstTax().getIGST().setScale(2, RoundingMode.UP));
								session.setAttribute("UGSTPercentage", hotelOrderRow.getHotelOrderRowGstTax().getUGST().setScale(2, RoundingMode.UP));

								session.setAttribute("CGST", CGST.setScale(2, RoundingMode.UP));
								session.setAttribute("SGST", SGST.setScale(2, RoundingMode.UP));
								session.setAttribute("IGST", IGST.setScale(2, RoundingMode.UP));
								session.setAttribute("UGST", UGST.setScale(2, RoundingMode.UP));
								session.setAttribute("TotalGST", TotalGST.setScale(2, RoundingMode.UP));
								session.setAttribute("ConvenienceFee",convenienceFee.setScale(2, RoundingMode.UP));
								session.setAttribute("ManagementFee",ManagementFee.setScale(2, RoundingMode.UP));
								// If corporate true add all tax and management fees
								if (bookingCompany.getCompanyRole().isCorporate())
									totWithGst = totWithGst.add(TotalGST.add(ManagementFee).add(convenienceFee));

							}else{
								session.setAttribute("isServiceTax",false);
								session.setAttribute("isGstTax",false);
								session.setAttribute("ConvenienceFee",new BigDecimal("0.00"));
								session.setAttribute("ManagementFee",new BigDecimal("0.00"));
							}

							BigDecimal baseWithTax = new BigDecimal("0.0");					
							baseWithTax = basefare.add(taxes);
							BigDecimal singlePassCost = new BigDecimal("0.0");
							singlePassCost = baseWithTax.divide(new BigDecimal(refinedGuestList.size())).setScale(0,RoundingMode.UP);
							session.setAttribute("singlePassCost", singlePassCost.setScale(2,RoundingMode.UP));
							session.setAttribute("baseWithTax", baseWithTax.setScale(2,RoundingMode.UP));
							session.setAttribute("totWithGst", totWithGst.setScale(2,RoundingMode.UP));
							session.setAttribute("amountinwords",ArithmeticUtil.DecimalValueCheacker(totRefundAmt.setScale(0, RoundingMode.UP).toString()));

							session.setAttribute("userid", bookingUser.getId());
							session.setAttribute("parentUserid", parentUser.getId());

							emailDao.getHotelOrderInvoiceDetails(session, orderId, email.getType(),
									bookingUser.getId(), parentUser.getId());

							session.setAttribute("address",
									bookingCompany.getBillingaddress().replaceAll("[2C%-+.^:,]", " "));
							session.setAttribute("hotelOrderRowId", hotelOrderRow);
							if (roomDeatailsList != null && roomDeatailsList.size() > 0)
								session.setAttribute("roomDeatailsList", roomDeatailsList.get(0));
							session.setAttribute("guestList", refinedGuestList);
							if (cancelpoliciesRefined != null && cancelpoliciesRefined.size() > 0)
								session.setAttribute("cancelpolicies", cancelpoliciesRefined.get(0));
							session.setAttribute("numberOfNights", numberOfNights);
							session.setAttribute("manualfield1",manualField1 );
							session.setAttribute("manualfield2",manualField2 );
							session.setAttribute("manualfield3",manualField3 );
							session.setAttribute("manualfield4",manualField4 );
							session.setAttribute("manualfield5", manualField5);
							hotelOrderRow.setBookingDate(DateConversion.getBluestarDateddMMMYYYY(hotelOrderRow.getBookingDate()));
							Map<String, Object> variables = new HashMap<String, Object>();
							variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
							variables.put("hotelorder", hotelOrderRow);
							variables.put("rmdetail", rmConfigTripDetailsModel);
							variables.put("isRmStruct",isRmStruct);
							//added by basha
							variables.put("manualrmfield1", manualField1rmconfig);
							variables.put("manualrmfield2", manualField2rmconfig);
							variables.put("manualrmfield3", manualField3rmconfig);
							variables.put("manualrmfield4", manualField4rmconfig);
							variables.put("manualrmfield5", manualField5rmconfig);
							//ended by basha
							variables.put("user", bookingUser);
							variables.put("company", bookingCompany);
							variables.put("companyEntity", companyEntity);
							variables.put("companylogo", CommonConfig.GetCommonConfig().getImage_path()
									+ bookingCompany.getCompanyid());
							variables.put("parentUser", parentUser);
							variables.put("parentComapny", parentCompany);
							variables.put("baseUrl", request.getScheme() + "://" + request.getServerName() + ":"
									+ request.getServerPort());
							final IWebContext ctx = new SpringWebContext(request, response, servletContext,
									locale, variables, applicationContext);

							htmlContent = emailContentService.sendCreditNoteIssueHotel(bookingCompany, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
									applicationContext, ctx);
							if (!email.isOnlyHtmlContent()) {
								emailService.sendCreditNoteIssueHotel(bookingUser, bookingCompany,parentCompany ,locale, request, response, servletContext,
										applicationContext,htmlContent, email,hotelOrderRow);
								emailNotificationDao.insertEmailNotification(parentCompany, parentCompany, bookingCompany,
										bookingUser, EmailNotification.ACTION_PARENT_TO_CHILD, email);

								logger.info("############## credit issue note  sent--  ");
							}
							logger.info("############## credit issue note  sent--  ");


						} else {
							logger.info("credit note request compnay not found  :");
							throw new Exception(
									"############## credit note request Company not found for credit note");
						}
					} 
					catch(Exception e)
					{
						throw new Exception(
								"############## hotel credit note request Creditnote not found for credit note");
					}

				}

			}else {
				throw new Exception(
						"############## hotel credit note request Creditnote not found for credit note");
			}

		}else {
			throw new Exception(
					"############## hotel credit note request Creditnote not found for credit note");
		}
		return htmlContent;
	}

	// to be used by getAllHotelFlightCustomerPayment 03-02-2017 by saumya ,to
	// send mail for partial payment
	public static void sendAllHotelCustomerPayment(String InventoryId, HttpServletRequest request,
			HttpServletResponse response, /*
			 * String comments, String
			 * description,
			 */
			List<NotificationDetail> notificationDetails, Company company, User user, HotelAndFlightPaymentTransctionDAO hotelAndFlightPaymentTransctionDAO, EmailService emailService, NotificationDAO notificationDAO, ServletContext servletContext, ApplicationContext applicationContext) {

		PaymentTransaction paymentTransaction = null;
		List<PaymentTransactionDetail> newPaymentTransactionDetailList = null;
		List<PaymentTransactionDetail> paymentTransactionDetailList = new ArrayList<>();
		try {
			final Locale locale = LocaleContextHolder.getLocale();
			Long longInventoryId = Long.parseLong(InventoryId);
			HotelPaymentTxDetailHistory hotelPaymentTxDetailHistory = hotelAndFlightPaymentTransctionDAO
					.getHotelPaymentTxDetailHistory(longInventoryId);
			BigDecimal partialAmtCount = new BigDecimal("0.00");
			BigDecimal restAmtCount = new BigDecimal("0.00");

			if (hotelPaymentTxDetailHistory != null && hotelPaymentTxDetailHistory.getPaymentTransaction() != null
					&& hotelPaymentTxDetailHistory.getPaymentTransaction().getIsPaymentSuccess() == false) {
				paymentTransaction = hotelPaymentTxDetailHistory.getPaymentTransaction();
				newPaymentTransactionDetailList = hotelAndFlightPaymentTransctionDAO
						.getPaymentTransactionDetail(paymentTransaction);
				if (newPaymentTransactionDetailList != null && newPaymentTransactionDetailList.size() > 0) {
					for (PaymentTransactionDetail paymentTransactionDetail : newPaymentTransactionDetailList) {
						paymentTransactionDetail.setCreatedDate(
								DateConversion.convertTimestampToString(paymentTransactionDetail.getCreatedAt()));
						paymentTransactionDetail
						.setAmount(paymentTransactionDetail.getAmount().setScale(2, BigDecimal.ROUND_UP));
						restAmtCount = restAmtCount.add(paymentTransactionDetail.getAmount());
						BigDecimal balance = paymentTransaction.getAmount().subtract(restAmtCount);
						paymentTransactionDetail.setBalance(balance.setScale(BigDecimal.ROUND_UP, 2));
						paymentTransactionDetailList.add(paymentTransactionDetail);
						partialAmtCount = partialAmtCount.add(paymentTransactionDetail.getAmount());
					}
				}
				BigDecimal balance = paymentTransaction.getAmount().subtract(partialAmtCount);
				paymentTransaction.setBalance(balance.setScale(BigDecimal.ROUND_UP, 2));
				paymentTransaction.setAmount(paymentTransaction.getAmount().setScale(BigDecimal.ROUND_UP, 2));
				emailService.sendAllHotelCustomerPayment(false, true, company, user, MediaType.IMAGE_PNG_VALUE,
						paymentTransaction, paymentTransactionDetailList, locale, request, response, servletContext,
						applicationContext);
				notificationDAO.makeEmailerTrue(notificationDetails);
			}

			/*
			 * if (hotelPaymentTxDetailHistory != null &&
			 * hotelPaymentTxDetailHistory.getApiProviderPaymentTransaction() !=
			 * null &&
			 * hotelPaymentTxDetailHistory.getApiProviderPaymentTransaction().
			 * getIsPaymentSuccess() == false) {
			 *
			 * }
			 */

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}

}
