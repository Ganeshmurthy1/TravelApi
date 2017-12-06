package com.tayyarah.flight.util.api.travelport;

import java.util.HashMap;
import java.util.Map;

public class FareRuleConstants {

	private Map<String,String> CategoryMap = new HashMap<String,String>();
	public FareRuleConstants(){
		CategoryMap.put("0","APPLICATION AND OTHER CONDITIONS");
		CategoryMap.put("4","FLIGHT APPLICATION");
		CategoryMap.put("5","ADVANCE RES/TICKETING");
		CategoryMap.put("6","MINIMUM STAY");
		CategoryMap.put("7","MAXIMUM STAY");
		CategoryMap.put("8","STOPOVERS");
		CategoryMap.put("9","TRANSFERS");
		CategoryMap.put("10","PERMITTED COMBINATIONS");
		CategoryMap.put("12","SURCHARGES");
		CategoryMap.put("15","SALES RESTRICTIONS");
		CategoryMap.put("16","PENALTIES");
		CategoryMap.put("18","TICKET ENDORSEMENT");
		CategoryMap.put("19","CHILDREN DISCOUNTS");
		CategoryMap.put("21","AGENT DISCOUNTS");
		CategoryMap.put("22","ALL OTHER DISCOUNTS");
		CategoryMap.put("23","MISCELLANEOUS PROVISIONS");
		CategoryMap.put("31","VOLUNTARY CHANGES");
	}
	public String getCategoryName(String key){
		String result=CategoryMap.get(key);	
		if(result == null){
			result="OTHER RULES";
		}
		return result;
	}
}