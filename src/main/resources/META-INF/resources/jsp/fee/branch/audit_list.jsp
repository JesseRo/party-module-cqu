<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css"/>


    <style type="text/css">
        .custom_table {

        }
    </style>
    <script type="text/javascript">
        var tableObj;

        function audit(state, id, msg) {
            layuiModal.confirm("确定要" + msg + "吗？", function () {
                $.ajax({
                    type: "post",
                    url: sessionStorage.getItem("feeUrl") + '/fee/branch/audit',
                    data: JSON.stringify({id: id, state: state}),
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    success: function (res) {
                        if (res.code === 0) {
                            layuiModal.alert("已" + msg);
                            tableObj.reload();
                        } else {
                            layuiModal.alert(res.message)
                        }
                    }
                });
            })
        }

        function reject(state, id, msg) {
            layuiModal.prompt("驳回原因", '', function (value) {
                $.ajax({
                    type: "post",
                    url: sessionStorage.getItem("feeUrl") + '/fee/branch/audit',
                    data: JSON.stringify({
                        id: id,
                        state: state,
                        reason: value
                    }),
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    success: function (res) {
                        if (res.code === 0) {
                            layuiModal.alert("已" + msg);
                            tableObj.reload();
                        } else {
                            layuiModal.alert(res.message)
                        }
                    }
                });
            })
        }

        $(function () {
            $('#upload-block form').attr("action",sessionStorage.getItem("feeUrl") + '/fee/branch/importConfig');
            $('#upload-block [name=token]').val(sessionStorage.getItem("sessionKey"));

            var checked = {};
            $('#config_audit').on('click', function () {
                var ids = Object.keys(checked);
                if (ids.length > 0) {
                    audit(1, ids, "通过");
                } else {
                    layuiModal.alert("请选择人员");
                }
            })
            $('#config_reject').on('click', function () {
                var ids = Object.keys(checked);
                if (ids.length > 0) {
                    reject(2, ids, "驳回");
                } else {
                    layuiModal.alert("请选择人员");
                }
            })
            $('#config_set').on('click', function () {
                window.location.href = "/branch/config-set";
            })
            $('#config_import').on('click', function () {
                $('#upload-block [type="file"]').click();
            })
            $('#upload-block [type="file"]').change(function () {
                $('#upload-block [type="submit"]').click();
            })
            layui.use('table', function () {
                var table = layui.table;
                var all;

                tableObj = table.render({
                    elem: '#feeTable',
                    url: sessionStorage.getItem("feeUrl") + '/fee/branch/audit/list', //数据接口
                    headers: {Authorization: sessionStorage.getItem("sessionKey")},
                    method: 'get',
                    page: {
                        limit: 10,   //每页条数
                        limits: [],
                        prev: '&lt;上一页',
                        next: '下一页&gt;',
                        groups: 4,
                    },
                    cols: [[ //表头
                        {type:'checkbox'},
                        {field: 'id', title: 'id', hide: true},
                        {field: 'memberName', title: '姓名', width: '10%'},
                        {field: 'orgName', title: '所在组织', width: '20%'},
                        {field: 'feeTypeName', title: '党费类型', width: '10%'},
                        {
                            field: 'fee', title: '党费金额', width: '10%', templet: function (d) {
                                return Number(d.fee) / 100;
                            }
                        },
                        {field: 'createTime', title: '提交时间', width: '10%'},
                        {
                            field: 'status', title: '审核状态', width: '10%', templet: function (d) {
                                var content = d.id ? ['审核中', '已通过', '已驳回', '已过期'][d.state] : '未提交';
                                return '<p style="color: red">' + content + '</p>';
                            }
                        },
                        {field: 'reason', title: '原因', width: '10%'},
                        {field: 'operation', title: '操作', width: '20%', toolbar: '#operationButton'}
                    ]],
                    parseData: function (res) { //res 即为原始返回的数据
                        all = res.data.pageData;

                        return {
                            "code": res.code, //解析接口状态
                            "msg": res.message, //解析提示文本
                            "count": res.data.count, //解析数据长度
                            "data": res.data.pageData //解析数据列表
                        };
                    }
                });

                table.on('checkbox(feeTable)', function(obj){
                    if (obj.checked) {
                        if (obj.type == 'all') {
                            checked = {};
                            for (var i = 0; i < all.length; i++) {
                                var a = all[i];
                                checked[a.id] = 1
                            }
                        } else {
                            checked[obj.data.id] = 1
                        }
                    } else {
                        if (obj.type == 'all') {
                            checked = {}
                        }
                        delete checked[obj.data.id]
                    }
                    console.log(obj.checked); //当前是否选中状态
                    console.log(obj.data); //选中行的相关数据
                    console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
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
                        <a href="javascript:;">党费管理</a>
                        <a href="javascript:;">类型审核</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <div id="upload-block" style="display: none;">
                <form method="post" target="uploadTarget"
                      enctype="multipart/form-data">
                    <input type="file" name="excel">
                    <input type="text" name="token">
                    <input type="submit">
                    <iframe name="uploadTarget"></iframe>
                </form>
            </div>
            <div class="operate_form_group">
                <button type="button" id="config_audit" class="layui-btn custom_btn search_btn"
                        style="float: right;height: 38px;">批量通过
                </button>
                <button type="button" id="config_reject" class="layui-btn custom_btn search_btn"
                        style="float: right;height: 38px;">批量驳回
                </button>
                <button type="button" id="config_set" class="layui-btn custom_btn search_btn"
                        style="float: right;height: 38px;">录入党员党费数据
                </button>
                <button type="button" id="config_import" class="layui-btn custom_btn search_btn"
                        style="float: right;height: 38px;">批量录入党费数据
                </button>
                <button type="button" id="config_template" class="layui-btn custom_btn search_btn"
                        style="float: right;height: 38px;" onclick="window.open('/images/党费设置模板.xlsx')">下载导入模板
                </button>
            </div>
            <table id="feeTable" lay-filter="feeTable" class="custom_table"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
<script type="text/html" id="operationButton">
    {{# if(d.id){ }}
    <a class="layui-btn layui-btn-xs" onclick="window.location.href='/audit_detail?id={{d.id}}'">详情</a>
    {{# } }}

    {{# if(d.state == 0 && d.auditLevel == d.auditOrgType){ }}
    <a class="layui-btn layui-btn-xs layui-btn-warm" onclick="audit(1, ['{{d.id}}'], '通过')">通过</a>
    <a class="layui-btn layui-btn-xs layui-btn-danger" onclick="reject(2, ['{{d.id}}'], '驳回')">驳回</a>
    {{# } }}

</script>
</body>
</html>