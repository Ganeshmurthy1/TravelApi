package com.tayyarah.hotel.dao;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tayyarah.hotel.model.HotelOverview;

public class HoteloverviewDaoImp implements HoteloverviewDao {
	@Autowired
	private SessionFactory hotelsessionFactory;	
	@Override
	public HotelOverview getHotelOverviewByVendorID(String VendorID)
			throws HibernateException {	
		HotelOverview hotelOverview = null;
		Session session = null;
		try{
			session = hotelsessionFactory.openSession();			
			String sql = "from HotelOverview  hv where hv.apiVendorId=:api_vendor_id";
			hotelOverview = (HotelOverview) session.createQuery(sql)
					.setParameter("api_vendor_id", VendorID)
					.uniqueResult();
		}catch (HibernateException e) {		
			throw e; 
		}finally {
			if(session != null)
				session.close();  
		}
		return hotelOverview;
	}
	
	@Override
	public Map<String, HotelOverview> getHotelOverviewByVendorID(List<String> vendorIdList) throws HibernateException {
		Map<String, HotelOverview> hotelmap = new HashMap<String, HotelOverview>();
		StringBuilder vendorIdinquerysection = new StringBuilder();
		for (int i = 0; i < vendorIdList.size(); i++) {
			if(i == vendorIdList.size()-1)
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"'");
			else
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"',");
			}	
		Session session = null;
		try{
			session = hotelsessionFactory.openSession();			
			String sql = "from HotelOverview hv  where hv.apiVendorId IN (:vendorIds)";
			Query query = session.createQuery(sql);
			query.setParameterList("vendorIds", vendorIdList);
			query.setReadOnly(true);
			List<HotelOverview> list = query.list();		
			for (HotelOverview hotelOverview : list)
				hotelmap.put(hotelOverview.getApiVendorId(), hotelOverview);

		}catch (HibernateException e) {			
			throw e; 
		}finally {
			if(session != null)
				session.close();  
		}
		return hotelmap;
	}
	
	@Override
	public Map<String, HotelOverview> getHotelOverviewNativeMapByVendorIDs(List<String> vendorIdList, String apiProviderId) throws HibernateException {
		Map<String, HotelOverview> hotelmap = new HashMap<String, HotelOverview>();
		StringBuilder vendorIdinquerysection = new StringBuilder();
		for (int i = 0; i < vendorIdList.size(); i++) {
			if(i == vendorIdList.size()-1)
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"'");
			else
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"',");
		}		
		Session session = null;
		try{
			session = hotelsessionFactory.openSession();
			String sql = "from HotelOverview hv where hv.apiVendorId IN (:VendorIds)  and hv.apiProviderId=:api_provider_id"; 
			Query query = session.createQuery(sql);
			query.setParameterList("VendorIds", vendorIdList);
			query.setParameter("api_provider_id", apiProviderId);
			query.setReadOnly(true);
			List<HotelOverview> list = query.list();				
			for (HotelOverview hotelOverview : list)
				hotelmap.put(hotelOverview.getApiVendorId(), hotelOverview);

		}catch (HibernateException e) {		
			throw e; 
		}finally {
			if(session != null)
				session.close();  
		}
		return hotelmap;
	}

	@Override
	public Map<String, HotelOverview> getHotelOverviewApiMapByVendorIDs(List<String> vendorIdList, String apiProviderId) throws HibernateException {
		Map<String, HotelOverview> hotelmap = new HashMap<String, HotelOverview>();
		StringBuilder vendorIdinquerysection = new StringBuilder();
		for (int i = 0; i < vendorIdList.size(); i++) {
			if(i == vendorIdList.size()-1)
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"'");
			else
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"',");
		}		
		Session session = null;
		try{
			session = hotelsessionFactory.openSession();
			String sql = "from HotelOverview hv where hv.apiVendorId IN (:VendorIds)  and hv.apiProviderId=:api_provider_id"; 
			Query query = session.createQuery(sql);
			query.setParameterList("VendorIds", vendorIdList);
			query.setParameter("api_provider_id", apiProviderId);
			query.setReadOnly(true);
			List<HotelOverview> list = query.list();		
			for (HotelOverview hotelOverview : list)
				hotelmap.put(String.valueOf(hotelOverview.getHoteId()), hotelOverview);

		}catch (HibernateException e) {
			throw e; 
		}finally {
			if(session != null)
				session.close();  
		}
		return hotelmap;
	}
}