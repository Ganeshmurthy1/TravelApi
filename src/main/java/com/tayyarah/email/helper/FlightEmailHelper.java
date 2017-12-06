package com.tayyarah.email.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
import com.tayyarah.email.dao.FlightOrderRowEmailDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.email.entity.model.EmailNotification;
import com.tayyarah.flight.dao.FlightCreditNoteDao;
import com.tayyarah.flight.entity.FlightCreditNote;
import com.tayyarah.flight.entity.FlightOrderCustomerPriceBreakup;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.entity.FlightOrderRowMarkup;
import com.tayyarah.flight.entity.FlightOrderTripDetail;
import com.tayyarah.flight.entity.FlightPaymentTxDetailHistory;
import com.tayyarah.flight.model.FlightInvoiceData;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.services.EmailContentService;
import com.tayyarah.services.EmailService;
import com.tayyarah.user.entity.User;


@Component
public class FlightEmailHelper{

	public static final Logger logger = Logger.getLogger(FlightEmailHelper.class);

	public String sendFlightVoucherEmail(Boolean isFailed, Email email, HttpServletRequest request,
			HttpServletResponse response, Locale locale, FlightOrderRowEmailDao flightOrderRowEmailDao, FlightInvoiceData flightInvoiceData, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService,boolean isNotification) throws MessagingException, MailException, NullPointerException,
	UnsupportedEncodingException, DocumentException, IOException, Exception {
		String htmlContent = null;
		if (email != null) {
			FlightOrderRow flightorderrow = null;
			Company company = null;
			CompanyEntity companyEntity=null;
			User user = null;
			String orderId = email.getOrderId();
			BigDecimal totalAmount = new BigDecimal("0.0");
			HttpSession session = request.getSession();			
			flightorderrow = flightOrderRowEmailDao.flightOrderRowByOrderId(orderId);
			if (flightorderrow != null) {

				company = allEmailDao.getCompanyByCompanyId(flightorderrow.getCompanyId());
				//ADDED BY BASHA FOR TERMS AND CONDITIONS ERROR WHILE COMING INVOICE
				Company superUserObj=allEmailDao.getSuperCompanyByCompanyUserId(company.getSuper_company_userid());
				if(company.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()==null){
					company.setTemsandcondtions("NA");
					superUserObj.setTemsandcondtions("NA");
				}else if(company.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()!=null){
					company.setTemsandcondtions(superUserObj.getTemsandcondtions());
				}else if(company.getTemsandcondtions()==null){
					company.setTemsandcondtions("NA");
				}
				if(flightorderrow.getCompanyEntityId()!=null && flightorderrow.getCompanyEntityId() >0){ 
					companyEntity=allEmailDao.getCompanyEntityByCompanyId(flightorderrow.getCompanyEntityId());
					company=companyEntity.getCompany();
				}
				user = allEmailDao.getUserByUserId(flightorderrow.getUserId());

				if (company != null && user != null) { 
					// BigDecimal GSTfinalpricewithoutTax
					// =totWithGst.subtract(withtax);
					if (flightorderrow.getCustomer() != null) {
						String firstName=flightorderrow.getCustomer().getFirstName()!=null?flightorderrow.getCustomer().getFirstName():"";
						String lastName=flightorderrow.getCustomer().getLastName()!=null?flightorderrow.getCustomer().getLastName():"";
						session.setAttribute("name",firstName.replaceAll("[2C%-+.^:,]", " ") + " "
										+ lastName.replaceAll("[2C%-+.^:,]", " "));

						session.setAttribute("email", flightorderrow.getCustomer().getEmail());
						session.setAttribute("phone", flightorderrow.getCustomer().getMobile());

						session.setAttribute("address",flightorderrow.getCustomer().getAddress()!=null?flightorderrow.getCustomer().getAddress().replaceAll("[2C%-+.^:,]", " "):"");
						session.setAttribute("city", flightorderrow.getCustomer().getCity() != null
								? flightorderrow.getCustomer().getCity().replaceAll("[2C%-+.^:,]", " ") : "");
						session.setAttribute("state", flightorderrow.getCustomer().getZip() != null
								? flightorderrow.getCustomer().getZip().replaceAll("[2C%-+.^:,]", " ") : "");
					}					
					List<FlightOrderTripDetail> tripDeatailsList = flightOrderRowEmailDao.flightTrips(flightorderrow);
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
					SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
					List<FlightOrderTripDetail> tripDeatailsListnew = new ArrayList<FlightOrderTripDetail>();
					String departureDt = "";
					if (tripDeatailsList != null && tripDeatailsList.size() > 0) {
						for (FlightOrderTripDetail flightOrderTripDetail : tripDeatailsList) {
							if (flightOrderTripDetail.getArrivalTimestamp() != null) {
								String arrivalDt = dateFormat.format(flightOrderTripDetail.getArrivalTimestamp())
										.toString();
								flightOrderTripDetail.setArrDate(arrivalDt);
								String arrivaltime = timeFormat.format(flightOrderTripDetail.getArrivalTime())
										.toString();
								flightOrderTripDetail.setArrTime(arrivaltime);
							}
							departureDt = dateFormat.format(flightOrderTripDetail.getDepartureTimestamp()).toString();
							String departtime = timeFormat.format(flightOrderTripDetail.getDepartureTime()).toString();
							flightOrderTripDetail.setDepTime(departtime);
							flightOrderTripDetail.setDepDate(departureDt);

							int flightDuration = Integer.parseInt(flightOrderTripDetail.getFlightDuration());
							if(flightDuration != 0){
								String flightTime = minuteToTime(flightDuration);
								flightOrderTripDetail.setFlightDuration(flightTime);
							}else{
								flightOrderTripDetail.setFlightDuration("0");
							}

							tripDeatailsListnew.add(flightOrderTripDetail);
						}
					}

					flightorderrow.setDepartureDate(departureDt);

					BigDecimal extraMealPrice = flightorderrow.getExtramealprice()!=null?flightorderrow.getExtramealprice():new BigDecimal("0.00");
					BigDecimal extraBaggagePrice = flightorderrow.getExtrabaggageprice()!=null?flightorderrow.getExtrabaggageprice():new BigDecimal("0.00");
					List<FlightInvoiceData> flightInvoiceList = flightInvoiceData.getFlightOrderCustomerPriceDetails(flightorderrow);
					if (flightorderrow.getStatusAction().equalsIgnoreCase("Hold")) {
						if (StringUtils.isNotBlank(flightorderrow.getLastTicketingDate())) {
							String lastticketdate = flightorderrow.getLastTicketingDate();
							SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
							SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MMM-yy");
							Date date = originalFormat.parse(lastticketdate);
							String lastTicketDateformated = targetFormat.format(date);
							session.setAttribute("lastTicketDate", lastTicketDateformated);
						} else {
							session.setAttribute("lastTicketDate", "");
						}
					} else {
						session.setAttribute("lastTicketDate", "");
					}

					if (flightInvoiceList != null && flightInvoiceList.size() > 0) {
						for (FlightInvoiceData flightInvoiceDataInner : flightInvoiceList) {
							if (flightInvoiceDataInner != null && flightInvoiceDataInner.getPrice() != null) {
								totalAmount = totalAmount.add(flightInvoiceDataInner.getPrice());
							}
							if (flightInvoiceDataInner.isMealAvailable()) {
								session.setAttribute("isMealAvailable", true);
							}
							if (flightInvoiceDataInner.isReturnMealAvailable()) {
								session.setAttribute("isReturnMealAvailable", true);
							}
							if (flightInvoiceDataInner.getBaggageweight() != 0) {
								session.setAttribute("isBaggageAvailable", true);
							}
							if (flightInvoiceDataInner.getReturnbaggageweight() != 0) {
								session.setAttribute("isReturnBaggageAvailable", true);
							}
						}
					}

					BigDecimal YQTax = new BigDecimal("0.00");
					BigDecimal YRTax = new BigDecimal("0.00");
					BigDecimal WOTax = new BigDecimal("0.00");
					BigDecimal PSFTax = new BigDecimal("0.00");
					BigDecimal UDFTax = new BigDecimal("0.00");
					BigDecimal JNTax = new BigDecimal("0.00");
					BigDecimal INTax = new BigDecimal("0.00");
					BigDecimal transactionFee = new BigDecimal("0.00");
					BigDecimal AllTaxes = new BigDecimal("0.00");
					BigDecimal OtherTax = new BigDecimal("0.00");
					if (flightorderrow.getFlightOrderCustomerPriceBreakups().size() > 0) {
						for (FlightOrderCustomerPriceBreakup flightOrderCustomerPriceBreakup : flightorderrow
								.getFlightOrderCustomerPriceBreakups()) {
							YQTax = YQTax.add(flightOrderCustomerPriceBreakup.getYQTax() != null
									? flightOrderCustomerPriceBreakup.getYQTax() : new BigDecimal("0.00"));
							YRTax = YRTax.add(flightOrderCustomerPriceBreakup.getYRTax() != null
									? flightOrderCustomerPriceBreakup.getYRTax() : new BigDecimal("0.00"));
							WOTax = WOTax.add(flightOrderCustomerPriceBreakup.getWOTax() != null
									? flightOrderCustomerPriceBreakup.getWOTax() : new BigDecimal("0.00"));
							PSFTax = PSFTax.add(flightOrderCustomerPriceBreakup.getPSFTax() != null
									? flightOrderCustomerPriceBreakup.getPSFTax() : new BigDecimal("0.00"));
							UDFTax = UDFTax.add(flightOrderCustomerPriceBreakup.getUDFTax() != null
									? flightOrderCustomerPriceBreakup.getUDFTax() : new BigDecimal("0.00"));
							JNTax = JNTax.add(flightOrderCustomerPriceBreakup.getJNTax() != null
									? flightOrderCustomerPriceBreakup.getJNTax() : new BigDecimal("0.00"));
							INTax = INTax.add(flightOrderCustomerPriceBreakup.getINTax() != null
									? flightOrderCustomerPriceBreakup.getINTax() : new BigDecimal("0.00"));
							transactionFee = transactionFee
									.add(flightOrderCustomerPriceBreakup.getTransactionFee() != null
									? flightOrderCustomerPriceBreakup.getTransactionFee()
											: new BigDecimal("0.00"));
						}
					}

					AllTaxes = YQTax.add(YRTax).add(WOTax).add(PSFTax).add(UDFTax).add(JNTax).add(INTax).add(transactionFee);

					session.setAttribute("isMealAvailable", false);
					session.setAttribute("isReturnMealAvailable", false);
					session.setAttribute("isBaggageAvailable", false);
					session.setAttribute("isReturnBaggageAvailable", false);
					session.setAttribute("isCorporate", company.getCompanyRole().isCorporate());
					session.setAttribute("YQTax", YQTax.setScale(2, RoundingMode.UP));
					session.setAttribute("YRTax", YRTax.setScale(2, RoundingMode.UP));
					session.setAttribute("WOTax", WOTax.setScale(2, RoundingMode.UP));
					session.setAttribute("PSFTax", PSFTax.setScale(2, RoundingMode.UP));
					session.setAttribute("UDFTax", UDFTax.setScale(2, RoundingMode.UP));
					session.setAttribute("JNTax", JNTax.setScale(2, RoundingMode.UP));
					session.setAttribute("INTax", INTax.setScale(2, RoundingMode.UP));
					session.setAttribute("transactionFee", transactionFee.setScale(0, RoundingMode.UP));


					// FlightInvoiceData gstTotalPrice
					// =flightInvoiceData.gstCalcullation(flightorderrow);
					BigDecimal totalgst = new BigDecimal(0);
					totalgst = (flightorderrow.getGstOnFlights() != null ? flightorderrow.getGstOnFlights()
							: new BigDecimal(0))
							.add(flightorderrow.getGst_on_markup() != null ? flightorderrow.getGst_on_markup()
									: new BigDecimal(0));

					BigDecimal baseFareinBooking = (flightorderrow.getPrice()
							.multiply(flightorderrow.getApiToBaseExchangeRate())							
							.multiply(flightorderrow.getBaseToBookingExchangeRate()));
					BigDecimal TaxinBooking = (flightorderrow.getTotalTaxes()
							.add((flightorderrow.getMarkUp() != null ? flightorderrow.getMarkUp() : new BigDecimal(0)))
							.multiply(flightorderrow.getApiToBaseExchangeRate()))							
							.multiply(flightorderrow.getBaseToBookingExchangeRate());

					OtherTax = TaxinBooking.subtract(AllTaxes).setScale(2, RoundingMode.UP);
					BigDecimal totWithGst = baseFareinBooking.add(TaxinBooking).add(extraMealPrice).add(extraBaggagePrice).add(flightorderrow.getProcessingFees());


					session.setAttribute("OtherTax", OtherTax);
					session.setAttribute("baseFareinBooking", baseFareinBooking.setScale(2, RoundingMode.UP));
					session.setAttribute("TaxinBooking", TaxinBooking.setScale(2, RoundingMode.UP));
					session.setAttribute("pnr", flightorderrow.getPnr());

					session.setAttribute("orderId", orderId);
					session.setAttribute("tripDeatailsList", tripDeatailsListnew);
					session.setAttribute("flightOrderCustomerDetails", flightorderrow.getFlightOrderCustomers());
					session.setAttribute("flightInvoiceList", flightInvoiceList);				


					if(flightorderrow.getIsInsuranceAdded()!= null && flightorderrow.getIsInsuranceAdded()){
						if(flightorderrow.getInsuranceOrderRowId()!= null){
							InsuranceOrderRow insuranceOrderRow = flightOrderRowEmailDao.insuranceOrderRowById(flightorderrow.getInsuranceOrderRowId());
							if(insuranceOrderRow != null){
								BigDecimal insuredAmount = insuranceOrderRow.getTotalAmount().setScale(2, RoundingMode.UP);
								totWithGst = totWithGst.add(insuredAmount).setScale(2, RoundingMode.UP);
								insuranceOrderRow.setTotalAmount(insuredAmount);
								session.setAttribute("insuranceOrderRow",insuranceOrderRow);  
								session.setAttribute("totWithGst", totWithGst.setScale(0, RoundingMode.UP));
							}
						}

					}else{
						session.setAttribute("totWithGst", totWithGst.setScale(0, RoundingMode.UP));
					}


					session.setAttribute("ExtraMealPrice",extraMealPrice.setScale(2, RoundingMode.UP));
					session.setAttribute("ExtraBaggagePrice",extraBaggagePrice.setScale(2, RoundingMode.UP));  



					if(user.getEmail().equalsIgnoreCase("DirectUser@intellicommsolutions.com")){
						session.setAttribute("IsB2C", true);
					}else{
						session.setAttribute("IsB2C", false);
					}

					String path = user.getImagepath();
					CommonConfig conf = CommonConfig.GetCommonConfig();
					/*
					 * String support_email=conf.getEmail_support();
					 * userid.setLanguage(support_email);//just set
					 * support_email to disaply this in invoice
					 */
					String imagePth = conf.getDefult_image_path();
					if (path != null) {
						logger.info("Image config imagePth controlerr " + conf.getImage_path());
						imagePth = conf.getImage_path() + path;
						logger.info("Image imagePth controlerr imagePth" + imagePth);
					}

					String agencyemail = user.getEmail();
					String customeremail = flightorderrow.getCustomer().getEmail();

					// Email voucher part....

					Map<String, Object> variables = new HashMap<String, Object>();
					variables.put("flightorder", flightorderrow);
					variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
					variables.put("user", user);
					variables.put("company", company);
					variables.put("companyEntity", companyEntity);
					variables.put("isFailed", isFailed);
					variables.put("baseUrl",
							request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
					final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale, variables,
							applicationContext);
					htmlContent = emailContentService.sendFlightOrderMailVoucher(false, true, flightorderrow, user,
							company, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
							applicationContext, imagePth, ctx);
					if (!email.isOnlyHtmlContent()) {
						if (agencyemail != null && customeremail != null) {
							emailService.sendFlightOrderMailVoucher(false, true, flightorderrow, user,
									company, MediaType.IMAGE_PNG_VALUE, locale, request, response,
									servletContext, applicationContext, imagePth, htmlContent, email,isNotification);
							logger.info("Flight E Voucher Email sent to customer for " + customeremail);
							if(!customeremail.equalsIgnoreCase("")){
								if (!agencyemail.equalsIgnoreCase(customeremail)) {
									logger.info(
											"########################## agency and customer emails are different ");
									emailService.sendFlightOrderMailVoucher(false, false, flightorderrow, user,
											company, MediaType.IMAGE_PNG_VALUE, locale, request, response,
											servletContext, applicationContext, imagePth, htmlContent, email,isNotification);
									logger.info("E Voucher Email sent to agency for " + agencyemail);
								}
							}
							emailNotificationDao.insertEmailNotification(company, company, company, user,
									EmailNotification.ACTION_PARENT_TO_CHILD, email);


							logger.info("EmailController send email call after emailService.sendSimpleMail----  ");
						} 					
					}

				} else {
					throw new Exception("Company or User not found for booking");
				}
			} else {
				throw new Exception("flightorderrow not found");
			}
		} else
			throw new Exception("email not found");
		return htmlContent;
	}

