package com.tayyarah.insurance.util.api.trawelltag;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;

import com.tayyarah.api.insurance.trawelltag.client.AgentAPIV2;
import com.tayyarah.api.insurance.trawelltag.client.AgentAPIV2Soap;
import com.tayyarah.api.insurance.trawelltag.client.CreatePolicy;
import com.tayyarah.api.insurance.trawelltag.model.Data;
import com.tayyarah.api.insurance.trawelltag.model.Policy;
import com.tayyarah.apiconfig.model.TrawellTagConfig;
import com.tayyarah.common.exception.ErrorCodeCustomerEnum;
import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.common.model.CurrencyConversionMap;
import com.tayyarah.common.util.FileUtil;
import com.tayyarah.company.dao.CompanyConfigDAO;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.insurance.dao.InsuranceCommonDao;
import com.tayyarah.insurance.entity.InsuranceOrderRow;
import com.tayyarah.insurance.entity.TrawellTagPremiumChart;
import com.tayyarah.insurance.exception.InsuranceErrorMessages;
import com.tayyarah.insurance.exception.InsuranceException;
import com.tayyarah.insurance.model.CreateInsurancePolicyRequest;
import com.tayyarah.insurance.model.InsuranceMarkUp;
import com.tayyarah.insurance.model.PolicyResponseData;
import com.tayyarah.insurance.model.TravellerDetails;


public class TrawellTagServiceCall {
	static final Logger logger = Logger.getLogger(TrawellTagServiceCall.class);

