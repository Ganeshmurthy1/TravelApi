package com.tayyarah.email.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.spring3.context.SpringWebContext;
import com.itextpdf.text.DocumentException;
import com.tayyarah.email.dao.AllEmailDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.entity.Company;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.dao.EmailNotificationDao;
import com.tayyarah.email.dao.FlightOrderRowEmailDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.email.entity.model.EmailNotification;
import com.tayyarah.flight.commission.dao.AirlineCommissionBlockDaoImp;
import com.tayyarah.flight.commission.dao.AirlineCommissionSheetDaoImp;
import com.tayyarah.flight.commission.entity.AirlineCommissionCompanyBlock;
import com.tayyarah.flight.commission.entity.AirlineCommissionMasterSheet;
import com.tayyarah.flight.dao.FlightCreditNoteDao;
import com.tayyarah.flight.model.FlightInvoiceData;
import com.tayyarah.hotel.dao.HotelCreditNoteDao;
import com.tayyarah.hotel.dao.HotelOrderRowEmailDao;
import com.tayyarah.hotel.model.HotelEmailInvoiceData;
import com.tayyarah.user.dao.WalletTransferHistoryDAO;
import com.tayyarah.user.entity.User;

@Controller
@RequestMapping(value = "/EmailNotification")
public class EmailNotificationController {
	static final Logger logger = Logger.getLogger(EmailNotificationController.class);
	@Autowired
	ServletContext servletContext;
	@Autowired
	ApplicationContext applicationContext;
	@Autowired
	FlightOrderRowEmailDao flightOrderRowEmailDao;
	@Autowired
	HotelOrderRowEmailDao hotelOrderId;
	@Autowired
	FlightInvoiceData pricebreakup;
	@Autowired
	HotelEmailInvoiceData hotelInvoiceData;
	@Autowired
	AllEmailDao allEmailDao;
	@Autowired
	EmailDao emailDao;
	@Autowired
	EmailNotificationDao emailNotificationDao;
	@Autowired
	WalletTransferHistoryDAO walletTransferHistoryDAO;
	@Autowired
	FlightCreditNoteDao flightCreditNoteDao;
	@Autowired
	HotelCreditNoteDao hotelCreditNoteDao;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine templateEngine;
	Date date = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	@Autowired
	AirlineCommissionBlockDaoImp airlineCommissionBlockDao;	
	@Autowired
	AirlineCommissionSheetDaoImp airlineCommissionSheetDao;
	
	
	@RequestMapping(value = "/sendMails", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody String sendMails(HttpServletRequest request,
			HttpServletResponse response) {
		ResponseHeader.setResponse(response);// Setting response header
		final Locale locale = LocaleContextHolder.getLocale();
		List<EmailNotification> emailNotifications = null;
		try {
			logger.info("list pending emails : ");
			emailNotifications = emailNotificationDao.getPendingMailNotifications();
			if(emailNotifications!=null && emailNotifications.size()>0)
			{
				logger.info("list pending emails count- ilyas--: " + emailNotifications.size());				
				sendAndUpdate(emailNotifications, request, response, locale);
			}
		} catch (Exception e) {
			logger.info("pending emails retrival...Exception " + e.getMessage());
			e.printStackTrace();
		}
		return "success";
	}


	public List<Integer> sendAndUpdate(List<EmailNotification> emailNotifications,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale) {
		List<Integer> statusIds = new ArrayList<Integer>();
		for (EmailNotification emailNotification : emailNotifications) {
			if(emailNotification != null)
			{
				try
				{					
					if(emailNotification.getEmail() != null)
					{
						if(emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_SHEET_CREATION || emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_SHEET_MODIFICATION ||
								emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_SHEET_DELETION ||emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_SHEET_ACTIVATION ||
								emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_SHEET_DEACTIVATION ||emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_BLOCK_CREATION ||
								emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_BLOCK_MODIFICATION ||emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_BLOCK_DELETION ||
								emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_BLOCK_ACTIVATION ||emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_BLOCK_DEACTIVATION						
								)
						{
							logger.info("####### start email sent sendCommissionChangeNotification...  ");
							sendCommissionChangeNotification(emailNotification, locale, request, response, servletContext, applicationContext);
							logger.info("####### End email sent sendCommissionChangeNotification...  ");
						}
						else
						{
							sendSystemActionNotification(emailNotification, locale, request, response, servletContext, applicationContext);
						}

					}				
					logger.info("####### email sent sucussfully...  ");
					emailNotification.setStatusMsg("Successfully Sent");
					emailNotification.setStatus(Email.STATUS_SENT_SUCCESS);
					emailNotificationDao.emailUpdateStatus(emailNotification);	

				}// every email sending should be checked for exceptions like MessagingException, MailException, NullPointerException, UnsupportedEncodingException , DocumentException, IOException, Exception
				catch (MessagingException e) {
					// Update email failed...
					logger.info("#############Email Failed :: MessagingException");
					logger.error(e);
					emailNotification.setStatusMsg("Email Failed :: MessagingException");
					emailNotification.setStatus(Email.STATUS_EMAIL_BLOCKED);
					emailNotificationDao.emailUpdateStatus(emailNotification);
					e.printStackTrace();
				}
				catch (MailException e) {
					// Update email failed...
					logger.info("#############Email Failed :: MailException");
					logger.error(e);
					emailNotification.setStatusMsg("Email Failed :: MailException");
					emailNotification.setStatus(Email.STATUS_SENT_ERROR_WRONG_EMAIL);
					emailNotificationDao.emailUpdateStatus(emailNotification);
					e.printStackTrace();
				}
				catch (NullPointerException e) {
					// Update email failed...
					logger.info("#############Email Failed :: NullPointerException");
					logger.error(e);
					emailNotification.setStatusMsg("Email Failed :: NullPointerException");
					emailNotification.setStatus(Email.STATUS_SENT_ERROR_SERVER_ISSUE);
					emailNotificationDao.emailUpdateStatus(emailNotification);
					e.printStackTrace();
				}
				catch (UnsupportedEncodingException e) {
					// Update email failed...
					logger.info("#############Email Failed :: UnsupportedEncodingException");
					logger.error(e);
					emailNotification.setStatusMsg("Email Failed :: UnsupportedEncodingException");
					emailNotification.setStatus(Email.STATUS_SENT_ERROR_SERVER_ISSUE);
					emailNotificationDao.emailUpdateStatus(emailNotification);
					e.printStackTrace();
				}
				catch (DocumentException e) {
					// Update email failed...
					logger.info("#############Email Failed :: DocumentException");
					logger.error(e);
					emailNotification.setStatusMsg("Email Failed :: DocumentException");
					emailNotification.setStatus(Email.STATUS_SENT_ERROR_SERVER_ISSUE);
					emailNotificationDao.emailUpdateStatus(emailNotification);
					e.printStackTrace();
				}
				catch (IOException e) {
					// Update email failed...
					logger.info("#############Email Failed :: IOException");
					logger.error(e);
					emailNotification.setStatusMsg("Email Failed :: IOException");
					emailNotification.setStatus(Email.STATUS_SENT_ERROR_SERVER_ISSUE);
					emailNotificationDao.emailUpdateStatus(emailNotification);
					e.printStackTrace();
				}
				catch (Exception e) {
					// Update email failed...
					logger.info("#############Email Failed :: IOException");
					logger.error(e);
					emailNotification.setStatusMsg("Email Failed :: IOException");
					emailNotification.setStatus(Email.STATUS_SENT_ERROR_SERVER_ISSUE);
					emailNotificationDao.emailUpdateStatus(emailNotification);
					e.printStackTrace();
				}
			}
		}
		return statusIds;
	}



	public boolean sendSystemActionNotification(final EmailNotification emailNotification,
			final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException , DocumentException, IOException {

		Company receipientCompany = null;
		Company performingCompany = null;
		Company receivingCompany = null;
		User performingUser = null;
		User receivingUser = null;

		receipientCompany = emailNotification.getRecipientCompanyId()==null?null:allEmailDao.getCompanyByCompanyId(String.valueOf(emailNotification.getRecipientCompanyId()));
		performingCompany = emailNotification.getPerformerCompanyId()==null?null:allEmailDao.getCompanyByCompanyId(String.valueOf(emailNotification.getPerformerCompanyId()));
		receivingCompany = emailNotification.getReceiverCompanyId()==null?null:allEmailDao.getCompanyByCompanyId(String.valueOf(emailNotification.getReceiverCompanyId()));						
		receivingUser = emailNotification.getReceiverUserId()==null?null:allEmailDao.getUserByUserId(String.valueOf(emailNotification.getReceiverUserId()));

		String receipientCompanyType = "Super User";//Distributor/agency
		String performerCompanyType = "Super User";//Distributor/agency
		String receiverCompanyType = "Super User";//Distributor/agency
		//String performerUserType = "Super User";//usermode(Distributor/agency), admin(Employee)
		String receiverUserType = "Admin";//usermode(Distributor/agency), admin(Employee)
		
		if(receipientCompany != null && performingCompany != null && receivingCompany != null && receivingUser != null && performingCompany.getCompanyid() != receivingCompany.getCompanyid())
		{
			if(receipientCompany.getCompanyRole().isAgent())
				receipientCompanyType = "Agent";
			else if(receipientCompany.getCompanyRole().isDistributor())
				receipientCompanyType = "Distributor";

			if(performingCompany.getCompanyRole().isAgent())
				performerCompanyType = "Agent";
			else if(performingCompany.getCompanyRole().isDistributor())
				performerCompanyType = "Distributor";

			if(performingCompany.getCompanyRole().isAgent())
				receiverCompanyType = "Agent";
			else if(performingCompany.getCompanyRole().isDistributor())
				receiverCompanyType = "Distributor";

			String action = "";	
			//int actionType = 1;	//0 parent himself, 1 parent to child, 2 child to parent , 3 child himself
			String child = receivingCompany.getCompanyname()+ " ("+receivingCompany.getEmail()+")";
			String childType = receiverCompanyType;


			switch (emailNotification.getEmail().getType()) {
			case Email.EMAIL_TYPE_FLIGHT_VOUCHER:
				action = " has sent flight E-Ticket to ";
				//actionType = 1;
				break;
			case Email.EMAIL_TYPE_HOTEL_VOUCHER:
				action = " has sent hotel voucher to";
				//actionType = 1;
				break;
			case Email.EMAIL_TYPE_USER_REGISTRATION:
				action = " has registered ";
				//actionType = 1;


				child = receivingUser.getUsername()+ " ("+receivingUser.getEmail()+")";
				childType = receiverUserType;


				break;							
			case Email.EMAIL_TYPE_USER_CREDENTIALS:
				action = " has sent login credentials to ";
				//actionType = 1;

				child = receivingUser.getUsername()+ " ("+receivingUser.getEmail()+")";
				childType = receiverUserType;

				break;							
			case Email.EMAIL_TYPE_USER_FORGOT_PWD_REGISTRATION:
				action = " has sent password details (forgot password) to ";
				//actionType = 3;
				child = receivingUser.getUsername()+ " ("+receivingUser.getEmail()+")";
				childType = receiverUserType;


				break;
			case Email.EMAIL_TYPE_COMPANY_REGISTRATION:
				action = " has registered ";
				//actionType = 1;
				break;							
			case Email.EMAIL_TYPE_COMPANY_FORGOT_PWD:
				action = " has sent password details (forgot password) to ";	
				//actionType = 1;
				break;
			case Email.EMAIL_TYPE_FRONT_USER_REGISTRATION:
				action = " has registered ";
				//actionType = 1;
				child = receivingUser.getUsername()+ " ("+receivingUser.getEmail()+")";
				childType = receiverUserType;


				break;
			case Email.EMAIL_TYPE_FRONT_USER_FORGOT_PWD:
				action = " has sent password details (forgot password) to  ";	
				//actionType = 3;
				child = receivingUser.getUsername()+ " ("+receivingUser.getEmail()+")";
				childType = receiverUserType;


				break;
			case Email.EMAIL_TYPE_COMPANY_APPROVAL:
				action = " has approved ";
				//actionType = 1;
				break;

			case Email.EMAIL_TYPE_BLOCKED_USER:
				action = " has blocked ";
				//actionType = 1;
				child = receivingUser.getUsername()+ " ("+receivingUser.getEmail()+")";
				childType = receiverUserType;
				break;
			case Email.EMAIL_TYPE_WHITE_LABEL:
				action = " has white labeled ";
				//actionType = 1;
				break;
			case Email.EMAIL_TYPE_USER_RESET_PASSWORD:
				action = " has reset password ";
				//actionType = 3;
				child = receivingUser.getUsername()+ " ("+receivingUser.getEmail()+")";
				childType = receiverUserType;
				break;
			case Email.EMAIL_TYPE_COMPANY_RESET_PASSWORD:
				action = " has reset password ";
				//actionType = 3;
				break;
			case Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS:
				action = " has sent flight E-Invoice to ";
				//actionType = 1;
				break;
			case Email.EMAIL_TYPE_DIS_INVOICE_TO_OTHERS:
				action = " has sent flight E-Invoice to ";
				//actionType = 1;
				break;	
			case Email.EMAIL_TYPE_HOTEL_INVOICE_SUPER_TO_OTHERS:
				action = " has sent hotel E-Invoice to ";
				//actionType = 1;
				break;	
			case Email.EMAIL_TYPE_HOTEL_INVOICE_DISTRIBUTOR_TO_OTHERS:
				action = " has sent hotel E-Invoice to ";
				//actionType = 1;
				break;		
				
			/*case Email.EMAIL_TYPE_WALLET_CREDIT_CHILD_NOTIFICATION:
				sendWalletNoticationEmail(email, request, response, locale);
				statusIds.add(email.getType());
				logger.info("---------EMAIL_TYPE_WALLET_CREDIT_CHILD_NOTIFICATION--------"
						+ statusIds.size());
				break;
			case Email.EMAIL_TYPE_WALLET_CREDIT_PARENT_NOTIFICATION:
				sendWalletNoticationEmail(email, request, response, locale);
				statusIds.add(email.getType());
				logger.info("---------EMAIL_TYPE_WALLET_CREDIT_PARENT_NOTIFICATION--------"
						+ statusIds.size());
				break;

				
			case Email.EMAIL_TYPE_WALLET_DEBIT_CHILD_NOTIFICATION:
				sendWalletNoticationEmail(email, request, response, locale);
				statusIds.add(email.getType());
				logger.info("---------EMAIL_TYPE_WALLET_DEBIT_CHILD_NOTIFICATION--------"
						+ statusIds.size());
				break;
			case Email.EMAIL_TYPE_WALLET_DEBIT_PARENT_NOTIFICATION:
				sendWalletNoticationEmail(email, request, response, locale);
				statusIds.add(email.getType());
				logger.info("---------EMAIL_TYPE_WALLET_DEBIT_PARENT_NOTIFICATION--------"
						+ statusIds.size());
				break;
*/
			/*case Email.EMAIL_TYPE_WALLET_NOTIFICATION:

				WalletAmountTranferHistory walletHistory = walletTransferHistoryDAO.getWalletById(emailNotification.getEmail().getOrderId());

				String innerActionType = "credit";
				if(walletHistory != null)
				{								
					if(walletHistory.getTransactionType() != null)
						innerActionType = walletHistory.getTransactionType();
				}
				if(innerActionType.equalsIgnoreCase("credit"))
					action = " has "+innerActionType+"ed to ";
				else
					action = " has "+innerActionType+"ed from ";
				//actionType = 1;

				child = receivingUser.getUsername()+ " ("+receivingUser.getEmail()+")";
				childType = receiverUserType;
				break;*/

			case Email.EMAIL_TYPE_CREDITNOTE_REQUEST_HOTEL:
				action = " has sent hotel credit note request to ";
				//actionType = 2;
				break;
			case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_HOTEL:
				action = " has issued hotel credit note to ";
				//actionType = 1;
				break;
			case Email.EMAIL_TYPE_CREDITNOTE_REQUEST_FLIGHT:
				action = " has sent flight credit note request to ";
				//actionType = 2;
				break;
			case Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_FLIGHT:
				action = " has issued flight credit note to ";
				//actionType = 1;
				break;
			default:
				action = "";
				//actionType = 1;
				break;
			}
			receipientCompany.tranformDisplayable();
			performingCompany.tranformDisplayable();
			receivingCompany.tranformDisplayable();	
			String recipientEmail = receipientCompany.getEmail();		
			Map<String, Object> variables = new HashMap<String, Object>();	

			variables.put("receipientComp", receipientCompany);
			variables.put("parentComp", performingCompany);
			variables.put("childComp", receivingCompany);
			variables.put("receipient", receipientCompany.getCompanyname());
			variables.put("parent", performingCompany.getCompanyname() + " ("+performingCompany.getEmail()+")");
			variables.put("receipientType", receipientCompanyType);
			variables.put("parentType", performerCompanyType);
			variables.put("childType", receiverCompanyType);
			variables.put("child", receivingCompany.getCompanyname()+ " ("+receivingCompany.getEmail()+")");
			variables.put("action", action);
			variables.put("actionType", emailNotification.getActionType());

			HashMap<String, String> datetimemap = CommonUtil.getDateTimeMapFromTimestamp(emailNotification.getEmail().getCreatedAt());
			CommonConfig conf = CommonConfig.GetCommonConfig();		
			String walletalertpath = CommonConfig.GetCommonConfig().getAdmin_url()+"images/walletalert.png";		
			variables.put("date", datetimemap.get("date"));	
			variables.put("time", datetimemap.get("time"));	
			variables.put("baseUrl",
					request.getScheme() + "://" + request.getServerName() + ":"
							+ request.getServerPort());
			response.setContentType("application/pdf");
			response.setHeader("content-disposition", "htmlContent; filename="
					+ "Filename.pdf");
			final IWebContext ctx = new SpringWebContext(request, response,
					servletContext, locale, variables, applicationContext);

			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
					true /* multipart */, "UTF-8");						
			message.setSubject("System Notification");	

			CommonConfig cc=CommonConfig.GetCommonConfig();
			message.setFrom(cc.getEmail_support());
			message.setTo(recipientEmail);			
			message.setCc(cc.getCare_mail_id());
			message.setBcc(cc.getEmail_support_bcc());
			message.setCc(cc.getEmail_support());
			
			// Create the HTML body using Thymeleaf
			final String htmlContent = this.templateEngine.process(
					"ParentActionNotification.html", ctx);			
			message.setText(htmlContent, true /* isHtml */);
			this.mailSender.send(mimeMessage);
			logger.info("System action notification to...." + recipientEmail);
			return true;
		}
		return false;
	}


	public boolean sendCommissionChangeNotification(final EmailNotification emailNotification,
			final Locale locale,
			final HttpServletRequest request,
			final HttpServletResponse response,
			final ServletContext servletContext,
			final ApplicationContext applicationContext)
					throws MessagingException, MailException, Exception, NullPointerException, UnsupportedEncodingException , DocumentException, IOException {
		if(emailNotification != null)
		{
			Company receipientCompany = null;
			Company performingCompany = null;
			Company receivingCompany = null;
			AirlineCommissionMasterSheet superUserAirlineCommissionSheet = null;
			AirlineCommissionCompanyBlock airlineCommissionCompanyBlock = null;
			receipientCompany = emailNotification.getRecipientCompanyId()==null?null:allEmailDao.getCompanyByCompanyId(String.valueOf(emailNotification.getRecipientCompanyId()));
			performingCompany = emailNotification.getPerformerCompanyId()==null?null:allEmailDao.getCompanyByCompanyId(String.valueOf(emailNotification.getPerformerCompanyId()));
			receivingCompany = emailNotification.getReceiverCompanyId()==null?null:allEmailDao.getCompanyByCompanyId(String.valueOf(emailNotification.getReceiverCompanyId()));						

			Long id = Long.valueOf(emailNotification.getOrderId()==null?"0":emailNotification.getOrderId());
			String itemname = "";
			String itemtype = "";
			if(emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_SHEET_CREATION || emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_SHEET_MODIFICATION ||
					emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_SHEET_DELETION ||emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_SHEET_ACTIVATION ||
					emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_SHEET_DEACTIVATION					
					)
			{
				superUserAirlineCommissionSheet = airlineCommissionSheetDao.getSuperUserAirlineCommissionSheet(id);
				if(superUserAirlineCommissionSheet != null)
				{
					itemname = superUserAirlineCommissionSheet.getName();
					itemtype = "Commission Sheet";
				}
			}
			else if( emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_BLOCK_CREATION ||
					emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_BLOCK_MODIFICATION ||emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_BLOCK_DELETION ||
					emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_BLOCK_ACTIVATION ||emailNotification.getType() == EmailNotification.EMAIL_TYPE_COMMISSION_BLOCK_DEACTIVATION	)
			{
				airlineCommissionCompanyBlock = airlineCommissionBlockDao.getAirlineCommissionCompanyBlock(id);
				if(airlineCommissionCompanyBlock != null)
				{
					itemname = airlineCommissionCompanyBlock.getName();
					itemtype = "Commission Block";
				}
			}
			String receipientCompanyType = "Super User";//Distributor/agency
			String performerCompanyType = "Super User";//Distributor/agency
			String receiverCompanyType = "Super User";//Distributor/agency			

			if(receipientCompany != null && performingCompany != null && receivingCompany != null)
			{
				if(receipientCompany.getCompanyRole().isAgent())
					receipientCompanyType = "Agent";
				else if(receipientCompany.getCompanyRole().isDistributor())
					receipientCompanyType = "Distributor";

				if(performingCompany.getCompanyRole().isAgent())
					performerCompanyType = "Agent";
				else if(performingCompany.getCompanyRole().isDistributor())
					performerCompanyType = "Distributor";

				if(performingCompany.getCompanyRole().isAgent())
					receiverCompanyType = "Agent";
				else if(performingCompany.getCompanyRole().isDistributor())
					receiverCompanyType = "Distributor";
				String action = "";	
				//int actionType = 1;	//0 parent himself, 1 parent to child, 2 child to parent , 3 child himself
				String child = receivingCompany.getCompanyname()+ " ("+receivingCompany.getEmail()+")";
				String childType = receiverCompanyType;

				switch (emailNotification.getType()) {
				case EmailNotification.EMAIL_TYPE_COMMISSION_SHEET_CREATION:
					action = " has created ";					
					break;
				case EmailNotification.EMAIL_TYPE_COMMISSION_SHEET_MODIFICATION:
					action = " has modified ";					
					break;
				case EmailNotification.EMAIL_TYPE_COMMISSION_SHEET_DELETION:
					action = " has deleted ";					
					break;					
				case EmailNotification.EMAIL_TYPE_COMMISSION_SHEET_ACTIVATION:
					action = " has activated ";					
					break;
				case EmailNotification.EMAIL_TYPE_COMMISSION_SHEET_DEACTIVATION:
					action = " has deactivated ";					
					break;		
				case EmailNotification.EMAIL_TYPE_COMMISSION_BLOCK_CREATION:
					action = " has created ";					
					break;
				case EmailNotification.EMAIL_TYPE_COMMISSION_BLOCK_MODIFICATION:
					action = " has modified ";					
					break;
				case EmailNotification.EMAIL_TYPE_COMMISSION_BLOCK_DELETION:
					action = " has deleted ";					
					break;					
				case EmailNotification.EMAIL_TYPE_COMMISSION_BLOCK_ACTIVATION:
					action = " has activated ";					
					break;
				case EmailNotification.EMAIL_TYPE_COMMISSION_BLOCK_DEACTIVATION:
					action = " has deactivated ";					
					break;	
				default:
					action = "";
					//actionType = 1;
					break;
				}	

				receipientCompany.tranformDisplayable();
				performingCompany.tranformDisplayable();
				receivingCompany.tranformDisplayable();	
				String recipientEmail = receipientCompany.getEmail();		
				Map<String, Object> variables = new HashMap<String, Object>();		
				variables.put("receipientComp", receipientCompany);
				variables.put("parentComp", performingCompany);
				variables.put("childComp", receivingCompany);
				variables.put("receipient", receipientCompany.getCompanyname());
				variables.put("parent", performingCompany.getCompanyname() + " ("+performingCompany.getEmail()+")");
				variables.put("child", receivingCompany.getCompanyname()+ " ("+receivingCompany.getEmail()+")");
				variables.put("item", itemname);
				variables.put("receipientType", receipientCompanyType);
				variables.put("parentType", performerCompanyType);
				variables.put("childType", receiverCompanyType);
				variables.put("itemType", itemtype);
				variables.put("action", action);
				variables.put("actionType", emailNotification.getActionType());

				HashMap<String, String> datetimemap = CommonUtil.getDateTimeMapFromTimestamp(emailNotification.getCreatedAt());
				CommonConfig conf = CommonConfig.GetCommonConfig();		
				String walletalertpath = CommonConfig.GetCommonConfig().getAdmin_url()+"images/walletalert.png";		
				variables.put("date", datetimemap.get("date"));	
				variables.put("time", datetimemap.get("time"));	
				variables.put("baseUrl",
						request.getScheme() + "://" + request.getServerName() + ":"
								+ request.getServerPort());
				response.setContentType("application/pdf");
				response.setHeader("content-disposition", "htmlContent; filename="
						+ "Filename.pdf");
				final IWebContext ctx = new SpringWebContext(request, response,
						servletContext, locale, variables, applicationContext);

				// Prepare message using a Spring helper
				final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
				final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true /* multipart */, "UTF-8");						
				message.setSubject("Commission Notification");	

				CommonConfig cc = CommonConfig.GetCommonConfig();
				message.setFrom(cc.getEmail_support());
				message.setTo(recipientEmail);			
				message.setCc(cc.getCare_mail_id());
				message.setBcc(cc.getEmail_support_bcc());
				message.setCc(cc.getEmail_support());
				
				// Create the HTML body using Thymeleaf
				final String htmlContent = this.templateEngine.process(
						"CommissionNotification.html", ctx);				
				message.setText(htmlContent, true /* isHtml */);			
				this.mailSender.send(mimeMessage);
				logger.info("System action notification to...." + recipientEmail);
			}

		}
		return true;
	}
}