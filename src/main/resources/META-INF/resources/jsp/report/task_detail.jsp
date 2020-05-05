<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>


<portlet:resourceURL id="/hg/getPublicObject" var="getPublicObject"/>

<portlet:resourceURL id="/form/uploadImage" var="uploadimageUrl"/>
<!-- 视频上传 -->
<portlet:resourceURL id="/form/uploadVideo" var="uploadvideoUrl"/>
<!-- 附件上传 -->
<portlet:resourceURL id="/form/uploadFile" var="uploadfileUrl"/>

<portlet:actionURL name="/secondary/task/add" var="add"/>


<html>
<head>

    <style type="text/css">

        input#button1 {
            float: right;
        }

        textarea {
            width: 100%;
            height: 100px;
        }

        input#button2 {
            float: left;
        }

        /* 常用人员弹窗样式 */
        #commonStaff thead {
            border-bottom: 1px solid #d8d8d8;
            background: #f5f5f5;
        }

        #commonStaff .modal-body {
            max-height: 400px;
        }

        #commonStaff .common_list {
            border-right: none;
        }

        #commonStaff .common_list tr td {
            cursor: pointer;
        }

        #commonStaff .common_list tr td:nth-child(1) {
            cursor: default;
        }

        #commonStaff table td {
            font-size: 13px;
        }

        #commonStaff th,
        #commonStaff .common_list td {
            border: none;
        }

        #commonStaff .modal-body > div {
            padding: 0;
        }

        #commonStaff .modal-body > div table {
            width: 100%;
        }

        #commonStaff .add_btn {
            height: auto;
            background: #f5f5f5;
            border: 1px solid #d8d8d8;
            border-top: none;
        }

        #commonStaff .add_btn button {
            margin: 15px;
        }

        #commonStaff .add_btn img {
            margin-right: 5px;
        }

        .table_container {
            height: 200px;
            overflow-y: auto;
            border: 1px solid #d8d8d8;
        }

        .common_list_container .table_container {
            border-right: none;
        }

        #commonStaff .table_title {
            text-align: center;
        }

        .add_btn form {
            width: 80%;
            background: #fff;
            border: 1px solid #d8d8d8;
            border-radius: 6px;
            padding: 10px 5px;
            padding-bottom: 0;
        }

        #commonStaff .add_btn form {
            margin: 0 0 10px 10px;
            display: none;
        }

        #commonStaff .add_btn form .btn {
            margin: 0;
        }

        #commonStaff .form-group {
            margin-bottom: 0;
        }

        .form_btn {
            text-align: right;
        }

        .common_member_list tr td:nth-child(1) {
            border-left: none;
        }

        .common_member_list tr td:nth-child(2) {
            border-right: none;
        }

        .no-padding {
            padding-left: 0px;
            padding-right: 0px;
            margin-bottom: 20px;
        }

        .right {
            float: right;
        }
    </style>
    <link rel="stylesheet" href="${basePath}/css/publish.css"/>
    <script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${basePath}/js/form.js?version=5"></script>
    <script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.config-thumb.js?v=3"></script>
    <script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.all.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/lang/zh-cn/zh-cn.js"></script>
    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/third-party/codemirror/codemirror.css"/>
    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/themes/iframe.css"/>
    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/themes/default/css/ueditor.css"/>
    <script type="text/javascript" charset="utf-8"
            src="${ basePath }/js/utf8-jsp/third-party/codemirror/codemirror.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="${ basePath }/js/utf8-jsp/third-party/zeroclipboard/ZeroClipboard.js"></script>
    <script type="text/javascript" src="${basePath}/js/jquery-validation.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/validation-message-zh.js?v=2"></script>
    <script type="text/javascript">
        $(function () {
            var ueObj = UE.getEditor("task_content", {initialFrameWidth: 821});
            var uploadUrls = {
                file: '${uploadfileUrl}',
                image: '${uploadimageUrl}',
                video: '${uploadvideoUrl}'
            }
            UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
            UE.Editor.prototype.getActionUrl = function (action) {
                if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
                    return uploadUrls.image;
                } else if (action == 'uploadvideo') {//视频
                    return uploadUrls.video;
                } else if (action == 'uploadfile') {//附件
                    return uploadUrls.file;
                } else {
                    return this._bkGetActionUrl.call(this, action);
                }
            };
            ueObj.ready(function () {
                ueObj.setContent($(".informationContent").val());
                ueObj.setDisabled();
            });
            $('#send').on('click', function () {
                submit();
            });
            $('#draft').on('click', function () {
                draftSubmit();
            });

            function draftSubmit() {
                var imgs = $(".table_info img[src='/images/radio_on.png']");
                var resourcesId = [];
                for (var i = 0; i < imgs.length; i++) {
                    var resourceId = $(imgs[i]).prev().val();
                    resourcesId.push(resourceId);
                }
                var resources = resourcesId.join(",") + "";
                $("#hiddenPublicObject").val(resources);

                $('#status').val("1");
                $('#submit').click();
            }

            var accepts = ['.xlsx', '.xls', 'doc', 'docx'];

            function submit() {

                var imgs = $(".table_info img[src='/images/radio_on.png']");
                var resourcesId = [];
                for (var i = 0; i < imgs.length; i++) {
                    var resourceId = $(imgs[i]).prev().val();
                    resourcesId.push(resourceId);
                }
                if (!$('[name=theme]').val()) {
                    layuiModal.alert("任务主题不得为空！");
                    return;
                }
                if (!$('[name=description]').val()) {
                    layuiModal.alert("任务描述不得为空！");
                    return;
                }
                if (!$('[name=files]').val()) {
                    layuiModal.alert("任务模版不得为空！");
                    return;
                }
                var resources = resourcesId.join(",") + "";
                $("#hiddenPublicObject").val(resources);
                if (!$("#hiddenPublicObject").val()) {
                    layuiModal.alert("请选择发布对象！");
                    return;
                }
                var files = $('[name=files]')[0].files
                for (var index = 0; index < files.length; index++) {
                    var filename = files[index].name;
                    filename = filename.substr(filename.lastIndexOf("."));
                    if (accepts.indexOf(filename) == -1) {
                        layuiModal.alert("请上传word或excel格式的文件");
                        return;
                    }
                }
                var confirmCallback = function () {
                    $('#status').val("2");
                    $('#submit').click();
                };
                layuiModal.confirm("确定要发布任务吗?", confirmCallback);
            }
        })
    </script>