	public static PolicyResponseData createInsurancePolicy(TrawellTagConfig trawellTagConfig,CreateInsurancePolicyRequest createInsurancePolicyRequest,Map<String,List<InsuranceMarkUp>> markupMap,CurrencyConversionMap currencyConversionMap,AppKeyVo appKeyVo,CompanyConfigDAO companyConfigDAO,CompanyDao companyDao,InsuranceCommonDao insuranceCommonDao,InsuranceOrderRow insuranceOrderRow){
		PolicyResponseData policyResponseData = null;
		try{

			List<StringBuilder> policyResponseStringList = new ArrayList<StringBuilder>();
			List<Policy> PolicyList = new ArrayList<Policy>();
			for (TravellerDetails travellerDetails : createInsurancePolicyRequest.getTravellerDetails()) {
				TrawellTagPremiumChart trawellTagPremiumChart = insuranceCommonDao.getTrawellTagPremiumChart(createInsurancePolicyRequest.getNoOfDays(), travellerDetails.getAge(),createInsurancePolicyRequest.getPlanId());
				Policy policyData = TrawellTagRequestBuilder.createPolicyRequest(trawellTagConfig, createInsurancePolicyRequest, trawellTagPremiumChart,travellerDetails);

				String policyDataXML = jaxbObjectToXML(policyData);
				// Save insurance Search Request
				try{
					FileUtil.writeJson("insurance", "TrawellTag", "create", false, policyDataXML.trim(),  String.valueOf(createInsurancePolicyRequest.getInsuredProductOrderId()+"-C"+travellerDetails.getCustomerId()));
				} catch (Exception e) {
					logger.error(" The filename, directory name ", e);
				}
				logger.info("---------------policyDataXML:"+ policyDataXML.trim());
				//System.out.println(policyDataXML.trim());				
				TrawellTagEncryption en = new TrawellTagEncryption();
				String encryptedText = en.getEncrypteData(policyDataXML.trim(),trawellTagConfig.getReferance(),trawellTagConfig.getSign());
				//System.out.println("encryptedText : " + encryptedText);
				//String encryptedTextTest = "FqVNHhisKQrghYVfogQM/oaZldZq7DUGPmdoy6l5yigNZLDSHsBCrGNFy31IkbVd8kNpv7lvn0pLpKIJWcpYpAWzgtG9SDBEKdnuzWzAjzLZNwvIZM0PLtXX62CspqPxQ0SDXK1v4m8eMqpcRt5YXaL401iTHxd7I5gX03BFIBijPqpmhujVIR1tiB46sjx6kk7USe3qpHrQ1sFqeZKvGjfiE4uR0tQXsGtiAN1Yd4nCo0A/il7cSf1IDaavxfc8w/eOA9fuzmo0BkKTz0UkNDX34uwION3nb2912mLvxSjcv3EE4nQAtX2hgk897Up/jvKWBY5mg+9Yv2Oas0VbFKzZIjw3OV2jOCvKTcjnh7/NdTjoHZQbOgwbEAdDZTBTC/I2B2zIO3dOYlT1RKn6zdLoXtvgIJAjEltnjjc2kCnna2ix1/Am9BbnqdU+wlmKaq300XwhZAY+lP2GD2vi0cVITJjRfb/WQMPRiw7mO/3z0FwulRgOKoPKrcHggBsA5VzKqd5jmMwas2KDlWqUtztBT8ZEF5pk+ConBoPZSKFn/+sLm6gZ9qfqVRC+NVDN+UMfXCkViuoLQtUa8alGHxKEVkNKQp4o2dsAgqkZm/mn738mhkYXUHtdZPMNsbr45fpB5YYQcoTTyLCVKrYTWQLf+6AiDAdWvqJP51+2EgHtMTofy1imTgd9SUburwpX9hReDjRaApPx0IXUD8R3QeWTV7YxGUPrN/2pJkpxlTuB06Zy2Rd/05O3kEOVsEHBLQmVX02fQdRnQAoQbWtkxX+riuZxf8MS0acNqNGjxJyK61x2iTlUA2p+sjPjm21Ra8BiQFjALsKyDjVjQ0YEcVW17NIfkGZ7ZrTuAd8W8sPH38dlsr3zj4dJlHvvcLAcpulI99fLeOgqAU7jkXU62idYmq2nJI/nh1s0p4yM6KrRr9INMe4/UmZ8xy8qlHrj9VW1oUUrJgztDZzwsWnU1cCPZTR3TT5WQjluZZ45xySwp2hK0yHRr7bvoycWwwSN8VWKt4DuIG3FiP20lzr+3RWut1ugkG7+niK8ps9qvZVEa83CIPEBVZJFrJpwH0f5uHuOzfdbqQKtv60trRhXsEDqgnZWhhanTo+gvpdtUZMZlYxyrvOsie7bTbSiNijWDu0Zm6c33ENmBv0z94LWoZWFQBqQuqdT1JvlrMgsV+WPUwX/S7774b/E+K5Chk5HkURiPXZ3CxoQNIswzRRiOAJafCZ3+sHhuLrDF9mvodfcwp51VotOCNs8CScgodHiVDXoQa2XrdgTZDppGFDbBMDluHxyKxMoqFSdXbraNFdTqj72EfUK8elFGyxg1ljo5cqcdGr+eefQ98LDsYjaScMyICyaCyeOpQNwL7YskThx3xZMQCtKsXAMmkRvGD7xnQWoResrkKJTPXAj8yTzhTAm+7PrIT+0+vllhm7AC3psFRQJP+uh2qkN3t/yoLtFSbbB3jW7zKUIrxDPkqDVr8Any1BuKGM2m09VPdEqBtCYXVBFqivVWLRElvjP0YxoQHCu2IHCySS3JTq8rEdkZ5xEkNMLgkmVo/z2dpjC95IXwBs9PlL8y5dGBuCEQqq9S/qhjFro8mPmVJsE4RFksVDWsPmyF1wURuiFrLaC8yfRboTlh8mwylLVQ+TmaOJ/XJRM74e2EoOQaAbyg/qhmvwLw6L65P5Gim/eSVCl2/aN4ZFEAplZ+iTQc906uEpDv7YV2MiE4MXam/Hdc7gQ7A4ST+Z1Zcp/6adnW9weM8Oo7+Nb4pQ6DBiltOteWn8Yv1XTD+FJ2xkLd8mVsCPihoJqZ8PFGJT3kMqs4igLETvBJIhabaRY9i9RDLqUOnitMU/TO6vWWg9XAKPW1pxik6WWFZ/alulCKMPC2HRNS6pVWzycZI9q6tPxlfSKOEhe";
				CreatePolicy createPolicy = buildCreatePolicyRequest(encryptedText, trawellTagConfig.getReferance());
				String dataResponse = callInsuranceCreatePolicy(createPolicy,trawellTagConfig.getUrl());
				logger.info("---------------dataResponse:"+ dataResponse.trim());
				// Save insurance Search Response
				try{
					FileUtil.writeJson("insurance", "TrawellTag", "create", true, dataResponse,  String.valueOf(createInsurancePolicyRequest.getInsuredProductOrderId()+"-C"+travellerDetails.getCustomerId()));
				} catch (Exception e) {
					logger.error(" The filename, directory name ", e);
				}
				
				StringBuilder responsestring = new StringBuilder();
				responsestring.append(dataResponse);

				int start = responsestring.indexOf("<data>");
				if(start != -1 )
				{
					responsestring.delete(0, start);
					int end = responsestring.indexOf("</data>");
					if(end!= -1)
					{
						responsestring.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
						policyResponseStringList.add(responsestring);						
					}
				}

				PolicyList.add(policyData);				
			}		
			if(policyResponseStringList.size() > 0)
				policyResponseData = TrawellTagResponseParser.policyResponseParser(policyResponseStringList,PolicyList,createInsurancePolicyRequest, markupMap, currencyConversionMap, appKeyVo, companyConfigDAO, companyDao, insuranceCommonDao,insuranceOrderRow);
			else
				throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.CREATEPOLICY_ERROR.getErrorMessage());

		}catch(Exception e){
			throw new InsuranceException(ErrorCodeCustomerEnum.Exception,InsuranceErrorMessages.CREATEPOLICY_ERROR.getErrorMessage());
		}
		return policyResponseData;
	}
	private static String jaxbObjectToXML(Policy policyData) {
		String xmlString = "";
		try {
			JAXBContext context = JAXBContext.newInstance(Policy.class);
			Marshaller m = context.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

			StringWriter sw = new StringWriter();
			m.marshal(policyData, sw);
			xmlString = sw.toString();

		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return xmlString;
	}

	public static void main(String[] args) {
		String dataResponse = callInsuranceCreatePolicyService();
		System.out.println("dataResponse : "+dataResponse);
		StringBuilder responsestring = new StringBuilder();
		responsestring.append(dataResponse);

		int start = responsestring.indexOf("<data>");
		if(start != -1 )
		{
			responsestring.delete(0, start);
			int end = responsestring.indexOf("</data>");
			if(end!= -1)
			{
				//					responsestring.delete(end-1, responsestring.length()-1);
				responsestring.insert(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				//System.out.println(responsestring);
				StringReader reader = new StringReader(responsestring.toString());
				Unmarshaller unmarshaller;
				try {
					unmarshaller = JAXBContext.newInstance(Data.class).createUnmarshaller();
					Data createPolicyResult = (Data)unmarshaller.unmarshal(reader);
					if(createPolicyResult!=null )
					{
						System.out.println(createPolicyResult.getClaimcode());
					}
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static String callInsuranceCreatePolicyService()
	{
		String encryptedTextTest = "2ZTr/YB7XIcl5nTu89KPoAhP8mo8ugEsC8Pd/QBqjaP9bW5lQ3FUGkh5G680CNOFIH1131G9tbd8VA3pofcChvlyH0bChlhsJZ9rWf5I0XoAboHyhSs68WpbSkRKBi9RmSf7Dsw7kqrFAn+fvLrXr02vEJ9u4F0+xwep+kw1LET1O1tw2JlkEH/mknHQdLNdGO78oEegVW4WJdCdd+q850CbQNARW2Vbf+k+Sorl+C6F5N/s2yFZ3Zt08JzlVGbtQJJqSgWGb4/On7Nsn+Xx/pKiwxUoGMkHyhnV+WMDa4Acpmq1NovTYu0TcQgzzs8NfEMoYBWoME4O5n0ojyq260hWcO9xo0TXsmxmMJyGaLebM/eqmnj45+8KLCz10AoHGSdW9bnBTUiyfVcMARhnF6zJTiwUbNfDOEvhg9Ce1uFgbLPiJ6CS9OkW5oSYht4JPfjqyRA2ndGdxukOpcm8qKn5ry8lxdPw0xoEiTQQYEXKCgbpR8D/4kquzknHHopfz2bHQje/pGgO1owegvANwBANYKHdDnNLzqYByDnkI+kAEb4jDnbfIEV0DrVkG1rPfaOuglAimivEPSoPGBum3LEUKrIJXZsxKqAZsTzpUc9xWko+IB1c+c1kPYb3b2d/TH7SNTcb8YjVjIe6F+jNHqLiuvjX8tV0fcXDJq3LtNJdx2VjyNFrEkqz94LDiOsW2G0vO0c2qAN9AwWGMYYRHyr4IiDs4AT24EdedTvtNKBOkMWOK9FuIx7b4Q1/D73648jocjanFrZ46yR5wKfVoKVys42CdD9ONSkDnlhQpXugVxt1cDqi6Nk1naDvJtx0RSVkeYNzE3dVw2dPUtN2Awm++Fjc31H2ox+FS/lGmhGEykzLSI6oC3BYxQy7kf9cJfxQrQv9czgf3eJ11fjJBnLFT7B+vVK3TlRbcDrhSVXEo1MOm96cJB9tZ2t7IXGHiWN+/PPVW2ebyzg0zCeLjgTt+vM8KX7rjqWlZ6FV5crw29cv4mKP0Uq2rw2YgZm0iBAQtrydLYkCNafDKY6oXP9ffYQOX+cXPSOPa/CgDcSqfz+MGle1Ih9ZBAGb9YG9eaNPmRzRszrPbpdtzRZ6/qSet9I6v8nXDMIYe+ESBkdVnMk3G3yXT+u5W6tDRcKJ+jJcid7+8kV1TgkJDbUwa2vI0ov4bNpBEd42rOeUxxq/mR5tv7NhWQkmYvHkqPXKf/PEBoAP6Wh7R0amgpOeT4cIxLV5IpO0MvH4yga1YSbfapQyclo2GLSQjM9tkf/8sLgnjFRhIixCARTYdyJxYJzeZIFORzZ1hTLuKQYTWFhqZX/6e+2nLeJaOws35AZ/psw9Uxtr32SYZxHj7tBtWKTZPGzTaZ1bXJ2NgD++BWmqjoqjXDuba3me31tWhDGt3870lQHraOm2tk2WWiaBIMp/b4Jx5ApwQ7+euBofTmqDiNB5e7AgBzqFHmPlHkt6w7MQ69WsztflYTT+UqRlVD51smxTqaYXVcqtepkTUeEqGCWmrg1PhfDFeu85vWkwaU7EX2UDTUxK3sxFAjSCKWkYo6yuV1O7WZJUSjl/PnKc4t0NRnZoF1fgums+xkaG/wazHfkR13g2eYrwxHqa2FnTABdI+YysvjtDlgPAMJT8L9Wr7maEgYed4nAvIHqTSBaNYW9sz3DVWlpsPQYWhB19H5/8rVkvi2a2Ww9HClYVlP2OS9fdn6qVMDXmb/V5ad7Z7vWg9vWzKbBz5bIpVw2cF3I6LpMJLCEj2jKx1+ReUXeljTVnQembAXEh3gxuobUn/RrPCznww9QBFhAjj8rp9V2pZYnCDcL9ywhEl2oqtrxaWOxToYzYskxtrjIpGuySUreC0RAFnZNPlXXMw2fdeEwa8MzOLya23CAfIhSE3rIOdsMi3Xr2N2RoIKcA";
		String referenceCode = "75b10f9f-b4f0-455d-ba6a-e68c6dc626ce"; //TODO
		String endPoint = "http://karvatgroup.org/trawelltag/v2/AgentAPIV2.asmx?WSDL";
		CreatePolicy createPolicy = buildCreatePolicyRequest(encryptedTextTest,referenceCode);
		String dataResponse = callInsuranceCreatePolicy(createPolicy,endPoint);
		return dataResponse;
	}

	private static String callInsuranceCreatePolicy(CreatePolicy createPolicy, String endPoint) {
		String dataResponse = null;
		try {
			//System.out.println(endPoint);
			AgentAPIV2  agentAPIVService = new AgentAPIV2();
			AgentAPIV2Soap agentAPIV2SoapPort =agentAPIVService.getAgentAPIV2Soap();
			BindingProvider bindingProvider = (BindingProvider) agentAPIV2SoapPort;
			bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,endPoint);
			//"http://xx.xx.xx.xx/myservice/MYGateway");
			Map<String, Object> req_ctx = bindingProvider.getRequestContext();
			Map<String, List<String>> headers = new HashMap<String, List<String>>();
			headers.put(HttpHeaders.ACCEPT_ENCODING, Collections.singletonList("gzip"));
			req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
			dataResponse = agentAPIV2SoapPort.createPolicy(createPolicy.getData(), createPolicy.getRef());
			//System.out.println("dataResponse Response is : "+dataResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataResponse;
	}

	private static CreatePolicy buildCreatePolicyRequest(String encryptedTextTest, String referenceCode) {
		CreatePolicy createPolicy = new CreatePolicy();
		createPolicy.setData(encryptedTextTest);
		createPolicy.setRef(referenceCode);
		return createPolicy;
	}
}
