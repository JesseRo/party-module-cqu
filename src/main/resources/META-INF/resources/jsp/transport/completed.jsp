<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<portlet:resourceURL id="/transport/page1" var="transport"/>
<portlet:resourceURL id="/transport/approval" var="approval"/>
<portlet:resourceURL id="/retention/page" var="retention"/>
<portlet:resourceURL id="/transport/export" var="export"/>


<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery.jqprint-0.3.js"></script>


    <style type="text/css">
        .bg_white_container .layui-tab .layui-tab-content .layui-show {
            height: 100%;
        }

        .bg_white_container .layui-tab .layui-tab-content {
            padding: 0;
            height: calc(100% - 50px);
        }

        .bg_white_container .layui-tab {
            height: 100%;
            margin: 0;
        }

        .form-label {
            padding: 0 !important;
        }

        .control-label {
            line-height: 36px;
        }

        #letter_modal input, select {
            height: 36px !important;
            text-indent: 0 !important;
        }

        .form-group > div {
            margin-top: 10px;
            margin-bottom: 10px;
        }

        #div_print p {
            margin-bottom: 2px;
        }

        .layui-layer-dialog .layui-layer-content {
            line-height: 22px;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            var statusList = ["审核中", "已通过", "已驳回", "已上传回执", "已确认回执", "已重新申请"];
            var retentionCount;
            layui.use('table', function () {
                var table = layui.table;

                //第一个实例
                var retentionTable = table.render({
                    elem: '#retentionTable',
                    url: '${retention}', //数据接口
                    method: 'post',
                    where: {completed: true},
                    page: {
                        limit: 10,   //每页条数
                        limits: [],
                        prev: '&lt;上一页',
                        next: '下一页&gt;',
                        groups: 4
                    },
                    cols: [[ //表头
                        {field: 'retention_id', title: 'id', hide: true},
                        {field: 'user_name', title: '姓名', width: '15%'},
                        {field: 'org_name', title: '所在支部', width: '25%'},
                        {field: 'second_name', title: '二级党委', width: '25%'},
                        {field: 'time', title: '申请时间', width: '15%'},
                        {
                            field: 'status', title: '状态', width: '20%', templet: function (d) {
                                return statusList[d.status];
                            }
                        },
                    ]],
                    parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                        retentionCount = res.count;
                        return res;
                    }
                });

                var modalTemplate = "<div id=\"letter_modal\" style=\"width: 720px;\">\n" +
                    "    <div style=\"height: 180px;width: 100%; border-bottom: 1px solid #d4d4d4; margin-bottom: 0;\" class=\"col-sm-12 col-xs-12 form-group\">\n" +
                    "        <div class=\"col-sm-12 col-xs-12\">\n" +
                    "            <h3 style=\"text-align: center;margin-top: 0;\">申请信息</h3>\n" +
                    "        </div>\n" +
                    "        <div class=\"col-sm-6 col-xs-12\">\n" +
                    "            <div class=\"col-sm-3 col-xs-3 form-label\">\n" +
                    "                <span class=\" control-label form-label-required\">去往单位</span>\n" +
                    "            </div>\n" +
                    "            <div class=\"col-sm-9 col-xs-9 form-label\">\n" +
                    "                <input class=\"form-control\" name=\"toOrg\" value=\"{toOrg}\" disabled>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "        <div class=\"col-sm-6 col-xs-12\">\n" +
                    "            <div class=\"col-sm-3 col-xs-3 form-label\">\n" +
                    "                <span class=\" control-label form-label-required\">地区</span>\n" +
                    "            </div>\n" +
                    "            <div class=\"col-sm-9 col-xs-9 form-label\">\n" +
                    "                <select class=\"form-control\" name=\"region\" value=\"\" disabled>\n" +
                    "                    <option>市外</option>\n" +
                    "                    <option>市内</option>\n" +
                    "                    <option>校外</option>\n" +
                    "                    <option>校内</option>\n" +
                    "                </select>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "        <div class=\"col-sm-6 col-xs-12\">\n" +
                    "            <div class=\"col-sm-3 col-xs-3 form-label\">\n" +
                    "                <span class=\" control-label form-label-required\">介绍信抬头</span>\n" +
                    "            </div>\n" +
                    "            <div class=\"col-sm-9 col-xs-9 form-label\">\n" +
                    "                <input class=\"form-control\" name=\"title\" value=\"{toOrgTitle}\" disabled>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "        <div class=\"col-sm-6 col-xs-12\">\n" +
                    "            <div class=\"col-sm-3 col-xs-3 form-label\">\n" +
                    "                <span class=\" control-label form-label-required\">形式</span>\n" +
                    "            </div>\n" +
                    "            <div class=\"col-sm-9 col-xs-9 form-label\">\n" +
                    "                <select class=\"form-control\" name=\"type\" value=\"\" disabled>\n" +
                    "                    <option>纸质</option>\n" +
                    "                    <option>电子</option>\n" +
                    "                </select>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "    <div style=\"height: 400px;width: 100%;  clear: both;\" id=\"div_print\" class='letter'>\n" +
                    "        <style type=\"text/css\">@page {size: auto; margin-top:0; margin-bottom: 0;}</style>" +
                    "<div  style=\"padding-top: 0.1px;\">\n" +
                    "            <h3 style=\"text-align: center;\">中国共产党组织关系介绍信</h3>\n" +
                    "        </div>\n" +
                    "        <div style=\"height: 30px; margin-bottom: 6px; display: flex;\">\n" +
                    "            <div style=\"display: inline-block; width: 100%;\">\n" +
                    "                    <div class=' control-label form-label-required' style=\"margin-left: 70%;display: inline-block;\">编号：</div>\n" +
                    "                    <div style='display: inline-block;width: 13%;'><input class=\"form-control\" name=\"subject\" value=\"\" style=\"height: 30px !important;width: 120px;\"></div>\n" +
                    "            </div>" +
                    "        </div>\n" +
                    "        <div class=\"view\" style=\"overflow-y: hidden; padding: 0 20px;\">\n" +
                    "            <p style=\"white-space: normal;\">\n" +
                    "                <span style=\"border-bottom: 1px solid;\">&nbsp;{secondary} :</span>\n" +
                    "            </p>\n" +
                    "            <p style=\"white-space: normal;\">&nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;\n" +
                    "                <span style=\"border-bottom: 1px solid;\">&nbsp; {name}&nbsp;\n" +
                    "                </span>同志，\n" +
                    "                <span style=\"border-bottom: 1px solid;\">&nbsp; {sex}&nbsp;\n" +
                    "                </span>，\n" +
                    "                <span style=\"border-bottom: 1px solid;\">&nbsp; {age}&nbsp;\n" +
                    "                </span>岁，\n" +
                    "                <span style=\"border-bottom: 1px solid;\">&nbsp; {ethnic}&nbsp;\n" +
                    "                </span>，是中共\n" +
                    "                <span style=\"border-bottom: 1px solid;\">&nbsp; {type}&nbsp;\n" +
                    "                </span>，身份证号码<br>\n" +
                    "            </p>\n" +
                    "            <p style=\"white-space: normal;\">\n" +
                    "                <span style=\"border-bottom: 1px solid;\">\n" +
                    "                    &nbsp; {identity}&nbsp;\n" +
                    "                </span>，由\n" +
                    "                <span style=\"border-bottom: 1px solid;\">\n" +
                    "                    &nbsp; {secondary}&nbsp;\n" +
                    "                </span>转到\n" +
                    "                <span style=\"border-bottom: 1px solid;\">\n" +
                    "                    &nbsp; {toOrg}&nbsp;\n" +
                    "                </span>，" +
                    "           </p>" +
                    "           <p style=\"white-space: normal;\">请转接组织关系。该同志党费已交至\n" +
                    "                <span style=\"border-bottom: 1px solid;\">\n" +
                    "                    &nbsp; 2020&nbsp;\n" +
                    "                </span>年\n" +
                    "                <span style=\"border-bottom: 1px solid;\">\n" +
                    "                    &nbsp; 2&nbsp;\n" +
                    "                </span>月。" +
                    "            </p>\n" +
                    "            <br>\n" +
                    "            <p style=\"white-space: normal;\">有效期\n" +
                    "                <select style=\"height: 30px;text-indent: 0;\">\n" +
                    "                    <option>叁拾</option>\n" +
                    "                    <option>陆拾</option>\n" +
                    "                    <option>玖拾</option>\n" +
                    "                </select>\n" +
                    "                天\n" +
                    "            </p>\n" +
                    "            <p style=\"white-space: normal;text-align: right;\">\n" +
                    "                <span style=\"border-bottom: 1px solid;\">&nbsp; {currentYear}&nbsp;\n" +
                    "                </span>年\n" +
                    "                <span style=\"border-bottom: 1px solid;\">&nbsp; {currentMonth}&nbsp;\n" +
                    "                </span>月\n" +
                    "                <span style=\"border-bottom: 1px solid;\">&nbsp; {currentDay}&nbsp;\n" +
                    "                </span>日\n" +
                    "            </p>\n" +
                    "            <p style=\"white-space: normal;\">\n" +
                    "                党员电话或其他联系方式：\n" +
                    "                <span style=\"border-bottom: 1px solid;\">&nbsp; {telephone}&nbsp;\n" +
                    "                </span>\n" +
                    "            </p>\n" +
                    "            <br>\n" +
                    "            <p style=\"white-space: normal;\">\n" +
                    "                党员原所在基础党委通讯地址：\n" +
                    "                <span style=\"border-bottom: 1px solid;\">&nbsp; {orgAddress}&nbsp;\n" +
                    "                </span>\n" +
                    "            </p>\n" +
                    "            <br>\n" +
                    "            <p style=\"white-space: normal;\">\n" +
                    "                联系电话：\n" +
                    "                <span style=\"border-bottom: 1px solid;\">&nbsp; {orgTelephone}&nbsp;\n" +
                    "                </span>\n" +
                    "                &nbsp;&nbsp;传真：<span style=\"border-bottom: 1px solid;\">&nbsp; {orgFax}&nbsp;\n" +
                    "                </span>\n" +
                    "                &nbsp;&nbsp;邮编：<span style=\"border-bottom: 1px solid;\">&nbsp; {orgMailCode}&nbsp;\n" +
                    "                </span>\n" +
                    "            </p>\n" +
                    "        </div>\n" +
                    "        <div class=\"view\" style=\"overflow-y: hidden; \">\n" +
                    "\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "    <div style=\"height: 20%;width: 100%; border-bottom: 1px solid #d4d4d4;\">\n" +
                    "\n" +
                    "    </div>\n" +
                    "</div>\n";

                table.on('tool(activityTable)', function (obj) {
                    var data = obj.data;
                    if (obj.event === 'print') {
                        var today = new Date();
                        var html = modalTemplate
                            .replace(/{secondary}/g, data.org_name)
                            .replace("{name}", data.user_name)
                            .replace("{sex}", data.sex)
                            .replace("{age}", data.age)
                            .replace("{ethnic}", data.ethnicity)
                            .replace("{type}", data.member_type)
                            .replace("{identity}", data.identity)
                            .replace(/{toOrg}/g, data.to_org_name)
                            .replace("{toOrgTitle}", data.to_org_title)
                            .replace("{year}", 2020)
                            .replace("{month}", 2)
                            .replace("{currentYear}", today.getFullYear())
                            .replace("{currentMonth}", today.getMonth() + 1)
                            .replace("{currentDay}", today.getDate())
                            .replace("{telephone}", data.phone_number)
                            .replace("{orgAddress}", data.org_address || "")
                            .replace("{orgTelephone}", data.org_contactor_phone || "")
                            .replace("{orgFax}", data.org_fax || "")
                            .replace("{orgMailCode}", data.mail_code || "");

                        layer.open({
                            title: '中国共产党员组织关系介绍信'
                            , maxWidth: 760
                            , maxHeight: 707
                            , resize: false
                            , content: html
                            , fixed: true
                            , move: false
                            , btn: ["打印", "取消"]
                            , yes: function (index) {
                                $('#div_print').jqprint(
                                    {
                                        debug: false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
                                        importCSS: false, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
                                        printContainer: true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
                                        operaSupport: false//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
                                    }
                                )
                            }
                        });
                    }
                });

                var transportCount;

                var transportTable = table.render({
                    elem: '#transportTable',
                    url: '${transport}', //数据接口
                    method: 'post',
                    where: {completed: true},
                    page: {
                        limit: 10,   //每页条数
                        limits: [],
                        prev: '&lt;上一页',
                        next: '下一页&gt;',
                        groups: 4
                    },
                    cols: [[ //表头
                        {field: 'transport_id', title: 'id', hide: true},
                        {field: 'user_name', title: '姓名', width: '10%'},
                        {field: 'org_name', title: '所在支部', width: '18.33%'},
                        {field: 'org_name', title: '所在支部', width: '18.33%'},
                        {field: 'to_org_name', title: '去往单位', width: '18.33%'},
                        {field: 'time', title: '申请时间', width: '10%'},
                        {field: 'reason', title: '原因', width: '15%'},
                        {
                            field: 'status', title: '状态', width: '10%', templet: function (d) {
                                return statusList[d.status];
                            }
                        },
                    ]],
                    parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                        transportCount = res.count;
                        return res;
                    }
                });

                $('#transportSearchBtn').on('click', function () {
                    transportTable.reload({
                        where: {
                            type: $('#transportType').val(),
                            memberName: $('#searchCondition').val(),
                            completed: true,
                            startDate: startDate,
                            endDate: endDate
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                });
                $('#retentionSearchBtn').on('click', function () {
                    retentionTable.reload({
                        where: {
                            memberName: $('#retentionCondition').val(),
                            completed: true,
                            startDate: rStartDate,
                            endDate: rEndDate
                        },
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                });
                $('#transportExportBtn').on('click', function () {
                    if (transportCount > 0) {
                        var type = $('#transportType').val(), memberName = $('#searchCondition').val()
                        var url = "${export}&action=transport&completed=true&memberName="
                            + memberName + "&type=" + type;
                        if (startDate && endDate) {
                            url += "&startDate=" + startDate + "&endDate=" + endDate;
                        }
                        window.location.href = url;
                    } else {
                        layuiModal.alert("没有数据，无法导出");
                    }
                });
                $('#retentionExportBtn').on('click', function () {
                    if (retentionCount > 0) {
                        var memberName = $('#retentionCondition').val();
                        var url = "${export}&action=retention&completed=true&memberName=" + memberName;
                        if (startDate && endDate) {
                            url += "&startDate=" + rStartDate + "&endDate=" + rEndDate;
                        }
                        window.location.href = url;
                    } else {
                        layuiModal.alert("没有数据，无法导出");
                    }
                });
            });
            var startDate, endDate, rStartDate, rEndDate;

            function toDateStr(date){
                if (date.year) {
                    return date.year + '-' + date.month + '-' + date.date;
                }else {
                    return "";
                }
            }

            layui.use('laydate', function () {
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#date_range'
                    , range: '-',
                    done: function (value, date, e) {
                        console.log(value); //得到日期生成的值，如：2017-08-18
                        console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                        console.log(e); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                        startDate = toDateStr(date);
                        endDate = toDateStr(e);
                    }
                });
                laydate.render({
                    elem: '#date_range2'
                    , range: '-',
                    done: function (value, date, e) {
                        console.log(value); //得到日期生成的值，如：2017-08-18
                        console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                        console.log(e); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                        rStartDate = toDateStr(date);
                        rEndDate = toDateStr(e);
                    }
                });
            })
        });

        function transportApprove(e, status) {
            var id = $(e).parent().parent().parent().parent().find("[data-field='transport_id']").children().text();
            $.post("${approval}", {id: id, type: 'transport', status: status}, function (res) {
                if (res.result) {
                    alert("已" + $(e).text());
                    window.location.reload();
                }
            })
        }

        function retentionApprove(e, status) {
            var id = $(e).parent().parent().parent().parent().find("[data-field='retention_id']").children().text();
            $.post("${approval}", {id: id, type: 'retention', status: status}, function (res) {
                if (res.result) {
                    alert("已" + $(e).text());
                    window.location.reload();
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
                        <a href="javascript:;">组织关系转接</a>
                        <a href="javascript:;">汇总查询</a>
                    </span>
        </div>
        <div class="bg_white_container">
            <div class="layui-tab">
                <ul class="layui-tab-title">
                    <li class="layui-this">组织关系转移</li>
                    <li>组织关系保留</li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show">
                        <div class="operate_form_group">
                            <select type="text" name="title" id="transportType" autocomplete="off" class="form-control"
                                    style="width: 15%;float: left;border-radius: 0;height: 40px!important;">
                                <option value="">全部转出类型</option>
                                <option value="0,1">校内</option>
                                <option value="2">市内</option>
                                <option value="3">市外</option>
                            </select>
                            <div class="layui-input-inline" style="margin-left: 20px;height: 40px;">
                                <input type="text" class="layui-input" id="date_range" placeholder="日期范围">
                            </div>
                            <input type="text" name="title" id="searchCondition" placeholder="查询条件" autocomplete="off"
                                   class="layui-input custom_input"
                                   style="margin-left: 20px; float: none;height: 40px;">
                            <button type="button" id="transportSearchBtn" class="layui-btn custom_btn search_btn"
                                    style="float: none;">查询
                            </button>
                            <button type="button" id="transportExportBtn" class="layui-btn custom_btn search_btn"
                                    style="float: right;">导出数据
                            </button>
                        </div>
                        <table id="transportTable" lay-filter="activityTable" class="custom_table"></table>
                    </div>
                    <div class="layui-tab-item">
                        <div class="operate_form_group">
                            <input type="text" name="title" id="retentionCondition" placeholder="搜索条件"
                                   autocomplete="off" class="layui-input custom_input">
                            <div class="layui-input-inline" style="margin-left: 20px;height: 40px;">
                                <input type="text" class="layui-input" id="date_range2" placeholder="日期范围">
                            </div>
                            <button type="button" id="retentionSearchBtn" class="layui-btn custom_btn search_btn"
                            style="float: none;">查询
                            </button>
                            <button type="button" id="retentionExportBtn" class="layui-btn custom_btn search_btn"
                                    style="float: right;">导出数据
                            </button>
                        </div>
                        <table id="retentionTable" lay-filter="retentionTable" class="custom_table"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>

<script type="text/html" id="transportBtns">
    <div class="operate_btns">
        <span class="blue_text" onclick="window.location.href='/transport_detail?id={{d.transport_id}}'">详情</span>
        {{# if(d.type == '3' && (d.status == 1 || d.status == 3 || d.status == 4)){ }}
        <span class="blue_text" lay-event="print">打印</span>
        {{# } }}
        {{# if(d.current_approve_org == '${department}' && d.status == 0){ }}
        <span class="blue_text" onclick="transportApprove(this, 1);">通过</span>
        <span class="red_text" onclick="transportApprove(this, 2);">驳回</span>
        {{# } }}
        {{# if(d.status == 3){ }}
        <span class="blue_text" onclick="window.location.href='{{d.receipt}}'">回执</span>
        <span class="blue_text" onclick="transportApprove(this, 4);">确认</span>
        {{# } }}
    </div>
</script>
<script type="text/html" id="retentionBtns">
    <div class="operate_btns">
        <span class="blue_text" onclick="window.location.href='/retention_detail?id={{d.retention_id}}'">详情</span>
        {{# if(d.status == 0){ }}
        <span class="blue_text" onclick="retentionApprove(this, 1);">通过</span>
        <span class="red_text" onclick="retentionApprove(this, 2);">驳回</span>
        {{# } }}
    </div>
</script>
</body>
</html>