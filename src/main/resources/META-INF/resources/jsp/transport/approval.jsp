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
    <link rel="stylesheet" type="text/css" href="${basePath}/css/print_div.css"/>
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

        #div_print1 p {
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
                    page: {
                        limit: 10,   //每页条数
                        limits: [],
                        prev: '&lt;上一页',
                        next: '下一页&gt;',
                        groups: 4
                    },
                    cols: [[ //表头
                        {field: 'retention_id', title: 'id', hide: true},
                        {field: 'user_name', title: '姓名', width: '10%', templet: function (d) {
                                return "<a onclick='window.open(\"/addperson?userId=" + d.user_id + "\", \"_blank\")'>" + d.user_name+ "</a>"
                            }
                        },
                        {field: 'org_name', title: '所在支部', width: '20%'},
                        {field: 'second_name', title: '二级党委', width: '20%'},
                        {field: 'time', title: '申请时间', width: '10%'},
                        {
                            field: 'status', title: '状态', width: '15%', templet: function (d) {
                                return statusList[d.status];
                            }
                        },
                        {field: 'operate', title: '操作', width: '25%', toolbar: '#retentionBtns'}
                    ]] ,
                    parseData: function(res){ //将原始数据解析成 table 组件所规定的数据
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
                    "    <div style=\"height: 400px;width: 100%;  clear: both;\" id=\"div_print1\" class='letter'>\n" +
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
                    "                <span style=\"border-bottom: 1px solid;\" >&nbsp;{secondary} :</span>\n" +
                    "            </p>\n" +
                    "            <p style=\"white-space: normal;\">&nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;\n" +
                    "                <span style=\"border-bottom: 1px solid;\" id='print_name'>&nbsp; {name}&nbsp;\n" +
                    "                </span>同志，\n" +
                    "                <span style=\"border-bottom: 1px solid;\" id='print_sex'>&nbsp; {sex}&nbsp;\n" +
                    "                </span>，\n" +
                    "                <span style=\"border-bottom: 1px solid;\" id='print_age'>&nbsp; {age}&nbsp;\n" +
                    "                </span>岁，\n" +
                    "                <span style=\"border-bottom: 1px solid;\" id='print_ethnic'>&nbsp; {ethnic}&nbsp;\n" +
                    "                </span>，是中共\n" +
                    "                <span style=\"border-bottom: 1px solid;\" id='print_type'>&nbsp; {type}&nbsp;\n" +
                    "                </span>，身份证号码<br>\n" +
                    "            </p>\n" +
                    "            <p style=\"white-space: normal;\">\n" +
                    "                <span style=\"border-bottom: 1px solid;\" id='print_identity'>\n" +
                    "                    &nbsp; {identity}&nbsp;\n" +
                    "                </span>，由\n" +
                    "                <span style=\"border-bottom: 1px solid;\" id='print_org'>\n" +
                    "                    &nbsp; {secondary}&nbsp;\n" +
                    "                </span>转到\n" +
                    "                <span style=\"border-bottom: 1px solid;\" id='print_to_org'>\n" +
                    "                    &nbsp; {toOrg}&nbsp;\n" +
                    "                </span>，" +
                    "           </p>" +
                    "           <p style=\"white-space: normal;\">请转接组织关系。该同志党费已交至" +
                    "               <input id='print_fee_year' style=\"max-width: 60px;height: 20px!important;text-align: center;\" contenteditable=\"true\">" +
                    "               年<input id='print_fee_month' style=\"max-width: 30px;height: 20px!important;text-align: center;\" contenteditable=\"true\">" +
                    "            月</p>\n" +
                    "            <br>\n" +
                    "            <p style=\"white-space: normal;\">有效期\n" +
                    "                <select style=\"height: 30px;text-indent: 0;\" id='print_valid_time'>\n" +
                    "                    <option>叁拾</option>\n" +
                    "                    <option>陆拾</option>\n" +
                    "                    <option>玖拾</option>\n" +
                    "                </select>\n" +
                    "                天\n" +
                    "            </p>\n" +
                    "            <p style=\"white-space: normal;text-align: right;\">\n" +
                    "                <input id='print_year' style=\"max-width: 60px;height: 20px!important;text-align: center;\">年\n" +
                    "              <input id='print_month' style=\"max-width: 30px;height: 20px!important;text-align: center;\">月\n" +
                    "                <input id='print_day' style=\"max-width: 30px;height: 20px!important;text-align: center;\">日\n" +
                    "            </p>\n" +
                    "            <p style=\"white-space: normal;\">\n" +
                    "                党员电话或其他联系方式：\n" +
                    "                <span style=\"border-bottom: 1px solid;\" id='print_telephone'>&nbsp; {telephone}&nbsp;\n" +
                    "                </span>\n" +
                    "            </p>\n" +
                    "            <br>\n" +
                    "            <p style=\"white-space: normal;\">\n" +
                    "                党员原所在基础党委通讯地址：\n" +
                    "                <span style=\"border-bottom: 1px solid;\" id='print_orgAddress'>&nbsp; {orgAddress}&nbsp;\n" +
                    "                </span>\n" +
                    "            </p>\n" +
                    "            <br>\n" +
                    "            <p style=\"white-space: normal;\">\n" +
                    "                联系电话：\n" +
                    "                <span style=\"border-bottom: 1px solid;\"id='print_org_telephone'>&nbsp; {orgTelephone}&nbsp;\n" +
                    "                </span>\n" +
                    "                &nbsp;&nbsp;传真：<span style=\"border-bottom: 1px solid;\" id='print_org_fax'>&nbsp; {orgFax}&nbsp;\n" +
                    "                </span>\n" +
                    "                &nbsp;&nbsp;邮编：<span style=\"border-bottom: 1px solid;\" id='print_org_mailcode'>&nbsp; {orgMailCode}&nbsp;\n" +
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
                    "</div>\n" +
                    "<div style=\"height: 297mm;width: 210mm;background-color: #9cb945; font-size: 16px;\" id=\"div_print\" class=\"letter\">\n" +
                    "    <style type=\"text/css\">@page {\n" +
                    "        size: auto;\n" +
                    "        margin-top: 0;\n" +
                    "        margin-bottom: 0;\n" +
                    "    }\n" +
                    "\n" +
                    "    .line-top1 div {\n" +
                    "        top: 40mm;\n" +
                    "    }\n" +
                    "\n" +
                    "    .line-top2 div {\n" +
                    "        top: 55mm;\n" +
                    "    }\n" +
                    "\n" +
                    "    .line-top3 div {\n" +
                    "        top: 68mm;\n" +
                    "    } " +
                    "   .line1 div {\n" +
                    "        top: 120mm;\n" +
                    "    }\n" +
                    "\n" +
                    "    .line2 div {\n" +
                    "        top: 130mm;\n" +
                    "    }\n" +
                    "\n" +
                    "    .line3 div {\n" +
                    "        top: 140mm;\n" +
                    "    }\n" +
                    "\n" +
                    "    .line4 div {\n" +
                    "        top: 150mm;\n" +
                    "    }\n" +
                    "\n" +
                    "    .line5 div {\n" +
                    "        top: 160mm;\n" +
                    "    }\n" +
                    "\n" +
                    "    .line6 div {\n" +
                    "        top: 170mm;\n" +
                    "    }\n" +
                    "\n" +
                    "    .line7 div {\n" +
                    "        top: 190mm;\n" +
                    "    }\n" +
                    "\n" +
                    "    .line8 div {\n" +
                    "        top: 200mm;\n" +
                    "    }\n" +
                    "\n" +
                    "    .line9 div {\n" +
                    "        top: 210mm;\n" +
                    "    }\n" +
                    "\n" +
                    "    .line10 div {\n" +
                    "        top: 220mm;\n" +
                    "    }\n" +
                    "    </style>\n" +
                    "    <div class=\"line-top1\">\n" +
                    "        <div style=\"position: absolute;left: 50mm;\" class=\"name\"></div>\n" +
                    "    </div>\n" +
                    "    <div class=\"line-top2\">\n" +
                    "        <div style=\"position: absolute;left: 70mm;\" class=\"org_name\"></div>\n" +
                    "        <div style=\"position: absolute;left: 135mm;\" class=\"toOrg\"></div>\n" +
                    "    </div>\n" +
                    "    <div class=\"line-top3\">\n" +
                    "        <div style=\"position: absolute;left: 125.7mm;\" class=\"currentYear\"></div>\n" +
                    "        <div style=\"position: absolute;left: 147.7mm;\" class=\"currentMonth\"></div>\n" +
                    "        <div style=\"position: absolute;left: 165.7mm;\" class=\"currentDay\"></div>\n" +
                    "    </div>" +
                    "    <div class=\"line1\">\n" +
                    "        <div style=\"position: absolute;left: 21.7mm;\" class=\"org_name\">本科生第一党支部</div>\n" +
                    "    </div>\n" +
                    "    <div class=\"line2\">\n" +
                    "        <div style=\"position: absolute;left: 36.7mm;\" class=\"name\">罗怀西</div>\n" +
                    "        <div style=\"position: absolute;left: 121.7mm;\" class=\"age\">28</div>\n" +
                    "        <div style=\"position: absolute;left: 150.7mm;\" class=\"ethnic\">汉</div>\n" +
                    "    </div>\n" +
                    "    <div class=\"line3\">\n" +
                    "        <div style=\"position: absolute;left: 110.7mm;\" class=\"identity\">111111111111111111</div>\n" +
                    "    </div>\n" +
                    "    <div class=\"line4\">\n" +
                    "        <div style=\"position: absolute;left: 27mm;font-size: 16px;\" class=\"org_name\"></div>\n" +
                    "        <div style=\"position: absolute;left: 95.7mm;\" class=\"toOrg\"></div>\n" +
                    "    </div>\n" +
                    "    <div class=\"line5\">\n" +
                    "        <div style=\"position: absolute;left: 110.7mm;\" class=\"feeYear\"></div>\n" +
                    "        <div style=\"position: absolute;left: 135.7mm;\" class=\"feeMonth\"></div>\n" +
                    "    </div>\n" +
                    "    <div class=\"line6\">\n" +
                    "        <div style=\"position: absolute;left: 52.7mm;\" class=\"validTime\"></div>\n" +
                    "    </div>\n" +
                    "    <div class=\"line7\">\n" +
                    "        <div style=\"position: absolute;left: 125.7mm;\" class=\"currentYear\"></div>\n" +
                    "        <div style=\"position: absolute;left: 147.7mm;\" class=\"currentMonth\"></div>\n" +
                    "        <div style=\"position: absolute;left: 165.7mm;\" class=\"currentDay\"></div>\n" +
                    "    </div>\n" +
                    "    <div class=\"line8\">\n" +
                    "        <div style=\"position: absolute;left: 95.7mm;\" class=\"telephone\"></div>\n" +
                    "    </div>\n" +
                    "    <div class=\"line9\">\n" +
                    "        <div style=\"position: absolute;left: 95.7mm;font-size: 16px;\" class=\"orgAddress\"></div>\n" +
                    "    </div>\n" +
                    "    <div class=\"line10\">\n" +
                    "        <div style=\"position: absolute;left: 47.7mm;\" class=\"orgTelephone\"></div>\n" +
                    "        <div style=\"position: absolute;left: 100.7mm;\" class=\"orgFax\"></div>\n" +
                    "        <div style=\"position: absolute;left: 155.7mm;\" class=\"orgMailCode\"></div>\n" +
                    "    </div>\n" +
                    "\n" +
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
                                $('#div_print .org_name').text($('#print_org').text());
                                $('#div_print .name').text($('#print_name').text());
                                $('#div_print .age').text($('#print_age').text());
                                $('#div_print .ethnic').text($('#print_ethnic').text());
                                $('#div_print .identity').text($('#print_identity').text());
                                $('#div_print .toOrg').text($('#print_to_org').text());
                                $('#div_print .feeYear').text($('#print_fee_year').val());
                                $('#div_print .feeMonth').text($('#print_fee_month').val());
                                $('#div_print .validTime').text($('#print_valid_time').val());
                                $('#div_print .currentYear').text($('#print_year').val());
                                $('#div_print .currentMonth').text($('#print_month').val());
                                $('#div_print .currentDay').text($('#print_day').val());
                                $('#div_print .telephone').text($('#print_telephone').text());
                                $('#div_print .orgAddress').text($('#print_orgAddress').text());
                                $('#div_print .orgTelephone').text($('#print_org_telephone').text());
                                $('#div_print .orgFax').text($('#print_org_fax').text());
                                $('#div_print .orgMailCode').text($('#print_org_mailcode').text());
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
                    page: {
                        limit: 10,   //每页条数
                        limits: [],
                        prev: '&lt;上一页',
                        next: '下一页&gt;',
                        groups: 4
                    },
                    cols: [[ //表头
                        {field: 'transport_id', title: 'id', hide: true},
                        {field: 'user_name', title: '姓名', width: '7%', templet: function (d) {
                                return "<a onclick='window.open(\"/addperson?userId=" + d.user_id + "\", \"_blank\")'>" + d.user_name + "</a>"
                            }
                        },
                        {field: 'org_name', title: '所在支部', width: '13.33%'},
                        {field: 'second_name', title: '二级党委', width: '13.3%'},
                        {field: 'to_org_name', title: '去往单位', width: '13.33%'},
                        {field: 'time', title: '申请时间', width: '6%'},
                        {field: 'reason', title: '原因', width: '7%'},
                        {
                            field: 'status', title: '状态', width: '10%', templet: function (d) {
                                return statusList[d.status];
                            }
                        },
                        {field: 'current_org_name', title: '当前审核组织', width: '10%'},
                        {field: 'operate', title: '操作', width: '20%', toolbar: '#transportBtns'}
                    ]],
                    parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                        transportCount = res.count;
                        return res;
                    }
                });

                $('#transportSearchBtn').on('click', function () {
                    transportTable.reload({
                        where: {type: $('#transportType').val(), memberName: $('#searchCondition').val()},
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                });
                $('#retentionSearchBtn').on('click', function () {
                    retentionTable.reload({
                        where: {memberName: $('#retentionCondition').val()},
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                });
                $('#transportExportBtn').on('click', function () {
                    if (transportCount > 0) {
                        var type = $('#transportType').val(), memberName = $('#searchCondition').val()
                        window.location.href = "${export}&action=transport&memberName=" + memberName + "&type=" + type;
                    } else {
                        layuiModal.alert("没有数据，无法导出");
                    }
                });
                $('#retentionExportBtn').on('click', function () {
                    if (retentionCount > 0) {
                        var memberName = $('#retentionCondition').val();
                        window.location.href = "${export}&action=retention&memberName=" + memberName;
                    } else {
                        layuiModal.alert("没有数据，无法导出");
                    }
                });
            });
        });

        function transportApprove(e, status) {
            var id = $(e).parent().parent().parent().parent().find("[data-field='transport_id']").children().text();
            $.post("${approval}", {id: id, type: 'transport', status: status}, function (res) {
                if (res.result) {
                    layuiModal.alert("已" + $(e).text());
                    window.location.reload();
                }
            })
        }

        function retentionApprove(e, status) {
            var id = $(e).parent().parent().parent().parent().find("[data-field='retention_id']").children().text();
            $.post("${approval}", {id: id, type: 'retention', status: status}, function (res) {
                if (res.result) {
                    layuiModal.alert("已" + $(e).text());
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
                        <a href="javascript:;">审批申请</a>
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
                            <input type="text" name="title" id="searchCondition" placeholder="搜索党员" autocomplete="off"
                                   class="layui-input custom_input" style="margin-left: 20px;">
                            <button type="button" id="transportSearchBtn" class="layui-btn custom_btn search_btn">查询
                            </button>
                            <button type="button" id="transportExportBtn" class="layui-btn custom_btn search_btn"
                                    style="float: right;">导出数据
                            </button>
                        </div>
                        <table id="transportTable" lay-filter="activityTable" class="custom_table"></table>
                    </div>
                    <div class="layui-tab-item">
                        <div class="operate_form_group">
                            <input type="text" name="title" id="retentionCondition" placeholder="搜索党员"
                                   autocomplete="off" class="layui-input custom_input">
                            <button type="button" id="retentionSearchBtn" class="layui-btn custom_btn search_btn">查询
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
        <span class="blue_text" onclick="window.open('{{d.receipt}}');">回执</span>
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