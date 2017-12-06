package com.tayyarah.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.apiproviderconfig.entity.ApiProviderBluestarConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderCommonConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderDesiyaConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderEtravelSmartConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderTayyarahConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderTboConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderTrawellTagConfig;
import com.tayyarah.common.dao.ApiProviderCommonConfigActiveDaoImpl;
import com.tayyarah.common.model.AppKeyVo;


public class ApiProviderServiceImpl {
	static final Logger logger = Logger.getLogger(ApiProviderServiceImpl.class);
	@Autowired
	ApiProviderCommonConfigActiveDaoImpl commonConfigApiDatabaseDao;
	public static ApiProviderServiceImpl apiProviderServiceImpl;

	@PostConstruct
	public void init() {
		apiProviderServiceImpl = this;
		apiProviderServiceImpl.commonConfigApiDatabaseDao = this.commonConfigApiDatabaseDao;
	}	

	public static ApiProviderTboConfig businnessLogicforTboLiveOrTest(AppKeyVo appKeyVo) {
		List<ApiProviderCommonConfig> commonConfigList = new ArrayList<ApiProviderCommonConfig>();
		List<ApiProviderTboConfig> apiProviderTboConfigList = new ArrayList<ApiProviderTboConfig>();
		ApiProviderTboConfig apiProviderTboConfigResponse = null;
		try {
			commonConfigList = apiProviderServiceImpl.commonConfigApiDatabaseDao.commonConfigActiveList("tboActive","tboFlightActive",appKeyVo);
		} catch (Exception tboConfigException) {
		}
		if (commonConfigList != null && commonConfigList.size() > 0) {
			ApiProviderCommonConfig apiProviderCommonConfig = commonConfigList.get(0);
			try {
				String testOrLiveVariable = apiProviderCommonConfig.isTboFlightEnvironment()?"live":"test";
				apiProviderTboConfigList = apiProviderServiceImpl.commonConfigApiDatabaseDao.getApiProviderTboConfigList(testOrLiveVariable);
			} catch (Exception ee) {
			}
			if (apiProviderTboConfigList != null && apiProviderTboConfigList.size() > 0) {
				for (ApiProviderTboConfig apiProviderTboConfig : apiProviderTboConfigList) {
					if (apiProviderTboConfig.isActive()) {							
						apiProviderTboConfigResponse = apiProviderTboConfig;							
						return apiProviderTboConfigResponse;
					}
				}
			}		
		}
		return apiProviderTboConfigResponse;
	}

	public static ApiProviderDesiyaConfig businnessLogicforDesiyaLiveOrTest(AppKeyVo appKeyVo) {
		List<ApiProviderCommonConfig> commonConfigList = new ArrayList<ApiProviderCommonConfig>();
		List<ApiProviderDesiyaConfig> apiProviderDesiyaConfigList = new ArrayList<ApiProviderDesiyaConfig>();
		ApiProviderDesiyaConfig apiProviderTboConfigResponse = new ApiProviderDesiyaConfig();

		try {
			commonConfigList = apiProviderServiceImpl.commonConfigApiDatabaseDao.commonConfigActiveList("desiyaActive","desiyaHotelActive",appKeyVo);
		} catch (Exception tboConfigException) {
		}

		if (commonConfigList != null && commonConfigList.size() > 0) {
			ApiProviderCommonConfig apiProviderCommonConfig = commonConfigList.get(0);		
			try {
				String testOrLiveVariable=apiProviderCommonConfig.isTboFlightEnvironment()?"live":"test";
				apiProviderDesiyaConfigList = apiProviderServiceImpl.commonConfigApiDatabaseDao.getApiProviderDesiyaConfigList(testOrLiveVariable);
			} catch (Exception ee) {
			}

			if (apiProviderDesiyaConfigList != null && apiProviderDesiyaConfigList.size() > 0) {
				ApiProviderDesiyaConfig apiProviderDesiyeConfig = apiProviderDesiyaConfigList.get(0);
				if (apiProviderDesiyeConfig.isActive()) {
					apiProviderTboConfigResponse.setPassword(apiProviderDesiyeConfig.getPassword());
					apiProviderTboConfigResponse.setUserName(apiProviderDesiyeConfig.getUserName());
					apiProviderTboConfigResponse.setEndPointUrl(apiProviderDesiyeConfig.getEndPointUrl());
					apiProviderTboConfigResponse.setPropertyId(apiProviderDesiyeConfig.getPropertyId());
					apiProviderTboConfigResponse.setProviderId(apiProviderDesiyeConfig.getProviderId());
					apiProviderTboConfigResponse.setProviderName(apiProviderDesiyeConfig.getProviderName());
					apiProviderTboConfigResponse.setApiCurrency(apiProviderDesiyeConfig.getApiCurrency());
					apiProviderTboConfigResponse.setEnvironment(apiProviderDesiyeConfig.getEnvironment());
					apiProviderTboConfigResponse.setActive(apiProviderDesiyeConfig.isActive());
					return apiProviderTboConfigResponse;
				}
			}			
		}
		return new ApiProviderDesiyaConfig();
	}

