package com.dongluhitec.card.domain.db.meeting;


import java.io.Serializable;
import java.util.Date;


public class MeetingMember implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -892773043129704604L;
	public enum MeetingMemberStateEnum{
		全部,未打卡,正常,迟到,早退,请假
	}
    private String descript;
    private Long meetingId;
    private boolean isGroup=false;
    private Long userId;
    private String userName;
    private String userIdentifire;
    private String userGroupCodeJoinStr;
    private String userGroupCodeNameJoinStr;
    private String deviceIdentifie;
    private MeetingMemberStateEnum state=MeetingMemberStateEnum.未打卡;
    private Date inEventTime;
    private Date updateTime;
    
    private String seat;
    
    private String userJob;
    private String userAttendType;
    private String userIdentityType;
    private String userSubGroupType;
    private String meetingName;
    
    
    public String getInEventTimeLabel() {
		return "";
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public Long getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(Long meetingId) {
		this.meetingId = meetingId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserIdentifire() {
		return userIdentifire;
	}
	public void setUserIdentifire(String userIdentifire) {
		this.userIdentifire = userIdentifire;
	}
	public MeetingMemberStateEnum getState() {
		return state;
	}
	public void setState(MeetingMemberStateEnum state) {
		this.state = state;
	}
	public Date getInEventTime() {
		return inEventTime;
	}
	public void setInEventTime(Date inEventTime) {
		this.inEventTime = inEventTime;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public String getUserGroupCodeJoinStr() {
		return userGroupCodeJoinStr;
	}
	public void setUserGroupCodeJoinStr(String userGroupCodeJoinStr) {
		this.userGroupCodeJoinStr = userGroupCodeJoinStr;
	}
	public String getUserGroupCodeNameJoinStr() {
		return userGroupCodeNameJoinStr;
	}
	public void setUserGroupCodeNameJoinStr(String userGroupCodeNameJoinStr) {
		this.userGroupCodeNameJoinStr = userGroupCodeNameJoinStr;
	}
	public boolean isGroup() {
		return isGroup;
	}
	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
    @Override
    public boolean equals(Object obj) {
    	if (obj!=null&&obj.getClass().equals(getClass())) {
    		MeetingMember member = getClass().cast(obj);
    		if (member.getUserId()!=null&&getUserId()!=null) {
				return getUserId().equals(member.getUserId())||getUserIdentifire().equals(member.getUserIdentifire());
			}
		}
    	return super.equals(obj);
    }

	public String getDeviceIdentifie() {
		return deviceIdentifie;
	}

	public void setDeviceIdentifie(String deviceIdentifie) {
		this.deviceIdentifie = deviceIdentifie;
	}
	@Override
	public String toString() {
		return "userName="+userName+"--inEventTime="+inEventTime;
	}
	public String getUserJob() {
		return userJob;
	}
	public void setUserJob(String userJob) {
		this.userJob = userJob;
	}
	public String getUserAttendType() {
		return userAttendType;
	}
	public void setUserAttendType(String userAttendType) {
		this.userAttendType = userAttendType;
	}
	public String getUserIdentityType() {
		return userIdentityType;
	}
	public void setUserIdentityType(String userIdentityType) {
		this.userIdentityType = userIdentityType;
	}
	public String getUserSubGroupType() {
		return userSubGroupType;
	}
	public void setUserSubGroupType(String userSubGroupType) {
		this.userSubGroupType = userSubGroupType;
	}
	public String getMeetingName() {
		return meetingName;
	}
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
}
