package com.tayyarah.hotel.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;

import com.tayyarah.common.model.AppKeyVo;
import com.tayyarah.company.entity.CompanyConfig;
import com.tayyarah.hotel.entity.HotelMarkup;

public interface HotelMarkupDao {	
	public HotelMarkup insertHotelMarkup(HotelMarkup hm)throws HibernateException, IOException;
	public List<HotelMarkup> getHotelMarkups(int companyId) throws HibernateException, IOException; 
	public List<HotelMarkup> getHotelMarkups(int companyId, int configId) throws HibernateException, IOException; 
	public List<HotelMarkup> getHotelMarkups(int companyId, int configId, int isaccumulative) throws HibernateException, IOException; 	
	public List<HotelMarkup> getAllHotelMarkups() throws HibernateException, IOException;  	
	public Map<String,List<HotelMarkup>> getHotelMarkUpConfigMapByCompanyId(AppKeyVo appKeyVo, Map<String,List<HotelMarkup>>  markupMap)throws SQLException, Exception; 	
	public Map<String,List<HotelMarkup>> getParentMarkupMap(CompanyConfig companyConfig, CompanyConfig parentCompConfig, Map<String,List<HotelMarkup>>  markupMap)throws SQLException, Exception;	
}
