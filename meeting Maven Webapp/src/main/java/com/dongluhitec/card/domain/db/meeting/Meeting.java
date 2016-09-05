package com.dongluhitec.card.domain.db.meeting;


import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: panmingzhi
 * Date: 13-9-4
 * Time: 下午3:12
 * To change this template use File | Settings | File Templates.
 */
public class Meeting implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9083746070991241426L;
	protected Long id;
    private String identifier;
    private String name;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "id="+id+"-identifier="+identifier+"-name="+name;
	}
}
