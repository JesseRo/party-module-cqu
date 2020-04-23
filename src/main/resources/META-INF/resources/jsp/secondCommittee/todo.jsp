<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/init.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>二级党委-待办事项</title>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css"/>
    <portlet:resourceURL id="/org/meeting/page" var="OrgMeetingPage" />
    <style type="text/css">
        .content_table thead tr{
            background: #F6F8FC;
            height: 48px;
            font-size: 16px;
        }
        .content_table thead th{
            padding: 5px 15px !important;
        }
        .content_table tr:nth-child(2n) {
            background: #FBFCFE;
        }
        .content_table td{
            min-width: 130px;
            padding: 5px 15px !important;
            height: 48px;
            font-size: 14px;
        }

        .table_outer_box > table thead, tbody tr {
            display: table-row !important;
            width: 100%;
            table-layout: fixed;
        }
        #searchForm .layui-form-item .layui-inline .keyword {
            width: 300px;
            margin-right: 0px;
        }
        #personInfo .layui-form-item .layui-input-inline{
            width:200px
        }
        #personInfo .layui-form-label{
            width:140px;
            font-weight:bold;
        }
        #personInfo .layui-form-label-text{
            float: left;
            display: block;
            padding: 0 10px;
            width: 200px;
            font-weight: 400;
            line-height: 40px;
            font-size: 16px;
            text-align: left;
        }
        th, tr{
            text-align:center !important;
        }
        .layui-table-page-center{
            text-align: center;
        }
    </style>
</head>
<body>

<portlet:renderURL var="showConferenceDetailUrl">
    <portlet:param name="mvcRenderCommandName" value="/hg/showConferenceDetail"/>
</portlet:renderURL>

<portlet:renderURL var="submitPlanUrl">
    <portlet:param name="mvcRenderCommandName" value="/hg/SubmitPlan"/>
</portlet:renderURL>

<portlet:actionURL name="/hg/postSubmissions" var="submitForm"/>
<portlet:resourceURL id="/hg/taskCancle" var="taskCancle"/>
<portlet:resourceURL id="/secondary/loadBranch" var="loadBranchUrl"/>
<portlet:resourceURL id="/secondary/resend" var="resendUrl"/>
<portlet:resourceURL id="/hg/taskCheckReplyState" var="taskCheckReplyState"/>


<portlet:renderURL var="showCommentDetailUrl">
    <portlet:param name="mvcRenderCommandName" value="/hg/showCommentDetail"/>
</portlet:renderURL>
<portlet:renderURL var="showExperienceDetailUrl">
    <portlet:param name="mvcRenderCommandName" value="/hg/showExperienceDetail"/>
</portlet:renderURL>

<div class="table_form_content">
    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group" style="margin-bottom: 20px;">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">组织生活管理</a>
                        <a href="javascript:;">已发计划</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <form class="layui-form" id="searchForm">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <div class="layui-input-inline keyword">
                            <input type="text" name="keyword"  placeholder="请输入姓名、组织、主题关键字" class="layui-input">
                        </div>
                        <button type="button"  class="layui-btn layui-btn-warm"  lay-submit="" lay-filter="searchForm"><icon class="layui-icon layui-icon-search"></icon>搜索</button>
                    </div>
                </div>
            </form>
            <table id="meetingPlanTable" lay-filter="meetingPlanTable"></table>
        </div>
    </div>
</div>


<!-- 模态框（Modal） -->
<div class="modal fade" id="transmit" tabindex="-1" role="dialog" aria-labelledby="transmitLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="transmitLabel">部署</h4>
            </div>
            <div class="modal-body">
                <div class="col-sm-12 col-xs-12 publish_obj_container">
                    <div class="col-sm-12 col-xs-12 publish_obj">
                        <div class="publish_obj_content">
                            <div class="publish_obj_title">
                                所辖党支部
                                <div class="right">
                                    <div class="select_choice all_select">
                                        <img src="${basePath }/images/not_check_icon.png"/>
                                        <input type="hidden"/>
                                        <span>全选</span>
                                    </div>
                                    <div class="select_choice oppsite_select">
                                        <img src="${basePath }/images/not_check_icon.png"/>
                                        <input type="hidden"/>
                                        <span>反选</span>
                                    </div>
                                </div>

                            </div>
                            <div class="publish_obj_info container_scroll_hidden">
                                <ul class="list-group col-sm-12 col-xs-12" id="branch-list">

                                </ul>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="cancel_resend">取消</button>
                <button type="button" class="btn btn_main" id="resend">部署</button>
            </div>
        </div>
    </div>
