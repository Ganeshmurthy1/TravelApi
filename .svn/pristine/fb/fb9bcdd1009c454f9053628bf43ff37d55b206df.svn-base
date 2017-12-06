package com.tayyarah.common.util.soap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.tayyarah.hotel.util.HotelApiCredentials;
import com.tayyarah.hotel.util.api.concurrency.RezLivePullerTask;

import sun.net.www.protocol.http.HttpURLConnection;


public class HttpPostClient {
	public HttpPostClient(HotelApiCredentials api) {
		super();
		this.api = api;
	}
	private HotelApiCredentials api;	    
	public static final Logger logger = Logger.getLogger(HttpPostClient.class);

	public StringBuilder sendPost(StringBuilder req, String actionName) throws IOException, ProtocolException {	
		logger.info("-------------(((((end point : "+api.getEndPointUrl()+actionName);
		logger.info("-------------(((((request : "+req);


		URL obj = new URL(api.getEndPointUrl()+actionName);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		//add reuqest header
		con.setRequestMethod("POST");
		if(actionName.equalsIgnoreCase(RezLivePullerTask.URL_FIND_HOTEL_GEO))
		{
			con.setRequestProperty("Accept-Encoding", "gzip");
			logger.info("-------------(((((request Accept-Encoding: gzip");
		}

		con.setRequestProperty("content-type", "application/x-www-form-urlencoded");			
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(req.toString());
		wr.flush();
		wr.close();
		@SuppressWarnings("unused")
		int responseCode = con.getResponseCode();
		logger.info("\nSending 'POST' request to URL : " + api.getEndPointUrl()+actionName);
		logger.info("Post parameters : " + req);
		logger.info("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();			
		//print result
		logger.info(response.toString());
		return response;

	}


}
