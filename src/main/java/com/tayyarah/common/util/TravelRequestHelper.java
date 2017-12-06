package com.tayyarah.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class TravelRequestHelper {

	public static String getQuotationUserId(String orderId) throws Exception
	{
		if(orderId != null)
		{
			StringTokenizer tok=new StringTokenizer(orderId, "[<\\>]");
			List<String> addList=  new LinkedList<>();
			while(tok.hasMoreTokens()){
				String token=tok.nextToken();
				addList.add(token.trim());
			} 
			if(addList!=null && addList.size()>0)
			{
				String userId = addList.get(0);
				return userId;
			}
		}
		return null;
	}
	public static String getTravelRequestId(String orderId) throws Exception
	{
		if(orderId != null)
		{
			StringTokenizer tok=new StringTokenizer(orderId, "[<\\>]");
			List<String> addList=  new LinkedList<>();
			while(tok.hasMoreTokens()){
				String token=tok.nextToken();
				addList.add(token.trim());
			} 
			if(addList!=null && addList.size()>0)
			{
				String requestId = addList.get(1);
				return requestId;
			}
		}
		return null;
	}
	public static List<Long> getQuotationIdList(String orderId) throws Exception
	{

		if(orderId != null)
		{
			StringTokenizer tok=new StringTokenizer(orderId, "[<\\>]");
			List<String> addList=  new LinkedList<>();
			while(tok.hasMoreTokens()){
				String token=tok.nextToken();
				addList.add(token.trim());
			} 
			if(addList!=null && addList.size()>0)
			{
				String quotationIdsTxt=addList.get(2);
				List<Long> idList = new ArrayList<Long>();
				for (String idText : Arrays.asList(quotationIdsTxt.split(","))) {
					idList.add(Long.valueOf(idText));
				}
				return idList;
			}
		}

		return new ArrayList<Long>();
	}
}
