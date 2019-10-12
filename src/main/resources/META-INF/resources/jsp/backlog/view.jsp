<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>党支部-待办事项</title>
<%--     <link rel="stylesheet" href="${basePath }/css/common.css" /> --%>
    <link rel="stylesheet" href="${basePath }/css/party_member.css" />
    <link rel="stylesheet" href="${basePath }/css/party_organization.css" />
<%--     <link rel="stylesheet" href="${basePath }/css/bootstrap.min.css" /> --%>
    
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
<body>

	
	<div class="todo">
		<div class="todobander">
			<span class="todotitle">待办事项</span>
		</div>
		
		
		<div class="todoinfo">
			<table class="todolist">
				<tr>
					<td>
						 <select id="u3143_input" class="meetingtype">
				          <option selected data-label="会议类型" value="会议类型">会议类型</option>
				          <option data-label="党课" value="党课">党课</option>
				          <option data-label="支部党员大会" value="支部党员大会">支部党员大会</option>
				          <option data-label="党支部委员会" value="党支部委员会">党支部委员会</option>
				          <option data-label="党小组会" value="党小组会">党小组会</option>
				          <option data-label="民主生活会" value="民主生活会">民主生活会</option>
				          <option data-label="专题组织生活会" value="专题组织生活会">专题组织生活会</option>
				          <option data-label="谈心谈话" value="谈心谈话">谈心谈话</option>
				          <option data-label="主题党日" value="主题党日">主题党日</option>
				          <option data-label="民主评议党员" value="民主评议党员">民主评议党员</option>
				          <option data-label="其它" value="通知">其它</option>
				        </select>
					</td>
					<td>会议主题</td>
					<td>发布时间</td>
					<td>
						<select id="u3144_input" class="taskstatus">
				          <option selected data-label="任务状态" value="任务状态">任务状态</option>
				          <option data-label="已提交" value="已提交">已提交</option>
				          <option data-label="待审批" value="待审批">待审批</option>
				          <option data-label="已通过" value="已通过">已通过</option>
				          <option data-label="被驳回" value="被驳回">被驳回</option>
				          <option data-label="未检查" value="未检查">未检查</option>
				          <option data-label="已检查" value="已检查">已检查</option>
				          <option data-label="待分配" value="待分配">待分配</option>
				          <option data-label="已完结" value="已完结">已完结</option>
				        </select>
					</td>
					<td>操作</td>
					<td>已读回执</td>
					<td>上传会议记录</td>
					<td>备注</td>
				</tr>
				
				
				
				<portlet:renderURL var="showDetailUrl">
					<portlet:param name="mvcRenderCommandName" value="/hg/showConferenceDetail"/>
				</portlet:renderURL>
				<c:forEach var="c" items="${taskList}"  varStatus="status">
					<tr>
						<td>${c.meeting_type}</td>
						<td><a href="${showDetailUrl}&resourceId=${c.resource_id}" >${c.theme}</a></td>
						<td>${c.public_date}</td>
						<td>${c.task_state}</td>
						<td>
							<c:if test="${c.info_status == '未读' && c.meeting_type != '专题组织生活会'}">
								<a href="">转配给党支部</a>	
							</c:if>
							<c:if test="${c.info_status == '未读' && c.meeting_type == '专题组织生活会'}">
								<a href="">报送计划</a>	
							</c:if>
							<c:if test="${c.task_state == '已提交'}">
								<a href="">撤回</a>	
							</c:if>
							<c:if test="${c.task_state == '待审批'}">
								<a href=""></a>	
							</c:if>
							<c:if test="${empty c.task_state && c.info_status != '未读'}">
								<a href="">查看进度</a>	
							</c:if>
							<c:if test="${c.task_state == '已通过'}">
								<a href="">通知党员</a>	
							</c:if>
							<c:if test="${c.task_state == '被驳回'}">
								<a href="">重拟计划</a>	
							</c:if>
							
						</td>
						
						<td>
							<c:if test="${c.task_state == '已通过' || c.task_state == '已检查' ||c.task_state == '已完结'}">
								<a href="">查看</a>	
							</c:if>
						</td>
						
						<td>
							<c:if test="${c.attachment == 'f'}">
								<a href="">上传</a>	
							</c:if>
							<c:if test="${c.attachment == 't'}">
								已上传
							</c:if>
						</td>
						
						<td>
								${c.remark}
						</td>
					</tr>
				</c:forEach>
				
				
			</table>
		
		</div>
	</div>
</body>


   
    <script>
    </script>
</html>