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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.flight.exception.FlightErrorMessages;
import com.tayyarah.flight.exception.FlightException;
import com.tayyarah.flight.model.BluestarSearchData;
import com.tayyarah.flight.model.Flightsearch;



public class BluestarGetFlightAvailabilityXMLObjectCoversion {
	static Logger logger = Logger.getLogger(BluestarGetFlightAvailabilityXMLObjectCoversion.class);

	public static BluestarSearchData createSearchFlightResponseFromXML(
			ByteArrayOutputStream flightSearchXMlDataStream, Flightsearch flightsearch) {
		BluestarSearchData bluestarSearchData = new BluestarSearchData();
		String flightSearchXMlDataString = flightSearchXMlDataStream.toString();
		flightSearchXMlDataString = flightSearchXMlDataString.replaceAll("&lt;", "<");
		flightSearchXMlDataString = flightSearchXMlDataString.replaceAll("&gt;", ">");		
		if(flightSearchXMlDataString.contains("<FlightDetails>")){	
			String flightdetails = flightSearchXMlDataString.substring(
					flightSearchXMlDataString.indexOf("<FlightDetails>") + 16,
					flightSearchXMlDataString.indexOf("</FlightDetails>") - 1);

			String FareDetails = flightSearchXMlDataString.substring(
					flightSearchXMlDataString.indexOf("<FareDetails>") + 14,
					flightSearchXMlDataString.indexOf("</FareDetails>") - 1);

			List<String> flightdetaillist = new ArrayList<String>();
			String temp = flightdetails;
			String flightdetailHeader = temp.substring(1, temp.indexOf("]"));
			String faretemp = FareDetails;
			String flightFaredetailHeader = faretemp.substring(1, faretemp.indexOf("]"));
			Map<String, Integer> flightFareDetailsHeaderMap = new HashMap<String, Integer>();

			// create the header here for flight fare data 
			if (faretemp.indexOf("" + flightFaredetailHeader + "],") != -1) {
				faretemp = faretemp.substring(flightFaredetailHeader.length() + 3);
				String[] header = flightFaredetailHeader.split(",");                      
				for(int i = 0 ; i < header.length ; i++){                        	
					String name = header[i];
					flightFareDetailsHeaderMap.put(name, i);			
				}
			} else {
				faretemp = "";
			}
			Map<String, Integer> flightDetailsHeaderMap = new HashMap<String, Integer>();
			Map<Integer, String> flightDetailsHeaderMapAsIndexkey = new HashMap<Integer, String>();
			
			// create the header here for flight segment data 
			if (temp.indexOf("" + flightdetailHeader + "],") != -1) {
				temp = temp.substring(flightdetailHeader.length() + 3);
				String[] header = flightdetailHeader.split(",");                      
				for(int i = 0 ; i < header.length ; i++){                        	
					String name = header[i];
					flightDetailsHeaderMap.put(name, i);
					flightDetailsHeaderMapAsIndexkey.put(i, name);
				}
			} else {
				temp = "";
			}
			flightdetaillist.add(flightdetailHeader);
			int m =0;
			while (temp !=null && temp.length() > 3) {
				String flightdetail = temp.substring(1, temp.indexOf("]"));
				if (temp.indexOf("" + flightdetail + "],") != -1) {
					temp = temp.substring(flightdetail.length() + 3);
				} else {
					temp = "";
				}
				String[] flightdeatilArrayTEmp = flightdetail.split(",");
				if (flightsearch.getTripType().equalsIgnoreCase("O")) {
					flightdetaillist.add(flightdetail);
					m++;
				}
				else {
					if(temp!=null && temp.length()>0)
					{
						String flightdetailTEmp = temp.substring(1, temp.indexOf("]"));
						if (temp.indexOf("" + flightdetailTEmp + "],") != -1) {
							temp = temp.substring(flightdetailTEmp.length() + 3);
							String[] header = flightdetailHeader.split(",");                      
							for(int i = 0 ; i < header.length ; i++){                        	
								String name = header[i];
								flightDetailsHeaderMap.put(name, i);
								flightDetailsHeaderMapAsIndexkey.put(i, name);
							}
						} else {
							temp = "";
						}
						String[] flightdeatilArrayTEmp2 = flightdetailTEmp.split(",");
						List<String> tempflightlist = new ArrayList<String>();
						if (flightdeatilArrayTEmp[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]
								.equals(flightdeatilArrayTEmp2[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)])) {
							tempflightlist.add(flightdetailTEmp);
							String roundSegmemt = "";
							while (temp !=null && temp.length() > 0) {
								String flightdetailTEmp3 = temp.substring(1,
										temp.indexOf("]"));								
								if (temp.indexOf("" + flightdetailTEmp3 + "],") != -1) {
									temp = temp.substring(flightdetailTEmp3.length() + 3);
								} else {
									temp = "";
								}
								String[] flightdeatilArrayTEmp3 = flightdetailTEmp3
										.split(",");
								if (flightdeatilArrayTEmp[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)]
										.equals(flightdeatilArrayTEmp3[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.SrNo)])) {
									tempflightlist.add(flightdetailTEmp3);
								} else {
									roundSegmemt = flightdetailTEmp3;
									break;
								}
							}
							List<String> flightdetaillistTEMP = getNewFlightDetail3(
									flightdetail, tempflightlist, roundSegmemt,flightDetailsHeaderMap);
							if(flightdetaillistTEMP!=null && flightdetaillistTEMP.size()>0)
							{
								for (String NewFlightDetailNEW : flightdetaillistTEMP) {
									flightdetaillist.add(NewFlightDetailNEW);
								}
							}
						
							String[] flightdeatilArrayTEmp3 = roundSegmemt.split(",");
							if (!flightdeatilArrayTEmp3[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]
									.equals(flightdeatilArrayTEmp[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)])) {
								String flightdetailTEmp4 = temp.substring(1,
										temp.indexOf("]"));
								if (temp.indexOf("" + flightdetailTEmp4 + "],") != -1) {
									temp = temp.substring(flightdetailTEmp4.length() + 3);
								} else {
									temp = "";
								}
								flightdetaillist.add(flightdetailTEmp4);

								String[] flightdeatilArrayTEmp4 = flightdetailTEmp4
										.split(",");
								if (!flightdeatilArrayTEmp4[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]
										.equals(flightdeatilArrayTEmp[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)])) {
									String flightdetailTEmp5 = temp.substring(1,
											temp.indexOf("]"));
									if (temp.indexOf("" + flightdetailTEmp5 + "],") != -1) {
										temp = temp.substring(flightdetailTEmp5.length() + 3);
									} else {
										temp = "";
									}
									flightdetaillist.add(flightdetailTEmp5);
									String[] flightdeatilArrayTEmp5 = flightdetailTEmp5
											.split(",");
									if (!flightdeatilArrayTEmp5[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]
											.equals(flightdeatilArrayTEmp[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)])) {
										String flightdetailTEmp6 = temp.substring(1,
												temp.indexOf("]"));
										if (temp.indexOf("" + flightdetailTEmp6 + "],") != -1) {
											temp = temp.substring(flightdetailTEmp6.length() + 3);
										} else {
											temp = "";
										}
										flightdetaillist.add(flightdetailTEmp6);
										String[] flightdeatilArrayTEmp6 = flightdetailTEmp6
												.split(",");
										if (!flightdeatilArrayTEmp6[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]
												.equals(flightdeatilArrayTEmp[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)])) {
											String flightdetailTEmp7 = temp.substring(
													1, temp.indexOf("]"));
											if (temp.indexOf("" + flightdetailTEmp7
													+ "],") != -1) {
												temp = temp.substring(flightdetailTEmp7.length() + 3);
											} else {
												temp = "";
											}
											flightdetaillist.add(flightdetailTEmp7);
										}
									}
								}
							}
						} else {
							List<String> flightdetaillistTEMP = getNewFlightDetail(flightdetail, flightdetailTEmp,flightDetailsHeaderMap);
							if(flightdetaillistTEMP!=null && flightdetaillistTEMP.size()>0)
							{
								for (String NewFlightDetailNEW : flightdetaillistTEMP) {
									flightdetaillist.add(NewFlightDetailNEW);
								}
							}
							if (!flightdeatilArrayTEmp2[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]
									.equals(flightdeatilArrayTEmp[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)])) {
								String flightdetailTEmp4 = temp.substring(1,
										temp.indexOf("]"));
								if (temp.indexOf("" + flightdetailTEmp4 + "],") != -1) {
									temp = temp.substring(flightdetailTEmp4.length() + 3);
								} else {
									temp = "";
								}
								flightdetaillist.add(flightdetailTEmp4);
								String[] flightdeatilArrayTEmp4 = flightdetailTEmp4
										.split(",");
								if (!flightdeatilArrayTEmp4[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]
										.equals(flightdeatilArrayTEmp[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)])) {
									String flightdetailTEmp5 = temp.substring(1,
											temp.indexOf("]"));
									if (temp.indexOf("" + flightdetailTEmp5 + "],") != -1) {
										temp = temp.substring(flightdetailTEmp5.length() + 3);
									} else {
										temp = "";
									}
									flightdetaillist.add(flightdetailTEmp5);
									String[] flightdeatilArrayTEmp5 = flightdetailTEmp5.split(",");
									if (!flightdeatilArrayTEmp5[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.ToAirportCode)]
											.equals(flightdeatilArrayTEmp[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.FromAirportCode)])) {
										String flightdetailTEmp6 = temp.substring(1,
												temp.indexOf("]"));
										if (temp.indexOf("" + flightdetailTEmp6 + "],") != -1) {
											temp = temp.substring(flightdetailTEmp6.length() + 3);
										} else {
											temp = "";
										}
										flightdetaillist.add(flightdetailTEmp6);
									}
								}
							}
						}
					}
				}
			}
			Map<String, String[]> fareDetailMap = new HashMap<String, String[]>();
			List<String> keyList = new ArrayList<String>();		
			TreeSet<String> uniquePriceSet = new TreeSet<String>(new PriceComparator());
			List<String[]> flightDetailArray = new ArrayList<String[]>();			
			if(flightdetaillist!=null && flightdetaillist.size()>0)
			{
				for (int i = 1; i < flightdetaillist.size(); i++) {
					String flightdeatil = flightdetaillist.get(i);
					String key = flightdeatil.substring(0, flightdeatil.indexOf(","));					
					String[] flightdeatilArray = flightdeatil.split(",");
					fareDetailMap.put(key, flightdeatilArray);
					if (!flightdeatilArray[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)].equals("0")) {
						uniquePriceSet.add(flightdeatilArray[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)]);
					}
					flightDetailArray.add(flightdeatilArray);
					keyList.add(key);
				}
			}
			List<String> faredetaillist = new ArrayList<String>();
			String temp1 = FareDetails;
			while (temp1!=null && temp1.length() > 3) {
				String faredetail = temp1.substring(1, temp1.indexOf("]"));
				if (temp1.indexOf("" + faredetail + "],") != -1) {
					temp1 = temp1.substring(faredetail.length() + 3);
				} else {
					temp1 = "";
				}
				faredetaillist.add(faredetail);
			}
			Map<String, String[]> flightDetailMap = new HashMap<String, String[]>();
			if(faredetaillist!=null && faredetaillist.size()>0)
			{
				for (int i = 1; i < faredetaillist.size(); i++) {
					String faredeatil = faredetaillist.get(i);
					if (flightsearch.getTripType().equalsIgnoreCase("O")) {
						String key = faredeatil.substring(0, faredeatil.indexOf(","));
						String[] faredeatilArray = faredeatil.split(",");
						flightDetailMap.put(key, faredeatilArray);
					} else
						if (flightsearch.getTripType().equalsIgnoreCase("R")) {
							String key = faredeatil.substring(0, faredeatil.indexOf(","));
							if (i != faredetaillist.size() - 1) {
								String Nextkey = faredetaillist.get(i + 1).substring(0,
										faredeatil.indexOf(","));

								if (key.substring(0, key.length() - 1).equals(
										Nextkey.substring(0, Nextkey.length() - 1))) {

									String faredetailTEMP = getNewFareDetail(faredeatil,
											faredetaillist.get(i + 1));								
									String[] faredeatilArray = faredetailTEMP.split(",");
									flightDetailMap.put(key, faredeatilArray);
									i++;
								} else {
									String[] faredeatilArray = faredeatil.split(",");
									flightDetailMap.put(key, faredeatilArray);
								}
							} else {
								String[] faredeatilArray = faredeatil.split(",");
								flightDetailMap.put(key, faredeatilArray);
							}
						}
					}
				}
			bluestarSearchData.setFlightFareDetailsHeaderMap(flightFareDetailsHeaderMap);
			bluestarSearchData.setFareDetailMap(fareDetailMap);
			bluestarSearchData.setFlightDetailArray(flightDetailArray);
			bluestarSearchData.setFlightDetailMap(flightDetailMap);
			bluestarSearchData.setFlightDetailsHeaderMap(flightDetailsHeaderMap);
			bluestarSearchData.setUniquePriceSet(uniquePriceSet);
		}
		else{
			logger.info("Blue Star Search API Error :" + flightSearchXMlDataString);
			throw new FlightException(ErrorCodeCustomerEnum.Exception,FlightErrorMessages.NO_FLIGHT);
		}
		return bluestarSearchData;
	}

	public static List<String> getNewFlightDetail(String flightdetail,
			String flightdetailTEmp, Map<String, Integer> flightDetailsHeaderMap) {
		List<String> flightdetaillist = new ArrayList<String>();
		String[] flightdeatilArray1 = flightdetail.split(",");
		String[] flightdeatilArray2 = flightdetailTEmp.split(",");
		String newtax2 = "0";

		BigDecimal newtotal = new BigDecimal(
				flightdeatilArray1[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)])
				.add(new BigDecimal(
						flightdeatilArray2[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)]));
		BigDecimal newtax = new BigDecimal(
				flightdeatilArray1[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)])
				.add(new BigDecimal(
						flightdeatilArray2[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)]));

		String newtotalamount1 = newtotal.toString();
		String newtotalamount2 = "0";
		String newtax1 = newtax.toString();
		flightdeatilArray1[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)] = newtotalamount1;
		flightdeatilArray1[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)] = newtax1;
		flightdeatilArray2[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)] = newtotalamount2;
		flightdeatilArray2[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)] = newtax2;
		StringBuilder sb1 = new StringBuilder();
		for (int i = 0; i < flightdeatilArray1.length; i++) {
			sb1.append(flightdeatilArray1[i]);
			if (i < flightdeatilArray1.length - 1) {
				sb1.append(",");
			}
		}
		StringBuilder sb2 = new StringBuilder();
		for (int i = 0; i < flightdeatilArray2.length; i++) {
			sb2.append(flightdeatilArray2[i]);
			if (i < flightdeatilArray2.length - 1) {
				sb2.append(",");
			}
		}
		flightdetaillist.add(sb1.toString());
		flightdetaillist.add(sb2.toString());		
		return flightdetaillist;
	}

	public static List<String> getNewFlightDetail3(String flightdetail,
			List<String> flightdetailTEmpMiddle, String flightdetailTEmp, Map<String, Integer> flightDetailsHeaderMap) {
		List<String> flightdetaillist = new ArrayList<String>();
		String[] flightdeatilArray1 = flightdetail.split(",");
		String[] flightdeatilArray2 = flightdetailTEmp.split(",");
		String newtax2 = "0";
		BigDecimal newtotal = new BigDecimal(
				flightdeatilArray1[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)])
				.add(new BigDecimal(
						flightdeatilArray2[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)]));
		BigDecimal newtax = new BigDecimal(
				flightdeatilArray1[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)])
				.add(new BigDecimal(
						flightdeatilArray2[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)]));
		String newtotalamount1 = newtotal.toString();
		String newtotalamount2 = "0";
		String newtax1 = newtax.toString();
		flightdeatilArray1[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)] = newtotalamount1;
		flightdeatilArray1[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)] = newtax1;
		flightdeatilArray2[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TotalAmount)] = newtotalamount2;
		flightdeatilArray2[flightDetailsHeaderMap.get(BluestarConstantsKeyMap.TaxAmount)] = newtax2;
		StringBuilder sb1 = new StringBuilder();
		for (int i = 0; i < flightdeatilArray1.length; i++) {
			sb1.append(flightdeatilArray1[i]);
			if (i < flightdeatilArray1.length - 1) {
				sb1.append(",");
			}

		}
		StringBuilder sb2 = new StringBuilder();
		for (int i = 0; i < flightdeatilArray2.length; i++) {
			sb2.append(flightdeatilArray2[i]);
			if (i < flightdeatilArray2.length - 1) {
				sb2.append(",");
			}
		}
		flightdetaillist.add(sb1.toString());
		for (String tempflight : flightdetailTEmpMiddle) {

			flightdetaillist.add(tempflight);
		}
		flightdetaillist.add(sb2.toString());
		return flightdetaillist;
	}

	public static String getNewFareDetail(String flightdetail,
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
}