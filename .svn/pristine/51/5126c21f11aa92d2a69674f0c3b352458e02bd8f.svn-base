package com.tayyarah.insurance.util.api.trawelltag;

import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.tayyarah.api.insurance.trawelltag.model.CreatePolicyResult;
import com.tayyarah.api.insurance.trawelltag.model.Data;
import com.tayyarah.api.insurance.trawelltag.model.Policy;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.gstconfig.model.InsurenceGstTax;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.common.util.ApiResponseSaver;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.company.entity.Company;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.insurance.dao.InsuranceCommonDao;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.insurance.exception.InsuranceErrorMessages;
import com.tayyarah.insurance.exception.InsuranceException;
import com.tayyarah.insurance.model.CreateInsurancePolicyRequest;
import com.tayyarah.insurance.model.InsuranceMarkUp;
import com.tayyarah.insurance.model.PolicyResponseData;
import com.tayyarah.insurance.model.PolicyResposne;
import com.tayyarah.insurance.model.PriceDetail;
import com.tayyarah.insurance.model.Status;
import com.tayyarah.insurance.util.InsuranceCommonUtil;
import com.tayyarah.insurance.util.InsuranceMarkUpHelper;

public class TrawellTagResponseParser {
	static final Logger logger = Logger.getLogger(TrawellTagResponseParser.class);


	public static PolicyResponseData policyResponseParser(List<StringBuilder> responseList,List<Policy> policyDataList,CreateInsurancePolicyRequest createInsurancePolicyRequest,Map<String,List<InsuranceMarkUp>> markupMap,CurrencyConversionMap currencyConversionMap,AppKeyVo appKeyVo,CompanyConfigDAO companyConfigDAO,CompanyDao companyDao,InsuranceCommonDao insuranceCommonDao,InsuranceOrderRow insuranceOrderRow){
		PolicyResponseData policyResponseData = new PolicyResponseData();
		try{

			CompanyConfig companyConfig = null;
			try {
				companyConfig = appKeyVo.getCompanyConfig();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			Company company = null;
			try{
				company = appKeyVo.getCompany();
			}catch(Exception e){
				e.printStackTrace();
			}
			Company parentCompany = null;
			try{
				parentCompany = companyDao.getParentCompany(company);
			}catch(Exception e){
				e.printStackTrace();
			}
			Status status = new Status();
			policyResponseData = new PolicyResponseData();		
			List<PolicyResposne> policyResponseList = new ArrayList<PolicyResposne>();
			Unmarshaller unmarshaller = JAXBContext.newInstance(CreatePolicyResult.class).createUnmarshaller();
			int i = 0;
			for (StringBuilder response : responseList) {
				if(response!=null)
				{

					StringReader reader = new StringReader(response.toString());
					unmarshaller = JAXBContext.newInstance(Data.class).createUnmarshaller();
					Data createPolicyData = (Data)unmarshaller.unmarshal(reader);
					if(createPolicyData!=null )
					{
						System.out.println(createPolicyData.getClaimcode());
					}

					CreatePolicyResult createPolicyResult = new CreatePolicyResult();
					createPolicyResult.setData(createPolicyData);
					if(i == 0)
					{
						ApiResponseSaver.saveInsuranceApiResponse(insuranceCommonDao, createPolicyResult, insuranceOrderRow);
					}

					if(!createPolicyResult.getData().getStatus().equalsIgnoreCase("Error"))
					{
						status.setErrorCode(Status.SUCCESSCODE);
						status.setErrorMessage("Success");			   

						PolicyResposne policyResposne = new PolicyResposne();
						policyResposne.setClaimCode(createPolicyResult.getData().getClaimcode());
						policyResposne.setDocumentUrl(createPolicyResult.getData().getDocument());
						policyResposne.setPolicyNumber(createPolicyResult.getData().getPolicy());
						policyResponseData.setReferance(createPolicyResult.getData().getReference());
						policyResponseList.add(policyResposne);

					}else{
						status.setErrorCode(Status.FAILEDCODE);
						status.setErrorMessage("Failed");
					}			

					i++;
				}
			}

			PriceDetail priceDetail = new PriceDetail();
			priceDetail.setApiBasePrice(new BigDecimal(policyDataList.get(0).getPlan().getTotalbasecharges()).multiply(new BigDecimal(createInsurancePolicyRequest.getTravellerDetails().size())));
			priceDetail.setApiTaxes(new BigDecimal(policyDataList.get(0).getPlan().getServicetax()).multiply(new BigDecimal(createInsurancePolicyRequest.getTravellerDetails().size())));
			priceDetail.setApiTotalPrice(new BigDecimal(policyDataList.get(0).getPlan().getTotalcharges()).multiply(new BigDecimal(createInsurancePolicyRequest.getTravellerDetails().size())));
			priceDetail.setBasePrice(insuranceOrderRow.getBasePrice());

			InsuranceMarkUpHelper.applyMarkupOnPolicyResponse(priceDetail,markupMap,companyConfig);

			InsurenceGstTax insurenceGstTax = null;
			if(companyConfig != null){
				if(companyConfig.getCompanyConfigType().isB2E()){
					if(companyConfig.getTaxtype()!=null && companyConfig.getTaxtype().equalsIgnoreCase("GST")){
						insurenceGstTax = new InsurenceGstTax();
						BigDecimal totalPrice = priceDetail.getTotalPrice();
						insurenceGstTax = InsuranceCommonUtil.getInsurenceGstTax(companyConfig, company, parentCompany);
						BigDecimal totalPriceAfterGstTax = totalPrice.add(insurenceGstTax.getManagementFee().add(insurenceGstTax.getTotalTax()));
						totalPriceAfterGstTax = totalPriceAfterGstTax.setScale(2, RoundingMode.UP);
						BigDecimal totalPayable = priceDetail.getTotalPayableAmount().add(insurenceGstTax.getManagementFee()).add(insurenceGstTax.getTotalTax());
						priceDetail.setTotalPayableAmount(totalPayable);
						priceDetail.setTotalPrice(totalPriceAfterGstTax);
					}
				}
			}
			priceDetail.setInsurenceGstTax(insurenceGstTax);

			policyResponseData.setPolicyResposne(policyResponseList);
			policyResponseData.setStatus(status);	
			policyResponseData.setPriceDetail(priceDetail);

		}catch(Exception e){
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.CREATEPOLICY_ERROR.getErrorMessage());
		}
		return policyResponseData;
	}
}