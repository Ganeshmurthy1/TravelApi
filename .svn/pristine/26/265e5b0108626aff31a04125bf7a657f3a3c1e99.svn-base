package com.tayyarah.hotel.reznext.test.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tayyarah.common.util.ResponseHeader;
import com.tayyarah.company.dao.CompanyDao;
import com.tayyarah.hotel.controller.HotelSearchController;
import com.tayyarah.hotel.dao.ApiHotelMapStoreDao;
import com.tayyarah.hotel.dao.HotelMarkupDao;
import com.tayyarah.hotel.dao.HotelSearchDao;
import com.tayyarah.hotel.dao.HotelTransactionDao;
import com.tayyarah.hotel.entity.Islhotelmapping;
import com.tayyarah.hotel.model.OTAHotelAvailRS;
import com.tayyarah.hotel.util.HotelObjectTransformer;


@RestController
@RequestMapping("/hotel/reznextroomdetail")
public class ReznextRoomDetailsController {

	@Autowired
	HotelObjectTransformer hotelObjectTransformer;
	@Autowired
	HotelTransactionDao hotelTransactionDao;
	@Autowired
	HotelSearchDao hotelSearchDao;
	@Autowired
	CompanyDao CDAO;
	@Autowired
	HotelMarkupDao hotelMarkupDao;
	@Autowired
	ApiHotelMapStoreDao apihotelstoredao;


	public static final Logger logger = Logger.getLogger(HotelSearchController.class);

	@RequestMapping(value = "",method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody
	OTAHotelAvailRS.RoomStays.RoomStay getRZRoomStayDetails(@RequestParam(value="searchkey") int searchkey,@RequestParam(value="hotelcode") String hotelcode, HttpServletResponse response) {	

		ResponseHeader.setResponse(response);//Setting response header		
		HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay> apiHotelMap = new HashMap<String, com.tayyarah.api.hotel.travelguru.model.OTAHotelAvailRS.RoomStays.RoomStay>();
		Islhotelmapping islhotelmapping= null;
		try
		{			
			apiHotelMap =apihotelstoredao.getTGRoomStaysMap(searchkey);	
			OTAHotelAvailRS.RoomStays.RoomStay rs;
			rs = hotelObjectTransformer.convertTGtoNative(null, null, apiHotelMap.get(hotelcode));
			return rs;
		} 
		catch (HibernateException e) {
			logger.info("query Exception:HibernateException:"+ e.getMessage());
		}
		catch (IOException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
		}
		catch (ClassNotFoundException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
		} catch (JAXBException e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
		}
		catch (Exception e) {
			logger.info("query Exception:IOException:"+ e.getMessage());
		}
		return null;
	}
}