</head>
<body>
<div class="table_form_content">
    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group" style="margin-bottom: 20px;">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
					<a href="javascript:;">数据报送</a>
					<a href="javascript:;">发布任务</a>
				</span>
        </div>
        <div class="bg_white_container">
            <div class="content_form" style="padding: 20px 0;">
                <form class="form-horizontal" role="form" action="${add }" method="post">
                    <div id="hg-form-container" class="form-group">
                        <div class="col-sm-12 col-xs-12 no-padding">
                            <div class="col-sm-6 col-xs-12">
                                <div class="col-sm-3 col-xs-3 ">
                                    <span class="control-label">任务主题</span>
                                </div>
                                <div class="col-sm-9 col-xs-9">
                                    <input disabled class="form-control" name="theme" value="${task.theme}"
                                           style="text-indent: inherit;">
                                </div>
                            </div>
                        </div>
<%--                        <div class="col-sm-12 col-xs-12 no-padding">--%>
<%--                            <div class="col-sm-6 col-xs-12">--%>
<%--                                <div class="col-sm-3 col-xs-3 ">--%>
<%--                                    <span class="control-label form-label-required">任务描述</span>--%>
<%--                                </div>--%>
<%--                                <div class="col-sm-9 col-xs-9">--%>
<%--                                    <textarea disabled class="form-control" name="description">${task.description}</textarea>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <div class="col-sm-12 col-xs-12 no-padding">--%>
<%--                            <div class="col-sm-6 col-xs-12">--%>
<%--                                <div class="col-sm-3 col-xs-3 ">--%>
<%--                                    <span class="control-label form-label-required">任务模版</span>--%>
<%--                                </div>--%>
<%--                                <div class="col-sm-9 col-xs-9">--%>
<%--                                    <input multiple type="file" name="files" style="text-indent: inherit;"--%>
<%--                                           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document">--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
                        <div class="col-sm-12 col-xs-12 no-padding">
                            <div class="col-sm-6 col-xs-12 publish_obj_container">
                                <div class="col-sm-3 col-xs-3">
                                    <span class="control-label">发布对象</span>
                                </div>
                                <div class="col-sm-9 col-xs-12 has_select_div" style="display: block;">
                                    <div class="has_select_container">
                                        <div class="has_select_title" style="border-bottom: none;"><span></span>
                                            <div class="right"><span>已选择</span><br><span
                                                    class="select_num">${fn:length(orgNames)}</span><span>个党委</span></div>
                                        </div>
                                        <div class="has_select_content container_scroll_hidden">
                                            <ul class="has_select_list">
                                                <c:forEach items="#{orgNames}" var="org">
                                                    <li>${org}</li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 col-xs-12 no-padding">
                            <div class="col-sm-6 col-xs-12">
                                <div class="col-sm-3 col-xs-3 ">
                                    <span class="control-label">任务说明</span>
                                </div>
                                <div class="col-sm-9 col-xs-9">
                                    <script id="task_content" name="task_content" type="text/plain"></script>
                                </div>
                            </div>
                        </div>

                        <input id="submit" type="submit" style="display:none;"/>
                        <input id="formId" type="hidden" name="formId" value="${formId}"/>
                        <input id="status" type="hidden" name="status" value="1"/>
                        <input id="taskId" type="hidden" name="taskId" value="${task.task_id}"/>
                        <input class="informationContent" type="hidden" value='${task.content }'>
                        <input id="hiddenPublicObject" type="hidden" name="publicObject"/>
                        <div class="layui-inline btn_group"
                             style="width: calc(50% - 120px);margin: 0;margin-top: 10px;">
                            <label class="layui-form-label"></label>
                            <div class="layui-input-inline">
                                <button onclick="window.history.back();" type="button"
                                        class="layui-btn layui-btn-primary"
                                        style="background-color: transparent;color: #666;padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;border-radius: 4px;">
                                    返回
                                </button>
                            </div>
                        </div>
                        <%--                    <button id="send" type="button" class="btn btn-default col-sm-2 col-xs-4" style="margin-left: 12%; ">发布--%>
                        <%--                    </button>--%>
                        <%--                    <button id="draft" type="button" class="btn btn-default col-sm-2 col-xs-4" style="margin-left: 45%;">保存为草稿--%>
                        <%--                    </button>--%>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>