	public static ApiProviderTboConfig businnessLogicforTboHotelLiveOrTest(AppKeyVo appKeyVo) {
		List<ApiProviderCommonConfig> commonConfigList = new ArrayList<ApiProviderCommonConfig>();
		List<ApiProviderTboConfig> apiProviderTboConfigList = new ArrayList<ApiProviderTboConfig>();
		ApiProviderTboConfig apiProviderTboConfigResponse = new ApiProviderTboConfig();
		try {
			commonConfigList = apiProviderServiceImpl.commonConfigApiDatabaseDao.commonConfigActiveList("tboActive","tboHotelActive",appKeyVo);
		} catch (Exception tboConfigException) {
		}
		if (commonConfigList != null && commonConfigList.size() > 0) {
			ApiProviderCommonConfig apiProviderCommonConfig = commonConfigList.get(0);
			//			if (apiProviderCommonConfig.isTboActive() && apiProviderCommonConfig.isTboFlightActive()) {
			try {
				String testOrLiveVariable=apiProviderCommonConfig.isTboFlightEnvironment()?"live":"test";
				apiProviderTboConfigList = apiProviderServiceImpl.commonConfigApiDatabaseDao
						.getApiProviderTboConfigList(testOrLiveVariable);
			} catch (Exception ee) {
			}

			if (apiProviderTboConfigList != null && apiProviderTboConfigList.size() > 0) {					
				for (ApiProviderTboConfig apiProviderTboConfig : apiProviderTboConfigList) {
					if (apiProviderTboConfig.isActive()) {
						apiProviderTboConfigResponse = apiProviderTboConfig;
						return apiProviderTboConfigResponse;
					}
				}

			}		
		}
		return new ApiProviderTboConfig();
	}

