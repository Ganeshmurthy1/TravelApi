package com.tayyarah.insurance.util.api.trawelltag;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.json.JsonException;

import org.apache.log4j.Logger;

import com.tayyarah.apiconfig.model.TrawellTagConfig;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.insurance.dao.InsuranceCommonDao;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.insurance.exception.InsuranceErrorMessages;
import com.tayyarah.insurance.exception.InsuranceException;
import com.tayyarah.insurance.model.CreateInsurancePolicyRequest;
import com.tayyarah.insurance.model.InsuranceMarkUp;
import com.tayyarah.insurance.model.PolicyResponseData;


public class TrawellTagInsuranceExecutorServiceTask implements Callable<PolicyResponseData>{
	static final Logger logger = Logger.getLogger(TrawellTagInsuranceExecutorServiceTask.class);
	private CreateInsurancePolicyRequest createInsurancePolicyRequest;
	private Map<String,List<InsuranceMarkUp>> markupMap;
	private CurrencyConversionMap currencyConversionMap = null;
	private TrawellTagConfig trawellTagConfig;
	private AppKeyVo appKeyVo ;
	private CompanyConfigDAO companyConfigDAO;
	private CompanyDao companyDao;	
	private InsuranceCommonDao insuranceCommonDao;
	private InsuranceOrderRow insuranceOrderRow;
	public TrawellTagInsuranceExecutorServiceTask(TrawellTagConfig trawellTagConfig,CreateInsurancePolicyRequest createInsurancePolicyRequest,Map<String,List<InsuranceMarkUp>> markupMap,CurrencyConversionMap currencyConversionMap,AppKeyVo appKeyVo,CompanyConfigDAO companyConfigDAO,CompanyDao companyDao,InsuranceCommonDao insuranceCommonDao,InsuranceOrderRow insuranceOrderRow){
		this.trawellTagConfig = trawellTagConfig;
		this.createInsurancePolicyRequest = createInsurancePolicyRequest;
		this.companyConfigDAO = companyConfigDAO;
		this.currencyConversionMap = currencyConversionMap;
		this.appKeyVo = appKeyVo;    	
		this.markupMap = markupMap;    	
		this.companyDao = companyDao;
		this.insuranceCommonDao = insuranceCommonDao;
		this.insuranceOrderRow = insuranceOrderRow;
	}

	@Override
	public PolicyResponseData call() throws Exception {
		PolicyResponseData policyResponseData = null;
		try{
			policyResponseData = TrawellTagServiceCall.createInsurancePolicy(trawellTagConfig,createInsurancePolicyRequest,markupMap,currencyConversionMap,appKeyVo,companyConfigDAO,companyDao,insuranceCommonDao,insuranceOrderRow);
		}catch(JsonException e){
			logger.error("ClassNotFoundException",e);
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.CREATEPOLICY_ERROR.getErrorMessage());
		}catch(Exception e){
			logger.error("ClassNotFoundException",e);
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.CREATEPOLICY_ERROR.getErrorMessage());
		}
		return policyResponseData;
	}

}
