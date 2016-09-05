package com.dongluhitec.card.blservice;

import java.util.Date;
import java.util.List;

import com.dongluhitec.card.domain.db.Device;
import com.dongluhitec.card.domain.db.meeting.Meeting;
import com.dongluhitec.card.domain.db.meeting.MeetingMember;

public interface MeetingMonitorService {
	List<Meeting> getMeetingByBetwenTime(Date startTime,Date endTime);
	List<Device> getDeviceByMeeting(Long meetingId);
	MeetingMember getLastEventMember(Long meetingId,String deviceIdentifie);
	byte[] getHeaderUserImage(Long userId);
}
