<%@ include file="/init.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <portlet:actionURL name="/hg/zzq_edit" var="zzq_editURL" />/>

<aui:form action="${zzq_editURL }" method="post" >
	<aui:input lable="设置" name="size" type="text" />
	<aui:button type="submit" value="保存" />
</aui:form>

