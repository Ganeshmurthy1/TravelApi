package com.tayyarah.common.util;

import java.io.File;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.itextpdf.text.log.SysoCounter;
import com.tayyarah.bus.entity.BusOrderRow;
import com.tayyarah.company.entity.Company;
import com.tayyarah.configuration.CommonConfig;
import com.tayyarah.email.entity.model.Email;
import com.tayyarah.flight.entity.FlightOrderRow;
import com.tayyarah.hotel.entity.HotelOrderRow;
import com.tayyarah.user.entity.User;




public class LogsRequestResponse {
	public static final Logger logger = Logger.getLogger(LogsRequestResponse.class); 
	@Autowired
	private JavaMailSender mailSender; 
	public boolean sendLogsToTeamLeads(final FlightOrderRow flightorder,final HotelOrderRow hotelorder,final BusOrderRow busorder,final User user, final Locale locale,Email email,final Company company, HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext, ApplicationContext applicationContext,MimeMessage mimeMessage, MimeMessageHelper message)throws MessagingException, MailException, Exception, NullPointerException {

		CommonConfig cc = CommonConfig.GetCommonConfig();
		String apiProvider = flightorder.getProviderAPI();
		if(flightorder != null){
			String fileName = CommonConfig.GetCommonConfig().getLog_location_linux()+"/tayyarah/flight/";		
			String[] search_key = flightorder.getTransaction_key().split(":");		
			File folder = new File(fileName);
			//File folder = new File("C://Users/HARSHA//Desktop//options//provider_req_res//tayyarah//flight");
			File[] listOfFiles = folder.listFiles();
			if(listOfFiles!=null && listOfFiles.length>0){			
				for (int i = 0; i < listOfFiles.length; i++) {		    	
					if(listOfFiles[i].isDirectory()){
						if(listOfFiles[i].getName().equals(flightorder.getProviderAPI())){ 
							File fileDir = new File(listOfFiles[i].getAbsolutePath());
							String[] files = fileDir.list(); 
							if(files!=null && files.length>0){ 
								for(String f:files){ 
									if(f.equalsIgnoreCase(String.valueOf( search_key[1]+search_key[2])+"-LCCBook-request.json"))/*+"-LCCBook-request.json"*/
									{
										FileSystemResource jsonFile = new FileSystemResource(fileDir+"/"+f);
										getmessage(message, "Flight LCCBook-request", jsonFile);																	
										break;
									}else if(f.equalsIgnoreCase(String.valueOf( search_key[1]+search_key[2])+"-LCCBook-response.json")){
										FileSystemResource jsonFile = new FileSystemResource(fileDir+"/"+f);
										getmessage(message, "Flight LCCBook-response", jsonFile);									
										break;
									}else{

									}
									if(f.equalsIgnoreCase(String.valueOf( search_key[1]+search_key[2])+"-NONLCCBook-response.json")){
										FileSystemResource jsonFile = new FileSystemResource(fileDir+"/"+f);
										getmessage(message, "Flight NONLCCBook-response", jsonFile);									
										break;
									}else if(f.equalsIgnoreCase(String.valueOf( search_key[1]+search_key[2])+"-NONLCCBook-request.json")){
										FileSystemResource jsonFile = new FileSystemResource(fileDir+"/"+f);
										getmessage(message, "Flight NONLCCBook-request", jsonFile);									
										break;
									}
									else{

									}
								}
							}
						}
					}
				}
			}
		}
		if(hotelorder != null){
			String fileName = CommonConfig.GetCommonConfig().getLog_location_linux()+"/tayyarah/hotel/";		
			String search_key = String.valueOf(hotelorder.getSearchKey());	
			File folder = new File(fileName);
			//File folder = new File("C://Users/HARSHA//Desktop//options//provider_req_res//tayyarah//flight");
			File[] listOfFiles = folder.listFiles();
			if(listOfFiles!=null && listOfFiles.length>0){			
				for (int i = 0; i < listOfFiles.length; i++) {		    	
					if(listOfFiles[i].isDirectory()){
						if(listOfFiles[i].getName().equals(hotelorder.getApiProvoder())){ 
							File fileDir = new File(listOfFiles[i].getAbsolutePath());
							String[] files = fileDir.list(); 
							if(files!=null && files.length>0){ 
								for(String f:files){ 
									if(f.equalsIgnoreCase(search_key+"-book-request.json"))/*+"-LCCBook-request.json"*/
									{
										FileSystemResource jsonFile = new FileSystemResource(fileDir+"/"+f);
										getmessage(message, "Hotel Book-request", jsonFile);																	
										break;
									}else if(f.equalsIgnoreCase(search_key+"-book-response.json")){
										FileSystemResource jsonFile = new FileSystemResource(fileDir+"/"+f);
										getmessage(message, "Hotel Book-response", jsonFile);									
										break;
									}else{

									}

								}
							}
						}
					}
				}
			}
		}
		if(busorder != null){
			String fileName = CommonConfig.GetCommonConfig().getLog_location_linux()+"/tayyarah/bus/";		
			String search_key = String.valueOf(busorder.getTransactionKey());	
			File folder = new File(fileName);
			//File folder = new File("C://Users/HARSHA//Desktop//options//provider_req_res//tayyarah//flight");
			File[] listOfFiles = folder.listFiles();
			if(listOfFiles!=null && listOfFiles.length>0){			
				for (int i = 0; i < listOfFiles.length; i++) {		    	
					if(listOfFiles[i].isDirectory()){
						if(listOfFiles[i].getName().equals(busorder.getSupplierName())){ 
							File fileDir = new File(listOfFiles[i].getAbsolutePath());
							String[] files = fileDir.list(); 
							if(files!=null && files.length>0){ 
								for(String f:files){ 
									if(f.equalsIgnoreCase(search_key+"-confirm-request.json"))/*+"-LCCBook-request.json"*/
									{
										FileSystemResource jsonFile = new FileSystemResource(fileDir+"/"+f);
										getmessage(message, "Bus Book-request", jsonFile);																	
										break;
									}else if(f.equalsIgnoreCase(search_key+"-confirm-response.json")){
										FileSystemResource jsonFile = new FileSystemResource(fileDir+"/"+f);
										getmessage(message, "Bus Book-response", jsonFile);									
										break;
									}else{

									}

								}
							}
						}
					}
				}
			}
		}

		return true;
	}
	public MimeMessageHelper getmessage(MimeMessageHelper message,String subject,FileSystemResource jsonFile){
		try{
			message.setSubject(subject);
			message.setTo("yogesh@intellicommsolutions.com");
			//message.setCc("vimalsvsr@gmail.com");
			message.addAttachment("TechLead and CTO",jsonFile);
			System.out.println("TechLead and CTO"+jsonFile.toString());
		}catch(Exception e){
			logger.info("MimeMessageHelper getmessage "+e);
		}
		return message;
	}

}