</div>


<!--撤回弹窗  -->
<!-- 撤回模态框 -->
<!--     <a href="javascript:;" data-toggle="modal" data-target="#recall">撤回</a> -->
<!-- 模态框（Modal） -->
<div class="modal fade" id="recall" tabindex="-1" role="dialog" aria-labelledby="recallLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="recallLabel">撤回</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <div class="col-sm-12 col-xs-12">
                            请确认撤回该计划？
                            <input type="hidden" class="form-control reasonContent" placeholder="请输入撤回理由"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn_main">确定</button>
            </div>
        </div>
    </div>
</div>


<!-- 查看模态框 -->
<!--  <a href="javascript:;" data-toggle="modal" data-target="#check">查看已读回执</a> -->
<!-- 模态框（Modal） -->
<div class="modal fade check" id="check" tabindex="-1" role="dialog" aria-labelledby="checkLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="checkLabel">已读回执</h4>
            </div>
            <div class="modal-body">
                <table class="col-sm-12 col-xs-12 replaytable">
                    <thead>
                    <tr>
                        <th>姓名</th>
                        <th class="select_state">
                            <!--  <select class="select_state">
                                 <option value="allState">已读/未读</option>
                                 <option value="read">已读</option>
                                 <option value="noRead">未读</option>
                             </select>-->
                            <input class="reply_infromId" type="hidden">
                            <input class="reply_orgId" type="hidden">

                            通知
                        </th>
                        <!--                                 <th>评分</th> -->
                        <th>是否上传心得</th>
                    </tr>
                    </thead>
                    <tbody class="tbody_reply">

                    </tbody>

                </table>
            </div>
        </div>
    </div>
</div>


