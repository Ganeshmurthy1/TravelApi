/**
@Author manish
22-dec-2016 
BluestarGetFlightAvailabilityXMLObjectCoversion.java
 */
/**
 * 
 */
package com.tayyarah.flight.util.api.bluestar;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.BluestarPriceData;
import com.tayyarah.flight.model.FlightSegments;
import com.tayyarah.flight.model.Flightsearch;
import com.tayyarah.flight.model.UAPISearchFlightKeyMap;



public class BluestarGetFlightPriceXMLObjectCoversion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(BluestarGetFlightPriceXMLObjectCoversion.class);

	public static BluestarPriceData createFlightPriceResponseFromXML(LinkedHashMap<String, FlightSegments> FlightSegmentstMap,
			ByteArrayOutputStream flightPriceByteStream, Flightsearch flightsearch,UAPISearchFlightKeyMap uapiSearchFlightKeyMap, String tempflightIndex) {
		BluestarPriceData bluestarPriceData = new BluestarPriceData();
		String flightPriceStringData=flightPriceByteStream.toString();
		Map<String ,String[]> faredetailMap = new HashMap<String, String[]>(); //uapiSearchFlightKeyMap.getFaredetailMap();
		Map<String, Integer> flightDetailsHeaderMap = new HashMap<String, Integer>();
		Map<String, Integer> fareDetailsHeaderMap = new HashMap<String, Integer>();
		flightPriceStringData=flightPriceStringData.replaceAll("&lt;", "<");flightPriceStringData=flightPriceStringData.replaceAll("&gt;", ">");
		if(flightPriceStringData.indexOf("<FlightDetails>")==-1)
		{
			throw new FlightException(ErrorCodeCustomerEnum.JAXBException,FlightErrorMessages.NO_AIRPRICE);
		}
		else
		{
			String flightDetails=flightPriceStringData.substring(flightPriceStringData.indexOf("<FlightDetails>")+16,flightPriceStringData.indexOf("</FlightDetails>")-1);
			List<String> flightdetaillist=new ArrayList<String>();			
			String flightDetailsTemp1=flightDetails;
			String flightdetailHeader = flightDetailsTemp1.substring(1, flightDetailsTemp1.indexOf("]"));	
			List<String[]> flightdeatilArraylist = new ArrayList<String[]>();
			if (flightDetailsTemp1!=null && flightDetailsTemp1.length()>0 && flightDetailsTemp1.indexOf("" + flightdetailHeader + "],") != -1) {
				flightDetailsTemp1 = flightDetailsTemp1.substring(flightdetailHeader.length() + 3);
				String[] header = flightdetailHeader.split(",");                      
				for(int i=0;i<header.length;i++){
					Integer index = i;
					String name = header[i];
					flightDetailsHeaderMap.put(name, index);
				}
			} else {
				flightDetailsTemp1 = "";
			}
			while(flightDetailsTemp1!=null && flightDetailsTemp1.length()>0 && flightDetailsTemp1.length()>3){
				String flightdetail=flightDetailsTemp1.substring(1,flightDetailsTemp1.indexOf("]"));
				if(flightDetailsTemp1.indexOf(""+flightdetail+"],")!=-1){
					flightDetailsTemp1=flightDetailsTemp1.substring(flightdetail.length()+3);
				}else{
					flightDetailsTemp1="";
				}
				String[] flightdeatilArrayTEmp = flightdetail.split(",");
				flightdeatilArraylist.add(flightdeatilArrayTEmp);
				flightdetaillist.add(flightdetail);
			}	
			String[] flightdeatilArray=getNewFlightDetailfromList(flightdeatilArraylist,flightDetailsHeaderMap);
			String faredetails=flightPriceStringData.substring(flightPriceStringData.indexOf("<FareDetails>")+14,flightPriceStringData.indexOf("</FareDetails>")-1);
			List<String> faredetaillist=new ArrayList<String>();
			String fareDetailsTemp=faredetails;
			String fareDetailsHeader = faredetails.substring(1, fareDetailsTemp.indexOf("]"));	
			if (fareDetailsTemp!=null && fareDetailsTemp.length()>0 && fareDetailsTemp.indexOf("" + fareDetailsHeader + "],") != -1) 
			{
				fareDetailsTemp = fareDetailsTemp.substring(fareDetailsHeader.length() + 3);
				String[] header = fareDetailsHeader.split(",");                      
				for(int i=0;i<header.length;i++)
				{
					Integer index = i;
					String name = header[i];
					fareDetailsHeaderMap.put(name, index);
				}
			} else {
				fareDetailsTemp = "";
			}
			while(fareDetailsTemp.length()>3)
			{
				String faredetail=fareDetailsTemp.substring(1,fareDetailsTemp.indexOf("]"));
				if(fareDetailsTemp.indexOf(""+faredetail+"],")!=-1)
				{
					fareDetailsTemp=fareDetailsTemp.substring(faredetail.length()+3);
				}
				else
				{
					fareDetailsTemp="";
				}
				faredetaillist.add(faredetail);
			}	
			if(faredetaillist!=null && faredetaillist.size()>0)
			{
				String[] faredeatilArray = faredetaillist.get(0).split(",");
				for(int  i=0;i<faredetaillist.size();i++)
				{
					String faredeatil = faredetaillist.get(i);
					String key = faredeatil.substring(0, faredeatil.indexOf(","));
					if (i != faredetaillist.size() - 1) 
					{
						String Nextkey = faredetaillist.get(i + 1).substring(0,faredeatil.indexOf(","));
						if (key.substring(0, key.length() - 1).equals(Nextkey.substring(0, Nextkey.length() - 1)))
						{
							String faredetailTEMP = getNewFareDetailfromList(faredeatil,faredetaillist.get(i + 1));
							faredeatilArray = faredetailTEMP.split(",");
							i++;
						} 
						else
						{
							faredeatilArray = faredeatil.split(",");
						}
					} 
					else 
					{
						faredeatilArray = faredeatil.split(",");
					}
				}
				faredetailMap.put(faredeatilArray[fareDetailsHeaderMap.get(BluestarConstantsKeyMap.APSrNo)], faredeatilArray);
			}
			String flightdeatil=flightdetaillist.get(0);
			String[] data = flightdeatil.split(",");  
			Integer tracknoindex = flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TrackNo);
			String trackNo = data[tracknoindex];		
			FlightSegmentstMap.get(tempflightIndex).getSegments().get(0).setTrackno(trackNo);
			bluestarPriceData.setFlightdeatilArray(flightdeatilArray);
			bluestarPriceData.setFaredetailMap(faredetailMap);
			bluestarPriceData.setFareDetailsHeaderMap(fareDetailsHeaderMap);
			bluestarPriceData.setFlightDetailsHeaderMap(flightDetailsHeaderMap);
		}
		return bluestarPriceData;
	}

	public static String getNewFareDetailfromList(String flightdetail,
			String flightdetailTEmp) {
		String[] flightdeatilArray1 = flightdetail.split(",");
		String[] flightdeatilArray2 = flightdetailTEmp.split(",");
		StringBuilder sb1 = new StringBuilder();
		for (int i = 0; i < flightdeatilArray1.length; i++) {
			if (i != 0) {
				BigDecimal newtotal = new BigDecimal(flightdeatilArray1[i])
						.add(new BigDecimal(flightdeatilArray2[i]));
				sb1.append(newtotal.toString());
			} else {
				sb1.append(flightdeatilArray1[i]);
			}
			if (i < flightdeatilArray1.length - 1) {
				sb1.append(",");
			}
		}
		return sb1.toString();
	}

	public static String[] getNewFlightDetailfromList(List<String[]> flightdetailArray, Map<String, Integer> flightDetailsHeaderMap) {
		StringBuilder sb1 = new StringBuilder();
		BigDecimal newtotal = new BigDecimal("0.00");
		BigDecimal newtax = new BigDecimal("0.00");
		for (int i = 0; i < flightdetailArray.size(); i++) {
			String[] flightdeatilArray=flightdetailArray.get(i);
			newtotal = newtotal.add(new BigDecimal(flightdeatilArray[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.APTotalAmount)]));
			newtax = newtax.add(new BigDecimal(flightdeatilArray[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.APTaxAmount)]));
		}
		String[] flightdeatilArray=flightdetailArray.get(0);
		flightdeatilArray[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.APTotalAmount)]=newtotal.toString();
		flightdeatilArray[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.APTaxAmount)]=newtax.toString();
		return flightdeatilArray;
	}	
}