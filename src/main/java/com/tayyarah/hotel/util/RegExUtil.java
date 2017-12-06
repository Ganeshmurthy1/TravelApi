package com.tayyarah.hotel.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class RegExUtil {

	public static final String PATTERN_LATITUDE_REGEX = "(?i)(?:[0-8]\\d|90)(?:[0-5]\\d)(?:[0-5]\\d)[NS]";
	public static final String PATTERN_LONGITUDE_REGEX = "(?:0\\d{2}|1[0-7]\\d|180)(?:[0-5]\\d)(?:[0-5]\\d)[EW]";
	public static final String PATTERN_LINE_SPECIAL_CHARS = "[[ ]*|[,]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*|[-]*]+";
	static final Logger logger = Logger.getLogger(RegExUtil.class);

	public RegExUtil() {
		// TODO Auto-generated constructor stub
	}
	public static boolean isValidLatitude(String latitude)
	{
		// the same regex with ignore case flag activated
		// final String LATITUDE_REGEX = "(?i)(?:[0-8]\\d|90)(?:[0-5]\\d)(?:[0-5]\\d)[NS]"; 
		if(latitude == null || latitude.length() == 0)
		{
			//logger.info("---**************, given string is null and , a not a valid lattitude-"+latitude);	
			return false;
		}
		if (latitude.matches(PATTERN_LATITUDE_REGEX)) {
			// We have a latitude ...
			//logger.info("--#############-its ok, givenn string can be a lattitude"+latitude);
			
			return true;
		} else {
			// This is not a latitude...
			//logger.info("---**************, given string is a not a valid lattitude-"+latitude);	
			return false;
		}
	}
	public static boolean isValidLongitude(String longitude)
	{
		// the same regex with ignore case flag activated
		// final String LATITUDE_REGEX = "(?i)(?:[0-8]\\d|90)(?:[0-5]\\d)(?:[0-5]\\d)[NS]"; 
		if(longitude == null || longitude.length() == 0)
		{
			//logger.info("---**************, given string is null and , a not a valid longitude-"+longitude);	
			return false;
		}
		if (longitude.matches(PATTERN_LONGITUDE_REGEX)) {
			// We have a latitude ...
			//logger.info("--#############-its ok, given string can be a longitude"+longitude);
			return true;
		} else {
			// This is not a latitude...
			//logger.info("---**************, given string is a not a valid longitude-"+longitude);		
			return false;
		}
	}
	public static boolean isValidLAT(String LAT) {
	    String LAT_PATTERN = "(-?[0-8]?[0-9](\\.\\d*)?)|-?90(\\.[0]*)?";
	    Pattern pattern = Pattern.compile(LAT_PATTERN);
	    Matcher matcher = pattern.matcher(LAT);
	    return matcher.matches();
	}


	public static boolean isValidLONG(String LONGITUDE) {
	    String LONG_PATTERN = "(-?([1]?[0-7][1-9]|[1-9]?[0-9])?(\\.\\d*)?)|-?180(\\.[0]*)?";
	    Pattern pattern = Pattern.compile(LONG_PATTERN);
	    Matcher matcher = pattern.matcher(LONGITUDE);
	    return matcher.matches();

	}
	public static boolean isSamePosition(String latStr1, String lngStr1, String latStr2, String lngStr2) {
		//Distance range to avoid longitude and latitude small variations
		float distRange = 50;//10 meters
		if(isValidLAT(latStr1) && isValidLAT(latStr2) && isValidLONG(lngStr1) && isValidLONG(lngStr2))
		{
			//logger.info("----valid logitudes and latitudes..");
			float lat1 = Float.parseFloat(latStr1);
			float lng1 = Float.parseFloat(lngStr1);
			float lat2 = Float.parseFloat(latStr2);
			float lng2 = Float.parseFloat(lngStr2);

			double earthRadius = 6371000; //meters
			double dLat = Math.toRadians(lat2-lat1);
			double dLng = Math.toRadians(lng2-lng1);
			double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
					Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
					Math.sin(dLng/2) * Math.sin(dLng/2);
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
			float dist = (float) (earthRadius * c);
			
			//logger.info("---distance between tow positions."+dist);
			if(dist <= distRange)
			{
				//logger.info("--#############-its ok, two positions are same , or within considerable distance range--hotels should be same-"+dist);
				return true;
			}
			else
			{
				//logger.info("---**************, two positions are not within considerable distance range--hotels should be different-"+dist);				
				return false;
			}
		}
		else
		{
			return false;
		}

	}
	

	public static boolean isSimilarLines(String line1, String line2) {
		//Distance range to avoid longitude and latitude small variations
		//String s = "I want to walk my dog";
		//String l = "sofia, malgré tout aimait : la laitue et le choux !";
		//l.split("[[ ]*|[,]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+");
		String[] line1Tokens = line1.split(PATTERN_LINE_SPECIAL_CHARS);   
		String[] line2Tokens = line2.split(PATTERN_LINE_SPECIAL_CHARS); 
		//logger.info("----line 1 tokens.."+Arrays.toString(line1Tokens));
		//logger.info("----line 2 tokens.."+Arrays.toString(line2Tokens));
		Arrays.sort(line1Tokens);
		Arrays.sort(line2Tokens);
		//logger.info("----sorted line 1 tokens.."+Arrays.toString(line1Tokens));
		//logger.info("----sorted line 2 tokens.."+Arrays.toString(line2Tokens));
		
		if(line1Tokens.length != line2Tokens.length)
		{
			return false;
		}
		else
		{			
			if(Arrays.equals(line1Tokens, line2Tokens))
			{
				//logger.info("-----#############,...-Lines tokens are same in literals / words.,  should be same hotels.");				
				return true;
			}
			else
			{
				//logger.info("-----**************,...-Lines tokens are different in literals/ words,  should be different hotels.");				
				return false;
			}
		}
		 
	}
	


	
	
	
}
