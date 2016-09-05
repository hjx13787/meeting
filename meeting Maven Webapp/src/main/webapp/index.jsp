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
<%--$(function() {
		
		 var loginFun = function() {
				var $form = $('#loginForm');//选中的tab里面的form
				$('#loginBtn').linkbutton('disable');
				$.post('<%=path%>/MeetingServlet?method=setMeetingDevice', $form
						.serialize(), function(result) {
					if (result.success) {
						$('#loginDialog').close();
					} else {
						$.messager.alert('提示', result.msg, 'error', function() {
							$('#loginBtn').linkbutton('enable');
						});
					}
				}, 'json');
		};
		var init=function() {
			// 下拉框选择控件，下拉框的内容是动态查询数据库信息
			  $('#meetingName').combobox({ 
			          url:'<%=path%>/MeetingServlet?method=getMeetings', 
			          editable:false, //不可编辑状态
			          cache: false,
			          panelHeight: '150',
			          valueField:'id',   
			          textField:'name',
			          
			    onHidePanel: function(){
			        $("#device").combobox("setValue",'');//清空课程
			        var id = $('#device').combobox('getValue');		
			      	alert(id);
			      	
			      $.ajax({
			        type: "POST",
			        url: '<%=path%>/MeetingServlet?method=getDevices&meetingid=' + id,
			        cache: false,
			        dataType : "json",
			        success: function(data){
			        	$("#device").combobox("loadData",data);
			                 }
			            }); 	
			         }
				});
			  $('#device').combobox({ 
			      //url:'itemManage!categorytbl', 
			      editable:false, //不可编辑状态
			      cache: false,
			      panelHeight: '150',//自动高度适合
			      valueField:'identifier',   
			      textField:'name'
			     });
	}; 
		/* $('#loginDialog').show().dialog({
			modal : false,
			closable : false,
			iconCls : 'ext-icon-lock_open',
			buttons : [ {
				id : 'loginBtn',
				text : '确认',
				handler : function() {
					loginFun();
				}
			} ],
			onOpen : function() {
				$('form :input:first').focus();
				$('form :input').keyup(function(event) {
					if (event.keyCode == 13) {
						loginFun();
					}
				});
				init();
			}
		}); */
	});--%>
</script>
</head>

<body style="vertical-align: middle;">

	<div title="s" style="position:absolute;left:50%;top:50%;margin-left:-270px;margin-top:-160px;height:320px;width:540px;">
		<div style="float: left;margin-top:30px;">
			<image id="image" width="200px" height="250px" src="user.png" />
		</div>
		<div style="text-align:left;float: left;font-size:18pt;margin-left:10px;line-height:100px">
			<li style="list-style-type:none">用户姓名：<b id="name"></b></li>
			<li style="list-style-type:none">所属部门：<b id="group"></b></li>
			<li style="list-style-type:none">用户座位：<b id="seat"></b></li>
		</div>
	</div>
	<!-- <div id="loginDialog" title="监控设备选择" style="display: none; width: 320px; height: 180px; overflow: hidden;">
			<div title="设备选择" style="overflow: hidden; padding: 10px;">
				<form id="loginForm" method="post" class="form">
					<table class="table" style="width: 100%; height: 100%;">
						<tr>
							<th width="50">会议</th>
							<td><input name="meetingName" class="easyui-combobox" value="" style="width: 210px;" /></td>
						</tr>
						<tr>
							<th>设备</th>
							<td><input name="device" class="easyui-combobox" value=""
								style="width: 210px;" /></td>
						</tr>
					</table>
				</form>
			</div>
	</div> -->
</body>
</html>
