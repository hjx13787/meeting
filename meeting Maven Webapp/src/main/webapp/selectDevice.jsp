<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>会议监控设备选择</title>
    
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
	$(function() {
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
			        var id = $('#meetingName').combobox('getValue');		
			      	
			      $.ajax({
			        type: "POST",
			        url: '<%=path%>/MeetingServlet?method=getDevices&meetingid=' + id,
			        cache: false,
			        dataType : "json",
			        success: function(data){
			        			if(data.success=false){
			        				$.messager.alert('提示', data.msg, 'error', function() {
										
									});
			        			}else{
			        				$("#device").combobox("loadData",data.obj);
			        			}
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
	});
	
	function submitForm() {
			var $form = $('#loginForm');//
			$('#loginBtn').linkbutton('disable');
			$.post('<%=path%>/MeetingServlet?method=setMeetingDevice', $form.serialize(), function(result) {
				if (result.success) {
					window.location.href = 'index.jsp'
				} else {
					$.messager.alert('提示', result.msg, 'error', function() {
						$('#loginBtn').linkbutton('enable');
					});
				}
			}, 'json');
		};
		function submitFormNoImage() {
			var $form = $('#loginForm');//
			$('#loginBtn').linkbutton('disable');
			$.post('<%=path%>/MeetingServlet?method=setMeetingDevice', $form.serialize(), function(result) {
				if (result.success) {
					window.location.href = 'indexnoImage.jsp'
				} else {
					$.messager.alert('提示', result.msg, 'error', function() {
						$('#loginBtn').linkbutton('enable');
					});
				}
			}, 'json');
		};
		function submitFormWenchuan() {
			var $form = $('#loginForm');//
			$('#loginBtn').linkbutton('disable');
			$.post('<%=path%>/MeetingServlet?method=setMeetingDevice', $form.serialize(), function(result) {
				if (result.success) {
					window.location.href = 'test.jsp'
				} else {
					$.messager.alert('提示', result.msg, 'error', function() {
						$('#loginBtn').linkbutton('enable');
					});
				}
			}, 'json');
		};
	</script>
  </head>
  
  <body>
  		
  		<form id="loginForm" method="post">
       <span>会议：</span>
       <input class="easyui-combobox"  style="width:200px;" name="meetingName"  id="meetingName">
       <span>设备：</span>
       <input class="easyui-combobox"  style="width:200px;" name="device" id="device">
       <a href="javascript:void(0)" id="loginBtn" class="easyui-linkbutton" onclick="submitFormNoImage()">监控无图片</a>
       <a href="javascript:void(0)" id="loginBtn" class="easyui-linkbutton" onclick="submitForm()">监控有图片</a>
       <a href="javascript:void(0)" id="loginBtn" class="easyui-linkbutton" onclick="submitFormWenchuan()">汶川县会议</a>
       </form>
  </body>
</html>
