package com.tayyarah.common.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.common.dao.MoneyExchangeDao;
import com.tayyarah.common.entity.MoneyExchange;
import com.tayyarah.common.util.AppControllerUtil;
import com.tayyarah.common.util.CommonValidator;
import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyDao;

@RestController
@RequestMapping("/currency")
public class CurrencyExchangeController {
	static final Logger logger = Logger.getLogger(CurrencyExchangeController.class);
	@Autowired
	MoneyExchangeDao moneydao;
	@Autowired
	CompanyDao companyDao;
	private  CommonValidator validator = new CommonValidator();
	
	@RequestMapping(value = "/list", method = RequestMethod.GET,headers="Accept=application/json")  
	public @ResponseBody  
	List<MoneyExchange> getCurrencyList() {  
		List<MoneyExchange> currnecy = null;  
		try {  
			currnecy = moneydao.getEntityList(); 
		} catch (Exception e) {  
			e.printStackTrace();  
		} 
		return currnecy;  
	}  


	/* Ger a single objct in Json form Money Exchange Rate */  
	@RequestMapping(value = "/convert/{CurrencyCode}/{convertedCur}",  method = RequestMethod.GET,headers="Accept=application/json")  
	@ResponseBody  
	public Map<String, Double> getMoneyExchangeValue(@PathVariable("CurrencyCode") String CurrencyCode,@PathVariable("convertedCur") String convertedCur, HttpServletRequest request) {  
		Map<String, Double> currencyrate=null; 
		try { 
			logger.info("Before : "+CurrencyCode);
			logger.info("currencyrate : "+convertedCur);
			currencyrate=  moneydao.getCurrencyRate(CurrencyCode, convertedCur);

		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		return currencyrate;  
	}  
	//http://localhost:8080/LintasTravelAPI/booking/currencyconverter?app_key=B6cynYrcL6TwIH5GtMHhVH64cNvVTPKG6FXnf4Fpo9A=&from=MYR&to=INR
	@RequestMapping(value="/currencyconverter",headers={"Accept=application/json"},produces={"application/json"})
	public @ResponseBody String getCurrencyConverter(@RequestParam(value="app_key") String app_key,@RequestParam(value="from") String from,@RequestParam(value="to") String to,HttpServletResponse response) {

		ResponseHeader.setResponse(response);//Setting response header
		logger.info("getCurrencyConverter method called : ");

		AppControllerUtil.validateAppKey(companyDao, app_key);
		validator.currencyconverterValidator(from, to);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try
		{
			URL hp = new URL("http://cheapfaresindia.makemytrip.com/international/json/currency?from="+from+"&to="+to);
			URLConnection hpCon = hp.openConnection(); 
			DataInputStream	in = new DataInputStream((InputStream) hpCon.getInputStream());
			String line;
			br = new BufferedReader(new InputStreamReader(in));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}	
}