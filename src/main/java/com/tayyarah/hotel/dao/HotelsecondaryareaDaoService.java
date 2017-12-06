package com.tayyarah.hotel.dao;

import java.util.List;

import com.tayyarah.hotel.entity.Hotelsecondaryarea;



public interface HotelsecondaryareaDaoService {
	
	public  List<Hotelsecondaryarea> getEntityByVid(String hotelsecondaryVid ) throws Exception;
	public List<Hotelsecondaryarea> getEntityById(int vendorId) throws Exception;
}
