<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>二级党委-查看进度</title>
<%--     <link rel="stylesheet" href="${basePath }/css/common.css" /> --%>
    <link rel="stylesheet" href="${basePath }/css/party_member.css" />
    <link rel="stylesheet" href="${basePath }/css/party_organization.css" />
<%--     <link rel="stylesheet" href="${basePath }/css/bootstrap.min.css" /> --%>
        <script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
     <style type="text/css">
    	.todobander{
   		    height: 40px;
		    background: inherit;
		    background-color: rgba(206, 0, 0, 1);
		    border: none;
    	}
    	
    	
    	.todotitle{
    		white-space: nowrap;
   		    left: 30px;
		    top: 8px;
		    font-weight: 700;
		    color: #FFFFFF;
		    font-size: 18px;
		    position: relative;
    	}
    	
    	.todolist{
    	    width: 1000px;
    	    }
    	    
    	.todolist a:hover {
    		text-decoration: none;
    	} 
    	    
    	.todoinfo{
    	margin-top: 20px;
    	}
    	
    	td{
		    text-align:center;
		    border:1px solid #000; 
		    margin-left:-1px; 
		    margin-top:-1px;
		}
		
		tr{
		    
		    height: 30px;
		}
    
    </style>
</head>
<body onload="load()">

	
	<div class="todo">
		<div class="todobander">
			<span class="todotitle">查看进度</span>
			<input type="hidden" class="typenote" id="typenote" value="${type}"/>
			<input type="hidden" class="statusnote" id="statusnote" value="${status}"/>
		</div>
		
			
			<portlet:actionURL name="/hg/searchByTypeStatus" var="searchByTypeStatusUrl">
			</portlet:actionURL>
			
    
		<div class="todoinfo">
			<table class="todolist">
				<tr>
					<td >党支部</td>
					<td>开展主题</td>
					<td>会议主题</td>
					<td>开展时间</td>
					<td>开展地点</td>
					<td>主持人</td>
					<td>联系人</td>
					<td>联系人电话</td>
					<td>
						<select id="taskstatus" class="taskstatus" onchange="typeStatusAjax()">
				          <option value="任务状态">任务状态</option>
				          <option value="已提交">已提交</option>
				          <option value="待审批">待审批</option>
				          <option value="已通过">已通过</option>
				          <option value="被驳回">被驳回</option>
				          <option value="未检查">未检查</option>
				          <option value="已检查">已检查</option>
				          <option value="待分配">待分配</option>
				          <option value="已完结">已完结</option>
				        </select>
					</td>
					<td>审核人</td>
					<td>操作</td>
					<td>已读回执</td>
					<td>现场照片</td>
					<td>参会人数</td>
					<td>请假人员</td>
					<td>出勤率</td>
					<td>会议上传记录</td>
				</tr>
				
							
				<c:forEach var="c" items="${taskList}"  varStatus="status">
					<tr class="oneinfo" id=""oneinfo"">
						<td >党支部</td>
					<td>${c.theme}</td>
					<td>${c.truth_start_date}</td>
					<td>${c.place}</td>
					<td>${c.compere_person}</td>
					<td>${c.link_man}</td>
					<td>${c.link_telephone}</td>
					<td>${c.task_state}</td>
					<td>${c.meeting_type}</td>
					<td>
						<c:if test="${c.info_status == '已提交'}">
							<a href="">分配</a>	
						</c:if>
						<c:if test="${c.info_status == '待审批'}">
							<a href="">催办</a>	
						</c:if>
						<c:if test="${c.info_status == '未检查'}">
							<a href="">录入</a>	
						</c:if>
					
					</td>
					<td>已读回执</td>
					<td>现场照片</td>
					<td>${c.meeting_type}</td>
					<td>${c.meeting_type}</td>
					<td>${c.meeting_type}</td>
					<td>会议上传记录</td>
					</tr>
				</c:forEach>
				
				
			</table>
		
		</div>
	</div>
	
</body>


   
    <script>
    	
	    function load(){
			 var typenote = $("#typenote").val();
		     $(".meetingtype").val(typenote);
		     
		     var statusnote = $("#statusnote").val();
		     $(".taskstatus").val(statusnote);
	    }

// 	    	var type = ${".typenote"}.val();
// 	    	var status = ${".statusnote"}.val();
//     		$(".meetingtype option[value='"+type+"']").attr("selected","selected");
//     		$(".meetingtype option[value!='"+type+"']").removeAttr("selected");
    		
//     		$(".taskstatus option[value='"+status+"']").attr("selected","selected");
//     		$(".taskstatus option[value!='"+status+"']").removeAttr("selected");
	    	
// 	    }
    	
    	 function  typeStatusAjax(){
	   		   var typeobj = document.getElementById("meetingtype");
	    	   var typeindex = typeobj.selectedIndex;
	    	   var typevalue = typeobj.options[typeindex].value;
	    	   
	    	   var statusobj = document.getElementById("taskstatus");
	    	   var statusindex = statusobj.selectedIndex;
	    	   var statusvalue = statusobj.options[statusindex].value;
// 	    	   alert("type :" + typevalue +",status :" + statusvalue);
	    	   
	   		   var url="${searchByTypeStatusUrl}";
	   		   
// 	   		 alert("url :" + url );
	  		   var relurl=url+"&mettingType="+typevalue+"&taskstatus="+statusvalue;
// 	  		 alert("relurl :" + relurl );
	  		   window.location.href=relurl;
  	   	   } 

    	 
    	 
    	
		
    	
    
    </script>
</html>