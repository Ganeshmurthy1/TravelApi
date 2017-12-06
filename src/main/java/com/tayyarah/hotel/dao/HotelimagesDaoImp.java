package com.tayyarah.hotel.dao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import com.tayyarah.hotel.model.Hotelimage;


public class HotelimagesDaoImp implements HotelimagesDao{
	@Autowired
	private SessionFactory hotelsessionFactory;
	@SuppressWarnings("unchecked")
	@Override
	public List<Hotelimage> getByVendorId(String HotelImageVid)  {
		Session session = hotelsessionFactory.openSession();		
		String sql = "from Hotelimage hi  where hi.apiVendorId=api_vendor_id=";
		Query query = session.createQuery(sql);
		query.setParameter("api_vendor_id", HotelImageVid);
		query.setReadOnly(true);
		List<Hotelimage> list = query.list();
		if (list.size() > 0) {
			session.close();
		}
		return list;
	}

	@Override
	public List<String> getImagesByVendorID(String vendorID)  {		
		List<String> hotelimages= new ArrayList<>();
		Session session = hotelsessionFactory.openSession();		
		String sql = "from Hotelimage hi  where hi.apiVendorId=:api_vendor_id";
		Query query=session.createQuery(sql);
		query.setParameter("api_vendor_id", vendorID);
		List<Hotelimage> hotelimageList=query.list();
		if(hotelimageList!=null && hotelimageList.size()>0 ){
			for(Hotelimage image:hotelimageList){
				hotelimages.add(image.getImageUrl());
			}
		}
		session.close();
		return hotelimages;
	}


	@Override
	public Map<String, List<String>> getImagesByVendorID(List<String> vendorIdList) throws HibernateException {
		Map<String, List<String>> imagesmap = new HashMap<String, List<String>>();
		List<Integer> vendorIds=new ArrayList<>();
		if(vendorIdList!=null && vendorIdList.size()>0 ){
			for(String vendorId:vendorIdList){
				vendorIds.add(Integer.parseInt(vendorId));
			}
		}
		StringBuilder vendorIdinquerysection = new StringBuilder();
		for (int i = 0; i < vendorIdList.size(); i++) {
			if(i == vendorIdList.size()-1)
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"'");
			else
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"',");
			imagesmap.put(vendorIdList.get(i), new ArrayList<String>());
		}		
		Session session = null;
		try{
			session = hotelsessionFactory.openSession();		
			String sql = "from Hotelimage hi  where hi.apiVendorId IN (:vendorIds)";
			Query query = session.createQuery(sql);
			query.setParameterList("vendorIds", vendorIds);
			query.setReadOnly(true);
			List<Hotelimage> list = query.list();		
			for (Hotelimage hotelimage : list)
			{
				List<String> existlist = imagesmap.get(hotelimage.getApiVendorId());
				existlist.add(hotelimage.getImageUrl());
				imagesmap.put(hotelimage.getApiVendorId(), existlist);
			}

		}catch (HibernateException e) {			
			throw e; 
		}finally {
			if(session != null)
				session.close();  
		}		
		return imagesmap;
	}

	@Override
	public Map<String, List<String>> getImagesMapByVendorIDs(List<String> vendorIdList, String apiProviderId,
			boolean isCommon) throws HibernateException {
		Map<String, List<String>> imagesmap = new HashMap<String, List<String>>();
		List<Integer> vendorIds=new ArrayList<>();
		if(vendorIdList!=null && vendorIdList.size()>0 ){
			for(String vendorId:vendorIdList){
				vendorIds.add(Integer.parseInt(vendorId));
			}
		}		
		StringBuilder vendorIdinquerysection = new StringBuilder();
		for (int i = 0; i < vendorIdList.size(); i++) {
			if(i == vendorIdList.size()-1)
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"'");
			else
				vendorIdinquerysection.append("'"+vendorIdList.get(i)+"',");
			imagesmap.put(vendorIdList.get(i), new ArrayList<String>());
		}		
		Session session = null;
		Query query=null;
		String sql=null;
		try{
			session = hotelsessionFactory.openSession();			
			if(isCommon){
				sql = "from Hotelimage hi where hi.hoteId IN (:vendorIds)";
				query = session.createQuery(sql);
				query.setParameterList("vendorIds", vendorIds);

			}else{
				sql = "from Hotelimage hi where hi.apiVendorId IN (:vendorIds)";
				query = session.createQuery(sql);
				query.setParameterList("vendorIds", vendorIds);
			}
			query.setReadOnly(true);
			List<Hotelimage> list = query.list();				 
			for (Hotelimage hotelimage : list)
			{
				if(!isCommon)
				{
					List<String> existlist = imagesmap.get(hotelimage.getApiVendorId());
					existlist.add(hotelimage.getImageUrl());
					imagesmap.put(hotelimage.getApiVendorId(), existlist);
				}
				else
				{
					List<String> existlist = imagesmap.get(hotelimage.getHoteId());
					existlist.add(hotelimage.getImageUrl());
					imagesmap.put(String.valueOf(hotelimage.getHoteId()), existlist);
				}
			}

		}catch (HibernateException e) {			
			throw e; 
		}finally {
			if(session != null)
				session.close();  
		}		
		return imagesmap;
	}

	@Override
	public Map<String, List<String>> getImagesApiMap(Map<String, Integer> hotelidmap) throws HibernateException {
		Map<String, List<String>> imagesmap = new HashMap<String, List<String>>();

		StringBuilder hotelidquerysection = new StringBuilder();
		List<Integer> hotelids = new ArrayList<>();
		int i=0;
		for (Entry<String, Integer> entry : hotelidmap.entrySet()) {
			hotelids.add(entry.getValue());
			if(i == hotelidmap.size()-1)
				hotelidquerysection.append(""+entry.getValue()+"");
			else
				hotelidquerysection.append(""+entry.getValue()+",");
			imagesmap.put(entry.getKey(), new ArrayList<String>());
			i++;
		}		
		Session session = null;
		try{
			session = hotelsessionFactory.openSession();		
			String sql = "from Hotelimage hi where hi.hoteId IN (:hotelIds)";			
			Query query = session.createQuery(sql);
			query.setParameterList("hotelIds", hotelids);
			query.setReadOnly(true);
			List<Hotelimage> list = query.list();		
			for (Hotelimage hotelimage : list)
			{
				List<String> existlist = imagesmap.get(hotelimage.getApiVendorId());
				existlist.add(hotelimage.getImageUrl());
				imagesmap.put(hotelimage.getApiVendorId(), existlist);				
			}

		}catch (HibernateException e) {			
			throw e; 
		}finally {
			if(session != null)
				session.close();  
		}
		return imagesmap;
	}
}