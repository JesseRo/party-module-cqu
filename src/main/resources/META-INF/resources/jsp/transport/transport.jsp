<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<portlet:resourceURL id="/transport/org" var="brunches"/>
<portlet:resourceURL id="/transport/save" var="save"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/organ-relation-transfer.min.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css" />
<%--    <link rel="stylesheet" href="${basePath}/css/jquery.dropdown.css"/>--%>
<%--    <script type="text/javascript" src="${basePath}/js/jquery.dropdown.js?v=11"></script>--%>

    <style type="text/css">
    </style>
    <script type="text/javascript" >
        $(function() {
            layui.use('form', function(){
                var form = layui.form;
                //表单提交
                form.on('submit(organRelaForm)', function(data){
                    // layer.alert(JSON.stringify(data.field), {
                    //     title: '最终的提交信息'
                    // })
                    return false;
                });
                form.on('select(transport_type)', function(data){
                    var type = data.value;
                    if (type === '0'){
                        $('#reason_title').text("留在本单位原因：");
                    }else {
                        $('#reason_title').text("转接原因：");
                    }
                    if(type === '0'){
                        $('#org_name').hide();
                        $('#org_all').hide();
                        $('#org_brunch').show();
                    }else if(type === '1'){
                        $('#org_name').hide();
                        $('#org_brunch').hide();
                        $('#org_all').show();
                    }else{
                        $('#org_name').show();
                        $('#org_brunch').hide();
                        $('#org_all').hide();
                    }
                    console.log(data);
                });
            });
            //时间选择器
            layui.use('laydate', function(){
                var laydate = layui.laydate;
                //执行一个laydate实例
                laydate.render({
                    elem: '#date', //指定元素
                });
            });

            <%--function initSelect(type) {--%>
            <%--    var candidatesGroup;--%>
            <%--    if( type === '0'){--%>
            <%--        candidatesGroup = groups.brunchInSecondary;--%>
            <%--    }else if (type === '1'){--%>
            <%--        candidatesGroup = groups.allOrg;--%>
            <%--    }--%>
            <%--    var candidates = [];--%>

            <%--    for ( var group in candidatesGroup) {--%>
            <%--        for ( var j in candidatesGroup[group]) {--%>
            <%--            var member = candidatesGroup[group][j];--%>
            <%--            candidates.push({--%>
            <%--                id : member.org_id,--%>
            <%--                disabled : false,--%>
            <%--                groupId : group.org_id,--%>
            <%--                groupName : group.org_name,--%>
            <%--                name : member.org_name,--%>
            <%--                selected : false--%>
            <%--            });--%>
            <%--        }--%>
            <%--    }--%>
            <%--    if (candidates.length === 0) {--%>
            <%--        candidates = [ {--%>
            <%--            name : '没有数据',--%>
            <%--            disabled : true--%>
            <%--        } ];--%>
            <%--    }--%>
            <%--    $('.dropdown-sin-2').data('dropdown').changeStatus();--%>
            <%--    $('.dropdown-sin-2').data('dropdown').update(candidates,true);--%>
            <%--}--%>

            <%--function refresh(type) {--%>
            <%--    $('.dropdown-sin-2').dropdown({--%>
            <%--        data : [ {--%>
            <%--            name : '没有数据',--%>
            <%--            disabled : true--%>
            <%--        } ],--%>
            <%--        input : '<input type="text" maxLength="20" placeholder="请输入搜索">',--%>
            <%--        choice : function() {--%>
            <%--        }--%>
            <%--    });--%>

            <%--    if (groups == null){--%>
            <%--        $.get('${brunches}', {--%>
            <%--            orgId : org--%>
            <%--        }, function(res) {--%>
            <%--            if (res.result) {--%>
            <%--                groups = res.data;--%>
            <%--               initSelect(type);--%>
            <%--            }--%>
            <%--        });--%>
            <%--    }else {--%>
            <%--        initSelect(type);--%>
            <%--    }--%>
            <%--}--%>
            <%--$('.dropdown-sin-2').dropdown({--%>
            <%--    data : [ {--%>
            <%--        name : '没有数据',--%>
            <%--        disabled : true--%>
            <%--    } ],--%>
            <%--    input : '<input type="text" maxLength="20" placeholder="请输入搜索">',--%>
            <%--    choice : function() {--%>
            <%--    }--%>
            <%--});--%>
            // setTimeout(function() {
            //     $('.dropdown-sin-2').data('dropdown').changeStatus(
            //         "readonly");
            // }, 0);

            var groups = null;

            $('._submit').on('click', function (e) {
                var type = $('.transport_type').val();
                var org;
                if (type === '0' ){
                    org = $('#org_brunch_select').val();
                }else if(type === '1'){
                    org = $('#org_all_select').val();
                }else {
                    org = $('#org').val();
                }
                var form = $('[name=transport_form]').val();
                var title = $('[name=transport_title]').val();
                var reason = $('[name=transport_reason]').val();
                $.post('${save}', {type: type, org: org, form: form, title: title, reason: reason}, function (res) {
                    if(res.result){
                        alert('提交成功');
                        window.location.reload();
                    }
                })
            });

            if (false){
                $('.dropdown-sin-2').data('dropdown').choose([]);

            }

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
                        <a href="javascript:;">组织关系转出</a>
                    </span>
    </div>
    <div class="form_content">
        <form class="layui-form custom_form" id="organRelaForm">
            <div class="layui-form-item">
                <label class="layui-form-label">转出类型：</label>
                <div class="layui-input-block">
                    <select name="transport_type" lay-filter="transport_type" class="transport_type">
                        <option value="0">院内</option>
                        <option value="1">校内</option>
                        <option value="2">重庆市内</option>
                        <option value="3" selected="">重庆市外</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">转出单位：</label>
                <div class="layui-input-block" id="org_name">
                    <input type="text" id="org" placeholder="按照转入单位要求填转入往单位" maxlength="20" name="org" autocomplete="off" class="layui-input">
                </div>
<%--                <div class="dropdown-sin-2" style="display: none;" id="org_id">--%>
<%--                    <select name="org" style="display:none;" multiple placeholder="请选择" id="org_select"></select>--%>
<%--                </div>--%>
                <div class="layui-input-block" id="org_brunch" style="display: none;">
                    <select name="org_name" id="org_brunch_select">
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
                    <select name="org_name" id="org_all_select">
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
                    <select name="transport_form" lay-filter="aihao">
                        <option value="纸质"  selected="">纸质</option>
                        <option value="电子">电子</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">介绍信抬头：</label>
                <div class="layui-input-block">
                    <textarea maxlength="200" name="transport_title" placeholder="转入/挂靠党组织名称（详见注意事项）" class="layui-textarea"></textarea>
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label" id="reason_title">转接原因：</label>
                <div class="layui-input-block">
                    <select name="transport_reason" lay-filter="aihao">
                        <option value="升学"  selected="">升学</option>
                        <option value="工作">工作</option>
                        <option value="其他">其他</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item btn_group">
                <div class="layui-input-block">
                    <button type="button" class="layui-btn _submit" lay-submit="" lay-filter="organRelaForm">提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
        <div class="tips_container">
            <p class="tips_title">注意事项</p>
            <p>1.请与转入单位党务工作部门（非人事）落实；</p>
            <p> 2.市外原则上需县级以上党组织，不应出现支部、街道等；</p>
            <p>3.室外则通过12371平台进行转接，抬头需落实到支部；</p>
            <p>4.以转入“重庆大学外国语学院”为例，【由市外转入】去往单位：重庆大学外国语学院，介绍信抬头：重庆市委教育工委；【由市内转入】去往单位：重庆大学外国语学院，介绍信抬头：市委教工委-重庆大学党委-外语学院党务-XX支部</p>
        </div>
    </div>
</div>
</div>
</body>
</html>