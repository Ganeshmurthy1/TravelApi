package com.tayyarah.hotel.dao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.RzHotelOverview;

public class RzHotelOverviewDaoServiceImp implements RzHotelOverviewDaoService {
	@Autowired
	RzHotelOverviewDao rzoverviewDao;
	@Override
	public RzHotelOverview getEntityVendorId(int VendorID) throws Exception {
		// TODO Auto-generated method stub
		return rzoverviewDao.getHotelByVendoriD(VendorID);
	}

	@Override
	public List<RzHotelOverview> getEntityByVid(String HotelRoomDesVid)
			throws Exception {
		// TODO Auto-generated method stub
		return rzoverviewDao.getByVendorId(HotelRoomDesVid);
	}

}
