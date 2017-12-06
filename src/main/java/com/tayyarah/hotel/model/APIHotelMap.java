package com.tayyarah.hotel.model;

import java.io.Serializable;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.hibernate.HibernateException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS;
import com.tayyarah.hotel.entity.HotelMarkup;
import com.tayyarah.hotel.model.OTAHotelAvailRS.RoomStays.RoomStay;
import com.tayyarah.hotel.util.HotelMarkUpApplier;
import com.tayyarah.hotel.util.HotelMarkUpUtil;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIHotelMap implements Serializable, HotelMarkUpApplier {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OTAHotelAvailRS travelapi;		
	private com.tayyarah.hotel.model.OTAHotelAvailRS lintasapi;	
	private BigInteger searchKey;		
	@JsonIgnore
	private int apiId;
	private BigInteger transactionKey;
	private String correlationId;
	private String hotelcode;
	private String roomid;
	private APIStatus status;
	protected com.tayyarah.hotel.model.TPAExtensions tpaExtensions;
	private TreeMap<String, RoomStay> roomStaysMap = null;
	private TreeMap<String,  HashMap<Integer,  HashMap<String, Boolean>>> apiProviderMap = null;
	private HotelSearchCommand hotelSearchCommand = null;	
	
	
	public APIHotelMap() {
		super();
		
	}	
	
	public BigInteger getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(BigInteger searchKey) {
		this.searchKey = searchKey;
	}
	public BigInteger getTransactionKey() {
		return transactionKey;
	}
	public void setTransactionKey(BigInteger transactionKey) {
		this.transactionKey = transactionKey;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getHotelcode() {
		return hotelcode;
	}
	public void setHotelcode(String hotelcode) {
		this.hotelcode = hotelcode;
	}
	public String getRoomid() {
		return roomid;
	}
	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
	public int getApiId() {
		return apiId;
	}
	public void setApiId(int apiId) {
		this.apiId = apiId;
	}
	public APIStatus getStatus() {
		return status;
	}
	public void setStatus(APIStatus status) {
		this.status = status;
	}	
	public TPAExtensions getTpaExtensions() {
		return tpaExtensions;
	}
	public void setTpaExtensions(TPAExtensions tpaExtensions) {
		this.tpaExtensions = tpaExtensions;
	}	
	/**
	 * @return the travelapi
	 */
	public OTAHotelAvailRS getTravelapi() {
		return travelapi;
	}
	/**
	 * @param travelapi the travelapi to set
	 */
	public void setTravelapi(OTAHotelAvailRS travelapi) {
		this.travelapi = travelapi;
	}	
	
	/**
	 * @return the lintasapi
	 */
	public com.tayyarah.hotel.model.OTAHotelAvailRS getLintasapi() {
		return lintasapi;
	}
	/**
	 * @param lintasapi the lintasapi to set
	 */
	public void setLintasapi(com.tayyarah.hotel.model.OTAHotelAvailRS lintasapi) {
		this.lintasapi = lintasapi;
	}
	public TreeMap<String, HashMap<Integer, HashMap<String, Boolean>>> getApiProviderMap() {
		if(apiProviderMap == null)
		{
			apiProviderMap = new TreeMap<String, HashMap<Integer,HashMap<String,Boolean>>> ();
		}
		return apiProviderMap;
	}	
	public void setApiProviderMap(
			TreeMap<String, HashMap<Integer, HashMap<String, Boolean>>> apiProviderMap) {
		this.apiProviderMap = apiProviderMap;
	}		
	public HotelSearchCommand getHotelSearchCommand() {
		return hotelSearchCommand;
	}
	public void setHotelSearchCommand(HotelSearchCommand hotelSearchCommand) {
		this.hotelSearchCommand = hotelSearchCommand;
	}	
	public TreeMap<String, RoomStay> getRoomStays() {		
		return roomStaysMap;
	}
	public void setRoomStays(TreeMap<String, RoomStay> roomStaysMap) {
		this.roomStaysMap = roomStaysMap;
	}	
	public void clearUnDeliverables() {
		HashMap<String, RoomStay> tsmap = new HashMap<String, RoomStay>();
		for (Map.Entry<String, RoomStay> entry : this.roomStaysMap.entrySet()) {
		    String key = entry.getKey();
		    RoomStay rs = entry.getValue();
		    rs.setRatePlans(null);
		    rs.setRoomRates(null);
		    rs.setRoomTypes(null);		    
		    tsmap.put(key, rs);
		}
	}
	public void applyMarkUpAdd(String hotelcode, RoomStay rs) throws HibernateException, Exception {
		//this.roomStaysMap.put(key, value)		
	}
	public void applyMarkUpAdd(HashMap<String, RoomStay> rsmap) throws HibernateException, Exception {
		// TODO Auto-generated method stub		
	}
	public void applyMarkUpInit(HashMap<String, RoomStay> rsmap) throws HibernateException, Exception {
		// TODO Auto-generated method stub		
	}
	public void applyMarkUpInit(HotelSearchCommand hs, List<HotelMarkup> markups) throws HibernateException, Exception {
		this.roomStaysMap = HotelMarkUpUtil.applyMarkUpOnHotelResult(hs, markups, this.roomStaysMap);
		
	}
	@Override
	public void applyMarkUpInitHotelSearch(HotelSearchCommand hs, List<HotelMarkup> markups)
			throws HibernateException, Exception {
		HashMap<String, RoomStay> tsmap = new HashMap<String, RoomStay>();
		for (Map.Entry<String, RoomStay> entry : this.roomStaysMap.entrySet()) {
		    String key = entry.getKey();
		    RoomStay rs = entry.getValue();
		    rs = HotelMarkUpUtil.applyMarkUpOnHotel(hs, markups,rs);	
		    //rs = HotelMarkUpUtil.applyMarkUpOnHotelRoomDetail(hs, markups, rs);
		    tsmap.put(key, rs);
		}
		
	}
	@Override
	public void applyAllLevelMarkUpInitHotelSearch(HotelSearchCommand hs,  HotelMarkupCommissionDetails hotelmarkupCommissionDetails)
			throws HibernateException, Exception {
		HashMap<String, RoomStay> tsmap = new HashMap<String, RoomStay>();
		for (Map.Entry<String, RoomStay> entry : this.roomStaysMap.entrySet()) {
		    String key = entry.getKey();
		    RoomStay rs = entry.getValue();
		    rs = HotelMarkUpUtil.applyMarkUpOnHotel(hs, hotelmarkupCommissionDetails ,rs);	
		    //rs = HotelMarkUpUtil.applyMarkUpOnHotelRoomDetail(hs, markups, rs);
		    tsmap.put(key, rs);
		}
		
	}	
	public RoomStay clearUnDeliverables(RoomStay rs) {
		rs.setRatePlans(null);
	    rs.setRoomRates(null);
	    rs.setRoomTypes(null);
	    return rs;
	}	
}
