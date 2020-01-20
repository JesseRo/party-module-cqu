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
        .right{
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
            var div = '<div class="col-sm-6 col-xs-12 publish_obj_container">' +
                '<div class="col-sm-3 col-xs-3">' +
                '<span class="control-label">发布对象</span>' +
                '</div>' +
                '<div class="col-sm-9 col-xs-9 publish_obj">' +
                '<div class="publish_obj_content">' +
                '<div class="publish_obj_title">' +
                '二级党组织' +
                '<div class="right">' +
                ' <div class="select_choice all_select">' +
                ' <img src="/images/not_check_icon.png" />' +
                ' <input type="hidden" />' +
                ' <span>全选</span>' +
                '</div>' +
                ' <div class="select_choice oppsite_select">' +
                ' <img src="/images/not_check_icon.png" />' +
                '<input type="hidden" />' +
                '<span>反选</span>' +
                ' </div>' +
                '</div>' +
                ' </div>' +
                '<div class="publish_obj_info container_scroll_hidden">' +
                '<ul class="list-group">' +
                '</ul>' +
                '</div>' +
                ' </div>' +
                '</div>' +
                '</div>' +
                '<div class="col-sm-6 col-xs-12 has_select_div">' +
                '<div class="has_select_container">' +
                ' <div class="has_select_title">' +
                ' <span>已选goo</span>' +
                '<div class="right">' +
                '<span>已选择</span>' +
                '<span class="select_num"></span>' +
                '<span>个党委</span>' +
                '</div>' +
                ' </div>' +
                ' <div class="has_select_content container_scroll_hidden">' +
                ' <ul class="has_select_list">' +
                ' </ul>' +
                '</div>' +
                '</div>' +
                '</div>';
            var title = "${publicObjectTitle}";
            div = div.replace("二级党组织", title);
            div = div.replace("已选goo", title);
            div = div.replace("个党委", title);
            $("#hg-form-container > .no-padding").eq(3).html(div);
            $("#hg-form-container > .no-padding").eq(3).find(".control-label").addClass("form-label-required");
            $.ajax({
                url: '${getPublicObject}',
                type: 'POST',
                data: "",
                dataType: 'json',
                async: false,
                success: function (returndata) {
                    for (var i = 0; i < returndata.length; i++) {
                        var li = '<li class="list-group-item table_info"> ' +
                            '<span title="马克思主义学院党委">' + returndata[i].org_name + '</span> ' +
                            '<input type="hidden" value="' + returndata[i].org_id + '"/> ' +
                            '<img class="right" src="/images/radio.png" /> ' +
                            '</li>';
                        $(".list-group").append(li);
                    }
                },
                error: function (returndata) {
                    alert("获取数据失败");
                }
            });

            /*   二级党委的选中及取消选中 */
            $("#hg-form-container").on("click", ".publish_obj_info li", function () {
                $(".publish_obj_content .select_choice").find("img").attr("src", "/images/not_check_icon.png")
                var oldSrc = $(this).find(".right").attr("src");
                var newSrc = "";
                if (oldSrc.indexOf("on") > 0) {
                    newSrc = oldSrc.replace(/_on.png/, ".png");
                } else {
                    newSrc = oldSrc.replace(/.png/, "_on.png");
                }
                $(this).find(".right").attr("src", newSrc);
                var sum = $(".publish_obj_info").find("img.right").length;
                var imgNum = $(".publish_obj_info").find("img[src='/images/radio_on.png']").length;
                if (sum == imgNum) {
                    $(".all_select").find("img").attr("src", "/images/checked_icon.png");
                    $(".oppsite_select").find("img").attr("src", "/images/not_check_icon.png");
                } else {
                    $(".all_select").find("img").attr("src", "/images/not_check_icon.png");
                }
                renderSelected();
            })

            /*   二级党委全选 */
            $("#hg-form-container").on("click", ".all_select", function () {
                $(this).find("img").attr("src", "/images/checked_icon.png");
                $(this).siblings(".oppsite_select").find("img").attr("src", "/images/not_check_icon.png");
                $(".publish_obj_info").find("img.right").each(function () {
                    $(this).attr("src", "/images/radio_on.png");
                })
                renderSelected();
            })

            /*   二级党委反选 */
            $("#hg-form-container").on("click", ".oppsite_select", function () {
                $(this).find("img").attr("src", "/images/checked_icon.png");
                $(this).siblings(".all_select").find("img").attr("src", "/images/not_check_icon.png");
                $(".publish_obj_info").find("img.right").each(function () {
                    var Src = $(this).attr("src");
                    if (Src.indexOf("on") > 0) {
                        $(this).attr("src", "/images/radio.png")
                    } else {
                        $(this).attr("src", "/images/radio_on.png");
                    }
                })
                renderSelected();
            })

            /*  渲染已选中的党委 */
            function renderSelected() {
                console.log("test")
                var _target = $(".publish_obj_info .list-group-item");
                var num = 0;
                $(".has_select_list").html("");
                $(".has_select_div").css("display", "block");
                _target.each(function () {
                    var _src = $(this).find("img").attr("src");
                    if (_src.indexOf("on") > 0) {
                        var _text = $(this).find("span").html();
                        var item = "<li>" + _text + "</li>";
                        $(".has_select_list").append(item);
                        num += 1;
                    }
                })
                $(".has_select_title .select_num").html(num);
                if (num == 0) {
                    $(".has_select_div").css("display", "none");
                }
            }

            var ueObj = UE.getEditor("task_content", { initialFrameWidth:821   });
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
            ueObj.ready(function() {
                ueObj.setContent($(".informationContent").val());
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
                var resources = resourcesId.join(",") + "";
                $("#hiddenPublicObject").val(resources);
                if (!$("#hiddenPublicObject").val()) {
                    layuiModal.alert("请选择发布对象！");
                }
                var files = $('[name=files]')[0].files
                for(var index = 0; index < files.length; index++){
                    var filename = files[index].name;
                    filename = filename.substr(filename.lastIndexOf("."));
                    if(accepts.indexOf(filename) == -1){
                        alert("请上传word或excel格式的文件");
                        return
                    }
                }
                var confirmCallback = function(){
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
        <div class="content_form"  style="padding: 20px 0;">
            <form class="form-horizontal" role="form" action="${add }" method="post">
                <div id="hg-form-container" class="form-group">
                    <div class="col-sm-12 col-xs-12 no-padding">
                        <div class="col-sm-6 col-xs-12">
                            <div class="col-sm-3 col-xs-3 ">
                                <span class="control-label form-label-required">任务主题</span>
                            </div>
                            <div class="col-sm-9 col-xs-9">
                                <input class="form-control" name="theme" value="${task.theme}" style="text-indent: inherit;">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12 col-xs-12 no-padding">
                        <div class="col-sm-6 col-xs-12">
                            <div class="col-sm-3 col-xs-3 ">
                                <span class="control-label form-label-required">任务描述</span>
                            </div>
                            <div class="col-sm-9 col-xs-9">
                                <textarea class="form-control" name="description">${task.description}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12 col-xs-12 no-padding">
                        <div class="col-sm-6 col-xs-12">
                            <div class="col-sm-3 col-xs-3 ">
                                <span class="control-label form-label-required">任务模版</span>
                            </div>
                            <div class="col-sm-9 col-xs-9">
                                <input multiple type="file" name="files" style="text-indent: inherit;"
                                       accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12 col-xs-12 no-padding">
                        <div class="col-sm-6 col-xs-12 publish_obj_container">
                            <div class="col-sm-3 col-xs-3">
                                <span class="control-label">发布对象</span>
                            </div>
                            <div class="col-sm-9 col-xs-9 publish_obj">
                                <div class="publish_obj_content">
                                    <div class="publish_obj_title">
                                        二级党组织
                                        <div class="right">
                                            <div class="select_choice all_select">
                                                <img src="/images/not_check_icon.png"/>
                                                <input type="hidden"/>
                                                <span>全选</span>
                                            </div>
                                            <div class="select_choice oppsite_select">
                                                <img src="/images/not_check_icon.png"/>
                                                <input type="hidden"/>
                                                <span>反选</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="publish_obj_info container_scroll_hidden">
                                        <ul class="list-group">
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12 has_select_div">
                            <div class="has_select_container">
                                <div class="has_select_title">
                                    <span>已选goo</span>
                                    <div class="right">
                                        <span>已选择</span>
                                        <span class="select_num"></span>
                                        <span>个党委</span>
                                    </div>
                                </div>
                                <div class="has_select_content container_scroll_hidden">
                                    <ul class="has_select_list">
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12 col-xs-12 no-padding">
                        <div class="col-sm-6 col-xs-12">
                            <div class="col-sm-3 col-xs-3 ">
                                <span class="control-label form-label-required">任务说明</span>
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
                    <div class="layui-inline btn_group" style="width: calc(50% - 120px);margin: 0;margin-top: 10px;">
                        <label class="layui-form-label"></label>
                        <div class="layui-input-inline">
                            <button id="send" type="button" class="layui-btn" lay-submit="" lay-filter="partyMemForm" style="padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;background-color: #FFAB33;border-radius: 4px;">
                                发布
                            </button>
                            <button onclick="window.history.back();" type="button" class="layui-btn layui-btn-primary" style="background-color: transparent;color: #666;padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;border-radius: 4px;">
                                取消
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