/**
@Author yogeshsharma
20-Nov-2015 2015
PriceComparator.java
*/
/**
 * 
 */
package com.tayyarah.flight.util.api.bluestar;

import java.io.Serializable;
import java.util.Comparator;

public class PriceComparator implements Comparator,Serializable {
	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub

		int s1 = Integer.parseInt(o1.toString());
		int s2 = Integer.parseInt(o2.toString());

		if (s1 == s2)
			return 0;
		else if (s1 > s2)
			return 1;
		else
			return -1;
	}
}