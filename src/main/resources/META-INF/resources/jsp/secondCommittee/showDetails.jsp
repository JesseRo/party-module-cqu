<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<%@ include file="/init.jsp" %>
<!DOCTYPE html>
<html>


<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>二级党委-待办事项</title>
    
     <style type="text/css">
    	.details_title{
    		font-size: 20px;
		    padding: 50px 0 25px 0;
		    font-weight: 600;
		    text-align: center;
    	}
    	
    	.beforeInfo{
    		font-size: 16px;
		    font-weight: 600;
		    color: #666;
		    margin-right: 5px;
    	}
    	
    	.details_content_title{
    		    padding: 20px 0 10px 0;
			    box-sizing: content-box;
			    height: 20px;
			    font-size: 14px;
			    color: #999;
			    border-bottom: 1px solid #e1e1e1;
    	}
    	
    	.details_content_info{
    		    max-height: 590px;
			    padding-top: 20px;
			    font-size: 18px;
			    line-height: 40px;
    	}
    	
    	.btn_group{
    	    text-align: center;
    	}
    	.details_content_title .title_time{
    		text-align:right;
    	}
    	.details_content_info .info_description{
            min-height: 200px;
        }
    	
    </style>
</head>




<body>

	<portlet:renderURL var="confirmConferenceDetailUrl">
		<portlet:param name="mvcRenderCommandName" value="/hg/confirmConferenceDetail"/>
	</portlet:renderURL>
                <div class="content_title hidden-xs">
                    	消息详情
                </div>
               <div class="details_container">
                    <p class="details_title">${informMeeting.meeting_theme}</p>
                    <div class="details_content">
                        <div class="details_content_title">
                            <p class="col-sm-6 col-xs-12"><span class="beforeInfo">会议类型</span>${informMeeting.meeting_type}</p>
<%--                         <p class="col-sm-6 col-xs-12"><span class="beforeInfo">开展时间</span>${informMeeting.start_time} -- ${informMeeting.end_time}</p> --%>
                            
<%--                             <p class="col-sm-6 col-xs-12"><span class="beforeInfo">开展时间</span> ${fn:substring(informMeeting.start_time,0,4)}年/${fn:substring(informMeeting.start_time,6,8)}月/${fn:substring(informMeeting.start_time,10,12)}日——${fn:substring(informMeeting.end_time,0,4)}年/${fn:substring(informMeeting.end_time,6,8)}月/${fn:substring(informMeeting.end_time,10,12)}日 </p> --%>
<%-- 								 <p class="col-sm-6 col-xs-12"><span class="beforeInfo">开展时间</span> ${fn:substring(informMeeting.start_time,0,19)}——${fn:substring(informMeeting.end_time,0,19)} --%>
								 <p class="col-sm-6 col-xs-12 title_time"><span class="beforeInfo">开展时间</span> ${fn:substring(informMeeting.start_time,0,4)}/${fn:substring(informMeeting.start_time,5,7)}/${fn:substring(informMeeting.start_time,8,10)}${fn:substring(informMeeting.start_time,11,19)}—${fn:substring(informMeeting.end_time,0,4)}/${fn:substring(informMeeting.end_time,5,7)}/${fn:substring(informMeeting.end_time,8,10)}${fn:substring(informMeeting.end_time,11,19)} </p>
								 
								 
                        </div>
                        <div class="details_content_info">
                        	<div class="info_description">${informMeeting.content}</div>
	                         <div class="btn_group">
	                            <button class="btn btn-default btn-lg main_color_btn">
	                            <a href="${confirmConferenceDetailUrl}&informId=${informMeeting.inform_id}&informStatus=${informMeeting.task_status}" style="color:white">确定</a>
	                            </button>
	                         </div>
                        </div>
                     </div>
                </div>
</body>


   
    <script>
    </script>
</html>