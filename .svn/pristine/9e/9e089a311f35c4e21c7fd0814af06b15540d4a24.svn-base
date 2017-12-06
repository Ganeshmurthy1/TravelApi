package com.tayyarah.hotel.util;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.tayyarah.api.hotel.travelguru.staticcontent.model.OTAHotelDescriptiveInfoRS;
import com.tayyarah.api.hotel.travelguru.staticcontent.model.OTAHotelDescriptiveInfoRS.HotelDescriptiveContents.HotelDescriptiveContent;
import com.tayyarah.api.hotel.travelguru.staticcontent.model.OTAHotelDescriptiveInfoRS.HotelDescriptiveContents.HotelDescriptiveContent.FacilityInfo.GuestRooms.GuestRoom;

public class HotelBackUpService {
	static final Logger logger = Logger.getLogger(HotelBackUpService.class);
	public Boolean takeMySqlBackUp()
	{
		Boolean result = false;
		//Process p = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			// p = runtime.exec("mysqldump -uroot -pdbpass --add-drop-database -B dbname -r " + "filepath" + "Filename" + ".sql");
			//  String executeCmd = "C:/Program Files/MySQL/MySQL Server 5.7/bin/mysqldump -u " + dbUserName + " -p" + dbPassword + " --add-drop-database -B " + dbName + " -r " + path;

			//p = runtime.exec("C:/Program Files/MySQL/MySQL Server 5.7/bin/mysqldump -uroot -upassword --add-drop-database -B world -r D://world.sql");

			//p = runtime.exec("C:/Program Files/MySQL/MySQL Server 5.7/bin/mysqldump -uroot -ppassword world > D:/world.sql");


			List<String> args = new ArrayList<String>();
			args.add("C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysqldump.exe");
			//...
			args.add("-u");
			args.add("root");
			args.add("-p" + "password"); //?? Is this a single command?
			args.add("--add-drop-database");
			args.add("-B");
			args.add("tayyarah_hotels");
			args.add("-r");
			args.add("D:\\tayyarah_hotels.sql");
			ProcessBuilder pb = new ProcessBuilder(args);
			pb.redirectError();
			Process p = pb.start();

			InputStream is = p.getInputStream();
			int in = -1;
			while ((in = is.read()) != -1) {
				System.out.print((char)in);
				logger.info((char)in);
			}

			int processCompleted = p.waitFor();
			if (processCompleted == 0) {
				result = true;
				logger.info("Backup created successfully!");

			} else {
				result = false;
				logger.info("Could not create the backup");
			}




			/* logger.info("Backup process called successfully!");
	        //change the dbpass and dbname with your dbpass and dbname
	        int processComplete = p.waitFor();

	        if (processComplete == 0) {
	        	result = true;
	        	logger.info("Backup created successfully!");

	        } else {
	        	result = false;
	        	logger.info("Could not create the backup");
	        }
			 */

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	public Boolean createAndUpdate()
	{
		Boolean result = false;
		//Process p = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			// p = runtime.exec("mysqldump -uroot -pdbpass --add-drop-database -B dbname -r " + "filepath" + "Filename" + ".sql");
			//  String executeCmd = "C:/Program Files/MySQL/MySQL Server 5.7/bin/mysqldump -u " + dbUserName + " -p" + dbPassword + " --add-drop-database -B " + dbName + " -r " + path;

			//p = runtime.exec("C:/Program Files/MySQL/MySQL Server 5.7/bin/mysqldump -uroot -upassword --add-drop-database -B world -r D://world.sql");

			//p = runtime.exec("C:/Program Files/MySQL/MySQL Server 5.7/bin/mysqldump -uroot -ppassword world > D:/world.sql");

			// mysql -uroot -ppassword -e "select * from tayyarah_hotels.hoteloverview"
			List<String> args = new ArrayList<String>();
			args.add("C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysql.exe");
			//...
			args.add("-u");
			args.add("root");
			args.add("-p" + "password"); //?? Is this a single command?
			args.add("-e");	       
			args.add("select * from tayyarah_hotels.hoteloverview");	       
			ProcessBuilder pb = new ProcessBuilder(args);
			pb.redirectError();
			Process p = pb.start();

			InputStream is = p.getInputStream();
			int in = -1;
			while ((in = is.read()) != -1) {
				System.out.print((char)in);
				//logger.info((char)in);
			}

			int processCompleted = p.waitFor();
			if (processCompleted == 0) {
				result = true;
				logger.info("DB traversed successfully!");

			} else {
				result = false;
				logger.info("Could not traverse the DB");
			}

			// Runtime.getRuntime().exec(  new String [] {"mysql", "-u", "root", "-pmanager", "-e", "show databases"} );


			/* logger.info("Backup process called successfully!");
	        //change the dbpass and dbname with your dbpass and dbname
	        int processComplete = p.waitFor();

	        if (processComplete == 0) {
	        	result = true;
	        	logger.info("Backup created successfully!");

	        } else {
	        	result = false;
	        	logger.info("Could not create the backup");
	        }
			 */

		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	public Boolean createHotelOverview()
	{
		Boolean result = false;
		//Process p = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			List<String> args = new ArrayList<String>();
			args.add("C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysql.exe");	        
			args.add("-u");
			args.add("root");
			args.add("-p" + "password"); //?? Is this a single command?
			args.add("-e");	  
			//write query to create table	        
			//String query =  "CREATE TABLE tayyarah_hotels.pet (name VARCHAR(20), owner VARCHAR(20),"
			//		+ "species VARCHAR(20), sex CHAR(1), birth DATE, death DATE)";	        
			String query  = "CREATE TABLE tayyarah_hotels.tg_hoteloverview "
					+ "(hotel_id int(11) NOT NULL AUTO_INCREMENT,VendorName varchar(500) DEFAULT NULL,"
					+ "api_vendor_id varchar(8) NOT NULL,HotelClass varchar(50) DEFAULT NULL,"
					+ "Location varchar(57) DEFAULT NULL,City varchar(50) DEFAULT NULL,"
					+ "Country varchar(50) DEFAULT NULL, Address1 varchar(111) DEFAULT NULL,"
					+ "Address2 varchar(60) DEFAULT NULL,Area varchar(57) DEFAULT NULL,"
					+ "Address varchar(500) DEFAULT NULL,HotelOverview varchar(1493) DEFAULT NULL,"
					+ "ReviewRating varchar(50) DEFAULT NULL,ReviewCount varchar(50) DEFAULT NULL,"
					+ "Latitude decimal(28,15) DEFAULT NULL,Longitude decimal(28,14) DEFAULT NULL,"
					+ "DefaultCheckInTime time DEFAULT NULL,DefaultCheckOutTime time DEFAULT NULL,"
					+ "Hotel_Star int(11) DEFAULT NULL,HotelGroupID varchar(50) DEFAULT NULL,"
					+ "HotelGroupName varchar(69) DEFAULT NULL,ImagePath varchar(108) DEFAULT NULL,"
					+ "HotelSearchKey varchar(85) DEFAULT NULL,Area_Seo_Id varchar(55) DEFAULT NULL,"
					+ "City_Zone varchar(50) DEFAULT NULL,WEEKDAY_RANK int(11) DEFAULT NULL,"
					+ "WEEKEND_RANK int(11) DEFAULT NULL,api_provider_id varchar(3) NOT NULL,"
					+ "State varchar(60) DEFAULT NULL,hotel_desc varchar(4000) DEFAULT NULL,"
					+ "id int(11) DEFAULT NULL,PRIMARY KEY (hotel_id),"
					+ "KEY api_vendor (api_vendor_id),"
					+ "KEY api_providerid_fk_hotover_idx (api_provider_id)) ENGINE=InnoDB AUTO_INCREMENT=191838 DEFAULT CHARSET=latin1;";
			args.add(query);	       
			ProcessBuilder pb = new ProcessBuilder(args);
			pb.redirectError();
			Process p = pb.start();
			InputStream is = p.getInputStream();
			int in = -1;
			while ((in = is.read()) != -1) {
				//System.out.print((char)in);
				//logger.info((char)in);
			}
			int processCompleted = p.waitFor();
			if (processCompleted == 0) {
				result = true;
				logger.info("DB traversed successfully!");
			} else {
				result = false;
				logger.info("Could not traverse the DB");
			}      
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}


	public Boolean insertHotelOverview()
	{
		Boolean result = false;
		//Process p = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			List<String> args = new ArrayList<String>();
			args.add("C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysql.exe");	        
			args.add("-u");
			args.add("root");
			args.add("-p" + "password"); //?? Is this a single command?
			//StringBuffer query = createSQLDump();
			if(createSQLDump())
			{
				//args.add(queryContent.toString());
				
				//D:\\Ramesh laptop-backup\\static content\\Xml\\tg_hoteloverview.sql
				//args.add("-e");	
				//args.add(query.toString());	
				args.add("tayyarah_hotels tg_hoteloverview < \"D:\\Ramesh laptop-backup\\static content\\Xml\\tg_hoteloverview.sql\"");	 			
				
				ProcessBuilder pb = new ProcessBuilder(args);
				pb.redirectError();
				Process p = pb.start();
				InputStream is = p.getInputStream();
				int in = -1;
				while ((in = is.read()) != -1) {
					//System.out.print((char)in);
					//logger.info((char)in);
				}
				int processCompleted = p.waitFor();
				if (processCompleted == 0) {
					result = true;
					logger.info("DB traversed successfully!");
				} else {
					result = false;
					logger.info("Could not traverse the DB");
				}      
			}
			else
			{
				result = false;
				logger.info("Insert problem #####");
			}
			
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}



	public boolean createSQLDumpSample()
	{
		OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = null;	
		StringBuffer queryContentFinal = new StringBuffer();
		StringBuffer queryContentHotelOverView = new StringBuffer();
		StringBuffer queryContentHotelRoomDescription = new StringBuffer();
		/*queryContent.append("INSERT INTO tayyarah_hotels.tg_hoteloverview"
				+ "(VendorName,api_vendor_id,HotelClass,Location,City,Country,Address1,Address2,"
				+ "Area,Address,HotelOverview,ReviewRating,ReviewCount,Latitude,Longitude,DefaultCheckInTime,"
				+ "DefaultCheckOutTime,Hotel_Star,HotelGroupID,HotelGroupName,ImagePath,HotelSearchKey,Area_Seo_Id,"
				+ "City_Zone,WEEKDAY_RANK,WEEKEND_RANK,api_provider_id,State,hotel_desc,id)VALUES ");
			*/
		queryContentHotelOverView.append("INSERT INTO tayyarah_hotels.tg_hoteloverview"
				+ "(VendorName,api_vendor_id, api_provider_id)VALUES ");	
		queryContentHotelRoomDescription.append("INSERT INTO tayyarah_hotels.tg_hoteloverview"
				+ "(HotelCode,roomname)VALUES ");	
		boolean isItemThere = false;
		String hotelname = "";
		try {
			//D:\Ramesh laptop-backup\static content\Xml
			File file = new File("D:\\Ramesh laptop-backup\\static content\\Xml\\HotelOverview.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(com.tayyarah.api.hotel.travelguru.staticcontent.model.OTAHotelDescriptiveInfoRS.class).createUnmarshaller();
			otaHotelDescriptiveInfoRS = (com.tayyarah.api.hotel.travelguru.staticcontent.model.OTAHotelDescriptiveInfoRS)unmarshaller.unmarshal(file);
			
			Integer rowcount = 0;
			if( otaHotelDescriptiveInfoRS !=null && otaHotelDescriptiveInfoRS.getHotelDescriptiveContents() != null && otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContent() !=null)
			{
				for (HotelDescriptiveContent hotelDescriptiveContent : otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContent()) {
					//System.out.println("HotelCode "+hotelDescriptiveContent.getHotelCode() + " Name "+hotelDescriptiveContent.getHotelName()+ " Latitude " +hotelDescriptiveContent.getHotelInfo().getPosition().getLatitude()+" Longitude "+hotelDescriptiveContent.getHotelInfo().getPosition().getLongitude()+" City Name "+hotelDescriptiveContent.getContactInfos().getContactInfo().getAddresses().getAddress().getCityName());
					
					//display hotel amenties, adddress, position     
					// loop and display rooms and its names, amenities, inclusions etc..
					hotelname = hotelDescriptiveContent.getHotelName();
					if(hotelDescriptiveContent.getHotelName().contains("'"))
					{
						hotelname = hotelname.replaceAll("'", "-");
					}
					if(hotelname.contains("WelcomHeritage Judge"))
						System.out.println("HotelCode "+hotelDescriptiveContent.getHotelCode() + " Name "+hotelname+ " Latitude " +hotelDescriptiveContent.getHotelInfo().getPosition().getLatitude()+" Longitude "+hotelDescriptiveContent.getHotelInfo().getPosition().getLongitude()+" City Name "+hotelDescriptiveContent.getContactInfos().getContactInfo().getAddresses().getAddress().getCityName());
						
					queryContentHotelOverView.append("("
							+ "\'"+hotelname+"\',"
							+ "\'"+hotelDescriptiveContent.getHotelCode()+"\',"
							+ "\'0h\')"
							+ ((rowcount < otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContent().size()-1)?",":"")							
							);	
					for (GuestRoom guestRoom : hotelDescriptiveContent.getFacilityInfo().getGuestRooms().getGuestRoom()) {
						queryContentHotelRoomDescription.append("("
								+ "'"+hotelDescriptiveContent.getHotelCode()+"',"
								+ "'"+guestRoom.getRoomTypeName()+"')"
								+ ((rowcount < otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContent().size()-1)?",":"")							
								);	
					}
					isItemThere = true;
					/*for (Amenity amenity : hotelDescriptiveContent.getTPAExtensions().getFacilities().getAmenity()) {
						System.out.println(" Amenity Desc " +amenity.getDescription());
					}*/
					rowcount ++;
				}
			}
			
			if(!isItemThere)
			{
				
				queryContentFinal =  null;
			}
			else
			{
				
				
				queryContentFinal = queryContentFinal.append("/*!TG static content retived from travlguru */\n\n\n\n\\n");
				queryContentFinal.append("\n\n/* Hotel overview content */\n");
				queryContentFinal.append(queryContentHotelOverView);
				/*queryContentFinal.append("\n\n/* Hotel room content \*\/\n");
				queryContentFinal.append(queryContentHotelRoomDescription);
				*/
				writeSql(queryContentHotelOverView.toString());
			}
			
		} catch (UnsupportedOperationException e) {
			logger.error("static xml UnsupportedOperationException ## ", e);
			e.printStackTrace();
			queryContentFinal =  null;
		} 		
		catch (JAXBException e) {
			logger.error("static xml JAXBException ## ", e);
			e.printStackTrace();
			queryContentFinal =  null;
		}		
		return isItemThere;
	}
	
	
	
	public boolean createSQLDump()
	{
		com.tayyarah.api.hotel.travelguru.staticcontent.model.OTAHotelDescriptiveInfoRS otaHotelDescriptiveInfoRS = null;	
		StringBuffer queryContentFinal = new StringBuffer();
		StringBuffer queryContentHotelOverView = new StringBuffer();
		StringBuffer queryContentHotelRoomDescription = new StringBuffer();
		/*queryContent.append("INSERT INTO tayyarah_hotels.tg_hoteloverview"
				+ "(VendorName,api_vendor_id,HotelClass,Location,City,Country,Address1,Address2,"
				+ "Area,Address,HotelOverview,ReviewRating,ReviewCount,Latitude,Longitude,DefaultCheckInTime,"
				+ "DefaultCheckOutTime,Hotel_Star,HotelGroupID,HotelGroupName,ImagePath,HotelSearchKey,Area_Seo_Id,"
				+ "City_Zone,WEEKDAY_RANK,WEEKEND_RANK,api_provider_id,State,hotel_desc,id)VALUES ");
			*/
		queryContentHotelOverView.append("INSERT INTO tayyarah_hotels.tg_hoteloverview"
				+ "(hotel_id,VendorName,api_vendor_id,HotelClass,"
				+ "Location,City,Country,Address1,Address2,Area,Address,"
				+ "HotelOverview,ReviewRating,ReviewCount,Latitude,Longitude,"
				+ "DefaultCheckInTime,DefaultCheckOutTime,Hotel_Star,HotelGroupID,"
				+ "HotelGroupName,ImagePath,HotelSearchKey,Area_Seo_Id,City_Zone,"
				+ "WEEKDAY_RANK,WEEKEND_RANK,api_provider_id,State,hotel_desc,id)VALUES ");	
		queryContentHotelRoomDescription.append("INSERT INTO tayyarah_hotels.tg_hoteloverview"
				+ "(HotelCode,roomname)VALUES ");	
		boolean isItemThere = false;
		try {
			//D:\Ramesh laptop-backup\static content\Xml
			File file = new File("D:\\Ramesh laptop-backup\\static content\\Xml\\HotelOverview.xml");			
			Unmarshaller unmarshaller = JAXBContext.newInstance(com.tayyarah.api.hotel.travelguru.staticcontent.model.OTAHotelDescriptiveInfoRS.class).createUnmarshaller();
			otaHotelDescriptiveInfoRS = (com.tayyarah.api.hotel.travelguru.staticcontent.model.OTAHotelDescriptiveInfoRS)unmarshaller.unmarshal(file);
			
			Integer rowcount = 1;
			String hotelname = "";
			String description = "";
			if( otaHotelDescriptiveInfoRS !=null && otaHotelDescriptiveInfoRS.getHotelDescriptiveContents() != null && otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContent() !=null)
			{
				
				for (HotelDescriptiveContent hotelDescriptiveContent : otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContent()) {
					System.out.println("HotelCode "+hotelDescriptiveContent.getHotelCode() + " Name "+hotelDescriptiveContent.getHotelName()+ " Latitude " +hotelDescriptiveContent.getHotelInfo().getPosition().getLatitude()+" Longitude "+hotelDescriptiveContent.getHotelInfo().getPosition().getLongitude()+" City Name "+hotelDescriptiveContent.getContactInfos().getContactInfo().getAddresses().getAddress().getCityName());
					//display hotel amenties, adddress, position     
					// loop and display rooms and its names, amenities, inclusions etc..
					hotelname = hotelDescriptiveContent.getHotelName();
					if(hotelname!=null && hotelname.contains("'"))
					{
						hotelname = hotelname.replaceAll("'", "-");
					}
					description = hotelDescriptiveContent.getHotelInfo().getDescriptions().getDescriptiveText();
					if(description!= null && description.contains("'"))
					{
						description = description.replaceAll("'", "-");
					}
					
					queryContentHotelOverView.append("("
							+rowcount+","
							+ "'"+hotelname+"',"
							+ "'"+hotelDescriptiveContent.getHotelCode()+"',"
							+ "'"+"',"
							+ "'"+hotelDescriptiveContent.getTPAExtensions().getAreaSeoId()+"',"
							+ "'"+hotelDescriptiveContent.getContactInfos().getContactInfo().getAddresses().getAddress().getCityName()+"',"
							+ "'"+hotelDescriptiveContent.getContactInfos().getContactInfo().getAddresses().getAddress().getCounty()+"',"
							+ "'"+"',"
							+ "'"+"',"
							+ "'"+"',"
							+ "'"+"',"
							+ "'"+"',"
							+ "'"+hotelDescriptiveContent.getTPAExtensions().getReviewRating()+"',"
							+ "'"+hotelDescriptiveContent.getTPAExtensions().getReviewCount()+"',"
							+ ""+hotelDescriptiveContent.getHotelInfo().getPosition().getLatitude()+","
							+ ""+hotelDescriptiveContent.getHotelInfo().getPosition().getLongitude()+","	
							//+ ""+hotelDescriptiveContent.getPolicies().getPolicy().getPolicyInfo().getCheckInTime()+","
							//+ ""+hotelDescriptiveContent.getPolicies().getPolicy().getPolicyInfo().getCheckOutTime()+","
							+ null+","
							+ null+","
							
							+ "'"+hotelDescriptiveContent.getTPAExtensions().getHotelStar()+"',"
							+ "'"+"',"
							+ "'"+"',"
							+ "'"+hotelDescriptiveContent.getTPAExtensions().getImagePath()+"',"
							+ "'"+hotelDescriptiveContent.getTPAExtensions().getHotelSearchKey()+"',"
							+ "'"+hotelDescriptiveContent.getTPAExtensions().getAreaSeoId()+"',"
							+ "'"+hotelDescriptiveContent.getTPAExtensions().getCityZone()+"',"
							+ ""+hotelDescriptiveContent.getTPAExtensions().getWeekDayRank()+","
							+ ""+hotelDescriptiveContent.getTPAExtensions().getWeekEndRank()+","
							+ "'"+"0H"+"',"
							+ "'"+hotelDescriptiveContent.getTPAExtensions().getHotelState()+"',"
							+ "'"+description+"',"
							+rowcount+")"
							+ ((rowcount < otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContent().size())?",\n":"\n")							
							);	
					
					for (GuestRoom guestRoom : hotelDescriptiveContent.getFacilityInfo().getGuestRooms().getGuestRoom()) {
						queryContentHotelRoomDescription.append("("
								+ "'"+hotelDescriptiveContent.getHotelCode()+"',"
								+ "'"+guestRoom.getRoomTypeName()+"')"
								+ ((rowcount < otaHotelDescriptiveInfoRS.getHotelDescriptiveContents().getHotelDescriptiveContent().size()-1)?",":"")							
								);	
					}
					isItemThere = true;
					
					//if(rowcount == 10)
					//	break;
						
					rowcount ++;
					/*for (Amenity amenity : hotelDescriptiveContent.getTPAExtensions().getFacilities().getAmenity()) {
						System.out.println(" Amenity Desc " +amenity.getDescription());
					}*/
				}
			}
			
			if(!isItemThere)
			{
				
				queryContentHotelOverView =  null;
			}
			else
			{
				
				
				queryContentFinal = queryContentFinal.append("/*!TG static content retived from travlguru */\n\n\n\n\n");
				queryContentFinal.append("\n\n/* Hotel overview content */\n");
				queryContentFinal.append(queryContentHotelOverView);
				//queryContentFinal.append("\n\n/* Hotel room content */\n");
				//queryContentFinal.append(queryContentHotelRoomDescription);
				
				writeSql(queryContentFinal.toString());
			}
			
		} catch (UnsupportedOperationException e) {
			logger.error("static xml UnsupportedOperationException ## ", e);
			e.printStackTrace();
			queryContentFinal =  null;
		} 		
		catch (JAXBException e) {
			logger.error("static xml JAXBException ## ", e);
			e.printStackTrace();
			queryContentFinal =  null;
		}		
		return isItemThere;
	}
	
	public static boolean writeSql(String queryContent)
	{		
		String filename = "D:\\Ramesh laptop-backup\\static content\\Xml\\tg_hoteloverview.sql";
		BufferedWriter bufferedWriter = null;
		try {	            
			File myFile = new File(filename);
			// check if file exist, otherwise create the file before writing
			if (!myFile.exists()) {
				myFile.createNewFile();
			}
			Writer writer = new FileWriter(myFile);
			bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(queryContent);
			logger.info("SQL is created at---"+filename+".....");
		} catch (IOException e) {
			logger.info("SQL creation at---"+filename+"....FileNotFoundException."+e.getMessage());

			e.printStackTrace();
		} finally{
			try{
				if(bufferedWriter != null) bufferedWriter.close();
			} catch(Exception ex){

			}
		}
		return true;
	}
	
	
	
	

}
