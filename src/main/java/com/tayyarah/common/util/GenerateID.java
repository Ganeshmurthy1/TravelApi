package com.tayyarah.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author ilyas
 * 8/10/2015
 *
 */
public class GenerateID {	
	private final String ID;
	
	public GenerateID(){
		synchronized (GenerateID.class) {
			DateFormat df1 = new SimpleDateFormat("yyMMddHHmmssSSS");
			Calendar calobj1 = Calendar.getInstance();
			ID = df1.format(calobj1.getTime()).toString();
			Thread ti = new Thread();
			ti.start();
			try {
				ti.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ti=null;
		}
	}
	public String toString() {
		return ID;
	}
}
