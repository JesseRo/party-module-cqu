<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 保存新增站点 -->
<portlet:actionURL name="/form-configure/set" var="set"/>
	<portlet:renderURL var="formConfigureRender">
		   <portlet:param name="mvcRenderCommandName" value="/form-configure/render"/>
	</portlet:renderURL>

<div style="text-align: center;">
<style>
		#form-configure{
			border:1px solid black;
			margin: 20px auto;
		   	text-align: center;
			width:90%;
			border-collapse: collapse;
    		table-layout: fixed;
		}
		#form-configure th,tr,td{
			border:1px solid black;
		    width: 25%;
		   	height: 32px;
		   	text-overflow:ellipsis;
			overflow:hidden;
			white-space: nowrap;
		}
		#form-configure th{
			text-align: center;
		}
</style>
	<table id="form-configure">
		<tr>
		<c:forEach items="${titles }" var="title">
			
			<th>${title }</th>
		</c:forEach>
			
		</tr>
		<c:forEach items="${designs }" var="design">
			<tr id="${design.id }">
				<td>${design.name }</td>
				<td>${design.submit_command }</td>
				<td>${design.render_jsp }</td>
				<td>${design.render_command }</td>
				<td>${design.description }</td>
				<td>${design.design }</td>
				<td><button id="confirm" onclick="document.location.href='${set}&designId=${design.id }'">设定</button>
				<button id="detail" onclick="document.location.href='${formConfigureRender}&designId=${design.id }'">详情</button></td>
			</tr>
		</c:forEach>
	</table>
	<button onclick="document.location.href='${formConfigureRender}'">新增</button>
</div>