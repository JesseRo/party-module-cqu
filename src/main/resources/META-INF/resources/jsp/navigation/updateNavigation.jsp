<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head></head>
	
	<body>
		<portlet:actionURL name="/navigation/update" var="updateUrl">
		</portlet:actionURL>
		<form action="${updateUrl }" method="post">
			<input type="hidden" name="navigationId" value="${info.navigation_id }">
			导航名称:<input class="navName" value="${info.navigation_name }" name="navName"><br>
			导航路径:<input readonly="readonly" value="${info.navigation_url }"><br>
			显示位置:<input class="location" value="${info.show_location }" name="location"><br>
			角色权限:<input class="role" value="${info.navigation_to_role }" name="role"><br>
			<button onclick="javascript:window.history.back(-1);">取消</button>
			<button type="submit">确认</button>
		</form>
		
	</body>
</html>