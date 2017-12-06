package com.tayyarah.common.util.enums;



public enum InventoryTypeEnum {
	
	DEFAULT (0, "default", "default type","default.jpg",""),
	USER (1, "user", "user item","employee.png","agentDetails"),
	USER_WALLET (2, "wallet", "user wallet item","wallet.png","TransactionDetail"),
	COMPANY (3, "company", "company item","newcompany.png","companyDetails"),
	COMPANY_CONFIG (4, "company config", "company config item","company_config.png","companyConfigProfile"),	
	PASSENGER(5, "passenger Details", "passenger details","passenger.jpg",""),
	HOTEL_ORDER (6, "hotel order", "hotel order info","hotelorder.png","showReportDetails"),
	HOTEL_ROOM_DETAILS(7, "hotel room detail", "hotel room detail info","hotelroom.jpg",""),	
	HOTEL_QUOATATION(8, "hotel quoatation", "hotel quoatation info","hotelquote.png","getHotelRequestTravelQuotationList"),	
	FLIGHT_ORDER(9,"flight order","flight order info","flightorder.png","showPassengerDetails"),
	FLIGHT_QUOATATION(10, "flight quoatation", "flight quoatation info","flightquote.jpg",""),	
	CMS(11,"cms","cms info","cms.png","CmsDetails"),
	PAYMENT_PENDING(12,"payment pending","payment pending alert","payalert.jpg",""),
	B2CUSER (13, "B2cuser", "user item","b2cuser.png","UserDetail"),
	COMPANY_APPROVAL (14, "company approval", "company item","company_approval.png","companyDetails"),
	FLIGHT_MARKUP (15, "mark up", "markup item","markup.png","markupProfile"),
	HOTEL_MARKUP (16, "mark up", "markup item","markup.png","hoteMarkupProfile"),
	HOTEL_BOOKREQUEST (17, "hotel request", "Hotel item","hotelbookrequest.png","hotelOrderQuotationView"),
	NEW_NOTIFICATION(18,"notification request","notification type","","notification");

	private final Integer id;
	private final String title;  
	private final String description; 
	private final String imagename; 
	private final String actionname;
	InventoryTypeEnum(Integer id, String title, String description,String imagename,String actionname) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.imagename = imagename;
		this.actionname = actionname;
	}
	public static InventoryTypeEnum getBrowsingHistoryDetailTypeEnum(Integer detailId) {

		InventoryTypeEnum inventoryDetailTypeEnum = DEFAULT;
		if(detailId == null)
			return DEFAULT;
		for (InventoryTypeEnum bd : InventoryTypeEnum.values())
		{
			if(bd.getId() == detailId)
			{
				inventoryDetailTypeEnum = bd;
			}		
		}
		return inventoryDetailTypeEnum; 
	}

	public Integer getId() { return id; }
	public String getTitle() { return title; }
	public String getImagename() {
		return imagename;
	}
	public String getLink() { return description; }
	public String getActionname() {
		return actionname;
	}
}