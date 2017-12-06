package com.tayyarah.hotel.reposit.dao;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.tayyarah.hotel.reposit.entity.Facility;
import com.tayyarah.hotel.reposit.entity.HotelMealFare;
import com.tayyarah.hotel.reposit.entity.HotelRoomExtraFare;
import com.tayyarah.hotel.reposit.entity.Hotelimage;
import com.tayyarah.hotel.reposit.entity.Hotelinandaround;
import com.tayyarah.hotel.reposit.entity.Hotelmealtype;
import com.tayyarah.hotel.reposit.entity.Hoteloverview;
import com.tayyarah.hotel.reposit.entity.Hotelreview;
import com.tayyarah.hotel.reposit.entity.Hotelroomdescription;
import com.tayyarah.hotel.reposit.entity.Hotelroomfare;
import com.tayyarah.hotel.reposit.entity.Hotelsegment;
import com.tayyarah.hotel.reposit.entity.Mealsfare;
public interface HotelRepositDAO {
	
	public List<Facility> getFacilityById(int id) throws HibernateException;
	public List<Hotelimage> getHotelImageById(int id) throws HibernateException;
	public List<Hotelinandaround> getHotelinandaroundById(int id) throws HibernateException;
	public List<HotelMealFare> getHotelMealFareByVendorId(int vendorid) throws HibernateException;
	public List<Hotelmealtype> getHotelmealtypeByMealId( byte mealtypeid) throws HibernateException;
	public List<Hoteloverview> getHoteloverviewById(int id) throws HibernateException;
	public List<Hoteloverview> getHoteloverviewByCity(String city) throws HibernateException;
	public List<Hotelreview> getHotelreviewById(int id) throws HibernateException;
	public List<Hotelroomdescription> getHotelroomdescriptionById(int id) throws HibernateException;
	public List<HotelRoomExtraFare> getHotelRoomExtraFareById(int id) throws HibernateException;
	public List<Hotelroomfare> getHotelroomfare(int id) throws HibernateException;
	public List<Hotelroomfare> getHotelroomfare(int id, int roomid) throws HibernateException;
	public Map<Integer, Hotelroomfare> getHotelroomfareMap(int id)
			throws HibernateException;
	public List<Hotelsegment> getHotelsegmentById(int segmentId) throws HibernateException;
	public List<Mealsfare> getMealsfareById(int mealfareId) throws HibernateException;
	
}
