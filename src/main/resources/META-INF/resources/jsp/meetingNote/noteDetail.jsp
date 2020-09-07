<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <style type="text/css">
        .layui-form-label-text{
            float: left;
            display: block;
            padding: 0 10px;
            width: 260px;
            font-weight: 400;
            line-height: 40px;
            font-size: 16px;
            text-align: left;
        }
        .inform-detail .layui-form-item{
            margin-bottom: 0px;
        }
        .inform-detail .layui-inline{
            margin-bottom: 0px;
        }
        .inform-detail .layui-form-label{
            font-weight: bold;
        }
        .layui-form-item .layui-input-inline{
            width:300px;
        }
        .layui-form-item .member-list{
            width:480px;
        }
        .layui-form-item .layui-long{
            width:620px;
        }
        .layui-card-body{
            height: 200px;
            overflow-y: scroll;
        }
    </style>
</head>
<body>
<div class="table_form_content ">
    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">组织生活管理</a>
                        <a href="javascript:;">会议纪要详情</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <div class="content_form form_container">
                <div class="layui-card">
                    <div class="layui-card-header">审批状态：
                        <c:if test="${meetingNote.status == 1}">
                            <b>待审核</b>
                        </c:if>
                        <c:if test="${meetingNote.status == 2}">
                            <b>已通过</b>
                        </c:if>
                        <c:if test="${meetingNote.status == 3}">
                            <b>未通过(原因：${meetingNote.reason})</b>
                        </c:if>
                    </div>
                </div>
                <div class="form_container">
                    <div class="layui-form custom_form inform-detail"  method="post">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <input type="hidden" name="meetingNoteId" value="${meetingNote.id}">
                                <label class="layui-form-label">党组织：</label>
                                <div class="layui-input-inline">
                                    <label class="layui-form-label-text">${meeting.sname }</label>
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">会议类型：</label>
                                <div class="layui-input-inline">
                                    <label class="layui-form-label-text">${meeting.meeting_type }</label>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">会议主题：</label>
                                <div class="layui-input-inline layui-long">
                                    <label class="layui-form-label-text">${meeting.meeting_theme }</label>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">会议考勤：</label>
                                <div class="layui-input-inline">
                                    <div class="layui-row member-list">
                                        <div class="layui-col-md6">
                                            <div class="layui-card">
                                                <div class="layui-card-header">应到人员</div>
                                                <div class="layui-card-body">
                                                    <ul>
                                                    <c:forEach var="m" items="${participants }">
                                                        <li>${m.member_name}</li>
                                                    </c:forEach>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="layui-col-md6">
                                            <div class="layui-card">
                                                <div class="layui-card-header">实到人员</div>
                                                <div class="layui-card-body">
                                                    <ul>
                                                        <c:forEach var="m" items="${memberList }">
                                                            <li>${m.member_name}</li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline ueditor_container">
                                <label class="layui-form-label">会议纪要：</label>
                                <div class="layui-input-inline layui-form-label-text layui-long">
                                    ${meetingNote.attachment }
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline btn_group">
                                <label class="layui-form-label"></label>
                                <div class="layui-input-inline">
                                    <button type="button" class="layui-btn layui-btn-primary" onclick="window.history.back();">返回</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>


</html>
