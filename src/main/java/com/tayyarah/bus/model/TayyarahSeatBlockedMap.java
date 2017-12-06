package com.tayyarah.bus.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TayyarahSeatBlockedMap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,List<BusMarkUpConfig>> busMarkUpConfiglistMap = new HashMap<String,List<BusMarkUpConfig>>();
	private BusMarkupCommissionDetails markupCommissionDetails;
	private BusBlockTicketRequest busBlockTicketRequest;
	private BusBlockTicketResponse busBlockTicketResponse;
	public Map<String, List<BusMarkUpConfig>> getBusMarkUpConfiglistMap() {
		return busMarkUpConfiglistMap;
	}
	public BusMarkupCommissionDetails getMarkupCommissionDetails() {
		return markupCommissionDetails;
	}
	public BusBlockTicketRequest getBusBlockTicketRequest() {
		return busBlockTicketRequest;
	}
	public BusBlockTicketResponse getBusBlockTicketResponse() {
		return busBlockTicketResponse;
	}
	public void setBusMarkUpConfiglistMap(Map<String, List<BusMarkUpConfig>> busMarkUpConfiglistMap) {
		this.busMarkUpConfiglistMap = busMarkUpConfiglistMap;
	}
	public void setMarkupCommissionDetails(BusMarkupCommissionDetails markupCommissionDetails) {
		this.markupCommissionDetails = markupCommissionDetails;
	}
	public void setBusBlockTicketRequest(BusBlockTicketRequest busBlockTicketRequest) {
		this.busBlockTicketRequest = busBlockTicketRequest;
	}
	public void setBusBlockTicketResponse(BusBlockTicketResponse busBlockTicketResponse) {
		this.busBlockTicketResponse = busBlockTicketResponse;
	}
}
