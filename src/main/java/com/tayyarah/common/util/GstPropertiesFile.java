package com.tayyarah.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;
import org.apache.log4j.Logger;

public class GstPropertiesFile  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	public static Logger logger = Logger.getLogger(GstPropertiesFile.class);	
	private String companyType = "tayyarah";
	
	public Boolean getGstSwitchonValue(){
		Boolean isLintasGst = false;
		InputStream inputStream = null;
		try {
			Properties properties=new Properties();
			String propFileName="GstEnable.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			// get the property value and print it out
			String lintasGst = properties.getProperty("isLintasGst");
			logger.info("-----lintas property Gst---"+lintasGst);
			if(lintasGst.equalsIgnoreCase("true")){
				isLintasGst=true;
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isLintasGst;
	}
	
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}	
}