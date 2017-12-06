package com.tayyarah.email.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
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

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.spring3.context.SpringWebContext;

import com.itextpdf.text.DocumentException;
import com.tayyarah.bus.dao.BusCreditNoteDao;
import com.tayyarah.bus.dao.BusOrderRowEmailDao;
import com.tayyarah.bus.entity.BusCreditNote;
import com.tayyarah.bus.entity.BusOrderCustomerDetail;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.bus.quotation.entity.BusTravelRequestQuotation;
import com.tayyarah.common.dao.RmConfigDetailDAO;
import com.tayyarah.common.entity.RmConfigModel;
import com.tayyarah.common.entity.RmConfigTripDetailsModel;
import com.tayyarah.common.entity.StateInfo;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.ArithmeticUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyEntity;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.dao.AllEmailDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.email.dao.EmailNotificationDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.email.entity.model.EmailNotification;
import com.tayyarah.services.EmailContentService;
import com.tayyarah.services.EmailService;
import com.tayyarah.user.entity.User;

@Component
public class BusEmailHelper {

	public static final Logger logger = Logger.getLogger(BusEmailHelper.class);

	public String sendBusVoucherEmail(Email email, HttpServletRequest request, HttpServletResponse response, Locale locale, BusOrderRowEmailDao busOrderRowEmailDao, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService) throws MessagingException, MailException, NullPointerException,
	UnsupportedEncodingException, DocumentException, IOException, Exception {
		String htmlContent = null;
		
		if (email != null) {
			BusOrderRow busOrderRow = null;
			Company company = null;
			CompanyEntity companyEntity=null;
			User user = null;
			String orderId = email.getOrderId();	
			HttpSession session = request.getSession();			
			busOrderRow = busOrderRowEmailDao.getBusOrderRowDetailsByOrderId(orderId);
			if (busOrderRow != null) {			
				SimpleDateFormat dateFormatShow = new SimpleDateFormat("dd-MMM-yy");
				String onwardDate = dateFormatShow.format(busOrderRow.getTravelDate());			
				BigDecimal finalAmount = busOrderRow.getTotalAmount();
				finalAmount = AmountRoundingModeUtil.roundingModeForBus(finalAmount);				
				busOrderRow.setTotalAmount(finalAmount);
				company = allEmailDao.getCompanyByCompanyId(busOrderRow.getCompanyId());
				user = allEmailDao.getUserByEmail(company.getEmail());			
				Company superUserObj=allEmailDao.getSuperCompanyByCompanyUserId(company.getSuper_company_userid());
				if(company.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()==null){
					company.setTemsandcondtions("NA");
					superUserObj.setTemsandcondtions("NA");
				}else if(company.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()!=null){
					company.setTemsandcondtions(superUserObj.getTemsandcondtions());
				}else if(company.getTemsandcondtions()==null){
					company.setTemsandcondtions("NA");
				}				
				if(busOrderRow.getCompanyEntityId()!=null && busOrderRow.getCompanyEntityId() >0){
					companyEntity = allEmailDao.getCompanyEntityByCompanyId(busOrderRow.getCompanyEntityId());
					company = companyEntity.getCompany();
				}
				if (company != null && user != null) {			

					List<BusOrderCustomerDetail> busOrderCustomerDetailList = busOrderRowEmailDao.geBusOrderCustomerDetailList(busOrderRow.getId());
					BusOrderCustomerDetail busOrderCustomer = null;
					for (BusOrderCustomerDetail busOrderCustomerDetail : busOrderCustomerDetailList) {
						busOrderCustomer = busOrderCustomerDetail;
						break;
					}
					String CancelPolicyOffline=null;
					List<BusCancellationPolicy> busCancellationPolicyList = new ArrayList<>();
					if(busOrderRow.getBookingMode().equalsIgnoreCase("Online")){
						if(busOrderRow.getCancellationPolicy()!=null && !busOrderRow.getCancellationPolicy().equalsIgnoreCase("")){					
							JSONParser parser = new JSONParser();
							JSONArray cancelArray = (JSONArray) parser.parse(busOrderRow.getCancellationPolicy());
							for(int i=0;i<cancelArray.size();i++){
								BusCancellationPolicy busCancellationPolicy = new BusCancellationPolicy();
								JSONObject cancelpolicy = (JSONObject) cancelArray.get(i);
								String cutoffTime = (String) cancelpolicy.get("cutoffTime");
								String refundInPercentage = (String) cancelpolicy.get("refundInPercentage");
								busCancellationPolicy.setCutoffTime(cutoffTime);
								busCancellationPolicy.setRefundInPercentage(refundInPercentage);
								busCancellationPolicyList.add(busCancellationPolicy);
							}
						}

					}else{
						CancelPolicyOffline=busOrderRow.getCancellationPolicy();
					}
					session.setAttribute("isCorporate", company.getCompanyRole().isCorporate());
					session.setAttribute("onwarddate", onwardDate);
					session.setAttribute("customers", busOrderCustomerDetailList);
					if(busOrderRow.getBookingMode().equalsIgnoreCase("Online"))
						session.setAttribute("cancellationPolicy", busCancellationPolicyList);
					else
						session.setAttribute("cancellationPolicy", CancelPolicyOffline);
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
					String customeremail = busOrderCustomerDetailList.get(0).getEmail();

					Map<String, Object> variables = new HashMap<String, Object>();
					variables.put("busorder", busOrderRow);
					variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
					variables.put("user", user);
					variables.put("company", company);	
					variables.put("companyEntity", companyEntity);
					variables.put("baseUrl",
							request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
					final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale, variables,
							applicationContext);
					htmlContent = emailContentService.sendBusOrderMailVoucher(user, company,  MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext, applicationContext, ctx);
					if (!email.isOnlyHtmlContent()) {							
						if (agencyemail != null && customeremail != null) {

							emailService.sendBusOrderMailVoucher(true,busOrderRow,busOrderCustomer,user,
									company, MediaType.IMAGE_PNG_VALUE, locale, request, response,
									servletContext, applicationContext,  htmlContent, email);
							if(!customeremail.equalsIgnoreCase("")){
								if (!agencyemail.equalsIgnoreCase(customeremail)) {
									logger.info(
											"########################## agency and customer emails are different ");
									emailService.sendBusOrderMailVoucher(false,busOrderRow,busOrderCustomer,user,
											company, MediaType.IMAGE_PNG_VALUE, locale, request, response,
											servletContext, applicationContext,  htmlContent, email);

								}
							}
							emailNotificationDao.insertEmailNotification(company, company, company, user,
									EmailNotification.ACTION_PARENT_TO_CHILD, email);
						}
					} 
				} else {
					throw new Exception("Company or User not found for booking");
				}
			} else {
				throw new Exception("Bus orderrow not found");
			}
		} else
			throw new Exception("email not found");
		return htmlContent;
	}

