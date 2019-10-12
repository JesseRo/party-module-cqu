<%@ include file="/init.jsp" %>
<%@ page contentType="text/html; charset=utf-8" %>

<%--<aui:form action="" method="post" > --%>
	
	<portlet:actionURL name="/pageSize" var="pageSize" /> 
	<aui:form action="${pageSize }" method="post" >
		<aui:input name="pageSize" value=""/>
		<aui:input name="chaining_type" value=""/>
		<aui:button type="submit" value="设置图片数" />
	</aui:form>
	