	public static ApiProviderTayyarahConfig businnessLogicforTayyarahHotelLiveOrTest(AppKeyVo appKeyVo) {
		List<ApiProviderCommonConfig> commonConfigList = new ArrayList<ApiProviderCommonConfig>();
		List<ApiProviderTayyarahConfig> apiProviderTayyarahConfigList = new ArrayList<ApiProviderTayyarahConfig>();
		ApiProviderTayyarahConfig apiProviderTayyarahConfigResponse = new ApiProviderTayyarahConfig();
		try {
			commonConfigList = apiProviderServiceImpl.commonConfigApiDatabaseDao.commonConfigActiveList("tayyarahActive","tayyarahHotelActive",appKeyVo);
		} catch (Exception tboConfigException) {
		}

		if (commonConfigList != null && commonConfigList.size() > 0) {
			ApiProviderCommonConfig apiProviderCommonConfig = commonConfigList.get(0);			
			try {
				String testOrLiveVariable=apiProviderCommonConfig.isTboFlightEnvironment()?"live":"test";
				apiProviderTayyarahConfigList = apiProviderServiceImpl.commonConfigApiDatabaseDao
						.getApiProviderTayyarahConfigList(testOrLiveVariable);
			} catch (Exception ee) {
			}

			if (apiProviderTayyarahConfigList != null && apiProviderTayyarahConfigList.size() > 0) {
				ApiProviderTayyarahConfig apiProviderTayyarahConfig = apiProviderTayyarahConfigList.get(0);
				if (apiProviderTayyarahConfig.isActive()) {
					apiProviderTayyarahConfigResponse.setAuthUrl(apiProviderTayyarahConfig.getAuthUrl());
					apiProviderTayyarahConfigResponse.setPassword(apiProviderTayyarahConfig.getPassword());
					apiProviderTayyarahConfigResponse.setUserName(apiProviderTayyarahConfig.getUserName());
					apiProviderTayyarahConfigResponse.setEndPointUrl(apiProviderTayyarahConfig.getEndPointUrl());
					apiProviderTayyarahConfigResponse.setPropertyId(apiProviderTayyarahConfig.getPropertyId());
					apiProviderTayyarahConfigResponse.setPropertyId(apiProviderTayyarahConfig.getPropertyId());
					apiProviderTayyarahConfigResponse.setProviderName(apiProviderTayyarahConfig.getProviderName());
					apiProviderTayyarahConfigResponse.setApiCurrency(apiProviderTayyarahConfig.getApiCurrency());
					apiProviderTayyarahConfigResponse.setEnvironment(apiProviderTayyarahConfig.getEnvironment());
					apiProviderTayyarahConfigResponse.setActive(apiProviderTayyarahConfig.isActive());
					return apiProviderTayyarahConfigResponse;
				}
			}			
		}
		return new ApiProviderTayyarahConfig();
	}

	public static ApiProviderBluestarConfig businessLogicforBluestarLiveOrTest(AppKeyVo appKeyVo) {
		List<ApiProviderCommonConfig> commonConfigList = new ArrayList<ApiProviderCommonConfig>();
		List<ApiProviderBluestarConfig> apiProviderBluestarConfigList = new ArrayList<ApiProviderBluestarConfig>();
		ApiProviderBluestarConfig apiProviderBluestarConfigResponse = null;
		try {
			commonConfigList = apiProviderServiceImpl.commonConfigApiDatabaseDao.commonConfigActiveList("bluestarActive","bluestarFlightActive",appKeyVo);
		} catch (Exception tboConfigException) {
		}
		if (commonConfigList != null && commonConfigList.size() > 0) {
			ApiProviderCommonConfig apiProviderCommonConfig = commonConfigList.get(0);
			try {
				String testOrLiveVariable=apiProviderCommonConfig.isBluestarFlightEnvironment()?"live":"test";
				apiProviderBluestarConfigList = apiProviderServiceImpl.commonConfigApiDatabaseDao
						.getApiProviderBluestarConfigList(testOrLiveVariable);
			} catch (Exception ee) {
			}

			if (apiProviderBluestarConfigList != null && apiProviderBluestarConfigList.size() > 0) {
				for (ApiProviderBluestarConfig apiProviderBluestarConfig : apiProviderBluestarConfigList) {
					if (apiProviderBluestarConfig.isActive()) {
						apiProviderBluestarConfigResponse = apiProviderBluestarConfig;						
						return apiProviderBluestarConfigResponse;
					}
				}
			}
		}
		return apiProviderBluestarConfigResponse;
	}

