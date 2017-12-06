package com.tayyarah.flight.model;

import java.io.Serializable;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travelport.api_v33.AirResponse.TypeUnitWeight;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FareRule implements Serializable
{
   
	private static final long serialVersionUID = 1L;
	
	
	private String fareInfoRef;
	private TypeUnitWeight bagAllowanceUnit;
    private BigInteger bagAllowanceValue;
	private String airlineCode;
    private String depDate;
    private String depCode;
    private String basisCode;
    private String arrCode;
    private String url;
    private String fareValue;
    private String fareProviderCode;

    public String getFareInfoRef() {
		return fareInfoRef;
	}

	public void setFareInfoRef(String fareInfoRef) {
		this.fareInfoRef = fareInfoRef;
	}

	public String getFareValue() {
		return fareValue;
	}

	public void setFareValue(String fareValue) {
		this.fareValue = fareValue;
	}

	public String getFareProviderCode() {
		return fareProviderCode;
	}

	public void setFareProviderCode(String fareProviderCode) {
		this.fareProviderCode = fareProviderCode;
	}

	
    
    public TypeUnitWeight getBagAllowanceUnit() {
		return bagAllowanceUnit;
	}

	public void setBagAllowanceUnit(TypeUnitWeight typeUnitWeight) {
		this.bagAllowanceUnit = typeUnitWeight;
	}

	public BigInteger getBagAllowanceValue() {
		return bagAllowanceValue;
	}

	public void setBagAllowanceValue(BigInteger bigInteger) {
		this.bagAllowanceValue = bigInteger;
	}

    public String getAirlineCode ()
    {
        return airlineCode;
    }

    public void setAirlineCode (String airlineCode)
    {
        this.airlineCode = airlineCode;
    }

    public String getDepDate ()
    {
        return depDate;
    }

    public void setDepDate (String depDate)
    {
        this.depDate = depDate;
    }

    public String getDepCode ()
    {
        return depCode;
    }

    public void setDepCode (String depCode)
    {
        this.depCode = depCode;
    }

    public String getBasisCode ()
    {
        return basisCode;
    }

    public void setBasisCode (String basisCode)
    {
        this.basisCode = basisCode;
    }

    public String getArrCode ()
    {
        return arrCode;
    }

    public void setArrCode (String arrCode)
    {
        this.arrCode = arrCode;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
	public String toString() {
		return "FareRule [fareInfoRef=" + fareInfoRef + ", fareValue="
				+ fareValue + ", fareProviderCode=" + fareProviderCode
				+ ", bagAllowanceUnit=" + bagAllowanceUnit
				+ ", bagAllowanceValue=" + bagAllowanceValue + ", airlineCode="
				+ airlineCode + ", depDate=" + depDate + ", depCode=" + depCode
				+ ", basisCode=" + basisCode + ", arrCode=" + arrCode
				+ ", url=" + url + "]";
	}
}
