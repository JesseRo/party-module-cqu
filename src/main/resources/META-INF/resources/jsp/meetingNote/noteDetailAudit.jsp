<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<portlet:resourceURL id="/org/meetingNote/audit" var="meetingNoteAudit"/>
<html>
<head>
    <style type="text/css">
        .layui-form-label-text {
            float: left;
            display: block;
            padding: 0 10px;
            width: 260px;
            font-weight: 400;
            line-height: 40px;
            font-size: 16px;
            text-align: left;
        }

        .inform-detail .layui-form-item {
            margin-bottom: 0px;
        }

        .inform-detail .layui-inline {
            margin-bottom: 0px;
        }

        .inform-detail .layui-form-label {
            font-weight: bold;
        }

        .layui-form-item .member-list {
            width: 480px;
        }

        .layui-form-item .layui-long {
            width: 620px;
        }

        .layui-card-body {
            height: 200px;
            overflow-y: scroll;
        }

        #rejectModal .layui-form-label {
            float: left;
            display: block;
            padding: 9px 0px;
            font-weight: 400;
            line-height: 20px;
            text-align: left;
        }
    </style>
</head>
<body>
<div class="table_form_content">
    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">组织生活管理</a>
                        <a href="javascript:;">审批会议纪要</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <div class="content_form form_container">
                <div class="form_container">
                    <div class="layui-form custom_form" method="post">
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
                                <div class="layui-input-inline">
                                    <label class="layui-form-label-text">${meeting.meeting_theme }</label>
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">开展时间：</label>
                                <div class="layui-input-inline">
                                    <label class="layui-form-label-text"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${meeting.start_time }" /></label>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">地点：</label>
                                <div class="layui-input-inline">
                                    <label class="layui-form-label-text">${meeting.place_name }</label>
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">主持人：</label>
                                <div class="layui-input-inline">
                                    <label class="layui-form-label-text">${meeting.host_name }</label>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">联系委员：</label>
                                <div class="layui-input-inline">
                                    <label class="layui-form-label-text">${meeting.sit }</label>
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">联系人：</label>
                                <div class="layui-input-inline">
                                    <label class="layui-form-label-text">${meeting.contact_name }</label>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">联系电话：</label>
                                <div class="layui-input-inline layui-long">
                                    <label class="layui-form-label-text">${meeting.contact_phone }</label>
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
                                <div class="layui-input-inline  layui-form-label-text layui-long">
                                    ${meetingNote.attachment }
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline btn_group">
                                <label class="layui-form-label"></label>
                                <div class="layui-input-inline layui-long">
                                    <c:if test="${permission =='audit'}">
                                        <%-- <button type="button" class="layui-btn layui-btn-warm" onclick="pass('${meetingNote.id}')">通过</button>--%>
                                        <button type="button" class="layui-btn layui-btn-warm"
                                                onclick="reject('${meetingNote.id}')">驳回
                                        </button>
                                    </c:if>
                                    <button type="button" class="layui-btn layui-btn-primary"
                                            onclick="window.history.back();">返回
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div style="display: none" id="rejectModal">
        <form class="layui-form" action="">
            <input type="hidden" class="layui-layer-input" name="meetingId" value="1">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label layui-required">驳回理由:</label>
                    <div class="layui-input-inline">
                        <input type="text" class="layui-input" name="rejectReason" id="rejectReason">
                    </div>
                </div>
            </div>

            <div class="layui-layer-btn layui-layer-btn-">
                <a class="layui-layer-btn0" type="button" lay-submit="" lay-filter="rejectForm">确定</a>
                <a class="layui-layer-btn1">取消</a>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    var layer;
    var rejectId;
    layui.use(['layer', 'form'], function () {
        layer = layui.layer;
        var form = layui.form;
        form.on('submit(rejectForm)', function (data) {
            var url = "${meetingNoteAudit}";
            $.ajax({
                url: url,
                data: {noteId: rejectId, isPass: false, rejectReason: data.field.rejectReason},
                dataType: 'json',
                async: false,
                success: function (res) {
                    if (res && res.code == 200) {
                        layer.msg("驳回成功。");
                        setTimeout(function () {
                            window.history.back();
                        }, 1000);
                    } else {
                        layer.msg("驳回失败。");
                    }
                }
            });

        })
    });

    function pass(noteId) {
        layer.confirm('确认通过？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                url: "${meetingNoteAudit}",
                data: {noteId: noteId, isPass: true},
                dataType: 'json',
                success: function (res) {
                    if (res && res.code == 200) {
                        layer.msg("审核成功。");
                        setTimeout(function () {
                            window.history.back();
                        }, 1000);
                    } else {
                        layer.msg("审核失败。");
                    }
                }
            });
        });
    }

    function reject(noteId) {
        rejectId = noteId;
        layer.prompt({
            type: 1,
            btn: 0,
            content: $("#rejectModal")
        });
    }
</script>
</body>


</html>
