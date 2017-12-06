package com.tayyarah.email.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.spring3.context.SpringWebContext;

import com.tayyarah.common.dao.RmConfigDetailDAO;
import com.tayyarah.common.entity.RmConfigModel;
import com.tayyarah.common.entity.RmConfigTripDetailsModel;
import com.tayyarah.common.entity.StateInfo;
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
import com.tayyarah.quotation.entity.TrainTravelRequestQuotation;
import com.tayyarah.services.EmailContentService;
import com.tayyarah.services.EmailService;
import com.tayyarah.train.dao.TrainCreditNoteDao;
import com.tayyarah.train.dao.TrainOrderRowEmailDao;
import com.tayyarah.train.entity.TrainCreditNote;
import com.tayyarah.train.entity.TrainOrderRow;
import com.tayyarah.user.entity.User;

@Component
public class TrainEmailHelper {

	public static final Logger logger = Logger.getLogger(TrainEmailHelper.class);
	public  String sendTrainInvoiceEmail(Email email, HttpServletRequest request, HttpServletResponse response, Locale locale, TrainOrderRowEmailDao trainOrderRowEmailDao, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService,RmConfigDetailDAO rmConfigDetailDAO)
			throws Exception {

		String htmlContent = null;
		boolean isRmStruct=false;
		boolean istrainOrderList=false;
		if (email != null) {
			TrainOrderRow trainOrderRow = null;
			TrainTravelRequestQuotation trainTravelRequestQuotation = null;
			Company company = null;
			CompanyEntity companyEntity=null;
			User user = null;
			Company superCompany=allEmailDao.getCompanyByCompanyId("1");
			String orderId = email.getOrderId();
			HttpSession session = request.getSession();
			RmConfigTripDetailsModel rmConfigTripDetailsModel = null;
			List<String> fieldNameArray = new ArrayList<String>();
			trainOrderRow = trainOrderRowEmailDao.getTrainOrderRowDetailsByOrderId(orderId);
			trainTravelRequestQuotation = trainOrderRowEmailDao.getTrainOrderQuotationDetailsById(trainOrderRow.getId());

			if (trainOrderRow != null) {
				company = allEmailDao.getCompanyByCompanyId(trainOrderRow.getCompanyId());
				//ADDED BY  BASHA FOR TERMS AND CONDITIONS ERROR WHILE GETTING INVOICE
				Company superUserObj=allEmailDao.getSuperCompanyByCompanyUserId(company.getSuper_company_userid());
				if(company.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()==null){
					company.setTemsandcondtions("NA");
						superUserObj.setTemsandcondtions("NA");
					}else if(company.getTemsandcondtions()==null && superUserObj.getTemsandcondtions()!=null){
						company.setTemsandcondtions(superUserObj.getTemsandcondtions());
					}else if(company.getTemsandcondtions()==null){
						company.setTemsandcondtions("NA");
					}
				user = allEmailDao.getUserByEmail(company.getEmail());

				StateInfo stateInfo = allEmailDao.getStateInfo(company.getBillingstate());	 
				if(stateInfo !=null)
					company.setStateCode(stateInfo.getStateCode());

				if(trainOrderRow.getCompanyEntityId() != null){ 
					companyEntity = allEmailDao.getCompanyEntityByCompanyId(trainOrderRow.getCompanyEntityId());					
				}
				rmConfigTripDetailsModel = rmConfigDetailDAO.getRmConfigDetail(trainOrderRow.getOrderId());
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
				if(trainOrderRow.getTrainOrderCustomerList()!=null && trainOrderRow.getTrainOrderCustomerList().size()>0) 
					 istrainOrderList =true;
				if(trainOrderRow.getTrainOrderRowRmConfigStruct()!=null) 
					 isRmStruct=true;
				 
				String dynamicSchema=rmConfigModel!=null?rmConfigModel.getDynamicFieldsData():"";
				if(isRmStruct) 
					dynamicSchema=trainOrderRow.getTrainOrderRowRmConfigStruct().getRmDynamicData();
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
					if (trainOrderRow.getOrderCustomer() != null) {
						String firstName=trainOrderRow.getOrderCustomer().getFirstName()!=null?trainOrderRow.getOrderCustomer().getFirstName():"";
						String lastName=trainOrderRow.getOrderCustomer().getLastName()!=null?trainOrderRow.getOrderCustomer().getLastName():"";
						session.setAttribute("name",firstName.replaceAll("[2C%-+.^:,]", " ") + " "
										+ lastName.replaceAll("[2C%-+.^:,]", " "));
						company = company.tranformDisplayable();
						String path = user.getImagepath();
						CommonConfig conf = CommonConfig.GetCommonConfig();
						String imagePth = conf.getDefult_image_path();
						if (path != null) {
							imagePth = conf.getImage_path() + path;
						}

						String agencyemail = user.getEmail();
						String customeremail = trainOrderRow.getOrderCustomer().getEmail();
						BigDecimal baseFare = trainOrderRow.getBasePrice()!=null?trainOrderRow.getBasePrice().setScale(0, RoundingMode.UP):new BigDecimal("0");
						baseFare = baseFare.add(trainOrderRow.getMarkUp()!=null?trainOrderRow.getMarkUp().setScale(0, RoundingMode.UP):new BigDecimal("0"));
						trainOrderRow.setBasePrice(baseFare.setScale(2, RoundingMode.UP));
						BigDecimal BaseServiceTax = new BigDecimal("0.00");
						BigDecimal ManagementFee = new BigDecimal("0.00");
						BigDecimal convenienceFee = new BigDecimal("0.00");

						BigDecimal totalAmountAfterTax = new BigDecimal("0.00");
						if(trainOrderRow.getTrainOrderRowServiceTax() != null){
							session.setAttribute("isServiceTax",true);
							session.setAttribute("isGstTax",false);
							if(trainOrderRow.getTicketType().trim().equalsIgnoreCase("normal")){
								ManagementFee =  trainOrderRow.getTrainOrderRowServiceTax().getManagementFee()!=null?trainOrderRow.getTrainOrderRowServiceTax().getManagementFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
							}
							if(trainOrderRow.getTicketType().trim().equalsIgnoreCase("tatkal")){
								ManagementFee =  trainOrderRow.getTrainOrderRowServiceTax().getManagementFeeTatkal()!=null?trainOrderRow.getTrainOrderRowServiceTax().getManagementFeeTatkal().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
							}
							convenienceFee = trainOrderRow.getTrainOrderRowServiceTax().getConvenienceFee()!=null?trainOrderRow.getTrainOrderRowServiceTax().getConvenienceFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
							BaseServiceTax = ManagementFee.setScale(2, RoundingMode.UP).divide(new BigDecimal(100)).multiply(trainOrderRow.getTrainOrderRowServiceTax().getBasicTax() != null?trainOrderRow.getTrainOrderRowServiceTax().getBasicTax():new BigDecimal(0));
							BigDecimal SBC = new BigDecimal("0.00");
							SBC = ManagementFee.setScale(2, RoundingMode.UP).divide(new BigDecimal("100.0"))
									.multiply(trainOrderRow.getTrainOrderRowServiceTax().getSwatchBharathCess() != null?trainOrderRow.getTrainOrderRowServiceTax().getSwatchBharathCess():new BigDecimal(0));
							BigDecimal KKC = new BigDecimal("0.00");
							KKC = ManagementFee.setScale(2, RoundingMode.UP).divide(new BigDecimal("100.0"))
									.multiply(trainOrderRow.getTrainOrderRowServiceTax().getKrishiKalyanCess() != null?trainOrderRow.getTrainOrderRowServiceTax().getKrishiKalyanCess():new BigDecimal(0));
							BigDecimal totalServiceTax = new BigDecimal("0.00");
							totalServiceTax = ManagementFee.setScale(0, RoundingMode.UP).divide(new BigDecimal("100.0"))
									.multiply(trainOrderRow.getTrainOrderRowServiceTax().getTotalTax() != null?trainOrderRow.getTrainOrderRowServiceTax().getTotalTax():new BigDecimal(0));

							totalAmountAfterTax = trainOrderRow.getTotalAmount().add(totalServiceTax).setScale(0, RoundingMode.UP);
							session.setAttribute("BaseServiceTax", BaseServiceTax.setScale(2, RoundingMode.UP));
							session.setAttribute("SBC", SBC.setScale(2, RoundingMode.UP));
							session.setAttribute("KKC", KKC.setScale(2, RoundingMode.UP));
							session.setAttribute("TotalServiceTax", totalServiceTax.setScale(0, RoundingMode.UP));
						}
						else if(trainOrderRow.getTrainOrderRowGstTax() != null){
							session.setAttribute("isServiceTax",false);
							session.setAttribute("isGstTax",true);
							if(trainOrderRow.getTicketType().trim().equalsIgnoreCase("normal")){
								ManagementFee =  trainOrderRow.getTrainOrderRowGstTax().getManagementFee()!=null?trainOrderRow.getTrainOrderRowGstTax().getManagementFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
							}
							if(trainOrderRow.getTicketType().trim().equalsIgnoreCase("tatkal")){
								ManagementFee =  trainOrderRow.getTrainOrderRowGstTax().getManagementFeeTatkal()!=null?trainOrderRow.getTrainOrderRowGstTax().getManagementFeeTatkal().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
							}

							BigDecimal CGST = new BigDecimal("0.00");
							CGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(trainOrderRow.getTrainOrderRowGstTax().getCGST()!=null?trainOrderRow.getTrainOrderRowGstTax().getCGST():new BigDecimal("0.00"));
							BigDecimal SGST = new BigDecimal("0.00");
							SGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(trainOrderRow.getTrainOrderRowGstTax().getSGST()!=null?trainOrderRow.getTrainOrderRowGstTax().getSGST():new BigDecimal("0.00"));
							BigDecimal IGST = new BigDecimal("0.00");
							IGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(trainOrderRow.getTrainOrderRowGstTax().getIGST()!=null?trainOrderRow.getTrainOrderRowGstTax().getIGST():new BigDecimal("0.00"));
							BigDecimal UGST = new BigDecimal("0.00");
							UGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(trainOrderRow.getTrainOrderRowGstTax().getUGST()!=null?trainOrderRow.getTrainOrderRowGstTax().getUGST():new BigDecimal("0.00"));
							BigDecimal TotalGST = new BigDecimal("0.00");
							TotalGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(trainOrderRow.getTrainOrderRowGstTax().getTotalGst()!=null?trainOrderRow.getTrainOrderRowGstTax().getTotalGst():new BigDecimal("0.00"));
							convenienceFee =trainOrderRow.getTrainOrderRowGstTax().getConvenienceFee()!=null?trainOrderRow.getTrainOrderRowGstTax().getConvenienceFee().setScale(0, RoundingMode.UP):new BigDecimal("0.00");
							totalAmountAfterTax = trainOrderRow.getTotalAmount().setScale(0, RoundingMode.UP).add(trainOrderRow.getTotalGstTax().setScale(0, RoundingMode.UP));

							session.setAttribute("CGSTPercentage",trainOrderRow.getTrainOrderRowGstTax().getCGST().setScale(2, RoundingMode.UP));
							session.setAttribute("SGSTPercentage",trainOrderRow.getTrainOrderRowGstTax().getSGST().setScale(2, RoundingMode.UP));
							session.setAttribute("IGSTPercentage",trainOrderRow.getTrainOrderRowGstTax().getIGST().setScale(2, RoundingMode.UP));
							session.setAttribute("UGSTPercentage",trainOrderRow.getTrainOrderRowGstTax().getUGST().setScale(2, RoundingMode.UP));

							session.setAttribute("CGST", CGST.setScale(2, RoundingMode.UP));
							session.setAttribute("SGST", SGST.setScale(2, RoundingMode.UP));
							session.setAttribute("IGST", IGST.setScale(2, RoundingMode.UP));
							session.setAttribute("UGST", UGST.setScale(2, RoundingMode.UP));
							session.setAttribute("TotalGST", TotalGST.setScale(2, RoundingMode.UP));
						}else{
							session.setAttribute("isServiceTax",false);
							session.setAttribute("isGstTax",false);		
						}
						trainOrderRow.setManagementFee(ManagementFee.setScale(2, RoundingMode.UP));
						trainOrderRow.setConvenienceFee(convenienceFee.setScale(2, RoundingMode.UP));

						BigDecimal basewithtax = new BigDecimal("0.0");
						basewithtax = trainOrderRow.getBasePrice().add(trainOrderRow.getOtherTaxes());								

						session.setAttribute("basewithtax", basewithtax.setScale(2, RoundingMode.UP));				
						session.setAttribute("isCorporate", company.getCompanyRole().isCorporate());
						session.setAttribute("ConvenienceFee",convenienceFee.setScale(2, RoundingMode.UP));
						session.setAttribute("ManagementFee",ManagementFee.setScale(2, RoundingMode.UP));
						session.setAttribute("TotalAmount",totalAmountAfterTax.setScale(0, RoundingMode.UP));
						session.setAttribute("amountinwords",ArithmeticUtil.DecimalValueCheacker(totalAmountAfterTax.setScale(0, RoundingMode.UP).toString()));
						session.setAttribute("manualfield1",manualField1 );
						session.setAttribute("manualfield2",manualField2 );
						session.setAttribute("manualfield3",manualField3 );
						session.setAttribute("manualfield4",manualField4 );
						session.setAttribute("manualfield5", manualField5);
						Map<String, Object> variables = new HashMap<String, Object>();
						variables.put("trainOrderRow" , trainOrderRow);
						variables.put("rmdetail", rmConfigTripDetailsModel);
						variables.put("isRmStruct",isRmStruct);
						variables.put("istrainOrderList",istrainOrderList);
						//added by basha
						variables.put("manualrmfield1", manualField1rmconfig);
						variables.put("manualrmfield2", manualField2rmconfig);
						variables.put("manualrmfield3", manualField3rmconfig);
						variables.put("manualrmfield4", manualField4rmconfig);
						variables.put("manualrmfield5", manualField5rmconfig);
						//ended by basha
						variables.put("trainTravelRequestQuotation" , trainTravelRequestQuotation);
						variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
						variables.put("user", user);
						variables.put("company", company);
						variables.put("companyEntity", companyEntity);
						variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());


						final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale,
								variables, applicationContext);
						htmlContent = emailContentService.sendTrainOrderMailInvoice(false, true, trainOrderRow, user,
								company, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
								applicationContext, imagePth, ctx);
						if (!email.isOnlyHtmlContent()) {
							if (email.getType() == Email.EMAIL_TYPE_TRAIN_INVOICE) {
								if (agencyemail != null && customeremail != null) {
									emailService.sendTrainOrderMailInvoice(false, true, trainOrderRow, user,
											company, MediaType.IMAGE_PNG_VALUE, locale, request, response,
											servletContext, applicationContext, imagePth, htmlContent, email);
									logger.info("Invoice Email sent to   for " + customeremail);
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
					throw new Exception("trainOrderRow not found");
				}
			} 
		}
		return htmlContent;

	}

	public static void sendCreditNoteRequestMailTrain(Email email,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, int emailTypeSuperInvoiceToOthers,
			TrainOrderRowEmailDao trainOrderRowEmailDao, EmailDao emailDao,
			EmailService emailService,
			EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao,
			ApplicationContext applicationContext,
			ServletContext servletContext,
			TrainCreditNoteDao trainCreditNoteDao,
			EmailContentService emailContentService) throws Exception {
		
		if (email != null) {
			String creditNoteId = email.getOrderId();
			TrainCreditNote creditNote = trainCreditNoteDao.getCreditNoteById(creditNoteId);	

			if (creditNote != null) {
				TrainOrderRow trainOrderRow = trainOrderRowEmailDao.getTrainOrderRowDetailsById(Long.valueOf(creditNote.getRowId()));
				if (trainOrderRow != null) {
					Company company = null;
					Company parentCompany = null;
					User user = null;
					BigDecimal totalAmount = new BigDecimal("0.0");
					HttpSession session = request.getSession();
					BigDecimal beforeGstTot = trainOrderRow.getTotalAmount();
					BigDecimal totWithGst = trainOrderRow.getBasePrice();
					
					BigDecimal totalwithtax = trainOrderRow.getTaxes().add(totWithGst);
					String invoiceNo = trainOrderRow.getInvoiceNo();
					user = allEmailDao.getUserByUserId(creditNote.getUserId());
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

								session.setAttribute("totalAmount",
										totalAmount.setScale(2, BigDecimal.ROUND_FLOOR));
								BigDecimal totalgst = new BigDecimal(0);
								totalgst = new BigDecimal(0);
								BigDecimal totalgstinBooking = totalgst
										.multiply(trainOrderRow.getBaseToBookingExchangeRate());
								BigDecimal gstOnAirAmount = ArithmeticUtil.divideTo2Decimal(
										totalgst.multiply(new BigDecimal("100.00")), new BigDecimal("6.0"));// totalgst.multiply(new
								
								BigDecimal baseFareinBooking = (trainOrderRow.getBasePrice()
										.multiply(trainOrderRow.getApiToBaseExchangeRate())
										.add((trainOrderRow.getMarkUp() != null ? trainOrderRow.getMarkUp()
												: new BigDecimal(0))))
										.multiply(trainOrderRow.getBaseToBookingExchangeRate());
								BigDecimal TaxinBooking = (trainOrderRow.getTaxes()
										.multiply(trainOrderRow.getApiToBaseExchangeRate()))
										.multiply(trainOrderRow.getBaseToBookingExchangeRate());
								session.setAttribute("baseFareinBooking",
										baseFareinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("TaxinBooking",
										TaxinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("pnr", trainOrderRow.getConfirmationNumber());
								session.setAttribute("totalgst",
										totalgstinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("gstOnAirAmount",
										gstOnAirAmount.multiply(trainOrderRow.getBaseToBookingExchangeRate())
										.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("orderId", trainOrderRow.getOrderId());
								session.setAttribute("invoiceNo", invoiceNo);
								session.setAttribute("beforeGstTot",
										beforeGstTot.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("totWithGst", totWithGst.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("totalwithtax",
										totalwithtax.setScale(2, BigDecimal.ROUND_FLOOR));

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
								session.setAttribute("currency", trainOrderRow.getBookingCurrency());


								if (company.getCompanyid() != parentCompany.getCompanyid()) {
									emailService.sendCreditNoteRequestTrain(trainOrderRow, creditNote, user,
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
					throw new Exception("############## credit note request Trainorder not found for credit note");
				}
			} else {
				throw new Exception("############## credit note request Creditnote not found for credit note");
			}
		}
	}

	public static  String  sendCreditNoteIssueMailTrain(Email email,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, int emailTypeSuperInvoiceToOthers,
			TrainOrderRowEmailDao trainOrderRowEmailDao, EmailDao emailDao,
			EmailService emailService,
			EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao,
			ApplicationContext applicationContext,
			ServletContext servletContext,
			EmailContentService emailContentService,
			TrainCreditNoteDao trainCreditNoteDao,
			RmConfigDetailDAO rmConfigDetailDAO, CompanyDao companyDao) throws Exception {

		String htmlContent = null;
		boolean isRmStruct=false;
		boolean istrainOrderList=false;
		if (email != null) {
			String creditNoteId = email.getOrderId();
			TrainCreditNote creditNote = trainCreditNoteDao.getCreditNoteById(creditNoteId);
			if (creditNote != null) {
				TrainOrderRow trainOrderRow = trainOrderRowEmailDao.getTrainOrderRowDetailsById(Long.valueOf(creditNote.getRowId()));
				TrainTravelRequestQuotation trainTravelRequestQuotation = null;
				Company bookingCompany = null;
				User user = null;
				CompanyEntity companyEntity = null;
				Company superCompany=allEmailDao.getCompanyByCompanyId("1");
				HttpSession session = request.getSession();


				RmConfigTripDetailsModel rmConfigTripDetailsModel = null;
				List<String> fieldNameArray = new ArrayList<String>();

				//trainTravelRequestQuotation = trainOrderRowEmailDao.getTrainOrderQuotationDetailsById(trainOrderRow.getId());
				if (trainOrderRow != null) {
					LinkedList<Company> companyListBottomToTop= new LinkedList<>();

					LinkedHashMap<String, TrainCreditNote> creditNoteMarkupMap = new LinkedHashMap<>();
					companyListBottomToTop = CommonUtil.getParentCompanyBottomToTop(Integer.parseInt(creditNote.getCompanyId()),companyDao);


					Company parentCompany = null;
					User parentUser = null;
					bookingCompany = allEmailDao.getCompanyByCompanyId(trainOrderRow.getCompanyId());
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

					if(trainOrderRow.getCompanyEntityId() != null){ 
						companyEntity = allEmailDao.getCompanyEntityByCompanyId(trainOrderRow.getCompanyEntityId());					
					}
					parentCompany = emailDao.getParentCompany(bookingCompany);
					parentCompany.initLogoDisplayable();
					if (parentCompany != null) {
						parentUser = emailDao.getCompanyWalletUser(parentCompany);
						if (parentUser != null) {
							parentCompany.initLogoDisplayable();
							parentUser.initLogoDisplayable();
						}
					}
					List<TrainCreditNote>  newCreditNoteList = trainCreditNoteDao.getCreditNoteListByOrderRowID(trainOrderRow.getId());

					BigDecimal totManagementFees=new BigDecimal("0.00");
					BigDecimal totConvenienceFee=new BigDecimal("0.00");
					BigDecimal totCancellationFee=new BigDecimal("0.00");
					BigDecimal totRefundAmt = new BigDecimal("0.00");
					String creditNoteNumber = null;
					Timestamp creditNoteDate= null;
					if(trainOrderRow!=null && trainOrderRow.isCreditNoteIssued())
					{
						if(creditNote!=null){
							if(newCreditNoteList!=null && newCreditNoteList.size()>0)
							{
								for(TrainCreditNote creditNoteInner:newCreditNoteList)
								{
									creditNoteMarkupMap.put(creditNoteInner.getCompanyId(), creditNoteInner);
								}
							} 
						}
						java.util.Iterator<Company> coIterator = companyListBottomToTop.descendingIterator();
						while(coIterator.hasNext())
						{
							Company companyInner = coIterator.next();
							TrainCreditNote creditNoteInner =creditNoteMarkupMap.get(String.valueOf(companyInner.getCompanyid()));
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
					session.setAttribute("basePrice", trainOrderRow.getBasePrice().setScale(2, RoundingMode.UP));
					session.setAttribute("otherTaxes", trainOrderRow.getOtherTaxes().setScale(2, RoundingMode.UP));
					session.setAttribute("refundAmt", totRefundAmt.setScale(0, RoundingMode.UP));
					session.setAttribute("totManagementFees", totManagementFees.setScale(2, RoundingMode.UP));
					session.setAttribute("totConvenienceFee", totConvenienceFee.setScale(2, RoundingMode.UP));
					session.setAttribute("totCancellationFee", totCancellationFee.setScale(2, RoundingMode.UP));
					session.setAttribute("creditNoteNumber", creditNoteNumber);
					session.setAttribute("creditNoteDate", creditNoteDate);

					trainTravelRequestQuotation = trainOrderRowEmailDao.getTrainOrderQuotationDetailsById(trainOrderRow.getId());

					if (trainOrderRow != null && trainTravelRequestQuotation!=null) {
						bookingCompany = allEmailDao.getCompanyByCompanyId(trainOrderRow.getCompanyId());
						stateInfo = allEmailDao.getStateInfo(bookingCompany.getBillingstate());	 
						if(stateInfo !=null)
							bookingCompany.setStateCode(stateInfo.getStateCode());

						user = allEmailDao.getUserByEmail(bookingCompany.getEmail());
						rmConfigTripDetailsModel = rmConfigDetailDAO.getRmConfigDetail(trainOrderRow.getOrderId());
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
						if(trainOrderRow.getTrainOrderCustomerList()!=null && trainOrderRow.getTrainOrderCustomerList().size()>0) 
							 istrainOrderList =true;
						if(trainOrderRow.getTrainOrderRowRmConfigStruct()!=null) 
							 isRmStruct=true;
						 
						String dynamicSchema=rmConfigModel!=null?rmConfigModel.getDynamicFieldsData():"";
						if(isRmStruct) 
							dynamicSchema=trainOrderRow.getTrainOrderRowRmConfigStruct().getRmDynamicData();
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
							if (trainOrderRow.getOrderCustomer() != null) {
								session.setAttribute("name",
										trainOrderRow.getOrderCustomer().getFirstName().replaceAll("[2C%-+.^:,]", " ") + " "
												+ trainOrderRow.getOrderCustomer().getLastName().replaceAll("[2C%-+.^:,]",
														" "));
								bookingCompany = bookingCompany.tranformDisplayable();
								String path = user.getImagepath();
								CommonConfig conf = CommonConfig.GetCommonConfig();
								String imagePth = conf.getDefult_image_path();
								if (path != null) {
									imagePth = conf.getImage_path() + path;
								}

								String agencyemail = user.getEmail();
								String customeremail = trainOrderRow.getOrderCustomer().getEmail();
								BigDecimal baseFare = trainOrderRow.getBasePrice()!=null?trainOrderRow.getBasePrice().setScale(0, RoundingMode.UP):new BigDecimal("0");

								BigDecimal totalMarkupToCompany = new BigDecimal(0);
								Map<String, BigDecimal> orderRowMarkupMap =  new LinkedHashMap<>();
								if(trainOrderRow!=null)
								{
									if(bookingCompany.getCompanyRole().isSuperUser())
									{
										orderRowMarkupMap.put(String.valueOf(bookingCompany.getCompanyid()), trainOrderRow.getMarkUp());
									}
									else
									{
										Company companyParent= companyDao.getParentCompany(bookingCompany);
										orderRowMarkupMap.put(String.valueOf(companyParent.getCompanyid()), trainOrderRow.getMarkUp());
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
								baseFare = baseFare.add(totalMarkupToCompany!=null?totalMarkupToCompany.setScale(0, RoundingMode.UP):new BigDecimal("0.00"));
								baseFare = baseFare.setScale(2, RoundingMode.UP);
								trainOrderRow.setBasePrice(baseFare);
								BigDecimal BaseServiceTax = new BigDecimal("0.00");
								BigDecimal ManagementFee = new BigDecimal("0.00");
								BigDecimal convenienceFee = new BigDecimal("0.00");
                                BigDecimal totalAmountAfterTax = new BigDecimal("0.00");
                             
								if(trainOrderRow.getTrainOrderRowServiceTax() != null){
									session.setAttribute("isServiceTax",true);
									session.setAttribute("isGstTax",false);									
								
								/*	if(trainOrderRow.getTicketType().trim().equalsIgnoreCase("normal")){
										ManagementFee =  trainOrderRow.getTrainOrderRowServiceTax().getManagementFee()!=null?trainOrderRow.getTrainOrderRowServiceTax().getManagementFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
									}
									if(trainOrderRow.getTicketType().trim().equalsIgnoreCase("tatkal")){
										ManagementFee =  trainOrderRow.getTrainOrderRowServiceTax().getManagementFeeTatkal()!=null?trainOrderRow.getTrainOrderRowServiceTax().getManagementFeeTatkal().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
									}*/
									convenienceFee = trainOrderRow.getTrainOrderRowServiceTax().getConvenienceFee()!=null?trainOrderRow.getTrainOrderRowServiceTax().getConvenienceFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
									
										
									BaseServiceTax = ManagementFee.setScale(0, RoundingMode.UP).divide(new BigDecimal(100)).multiply(trainOrderRow.getTrainOrderRowServiceTax().getBasicTax() != null?trainOrderRow.getTrainOrderRowServiceTax().getBasicTax():new BigDecimal(0));
									BigDecimal SBC = new BigDecimal("0.00");
									SBC = ManagementFee.setScale(0, RoundingMode.UP).divide(new BigDecimal("100.0"))
											.multiply(trainOrderRow.getTrainOrderRowServiceTax().getSwatchBharathCess() != null?trainOrderRow.getTrainOrderRowServiceTax().getSwatchBharathCess():new BigDecimal(0));
									BigDecimal KKC = new BigDecimal("0.00");
									KKC = ManagementFee.setScale(0, RoundingMode.UP).divide(new BigDecimal("100.0"))
											.multiply(trainOrderRow.getTrainOrderRowServiceTax().getKrishiKalyanCess() != null?trainOrderRow.getTrainOrderRowServiceTax().getKrishiKalyanCess():new BigDecimal(0));
									BigDecimal totalServiceTax = new BigDecimal("0.00");
									totalServiceTax = ManagementFee.setScale(0, RoundingMode.UP).divide(new BigDecimal("100.0"))
											.multiply(trainOrderRow.getTrainOrderRowServiceTax().getTotalTax() != null?trainOrderRow.getTrainOrderRowServiceTax().getTotalTax():new BigDecimal(0));

									totalAmountAfterTax = trainOrderRow.getTotalAmount().add(totalServiceTax).setScale(0, RoundingMode.UP);
									session.setAttribute("BaseServiceTax", BaseServiceTax.setScale(2, RoundingMode.UP));
									session.setAttribute("SBC", SBC.setScale(2, RoundingMode.UP));
									session.setAttribute("KKC", KKC.setScale(2, RoundingMode.UP));
									session.setAttribute("TotalServiceTax", totalServiceTax.setScale(2, RoundingMode.UP));

								}else if(trainOrderRow.getTrainOrderRowGstTax() != null){
									session.setAttribute("isServiceTax",false);
									session.setAttribute("isGstTax",true);
									BigDecimal CGST = new BigDecimal("0.00");
									BigDecimal SGST = new BigDecimal("0.00");
									BigDecimal IGST = new BigDecimal("0.00");
									BigDecimal UGST = new BigDecimal("0.00");
									/*//added by basha
									ManagementFee=totManagementFees;
									//added by basha
									if(trainOrderRow.getTicketType().trim().equalsIgnoreCase("normal")){
										gstOrServiceTaxManagementfee =  trainOrderRow.getTrainOrderRowGstTax().getManagementFee()!=null?trainOrderRow.getTrainOrderRowGstTax().getManagementFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
									}
									if(trainOrderRow.getTicketType().trim().equalsIgnoreCase("tatkal")){
										gstOrServiceTaxManagementfee =  trainOrderRow.getTrainOrderRowGstTax().getManagementFeeTatkal()!=null?trainOrderRow.getTrainOrderRowGstTax().getManagementFeeTatkal().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
									}*/
									
									session.setAttribute("CGSTPercentage",trainOrderRow.getTrainOrderRowGstTax().getCGST().setScale(2, RoundingMode.UP));
									session.setAttribute("SGSTPercentage",trainOrderRow.getTrainOrderRowGstTax().getSGST().setScale(2, RoundingMode.UP));
									session.setAttribute("IGSTPercentage",trainOrderRow.getTrainOrderRowGstTax().getIGST().setScale(2, RoundingMode.UP));
									session.setAttribute("UGSTPercentage",trainOrderRow.getTrainOrderRowGstTax().getUGST().setScale(2, RoundingMode.UP));
									session.setAttribute("CGST", CGST.setScale(2, RoundingMode.UP));
									session.setAttribute("SGST", SGST.setScale(2, RoundingMode.UP));
									session.setAttribute("IGST", IGST.setScale(2, RoundingMode.UP));
									session.setAttribute("UGST", UGST.setScale(2, RoundingMode.UP));
									totalAmountAfterTax = trainOrderRow.getTotalAmount().setScale(0, RoundingMode.UP).add(trainOrderRow.getTotalGstTax().setScale(0, RoundingMode.UP));

								}else{
									session.setAttribute("isServiceTax",false);
									session.setAttribute("isGstTax",false);
								}


								// Email voucher part....
								BigDecimal basewithtax = new BigDecimal("0.0");
								//added by basha
								BigDecimal basewithtaxManagementFee = new BigDecimal("0.0");
								
								/*if(trainOrderRow.getTicketType().equalsIgnoreCase("tatkal"))
									basewithtax = trainOrderRow.getBasePrice().add(trainOrderRow.getOtherTaxes().add(trainOrderRow.getManagementFeeTatkal().add(trainOrderRow.getTotalGstTax())));
								else
									basewithtax = trainOrderRow.getBasePrice().add(trainOrderRow.getOtherTaxes().add(trainOrderRow.getManagementFee()).add(trainOrderRow.getTotalGstTax()));
								*/
								//edited by basha
								basewithtax = trainOrderRow.getBasePrice().add(trainOrderRow.getOtherTaxes());	
									
								
								session.setAttribute("basewithtax", basewithtax.setScale(2, RoundingMode.UP));	
								Map<String, Object> variables = new HashMap<String, Object>();
								session.setAttribute("isCorporate", bookingCompany.getCompanyRole().isCorporate());

								session.setAttribute("ConvenienceFee",convenienceFee.setScale(2, RoundingMode.UP));
								session.setAttribute("ManagementFee",ManagementFee.setScale(2, RoundingMode.UP));
								session.setAttribute("TotalAmount",totalAmountAfterTax.setScale(0, RoundingMode.UP));
								session.setAttribute("amountinwords",ArithmeticUtil.DecimalValueCheacker(totRefundAmt.setScale(0, RoundingMode.UP).toString()));
								session.setAttribute("manualfield1",manualField1 );
								session.setAttribute("manualfield2",manualField2 );
								session.setAttribute("manualfield3",manualField3 );
								session.setAttribute("manualfield4",manualField4 );
								session.setAttribute("manualfield5", manualField5);
								variables.put("trainOrderRow" , trainOrderRow);
								variables.put("rmdetail", rmConfigTripDetailsModel);
								variables.put("isRmStruct",isRmStruct);
								variables.put("istrainOrderList",istrainOrderList);
								//added by basha
								variables.put("manualrmfield1", manualField1rmconfig);
								variables.put("manualrmfield2", manualField2rmconfig);
								variables.put("manualrmfield3", manualField3rmconfig);
								variables.put("manualrmfield4", manualField4rmconfig);
								variables.put("manualrmfield5", manualField5rmconfig);
								//ended by basha
								variables.put("trainTravelRequestQuotation" , trainTravelRequestQuotation);
								variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
								variables.put("user", user);
								variables.put("company", bookingCompany);
								variables.put("companyEntity", companyEntity);
								variables.put("baseUrl",request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());


								final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale,
										variables, applicationContext);
								htmlContent = emailContentService.sendTrainOrderMailInvoice(false, true, trainOrderRow, user,
										bookingCompany, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
										applicationContext, imagePth, ctx);

								htmlContent = emailContentService.sendCreditNoteIssueTrain(bookingCompany, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
										applicationContext, ctx);
								if (!email.isOnlyHtmlContent()) {
									emailService.sendCreditNoteIssueTrain(false, true, trainOrderRow, creditNote, user,
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
					throw new Exception("############## credit note request Trainorder not found for credit note");
				}
			}
		}
		return htmlContent;


	}

}
