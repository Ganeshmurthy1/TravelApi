package com.tayyarah.hotel.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import com.tayyarah.hotel.model.Facility;

public class HotelFacilityDaoImp implements HotelFacilityDao{
	@Autowired
	private SessionFactory hotelsessionFactory;	
	public static final Logger logger = Logger.getLogger(HotelFacilityDaoImp.class);	

	@SuppressWarnings("unchecked")
	@Override
	public List<Facility> getByVendorId(String searchVid) throws HibernateException {
		Session session = hotelsessionFactory.openSession();		
		Criteria criteria=session.createCriteria(Facility.class);
		criteria.add(Restrictions.eq("apiVendorId", searchVid));
		List<Facility> list = criteria.list();
		if (list!=null && list.size() > 0) {
			session.close();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Facility> getDescriptionByVendorProperty(String VendorID,
			String AmenityType) {		
		List<Facility> hotelDesc=null;
		Session session=null;
		try{
			session = hotelsessionFactory.openSession();		
			Criteria criteria=session.createCriteria(Facility.class);
			Conjunction conjuction=Restrictions.conjunction();
			conjuction.add(Restrictions.eq("apiVendorId", VendorID));
			conjuction.add(Restrictions.eq("AmenityType", AmenityType));
			hotelDesc = criteria.add(conjuction).list();
		}
		catch(HibernateException e){
			logger.info("HibernateException---"+e.getMessage());
		}finally {
			session.close();
		}
		return hotelDesc;
	}

	@Override
	public Map<String, List<Facility>> getFacilityByVendorID(List<String> vendorIdList, String AmenityType)
			throws HibernateException {
		Map<String, List<Facility>> facilitymap = new HashMap<String, List<Facility>>();

		StringBuilder vendorIdinquerysection = new StringBuilder();
		for (int i = 0; i < vendorIdList.size(); i++) {
			if(i == vendorIdList.size()-1)
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"'");
			else
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"',");

			facilitymap.put(vendorIdList.get(i), new ArrayList<Facility>());
		}		
		Session session = null;
		Query query=null;
		String sql = null;
		try{
			session = hotelsessionFactory.openSession();			
			if(AmenityType != null){
				sql = "from Facility fc where fc.apiVendorId IN (:vendorIds) and  fc.AmenityType=:amenityType";
				query = session.createQuery(sql);
				query.setParameterList("vendorIds", vendorIdList);
				query.setParameter("amenityType", AmenityType);
			}
			else{
				sql = "from Facility fc where fc.apiVendorId IN (:vendorIds)";
				query = session.createQuery(sql);
				query.setParameterList("vendorIds", vendorIdList);
			}
			query.setReadOnly(true);
			List<Facility> list = query.list();				
			Map<String, List<Facility>> tempmap = facilitymap;
			for (Facility facility : list)
			{
				List<Facility> existlist = facilitymap.get(facility.getApiVendorId());
				existlist.add(facility);
				tempmap.put(facility.getApiVendorId(), existlist);
			}		
			return tempmap;

		}catch (HibernateException e) {			
			e.printStackTrace(); 
		}finally {
			if(session != null)
				session.close(); 
		}
		return facilitymap;
	}

	@Override
	public Map<String, List<Facility>> getFacilityNativeMapByVendorIDs(List<String> vendorIdList, String amenityType,
			String apiProviderId) throws HibernateException {
		Map<String, List<Facility>> facilitymap = new HashMap<String, List<Facility>>();

		StringBuilder vendorIdinquerysection = new StringBuilder();
		for (int i = 0; i < vendorIdList.size(); i++) {
			if(i == vendorIdList.size()-1)
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"'");
			else
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"',");
			facilitymap.put(vendorIdList.get(i), new ArrayList<Facility>());
		}		
		Session session = null;
		Query query=null;
		String sql = null;
		try{
			session = hotelsessionFactory.openSession();			
			if(amenityType != null){
				sql = "from Facility fc where fc.apiVendorId IN (:vendorIds) and  fc.AmenityType=:amenityType";
				query = session.createQuery(sql);
				query.setParameterList("vendorIds", vendorIdList);
				query.setParameter("amenityType", amenityType);
			}
			else{
				sql = "from Facility fc where fc.apiVendorId IN (:vendorIds)";	
				query = session.createQuery(sql);
				query.setParameterList("vendorIds", vendorIdList);
			}
			query.setReadOnly(true);
			List<Facility> list = query.list();			
			Map<String, List<Facility>> tempmap = facilitymap;
			for (Facility facility : list)
			{
				List<Facility> existlist = facilitymap.get(facility.getApiVendorId());
				existlist.add(facility);
				tempmap.put(facility.getApiVendorId(), existlist);
			}			
			return tempmap;
		}catch (HibernateException e) {		
			e.printStackTrace(); 
		}finally {if(session != null)
			session.close(); session.close(); 
		}
		return facilitymap;
	}


	@Override
	public Map<String, List<Facility>> getFacilityApiMap(Map<String, Integer> hotelidmap, String amenityType) throws HibernateException {
		Map<String, List<Facility>> facilitymap = new HashMap<String, List<Facility>>();
		List<Integer> hotelids = new  ArrayList<>();	
		int i = 0;
		for (Entry<String, Integer> entry : hotelidmap.entrySet()) {			
			facilitymap.put(entry.getKey(), new ArrayList<Facility>());
			i++;
		}		
		Session session = null;
		String sql=null;
		Query query=null;
		try{
			session = hotelsessionFactory.openSession();
			if(amenityType != null){
				sql = "from Facility fc where fc.hoteId IN (:hotelids) and fc.AmenityType =:AmenityType";			
				query = session.createQuery(sql);
				query.setParameterList("hotelids", hotelids);
				query.setParameter("AmenityType", amenityType);
			}
			else{
				sql = "from Facility fc where fc.hoteId IN (:hotelids)";	
				query = session.createQuery(sql);
				query.setParameterList("hotelids", hotelids);
			}			
			query.setReadOnly(true);
			List<Facility> list = query.list();			
			Map<String, List<Facility>> tempmap = facilitymap;
			for (Facility facility : list)
			{
				List<Facility> existlist = facilitymap.get(facility.getApiVendorId());
				existlist.add(facility);
				tempmap.put(facility.getApiVendorId(), existlist);
			}		
			return tempmap;
		}catch (HibernateException e) {			
			e.printStackTrace(); 
		}finally {
			if(session != null)
				session.close();  
		}
		return facilitymap;
	}
}