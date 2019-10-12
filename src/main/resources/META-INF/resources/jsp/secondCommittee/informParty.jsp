<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- 保存新增站点 -->
<%-- <portlet:actionURL name="${submitCommand }" var="submitForm"/> --%>
<portlet:actionURL name="/hg/postSubmissions" var="submitForm"/>

<portlet:resourceURL id="/hg/getPublicObject" var="getPublicObject"/>
<portlet:resourceURL id="/hg/getPlace" var="getPlace"/>
<!--获取常用组 -->
<portlet:resourceURL id="/hg/getGroup" var="getGroup"/>
<portlet:resourceURL id="/form/uploadImage" var="uploadimageUrl"/>
<!-- 视频上传 -->
<portlet:resourceURL id="/form/uploadVideo" var="uploadvideoUrl"/>
<!-- 附件上传 -->
<portlet:resourceURL id="/form/uploadFile" var="uploadfileUrl"/>

<portlet:renderURL var="informPartyUrl">
    <portlet:param name="mvcRenderCommandName" value="/hg/informParty"/>
</portlet:renderURL>

<html>
<head>
    <style type="text/css">
        .buttons {
            position: relative;
            top: 80px;
            left: 350px;
        }

        /* #hg-form-container > div:nth-child(1) .control-label{
            font-size:12px;
        } */

        button.btn.btn-default {
            margin-left: 40%;
            margin-right: 10%;
        }
    </style>
    <link rel="stylesheet" href="${basePath}/css/publish.css"/>
    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/third-party/codemirror/codemirror.css"/>
    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/themes/iframe.css"/>
    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/themes/default/css/ueditor.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery-validation.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${basePath}/js/form.js?version=5"></script>
    <%-- <script type="text/javascript" src="${basePath}/js/form.js?version=99"></script> --%>
    <script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.config-thumb.js?v=3"></script>

    <%--     	    <script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/lang/zh-cn/zh-cn.js"></script> --%>
    <script type="text/javascript" charset="utf-8"
            src="${ basePath }/js/utf8-jsp/third-party/codemirror/codemirror.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="${ basePath }/js/utf8-jsp/third-party/zeroclipboard/ZeroClipboard.js"></script>


</head>
<body>
<input type="hidden" class="meetingPlanStr" value='${meetingPlanStr }'>
<div class="">
    <div class="content_title">
        ${title }
    </div>
    <div class="content_form">
        <form class="form-horizontal" role="form" action="${informPartyUrl }" method="post">
            <div id="hg-form-container" class="form-group">
                <input id="meetingId" type="hidden" name="meetingId"/>
                <input id="groupId" type="hidden" name="groupId"/>
                <input id="startTime" type="hidden" name="startTime"/>
                <input type="hidden" name="formId" value="${formId}"/>
                <input id="meeting_theme_secondary" type="hidden" name="meeting_theme_secondary"/>
                <input id="submit" type="submit" style="display:none;"/>
                <!-- 						<input id="hiddenstate" type="hidden" name="state"/> -->
                <!-- 						<input id="infrom_id" type="hidden" name="infrom_id"/> -->
                <!-- 						<input id="newAndOld" type="hidden" name="newAndOld"/> -->
            </div>
            <div class="btn_group col-sm-12">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="formsubmitgraft()">取消
                </button>
                <button type="button" class="btn btn_main add_sure_btn" style="color:white" onclick="formsubmit()">确定</button>
            </div>
        </form>
    </div>
</div>


<script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.all.js"></script>
<script type="text/javascript">

    $(function () {
        var form = ${design};
        var uploadUrls = {
            file: '${uploadfileUrl}',
            image: '${uploadimageUrl}',
            video: '${uploadvideoUrl}'
        }
        var hgDoms = new HgDoms(form, uploadUrls, 'hg-form-container');
        hgDoms.ueditor.ready(function () {
            hgDoms.ueditor.setContent(j.content);
        });
        
        var rules = {};
        console.log(JSON.stringify(form));
        for(var k in form.columns){
        	var column = form.columns[k];
        	if(column.validation || column.required){
        		var r = {};
        		if(column.validation){
        			var v = column.validateParam;
        			if(v){
             			try{
            				v = eval(v);
            			}catch(e){
            				console.log(e);
            			}
        			}else{
        				v = true;
        			}
        			r[column.validation] = v;
        		}
        		if(column.required){
        			r.required = true;
        		}
        		rules[column.id] = r;
        	}
        }
        var canSubmit = true;
        $("#hg-form-container").parent().validate({             
            submitHandler: function(form) {
            	if(canSubmit){
            		canSubmit = false;
            		form.submit();
                    return false;
            	}
		    },
            rules: rules
        });
        /*  alert("欢迎来到通知页面!"); */
        var meetingPlan = $(".meetingPlanStr").val();
        meetingPlan = meetingPlan.substring(0, meetingPlan.length);
        var j = JSON.parse(meetingPlan);
        console.log(meetingPlan);
        var start = j.start_time;
        /* alert(meetingPlan); */
        var end = j.end_time;
        $("input[name='secondary']").val(j.sname);
        $("input[name='branch']").val(j.sname);
        var orgType = j.orgType;
        if (orgType == "secondary") {
            /* $("#hg-form-container").find(" > div:eq(0) .control-label").css("font-size","12px"); */
            $("#hg-form-container").find(".col-sm-6.col-xs-12").eq(1).css("display", "none");
            $("#hg-form-container").find(".col-sm-6.col-xs-12").eq(0).find(".col-sm-2.col-xs-3.control-label").css("font-size", "12px");
        } else {
            $("#hg-form-container").find(".col-sm-6.col-xs-12").eq(0).css("display", "none");
        }
        $("input[name='type']").val(j.meeting_type);
        $("input[name='theme']").val(j.meeting_theme);
        $("input[name='place']").val(j.place);
        $("input[name='runtime']").val(start.substr(0, 19) + "-" + end.substring(11, 19));
        $("input[name='startTime']").val(start);
        $("input[name='totaltime']").val(j.total_time + " 分钟");
        $("input[name='host']").val(j.host);
        $("input[name='participants']").val(j.group_name);
        $("input[name='contact']").val(j.contact);
        $("input[name='contactnumber']").val(j.contact_phone);
        $("input[name='meetingId']").val(j.meeting_id);
        $("input[name='groupId']").val(j.group_id);
        /*$("select[name='sendpath']").val("站内");*/
        $("input[name='meeting_theme_secondary']").val(j.meeting_theme_secondary);
    });


    /*   function formsubmitgraft(){
           $("#hiddenstate").val(0);
           history.back();
        }  */

    function formsubmitgraft() {
        var seconndaryShow = $("#hg-form-container").find(".col-sm-6.col-xs-12").eq(0).css("display");
        var branchShow = $("#hg-form-container").find(".col-sm-6.col-xs-12").eq(1).css("display");
        if (branchShow == 'none') {
            window.location.href = "/backlogtwo";
        } else {
            window.location.href = "/task";
        }
    }


    function formsubmit() {
    /*    var path = $("select[name='sendpath']").val();
        if (path == null) {
            $.hgConfirm("提示", "请选择发送途径。");
            $("#hg_confirm").modal("show");
            $("#hg_confirm .btn_main").click(function () {
                $("#hg_confirm").modal("hide");
                return;
            });
        } else {*/
           $.hgConfirm("提示", "确定通知吗？");
           $("#hg_confirm").modal("show");
           $("#hg_confirm .btn_main").click(function () {
               $("#hg_confirm").modal("hide");
              /*  $.tip("通知信息发送！"); */
               $('#submit').click();
           });
        
    }


</script>
</body>
</html>