package com.tayyarah.insurance.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.tayyarah.apiconfig.model.TrawellTagConfig;
import com.tayyarah.common.dao.MoneyExchangeDao;
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
import com.tayyarah.insurance.util.api.trawelltag.TrawellTagInsuranceExecutorServiceTask;




public class InsurancePolicyExecutorServiceTaskHelper {
	static final Logger logger = Logger.getLogger(InsurancePolicyExecutorServiceTaskHelper.class);
	
	public static PolicyResponseData createPolicyService(CreateInsurancePolicyRequest createInsurancePolicyRequest,AppKeyVo appKeyVo,Map<String,List<InsuranceMarkUp>> markupMap,MoneyExchangeDao moneydao,CompanyDao companyDao,CompanyConfigDAO companyConfigDAO,InsuranceCommonDao insuranceCommonDao,InsuranceOrderRow insuranceOrderRow){
		PolicyResponseData policyResponseData = new PolicyResponseData();
		ExecutorService executorService = Executors.newFixedThreadPool(8);		
		List<Future<PolicyResponseData>> futures = new ArrayList<Future<PolicyResponseData>>();
		try{
			TrawellTagConfig trawellTagConfig = TrawellTagConfig.GetTrawellTagConfig(appKeyVo);
			CurrencyConversionMap currencyConversionMap = InsuranceCommonUtil.buildCurrencyConversionMap(createInsurancePolicyRequest.getCurrency(),moneydao);
			Future<PolicyResponseData> future = executorService.submit(new TrawellTagInsuranceExecutorServiceTask(trawellTagConfig,createInsurancePolicyRequest,markupMap,currencyConversionMap,appKeyVo,companyConfigDAO,companyDao,insuranceCommonDao,insuranceOrderRow));
			futures.add(future);
		}catch(Exception e){
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.CREATEPOLICY_ERROR.getErrorMessage());
		}
		
		for(Future<PolicyResponseData> future:futures){
			try {
				policyResponseData = future.get();
			} catch (ExecutionException e)
			{
				throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.CREATEPOLICY_ERROR.getErrorMessage());
			}
			catch (InterruptedException  e) {
				throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.CREATEPOLICY_ERROR.getErrorMessage());
			}
		}
		
		
		return policyResponseData;
	}
}
