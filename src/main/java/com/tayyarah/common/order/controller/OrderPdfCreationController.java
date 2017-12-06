package com.tayyarah.common.order.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;*/
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.spring3.context.SpringWebContext;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.tayyarah.bus.dao.BusCreditNoteDao;
import com.tayyarah.bus.dao.BusOrderRowEmailDao;
import com.tayyarah.bus.entity.BusOrderCustomerDetail;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.car.dao.CarCreditNoteDao;
import com.tayyarah.car.dao.CarOrderRowEmailDao;
import com.tayyarah.common.dao.RmConfigDetailDAO;
import com.tayyarah.common.entity.ApiProviderPaymentTransactionDetail;
import com.tayyarah.common.entity.PaymentTransactionDetail;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.ArithmeticUtil;
import com.tayyarah.common.util.CommonUtil;
import com.tayyarah.common.util.TravelCode;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.dao.AllEmailDao;
import com.tayyarah.email.dao.EmailDao;
import com.tayyarah.email.dao.EmailNotificationDao;
import com.tayyarah.email.dao.FlightOrderRowEmailDao;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.email.helper.BusEmailHelper;
import com.tayyarah.email.helper.CarEmailHelper;
import com.tayyarah.email.helper.FlightEmailHelper;
import com.tayyarah.email.helper.HotelEmailHelper;
import com.tayyarah.email.helper.InsuranceEmailHelper;
import com.tayyarah.email.helper.MiscellaneousEmailHelper;
import com.tayyarah.email.helper.TrainEmailHelper;
import com.tayyarah.email.helper.VisaEmailHelper;
import com.tayyarah.flight.dao.FlightCreditNoteDao;
import com.tayyarah.flight.entity.FlightOrderCustomerPriceBreakup;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.entity.FlightOrderTripDetail;
import com.tayyarah.flight.model.FlightInvoiceData;
import com.tayyarah.flight.quotation.dao.FlightTravelRequestDao;
import com.tayyarah.flight.quotation.entity.FlightTravelRequestQuotation;
import com.tayyarah.hotel.dao.HotelCreditNoteDao;
import com.tayyarah.hotel.dao.HotelOrderRowEmailDao;
import com.tayyarah.hotel.dao.HotelSearchRoomDetailDao;
import com.tayyarah.hotel.entity.HotelOrderCancellationPolicy;
import com.tayyarah.hotel.entity.HotelOrderGuest;
import com.tayyarah.hotel.entity.HotelOrderRoomInfo;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.entity.HotelSearchRoomDetailTemp;
import com.tayyarah.hotel.quotation.dao.HotelOfflineBookingDao;
import com.tayyarah.hotel.quotation.dao.HotelTravelRequestDao;
import com.tayyarah.hotel.quotation.entity.HotelTravelRequestQuotation;
import com.tayyarah.insurance.dao.InsuranceCreditNoteDao;
import com.tayyarah.insurance.dao.InsuranceOrderRowEmailDao;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.misellaneous.dao.MiscellaneousCreditNoteDao;
import com.tayyarah.misellaneous.dao.MiscellaneousOrderRowEmailDao;
import com.tayyarah.services.EmailContentService;
import com.tayyarah.services.EmailService;
import com.tayyarah.services.PdfService;
import com.tayyarah.train.dao.TrainCreditNoteDao;
import com.tayyarah.train.dao.TrainOrderRowEmailDao;
import com.tayyarah.user.entity.User;
import com.tayyarah.visa.dao.VisaCreditNoteDao;
import com.tayyarah.visa.dao.VisaOrderRowEmailDao;

@Controller
@RequestMapping(value = "/getpdf")
public class OrderPdfCreationController {
	static final Logger logger = Logger.getLogger(OrderPdfCreationController.class);
	@Autowired
	FlightOrderRowEmailDao flightOrderRowEmailDao;
	@Autowired
	BusOrderRowEmailDao busOrderRowEmailDao;
	@Autowired
	CarOrderRowEmailDao carOrderRowEmailDao;
	@Autowired
	TrainOrderRowEmailDao trainOrderRowEmailDao;
	@Autowired
	VisaOrderRowEmailDao visaOrderRowEmailDao;
	@Autowired
	InsuranceOrderRowEmailDao insuranceOrderRowEmailDao;
	@Autowired
	AllEmailDao allEmailDao;
	@Autowired
	EmailDao emailDao;
	@Autowired
	FlightInvoiceData pricebreakup;
	@Autowired
	private PdfService pdfService;
	@Autowired
	ServletContext servletContext;
	@Autowired
	ApplicationContext applicationContext;
	@Autowired
	private EmailContentService emailContentService;
	@Autowired
	FlightInvoiceData flightInvoiceData;
	@Autowired
	HotelOrderRowEmailDao hotelOrderId;
	@Autowired
	HotelTravelRequestDao hotelTravelRequestDao;
	@Autowired
	FlightTravelRequestDao flightTravelRequestDao;
	@Autowired
	HotelOfflineBookingDao hotelOfflineBookingDao;
	@Autowired
	HotelSearchRoomDetailDao hotelSearchRoomDetailDao;
	@Autowired
	RmConfigDetailDAO rmConfigDetailDAO;
	@Autowired
	private EmailService emailService;
	@Autowired
	EmailNotificationDao emailNotificationDao;
	@Autowired
	FlightCreditNoteDao flightCreditNoteDao;
	@Autowired
	HotelCreditNoteDao hotelCreditNoteDao;
	@Autowired
	CarCreditNoteDao carCreditNoteDao;
	@Autowired
	TrainCreditNoteDao trainCreditNoteDao;
	@Autowired
	BusCreditNoteDao busCreditNoteDao;
	@Autowired
	InsuranceCreditNoteDao insuranceCreditNoteDao;
	@Autowired
	VisaCreditNoteDao visaCreditNoteDao;
	@Autowired
	CompanyDao companyDao; 
	@Autowired
	HotelOrderRowEmailDao hotelOrderRowEmailDao;
	@Autowired
	FlightEmailHelper flightEmailHelper;
	@Autowired
	HotelEmailHelper hotelEmailHelper;
	@Autowired
	CarEmailHelper carEmailHelper;
	@Autowired
	BusEmailHelper busEmailHelper;
	@Autowired
	TrainEmailHelper trainEmailHelper;
	@Autowired
	VisaEmailHelper visaEmailHelper;
	@Autowired
	InsuranceEmailHelper insuranceEmailHelper;	
	@Autowired
	MiscellaneousEmailHelper miscellaneousEmailHelper;
	@Autowired
	MiscellaneousOrderRowEmailDao miscellaneousOrderRowEmailDao;	
	@Autowired
	MiscellaneousCreditNoteDao miscellaneousCreditNoteDao;

