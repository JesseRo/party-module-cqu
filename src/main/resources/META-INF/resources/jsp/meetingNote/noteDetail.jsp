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
                    <div class="layui-form custom_form inform-detail" style="width: 900px;" method="post">
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
                                    <label class="layui-form-label-text" style="width: 100%;">${meeting.meeting_theme }</label>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">开展时间：</label>
                                <div class="layui-input-inline">
                                    <label class="layui-form-label-text"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${meeting.start_time }" /></label>
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">地点：</label>
                                <div class="layui-input-inline">
                                    <label class="layui-form-label-text">${meeting.place_name }</label>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">主持人：</label>
                                <div class="layui-input-inline">
                                    <label class="layui-form-label-text">${meeting.host_name }</label>
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">联系委员：</label>
                                <div class="layui-input-inline">
                                    <label class="layui-form-label-text">${meeting.sit }</label>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">联系人：</label>
                                <div class="layui-input-inline">
                                    <label class="layui-form-label-text">${meeting.contact_name }</label>
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">联系电话：</label>
                                <div class="layui-input-inline">
                                    <label class="layui-form-label-text">${meeting.contact_phone }</label>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">应到人员：</label>
                                <div class="layui-input-inline layui-long">
                                    <label class="layui-form-label-text" style="word-wrap: break-word; width: 100%;">
                                        ${participants}
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">实到人员：</label>
                                <div class="layui-input-inline layui-long">
                                    <label class="layui-form-label-text" style="word-wrap: break-word; width: 100%;">
                                        ${memberList}
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline ueditor_container">
                                <label class="layui-form-label">计划内容：</label>
                                <div class="layui-input-inline layui-form-label-text layui-long">
                                    ${meeting.content }
                                </div>
                            </div>
                        </div>
<%--                        <div class="layui-form-item">--%>
<%--                            <div class="layui-inline ueditor_container">--%>
<%--                                <div class="layui-input-inline layui-form-label-text layui-long">--%>
<%--                                    ${meeting.content }--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
                        <div class="layui-form-item">
                            <div class="layui-inline ueditor_container">
                                <label class="layui-form-label">会议纪要：</label>
                                <div class="layui-input-inline layui-form-label-text layui-long">
                                    ${meetingNote.attachment }
                                </div>
                            </div>
                        </div>
<%--                        <div class="layui-form-item">--%>
<%--                            <div class="layui-inline ueditor_container">--%>
<%--                                <div class="layui-input-inline layui-form-label-text layui-long">--%>
<%--                                    ${meetingNote.attachment }--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
                        <div class="layui-form-item">
                            <div class="layui-inline btn_group">
                                <label class="layui-form-label"></label>
                                <div class="layui-input-inline">
                                    <button type="button" class="layui-btn layui-btn-primary" onclick="window.history.back();">返回</button>
                                    <button type="button" class="layui-btn layui-btn-warm" id="pdf_button">导出pdf</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${basePath}/js/html2canvas.js?v=1"></script>
<script type="text/javascript" src="${basePath}/js/jspdf.umd.min.js"></script>
<script>
    $(function () {
        var pdf = new jspdf.jsPDF('', 'pt', 'a4');

        $('.ueditor_container').find('img').each(function (i, e) {
            var url = $(e).attr('src');
            $(e).attr("width", 500);
            if (url.indexOf(document.location.host) !== -1) {
                return;
            }
            $(e).attr("src", 'http://' + document.location.hostname + ':9007/proxy?target=' + encodeURIComponent(url));
        });

        function renderPdf(children, i, lastHeight, currentPageHeight) {
            html2canvas(children[i], {useCORS: true}).then(canvas => {
                var contentWidth = canvas.width;
                var contentHeight = canvas.height;
                var pageHeight = contentWidth / 492.28 * 741.89;
                var nextPageHeight = currentPageHeight;
                //一页pdf显示html页面生成的canvas高度;
                if (contentHeight > 0) {
                    if (currentPageHeight < lastHeight + contentHeight) {
                        if (i > 0) {
                            pdf.addPage();
                        }
                        pdf.addImage(canvas.toDataURL('image/jpeg', 1.0), 'JPEG', 50, 50, 495.28, (contentHeight / contentWidth) * 495.28);
                        nextPageHeight += pageHeight;
                        lastHeight = currentPageHeight + contentHeight;
                    } else {
                        pdf.addImage(canvas.toDataURL('image/jpeg', 1.0), 'JPEG', 50, 50 + ((lastHeight - currentPageHeight + pageHeight) / contentWidth) * 495.28, 495.28, (contentHeight / contentWidth) * 495.28);
                        lastHeight += contentHeight;
                    }
                }
                if (i + 1 < children.length) {
                    renderPdf(children, i + 1, lastHeight, nextPageHeight)
                } else {
                    pdf.save('stone.pdf');
                }
            });
        }


        $('#pdf_button').on('click', function () {
            var children = $('.inform-detail').children();
            var doms = [];
            for (var i = 0; i < children.length - 1; i++) {
                doms.push(children[i])
            }
            renderPdf(doms, 0, 0, 0);
        })
    })
</script>

</body>


</html>
