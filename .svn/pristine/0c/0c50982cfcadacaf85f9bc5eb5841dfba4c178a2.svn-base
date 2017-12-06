package com.tayyarah.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tayyarah.configuration.CommonConfig;

public class FileUtil {
	static final Logger logger = Logger.getLogger(FileUtil.class);

	public static String getLogDir() {
		
		String dirName = CommonConfig.GetCommonConfig().getLog_location_windows();
		String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
		if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
			dirName = CommonConfig.GetCommonConfig().getLog_location_linux();
		} else if (OS.indexOf("win") >= 0) {
			dirName = CommonConfig.GetCommonConfig().getLog_location_windows();
		} else if (OS.indexOf("nux") >= 0) {
			dirName = CommonConfig.GetCommonConfig().getLog_location_linux();
		} else {
			dirName = CommonConfig.GetCommonConfig().getLog_location_windows();
		}		
		if(CommonConfig.GetCommonConfig().isIs_tayyarah_enabled())
			dirName = dirName+ File.separator + "tayyarah";
		else if(CommonConfig.GetCommonConfig().isIs_lintas_enabled())
			dirName = dirName+ File.separator + "lintas";
		return dirName;
	}
	
	public static boolean writeSoap(String apiType, String apiname, String actionname, Boolean isresponse, SOAPMessage soapmessage, String transId)
	{
		FileOutputStream fOut;			
		File optDir = new File(getLogDir()+ File.separator+apiType+ File.separator+apiname);
		if (!optDir.exists()) {
			optDir.mkdirs();
		}
	
		String filename = optDir.getAbsolutePath() + File.separator +transId+"-"+actionname+(isresponse?"-response":"-request")+".xml";
		try {			
			fOut = new FileOutputStream(filename);
			soapmessage.writeTo(fOut);			
			return true;
		}
		catch (FileNotFoundException e) {		
			logger.info("Log creation at---"+filename+"....FileNotFoundException."+e.getMessage());
			e.printStackTrace();
			return false;
		}
		catch (SOAPException e) {			
			logger.info("Log creation at---"+filename+"....SOAPException."+e.getMessage());
			e.printStackTrace();
			return false;
		}
		catch (IOException e) {			
			logger.info("Log creation at---"+filename+"....IOException."+e.getMessage());
			e.printStackTrace();
			return false;
		}
		catch (Exception e) {			
			logger.info("Log creation at---"+filename+"....Exception."+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean writeSoap(String apiType, String apiname, String actionname, Boolean isresponse, String soapmessage, String transId)
	{	
		transId = transId.replaceAll(":", "_").replaceAll("-", "_");		
		File optDir = new File(getLogDir()+ File.separator+apiType+ File.separator+apiname);
		if (!optDir.exists()) {
			optDir.mkdirs();
		}
		
		String filename = optDir.getAbsolutePath() + File.separator +transId+"-"+actionname+(isresponse?"-response":"-request")+".xml";
		BufferedWriter bufferedWriter = null;
		try {	            
			File myFile = new File(filename);
			
			// check if file exist, otherwise create the file before writing
			if (!myFile.exists()) {
				myFile.createNewFile();
			}
			Writer writer = new FileWriter(myFile);
			bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(soapmessage);
			logger.info("Log is created at---"+filename+".....");
		} catch (IOException e) {
			logger.info("Log creation at---"+filename+"....FileNotFoundException."+e.getMessage());
			e.printStackTrace();
		} finally{
			try{
				if(bufferedWriter != null) bufferedWriter.close();
			} catch(Exception ex){

			}
		}
		return true;
	}

	public static boolean writeJson(String apiType, String apiname, String actionname, Boolean isresponse, Object message, String transId)
	{
		ObjectMapper mapper = new ObjectMapper();
		FileOutputStream fOut;		
		File optDir = new File(getLogDir()+ File.separator+apiType+ File.separator+apiname);
		if (!optDir.exists()) {
			optDir.mkdirs();
		}
		
		String filename = optDir.getAbsolutePath() + File.separator+transId+"-"+actionname+(isresponse?"-response":"-request")+".json";
		try {	
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), message);			
			return true;
		}
		catch (FileNotFoundException e) {			
			logger.info("Log creation at---"+filename+"....FileNotFoundException."+e.getMessage());
			e.printStackTrace();
			return false;
		}
		catch (NullPointerException e) {			
			logger.info("Log creation at---"+filename+"....NullPointerException."+e.getMessage());

			e.printStackTrace();
			return false;
		}
		catch (Exception e) {		
			logger.info("Log creation at---"+filename+"....Exception."+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public static void createJsonResponses(Object o,String filename, String folderName){	
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File("E:\\FlightREQ@RES\\"+folderName+"\\"+filename+".json"),o);
		} catch (JsonGenerationException e) {e.printStackTrace();} catch (JsonMappingException e) {e.printStackTrace(); } catch (IOException e) {e.printStackTrace();}
	}	
}
