package com.tayyarah.hotel.util;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.tayyarah.hotel.model.RoomBookingKeyMap;

public class HotelIdFactoryImpl implements Serializable{	

	private static final long serialVersionUID = 1L;
	private static HotelIdFactoryImpl INSTANCE = null;
	private String hostName;
	private final long creationTimeMillis;
	private long lastTimeMillis;
	private static long discriminator;
	private final long creationTimeMillisShort;
	private long lastTimeMillisShort;
	private static long discriminatorShort;
	private final long creationTimeMillisShorter;
	private long lastTimeMillisShorter;
	private static long discriminatorShorter;

	public static synchronized HotelIdFactoryImpl getInstance(){
		if(INSTANCE == null)
			return new HotelIdFactoryImpl();
		return INSTANCE;
	}

	private HotelIdFactoryImpl(){
		this.creationTimeMillis = System.currentTimeMillis();
		this.lastTimeMillis = creationTimeMillis;

		this.creationTimeMillisShort = System.currentTimeMillis()%1000000;
		this.lastTimeMillisShort = creationTimeMillisShort;


		this.creationTimeMillisShorter = System.currentTimeMillis()%1000000;
		this.lastTimeMillisShorter = creationTimeMillisShort;

		try {
			this.hostName = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			this.hostName = "Localhost";
			e.printStackTrace();
		}
	}

	public synchronized String createShortId(String idheader) {
		String id;
		long now = System.currentTimeMillis()%1000000;

		if (now == lastTimeMillisShort) {
			++discriminatorShort;
		} else {
			discriminatorShort = 0;
		}

		// creationTimeMillis used to prevent multiple instances of the JVM
		// running on the same host returning clashing IDs.
		// The only way a clash could occur is if the applications started at
		// exactly the same time.
		id = String.format("%s%d%d", idheader, now, discriminatorShort);
		lastTimeMillisShort = now;

		return id;
	}
	public synchronized String createLongId(String idheader) {
		String id;
		long now = System.currentTimeMillis();

		if (now == lastTimeMillis) {
			++discriminator;
		} else {
			discriminator = 0;
		}

		// creationTimeMillis used to prevent multiple instances of the JVM
		// running on the same host returning clashing IDs.
		// The only way a clash could occur is if the applications started at
		// exactly the same time.
		id = String.format("%s%d%d", idheader, now, discriminator);
		lastTimeMillis = now;
		return id;
	}
	public synchronized RoomBookingKeyMap createRoomRatePlanCodes(String bookingkey) {
		String ratePlanCode;
		String roomTypeCode;
		long now = System.currentTimeMillis()%1000000;

		if (now == lastTimeMillisShorter) {
			++discriminatorShorter;
		} else {
			discriminatorShorter = 0;
		}

		// creationTimeMillis used to prevent multiple instances of the JVM
		// running on the same host returning clashing IDs.
		// The only way a clash could occur is if the applications started at
		// exactly the same time.
		ratePlanCode = String.format("%s%d%d", "RP", now, discriminatorShorter);
		roomTypeCode = String.format("%s%d%d", "RT", now, discriminatorShorter);
		lastTimeMillisShorter = now;
		RoomBookingKeyMap roomBokingKeyMap = new RoomBookingKeyMap();
		roomBokingKeyMap.setBookingKey(bookingkey);
		roomBokingKeyMap.setRatePlanCode(ratePlanCode);
		roomBokingKeyMap.setRoomTypeCode(roomTypeCode);
		return roomBokingKeyMap;
	}
	public synchronized RoomBookingKeyMap createRatePlanCode(RoomBookingKeyMap roomBokingKeyMap) {
		String ratePlanCode;		   
		long now = System.currentTimeMillis()%1000000;
		if (now == lastTimeMillisShorter) {
			++discriminatorShorter;
		} else {
			discriminatorShorter = 0;
		}
		// creationTimeMillis used to prevent multiple instances of the JVM
		// running on the same host returning clashing IDs.
		// The only way a clash could occur is if the applications started at
		// exactly the same time.
		ratePlanCode = String.format("%s%d%d", "RP", now, discriminatorShorter);		   
		lastTimeMillisShorter = now;		   
		roomBokingKeyMap.setRatePlanCode(ratePlanCode);		    
		return roomBokingKeyMap;
	}

	public synchronized RoomBookingKeyMap createRoomRateBookingKey(String roomTypeCode, String ratePlanCode) {		 
		long now = System.currentTimeMillis()%1000000;
		String bookingKey;
		if (now == lastTimeMillisShorter) {
			++discriminatorShorter;
		} else {
			discriminatorShorter = 0;
		}

		// creationTimeMillis used to prevent multiple instances of the JVM
		// running on the same host returning clashing IDs.
		// The only way a clash could occur is if the applications started at
		// exactly the same time.
		bookingKey = String.format("%s%s%s%d%d", "RBK","-"+roomTypeCode, "-"+ratePlanCode, now, discriminatorShorter);		   
		lastTimeMillisShorter = now;
		RoomBookingKeyMap roomBokingKeyMap = new RoomBookingKeyMap();
		roomBokingKeyMap.setBookingKey(bookingKey);
		roomBokingKeyMap.setRatePlanCode(ratePlanCode);
		roomBokingKeyMap.setRoomTypeCode(roomTypeCode);
		return roomBokingKeyMap;
	}

	public synchronized RoomBookingKeyMap createRoomRateBookingKeySimple(String roomTypeCode, String ratePlanCode) {		 
		long now = System.currentTimeMillis()%1000000;
		String bookingKey;
		if (now == lastTimeMillisShorter) {
			++discriminatorShorter;
		} else {
			discriminatorShorter = 0;
		}

		// creationTimeMillis used to prevent multiple instances of the JVM
		// running on the same host returning clashing IDs.
		// The only way a clash could occur is if the applications started at
		// exactly the same time.
		bookingKey = String.format("%s%d%d", "RBK",now, discriminatorShorter);		   
		lastTimeMillisShorter = now;
		RoomBookingKeyMap roomBokingKeyMap = new RoomBookingKeyMap();
		roomBokingKeyMap.setBookingKey(bookingKey);
		roomBokingKeyMap.setRatePlanCode(ratePlanCode);
		roomBokingKeyMap.setRoomTypeCode(roomTypeCode);
		return roomBokingKeyMap;
	}
	@Override
	public String toString() {
		return "HotelIdFactoryImpl [hostName=" + hostName + ", creationTimeMillis=" + creationTimeMillis
				+ ", lastTimeMillis=" + lastTimeMillis + ", discriminator=" + discriminator
				+ ", creationTimeMillisShort=" + creationTimeMillisShort + ", lastTimeMillisShort="
				+ lastTimeMillisShort + ", discriminatorShort=" + discriminatorShort + ", creationTimeMillisShorter="
				+ creationTimeMillisShorter + ", lastTimeMillisShorter=" + lastTimeMillisShorter
				+ ", discriminatorShorter=" + discriminatorShorter + "]";
	}


}
