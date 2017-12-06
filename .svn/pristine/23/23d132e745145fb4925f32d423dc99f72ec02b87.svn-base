package com.tayyarah.services;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IWebContext;

import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.car.entity.CarOrderRow;
import com.tayyarah.company.entity.Company;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.misellaneous.entity.MiscellaneousOrderRow;
import com.tayyarah.train.entity.TrainOrderRow;
import com.tayyarah.user.entity.User;
import com.tayyarah.visa.entity.VisaOrderRow;




@Service
public class EmailContentService {
	@Autowired
	private TemplateEngine templateEngine;
	Date date = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	static final Logger logger = Logger.getLogger(EmailContentService.class);
	public String createHotelOrderInvoiceMail(Company bookingCompany, IWebContext ctx) {
		String htmlContent=null;
		if(bookingCompany.isMyEmailDir())
		{
			String remotePath = CommonConfig.GetCommonConfig().getLog_location();
			try {
				boolean emailFileFound= false;
				//for remote path
				File sysdir=new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(bookingCompany.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();

								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Hotel-Invoice.html"))
										{
											htmlContent  = this.templateEngine.process(bookingCompany.getCompany_userid()+"/"+f, ctx); /*company.getCompany_userid()+*/
											emailFileFound= true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Hotel-Invoice.html", ctx);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			htmlContent = this.templateEngine.process("Hotel-Invoice.html", ctx);
		}
		return htmlContent;
	}
	public String sendFlightOrderMailVoucher(boolean b, boolean c, FlightOrderRow flightorderrow, User user, Company company,
			String imagePngValue, Locale locale, HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, ApplicationContext applicationContext, String imagePth, IWebContext ctx) {
		String htmlContent=null;
		if(company.isMyEmailDir()){
			String corporateDirName = company.getCompany_userid();
			String remotePath=CommonConfig.GetCommonConfig().getLog_location();

			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Flight-Voucher-Corporate.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Flight-Voucher.html", ctx);
					//htmlContent = this.templateEngine.process("Flight-Voucher-Corporate.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in sendFlightOrderMail");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Flight-Voucher.html", ctx);

		}
		return  htmlContent;
	}
	public String sendHotelOrderMailVoucher(Boolean isFailed, boolean b, HotelOrderRow hotelOrderRow, User user,
			Company company, String imagePngValue, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext, IWebContext ctx) {
		String htmlContent=null;
		if(company.isMyEmailDir()){
			String corporateDirName = company.getCompany_userid();
			String remotePath=CommonConfig.GetCommonConfig().getLog_location();

			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Hotel-Voucher.html")){
											htmlContent  = this.templateEngine.process("Hotel-Voucher.html", ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent  = this.templateEngine.process("Hotel-Voucher.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in sendFlightOrderMail");
			}
		}
		else
		{
			htmlContent  = this.templateEngine.process("Hotel-Voucher.html", ctx);
		}
		return  htmlContent;

	}
	public String sendFlightOrderMailInvoice(boolean b, boolean c, FlightOrderRow flightorderrow, User user,
			Company company, String imagePngValue, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext,
			String imagePth, IWebContext ctx) {

		String htmlContent=null;
		if(company.isMyEmailDir()){
			String corporateDirName = company.getCompany_userid();
			String remotePath=CommonConfig.GetCommonConfig().getLog_location();

			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Flight-Invoice.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					//htmlContent = this.templateEngine.process("Flight-Invoice-Corporate.html", ctx);
					htmlContent = this.templateEngine.process("Flight-Invoice.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in sendFlightOrderMail");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Flight-Invoice.html", ctx);
		}
		return  htmlContent;

	}
	public String sendCarOrderMailInvoice(boolean b, boolean c, CarOrderRow carOrderRow, User user, Company company,
			String imagePngValue, Locale locale, HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, ApplicationContext applicationContext, String imagePth, IWebContext ctx) {

		String htmlContent=null;
		if(company.isMyEmailDir()){
			String remotePath=CommonConfig.GetCommonConfig().getLog_location();

			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Car-Invoice.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Car-Invoice.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in sendCarOrderMail");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Car-Invoice.html", ctx);
		}
		return  htmlContent;
	}
	public String sendTrainOrderMailInvoice(boolean b, boolean c, TrainOrderRow trainOrderRow, User user,
			Company company, String imagePngValue, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext,
			String imagePth, IWebContext ctx) {
		String htmlContent = null;
		if(company.isMyEmailDir()){
			String remotePath = CommonConfig.GetCommonConfig().getLog_location();
			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Train-Invoice.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Train-Invoice.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in sendCarOrderMail");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Train-Invoice.html", ctx);
		}
		return  htmlContent;
	}
	
	public String sendBusOrderMailInvoice(boolean b, boolean c, BusOrderRow busOrderRow, User user, Company company,
			String imagePngValue, Locale locale, HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, ApplicationContext applicationContext, String imagePth, IWebContext ctx) {
		String htmlContent = null;
		if(company.isMyEmailDir()){
			String remotePath=CommonConfig.GetCommonConfig().getLog_location();
			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Bus-Invoice.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Bus-Invoice.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in sendCarOrderMail");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Bus-Invoice.html", ctx);
		}
		return  htmlContent;
	}
	