	public String sendBusEmail(Email email, HttpServletRequest request, HttpServletResponse response, Locale locale, BusOrderRowEmailDao busOrderRowEmailDao, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService,RmConfigDetailDAO rmConfigDetailDAO)
			throws Exception {
		// TODO Auto-generated method stub
		String htmlContent = null;
		boolean isRmStruct=false;
		if (email != null) {
			BusOrderRow busOrderRow = null;
			BusTravelRequestQuotation busTravelRequestQuotation = null;
			Company company = null;
			User user = null;
			Company superCompany=allEmailDao.getCompanyByCompanyId("1");
			String orderId = email.getOrderId();
			HttpSession session = request.getSession();
			RmConfigTripDetailsModel rmConfigTripDetailsModel = null;
			List<String> fieldNameArray = new ArrayList<String>();
			CompanyEntity companyEntity = null;
			busOrderRow = busOrderRowEmailDao.getBusOrderRowDetailsByOrderId(orderId);
			if (busOrderRow != null) {
				company = allEmailDao.getCompanyByCompanyId(busOrderRow.getCompanyId());				
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

				if(busOrderRow.getCompanyEntityId() != null){ 
					companyEntity = allEmailDao.getCompanyEntityByCompanyId(busOrderRow.getCompanyEntityId());					
				}
				user = allEmailDao.getUserByEmail(company.getEmail());
				rmConfigTripDetailsModel = rmConfigDetailDAO.getRmConfigDetail(busOrderRow.getOrderId());
				RmConfigModel rmConfigModel = rmConfigDetailDAO.getRmConfigModel(company.getCompanyid());
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
				 if(busOrderRow.getBusOrderRowRmConfigStruct()!=null) 
					 isRmStruct=true;
				 
					String dynamicSchema=rmConfigModel!=null?rmConfigModel.getDynamicFieldsData():"";
					if(isRmStruct) 
						dynamicSchema=busOrderRow.getBusOrderRowRmConfigStruct().getRmDynamicData();
					
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
					company.setRegisterNumber(superCompany.getRegisterNumber());
					if (busOrderRow.getOrderCustomer() != null) {
						String firstName=busOrderRow.getOrderCustomer().getFirstName()!=null?busOrderRow.getOrderCustomer().getFirstName():"";
						String lastName=busOrderRow.getOrderCustomer().getLastName()!=null?busOrderRow.getOrderCustomer().getLastName():"";
						session.setAttribute("name",firstName.replaceAll("[2C%-+.^:,]", " ") + " "
										+ lastName.replaceAll("[2C%-+.^:,]", " "));
						company = company.tranformDisplayable();
						String path = user.getImagepath();
						CommonConfig conf = CommonConfig.GetCommonConfig();
						String imagePth = conf.getDefult_image_path();
						if (path != null) {
							imagePth = conf.getImage_path() + path;
						}
						List<BusOrderCustomerDetail> busOrderCustomerDetailList = new ArrayList<>();
						for (BusOrderCustomerDetail busOrderCustomerDetail : busOrderRow.getBusOrderCustomerDetails()) {
							BigDecimal seatPrice = new BigDecimal(0);
							if(busOrderRow.getBusOrderRowServiceTax()!=null){
								seatPrice = busOrderCustomerDetail.getSeatPrice().subtract(busOrderRow.getServiceTax()).setScale(2, RoundingMode.UP);
							}
							if(busOrderRow.getBusOrderRowGstTax()!=null){
								seatPrice = busOrderCustomerDetail.getSeatPrice().subtract(busOrderRow.getBusOrderRowGstTax().getManagementFee()).subtract(busOrderRow.getTotalGstTax()).setScale(2, RoundingMode.UP);
							}
							busOrderCustomerDetail.setSeatPrice(seatPrice);
							busOrderCustomerDetailList.add(busOrderCustomerDetail);
						}
						busOrderRow.setBusOrderCustomerDetails(busOrderCustomerDetailList);
						String agencyemail = user.getEmail();
						String customeremail = busOrderRow.getOrderCustomer().getEmail();
						BigDecimal baseFare = busOrderRow.getBasePrice()!=null?busOrderRow.getBasePrice().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
						baseFare = baseFare.add(busOrderRow.getMarkUp()!=null?busOrderRow.getMarkUp().setScale(2, RoundingMode.UP):new BigDecimal("0.00"));
						busOrderRow.setBasePrice(baseFare);
						BigDecimal totalAmountAfterTax = new BigDecimal("0.00");
						BigDecimal ManagementFee = new BigDecimal("0.00");
						BigDecimal convenienceFee = new BigDecimal("0.00");
						if(busOrderRow.getBusOrderRowServiceTax() != null){
							session.setAttribute("isServiceTax",true);
							session.setAttribute("isGstTax",false);
							BigDecimal BaseServiceTax = new BigDecimal("0.00");
							convenienceFee = busOrderRow.getBusOrderRowServiceTax().getConvenienceFee()!=null?busOrderRow.getBusOrderRowServiceTax().getConvenienceFee().setScale(0, RoundingMode.UP):new BigDecimal("0.00");
							ManagementFee =  busOrderRow.getBusOrderRowServiceTax().getManagementFee()!=null?busOrderRow.getBusOrderRowServiceTax().getManagementFee().setScale(0, RoundingMode.UP):new BigDecimal("0.00");
							BigDecimal totalFare = busOrderRow.getTotalAmount();
							BaseServiceTax = totalFare.setScale(2, RoundingMode.UP)
									.divide(new BigDecimal("100.0")).multiply(busOrderRow.getBusOrderRowServiceTax().getBasicTax() != null?busOrderRow.getBusOrderRowServiceTax().getBasicTax():new BigDecimal(0));
							BigDecimal SBC = new BigDecimal("0.00");
							SBC = totalFare.setScale(2, RoundingMode.UP).divide(new BigDecimal("100.0"))
									.multiply(busOrderRow.getBusOrderRowServiceTax().getSwatchBharathCess() != null?busOrderRow.getBusOrderRowServiceTax().getSwatchBharathCess():new BigDecimal(0));
							BigDecimal KKC = new BigDecimal("0.00");
							KKC = totalFare.setScale(2, RoundingMode.UP).divide(new BigDecimal("100.0"))
									.multiply(busOrderRow.getBusOrderRowServiceTax().getKrishiKalyanCess() != null?busOrderRow.getBusOrderRowServiceTax().getKrishiKalyanCess():new BigDecimal(0));

							totalAmountAfterTax = busOrderRow.getTotalAmount().setScale(0, RoundingMode.UP).add(busOrderRow.getServiceTax().setScale(0, RoundingMode.UP));
							session.setAttribute("BaseServiceTax", BaseServiceTax.setScale(2, RoundingMode.UP));
							session.setAttribute("SBC", SBC.setScale(2, RoundingMode.UP));
							session.setAttribute("KKC", KKC.setScale(2, RoundingMode.UP));
							session.setAttribute("TotalServiceTax", busOrderRow.getServiceTax().setScale(0, RoundingMode.UP));
							session.setAttribute("ConvenienceFee",convenienceFee);
							session.setAttribute("ManagementFee",ManagementFee);
						}
						else if(busOrderRow.getBusOrderRowGstTax() != null){
							session.setAttribute("isServiceTax",false);
							session.setAttribute("isGstTax",true);

							ManagementFee = busOrderRow.getBusOrderRowGstTax().getManagementFee()!=null?busOrderRow.getBusOrderRowGstTax().getManagementFee():new BigDecimal("0.00");
							BigDecimal CGST = new BigDecimal("0.00");
							CGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(busOrderRow.getBusOrderRowGstTax().getCGST()!=null?busOrderRow.getBusOrderRowGstTax().getCGST():new BigDecimal("0.00"));
							BigDecimal SGST = new BigDecimal("0.00");
							SGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(busOrderRow.getBusOrderRowGstTax().getSGST()!=null?busOrderRow.getBusOrderRowGstTax().getSGST():new BigDecimal("0.00"));
							BigDecimal IGST = new BigDecimal("0.00");
							IGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(busOrderRow.getBusOrderRowGstTax().getIGST()!=null?busOrderRow.getBusOrderRowGstTax().getIGST():new BigDecimal("0.00"));
							BigDecimal UGST = new BigDecimal("0.00");
							UGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(busOrderRow.getBusOrderRowGstTax().getUGST()!=null?busOrderRow.getBusOrderRowGstTax().getUGST():new BigDecimal("0.00"));
							BigDecimal TotalGST = new BigDecimal("0.00");
							TotalGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(busOrderRow.getBusOrderRowGstTax().getTotalGst()!=null?busOrderRow.getBusOrderRowGstTax().getTotalGst():new BigDecimal("0.00"));
							convenienceFee = busOrderRow.getBusOrderRowGstTax().getConvenienceFee()!=null?busOrderRow.getBusOrderRowGstTax().getConvenienceFee().setScale(0, RoundingMode.UP):new BigDecimal("0.00");
							totalAmountAfterTax = busOrderRow.getTotalAmount().setScale(0, RoundingMode.UP).add(busOrderRow.getTotalGstTax().setScale(0, RoundingMode.UP));

							session.setAttribute("CGSTPercentage", busOrderRow.getBusOrderRowGstTax().getCGST().setScale(2, RoundingMode.UP));
							session.setAttribute("SGSTPercentage", busOrderRow.getBusOrderRowGstTax().getSGST().setScale(2, RoundingMode.UP));
							session.setAttribute("IGSTPercentage", busOrderRow.getBusOrderRowGstTax().getIGST().setScale(2, RoundingMode.UP));
							session.setAttribute("UGSTPercentage", busOrderRow.getBusOrderRowGstTax().getUGST().setScale(2, RoundingMode.UP));

							session.setAttribute("CGST", CGST.setScale(2, RoundingMode.UP));
							session.setAttribute("SGST", SGST.setScale(2, RoundingMode.UP));
							session.setAttribute("IGST", IGST.setScale(2, RoundingMode.UP));
							session.setAttribute("UGST", UGST.setScale(2, RoundingMode.UP));
							session.setAttribute("TotalGST", TotalGST.setScale(2, RoundingMode.UP));
							session.setAttribute("ConvenienceFee",convenienceFee.setScale(2, RoundingMode.UP));
							session.setAttribute("ManagementFee",ManagementFee.setScale(2, RoundingMode.UP));
						}else{
							session.setAttribute("isServiceTax",false);
							session.setAttribute("isGstTax",false);
							session.setAttribute("ConvenienceFee",new BigDecimal("0.00"));
							session.setAttribute("ManagementFee",new BigDecimal("0.00"));
						}
					
						BigDecimal basewithtax = new BigDecimal("0.0");
						basewithtax = busOrderRow.getBasePrice().add(busOrderRow.getOtherTaxes());				

						session.setAttribute("basewithtax", basewithtax.setScale(2, RoundingMode.UP));
						session.setAttribute("isCorporate", company.getCompanyRole().isCorporate());						
						session.setAttribute("TotalAmount",totalAmountAfterTax);
						session.setAttribute("amountinwords",ArithmeticUtil.DecimalValueCheacker(totalAmountAfterTax.setScale(0, RoundingMode.UP).toString()));
						session.setAttribute("manualfield1",manualField1 );
						session.setAttribute("manualfield2",manualField2 );
						session.setAttribute("manualfield3",manualField3 );
						session.setAttribute("manualfield4",manualField4 );
						session.setAttribute("manualfield5", manualField5);
						Map<String, Object> variables = new HashMap<String, Object>();
						variables.put("busOrderRow",busOrderRow);
						variables.put("rmdetail", rmConfigTripDetailsModel);
						variables.put("isRmStruct",isRmStruct);
						//added by basha
						variables.put("manualrmfield1", manualField1rmconfig);
						variables.put("manualrmfield2", manualField2rmconfig);
						variables.put("manualrmfield3", manualField3rmconfig);
						variables.put("manualrmfield4", manualField4rmconfig);
						variables.put("manualrmfield5", manualField5rmconfig);
						//ended by basha
						//	variables.put("busTravelRequestQuotation",busTravelRequestQuotation);
						variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
						variables.put("user", user);
						variables.put("company", company);
						variables.put("companyEntity", companyEntity);
						variables.put("baseUrl",
								request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
						final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale,
								variables, applicationContext);
						htmlContent = emailContentService.sendBusOrderMailInvoice(false, true, busOrderRow, user,
								company, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
								applicationContext, imagePth, ctx);
						if (!email.isOnlyHtmlContent()) {
							if (email.getType() == Email.EMAIL_TYPE_BUS_INVOICE) {
								if (agencyemail != null && customeremail != null) {
									emailService.sendBusOrderMailInvoice(false, true, busOrderRow, user,
											company, MediaType.IMAGE_PNG_VALUE, locale, request, response,
											servletContext, applicationContext, imagePth, htmlContent, email);
									logger.info("Invoice Email sent to customer for " + customeremail);
									emailNotificationDao.insertEmailNotification(company, company, company, user,
											EmailNotification.ACTION_PARENT_TO_CHILD, email);
								}

								logger.info(
										"EmailController send email call after emailService.sendSimpleMail----  ");

							}
						}

					} else {
						throw new Exception("Company or User not found for booking");
					}
				} else {
					throw new Exception("busOrderRow not found");
				}
			}else
				throw new Exception("email not found");


		}
		return htmlContent;
	}
	public static void sendCreditNoteRequestMailBus(Email email,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, int emailTypeSuperInvoiceToOthers,
			BusOrderRowEmailDao busOrderRowEmailDao, EmailDao emailDao,
			EmailService emailService,
			EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao,
			ApplicationContext applicationContext,
			ServletContext servletContext,
			EmailContentService emailContentService,
			BusCreditNoteDao busCreditNoteDao) throws Exception {

		if (email != null) {
			String creditNoteId = email.getOrderId();
			logger.info("credit note request----- creditNoteId: " + creditNoteId);

			BusCreditNote creditNote = busCreditNoteDao.getCreditNoteById(creditNoteId);
			logger.info("credit note request----- creditNote: " + creditNote);

			if (creditNote != null) {
				BusOrderRow busOrderRow = busOrderRowEmailDao.getBusOrderRowDetailsById(Long.valueOf(creditNote.getRowId()));

				logger.info("credit note request----- busOrderRow: " + busOrderRow);

				if (busOrderRow != null) {
					Company company = null; //
					Company parentCompany = null;
					User user = null;
					BigDecimal totalAmount = new BigDecimal("0.0");
					HttpSession session = request.getSession();
					BigDecimal beforeGstTot = busOrderRow.getTotalAmount();

					BigDecimal totWithGst = busOrderRow.getBasePrice();
					logger.info("totWithGst  :" + totWithGst);
					BigDecimal totalwithtax = busOrderRow.getTaxes().add(totWithGst);
					String invoiceNo = busOrderRow.getInvoiceNo();
					user = allEmailDao.getUserByUserId(creditNote.getUserId());
					if (user != null) {
						user.initLogoDisplayable();
						logger.info("############## credit note request user id =" + user.getUserrole_id()
						+ ", name =" + user.getUsername());
						company = emailDao.getUserCompany(user);

						if (company != null) {
							logger.info("############## credit note requesting company =" + company.toString());
							company.initLogoDisplayable();
							parentCompany = emailDao.getParentCompany(company);
							if (parentCompany != null) {
								logger.info("############## credit note receiving company ="
										+ parentCompany.toString());
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



								logger.info("total base fare" + totalAmount);
								session.setAttribute("totalAmount",
										totalAmount.setScale(2, BigDecimal.ROUND_FLOOR));
								BigDecimal totalgst = new BigDecimal(0);
								totalgst = new BigDecimal(0);
								BigDecimal totalgstinBooking = totalgst
										.multiply(busOrderRow.getBaseToBookingExchangeRate());
								BigDecimal gstOnAirAmount = ArithmeticUtil.divideTo2Decimal(
										totalgst.multiply(new BigDecimal("100.00")), new BigDecimal("6.0"));// totalgst.multiply(new
								// BigDecimal("100.00")).divide(new
								// BigDecimal("6.0"));
								//////////////////
								BigDecimal baseFareinBooking = (busOrderRow.getBasePrice()
										.multiply(busOrderRow.getApiToBaseExchangeRate())
										.add((busOrderRow.getMarkUp() != null ? busOrderRow.getMarkUp()
												: new BigDecimal(0))))
										.multiply(busOrderRow.getBaseToBookingExchangeRate());
								BigDecimal TaxinBooking = (busOrderRow.getTaxes()
										.multiply(busOrderRow.getApiToBaseExchangeRate()))
										.multiply(busOrderRow.getBaseToBookingExchangeRate());
								session.setAttribute("baseFareinBooking",
										baseFareinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("TaxinBooking",
										TaxinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("pnr", busOrderRow.getConfirmationNumber());
								session.setAttribute("totalgst",
										totalgstinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("gstOnAirAmount",
										gstOnAirAmount.multiply(busOrderRow.getBaseToBookingExchangeRate())
										.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("orderId", busOrderRow.getOrderId());
								session.setAttribute("invoiceNo", invoiceNo);
								session.setAttribute("beforeGstTot",
										beforeGstTot.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("totWithGst", totWithGst.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("totalwithtax",totalwithtax.setScale(2, BigDecimal.ROUND_FLOOR));

								session.setAttribute("cancelfee", (creditNote.getCancellationFees() != null
										? creditNote.getCancellationFees().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("convinfee", (creditNote.getConvenienceFees() != null
										? creditNote.getConvenienceFees().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("bookamount",
										(creditNote.getTotalBookingAmount() != null ? creditNote
												.getTotalBookingAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("refundamount",
										(creditNote.getRefundedAmount() != null
										? creditNote.getRefundedAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("gstamount",
										(creditNote.getGstAmount() != null
										? creditNote.getGstAmount().setScale(2, BigDecimal.ROUND_FLOOR)
												: new BigDecimal(0)));
								session.setAttribute("currency", busOrderRow.getBookingCurrency());


								if (company.getCompanyid() != parentCompany.getCompanyid()) {
									emailService.sendCreditNoteRequestBus(busOrderRow, creditNote, user,
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

	public static String  sendCreditNoteIssueMailBus(Email email,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, int emailTypeSuperInvoiceToOthers,
			BusOrderRowEmailDao busOrderRowEmailDao, EmailDao emailDao,
			EmailService emailService,
			EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao,
			ApplicationContext applicationContext,
			ServletContext servletContext,
			EmailContentService emailContentService,
			BusCreditNoteDao busCreditNoteDao,
			RmConfigDetailDAO rmConfigDetailDAO, CompanyDao companyDao) throws Exception {

		String htmlContent = null;
		boolean isRmStruct=false;
		if (email != null) {
			String creditNoteId = email.getOrderId();
			BusCreditNote creditNote = busCreditNoteDao.getCreditNoteById(creditNoteId);
			if (creditNote != null) {
				BusOrderRow busOrderRow = busOrderRowEmailDao.getBusOrderRowDetailsById(Long.valueOf(creditNote.getRowId()));
				BusTravelRequestQuotation busTravelRequestQuotation = null;
				Company bookingCompany = null;
				User user = null;
				CompanyEntity companyEntity=null;
				Company superCompany=allEmailDao.getCompanyByCompanyId("1");
				HttpSession session = request.getSession();
				RmConfigTripDetailsModel rmConfigTripDetailsModel = null;
				List<String> fieldNameArray = new ArrayList<String>();

				//busTravelRequestQuotation = busOrderRowEmailDao.getBusOrderQuotationDetailsById(busOrderRow.getId());
				if (busOrderRow != null) {

					LinkedList<Company> companyListBottomToTop= new LinkedList<>();
					LinkedHashMap<String, BusCreditNote> creditNoteMarkupMap = new LinkedHashMap<>();
					companyListBottomToTop = CommonUtil.getParentCompanyBottomToTop(Integer.parseInt(creditNote.getCompanyId()),companyDao);

					Company parentCompany = null;
					User parentUser = null;
					bookingCompany = allEmailDao.getCompanyByCompanyId(busOrderRow.getCompanyId());
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

					if(busOrderRow.getCompanyEntityId() != null){ 
						companyEntity = allEmailDao.getCompanyEntityByCompanyId(busOrderRow.getCompanyEntityId());					
					}
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
					List<BusCreditNote>   newCreditNoteList= busCreditNoteDao.getCreditNoteListByOrderRowID(busOrderRow.getId());
					BigDecimal totManagementFees=new BigDecimal("0.00");
					BigDecimal totConvenienceFee=new BigDecimal("0.00");
					BigDecimal totCancellationFee=new BigDecimal("0.00");
					BigDecimal totRefundAmt = new BigDecimal("0.00");
					String creditNoteNumber = null;
					Timestamp creditNoteDate= null;
					if(busOrderRow!=null && busOrderRow.isCreditNoteIssued())
					{
						if(creditNote!=null){
							if(newCreditNoteList!=null && newCreditNoteList.size()>0)
							{
								for(BusCreditNote creditNoteInner:newCreditNoteList)
								{
									creditNoteMarkupMap.put(creditNoteInner.getCompanyId(), creditNoteInner);
								}
							} 
						}
						java.util.Iterator<Company> coIterator = companyListBottomToTop.descendingIterator();
						while(coIterator.hasNext())
						{
							Company companyInner = coIterator.next();
							BusCreditNote creditNoteInner =creditNoteMarkupMap.get(String.valueOf(companyInner.getCompanyid()));
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


					user = allEmailDao.getUserByEmail(bookingCompany.getEmail());
					rmConfigTripDetailsModel = rmConfigDetailDAO.getRmConfigDetail(busOrderRow.getOrderId());
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
					 if(busOrderRow.getBusOrderRowRmConfigStruct()!=null) 
						 isRmStruct=true;
					 
						String dynamicSchema=rmConfigModel!=null?rmConfigModel.getDynamicFieldsData():"";
						if(isRmStruct) 
							dynamicSchema=busOrderRow.getBusOrderRowRmConfigStruct().getRmDynamicData();
						
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

					if (bookingCompany != null && user != null) {
						bookingCompany.setRegisterNumber(superCompany.getRegisterNumber());
						if (busOrderRow.getOrderCustomer() != null) {
							session.setAttribute("name",
									busOrderRow.getOrderCustomer().getFirstName().replaceAll("[2C%-+.^:,]", " ") + " "
											+ busOrderRow.getOrderCustomer().getLastName().replaceAll("[2C%-+.^:,]", " "));
							bookingCompany = bookingCompany.tranformDisplayable();
							String path = user.getImagepath();
							CommonConfig conf = CommonConfig.GetCommonConfig();
							String imagePth = conf.getDefult_image_path();
							if (path != null) {
								imagePth = conf.getImage_path() + path;
							}

							String agencyemail = user.getEmail();
							String customeremail = busOrderRow.getOrderCustomer().getEmail();
							BigDecimal baseFare = busOrderRow.getBasePrice()!=null?busOrderRow.getBasePrice().setScale(0, RoundingMode.UP):new BigDecimal("0.00");

							BigDecimal totalMarkupToCompany = new BigDecimal(0);
							Map<String, BigDecimal> orderRowMarkupMap =  new LinkedHashMap<>();
							if(busOrderRow!=null)
							{
								if(bookingCompany.getCompanyRole().isSuperUser())
								{
									orderRowMarkupMap.put(String.valueOf(bookingCompany.getCompanyid()), busOrderRow.getMarkUp());
								}
								else
								{
									Company companyParent= companyDao.getParentCompany(bookingCompany);
									orderRowMarkupMap.put(String.valueOf(companyParent.getCompanyid()), busOrderRow.getMarkUp());
									orderRowMarkupMap.put(String.valueOf(bookingCompany.getCompanyid()), new BigDecimal(0));
								}
							}

							java.util.Iterator<Company> lit = companyListBottomToTop.descendingIterator();
							while(lit.hasNext())
							{
								Company companyInner = lit.next();
								if(String.valueOf(companyInner.getCompanyid()).equals(creditNote.getCompanyId()))
								{
									break;
								}
								else{
									BigDecimal markUpAmount =orderRowMarkupMap.get(String.valueOf(companyInner.getCompanyid()));
									totalMarkupToCompany = totalMarkupToCompany.add(markUpAmount != null ? markUpAmount : new BigDecimal(0));
								}

							}
							List<BusOrderCustomerDetail> busOrderCustomerDetailList = new ArrayList<>();
							for (BusOrderCustomerDetail busOrderCustomerDetail : busOrderRow.getBusOrderCustomerDetails()) {
								BigDecimal seatPrice = new BigDecimal(0);
								if(busOrderRow.getBusOrderRowServiceTax()!=null){
									seatPrice = busOrderCustomerDetail.getSeatPrice().subtract(busOrderRow.getServiceTax()).subtract(busOrderRow.getOtherTaxes()).subtract(busOrderRow.getManagementFee()).setScale(2, RoundingMode.UP);
								}
								if(busOrderRow.getBusOrderRowGstTax()!=null){
									//added by basha
									seatPrice = busOrderCustomerDetail.getSeatPrice().subtract(busOrderRow.getBusOrderRowGstTax().getManagementFee()).subtract(busOrderRow.getTotalGstTax()).setScale(2, RoundingMode.UP);
									//seatPrice = busOrderCustomerDetail.getSeatPrice().subtract(busOrderRow.getTotalGstTax()).subtract(busOrderRow.getOtherTaxes()).subtract(busOrderRow.getManagementFee()).setScale(2, RoundingMode.UP);
								}
								busOrderCustomerDetail.setSeatPrice(seatPrice);
								busOrderCustomerDetailList.add(busOrderCustomerDetail);
							}
							busOrderRow.setBusOrderCustomerDetails(busOrderCustomerDetailList);
							baseFare = baseFare.add(totalMarkupToCompany!=null?totalMarkupToCompany.setScale(2, RoundingMode.UP):new BigDecimal("0.00"));
							busOrderRow.setBasePrice(baseFare);

							BigDecimal convenienceFee = new BigDecimal("0.00");
							BigDecimal ManagementFee =  new BigDecimal("0.00");

							BigDecimal totalFare = busOrderRow.getTotalAmount();
							BigDecimal totalAmountAfterTax = new BigDecimal("0.00");
							if(busOrderRow.getBusOrderRowServiceTax() != null){
								session.setAttribute("isServiceTax",true);
								session.setAttribute("isGstTax",false);
								BigDecimal BaseServiceTax = new BigDecimal("0.00");
								BaseServiceTax = totalFare.setScale(0, RoundingMode.UP)
										.divide(new BigDecimal("100.0")).multiply(busOrderRow.getBusOrderRowServiceTax().getBasicTax() != null?busOrderRow.getBusOrderRowServiceTax().getBasicTax():new BigDecimal(0));
								BigDecimal SBC = new BigDecimal("0.00");
								SBC = totalFare.setScale(0, RoundingMode.UP).divide(new BigDecimal("100.0"))
										.multiply(busOrderRow.getBusOrderRowServiceTax().getSwatchBharathCess() != null?busOrderRow.getBusOrderRowServiceTax().getSwatchBharathCess():new BigDecimal(0));
								BigDecimal KKC = new BigDecimal("0.00");
								KKC = totalFare.setScale(0, RoundingMode.UP).divide(new BigDecimal("100.0"))
										.multiply(busOrderRow.getBusOrderRowServiceTax().getKrishiKalyanCess() != null?busOrderRow.getBusOrderRowServiceTax().getKrishiKalyanCess():new BigDecimal(0));

								totalAmountAfterTax = busOrderRow.getTotalAmount().setScale(0, RoundingMode.UP).add(busOrderRow.getServiceTax().setScale(0, RoundingMode.UP));
								session.setAttribute("BaseServiceTax", BaseServiceTax.setScale(0, RoundingMode.UP));
								session.setAttribute("SBC", SBC.setScale(0, RoundingMode.UP));
								session.setAttribute("KKC", KKC.setScale(0, RoundingMode.UP));
								session.setAttribute("TotalServiceTax", busOrderRow.getServiceTax().setScale(0, RoundingMode.UP));
							}else if(busOrderRow.getBusOrderRowGstTax() != null){
								session.setAttribute("isServiceTax",false);
								session.setAttribute("isGstTax",true);
								BigDecimal CGST = new BigDecimal("0.00");
								BigDecimal SGST = new BigDecimal("0.00");
								BigDecimal IGST = new BigDecimal("0.00");
								BigDecimal UGST = new BigDecimal("0.00");

								session.setAttribute("CGSTPercentage",busOrderRow.getBusOrderRowGstTax().getCGST().setScale(2, RoundingMode.UP));
								session.setAttribute("SGSTPercentage",busOrderRow.getBusOrderRowGstTax().getSGST().setScale(2, RoundingMode.UP));
								session.setAttribute("IGSTPercentage",busOrderRow.getBusOrderRowGstTax().getIGST().setScale(2, RoundingMode.UP));
								session.setAttribute("UGSTPercentage",busOrderRow.getBusOrderRowGstTax().getUGST().setScale(2, RoundingMode.UP));
								session.setAttribute("CGST", CGST.setScale(2, RoundingMode.UP));
								session.setAttribute("SGST", SGST.setScale(2, RoundingMode.UP));
								session.setAttribute("IGST", IGST.setScale(2, RoundingMode.UP));
								session.setAttribute("UGST", UGST.setScale(2, RoundingMode.UP));
								totalAmountAfterTax = busOrderRow.getTotalAmount().setScale(0, RoundingMode.UP).add(busOrderRow.getTotalGstTax().setScale(0, RoundingMode.UP));

							}else{
								session.setAttribute("isServiceTax",false);
								session.setAttribute("isGstTax",false);
							}



							// Email voucher part....

							BigDecimal basewithtax = new BigDecimal("0.0");

							basewithtax = busOrderRow.getBasePrice().add(busOrderRow.getOtherTaxes());								

							session.setAttribute("basewithtax", basewithtax.setScale(2, RoundingMode.UP));
							session.setAttribute("isCorporate", bookingCompany.getCompanyRole().isCorporate());							
							session.setAttribute("ConvenienceFee",convenienceFee.setScale(2, RoundingMode.UP));
							session.setAttribute("ManagementFee",ManagementFee.setScale(2, RoundingMode.UP));
							session.setAttribute("TotalAmount",totRefundAmt);
							session.setAttribute("amountinwords",ArithmeticUtil.DecimalValueCheacker(totRefundAmt.setScale(0, RoundingMode.UP).toString()));
							session.setAttribute("manualfield1",manualField1 );
							session.setAttribute("manualfield2",manualField2 );
							session.setAttribute("manualfield3",manualField3 );
							session.setAttribute("manualfield4",manualField4 );
							session.setAttribute("manualfield5", manualField5);
							Map<String, Object> variables = new HashMap<String, Object>();
							variables.put("busOrderRow",busOrderRow);
							variables.put("rmdetail", rmConfigTripDetailsModel);
							variables.put("isRmStruct",isRmStruct);
							//added by basha
							variables.put("manualrmfield1", manualField1rmconfig);
							variables.put("manualrmfield2", manualField2rmconfig);
							variables.put("manualrmfield3", manualField3rmconfig);
							variables.put("manualrmfield4", manualField4rmconfig);
							variables.put("manualrmfield5", manualField5rmconfig);
							//ended by basha
							//	variables.put("busTravelRequestQuotation",busTravelRequestQuotation);
							variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
							variables.put("user", user);
							variables.put("company", bookingCompany);
							variables.put("companyEntity", companyEntity);
							variables.put("baseUrl",
									request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
							final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale,
									variables, applicationContext);
							htmlContent = emailContentService.sendCreditNoteIssueBus(bookingCompany, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
									applicationContext, ctx);
							if (!email.isOnlyHtmlContent()) {
								emailService.sendCreditNoteIssueBus(false, true, busOrderRow, creditNote, user,
										bookingCompany, parentCompany, locale, request, response, servletContext,
										applicationContext,htmlContent,email);

								emailNotificationDao.insertEmailNotification(parentCompany, parentCompany, bookingCompany,
										user, EmailNotification.ACTION_PARENT_TO_CHILD, email);

								logger.info("############## credit issue note  sent--  ");
							}
							/*} else {
									logger.info(
											"##############   credit note isseued same company  on internal request : "
													+ company.getCompanyname());
								}*/

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
		}
		return htmlContent;


	}


}
class BusCancellationPolicy {
	private String cutoffTime;
	private String refundInPercentage;
	public String getCutoffTime() {
		return cutoffTime;
	}
	public String getRefundInPercentage() {
		return refundInPercentage;
	}
	public void setCutoffTime(String cutoffTime) {
		this.cutoffTime = cutoffTime;
	}
	public void setRefundInPercentage(String refundInPercentage) {
		this.refundInPercentage = refundInPercentage;
	}
}
