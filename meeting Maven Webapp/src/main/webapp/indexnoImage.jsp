<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
System.out.println(path);
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>用户签到信息</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="themes/icon.css">
<script type="text/javascript" src="jquery.min.js"></script>
<script type="text/javascript" src="jquery.easyui.min.js"></script>
<script type="text/javascript">
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

			$("#image").attr('src',"<%=path%>/MeetingServlet?method=getUserImage&random="+Math.random());
			lastIdentifier = data.userIdentifire;
        },
		error:function(data){
			alert(data);
		}
   });
}
</script>
</head>

<body>
	<div title="s" style="position:absolute;left:50%;top:50%;margin-left:-160px;margin-top:-150px;height:300px;width:320px;">
		<div style="text-align:left;float: left;font-size:18pt;line-height:100px">
			<li style="list-style-type:none">用户姓名：<b id="name"></b></li>
			<li style="list-style-type:none">所属部门：<b id="group"></b></li>
			<li style="list-style-type:none">用户座位：<b id="seat"></b></li>
		</div>
	</div>
</body>
</html>
