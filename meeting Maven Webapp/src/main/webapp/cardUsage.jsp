<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
String deviceIdentifier=request.getParameter("deviceIdentifier");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>安康市人民政府刷卡监控</title>
    
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
			url: "<%=path%>/MeetingServlet?method=getCardUsage&deviceIdentifier=<%=deviceIdentifier%>",
	        type: "POST",
	        dataType: "json",
	        success: function(data) {
				if(data.id==""||data.id==null||lastIdentifier == data.id){
					return;
				}
				if(data.visitorId!=null){
					 $("#nameLabel").html("访客姓名：");  
			         $("#idcardLabel").html("证 件 号：");  
			         $("#companyLabel").html("访客单位：");
			         $("#userNameLabel").html("被访人员：");
			         $("#userGroupLabel").html("被访部门：");
			         $("#reasonLabel").html("访问理由：");
			         $("#name").html(data.visitorName);  
			         $("#idcard").html(data.visitorIdCard);  
			         $("#company").html(data.visitorCompany);
			         $("#userName").html(data.userName);
			         $("#userGroup").html(data.userGroup);
			         $("#reason").html(data.visitorReason);
			         $("#image").attr('src',"<%=path%>/MeetingServlet?method=getCardUsageImage&imageName="+data.imageName);
				}else if(data.userId!=null){
					 $("#nameLabel").html("用户姓名："); 
					 $("#idcardLabel").html("用户部门：");  
			         $("#companyLabel").html("");
			         $("#userNameLabel").html("");
			         $("#userGroupLabel").html("");
			         $("#reasonLabel").html("");
			         $("#name").html(data.userName);  
			         $("#idcard").html(data.userGroup);  
			         $("#company").html("");
			         $("#userName").html("");
			         $("#userGroup").html("");
			         $("#reason").html("");
			         $("#image").attr('src',"<%=path%>/MeetingServlet?method=getCardUsageImage&random="+Math.random());
				}
				lastIdentifier = data.id;
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
<body style="background:red;font-family:FangSong_GB2312;">
<div class="parent">
    <div class="children">
    	<div style="float:left;">
    		<div style="float:left;width:140px;height:140px;"><img src="guohui.jpg" style="width:140px;height:140px;"></div>
    		<div style="float:left;color:yellow;font-size:62px;width:860px;height:150px;line-height:75px;text-align:center;">
    		<b></b><br>
    		安康市人民政府
    		
    		</div>
    	</div>
    	<div style="float:left;height:3px;background:yellow;width:100%"></div>
    	<div style="float:left;width:100%;height:100%;">
    		<div style="float:left;width:61%;height:100%;">
    			<div style="float: left;text-align:left;width:100%;margin-top:20px;">
    				<div style="text-align:left;float: left;font-size:40;margin-left:0px;line-height:80px;color:yellow;width:100%;">
    					<li style="list-style-type:none;"><b id="nameLabel">用户姓名：</b><b id="name"></b></li>
						<li style="list-style-type:none"><b id="idcardLabel">证 件 号：</b><b id="idcard"></b></li>
						<li style="list-style-type:none"><b id="companyLabel">公司名称：</b><b id="company"></b></li>
						<li style="list-style-type:none"><b id="userNameLabel">被访人员：</b><b id="userName"></b></li>
						<li style="list-style-type:none"><b id="userGroupLabel">被访部门：</b><b id="userGroup"></b></li>
						<li style="list-style-type:none"><b id="reasonLabel">访问理由：</b><b id="reason"></b></li>
    				</div>
    			</div>
    		</div>
    		<div style="float:left;width:39%;height:100%;">
    			<div style="float:left;height:100%;width:100%;margin-top:20px;border:2px solid #000;"><img id="image" style="float:left;width:100%;height:100%;" src="hu.jpg"></div>
    		</div>
    	</div>
    </div>
</div>
  </body>
</html>
