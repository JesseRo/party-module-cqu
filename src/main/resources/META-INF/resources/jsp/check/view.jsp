<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>


<portlet:resourceURL id="/hg/check/page" var="personal"/>
<portlet:resourceURL id="/hg/check/delete" var="delete"/>
<portlet:resourceURL id="/hg/check/add" var="add"/>
<portlet:resourceURL id="/org/memberGroup" var="candidate" />
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1" />
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css" />
    <link rel="stylesheet" href="${basePath}/css/jquery.dropdown.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery.dropdown.js?v=11"></script>

    <style type="text/css">
        input, select{
            text-indent: 0;
        }
    </style>
    <script type="text/javascript" >
        var tableObj;
        $(function() {
            layui.use('table', function(){
                var table = layui.table;
                tableObj = table.render({
                    elem: '#logTable',
                    url: '${personal}', //数据接口
                    method: 'post',
                    where: {search: $('#searchCondition').val()},
                    page: {
                        limit:10,   //每页条数
                        limits:[],
                        prev:'&lt;上一页',
                        next:'下一页&gt;',
                        groups:4
                    },
                    cols: [[ //表头
                        {field: 'id', title: 'id', hide: true},
                        {field: 'member_name', title: '督察人员', width:'33.3%'},
                        {field: 'campus', title: '所在校区', width:'33.3%'},
                        {field: 'meeting_theme', title: '操作', width:'33.4%', toolbar: '#transportBtns'}
                    ]]
                });
            });
            $('#searchCondition').change( function () {
                _reload();
            });

            function refresh(org) {
                $('.dropdown-mul-2').dropdown({
                    data : [ {
                        name : '没有数据',
                        disabled : true
                    } ],
                    input : '<input type="text" maxLength="20" placeholder="请输入搜索">',
                    limitCount: 1,
                    choice : function() {

                    }
                });
                $.get('${candidate}', {
                    orgId : org
                }, function(res) {
                    if (res.result) {
                        var admins = res.data.admins;
                        var candidatesGroup = res.data.candidates;
                        var candidates = [];
                        var _admin = {};
                        for ( var i in admins) {
                            _admin[admins[i]] = admins[i];
                        }
                        for ( var group in candidatesGroup) {
                            for ( var j in candidatesGroup[group]) {
                                var member = candidatesGroup[group][j];
                                candidates.push({
                                    id : member.member_identity,
                                    disabled : false,
                                    groupId : group,
                                    groupName : group,
                                    name : member.member_name,
                                    selected : false,
                                    phone: member.member_phone_number
                                });
                            }
                        }
                        if (candidates.length === 0) {
                            candidates = [ {
                                name : '没有数据',
                                disabled : true
                            } ];
                        }
                        $('.dropdown-mul-2').data('dropdown').changeStatus();
                        $('.dropdown-mul-2').data('dropdown').update(candidates,true);
                    }
                });
            }

            layui.use('layer', function() {
                var layer = layui.layer;
            });
            $('#addBtn').on('click', function () {
                layer.open({
                    title: '新增督察人员'
                    ,content: "<div id=\"add_popup\">\n" +
                        "    <div class=\"layui-form-item\">\n" +
                        "        <label class=\"layui-form-label\" style='width: 100px;'>督察人员：</label>\n" +
                        "        <div class=\"layui-input-block\">\n" +
                        "            <div class=\"dropdown-mul-2\">\n" +
                        "                <select id=\"add_user\">\n" +
                        "                </select>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "    <div class=\"layui-form-item\">\n" +
                        "        <label class=\"layui-form-label\" style='width: 100px;'>校区：</label>\n" +
                        "        <div class=\"layui-input-block\">\n" +
                        "            <select autocomplete=\"off\" class=\"form-control\" id=\"add_campus\">\n" +
                        "                <option disabled selected>请选择校区</option>\n" +
                        "                <option>A区</option>\n" +
                        "                <option>B区</option>\n" +
                        "                <option>C区</option>\n" +
                        "                <option>虎溪校区</option>\n" +
                        "            </select>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "</div>"
                    ,btn: ['确定', '取消']
                    ,yes: function (index) {
                        var user = $('#add_user').val();
                        var campus = $('#add_campus').val();
                        if (user && campus){
                            $.post('${add}', {userId: user, campus: campus}, function (res) {
                                if (res.result){
                                    _reload();
                                    layer.close(index);
                                }else {
                                    layuiModal.alert("新增失败");
                                }
                            })
                        }else {
                            layuiModal.alert("不能为空");
                        }
                    }
                    ,btn2: function () {
                        $('.dropdown-mul-2').data('dropdown').reset();
                    }
                    ,cancel: function () {
                        $('.dropdown-mul-2').data('dropdown').reset();
                    }
                });
                refresh("ddddd");
            });
        });
        function _reload(){
            tableObj.reload({
                elem: '#logTable',
                url: '${personal}', //数据接口
                method: 'post',
                where: {search: $('#searchCondition').val()},
                page: {
                    limit:10,   //每页条数
                    limits:[],
                    prev:'&lt;上一页',
                    next:'下一页&gt;',
                    groups:4
                },
                cols: [[ //表头
                    {field: 'id', title: 'id', hide: true},
                    {field: 'member_name', title: '督察人员', width:'33.3%'},
                    {field: 'campus', title: '所在校区', width:'33.3%'},
                    {field: 'meeting_theme', title: '操作', width:'33.4%', toolbar: '#transportBtns'}
                ]]
            });
        }
        function _delete(e) {
            var id = $(e).parent().parent().parent().parent().find("[data-field='id']").children().text();
            $.post('${delete}', {id: id}, function (res) {
                if (res.result){
                    alert("已" + $(e).text());
                    _reload();
                }
            })
        }
    </script>
</head>
<body>
<div class="table_form_content">
    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">组织生活管理</a>
                        <a href="javascript:;">督察人员管理</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <div class="form-group">
                <div class="col-sm-3 col-xs-12" style="padding-left: 0;">
                    <div class="col-sm-12 col-xs-9" style="padding-left: 0;">
                        <select name="title" id="searchCondition" placeholder="请选择校区" style="text-indent: 0;" class="form-control">
                            <option value="">所有校区</option>
                            <option>A区</option>
                            <option>B区</option>
                            <option>C区</option>
                            <option>虎溪校区</option>
                        </select>
                    </div>
                </div>
                <div class="btn_group">
                    <a>
                        <button class="layui-btn" id="addBtn" style="padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;background-color: #FFAB33;border-radius: 4px;">
                        新增
                        </button>
                    </a>
                </div>
            </div>
<%--            <div class="operate_form_group">--%>
<%--                <select id="searchCondition"  autocomplete="off" class="layui-input custom_input">--%>
<%--                    <option disabled>请选择校区</option>--%>
<%--                    <option>A区</option>--%>
<%--                    <option>B区</option>--%>
<%--                    <option>C区</option>--%>
<%--                    <option>虎溪校区</option>--%>
<%--                </select>--%>
<%--                <button type="button" id="addBtn" class="layui-btn custom_btn search_btn">新增</button>--%>
<%--            </div>--%>
            <table id="logTable" lay-filter="activityTable" class="custom_table"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
<script type="text/html" id="transportBtns">
    <div class="operate_btns">
        <span class="blue_text" onclick="_delete(this);">删除</span>
    </div>
</script>
</body>
</html>