</body>
<script type="text/html" id="meetingPlanTableBtns">
    {{#  if(d.task_status == "1"){ }}
    <%--<a class="layui-btn layui-btn-xs" lay-event="check">审核</a>--%>
    <a class="layui-btn layui-btn-xs" onclick="Pass('${d.meeting_id }');">
        通过</a>
    <a  class="layui-btn layui-btn-xs" onclick="entry('${d.meeting_id }');">
        驳回</a>
    {{#  } }}
    {{#  if(d.task_status == '4' || d.task_status == '5' || d.task_status == '6'){ }}
    <a class="layui-btn layui-btn-xs" href="/sendplan?meetingId=${d.meeting_id }&orgType=secondary&type=edit"> 编辑</a>
    <%--<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>--%>
    {{#  } }}
    <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
</script>
<script>
    layui.use(['table','layer','form'], function() {
        var table = layui.table,
            layer = layui.layer,
            form = layui.form;
        renderTable();
        form.on('submit(searchForm)', function (data) {
            renderTable();
        })
        function renderTable(){
            var  where = {
                keyword: $("#searchForm input[name=keyword]").val()
            };
            table.render({
                elem: '#meetingPlanTable',
                where: where,
                height:560,
                url: '${OrgMeetingPage}', //数据接口
                method: 'post',
                page: {
                    limit:10,   //每页条数
                    limits:[10,15,20],
                    prev:'&lt;上一页',
                    next:'下一页&gt;',
                    theme: '#FFB800',
                    groups:4
                },
                cols: [[ //表头
                    {field: 'meeting_type', align:'center', title: '活动类型'},
                    {field: 'meeting_theme', align:'center',width:320, title: '活动主题',templet:function(d){
                            return '<a  href="/approvaldetails?meetingId='+d.meeting_id+'&orgType=secondary">'+d.meeting_theme+'</a>';
                        }},
                    {field: 'start_time', align:'center', title: '开始时间',width:180,templet: function(d){return new Date(d.start_time).format("yyyy-MM-dd hh:mm:ss");}},
                    {field: 'total_time', align:'center', title: '时长',width:100,templet: function(d){return d.total_time/60;}},
                    {field: 'member_name', align:'center', title: '联系人'},
                    {field: 'task_status', align:'center', title: '任务状态',width:120,templet: function(d){
                            var status = '';
                            switch(parseInt(d.task_status)){
                                case 1:status = '待提交';break;
                                case 1:status = '待审核';break;
                                case 2:status = '已撤回';break;
                                case 3:status = '已驳回';break;
                                case 4:status = '已通过';break;
                                case 5:status = '已指派';break;
                                case 6:status = '未检查';break;
                                case 7:status = '已检查';break;
                            }
                            return status;
                        }},
                    {field: 'operation', align:'center', title: '操作',width:200,toolbar: '#meetingPlanTableBtns'},

                ]]
            });
            $(".layui-table-view .layui-table-page").addClass("layui-table-page-center");
            $(".layui-table-view .layui-table-page").removeClass("layui-table-page");
            //监听事件
            table.on('tool(meetingPlanTable)', function(obj){
                switch(obj.event){
                    case 'check':
                        //renderDetail('check',obj);
                        break;
                    case 'detail':
                        // renderDetail('check',obj);
                        break;
                };
            });
        }
    });
    Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
    /*常用人员 添加*/
    $(".add_btn").on("click", ">.btn", function () {
        $(this).siblings("form").css("display", "inline-block");
    });

    /*取消添加弹窗*/
    $(".add_btn").on("click", ".form_hide_btn", function () {
        $(this).parents("form").css("display", "none");
        $(this).parents(".form-group").find(".add_member_input").val("");
    });

    /*转派党支部*/
    $('.load-branch').on('click', function () {
        var informId = $(this).parent().parent().attr('id');
        $.post("${loadBranchUrl}", {}, function (res) {
            if (res.result) {
                $('#branch-list').attr('informId', informId);
                var branchLi = "<li class=\"list-group-item col-sm-6 col-xs-12\">" +
                    "<span id=\"{{branchId}}\" title=\"马克思主义学院党委\">{{branchName}}</span>" +
                    "<input type=\"hidden\" />" +
                    "<img class=\"right\" src=\"${basePath }/images/radio.png\" />" +
                    "</li>";
                var html = '';
                for (var i in res.data) {
                    var branch = res.data[i];
                    html += branchLi.replace("{{branchId}}", branch.org_id).replace("{{branchName}}", branch.org_name);
                }

                $('#branch-list').html(html);
            } else {
                alert(res.message);
            }
        });
    });

    /*转派*/
    $('#resend').on('click', function () {
        var selected = [];
        $('.publish_obj_info li').each(function () {
            var branch = $(this);
            var branchId = branch.find('input').val();
            if (branchId === branch.find('span').attr('id')) {
                selected.push(branchId);
            }
        });
        if (selected.length > 0) {
            var informId = $('#branch-list').attr('informId');
            $.post("${resendUrl}", {branches: selected.join(','), informId: informId}, function (res) {
                if (res.result) {
                    alert("已指派");
                    $('.load-branch').parent().html('<a href="/branchview?inform_id="' + informId + '>查看进度</a>	');


                    $('#cancel_resend').click();
                } else {
                    alert(res.message);
                }
            });
        }
    });

    /*sss*/
    $(".publish_obj_info").on("click", "li", function () {
        $(".publish_obj_content .select_choice").find("img").attr("src", "${basePath}/images/not_check_icon.png");
        var _text = $(this).find("span").attr('id');
        var oldSrc = $(this).find(".right").attr("src");
        var newSrc = "";
        if (oldSrc.indexOf("on") > 0) {
            newSrc = oldSrc.replace(/_on.png/, ".png");
            $(this).find("input").val("");
        } else {
            newSrc = oldSrc.replace(/.png/, "_on.png");
            $(this).find("input").val(_text);
        }
        $(this).find(".right").attr("src", newSrc);
        var sum = $(".publish_obj_info").find("img.right").length;
        var imgNum = $(".publish_obj_info").find("img[src='${basePath}/images/radio_on.png']").length;
        if (sum == imgNum) {
            $(".all_select").find("img").attr("src", "${basePath}/images/checked_icon.png");
            $(".oppsite_select").find("img").attr("src", "${basePath}/images/not_check_icon.png");
        } else {
            $(".all_select").find("img").attr("src", "${basePath}/images/not_check_icon.png");
        }
    });


    /*二级党委全选*/
    $(".all_select").click(function () {
        $(this).find("img").attr("src", "${basePath}/images/checked_icon.png");
        $(this).siblings(".oppsite_select").find("img").attr("src", "${basePath}/images/not_check_icon.png");
        $(".publish_obj_info").find("img.right").each(function () {
            $(this).attr("src", "${basePath}/images/radio_on.png");
        });
    });

    /*二级党委反选*/
    $(".oppsite_select").click(function () {
        $(this).find("img").attr("src", "${basePath}/images/checked_icon.png");
        $(this).siblings(".all_select").find("img").attr("src", "${basePath}/images/not_check_icon.png");
        $(".publish_obj_info").find("img.right").each(function () {
            var Src = $(this).attr("src");
            if (Src.indexOf("on") > 0) {
                $(this).attr("src", "${basePath}/images/radio.png");
            } else {
                $(this).attr("src", "${basePath}/images/radio_on.png");
            }
        });
    });


    function typeStatusAjax() {
        $("input[name='pageNo']").val(1);
        $("#mt").val($("#meetingType").val());
        $("#ts").val($("#taskStatus").val());
        $("#getPageNo").submit();
    }


    /*撤回*/
    $(".table_info").on("click", ".cancl_reson", function () {
        $(".table_info").find(".cancl_reson_value").removeClass("cancl_reson_value");
        $(this).addClass("cancl_reson_value");
        $("#recall").modal("show");
    });

    /*撤回确定按钮*/
    $(".btn_main").click(function () {
        var infrom_id = $(".table_info").find(".cancl_reson_value").next().val();
        var org_id = $(".table_info").find(".cancl_reson_value").next().next().val();
        var url = "${taskCancle}";
        var reasonContent = $(".reasonContent").val();
        $.ajax({
            url: url,
            data: {infromId: infrom_id, orgId: org_id, cancleReson: reasonContent},
            dataType: "text",
            success: function (succee) {
                console.log(succee);
                if ("SUCCEE" == succee) {
                    window.location.reload();
                }
            }
        });
    });


    /*查看*/
    $(".table_info").on("click", ".check_reply_state", function () {
        $("#check").modal("show");
        var infromId = $(this).next().val();
        var orgId = $(this).next().next().val();
        $(".reply_infromId").val(infromId);
        $(".reply_orgId").val(orgId);
        var url = "${taskCheckReplyState}";
        /* var url1="


        ${showCommentDetailUrl}"; */
        var url1 = "/showevaluation?meet=1";
        var url2 = "${showExperienceDetailUrl}";

        $.ajax({
            url: url,
            data: {infromId: infromId, orgId: orgId, path: "allState"},
            dataType: "json",
            async: false,
            success: function (data) {
                $(".tbody_reply").empty();
                for (var i = 0; i < data.length; i++) {
                    var c = data[i];
                    if (!c.user_name) {
                        continue;
                    }
                    if (c.check_status == '未读') {
                        if (c.comments_state == 1) {
                            if (c.upload_state == 1) {
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>已通知</td> <td><a class="seeScoreDetail" href="' + url1 + '&meetingId=' + c.meeting_id + '&userId=' + c.user_id + '">' + c.comments_score + '分</a></td><td><a class="seeExperienceDetail" href="' + url2 + '&meetingId=' + c.meeting_id + '&userId=' + c.user_id + '">是</a></td> </tr>');
                            } else {
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>已通知</td> <td><a class="seeScoreDetail" href="' + url1 + '&meetingId=' + c.meeting_id + '&userId=' + c.user_id + '">' + c.comments_score + '分</a></td><td>否</td> </tr>');
                            }
                        } else {
                            if (c.upload_state == 1) {
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>已通知</td> <td><a class="seeExperienceDetail" href="' + url2 + '&meetingId=' + c.meeting_id + '&userId=' + c.user_id + '">是</a></td> </tr>');
                            } else {
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>已通知</td> <td>否</td> </tr>');
                            }
                        }
                    } else {
                        if (c.comments_state == 1) {
                            if (c.upload_state == 1) {
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>已通知</td> <td><a class="seeScoreDetail" href="' + url1 + '&meetingId=' + c.meeting_id + '&userId=' + c.user_id + '">' + c.comments_score + '分</a></td><td><a class="seeExperienceDetail" href="' + url2 + '&meetingId=' + c.meeting_id + '&userId=' + c.user_id + '">是</a></td> </tr>');
                            } else {
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>已通知</td> <td ><a class="seeScoreDetail" href="' + url1 + '&meetingId=' + c.meeting_id + '&userId=' + c.user_id + '">' + c.comments_score + '分</a></td><td>否</td> </tr>');
                            }
                        } else {
                            if (c.upload_state == 1) {
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>已通知</td> <td><a class="seeExperienceDetail" href="' + url2 + '&meetingId=' + c.meeting_id + '&userId=' + c.user_id + '">是</a></td> </tr>');
                            } else {
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>已通知</td> <td>否</td> </tr>');
                            }
                        }
                    }

                    /*  $(".seeExperienceDetail").attr("href",url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id);
                     $(".seeScoreDetail").attr("href",url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id);
                      */

                }
            }
        });
    });


    /*sss*/
    $(".select_state").change(function () {
        var state = $(".select_state").val();
        var infromId = $(".reply_infromId").val();
        var orgId = $(".reply_orgId").val();
        var url = "${taskCheckReplyState}";
        /* var url1="


        ${showCommentDetailUrl}"; */
        var url1 = "/showevaluation?meet=1";
        var url2 = "${showExperienceDetailUrl}";
        $.ajax({
            url: url,
            data: {infromId: infromId, orgId: orgId, state: state, path: "find"},
            dataType: "json",
            success: function (data) {
                $(".tbody_reply").empty();
                for (var i = 0; i < data.length; i++) {
                    var c = data[i];
                    if (!c.user_name) {
                        continue;
                    }
                    if (c.check_status == '未读') {
                        if (c.comments_state == 1) {
                            if (c.upload_state == 1) {
                                /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td><span class="score">'+ c.comments_score +'分</span></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>未读</td> <td><a class="seeExperienceDetail" href="' + url2 + '&meetingId=' + c.meeting_id + '&userId=' + c.user_id + '">是</a></td> </tr>');
                            } else {
                                /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td>否</td> </tr>') ;  */
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>未读</td> <td>否</td> </tr>');
                            }
                        } else {
                            if (c.upload_state == 1) {
                                /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>未读</td> <td><a class="seeExperienceDetail" href="' + url2 + '&meetingId=' + c.meeting_id + '&userId=' + c.user_id + '">是</a></td> </tr>');
                            } else {
                                /* $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>未读</td> <td></td><td>否</td> </tr>') ;  */
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>未读</td> <td>否</td> </tr>');
                            }
                        }
                    } else {
                        if (c.comments_state == 1) {
                            if (c.upload_state == 1) {
                                /*   $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>已读</td> <td><a class="seeExperienceDetail" href="' + url2 + '&meetingId=' + c.meeting_id + '&userId=' + c.user_id + '">是</a></td> </tr>');
                            } else {
                                /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td ><a class="seeScoreDetail" href="'+ url1 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">'+ c.comments_score +'分</a></td><td>否</td> </tr>') ;  */
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>已读</td> <td>否</td> </tr>');
                            }
                        } else {
                            if (c.upload_state == 1) {
                                /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td></td><td><a class="seeExperienceDetail" href="'+ url2 +'&meetingId='+ c.meeting_id +'&userId='+ c.user_id+'">是</a></td> </tr>') ;  */
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>已读</td> <td><a class="seeExperienceDetail" href="' + url2 + '&meetingId=' + c.meeting_id + '&userId=' + c.user_id + '">是</a></td> </tr>');
                            } else {
                                /*  $(".tbody_reply").append('<tr> <td>'+c.user_name+'</td> <td>已读</td> <td></td><td>否</td> </tr>') ;  */
                                $(".tbody_reply").append('<tr> <td>' + c.user_name + '</td> <td>已读</td> <td>否</td> </tr>');
                            }
                        }
                    }
                }
            }
        });
    });


    $(".seeScoreDetail").click(function () {
        $("#check").modal("hide");
    });


    $(".seeExperienceDetail").click(function () {
        $("#check").modal("hide");
    });
</script>

</html>