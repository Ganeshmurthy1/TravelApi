package com.tayyarah.common.dao;

import java.util.List;

import com.tayyarah.apiproviderconfig.entity.ApiProviderBluestarConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderCommonConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderDesiyaConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderEtravelSmartConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderTayyarahConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderTboConfig;
import com.tayyarah.apiproviderconfig.entity.ApiProviderTrawellTagConfig;
import com.tayyarah.common.model.AppKeyVo;

public interface ApiProviderCommonConfigActiveDao {
	public List<ApiProviderCommonConfig> commonConfigActiveList(String apiStatus,String apiFlightStatus,AppKeyVo appKeyVo);
	public List<ApiProviderTboConfig> getApiProviderTboConfigList(String tboFlightEnvironment) ;
	public List<ApiProviderBluestarConfig> getApiProviderBluestarConfigList(String tboFlightEnvironment);
	public List<ApiProviderDesiyaConfig> getApiProviderDesiyaConfigList(String tboFlightEnvironment) ;
	public List<ApiProviderTayyarahConfig> getApiProviderTayyarahConfigList(String tboFlightEnvironment);
	public List<ApiProviderEtravelSmartConfig> getApiProviderEtravelSmartConfigList(String esmartBusEnvironment);
	public List<ApiProviderTrawellTagConfig> getApiProviderTrawellTagConfigList(String trawelltagInsuranceEnvironment);

}
