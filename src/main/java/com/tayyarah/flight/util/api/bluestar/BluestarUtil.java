/**
@Author yogeshsharma
20-Nov-2015 2015
BluestarUtil.java
*/
/**
 * 
 */
package com.tayyarah.flight.util.api.bluestar;

import java.util.HashMap;
import java.util.Map;

public class BluestarUtil {

	public static Map<String, String> getCabinMap()
	{
		Map<String, String> ClassMap = new HashMap<String, String>();
		ClassMap.put("Y", "Economy");
		ClassMap.put("C", "Business");
		ClassMap.put("F", "First Class");
		return ClassMap;
	}
}
