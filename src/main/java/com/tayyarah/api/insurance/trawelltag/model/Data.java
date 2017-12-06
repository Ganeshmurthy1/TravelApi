package com.tayyarah.api.insurance.trawelltag.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="data")
public class Data {

	private String message;
	private String status;
	private String errorcode;
	private String document;
	private String claimcode;
	private String policy;
	private String reference;

	public String getMessage ()
	{
		return message;
	}

	public void setMessage (String message)
	{
		this.message = message;
	}

	public String getStatus ()
	{
		return status;
	}

	public void setStatus (String status)
	{
		this.status = status;
	}

	public String getErrorcode ()
	{
		return errorcode;
	}

	public void setErrorcode (String errorcode)
	{
		this.errorcode = errorcode;
	}

	public String getReference ()
	{
		return reference;
	}

	public void setReference (String reference)
	{
		this.reference = reference;
	}

	public String getDocument() {
		return document;
	}

	public String getClaimcode() {
		return claimcode;
	}

	public String getPolicy() {
		return policy;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public void setClaimcode(String claimcode) {
		this.claimcode = claimcode;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	@Override
	public String toString()
	{
		return "ClassPojo [message = "+message+", status = "+status+", errorcode = "+errorcode+", reference = "+reference+"]";
	}
}