	Date date = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	private long startTime;
	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody void getpdf(
			@RequestParam(value = "orderids", defaultValue = "FTBO160921225649954") String orderids,
			@RequestParam(value = "userid", defaultValue = "1") Integer userid,
			@RequestParam(value = "type", defaultValue = "1") Integer type, HttpServletRequest request,
			HttpServletResponse response) {
		startTime = System.currentTimeMillis();
		File pdf = null;
		try {
			final Locale locale = LocaleContextHolder.getLocale();
			logger.info(CommonUtil.getElapsedTime(startTime) + "seconds ... locale-----------:" + locale);
			pdf = getPdfFile(request, response, locale, orderids, type, userid);
			logger.info(CommonUtil.getElapsedTime(startTime) + "seconds ... flightpdf-----------:" + pdf);
			if (pdf != null)
				writePdfOnOutputStream(request, response, pdf);
			else
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (Exception e) {		
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	public File getPdfFile(HttpServletRequest request, HttpServletResponse response, Locale locale, String orderids,
			Integer type, Integer userId) throws NullPointerException, DocumentException, Exception {
		File flightpdf = null;
		switch (type) {
		case 3:
			flightpdf = GenerateHotelQuoatationPdf(false, orderids, userId, request, response, locale);
			break;
		case 4:
			flightpdf = GenerateHotelVoucherPdf(false, orderids, userId, request, response, locale);
			break;
		default:
			flightpdf = GenerateHotelVoucherPdf(false, orderids, request, response, locale);
			break;
		}
		return flightpdf;
	}

	public void writePdfOnOutputStream(HttpServletRequest request, HttpServletResponse response, File pdf)
			throws IOException {
		FileInputStream inputStream = new FileInputStream(pdf);
		// get MIME type of the file
		// set content attributes for the response
		response.setContentType("application/pdf");
		response.setContentLength((int) pdf.length());
		// set headers for the response
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", pdf.getName());
		response.setHeader(headerKey, headerValue);
		// get output stream of the response
		OutputStream outStream = response.getOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		logger.info(CommonUtil.getElapsedTime(startTime) + "seconds ... file write to outputstream over-----------:");
		inputStream.close();
		outStream.close();
	}

	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	public @ResponseBody void sample(
			@RequestParam(value = "orderids", defaultValue = "FTBO160921225649954") String orderids,
			HttpServletRequest request, HttpServletResponse response) {		
		try {
			final Locale locale = LocaleContextHolder.getLocale();			
			File flightpdf = GenerateFlightVoucherPdf(false, orderids, request, response, locale);
			FileInputStream inputStream = new FileInputStream(flightpdf);
			// get MIME type of the file
			// set content attributes for the response
			response.setContentType("application/pdf");
			response.setContentLength((int) flightpdf.length());
			// set headers for the response
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", flightpdf.getName());
			response.setHeader(headerKey, headerValue);
			// get output stream of the response
			OutputStream outStream = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			// write bytes read from the input stream into the output stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			inputStream.close();
			outStream.close();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/transactionsPdf", method = RequestMethod.GET)
	public @ResponseBody void generateSupplierOrCustometPaymentTransactionsPdf(
			@RequestParam(value = "orderids", defaultValue = "FTBO160921225649954") String orderids,
			@RequestParam(value = "type", defaultValue = "Customer") String type, HttpServletRequest request,
			HttpServletResponse response) {
		startTime = System.currentTimeMillis();
		try {
			final Locale locale = LocaleContextHolder.getLocale();			
			File flightpdf = downloadSupplierOrCustometPaymentTransactionsPdf(type, orderids, request, response,
					locale);			
			FileInputStream inputStream = new FileInputStream(flightpdf);
			response.setContentType("application/pdf");
			response.setContentLength((int) flightpdf.length());		
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", flightpdf.getName());
			response.setHeader(headerKey, headerValue);			
			OutputStream outStream = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;			
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}			
			inputStream.close();
			outStream.close();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}	

	@RequestMapping(value = "/flight", method = RequestMethod.GET)
	public @ResponseBody void sendpdffile(
			@RequestParam(value = "orderids", defaultValue = "FTBO160921225649954") String orderids,
			HttpServletRequest request, HttpServletResponse response) {		

		try {
			final Locale locale = LocaleContextHolder.getLocale();
			File flightpdf = GenerateFlightVoucherPdf(false, orderids, request, response, locale);
			FileInputStream inputStream = new FileInputStream(flightpdf);			
			response.setContentType("application/pdf");
			response.setContentLength((int) flightpdf.length());			
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", flightpdf.getName());
			response.setHeader(headerKey, headerValue);			
			OutputStream outStream = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;		
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			inputStream.close();
			outStream.close();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/hotel", method = RequestMethod.GET)
	public @ResponseBody void sendhotelpdffile(
			@RequestParam(value = "orderids", defaultValue = "HO14682604576730") String orderids,
			HttpServletRequest request, HttpServletResponse response) {		

		try {
			final Locale locale = LocaleContextHolder.getLocale();
			File hotelpdf = GenerateHotelVoucherPdf(false, orderids, request, response, locale);
			FileInputStream inputStream = new FileInputStream(hotelpdf);		
			response.setContentType("application/pdf");
			response.setContentLength((int) hotelpdf.length());			
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", hotelpdf.getName());
			response.setHeader(headerKey, headerValue);		
			OutputStream outStream = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;			
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}			
			inputStream.close();
			outStream.close();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/bus", method = RequestMethod.GET)
	public @ResponseBody void sendBusPdfFile(
			@RequestParam(value = "orderids", defaultValue = "TYBU12") String orderids,
			HttpServletRequest request, HttpServletResponse response) {		

		try {
			final Locale locale = LocaleContextHolder.getLocale();
			File flightpdf = GenerateBusVoucherPdf(orderids, request, response, locale);
			FileInputStream inputStream = new FileInputStream(flightpdf);			
			response.setContentType("application/pdf");
			response.setContentLength((int) flightpdf.length());			
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", flightpdf.getName());
			response.setHeader(headerKey, headerValue);			
			OutputStream outStream = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;		
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			inputStream.close();
			outStream.close();
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/invoice", method = RequestMethod.GET)
	public @ResponseBody void downloadInvoice(
			@RequestParam(value = "orderid", defaultValue = "FTBO160921225649954") String orderid,
			@RequestParam(value = "travelcode", defaultValue = "-1") int travelcode,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			boolean isMergedFlightPdf = false;
			Email email = new Email();
			email.setOrderId(orderid);
			email.setOnlyHtmlContent(true);
			String htmlContent = "";
			String productType = "";
			final Locale locale = LocaleContextHolder.getLocale();
			if(travelcode!= -1){
				switch (travelcode) {
				case TravelCode.FLIGHT:
					if(orderid.contains(",")){
						isMergedFlightPdf = true;
						File mergedpdffile = null;
						List<File> pdffiles = new ArrayList<File>();
						String[] orderidList = orderid.split(",");		
						if (orderidList != null && orderidList.length > 0) {
							for (int i = 0; i < orderidList.length; i++) {
								String orderId = orderidList[i];
								Email flightemail = getEmail(orderId);	
								flightemail.setOnlyHtmlContent(true);
								String flighthtmlContent = flightEmailHelper.sendFlightOffline_OnlineInvoice(false, flightemail, request, response, locale,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
								File pdffile = this.pdfService.createInvoicePdf(flighthtmlContent,"Invoice");
								pdffiles.add(pdffile);
							}
							mergedpdffile = getFile(pdffiles, "Flight");
							FileInputStream inputStream = new FileInputStream(mergedpdffile);			
							response.setContentType("application/pdf");
							response.setContentLength((int) mergedpdffile.length());
							String headerKey = "Content-Disposition";
							String headerValue = String.format("attachment; filename=\"%s\"", mergedpdffile.getName());
							response.setHeader(headerKey, headerValue);			
							OutputStream outStream = response.getOutputStream();
							byte[] buffer = new byte[4096];
							int bytesRead = -1;			
							while ((bytesRead = inputStream.read(buffer)) != -1) {
								outStream.write(buffer, 0, bytesRead);
							}			
							inputStream.close();
							outStream.close();
						}
					}else{
						htmlContent = flightEmailHelper.sendFlightOffline_OnlineInvoice(false, email, request, response, locale,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
						productType = "Flight";
					}					
					break;
				case TravelCode.HOTEL:
					htmlContent = hotelEmailHelper.sendHotelOffline_OnlineInvoice(email, request, response, locale,hotelSearchRoomDetailDao,hotelTravelRequestDao,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
					productType = "Hotel";
					break;
				case TravelCode.BUS:
					htmlContent = busEmailHelper.sendBusEmail(email, request, response, locale,busOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
					productType = "Bus";
					break;
				case TravelCode.CAR:
					htmlContent = carEmailHelper.sendCarInvoiceEmail(email, request, response, locale,carOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
					productType = "Car";
					break;
				case TravelCode.TRAIN:
					htmlContent = trainEmailHelper.sendTrainInvoiceEmail(email, request, response, locale,trainOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
					productType = "Train";
					break;
				case TravelCode.VISA:
					htmlContent = visaEmailHelper.sendVisaInvoiceEmail(email, request, response, locale,visaOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
					productType = "Visa";
					break;
				case TravelCode.INSURANCE:
					htmlContent = insuranceEmailHelper.sendInsuranceInvoiceEmail(email, request, response, locale,insuranceOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
					productType = "Insurance";
					break;
				case TravelCode.MISCELLANEOUS:
					htmlContent= miscellaneousEmailHelper.sendMiscellaneousInvoiceEmail(email, request, response, locale,miscellaneousOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,rmConfigDetailDAO);
					productType = "Miscellaneous";
					break;
				default:
					break;
				}

				if(!isMergedFlightPdf){
					File pdfFile = GenerateInvoicePdf(htmlContent, productType);
					FileInputStream inputStream = new FileInputStream(pdfFile);			
					response.setContentType("application/pdf");
					response.setContentLength((int) pdfFile.length());
					String headerKey = "Content-Disposition";
					String headerValue = String.format("attachment; filename=\"%s\"", pdfFile.getName());
					response.setHeader(headerKey, headerValue);			
					OutputStream outStream = response.getOutputStream();
					byte[] buffer = new byte[4096];
					int bytesRead = -1;			
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}			
					inputStream.close();
					outStream.close();
				}

			}
		} catch (Exception e) {			
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/creditnote", method = RequestMethod.GET)
	public @ResponseBody void downloadCreditNote(
			@RequestParam(value = "orderid", defaultValue = "FTBO160921225649954") String orderid,
			@RequestParam(value = "travelcode", defaultValue = "-1") int travelcode,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			Email email = new Email();
			email.setOrderId(orderid);
			email.setOnlyHtmlContent(true);
			String htmlContent = "";
			String productType = "";
			final Locale locale = LocaleContextHolder.getLocale();
			if(travelcode!= -1){
				switch (travelcode) {
				case TravelCode.FLIGHT:
					htmlContent = FlightEmailHelper.sendCreditNoteIssueMailFlight(email, request, response, locale,Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_FLIGHT,flightOrderRowEmailDao,flightInvoiceData,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,flightCreditNoteDao,emailContentService, rmConfigDetailDAO,companyDao);
					productType = "Flight";
					break;
				case TravelCode.HOTEL:
					htmlContent = HotelEmailHelper.sendCreditNoteIssueMailHotel(email, request, response, locale,Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_HOTEL,hotelOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,hotelCreditNoteDao, hotelSearchRoomDetailDao, rmConfigDetailDAO, companyDao);
					productType = "Hotel";
					break;
				case TravelCode.BUS:
					htmlContent = BusEmailHelper.sendCreditNoteIssueMailBus(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,busOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,busCreditNoteDao, rmConfigDetailDAO,companyDao);
					productType = "Bus";
					break;
				case TravelCode.CAR:
					htmlContent = CarEmailHelper.sendCreditNoteIssueMailCar(email, request, response, locale,Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_CAR,carOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,carCreditNoteDao, rmConfigDetailDAO,companyDao);
					productType = "Car";
					break;
				case TravelCode.TRAIN:
					htmlContent = TrainEmailHelper.sendCreditNoteIssueMailTrain(email, request, response, locale, Email.EMAIL_TYPE_CREDITNOTE_CONFIRM_CAR, trainOrderRowEmailDao, emailDao, emailService, emailNotificationDao, allEmailDao, applicationContext, servletContext, emailContentService, trainCreditNoteDao, rmConfigDetailDAO,companyDao);
					productType = "Train";
					break;
				case TravelCode.VISA:
					htmlContent = VisaEmailHelper.sendCreditNoteIssueMailVisa(email, request, response, locale,Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS,visaOrderRowEmailDao,emailDao,emailService,emailNotificationDao,allEmailDao,applicationContext,servletContext,emailContentService,visaCreditNoteDao, rmConfigDetailDAO,companyDao);
					productType = "Visa";
					break;
				case TravelCode.INSURANCE:
					htmlContent = InsuranceEmailHelper.sendCreditNoteIssueMailInsurance(email, request, response, locale, Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS, insuranceOrderRowEmailDao, emailDao, emailService, emailNotificationDao, allEmailDao, applicationContext, servletContext, emailContentService, insuranceCreditNoteDao, rmConfigDetailDAO,companyDao);
					productType = "Insurance";
					break;
				case TravelCode.MISCELLANEOUS:
					htmlContent = MiscellaneousEmailHelper.sendCreditNoteIssueMailMiscellaneous(email, request, response, locale, Email.EMAIL_TYPE_SUPER_INVOICE_TO_OTHERS, miscellaneousOrderRowEmailDao, emailDao, emailService, emailNotificationDao, allEmailDao, applicationContext, servletContext, emailContentService, miscellaneousCreditNoteDao, rmConfigDetailDAO, companyDao);
					productType = "Miscellaneous";
					break;
				default:
					break;
				}

				File pdfFile = GenerateCreditNodePdf(htmlContent, productType);
				FileInputStream inputStream = new FileInputStream(pdfFile);			
				response.setContentType("application/pdf");
				response.setContentLength((int) pdfFile.length());
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=\"%s\"", pdfFile.getName());
				response.setHeader(headerKey, headerValue);			
				OutputStream outStream = response.getOutputStream();
				byte[] buffer = new byte[4096];
				int bytesRead = -1;			
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}			
				inputStream.close();
				outStream.close();

			}
		} catch (Exception e) {			
			e.printStackTrace();
		}

	}

	public File GenerateHotelVoucherPdf(Boolean isFailed, String orderIds, HttpServletRequest request,
			HttpServletResponse response, Locale locale)
					throws NullPointerException, UnsupportedEncodingException, DocumentException, IOException, Exception {
		File pdffile = null;
		if (orderIds != null) {
			HotelOrderRow hotelOrderRowId = null;
			Company company = null;
			User userId = null;
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String orderId = orderIds;
			HttpSession session = request.getSession();		
			hotelOrderRowId = hotelOrderId.hotelOrderRowByOrderId(orderId);
			company = allEmailDao.getCompanyByCompanyId(hotelOrderRowId.getCompanyId());
			userId = allEmailDao.getUserByUserId(hotelOrderRowId.getUserId());			
			String checkIn = formatter.format(hotelOrderRowId.getCheckInDate());
			String checkOut = formatter.format(hotelOrderRowId.getCheckOutDate());
			DateTime dt1 = new DateTime(checkIn);
			DateTime dt2 = new DateTime(checkOut);
			int numberOfNights = Days.daysBetween(dt1, dt2).getDays();
			List<HotelOrderRoomInfo> roomDeatailsList = hotelOrderId.roomInfoDeatails(hotelOrderRowId);
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
			List<HotelOrderGuest> guestList = hotelOrderId.guesInformationDeatails(roomDeatailsList);
			List<HotelOrderGuest> refinedGuestList = new ArrayList<>();
			if (guestList.size() > 0) {
				for (HotelOrderGuest hotelOrderGuest : guestList) {
					if (hotelOrderGuest.getLeader().booleanValue()) {
						refinedGuestList.add(hotelOrderGuest);
					}
				}
			}			
			List<HotelOrderCancellationPolicy> cancelpolicies = hotelOrderId.getCancelPolicies(hotelOrderRowId);
			List<HotelOrderCancellationPolicy> cancelpoliciesRefined = new ArrayList<HotelOrderCancellationPolicy>();
			for (HotelOrderCancellationPolicy hotelOrderCancellationPolicy : cancelpolicies) {
				hotelOrderCancellationPolicy.roundOffFeeAmount();
				cancelpoliciesRefined.add(hotelOrderCancellationPolicy);
			}			
			try {
				BigDecimal finalprice = hotelOrderRowId.getFinalPrice();
				hotelOrderRowId.setFinalPrice(finalprice.setScale(2, RoundingMode.UP));
				BigDecimal taxes = hotelOrderRowId.getTaxes();
				taxes = taxes.multiply(hotelOrderRowId.getApiToBaseExchangeRate());
				taxes = taxes.multiply(hotelOrderRowId.getBaseToBookingExchangeRate());
				hotelOrderRowId.setTaxes(taxes.setScale(2, RoundingMode.UP));
				BigDecimal basefare = finalprice.subtract(taxes);
				basefare = basefare.setScale(2, RoundingMode.UP);
				BigDecimal servicecharges = hotelOrderRowId.getFeeAmount();
				hotelOrderRowId.setFeeAmount(servicecharges.setScale(2, RoundingMode.UP));
				BigDecimal proFees = finalprice.subtract(servicecharges).setScale(2, RoundingMode.UP);
				session.setAttribute("basefare", basefare);
			} catch (Exception e) {
				logger.info("taxes amount to booking currency....Exception----  ");
			}
			String combinationType = "";
			if (hotelOrderRowId.getSearchKey() != null) {
				HotelSearchRoomDetailTemp hotelSearchRoomDetail = new HotelSearchRoomDetailTemp(
						hotelOrderRowId.getSearchKey().longValue());
				if (hotelOrderRowId.getSearchKey() != null) {
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
			session.setAttribute("isCorporate", company.getCompanyRole().isCorporate());
			session.setAttribute("combinationType", combinationType);
			session.setAttribute("hotelOrderRowId", hotelOrderRowId);
			session.setAttribute("guestList", refinedGuestList);		
			session.setAttribute("numberOfNights", numberOfNights);	
			pdffile = this.pdfService.sendHotelOrderMailVoucher(false, true, hotelOrderRowId, userId, company,
					MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext);

		}

		return pdffile;
	}

	///// method written by raham
	public File downloadSupplierOrCustometPaymentTransactionsPdf(String type, String bookingOrderId,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
					throws NullPointerException, UnsupportedEncodingException, DocumentException, IOException, Exception {
		File mergedpdffile = null;
		List<File> pdffiles = new ArrayList<File>();
		logger.info(CommonUtil.getElapsedTime(startTime) + "seconds ... before create pdf-----------:");
		List<PaymentTransactionDetail> paymentTransactionDetailList = null;
		List<ApiProviderPaymentTransactionDetail> apiProviderPaymentTransactionDetailList = null;
		File pdffile = null;
		HotelOrderRow hotelOrderRow = hotelOrderId.hotelOrderRowByOrderId(bookingOrderId);

		if (type.equalsIgnoreCase("Customer")) {
			// write logic for customer payment transactions
			Object paymentTransaction = hotelOfflineBookingDao.getPaymentTransactionInfo(bookingOrderId);
			paymentTransactionDetailList = hotelOfflineBookingDao.getPaymentTransactionDetailList(bookingOrderId);
			pdffile = this.pdfService.createSupplierOrCustometPaymentTransactionsPdf(type, paymentTransaction,
					hotelOrderRow, paymentTransactionDetailList, apiProviderPaymentTransactionDetailList,
					MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext);
		}
		if (type.equalsIgnoreCase("Supplier")) {
			// write logic for Supplier payment transactions
			Object apiProviderpaymentTransaction = hotelOfflineBookingDao
					.getApiProviderPaymentTransactionInfo(bookingOrderId);
			apiProviderPaymentTransactionDetailList = hotelOfflineBookingDao
					.getApiProviderPaymentTransactionDetailList(bookingOrderId);
			pdffile = this.pdfService.createSupplierOrCustometPaymentTransactionsPdf(type,
					apiProviderpaymentTransaction, hotelOrderRow, paymentTransactionDetailList,
					apiProviderPaymentTransactionDetailList, MediaType.IMAGE_PNG_VALUE, locale, request, response,
					servletContext);
		}
		logger.info(CommonUtil.getElapsedTime(startTime) + "seconds ... after create pdf-----------:");
		pdffiles.add(pdffile);

		List<InputStream> pdfs = new ArrayList<InputStream>();
		for (int i = 0; i < pdffiles.size(); i++) {
			pdfs.add(new FileInputStream(pdffiles.get(i)));
		}
		File optDir = new File(
				getLogDir() + File.separator + "email" + File.separator + "PaymentTransactionsPDF" + File.separator + "pdf");
		if (!optDir.exists()) {
			optDir.mkdirs();
		}

		String path = optDir.getAbsolutePath() + File.separator + dateFormat.format(date) + "_"
				+ String.valueOf(new Random().nextInt()) + ".pdf";


		OutputStream output = new FileOutputStream(path);
		concatPDFs(pdfs, output, true);
		mergedpdffile = new File(path);
		logger.info(CommonUtil.getElapsedTime(startTime) + "seconds ... after pdf concatination-----------:");


		return mergedpdffile;
	}

	public File GenerateFlightVoucherPdf(Boolean isFailed, String orderIds, HttpServletRequest request,
			HttpServletResponse response, Locale locale)
					throws NullPointerException, UnsupportedEncodingException, DocumentException, IOException, Exception {
		File mergedpdffile = null;
		List<File> pdffiles = new ArrayList<File>();
		String[] orderidList = orderIds.split(",");	
		if (orderidList != null && orderidList.length > 0) {
			for (int i = 0; i < orderidList.length; i++) {
				FlightOrderRow flightorderrow = null;
				Company company = null;
				User user = null;
				String orderId = orderidList[i];
				BigDecimal totalAmount = new BigDecimal("0.0");
				HttpSession session = request.getSession();			
				flightorderrow = flightOrderRowEmailDao.flightOrderRowByOrderId(orderId);			
				if (flightorderrow != null) {
					BigDecimal beforeGstTot = flightorderrow.getFinalPrice();
					BigDecimal gstToBookingCurrency = flightorderrow.getGst_on_markup()
							.add(flightorderrow.getGstOnFlights()).multiply(flightorderrow.getBaseToBookingExchangeRate());
					String invoiceNo = flightorderrow.getInvoiceNo();
					company = allEmailDao.getCompanyByCompanyId(flightorderrow.getCompanyId());
					user = allEmailDao.getUserByUserId(flightorderrow.getUserId());
					if (company != null && user != null) {					
						if (flightorderrow.getCustomer() != null) {
							session.setAttribute("name",
									flightorderrow.getCustomer().getFirstName().replaceAll("[2C%-+.^:,]", " ") + " "
											+ flightorderrow.getCustomer().getLastName().replaceAll("[2C%-+.^:,]", " "));

							session.setAttribute("email", flightorderrow.getCustomer().getEmail());
							session.setAttribute("phone", flightorderrow.getCustomer().getMobile());

							session.setAttribute("address",
									flightorderrow.getCustomer().getAddress().replaceAll("[2C%-+.^:,]", " "));
							session.setAttribute("city", flightorderrow.getCustomer().getCity() != null
									? flightorderrow.getCustomer().getCity().replaceAll("[2C%-+.^:,]", " ") : "");
							session.setAttribute("state", flightorderrow.getCustomer().getZip() != null
									? flightorderrow.getCustomer().getZip().replaceAll("[2C%-+.^:,]", " ") : "");
						}
						logger.info("company involved... company : " + company.getCompanyname());
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

						BigDecimal totalgst = new BigDecimal(0);
						totalgst = (flightorderrow.getGstOnFlights() != null ? flightorderrow.getGstOnFlights()
								: new BigDecimal(0))
								.add(flightorderrow.getGst_on_markup() != null ? flightorderrow.getGst_on_markup()
										: new BigDecimal(0));
						BigDecimal totalgstinBooking = totalgst.multiply(flightorderrow.getBaseToBookingExchangeRate());

						BigDecimal gstOnAirAmount = ArithmeticUtil
								.divideTo2Decimal(totalgst.multiply(new BigDecimal("100.00")), new BigDecimal("6.0"));// totalgst.multiply(new

						BigDecimal baseFareinBooking = (flightorderrow.getPrice()
								.multiply(flightorderrow.getApiToBaseExchangeRate())							
								.multiply(flightorderrow.getBaseToBookingExchangeRate()));
						BigDecimal TaxinBooking = (flightorderrow.getTotalTaxes()
								.add((flightorderrow.getMarkUp() != null ? flightorderrow.getMarkUp() : new BigDecimal(0)))
								.multiply(flightorderrow.getApiToBaseExchangeRate()))							
								.multiply(flightorderrow.getBaseToBookingExchangeRate());

						OtherTax = TaxinBooking.subtract(AllTaxes).setScale(2, RoundingMode.UP);
						BigDecimal totWithGst = baseFareinBooking.add(TaxinBooking).add(extraMealPrice).add(extraBaggagePrice);

						session.setAttribute("OtherTax", OtherTax);
						session.setAttribute("baseFareinBooking", baseFareinBooking.setScale(2, RoundingMode.UP));
						session.setAttribute("TaxinBooking", TaxinBooking.setScale(2, RoundingMode.UP));
						session.setAttribute("pnr", flightorderrow.getPnr());

						session.setAttribute("orderId", orderId);
						session.setAttribute("tripDeatailsList", tripDeatailsListnew);
						session.setAttribute("flightOrderCustomerDetails", flightorderrow.getFlightOrderCustomers());
						session.setAttribute("flightInvoiceList", flightInvoiceList);	
						//added by basha
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
						//ended by basha
						//session.setAttribute("totWithGst", totWithGst.setScale(0, RoundingMode.UP));
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

						logger.info(CommonUtil.getElapsedTime(startTime) + "seconds ... before create pdf-----------:");
						File pdffile = this.pdfService.createFlightOrderPdf(false, true, flightorderrow, user, company,
								MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext, imagePth);
						logger.info(CommonUtil.getElapsedTime(startTime) + "seconds ... after create pdf-----------:");

						pdffiles.add(pdffile);

					} else {
						throw new Exception("Company or User not found for booking");
					}
				} else {
					throw new Exception("flightorderrow not found");
				}
			}

			List<InputStream> pdfs = new ArrayList<InputStream>();
			for (int i = 0; i < pdffiles.size(); i++) {
				pdfs.add(new FileInputStream(pdffiles.get(i)));
			}
			File optDir = new File(getLogDir() + File.separator + "email" + File.separator + "FlightPDF"
					+ File.separator + "pdf");
			if (!optDir.exists()) {
				optDir.mkdirs();
			}

			String path = optDir.getAbsolutePath() + File.separator + dateFormat.format(date) + "_"
					+ String.valueOf(new Random().nextInt()) + ".pdf";

			logger.info(CommonUtil.getElapsedTime(startTime) + "seconds ... before pdf concatination-----------:");
			OutputStream output = new FileOutputStream(path);
			concatPDFs(pdfs, output, true);
			mergedpdffile = new File(path);
			logger.info(CommonUtil.getElapsedTime(startTime) + "seconds ... after pdf concatination-----------:");

		} else
			throw new Exception("pdf not found");
		return mergedpdffile;
	}
	public File GenerateHotelQuoatationPdf(Boolean isFailed, String orderIds, Integer userId,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
					throws NullPointerException, UnsupportedEncodingException, DocumentException, IOException, Exception {
		File mergedpdffile = null;		
		String[] orderidList = orderIds.split(",");	
		if (orderidList != null && orderidList.length > 0) {
			for (int i = 0; i < orderidList.length; i++) {
				Company company = null;
				User user = null;
				String orderId = orderidList[i];				
				HotelTravelRequestQuotation hotelTravelRequestQuotation = hotelTravelRequestDao
						.getHotelRequestTravelQuotation(Long.valueOf(orderId));				
				if (hotelTravelRequestQuotation != null
						&& hotelTravelRequestQuotation.getHotelTravelRequest() != null) {
					user = allEmailDao.getUserByUserId(String.valueOf(userId));
					company = allEmailDao.getCompanyByCompanyId(String.valueOf(user.getCompanyid()));
					if (company != null && user != null) {
						user.tranformDisplayable();
						company.tranformDisplayable();					
						mergedpdffile = this.pdfService.createHotelQuotationPdf(false, true,
								hotelTravelRequestQuotation, user, company, MediaType.IMAGE_PNG_VALUE, locale, request,
								response, servletContext);	

					} else {
						throw new Exception("Company or User not found for booking");
					}
				} else {
					throw new Exception("hotelTravelRequestQuotation not found");
				}

			}
		}
		else
			throw new Exception("pdf not found");
		return mergedpdffile;
	}

	public File generateFlightQuoatationPdf(Boolean isFailed, String orderIds, Integer userId,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
					throws NullPointerException, UnsupportedEncodingException, DocumentException, IOException, Exception {
		File mergedpdffile = null;		
		String[] orderidList = orderIds.split(",");		
		if (orderidList != null && orderidList.length > 0) {
			for (int i = 0; i < orderidList.length; i++) {
				Company company = null;
				User user = null;
				String orderId = orderidList[i];	
				FlightTravelRequestQuotation flightTravelRequestQuotation = flightTravelRequestDao
						.getFlightTravelRequestQuotation(Long.valueOf(orderId));				
				if (flightTravelRequestQuotation != null
						&& flightTravelRequestQuotation.getFlightTravelRequest() != null
						&& flightTravelRequestQuotation.getFlightTravelRequestTripDetails() != null
						&& flightTravelRequestQuotation.getFlightTravelRequestTripDetails().size() > 0) {
					user = allEmailDao.getUserByUserId(String.valueOf(userId));
					company = allEmailDao.getCompanyByCompanyId(String.valueOf(user.getCompanyid()));
					if (company != null && user != null) {
						user.tranformDisplayable();
						company.tranformDisplayable();					
						mergedpdffile = this.pdfService.createFlightQuotationPdf(false, true,
								flightTravelRequestQuotation, user, company, MediaType.IMAGE_PNG_VALUE, locale, request,
								response, servletContext);				

					} else {
						throw new Exception("Company or User not found for booking");
					}
				} else {
					throw new Exception("flightTravelRequestQuotation not found");
				}
			}			

		}

		else
			throw new Exception("pdf not found");
		return mergedpdffile;
	}

	public File GenerateHotelVoucherPdf(Boolean isFailed, String orderIds, Integer userId, HttpServletRequest request,
			HttpServletResponse response, Locale locale)
					throws NullPointerException, UnsupportedEncodingException, DocumentException, IOException, Exception {
		File mergedpdffile = null;
		List<File> pdffiles = new ArrayList<File>();
		String[] orderidList = orderIds.split(",");		
		if (orderidList != null && orderidList.length > 0) {
			for (int i = 0; i < orderidList.length; i++) {
				Company company = null;
				User user = null;
				String orderId = orderidList[i];			
				HotelTravelRequestQuotation hotelTravelRequestQuotation = hotelTravelRequestDao
						.getHotelRequestTravelQuotation(Long.valueOf(orderId));				
				if (hotelTravelRequestQuotation != null
						&& hotelTravelRequestQuotation.getHotelTravelRequest() != null) {
					user = allEmailDao.getUserByUserId(String.valueOf(userId));
					company = allEmailDao.getCompanyByCompanyId(String.valueOf(user.getCompanyid()));
					if (company != null && user != null) {
						user.tranformDisplayable();
						company.tranformDisplayable();						
						File pdffile = this.pdfService.createHotelQuotationPdf(false, true, hotelTravelRequestQuotation,
								user, company, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext);					

						pdffiles.add(pdffile);

					} else {
						throw new Exception("Company or User not found for booking");
					}
				} else {
					throw new Exception("flightorderrow not found");
				}
			}

			List<InputStream> pdfs = new ArrayList<InputStream>();
			for (int i = 0; i < pdffiles.size(); i++) {
				pdfs.add(new FileInputStream(pdffiles.get(i)));
			}
			File optDir = new File(getLogDir() + File.separator + "email" + File.separator + "FlightInvoicePDF"
					+ File.separator + "pdf");
			if (!optDir.exists()) {
				optDir.mkdirs();
			}

			String path = optDir.getAbsolutePath() + File.separator + dateFormat.format(date) + "_"
					+ String.valueOf(new Random().nextInt()) + ".pdf";			
			OutputStream output = new FileOutputStream(path);
			concatPDFs(pdfs, output, true);
			mergedpdffile = new File(path);	

		}

		else
			throw new Exception("pdf not found");
		return mergedpdffile;
	}

	public File GenerateFlightQuoatationPdf(Boolean isFailed, String orderIds, Integer userId,
			HttpServletRequest request, HttpServletResponse response, Locale locale)
					throws NullPointerException, UnsupportedEncodingException, DocumentException, IOException, Exception {
		File mergedpdffile = null;
		List<File> pdffiles = new ArrayList<File>();
		String[] orderidList = orderIds.split(",");		
		if (orderidList != null && orderidList.length > 0) {
			for (int i = 0; i < orderidList.length; i++) {
				Company company = null;
				User user = null;
				String orderId = orderidList[i];				
				HotelTravelRequestQuotation hotelTravelRequestQuotation = hotelTravelRequestDao
						.getHotelRequestTravelQuotation(Long.valueOf(orderId));			
				if (hotelTravelRequestQuotation != null
						&& hotelTravelRequestQuotation.getHotelTravelRequest() != null) {
					user = allEmailDao.getUserByUserId(String.valueOf(userId));
					company = allEmailDao.getCompanyByCompanyId(String.valueOf(user.getCompanyid()));
					if (company != null && user != null) {
						user.tranformDisplayable();
						company.tranformDisplayable();						
						File pdffile = this.pdfService.createHotelQuotationPdf(false, true, hotelTravelRequestQuotation,								user, company, MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext);					

						pdffiles.add(pdffile);

					} else {
						throw new Exception("Company or User not found for booking");
					}
				} else {
					throw new Exception("flightorderrow not found");
				}
			}
			List<InputStream> pdfs = new ArrayList<InputStream>();
			for (int i = 0; i < pdffiles.size(); i++) {
				pdfs.add(new FileInputStream(pdffiles.get(i)));
			}
			File optDir = new File(getLogDir() + File.separator + "email" + File.separator + "FlightInvoicePDF"
					+ File.separator + "pdf");
			if (!optDir.exists()) {
				optDir.mkdirs();
			}
			String path = optDir.getAbsolutePath() + File.separator + dateFormat.format(date) + "_"
					+ String.valueOf(new Random().nextInt()) + ".pdf";

			OutputStream output = new FileOutputStream(path);
			concatPDFs(pdfs, output, true);
			mergedpdffile = new File(path);		

		}
		else
			throw new Exception("pdf not found");
		return mergedpdffile;
	}

	public File GenerateBusVoucherPdf(String orderIds, HttpServletRequest request,
			HttpServletResponse response, Locale locale){		
		File mergedpdffile = null;
		List<File> pdffiles = new ArrayList<File>();
		try{
			String[] orderidList = orderIds.split(",");		
			if (orderidList != null && orderidList.length > 0) {
				for (int i = 0; i < orderidList.length; i++) {
					Company company = null;
					User user = null;
					String orderId = orderidList[i];
					HttpSession session = request.getSession();
					BusOrderRow busOrderRow = null;				
					busOrderRow = busOrderRowEmailDao.getBusOrderRowDetailsByOrderId(orderId);
					if (busOrderRow != null) {			
						SimpleDateFormat dateFormatShow = new SimpleDateFormat("dd-MMM-yy");
						String onwardDate = dateFormatShow.format(busOrderRow.getTravelDate());
						BigDecimal finalAmount = busOrderRow.getTotalAmount();
						finalAmount = AmountRoundingModeUtil.roundingModeForBus(finalAmount);
						busOrderRow.setTotalAmount(finalAmount);
						company = allEmailDao.getCompanyByCompanyId(busOrderRow.getCompanyId());
						user = allEmailDao.getUserByEmail(company.getEmail());
						
						if (company != null && user != null) {	
							List<BusOrderCustomerDetail> busOrderCustomerDetailList = busOrderRowEmailDao.geBusOrderCustomerDetailList(busOrderRow.getId());
							BusOrderCustomerDetail busOrderCustomer = null;
							for (BusOrderCustomerDetail busOrderCustomerDetail : busOrderCustomerDetailList) {
								busOrderCustomer = busOrderCustomerDetail;
								break;
							}

							session.setAttribute("isCorporate", company.getCompanyRole().isCorporate());
							session.setAttribute("onwarddate", onwardDate);
							session.setAttribute("customers", busOrderCustomerDetailList);
							if(user.getEmail().equalsIgnoreCase("DirectUser@intellicommsolutions.com")){
								session.setAttribute("IsB2C", true);
							}else{
								session.setAttribute("IsB2C", false);
							}

							String path = user.getImagepath();
							CommonConfig conf = CommonConfig.GetCommonConfig();
							
							String imagePth = conf.getDefult_image_path();
							if (path != null) {
								logger.info("Image config imagePth controlerr " + conf.getImage_path());
								imagePth = conf.getImage_path() + path;
								logger.info("Image imagePth controlerr imagePth" + imagePth);
							}				

							Map<String, Object> variables = new HashMap<String, Object>();
							variables.put("busorder", busOrderRow);
							variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
							variables.put("user", user);
							variables.put("company", company);					
							variables.put("baseUrl",
									request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
							final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale, variables,
									applicationContext);
							String htmlContent = emailContentService.sendBusOrderMailVoucher(user, company,  MediaType.IMAGE_PNG_VALUE, locale, request, response, servletContext, applicationContext, ctx);

							File pdffile = this.pdfService.createBusVoucherPdf(htmlContent);
							pdffiles.add(pdffile);
						}
					}
				}
			}
			List<InputStream> pdfs = new ArrayList<InputStream>();
			for (int i = 0; i < pdffiles.size(); i++) {
				pdfs.add(new FileInputStream(pdffiles.get(i)));
			}
			File optDir = new File(getLogDir() + File.separator + "email" + File.separator + "FlightInvoicePDF"
					+ File.separator + "pdf");
			if (!optDir.exists()) {
				optDir.mkdirs();
			}
			String path = optDir.getAbsolutePath() + File.separator + dateFormat.format(date) + "_"
					+ String.valueOf(new Random().nextInt()) + ".pdf";

			OutputStream output = new FileOutputStream(path);
			concatPDFs(pdfs, output, true);
			mergedpdffile = new File(path);
		}catch(Exception e){

		}
		return mergedpdffile;
	}

	public static String getLogDir() {

		String dirName = CommonConfig.GetCommonConfig().getLog_location();
		String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
		if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
			dirName = CommonConfig.GetCommonConfig().getLog_location();
		} else if (OS.indexOf("win") >= 0) {
			dirName = CommonConfig.GetCommonConfig().getLog_location();
		} else if (OS.indexOf("nux") >= 0) {
			dirName = CommonConfig.GetCommonConfig().getLog_location();
		} else {
			dirName = CommonConfig.GetCommonConfig().getLog_location();
		}
		return dirName;

	}
	public static void concatPDFs(List<InputStream> streamOfPDFFiles, OutputStream outputStream, boolean paginate) {

		Document document = new Document();
		try {
			List<InputStream> pdfs = streamOfPDFFiles;
			List<PdfReader> readers = new ArrayList<PdfReader>();
			int totalPages = 0;
			Iterator<InputStream> iteratorPDFs = pdfs.iterator();

			// Create Readers for the pdfs.
			while (iteratorPDFs.hasNext()) {
				InputStream pdf = iteratorPDFs.next();
				PdfReader pdfReader = new PdfReader(pdf);
				readers.add(pdfReader);
				totalPages += pdfReader.getNumberOfPages();
			}
			// Create a writer for the outputstream
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);

			document.open();
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
			// data

			PdfImportedPage page;
			int currentPageNumber = 0;
			int pageOfCurrentReaderPDF = 0;
			Iterator<PdfReader> iteratorPDFReader = readers.iterator();

			// Loop through the PDF files and add to the output.
			while (iteratorPDFReader.hasNext()) {
				PdfReader pdfReader = iteratorPDFReader.next();

				// Create a new page in the target for each source page.
				while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
					document.newPage();
					pageOfCurrentReaderPDF++;
					currentPageNumber++;
					page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
					cb.addTemplate(page, 0, 0);

					// Code for pagination.
					if (paginate) {
						cb.beginText();
						cb.setFontAndSize(bf, 9);
						cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "" + currentPageNumber + " of " + totalPages,
								520, 5, 0);
						cb.endText();
					}
				}
				pageOfCurrentReaderPDF = 0;
			}
			outputStream.flush();
			document.close();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (document.isOpen())
				document.close();
			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	public File GenerateInvoicePdf(String htmlContent,String type){
		File pdffile = null;
		try{
			pdffile = this.pdfService.createInvoicePdf(htmlContent,type+" Invoice");
		}catch(Exception e){
			logger.info("GenerateCreditNodePdf " + e);
		}
		return pdffile;

	}
	public File GenerateCreditNodePdf(String htmlContent,String type){
		File pdffile = null;
		try{
			pdffile = this.pdfService.createCreditNotePdf(htmlContent,type+" FlightCreditNote");
		}catch(Exception e){
			logger.info("GenerateCreditNodePdf " + e);
		}
		return pdffile;

	}
	public Email getEmail(String orderId){
		Email email = new Email();
		email.setOrderId(orderId);
		email.setOnlyHtmlContent(true);	
		return email;
	}
	public File getFile(List<File> pdffiles,String fileName){
		File mergedpdffile = null;
		List<InputStream> pdfs = new ArrayList<InputStream>();
		try{		
			for (int i = 0; i < pdffiles.size(); i++) {
				pdfs.add(new FileInputStream(pdffiles.get(i)));
			}
			File optDir = new File(
					getLogDir() + File.separator + "email" + File.separator + fileName + File.separator + "pdf");
			if (!optDir.exists()) {
				optDir.mkdirs();
			}
			String path = optDir.getAbsolutePath() + File.separator + dateFormat.format(date) + "_"
					+ String.valueOf(new Random().nextInt()) + ".pdf";			
			OutputStream output = new FileOutputStream(path);
			concatPDFs(pdfs, output, true);
			mergedpdffile = new File(path);	
		}catch(Exception e){
			logger.info("Generate Pdf File Exception " + e);
		}
		return mergedpdffile;
	}
}
