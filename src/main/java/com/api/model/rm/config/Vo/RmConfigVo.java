package com.api.model.rm.config.Vo;

import java.util.List;

public class RmConfigVo {
	/**
	 * @author      : Shaik Basha
	 * @createdAt   : 12-20-2017
	 */
	private String txKey;
	private String serviceType;
	private List<RmConfigFields> rmConfigList;
	
	public List<RmConfigFields> getRmConfigList() {
		return rmConfigList;
	}
	public void setRmConfigList(List<RmConfigFields> rmConfigList) {
		this.rmConfigList = rmConfigList;
	}
	
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getTxKey() {
		return txKey;
	}
	public void setTxKey(String txKey) {
		this.txKey = txKey;
	}
	
	
	

}
