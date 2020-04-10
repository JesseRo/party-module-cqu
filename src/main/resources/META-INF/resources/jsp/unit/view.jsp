<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<portlet:resourceURL id="/unit/delete" var="delete"/>
<portlet:resourceURL id="/unit/page" var="unitPage"/>
<portlet:resourceURL id="/unit/save" var="unitSave"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1" />
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css" />
    <style type="text/css">
        #unitFormModel .layui-form-label{
            float: left;
            display: block;
            padding: 9px 0px;
            width: 110px;
            font-weight: 400;
            line-height: 20px;
            text-align: left;
        }
        #unitFormModel .layui-input-inline{
            width: 240px;
        }
        #unitFormModel .layui-form-label.layui-required:after {
            content: "*";
            color: red;
            position: absolute;
            top: 12px;
            right: 2px;
        }
    </style>
    <script type="text/javascript" >
        $(function() {
            layui.use(['table','form','layer'], function(){
                var table = layui.table,
                    form = layui.form,
                    layer= layui.layer;
                renderTable();
                function renderTable(){
                    var  where = {
                        keyword: $("#searchForm input[name=keyword]").val()
                    };
                    table.render({
                        elem: '#unitTable',
                        where: where,
                        url: '${unitPage}', //数据接口
                        method: 'post',
                        page: {
                            limit:10,   //每页条数
                            limits:[],
                            prev:'&lt;上一页',
                            next:'下一页&gt;',
                            groups:4,
                        },
                        cols: [[ //表头
                            {field: 'id', title: 'id', hide: true},
                            {field: 'unit_name', title: '行政机构名称', width:'40%'},
                            {field: 'update_member_name', title: '更新人员', width:'25%'},
                            {field: 'update_time', title: '更新时间', width: '20%'},
                            {field: 'operate', title: '操作', width: '15%', toolbar: '#unitBtns'}
                        ]]
                    });
                    table.on('tool(unitTable)', function(obj){
                        //var checkStatus = table.checkStatus(obj.config.id);
                        switch(obj.event){
                            case 'edit':
                                unitEdit(obj.data.id);
                                break;
                            case 'delete':
                                unitDelete(obj.data.id);
                                break;
                        };
                    });
                }

                function unitEdit(id){
                    renderUnitModal(id)
                }
                function renderUnitModal(id){
                    var index = layer.prompt({
                        title:id==null?'添加行政机构':'编辑行政机构',
                        type: 1,
                        btn: 0,
                        content: $("#unitFormModel")
                    });
                    form.on('submit(unitForm)', function(data){
                        var postData = data.field;
                        postData["id"] = id;
                        $.post("${unitSave}", postData, function (res) {
                            if(res.result){
                                var msg = id==null?'添加成功。':'修改成功。';
                                layer.msg(msg);
                                renderTable();
                                layer.close(index);
                            }else if(res){
                                layer.msg(res.message);
                            }else{
                                layer.msg("请刷新后再试。", {icon:7});
                            }
                        },"json");
                        return false;
                    });
                }
                function unitDelete(id) {
                    layer.confirm('您确认删除吗？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        $.post("${delete}", {id: id},function (res) {
                            if (res.result){
                                layer.msg("删除成功");
                                renderTable();
                            }
                        })
                    });
                }
                $("#search_btn").on("click",function(){
                    renderTable();
                });
                $("#createUnit").on("click",function() {
                    renderUnitModal(null);
                });
            });
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
                        <a href="javascript:;">组织关系转接</a>
                        <a href="javascript:;">审批申请</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <div class="operate_form_group">
                <input type="text" name="keyword" placeholder="搜索条件" autocomplete="off" class="layui-input custom_input" id="keyword">
                <button type="button" class="layui-btn custom_btn search_btn" id="search_btn">查询</button>
                <button type="button" class="layui-btn custom_btn publish_acti_btn" id="createUnit">新增信息</button>
            </div>
            <table id="unitTable" lay-filter="unitTable" class="custom_table"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
<script type="text/html" id="unitBtns">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
</script>
<!-- 弹窗 -->
<div style="display: none" id="unitFormModel">
    <form class="layui-form" action="">
        <input type="hidden" class="layui-layer-input" value="1">
        <div class="layui-form-item">
            <label class="layui-form-label layui-required">行政机构名称</label>
            <div class="layui-input-inline">
                <input type="text" name="unitName" lay-verify="required" maxlength="20" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-layer-btn layui-layer-btn-">
            <a class="layui-layer-btn0" type="button"  lay-submit="" lay-filter="unitForm">确定</a>
            <a class="layui-layer-btn1">取消</a>
        </div>
    </form>
</div>
</body>
</html>