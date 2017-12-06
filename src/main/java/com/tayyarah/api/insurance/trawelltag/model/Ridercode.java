package com.tayyarah.api.insurance.trawelltag.model;


import javax.xml.bind.annotation.XmlRootElement;

public class Ridercode {
	
	private String content;
	
    private String percent;

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getPercent ()
    {
        return percent;
    }

    public void setPercent (String percent)
    {
        this.percent = percent;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+", percent = "+percent+"]";
    }
}
