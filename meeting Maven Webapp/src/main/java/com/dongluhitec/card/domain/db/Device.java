package com.dongluhitec.card.domain.db;

import java.io.Serializable;

public class Device implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2398873195808709866L;
	
	private String name;

	private String identifier;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	@Override
	public String toString() {
		return "identifier="+identifier+"--name="+name;
	}
}