package com.tayyarah.services;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.StringTokenizer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.AbstractContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.spring3.context.SpringWebContext;

import com.itextpdf.text.DocumentException;
import com.tayyarah.bugtracker.entity.BugTrackerHistory;
import com.tayyarah.bus.entity.BusCreditNote;
import com.tayyarah.bus.entity.BusOrderCustomerDetail;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.car.entity.CarCreditNote;
import com.tayyarah.car.entity.CarOrderRow;
import com.tayyarah.common.entity.Enquiry;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.entity.PaymentTransactionDetail;
import com.tayyarah.common.notification.Notification;
import com.tayyarah.common.notification.NotificationDetail;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.common.util.HTMLtoPDFConvertor;
import com.tayyarah.common.util.LogsRequestResponse;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.flight.entity.FlightCreditNote;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.quotation.entity.FlightQuotationHistory;
import com.tayyarah.flight.quotation.entity.FlightTravelRequest;
import com.tayyarah.flight.quotation.entity.FlightTravelRequestQuotation;
import com.tayyarah.flight.quotation.model.FlightQuotationSchema;
import com.tayyarah.hotel.entity.HotelCreditNote;
import com.tayyarah.hotel.entity.HotelOrderGuest;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.model.HotelOffineVoucherUtility;
import com.tayyarah.hotel.model.HotelReport;
import com.tayyarah.hotel.quotation.entity.HotelQuotationHistory;
import com.tayyarah.hotel.quotation.entity.HotelTravelRequest;
import com.tayyarah.hotel.quotation.entity.HotelTravelRequestQuotation;
import com.tayyarah.hotel.quotation.model.HotelQuotationSchema;
import com.tayyarah.insurance.entity.InsuranceCreditNote;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.misellaneous.entity.MiscellaneousCreditNote;
import com.tayyarah.misellaneous.entity.MiscellaneousOrderRow;
import com.tayyarah.train.entity.TrainCreditNote;
import com.tayyarah.train.entity.TrainOrderRow;
import com.tayyarah.umrah.entity.TayyarahUmrahContactDetails;
import com.tayyarah.user.entity.FrontUserDetail;
import com.tayyarah.user.entity.User;
import com.tayyarah.user.entity.WalletAmountTranferHistory;
import com.tayyarah.visa.entity.VisaCreditNote;
import com.tayyarah.visa.entity.VisaOrderRow;