	public static ApiProviderEtravelSmartConfig getApiProviderEtravelSmartConfig(AppKeyVo appKeyVo) {
		List<ApiProviderCommonConfig> commonConfigList = new ArrayList<ApiProviderCommonConfig>();
		List<ApiProviderEtravelSmartConfig> apiProviderEtravelSmartConfigList = new ArrayList<ApiProviderEtravelSmartConfig>();
		ApiProviderEtravelSmartConfig ApiProviderEtravelSmartConfig = null;
		try {
			commonConfigList = apiProviderServiceImpl.commonConfigApiDatabaseDao.commonConfigActiveList("etravelActive","etravelBusActive",appKeyVo);
		} catch (Exception tboConfigException) {
		}
		if (commonConfigList != null && commonConfigList.size() > 0) {
			ApiProviderCommonConfig apiProviderCommonConfig = commonConfigList.get(0);
			try {
				String testOrLiveVariable = apiProviderCommonConfig.getApiProviderEtravelBusConfig().getEnvironment().equalsIgnoreCase("live")?"live":"test";
				apiProviderEtravelSmartConfigList = apiProviderServiceImpl.commonConfigApiDatabaseDao.getApiProviderEtravelSmartConfigList(testOrLiveVariable);
			} catch (Exception ee) {
			}
			if (apiProviderEtravelSmartConfigList != null && apiProviderEtravelSmartConfigList.size() > 0) {
				for (ApiProviderEtravelSmartConfig apiProviderEtravelSmartConfig : apiProviderEtravelSmartConfigList) {
					if (apiProviderEtravelSmartConfig.getActive()) {
						ApiProviderEtravelSmartConfig = apiProviderEtravelSmartConfig;						
						return ApiProviderEtravelSmartConfig;
					}
				}
			}
		}
		return ApiProviderEtravelSmartConfig;
	}
	public static ApiProviderTrawellTagConfig getApiProviderTrawellTagConfig(AppKeyVo appKeyVo) {
		List<ApiProviderCommonConfig> commonConfigList = new ArrayList<ApiProviderCommonConfig>();
		List<ApiProviderTrawellTagConfig> apiProviderTrawellTagConfigList = new ArrayList<ApiProviderTrawellTagConfig>();
		ApiProviderTrawellTagConfig ApiProviderTrawellTagConfig = null;
		try {
			commonConfigList = apiProviderServiceImpl.commonConfigApiDatabaseDao.commonConfigActiveList("trawellTagActive","trawellTagInsuranceActive",appKeyVo);
		} catch (Exception e) {
			logger.error("Exception Exception" + e);
		}
		if (commonConfigList != null && commonConfigList.size() > 0) {
			ApiProviderCommonConfig apiProviderCommonConfig = commonConfigList.get(0);
			try {
				String testOrLiveVariable = apiProviderCommonConfig.isTrawellTagInsuranceEnvironment()?"live":"test";
				apiProviderTrawellTagConfigList = apiProviderServiceImpl.commonConfigApiDatabaseDao.getApiProviderTrawellTagConfigList(testOrLiveVariable);
			} catch (Exception ee) {
				logger.error("commonConfigList Exception" + ee);
			}
			if (apiProviderTrawellTagConfigList != null && apiProviderTrawellTagConfigList.size() > 0) {
				for (ApiProviderTrawellTagConfig apiProviderTrawellTagConfig : apiProviderTrawellTagConfigList) {
					if (apiProviderTrawellTagConfig.getActive()) {
						ApiProviderTrawellTagConfig = apiProviderTrawellTagConfig;						
						return apiProviderTrawellTagConfig;
					}
				}
			}
		}
		return ApiProviderTrawellTagConfig;
	}
	/**
	 * @return the commonConfigApiDatabaseDao
	 */
	public ApiProviderCommonConfigActiveDaoImpl getCommonConfigApiDatabaseDao() {
		return commonConfigApiDatabaseDao;
	}

	public void setCommonConfigApiDatabaseDao(ApiProviderCommonConfigActiveDaoImpl commonConfigApiDatabaseDao) {
		this.commonConfigApiDatabaseDao = commonConfigApiDatabaseDao;
	}
}
