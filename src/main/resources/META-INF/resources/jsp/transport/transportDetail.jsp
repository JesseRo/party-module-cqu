<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<portlet:resourceURL id="/transport/org" var="brunches"/>
<portlet:resourceURL id="/transport/save" var="save"/>
<portlet:resourceURL id="/transport/receipt" var="receipt"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/organ-relation-transfer.min.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css" />
<%--    <link rel="stylesheet" href="${basePath}/css/jquery.dropdown.css"/>--%>
<%--    <script type="text/javascript" src="${basePath}/js/jquery.dropdown.js?v=11"></script>--%>
    <style>
        .organ_relation_container .organ_relation_page .form_content .custom_form{
            width: 36.24%;
        }
        .organ_relation_container .organ_relation_page .form_content .tips_container{
            width: 60%;
        }
    </style>
    <style type="text/css">
    </style>
    <script type="text/javascript" >
        $(function() {
            var data = ${transportJson};
            layui.use('form', function(){
                var form = layui.form;
                //表单提交
                form.on('submit(organRelaForm)', function(data){
                    // layer.alert(JSON.stringify(data.field), {
                    //     title: '最终的提交信息'
                    // })
                    return false;
                });
                if (data != null){
                    form.val("organRelaForm", {
                        transport_type: data.type,
                        org: data.to_org_name,
                        org_name: data.to_org_id,
                        transport_form: data.type === '3' ? "纸质" : "电子",
                        transport_title: data.to_org_title,
                        transport_reason: data.reason
                    });
                }
            });
        });
    </script>
</head>
<body>
<div class="organ_relation_container table_form_content">
<div class="organ_relation_page">
    <div class="breadcrumb_group">
        当前位置：
        <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">组织关系转接</a>
                        <a href="javascript:;">转出详情</a>
                    </span>
    </div>
    <div class="form_content">
        <form class="layui-form custom_form" id="organRelaForm" lay-filter="organRelaForm">
            <div class="layui-form-item">
                <label class="layui-form-label">转出类型：</label>
                <div class="layui-input-block">
                    <select name="transport_type" lay-filter="transport_type" class="organRelaForm" disabled>
                        <option value="0">院内</option>
                        <option value="1">校内</option>
                        <option value="2">重庆市内</option>
                        <option value="3" selected="">重庆市外</option>
                    </select>
                </div>
            </div>
            <input name="isResubmit" value="${isResubmit}" type="hidden">
            <div class="layui-form-item">
                <label class="layui-form-label">去往单位：</label>
                <div class="layui-input-block" id="org_name">
                    <input type="text" id="org" placeholder="按照转入单位要求填转入往单位" disabled maxlength="20" name="org" autocomplete="off" class="layui-input">
                </div>
<%--                <div class="dropdown-sin-2" style="display: none;" id="org_id">--%>
<%--                    <select name="org" style="display:none;" multiple placeholder="请选择" id="org_select"></select>--%>
<%--                </div>--%>
                <div class="layui-input-block" id="org_brunch" style="display: none;">
                    <select name="org_name" id="org_brunch_select" disabled>
                        <option value="">请选择</option>
                        <c:forEach var="group" items="${brunchGroup}">
                            <optgroup label="${group.key}">
                                <c:forEach var="brunch" items="${group.value}">
                                    <option value="${brunch.org_id}">${brunch.org_name}</option>
                                </c:forEach>
                            </optgroup>
                        </c:forEach>
                    </select>
                </div>
                <div class="layui-input-block" id="org_all" style="display: none;">
                    <select name="org_name" id="org_all_select" disabled>
                        <option value="">请选择</option>
                        <c:forEach var="group" items="${allBrunchGroup}">
                            <optgroup label="${group.key}">
                                <c:forEach var="brunch" items="${group.value}">
                                    <option value="${brunch.org_id}">${brunch.org_name}</option>
                                </c:forEach>
                            </optgroup>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">转出形式：</label>
                <div class="layui-input-block">
                    <select name="transport_form" lay-filter="organRelaForm" disabled>
                        <option value="纸质"  selected="">纸质</option>
                        <option value="电子">电子</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">介绍信抬头：</label>
                <div class="layui-input-block">
                    <textarea maxlength="200" disabled name="transport_title" placeholder="转入/挂靠党组织名称（详见注意事项）" class="layui-textarea"></textarea>
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label" id="reason_title">转出原因：</label>
                <div class="layui-input-block">
                    <input type="text" disabled name="transport_reason" autocomplete="off"
                           class="layui-input" lay-filter="transport_reason">
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">转出状态：</label>
                <div class="layui-input-block">
                    <p style="float: left;line-height: 35px;color: red;" id="status">${statusList[transport.status]}</p>
                </div>
            </div>
            <c:if test="${not empty transport.receipt}">
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">回执：</label>
                <div class="layui-input-block">
                    <p style="float: left;line-height: 35px; cursor: pointer;color: blue;" onclick="window.open('${transport.receipt}');">点击下载</p>
                </div>
            </div>
            </c:if>
            <div class="layui-form-item btn_group">
                <div class="layui-input-block">
                    <button type="button" style="color: white;" class="layui-btn _submit" lay-filter="organRelaForm" onclick="window.history.back();">返回</button>
                </div>
            </div>
        </form>
    </div>
</div>
</div>
</body>
</html>