	public String sendInsuranceOrderMailInvoice(boolean b, boolean c, InsuranceOrderRow insuranceOrderRow, User user,
			Company company, String imagePngValue, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext,
			String imagePth, IWebContext ctx) {
		String htmlContent = null;
		if(company.isMyEmailDir()){
			String remotePath=CommonConfig.GetCommonConfig().getLog_location();
			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Insurance-Invoice.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Insurance-Invoice.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in sendInsuranceOrderMail");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Insurance-Invoice.html", ctx);
		}
		return  htmlContent;
	}
	
	public String sendVisaOrderMailInvoice(boolean b, boolean c, VisaOrderRow visaOrderRow, User user, Company company,
			String imagePngValue, Locale locale, HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, ApplicationContext applicationContext, String imagePth, IWebContext ctx) {
		String htmlContent = null;
		if(company.isMyEmailDir()){
			String remotePath=CommonConfig.GetCommonConfig().getLog_location();
			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Visa-Invoice.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Visa-Invoice.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in sendVisaOrderMail");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Visa-Invoice.html", ctx);
		}
		return  htmlContent;
	}

	public String sendCreditNoteIssueFlight(Company company, String imagePngValue, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext, IWebContext ctx) {
		String htmlContent = null;
		if(company.isMyEmailDir()){
			String corporateDirName = company.getCompany_userid();
			String remotePath=CommonConfig.GetCommonConfig().getLog_location();
			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Flight-Creditnote-Issued.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Flight-Creditnote-Issued.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in sendCreditNoteIssueFlight");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Flight-Creditnote-Issued.html", ctx);
		}
		return  htmlContent;

	}

	public String sendCreditNoteIssueHotel(Company company, String imagePngValue, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext, IWebContext ctx) {
		String htmlContent = null;
		if(company.isMyEmailDir()){
			String corporateDirName = company.getCompany_userid();
			String remotePath = CommonConfig.GetCommonConfig().getLog_location();
			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Hotel-Creditnote-Issued.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Hotel-Creditnote-Issued.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in sendCreditNoteIssueFlight");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Hotel-Creditnote-Issued.html", ctx);
		}
		return  htmlContent;
	}

	public String sendCreditNoteIssueCar(Company company, String imagePngValue, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext, IWebContext ctx) {
 		String htmlContent = null;
		if(company.isMyEmailDir()){
			String corporateDirName = company.getCompany_userid();
			String remotePath = CommonConfig.GetCommonConfig().getLog_location();
			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Car-CreditNote-Issued.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Car-CreditNote-Issued.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in Car-FlightCreditNote-Issued.html");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Car-CreditNote-Issued.html", ctx);
		}
		return  htmlContent;
	}

	public String sendCreditNoteIssueTrain(Company company, String imagePngValue, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext, IWebContext ctx) {
		String htmlContent = null;
		if(company.isMyEmailDir()){
			String corporateDirName = company.getCompany_userid();
			String remotePath = CommonConfig.GetCommonConfig().getLog_location();
			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Train-CreditNote-Issue.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Train-CreditNote-Issue.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in Train-FlightCreditNote-Issue.html");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Train-CreditNote-Issue.html", ctx);
		}
		return  htmlContent;
	}
	
	public String sendCreditNoteIssueBus(Company company, String imagePngValue, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext, IWebContext ctx) {
		String htmlContent = null;
		if(company.isMyEmailDir()){
			String corporateDirName = company.getCompany_userid();
			String remotePath=CommonConfig.GetCommonConfig().getLog_location();
			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Bus-CreditNote-Issued.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Bus-CreditNote-Issued.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in Bus-FlightCreditNote-Issued.html");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Bus-CreditNote-Issued.html", ctx);
		}
		return  htmlContent;
	}
	
	public String sendCreditNoteIssueInsurance(Company company, String imagePngValue, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext, IWebContext ctx) {
		String htmlContent = null;
		if(company.isMyEmailDir()){
			String corporateDirName = company.getCompany_userid();
			String remotePath = CommonConfig.GetCommonConfig().getLog_location();
			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Insurance-CreditNote-Issued.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Insurance-CreditNote-Issued.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in Insurance-CreditNote-Issued.html");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Insurance-CreditNote-Issued.html", ctx);
		}
		return  htmlContent;
	}

	public String sendCreditNoteIssueVisa(Company company, String imagePngValue, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext, IWebContext ctx) {
		String htmlContent = null;
		if(company.isMyEmailDir()){
			String corporateDirName = company.getCompany_userid();
			String remotePath = CommonConfig.GetCommonConfig().getLog_location();
			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Visa-CreditNote-Issued.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Visa-CreditNote-Issued.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in Visa-FlightCreditNote-Issued.html");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Visa-CreditNote-Issued.html", ctx);
		}
		return  htmlContent;
	}
	
	public String sendBusOrderMailVoucher(User user,
			Company company, String imagePngValue, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext, IWebContext ctx) {
		String htmlContent = null;
		if(company.isMyEmailDir()){
			String corporateDirName = company.getCompany_userid();
			String remotePath = CommonConfig.GetCommonConfig().getLog_location();
			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Bus-Voucher.html")){
											htmlContent  = this.templateEngine.process("Hotel-Voucher.html", ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent  = this.templateEngine.process("Bus-Voucher.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in sendBusOrderMail");
			}
		}
		else
		{
			htmlContent  = this.templateEngine.process("Bus-Voucher.html", ctx);
		}
		return  htmlContent;
	}
	
	public String sendMiscellaneousOrderMailInvoice(boolean b, boolean c, MiscellaneousOrderRow miscellaneousOrderRow, User user, Company company,
			String imagePngValue, Locale locale, HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext, ApplicationContext applicationContext, String imagePth, IWebContext ctx) {
		String htmlContent = null;
		if(company.isMyEmailDir()){
			String remotePath = CommonConfig.GetCommonConfig().getLog_location();
			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Miscellaneous-Invoice.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Miscellaneous-Invoice.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in sendMiscellaneousOrderMail");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Miscellaneous-Invoice.html", ctx);
		}
		return  htmlContent;
	}
	
	public String sendCreditNoteIssueMiscellaneous(Company company, String imagePngValue, Locale locale, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext, IWebContext ctx) {
		String htmlContent = null;
		if(company.isMyEmailDir()){
			String corporateDirName = company.getCompany_userid();
			String remotePath = CommonConfig.GetCommonConfig().getLog_location();
			try {
				//for remote path
				boolean emailFileFound= false;
				File sysdir = new File(remotePath+"/corporate/");
				File[] listOfFiles = sysdir.listFiles();
				if(listOfFiles!=null && listOfFiles.length>0){
					for (int i = 0; i < listOfFiles.length; i++) {
						if(listOfFiles[i].isDirectory()){
							if(listOfFiles[i].getName().equals(company.getCompany_userid())){
								File fileDir = new File(listOfFiles[i].getAbsolutePath());
								String[] files = fileDir.list();
								if(files!=null && files.length>0){
									for(String f:files){
										if(f.equalsIgnoreCase("Miscellaneous-CreditNote-Issued.html")){
											htmlContent  = this.templateEngine.process(company.getCompany_userid()+"/"+f, ctx);
											emailFileFound = true;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(!emailFileFound)
				{
					htmlContent = this.templateEngine.process("Miscellaneous-CreditNote-Issued.html", ctx);
				}
			} catch (Exception e) {
				logger.error("Exception in Miscellaneous-FlightCreditNote-Issued.html");
			}
		}
		else
		{
			htmlContent = this.templateEngine.process("Miscellaneous-CreditNote-Issued.html", ctx);
		}
		return  htmlContent;
	}
}