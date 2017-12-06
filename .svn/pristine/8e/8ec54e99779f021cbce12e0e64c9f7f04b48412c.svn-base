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
import com.tayyarah.insurance.dao.InsuranceCreditNoteDao;
import com.tayyarah.insurance.dao.InsuranceOrderRowEmailDao;
import com.tayyarah.insurance.entity.InsuranceCreditNote;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.quotation.entity.InsuranceTravelRequestQuotation;
import com.tayyarah.services.EmailContentService;
import com.tayyarah.services.EmailService;
import com.tayyarah.user.entity.User;

@Component
public class InsuranceEmailHelper {

	public static final Logger logger = Logger.getLogger(InsuranceEmailHelper.class);

	public String sendInsuranceInvoiceEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, InsuranceOrderRowEmailDao insuranceOrderRowEmailDao, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService,RmConfigDetailDAO rmConfigDetailDAO) throws Exception {

		String htmlContent = null;
		boolean isRmStruct=false;
		boolean  isinsuranceOrderList=false;
		
		if (email != null) {
			InsuranceOrderRow insuranceOrderRow = null;
			Company company = null;
			CompanyEntity companyEntity=null;
			User user = null;
			Company superCompany=allEmailDao.getCompanyByCompanyId("1");
			String orderId = email.getOrderId();
			RmConfigTripDetailsModel rmConfigTripDetailsModel = null;
			List<String> fieldNameArray = new ArrayList<String>();
			HttpSession session = request.getSession();
			insuranceOrderRow = insuranceOrderRowEmailDao.getInsuranceOrderRowDetailsByOrderId(orderId);
			if (insuranceOrderRow != null) {
				company = allEmailDao.getCompanyByCompanyId(insuranceOrderRow.getCompanyId());				
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

				if(insuranceOrderRow.getCompanyEntityId() != null){ 
					companyEntity = allEmailDao.getCompanyEntityByCompanyId(insuranceOrderRow.getCompanyEntityId());					
				}
				user = allEmailDao.getUserByEmail(company.getEmail());
				rmConfigTripDetailsModel = rmConfigDetailDAO.getRmConfigDetail(insuranceOrderRow.getOrderId());
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
				
				if(insuranceOrderRow.getInsuranceOrderCustomerDetails()!=null && insuranceOrderRow.getInsuranceOrderCustomerDetails().size()>0) 
					isinsuranceOrderList=true;
				if(insuranceOrderRow.getInsuranceOrderRowRmConfigStruct()!=null) 
					 isRmStruct=true;
				 
				String dynamicSchema=rmConfigModel!=null?rmConfigModel.getDynamicFieldsData():"";
				if(isRmStruct) 
					dynamicSchema=insuranceOrderRow.getInsuranceOrderRowRmConfigStruct().getRmDynamicData();
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
					if (insuranceOrderRow.getOrderCustomer() != null) {
						String firstName=insuranceOrderRow.getOrderCustomer().getFirstName()!=null?insuranceOrderRow.getOrderCustomer().getFirstName():"";
						String lastName=insuranceOrderRow.getOrderCustomer().getLastName()!=null?insuranceOrderRow.getOrderCustomer().getLastName():"";
						session.setAttribute("name",firstName.replaceAll("[2C%-+.^:,]", " ") + " "
										+ lastName.replaceAll("[2C%-+.^:,]", " "));
						company = company.tranformDisplayable();
						String path = user.getImagepath();
						CommonConfig conf = CommonConfig.GetCommonConfig();
						String imagePth = conf.getDefult_image_path();
						if (path != null) {
							imagePth = conf.getImage_path() + path;
						}						
						String customeremail = insuranceOrderRow.getOrderCustomer().getEmail();
						BigDecimal convenienceFee = insuranceOrderRow.getConvenienceFee()!=null?insuranceOrderRow.getConvenienceFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
						BigDecimal ManagementFee =  insuranceOrderRow.getManagementFee()!=null?insuranceOrderRow.getManagementFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
						BigDecimal basePriceWithMarkup = insuranceOrderRow.getBasePrice().add(insuranceOrderRow.getMarkUpamount()!=null?insuranceOrderRow.getMarkUpamount():new BigDecimal(0)).setScale(0, RoundingMode.HALF_UP);
						insuranceOrderRow.setBasePrice(basePriceWithMarkup.setScale(2, RoundingMode.UP));
						insuranceOrderRow.setManagementFee(insuranceOrderRow.getManagementFee().setScale(2, RoundingMode.UP));
						insuranceOrderRow.setConvenienceFee(insuranceOrderRow.getConvenienceFee().setScale(2, RoundingMode.UP));
						BigDecimal totalAmountAfterTax = new BigDecimal("0.00");

						if(insuranceOrderRow.getInsuranceOrderRowServiceTax() != null){
							session.setAttribute("isServiceTax",true);
							session.setAttribute("isGstTax",false);
							totalAmountAfterTax = insuranceOrderRow.getTotalAmount().setScale(0, RoundingMode.UP).add(insuranceOrderRow.getServiceTax().setScale(0, RoundingMode.UP));
							session.setAttribute("TotalServiceTax", insuranceOrderRow.getServiceTax().setScale(0, RoundingMode.UP));
						}
						else if(insuranceOrderRow.getInsuranceOrderRowGstTax() != null){
							session.setAttribute("isServiceTax",false);
							session.setAttribute("isGstTax",true);
							ManagementFee = new BigDecimal("0.00");
							ManagementFee = insuranceOrderRow.getInsuranceOrderRowGstTax().getManagementFee()!=null?insuranceOrderRow.getInsuranceOrderRowGstTax().getManagementFee():new BigDecimal("0.00");
							BigDecimal CGST = new BigDecimal("0.00");
							CGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(insuranceOrderRow.getInsuranceOrderRowGstTax().getCGST()!=null?insuranceOrderRow.getInsuranceOrderRowGstTax().getCGST():new BigDecimal("0.00"));
							BigDecimal SGST = new BigDecimal("0.00");
							SGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(insuranceOrderRow.getInsuranceOrderRowGstTax().getSGST()!=null?insuranceOrderRow.getInsuranceOrderRowGstTax().getSGST():new BigDecimal("0.00"));
							BigDecimal IGST = new BigDecimal("0.00");
							IGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(insuranceOrderRow.getInsuranceOrderRowGstTax().getIGST()!=null?insuranceOrderRow.getInsuranceOrderRowGstTax().getIGST():new BigDecimal("0.00"));
							BigDecimal UGST = new BigDecimal("0.00");
							UGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(insuranceOrderRow.getInsuranceOrderRowGstTax().getUGST()!=null?insuranceOrderRow.getInsuranceOrderRowGstTax().getUGST():new BigDecimal("0.00"));
							BigDecimal TotalGST = new BigDecimal("0.00");
							TotalGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(insuranceOrderRow.getInsuranceOrderRowGstTax().getTotalGst()!=null?insuranceOrderRow.getInsuranceOrderRowGstTax().getTotalGst():new BigDecimal("0.00"));
							convenienceFee =insuranceOrderRow.getInsuranceOrderRowGstTax().getConvenienceFee()!=null?insuranceOrderRow.getInsuranceOrderRowGstTax().getConvenienceFee().setScale(0, RoundingMode.UP):new BigDecimal("0.00");
							totalAmountAfterTax = insuranceOrderRow.getTotalAmount().setScale(0, RoundingMode.UP).add(insuranceOrderRow.getTotalGstTax().setScale(0, RoundingMode.UP));

							session.setAttribute("CGSTPercentage",insuranceOrderRow.getInsuranceOrderRowGstTax().getCGST().setScale(2, RoundingMode.UP));
							session.setAttribute("SGSTPercentage",insuranceOrderRow.getInsuranceOrderRowGstTax().getSGST().setScale(2, RoundingMode.UP));
							session.setAttribute("IGSTPercentage",insuranceOrderRow.getInsuranceOrderRowGstTax().getIGST().setScale(2, RoundingMode.UP));
							session.setAttribute("UGSTPercentage",insuranceOrderRow.getInsuranceOrderRowGstTax().getUGST().setScale(2, RoundingMode.UP));

							session.setAttribute("CGST", CGST.setScale(2, RoundingMode.UP));
							session.setAttribute("SGST", SGST.setScale(2, RoundingMode.UP));
							session.setAttribute("IGST", IGST.setScale(2, RoundingMode.UP));
							session.setAttribute("UGST", UGST.setScale(2, RoundingMode.UP));
							session.setAttribute("TotalGST", TotalGST.setScale(2, RoundingMode.UP));
						}else{
							session.setAttribute("isServiceTax",false);
							session.setAttribute("isGstTax",false);	
						}
						BigDecimal basewithtax = new BigDecimal("0.0");
						basewithtax = insuranceOrderRow.getBasePrice().add(insuranceOrderRow.getOtherTaxes()).add(insuranceOrderRow.getTaxes());								

						session.setAttribute("basewithtax", basewithtax.setScale(2, RoundingMode.UP));	

						session.setAttribute("ConvenienceFee",convenienceFee.setScale(2, RoundingMode.UP));
						session.setAttribute("ManagementFee",ManagementFee.setScale(2, RoundingMode.UP));
						session.setAttribute("isCorporate", company.getCompanyRole().isCorporate());						
						session.setAttribute("TotalAmount",totalAmountAfterTax);
						session.setAttribute("amountinwords",ArithmeticUtil.DecimalValueCheacker(totalAmountAfterTax.setScale(0, RoundingMode.UP).toString()));
						session.setAttribute("manualfield1",manualField1 );
						session.setAttribute("manualfield2",manualField2 );
						session.setAttribute("manualfield3",manualField3 );
						session.setAttribute("manualfield4",manualField4 );
						session.setAttribute("manualfield5", manualField5);
						Map<String, Object> variables = new HashMap<String, Object>();
						variables.put("insuranceOrderRow" , insuranceOrderRow);
						variables.put("rmdetail", rmConfigTripDetailsModel);
						variables.put("isRmStruct",isRmStruct);
						variables.put("isinsuranceOrderList",isinsuranceOrderList);
						//added by basha
						variables.put("manualrmfield1", manualField1rmconfig);
						variables.put("manualrmfield2", manualField2rmconfig);
						variables.put("manualrmfield3", manualField3rmconfig);
						variables.put("manualrmfield4", manualField4rmconfig);
						variables.put("manualrmfield5", manualField5rmconfig);
						//ended by basha
						variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
						variables.put("user", user);
						variables.put("company", company);
						variables.put("companyEntity", companyEntity);
						variables.put("baseUrl",
								request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
						final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale,
								variables, applicationContext);
						htmlContent = emailContentService.sendInsuranceOrderMailInvoice(false, true, insuranceOrderRow, user,
								company, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
								applicationContext, imagePth, ctx);
						if (!email.isOnlyHtmlContent()) {
							if (email.getType() == Email.EMAIL_TYPE_INSURANCE_INVOICE) {

								emailService.sendInsuranceOrderMailInvoice(false, true, insuranceOrderRow, user,
										company, MediaType.IMAGE_PNG_VALUE, locale, request, response,
										servletContext, applicationContext, imagePth, htmlContent, email);
								logger.info("Invoice Email sent to customer for " + customeremail);
								emailNotificationDao.insertEmailNotification(company, company, company, user,
										EmailNotification.ACTION_PARENT_TO_CHILD, email);


								logger.info(
										"EmailController send email call after emailService.sendSimpleMail----  ");

							}
						}

					} else {
						throw new Exception("Company or User not found for booking");
					}
				} else {
					throw new Exception("insuranceOrderRow not found");
				}
			} else
				throw new Exception("email not found");
		}
		return htmlContent;

	}
	public static void sendCreditNoteRequestMailInsurance(Email email,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, int emailTypeSuperInvoiceToOthers,
			InsuranceOrderRowEmailDao insuranceOrderRowEmailDao, EmailDao emailDao,
			EmailService emailService,
			EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao,
			ApplicationContext applicationContext,
			ServletContext servletContext,
			EmailContentService emailContentService,
			InsuranceCreditNoteDao insuranceCreditNoteDao) throws Exception {

		if (email != null) {
			String creditNoteId = email.getOrderId();
			InsuranceCreditNote creditNote = insuranceCreditNoteDao.getCreditNoteById(creditNoteId);
			if (creditNote != null) {
				InsuranceOrderRow insuranceOrderRow = insuranceOrderRowEmailDao.getInsuranceOrderRowDetailsById(Long.valueOf(creditNote.getRowId()));
				if (insuranceOrderRow != null) {
					Company company = null;
					Company parentCompany = null;
					User user = null;
					BigDecimal totalAmount = new BigDecimal("0.0");
					HttpSession session = request.getSession();
					BigDecimal beforeGstTot = insuranceOrderRow.getTotalAmount();
					BigDecimal totWithGst = insuranceOrderRow.getBasePrice();					
					BigDecimal totalwithtax = insuranceOrderRow.getTaxes().add(totWithGst);
					String invoiceNo = insuranceOrderRow.getInvoiceNo();
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
										.multiply(insuranceOrderRow.getBaseToBookingExchangeRate());
								BigDecimal gstOnAirAmount = ArithmeticUtil.divideTo2Decimal(
										totalgst.multiply(new BigDecimal("100.00")), new BigDecimal("6.0"));// totalgst.multiply(new
								
								BigDecimal baseFareinBooking = (insuranceOrderRow.getBasePrice()
										.multiply(insuranceOrderRow.getApiToBaseExchangeRate())
										.add((new BigDecimal(0))))
										.multiply(insuranceOrderRow.getBaseToBookingExchangeRate());
								BigDecimal TaxinBooking = (insuranceOrderRow.getTaxes()
										.multiply(insuranceOrderRow.getApiToBaseExchangeRate()))
										.multiply(insuranceOrderRow.getBaseToBookingExchangeRate());
								session.setAttribute("baseFareinBooking",
										baseFareinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("TaxinBooking",
										TaxinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("pnr", insuranceOrderRow.getConfirmationNumber());
								session.setAttribute("totalgst",
										totalgstinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("gstOnAirAmount",
										gstOnAirAmount.multiply(insuranceOrderRow.getBaseToBookingExchangeRate())
										.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("orderId", insuranceOrderRow.getOrderId());
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
								session.setAttribute("currency", insuranceOrderRow.getBookingCurrency());


								if (company.getCompanyid() != parentCompany.getCompanyid()) {
									emailService.sendCreditNoteRequestInsurance(insuranceOrderRow, creditNote, user,
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

	public static String  sendCreditNoteIssueMailInsurance(Email email,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, int emailTypeSuperInvoiceToOthers,
			InsuranceOrderRowEmailDao insuranceOrderRowEmailDao, EmailDao emailDao,
			EmailService emailService,
			EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao,
			ApplicationContext applicationContext,
			ServletContext servletContext,
			EmailContentService emailContentService,
			InsuranceCreditNoteDao insuranceCreditNoteDao,
			RmConfigDetailDAO rmConfigDetailDAO, CompanyDao companyDao) throws Exception {

		String htmlContent = null;
		boolean isRmStruct=false;
		boolean isinsuranceOrderList=false;
		if (email != null) {
			String creditNoteId = email.getOrderId();
			InsuranceCreditNote creditNote = insuranceCreditNoteDao.getCreditNoteById(creditNoteId);
			if (creditNote != null) {
				InsuranceOrderRow insuranceOrderRow = insuranceOrderRowEmailDao.getInsuranceOrderRowDetailsById(Long.valueOf(creditNote.getRowId()));
				InsuranceTravelRequestQuotation insuranceTravelRequestQuotation = null;
				Company bookingCompany = null;
				User user = null;
				CompanyEntity companyEntity=null;
				Company superCompany=allEmailDao.getCompanyByCompanyId("1");
				HttpSession session = request.getSession();
				RmConfigTripDetailsModel rmConfigTripDetailsModel = null;
				List<String> fieldNameArray = new ArrayList<String>();
				insuranceTravelRequestQuotation = insuranceOrderRowEmailDao.getInsuranceOrderQuotationDetailsById(insuranceOrderRow.getId());
				if (insuranceOrderRow != null && insuranceTravelRequestQuotation!=null) {
					LinkedList<Company> companyListBottomToTop= new LinkedList<>();
					LinkedHashMap<String, InsuranceCreditNote> creditNoteMarkupMap = new LinkedHashMap<>();
					companyListBottomToTop = CommonUtil.getParentCompanyBottomToTop(Integer.parseInt(creditNote.getCompanyId()),companyDao);
					Company parentCompany = null;
					User parentUser = null;
					bookingCompany = allEmailDao.getCompanyByCompanyId(insuranceOrderRow.getCompanyId());					
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

					if(insuranceOrderRow.getCompanyEntityId() != null){ 
						companyEntity = allEmailDao.getCompanyEntityByCompanyId(insuranceOrderRow.getCompanyEntityId());					
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
					List<InsuranceCreditNote>   newCreditNoteList= insuranceCreditNoteDao.getCreditNoteListByOrderRowID(insuranceOrderRow.getId());
					BigDecimal totManagementFees=new BigDecimal("0.00");
					BigDecimal totConvenienceFee=new BigDecimal("0.00");
					BigDecimal totCancellationFee=new BigDecimal("0.00");
					BigDecimal totRefundAmt = new BigDecimal("0.00");
					String creditNoteNumber = null;
					Timestamp creditNoteDate= null;
					if(insuranceOrderRow!=null && insuranceOrderRow.isCreditNoteIssued())
					{
						if(creditNote!=null){
							if(newCreditNoteList!=null && newCreditNoteList.size()>0)
							{
								for(InsuranceCreditNote creditNoteInner:newCreditNoteList)
								{
									creditNoteMarkupMap.put(creditNoteInner.getCompanyId(), creditNoteInner);
								}
							} 
						}
						java.util.Iterator<Company> coIterator = companyListBottomToTop.descendingIterator();
						while(coIterator.hasNext())
						{
							Company companyInner = coIterator.next();
							InsuranceCreditNote creditNoteInner =creditNoteMarkupMap.get(String.valueOf(companyInner.getCompanyid()));
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
					rmConfigTripDetailsModel = rmConfigDetailDAO.getRmConfigDetail(insuranceOrderRow.getOrderId());
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
					
					if(insuranceOrderRow.getInsuranceOrderCustomerDetails()!=null &&  insuranceOrderRow.getInsuranceOrderCustomerDetails().size()>0) 
						isinsuranceOrderList=true;
					if(insuranceOrderRow.getInsuranceOrderRowRmConfigStruct()!=null) 
						 isRmStruct=true;
					 
					String dynamicSchema=rmConfigModel!=null?rmConfigModel.getDynamicFieldsData():"";
					if(isRmStruct) 
						dynamicSchema=insuranceOrderRow.getInsuranceOrderRowRmConfigStruct().getRmDynamicData();
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
						if (insuranceOrderRow.getOrderCustomer() != null) {
							session.setAttribute("name",
									insuranceOrderRow.getOrderCustomer().getFirstName().replaceAll("[2C%-+.^:,]", " ") + " "
											+ insuranceOrderRow.getOrderCustomer().getLastName().replaceAll("[2C%-+.^:,]", " "));
							bookingCompany = bookingCompany.tranformDisplayable();
							String path = user.getImagepath();
							CommonConfig conf = CommonConfig.GetCommonConfig();
							String imagePth = conf.getDefult_image_path();
							if (path != null) {
								imagePth = conf.getImage_path() + path;
							}						
							String customeremail = insuranceOrderRow.getOrderCustomer().getEmail();
							BigDecimal convenienceFee = new BigDecimal("0.00");
							BigDecimal ManagementFee =  new BigDecimal("0.00");
							BigDecimal baseFare = insuranceOrderRow.getBasePrice().setScale(0, RoundingMode.HALF_UP);

							BigDecimal totalMarkupToCompany = new BigDecimal(0);
							Map<String, BigDecimal> orderRowMarkupMap =  new LinkedHashMap<>();
							if(insuranceOrderRow!=null)
							{
								if(bookingCompany.getCompanyRole().isSuperUser())
								{
									orderRowMarkupMap.put(String.valueOf(bookingCompany.getCompanyid()), insuranceOrderRow.getMarkUpamount());
								}
								else
								{
									Company companyParent= companyDao.getParentCompany(bookingCompany);
									orderRowMarkupMap.put(String.valueOf(companyParent.getCompanyid()), insuranceOrderRow.getMarkUpamount());
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
							insuranceOrderRow.setBasePrice(baseFare);
							BigDecimal totalAmountAfterTax = new BigDecimal("0.00");
							
							if(insuranceOrderRow.getInsuranceOrderRowServiceTax() != null){
								session.setAttribute("isServiceTax",true);
								session.setAttribute("isGstTax",false);
									
								ManagementFee =  insuranceOrderRow.getManagementFee()!=null?insuranceOrderRow.getManagementFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
								totalAmountAfterTax = insuranceOrderRow.getTotalAmount().setScale(0, RoundingMode.UP).add(insuranceOrderRow.getServiceTax().setScale(0, RoundingMode.UP));
								session.setAttribute("TotalServiceTax", insuranceOrderRow.getServiceTax().setScale(2, RoundingMode.UP));
							}else if(insuranceOrderRow.getInsuranceOrderRowGstTax() != null){
								session.setAttribute("isServiceTax",false);
								session.setAttribute("isGstTax",true);
								BigDecimal CGST = new BigDecimal("0.00");
								BigDecimal SGST = new BigDecimal("0.00");
								BigDecimal IGST = new BigDecimal("0.00");
								BigDecimal UGST = new BigDecimal("0.00");
								//added by basha
							    session.setAttribute("CGSTPercentage",insuranceOrderRow.getInsuranceOrderRowGstTax().getCGST().setScale(2, RoundingMode.UP));
								session.setAttribute("SGSTPercentage",insuranceOrderRow.getInsuranceOrderRowGstTax().getSGST().setScale(2, RoundingMode.UP));
								session.setAttribute("IGSTPercentage",insuranceOrderRow.getInsuranceOrderRowGstTax().getIGST().setScale(2, RoundingMode.UP));
								session.setAttribute("UGSTPercentage",insuranceOrderRow.getInsuranceOrderRowGstTax().getUGST().setScale(2, RoundingMode.UP));
								session.setAttribute("CGST", CGST.setScale(2, RoundingMode.UP));
								session.setAttribute("SGST", SGST.setScale(2, RoundingMode.UP));
								session.setAttribute("IGST", IGST.setScale(2, RoundingMode.UP));
								session.setAttribute("UGST", UGST.setScale(2, RoundingMode.UP));
								totalAmountAfterTax = insuranceOrderRow.getTotalAmount().setScale(0, RoundingMode.UP).add(insuranceOrderRow.getTotalGstTax().setScale(0, RoundingMode.UP));
								
								
							}else{
								session.setAttribute("isServiceTax",false);
								session.setAttribute("isGstTax",false);
							}
							
							BigDecimal basewithtax = new BigDecimal("0.0");
							
							//edited by basha
							basewithtax = insuranceOrderRow.getBasePrice().add(insuranceOrderRow.getOtherTaxes());
							
							session.setAttribute("basewithtax", basewithtax.setScale(2, RoundingMode.UP));				
							session.setAttribute("basePrice",insuranceOrderRow.getBasePrice().setScale(2, RoundingMode.UP));
							session.setAttribute("otherTaxes",insuranceOrderRow.getOtherTaxes().setScale(2, RoundingMode.UP));
							session.setAttribute("ConvenienceFee",convenienceFee.setScale(2, RoundingMode.UP));
							session.setAttribute("ManagementFee",ManagementFee.setScale(2, RoundingMode.UP));
							session.setAttribute("isCorporate", bookingCompany.getCompanyRole().isCorporate());
							session.setAttribute("TotalAmount",totalAmountAfterTax.setScale(2, RoundingMode.UP));
							session.setAttribute("amountinwords",ArithmeticUtil.DecimalValueCheacker(totRefundAmt.setScale(0, RoundingMode.UP).toString()));
							session.setAttribute("manualfield1",manualField1 );
							session.setAttribute("manualfield2",manualField2 );
							session.setAttribute("manualfield3",manualField3 );
							session.setAttribute("manualfield4",manualField4 );
							session.setAttribute("manualfield5", manualField5);
							Map<String, Object> variables = new HashMap<String, Object>();
							variables.put("insuranceOrderRow" , insuranceOrderRow);
							variables.put("rmdetail", rmConfigTripDetailsModel);
							variables.put("isRmStruct",isRmStruct);
							variables.put("isinsuranceOrderList",isinsuranceOrderList);
							
							//added by basha
							variables.put("manualrmfield1", manualField1rmconfig);
							variables.put("manualrmfield2", manualField2rmconfig);
							variables.put("manualrmfield3", manualField3rmconfig);
							variables.put("manualrmfield4", manualField4rmconfig);
							variables.put("manualrmfield5", manualField5rmconfig);
							//ended by basha
							variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
							variables.put("user", user);
							variables.put("company", bookingCompany);
							variables.put("companyEntity", companyEntity);
							variables.put("baseUrl",
									request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
							final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale,
									variables, applicationContext);
							htmlContent = emailContentService.sendCreditNoteIssueInsurance(bookingCompany, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
									applicationContext, ctx);
							if (!email.isOnlyHtmlContent()) {
								emailService.sendCreditNoteIssueInsurance(false, true, insuranceOrderRow, creditNote, user,
										bookingCompany, parentCompany, locale, request, response, servletContext,
										applicationContext,htmlContent,email);

								emailNotificationDao.insertEmailNotification(parentCompany, parentCompany, bookingCompany,
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
		}
		return htmlContent;
	}
}