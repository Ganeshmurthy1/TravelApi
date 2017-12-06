package com.tayyarah.api.insurance.trawelltag.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Policy {
	private Identity identity;

    private Traveldetails traveldetails;

    private Insured insured;

    private Plan plan;

    private Otherdetails otherdetails;

    public Identity getIdentity ()
    {
        return identity;
    }

    public void setIdentity (Identity identity)
    {
        this.identity = identity;
    }

    public Traveldetails getTraveldetails ()
    {
        return traveldetails;
    }

    public void setTraveldetails (Traveldetails traveldetails)
    {
        this.traveldetails = traveldetails;
    }

    public Insured getInsured ()
    {
        return insured;
    }

    public void setInsured (Insured insured)
    {
        this.insured = insured;
    }

    public Plan getPlan ()
    {
        return plan;
    }

    public void setPlan (Plan plan)
    {
        this.plan = plan;
    }

    public Otherdetails getOtherdetails ()
    {
        return otherdetails;
    }

    public void setOtherdetails (Otherdetails otherdetails)
    {
        this.otherdetails = otherdetails;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [identity = "+identity+", traveldetails = "+traveldetails+", insured = "+insured+", plan = "+plan+", otherdetails = "+otherdetails+"]";
    }
}
