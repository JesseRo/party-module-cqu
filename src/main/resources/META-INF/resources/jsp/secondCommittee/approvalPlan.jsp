<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>二级党委-审批计划</title>
<%--     <link rel="stylesheet" href="${basePath }/css/common.css" /> --%>
    <link rel="stylesheet" href="${basePath }/css/party_member.css" />
    <link rel="stylesheet" href="${basePath }/css/party_organization.css" />
<%--     <link rel="stylesheet" href="${basePath }/css/bootstrap.min.css" /> --%>
    
    <style type="text/css">
    	.approvalplanbander{
   		    height: 40px;
		    background: inherit;
		    background-color: rgba(206, 0, 0, 1);
		    border: none;
    	}
    	
    	
    	.approvalplantitle{
    		white-space: nowrap;
   		    left: 30px;
		    top: 8px;
		    font-weight: 700;
		    color: #FFFFFF;
		    font-size: 18px;
		    position: relative;
    	}
    	
    	.approvalplaninfo{
    	margin-top: 20px;
    	}
    	
    	.approvalplanlist{
    	    width: 1000px;
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
    th,td{
     text-align: left;
    }
    @media (min-width: 768px){
     .content_table td, .content_table th {
    min-width: 170px;
    padding: 0 25px;
    }
    
    .table_info .PublishTime {
    min-width: 200px;
}
    </style>
    
</head>
<body>


	<div class="approvalplan">
		<div class="approvalplanbander">
			<span class="approvalplantitle">审批计划</span>
		</div>
		
		
		<div class="approvalplaninfo">
			<table class="approvalplanlist">
				<tr>
					<td>党支部</td>
					<td>会议类型</td>
					<td>发布时间</td>
					<td>开展主题</td>
					<td>开展时间</td>
					<td>开展地点</td>
					<td>参会人数</td>
					<td>主持人</td>
					<td>联系人</td>
					<td>联系人电话</td>
					<td>
						<select id="u4109_input" class="taskstatus">
				          <option selected value="任务状态">任务状态</option>
				          <option value="待审批">待审批</option>
				          <option value="已审批">已审批</option>
				        </select>
					</td>
					<td>审核人</td>
					<td>操作</td>
					<td>备注</td>
				</tr>
				
				<tr>
					<td>教工一支部</td>
					<td>明主生活会</td>
					<td>2017-12-15 09：00</td>
					<td>党风廉政</td>
					<td>2017/12/30 09:30-11:30</td>
					<td>302会议室</td>
					<td>12人</td>
					<td>张三</td>
					<td>李四</td>
					<td>15556788765</td>
					<td>待审批</td>
					<td>书记</td>
					<td>通过丨驳回</td>
					<td></td>
				</tr>
				
				<tr>
					<td>教工一支部</td>
					<td>明主生活会</td>
					<td>2017-12-15 09：00</td>
					<td>公平公正</td>
					<td>2017/12/30 09:30-11:30</td>
					<td>302会议室</td>
					<td>12人</td>
					<td>张三</td>
					<td>李四</td>
					<td>15556788765</td>
					<td>待审批</td>
					<td>书记</td>
					<td>通过</td>
					<td></td>
				</tr>
				
				<tr>
					<td>教工一支部</td>
					<td>明主生活会</td>
					<td>2017-12-15 09：00</td>
					<td>公平公正</td>
					<td>2017/12/30 09:30-11:30</td>
					<td>302会议室</td>
					<td>12人</td>
					<td>张三</td>
					<td>李四</td>
					<td>15556788765</td>
					<td>待审批</td>
					<td>书记</td>
					<td>驳回</td>
					<td>超过规定提交时间</td>
				</tr>
				
				
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				
			</table>
		
		</div>
	</div>
   
   

</body>


   
    <script>
    </script>
</html>