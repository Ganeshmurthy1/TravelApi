package com.tayyarah.hotel.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.entity.Hotelsecondaryarea;



public class HotelsecondaryareaDaoServiceImp implements HotelsecondaryareaDaoService {
@Autowired
HotelsecondaryareaDao hotelsecondarydao;

@Override
public List<Hotelsecondaryarea> getEntityByVid(String hotelsecondaryVid) throws Exception {
	// TODO Auto-generated method stub
	return hotelsecondarydao.getByVendorId(hotelsecondaryVid);
}


@Override
public  List<Hotelsecondaryarea> getEntityById(int vendorId) throws Exception {
	return hotelsecondarydao.getHotelById(vendorId);
	// TODO Auto-generated method stub
	
}




	
	

	

}
