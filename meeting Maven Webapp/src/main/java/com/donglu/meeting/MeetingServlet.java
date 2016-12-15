package com.donglu.meeting;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import com.dongluhitec.card.blservice.CardUsageInfo;
import com.dongluhitec.card.blservice.MeetingMonitorService;
import com.dongluhitec.card.domain.db.Device;
import com.dongluhitec.card.domain.db.meeting.Meeting;
import com.dongluhitec.card.domain.db.meeting.MeetingMember;

public class MeetingServlet extends HttpServlet {
	private static final String MAP_MEETING = "mapMeeting";
	String meetingSeverUrl="127.0.0.1";
	private static MeetingMonitorService service;
	private Long meetingId;
	private String deviceIdentifie;
	private MeetingMember member;
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
			getUserImage(null,req,resp);
		}else if(method.equals("getCardUsage")){
			getCardUsage(req,resp);
		}else if(method.equals("getCardUsageImage")){
			getCardUsageImage(req,resp);
		}else if(method.equals("getAllDevices")){
			getAllDevices(req,resp);
		}
		
	}
	private void getAllDevices(HttpServletRequest req, HttpServletResponse resp) {
		try {
			List<Device> list = service.findAllDevices();
			writeJson(list, req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取刷卡记录的图片
	 * @param req
	 * @param resp
	 */
	private void getCardUsageImage(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String imageName = req.getParameter("imageName");
			if (imageName!=null) {
				byte[] cardUsageImage = service.getCardUsageImage(imageName);
				ServletOutputStream outputStream = resp.getOutputStream();
				outputStream.write(cardUsageImage);
				outputStream.flush();
				outputStream.close();
			}else{
    			Long userId = (Long) req.getSession().getAttribute("userId");
    			if (userId!=null) {
    				getUserImage(userId, req, resp);
    			}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取最后的刷卡记录
	 * @param req
	 * @param resp
	 */
	private void getCardUsage(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String deviceIdentifier = req.getParameter("deviceIdentifier");
			CardUsageInfo cardUsageInfo = service.findLastCardUsageInfo(deviceIdentifier);
			if (cardUsageInfo==null) {
				cardUsageInfo=new CardUsageInfo();
			}else{
				Long userId = cardUsageInfo.getUserId();
				if (userId!=null) {
					req.getSession().setAttribute("userId", userId);
				}
			}
			writeJson(cardUsageInfo, req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 获取用户头像
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void getUserImage(Long userId,HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			if (userId==null) {
				if (member==null||member.getUserId()==null) {
					return;
				}
				userId=member.getUserId();
			}
			byte[] headerUserImage = service.getHeaderUserImage(userId);
			if (headerUserImage==null||headerUserImage.length==0) {
				headerUserImage = service.getHeaderUserImage(userId);
			}
			ServletOutputStream outputStream = resp.getOutputStream();
			outputStream.write(headerUserImage);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取会议设备
	 * @param req
	 * @param resp
	 */
	private void setMeetingDevice(HttpServletRequest req, HttpServletResponse resp) {
		try {
			req.setCharacterEncoding("UTF-8");
			String id = req.getParameter("id");
			String name = new String(req.getParameter("name").getBytes("iso-8859-1"),"utf-8");
			String deviceIdentifier = req.getParameter("deviceIdentifier");

			req.getSession().setAttribute("id", id);
			req.getSession().setAttribute("name",name);
			req.getSession().setAttribute("deviceIdentifier",deviceIdentifier);
    		
			String location = "test.jsp?name=" + URLEncoder.encode(name, "utf-8");
			resp.sendRedirect(location );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取最后刷卡的会议用户
	 * @param req
	 * @param resp
	 */
	private void getMember(HttpServletRequest req, HttpServletResponse resp) {
		String parameter = (String)req.getSession().getAttribute("id");
		if (parameter==null) {
			return;
		}
		Long id=Long.valueOf(parameter);
		String deviceIdentifier = (String)req.getSession().getAttribute("deviceIdentifier");
		member = new MeetingMember();
		member.setUserIdentifire("");
		member.setUserName("");
		member.setUserGroupCodeNameJoinStr("");
		member.setUserJob("");
		member.setUserAttendType("");
		member.setUserIdentityType("");
		member.setUserSubGroupType("");
		
		try {
			MeetingMember m = service.getLastEventMember(id, deviceIdentifier);
			if (m!=null) {
				member=m;
			}
			String userGroupCodeNameJoinStr = member.getUserGroupCodeNameJoinStr();
			int indexOf = userGroupCodeNameJoinStr.lastIndexOf(")");
			String substring = userGroupCodeNameJoinStr.substring(indexOf+1);
			member.setUserGroupCodeNameJoinStr(substring);
			member.setSeat(member.getSeat()==null?"":member.getSeat());
			String mName = (String)req.getSession().getAttribute("name");
			member.setMeetingName(mName);
		} catch (Exception e) {
			
		}
		writeJson(member, req, resp);
	}
	/**
	 * 查找会议下的设备
	 * @param req
	 * @param resp
	 */
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
	/**
	 * 获取所有会议
	 * @param req
	 * @param resp
	 */
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
			Map<Long,Meeting> mapMeeting=new HashMap<>();
			for (Meeting meeting : list) {
				mapMeeting.put(meeting.getId(), meeting);
			}
			req.getSession().setAttribute(MAP_MEETING, mapMeeting);
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
