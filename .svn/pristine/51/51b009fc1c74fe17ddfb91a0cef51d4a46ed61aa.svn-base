/**
@Author yogeshsharma
15-Sep-2015 2015
AppControllerUtil.java
 */
/**
 * 
 */
package com.tayyarah.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.tayyarah.common.exception.CommonException;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.exception.ErrorMessages;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.flight.dao.FlightBookingDao;
import com.tayyarah.flight.exception.FlightException;


public class AppControllerUtil {

	static final Logger logger = Logger.getLogger(AppControllerUtil.class);

	public static void validateAppKey(CompanyDao CDAO,String app_key)
	{		
		encryptions enc = new encryptions();
		app_key = app_key.replaceAll(" ", "+");
		String DecApp_key = enc.decrypt(app_key);		
		try {			
			boolean re=CDAO.checkAppkey(DecApp_key);		
			if(!re){				
				throw new CommonException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
			}
		} catch (HibernateException e) {
			logger.error("HibernateException",e);		
			throw new CommonException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.DB_ERROR);
		}catch (NumberFormatException e) {
			logger.error("NumberFormatException",e);
			throw new CommonException(ErrorCodeCustomerEnum.NumberFormatException,ErrorMessages.INVALID_APPKEY);
		}catch (Exception e) {
			logger.error("Exception",e);
			throw new CommonException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
		}
	}

	public static void validatePaymentAppKey(CompanyDao CDAO,String app_key,String refno)
	{
		encryptions enc = new encryptions();
		app_key = app_key.replaceAll(" ", "+");
		String DecApp_key = enc.decrypt(app_key);		
		try {			
			boolean re = CDAO.checkAppkey(DecApp_key);			
			if(!re){			
				throw new CommonException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
			}
		} catch (HibernateException e) {
			logger.error("HibernateException",e);		
			throw new CommonException(ErrorCodeCustomerEnum.HibernateException,
					ErrorMessages.DB_ERROR,ErrorMessages.PAY_S_BOOKING_FAILED,refno);

		}catch (NumberFormatException e) {
			logger.error("NumberFormatException",e);

			throw new CommonException(ErrorCodeCustomerEnum.NumberFormatException,ErrorMessages.INVALID_APPKEY,ErrorMessages.PAY_S_BOOKING_FAILED,refno);
		}catch (Exception e) {
			logger.error("Exception",e);

			throw new CommonException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY,ErrorMessages.PAY_S_BOOKING_FAILED,refno);
		}
	}

	public static String getDecryptedAppKey(CompanyDao CDAO,String app_key)
	{		
		encryptions enc = new encryptions();
		app_key = app_key.replaceAll(" ", "+");
		String DecApp_key = enc.decrypt(app_key);		
		try {			
			boolean re=CDAO.checkAppkey(DecApp_key);			
			if(!re){		
				throw new CommonException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new CommonException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.DB_ERROR);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			throw new CommonException(ErrorCodeCustomerEnum.NumberFormatException,ErrorMessages.INVALID_APPKEY);
		}catch (Exception e) {
			e.printStackTrace();
			throw new CommonException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
		}
		return DecApp_key;
	}

	public static void validateTransactionKey(FlightBookingDao FBDAO,String trans_key)
	{
		try {
			boolean re = FBDAO.getTransStatus(trans_key);
			if(!re){				
				throw new CommonException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_TRANSKEY);
			}
		} catch (HibernateException e) {		
			throw new CommonException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.DB_ERROR);
		}catch (Exception e) {
			e.printStackTrace();
			throw new CommonException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_TRANSKEY);
		}
	}
	public static String getDecryptedAppKey(String appKey)
	{		
		encryptions enc=new encryptions();
		appKey=appKey.replaceAll(" ", "+");
		String DecappKey=enc.decrypt(appKey);
		logger.info("getDecryptedAppKey DecappKey: "+DecappKey);
		return DecappKey;
	}
	public static AppKeyVo getDecryptedAppKeyObject(CompanyDao companyDao,String appKey)
	{		
		AppKeyVo appKeyVo = null;
		try {
			String decryptedKey = getDecryptedAppKey(appKey);
			String companyId = "";
			String configId = "";
			if(StringUtils.isNotBlank(decryptedKey))
			{
				configId = decryptedKey.substring(0, decryptedKey.indexOf("-"));
				companyId = decryptedKey.substring(decryptedKey.indexOf("-") + 1);
				if(StringUtils.isNotBlank(configId) && StringUtils.isNotBlank(companyId))
				{
					appKeyVo = new AppKeyVo();
					appKeyVo.setCompanyId(Integer.parseInt(companyId));
					appKeyVo.setConfigId(Integer.parseInt(configId));
					appKeyVo.setAppKey(appKey);
					try {
						boolean re=companyDao.checkAppkey(appKeyVo);
						if(!re){
							throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
						}
						
						appKeyVo.setCompany(companyDao.getCompany(appKeyVo.getCompanyId()));
						appKeyVo.setCompanyConfig(companyDao.getCompanyConfigUsingId(appKeyVo.getConfigId()));
					} catch (HibernateException e) {
						throw new FlightException(ErrorCodeCustomerEnum.HibernateException,ErrorMessages.DB_ERROR);
					}catch (Exception e) {
						e.printStackTrace();
						throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_TRANSKEY);
					}
				}
				else {
					throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
				}
			}
			else {
				throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new FlightException(ErrorCodeCustomerEnum.Exception,ErrorMessages.INVALID_APPKEY);
		}
		return appKeyVo;
	}
}