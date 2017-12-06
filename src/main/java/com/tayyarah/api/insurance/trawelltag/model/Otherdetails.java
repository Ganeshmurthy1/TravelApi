package com.tayyarah.api.insurance.trawelltag.model;

public class Otherdetails {
	private String universityaddress;

    private String policycomment;

    private String universityname;

    public String getUniversityaddress ()
    {
        return universityaddress;
    }

    public void setUniversityaddress (String universityaddress)
    {
        this.universityaddress = universityaddress;
    }

    public String getPolicycomment ()
    {
        return policycomment;
    }

    public void setPolicycomment (String policycomment)
    {
        this.policycomment = policycomment;
    }

    public String getUniversityname ()
    {
        return universityname;
    }

    public void setUniversityname (String universityname)
    {
        this.universityname = universityname;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [universityaddress = "+universityaddress+", policycomment = "+policycomment+", universityname = "+universityname+"]";
    }
}
