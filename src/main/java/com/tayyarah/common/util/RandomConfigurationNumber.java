package com.tayyarah.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;



public class RandomConfigurationNumber {
	private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final int RANDOM_STRING_LENGTH = 10;
	public  static String generateRandomTxID(){		
		final SimpleDateFormat sdfDate = new SimpleDateFormat("yyMMddHHmmssS");
		Date now = new Date();
		final String strDate = sdfDate.format(now);
		new Thread(new Runnable() {
			@Override
			public void run() {				
				try {
					Thread.sleep(1);					
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
			}
		}).start();
		return "TX"+strDate;	
	}

	public  static String generateBugReferenceNumber(Long id){		
		final SimpleDateFormat sdfDate = new SimpleDateFormat("yyMMddHHmmssS");
		Date now = new Date();
		final String strDate = sdfDate.format(now);
		new Thread(new Runnable() {
			@Override
			public void run() {			
				try {
					Thread.sleep(1);				
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
			}
		}).start();
		return "BUG_"+String.valueOf(id)+"_"+strDate;	
	} 

	public  static Long generateTripId(Long id){		
		long count = 100;
		id = count+id;		
		return id;
	} 

	public  static String generateHotelOfflineBookingNumber(){		
		final SimpleDateFormat sdfDate = new SimpleDateFormat("yyMMddHHmmssS");
		Date now = new Date();
		final String strDate = sdfDate.format(now);
		new Thread(new Runnable() {
			@Override
			public void run() {			
				try {
					Thread.sleep(1);					
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
			}
		}).start();
		return "HOF"+strDate;	
	} 

	public  static String generateHotelOfflineConfirmationNumber(){		
		final SimpleDateFormat sdfDate = new SimpleDateFormat("yyMMddHHmmssS");
		Date now = new Date();
		final String strDate = sdfDate.format(now);
		new Thread(new Runnable() {
			@Override
			public void run() {				
				try {
					Thread.sleep(1);					
				} catch (InterruptedException e) {				
					e.printStackTrace();
				}
			}
		}).start();
		return "TYH"+strDate;	
	} 

	public static String generateHotelandFlightPaymentTxKey(){	
		final SimpleDateFormat sdfDate = new SimpleDateFormat("yyMMddHHmmssS");
		Date now = new Date();
		final String strDate = sdfDate.format(now);
		new Thread(new Runnable() {
			@Override
			public void run() {				
				try {
					Thread.sleep(1);				
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
			}
		}).start();
		return "TK-"+strDate;	
	} 

	public  static String generateHotelOfflineInvoiceNumber(long id){
		return "TYH"+String.valueOf(id);	
	}

	public  static String generateFlightOfflineInvoiceNumber(long id){
		return "TYA"+String.valueOf(id);	
	}

	public  static String generateCarInvoiceNumber(long id){
		return "TYC"+String.valueOf(id);	
	}

	public  static String generateBusInvoiceNumber(long id){
		return "TYB"+String.valueOf(id);	
	}

	public  static String generateTrainInvoiceNumber(long id){
		return "TYT"+String.valueOf(id);	
	}

	public  static String generateInsurenceInvoiceNumber(long id){
		return "TYI"+String.valueOf(id);	
	}

	public  static String generateVisaInvoiceNumber(long id){
		return "TYV"+String.valueOf(id);	
	}

	public static String generateFlightOfflineBookingNumber() {		
		final SimpleDateFormat sdfDate = new SimpleDateFormat("yyMMddHHmmssS");
		Date now = new Date();
		final String strDate = sdfDate.format(now);
		new Thread(new Runnable() {
			@Override
			public void run() {				
				try {
					Thread.sleep(1);					
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
			}
		}).start();
		return "FOF"+strDate;	
	} 

	public static String generateConfigNumber(String configID){
		String randomString = null;
		switch(configID.length()){
		case 1: 
			randomString = "GIFNOC";			
			break;
		case 2: 
			randomString = "GIFNO";			
			break;
		case 3: 
			randomString = "GIFN";			
			break;
		case 4: 
			randomString = "GIF";			
			break;
		case 5: 
			randomString = "GI";			
			break;
		case 6: 
			randomString = "G";		
			break;
		default: 
			randomString = "";		
			break;
		}
		return randomString.concat(configID);
	} 

	public static String generateCreditNoteInvoiceNumber(String configID,String creditNoteType){
		String randomString = null;
		String CNType = null;

		switch(configID.length()){
		case 1: 
			randomString = "000000";		
			break;
		case 2: 
			randomString = "00000";			
			break;
		case 3: 
			randomString = "0000";			
			break;
		case 4: 
			randomString = "000";			
			break;
		case 5: 
			randomString = "00";			
			break;
		case 6: 
			randomString = "0";			
			break;
		default: 
			randomString = "";			
			break;
		}
		if(creditNoteType.equals("F")){
			CNType=creditNoteType;
		}
		if(creditNoteType.equals("H")){
			CNType=creditNoteType;
		}
		return CNType+"CNI"+randomString.concat(configID);
	} 

	public static String getEncryptedEmailCode(String configID){
		return new encryptions().encrypt(configID);
	} 

	public static String generateRandomString(){
		StringBuffer randStr = new StringBuffer();
		for(int i=0; i<RANDOM_STRING_LENGTH; i++){
			int number = getRandomNumber();
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}
	private static int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(CHAR_LIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}
	public static String randomFrontUserPassword(){		
		String password = generateRandomString();
		return password;
	}
}
