/**
@Author yogeshsharma
18-Aug-2015 2015
AirFareRuleResponseParser.java
*/
/**
 * 
 */
package com.tayyarah.flight.util.api.travelport;


import java.util.ArrayList;
import java.util.List;
import com.tayyarah.flight.model.FareRuleResponse;
import com.tayyarah.flight.model.NewFareRule;
import com.travelport.api_v33.AirResponse.AirFareRulesRsp;
import com.travelport.api_v33.AirResponse.FareRule;
import com.travelport.api_v33.AirResponse.FareRuleLong;

public class AirFareRuleResponseParser {

	/**
	 * @param airFareRulesRsp
	 * @return
	 */
	public static FareRuleResponse parseFareRule(AirFareRulesRsp airFareRulesRsp) {	
		FareRuleResponse FRR=new FareRuleResponse();
		FareRuleConstants fareRuleConstants=new FareRuleConstants();
		for (FareRule FareRule : airFareRulesRsp.getFareRule()) {
			 FRR=new FareRuleResponse();
			if(FareRule.getFareRuleLong() != null
					&& FareRule.getFareRuleLong().size() > 0) {
				List<NewFareRule> fareruleList=new ArrayList<NewFareRule>();
				for (FareRuleLong fareRuleLong : FareRule.getFareRuleLong()) {
					NewFareRule NFR = new NewFareRule();					
					NFR.setCategory(fareRuleLong.getCategory());
					NFR.setType(fareRuleLong.getType());
					NFR.setValue(fareRuleLong.getValue());
					NFR.setHeader(fareRuleConstants.getCategoryName(String.valueOf(fareRuleLong.getCategory())));
					fareruleList.add(NFR);
				}
				FRR.setFareruleList(fareruleList);
			}			
			FRR.setFareInfoRef(FareRule.getFareInfoRef());
			FRR.setProviderCode(FareRule.getProviderCode());			
		}
		return FRR;		
	}
}