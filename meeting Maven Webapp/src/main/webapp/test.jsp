<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title><%=new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8") %></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta charset="UTF-8">
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
.children{position:absolute; width:1000px; height:450px; left:50%; top:50%; margin-left:-500px;background:red;}
</style>
<body style="background:red;">
<div class="parent">
    <div class="children">
    	<div style="float:left;">
    		<div style="float:left;width:140px;height:140px;"><img src="dang.jpg" style="width:140px;height:140px;"></div>
    		<div style="float:left;color:yellow;font-size:42px;width:860px;height:150px;line-height:75px;text-align:center;">
    		<%
    		String title = new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8");
    		String[] split = title.split("，");
    		for(String t:split){
    			out.print("<b id='meetingName' style='text-align:center;whdth:100%'>"+t+"</b><br/>");
    		}
    		%>
    		
    		</div>
    	</div>
    	<div style="float:left;height:3px;background:yellow;width:100%"></div>
    	<div style="float:left;width:100%;height:100%;">
    		<div style="float:left;width:75%;height:100%;">
    			<div style="float: left;text-align:left;width:100%;margin-top:50px;">
    				<div style="text-align:left;float: left;font-size:40;margin-left:0px;line-height:80px;color:yellow;width:45%;">
    					<li style="list-style-type:none;">姓名：<b id="name"></b></li>
						<li style="list-style-type:none">编号：<b id="identifie"></b></li>
    				</div>
    				<div style="text-align:left;float: left;font-size:40;margin-left:0px;line-height:80px;color:yellow;">
    					<li style="list-style-type:none">职务：<b id="job"></b></li>
						<li style="list-style-type:none">座次：<b id="seat"></b></li>
    				</div>
    			</div>
    			<div style="float: left;text-align:left;width:100%;font-size:40;margin-left:0px;line-height:80px;color:yellow;">
    				<li style="list-style-type:none">单位：<b id="group"></b></li>
    			</div>
    			<div style="float: left;text-align:left;width:100%;">
    				<div style="text-align:left;float: left;font-size:40;margin-left:0px;line-height:80px;color:yellow;width:45%;">
						<li style="list-style-type:none">出席：<b id="outer"></b></li>
    				</div>
    				<div style="text-align:left;float: left;font-size:40;margin-left:0px;line-height:80px;color:yellow;">
						<li style="list-style-type:none">身份：<b id="identity"></b></li>
    				</div>
    			</div>
    			<div style="float: left;text-align:left;width:100%;font-size:40;margin-left:0px;line-height:80px;color:yellow;">
    				<li style="list-style-type:none">分团：<b id="subGroup"></b></li>
    			</div>
    		</div>
    		<div style="float:left;width:25%;height:100%;">
    			<div style="float:left;height:60%;width:100%;margin-top:20px;border:2px solid #000;"><img id="image" style="float:left;width:100%;height:100%;" src="hu.jpg"></div>
    			<div style="float:left;height:20%;width:100%;margin-top:5px;"><img style="float:left;width:100%;height:100%;" src="guanggao1.jpg"></div>
    			<div style="float:left;height:20%;width:100%;margin-top:5px;"><img style="float:left;width:100%;height:100%;" src="guanggao2.jpg"></div>
    		</div>
    	</div>
    </div>
</div>
  </body>
</html>
