package com.donglu.meeting;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.caucho.hessian.client.HessianProxyFactory;
import com.dongluhitec.card.blservice.MeetingMonitorService;
import com.dongluhitec.card.domain.db.Device;
import com.dongluhitec.card.domain.db.meeting.Meeting;
import com.dongluhitec.card.domain.db.meeting.MeetingMember;

public class MeetingServlet extends HttpServlet {
	String meetingSeverUrl="127.0.0.1";
	private static MeetingMonitorService service;
	private Long meetingId;
	private String deviceIdentifie;
	private MeetingMember member;
	private static Map<Long,Meeting> mapMeeting=new HashMap<>();
	@Override
	public void init() throws ServletException {
		try {
			HessianProxyFactory h=new HessianProxyFactory();
			String getenv = System.getenv("severUrl");
			if (getenv!=null&&getenv.isEmpty()) {
				meetingSeverUrl=getenv;
			}
			service = (MeetingMonitorService) h.create(MeetingMonitorService.class, "http://"+meetingSeverUrl+":8889/meetingMonitor");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
//		System.out.println(method);
		if (method==null||method.isEmpty()) {
			return;
		}
		if(method.equals("getMeetings")){
			getMeetings(req,resp);
		}else if(method.equals("getDevices")){
			getDevices(req,resp);
		}else if(method.equals("getMember")){
			getMember(req,resp);
		}else if(method.equals("setMeetingDevice")){
			setMeetingDevice(req,resp);
		}else if(method.equals("getUserImage")){
			getUserImage(req,resp);
		}
	}

	private void getUserImage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (member==null||member.getUserId()==null) {
			return;
		}
		
		byte[] headerUserImage = service.getHeaderUserImage(member.getUserId());
		System.out.println("headerUserImage.length=="+headerUserImage.length);
		if (headerUserImage.length<1) {
			URL resource = getClass().getClassLoader().getResource("user.png");
			File file = new File(resource.getPath());
			headerUserImage = Files.readAllBytes(Paths.get(file.getPath()));
		}
		ServletOutputStream outputStream = resp.getOutputStream();
		outputStream.write(headerUserImage);
		outputStream.flush();
		outputStream.close();
	}

	private void setMeetingDevice(HttpServletRequest req, HttpServletResponse resp) {
		String msg="";
		Json j=new Json();
		try {
			Map<String, String[]> map = req.getParameterMap();
			System.out.println(map);
			
			String id = req.getParameter("meetingName");
			String deviceIdentifie = req.getParameter("device");
			if (id==null||deviceIdentifie==null) {
				throw new Exception("没有选择会议或设备");
			}
			meetingId = Long.valueOf(id);
			this.deviceIdentifie=deviceIdentifie;
			j.setSuccess(true);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		j.setMsg(msg);
		writeJson(j, req, resp);
	}


	private void getMember(HttpServletRequest req, HttpServletResponse resp) {
		member = new MeetingMember();
		member.setUserIdentifire("");
		member.setUserName("");
		member.setUserGroupCodeNameJoinStr("");
		String meetingName = mapMeeting.get(meetingId)==null?"":mapMeeting.get(meetingId).getName();
//		System.out.println("meetingName====="+meetingName);
		member.setMeetingName(meetingName);
		try {
			MeetingMember m = service.getLastEventMember(meetingId, deviceIdentifie);
			if (m!=null) {
				member=m;
			}
			String userGroupCodeNameJoinStr = member.getUserGroupCodeNameJoinStr();
			int indexOf = userGroupCodeNameJoinStr.lastIndexOf(")");
			String substring = userGroupCodeNameJoinStr.substring(indexOf+1);
			member.setUserGroupCodeNameJoinStr(substring);
			member.setSeat(member.getSeat()==null?"":member.getSeat());
//			System.out.println(member.getUserName());
		} catch (Exception e) {
			
		}
		writeJson(member, req, resp);
	}

	private void getDevices(HttpServletRequest req, HttpServletResponse resp) {
		Json j=new Json();
		try {
			String id = req.getParameter("meetingid");
			if (id==null) {
				throw new Exception("没有选择会议");
			}
			Long meetingId = Long.valueOf(id);
			List<Device> deviceByMeeting = service.getDeviceByMeeting(meetingId);
			if (deviceByMeeting.isEmpty()) {
				throw new Exception("没有找到设备");
			}
			j.setSuccess(true);
			j.setObj(deviceByMeeting);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		writeJson(j, req, resp);
	}

	private void getMeetings(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Calendar c=Calendar.getInstance();
			c.setTime(new Date());
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			Date parse = c.getTime();
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			Date parse2 = c.getTime();
			List<Meeting> list = service.getMeetingByBetwenTime(parse, parse2);
			for (Meeting meeting : list) {
				mapMeeting.put(meeting.getId(), meeting);
			}
			writeJson(list, req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param includesProperties
	 *            需要转换的属性
	 * @param excludesProperties
	 *            不需要转换的属性
	 */
	public void writeJsonByFilter(Object object, String[] includesProperties, String[] excludesProperties, HttpServletRequest req, HttpServletResponse resp) {
		try {
			FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
			if (excludesProperties != null && excludesProperties.length > 0) {
				filter.getExcludes().addAll(Arrays.<String> asList(excludesProperties));
			}
			if (includesProperties != null && includesProperties.length > 0) {
				filter.getIncludes().addAll(Arrays.<String> asList(includesProperties));
			}
//			logger.info("对象转JSON：要排除的属性[" + excludesProperties + "]要包含的属性[" + includesProperties + "]");
			String json;
			String User_Agent = req.getHeader("User-Agent");
			
			if (User_Agent.indexOf("MSIE 6") > -1) {
				// 使用SerializerFeature.BrowserCompatible特性会把所有的中文都会序列化为\\uXXXX这种格式，字节数会多一些，但是能兼容IE6
				json = JSON.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.BrowserCompatible);
			} else {
				// 使用SerializerFeature.WriteDateUseDateFormat特性来序列化日期格式的类型为yyyy-MM-dd hh24:mi:ss
				// 使用SerializerFeature.DisableCircularReferenceDetect特性关闭引用检测和生成
				json = JSON.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
			}
//			logger.info("转换后的JSON字符串：" + json);
			resp.setContentType("text/html;charset=utf-8");
			resp.getWriter().write(json);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void writeJson(Object j, HttpServletRequest req, HttpServletResponse resp) {
		writeJsonByFilter(j, null, null, req, resp);
	}
}
