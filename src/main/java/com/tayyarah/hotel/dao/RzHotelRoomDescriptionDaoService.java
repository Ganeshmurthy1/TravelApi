package com.tayyarah.hotel.dao;
import java.util.List;

import com.tayyarah.hotel.model.RzHotelRoomDescription;

public interface RzHotelRoomDescriptionDaoService {
	public RzHotelRoomDescription getEntityRoomId(int roomid) throws Exception;
	public  List<RzHotelRoomDescription> getEntityByVid(String HotelRoomDesVid ) throws Exception;
}
