package com.tayyarah.apiproviderconfig.entity;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tayyarah.common.util.Timestampable;

@Entity
@Table(name="api_provider_common_config")
public class ApiProviderCommonConfig extends Timestampable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private long tboHotelId;
	@Transient
	private long tboFlightId;
	@Transient
	private long desiyaHotelId;
	@Transient
	private long bluestarFlightId;
	@Transient
	private long etravelBusId;


	@Column(name = "companyId")
	private int companyId;
	@Column(name = "configId")
	private int configId;
	@Column(name = "is_tbo_active")
	private boolean tboActive;
	@Column(name = "is_tbo_flight_active")
	private boolean tboFlightActive;
	@Column(name = "is_tbo_hotel_active")
	private boolean tboHotelActive;
	@Column(name = "is_desiya_active")
	private boolean desiyaActive;
	@Column(name = "is_desiya_hotel_active")
	private boolean desiyaHotelActive;
	@Column(name = "is_bluestar_active")
	private boolean bluestarActive;
	@Column(name = "is_bluestar_flight_active")
	private boolean bluestarFlightActive;
	@Column(name = "is_trawelltag_active")
	private boolean trawellTagActive;	
	@Column(name = "is_etravelsmart_active")
	private boolean etravelActive;
	@Column(name = "is_etravelsmart_bus_active")
	private boolean etravelBusActive;
	@Column(name = "is_trawelltag_insurance_active")
	private boolean trawellTagInsuranceActive;	
	@Column(name = "is_tayyarah_active")
	private boolean tayyarahActive;
	@Column(name = "is_tayyarah_hotel_active")
	private boolean tayyarahHotelActive;
	@Column(name = "is_active")
	private boolean active;
	@Column(name = "title", columnDefinition = "text")
	private String title;	 
	@Column(name = "is_tbo_flight_mode")
	private boolean tboFlightEnvironment;	 
	@Column(name = "is_tbo_hotel_mode")
	private boolean tboHotelEnvironment;	 
	@Column(name = "is_desiya_hotel_mode")
	private boolean desiyaHotelEnvironment;	 
	@Column(name = "is_tayyarah_hotel_mode")
	private boolean tayyarahHotelEnvironment;	 
	@Column(name = "is_bluestar_flight_mode")
	private boolean bluestarFlightEnvironment;	 
	@Column(name = "is_etravel_bus_mode")
	private boolean eSmartTravelEnvironment;
	@Column(name = "is_trawelltag_insurance_mode")
	private boolean trawellTagInsuranceEnvironment;

	@OneToOne(cascade =CascadeType.ALL)
	@JoinColumn(name = "bluestar_flight_id", referencedColumnName = "id",columnDefinition="BIGINT(20) default 0")
	private  ApiProviderBluestarConfig ApiProviderBluestarConfigFlight;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "desiya_hotel_id", referencedColumnName = "id",columnDefinition="BIGINT(20) default 0")
	private  ApiProviderDesiyaConfig apiProviderDesiyaConfigHotel ; 

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tbo_hotel_id", referencedColumnName = "id",columnDefinition="BIGINT(20) default 0")
	private ApiProviderTboConfig apiProviderTboConfigHotel;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tbo_flight_id", referencedColumnName = "id",columnDefinition="BIGINT(20) default 0")
	private ApiProviderTboConfig apiProviderTboConfigFlight;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tayyarah_hotel_id", referencedColumnName = "id",columnDefinition="BIGINT(20) default 0")
	private ApiProviderTayyarahConfig apiProviderTayyarahConfigFlight;

	@OneToOne(cascade =CascadeType.ALL)
	@JoinColumn(name = "etravel_bus_id", referencedColumnName = "id",columnDefinition="BIGINT(20) default 0")
	private  ApiProviderEtravelSmartConfig apiProviderEtravelBusConfig;

	@OneToOne(cascade =CascadeType.ALL)
	@JoinColumn(name = "trawelltag_insurance_id", referencedColumnName = "id")
	private  ApiProviderTrawellTagConfig apiProviderTrawellTagInsuranceConfig;


	public ApiProviderTayyarahConfig getApiProviderTayyarahConfigFlight() {
		return apiProviderTayyarahConfigFlight;
	}
	public void setApiProviderTayyarahConfigFlight(ApiProviderTayyarahConfig apiProviderTayyarahConfigFlight) {
		this.apiProviderTayyarahConfigFlight = apiProviderTayyarahConfigFlight;
	}
	public boolean isTboFlightEnvironment() {
		return tboFlightEnvironment;
	}
	public boolean isTayyarahHotelEnvironment() {
		return tayyarahHotelEnvironment;
	}
	public void setTayyarahHotelEnvironment(boolean tayyarahHotelEnvironment) {
		this.tayyarahHotelEnvironment = tayyarahHotelEnvironment;
	}
	public void setTboFlightEnvironment(boolean tboFlightEnvironment) {
		this.tboFlightEnvironment = tboFlightEnvironment;
	}
	public boolean isTboHotelEnvironment() {
		return tboHotelEnvironment;
	}
	public void setTboHotelEnvironment(boolean tboHotelEnvironment) {
		this.tboHotelEnvironment = tboHotelEnvironment;
	}
	public boolean isDesiyaHotelEnvironment() {
		return desiyaHotelEnvironment;
	}
	public void setDesiyaHotelEnvironment(boolean desiyaHotelEnvironment) {
		this.desiyaHotelEnvironment = desiyaHotelEnvironment;
	}
	public boolean isBluestarFlightEnvironment() {
		return bluestarFlightEnvironment;
	}
	public void setBluestarFlightEnvironment(boolean bluestarFlightEnvironment) {
		this.bluestarFlightEnvironment = bluestarFlightEnvironment;
	}
	public boolean isTboActive() {
		return tboActive;
	}
	public void setTboActive(boolean tboActive) {
		this.tboActive = tboActive;
	}
	public boolean isDesiyaActive() {
		return desiyaActive;
	}
	public void setDesiyaActive(boolean desiyaActive) {
		this.desiyaActive = desiyaActive;
	}
	public boolean isBluestarActive() {
		return bluestarActive;
	}
	public void setBluestarActive(boolean bluestarActive) {
		this.bluestarActive = bluestarActive;
	}

	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public boolean isTboFlightActive() {
		return tboFlightActive;
	}
	public void setTboFlightActive(boolean tboFlightActive) {
		this.tboFlightActive = tboFlightActive;
	}
	public boolean isTboHotelActive() {
		return tboHotelActive;
	}
	public void setTboHotelActive(boolean tboHotelActive) {
		this.tboHotelActive = tboHotelActive;
	}
	public boolean isDesiyaHotelActive() {
		return desiyaHotelActive;
	}
	public void setDesiyaHotelActive(boolean desiyaHotelActive) {
		this.desiyaHotelActive = desiyaHotelActive;
	}
	public boolean isBluestarFlightActive() {
		return bluestarFlightActive;
	}
	public void setBluestarFlightActive(boolean bluestarFlightActive) {
		this.bluestarFlightActive = bluestarFlightActive;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public ApiProviderBluestarConfig getApiProviderBluestarConfigFlight() {
		return ApiProviderBluestarConfigFlight;
	}
	public void setApiProviderBluestarConfigFlight(ApiProviderBluestarConfig apiProviderBluestarConfigFlight) {
		ApiProviderBluestarConfigFlight = apiProviderBluestarConfigFlight;
	}
	public ApiProviderDesiyaConfig getApiProviderDesiyaConfigHotel() {
		return apiProviderDesiyaConfigHotel;
	}
	public void setApiProviderDesiyaConfigHotel(ApiProviderDesiyaConfig apiProviderDesiyaConfigHotel) {
		this.apiProviderDesiyaConfigHotel = apiProviderDesiyaConfigHotel;
	}

	public ApiProviderTboConfig getApiProviderTboConfigHotel() {
		return apiProviderTboConfigHotel;
	}
	public void setApiProviderTboConfigHotel(ApiProviderTboConfig apiProviderTboConfigHotel) {
		this.apiProviderTboConfigHotel = apiProviderTboConfigHotel;
	}

	public ApiProviderTboConfig getApiProviderTboConfigFlight() {
		return apiProviderTboConfigFlight;
	}
	public void setApiProviderTboConfigFlight(ApiProviderTboConfig apiProviderTboConfigFlight) {
		this.apiProviderTboConfigFlight = apiProviderTboConfigFlight;
	}

	public boolean isTayyarahActive() {
		return tayyarahActive;
	}
	public void setTayyarahActive(boolean tayyarahActive) {
		this.tayyarahActive = tayyarahActive;
	}
	public boolean isTayyarahHotelActive() {
		return tayyarahHotelActive;
	}
	public void setTayyarahHotelActive(boolean tayyarahHotelActive) {
		this.tayyarahHotelActive = tayyarahHotelActive;
	}	
	public long getTboHotelId() {
		return tboHotelId;
	}
	public void setTboHotelId(long tboHotelId) {
		this.tboHotelId = tboHotelId;
	}
	public long getTboFlightId() {
		return tboFlightId;
	}
	public void setTboFlightId(long tboFlightId) {
		this.tboFlightId = tboFlightId;
	}
	public long getDesiyaHotelId() {
		return desiyaHotelId;
	}
	public void setDesiyaHotelId(long desiyaHotelId) {
		this.desiyaHotelId = desiyaHotelId;
	}
	public long getBluestarFlightId() {
		return bluestarFlightId;
	}
	public void setBluestarFlightId(long bluestarFlightId) {
		this.bluestarFlightId = bluestarFlightId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getConfigId() {
		return configId;
	}
	public void setConfigId(int configId) {
		this.configId = configId;
	}
	public long getEtravelBusId() {
		return etravelBusId;
	}
	public void setEtravelBusId(long etravelBusId) {
		this.etravelBusId = etravelBusId;
	}
	public boolean isEtravelActive() {
		return etravelActive;
	}
	public void setEtravelActive(boolean etravelActive) {
		this.etravelActive = etravelActive;
	}
	public boolean isEtravelBusActive() {
		return etravelBusActive;
	}
	public void setEtravelBusActive(boolean etravelBusActive) {
		this.etravelBusActive = etravelBusActive;
	}
	public boolean iseSmartTravelEnvironment() {
		return eSmartTravelEnvironment;
	}
	public ApiProviderEtravelSmartConfig getApiProviderEtravelBusConfig() {
		return apiProviderEtravelBusConfig;
	}
	public void seteSmartTravelEnvironment(boolean eSmartTravelEnvironment) {
		this.eSmartTravelEnvironment = eSmartTravelEnvironment;
	}
	public void setApiProviderEtravelBusConfig(ApiProviderEtravelSmartConfig apiProviderEtravelBusConfig) {
		this.apiProviderEtravelBusConfig = apiProviderEtravelBusConfig;
	}
	public boolean isTrawellTagActive() {
		return trawellTagActive;
	}
	public boolean isTrawellTagInsuranceActive() {
		return trawellTagInsuranceActive;
	}
	public boolean isTrawellTagInsuranceEnvironment() {
		return trawellTagInsuranceEnvironment;
	}
	public ApiProviderTrawellTagConfig getApiProviderTrawellTagInsuranceConfig() {
		return apiProviderTrawellTagInsuranceConfig;
	}
	public void setTrawellTagActive(boolean trawellTagActive) {
		this.trawellTagActive = trawellTagActive;
	}
	public void setTrawellTagInsuranceActive(boolean trawellTagInsuranceActive) {
		this.trawellTagInsuranceActive = trawellTagInsuranceActive;
	}
	public void setTrawellTagInsuranceEnvironment(boolean trawellTagInsuranceEnvironment) {
		this.trawellTagInsuranceEnvironment = trawellTagInsuranceEnvironment;
	}
	public void setApiProviderTrawellTagInsuranceConfig(ApiProviderTrawellTagConfig apiProviderTrawellTagInsuranceConfig) {
		this.apiProviderTrawellTagInsuranceConfig = apiProviderTrawellTagInsuranceConfig;
	}
}