	public void sendFlightInvoiceEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, int type, FlightOrderRowEmailDao flightOrderRowEmailDao, FlightInvoiceData flightInvoiceData, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext) throws MessagingException, MailException, NullPointerException,
	UnsupportedEncodingException, DocumentException, IOException, Exception {
		if (email != null) {
			FlightOrderRow flightorderrow = null;
			// Company company = null;
			User user = null;
			String orderId = email.getOrderId();
			BigDecimal totalAmount = new BigDecimal("0.0");
			HttpSession session = request.getSession();

			logger.info("flight order id : " + orderId);
			logger.info("order id" + orderId);

			//
			flightorderrow = flightOrderRowEmailDao.flightOrderRowByOrderId(orderId);
			try {
				if (flightorderrow != null) {
					BigDecimal beforeGstTot = flightorderrow.getFinalPrice();

					BigDecimal withtax = flightorderrow.getTotalTaxes();
					BigDecimal gstToBookingCurrency = flightorderrow.getGst_on_markup()
							.add(flightorderrow.getGstOnFlights())
							.multiply(flightorderrow.getBaseToBookingExchangeRate());
					BigDecimal totWithGst = flightorderrow.getFinalPrice().add(gstToBookingCurrency);
					BigDecimal totalwithtax = flightorderrow.getTotalTaxes().add(totWithGst);
					String invoiceNo = flightorderrow.getInvoiceNo();

					BigDecimal GSTfinalpricewithoutTax = totWithGst.subtract(withtax);

					BigDecimal gstOnAir = flightorderrow.getGstOnFlights();
					BigDecimal gstOnAirAmount = ArithmeticUtil
							.divideTo2Decimal(gstOnAir.multiply(new BigDecimal("100.00")), new BigDecimal("6.0"));
					// BigDecimal gstOnAirAmount =gstOnAir.multiply(new
					// BigDecimal("100.00")).divide(new BigDecimal("6.0"));




					BigDecimal extraMealPrice = flightorderrow.getExtramealprice()!=null?flightorderrow.getExtramealprice():new BigDecimal("0.00");
					BigDecimal extraBaggagePrice = flightorderrow.getExtrabaggageprice()!=null?flightorderrow.getExtrabaggageprice():new BigDecimal("0.00");
					flightorderrow.setBookingDate(DateConversion.getBluestarDateddMMMYYYY(flightorderrow.getBookingDate()));
					List<FlightOrderTripDetail> flighttripDeatailsList = flightOrderRowEmailDao.flightTrips(flightorderrow);
					logger.info("----------tripDeatailsList----- : " + flighttripDeatailsList.size());
					List<FlightOrderTripDetail> tripDeatailsList  = new ArrayList<>();
					for (FlightOrderTripDetail flightOrderTripDetail : flighttripDeatailsList) {
						String flightTime = minuteToTime(Integer.parseInt(flightOrderTripDetail.getFlightDuration()));
						flightOrderTripDetail.setFlightDuration(flightTime);
						tripDeatailsList.add(flightOrderTripDetail);
					}

					List<FlightInvoiceData> flightInvoiceList = flightInvoiceData
							.getFlightOrderCustomerPriceDetails(flightorderrow);
					if (flightorderrow.getStatusAction().equalsIgnoreCase("Hold")) {
						if (flightorderrow.getLastTicketingDate() != null) {
							String lastticketdate = flightorderrow.getLastTicketingDate();
							SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
							SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MMM-yy");
							Date date = originalFormat.parse(lastticketdate);
							String lastTicketDateformated = targetFormat.format(date);
							session.setAttribute("lastTicketDate", lastTicketDateformated);
						} else {
							session.setAttribute("lastTicketDate", "");
						}
					} else {
						session.setAttribute("lastTicketDate", "");
					}
					session.setAttribute("isMealAvailable", false);
					session.setAttribute("isReturnMealAvailable", false);
					session.setAttribute("isBaggageAvailable", false);
					session.setAttribute("isReturnBaggageAvailable", false);
					if (flightInvoiceList != null && flightInvoiceList.size() > 0) {
						logger.info(
								"----------flightInvoiceList For OrderCustomers----- : " + flightInvoiceList.size());
						logger.info(
								"----------flightInvoiceList For OrderCustomers----- : " + flightInvoiceList.size());
						for (FlightInvoiceData flightInvoiceDataInner : flightInvoiceList) {
							totalAmount = totalAmount.add(flightInvoiceDataInner.getPrice());
							if (flightInvoiceDataInner.isMealAvailable()) {
								session.setAttribute("isMealAvailable", true);
							}
							if (flightInvoiceDataInner.isReturnMealAvailable()) {
								session.setAttribute("isReturnMealAvailable", true);
							}
							if (flightInvoiceDataInner.getBaggageweight() != 0) {
								session.setAttribute("isBaggageAvailable", true);
							}
							if (flightInvoiceDataInner.getReturnbaggageweight() != 0) {
								session.setAttribute("isReturnBaggageAvailable", true);
							}
						}
					}					
					session.setAttribute("totalAmount", totalAmount.setScale(2, RoundingMode.UP));					
					session.setAttribute("gstOnAir", gstOnAir.setScale(2, RoundingMode.UP));
					session.setAttribute("gstOnAirAmount", gstOnAirAmount.setScale(2, RoundingMode.UP));
					session.setAttribute("orderId", orderId);
					session.setAttribute("tripDeatailsList", tripDeatailsList);
					session.setAttribute("flightInvoiceList", flightInvoiceList);
					session.setAttribute("invoiceNo", invoiceNo);
					session.setAttribute("beforeGstTot", beforeGstTot.setScale(2, RoundingMode.UP));					
					session.setAttribute("totalwithtax", totalwithtax.setScale(2, RoundingMode.UP));
					session.setAttribute("pricewithouttax",
							GSTfinalpricewithoutTax.setScale(2, RoundingMode.UP));
					BigDecimal baseFareinBooking = (flightorderrow.getPrice()
							.multiply(flightorderrow.getApiToBaseExchangeRate()).add(flightorderrow.getMarkUp()));
					BigDecimal TaxinBooking = (flightorderrow.getTotalTaxes()
							.multiply(flightorderrow.getApiToBaseExchangeRate()));
					session.setAttribute("baseFareinBooking", baseFareinBooking.setScale(2, RoundingMode.UP));
					session.setAttribute("TaxinBooking", TaxinBooking.setScale(2, RoundingMode.UP));
					session.setAttribute("pnr", flightorderrow.getPnr());
					session.setAttribute("ExtraMealPrice",
							String.valueOf(extraMealPrice.setScale(2, RoundingMode.UP)));
					session.setAttribute("ExtraBaggagePrice",
							String.valueOf(extraBaggagePrice.setScale(2, RoundingMode.UP)));
					emailDao.getOrderDetails(session, orderId, type);			
					logger.info("EmailController send email call---- recipientEmail: "
							+ flightorderrow.getCustomer().getEmail());
					if (session.getAttribute("userid") != null && !session.getAttribute("userid").equals("")
							&& session.getAttribute("parentUserid") != null
							&& !session.getAttribute("parentUserid").equals("")) {
						user = allEmailDao.getUserByUserId(String.valueOf(session.getAttribute("userid")));
						User parentUser = allEmailDao
								.getUserByUserId(String.valueOf(session.getAttribute("parentUserid")));
						Company companydetails = allEmailDao.getCompanyByCompanyId(String.valueOf(user.getCompanyid()));
						Company parentcompanydetails = allEmailDao
								.getCompanyByCompanyId(String.valueOf(user.getCompanyid()));
						session.setAttribute("address",
								companydetails.getBillingaddress().replaceAll("[2C%-+.^:,]", " "));			

						String path = parentUser.getImagepath();
						CommonConfig conf = CommonConfig.GetCommonConfig();
						String imagePth = conf.getDefult_image_path();
						if (path != null) {						
							imagePth = conf.getImage_path() + path;							
						}
						emailService.sendFlightInvoiceSuperToOthers(flightorderrow, user,
								MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
								applicationContext, imagePth, parentUser, parentcompanydetails, type);

						emailNotificationDao.insertEmailNotification(parentcompanydetails, parentcompanydetails,
								companydetails, user, EmailNotification.ACTION_PARENT_TO_CHILD, email);

					} else {
						throw new Exception("flightorderrow UserId Or ParentId not found");
					}
					logger.info("EmailController send email call after emailService.sendSimpleMail----  ");

				} else {
					throw new Exception("flightorderrow not found");
				}
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
	}

	public  String sendFlightOffline_OnlineInvoice(Boolean isFailed, Email email, HttpServletRequest request,
			HttpServletResponse response, Locale locale, FlightOrderRowEmailDao flightOrderRowEmailDao, FlightInvoiceData flightInvoiceData, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService,RmConfigDetailDAO rmConfigDetailDAO) throws MessagingException, MailException, NullPointerException,
	UnsupportedEncodingException, DocumentException, IOException, Exception {
		String htmlContent = null;
		boolean isRmStruct=false;
		if (email != null) {
			FlightOrderRow flightorderrow = null;
			Company company = null;
			CompanyEntity companyEntity = null;		
			User user = null;
			String orderId = email.getOrderId();
			BigDecimal totalAmount = new BigDecimal("0.0");
			HttpSession session = request.getSession();
			RmConfigTripDetailsModel rmConfigTripDetailsModel = new RmConfigTripDetailsModel();
			flightorderrow = flightOrderRowEmailDao.flightOrderRowByOrderId(orderId);
			List<String> fieldNameArray = new ArrayList<String>();
			logger.info(flightorderrow);
			if (flightorderrow != null) {
				Company parentCompany = null;
				User parentUser = null;
				company = allEmailDao.getCompanyByCompanyId(flightorderrow.getCompanyId());					
				Company superUserObj=allEmailDao.getSuperCompanyByCompanyUserId(company.getSuper_company_userid());
				if(company.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()==null){
					company.setTemsandcondtions("NA");
					superUserObj.setTemsandcondtions("NA");
				}else if(company.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()!=null){
					company.setTemsandcondtions(superUserObj.getTemsandcondtions());
				}else if(company.getTemsandcondtions()==null){
					company.setTemsandcondtions("NA");
				}
				StateInfo stateInfo = allEmailDao.getStateInfo(company.getBillingstate());	 
				if(stateInfo !=null)
					company.setStateCode(stateInfo.getStateCode());

				if(flightorderrow.getCompanyEntityId() != null){ 
					companyEntity = allEmailDao.getCompanyEntityByCompanyId(flightorderrow.getCompanyEntityId());					
				}
				parentCompany = emailDao.getParentCompany(company);
				parentCompany.initLogoDisplayable();
				if (parentCompany != null) {
					parentUser = emailDao.getCompanyWalletUser(parentCompany);
					if (parentUser != null) {
						parentCompany.initLogoDisplayable();
						parentUser.initLogoDisplayable();
					}
				}
				user = allEmailDao.getUserByEmail(company.getEmail());
				 rmConfigTripDetailsModel = rmConfigDetailDAO.getRmConfigDetail(flightorderrow.getOrderId());
				String manualField1rmconfig = null;
				String manualField2rmconfig = null;
				String manualField3rmconfig = null;
				String manualField4rmconfig = null;
				String manualField5rmconfig = null;
				String manualField1 = null;
				String manualField2 = null;
				String manualField3 = null;
				String manualField4 = null;
				String manualField5 = null;				
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
				 if(flightorderrow.getFlightOrderRowRmConfigStruct()!=null) 
					 isRmStruct=true;
				RmConfigModel rmConfigModel = rmConfigDetailDAO.getRmConfigModel(company.getCompanyid());
				String dynamicSchema=rmConfigModel!=null?rmConfigModel.getDynamicFieldsData():"";
				if(isRmStruct) 
					dynamicSchema=flightorderrow.getFlightOrderRowRmConfigStruct().getRmDynamicData();
				
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
				if (company != null && user != null) {					
					if (flightorderrow.getCustomer() != null) {
						session.setAttribute("name",
								flightorderrow.getCustomer().getFirstName().replaceAll("[2C%-+.^:,]", " ") + " "
										+ flightorderrow.getCustomer().getLastName().replaceAll("[2C%-+.^:,]", " "));

						session.setAttribute("email", flightorderrow.getCustomer().getEmail());
						session.setAttribute("phone", flightorderrow.getCustomer().getMobile());

						session.setAttribute("address",flightorderrow.getCustomer().getAddress()!=null?flightorderrow.getCustomer().getAddress().replaceAll("[2C%-+.^:,]", " "):"");
						session.setAttribute("city", flightorderrow.getCustomer().getCity() != null
								? flightorderrow.getCustomer().getCity().replaceAll("[2C%-+.^:,]", " ") : "");
						session.setAttribute("state", flightorderrow.getCustomer().getZip() != null
								? flightorderrow.getCustomer().getZip().replaceAll("[2C%-+.^:,]", " ") : "");
					}
					logger.info("company involved... company : " + company.getCompanyname());
					List<FlightOrderTripDetail> tripDeatailsList = flightOrderRowEmailDao.flightTrips(flightorderrow);
					List<FlightOrderTripDetail> tripDeatailsListnew = new ArrayList<FlightOrderTripDetail>();
					for (FlightOrderTripDetail flightOrderTripDetail : tripDeatailsList) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
						if (flightOrderTripDetail.getArrivalTimestamp() != null) {
							String arrivalDt = dateFormat.format(flightOrderTripDetail.getArrivalTimestamp())
									.toString();
							flightOrderTripDetail.setArrDate(arrivalDt);
						}
						if (flightOrderTripDetail.getDepartureTimestamp() != null) {
							String departureDt = dateFormat.format(flightOrderTripDetail.getDepartureTimestamp())
									.toString();
							flightOrderTripDetail.setDepDate(departureDt);
						}
						tripDeatailsListnew.add(flightOrderTripDetail);
					}
					company = company.tranformDisplayable();
					List<FlightInvoiceData> flightInvoiceList = flightInvoiceData
							.getFlightOrderCustomerPriceDetails(flightorderrow);
					if (flightorderrow.getStatusAction().equalsIgnoreCase("Hold")) {
						if (StringUtils.isNotBlank(flightorderrow.getLastTicketingDate())) {
							String lastticketdate = flightorderrow.getLastTicketingDate();
							SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
							SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MMM-yy");
							Date date = originalFormat.parse(lastticketdate);
							String lastTicketDateformated = targetFormat.format(date);
							session.setAttribute("lastTicketDate", lastTicketDateformated);
						} else {
							session.setAttribute("lastTicketDate", "");
						}
					} else {
						session.setAttribute("lastTicketDate", "");
					}
					session.setAttribute("isMealAvailable", false);
					session.setAttribute("isReturnMealAvailable", false);
					session.setAttribute("isBaggageAvailable", false);
					session.setAttribute("isReturnBaggageAvailable", false);

					flightorderrow.setBookingDate(DateConversion.getBluestarDateddMMMYYYY(flightorderrow.getBookingDate()));
					BigDecimal extraMealPrice = flightorderrow.getExtramealprice()!=null?flightorderrow.getExtramealprice():new BigDecimal("0.00");
					BigDecimal extraBaggagePrice = flightorderrow.getExtrabaggageprice()!=null?flightorderrow.getExtrabaggageprice():new BigDecimal("0.00");
					if (flightInvoiceList != null && flightInvoiceList.size() > 0) {
						for (FlightInvoiceData flightInvoiceDataInner : flightInvoiceList) {
							if (flightInvoiceDataInner != null && flightInvoiceDataInner.getPrice() != null) {
								totalAmount = totalAmount.add(flightInvoiceDataInner.getPrice());
							}
							if (flightInvoiceDataInner.isMealAvailable()) {
								session.setAttribute("isMealAvailable", true);
							}
							if (flightInvoiceDataInner.isReturnMealAvailable()) {
								session.setAttribute("isReturnMealAvailable", true);
							}
							if (flightInvoiceDataInner.getBaggageweight() != 0) {
								session.setAttribute("isBaggageAvailable", true);
							}
							if (flightInvoiceDataInner.getReturnbaggageweight() != 0) {
								session.setAttribute("isReturnBaggageAvailable", true);
							}
						}
					}
					session.setAttribute("isCorporate", company.getCompanyRole().isCorporate());
					BigDecimal YQTax = new BigDecimal("0.00");
					BigDecimal YRTax = new BigDecimal("0.00");
					BigDecimal WOTax = new BigDecimal("0.00");
					BigDecimal PSFTax = new BigDecimal("0.00");
					BigDecimal UDFTax = new BigDecimal("0.00");
					BigDecimal JNTax = new BigDecimal("0.00");
					BigDecimal INTax = new BigDecimal("0.00");
					BigDecimal K3Tax = new BigDecimal("0.00");
					BigDecimal transactionFee = new BigDecimal("0.00");
					BigDecimal AllTaxes = new BigDecimal("0.00");
					BigDecimal OtherTax = new BigDecimal("0.00");
					if (flightorderrow.getFlightOrderCustomerPriceBreakups().size() > 0) {
						for (FlightOrderCustomerPriceBreakup flightOrderCustomerPriceBreakup : flightorderrow
								.getFlightOrderCustomerPriceBreakups()) {
							YQTax = YQTax.add(flightOrderCustomerPriceBreakup.getYQTax() != null
									? flightOrderCustomerPriceBreakup.getYQTax() : new BigDecimal("0.00"));
							YRTax = YRTax.add(flightOrderCustomerPriceBreakup.getYRTax() != null
									? flightOrderCustomerPriceBreakup.getYRTax() : new BigDecimal("0.00"));
							WOTax = WOTax.add(flightOrderCustomerPriceBreakup.getWOTax() != null
									? flightOrderCustomerPriceBreakup.getWOTax() : new BigDecimal("0.00"));
							PSFTax = PSFTax.add(flightOrderCustomerPriceBreakup.getPSFTax() != null
									? flightOrderCustomerPriceBreakup.getPSFTax() : new BigDecimal("0.00"));
							UDFTax = UDFTax.add(flightOrderCustomerPriceBreakup.getUDFTax() != null
									? flightOrderCustomerPriceBreakup.getUDFTax() : new BigDecimal("0.00"));
							JNTax = JNTax.add(flightOrderCustomerPriceBreakup.getJNTax() != null
									? flightOrderCustomerPriceBreakup.getJNTax() : new BigDecimal("0.00"));
							INTax = INTax.add(flightOrderCustomerPriceBreakup.getINTax() != null
									? flightOrderCustomerPriceBreakup.getINTax() : new BigDecimal("0.00"));
							K3Tax = K3Tax.add(flightOrderCustomerPriceBreakup.getK3Tax() != null
									? flightOrderCustomerPriceBreakup.getK3Tax() : new BigDecimal("0.00"));
							transactionFee = transactionFee
									.add(flightOrderCustomerPriceBreakup.getTransactionFee() != null
									? flightOrderCustomerPriceBreakup.getTransactionFee()
											: new BigDecimal("0.00"));
						}
					}
					AllTaxes = YQTax.add(YRTax).add(WOTax).add(PSFTax).add(UDFTax).add(JNTax).add(INTax).add(transactionFee).add(K3Tax);	
					session.setAttribute("YQTax", YQTax.setScale(2, RoundingMode.UP));
					session.setAttribute("YRTax", YRTax.setScale(2, RoundingMode.UP));
					session.setAttribute("WOTax", WOTax.setScale(2, RoundingMode.UP));
					session.setAttribute("PSFTax", PSFTax.setScale(2, RoundingMode.UP));
					session.setAttribute("UDFTax", UDFTax.setScale(2, RoundingMode.UP));
					session.setAttribute("JNTax", JNTax.setScale(2, RoundingMode.UP));
					session.setAttribute("INTax", INTax.setScale(2, RoundingMode.UP));
					session.setAttribute("K3Tax", K3Tax.setScale(2, RoundingMode.UP));
					session.setAttribute("transactionFee", transactionFee.setScale(2, RoundingMode.UP));					
					session.setAttribute("totalAmount", totalAmount.setScale(2, RoundingMode.UP));

					BigDecimal baseFareinBooking = (flightorderrow.getPrice()
							.multiply(flightorderrow.getApiToBaseExchangeRate()).multiply(flightorderrow.getBaseToBookingExchangeRate()));
					BigDecimal TaxinBooking = (flightorderrow.getTotalTaxes().add(flightorderrow.getMarkUp() != null ? flightorderrow.getMarkUp() : new BigDecimal(0)))
							.multiply(flightorderrow.getApiToBaseExchangeRate())
							.multiply(flightorderrow.getBaseToBookingExchangeRate());

					BigDecimal totWithGst = baseFareinBooking.add(TaxinBooking).add(extraMealPrice).add(extraBaggagePrice);
					OtherTax = TaxinBooking.subtract(AllTaxes).setScale(2, RoundingMode.UP);

					session.setAttribute("ExtraMealPrice",extraMealPrice.setScale(2, RoundingMode.UP));
					session.setAttribute("ExtraBaggagePrice",extraBaggagePrice.setScale(2, RoundingMode.UP));
					session.setAttribute("OtherTax", OtherTax);

					if(flightorderrow.getFlightOrderRowServiceTax() != null){
						session.setAttribute("isServiceTax",true);
						session.setAttribute("isGstTax",false);

						BigDecimal BaseServiceTax = new BigDecimal("0.00");
						BaseServiceTax = baseFareinBooking.add(YQTax)
								.divide(new BigDecimal("100.0")).multiply(flightorderrow.getFlightOrderRowServiceTax()!=null && flightorderrow.getFlightOrderRowServiceTax().getBasicTax() != null
								? flightorderrow.getFlightOrderRowServiceTax().getBasicTax() : new BigDecimal(0));
						BigDecimal SBC = new BigDecimal("0.00");
						SBC = baseFareinBooking.add(YQTax)
								.divide(new BigDecimal("100.0"))
								.multiply(flightorderrow.getFlightOrderRowServiceTax()!=null && flightorderrow.getFlightOrderRowServiceTax().getSwatchBharathCess() != null
								? flightorderrow.getFlightOrderRowServiceTax().getSwatchBharathCess()
										: new BigDecimal(0));
						BigDecimal KKC = new BigDecimal("0.00");
						KKC = baseFareinBooking.add(YQTax)
								.divide(new BigDecimal("100.0"))
								.multiply(flightorderrow.getFlightOrderRowServiceTax()!=null && flightorderrow.getFlightOrderRowServiceTax().getKrishiKalyanCess() != null
								? flightorderrow.getFlightOrderRowServiceTax().getKrishiKalyanCess()
										: new BigDecimal(0));
						BigDecimal TotalServiceTax = new BigDecimal("0.00");
						TotalServiceTax = baseFareinBooking.add(YQTax)
								.divide(new BigDecimal("100.0"))
								.multiply(flightorderrow.getFlightOrderRowServiceTax()!=null && flightorderrow.getFlightOrderRowServiceTax().getTotalTax() != null
								? flightorderrow.getFlightOrderRowServiceTax().getTotalTax() : new BigDecimal(0));

						BigDecimal convenienceFee = flightorderrow.getFlightOrderRowServiceTax()!=null && flightorderrow.getFlightOrderRowServiceTax().getConvenienceFee()!=null?flightorderrow.getFlightOrderRowServiceTax().getConvenienceFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
						BigDecimal ManagementFee = flightorderrow.getFlightOrderRowServiceTax()!=null && flightorderrow.getFlightOrderRowServiceTax().getManagementFee()!=null?flightorderrow.getFlightOrderRowServiceTax().getManagementFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");

						if(flightorderrow.getTripType().equalsIgnoreCase("SRR")){
							BaseServiceTax = BaseServiceTax.multiply(new BigDecimal(2));
							SBC = SBC.multiply(new BigDecimal(2));
							KKC = KKC.multiply(new BigDecimal(2));
							TotalServiceTax = TotalServiceTax.multiply(new BigDecimal(2));
							convenienceFee = convenienceFee.multiply(new BigDecimal(2));
							ManagementFee = ManagementFee.multiply(new BigDecimal(2));
						}
						session.setAttribute("BaseServiceTax", BaseServiceTax.setScale(2, RoundingMode.UP));
						session.setAttribute("SBC", SBC.setScale(2, RoundingMode.UP));
						session.setAttribute("KKC", KKC.setScale(2, RoundingMode.UP));
						session.setAttribute("TotalServiceTax", TotalServiceTax.setScale(2, RoundingMode.UP));
						session.setAttribute("ConvenienceFee",convenienceFee.setScale(2, RoundingMode.UP));
						session.setAttribute("ManagementFee",ManagementFee.setScale(2, RoundingMode.UP));
						// If corporate true add all tax and management fees
						if (company.getCompanyRole().isCorporate())
							totWithGst = totWithGst.add(TotalServiceTax.add(ManagementFee).add(convenienceFee));

					}
					else if(flightorderrow.getFlightOrderRowGstTax() != null){
						session.setAttribute("isServiceTax",false);
						session.setAttribute("isGstTax",true);
						BigDecimal ManagementFee = new BigDecimal("0.00");
						ManagementFee = flightorderrow.getFlightOrderRowGstTax().getManagementFee()!=null?flightorderrow.getFlightOrderRowGstTax().getManagementFee():new BigDecimal("0.00");
						BigDecimal CGST = new BigDecimal("0.00");
						CGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(flightorderrow.getFlightOrderRowGstTax().getCGST()!=null?flightorderrow.getFlightOrderRowGstTax().getCGST():new BigDecimal("0.00"));
						BigDecimal SGST = new BigDecimal("0.00");
						SGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(flightorderrow.getFlightOrderRowGstTax().getSGST()!=null?flightorderrow.getFlightOrderRowGstTax().getSGST():new BigDecimal("0.00"));
						BigDecimal IGST = new BigDecimal("0.00");
						IGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(flightorderrow.getFlightOrderRowGstTax().getIGST()!=null?flightorderrow.getFlightOrderRowGstTax().getIGST():new BigDecimal("0.00"));
						BigDecimal UGST = new BigDecimal("0.00");
						UGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(flightorderrow.getFlightOrderRowGstTax().getUGST()!=null?flightorderrow.getFlightOrderRowGstTax().getUGST():new BigDecimal("0.00"));
						BigDecimal TotalGST = new BigDecimal("0.00");
						TotalGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(flightorderrow.getFlightOrderRowGstTax().getTotalGst()!=null?flightorderrow.getFlightOrderRowGstTax().getTotalGst():new BigDecimal("0.00"));

						BigDecimal convenienceFee =  flightorderrow.getFlightOrderRowGstTax().getConvenienceFee()!=null?flightorderrow.getFlightOrderRowGstTax().getConvenienceFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
						if(flightorderrow.getTripType().equalsIgnoreCase("SRR")){
							CGST = CGST.multiply(new BigDecimal(2));
							SGST = SGST.multiply(new BigDecimal(2));
							IGST = IGST.multiply(new BigDecimal(2));
							UGST = UGST.multiply(new BigDecimal(2));
							TotalGST = TotalGST.multiply(new BigDecimal(2));
							convenienceFee = convenienceFee.multiply(new BigDecimal(2));
							ManagementFee = ManagementFee.multiply(new BigDecimal(2));
						}
						session.setAttribute("CGSTPercentage", flightorderrow.getFlightOrderRowGstTax().getCGST().setScale(2, RoundingMode.UP));
						session.setAttribute("SGSTPercentage", flightorderrow.getFlightOrderRowGstTax().getSGST().setScale(2, RoundingMode.UP));
						session.setAttribute("IGSTPercentage", flightorderrow.getFlightOrderRowGstTax().getIGST().setScale(2, RoundingMode.UP));
						session.setAttribute("UGSTPercentage", flightorderrow.getFlightOrderRowGstTax().getUGST().setScale(2, RoundingMode.UP));

						session.setAttribute("CGST", CGST.setScale(2, RoundingMode.UP));
						session.setAttribute("SGST", SGST.setScale(2, RoundingMode.UP));
						session.setAttribute("IGST", IGST.setScale(2, RoundingMode.UP));
						session.setAttribute("UGST", UGST.setScale(2, RoundingMode.UP));
						session.setAttribute("TotalGST", TotalGST.setScale(2, RoundingMode.UP));
						session.setAttribute("ConvenienceFee",convenienceFee.setScale(2, RoundingMode.UP));
						session.setAttribute("ManagementFee",ManagementFee.setScale(2, RoundingMode.UP));
						// If corporate true add all tax and management fees
						if (company.getCompanyRole().isCorporate())
							totWithGst = totWithGst.add(TotalGST.add(ManagementFee).add(convenienceFee));

					}					
					else{
						session.setAttribute("isServiceTax",false);
						session.setAttribute("isGstTax",false);
						session.setAttribute("ConvenienceFee",new BigDecimal("0.00"));
						session.setAttribute("ManagementFee",new BigDecimal("0.00"));
					}

					BigDecimal baseWithTax = new BigDecimal("0.0");
					baseWithTax = baseFareinBooking.setScale(2, RoundingMode.UP).add(TaxinBooking.setScale(2, RoundingMode.UP)).add(extraMealPrice.setScale(2, RoundingMode.UP)).add(extraBaggagePrice.setScale(2, RoundingMode.UP));
					session.setAttribute("baseFareinBooking", baseFareinBooking.setScale(2, RoundingMode.UP));
					session.setAttribute("TaxinBooking", TaxinBooking.setScale(2, RoundingMode.UP));
					session.setAttribute("pnr", flightorderrow.getPnr());
					session.setAttribute("baseWithTax", baseWithTax);
					session.setAttribute("tripDeatailsList", tripDeatailsListnew);
					session.setAttribute("flightInvoiceList", flightInvoiceList);					
					session.setAttribute("totWithGst", totWithGst.setScale(0, RoundingMode.UP));
					session.setAttribute("amountinwords",ArithmeticUtil.DecimalValueCheacker(totWithGst.setScale(0, RoundingMode.UP).toString()));
					session.setAttribute("manualfield1",manualField1);
					session.setAttribute("manualfield2",manualField2);
					session.setAttribute("manualfield3",manualField3);
					session.setAttribute("manualfield4",manualField4 );
					session.setAttribute("manualfield5", manualField5);
  
					String path = user.getImagepath();
					CommonConfig conf = CommonConfig.GetCommonConfig();					
					String imagePth = conf.getDefult_image_path();
					if (path != null) {					
						imagePth = conf.getImage_path() + path;					
					}

					String agencyemail = user.getEmail();
					String customeremail = flightorderrow.getCustomer().getEmail();
					 
					Map<String, Object> variables = new HashMap<String, Object>();
					variables.put("flightorder", flightorderrow);
					variables.put("isRmStruct",isRmStruct);
					variables.put("rmdetail", rmConfigTripDetailsModel);					
					variables.put("manualrmfield1", manualField1rmconfig);
					variables.put("manualrmfield2", manualField2rmconfig);
					variables.put("manualrmfield3", manualField3rmconfig);
					variables.put("manualrmfield4", manualField4rmconfig);
					variables.put("manualrmfield5", manualField5rmconfig);
					variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
					variables.put("user", user);
					variables.put("company", company);
					variables.put("companyEntity", companyEntity);
					variables.put("isFailed", isFailed);
					variables.put("baseUrl",
							request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
					final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale, variables,
							applicationContext);
					htmlContent = emailContentService.sendFlightOrderMailInvoice(false, true, flightorderrow, user,
							company, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
							applicationContext, imagePth, ctx);
					if (!email.isOnlyHtmlContent()) {
						if (agencyemail != null && customeremail != null) {									
							emailService.sendFlightOrderMailInvoice(false, true, flightorderrow, user,
									company, MediaType.IMAGE_PNG_VALUE, locale, request, response,
									servletContext, applicationContext, imagePth, htmlContent, email);
							logger.info("Flight E Voucher Email sent to customer for " + customeremail);
							if(!customeremail.equalsIgnoreCase("")){
								if (!agencyemail.equalsIgnoreCase(customeremail)) {
									logger.info(
											"########################## agency and customer emails are different ");
									emailService.sendFlightOrderMailInvoice(false, false, flightorderrow, user,
											company, MediaType.IMAGE_PNG_VALUE, locale, request, response,
											servletContext, applicationContext, imagePth, htmlContent, email);
									logger.info("E Voucher Email sent to agency for " + agencyemail);
								}
							}
							emailNotificationDao.insertEmailNotification(company, company, company, user,
									EmailNotification.ACTION_PARENT_TO_CHILD, email);
						}

						logger.info("EmailController send email call after emailService.sendSimpleMail----  ");


					}
				} else {
					throw new Exception("Company or User not found for booking");
				}
			} else {
				throw new Exception("flightorderrow not found");
			}
		} else
			throw new Exception("email not found");
		return htmlContent;
	}

	public static String sendCreditNoteIssueMailFlightOld(Email email, HttpServletRequest request,
			HttpServletResponse response, Locale locale, int emailTypeSuperInvoiceToOthers, FlightOrderRowEmailDao flightOrderRowEmailDao, FlightInvoiceData flightInvoiceData, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, FlightCreditNoteDao flightCreditNoteDao, EmailContentService emailContentService) throws MessagingException, MailException, NullPointerException,
	UnsupportedEncodingException, DocumentException, IOException, Exception {
		String htmlContent = null;
		if (email != null) {
			String creditNoteId = email.getOrderId();
			FlightCreditNote flightCreditNote = flightCreditNoteDao.getCreditNoteById(creditNoteId);
			if (flightCreditNote != null) {
				FlightOrderRow flightorderrow = flightOrderRowEmailDao
						.flightOrderRowById(String.valueOf(flightCreditNote.getRowId()));
				if (flightorderrow != null && flightorderrow.getUserId() != null) {
					Company company = null; 					
					Company childCompany = null;
					User user = null;
					BigDecimal totalAmount = new BigDecimal("0.0");
					HttpSession session = request.getSession();
					BigDecimal beforeGstTot = flightorderrow.getFinalPrice();
					BigDecimal gstToBookingCurrency = flightorderrow.getGst_on_markup()
							.add(flightorderrow.getGstOnFlights())
							.multiply(flightorderrow.getBaseToBookingExchangeRate());					
					BigDecimal totWithGst = flightorderrow.getFinalPrice().add(gstToBookingCurrency);				
					BigDecimal totalwithtax = flightorderrow.getTotalTaxes().add(totWithGst);
					String invoiceNo = flightorderrow.getInvoiceNo();
					user = allEmailDao.getUserByUserId(flightCreditNote.getUserId());
					if (user != null) {
						user.initLogoDisplayable();						
						company = emailDao.getUserCompany(user);
						if (company != null) {	
							company.initLogoDisplayable();
							childCompany = emailDao.getImmediateChildCompanyBooked(company,
									Integer.valueOf(flightorderrow.getUserId()));
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
								List<FlightOrderTripDetail> tripDeatailsList = flightOrderRowEmailDao
										.flightTrips(flightorderrow);
								List<FlightInvoiceData> flightInvoiceList = flightInvoiceData
										.getFlightOrderCustomerPriceDetails(flightorderrow);
								if (flightInvoiceList != null && flightInvoiceList.size() > 0) {									
									for (FlightInvoiceData flightInvoiceDataLocal : flightInvoiceList) {
										if (flightInvoiceDataLocal != null && flightInvoiceDataLocal.getPrice() != null)
											totalAmount = totalAmount.add(flightInvoiceDataLocal.getPrice());
									}
								}
								BigDecimal totalgst = new BigDecimal(0);
								totalgst = (flightorderrow.getGstOnFlights() != null
										? flightorderrow.getGstOnFlights() : new BigDecimal(0))
										.add(flightorderrow.getGst_on_markup() != null
										? flightorderrow.getGst_on_markup() : new BigDecimal(0));
								BigDecimal totalgstinBooking = totalgst
										.multiply(flightorderrow.getBaseToBookingExchangeRate());
								BigDecimal gstOnAirAmount = ArithmeticUtil.divideTo2Decimal(
										totalgst.multiply(new BigDecimal("100.00")), new BigDecimal("6.0"));// totalgst.multiply(new
								BigDecimal baseFareinBooking = (flightorderrow.getPrice()
										.multiply(flightorderrow.getApiToBaseExchangeRate())
										.add((flightorderrow.getMarkUp() != null ? flightorderrow.getMarkUp()
												: new BigDecimal(0))))
										.multiply(flightorderrow.getBaseToBookingExchangeRate());
								BigDecimal TaxinBooking = (flightorderrow.getTotalTaxes()
										.multiply(flightorderrow.getApiToBaseExchangeRate()))
										.multiply(flightorderrow.getBaseToBookingExchangeRate());
								session.setAttribute("baseFareinBooking",
										baseFareinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("TaxinBooking",
										TaxinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("pnr", flightorderrow.getPnr());
								session.setAttribute("totalgst",
										totalgstinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("gstOnAirAmount",
										gstOnAirAmount.multiply(flightorderrow.getBaseToBookingExchangeRate())
										.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("orderId", flightorderrow.getOrderId());
								session.setAttribute("tripDeatailsList", tripDeatailsList);
								session.setAttribute("flightInvoiceList", flightInvoiceList);
								session.setAttribute("invoiceNo", invoiceNo);
								session.setAttribute("beforeGstTot",
										beforeGstTot.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("totWithGst", totWithGst.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("totalwithtax",
										totalwithtax.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("totalAmount",
										(flightCreditNote.getTotalBookingAmount() != null ? flightCreditNote
												.getTotalBookingAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("cancelfee", (flightCreditNote.getCancellationFees() != null
										? flightCreditNote.getCancellationFees().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("convinfee", (flightCreditNote.getConvenienceFees() != null
										? flightCreditNote.getConvenienceFees().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("bookamount",
										(flightCreditNote.getTotalBookingAmount() != null ? flightCreditNote
												.getTotalBookingAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("refundamount",
										(flightCreditNote.getRefundedAmount() != null
										? flightCreditNote.getRefundedAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("gstamount",
										(flightCreditNote.getGstAmount() != null
										? flightCreditNote.getGstAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("currency", flightorderrow.getBooking_currency());

								Map<String, Object> variables = new HashMap<String, Object>();
								variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
								variables.put("creditnote", flightCreditNote);
								variables.put("flightorder", flightorderrow);
								variables.put("user", user);
								variables.put("company", childCompany);
								variables.put("parentcompany", company);
								HashMap<String, String> datetimemap = CommonUtil.getDateTimeMapFromTimestamp(flightCreditNote.getIssuedAt());
								variables.put("requestdate", datetimemap.get("date"));
								variables.put("requesttime", datetimemap.get("time"));
								variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());

								final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale, variables,
										applicationContext);

								htmlContent = emailContentService.sendCreditNoteIssueFlight(company, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
										applicationContext, ctx);
								if (!email.isOnlyHtmlContent()) {
									emailService.sendCreditNoteIssueFlight(flightorderrow, flightCreditNote, user,
											childCompany, company, locale, request, response, servletContext,
											applicationContext,htmlContent,email);

									emailNotificationDao.insertEmailNotification(company, company, childCompany,
											user, EmailNotification.ACTION_PARENT_TO_CHILD, email);

									logger.info("############## credit issue note  sent--  ");
								}
							} else {
								logger.info("credit note request CHILD compnay not found  :");
								throw new Exception(
										"############## credit note request CHILD Company not found for credit note");
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
					throw new Exception("############## credit note request Flightorder not found for credit note");
				}
			} else {
				throw new Exception("############## credit note request Creditnote not found for credit note");
			}

		}
		return htmlContent;
	}

	public static void sendCreditNoteRequestMailFlight(Email email, HttpServletRequest request,
			HttpServletResponse response, Locale locale, int emailTypeSuperInvoiceToOthers, FlightOrderRowEmailDao flightOrderRowEmailDao, FlightInvoiceData flightInvoiceData, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, FlightCreditNoteDao flightCreditNoteDao, EmailContentService emailContentService) throws MessagingException, MailException, NullPointerException,
	UnsupportedEncodingException, DocumentException, IOException, Exception {
		if (email != null) {
			String creditNoteId = email.getOrderId();
			FlightCreditNote flightCreditNote = flightCreditNoteDao.getCreditNoteById(creditNoteId);
			if (flightCreditNote != null) {
				FlightOrderRow flightorderrow = flightOrderRowEmailDao
						.flightOrderRowById(String.valueOf(flightCreditNote.getRowId()));
				if (flightorderrow != null) {
					Company company = null; 
					Company parentCompany = null;
					User user = null;
					BigDecimal totalAmount = new BigDecimal("0.0");
					HttpSession session = request.getSession();
					BigDecimal beforeGstTot = flightorderrow.getFinalPrice();
					BigDecimal gstToBookingCurrency = flightorderrow.getGst_on_markup()
							.add(flightorderrow.getGstOnFlights())
							.multiply(flightorderrow.getBaseToBookingExchangeRate());					
					BigDecimal totWithGst = flightorderrow.getFinalPrice().add(gstToBookingCurrency);				
					BigDecimal totalwithtax = flightorderrow.getTotalTaxes().add(totWithGst);
					String invoiceNo = flightorderrow.getInvoiceNo();
					user = allEmailDao.getUserByUserId(flightCreditNote.getUserId());
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

								List<FlightOrderTripDetail> tripDeatailsList = flightOrderRowEmailDao
										.flightTrips(flightorderrow);
								List<FlightInvoiceData> flightInvoiceList = flightInvoiceData
										.getFlightOrderCustomerPriceDetails(flightorderrow);
								if (flightInvoiceList != null && flightInvoiceList.size() > 0) {									
									for (FlightInvoiceData flightInvoiceDataLocal : flightInvoiceList) {
										if (flightInvoiceDataLocal != null && flightInvoiceDataLocal.getPrice() != null)
											totalAmount = totalAmount.add(flightInvoiceDataLocal.getPrice());
									}
								}								
								session.setAttribute("totalAmount",
										totalAmount.setScale(2, BigDecimal.ROUND_FLOOR));
								BigDecimal totalgst = new BigDecimal(0);
								totalgst = (flightorderrow.getGstOnFlights() != null
										? flightorderrow.getGstOnFlights() : new BigDecimal(0))
										.add(flightorderrow.getGst_on_markup() != null
										? flightorderrow.getGst_on_markup() : new BigDecimal(0));
								BigDecimal totalgstinBooking = totalgst
										.multiply(flightorderrow.getBaseToBookingExchangeRate());
								BigDecimal gstOnAirAmount = ArithmeticUtil.divideTo2Decimal(
										totalgst.multiply(new BigDecimal("100.00")), new BigDecimal("6.0"));								
								BigDecimal baseFareinBooking = (flightorderrow.getPrice()
										.multiply(flightorderrow.getApiToBaseExchangeRate())
										.add((flightorderrow.getMarkUp() != null ? flightorderrow.getMarkUp()
												: new BigDecimal(0))))
										.multiply(flightorderrow.getBaseToBookingExchangeRate());
								BigDecimal TaxinBooking = (flightorderrow.getTotalTaxes()
										.multiply(flightorderrow.getApiToBaseExchangeRate()))
										.multiply(flightorderrow.getBaseToBookingExchangeRate());
								session.setAttribute("baseFareinBooking",
										baseFareinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("TaxinBooking",
										TaxinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("pnr", flightorderrow.getPnr());
								session.setAttribute("totalgst",
										totalgstinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("gstOnAirAmount",
										gstOnAirAmount.multiply(flightorderrow.getBaseToBookingExchangeRate())
										.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("orderId", flightorderrow.getOrderId());
								session.setAttribute("tripDeatailsList", tripDeatailsList);
								session.setAttribute("flightInvoiceList", flightInvoiceList);
								session.setAttribute("invoiceNo", invoiceNo);
								session.setAttribute("beforeGstTot",
										beforeGstTot.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("totWithGst", totWithGst.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("totalwithtax",
										totalwithtax.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("cancelfee", (flightCreditNote.getCancellationFees() != null
										? flightCreditNote.getCancellationFees().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("convinfee", (flightCreditNote.getConvenienceFees() != null
										? flightCreditNote.getConvenienceFees().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("bookamount",
										(flightCreditNote.getTotalBookingAmount() != null ? flightCreditNote
												.getTotalBookingAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("refundamount",
										(flightCreditNote.getRefundedAmount() != null
										? flightCreditNote.getRefundedAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("gstamount",
										(flightCreditNote.getGstAmount() != null
										? flightCreditNote.getGstAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("currency", flightorderrow.getBooking_currency());								
								if (company.getCompanyid() != parentCompany.getCompanyid()) {
									emailService.sendCreditNoteRequestFlight(flightorderrow, flightCreditNote, user,
											company, parentCompany, locale, request, response, servletContext,
											applicationContext);
									emailNotificationDao.insertEmailNotification(parentCompany, parentCompany,
											company, user, EmailNotification.ACTION_CHILD_TO_PARENT, email);

								} else {
									logger.info(
											"############## credit note request same company internal request : "
													+ company.getCompanyname());
								}
								logger.info("############## credit note request sent--  ");

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
					throw new Exception("############## credit note request Flightorder not found for credit note");
				}
			} else {
				throw new Exception("############## credit note request Creditnote not found for credit note");
			}

		}
	}

	public static String sendCreditNoteIssueMailFlight(Email email, HttpServletRequest request,
			HttpServletResponse response, Locale locale, int emailTypeSuperInvoiceToOthers, FlightOrderRowEmailDao flightOrderRowEmailDao, FlightInvoiceData flightInvoiceData, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, FlightCreditNoteDao flightCreditNoteDao, EmailContentService emailContentService, RmConfigDetailDAO rmConfigDetailDAO, CompanyDao companyDao) throws MessagingException, MailException, NullPointerException,
	UnsupportedEncodingException, DocumentException, IOException, Exception {
		String htmlContent = null;
		boolean isRmStruct=false;
		if (email != null) {
			String creditNoteId = email.getOrderId();
			FlightCreditNote flightCreditNote = flightCreditNoteDao.getCreditNoteById(creditNoteId);
			if (flightCreditNote != null) {
				FlightOrderRow flightorderrow = flightOrderRowEmailDao.flightOrderRowById(String.valueOf(flightCreditNote.getRowId()));

				LinkedList<Company> companyListBottomToTop= new LinkedList<>();

				LinkedHashMap<String, FlightOrderRowMarkup> flightOrderRowMarkupMap = new LinkedHashMap<>();
				LinkedHashMap<String, FlightCreditNote> creditNoteMarkupMap = new LinkedHashMap<>();
				companyListBottomToTop = CommonUtil.getParentCompanyBottomToTop(Integer.parseInt(flightCreditNote.getCompanyId()),companyDao);
				if(companyListBottomToTop!=null && companyListBottomToTop.size()>0)
				{
					if(flightorderrow.getFlightOrderRowMarkupList()!=null && flightorderrow.getFlightOrderRowMarkupList().size()>0)
					{
						for(FlightOrderRowMarkup flightOrderRowMarkup:flightorderrow.getFlightOrderRowMarkupList())
						{
							flightOrderRowMarkupMap.put(flightOrderRowMarkup.getCompanyId(), flightOrderRowMarkup);
						}
					}
				}
				Company bookingCompany = null;
				CompanyEntity companyEntity=null;
				User bookingUser = null;
				bookingCompany = allEmailDao.getCompanyByCompanyId(flightorderrow.getCompanyId());				
				Company superUserObj=allEmailDao.getSuperCompanyByCompanyUserId(bookingCompany.getSuper_company_userid());
				if(bookingCompany.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()==null){
					bookingCompany.setTemsandcondtions("NA");
					superUserObj.setTemsandcondtions("NA");
				}else if(bookingCompany.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()!=null){
					bookingCompany.setTemsandcondtions(superUserObj.getTemsandcondtions());
				}else if(bookingCompany.getTemsandcondtions()==null){
					bookingCompany.setTemsandcondtions("NA");
				}			
				bookingUser = allEmailDao.getUserByEmail(bookingCompany.getEmail());
				StateInfo stateInfo = allEmailDao.getStateInfo(bookingCompany.getBillingstate());	 
				if(stateInfo !=null)
					bookingCompany.setStateCode(stateInfo.getStateCode());

				if(flightorderrow.getCompanyEntityId() != null){ 
					companyEntity = allEmailDao.getCompanyEntityByCompanyId(flightorderrow.getCompanyEntityId());					
				}
				BigDecimal totalAmount = new BigDecimal("0.0");				
				HttpSession session = request.getSession();
				RmConfigTripDetailsModel rmConfigTripDetailsModel = null;
				List<String> fieldNameArray = new ArrayList<String>();
				Company parentCompany = null;
				User parentUser = null;
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
				if (flightorderrow != null && flightorderrow.getUserId() != null) {
					rmConfigTripDetailsModel = rmConfigDetailDAO.getRmConfigDetail(flightorderrow.getOrderId());
					RmConfigModel rmConfigModel = rmConfigDetailDAO.getRmConfigModel(bookingCompany.getCompanyid());
					String manualField1rmconfig = null;
					String manualField2rmconfig = null;
					String manualField3rmconfig = null;
					String manualField4rmconfig = null;
					String manualField5rmconfig = null;
					String manualField1 = null;
					String manualField2 = null;
					String manualField3 = null;
					String manualField4 = null;
					String manualField5 = null;
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
					 if(flightorderrow.getFlightOrderRowRmConfigStruct()!=null) 
						 isRmStruct=true;
					String dynamicSchema=rmConfigModel!=null?rmConfigModel.getDynamicFieldsData():"";
					if(isRmStruct) 
						dynamicSchema=flightorderrow.getFlightOrderRowRmConfigStruct().getRmDynamicData();
					
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
					List<FlightCreditNote> newCreditNoteList= flightCreditNoteDao.getCreditNoteListByOrderRowID(flightorderrow.getId());
					BigDecimal totManagementFees=new BigDecimal("0.00");
					BigDecimal totConvenienceFee=new BigDecimal("0.00");
					BigDecimal totCancellationFee=new BigDecimal("0.00");
					BigDecimal totRefundAmt = new BigDecimal("0.00");
					String creditNoteNumber = null;
					Timestamp creditNoteDate= null;
					if(flightorderrow!=null && flightorderrow.isCreditNoteIssued())
					{
						if(flightCreditNote!=null){
							if(newCreditNoteList!=null && newCreditNoteList.size()>0)
							{
								for(FlightCreditNote creditNoteInner:newCreditNoteList)
								{
									creditNoteMarkupMap.put(creditNoteInner.getCompanyId(), creditNoteInner);
								}
							} 
						}
						java.util.Iterator<Company> coIterator = companyListBottomToTop.descendingIterator();
						while(coIterator.hasNext())
						{
							Company companyInner = coIterator.next();
							FlightCreditNote creditNoteInner = creditNoteMarkupMap.get(String.valueOf(companyInner.getCompanyid()));
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
									if(String.valueOf(companyInner.getCompanyid()).equals(flightCreditNote.getCompanyId()))
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

									}

								}
							}
						}
						creditNoteNumber = flightCreditNote.getCNINumber();
						creditNoteDate = flightCreditNote.getIssuedAt();
					}     
					session.setAttribute("refundAmt", totRefundAmt.setScale(2, RoundingMode.UP));
					session.setAttribute("totManagementFees", totManagementFees.setScale(2, RoundingMode.UP));
					session.setAttribute("totConvenienceFee", totConvenienceFee.setScale(2, RoundingMode.UP));
					session.setAttribute("totCancellationFee", totCancellationFee.setScale(2, RoundingMode.UP));
					session.setAttribute("creditNoteNumber", creditNoteNumber);
					session.setAttribute("creditNoteDate", creditNoteDate);
					if (bookingCompany != null && bookingUser != null) {					
						if (flightorderrow.getCustomer() != null) {
							session.setAttribute("name",
									flightorderrow.getCustomer().getFirstName().replaceAll("[2C%-+.^:,]", " ") + " "
											+ flightorderrow.getCustomer().getLastName().replaceAll("[2C%-+.^:,]", " "));

							session.setAttribute("email", flightorderrow.getCustomer().getEmail());
							session.setAttribute("phone", flightorderrow.getCustomer().getMobile());

							session.setAttribute("address",flightorderrow.getCustomer().getAddress()!=null?flightorderrow.getCustomer().getAddress().replaceAll("[2C%-+.^:,]", " "):"");

							session.setAttribute("city", flightorderrow.getCustomer().getCity() != null
									? flightorderrow.getCustomer().getCity().replaceAll("[2C%-+.^:,]", " ") : "");
							session.setAttribute("state", flightorderrow.getCustomer().getZip() != null
									? flightorderrow.getCustomer().getZip().replaceAll("[2C%-+.^:,]", " ") : "");
						}
						List<FlightOrderTripDetail> tripDeatailsList = flightOrderRowEmailDao.flightTrips(flightorderrow);
						List<FlightOrderTripDetail> tripDeatailsListnew = new ArrayList<FlightOrderTripDetail>();
						for (FlightOrderTripDetail flightOrderTripDetail : tripDeatailsList) {
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm aa");
							if (flightOrderTripDetail.getArrivalTimestamp() != null) {
								String arrivalDt = dateFormat.format(flightOrderTripDetail.getArrivalTimestamp())
										.toString();
								flightOrderTripDetail.setArrDate(arrivalDt);
							}
							if (flightOrderTripDetail.getDepartureTimestamp() != null) {
								String departureDt = dateFormat.format(flightOrderTripDetail.getDepartureTimestamp())
										.toString();
								flightOrderTripDetail.setDepDate(departureDt);
							}
							tripDeatailsListnew.add(flightOrderTripDetail);
						}
						bookingCompany = bookingCompany.tranformDisplayable();
						List<FlightInvoiceData> flightInvoiceList = flightInvoiceData.getFlightOrderCustomerPriceDetails(flightorderrow);
						if (flightorderrow.getStatusAction().equalsIgnoreCase("Hold")) {
							if (StringUtils.isNotBlank(flightorderrow.getLastTicketingDate())) {
								String lastticketdate = flightorderrow.getLastTicketingDate();
								SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
								SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MMM-yy");
								Date date = originalFormat.parse(lastticketdate);
								String lastTicketDateformated = targetFormat.format(date);
								session.setAttribute("lastTicketDate", lastTicketDateformated);
							} else {
								session.setAttribute("lastTicketDate", "");
							}
						} else {
							session.setAttribute("lastTicketDate", "");
						}
						session.setAttribute("isMealAvailable", false);
						session.setAttribute("isReturnMealAvailable", false);
						session.setAttribute("isBaggageAvailable", false);
						session.setAttribute("isReturnBaggageAvailable", false);
						BigDecimal extraMealPrice = flightorderrow.getExtramealprice()!=null?flightorderrow.getExtramealprice():new BigDecimal("0.00");
						BigDecimal extraBaggagePrice = flightorderrow.getExtrabaggageprice()!=null?flightorderrow.getExtrabaggageprice():new BigDecimal("0.00");

						if (flightInvoiceList != null && flightInvoiceList.size() > 0) {
							for (FlightInvoiceData flightInvoiceDataInner : flightInvoiceList) {
								if (flightInvoiceDataInner != null && flightInvoiceDataInner.getPrice() != null) {
									totalAmount = totalAmount.add(flightInvoiceDataInner.getPrice());
								}
								if (flightInvoiceDataInner.isMealAvailable()) {
									session.setAttribute("isMealAvailable", true);
								}
								if (flightInvoiceDataInner.isReturnMealAvailable()) {
									session.setAttribute("isReturnMealAvailable", true);
								}
								if (flightInvoiceDataInner.getBaggageweight() != 0) {
									session.setAttribute("isBaggageAvailable", true);
								}
								if (flightInvoiceDataInner.getReturnbaggageweight() != 0) {
									session.setAttribute("isReturnBaggageAvailable", true);
								}
							}
						}
						session.setAttribute("isCorporate", bookingCompany.getCompanyRole().isCorporate());
						BigDecimal YQTax = new BigDecimal("0.00");
						BigDecimal YRTax = new BigDecimal("0.00");
						BigDecimal WOTax = new BigDecimal("0.00");
						BigDecimal PSFTax = new BigDecimal("0.00");
						BigDecimal UDFTax = new BigDecimal("0.00");
						BigDecimal JNTax = new BigDecimal("0.00");
						BigDecimal INTax = new BigDecimal("0.00");
						BigDecimal K3Tax = new BigDecimal("0.00");
						BigDecimal transactionFee = new BigDecimal("0.00");
						BigDecimal AllTaxes = new BigDecimal("0.00");
						BigDecimal OtherTax = new BigDecimal("0.00");
						if (flightorderrow.getFlightOrderCustomerPriceBreakups().size() > 0) {
							for (FlightOrderCustomerPriceBreakup flightOrderCustomerPriceBreakup : flightorderrow
									.getFlightOrderCustomerPriceBreakups()) {
								YQTax = YQTax.add(flightOrderCustomerPriceBreakup.getYQTax() != null
										? flightOrderCustomerPriceBreakup.getYQTax() : new BigDecimal("0.00"));
								YRTax = YRTax.add(flightOrderCustomerPriceBreakup.getYRTax() != null
										? flightOrderCustomerPriceBreakup.getYRTax() : new BigDecimal("0.00"));
								WOTax = WOTax.add(flightOrderCustomerPriceBreakup.getWOTax() != null
										? flightOrderCustomerPriceBreakup.getWOTax() : new BigDecimal("0.00"));
								PSFTax = PSFTax.add(flightOrderCustomerPriceBreakup.getPSFTax() != null
										? flightOrderCustomerPriceBreakup.getPSFTax() : new BigDecimal("0.00"));
								UDFTax = UDFTax.add(flightOrderCustomerPriceBreakup.getUDFTax() != null
										? flightOrderCustomerPriceBreakup.getUDFTax() : new BigDecimal("0.00"));
								JNTax = JNTax.add(flightOrderCustomerPriceBreakup.getJNTax() != null
										? flightOrderCustomerPriceBreakup.getJNTax() : new BigDecimal("0.00"));
								INTax = INTax.add(flightOrderCustomerPriceBreakup.getINTax() != null
										? flightOrderCustomerPriceBreakup.getINTax() : new BigDecimal("0.00"));
								K3Tax = K3Tax.add(flightOrderCustomerPriceBreakup.getK3Tax() != null
										? flightOrderCustomerPriceBreakup.getK3Tax() : new BigDecimal("0.00"));
								transactionFee = transactionFee
										.add(flightOrderCustomerPriceBreakup.getTransactionFee() != null
										? flightOrderCustomerPriceBreakup.getTransactionFee()
												: new BigDecimal("0.00"));
							}
						}
						AllTaxes = YQTax.add(YRTax).add(WOTax).add(PSFTax).add(UDFTax).add(JNTax).add(INTax).add(transactionFee);			

						session.setAttribute("YQTax", YQTax.setScale(2, RoundingMode.UP));
						session.setAttribute("YRTax", YRTax.setScale(2, RoundingMode.UP));
						session.setAttribute("WOTax", WOTax.setScale(2, RoundingMode.UP));
						session.setAttribute("PSFTax", PSFTax.setScale(2, RoundingMode.UP));
						session.setAttribute("UDFTax", UDFTax.setScale(2, RoundingMode.UP));
						session.setAttribute("JNTax", JNTax.setScale(2, RoundingMode.UP));
						session.setAttribute("INTax", INTax.setScale(2, RoundingMode.UP));
						session.setAttribute("K3Tax", K3Tax.setScale(2, RoundingMode.UP));
						session.setAttribute("transactionFee", transactionFee.setScale(2, RoundingMode.UP));

						BigDecimal baseFareinBooking = (flightorderrow.getPrice().multiply(flightorderrow.getApiToBaseExchangeRate()).multiply(flightorderrow.getBaseToBookingExchangeRate()));
						BigDecimal TaxinBooking = flightorderrow.getTotalTaxes();
						BigDecimal totalMarkupToCompany = new BigDecimal(0);
						BigDecimal markupTobeRemoved = new BigDecimal(0);
						java.util.Iterator<Company> lit = companyListBottomToTop.descendingIterator();
						while(lit.hasNext())
						{
							Company companyInner = lit.next();
							if(String.valueOf(companyInner.getCompanyid()).equals(flightCreditNote.getCompanyId()))
							{
								break;
							}
							else{
								FlightOrderRowMarkup flightOrderRowMarkup =flightOrderRowMarkupMap.get(String.valueOf(companyInner.getCompanyid()));
								if(flightOrderRowMarkup!=null)
								{
									totalMarkupToCompany = (totalMarkupToCompany.add(flightOrderRowMarkup.getMarkUp() != null ? flightOrderRowMarkup.getMarkUp() : new BigDecimal(0)))
											.multiply(flightorderrow.getApiToBaseExchangeRate())
											.multiply(flightorderrow.getBaseToBookingExchangeRate());
								}
							}
						}
						TaxinBooking = totalMarkupToCompany.add(TaxinBooking);
						BigDecimal totWithGst = baseFareinBooking.add(TaxinBooking).add(extraMealPrice).add(extraBaggagePrice);
						BigDecimal orderMarkup= flightorderrow.getMarkUp();
						markupTobeRemoved = orderMarkup.subtract(totalMarkupToCompany).multiply(flightorderrow.getApiToBaseExchangeRate())
								.multiply(flightorderrow.getBaseToBookingExchangeRate());
						totalAmount = totalAmount.subtract(markupTobeRemoved);
						session.setAttribute("totalAmount", totalAmount.setScale(2, RoundingMode.UP));
						OtherTax = TaxinBooking.subtract(AllTaxes).setScale(2, RoundingMode.UP);
						session.setAttribute("ExtraMealPrice",extraMealPrice.setScale(2, RoundingMode.UP));
						session.setAttribute("ExtraBaggagePrice",extraBaggagePrice.setScale(2, RoundingMode.UP));
						session.setAttribute("OtherTax", OtherTax);
						if(flightorderrow.getFlightOrderRowServiceTax() != null){
							session.setAttribute("isServiceTax",true);
							session.setAttribute("isGstTax",false);
							BigDecimal BaseServiceTax = new BigDecimal("0.00");
							BaseServiceTax = baseFareinBooking.add(YQTax)
									.divide(new BigDecimal("100.0")).multiply(flightorderrow.getFlightOrderRowServiceTax()!=null && flightorderrow.getFlightOrderRowServiceTax().getBasicTax() != null
									? flightorderrow.getFlightOrderRowServiceTax().getBasicTax() : new BigDecimal(0));
							BigDecimal SBC = new BigDecimal("0.00");
							SBC = baseFareinBooking.add(YQTax)
									.divide(new BigDecimal("100.0"))
									.multiply(flightorderrow.getFlightOrderRowServiceTax()!=null && flightorderrow.getFlightOrderRowServiceTax().getSwatchBharathCess() != null
									? flightorderrow.getFlightOrderRowServiceTax().getSwatchBharathCess()
											: new BigDecimal(0));
							BigDecimal KKC = new BigDecimal("0.00");
							KKC = baseFareinBooking.add(YQTax)
									.divide(new BigDecimal("100.0"))
									.multiply(flightorderrow.getFlightOrderRowServiceTax()!=null && flightorderrow.getFlightOrderRowServiceTax().getKrishiKalyanCess() != null
									? flightorderrow.getFlightOrderRowServiceTax().getKrishiKalyanCess()
											: new BigDecimal(0));
							BigDecimal TotalServiceTax = new BigDecimal("0.00");
							TotalServiceTax = baseFareinBooking.add(YQTax)
									.divide(new BigDecimal("100.0"))
									.multiply(flightorderrow.getFlightOrderRowServiceTax()!=null && flightorderrow.getFlightOrderRowServiceTax().getTotalTax() != null
									? flightorderrow.getFlightOrderRowServiceTax().getTotalTax() : new BigDecimal(0));
							BigDecimal convenienceFee = flightorderrow.getFlightOrderRowServiceTax()!=null && flightorderrow.getFlightOrderRowServiceTax().getConvenienceFee()!=null?flightorderrow.getFlightOrderRowServiceTax().getConvenienceFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
							BigDecimal ManagementFee = flightorderrow.getFlightOrderRowServiceTax()!=null && flightorderrow.getFlightOrderRowServiceTax().getManagementFee()!=null?flightorderrow.getFlightOrderRowServiceTax().getManagementFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
							if(flightorderrow.getTripType().equalsIgnoreCase("SRR")){
								BaseServiceTax = BaseServiceTax.multiply(new BigDecimal(2));
								SBC = SBC.multiply(new BigDecimal(2));
								KKC = KKC.multiply(new BigDecimal(2));
								TotalServiceTax = TotalServiceTax.multiply(new BigDecimal(2));
								convenienceFee = convenienceFee.multiply(new BigDecimal(2));
								ManagementFee = ManagementFee.multiply(new BigDecimal(2));
							}
							session.setAttribute("BaseServiceTax", BaseServiceTax.setScale(2, RoundingMode.UP));
							session.setAttribute("SBC", SBC.setScale(2, RoundingMode.UP));
							session.setAttribute("KKC", KKC.setScale(2, RoundingMode.UP));
							session.setAttribute("TotalServiceTax", TotalServiceTax.setScale(2, RoundingMode.UP));
							session.setAttribute("ConvenienceFee",convenienceFee.setScale(2, RoundingMode.UP));							
							session.setAttribute("ManagementFee",ManagementFee.setScale(2, RoundingMode.UP));
							// If corporate true add all tax and management fees
							if (bookingCompany.getCompanyRole().isCorporate())
								totWithGst = totWithGst.add(TotalServiceTax.add(ManagementFee).add(convenienceFee));

						}
						else if(flightorderrow.getFlightOrderRowGstTax() != null){
							session.setAttribute("isServiceTax",false);
							session.setAttribute("isGstTax",true);
							BigDecimal ManagementFee = new BigDecimal("0.00");
							BigDecimal convenienceFee  = new BigDecimal("0.00");
							BigDecimal CGST = new BigDecimal("0.00");
							BigDecimal SGST = new BigDecimal("0.00");
							BigDecimal IGST = new BigDecimal("0.00");
							BigDecimal UGST = new BigDecimal("0.00");
							BigDecimal TotalGST = new BigDecimal("0.00");						
							ManagementFee = new BigDecimal("0.00");		
							convenienceFee =  flightorderrow.getFlightOrderRowGstTax().getConvenienceFee()!=null?flightorderrow.getFlightOrderRowGstTax().getConvenienceFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
							if(flightorderrow.getTripType().equalsIgnoreCase("SRR")){
								CGST = CGST.multiply(new BigDecimal(2));
								SGST = SGST.multiply(new BigDecimal(2));
								IGST = IGST.multiply(new BigDecimal(2));
								UGST = UGST.multiply(new BigDecimal(2));
								TotalGST = TotalGST.multiply(new BigDecimal(2));
								convenienceFee = convenienceFee.multiply(new BigDecimal(2));								
								ManagementFee = ManagementFee.multiply(new BigDecimal(2));

							}
							session.setAttribute("CGSTPercentage", flightorderrow.getFlightOrderRowGstTax().getCGST().setScale(2, RoundingMode.UP));
							session.setAttribute("SGSTPercentage", flightorderrow.getFlightOrderRowGstTax().getSGST().setScale(2, RoundingMode.UP));
							session.setAttribute("IGSTPercentage", flightorderrow.getFlightOrderRowGstTax().getIGST().setScale(2, RoundingMode.UP));
							session.setAttribute("UGSTPercentage", flightorderrow.getFlightOrderRowGstTax().getUGST().setScale(2, RoundingMode.UP));
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

						}					
						else{
							session.setAttribute("isServiceTax",false);
							session.setAttribute("isGstTax",false);
							session.setAttribute("ConvenienceFee",new BigDecimal("0.00"));
							session.setAttribute("ManagementFee",new BigDecimal("0.00"));
						}
						BigDecimal baseWithTax = new BigDecimal("0.0");
						baseWithTax = baseFareinBooking.setScale(2, RoundingMode.UP).add(TaxinBooking.setScale(2, RoundingMode.UP)).add(extraMealPrice.setScale(2, RoundingMode.UP)).add(extraBaggagePrice.setScale(2, RoundingMode.UP));
						session.setAttribute("baseFareinBooking", baseFareinBooking.setScale(2, RoundingMode.UP));
						session.setAttribute("TaxinBooking", TaxinBooking.setScale(2, RoundingMode.UP));
						session.setAttribute("pnr", flightorderrow.getPnr());
						session.setAttribute("baseWithTax", baseWithTax);
						session.setAttribute("tripDeatailsList", tripDeatailsListnew);
						session.setAttribute("flightInvoiceList", flightInvoiceList);
						session.setAttribute("totWithGst", totWithGst.setScale(0, RoundingMode.UP));
						session.setAttribute("amountinwords",ArithmeticUtil.DecimalValueCheacker(totRefundAmt.setScale(0, RoundingMode.UP).toString()));
						session.setAttribute("manualfield1",manualField1 );
						session.setAttribute("manualfield2",manualField2 );
						session.setAttribute("manualfield3",manualField3 );
						session.setAttribute("manualfield4",manualField4 );
						session.setAttribute("manualfield5", manualField5);
						String path = bookingUser.getImagepath();
						CommonConfig conf = CommonConfig.GetCommonConfig();						
						String imagePth = conf.getDefult_image_path();
						if (path != null) {						
							imagePth = conf.getImage_path() + path;							
						}

						String agencyemail = bookingUser.getEmail();
						String customeremail = flightorderrow.getCustomer().getEmail();
						flightorderrow.setBookingDate(DateConversion.getBluestarDateddMMMYYYY(flightorderrow.getBookingDate()));

						Map<String, Object> variables = new HashMap<String, Object>();
						variables.put("flightorder", flightorderrow);
						variables.put("isRmStruct",isRmStruct);
						variables.put("rmdetail", rmConfigTripDetailsModel);					
						variables.put("manualrmfield1", manualField1rmconfig);
						variables.put("manualrmfield2", manualField2rmconfig);
						variables.put("manualrmfield3", manualField3rmconfig);
						variables.put("manualrmfield4", manualField4rmconfig);
						variables.put("manualrmfield5", manualField5rmconfig);					
						variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
						variables.put("user", bookingUser);
						variables.put("company", bookingCompany);
						variables.put("companyEntity", companyEntity);
						variables.put("baseUrl",
								request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
						final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale, variables,
								applicationContext);

						htmlContent = emailContentService.sendCreditNoteIssueFlight(bookingCompany, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
								applicationContext, ctx);
						if (!email.isOnlyHtmlContent()) {
							emailService.sendCreditNoteIssueFlight(flightorderrow, flightCreditNote, bookingUser,
									bookingCompany, parentCompany, locale, request, response, servletContext,
									applicationContext,htmlContent,email);

							emailNotificationDao.insertEmailNotification(parentCompany, parentCompany, bookingCompany,
									bookingUser, EmailNotification.ACTION_PARENT_TO_CHILD, email);

							logger.info("############## credit issue note  sent--  ");
						}

					} else {
						logger.info("credit note request CHILD compnay not found  :");
						throw new Exception(
								"############## credit note request CHILD Company not found for credit note");
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
			throw new Exception("############## credit note request Flightorder not found for credit note");
		}

		return htmlContent;
	}

	// to be used by getAllHotelFlightCustomerPayment 03-02-2017 by saumya,to
	// send mail for partial payment
	public static void sendAllFlightCustomerPayment(String InventoryId, HttpServletRequest request,
			HttpServletResponse response, List<NotificationDetail> notificationDetails, Company company, User user, HotelAndFlightPaymentTransctionDAO hotelAndFlightPaymentTransctionDAO, EmailService emailService, NotificationDAO notificationDAO, ServletContext servletContext, ApplicationContext applicationContext) {

		PaymentTransaction paymentTransaction = new PaymentTransaction();
		List<PaymentTransactionDetail> newPaymentTransactionDetailList = null;
		List<PaymentTransactionDetail> paymentTransactionDetailList = new ArrayList<>();
		BigDecimal partialAmtCount = new BigDecimal("0.00");
		BigDecimal restAmtCount = new BigDecimal("0.00");

		try {
			final Locale locale = LocaleContextHolder.getLocale();
			FlightPaymentTxDetailHistory flightPaymentTxDetailHistory = hotelAndFlightPaymentTransctionDAO
					.getFlightPaymentTxDetailHistory(Long.parseLong(InventoryId));
			if (flightPaymentTxDetailHistory != null && flightPaymentTxDetailHistory.getPaymentTransaction() != null
					&& flightPaymentTxDetailHistory.getPaymentTransaction().getIsPaymentSuccess() == false) {
				newPaymentTransactionDetailList = hotelAndFlightPaymentTransctionDAO
						.getPaymentTransactionDetail(flightPaymentTxDetailHistory.getPaymentTransaction());
				if (newPaymentTransactionDetailList != null && newPaymentTransactionDetailList.size() > 0) {
					for (PaymentTransactionDetail paymentTransactionDetail : newPaymentTransactionDetailList) {

						paymentTransactionDetail.setCreatedDate(
								DateConversion.convertTimestampToString(paymentTransactionDetail.getCreatedAt()));
						paymentTransactionDetail
						.setAmount(paymentTransactionDetail.getAmount().setScale(2, BigDecimal.ROUND_UP));
						restAmtCount = restAmtCount.add(paymentTransactionDetail.getAmount());
						BigDecimal balance = paymentTransaction!=null&& paymentTransaction.getAmount()!=null?paymentTransaction.getAmount().subtract(restAmtCount):restAmtCount;
						paymentTransactionDetail.setBalance(balance.setScale(BigDecimal.ROUND_UP, 2));
						paymentTransactionDetailList.add(paymentTransactionDetail);
						partialAmtCount = partialAmtCount.add(paymentTransactionDetail.getAmount());
					}
				}
				BigDecimal balance = paymentTransaction!=null&& paymentTransaction.getAmount()!=null?paymentTransaction.getAmount().subtract(partialAmtCount):partialAmtCount;
				paymentTransaction.setBalance(balance.setScale(BigDecimal.ROUND_UP, 2));
				paymentTransaction.setAmount(paymentTransaction.getAmount().setScale(BigDecimal.ROUND_UP, 2));
				emailService.sendAllHotelCustomerPayment(false, true, company, user, MediaType.IMAGE_PNG_VALUE,
						paymentTransaction, paymentTransactionDetailList, locale, request, response, servletContext,
						applicationContext);
				notificationDAO.makeEmailerTrue(notificationDetails);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	public static String minuteToTime(int minute) {
		int hour = minute / 60;
		minute %= 60;
		String p = "AM";
		if (hour > 12) {
			hour %= 12;
			p = "PM";
		}
		if (hour == 0) {
			hour = 12;
		}
		return (hour < 10 ? "0" + hour : hour) + "h" + (minute < 10 ? "0" + minute : minute) + "m" ;
	}

}
