package com.tayyarah.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConversion {

	public static final org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(DateConversion.class);


	public static String getBluestarDate(String dateInString){
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			Date date = formatter1.parse(dateInString);
			SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
			dateInString=formatter2.format(date);

		} catch (Exception e) {
			logger.error("Exception",e);
		}
		return dateInString;
	}

	public static String getBluestarDateddMMMYYYY(String dateInString){
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

		try {

			Date date = formatter1.parse(dateInString);
			SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
			dateInString=formatter2.format(date);

		} catch (Exception e) {
			logger.error("Exception",e);
		}
		return dateInString;
	}

	public static String convertDDMMYYtoYYMMDD(String dateInString){
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

		try {

			Date date = formatter1.parse(dateInString);
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
			dateInString=formatter2.format(date);

		} catch (Exception e) {
			logger.error("Exception",e);
		}
		return dateInString;
	}

	public static String  convertDateToStringToDate(Date date){
		//System.out.println("date--"+date);

		String stringDate=null;

		try {

			SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
			stringDate=formatter1.format(date);
			//System.out.println("stringDate----"+stringDate);

		} catch (Exception e) {
			logger.error("Exception",e);

		}
		return stringDate;
	}

	public static String  convertDateToStringDatewirhDDMonthYear(Date date){
		//System.out.println("date--"+date);

		String stringDate=null;

		try {

			SimpleDateFormat formatter1 = new SimpleDateFormat("dd MMM yyyy");
			stringDate=formatter1.format(date);
			//System.out.println("stringDate----"+stringDate);

		} catch (Exception e) {
			logger.error("Exception",e);

		}
		return stringDate;
	}

	public static String  convertTimestampToString(Date date){
		//System.out.println("date--"+date);

		String stringDate=null;

		try {

			SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm aaa");
			stringDate=formatter1.format(date);
			//System.out.println("stringDate----"+stringDate);

		} catch (Exception e) {
			logger.error("Exception",e);

		}
		return stringDate;
	}
	public static String  convertDateToStringDate(Date date){
		//System.out.println("date--"+date);

		String stringDate=null;

		try {

			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			stringDate=formatter1.format(date);
			//System.out.println("stringDate----"+stringDate);

		} catch (Exception e) {
			logger.error("Exception",e);

		}
		return stringDate;
	}


	public static String  convertDateToStringToDateWithTIME(Date date){
		//System.out.println("date--"+date);

		String stringDate=null;

		try {

			SimpleDateFormat sdf = new SimpleDateFormat();
			//sdf.setTimeZone(new SimpleTimeZone(0, "GMT"));
			sdf.applyPattern("dd-MM-yyyy HH:mm aaa");
			stringDate=sdf.format(date);
			//System.out.println("stringDate----"+stringDate);

		} catch (Exception e) {
			logger.error("Exception",e);

		}
		return stringDate;
	}

	public static String  convertTimestampToString(Timestamp timeStamp){
		//System.out.println("date--"+date);

		String stringDate=null;

		try {
			stringDate = new SimpleDateFormat("dd/MM/yyyy HH:mm aaa").format(timeStamp);
		} catch (Exception e) {
			logger.error("Exception",e);

		}
		return stringDate;
	}

	public static String  convertTimestampToStringwithoutseconds(Timestamp timeStamp){
		//System.out.println("date--"+date);

		String stringDate=null;

		try {
			stringDate = new SimpleDateFormat("dd/MM/yyyy HH:mm aaa").format(timeStamp);
		} catch (Exception e) {
			logger.error("Exception",e);

		}
		return stringDate;

	}


	/*Compare Two Time Stamp and get Mintutes diff*/

	public static long compareTwoTimeStamps(java.sql.Timestamp currentTime, java.sql.Timestamp oldTime)
	{
		long milliseconds1 = oldTime.getTime();
		long milliseconds2 = currentTime.getTime();

		long diff = milliseconds2 - milliseconds1;
		long diffSeconds = diff / 1000;
		long diffMinutes = diff / (60 * 1000);
		long diffHours = diff / (60 * 60 * 1000);
		long diffDays = diff / (24 * 60 * 60 * 1000);

		return diffMinutes;
	}

	public static Date StringToDate(String date ){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date convertedCurrentDate=null;;
		try {
			convertedCurrentDate = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return convertedCurrentDate;	 
	}



	public static Date StringToDateIndiaFormat(String date ){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date convertedCurrentDate=null;;
		try {
			convertedCurrentDate = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return convertedCurrentDate;	 
	}
	public static Date StringToDateIndiaFormatWithDash(String date ){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");	
		Date convertedCurrentDate=null;;
		try {
			convertedCurrentDate = sdf.parse(date);			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		 
		return convertedCurrentDate;	 
	}

	public static Date StringDateTimeToDateTime(String date ){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date convertedCurrentDate=null;;
		try {
			convertedCurrentDate = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return convertedCurrentDate;	 
	}


	public static Date StringToDateTest(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date convertedCurrentDate=null;;
		try {
			convertedCurrentDate = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return convertedCurrentDate;  
	}

	public static String  convertDateToStringToDateWithTIMETEST(Date date){
		//System.out.println("date--"+date);

		String stringDate=null;

		try {

			SimpleDateFormat sdf = new SimpleDateFormat();
			//sdf.setTimeZone(new SimpleTimeZone(0, "GMT"));
			sdf.applyPattern("yyyy-MM-dd HH:mm");
			stringDate=sdf.format(date);
			//System.out.println("stringDate----"+stringDate);

		} catch (Exception e) {
			logger.error("Exception",e);

		}
		return stringDate;
	}

	public static String  convertDateToStringToDate(Date date,String format){
		//logger.info("date--"+date);

		String stringDate=null;

		try {

			SimpleDateFormat formatter1 = new SimpleDateFormat(format);
			stringDate=formatter1.format(date);
			//logger.info("stringDate----"+stringDate);

		} catch (Exception e) {
			logger.error("Exception",e);

		}
		return stringDate;
	}
	public static Date StringToDateInsurance(String date ){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date convertedCurrentDate=null;;
		try {
			convertedCurrentDate = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
		return convertedCurrentDate;	 
	}
	


}
