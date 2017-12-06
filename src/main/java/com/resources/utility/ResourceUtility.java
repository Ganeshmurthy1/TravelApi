package com.resources.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.api.bulk.download.invoice.controller.BulkInvoiceDownloadController;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class ResourceUtility {
	public static final Logger logger = Logger.getLogger(BulkInvoiceDownloadController.class);
	public static File getFile(List<File> pdffiles,String srcFolder,String subFolder){
		File mergedpdffile = null;
		List<InputStream> pdfs = new ArrayList<InputStream>();
		try{
			if(pdffiles!=null && pdffiles.size()>0){
				for (File file:pdffiles) 
					pdfs.add(new FileInputStream(file));
				 
				File optDir = new File(srcFolder + File.separator + subFolder);
				if (!optDir.exists()) {
					optDir.mkdirs();
				}
				String path = optDir.getAbsolutePath() + File.separator + getDataTimeFormat()+ "_"+ String.valueOf(new Random().nextInt()) + ".pdf";			
				OutputStream output = new FileOutputStream(path);
				concatPDFs(pdfs, output, true);
				mergedpdffile = new File(path);	
			}
			
		}catch(Exception e){
			logger.info("Generate Pdf File Exception " + e);
		}
		return mergedpdffile;
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
	public static String  getDataTimeFormat(){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(date);
	}

	public  static boolean  isFileDownloadStream(File mergedPdfFile,HttpServletRequest request, HttpServletResponse response){
		boolean isFileDownLoaded=false;
		FileInputStream inputStream=null;
		OutputStream outStream=null;
		try {
			inputStream = new FileInputStream(mergedPdfFile);
			response.setContentType("application/pdf");
			response.setContentLength((int) mergedPdfFile.length());			
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", mergedPdfFile.getName());
			response.setHeader(headerKey, headerValue);			
			outStream = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;	
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			inputStream.close();
			outStream.close();
			isFileDownLoaded=true;
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.info("FileNotFoundException:" + e);
			isFileDownLoaded=false;
		}	
		catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("IOException:" + e);
			isFileDownLoaded=false;
		}
		return isFileDownLoaded;
	}

}
