package com.tayyarah.services;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.spring3.context.SpringWebContext;

import com.tayyarah.common.entity.ApiProviderPaymentTransaction;
import com.tayyarah.common.entity.ApiProviderPaymentTransactionDetail;
import com.tayyarah.common.entity.PaymentTransaction;
import com.tayyarah.common.entity.PaymentTransactionDetail;
import com.tayyarah.common.util.AmountRoundingModeUtil;
import com.tayyarah.common.util.DateConversion;
import com.tayyarah.common.util.HTMLtoPDFConvertor;
import com.tayyarah.company.entity.Company;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.flight.quotation.entity.FlightTravelRequestQuotation;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.hotel.quotation.entity.HotelTravelRequestQuotation;
import com.tayyarah.user.entity.User;


@Service
public class PdfService {
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private ApplicationContext applicationContext;
	static final Logger logger = Logger.getLogger(PdfService.class);
	Date date = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	public File createHotelQuotationPdf(boolean isFailed, boolean isCustomer,
			final HotelTravelRequestQuotation hotelTravelRequestQuotation, final User user, final Company company,
			final String imageContentType, final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response, final ServletContext servletContext)
					throws Exception, NullPointerException {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("roomrate", hotelTravelRequestQuotation.getSellRate().setScale(2, BigDecimal.ROUND_FLOOR));
		variables.put("quotation", hotelTravelRequestQuotation);
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("user", user);
		variables.put("company", company);
		variables.put("baseUrl", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale, variables,
				applicationContext);
		final String htmlContent = this.templateEngine.process("hotelquotaionpdf.html", ctx);
		File pdffile = new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent, "HotelInvoicePDF", dateFormat.format(date),
				String.valueOf(new Random().nextInt())));
		logger.info(
				"hotelTravelRequestQuotation PDF generated ...." + " for id " + hotelTravelRequestQuotation.getId());
		return pdffile;
	}

	public File createFlightQuotationPdf(boolean isFailed, boolean isCustomer,
			final FlightTravelRequestQuotation flightTravelRequestQuotation, final User user, final Company company,
			final String imageContentType, final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response, final ServletContext servletContext)
					throws Exception, NullPointerException {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("rate", flightTravelRequestQuotation.getTotalAmount().setScale(2, BigDecimal.ROUND_FLOOR));
		variables.put("quotation", flightTravelRequestQuotation);
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("user", user);
		variables.put("company", company);
		variables.put("baseUrl", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
		final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale, variables,
				applicationContext);
		final String htmlContent = this.templateEngine.process("flightquotaionpdf.html", ctx);
		File pdffile = new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent, "FlightPDF", dateFormat.format(date),
				String.valueOf(new Random().nextInt())));
		logger.info(
				"flightTravelRequestQuotation PDF generated ...." + " for id " + flightTravelRequestQuotation.getId());
		return pdffile;
	}

	public File createFlightOrderPdf(boolean isFailed, boolean isCustomer, final FlightOrderRow flightorder,
			final User user, final Company company, final String imageContentType, final Locale locale,
			final HttpServletRequest request, final HttpServletResponse response, final ServletContext servletContext,
			String path) throws Exception, NullPointerException {
		
		user.initLogoDisplayable();
		String recipientName = flightorder.getCustomer().getFirstName() + " " + flightorder.getCustomer().getLastName();
		String address = flightorder.getCustomer().getAddress();		
		String receptemail = user.getEmail();
		if (isCustomer)
			receptemail = flightorder.getCustomer().getEmail();
	
		if (flightorder.getStatusAction().equalsIgnoreCase("Hold")
				|| flightorder.getStatusAction().equalsIgnoreCase("Released"))
			flightorder.setInvoiceNo(flightorder.getOrderId());

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("flightorder", flightorder);
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("user", user);
		variables.put("company", company);
		variables.put("isFailed", isFailed);
		variables.put("baseUrl", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());

		final IWebContext ctx = new SpringWebContext(request, response, servletContext, locale, variables,
				applicationContext);

		final String htmlContent = this.templateEngine.process("Flight-Voucher.html", ctx);

		File pdffile = new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent, "FlightPDF", dateFormat.format(date),
				String.valueOf(new Random().nextInt())));

		logger.info("Flight PDF generated ...." + " for invoice no " + flightorder.getInvoiceNo());
		return pdffile;
	}
	
	public File createBusVoucherPdf(String htmlContent) throws Exception, NullPointerException {		
		File pdffile = new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent, "BusVoucherPdf", dateFormat.format(date),
				String.valueOf(new Random().nextInt())));
		return pdffile;
	}

	public File createHotelInvoicePdf(final HotelOrderRow hotelorder, final User user, final Company company,
			final String imageContentType, final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response, final ServletContext servletContext,
			final ApplicationContext applicationContext, User parentUser, Company parentComapny, String htmlContent)
					throws Exception, NullPointerException {

		user.initLogoDisplayable();
		String corporateDirName = company.getCompany_userid();
		File pdffile = new File(HTMLtoPDFConvertor.corporateHtmlRawToPDF(htmlContent, corporateDirName,
				dateFormat.format(date), String.valueOf(new Random().nextInt())));

		return pdffile;
	}

	public File createFlightInvoicePdf(final FlightOrderRow flightOrderRow, final User user, final Company company,
			final String imageContentType, final Locale locale, final HttpServletRequest request,
			final HttpServletResponse response, final ServletContext servletContext,
			final ApplicationContext applicationContext, String htmlContent) throws Exception, NullPointerException {
		user.initLogoDisplayable();
		String corporateDirName = company.getCompany_userid();
		File pdffile = new File(HTMLtoPDFConvertor.corporateHtmlRawToPDF(htmlContent, corporateDirName,
				dateFormat.format(date), String.valueOf(new Random().nextInt())));

		return pdffile;
	}

	public File createBusInvoicePdf(String htmlContent) throws Exception, NullPointerException {		
		File pdffile = new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent, "BusInvoicePdf", dateFormat.format(date),
				String.valueOf(new Random().nextInt())));
		return pdffile;
	}

	public File createCarInvoicePdf(String htmlContent) throws Exception, NullPointerException {
		File pdffile = new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent, "CarInvoicePdf", dateFormat.format(date),
				String.valueOf(new Random().nextInt())));		
		return pdffile;
	}

	public File createTrainInvoicePdf(String htmlContent) throws Exception, NullPointerException {	
		File pdffile = new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent, "TrainInvoicePdf", dateFormat.format(date),
		String.valueOf(new Random().nextInt())));		
		return pdffile;
	}

	public File createVisaInvoicePdf(String htmlContent) throws Exception, NullPointerException {
		File pdffile = new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent, "VisaInvoicePdf", dateFormat.format(date),
				String.valueOf(new Random().nextInt())));
		return pdffile;
	}

	public File createInsuranceInvoicePdf(String htmlContent)throws Exception, NullPointerException {
		File pdffile = new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent, "InsuranceInvoicePdf",
				dateFormat.format(date), String.valueOf(new Random().nextInt())));
		return pdffile;
	}

	public File sendHotelOrderMailVoucher(boolean isFailed, boolean isCustomer, final HotelOrderRow hotelorder,
			final User user, final Company company, final String imageContentType, final Locale locale,
			final HttpServletRequest request, final HttpServletResponse response, final ServletContext servletContext)
					throws Exception, NullPointerException {

		user.initLogoDisplayable();
		String receptemail = user.getEmail();
		if (isCustomer)
			receptemail = hotelorder.getOrderCustomer().getEmail();	

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("admin_url", CommonConfig.GetCommonConfig().getAdmin_url());
		variables.put("hotelorder", hotelorder);
		variables.put("user", user);
		variables.put("company", company);
		variables.put("companylogo", CommonConfig.GetCommonConfig().getImage_path() + company.getCompanyid());
		variables.put("isFailed", isFailed);
		variables.put("baseUrl", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
		final IWebContext ctxVoucher = new SpringWebContext(request, response, servletContext, locale, variables,
				applicationContext);
		final String htmlContentVoucher = this.templateEngine.process("Hotel-Voucher.html", ctxVoucher);
		File pdffile = new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContentVoucher, "HotelVoucherPDF",
				dateFormat.format(date), String.valueOf(new Random().nextInt())));		
		return pdffile;
	}

	public File createSupplierOrCustometPaymentTransactionsPdf(String type, Object obj, HotelOrderRow hotelOrderRow,
			List<PaymentTransactionDetail> paymentTransactionDetailList,
			List<ApiProviderPaymentTransactionDetail> apiProviderPaymentTransactionDetailList, String imagePngValue,
			Locale locale, HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) {
		// TODO Auto-generated method stub
		IWebContext ctx = null;
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("baseUrl", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
		String title = "";
		BigDecimal partialAmtCount = new BigDecimal("0.00");
		BigDecimal restAmtCount = new BigDecimal("0.00");
		List<PaymentTransactionDetail> paymentTransactionDetailListNew = new ArrayList<>();
		List<ApiProviderPaymentTransactionDetail> apiProviderPaymentTransactionDetailListNew = new ArrayList<>();
		if (paymentTransactionDetailList != null && paymentTransactionDetailList.size() > 0) {
			PaymentTransaction paymentTransaction = (PaymentTransaction) obj;
			if (hotelOrderRow != null && paymentTransaction != null) {
				paymentTransaction.setAmount(AmountRoundingModeUtil.roundingMode(paymentTransaction.getAmount()));
				if (paymentTransaction != null && paymentTransaction.getPayment_system().equalsIgnoreCase("Partial")) {
					if (paymentTransactionDetailList != null && paymentTransactionDetailList.size() > 0) {
						for (PaymentTransactionDetail paymentTransactionDetail : paymentTransactionDetailList) {
							paymentTransactionDetail.setCreatedDate(
									DateConversion.convertTimestampToString(paymentTransactionDetail.getCreatedAt()));
							paymentTransactionDetail
							.setAmount(paymentTransactionDetail.getAmount().setScale(2, BigDecimal.ROUND_UP));
							restAmtCount = restAmtCount.add(paymentTransactionDetail.getAmount());
							BigDecimal balance = paymentTransaction.getAmount().subtract(restAmtCount);
							paymentTransactionDetail.setBalance(balance);
							paymentTransactionDetailListNew.add(paymentTransactionDetail);
							partialAmtCount = partialAmtCount.add(paymentTransactionDetail.getAmount());
						}
					}
					BigDecimal balance = paymentTransaction.getAmount().subtract(partialAmtCount);
					paymentTransaction.setBalance(balance);

				}
			}

			variables.put("paymentTransaction", paymentTransaction);
			variables.put("customerPaymentTransactionDetailList", paymentTransactionDetailListNew);
			ctx = new SpringWebContext(request, response, servletContext, locale, variables, applicationContext);
			title = "Customer Payment Transaction(s)";
		}
		if (apiProviderPaymentTransactionDetailList != null && apiProviderPaymentTransactionDetailList.size() > 0) {
			ApiProviderPaymentTransaction apiProviderpaymentTransaction = (ApiProviderPaymentTransaction) obj;
			if (hotelOrderRow != null && apiProviderpaymentTransaction != null) {
				apiProviderpaymentTransaction
				.setAmount(apiProviderpaymentTransaction.getAmount().setScale(2, BigDecimal.ROUND_UP));
				if (apiProviderpaymentTransaction != null
						&& apiProviderpaymentTransaction.getPayment_system().equalsIgnoreCase("Partial")) {
					if (paymentTransactionDetailList != null && paymentTransactionDetailList.size() > 0) {
						for (ApiProviderPaymentTransactionDetail apiProviderpaymentTransactionDetail : apiProviderPaymentTransactionDetailList) {
							apiProviderpaymentTransactionDetail.setCreatedDate(DateConversion
									.convertTimestampToString(apiProviderpaymentTransactionDetail.getCreatedAt()));
							apiProviderpaymentTransactionDetail.setAmount(
									apiProviderpaymentTransactionDetail.getAmount().setScale(2, BigDecimal.ROUND_UP));
							restAmtCount = restAmtCount.add(apiProviderpaymentTransactionDetail.getAmount());
							BigDecimal balance = apiProviderpaymentTransactionDetail.getAmount().subtract(restAmtCount);
							apiProviderpaymentTransactionDetail.setBalance(balance);
							apiProviderPaymentTransactionDetailListNew.add(apiProviderpaymentTransactionDetail);
							partialAmtCount = partialAmtCount.add(apiProviderpaymentTransactionDetail.getAmount());
						}
					}
					BigDecimal balance = apiProviderpaymentTransaction.getAmount().subtract(partialAmtCount);
					apiProviderpaymentTransaction.setBalance(balance);

				}
			}

			variables.put("apiProviderpaymentTransaction", apiProviderpaymentTransaction);
			variables.put("apiProviderPaymentTransactionDetailList", apiProviderPaymentTransactionDetailListNew);
			title = "Supplier Payment Transaction(s)";
		}
		variables.put("title", title);
		ctx = new SpringWebContext(request, response, servletContext, locale, variables, applicationContext);
		final String htmlContent = this.templateEngine.process("payment-transaction-pdf.html", ctx);

		File pdffile = new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent, title, dateFormat.format(date),
				String.valueOf(new Random().nextInt())));

		logger.info("Flight PDF generated ...." + " for invoice no ");
		return pdffile;


	}
	public File createCreditNotePdf(String htmlContent,String foldername) throws Exception, NullPointerException {		
		File pdffile = new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent, foldername, dateFormat.format(date),
				String.valueOf(new Random().nextInt())));
		return pdffile;
	}
	public File createInvoicePdf(String htmlContent,String foldername) throws Exception, NullPointerException {		
		File pdffile = new File(HTMLtoPDFConvertor.htmlRawToPDF(htmlContent, foldername, dateFormat.format(date),
				String.valueOf(new Random().nextInt())));
		return pdffile;
	}
	public File createBulkInvoicePdf(String htmlContent,String foldername,String orderId) throws Exception, NullPointerException {		
		File pdffile = new File(HTMLtoPDFConvertor.createBulkInvoiceHtmlRawToPDFFolder(htmlContent, foldername, orderId));
		return pdffile;
	}
	public boolean deleteBulkInvoicePdfFiles(String srcFolder,String subFolder) throws Exception, NullPointerException {		
		return HTMLtoPDFConvertor.deleteFiles(srcFolder,subFolder);
	}
	
	public File createBulkInvoiceSinglePDFDownload(String htmlContent,String srcFolder,String subFolder,String dateFormat) throws Exception, NullPointerException {		
		File pdffile = new File(HTMLtoPDFConvertor.createBulkInvoiceSinglePDFDownload(htmlContent,srcFolder,subFolder,dateFormat));
		return pdffile;
	}
	

}
