package com.tayyarah.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.Locale;
import java.util.Random;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.tayyarah.configuration.CommonConfig;


public class HTMLtoPDFConvertor 
{
	static final Logger logger = Logger.getLogger(HTMLtoPDFConvertor.class);

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
	public static String htmlRawToPDF(String mailContent, String baseFolder, String identifier,String id) {
		File optDir = new File(getLogDir()+File.separator+"email"+File.separator+baseFolder+File.separator+"pdf");
		if (!optDir.exists()) {
			optDir.mkdirs();
		}
		String path = optDir.getAbsolutePath() + File.separator + identifier +"_"+ id+".pdf";		
		logger.info("################### Email pdf---path-"+ path);		
		logger.info("################### Email pdf---body-"+ mailContent);	
		try {
			Document document = new Document(PageSize.A4);
			PdfWriter pdfWriter = PdfWriter.getInstance
					(document, new FileOutputStream(path));
			document.open();
			XMLWorkerHelper worker = XMLWorkerHelper.getInstance();			
			worker.parseXHtml(pdfWriter, document, new StringReader(mailContent));
			document.close();		
		}
		catch (Exception e) {
			logger.info("########################## Email pdf---EXCEPTION-"+e.getMessage());
			logger.error(e);
		}
		return path;
	}
	
	public static String createBulkInvoiceHtmlRawToPDFFolder(String mailContent, String baseFolder, String orderId) {
		File optDir = new File(baseFolder+File.separator+"temp");
		if (!optDir.exists()) {
			optDir.mkdirs();
		}
		String path = optDir.getAbsolutePath() + File.separator + orderId+".pdf";		
		logger.info("################### Email pdf---path-"+ path);		
		logger.info("################### Email pdf---body-"+ mailContent);	
		try {
			Document document = new Document(PageSize.A4);
			PdfWriter pdfWriter = PdfWriter.getInstance
					(document, new FileOutputStream(path));
			document.open();
			XMLWorkerHelper worker = XMLWorkerHelper.getInstance();			
			worker.parseXHtml(pdfWriter, document, new StringReader(mailContent));
			document.close();		
		}
		catch (Exception e) {
			logger.info("########################## Email pdf---EXCEPTION-"+e.getMessage());
			logger.error(e);
		}
		return path;
	}
	
	public static String createBulkInvoiceSinglePDFDownload(String mailContent, String baseFolder,String subFolder,String identifier) {
		File optDir = new File(baseFolder+File.separator+subFolder);
		if (!optDir.exists()) {
			optDir.mkdirs();
		}
		String path = optDir.getAbsolutePath() + File.separator + identifier +"_"+ String.valueOf(new Random().nextInt())+".pdf";		
		logger.info("################### Email pdf---path-"+ path);		
		logger.info("################### Email pdf---body-"+ mailContent);	
		try {
			Document document = new Document(PageSize.A4);
			PdfWriter pdfWriter = PdfWriter.getInstance
					(document, new FileOutputStream(path));
			document.open();
			XMLWorkerHelper worker = XMLWorkerHelper.getInstance();			
			worker.parseXHtml(pdfWriter, document, new StringReader(mailContent));
			document.close();		
		}
		catch (Exception e) {
			logger.info("########################## Email pdf---EXCEPTION-"+e.getMessage());
			logger.error(e);
		}
		return path;
	}
	
	
	
	
	public static String corporateHtmlRawToPDF(String mailContent, String baseFolder, String identifier,String id) {
		File optDir = new File(getLogDir()+File.separator+"corporate"+File.separator+baseFolder);
		if (!optDir.exists()) {
			optDir.mkdirs();
		}
		String path = optDir.getAbsolutePath() + File.separator + identifier +"_"+ id+".pdf";		
		logger.info("################### Email pdf---path-"+ path);		
		logger.info("################### Email pdf---body-"+ mailContent);		

		try {
			Document document = new Document(PageSize.A4);
			PdfWriter pdfWriter = PdfWriter.getInstance
					(document, new FileOutputStream(path));
			document.open();
			XMLWorkerHelper worker = XMLWorkerHelper.getInstance();		
			worker.parseXHtml(pdfWriter, document, new StringReader(mailContent));
			document.close();
		}
		catch (Exception e) {
			logger.info("########################## Email pdf---EXCEPTION-"+e.getMessage());
			logger.error(e);
		}
		return path;
	}
	
	public static   boolean deleteFiles(String srcFolder,String subFolder){
		boolean isDirEmpty=false;
		File file = new File(srcFolder+File.separator+subFolder);
		File[] listOfFiles = file.listFiles(); 
		if(listOfFiles!=null && listOfFiles.length>0){
			for (int i = 0; i < listOfFiles.length; i++) 
				if (listOfFiles[i].isFile()) 
				{
					String  fileName = listOfFiles[i].getName();
					new File(fileName).delete();
				}
		}

		if(file.listFiles()==null) 
			isDirEmpty=true;
		else if(listOfFiles!=null && listOfFiles.length>0) 
			isDirEmpty=true;

		return isDirEmpty;

	}

	
}
