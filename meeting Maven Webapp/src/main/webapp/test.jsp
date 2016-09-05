<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>会议监控</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="themes/icon.css">
	<script type="text/javascript" src="jquery.min.js"></script>
	<script type="text/javascript" src="jquery.easyui.min.js"></script>
	
	<SCRIPT type="text/javascript">
	var int=self.setInterval("clock()",500)
	var lastIdentifier = "";
	function clock()
	{
		test();
	}

	function test(){
		$.ajax({
			contentType: "application/x-www-form-urlencoded",
			url: "<%=path%>/MeetingServlet?method=getMember",  
	        type: "POST",
	        dataType: "json",  
	        success: function(data) {
	            $("#meetingName").html(data.meetingName);
				if(lastIdentifier == data.userIdentifire){
					return;
				}
	            $("#name").html(data.userName);  
	            $("#group").html(data.userGroupCodeNameJoinStr);  
	            $("#seat").html(data.seat);
	            $("#identifie").html(data.userIdentifire);
	            $("#outer").html(data.userAttendType);
	            $("#job").html(data.userJob);
	            $("#identity").html(data.userIdentityType);
	            $("#subGroup").html(data.userSubGroupType);

				$("#image").attr('src',"<%=path%>/MeetingServlet?method=getUserImage&random="+Math.random());
				lastIdentifier = data.userIdentifire;
	        },
			error:function(data){
				alert(data);
			}
	   });
	}
	</SCRIPT>
  </head>
  <style>
.parent{position:relative;background:red; margin:0 auto;}
.children{position:absolute; width:900px; height:500px; left:50%; top:50%; margin-left:-450px;background:red;}
</style>
<body style="background:red;">
<div class="parent">
    <div class="children">
    	<div float:left;>
    		<div style="float:left;width:50px;height:50px;"><img src="国徽2.jpg" style="width:50px;height:50px;"></div>
    		<div style="float:left;color:yellow;font-size:22px;width:850px;height:50px;line-height:50px;text-align:center;">
    		<b id="meetingName"></b>
    		</div>
    	</div>
    	<div style="float:left;width:100%;height:100%;">
    		<div style="float:left;width:67%;height:100%;">
    			<div style="float: left;text-align:left;width:100%;">
    				<div style="text-align:left;float: left;font-size:18pt;margin-left:30px;line-height:100px;color:yellow;width:45%;">
    					<li style="list-style-type:none;">姓名：<b id="name"></b></li>
						<li style="list-style-type:none">编号：<b id="identifie"></b></li>
						<li style="list-style-type:none">座次：<b id="seat"></b></li>
						<li style="list-style-type:none">出席：<b id="outer"></b></li>
    				</div>
    				<div style="text-align:left;float: left;font-size:18pt;margin-left:10px;line-height:100px;color:yellow;">
    					<li style="list-style-type:none">职务：<b id="job"></b></li>
						<li style="list-style-type:none">单位：<b id="group"></b></li>
						<li style="list-style-type:none"><br/><b id="seat"></b></li>
						<li style="list-style-type:none">身份：<b id="identity"></b></li>
    				</div>
    			</div>
    			<div style="float: left;text-align:left;width:100%;font-size:18pt;margin-left:30px;line-height:100px;color:yellow;">
    				<li style="list-style-type:none">分团：<b id="subGroup"></b></li>
    			</div>
    		</div>
    		<div style="float:left;width:33%;height:100%;">
    			<div style="float:left;height:50%;width:100%;margin-top:20px;"><img id="image" style="float:left;width:100%;height:100%;" src="user.jpg"></div>
    			<div style="float:left;height:25%;width:100%;"><img style="float:left;width:100%;height:100%;" src="广告1.jpg"></div>
    			<div style="float:left;height:25%;width:100%;"><img style="float:left;width:100%;height:100%;" src="广告2.jpg"></div>
    		</div>
    	</div>
    </div>
</div>
  </body>
</html>
