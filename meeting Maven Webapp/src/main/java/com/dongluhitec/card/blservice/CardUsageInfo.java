package com.dongluhitec.card.blservice;

import java.io.Serializable;

public class CardUsageInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9143448034273154251L;
	private Long id;
	private Long visitorId;
	private String visitorName;
	private String visitorIdCard;
	private String visitorCompany;
	private String visitorReason;
	private Long userId;
	private String userName;
	private String userGroup;
	private String userIdentifire;
	private String imageName;
	
	public String getVisitorName() {
		return visitorName;
	}
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}
	public String getVisitorIdCard() {
		return visitorIdCard;
	}
	public void setVisitorIdCard(String visitorIdCard) {
		this.visitorIdCard = visitorIdCard;
	}
	public String getVisitorCompany() {
		return visitorCompany;
	}
	public void setVisitorCompany(String visitorCompany) {
		this.visitorCompany = visitorCompany;
	}
	public String getVisitorReason() {
		return visitorReason;
	}
	public void setVisitorReason(String visitorReason) {
		this.visitorReason = visitorReason;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	public String getUserIdentifire() {
		return userIdentifire;
	}
	public void setUserIdentifire(String userIdentifire) {
		this.userIdentifire = userIdentifire;
	}
	public Long getVisitorId() {
		return visitorId;
	}
	public void setVisitorId(Long visitorId) {
		this.visitorId = visitorId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
