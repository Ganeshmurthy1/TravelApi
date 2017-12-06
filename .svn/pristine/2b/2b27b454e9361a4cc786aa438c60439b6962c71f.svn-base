package com.tayyarah.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;

import com.tayyarah.configuration.CommonConfig;



public class AmountRoundingModeUtil {
	public static final Logger logger = Logger.getLogger(AmountRoundingModeUtil.class);	
	public static BigDecimal roundingMode(BigDecimal amount){
		BigDecimal convertamount = new BigDecimal(0);		
		CommonConfig commonConfig = CommonConfig.GetCommonConfig();
		if(commonConfig != null )
		{ 			
			if(commonConfig.getFlight_rounding_mode()!=null && !commonConfig.getFlight_rounding_mode().equals(""))
			{
				String mode = commonConfig.getFlight_rounding_mode();
			
				switch (mode) {
				case "UP":
					convertamount = amount.setScale(0, RoundingMode.UP);	
					break;
				case "DOWN":
					convertamount = amount.setScale(0, RoundingMode.DOWN);	
					break;
				case "FLOOR":
					convertamount = amount.setScale(0, RoundingMode.FLOOR);	
					break;
				case "CEILING":
					convertamount = amount.setScale(0, RoundingMode.CEILING);	
					break;
				case "HALF_UP":
					convertamount = amount.setScale(0, RoundingMode.HALF_UP);	
					break;
				case "HALF_DOWN":
					convertamount = amount.setScale(0, RoundingMode.HALF_DOWN);	
					break;
				case "HALF_EVEN":
					convertamount = amount.setScale(0, RoundingMode.HALF_EVEN);	
					break;
				case "ORIGINAL":
					convertamount = amount;
					break;
				default:
					convertamount = amount;
					break;
				}				
			}else{
				logger.info("CommonConfig flight rounding mode is empty");
			}
		}else{
			logger.info("CommonConfig not loaded");
		}
		
		return convertamount;
	}
	
	public static BigDecimal roundingModeForHotel(BigDecimal amount){
		BigDecimal convertamount = new BigDecimal(0);		
		CommonConfig commonConfig = CommonConfig.GetCommonConfig();
		if(commonConfig != null )
		{ 			
			if(commonConfig.getHotel_rounding_mode()!=null && !commonConfig.getHotel_rounding_mode().equals(""))
			{
				String mode = commonConfig.getFlight_rounding_mode();
			
				switch (mode) {
				case "UP":
					convertamount = amount.setScale(0, RoundingMode.UP);	
					break;
				case "DOWN":
					convertamount = amount.setScale(0, RoundingMode.DOWN);	
					break;
				case "FLOOR":
					convertamount = amount.setScale(0, RoundingMode.FLOOR);	
					break;
				case "CEILING":
					convertamount = amount.setScale(0, RoundingMode.CEILING);	
					break;
				case "HALF_UP":
					convertamount = amount.setScale(0, RoundingMode.HALF_UP);	
					break;
				case "HALF_DOWN":
					convertamount = amount.setScale(0, RoundingMode.HALF_DOWN);	
					break;
				case "HALF_EVEN":
					convertamount = amount.setScale(0, RoundingMode.HALF_EVEN);	
					break;
				case "ORIGINAL":
					convertamount = amount;
					break;
				default:
					convertamount = amount;
					break;
				}			
			}else{
				logger.info("CommonConfig hotel rounding mode is empty");
			}
		}else{
			logger.info("CommonConfig not loaded");
		}
		
		return convertamount;
	}
	
	public static BigDecimal roundingModeForBus(BigDecimal amount){
		BigDecimal convertamount = new BigDecimal(0);		
		CommonConfig commonConfig = CommonConfig.GetCommonConfig();
		if(commonConfig != null )
		{ 			
			if(commonConfig.getBus_rounding_mode()!=null && !commonConfig.getBus_rounding_mode().equals(""))
			{
				String mode = commonConfig.getBus_rounding_mode();
			
				switch (mode) {
				case "UP":
					convertamount = amount.setScale(0, RoundingMode.UP);	
					break;
				case "DOWN":
					convertamount = amount.setScale(0, RoundingMode.DOWN);	
					break;
				case "FLOOR":
					convertamount = amount.setScale(0, RoundingMode.FLOOR);	
					break;
				case "CEILING":
					convertamount = amount.setScale(0, RoundingMode.CEILING);	
					break;
				case "HALF_UP":
					convertamount = amount.setScale(0, RoundingMode.HALF_UP);	
					break;
				case "HALF_DOWN":
					convertamount = amount.setScale(0, RoundingMode.HALF_DOWN);	
					break;
				case "HALF_EVEN":
					convertamount = amount.setScale(0, RoundingMode.HALF_EVEN);	
					break;
				case "ORIGINAL":
					convertamount = amount;
					break;
				default:
					convertamount = amount;
					break;
				}			
			}else{
				logger.info("CommonConfig Bus rounding mode is empty");
			}
		}else{
			logger.info("CommonConfig not loaded");
		}
		
		return convertamount;
	}

}
