<%@ include file="/init.jsp" %>
<%@ page contentType="text/html; charset=utf-8" %>
	<portlet:actionURL name="/login/preferences" var="urlAddress" /> 
	<form action="${ urlAddress }" method="post" >
		cas统一身份认证登录地址:<input name="url_address" value=""/><br>
		<input type="submit" />
	</form>
	

	