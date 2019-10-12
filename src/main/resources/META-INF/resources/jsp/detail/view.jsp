<!-- 详细内容展示页面 -->
<%@ include file="/init.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<head>
    <meta charset='utf-8' />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <link rel="stylesheet" href="${basePath }/css/news_details.css">
    <title>新闻详情</title>
    <style type="text/css">
    	.news_details .news_details_description{
    		min-height:350px;
    	}
    	.news_details_description img{
    		width:70%;
    		margin:10px 15%;
    	}
    </style>
</head>
<body>
    <div class="full_screen news_details_container">
        <div class="min_width_1200">
            <ul class="bread section_content col-sm-12 col-xs-12">
                <li>
                    <img src="/images/bread_icon.png"> 当前位置：
                </li>
                <li>
                    <span>网站首页</span>
                </li>
                <c:if test="${not empty map.remark}">
	                <li class="divider">&gt;</li>
	                <li>
	                    <span>${map.remark }</span>
	                </li>
                </c:if>
                <li class="divider">&gt;</li>
                <li>
                    <span class="bread_on">新闻详情</span>
                </li>
            </ul>
            <div class="news_details col-sm-12 col-xs-12">
                <div class="news_details_title">${ map.content_title }</div>
                <div class="news_author">
                    <span>发布时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${map.publish_time}" /></span>
                    <span>作者：${ map.publisher_id }</span>
                </div>
                <div class="news_details_description">${ map.content_body }</div>
        		<portlet:resourceURL id="/dowloadResourceCommand" var ="download">  
<%--      				<portlet:param name="attachment_url" value="${map.attachment_url }"/> --%>
     			</portlet:resourceURL>
	     		<c:if test="${ not empty atts }">
	   				<c:forEach items="${atts }" var="att" varStatus="status" >
		                <h2 style="color: #47647a;background: #f6f6f6;font-size:14px;">
				        	<u>
						        <span class="glyphicon glyphicon-chevron-right" style="color: #47647a;"></span>&nbsp;&nbsp;附件下载${status.index+1}:&nbsp;
						        <a href="${download}&attachment_url=${att.attachment_url } ">《${ att.attachment_name }》</a>
				        	</u>
				        </h2><br/>
	   				</c:forEach>
	        	</c:if>
            </div>
        </div>
    </div>
    <script>
	    //footer 置于底部
	    $(function(){
	    	 $("#home").addClass("page_on");
	    })
    </script>
</body>
