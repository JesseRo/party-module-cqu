<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<portlet:resourceURL id="/unit/save" var="save"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/release-event.min.css" />

    <style type="text/css">
        .main_content{
            overflow-y: auto;
        }
    </style>
    <script type="text/javascript" >
        $(function() {
            layui.use(['form','layer'], function(){
                var form = layui.form,
                    layer = layui.layer;
                //表单提交
                form.on('submit(activityForm)', function(data){
                    // layer.alert(JSON.stringify(data.field), {
                    //     title: '最终的提交信息'
                    // });
                    console.log(JSON.stringify(data.field));
                    $.post("${save}", data.field, function (res) {
                        if(res.result){
                            layer.msg("保存成功！");
                            window.history.back();
                        }else {
                            layer.msg(res.message);
                        }
                    });
                    return false;
                });

            });
        });
    </script>
</head>
<body>
<div class="organ_retention_container table_form_content">
    <!-- 右侧盒子内容 -->
    <div class="organ_retention_page">
        <div class="breadcrumb_group">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">基础数据管理</a>
                        <a href="javascript:;">行政机构信息管理</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <div class="form_container">
                <form class="layui-form custom_form" id="activityForm">
                    <input type="hidden" value="${unit.id}" name="unitId">
                    <div class="layui-inline" style="height: 30px; margin-bottom: 48px;  width: 100%;clear: unset;">
                        <label class="layui-form-label" style="width: 120px;">行政机构名称：</label>
                        <div class="layui-input-inline" style="width: calc(50% - 120px);margin: 0;">
                            <input type="text" value="${unit.unit_name}" maxlength="20" name="unitName" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline btn_group">
                        <label class="layui-form-label"></label>
                        <div class="layui-input-inline">
                            <button type="button" class="layui-btn" lay-submit="" lay-filter="activityForm">确定</button>
                            <button type="button" onclick="window.history.back();" class="layui-btn layui-btn-primary">取消</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
</body>
</html>