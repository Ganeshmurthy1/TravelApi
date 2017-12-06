package com.tayyarah.hotel.dao;
import java.util.List;

import com.tayyarah.hotel.model.RzFacility;


public interface RzFacilityDaoService {
	public RzFacility getEntityVendorId(int VendorID) throws Exception;
	public  List<RzFacility> getEntityByVid(String HotelRoomDesVid ) throws Exception;
}
