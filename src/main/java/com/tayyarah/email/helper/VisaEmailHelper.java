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

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.spring3.context.SpringWebContext;

import com.itextpdf.text.DocumentException;
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
import com.tayyarah.hotel.dao.HotelCreditNoteDao;
import com.tayyarah.hotel.dao.HotelOrderRowEmailDao;
import com.tayyarah.hotel.entity.HotelCreditNote;
import com.tayyarah.hotel.entity.HotelOrderCancellationPolicy;
import com.tayyarah.hotel.entity.HotelOrderGuest;
import com.tayyarah.hotel.entity.HotelOrderRoomInfo;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.quotation.entity.VisaTravelRequestQuotation;
import com.tayyarah.services.EmailContentService;
import com.tayyarah.services.EmailService;
import com.tayyarah.user.entity.User;
import com.tayyarah.visa.dao.VisaCreditNoteDao;
import com.tayyarah.visa.dao.VisaOrderRowEmailDao;
import com.tayyarah.visa.entity.VisaCreditNote;
import com.tayyarah.visa.entity.VisaOrderRow;

@Component
public class VisaEmailHelper {

	public static final Logger logger = Logger.getLogger(VisaEmailHelper.class);

	public String sendVisaInvoiceEmail(Email email, HttpServletRequest request, HttpServletResponse response,
			Locale locale, VisaOrderRowEmailDao visaOrderRowEmailDao, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService,RmConfigDetailDAO rmConfigDetailDAO) throws Exception {

		String htmlContent = null;
		boolean isRmStruct=false;
		boolean  isvisaOrderList=false;
		if (email != null) {
			VisaOrderRow visaOrderRow = null;
			VisaTravelRequestQuotation visaTravelRequestQuotation = null;
			Company company = null;
			CompanyEntity companyEntity=null;
			User user = null;
			Company superCompany=allEmailDao.getCompanyByCompanyId("1");
			String orderId = email.getOrderId();
			HttpSession session = request.getSession();
			RmConfigTripDetailsModel rmConfigTripDetailsModel = null;
			List<String> fieldNameArray = new ArrayList<String>();
			visaOrderRow = visaOrderRowEmailDao.getVisaOrderRowDetailsByOrderId(orderId);
			visaTravelRequestQuotation = visaOrderRowEmailDao.getVisaOrderQuotationDetailsById(visaOrderRow.getId());

			if (visaOrderRow != null && visaTravelRequestQuotation!=null) {
				company = allEmailDao.getCompanyByCompanyId(visaOrderRow.getCompanyId());
				//ADDED BY BASHA FOR TERMS AND CONDITIONS ERROR WHILE GETTING INVOICE
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
				
				if(visaOrderRow.getCompanyEntityId() != null){ 
					companyEntity = allEmailDao.getCompanyEntityByCompanyId(visaOrderRow.getCompanyEntityId());					
				 }
				user = allEmailDao.getUserByEmail(company.getEmail());
				rmConfigTripDetailsModel = rmConfigDetailDAO.getRmConfigDetail(visaOrderRow.getOrderId());
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
				
				if(visaOrderRow.getVisaOrderCustomerList()!=null && visaOrderRow.getVisaOrderCustomerList().size()>0)
					isvisaOrderList=true;
				
				
				if(visaOrderRow.getVisaOrderRowRmConfigStruct()!=null) 
					 isRmStruct=true;
				 
				String dynamicSchema=rmConfigModel!=null?rmConfigModel.getDynamicFieldsData():"";
				if(isRmStruct) 
					dynamicSchema=visaOrderRow.getVisaOrderRowRmConfigStruct().getRmDynamicData();
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
					if (visaOrderRow.getOrderCustomer() != null) {
					String firstName=visaOrderRow.getOrderCustomer().getFirstName()!=null?visaOrderRow.getOrderCustomer().getFirstName():"N/A";
					String lastName=visaOrderRow.getOrderCustomer().getLastName()!=null?visaOrderRow.getOrderCustomer().getLastName():"N/A";
						session.setAttribute("name", firstName.replaceAll("[2C%-+.^:,]", " ") + " " + lastName.replaceAll("[2C%-+.^:,]", " "));
						company = company.tranformDisplayable();
						String path = user.getImagepath();
						CommonConfig conf = CommonConfig.GetCommonConfig();
						String imagePth = conf.getDefult_image_path();
						if (path != null) {
							imagePth = conf.getImage_path() + path;
						}

						String agencyemail = user.getEmail();
						String customeremail = visaOrderRow.getOrderCustomer().getEmail();
						BigDecimal baseFare = visaOrderRow.getBasePrice()!=null?visaOrderRow.getBasePrice().setScale(2, RoundingMode.UP): new BigDecimal("0.00");
						baseFare = baseFare.add(visaOrderRow.getMarkUp()!=null?visaOrderRow.getMarkUp().setScale(2, RoundingMode.UP):new BigDecimal("0.00"));
						visaOrderRow.setBasePrice(baseFare);
						BigDecimal totalAmountAfterTax = new BigDecimal("0.00");
						BigDecimal convenienceFee = visaOrderRow.getConvenienceFee()!=null?visaOrderRow.getConvenienceFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
						BigDecimal ManagementFee =  visaOrderRow.getManagementFee()!=null?visaOrderRow.getManagementFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
						visaOrderRow.setManagementFee(ManagementFee.setScale(2, RoundingMode.UP));
						visaOrderRow.setConvenienceFee(convenienceFee.setScale(2, RoundingMode.UP));
						if(visaOrderRow.getVisaOrderRowServiceTax() != null){
							session.setAttribute("isServiceTax",true);
							session.setAttribute("isGstTax",false);
							BigDecimal BaseServiceTax = new BigDecimal("0.00");
							BaseServiceTax = ManagementFee.setScale(2, RoundingMode.UP).divide(new BigDecimal(100)).multiply(visaOrderRow.getVisaOrderRowServiceTax().getBasicTax() != null?visaOrderRow.getVisaOrderRowServiceTax().getBasicTax():new BigDecimal(0));
							BigDecimal SBC = new BigDecimal("0.00");
							SBC = ManagementFee.setScale(2, RoundingMode.UP).divide(new BigDecimal("100.0"))
									.multiply(visaOrderRow.getVisaOrderRowServiceTax().getSwatchBharathCess() != null?visaOrderRow.getVisaOrderRowServiceTax().getSwatchBharathCess():new BigDecimal(0));
							BigDecimal KKC = new BigDecimal("0.00");
							KKC = ManagementFee.setScale(2, RoundingMode.UP).divide(new BigDecimal("100.0"))
									.multiply(visaOrderRow.getVisaOrderRowServiceTax().getKrishiKalyanCess() != null?visaOrderRow.getVisaOrderRowServiceTax().getKrishiKalyanCess():new BigDecimal(0));
							BigDecimal totalServiceTax = new BigDecimal("0.00");
							totalServiceTax = BaseServiceTax.add(SBC.setScale(2, RoundingMode.UP)).add(KKC.setScale(2, RoundingMode.UP));
						
							totalAmountAfterTax = totalAmountAfterTax.add(visaOrderRow.getTotalAmount()).add(totalServiceTax);
							
							session.setAttribute("BaseServiceTax", BaseServiceTax.setScale(2, RoundingMode.UP));
							session.setAttribute("SBC", SBC.setScale(2, RoundingMode.UP));
							session.setAttribute("KKC", KKC.setScale(2, RoundingMode.UP));
							session.setAttribute("TotalServiceTax", totalServiceTax.setScale(0, RoundingMode.UP));
						}else if(visaOrderRow.getVisaOrderRowGstTax() != null){
							session.setAttribute("isServiceTax",false);
							session.setAttribute("isGstTax",true);
							 ManagementFee = new BigDecimal("0.00");
							ManagementFee = visaOrderRow.getVisaOrderRowGstTax().getManagementFee()!=null?visaOrderRow.getVisaOrderRowGstTax().getManagementFee():new BigDecimal("0.00");
							BigDecimal CGST = new BigDecimal("0.00");
							CGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(visaOrderRow.getVisaOrderRowGstTax().getCGST()!=null?visaOrderRow.getVisaOrderRowGstTax().getCGST():new BigDecimal("0.00"));
							BigDecimal SGST = new BigDecimal("0.00");
							SGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(visaOrderRow.getVisaOrderRowGstTax().getSGST()!=null?visaOrderRow.getVisaOrderRowGstTax().getSGST():new BigDecimal("0.00"));
							BigDecimal IGST = new BigDecimal("0.00");
							IGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(visaOrderRow.getVisaOrderRowGstTax().getIGST()!=null?visaOrderRow.getVisaOrderRowGstTax().getIGST():new BigDecimal("0.00"));
							BigDecimal UGST = new BigDecimal("0.00");
							UGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(visaOrderRow.getVisaOrderRowGstTax().getUGST()!=null?visaOrderRow.getVisaOrderRowGstTax().getUGST():new BigDecimal("0.00"));
							BigDecimal TotalGST = new BigDecimal("0.00");
							TotalGST = ManagementFee.divide(new BigDecimal("100.0")).multiply(visaOrderRow.getVisaOrderRowGstTax().getTotalGst()!=null?visaOrderRow.getVisaOrderRowGstTax().getTotalGst():new BigDecimal("0.00"));
							convenienceFee = visaOrderRow.getVisaOrderRowGstTax().getConvenienceFee()!=null?visaOrderRow.getVisaOrderRowGstTax().getConvenienceFee().setScale(0, RoundingMode.UP):new BigDecimal("0.00");
							totalAmountAfterTax = visaOrderRow.getTotalAmount().setScale(0, RoundingMode.UP).add(visaOrderRow.getTotalGstTax().setScale(0, RoundingMode.UP));
							
							session.setAttribute("CGSTPercentage", visaOrderRow.getVisaOrderRowGstTax().getCGST().setScale(2, RoundingMode.UP));
							session.setAttribute("SGSTPercentage", visaOrderRow.getVisaOrderRowGstTax().getSGST().setScale(2, RoundingMode.UP));
							session.setAttribute("IGSTPercentage", visaOrderRow.getVisaOrderRowGstTax().getIGST().setScale(2, RoundingMode.UP));
							session.setAttribute("UGSTPercentage", visaOrderRow.getVisaOrderRowGstTax().getUGST().setScale(2, RoundingMode.UP));
							
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
						basewithtax = visaOrderRow.getBasePrice().add(visaOrderRow.getCourierCharges()).add(visaOrderRow.getVfsCharges())
								.add(visaOrderRow.getOtherTaxes());								
						
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
						variables.put("visaOrderRow" , visaOrderRow);
						variables.put("rmdetail", rmConfigTripDetailsModel);
						variables.put("isRmStruct",isRmStruct);
						variables.put("isvisaOrderList",isvisaOrderList);
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
						htmlContent = emailContentService.sendVisaOrderMailInvoice(false, true, visaOrderRow, user,
								company, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
								applicationContext, imagePth, ctx);
						if (!email.isOnlyHtmlContent()) {
							if (email.getType() == Email.EMAIL_TYPE_VISA_INVOICE) {
								if (agencyemail != null && customeremail != null) {
									emailService.sendVisaOrderMailInvoice(false, true, visaOrderRow, user,
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
					throw new Exception("visaOrderRow not found");
				}
			} else
				throw new Exception("email not found");
		}
		return htmlContent;

	}
	public static void sendCreditNoteRequestMailHotel(Email email, HttpServletRequest request,
			HttpServletResponse response, Locale locale, int emailTypeSuperInvoiceToOthers, HotelOrderRowEmailDao hotelOrderRowEmailDao, EmailDao emailDao, EmailService emailService, EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao, ApplicationContext applicationContext, ServletContext servletContext, EmailContentService emailContentService, HotelCreditNoteDao hotelCreditNoteDao) throws MessagingException, MailException, NullPointerException,
	UnsupportedEncodingException, DocumentException, IOException, Exception {
		if (email != null) {
			String creditNoteId = email.getOrderId();
			logger.info("credit note request----- creditNoteId: " + creditNoteId);

			HotelCreditNote hotelCreditNote = hotelCreditNoteDao.getHotelCreditNoteByRowId(creditNoteId);

			logger.info("credit note request----- creditNoteId: " + creditNoteId);

			if (hotelCreditNote != null) {
				logger.info("credit note request----- hotelCreditNote: " + hotelCreditNote);

				HotelOrderRow hotelOrderRow = hotelOrderRowEmailDao
						.hotelOrderRowByRowId(String.valueOf(hotelCreditNote.getRowId()));

				logger.info("credit note request----- hotelOrderRow: " + hotelOrderRow);

				if (hotelOrderRow != null) {
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					Company company = null; //
					Company parentCompany = null;//
					User user = null;
					BigDecimal totalAmount = new BigDecimal("0.0");
					HttpSession session = request.getSession();
					String checkIn = formatter.format(hotelOrderRow.getCheckInDate());
					String checkOut = formatter.format(hotelOrderRow.getCheckOutDate());
					DateTime dt1 = new DateTime(checkIn);
					DateTime dt2 = new DateTime(checkOut);
					System.out.print(Days.daysBetween(dt1, dt2).getDays() + " Nights, ");
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
					session.setAttribute("cancelpolicies", cancelpolicies.get(0));
					session.setAttribute("numberOfNights", numberOfNights);

					company = emailDao.getCompanyByCompanyUserId(hotelCreditNote.getCompanyId());
					parentCompany = emailDao.getParentCompany(company);
					user = allEmailDao.getUserByUserId(hotelCreditNote.getUserId());

					user = allEmailDao.getUserByUserId(hotelCreditNote.getUserId());
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

									// emailNotificationDao.insertEmailNotification(parentCompany,
									// parentCompany, company, user,
									// EmailNotification.ACTION_CHILD_TO_PARENT,
									// email);

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

	public static void sendCreditNoteRequestMailVisa(Email email,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, int emailTypeSuperInvoiceToOthers,
			VisaOrderRowEmailDao visaOrderRowEmailDao, EmailDao emailDao,
			EmailService emailService,
			EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao,
			ApplicationContext applicationContext,
			ServletContext servletContext,
			EmailContentService emailContentService,
			VisaCreditNoteDao visaCreditNoteDao) throws Exception {

		if (email != null) {
			String creditNoteId = email.getOrderId();
			logger.info("credit note request----- creditNoteId: " + creditNoteId);

			VisaCreditNote creditNote = visaCreditNoteDao.getCreditNoteById(creditNoteId);
			logger.info("credit note request----- creditNote: " + creditNote);

			if (creditNote != null) {
				VisaOrderRow visaOrderRow = visaOrderRowEmailDao.getVisaOrderRowDetailsById(Long.valueOf(creditNote.getRowId()));

				logger.info("credit note request----- visaOrderRow: " + visaOrderRow);

				if (visaOrderRow != null) {
					Company company = null; //
					Company parentCompany = null;
					User user = null;
					BigDecimal totalAmount = new BigDecimal("0.0");
					HttpSession session = request.getSession();
					BigDecimal beforeGstTot = visaOrderRow.getTotalAmount();

					BigDecimal totWithGst = visaOrderRow.getBasePrice();
					logger.info("totWithGst  :" + totWithGst);
					BigDecimal totalwithtax = visaOrderRow.getTaxes().add(totWithGst);
					String invoiceNo = visaOrderRow.getInvoiceNo();
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
										.multiply(visaOrderRow.getBaseToBookingExchangeRate());
								BigDecimal gstOnAirAmount = ArithmeticUtil.divideTo2Decimal(
										totalgst.multiply(new BigDecimal("100.00")), new BigDecimal("6.0"));// totalgst.multiply(new
								// BigDecimal("100.00")).divide(new
								// BigDecimal("6.0"));
								//////////////////
								BigDecimal baseFareinBooking = (visaOrderRow.getBasePrice()
										.multiply(visaOrderRow.getApiToBaseExchangeRate())
										.add((visaOrderRow.getMarkUp() != null ? visaOrderRow.getMarkUp()
												: new BigDecimal(0))))
										.multiply(visaOrderRow.getBaseToBookingExchangeRate());
								BigDecimal TaxinBooking = (visaOrderRow.getTaxes()
										.multiply(visaOrderRow.getApiToBaseExchangeRate()))
										.multiply(visaOrderRow.getBaseToBookingExchangeRate());
								session.setAttribute("baseFareinBooking",
										baseFareinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("TaxinBooking",
										TaxinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("pnr", visaOrderRow.getConfirmationNumber());
								session.setAttribute("totalgst",
										totalgstinBooking.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("gstOnAirAmount",
										gstOnAirAmount.multiply(visaOrderRow.getBaseToBookingExchangeRate())
										.setScale(2, BigDecimal.ROUND_FLOOR));
								session.setAttribute("orderId", visaOrderRow.getOrderId());
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
								session.setAttribute("currency", visaOrderRow.getBookingCurrency());


								if (company.getCompanyid() != parentCompany.getCompanyid()) {
									emailService.sendCreditNoteRequestVisa(visaOrderRow, creditNote, user,
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

	public static  String  sendCreditNoteIssueMailVisa(Email email,
			HttpServletRequest request, HttpServletResponse response,
			Locale locale, int emailTypeSuperInvoiceToOthers,
			VisaOrderRowEmailDao visaOrderRowEmailDao, EmailDao emailDao,
			EmailService emailService,
			EmailNotificationDao emailNotificationDao, AllEmailDao allEmailDao,
			ApplicationContext applicationContext,
			ServletContext servletContext,
			EmailContentService emailContentService,
			VisaCreditNoteDao visaCreditNoteDao,
			RmConfigDetailDAO rmConfigDetailDAO, CompanyDao companyDao) throws Exception {

		String htmlContent = null;
		boolean isRmStruct=false;
		boolean  isvisaOrderList=false;
		if (email != null) {
			String creditNoteId = email.getOrderId();
			VisaCreditNote creditNote = visaCreditNoteDao.getCreditNoteById(creditNoteId);
			if (creditNote != null) {
				VisaOrderRow visaOrderRow = visaOrderRowEmailDao.getVisaOrderRowDetailsById(Long.valueOf(creditNote.getRowId()));
				VisaTravelRequestQuotation visaTravelRequestQuotation = null;
				Company bookingCompany = null;
				User user = null;
				CompanyEntity companyEntity = null;
				Company superCompany=allEmailDao.getCompanyByCompanyId("1");

				HttpSession session = request.getSession();
				RmConfigTripDetailsModel rmConfigTripDetailsModel = null;
				List<String> fieldNameArray = new ArrayList<String>();
				visaTravelRequestQuotation = visaOrderRowEmailDao.getVisaOrderQuotationDetailsById(visaOrderRow.getId());
				if (visaOrderRow != null && visaTravelRequestQuotation!=null) {

					LinkedList<Company> companyListBottomToTop= new LinkedList<>();
					LinkedHashMap<String, VisaCreditNote> creditNoteMarkupMap = new LinkedHashMap<>();
					companyListBottomToTop = CommonUtil.getParentCompanyBottomToTop(Integer.parseInt(creditNote.getCompanyId()),companyDao);


					Company parentCompany = null;
					User parentUser = null;
					bookingCompany = allEmailDao.getCompanyByCompanyId(visaOrderRow.getCompanyId());
					//ADDED BY BASHA FOR TERMS AND CONDITIONS ERROR WHILE GETTING INVOICE OR CREDITNOTE
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

					if(visaOrderRow.getCompanyEntityId() != null){ 
						companyEntity = allEmailDao.getCompanyEntityByCompanyId(visaOrderRow.getCompanyEntityId());					
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
					List<VisaCreditNote>   newCreditNoteList= visaCreditNoteDao.getCreditNoteListByOrderRowID(visaOrderRow.getId());
					BigDecimal totManagementFees=new BigDecimal("0.00");
					BigDecimal totConvenienceFee=new BigDecimal("0.00");
					BigDecimal totCancellationFee=new BigDecimal("0.00");
					BigDecimal totRefundAmt = new BigDecimal("0.00");
					String creditNoteNumber = null;
					Timestamp creditNoteDate= null;
					if(visaOrderRow!=null && visaOrderRow.isCreditNoteIssued())
					{
						if(creditNote!=null){
							if(newCreditNoteList!=null && newCreditNoteList.size()>0)
							{
								for(VisaCreditNote creditNoteInner:newCreditNoteList)
								{
									creditNoteMarkupMap.put(creditNoteInner.getCompanyId(), creditNoteInner);
								}
							} 
						}
						java.util.Iterator<Company> coIterator = companyListBottomToTop.descendingIterator();
						while(coIterator.hasNext())
						{
							Company companyInner = coIterator.next();
							VisaCreditNote creditNoteInner =creditNoteMarkupMap.get(String.valueOf(companyInner.getCompanyid()));
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
					session.setAttribute("refundAmt", totRefundAmt.setScale(0, RoundingMode.UP));
					session.setAttribute("totManagementFees", totManagementFees.setScale(2, RoundingMode.UP));
					session.setAttribute("totConvenienceFee", totConvenienceFee.setScale(2, RoundingMode.UP));
					session.setAttribute("totCancellationFee", totCancellationFee.setScale(2, RoundingMode.UP));
					session.setAttribute("creditNoteNumber", creditNoteNumber);
					session.setAttribute("creditNoteDate", creditNoteDate);

				
					user = allEmailDao.getUserByEmail(bookingCompany.getEmail());
					rmConfigTripDetailsModel = rmConfigDetailDAO.getRmConfigDetail(visaOrderRow.getOrderId());
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
					if(visaOrderRow.getVisaOrderCustomerList()!=null && visaOrderRow.getVisaOrderCustomerList().size()>0)
						isvisaOrderList=true;
					if(visaOrderRow.getVisaOrderRowRmConfigStruct()!=null) 
						 isRmStruct=true;
					 
					String dynamicSchema=rmConfigModel!=null?rmConfigModel.getDynamicFieldsData():"";
					if(isRmStruct) 
						dynamicSchema=visaOrderRow.getVisaOrderRowRmConfigStruct().getRmDynamicData();
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
						if (visaOrderRow.getOrderCustomer() != null) {
							session.setAttribute("name",
									visaOrderRow.getOrderCustomer().getFirstName().replaceAll("[2C%-+.^:,]", " ") + " "
											+ visaOrderRow.getOrderCustomer().getLastName().replaceAll("[2C%-+.^:,]", " "));
							bookingCompany = bookingCompany.tranformDisplayable();
							String path = user.getImagepath();
							CommonConfig conf = CommonConfig.GetCommonConfig();
							String imagePth = conf.getDefult_image_path();
							if (path != null) {
								imagePth = conf.getImage_path() + path;
							}

							String agencyemail = user.getEmail();
							String customeremail = visaOrderRow.getOrderCustomer().getEmail();
							BigDecimal baseFare = visaOrderRow.getBasePrice()!=null?visaOrderRow.getBasePrice().setScale(0, RoundingMode.UP): new BigDecimal("0.00");
							
							BigDecimal totalMarkupToCompany = new BigDecimal(0);
							Map<String, BigDecimal> orderRowMarkupMap =  new LinkedHashMap<>();
							if(visaOrderRow!=null)
							{
								if(bookingCompany.getCompanyRole().isSuperUser())
								{
									orderRowMarkupMap.put(String.valueOf(bookingCompany.getCompanyid()), visaOrderRow.getMarkUp());
								}
								else
								{
									Company companyParent= companyDao.getParentCompany(bookingCompany);
									orderRowMarkupMap.put(String.valueOf(companyParent.getCompanyid()), visaOrderRow.getMarkUp());
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
							baseFare = baseFare.add(totalMarkupToCompany!=null?totalMarkupToCompany.setScale(2, RoundingMode.UP):new BigDecimal("0.00"));
							visaOrderRow.setBasePrice(baseFare);
							BigDecimal convenienceFee = new BigDecimal("0.00");
							BigDecimal ManagementFee =  new BigDecimal("0.00");
							BigDecimal totalAmountAfterTax = new BigDecimal("0.00");
							
							if(visaOrderRow.getVisaOrderRowServiceTax() != null){
								session.setAttribute("isServiceTax",true);
								session.setAttribute("isGstTax",false);
								BigDecimal BaseServiceTax = new BigDecimal("0.00");
								BaseServiceTax = ManagementFee.setScale(0, RoundingMode.UP).divide(new BigDecimal(100)).multiply(visaOrderRow.getVisaOrderRowServiceTax().getBasicTax() != null?visaOrderRow.getVisaOrderRowServiceTax().getBasicTax():new BigDecimal(0));
								BigDecimal SBC = new BigDecimal("0.00");
								SBC = ManagementFee.setScale(0, RoundingMode.UP).divide(new BigDecimal("100.0"))
										.multiply(visaOrderRow.getVisaOrderRowServiceTax().getSwatchBharathCess() != null?visaOrderRow.getVisaOrderRowServiceTax().getSwatchBharathCess():new BigDecimal(0));
								BigDecimal KKC = new BigDecimal("0.00");
								KKC = ManagementFee.setScale(0, RoundingMode.UP).divide(new BigDecimal("100.0"))
										.multiply(visaOrderRow.getVisaOrderRowServiceTax().getKrishiKalyanCess() != null?visaOrderRow.getVisaOrderRowServiceTax().getKrishiKalyanCess():new BigDecimal(0));
								BigDecimal totalServiceTax = new BigDecimal("0.00");
								totalServiceTax = BaseServiceTax.setScale(2, RoundingMode.UP).add(SBC.setScale(2, RoundingMode.UP)).add(KKC.setScale(2, RoundingMode.UP));
								//commenyted  by basha
								ManagementFee =  visaOrderRow.getManagementFee()!=null?visaOrderRow.getManagementFee().setScale(2, RoundingMode.UP):new BigDecimal("0.00");
								totalAmountAfterTax = totalAmountAfterTax.add(visaOrderRow.getTotalAmount()).add(totalServiceTax);
								session.setAttribute("BaseServiceTax", BaseServiceTax.setScale(2, RoundingMode.UP));
								session.setAttribute("SBC", SBC.setScale(2, RoundingMode.UP));
								session.setAttribute("KKC", KKC.setScale(2, RoundingMode.UP));
								session.setAttribute("TotalServiceTax", totalServiceTax.setScale(2, RoundingMode.UP));
							}else if(visaOrderRow.getVisaOrderRowGstTax() != null){
								session.setAttribute("isServiceTax",false);
								session.setAttribute("isGstTax",true);
								BigDecimal CGST = new BigDecimal("0.00");
								BigDecimal SGST = new BigDecimal("0.00");
								BigDecimal IGST = new BigDecimal("0.00");
								BigDecimal UGST = new BigDecimal("0.00");
								session.setAttribute("CGSTPercentage",visaOrderRow.getVisaOrderRowGstTax().getCGST().setScale(2, RoundingMode.UP));
								session.setAttribute("SGSTPercentage",visaOrderRow.getVisaOrderRowGstTax().getSGST().setScale(2, RoundingMode.UP));
								session.setAttribute("IGSTPercentage",visaOrderRow.getVisaOrderRowGstTax().getIGST().setScale(2, RoundingMode.UP));
								session.setAttribute("UGSTPercentage",visaOrderRow.getVisaOrderRowGstTax().getUGST().setScale(2, RoundingMode.UP));
								session.setAttribute("CGST", CGST.setScale(2, RoundingMode.UP));
								session.setAttribute("SGST", SGST.setScale(2, RoundingMode.UP));
								session.setAttribute("IGST", IGST.setScale(2, RoundingMode.UP));
								session.setAttribute("UGST", UGST.setScale(2, RoundingMode.UP));
								totalAmountAfterTax = visaOrderRow.getTotalAmount().setScale(0, RoundingMode.UP).add(visaOrderRow.getTotalGstTax().setScale(0, RoundingMode.UP));
								
							}else{
								session.setAttribute("isServiceTax",false);
								session.setAttribute("isGstTax",false);
							}
							
							// Email voucher part....

							BigDecimal basewithtax = new BigDecimal("0.0");
							
							//edited by basha
							basewithtax = visaOrderRow.getBasePrice().add(visaOrderRow.getCourierCharges()).add(visaOrderRow.getVfsCharges())
									.add(visaOrderRow.getOtherTaxes());								
							
							session.setAttribute("basewithtax", basewithtax.setScale(2, RoundingMode.UP));
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
							Map<String, Object> variables = new HashMap<String, Object>();
							variables.put("visaOrderRow" , visaOrderRow);
							variables.put("rmdetail", rmConfigTripDetailsModel);
							variables.put("isRmStruct",isRmStruct);
							variables.put("isvisaOrderList",isvisaOrderList);
							
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
							htmlContent = emailContentService.sendCreditNoteIssueVisa(bookingCompany, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext,
									applicationContext, ctx);
							if (!email.isOnlyHtmlContent()) {
								emailService.sendCreditNoteIssueVisa(false, true, visaOrderRow, creditNote, user,
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
