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
        table_outer_box > table thead, tbody tr {
            display: table-row !important;
            width: 100%;
            table-layout: auto;
        }
        #searchForm .layui-form-item .layui-inline .keyword {
            width: 300px;
            margin-right: 0px;
        }
        #personInfo .layui-form-item .layui-input-inline{
            width:200px
        }
        #searchForm .layui-form-label{
            margin-bottom: 0px;
            width:120px;
        }
        #personInfo .layui-form-label{
            width:140px;
            font-weight:bold;
        }
        #personInfo .layui-form-label-text{
            float: left;
            display: block;
            padding: 0 10px;
            width: 200px;
            font-weight: 400;
            line-height: 40px;
            font-size: 16px;
            text-align: left;
        }
        th, tr{
            text-align:center !important;
        }
        .layui-table-page-center{
            text-align: center;
        }
    </style>
    <script type="text/javascript" >
        layui.use(['table','layer','form'], function() {
            var table = layui.table,
                layer = layui.layer,
                form = layui.form;
            var pageInfo = {
                page:1,
                size:10
            };
            renderTable(1,pageInfo.size);
            form.on('submit(searchForm)', function (data) {
                renderTable(1,pageInfo.size);
            })
            function renderTable(page,size){
                var  where = {
                    keyword: $("#searchForm input[name=keyword]").val(),
                    dateType: $("#searchForm select[name=dateType]").val()
                };
                var ins = table.render({
                    elem: '#checkPersonTable',
                    where: where,
                    height:560,
                    url: '${personal}', //数据接口
                    method: 'post',
                    page: {
                        limit:size,   //每页条数
                        curr:page,
                        limits:[10,15,20],
                        prev:'&lt;上一页',
                        next:'下一页&gt;',
                        theme: '#FFB800',
                        groups:4
                    },
                    cols: [[ //表头
                        {field: 'member_code', align:'center', title: '工号', width:'14.16%'},
                        {field: 'member_name', align:'center', title: '姓名', width:'14.16%'},
                        {field: 'org_name', align:'center', title: '所在二级党组织', width:'14.16%'},
                        {field: 'secName', align:'center', title: '所在支部', width:'14.16%'},
                        {field: 'type', align:'center', title: '类别', width:'14.16%'},
                        {field: 'count', align:'center', title: '检查次数', width:'14.16%'},
                        {field: 'operation', align:'center', title: '操作', width:'15%', toolbar: '#checkPersonTableBtns'}
                    ]],
                    done: function(res, curr, count){
                        pageInfo.page = curr;
                        pageInfo.size = ins.config.limit;
                    }
                });
                $(".layui-table-view .layui-table-page").addClass("layui-table-page-center");
                $(".layui-table-view .layui-table-page").removeClass("layui-table-page");
                //监听事件
                table.on('tool(checkPersonTable)', function(obj){
                    if (obj.event === 'delete') {
                        deleteCheckPerson(obj.data.id);
                    }
                });
                function deleteCheckPerson(id){
                    layer.confirm('您确认删除吗？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        $.post('${delete}', {id: id}, function (res) {
                            if (res.result){
                                layer.msg("删除成功");
                                setTimeout(function(){window.location.reload()}, 1000);
                            }
                        })
                    });
                }
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
                            "    <div class=\"layui-form-item\">\n" +
                            "        <label class=\"layui-form-label\" style='width: 100px;'>类别：</label>\n" +
                            "        <div class=\"layui-input-block\">\n" +
                            "            <select autocomplete=\"off\" class=\"form-control\" id=\"add_type\">\n" +
                            "                <option disabled selected>请选择类别</option>\n" +
                            "                <option>一类</option>\n" +
                            "                <option>二类</option>\n" +
                            "            </select>\n" +
                            "        </div>\n" +
                            "    </div>\n" +
                            "</div>"
                        ,btn: ['确定', '取消']
                        ,yes: function (index) {
                            var user = $('#add_user').val();
                            var campus = $('#add_campus').val();
                            var type = $('#add_type').val();
                            if (user && campus && type){
                                $.post('${add}', {userId: user, campus: campus, type: type}, function (res) {
                                    if (res.result){
                                        layer.close(index);
                                        renderTable(1,pageInfo.size);
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
            }
        });
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
            <form class="layui-form" id="searchForm">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">校区</label>
                        <div class="layui-input-inline">
                            <select name="searchCondition">
                                <option value="">全部</option>
                                <option>A区</option>
                                <option>B区</option>
                                <option>C区</option>
                                <option>虎溪校区</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <div class="layui-input-inline keyword">
                            <input type="text" name="keyword"  placeholder="请输入姓名、组织关键字" class="layui-input">
                        </div>
                        <button type="button"  class="layui-btn layui-btn-warm"  lay-submit="" lay-filter="searchForm"><icon class="layui-icon layui-icon-search"></icon>搜索</button>
                    </div>
                    <div class="layui-inline">
                        <button type="button" class="layui-btn layui-btn-warm" id="addBtn">新增</button>
                    </div>
                </div>
            </form>
            <table id="checkPersonTable" lay-filter="checkPersonTable"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
</body>
<script type="text/html" id="checkPersonTableBtns">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
</script>
</html>