package com.tayyarah.common.util;


import javax.servlet.http.HttpServletResponse;

public class ResponseHeader {
	
	public static void setResponse(HttpServletResponse response){
		 response.setHeader("Access-Control-Allow-Origin", "*");
         response.setHeader("Access-Control-Allow-Methods", "POST");
         response.setHeader("Access-Control-Allow-Headers", "Content-Type");
         response.setHeader("Access-Control-Max-Age", "86400");	
		
	}
	public static void setPostResponse(HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
         response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
         response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
         response.addHeader("Access-Control-Max-Age", "1728000");
		
	}

}
