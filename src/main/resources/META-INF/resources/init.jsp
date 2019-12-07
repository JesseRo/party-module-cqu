
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<link href="${ basePath }/js/zTre/css/zTreeStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${basePath }/js/zTre/jquery.ztree.core.js"></script> 
<script type="text/javascript" src="${basePath }/js/ajaxfileupload/ajaxfileupload.js"></script>
<script type="text/javascript" src="${basePath}/js/bootstrap3-validation.js"></script>
<%--<script type="text/javascript" src="${basePath}/js/bootstrap.min.js"></script> --%>
<script type="text/javascript" src="${basePath}/js/partyModal.js"></script>
<liferay-theme:defineObjects />

<portlet:defineObjects />

<script type="text/javascript">
  $("#hg-form-container").parent().keydown(function(event){
	//  console.log(event);
	  if(event.keyCode==13){
		  return false;
		  } 
  });
</script>
<style>
.form-label-required::before {
    content: "*";
    position: absolute;
    top: 2px;
    left: -10px;
    color: #ce0000;
    line-height: 40px;
}
 label.error{
   position: absolute;
   color: #ce0000;
   font-size: 12px;
   font-weight: normal;
}

.addPersons {
    max-height: 300px;
    overflow: scroll;
}
#imgEntry{
	width: 100%;
    height: 100%;
}

</style>
