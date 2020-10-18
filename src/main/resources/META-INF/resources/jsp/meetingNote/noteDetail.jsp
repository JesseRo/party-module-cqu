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
                                <label class="layui-form-label">计划内容：</label>
                                <div class="layui-input-inline layui-form-label-text layui-long">
                                    ${meeting.content }
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
<script type="text/javascript" src="${basePath}/js/html2canvas.js?v=1"></script>
<script type="text/javascript" src="${basePath}/js/jspdf.umd.min.js"></script>
<script>
    $(function () {
        var pdf = new jspdf.jsPDF('', 'pt', 'a4');
        setTimeout(function() {
            // html2canvas($('.content_form .form_container')[0], {
            //     userCORS: true
            // }).then(function(canvas) {
                // var contentWidth = canvas.width;
                // var contentHeight = canvas.height;
                //
                // //一页pdf显示html页面生成的canvas高度;
                // var pageHeight = contentWidth / 592.28 * 841.89;
                // //未生成pdf的html页面高度
                // var leftHeight = contentHeight;
                // //页面偏移
                // var position = 0;
                // //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
                // var imgWidth = 595.28;
                // var imgHeight = 592.28/contentWidth * contentHeight;
                //
                // var pageData = canvas.toDataURL('image/jpeg', 1.0);
                //
                // //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
                // //当内容未超过pdf一页显示的范围，无需分页
                // if (leftHeight < pageHeight) {
                //     pdf.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight );
                // } else {
                //     while(leftHeight > 0) {
                //         pdf.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
                //         leftHeight -= pageHeight;
                //         position -= 841.89;
                //         //避免添加空白页
                //         if(leftHeight > 0) {
                //             pdf.addPage();
                //         }
                //     }
                // }
                //
                // pdf.save('content.pdf');

            html2canvas($('.content_form .form_container')[0], {
                userCORS: true
            }).then(canvas => {
                document.body.appendChild(canvas);
                //返回图片dataURL，参数：图片格式和清晰度(0-1)
                var pageData = canvas.toDataURL('image/jpeg', 1.0);
                //方向默认竖直，尺寸ponits，格式a4[595.28,841.89]
                //addImage后两个参数控制添加图片的尺寸，此处将页面高度按照a4纸宽高比列进行压缩
                pdf.addImage(pageData, 'JPEG', 0, 0, 595.28, 592.28/canvas.width * canvas.height );
                pdf.save('stone.pdf');
            });
        }, 1000);
    })
</script>

</body>


</html>