@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine templateEngine;
	Date date = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	static final Logger logger = Logger.getLogger(EmailService.class);

	public boolean sendFlightOrderMailVoucher(boolean isFailed, boolean isCustomer, final FlightOrderRow flightorder, final User user,
			final Company company, final String imageContentType,
			final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext,String path, String htmlContent, Email email,boolean isNofication)
					throws MessagingException, MailException, Exception, NullPointerException{
		String recipientName = flightorder.getCustomer().getFirstName() + " " + flightorder.getCustomer().getLastName();
		String address = flightorder.getCustomer().getAddress();
		user.initLogoDisplayable();
		String receptemail = user.getEmail();
		if(isCustomer)
			receptemail=flightorder.getCustomer().getEmail()!=null && !flightorder.getCustomer().getEmail().equalsIgnoreCase("")?flightorder.getCustomer().getEmail():user.getEmail();

			// Prepare the evaluation context
			if(flightorder.getStatusAction().equalsIgnoreCase("Hold") || flightorder.getStatusAction().equalsIgnoreCase("Released"))
				flightorder.setInvoiceNo(flightorder.getOrderId());

			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
					true /* multipart */, "UTF-8");
			String apiProvider = flightorder.getProviderAPI();

			CommonConfig cc = CommonConfig.GetCommonConfig();
			String toEmails []=null;
			String CCEmails []=null;
			if(email!=null){
				if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals(""))&&(email.getToEmailAddress()!=null && !email.getToEmailAddress().equals(""))){
					toEmails=email.getToEmailAddress().split(";");
					CCEmails=email.getCcEmailAddress().split(";");
					message.setTo(toEmails);
					message.setCc(CCEmails);
				}
				else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals(""))){
					toEmails=email.getToEmailAddress().split(";");
					message.setTo(toEmails);
				}
				else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals(""))){
					CCEmails=email.getCcEmailAddress().split(";");
					message.setCc(CCEmails);
				}
				else{
					if(!flightorder.getStatusAction().equalsIgnoreCase("Failed"))
						message.setTo(receptemail);

				}
			}
			if(!isFailed)
			{
				message.setSubject("Agency: Flight E-Ticket  With PNR: "+flightorder.getPnr()+ "  With Itinerary Is  " +flightorder.getOrigin()+"-"+flightorder.getDestination());
				if(isCustomer)
					message.setSubject("Customer: Flight E-Ticket With PNR: "+flightorder.getPnr()+ "  With Itinerary Is  " +flightorder.getOrigin()+"-"+flightorder.getDestination());

			}
			else
			{
				message.setSubject("Flight Booking Failed");
			}
			message.setFrom(cc.getEmail_admin());
			String Title = "Flight E-Ticket With PNR: "+flightorder.getPnr()+ "  With Itinerary Is  " +flightorder.getOrigin()+"-"+flightorder.getDestination(); 		

			if(flightorder.getStatusAction().equalsIgnoreCase("Confirmed"))
			{
				message.setSubject("Flight Voucher  With PNR: "+flightorder.getPnr()+ "  With Itinerary Is  " +flightorder.getOrigin()+"-"+flightorder.getDestination());
				message.setCc(company.getEmail());
				message.setBcc(cc.getEmail_support());
				message.setBcc(cc.getEmail_booking()); 				
			}
			else if(flightorder.getStatusAction().equalsIgnoreCase("Hold")){
				message.setSubject("Reserved Flight Itinerary");
				message.setBcc(cc.getEmail_support());
				message.setBcc(cc.getEmail_booking());
				Title = "Reserved Flight Itinerary";
			}
			else if(flightorder.getStatusAction().equalsIgnoreCase("Released")){
				message.setSubject("Released Flight Itinerary");
				message.setBcc(cc.getEmail_support());
				message.setBcc(cc.getEmail_booking());
				Title = "Released Flight Itinerary";
			}
			else if(flightorder.getStatusAction().equalsIgnoreCase("Cancelled") && flightorder.getPaymentStatus().equalsIgnoreCase("Refund")){
				message.setSubject("Flight Voucher  With PNR: "+flightorder.getPnr()+ "  With Itinerary Is  " +flightorder.getOrigin()+"-"+flightorder.getDestination()+"  Is Cancelled");
				message.setCc(company.getEmail());
				message.setBcc(cc.getEmail_support());
				message.setBcc(cc.getEmail_booking()); 
			}
			else {
				message.setSubject("Flight E-Ticket Failed");
				Title = "Flight E-Ticket";
				if (company != null && user!=null){
					if(!flightorder.getStatusAction().equalsIgnoreCase("Failed")){
						message.setBcc(cc.getEmail_support());
						message.setBcc(cc.getEmail_booking());
						message.setCc(company.getEmail());
					}else{
						message.setTo(cc.getEmail_booking());
					}
				}

			}
			if((flightorder.getStatusAction().equalsIgnoreCase("Failed") || flightorder.getStatusAction().equalsIgnoreCase("Reserved"))&& flightorder.getBookingMode().equalsIgnoreCase("Online")){
				LogsRequestResponse logsRequestResponse = new LogsRequestResponse();
				logsRequestResponse.sendLogsToTeamLeads(flightorder,null,null, user, locale,email,company, request, response, servletContext, applicationContext,mimeMessage,message);
			}

			message.setText(htmlContent, true);
			if(isNofication){				
				//message.setSubject("Flight Reminder");
				//	added by basha
				if(email.getType() == 91){
					message.setSubject("PNR:"+flightorder.getPnr() +" - Your Flight From  "+flightorder.getOrigin()+" - "+flightorder.getDestination() +"  Departing in 24 hrs");
				}else if(email.getType() == 92){
					message.setSubject("PNR:"+flightorder.getPnr() +" - Your Flight From  "+flightorder.getOrigin()+" - "+flightorder.getDestination() +"  Departing in 4 hrs");
				}

			}else{
				message.addAttachment(Title,new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"FlightVoucherPDF", dateFormat.format(date),
						String.valueOf(new Random().nextInt())))); 
			}
			this.mailSender.send(mimeMessage);
			logger.info("Sent Flight invoice mail to...." + receptemail	+ " for invoice no " + flightorder.getInvoiceNo());
			return true;
	}

	public boolean sendFlightInvoiceSuperToOthers(final FlightOrderRow flightorder, final User user,
			final String imageContentType,
			final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext,String path,User parentUser,Company parentComapny,int type)
					throws MessagingException, MailException, Exception, NullPointerException{

		String recipientEmail = user.getEmail();

		// Prepare the evaluation context
		user.initLogoDisplayable();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("flightorder", flightorder);
		variables.put("user", user);
		variables.put("parentUser", parentUser);
		variables.put("parentComapny", parentComapny);

		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,
				servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true, "UTF-8");

		message.setSubject("Flight invoice   With Invoice No: "+flightorder.getInvoiceNo()+ "With Itinerary Is" +flightorder.getOrigin()+"-"+flightorder.getDestination());
		CommonConfig cc = CommonConfig.GetCommonConfig();
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		if(flightorder.getStatusAction().equalsIgnoreCase("Confirmed"))
		{
			message.setTo(recipientEmail);
		}else {
			logger.info("statu of flight Failed airline  ticket " +flightorder.getStatusAction());
		}



		/*if corporate is there, then it should go from corporate company id folder else from local commomn corporate
		invoice for all corporate companyes else default template should go*/

		if(parentComapny.isMyEmailDir()){
			String corporateDirName = parentComapny.getCompany_userid();
			String remotePath=CommonConfig.GetCommonConfig().getLog_location();
			boolean emailFileFound= false;
			try {
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(parentComapny.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								String htmlContent=null;
								if(files!=null && files.length>0){
									for(String f:files){
										//System.out.println(f);
										if(f.equalsIgnoreCase("Flight-Super-To-Others-Invoice-corporate.html"))
										{
											htmlContent  = this.templateEngine.process(parentComapny.getCompany_userid()+"/"+f, ctx);
											message.setText(htmlContent, true );
											message.addAttachment("Flight-Ticket-invoice",
													new File(HTMLtoPDFConvertor.corporateHtmlRawToPDF(htmlContent,
															corporateDirName, dateFormat.format(date),
															String.valueOf(new Random().nextInt()))));
											emailFileFound = true ;
											break;
										}
										if(f.equalsIgnoreCase("Flight-Dis-To-Others-Invoice-corporate.html")){
											if(type==Email.EMAIL_TYPE_DIS_INVOICE_TO_OTHERS)
											{
												htmlContent  = this.templateEngine.process(parentComapny.getCompany_userid()+"/"+f, ctx);
												message.setText(htmlContent, true );
												message.addAttachment("Flight-Ticket-invoice",
														new File(HTMLtoPDFConvertor.corporateHtmlRawToPDF(htmlContent,
																corporateDirName, dateFormat.format(date),
																String.valueOf(new Random().nextInt()))));
												emailFileFound = true ;
												break;
											}
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					String htmlContent = this.templateEngine.process("Flight-Super-To-Others-Invoice-corporate.html", ctx);
					if(type==Email.EMAIL_TYPE_DIS_INVOICE_TO_OTHERS)
					{
						htmlContent = this.templateEngine.process("Flight-Dis-To-Others-Invoice-corporate.html", ctx);
					}
					message.setText(htmlContent, true);
					message.addAttachment("Flight-Ticket-invoice",
							new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
									corporateDirName, dateFormat.format(date),
									String.valueOf(new Random().nextInt()))));
				}
			} catch (Exception e) {
				logger.error("Exception in sendFlightOrderMail");
			}
		}
		else{
			String htmlContent = this.templateEngine.process("Flight-Super-To-Others-Invoice.html", ctx);
			if(type==Email.EMAIL_TYPE_DIS_INVOICE_TO_OTHERS){
				htmlContent = this.templateEngine.process("Flight-Dis-To-Others-Invoice.html", ctx);
			}
			message.setText(htmlContent, true);

			message.addAttachment("Flight-Ticket-invoice",new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
					"FlightInvoicePDF", dateFormat.format(date),
					String.valueOf(new Random().nextInt()))));
		}
		this.mailSender.send(mimeMessage);
		logger.info("Sent Flight invoice mail to...." + recipientEmail + " for invoice no " + flightorder.getInvoiceNo());
		return true;
	}


	public boolean sendHotelOrderMailVoucher(boolean isFailed, boolean isCustomer, final HotelOrderRow hotelorder, final User user,
			final Company company, final String imageContentType,
			final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext, String htmlContent, Email email)
					throws MessagingException, MailException, Exception, NullPointerException{

		user.initLogoDisplayable();
		String receptemail=user.getEmail();


		if(isCustomer)
			receptemail=hotelorder.getOrderCustomer().getEmail()!=null && !hotelorder.getOrderCustomer().getEmail().equalsIgnoreCase("")?hotelorder.getOrderCustomer().getEmail():user.getEmail();
			if(receptemail==null && receptemail.trim().equalsIgnoreCase("")){
				receptemail=hotelorder.getHotelOrderRoomInfos().get(0).getHotelOrderGuests().get(0).getEmail();
			}

			//receptemail=hotelorder.getOrderCustomer().getEmail();
			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
			CommonConfig cc = CommonConfig.GetCommonConfig();
			String title="Hotel E-Voucher With Booking Ref No: "+hotelorder.getOrderReference()+ " With CheckIn Date Is " +checkInDate(hotelorder.getCheckInDate());
			//System.out.println("hotel title "+title);
			String toEmails []=null;
			String CCEmails []=null;
			if(email!=null){
				if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals(""))&&(email.getToEmailAddress()!=null && !email.getToEmailAddress().equals(""))){
					toEmails=email.getToEmailAddress().split(";");
					CCEmails=email.getCcEmailAddress().split(";");
					message.setTo(toEmails);
					message.setCc(CCEmails);
				}
				else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals(""))){
					toEmails=email.getToEmailAddress().split(";");
					message.setTo(toEmails);
				}
				else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals(""))){
					CCEmails=email.getCcEmailAddress().split(";");
					message.setCc(CCEmails);
				}else{
					message.setTo(receptemail);
				}
			}
			if(!isFailed)
			{
				message.setSubject("Agency: Hotel E-Voucher With Booking Ref No: "+hotelorder.getOrderReference()+ " With CheckIn Date Is " +checkInDate(hotelorder.getCheckInDate()));
				if(isCustomer)
					message.setSubject("Customer: Hotel E-Voucher With Booking Ref No: "+hotelorder.getOrderReference()+ " With CheckIn Date Is " +checkInDate(hotelorder.getCheckInDate()));
				message.setFrom(cc.getEmail_admin());
				message.setBcc(cc.getEmail_support());
				message.setBcc(cc.getEmail_booking());
				if(hotelorder.getStatusAction().equalsIgnoreCase("Confirmed")){
					message.setCc(company.getEmail());
				}
			}
			else
			{
				message.setSubject("Hotel Booking Failed");
				message.setFrom(cc.getEmail_admin());
				message.setTo(company.getEmail());
				message.setBcc(cc.getEmail_support());
				message.setBcc(cc.getEmail_booking());
			}

			if((!hotelorder.getStatusAction().equalsIgnoreCase("Confirmed") || !hotelorder.getStatusAction().equalsIgnoreCase("Reserved") )&& hotelorder.getBookingMode().equalsIgnoreCase("Online")){
				LogsRequestResponse logsRequestResponse = new LogsRequestResponse();
				boolean isSucess=logsRequestResponse.sendLogsToTeamLeads(null,hotelorder,null, user, locale,email,company, request, response, servletContext, applicationContext,mimeMessage,message);
			System.out.println("is sucess:"+isSucess);
			}

			message.setText(htmlContent, true /* isHtml */);
			message.addAttachment(
					title,
					new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
							"HotelInvoicePDF", dateFormat.format(date),
							String.valueOf(new Random().nextInt()))));


			this.mailSender.send(mimeMessage);
			logger.info("Sent Hotel Voucher mail to...."  + " for invoice no " +hotelorder.getInvoiceNo());
			return true;
	}

	public boolean sendHotelOfflineVoucherToEmail(boolean isFailed, boolean isCustomer,Email email,HotelTravelRequestQuotation hotelQuotation,	HotelReport hotelReport, Map <HotelOffineVoucherUtility, HotelOrderGuest>  hotelVoucherMap, final User user,
			final Company company, final String imageContentType,
			final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext, String htmlContent)
					throws MessagingException, MailException, Exception, NullPointerException{

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		CommonConfig cc = CommonConfig.GetCommonConfig();
		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}

		}
		message.setText(htmlContent, true /* isHtml */);
		message.setSubject("Corporate hotel vocher with selected quotation(TRNO : "+hotelQuotation.getHotelTravelRequest().getTRNo()+")");
		message.addAttachment(
				"Corporate hotel vocher with selected quotation  (TRNO : "+hotelQuotation.getHotelTravelRequest().getTRNo()+")",
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"Corporate Hotel Vocher PDF"," "+dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));

		this.mailSender.send(mimeMessage);
		return true;
	}
	public boolean sendHotelOrderInvoiceMail(final HotelOrderRow hotelorder, final User user,
			final Company company, final String imageContentType,
			final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext, User parentUser, Company parentComapny, Email email,String htmlContent )
					throws MessagingException, MailException, Exception, NullPointerException{

		user.initLogoDisplayable();
		String corporateDirName = company.getCompany_userid();
		String receptemail = hotelorder.getOrderCustomer().getEmail();
		if(receptemail==null && receptemail.trim().equalsIgnoreCase("")){
			receptemail=hotelorder.getHotelOrderRoomInfos().get(0).getHotelOrderGuests().get(0).getEmail();
		}else if(receptemail==null && receptemail.trim().equalsIgnoreCase("")){
			receptemail=user.getEmail();
		}



		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Hotel Invoice With Order Ref No: "+hotelorder.getOrderReference()+" And Invoice No: "+hotelorder.getInvoiceNo()+"With CheckIn Date Is" +checkInDate(hotelorder.getCheckInDate()));
		CommonConfig cc = CommonConfig.GetCommonConfig();
		message.setFrom(cc.getEmail_admin());
		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}
			else{
				message.setCc(cc.getEmail_booking());
				message.setTo(receptemail);
			}
		}

		String Title = "Hotel Invoice With Order Ref No: "+hotelorder.getOrderReference()+" And Invoice No: "+hotelorder.getInvoiceNo()+"With CheckIn Date Is" +checkInDate(hotelorder.getCheckInDate());
		message.setText(htmlContent, true /* isHtml */);
		message.addAttachment(Title,
				new File(HTMLtoPDFConvertor.corporateHtmlRawToPDF(htmlContent,
						corporateDirName, dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));
		this.mailSender.send(mimeMessage);
		logger.info("Sent Hotel Invoice mail to...." +receptemail +" for invoice no " +hotelorder.getInvoiceNo());

		return true;
	}

	public boolean sendEmailVerficationACKCompany(final Company company,
			final Company parentcompany,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException {

		String recipientEmail = parentcompany.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		String parentcompanytype = "Super User";
		if(parentcompany.getCompanyRole().isAgent())
			parentcompanytype = "Agency";
		else if(parentcompany.getCompanyRole().isDistributor())
			parentcompanytype = "Distributor";

		String companytype = "User";
		if(company.getCompanyRole().isAgent())
			companytype = "Agency";
		else if(company.getCompanyRole().isDistributor())
			companytype = "Distributor";


		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("parentcompanytype", parentcompanytype);
		variables.put("companytype", companytype);
		variables.put("company", company);
		variables.put("parentcompany", parentcompany);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* recipientEmail multipart */, "UTF-8");
		message.setSubject("Email Verification Successful");
		CommonConfig cc = CommonConfig.GetCommonConfig();
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		message.setTo(recipientEmail);

		// Create the HTML body using Thymeleaf
		final String htmlContent = this.templateEngine.process("CompanyEmailVerifyAckCompany.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent Email verification ACK to...." + recipientEmail + " for company name " + company.getCompanyname());
		return true;
	}
	public boolean sendEmailVerficationACKUser(final User user,
			final Company parentcompany,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException {

		String recipientEmail = parentcompany.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("user", user);
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("parentcompany", parentcompany);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* recipientEmail multipart */, "UTF-8");
		message.setSubject("Email Verification Successful");
		CommonConfig cc = CommonConfig.GetCommonConfig();
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		message.setTo(recipientEmail);

		// Create the HTML body using Thymeleaf
		final String htmlContent = this.templateEngine.process("CompanyEmailVerifyAckUser.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent Email verification ACKUser to...." + recipientEmail+ " for user name " + user.getUsername());
		return true;
	}



	public boolean sendCompanyRegistrationMail(final Company companyId,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext,String path)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException {

		String recipientEmail = companyId.getEmail();
		Company user = new Company();
		user.setImagepath(path);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("companyId", companyId);
		variables.put("user", user);
		String encodedEmailCode = URLEncoder.encode(companyId.getEmailCode(), "UTF-8");
		variables.put("emailverify", CommonConfig.GetCommonConfig().getEmail_verify_url()+"company?code="+encodedEmailCode);
		//logger.info("verification link...." + CommonConfig.GetCommonConfig().getEmail_verify_url()+"company?code="+encodedEmailCode);


		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* recipientEmail multipart */, "UTF-8");
		message.setSubject("Email Verification");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		message.setTo(recipientEmail);

		// Create the HTML body using Thymeleaf
		final String htmlContent = this.templateEngine.process("Companyregister.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent CompanyRegistrationMail to...." + recipientEmail	+ " for company name " + companyId.getCompanyname());
		return true;
	}
	public boolean sendFlightPNR(final String userid,
			final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext,final String recipientEmail,final String pnr)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException {

		Map<String, Object> variables = new HashMap<String, Object>();
		User user=new User();
		user.setId(Integer.parseInt(userid));
		user.setUsername(pnr);
		variables.put("user", user);
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Cancel this PNR");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		// Create the HTML body using Thymeleaf
		final String htmlContent = this.templateEngine.process("sendPnr.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent CompanyRegistrationMail to...." + recipientEmail	+ " for userid name " + userid);
		return true;
	}
	public boolean CompanyResetPassword(final Company companyId,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException {

		String recipientEmail = companyId.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("companyId", companyId);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Registration confirmation ");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		// Create the HTML body using Thymeleaf
		final String htmlContent = this.templateEngine.process("Companyregister.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent CompanyRegistrationMail to...." + recipientEmail	+ " for company name " + companyId.getCompanyname());
		return true;
	}

	public boolean sendCompanyDetailsMail(final Email email, final Company companyId,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext,final String imagepath ,final User user1)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException {

		String recipientEmail = companyId.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		User user = new User();
		user.setImagepath(imagepath);
		variables.put("admin_url", CommonConfig.GetCommonConfig().getIbe_url());
		variables.put("companyId", companyId);
		variables.put("user", user);
		variables.put("user1", user1);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Company Approval Mail ");
		if(email.getType() == Email.EMAIL_TYPE_COMPANY_UPDATION)
			message.setSubject("Company Updation Mail");

		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());

		final String htmlContent = this.templateEngine.process("CompanyApprovalmail.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent CompanyRegistrationMail to...." + recipientEmail	+ " for company name " + companyId.getCompanyname());
		return true;
	}

	public void sendEmailUserDetails(User user1, Company parentcompany, String imagePngValue, Locale locale,
			HttpServletRequest request, HttpServletResponse response, ServletContext servletContext,
			ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException{

		String recipientEmail = user1.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		User user = new User();
		user.setImagepath(imagePngValue);
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("companyId", parentcompany);
		variables.put("user", user1);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true , "UTF-8");
		message.setSubject("User Approval Mail ");

		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());

		final String htmlContent = this.templateEngine.process("UserApprovalmail.html", ctx);
		message.setText(htmlContent, true);
		this.mailSender.send(mimeMessage);
		logger.info("Sent CompanyRegistrationMail to...." + recipientEmail	+ " for company name " + parentcompany.getCompanyname());

	}



	/*	public boolean sendEmailUserDetails(final Email email, final Company companyId,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext,final String imagepath ,final User user1)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException {

		String recipientEmail = companyId.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		User user = new User();
		user.setImagepath(imagepath);
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("companyId", companyId);
		variables.put("user", user);
		variables.put("user1", user1);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true  multipart , "UTF-8");
		message.setSubject("Company Approval Mail ");
		if(email.getType() == Email.EMAIL_TYPE_COMPANY_UPDATION)
			message.setSubject("Company Updation Mail");

		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());

		final String htmlContent = this.templateEngine.process("CompanyApprovalmail.html", ctx);
		message.setText(htmlContent, true  isHtml );
		this.mailSender.send(mimeMessage);
		logger.info("Sent CompanyRegistrationMail to...." + recipientEmail	+ " for company name " + companyId.getCompanyname());
		return true;
	}*/




	public boolean sendCompanyUpdateMail(final Company companyId,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException {

		String recipientEmail = companyId.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("company", companyId);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Company Updation Mail ");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());

		final String htmlContent = this.templateEngine.process("CompanyUpdateMail.html", ctx);
		message.setText(htmlContent, true /* isHtml */);

		this.mailSender.send(mimeMessage);
		logger.info("Sent CompanyUpdateMail to...." + recipientEmail+ " for company name " + companyId.getCompanyname());
		return true;
	}
	public boolean sendWhiteLabelCompanyMail(final Company company,
			final CompanyConfig companyConfig, final String imageContentType,
			final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext ,final String imagepath)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException {

		String recipientEmail = company.getEmail();
		Company user = new Company();
		user.setImagepath(imagepath);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("company", company);
		variables.put("user", user);
		variables.put("companyConfig", companyConfig);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,	servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("White Label Company Mail ");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		// Create the HTML body using Thymeleaf
		final String htmlContent = this.templateEngine.process(	"whiteLabelCompanyMail.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent CompanyRegistrationMail to...." + recipientEmail + " for company name " + company.getCompanyname());
		return true;
	}

	public boolean sendCompanyConfigApprovalMail(final Company company,
			final CompanyConfig companyConfig, final String imageContentType,
			final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException {

		String recipientEmail = company.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("company", company);
		variables.put("companyConfig", companyConfig);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Config Approval Mail ");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		// Create the HTML body using Thymeleaf
		final String htmlContent = this.templateEngine.process("CompanyConfigApproval.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent CompanyRegistrationMail to...." + recipientEmail	+ " for company name " + company.getCompanyname());
		return true;
	}

	public boolean sendCompanyForgotPassWordMail(final Company companyId,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext, String imagepath)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException {

		String recipientEmail = companyId.getEmail();
		Company user = new Company();
		user.setImagepath(imagepath);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("companyId", companyId);
		variables.put("user", user);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Forgot PassWord");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());

		final String htmlContent = this.templateEngine.process(	"Companyregister.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent CompanyForgotPassWordMail to...." + recipientEmail + " for company name " + companyId.getCompanyname());
		return true;
	}

	public boolean sendUserRegistrationMail(final User user, final Company parentcompany,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException , DocumentException, IOException {

		String recipientEmail = user.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("user", user);
		variables.put("parentcompany", parentcompany);
		String encodedEmailCode = URLEncoder.encode(user.getEmailCode(), "UTF-8");
		variables.put("emailverify", CommonConfig.GetCommonConfig().getEmail_verify_url()+"user?code="+encodedEmailCode);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		//	response.setContentType("application/pdf");
		//	response.setHeader("content-disposition", "htmlContent; filename="+ "Filename.pdf");
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Registration confirmation");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		// Create the HTML body using Thymeleaf
		final String htmlContent = this.templateEngine.process("UserRegister.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent UserRegistrationMail to...." + recipientEmail	+ " for user name " + user.getUsername());
		return true;
	}

	public boolean sendUserCredentialMail(final User user, final Company parentcompany,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException , DocumentException, IOException {

		String recipientEmail = user.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getIbe_url());
		variables.put("user", user);
		variables.put("parentcompany", parentcompany);
		String encodedEmailCode = URLEncoder.encode(user.getEmailCode(), "UTF-8");
		variables.put("emailverify", CommonConfig.GetCommonConfig().getEmail_verify_url()+"user?code="+encodedEmailCode);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		response.setContentType("application/pdf");
		response.setHeader("content-disposition", "htmlContent; filename="+ "Filename.pdf");
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Login Credentials");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());

		final String htmlContent = this.templateEngine.process("UserApprovalmail.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent UserRegistrationMail to...." + recipientEmail	+ " for user name " + user.getUsername());
		return true;
	}

	public boolean sendUserForgotPasswordMail(final User user, final Company parentcompany,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException , DocumentException, IOException {

		String recipientEmail = user.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		System.out.println("CommonConfig.GetCommonConfig().getAdmin_url()-------"+CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("user", user);
		variables.put("parentcompany", parentcompany);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		/*response.setContentType("application/pdf");
		response.setHeader("content-disposition", "htmlContent; filename="+ "Filename.pdf");*/
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Registration confirmation");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());

		final String htmlContent = this.templateEngine.process("UserForgotPassword.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent UserRegistrationMail to...." + recipientEmail	+ " for user name " + user.getUsername());
		return true;
	}

	public boolean sendUserResetPassword(final User userId,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext,String imagepath)
					throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException , DocumentException, IOException, Exception {

		String recipientEmail = userId.getEmail();
		User user = new User();
		user.setImagepath(imagepath);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("userId", userId);
		variables.put("user", user);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		response.setContentType("application/pdf");
		response.setHeader("content-disposition", "htmlContent; filename="+ "Filename.pdf");
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Registration confirmation");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());


		final String htmlContent = this.templateEngine.process("UserResetPassword.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent UserRegistrationMail to...." + recipientEmail	+ " for user name " + userId.getUsername());
		return true;
	}

	public boolean sendLockedUserMail(final User userId,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext,String imagepath
			)
					throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException , DocumentException, IOException, Exception {

		String recipientEmail = userId.getEmail();
		User user = new User();
		user.setImagepath(imagepath);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("user", user);
		variables.put("userId", userId);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		response.setContentType("application/pdf");
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject(" Unlocked confirmation");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());

		final String htmlContent = this.templateEngine.process("lockedUserMail.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent UserRegistrationMail to...." + recipientEmail	+ " for user name " + userId.getUsername());

		return true;
	}

	public boolean sendUserForgotPasswordMail(final User userId,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException , DocumentException, IOException, Exception {

		String recipientEmail = userId.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("userId", userId);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Forgot Password");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(userId.getEmail());
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());

		final String htmlContent = this.templateEngine.process("ForgotPasswordOfUser.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent UserForgotPasswordMail to...." + recipientEmail+ " for user name " + userId.getUsername());
		return true;
	}

	public boolean sendFrontUserDetailMail(final FrontUserDetail FrontuserId,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext,Email email)
					throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException , DocumentException, IOException, Exception {

		String recipientEmail = FrontuserId.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getIbe_url());
		variables.put("FrontuserId", FrontuserId);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("User Detail");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(FrontuserId.getEmail());
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		String htmlContent = "";
		if(email.getType() == Email.EMAIL_TYPE_FRONT_USER_REGISTRATION){
			htmlContent = this.templateEngine.process("FrontuserRegister.html", ctx);
		}
		if(email.getType() == Email.EMAIL_TYPE_FRONT_USER_REGISTRATION_BY_TAYYARAH){
			htmlContent = this.templateEngine.process("FrontuserRegisterByTayyarah.html", ctx);
		}
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent FrontUserDetailMail to...." + recipientEmail	+ " for user name " + FrontuserId.getFirstName());
		return true;
	}

	public boolean sendFrontUserForgotPasswordMail(
			final FrontUserDetail FrontuserId, final String imageContentType,
			final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException , DocumentException, IOException, Exception {

		String recipientEmail = FrontuserId.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("FrontuserId", FrontuserId);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());

		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);
		((AbstractContext) ctx).setVariable("message","I don't want to live on this planet anymore");
		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("ForgotPassword Mail");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(FrontuserId.getEmail());
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());

		final String htmlContent = this.templateEngine.process("FrontuserForgotPassword.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent FrontUserForgotPasswordMail to...." + recipientEmail+ " for user name " + FrontuserId.getFirstName());
		return true;
	}

	public boolean sendFlightBookedTicketMail(final List<FlightOrderRow> flightorder,
			final User user, final String imageContentType,
			final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext,String path)
					throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException , DocumentException, IOException, Exception {

		String companyEmail = user.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		User user1= new User();
		user1.setImagepath(path);
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("flightorder", flightorder);
		variables.put("user1", user1);
		variables.put("user", user);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Flight Invoice");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(companyEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());

		final String htmlContent = this.templateEngine.process("Flight-Booked-Email-Invoice.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		message.addAttachment(
				"Flight-Booked-Email-Invoice.html",
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"Flig-Booked-Invoice", dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));

		this.mailSender.send(mimeMessage);
		logger.info("Sent Flight invoice mail to...." + companyEmail + " for invoice no ");
		return true;
	}
	public boolean sendHotelBookedTicketMail(final List<HotelOrderRow> hotelOrderRow,
			final User user, final String imageContentType,
			final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext,String path)
					throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException , DocumentException, IOException, Exception {

		String companyEmail = user.getEmail();
		User user1= new User();
		user1.setImagepath(path);
		// Prepare the evaluation context
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("hotelOrderRow", hotelOrderRow);
		variables.put("user1", user1);
		variables.put("user", user);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Hotel Booked Invoice");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(user.getEmail());
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		final String htmlContent = this.templateEngine.process("Hotel-Booked-Email-Invoice.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		message.addAttachment(
				"Hotel-Booked-Email-Invoice.html",
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"Hotel-Booked-Invoice", dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));
		this.mailSender.send(mimeMessage);
		logger.info("Sent Flight invoice mail to...." + companyEmail+ " for invoice no ");
		return true;
	}
	public boolean sendFlightCancelTicketMail(final FlightOrderRow flightorder,
			final FlightCreditNote creditnote, final User user,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext,String path)
					throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException , DocumentException, IOException, Exception {

		String recipientEmail = user.getEmail();
		String customerEmail = flightorder.getCustomer().getEmail();

		// Prepare the evaluation context
		User user1= new User();
		user1.setImagepath(path);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("flightorder", flightorder);
		variables.put("user1", user1);
		variables.put("user", user);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Ticket Canceld");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(customerEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		final String htmlContent = this.templateEngine.process("Flight-Cancel-Email-Invoice.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		message.addAttachment(
				"Booked-Ticket-invoice",
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"FlightInvoicePDF", dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));
		this.mailSender.send(mimeMessage);
		logger.info("Sent Flight invoice mail to...." + recipientEmail+ " for invoice no " + flightorder.getInvoiceNo());
		return true;
	}

	public boolean sendWalletTransferNotification(final Email email, final User user, final WalletAmountTranferHistory walletHistory, final Company parentcompany,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException , DocumentException, IOException {

		String recipientEmail = user.getEmail();
		Boolean isParentToChild = true;
		if(email.getType() == Email.EMAIL_TYPE_WALLET_CREDIT_PARENT_NOTIFICATION || email.getType() == Email.EMAIL_TYPE_WALLET_DEBIT_PARENT_NOTIFICATION || email.getType() == Email.EMAIL_TYPE_WALLET_DEPOSIT_PARENT_NOTIFICATION)
		{
			recipientEmail = parentcompany.getEmail();
			isParentToChild = false;
		}

		Map<String, Object> variables = new HashMap<String, Object>();
		try{
			BigDecimal amount = walletHistory.getAmount();
			walletHistory.setAmount(amount.setScale(2,
					BigDecimal.ROUND_HALF_UP));
			BigDecimal currentbalance = walletHistory.getClosingBalance();
			walletHistory.setClosingBalance(currentbalance.setScale(2,
					BigDecimal.ROUND_HALF_UP));
			BigDecimal currentbalanceParent = walletHistory.getParentClosingBalance();
			walletHistory.setParentClosingBalance(currentbalanceParent.setScale(2,
					BigDecimal.ROUND_HALF_UP));
		} catch (Exception e) {
			logger.info("taxes amount to booking currency....Exception----  ");
		}
		String walletaction = walletHistory.getTransactionType();
		if(email.getType() == Email.EMAIL_TYPE_WALLET_CREDIT_PARENT_NOTIFICATION || email.getType() == Email.EMAIL_TYPE_WALLET_DEPOSIT_PARENT_NOTIFICATION ||  email.getType() == Email.EMAIL_TYPE_WALLET_WITHDRAW_CHILD_NOTIFICATION)
			walletaction = "debit";
		if(email.getType() == Email.EMAIL_TYPE_WALLET_DEBIT_PARENT_NOTIFICATION || email.getType() == Email.EMAIL_TYPE_WALLET_WITHDRAW_PARENT_NOTIFICATION)
			walletaction = "credit";


		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("walletaction", walletaction);
		variables.put("isParentToChild", isParentToChild);
		variables.put("user", user);
		variables.put("email", email);
		variables.put("wallethistory", walletHistory);
		variables.put("parentcompany", parentcompany);
		HashMap<String, String> datetimemap = CommonUtil.getDateTimeMapFromTimestamp(walletHistory.getCreatedAt());
		CommonConfig conf = CommonConfig.GetCommonConfig();
		String walletalertpath = CommonConfig.GetCommonConfig().getAdmin_url()+"images/walletalert.png";
		variables.put("walletdate", datetimemap.get("date"));
		variables.put("wallettime", datetimemap.get("time"));
		variables.put("walletalertpath", walletalertpath);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		response.setContentType("application/pdf");
		response.setHeader("content-disposition", "htmlContent; filename="+ "Filename.pdf");
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);
		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Wallet "+walletaction+" Notification");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		// Create the HTML body using Thymeleaf
		final String htmlContent = this.templateEngine.process("WalletNotification.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent UserRegistrationMail to...." + recipientEmail	+ " for user name " + user.getUsername());
		return true;
	}

	public boolean sendWalletTransferNotificationSuperUser(final Boolean isParentToChild, final User user, final WalletAmountTranferHistory walletHistory, final Company parentcompany,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException , DocumentException, IOException {

		String recipientEmail = user.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		try{
			BigDecimal amount = walletHistory.getAmount();
			walletHistory.setAmount(amount.setScale(2,
					BigDecimal.ROUND_HALF_UP));
			BigDecimal currentbalanceParent = walletHistory.getParentClosingBalance();
			walletHistory.setParentClosingBalance(currentbalanceParent.setScale(2,
					BigDecimal.ROUND_HALF_UP));
			walletHistory.setClosingBalance(currentbalanceParent.setScale(2,
					BigDecimal.ROUND_HALF_UP));
		} catch (Exception e) {
			logger.info("taxes amount to booking currency....Exception----  ");
		}
		String parentaction = walletHistory.getTransactionType();
		String childaction = "credit";
		if(parentaction.equalsIgnoreCase("credit"))
			childaction = "debit";
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("parentaction", parentaction);
		variables.put("childaction", childaction);
		variables.put("isParentToChild", isParentToChild);
		variables.put("user", user);
		variables.put("wallethistory", walletHistory);
		variables.put("parentcompany", parentcompany);
		HashMap<String, String> datetimemap = CommonUtil.getDateTimeMapFromTimestamp(walletHistory.getCreatedAt());
		CommonConfig conf = CommonConfig.GetCommonConfig();
		String walletalertpath = CommonConfig.GetCommonConfig().getAdmin_url()+"images/walletalert.png";
		variables.put("walletdate", datetimemap.get("date"));
		variables.put("wallettime", datetimemap.get("time"));
		variables.put("walletalertpath", walletalertpath);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		response.setContentType("application/pdf");
		response.setHeader("content-disposition", "htmlContent; filename="+ "Filename.pdf");
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);
		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Wallet "+parentaction+" Notification");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(recipientEmail);
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		// Create the HTML body using Thymeleaf
		final String htmlContent = this.templateEngine.process("WalletNotification.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent UserRegistrationMail to...." + recipientEmail	+ " for user name " + user.getUsername());
		return true;
	}

	public boolean sendCreditNoteRequestFlight(final FlightOrderRow flightorder,
			final FlightCreditNote creditnote, final User user, final Company company, final Company parentCompany,
			final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException , DocumentException, IOException, Exception {

		String recipientEmail = user.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("creditnote", creditnote);
		variables.put("flightorder", flightorder);
		variables.put("user", user);
		variables.put("company", company);
		variables.put("parentcompany", parentCompany);
		HashMap<String, String> datetimemap = CommonUtil.getDateTimeMapFromTimestamp(creditnote.getOrderedAt());
		variables.put("requestdate", datetimemap.get("date"));
		variables.put("requesttime", datetimemap.get("time"));
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Credit Note Request on Flight Booking With PNR: "+flightorder.getPnr()+ "  With Itinerary Is  " +flightorder.getOrigin()+"-"+flightorder.getDestination());
		String title="Flight FlightCreditNote Request With PNR: "+flightorder.getPnr()+ "  With Itinerary Is  " +flightorder.getOrigin()+"-"+flightorder.getDestination();
		CommonConfig cc=CommonConfig.GetCommonConfig();
		if (parentCompany != null)
			message.setTo(parentCompany.getEmail());

		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(company.getEmail());
		message.setBcc(cc.getEmail_booking());
		final String htmlContent = this.templateEngine.process("Flight-Request-Creditnote.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		message.addAttachment(
				title,
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"FlightInvoicePDF", "Credit Request-Flight-"+dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));

		this.mailSender.send(mimeMessage);
		logger.info("############Credit Note Request sent to...." + recipientEmail+ " for invoice no " + flightorder.getInvoiceNo());
		return true;
	}

	public boolean sendCreditNoteIssueFlight(final FlightOrderRow flightorder,
			final FlightCreditNote creditnote, final User user, final Company company, final Company parentCompany,
			final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext ,final String htmlContent, Email email)
					throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException , DocumentException, IOException, Exception {
		String recipientEmail = user.getEmail();

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Credit Note Issued on Flight Booking With PNR: "+flightorder.getPnr()+ "  With Itinerary Is  " +flightorder.getOrigin()+"-"+flightorder.getDestination());
		String title= "Credit Note Issued on Flight Booking With PNR: "+flightorder.getPnr()+ "  With Itinerary Is  " +flightorder.getOrigin()+"-"+flightorder.getDestination();
		CommonConfig cc=CommonConfig.GetCommonConfig();
		if (company != null)
			if (company != null)
				message.setTo(company.getEmail());
		message.setFrom(cc.getEmail_admin());
		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}
			else{
				message.setCc(cc.getEmail_booking());
			}
		}
		message.setBcc(cc.getEmail_support());
		message.setBcc(parentCompany.getEmail());
		message.setBcc(cc.getEmail_booking());

		message.setText(htmlContent, true /* isHtml */);
		message.addAttachment(
				title,
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"FlightCreditnoteIssued", "Credit Issue-Flight-"+dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));

		this.mailSender.send(mimeMessage);
		logger.info("############Credit Note Issued to...." + recipientEmail+ " for invoice no " + flightorder.getInvoiceNo());
		return true;
	}

	public boolean sendCreditNoteRequestHotel(final HotelOrderRow hotelorder,
			final HotelCreditNote hotelcreditnote, final User user, final Company company, final Company parentCompany,
			final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException , DocumentException, IOException, Exception {
		String recipientEmail = user.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("hotelcreditnote", hotelcreditnote);
		variables.put("hotelorder", hotelorder);
		variables.put("user", user);
		variables.put("company", company);
		variables.put("parentcompany", parentCompany);
		HashMap<String, String> datetimemap = CommonUtil.getDateTimeMapFromTimestamp(hotelcreditnote.getOrderedAt());
		variables.put("requestdate", datetimemap.get("date"));
		variables.put("requesttime", datetimemap.get("time"));
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Credit Note Request on Hotel Booking With Booking Ref No: "+hotelorder.getOrderReference()+ " With CheckIn Date Is " +checkInDate(hotelorder.getCheckInDate()));
		String title="Credit Note Request on Hotel Booking With Booking Ref No: "+hotelorder.getOrderReference()+ " With CheckIn Date Is " +checkInDate(hotelorder.getCheckInDate());
		CommonConfig cc=CommonConfig.GetCommonConfig();
		if (parentCompany != null)
			message.setTo(parentCompany.getEmail());
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(company.getEmail());
		message.setBcc(cc.getEmail_booking());

		final String htmlContent = this.templateEngine.process("Hotel-Creditnote-Request.html", ctx);
		message.setText(htmlContent, true);
		message.addAttachment(
				title,
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"FlightInvoicePDF", "Credit Request-Hotel-"+dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));

		this.mailSender.send(mimeMessage);
		logger.info("############Credit Note Request sent to...." + recipientEmail	+ " for invoice no " + hotelorder.getInvoiceNo());
		return true;
	}

	public boolean sendCreditNoteIssueHotel( final User user, final Company company, final Company parentCompany,
			final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext, String htmlContent,Email email,HotelOrderRow hotelorder)
					throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException , DocumentException, IOException, Exception {
		String recipientEmail = user.getEmail();

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Credit Note Issued on Hotel Booking  With Booking Ref No: "+hotelorder.getOrderReference()+ " With CheckIn Date Is " +checkInDate(hotelorder.getCheckInDate()));
		String title="Credit Note Issued on Hotel Booking  With Booking Ref No: "+hotelorder.getOrderReference()+ " With CheckIn Date Is " +checkInDate(hotelorder.getCheckInDate());
		CommonConfig cc=CommonConfig.GetCommonConfig();

		if (company != null)
			message.setTo(company.getEmail());
		message.setFrom(cc.getEmail_admin());
		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}
			else{
				message.setCc(cc.getEmail_booking());
			}
		}
		message.setBcc(cc.getEmail_support());
		message.setBcc(parentCompany.getEmail());
		message.setBcc(cc.getEmail_booking());
		message.setText(htmlContent, true /* isHtml */);
		message.addAttachment(
				title,
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"HotelCreditNote", "Credit Note-Hotel-"+dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));

		this.mailSender.send(mimeMessage);
		return true;
	}

	public boolean sendEnquiryEmail(final Enquiry enquiry,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException , DocumentException, IOException {
		Map<String, Object> variables = new HashMap<String, Object>();
		CommonConfig conf = CommonConfig.GetCommonConfig();
		variables.put("logo", conf.getDefult_image_path());
		variables.put("enquiry", enquiry);
		HashMap<String, String> checkindatetimemap = CommonUtil.getDateTimeMapFromTimestamp(enquiry.getCheckinDateTime());
		variables.put("checkindate", checkindatetimemap.get("date"));
		variables.put("checkintime", checkindatetimemap.get("time"));

		HashMap<String, String> checkoutdatetimemap = CommonUtil.getDateTimeMapFromTimestamp(enquiry.getCheckoutDateTime());
		variables.put("checkoutdate", checkoutdatetimemap.get("date"));
		variables.put("checkouttime", checkoutdatetimemap.get("time"));

		variables.put("preferredtime", enquiry.getPreferedDateTime());

		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		response.setContentType("application/pdf");
		response.setHeader("content-disposition", "htmlContent; filename="+ "Filename.pdf");
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);
		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("User Enquiry");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo(cc.getEmail_booking());
		message.setFrom(enquiry.getUserEmail());
		message.setBcc(cc.getEmail_booking());

		final String htmlContent = this.templateEngine.process("UserEnquiry.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		logger.info("Sent UserEnquiry to...." + cc.getEmail_support()+ " from " + enquiry.getUserEmail());
		return true;
	}

	public boolean sendHotelQuotationEmail(final User user,
			final Company company,
			final HotelTravelRequest HotelTravelRequest,
			final List<HotelTravelRequestQuotation> quotations,
			final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException , DocumentException, IOException {

		Map<String, Object> variables = new HashMap<String, Object>();
		CommonConfig conf = CommonConfig.GetCommonConfig();
		Map<Integer, ArrayList<String>> rowDisplayables = new HashMap<Integer, ArrayList<String>>();
		ArrayList<String> headerDisplayables = new ArrayList<String>();
		headerDisplayables.add("");

		if(quotations!=null && quotations.size()>0)
		{
			for(int j=0; j<quotations.size(); j++)
			{

				HotelTravelRequestQuotation hotelTravelRequestQuotation = quotations.get(j);
				if(hotelTravelRequestQuotation != null)
				{
					if(j==0)
						headerDisplayables.add("Preferred Option");
					else
						headerDisplayables.add("Option "+(j+1));
					try{
						String date = hotelTravelRequestQuotation.getAdditionalData();
						StringTokenizer tok=new StringTokenizer(date, "[<#\\#>]");
						List<String> addList=  new LinkedList<>();
						while(tok!=null && tok.hasMoreTokens()){
							String token=tok.nextToken();
							addList.add(token.trim());
						}

						List<HotelQuotationSchema> hotelQuotationSchemalist = new ArrayList<HotelQuotationSchema>();
						if(addList!=null && addList.size()>0)
						{
							Iterator<String> itr=addList.iterator();
							String[] partsTest=null;
							if(itr!=null)
							{
								while (itr.hasNext()) {
									String s=itr.next();
									if(s!=null)
									{
										partsTest=s.split("\\(\\|\\)");
										logger.info("partsTest... "+partsTest);
										if(partsTest!=null && partsTest.length>4)
										{
											logger.info("partsTest... " + partsTest[2]);
											hotelQuotationSchemalist.add(new HotelQuotationSchema(partsTest[0], partsTest[1], partsTest[2], Integer.parseInt(partsTest[3]), partsTest[4], partsTest[5]));
										}
									}
								}
							}
							if(hotelQuotationSchemalist!=null && hotelQuotationSchemalist.size()>0)
							{
								Collections.sort(hotelQuotationSchemalist, new Comparator<HotelQuotationSchema>() {
									@Override
									public int compare(HotelQuotationSchema lhs, HotelQuotationSchema rhs) {
										// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
										if(lhs == null || rhs == null)
											return 0;
										return lhs.getPositionNumber() > rhs.getPositionNumber() ? -1 : (lhs.getPositionNumber() < rhs.getPositionNumber() ) ? 1 : 0;
									}
								});
							}
						}

						int tripIndex=0;
						int schemaIndex = 0;

						if(hotelQuotationSchemalist!=null && hotelQuotationSchemalist.size()>0)
						{
							for (HotelQuotationSchema hotelQuotationSchema : hotelQuotationSchemalist) {
								Integer position = (hotelQuotationSchema.getPositionNumber());
								ArrayList<String> columnDisplayables = (rowDisplayables.get(position)!=null)?rowDisplayables.get(position): new ArrayList<String> ();
								if(j==0 && columnDisplayables!=null && columnDisplayables.size() == 0)
								{
									if(hotelQuotationSchema.getDisplayName()!=null && hotelQuotationSchema.getDisplayName().equalsIgnoreCase("Prefer Property"))
									{
										tripIndex= position;
									}
									columnDisplayables.add(hotelQuotationSchema.getDisplayName());
								}

								columnDisplayables.add(hotelQuotationSchema.getData());
								rowDisplayables.put(position, columnDisplayables);
							}
						}
						if(rowDisplayables!=null && rowDisplayables.size()>0)
						{
							for(Entry<Integer, ArrayList<String>> entry : rowDisplayables.entrySet()) {
								Integer key = entry.getKey();
								ArrayList<String> value = entry.getValue();
								if(value!=null && value.size()>0)
								{
									for(int i=0;i<value.size();i++)
									{
										String val = value.get(i);
										if(key==tripIndex && i>0 && val!=null)
										{
											if( val.equalsIgnoreCase("true"))
											{
												value.set(i, "Yes");
											}
											else if( val.equalsIgnoreCase("false"))
											{
												value.set(i, "No");
											}
										}
									}
								}
							}
						}
					}
					catch(Exception e)
					{
						System.out.println(e);
						throw new Exception("Unparsable quotation schema :: ERROR"+e);
					}
				}

			}
		}

		rowDisplayables.put(0,headerDisplayables);
		//variables.put("mapsize", 4);
		variables.put("user", user);
		variables.put("company", company);
		variables.put("travelrequest", HotelTravelRequest);
		variables.put("displayables", rowDisplayables.values());
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		response.setContentType("application/pdf");
		response.setHeader("content-disposition", "htmlContent; filename="+ "Filename.pdf");
		final IWebContext ctx = new SpringWebContext(request, response,
				servletContext, locale, variables, applicationContext);
		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
				true /* multipart */, "UTF-8");
		message.setSubject("Hotel Travel Request (ID # "+HotelTravelRequest.getTRNo()+" ) Quotations ");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setFrom(cc.getEmail_admin());
		message.setTo(user.getEmail());
		final String htmlContent = this.templateEngine.process("Hotel-Quotation-Email.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		/*message.addAttachment(
				"Hotel Travel Request (ID # "+HotelTravelRequest.getTRNo()+" ) Quotations ",
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"FlightInvoicePDF", "Hotel-quotation-"+dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));*/
		this.mailSender.send(mimeMessage);
		logger.info("Sent Hotel Quotation Response to...." + user.getEmail()+ " from " + cc.getEmail_admin());
		return true;
	}


	/*_---------------------------------for alternative emails-----------------------------*/

	public boolean sendHotelQuotationAlternativeEmail(final User user,
			final Company company,
			final HotelTravelRequest HotelTravelRequest,
			final List<HotelTravelRequestQuotation> quotations,
			final HotelQuotationHistory hotelQuotationHistoryEmail,
			final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException , DocumentException, IOException {

		Map<String, Object> variables = new HashMap<String, Object>();
		Map<Integer, ArrayList<String>> rowDisplayables = new HashMap<Integer, ArrayList<String>>();
		ArrayList<String> headerDisplayables = new ArrayList<String>();
		headerDisplayables.add("");
		if(quotations!=null && quotations.size()>0)
		{
			for(int j=0; j<quotations.size(); j++)
			{

				HotelTravelRequestQuotation hotelTravelRequestQuotation = quotations.get(j);
				if(hotelTravelRequestQuotation != null)
				{
					if(j==0)
						headerDisplayables.add("Preferred Option");
					else
						headerDisplayables.add("Option "+(j+1));
					try{
						String date = hotelTravelRequestQuotation.getAdditionalData();
						StringTokenizer tok=new StringTokenizer(date, "[<#\\#>]");
						List<String> addList=  new LinkedList<>();
						while(tok!=null && tok.hasMoreTokens()){
							String token=tok.nextToken();
							addList.add(token.trim());
						}

						List<HotelQuotationSchema> hotelQuotationSchemalist = new ArrayList<HotelQuotationSchema>();
						if(addList!=null && addList.size()>0)
						{
							Iterator<String> itr=addList.iterator();
							String[] partsTest=null;
							if(itr!=null)
							{
								while (itr.hasNext()) {
									String s=itr.next();
									if(s!=null)
									{
										partsTest=s.split("\\(\\|\\)");
										logger.info("partsTest... "+partsTest);
										if(partsTest!=null && partsTest.length>4)
										{
											logger.info("partsTest... " + partsTest[2]);
											hotelQuotationSchemalist.add(new HotelQuotationSchema(partsTest[0], partsTest[1], partsTest[2], Integer.parseInt(partsTest[3]), partsTest[4], partsTest[5]));
										}
									}
								}
							}
							if(hotelQuotationSchemalist!=null && hotelQuotationSchemalist.size()>0)
							{
								Collections.sort(hotelQuotationSchemalist, new Comparator<HotelQuotationSchema>() {
									@Override
									public int compare(HotelQuotationSchema lhs, HotelQuotationSchema rhs) {
										// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
										if(lhs == null || rhs == null)
											return 0;
										return lhs.getPositionNumber() > rhs.getPositionNumber() ? -1 : (lhs.getPositionNumber() < rhs.getPositionNumber() ) ? 1 : 0;
									}
								});
							}
						}

						int tripIndex=0;
						int schemaIndex = 0;

						if(hotelQuotationSchemalist!=null && hotelQuotationSchemalist.size()>0)
						{
							for (HotelQuotationSchema hotelQuotationSchema : hotelQuotationSchemalist) {
								Integer position = (hotelQuotationSchema.getPositionNumber());
								ArrayList<String> columnDisplayables = (rowDisplayables.get(position)!=null)?rowDisplayables.get(position): new ArrayList<String> ();
								if(j==0 && columnDisplayables!=null && columnDisplayables.size() == 0)
								{
									if(hotelQuotationSchema.getDisplayName()!=null && hotelQuotationSchema.getDisplayName().equalsIgnoreCase("Prefer Property"))
									{
										tripIndex= position;
									}
									columnDisplayables.add(hotelQuotationSchema.getDisplayName());
								}

								columnDisplayables.add(hotelQuotationSchema.getData());
								rowDisplayables.put(position, columnDisplayables);
							}
						}
						if(rowDisplayables!=null && rowDisplayables.size()>0)
						{
							for(Entry<Integer, ArrayList<String>> entry : rowDisplayables.entrySet()) {
								Integer key = entry.getKey();
								ArrayList<String> value = entry.getValue();
								if(value!=null && value.size()>0)
								{
									for(int i=0;i<value.size();i++)
									{
										String val = value.get(i);
										if(key==tripIndex && i>0 && val!=null)
										{
											if( val.equalsIgnoreCase("true"))
											{
												value.set(i, "Yes");
											}
											else if( val.equalsIgnoreCase("false"))
											{
												value.set(i, "No");
											}
										}
									}
								}
							}
						}
					}
					catch(Exception e)
					{
						System.out.println(e);
						throw new Exception("Unparsable quotation schema :: ERROR"+e);
					}
				}

			}
		}
		rowDisplayables.put(0,headerDisplayables);
		variables.put("user", user);
		variables.put("company", company);
		variables.put("travelrequest", HotelTravelRequest);
		variables.put("displayables", rowDisplayables.values());
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		response.setContentType("application/pdf");
		response.setHeader("content-disposition", "htmlContent; filename="+ "Filename.pdf");
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);
		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Hotel Travel Request (ID # "+HotelTravelRequest.getTRNo()+" ) Quotations ");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setFrom(cc.getEmail_admin());
		String toEmails []=null;
		String CCEmails []=null;
		if(hotelQuotationHistoryEmail!=null){
			if( hotelQuotationHistoryEmail.getCcMails()!=null && !hotelQuotationHistoryEmail.getCcMails().equals("") && !hotelQuotationHistoryEmail.getToMails().equals("")){
				toEmails=hotelQuotationHistoryEmail.getToMails().split(";");
				CCEmails=hotelQuotationHistoryEmail.getCcMails().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if(hotelQuotationHistoryEmail.getToMails()!=null && !hotelQuotationHistoryEmail.getToMails().equals("")){
				toEmails=hotelQuotationHistoryEmail.getToMails().split(";");
				message.setTo(toEmails);
			}
			else if(hotelQuotationHistoryEmail.getCcMails()!=null && !hotelQuotationHistoryEmail.getCcMails().equals("")){
				CCEmails=hotelQuotationHistoryEmail.getCcMails().split(";");
				message.setCc(CCEmails);
			}

		}

		final String htmlContent = this.templateEngine.process("Hotel-Quotation-Email.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		/*message.addAttachment(
				"Hotel Travel Request (ID # "+HotelTravelRequest.getTRNo()+" ) Quotations ",
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"FlightInvoicePDF", "Hotel-quotation-"+dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));	*/
		// step 1
		this.mailSender.send(mimeMessage);
		logger.info("Sent Hotel Quotation Response to...." + user.getEmail()+ " from " + cc.getEmail_admin());
		return true;
	}


	public boolean sendFlightQuotationAlternativeEmail(final User user,
			final Company company,
			final FlightTravelRequest flightTravelRequest,
			final List<FlightTravelRequestQuotation> quotations,
			final FlightQuotationHistory flightQuotationHistoryEmail,
			final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException , DocumentException, IOException {

		Map<String, Object> variables = new HashMap<String, Object>();
		Map<Integer, ArrayList<String>> rowDisplayables = new HashMap<Integer, ArrayList<String>>();
		ArrayList<String> headerDisplayables = new ArrayList<String>();
		headerDisplayables.add("");
		if(quotations!=null && quotations.size()>0)
		{
			for(int j=0; j<quotations.size(); j++)
			{
				FlightTravelRequestQuotation flightTravelRequestQuotation = quotations.get(j);
				if(flightTravelRequestQuotation != null)
				{
					if(j==0)
						headerDisplayables.add("Preferred Option");
					else
						headerDisplayables.add("Option "+(j+1));
					try{
						String date =flightTravelRequestQuotation.getAdditionalData();
						StringTokenizer tok=new StringTokenizer(date, "[<#\\#>]");
						if(tok!=null)
						{
							List<String> addList=  new LinkedList<>();
							while(tok.hasMoreTokens()){
								String token=tok.nextToken();
								addList.add(token.trim());
							}
							List<FlightQuotationSchema> flightQuotationSchemalist = new ArrayList<FlightQuotationSchema>();
							if(addList!=null && addList.size()>0)
							{
								Iterator<String> itr=addList.iterator();
								String[] partsTest=null;

								while (itr.hasNext()) {
									String s=itr.next();
									if(s!=null)
									{
										partsTest=s.split("\\(\\|\\)");
										logger.info("partsTest... " + partsTest.toString());
										if(partsTest!=null && partsTest.length>4)
										{
											logger.info("partsTest... " + partsTest[2]);
											flightQuotationSchemalist.add(new FlightQuotationSchema(partsTest[0], partsTest[1], partsTest[2], Integer.parseInt(partsTest[3]), partsTest[4], partsTest[5]));
										}
									}
								}
							}
							if(flightQuotationSchemalist!=null && flightQuotationSchemalist.size()>0)
							{
								Collections.sort(flightQuotationSchemalist, new Comparator<FlightQuotationSchema>() {
									@Override
									public int compare(FlightQuotationSchema lhs, FlightQuotationSchema rhs) {
										// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
										if(lhs == null || rhs == null)
											return 0;
										return lhs.getPositionNumber() > rhs.getPositionNumber() ? -1 : (lhs.getPositionNumber() < rhs.getPositionNumber() ) ? 1 : 0;
									}
								});

								int tripIndex=0;
								int schemaIndex = 0;

								for (FlightQuotationSchema flightQuotationSchema : flightQuotationSchemalist) {
									Integer position = (flightQuotationSchema.getPositionNumber());
									ArrayList<String> columnDisplayables = (rowDisplayables.get(position)!=null)?rowDisplayables.get(position): new ArrayList<String> ();
									if(j==0 && columnDisplayables.size() == 0)
									{

										if(flightQuotationSchema.getDisplayName()!=null && flightQuotationSchema.getDisplayName().equalsIgnoreCase("Trip Type"))
										{
											tripIndex= position;
										}
										columnDisplayables.add(flightQuotationSchema.getDisplayName());

									}
									columnDisplayables.add(flightQuotationSchema.getData());
									rowDisplayables.put(position, columnDisplayables);
									schemaIndex++;
								}

								if(rowDisplayables!=null && rowDisplayables.size()>0)
								{
									for(Entry<Integer, ArrayList<String>> entry : rowDisplayables.entrySet()) {
										Integer key = entry.getKey();
										ArrayList<String> value = entry.getValue();
										if(value!=null && value.size()>0)
										{
											for(int i=0;i<value.size();i++){
												String val = value.get(i);
												if(key==tripIndex && i>0 && val!=null)
												{
													if( val.equalsIgnoreCase("O"))
													{
														value.set(i, "Oneway");
													}
													else if( val.equalsIgnoreCase("R"))
													{
														value.set(i, "Roundtrip");
													}
												}
											}
										}
									}
								}
							}
						}
					}
					catch(Exception e)
					{
						throw new Exception("Unparsable quotation schema :: ERROR");
					}
				}
			}
		}
		rowDisplayables.put(0,headerDisplayables);
		variables.put("user", user);
		variables.put("company", company);
		variables.put("travelrequest", flightTravelRequest);
		variables.put("displayables", rowDisplayables.values());
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		response.setContentType("application/pdf");
		response.setHeader("content-disposition", "htmlContent; filename="+ "Filename.pdf");
		final IWebContext ctx = new SpringWebContext(request, response,
				servletContext, locale, variables, applicationContext);
		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Flight Travel Request (ID # "+flightTravelRequest.getTravelRequestNumber()+" ) Quotations ");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setFrom(cc.getEmail_admin());
		String toEmails []=null;
		String CCEmails []=null;
		if(flightQuotationHistoryEmail!=null){
			if(flightQuotationHistoryEmail!=null){
				if((!flightQuotationHistoryEmail.getCcMails().equals("") )&& (!flightQuotationHistoryEmail.getToMails().equals("") )){
					toEmails=flightQuotationHistoryEmail.getToMails().split(";");
					CCEmails=flightQuotationHistoryEmail.getCcMails().split(";");
					message.setTo(toEmails);
					message.setCc(CCEmails);
				}
				else if(!flightQuotationHistoryEmail.getToMails().equals("")){
					toEmails=flightQuotationHistoryEmail.getToMails().split(";");
					message.setTo(toEmails);
				}
				else if(!flightQuotationHistoryEmail.equals("")){
					CCEmails=flightQuotationHistoryEmail.getCcMails().split(";");
					message.setCc(CCEmails);
				}

			}
		}

		final String htmlContent = this.templateEngine.process("Flight-Quotation-Email.html", ctx);
		message.setText(htmlContent, true /* isHtml */);

		/*message.addAttachment("Flight Travel Request (ID # "+flightTravelRequest.getTravelRequestNumber()+" ) Quotations ",
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"FlightQuotationPDF", "Flight-quotation-"+dateFormat.format(date),
						String.valueOf(new Random().nextInt())))); */

		this.mailSender.send(mimeMessage);
		logger.info("Sent Flight Quotation Response to...." + user.getEmail()+ " from " + cc.getEmail_admin());
		return true;
	}

	public boolean sendFlightQuotationEmail(final User user,
			final Company company,
			final FlightTravelRequest flightTravelRequest,
			final List<FlightTravelRequestQuotation> quotations,
			final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException , DocumentException, IOException {

		Map<String, Object> variables = new HashMap<String, Object>();
		Map<Integer, ArrayList<String>> rowDisplayables = new HashMap<Integer, ArrayList<String>>();
		ArrayList<String> headerDisplayables = new ArrayList<String>();
		headerDisplayables.add("");
		if(quotations!=null && quotations.size()>0)
		{
			for(int j=0; j<quotations.size(); j++)
			{
				FlightTravelRequestQuotation flightTravelRequestQuotation = quotations.get(j);
				if(flightTravelRequestQuotation != null)
				{
					if(j==0)
						headerDisplayables.add("Preferred Option");
					else
						headerDisplayables.add("Option "+(j+1));
					try{
						String date =flightTravelRequestQuotation.getAdditionalData();
						StringTokenizer tok=new StringTokenizer(date, "[<#\\#>]");
						if(tok!=null)
						{
							List<String> addList=  new LinkedList<>();
							while(tok.hasMoreTokens()){
								String token=tok.nextToken();
								addList.add(token.trim());
							}
							List<FlightQuotationSchema> flightQuotationSchemalist = new ArrayList<FlightQuotationSchema>();
							if(addList!=null && addList.size()>0)
							{
								Iterator<String> itr=addList.iterator();
								String[] partsTest=null;

								while (itr.hasNext()) {
									String s=itr.next();
									if(s!=null)
									{
										partsTest=s.split("\\(\\|\\)");
										if(partsTest!=null && partsTest.length>4)
										{
											logger.info("partsTest... " + partsTest[2]);
											flightQuotationSchemalist.add(new FlightQuotationSchema(partsTest[0], partsTest[1], partsTest[2], Integer.parseInt(partsTest[3]), partsTest[4], partsTest[5]));
										}
									}
								}
							}
							if(flightQuotationSchemalist!=null && flightQuotationSchemalist.size()>0)
							{
								Collections.sort(flightQuotationSchemalist, new Comparator<FlightQuotationSchema>() {
									@Override
									public int compare(FlightQuotationSchema lhs, FlightQuotationSchema rhs) {
										// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
										if(lhs == null || rhs == null)
											return 0;
										return lhs.getPositionNumber() > rhs.getPositionNumber() ? -1 : (lhs.getPositionNumber() < rhs.getPositionNumber() ) ? 1 : 0;
									}
								});

								int tripIndex=0;
								int schemaIndex = 0;

								for (FlightQuotationSchema flightQuotationSchema : flightQuotationSchemalist) {
									Integer position = (flightQuotationSchema.getPositionNumber());
									ArrayList<String> columnDisplayables = (rowDisplayables.get(position)!=null)?rowDisplayables.get(position): new ArrayList<String> ();
									if(j==0 && columnDisplayables.size() == 0)
									{

										if(flightQuotationSchema.getDisplayName()!=null && flightQuotationSchema.getDisplayName().equalsIgnoreCase("Trip Type"))
										{
											tripIndex= position;
										}
										columnDisplayables.add(flightQuotationSchema.getDisplayName());

									}
									columnDisplayables.add(flightQuotationSchema.getData());
									rowDisplayables.put(position, columnDisplayables);
									schemaIndex++;
								}

								if(rowDisplayables!=null && rowDisplayables.size()>0)
								{
									for(Entry<Integer, ArrayList<String>> entry : rowDisplayables.entrySet()) {
										Integer key = entry.getKey();
										ArrayList<String> value = entry.getValue();
										if(value!=null && value.size()>0)
										{
											for(int i=0;i<value.size();i++){
												String val = value.get(i);
												if(key==tripIndex && i>0 && val!=null)
												{
													if( val.equalsIgnoreCase("O"))
													{
														value.set(i, "Oneway");
													}
													else if( val.equalsIgnoreCase("R"))
													{
														value.set(i, "Roundtrip");
													}
												}
											}
										}
									}
								}
							}
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
						throw new Exception("Unparsable quotation schema :: ERROR");
					}
				}
			}
		}
		rowDisplayables.put(0,headerDisplayables);
		variables.put("user", user);
		variables.put("company", company);
		variables.put("travelrequest", flightTravelRequest);
		variables.put("displayables", rowDisplayables.values());
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		response.setContentType("application/pdf");
		response.setHeader("content-disposition", "htmlContent; filename="+ "Filename.pdf");
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);
		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Flight Travel Request (ID # "+flightTravelRequest.getTravelRequestNumber()+" ) Quotations ");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setFrom(cc.getEmail_admin());
		message.setTo(user.getEmail());
		final String htmlContent = this.templateEngine.process("Flight-Quotation-Email.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		/*message.addAttachment(
				"Flight Travel Request (ID # "+flightTravelRequest.getTravelRequestNumber()+" ) Quotations ",
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"FlightInvoicePDF", "Flight-quotation-"+dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));	*/

		this.mailSender.send(mimeMessage);
		logger.info("Sent Flight Quotation Response to...." + user.getEmail()+ " from " + cc.getEmail_admin());
		return true;
	}


	public boolean sendBugHistoryCreatedMailToTechHeads(final Email email, final List<String> techHeadUsernew,final User techSupportUser,final User assignedEmp,final User raisedByUser,BugTrackerHistory bugTrackerHistory,
			final String imageContentType, final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException {

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("bugTrackerHistory", bugTrackerHistory);
		variables.put("techSupportUser", techSupportUser);
		variables.put("assignedEmp", assignedEmp);
		variables.put("raisedByUser", raisedByUser);
		variables.put("baseUrl", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Bug Request"+" "+bugTrackerHistory.getStatus());
		String htmlContent = null;
		if(email.getType() == Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_CREATED){
			if(techSupportUser == null  ){
				String[] recipientToEmaill={raisedByUser.getEmail()};
				message.setTo(recipientToEmaill);
				for (String techheadmail : techHeadUsernew) {
					message.setBcc(techheadmail);
				}
				message.setFrom(raisedByUser.getEmail());
				htmlContent = this.templateEngine.process("BugTrackerRequestCreated.html", ctx);
			}
			else{

				String[] recipientToEmaill={assignedEmp.getEmail(),techSupportUser.getEmail()};
				message.setTo(recipientToEmaill);
				for (String techheadmail : techHeadUsernew) {
					message.setBcc(techheadmail);
				}
				htmlContent = this.templateEngine.process("BugTrackerRequestCreated.html", ctx);

			}


		} 
		if(email.getType()==Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_ASSIGNED || email.getType()==Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_PENDING || email.getType()==Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_WORK_IN_PROGRESS || email.getType()==Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_STILL_IN_PROGRESS)
		{
			message.setTo(techSupportUser.getEmail());
			for (String techheadmail : techHeadUsernew) {
				message.setBcc(techheadmail);
			}
			htmlContent = this.templateEngine.process("BugTrackerRequestAssigned.html", ctx);
		}
		if(email.getType()==Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_TEST_REVIEW){
			String[] recipientToEmaill={assignedEmp.getEmail(),techSupportUser.getEmail()};
			message.setTo(recipientToEmaill);
			for (String techheadmail : techHeadUsernew) {
				message.setBcc(techheadmail);
			}
			htmlContent = this.templateEngine.process("BugTrackerRequestAssigned.html", ctx);
		}
		if(email.getType()==Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_REVIEW){

			message.setTo(techSupportUser.getEmail());
			for (String techheadmail : techHeadUsernew) {
				message.setBcc(techheadmail);
			}
			htmlContent = this.templateEngine.process("BugTrackerRequestAssigned.html", ctx);
		}
		if(email.getType()==Email.EMAIL_TYPE_BUG_TRACKER_HISTORY_CLOSED){
			String[] recipientToEmaill={raisedByUser.getEmail(),techSupportUser.getEmail()};
			message.setTo(recipientToEmaill);
			for (String techheadmail : techHeadUsernew) {
				message.setBcc(techheadmail);
			}
			htmlContent = this.templateEngine.process("BugTrackerRequestAssigned.html", ctx);
		}


		message.setText(htmlContent, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		return true;
	}




	public boolean sendNotificationMailToUser(Company company,User user,
			final String imageContentType,
			final Locale locale, final HttpServletRequest request,final Notification notification, final List<NotificationDetail> notificationDetails,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException{

		String receptemail = user.getEmail();
		user.initLogoDisplayable();

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("notificationDetailsList",notificationDetails);
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("user", user);
		variables.put("company", company);
		variables.put("companylogo", CommonConfig.GetCommonConfig().getImage_path()+company.getCompanyid());
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctxVoucher = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setSubject("Notification");
		message.setFrom(cc.getEmail_admin());
		if(notification.getCcEmailAddress()!=null && !notification.getCcEmailAddress().equalsIgnoreCase("")){
			if(notification.getCcEmailAddress().contains(";")){
				StringTokenizer tok = new StringTokenizer(notification.getCcEmailAddress(), ";");
				while(tok.hasMoreTokens()){
					String token=tok.nextToken();
					message.setCc(token.trim());
				}
			}else{
				message.setCc(notification.getCcEmailAddress());
			}
		}
		if(notification.getToEmailAddress()!=null && !notification.getToEmailAddress().equalsIgnoreCase("")){
			if(notification.getToEmailAddress().contains(";")){
				StringTokenizer tok = new StringTokenizer(notification.getToEmailAddress(), ";");
				while(tok.hasMoreTokens()){
					String token=tok.nextToken();
					message.setTo(token.trim());
				}
			}else{
				message.setTo(notification.getToEmailAddress());
			}
		}else{
			message.setTo(receptemail);
		}


		final String htmlContentVoucher = this.templateEngine.process("common-notification.html", ctxVoucher);
		message.setText(htmlContentVoucher, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		return true;
	}


	public boolean sendAllHotelCustomerPayment(boolean isSupplier, boolean isCustomer,  Company company,User user,
			final String imageContentType, final PaymentTransaction paymentTransaction,List<PaymentTransactionDetail> paymentTransactionDetailList,
			final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException{

		String receptemail=user.getEmail();
		user.initLogoDisplayable();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("paymentTransactionDetailList", paymentTransactionDetailList);
		variables.put("paymentTransaction", paymentTransaction);
		variables.put("user", user);
		variables.put("company", company);
		variables.put("companylogo", CommonConfig.GetCommonConfig().getImage_path()+company.getCompanyid());
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctxVoucher = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		if(isCustomer){
			message.setSubject("Due Payment Alert!!!");
			message.setFrom(cc.getEmail_admin());
			message.setTo(receptemail);
		}
		final String htmlContentVoucher = this.templateEngine.process("partial-payment-notification.html", ctxVoucher);
		message.setText(htmlContentVoucher, true /* isHtml */);
		this.mailSender.send(mimeMessage);
		return true;
	}

	public boolean sendFlightOrderMailInvoice(boolean isFailed, boolean isCustomer, FlightOrderRow flightorderrow, User user,
			Company company, String imagePngValue, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext,
			String imagePth, String htmlContent, Email email) throws MessagingException {

		String recipientName = flightorderrow.getCustomer().getFirstName() + " " + flightorderrow.getCustomer().getLastName();
		//String recipientEmail = flightorder.getCustomer().getEmail();
		String address = flightorderrow.getCustomer().getAddress();
		user.initLogoDisplayable();
		String receptemail = user.getEmail();
		if(isCustomer)
			receptemail=flightorderrow.getCustomer().getEmail()!=null && !flightorderrow.getCustomer().getEmail().equals("")?flightorderrow.getCustomer().getEmail():user.getEmail();

			// Prepare the evaluation context
			if(flightorderrow.getStatusAction().equalsIgnoreCase("Hold") || flightorderrow.getStatusAction().equalsIgnoreCase("Released"))
				flightorderrow.setInvoiceNo(flightorderrow.getOrderId());

			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
					true /* multipart */, "UTF-8");


			CommonConfig cc = CommonConfig.GetCommonConfig();
			message.setFrom(cc.getEmail_admin());
			message.setBcc(cc.getEmail_booking());
			if(!isFailed)
			{
				message.setSubject("Agency:Flight Invoice With PNR: "+flightorderrow.getPnr()+" And Invoice No: "+flightorderrow.getInvoiceNo()+" With Itinerary Is "+flightorderrow.getOrigin()+"-"+flightorderrow.getDestination());
				if(isCustomer)
					message.setSubject("Customer:Flight Invoice With PNR: "+flightorderrow.getPnr()+" And Invoice No: "+flightorderrow.getInvoiceNo()+" With Itinerary Is "+flightorderrow.getOrigin()+"-"+flightorderrow.getDestination());
			}
			else
			{
				message.setSubject("Flight Booking Failed");
			}
			String Title = "Flight Invoice With PNR: "+flightorderrow.getPnr()+" And Invoice No: "+flightorderrow.getInvoiceNo()+" With Itinerary Is "+flightorderrow.getOrigin()+"-"+flightorderrow.getDestination();
			message.setSubject("Flight Invoice With PNR: "+flightorderrow.getPnr()+" And Invoice No: "+flightorderrow.getInvoiceNo()+" With Itinerary Is "+flightorderrow.getOrigin()+"-"+flightorderrow.getDestination());
			String toEmails []=null;
			String CCEmails []=null;
			if(email!=null){
				if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
					toEmails=email.getToEmailAddress().split(";");
					CCEmails=email.getCcEmailAddress().split(";");
					message.setTo(toEmails);
					message.setCc(CCEmails);
				}
				else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals("")) && (email.getCcEmailAddress()==null || email.getCcEmailAddress().equals(""))){
					toEmails=email.getToEmailAddress().split(";");
					message.setTo(toEmails);
				}
				else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals("")) && (email.getToEmailAddress()==null || email.getToEmailAddress().equals(""))){
					CCEmails=email.getCcEmailAddress().split(";");
					message.setCc(CCEmails);
				}else{
					message.setTo(receptemail);
					if(!company.getEmail().equalsIgnoreCase(receptemail))
						message.setCc(company.getEmail());
				}

			}

			message.setText(htmlContent, true);
			message.addAttachment(Title,new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
					"FlightInvoicePDF", dateFormat.format(date),
					String.valueOf(new Random().nextInt()))));
			// Send mail
			this.mailSender.send(mimeMessage);
			logger.info("Sent Flight invoice mail to...." + receptemail	+ " for invoice no " + flightorderrow.getInvoiceNo());
			return true;


	}

	public boolean sendCarOrderMailInvoice(boolean isSupplier, boolean isCustomer, CarOrderRow carOrderRow, User user, Company company,
			String imagePngValue, Locale locale, HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, ApplicationContext applicationContext, String imagePth, String htmlContent,
			Email email) throws MessagingException {


		String recipientName = carOrderRow.getOrderCustomer().getFirstName() + " " + carOrderRow.getOrderCustomer().getLastName();
		//String recipientEmail = flightorder.getCustomer().getEmail();
		String address = carOrderRow.getOrderCustomer().getAddress();
		user.initLogoDisplayable();
		String receptemail = user.getEmail();

		if(isCustomer)
			receptemail = carOrderRow.getOrderCustomer().getEmail();

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setSubject("Agency: Car Invoice With OrderId- "+carOrderRow.getOrderId()+ " And Invoice No: "+carOrderRow.getInvoiceNo());
		if(isCustomer)
			message.setSubject("Customer: Car Invoice With OrderId-  "+carOrderRow.getOrderId()+ " And Invoice No: "+carOrderRow.getInvoiceNo());

		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_booking());
		String Title = "Customer: Car Invoice With OrderId- "+carOrderRow.getOrderId()+ " And Invoice No: "+carOrderRow.getInvoiceNo();
		message.setSubject("Customer: Car Invoice With OrderId-  "+carOrderRow.getOrderId()+ " And Invoice No: "+carOrderRow.getInvoiceNo());


		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals("")) && (email.getCcEmailAddress()==null || email.getCcEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals("")) && (email.getToEmailAddress()==null || email.getToEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}else{
				message.setTo(receptemail);
				if(!company.getEmail().equalsIgnoreCase(receptemail))
					message.setCc(company.getEmail());
			}
		}

		message.setText(htmlContent, true);
		message.addAttachment(Title,new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
				"CarInvoicePDF", dateFormat.format(date),
				String.valueOf(new Random().nextInt()))));
		// Send mail
		this.mailSender.send(mimeMessage);
		logger.info("Sent Car invoice mail to...." + receptemail	+ " for invoice no " + carOrderRow.getInvoiceNo());
		return true;




	}

	public boolean sendTrainOrderMailInvoice(boolean isSupplier, boolean isCustomer, TrainOrderRow trainOrderRow, User user, Company company,
			String imagePngValue, Locale locale, HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, ApplicationContext applicationContext, String imagePth, String htmlContent,
			Email email)throws MessagingException {


		String recipientName = trainOrderRow.getOrderCustomer().getFirstName() + " " + trainOrderRow.getOrderCustomer().getLastName();
		//String recipientEmail = flightorder.getCustomer().getEmail();
		String address = trainOrderRow.getOrderCustomer().getAddress();
		user.initLogoDisplayable();
		String receptemail=user.getEmail();
		if(isCustomer)
			receptemail=trainOrderRow.getOrderCustomer().getEmail();

		// Prepare the evaluation context
		if(trainOrderRow.getStatusAction().equalsIgnoreCase("Hold") || trainOrderRow.getStatusAction().equalsIgnoreCase("Released"))
			trainOrderRow.setInvoiceNo(trainOrderRow.getOrderId());

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
				true /* multipart */, "UTF-8");

		//care@tayyarah.com
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setSubject("Agency: Train Invoice With OrderId-  "+trainOrderRow.getOrderId()+ " And Invoice No: "+trainOrderRow.getInvoiceNo());
		if(isCustomer)
			message.setSubject("Customer: Train Invoice With OrderId-  "+trainOrderRow.getOrderId()+ " And Invoice No: "+trainOrderRow.getInvoiceNo());
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_booking());
		String Title = "Train InvoiceWith OrderId-  "+trainOrderRow.getOrderId()+ " And Invoice No: "+trainOrderRow.getInvoiceNo();
		message.setSubject("Train Invoice With OrderId-  "+trainOrderRow.getOrderId()+ " And Invoice No: "+trainOrderRow.getInvoiceNo());

		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals("")) && (email.getCcEmailAddress()==null || email.getCcEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals("")) && (email.getToEmailAddress()==null || email.getToEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}else{
				message.setTo(receptemail);
				if(!company.getEmail().equalsIgnoreCase(receptemail))
					message.setCc(company.getEmail());
			}
		}


		message.setText(htmlContent, true);
		message.addAttachment(Title,new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
				"TrainInvoicePDF", dateFormat.format(date),
				String.valueOf(new Random().nextInt()))));
		// Send mail
		this.mailSender.send(mimeMessage);
		logger.info("Sent Train invoice mail to...." + receptemail	+ " for invoice no " + trainOrderRow.getInvoiceNo());
		return true;




	}

	public boolean sendBusOrderMailInvoice(boolean isSupplier, boolean isCustomer, BusOrderRow busOrderRow, User user, Company company,
			String imagePngValue, Locale locale, HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, ApplicationContext applicationContext, String imagePth, String htmlContent,
			Email email)throws MessagingException {

		String recipientName = busOrderRow.getOrderCustomer().getFirstName() + " " + busOrderRow.getOrderCustomer().getLastName();
		//String recipientEmail = flightorder.getCustomer().getEmail();
		String address = busOrderRow.getOrderCustomer().getAddress();
		user.initLogoDisplayable();
		String receptemail=user.getEmail();
		if(isCustomer)
			receptemail=busOrderRow.getOrderCustomer().getEmail();

		// Prepare the evaluation context
		if(busOrderRow.getStatusAction().equalsIgnoreCase("Hold") || busOrderRow.getStatusAction().equalsIgnoreCase("Released"))
			busOrderRow.setInvoiceNo(busOrderRow.getOrderId());

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
				true /* multipart */, "UTF-8");


		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setSubject("Agency: Bus Invoice With OrderId-  "+busOrderRow.getOrderId()+ " And Invoice No: "+busOrderRow.getInvoiceNo());
		if(isCustomer)
			message.setSubject("Customer: Bus Invoice With OrderId-  "+busOrderRow.getOrderId()+ " And Invoice No: "+busOrderRow.getInvoiceNo());
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		String Title = "BusInvoice With OrderId-  "+busOrderRow.getOrderId()+ " And Invoice No: "+busOrderRow.getInvoiceNo();
		message.setSubject("Bus Invoice With OrderId-  "+busOrderRow.getOrderId()+ " And Invoice No: "+busOrderRow.getInvoiceNo());

		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals("")) && (email.getCcEmailAddress()==null || email.getCcEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals("")) && (email.getToEmailAddress()==null || email.getToEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}else{
				message.setTo(receptemail);
				if(!company.getEmail().equalsIgnoreCase(receptemail))
					message.setCc(company.getEmail());
			}
		}

		message.setText(htmlContent, true);
		message.addAttachment(Title,new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
				"BusInvoicePDF", dateFormat.format(date),
				String.valueOf(new Random().nextInt()))));
		// Send mail
		this.mailSender.send(mimeMessage);
		logger.info("Sent Bus invoice mail to...." + receptemail	+ " for invoice no " + busOrderRow.getInvoiceNo());
		return true;



		// TODO Auto-generated method stub
	}

	public boolean sendInsuranceOrderMailInvoice(boolean isSupplier, boolean isCustomer, InsuranceOrderRow insuranceOrderRow, User user,
			Company company, String imagePngValue, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext,
			String imagePth, String htmlContent, Email email) throws MessagingException {


		String recipientName = insuranceOrderRow.getOrderCustomer().getFirstName() + " " + insuranceOrderRow.getOrderCustomer().getLastName();
		//String recipientEmail = flightorder.getCustomer().getEmail();
		String address = insuranceOrderRow.getOrderCustomer().getAddress();
		user.initLogoDisplayable();
		String receptemail=user.getEmail();
		if(isCustomer)
			receptemail=insuranceOrderRow.getOrderCustomer().getEmail();

		// Prepare the evaluation context
		if(insuranceOrderRow.getStatusAction().equalsIgnoreCase("Hold") || insuranceOrderRow.getStatusAction().equalsIgnoreCase("Released"))
			insuranceOrderRow.setInvoiceNo(insuranceOrderRow.getOrderId());

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
				true /* multipart */, "UTF-8");

		//insurancee@tayyarah.com
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setSubject("Agency: Insurance Invoice With OrderId-  "+insuranceOrderRow.getOrderId()+ " And Invoice No: "+insuranceOrderRow.getInvoiceNo());
		if(isCustomer)
			message.setSubject("Customer: Insurance Invoice With OrderId-  "+insuranceOrderRow.getOrderId()+ " And Invoice No: "+insuranceOrderRow.getInvoiceNo());
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		String Title = "InsuranceInvoice With OrderId-  "+insuranceOrderRow.getOrderId()+ " And Invoice No: "+insuranceOrderRow.getInvoiceNo();
		message.setSubject("Insurance Invoice With OrderId-  "+insuranceOrderRow.getOrderId()+ " And Invoice No: "+insuranceOrderRow.getInvoiceNo());

		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals("")) && (email.getCcEmailAddress()==null || email.getCcEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals("")) && (email.getToEmailAddress()==null || email.getToEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}else{
				message.setTo(receptemail);
				if(!company.getEmail().equalsIgnoreCase(receptemail))
					message.setCc(company.getEmail());
			}

		}

		message.setText(htmlContent, true);
		message.addAttachment(Title,new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
				"InsuranceInvoicePDF", dateFormat.format(date),
				String.valueOf(new Random().nextInt()))));
		// Send mail
		this.mailSender.send(mimeMessage);
		logger.info("Sent Insurance invoice mail to...." + receptemail	+ " for invoice no " + insuranceOrderRow.getInvoiceNo());
		return true;
	}

	public boolean sendVisaOrderMailInvoice(boolean isSupplier, boolean isCustomer, VisaOrderRow visaOrderRow, User user, Company company,
			String imagePngValue, Locale locale, HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, ApplicationContext applicationContext, String imagePth, String htmlContent,
			Email email) throws MessagingException {


		String recipientName = visaOrderRow.getOrderCustomer().getFirstName() + " " + visaOrderRow.getOrderCustomer().getLastName();
		//String recipientEmail = flightorder.getCustomer().getEmail();
		String address = visaOrderRow.getOrderCustomer().getAddress();
		user.initLogoDisplayable();
		String receptemail=user.getEmail();
		if(isCustomer)
			receptemail=visaOrderRow.getOrderCustomer().getEmail();

		// Prepare the evaluation context
		if(visaOrderRow.getStatusAction().equalsIgnoreCase("Hold") || visaOrderRow.getStatusAction().equalsIgnoreCase("Released"))
			visaOrderRow.setInvoiceNo(visaOrderRow.getOrderId());

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
				true /* multipart */, "UTF-8");

		//visae@tayyarah.com
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setSubject("Agency: Visa Invoice With OrderId-  "+visaOrderRow.getOrderId()+ " And Invoice No: "+visaOrderRow.getInvoiceNo());
		if(isCustomer)
			message.setSubject("Customer: Visa Invoice With OrderId-  "+visaOrderRow.getOrderId()+ " And Invoice No: "+visaOrderRow.getInvoiceNo());
		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());
		String Title = "VisaInvoice With OrderId-  "+visaOrderRow.getOrderId()+ " And Invoice No: "+visaOrderRow.getInvoiceNo();
		message.setSubject("Visa Invoice With OrderId-  "+visaOrderRow.getOrderId()+ " And Invoice No: "+visaOrderRow.getInvoiceNo());

		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals("")) && (email.getCcEmailAddress()==null || email.getCcEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals("")) && (email.getToEmailAddress()==null || email.getToEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}else{
				message.setTo(receptemail);
				if(!company.getEmail().equalsIgnoreCase(receptemail))
					message.setCc(company.getEmail());
			}
		}

		message.setText(htmlContent, true);
		message.addAttachment(Title,new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
				"VisaInvoicePDF", dateFormat.format(date),
				String.valueOf(new Random().nextInt()))));
		// Send mail
		this.mailSender.send(mimeMessage);
		logger.info("Sent Visa invoice mail to...." + receptemail	+ " for invoice no " + visaOrderRow.getInvoiceNo());
		return true;




	}

	public boolean sendCreditNoteRequestCar(CarOrderRow carOrderRow,
			CarCreditNote creditNote, User user, Company company,
			Company parentCompany, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext,
			ApplicationContext applicationContext) throws MessagingException {


		String recipientEmail = user.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("creditnote", creditNote);
		variables.put("carOrderRow", carOrderRow);
		variables.put("user", user);
		variables.put("company", company);
		variables.put("parentcompany", parentCompany);
		HashMap<String, String> datetimemap = CommonUtil.getDateTimeMapFromTimestamp(creditNote.getOrderedAt());
		variables.put("requestdate", datetimemap.get("date"));
		variables.put("requesttime", datetimemap.get("time"));
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Credit Note Request on Car Booking With OrderId-  "+carOrderRow.getOrderId()+ " And Invoice No: "+carOrderRow.getInvoiceNo());
		String title="Car FlightCreditNote Request With OrderId-  "+carOrderRow.getOrderId()+ " And Invoice No: "+carOrderRow.getInvoiceNo();
		CommonConfig cc=CommonConfig.GetCommonConfig();
		if (parentCompany != null)
			message.setTo(parentCompany.getEmail());

		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(company.getEmail());
		message.setBcc(cc.getEmail_booking());
		final String htmlContent = this.templateEngine.process("Car-Request-Creditnote.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		message.addAttachment(
				title,
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"CarCreditNotePDF", "Credit Request-Car-"+dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));

		this.mailSender.send(mimeMessage);
		logger.info("############Credit Note Request sent to...." + recipientEmail+ " for invoice no " + carOrderRow.getInvoiceNo());
		return true;


	}

	public boolean sendCreditNoteIssueCar(boolean isSupplier, boolean isCustomer,CarOrderRow carOrderRow,
			CarCreditNote creditNote, User user, Company company,
			Company parentCompany, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext,
			ApplicationContext applicationContext, String htmlContent,
			Email email) throws MessagingException {



		String recipientName = carOrderRow.getOrderCustomer().getFirstName() + " " + carOrderRow.getOrderCustomer().getLastName();
		//String recipientEmail = flightorder.getCustomer().getEmail();
		String address = carOrderRow.getOrderCustomer().getAddress();
		user.initLogoDisplayable();
		String receptemail = user.getEmail();

		if(isCustomer)
			receptemail = carOrderRow.getOrderCustomer().getEmail();

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setSubject("Agency: Car Credit Note With OrderId-  "+carOrderRow.getOrderId()+ " And Invoice No: "+carOrderRow.getInvoiceNo());
		if(isCustomer)
			message.setSubject("Customer: Car Credit Note With OrderId-  "+carOrderRow.getOrderId()+ " And Invoice No: "+carOrderRow.getInvoiceNo());

		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_booking());
		String Title = "Credit Note Issued on Car Booking With OrderId-  "+carOrderRow.getOrderId()+ " And Invoice No: "+carOrderRow.getInvoiceNo();
		message.setSubject("Credit Note Issued on Car Booking With OrderId-  "+carOrderRow.getOrderId()+ " And Invoice No: "+carOrderRow.getInvoiceNo());


		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals("")) && (email.getCcEmailAddress()==null || email.getCcEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals("")) && (email.getToEmailAddress()==null || email.getToEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}else{
				message.setTo(receptemail);
				if(!company.getEmail().equalsIgnoreCase(receptemail))
					message.setCc(company.getEmail());
			}
		}

		message.setText(htmlContent, true);
		message.addAttachment(Title,new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
				"CarInvoicePDF", dateFormat.format(date),
				String.valueOf(new Random().nextInt()))));
		// Send mail
		this.mailSender.send(mimeMessage);
		logger.info("Sent Car Credit Note mail to...." + receptemail	+ " for invoice no " + carOrderRow.getInvoiceNo());
		return true;
	}


	public boolean sendCreditNoteRequestTrain(TrainOrderRow trainOrderRow,
			TrainCreditNote creditNote, User user, Company company,
			Company parentCompany, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext,
			ApplicationContext applicationContext) throws MessagingException {


		String recipientEmail = user.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("creditnote", creditNote);
		variables.put("trainOrderRow", trainOrderRow);
		variables.put("user", user);
		variables.put("company", company);
		variables.put("parentcompany", parentCompany);
		HashMap<String, String> datetimemap = CommonUtil.getDateTimeMapFromTimestamp(creditNote.getOrderedAt());
		variables.put("requestdate", datetimemap.get("date"));
		variables.put("requesttime", datetimemap.get("time"));
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Train FlightCreditNote Request With OrderId-  "+trainOrderRow.getOrderId()+ " And Invoice No: "+trainOrderRow.getInvoiceNo());
		String title="Train FlightCreditNote Request With OrderId-  "+trainOrderRow.getOrderId()+ " And Invoice No: "+trainOrderRow.getInvoiceNo();
		CommonConfig cc=CommonConfig.GetCommonConfig();
		if (parentCompany != null)
			message.setTo(parentCompany.getEmail());

		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(company.getEmail());
		message.setBcc(cc.getEmail_booking());
		final String htmlContent = this.templateEngine.process("Train-Request-Creditnote.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		message.addAttachment(
				title,
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"TrainCreditNotePDF", "Credit Request-Train-"+dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));

		this.mailSender.send(mimeMessage);
		logger.info("############Credit Note Request sent to...." + recipientEmail+ " for invoice no " + trainOrderRow.getInvoiceNo());
		return true;


	}

	public boolean sendCreditNoteIssueTrain(boolean isSupplier, boolean isCustomer,TrainOrderRow trainOrderRow,
			TrainCreditNote creditNote, User user, Company company,
			Company parentCompany, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext,
			ApplicationContext applicationContext, String htmlContent,
			Email email) throws MessagingException {



		String recipientName = trainOrderRow.getOrderCustomer().getFirstName() + " " + trainOrderRow.getOrderCustomer().getLastName();
		//String recipientEmail = flightorder.getCustomer().getEmail();
		String address = trainOrderRow.getOrderCustomer().getAddress();
		user.initLogoDisplayable();
		String receptemail = user.getEmail();

		if(isCustomer)
			receptemail = trainOrderRow.getOrderCustomer().getEmail();

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setSubject("Agency: Train Credit Note With OrderId-  "+trainOrderRow.getOrderId()+ " And Invoice No: "+trainOrderRow.getInvoiceNo());
		if(isCustomer)
			message.setSubject("Customer: Train Credit Note With OrderId-  "+trainOrderRow.getOrderId()+ " And Invoice No: "+trainOrderRow.getInvoiceNo());

		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_booking());
		String Title = "Credit Note Issued on Train Booking With OrderId-  "+trainOrderRow.getOrderId()+ " And Invoice No: "+trainOrderRow.getInvoiceNo();
		message.setSubject("Credit Note Issued on Train Booking With OrderId-  "+trainOrderRow.getOrderId()+ " And Invoice No: "+trainOrderRow.getInvoiceNo());


		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals("")) && (email.getCcEmailAddress()==null || email.getCcEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals("")) && (email.getToEmailAddress()==null || email.getToEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}else{
				message.setTo(receptemail);
				if(!company.getEmail().equalsIgnoreCase(receptemail))
					message.setCc(company.getEmail());
			}
		}

		message.setText(htmlContent, true);
		message.addAttachment(Title,new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
				"TrainInvoicePDF", dateFormat.format(date),
				String.valueOf(new Random().nextInt()))));
		// Send mail
		this.mailSender.send(mimeMessage);
		logger.info("Sent Train Credit Note mail to...." + receptemail	+ " for invoice no " + trainOrderRow.getInvoiceNo());
		return true;
	}

	public boolean sendCreditNoteRequestVisa(VisaOrderRow visaOrderRow,
			VisaCreditNote creditNote, User user, Company company,
			Company parentCompany, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext,
			ApplicationContext applicationContext) throws MessagingException {


		String recipientEmail = user.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("creditnote", creditNote);
		variables.put("visaOrderRow", visaOrderRow);
		variables.put("user", user);
		variables.put("company", company);
		variables.put("parentcompany", parentCompany);
		HashMap<String, String> datetimemap = CommonUtil.getDateTimeMapFromTimestamp(creditNote.getOrderedAt());
		variables.put("requestdate", datetimemap.get("date"));
		variables.put("requesttime", datetimemap.get("time"));
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Credit Note Request on Visa Booking With OrderId-  "+visaOrderRow.getOrderId()+ " And Invoice No: "+visaOrderRow.getInvoiceNo());
		String title="Visa FlightCreditNote Request on Visa Booking With OrderId-  "+visaOrderRow.getOrderId()+ " And Invoice No: "+visaOrderRow.getInvoiceNo();
		CommonConfig cc=CommonConfig.GetCommonConfig();
		if (parentCompany != null)
			message.setTo(parentCompany.getEmail());

		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(company.getEmail());
		message.setBcc(cc.getEmail_booking());
		final String htmlContent = this.templateEngine.process("Visa-Request-Creditnote.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		message.addAttachment(
				title,
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"VisaCreditNotePDF", "Credit Request-Visa-"+dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));

		this.mailSender.send(mimeMessage);
		logger.info("############Credit Note Request sent to...." + recipientEmail+ " for invoice no " + visaOrderRow.getInvoiceNo());
		return true;


	}

	public boolean sendCreditNoteIssueVisa(boolean isSupplier, boolean isCustomer,VisaOrderRow visaOrderRow,
			VisaCreditNote creditNote, User user, Company company,
			Company parentCompany, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext,
			ApplicationContext applicationContext, String htmlContent,
			Email email) throws MessagingException {



		String recipientName = visaOrderRow.getOrderCustomer().getFirstName() + " " + visaOrderRow.getOrderCustomer().getLastName();
		//String recipientEmail = flightorder.getCustomer().getEmail();
		String address = visaOrderRow.getOrderCustomer().getAddress();
		user.initLogoDisplayable();
		String receptemail = user.getEmail();

		if(isCustomer)
			receptemail = visaOrderRow.getOrderCustomer().getEmail();

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setSubject("Agency: Visa Credit Note With OrderId-  "+visaOrderRow.getOrderId()+ " And Invoice No: "+visaOrderRow.getInvoiceNo());
		if(isCustomer)
			message.setSubject("Customer: Visa Credit Note With OrderId-  "+visaOrderRow.getOrderId()+ " And Invoice No: "+visaOrderRow.getInvoiceNo());

		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_booking());
		String Title = "Credit Note Issued on Visa Booking With OrderId-  "+visaOrderRow.getOrderId()+ " And Invoice No: "+visaOrderRow.getInvoiceNo();
		message.setSubject("Credit Note Issued on Visa Booking With OrderId-  "+visaOrderRow.getOrderId()+ " And Invoice No: "+visaOrderRow.getInvoiceNo());


		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals("")) && (email.getCcEmailAddress()==null || email.getCcEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals("")) && (email.getToEmailAddress()==null || email.getToEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}else{
				message.setTo(receptemail);
				if(!company.getEmail().equalsIgnoreCase(receptemail))
					message.setCc(company.getEmail());
			}
		}

		message.setText(htmlContent, true);
		message.addAttachment(Title,new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
				"VisaInvoicePDF", dateFormat.format(date),
				String.valueOf(new Random().nextInt()))));
		// Send mail
		this.mailSender.send(mimeMessage);
		logger.info("Sent Visa Credit Note mail to...." + receptemail	+ " for invoice no " + visaOrderRow.getInvoiceNo());
		return true;
	}
	public boolean sendCreditNoteRequestInsurance(InsuranceOrderRow insuranceOrderRow,
			InsuranceCreditNote creditNote, User user, Company company,
			Company parentCompany, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext,
			ApplicationContext applicationContext) throws MessagingException {


		String recipientEmail = user.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("creditnote", creditNote);
		variables.put("insuranceOrderRow", insuranceOrderRow);
		variables.put("user", user);
		variables.put("company", company);
		variables.put("parentcompany", parentCompany);
		HashMap<String, String> datetimemap = CommonUtil.getDateTimeMapFromTimestamp(creditNote.getOrderedAt());
		variables.put("requestdate", datetimemap.get("date"));
		variables.put("requesttime", datetimemap.get("time"));
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Credit Note Request on Insurance Booking With OrderId-  "+insuranceOrderRow.getOrderId()+ " And Invoice No: "+insuranceOrderRow.getInvoiceNo());
		String title="Credit Note Request on Insurance Booking With OrderId-  "+insuranceOrderRow.getOrderId()+ " And Invoice No: "+insuranceOrderRow.getInvoiceNo();
		CommonConfig cc=CommonConfig.GetCommonConfig();
		if (parentCompany != null)
			message.setTo(parentCompany.getEmail());

		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(company.getEmail());
		message.setBcc(cc.getEmail_booking());
		final String htmlContent = this.templateEngine.process("Insurance-Request-Creditnote.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		message.addAttachment(
				title,
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"InsuranceCreditNotePDF", "Credit Request-Insurance-"+dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));

		this.mailSender.send(mimeMessage);
		logger.info("############Credit Note Request sent to...." + recipientEmail+ " for invoice no " + insuranceOrderRow.getInvoiceNo());
		return true;


	}

	public boolean sendCreditNoteIssueInsurance(boolean isSupplier, boolean isCustomer,InsuranceOrderRow insuranceOrderRow,
			InsuranceCreditNote creditNote, User user, Company company,
			Company parentCompany, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext,
			ApplicationContext applicationContext, String htmlContent,
			Email email) throws MessagingException {



		String recipientName = insuranceOrderRow.getOrderCustomer().getFirstName() + " " + insuranceOrderRow.getOrderCustomer().getLastName();
		//String recipientEmail = flightorder.getCustomer().getEmail();
		String address = insuranceOrderRow.getOrderCustomer().getAddress();
		user.initLogoDisplayable();
		String receptemail = user.getEmail();

		if(isCustomer)
			receptemail = insuranceOrderRow.getOrderCustomer().getEmail();

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setSubject("Agency: Insurance Credit Note With OrderId-  "+insuranceOrderRow.getOrderId()+ " And Invoice No: "+insuranceOrderRow.getInvoiceNo());
		if(isCustomer)
			message.setSubject("Customer: Insurance Credit Note With OrderId-  "+insuranceOrderRow.getOrderId()+ " And Invoice No: "+insuranceOrderRow.getInvoiceNo());

		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_booking());
		String Title = "Credit Note Issued on Insurance Booking With OrderId-  "+insuranceOrderRow.getOrderId()+ " And Invoice No: "+insuranceOrderRow.getInvoiceNo();
		message.setSubject("Credit Note Issued on Insurance Booking With OrderId-  "+insuranceOrderRow.getOrderId()+ " And Invoice No: "+insuranceOrderRow.getInvoiceNo());


		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals("")) && (email.getCcEmailAddress()==null || email.getCcEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals("")) && (email.getToEmailAddress()==null || email.getToEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}else{
				message.setTo(receptemail);
				if(!company.getEmail().equalsIgnoreCase(receptemail))
					message.setCc(company.getEmail());
			}
		}

		message.setText(htmlContent, true);
		message.addAttachment(Title,new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
				"InsuranceInvoicePDF", dateFormat.format(date),
				String.valueOf(new Random().nextInt()))));
		// Send mail
		this.mailSender.send(mimeMessage);
		logger.info("Sent Insurance Credit Note mail to...." + receptemail	+ " for invoice no " + insuranceOrderRow.getInvoiceNo());
		return true;
	}
	public boolean sendCreditNoteRequestBus(BusOrderRow busOrderRow,
			BusCreditNote creditNote, User user, Company company,
			Company parentCompany, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext,
			ApplicationContext applicationContext) throws MessagingException {


		String recipientEmail = user.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("creditnote", creditNote);
		variables.put("busOrderRow", busOrderRow);
		variables.put("user", user);
		variables.put("company", company);
		variables.put("parentcompany", parentCompany);
		HashMap<String, String> datetimemap = CommonUtil.getDateTimeMapFromTimestamp(creditNote.getOrderedAt());
		variables.put("requestdate", datetimemap.get("date"));
		variables.put("requesttime", datetimemap.get("time"));
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Credit Note Request on Bus Booking With OrderId-  "+busOrderRow.getOrderId()+ " And Invoice No: "+busOrderRow.getInvoiceNo());
		String title="Credit Note Request on Bus Booking With OrderId-  "+busOrderRow.getOrderId()+ " And Invoice No: "+busOrderRow.getInvoiceNo();
		CommonConfig cc=CommonConfig.GetCommonConfig();
		if (parentCompany != null)
			message.setTo(parentCompany.getEmail());

		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(company.getEmail());
		message.setBcc(cc.getEmail_booking());
		final String htmlContent = this.templateEngine.process("Bus-Request-Creditnote.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		message.addAttachment(
				title,
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"BusCreditNotePDF", "Credit Request-Bus-"+dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));

		this.mailSender.send(mimeMessage);
		logger.info("############Credit Note Request sent to...." + recipientEmail+ " for invoice no " + busOrderRow.getInvoiceNo());
		return true;


	}

	public boolean sendCreditNoteIssueBus(boolean isSupplier, boolean isCustomer,BusOrderRow busOrderRow,
			BusCreditNote creditNote, User user, Company company,
			Company parentCompany, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext,
			ApplicationContext applicationContext, String htmlContent,
			Email email) throws MessagingException {



		String recipientName = busOrderRow.getOrderCustomer().getFirstName() + " " + busOrderRow.getOrderCustomer().getLastName();
		//String recipientEmail = flightorder.getCustomer().getEmail();
		String address = busOrderRow.getOrderCustomer().getAddress();
		user.initLogoDisplayable();
		String receptemail = user.getEmail();

		if(isCustomer)
			receptemail = busOrderRow.getOrderCustomer().getEmail();

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setSubject("Agency: Bus Credit Note With OrderId-  "+busOrderRow.getOrderId()+ " And Invoice No: "+busOrderRow.getInvoiceNo());
		if(isCustomer)
			message.setSubject("Customer: Bus Credit Note With OrderId-  "+busOrderRow.getOrderId()+ " And Invoice No: "+busOrderRow.getInvoiceNo());

		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_booking());
		//previous code  commented by basha
		//String Title = "BusInvoice";
		//message.setSubject("Bus Invoice");
		//new code added by basha
		String Title = "Credit Note Issued on Bus Booking With OrderId-  "+busOrderRow.getOrderId()+ " And Invoice No: "+busOrderRow.getInvoiceNo();
		message.setSubject("Credit Note Issued on Bus Booking With OrderId-  "+busOrderRow.getOrderId()+ " And Invoice No: "+busOrderRow.getInvoiceNo());


		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals("")) && (email.getCcEmailAddress()==null || email.getCcEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals("")) && (email.getToEmailAddress()==null || email.getToEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}else{
				message.setTo(receptemail);
				if(!company.getEmail().equalsIgnoreCase(receptemail))
					message.setCc(company.getEmail());
			}
		}

		message.setText(htmlContent, true);
		message.addAttachment(Title,new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
				"BusInvoicePDF", dateFormat.format(date),
				String.valueOf(new Random().nextInt()))));
		// Send mail
		this.mailSender.send(mimeMessage);
		logger.info("Sent Bus Credit Note mail to...." + receptemail	+ " for invoice no " + busOrderRow.getInvoiceNo());
		return true;
	}


	//Created by harsha---------- for sending mail to corporate companies one day before based on  corporate agreement expiry date
	public boolean sendCorporateAgreementExpiryMail(Company company,Company parentCompany,String imagePngValue, Locale locale,HttpServletRequest request, HttpServletResponse response, ServletContext servletContext,ApplicationContext applicationContext)throws MessagingException {
		// TODO Auto-generated method stub
		if(company!=null && company.getAgreementExpiryDate()!=null){
			/*String recipientEmail = company.getEmail();*/
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("company", company);
			variables.put("parentCompany",parentCompany);
			variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
			final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);
			CommonConfig cc=CommonConfig.GetCommonConfig();
			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true , "UTF-8");
			message.setSubject("Corporate Agreement Expiry Alert");
			if (company != null && company.getEmail()!=null)
				message.setTo(company.getEmail());

			if (parentCompany != null && parentCompany.getEmail()!=null)
				message.setCc(parentCompany.getEmail());

			message.setFrom(cc.getEmail_admin());
			message.setBcc(cc.getEmail_support());
			final String htmlContent = this.templateEngine.process("Corporate-Agreement-Expiry-Alert.html", ctx);
			message.setText(htmlContent, true /* isHtml */);
			message.addAttachment("Corporate Agreement Expiry",
					new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
							"CorporateAgreementExpiryPDF", "CorporateAgreementExpiry-"+dateFormat.format(date),
							String.valueOf(new Random().nextInt()))));

			this.mailSender.send(mimeMessage);
			/*logger.info("############Credit Note Request sent to...." + recipientEmail+ " for invoice no " + );*/

		}
		return true;
	}
	public boolean sendBusOrderMailVoucher(boolean isCustomer, final BusOrderRow busorder, final BusOrderCustomerDetail  busOrderCustomerDetail,final User user,
			final Company company, final String imageContentType,
			final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext, String htmlContent, Email email)
					throws MessagingException, MailException, Exception, NullPointerException{

		user.initLogoDisplayable();
		String receptemail=user.getEmail();


		if(isCustomer)
			receptemail = busOrderCustomerDetail.getEmail()!=null && !busOrderCustomerDetail.getEmail().equalsIgnoreCase("")?busOrderCustomerDetail.getEmail():user.getEmail();

			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
			CommonConfig cc = CommonConfig.GetCommonConfig();
			String title="Bus E-Voucher With OrderId-  "+busorder.getOrderId()+ " And Invoice No: "+busorder.getInvoiceNo();
			message.setSubject("Agency: Bus E-Voucher With OrderId-  "+busorder.getOrderId()+ " And Invoice No: "+busorder.getInvoiceNo());
			if(isCustomer)
				message.setSubject("Customer: Bus E-Voucher With OrderId-  "+busorder.getOrderId()+ " And Invoice No: "+busorder.getInvoiceNo());

			message.setFrom(cc.getEmail_admin());		
			message.setBcc(cc.getEmail_booking());

			String toEmails []=null;
			String CCEmails []=null;
			if(email!=null){
				if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
					toEmails=email.getToEmailAddress().split(";");
					CCEmails=email.getCcEmailAddress().split(";");
					message.setTo(toEmails);
					message.setCc(CCEmails);
				}
				else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals("")) && (email.getCcEmailAddress()==null || email.getCcEmailAddress().equals(""))){
					toEmails=email.getToEmailAddress().split(";");
					message.setTo(toEmails);
				}
				else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals("")) && (email.getToEmailAddress()==null || email.getToEmailAddress().equals(""))){
					CCEmails=email.getCcEmailAddress().split(";");
					message.setCc(CCEmails);
				}else{
					message.setTo(receptemail);
					if(!company.getEmail().equalsIgnoreCase(receptemail))
						message.setCc(company.getEmail());
				}
			}			

			if((busorder.getStatusAction().equalsIgnoreCase("Failed") ||  busorder.getStatusAction().equalsIgnoreCase("Failed") )&& busorder.getBookingMode().equalsIgnoreCase("Online")){
				LogsRequestResponse logsRequestResponse = new LogsRequestResponse();
				logsRequestResponse.sendLogsToTeamLeads(null,null,busorder, user, locale,email,company, request, response, servletContext, applicationContext,mimeMessage,message);
			}


			message.setText(htmlContent, true /* isHtml */);
			message.addAttachment(
					title,
					new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
							"BusVoucherPDF", dateFormat.format(date),
							String.valueOf(new Random().nextInt()))));


			this.mailSender.send(mimeMessage);
			logger.info("Sent Bus Voucher mail to...."  + "  " );
			return true;
	}



	/*public boolean sendFlightNotificationMailvvvv(final FlightOrderRow flightorder,
			final User user,final Company company, final String imageContentType,
			final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext,String path)
					throws MessagingException, MailException, NullPointerException, UnsupportedEncodingException , DocumentException, IOException, Exception {

		String companyEmail = user.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		User user1= new User();
		user1.setImagepath(path);
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("flightorder", flightorder);
		variables.put("company", company);
		variables.put("user", user);
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true  multipart , "UTF-8");
		//message.setSubject("Flight Reminder");
		//added by basha
		message.setSubject("PNR:"+flightorder.getPnr() +"- Your Flight From"+flightorder.getOrigin()+"-"+flightorder.getDestination() +"Departing in 24/4 hrs");
		CommonConfig cc=CommonConfig.GetCommonConfig(); 
		message.setFrom(cc.getEmail_admin());
		message.setTo(companyEmail);
		message.setBcc(cc.getEmail_support());
		message.setBcc(cc.getEmail_booking());

		final String htmlContent = this.templateEngine.process("Flight-Booked-Notification.html", ctx);
		message.setText(htmlContent, true);

		this.mailSender.send(mimeMessage);
				logger.info("Sent Flight invoice mail to...." + companyEmail + " for invoice no ");

		return true;
	}*/
	public boolean sendMiscellaneousOrderMailInvoice(boolean isSupplier, boolean isCustomer, MiscellaneousOrderRow miscellaneousOrderRow, User user, Company company,
			String imagePngValue, Locale locale, HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, ApplicationContext applicationContext, String imagePth, String htmlContent,
			Email email) throws MessagingException {


		String recipientName = miscellaneousOrderRow.getOrderCustomer().getFirstName() + " " + miscellaneousOrderRow.getOrderCustomer().getLastName();
		//String recipientEmail = flightorder.getCustomer().getEmail();
		String address = miscellaneousOrderRow.getOrderCustomer().getAddress();
		user.initLogoDisplayable();
		String receptemail = user.getEmail();

		if(isCustomer)
			receptemail = miscellaneousOrderRow.getOrderCustomer().getEmail();

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setSubject("Agency: Miscellaneous Invoice With OrderId-  "+miscellaneousOrderRow.getOrderId()+ " And Invoice No: "+miscellaneousOrderRow.getInvoiceNo());
		if(isCustomer)
			message.setSubject("Customer: Miscellaneous Invoice With OrderId-  "+miscellaneousOrderRow.getOrderId()+ " And Invoice No: "+miscellaneousOrderRow.getInvoiceNo());

		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_booking());
		String Title = "MiscellaneousInvoice With OrderId-  "+miscellaneousOrderRow.getOrderId()+ " And Invoice No: "+miscellaneousOrderRow.getInvoiceNo();
		message.setSubject("Miscellaneous Invoice With OrderId-  "+miscellaneousOrderRow.getOrderId()+ " And Invoice No: "+miscellaneousOrderRow.getInvoiceNo());


		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals("")) && (email.getCcEmailAddress()==null || email.getCcEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals("")) && (email.getToEmailAddress()==null || email.getToEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}else{
				message.setTo(receptemail);
				if(!company.getEmail().equalsIgnoreCase(receptemail))
					message.setCc(company.getEmail());
			}
		}

		message.setText(htmlContent, true);
		message.addAttachment(Title,new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
				"MiscellaneousInvoicePDF", dateFormat.format(date),
				String.valueOf(new Random().nextInt()))));
		// Send mail
		this.mailSender.send(mimeMessage);
		logger.info("Sent Miscellaneous invoice mail to...." + receptemail	+ " for invoice no " + miscellaneousOrderRow.getInvoiceNo());
		return true;




	}
	public boolean sendCreditNoteIssueMiscellaneous(boolean isSupplier, boolean isCustomer,MiscellaneousOrderRow miscellaneousOrderRow,
			MiscellaneousCreditNote creditNote, User user, Company company,
			Company parentCompany, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext,
			ApplicationContext applicationContext, String htmlContent,
			Email email) throws MessagingException {



		String recipientName = miscellaneousOrderRow.getOrderCustomer().getFirstName() + " " + miscellaneousOrderRow.getOrderCustomer().getLastName();
		//String recipientEmail = flightorder.getCustomer().getEmail();
		String address = miscellaneousOrderRow.getOrderCustomer().getAddress();
		user.initLogoDisplayable();
		String receptemail = user.getEmail();

		if(isCustomer)
			receptemail = miscellaneousOrderRow.getOrderCustomer().getEmail();

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setSubject("Agency: Miscellaneous Credit Note With OrderId-  "+miscellaneousOrderRow.getOrderId()+ " And Invoice No: "+miscellaneousOrderRow.getInvoiceNo());
		if(isCustomer)
			message.setSubject("Customer: Miscellaneous Credit Note With OrderId-  "+miscellaneousOrderRow.getOrderId()+ " And Invoice No: "+miscellaneousOrderRow.getInvoiceNo());

		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_booking());
		String Title = "Credit Note Issued on Miscellaneous Booking With OrderId-  "+miscellaneousOrderRow.getOrderId()+ " And Invoice No: "+miscellaneousOrderRow.getInvoiceNo();
		message.setSubject("Credit Note Issued on Miscellaneous Booking With OrderId-  "+miscellaneousOrderRow.getOrderId()+ " And Invoice No: "+miscellaneousOrderRow.getInvoiceNo());


		String toEmails []=null;
		String CCEmails []=null;
		if(email!=null){
			if((email.getCcEmailAddress()!=null && email.getToEmailAddress()!=null)&&(!email.getCcEmailAddress().equals("") && !email.getToEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				CCEmails=email.getCcEmailAddress().split(";");
				message.setTo(toEmails);
				message.setCc(CCEmails);
			}
			else if((email.getToEmailAddress()!=null && !email.getToEmailAddress().equals("")) && (email.getCcEmailAddress()==null || email.getCcEmailAddress().equals(""))){
				toEmails=email.getToEmailAddress().split(";");
				message.setTo(toEmails);
			}
			else if((email.getCcEmailAddress()!=null && !email.getCcEmailAddress().equals("")) && (email.getToEmailAddress()==null || email.getToEmailAddress().equals(""))){
				CCEmails=email.getCcEmailAddress().split(";");
				message.setCc(CCEmails);
			}else{
				message.setTo(receptemail);
				if(!company.getEmail().equalsIgnoreCase(receptemail))
					message.setCc(company.getEmail());
			}
		}

		message.setText(htmlContent, true);
		message.addAttachment(Title,new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
				"MiscellaneousInvoicePDF", dateFormat.format(date),
				String.valueOf(new Random().nextInt()))));
		// Send mail
		this.mailSender.send(mimeMessage);
		logger.info("Sent Miscellaneous Credit Note mail to...." + receptemail	+ " for invoice no " + miscellaneousOrderRow.getInvoiceNo());
		return true;
	}
	public boolean sendCreditNoteRequestMiscellaneous(MiscellaneousOrderRow miscellaneousOrderRow,
			MiscellaneousCreditNote creditNote, User user, Company company,
			Company parentCompany, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext,
			ApplicationContext applicationContext) throws MessagingException {


		String recipientEmail = user.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("creditnote", creditNote);
		variables.put("miscellaneousOrderRow", miscellaneousOrderRow);
		variables.put("user", user);
		variables.put("company", company);
		variables.put("parentcompany", parentCompany);
		HashMap<String, String> datetimemap = CommonUtil.getDateTimeMapFromTimestamp(creditNote.getOrderedAt());
		variables.put("requestdate", datetimemap.get("date"));
		variables.put("requesttime", datetimemap.get("time"));
		variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":"	+ request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");
		message.setSubject("Credit Note Request on Miscellaneous Booking With OrderId-  "+miscellaneousOrderRow.getOrderId()+ " And Invoice No: "+miscellaneousOrderRow.getInvoiceNo());
		String title="Credit Note Request on Miscellaneous Booking With OrderId-  "+miscellaneousOrderRow.getOrderId()+ " And Invoice No: "+miscellaneousOrderRow.getInvoiceNo();
		CommonConfig cc=CommonConfig.GetCommonConfig();
		if (parentCompany != null)
			message.setTo(parentCompany.getEmail());

		message.setFrom(cc.getEmail_admin());
		message.setBcc(cc.getEmail_support());
		message.setBcc(company.getEmail());
		message.setBcc(cc.getEmail_booking());
		final String htmlContent = this.templateEngine.process("Miscellaneous-Request-Creditnote.html", ctx);
		message.setText(htmlContent, true /* isHtml */);
		message.addAttachment(
				title,
				new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent,
						"MiscellaneousCreditNotePDF", "Credit Request-Miscellaneous-"+dateFormat.format(date),
						String.valueOf(new Random().nextInt()))));

		this.mailSender.send(mimeMessage);
		logger.info("############Credit Note Request sent to...." + recipientEmail+ " for invoice no " + miscellaneousOrderRow.getInvoiceNo());
		return true;


	}


	public String checkInDate(Date checkInDate) {
		// System.out.println("checkInDate"+new SimpleDateFormat("dd MMM yyyy").format(checkInDate));
		return new SimpleDateFormat("dd MMM yyyy").format(checkInDate);

	}




	public boolean sendEnquiryUmrah(TayyarahUmrahContactDetails tayyarahUmrahContactDetails, String imagePngValue,
			Locale locale, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext,
			ApplicationContext applicationContext, String imagePth) throws MessagingException {

		String recipientEmail = tayyarahUmrahContactDetails.getEmail();
		Map<String, Object> variables = new HashMap<String, Object>(); 
		final IWebContext ctx = new SpringWebContext(request, response,servletContext, locale, variables, applicationContext); 
		variables.put("tayyarahUmrahContactDetails",tayyarahUmrahContactDetails ); 
		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true /* multipart */, "UTF-8");

		CommonConfig cc=CommonConfig.GetCommonConfig();
		message.setTo("info@tayyarahumrah.com");
		message.setBcc("yogiwebs@gmail.com");
		//message.setBcc(cc.getEmail_booking());
		String Title = "Enquiry Details For"+tayyarahUmrahContactDetails.getName();
		message.setSubject("TayyarahUmrah Enquiry Details"); 
		final String htmlContent = this.templateEngine.process("TayyarahUmrahEnquiryForm.html", ctx);
		message.setText(htmlContent, true /* isHtml */); 
		// Send mail
		this.mailSender.send(mimeMessage);
		logger.info("sendEnquiryUmrah  mail sent to...." +"info@tayyarahumrah.com" ); 

		return true; 
	}
}