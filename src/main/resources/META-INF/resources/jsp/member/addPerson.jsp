<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<head>
    <title>抽查统计</title>
    <style>
        @media (min-width: 768px) {
            .table_info .publish_time {
                min-width: 200px;
            }

            .content_info .content_form {
                padding: 0px 20px 0 20px;
            }
        }

        th, td {
            text-align: left;
        }

        .content_info .content_form .form-group .control-label {
            text-align: right;
            color: #333;
            font-size: 16px;
        }

        /*#button1 {*/
        /*    margin-left: 10%;*/
        /*    margin-top: 5%*/
        /*}*/

        /*#button2 {*/
        /*    float: right;*/
        /*    margin-right: 10%;*/
        /*    margin-top: 5%;*/
        /*}*/

        #birth_place {
            width: 29%;
            float: right;
            height: 25px;
            vertical-align: baseline;
        }

        .class_span {
            float: left;
            text-align: right;
            color: #333;
            font-size: 16px;
            padding: 7px 0 0 0;
        }

        .class_select {
            width: 36%;
            float: left;
        }

        .class_input {
            width: 20%;
            float: left;
        }

        .content_info .content_form .form-group > div {
            margin-bottom: 20px;
        }
    </style>
    <script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${basePath}/js/pagination.js"></script>
    <script type="text/javascript" src="${basePath}/js/ChineseCities.min.js" charset="utf-8"></script>
    <portlet:resourceURL id="/hg/assigned" var="assigned"/>
    <portlet:resourceURL id="/hg/getMeetingTypeAndTheme" var="getMeetingTypeAndTheme"/>
    <portlet:resourceURL id="/hg/orgCheckCountExport" var="orgCheckCountExport"/>
    <portlet:resourceURL id="/hg/org/exist" var="orgexist"/>
</head>
<body>
<div class="table_form_content">
<%--    <div class="table_form_content activity_manage_container">--%>
    <div class="activity_manage_page">
        <div class="breadcrumb_group" style="margin-bottom: 20px;">
            当前位置：
            <c:if test="${portlet_name == 'party'}">
            <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">基础数据管理</a>
                        <a href="javascript:;" onclick="window.location.href='/partyusermanager'">党员信息管理</a>
                        <a href="javascript:;">党员个人信息</a>
                    </span>
            </c:if>
            <c:if test="${portlet_name == 'personal'}">
            <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">个人信息</a>
            </span>
            </c:if>

        </div>
        <div class="bg_white_container">
            <div class="content_form form_container">
                <portlet:actionURL var="addUser" name="/org/add/user">
                </portlet:actionURL>
                <form class="form-horizontal" id="addPersonForm" role="form" action="${addUser }" method="post"
                      style="width: 960px;">
                    <div class="form-group">

                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</span>
                            <div class="col-sm-9 col-xs-9">
                                <!-- <select class="form-control"  name="meetType" id="meetType">
                                  <option value="">请选择</option>
                                </select>  -->
                                <input type="text" class="form-control" name="userName" id="userName" value="${info.member_name }">
                            </div>
                        </div>

                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</span>
                            <div class="col-sm-9 col-xs-9">
                                <select class="form-control" name="sex" id="sex">
                                    <option value="" disabled>-请选择-</option>
                                    <option value="男">男</option>
                                    <option value="女">女</option>
                                </select>
                            </div>
                        </div>

                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族</span>
                            <div class="col-sm-9 col-xs-9">
                                <select class="form-control sconed_party" name="ethnicity" id="ethnicity">
                                    <option value="" disabled>请选择</option>
                                </select>
                                <%-- <input type="text" class="form-control"  name="ethnicity" id="ethnicity" value="${info.member_ethnicity }"> --%>
                            </div>
                        </div>

                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">籍&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贯</span>
                            <div class="col-sm-9 col-xs-9" style="">
                                <div class="col-sm-6 col-xs-6" style="padding-left: 0;padding-right: 6px">
                                    <select id="province" class="form-control" name="province">
                                        <option value="请选择省份" disabled>请选择省份</option>
                                    </select>
                                </div>
                                <div class="col-sm-6 col-xs-6" style="padding-left: 6px;padding-right: 0">
                                    <select id="city" class="form-control" name="city">
                                        <option value="请选择城市" disabled="">请选择城市</option>
                                    </select>
                                </div>
                            </div>
                            <input id="birth_place" type="hidden">
                        </div>

                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">出生年月</span>
                            <div class="col-sm-9 col-xs-9">
                                <input type="text" name="birthday" id="labCheckDate" value="${info.member_birthday }"
                                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="form-control start_date">
                            </div>
                        </div>

                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">入党时间</span>
                            <div class="col-sm-9 col-xs-9">
                                <input type="text" name="join_party_time" id="labCheckEndDate" value="${info.member_join_date }"
                                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="form-control start_date">
                            </div>
                        </div>

                        <div class="col-sm-6 col-xs-12">
                            <!--  <span class="col-sm-3 col-xs-3 control-label form-label-required">转正时间</span> -->
                            <span class="col-sm-3 col-xs-3 control-label">转正时间</span>
                            <div class="col-sm-9 col-xs-9">
                                <input type="text" name="turn_Time" id="turn_labCheckEndDate" value="${info.member_fomal_date }"
                                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="form-control start_date">
                            </div>
                        </div>
                        <!--
                          <div class="col-sm-6 col-xs-12">
                             <span class="col-sm-3 col-xs-3 control-label">抽查状态</span>
                             <div class="col-sm-9 col-xs-9">
                                 <select class="form-control"  name="checkState" id="checkState">
                                     <option value="">--</option>
                                     <option value="f">未抽查</option>
                                     <option value="t">已抽查</option>
                                 </select>
                             </div>
                         </div>  -->
                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">联系电话</span>
                            <div class="col-sm-9 col-xs-9">
                                <input type="text" class="form-control" placeholder="手机号码" name="telephone" id="telephone"
                                       value="${info.member_phone_number }">
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">身份证号</span>
                            <div class="col-sm-9 col-xs-9">
                                <input type="text" class="form-control" name="ID_card" id="ID_card"
                                       value="${info.member_identity }">
                            </div>
                        </div>

                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">文化程度</span>
                            <div class="col-sm-9 col-xs-9">
                                <%--  <input type="text" class="form-control"  name="educational_level" id="educational_level" value="${info.member_degree }"> --%>
                                <select class="form-control" name="educational_level" id="educational_level">
                                    <option value="" disabled>-请选择-</option>
                                    <option value="本科">本科</option>
                                    <option value="硕士研究生">硕士研究生</option>
                                    <option value="博士研究生">博士研究生</option>
                                    <option value="其他">其他</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">党员类型</span>
                            <div class="col-sm-9 col-xs-9">
                                <select class="form-control" name="party_type" id="party_type">
                                    <option value="" disabled>-请选择-</option>
                                    <option value="正式党员">正式党员</option>
                                    <option value="预备党员">预备党员</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">家庭住址</span>
                            <div class="col-sm-9 col-xs-9">
                                <input type="text" class="form-control" name="home_addrss" id="home_addrss"
                                       value="${info.member_address }">
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">人员类别</span>
                            <div class="col-sm-9 col-xs-9">
                                <select class="form-control" name="job" id="job">
                                    <option value="" disabled>-请选择-</option>

                                </select>
                                <%--  <input type="text" class="form-control"  name="job" id="job" value="${info.member_job }"> --%>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱</span>
                            <div class="col-sm-9 col-xs-9">
                                <input type="text" class="form-control" name="email" id="email" value="${info.email }">
                            </div>
                        </div>
                        <%--            <div class="col-sm-6 col-xs-12">--%>
                        <%--                <span class="col-sm-3 col-xs-3 control-label form-label-required">党内职务</span>--%>
                        <%--                <div class="col-sm-9 col-xs-9">--%>
                        <%--                    <select class="form-control" name="positior" id="positior">--%>
                        <%--                        <option value="">-请选择-</option>--%>
                        <%--                    </select>--%>
                        <%--                </div>--%>
                        <%--            </div>--%>
                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">婚姻状况</span>
                            <div class="col-sm-9 col-xs-9">
                                <select class="form-control" name="marriage" id="marriage">
                                    <option value="" disabled>-请选择-</option>
                                    <option value="已婚">已婚</option>
                                    <option value="未婚">未婚</option>
                                    <option value="离异">离异</option>
                                    <option value="丧偶">丧偶</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">党费标准</span>
                            <div class="col-sm-9 col-xs-9">
                                <input type="text" class="form-control" name="major_title" id="major_title" onblur="check(this)"
                                       onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="（元/月）" value="${info.member_major_title }">
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">行政机构</span>
                            <div class="col-sm-9 col-xs-9">
                                <select class="form-control" name="unit" id="unit">
                                    <option value="" disabled selected>-请选择-</option>
                                    <c:forEach items="${units}" var="u">
                                        <option value="${u.id}">${u.unit_name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-6 col-xs-12">
                            <span class="col-sm-3 col-xs-3 control-label form-label-required">是否处级以上干部</span>
                            <div class="col-sm-9 col-xs-9">
                                <select class="form-control" name="isLeader" id="isLeader">
                                    <option value="" disabled>-请选择-</option>
                                    <option value="是">是</option>
                                    <option value="否">否</option>
                                </select>
                            </div>
                        </div>
<%--                        <div class="col-sm-6 col-xs-12" id="sushe">--%>

<%--                        </div>--%>
                        <input type="hidden" name="seconedName" value="${seconedName }"/>
                        <input type="hidden" name="state" id="state" value="${state }"/>
                        <input type="hidden" name="prevID_card" value="${info.member_identity }"/>
                        <input type="hidden" name="id" value="${info.id }"/>
                        <input type="hidden" name="orgId" id="org_id" value="${orgId }"/>
                        <input type="hidden" name="addPersonFormId" id="addPersonFormId" value="${addpersonformid}"/>
                        <%--                <input class="btn btn_main" id="button1" type="button" value="取消"/>--%>
                        <%--                <input class="btn btn_main" id="button2" type="button" value="确定" onclick="formsubmit();"/>--%>
                        <div class="col-sm-12 col-xs-12">
                            <div class="layui-inline btn_group" style="width: calc(50% - 120px);margin: 0;margin-top: 10px;">
                                <label class="layui-form-label"></label>
                                <div class="layui-input-inline">
                                    <button type="button" id="button1" class="layui-btn" style="padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;background-color: #FFAB33;border-radius: 4px;">
                                        确定
                                    </button>
                                    <button onclick="window.history.back();" type="button" id="button2" class="layui-btn layui-btn-primary" style="background-color: transparent;color: #666;padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;border-radius: 4px;">
                                        取消
                                    </button>
                                </div>
                            </div>
                        </div>
                </form>
            </div>
        </div>
    </div>

</div>
<script type="text/javascript">
    $(function () {
        var error = "${error}";
        if (error) {
            alert(error);
        }
        var sex = "${info.member_sex}";
        var myJob = "${info.member_job }";
        var partyPositior = "${info.member_party_position}";
        console.log(partyPositior);
        var role = "${role};";
        /* var jobs=
        ${jobs}; */
        var jobs = ["本科生", "硕士研究生", "博士研究生", "行政管理人员", "专业技术人员", "其他"];
        //var new_class=["竹一","竹二","楠","桃","李","橘","梅","杏"];
        var new_class = "${room}";
        new_class = new_class.substring(1, new_class.length - 1);
        var n1 = new_class.split(",");
        for (var n = 0; n < n1.length; n++) {
            n1[n] = n1[n].replace(/(^\s*)|(\s*$)/g, "");
        }
        //var positior=["党支部书记","党支部副书记","党务秘书","普通党员"];
        var positior = "${positior}";
        //alert(positior);
        positior = positior.substring(1, positior.length - 1);
        var a = positior.split(",");
        for (var s = 0; s < a.length; s++) {
            a[s] = a[s].replace(/(^\s*)|(\s*$)/g, "");
        }
        if (role.indexOf("普通党员") >= 0) {
            $("#major_title").attr("readonly", "readonly");
        }

        // 宿舍
        /* $("#sushe").children().remove();
        if (myJob == "本科生" || myJob == "硕士研究生" || myJob == "博士研究生") {
            var classnew = "${info.member_new_class }";
            var classnew1 = classnew.substring(0, classnew.indexOf("园区"));
            var classnew2 = classnew.substring(classnew.indexOf("园区") + 2, classnew.indexOf("栋"));
            var classnew3 = classnew.substring(classnew.indexOf("栋") + 1, classnew.lastIndexOf("室"));
            //var v1="value=value.replace(/[^\w\.\/]/ig,'')";
            //var v2="value=value.replace(/[^\d]/g,'')";
            var h = $('<span class="col-sm-3 col-xs-3 control-label form-label-required">学生宿舍</span><div class="col-sm-9 col-xs-9" ><select class="form-control class_select"  name="new_class1" id="new_class1" ><option value="">-请选择-</option></select><span class="class_span">园区</span> <input type="text" class="form-control class_input" onblur="nclass2" name="new_class2" id="new_class2()" value="' + classnew2 + '"><span class="class_span">栋</span><input type="text" class="form-control class_input" onblur="nclass3()" name="new_class3" id="new_class3" value="' + classnew3 + '"><span class="class_span">室</span></div>');
            $("#sushe").append(h);
            for (var i = 0; i < n1.length; i++) {
                var option;
                if (classnew1 == n1[i]) {
                    option = $('<option selected ="selected" value="' + n1[i] + '">' + n1[i] + '</option>');
                } else {
                    option = $('<option value="' + n1[i] + '">' + n1[i] + '</option>');
                }
                $("#new_class1").append(option);
            }
        }*/
        //alert(a.length);
        for (var i = 0; i < a.length; i++) {
            var option;
            if (partyPositior == a[i]) {
                option = $('<option selected ="selected" value="' + a[i] + '">' + a[i] + '</option>');
            } else {
                option = $('<option value="' + a[i] + '">' + a[i] + '</option>');
            }
            $("#positior").append(option);
        }
        for (var p in jobs) {
            var job = jobs[p];
            var option;
            if (myJob == job) {
                option = $('<option selected ="selected" value="' + job + '">' + job + '</option>');
            } else {
                option = $('<option value="' + job + '">' + job + '</option>');
            }
            $("#job").append(option);
        }
        $('#marriage').val('${info.member_marriage}');
        /* if(sex){$("#ID_card").attr("readOnly",true);} */
        var member_type = "${info.member_type}";
        $("#party_type option").each(function () {
            if ($(this).attr("value") == member_type) {
                $(this).attr("selected", "selected");
            }
        });
        $("#job").blur(function () {
            var stype = $("#job option:selected").text();
            $("#sushe").children().remove();
            if (stype == "本科生" || stype == "硕士研究生" || stype == "博士研究生") {
                var classnew = "${info.member_new_class }";
                var classnew1 = classnew.substring(0, classnew.indexOf("园区"));
                var classnew2 = classnew.substring(classnew.indexOf("园区") + 2, classnew.indexOf("栋"));
                var classnew3 = classnew.substring(classnew.indexOf("栋") + 1, classnew.lastIndexOf("室"));
                //var v1="value=value.replace(/[^\w\.\/]/ig,'')";
                //var v2="value=value.replace(/[^\d]/g,'')";
                var h = $('<span class="col-sm-3 col-xs-3 control-label form-label-required">学生宿舍</span><div class="col-sm-9 col-xs-9" ><select class="form-control class_select"  name="new_class1" id="new_class1" ><option value="">-请选择-</option></select><span class="class_span">园区</span> <input type="text" class="form-control class_input" onblur="nclass2()" name="new_class2" id="new_class2" value="' + classnew2 + '"><span class="class_span">栋</span><input type="text" class="form-control class_input" onblur="nclass3()" name="new_class3" id="new_class3" value="' + classnew3 + '"><span class="class_span">室</span></div>');
                $("#sushe").append(h);
                console.log(n1);
                for (var i = 0; i < n1.length; i++) {
                    var option;
                    if (classnew1 == n1[i]) {
                        option = $('<option selected ="selected" value="' + n1[i] + '">' + n1[i] + '</option>');
                    } else {
                        option = $('<option value="' + n1[i] + '">' + n1[i] + '</option>');
                    }
                    $("#new_class1").append(option);
                }
                console.log(classnew + "-------" + classnew1);
            }

        })

        $("#sex option").each(function () {
            if ($(this).attr("value") == sex) {
                $(this).attr("selected", "selected");
            }
        });

        var national = [
            "汉族", "壮族", "满族", "回族", "苗族", "维吾尔族", "土家族", "彝族", "蒙古族", "藏族", "布依族", "侗族", "瑶族", "朝鲜族", "白族", "哈尼族",
            "哈萨克族", "黎族", "傣族", "畲族", "傈僳族", "仡佬族", "东乡族", "高山族", "拉祜族", "水族", "佤族", "纳西族", "羌族", "土族", "仫佬族", "锡伯族",
            "柯尔克孜族", "达斡尔族", "景颇族", "毛南族", "撒拉族", "布朗族", "塔吉克族", "阿昌族", "普米族", "鄂温克族", "怒族", "京族", "基诺族", "德昂族", "保安族",
            "俄罗斯族", "裕固族", "乌孜别克族", "门巴族", "鄂伦春族", "独龙族", "塔塔尔族", "赫哲族", "珞巴族"
        ];
        var nationa = "${info.member_ethnicity }";
        for (var i = 0; i < national.length; i++) {
            if (national[i] == nationa) {
                var option = $('<option selected="selected" value="' + national[i] + '">' + national[i] + '</option>')
                $("#ethnicity").append(option);
            } else {
                var option = $('<option value="' + national[i] + '">' + national[i] + '</option>')
                $("#ethnicity").append(option);
            }
        }
        var education = "${info.member_degree }";
        $("#educational_level option").each(function () {
            if ($(this).attr("value") == education) {
                $(this).attr("selected", "selected");
            }
        });
        var chineseCities = new ChineseCities({
            'province': 'province', //省份ID
            'city': 'city', //城市ID
            'hasSelect': function (data) {
                $("#birth_place").val(data.province + "-" + data.city);
            }//选中后回调函数
        });
        $('#province').val("${info.member_province}");
        $('#province').change();
        $('#city').val("${info.member_city}");
        $('#city').change();
        $('#unit').val('${info.member_unit}');

        function formsubmit() {
            var isContinue = true;
            $(".form-label-required").next().find("input").each(function () {
                if (!$(this).val()) {
                    isContinue = false;
                }
            });
            $(".form-label-required").next().find("select").each(function () {
                if (!$(this).val()) {
                    isContinue = false;
                }
            });
            if (!isContinue){
                alert("请完善基本信息");
                return
            }
            if ($("#ID_card").val().length !== 18) {
                isContinue = false;
                alert("请输入正确的身份证号码");
            }else if (!/^1\d{10}$/.test($("#telephone").val())) {
                isContinue = false;
                alert("请输入正确的手机号码");
            }else if (!/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/.test($("#email").val())) {
                isContinue = false;
                alert("请输入正确的邮箱号码");
            }
            if (isContinue) {
                var data = {
                    OrgId: $("#org_id").val(),
                    UserId: $("#ID_card").val()
                };
                if ("add" == $("#state").val() || $("#ID_card").val() != "${info.member_identity }") {
                    $.ajax({
                        type: "post",
                        url: "${orgexist}",
                        data: data,
                        dataType: "json",
                        async: false,
                        success: function (res) {
                            if (true == res.state) {
                                isContinue = false;
                                alert(res.message);
                            }
                        }
                    });
                }
            }else {
                alert("请完善基本信息");
            }
            if (isContinue) {
                $("#addPersonForm").submit();
            }
        }
        $('#button1').on('click', formsubmit);
    });

    function check(e) {
        var re = /^\d+(?=\.{0,1}\d+$|$)/
        if (e.value != "") {
            if (!re.test(e.value)) {
                alert("请输入正确的数字");
                e.value = "";
                e.focus();
            } else {
                var va = e.value
                if (e.value.indexOf(".") == 0) {
                    e.value = "0" + va.substring(0, 3);
                } else if (e.value.indexOf(".") < 0) {
                    e.value = va + ".00";
                } else {
                    e.value = va.substring(0, va.indexOf(".") + 3);
                }
            }
        }
    };

    function nclass3() {
        var patrn = /^[0-9]*$/;
        if (patrn.exec($("#new_class3").val()) == null) {
            alert('请输入数字');
            $("#new_class3").val("");

            $("#new_class3").focus();
        } else {
            console.log('true');
        }

    };

    function nclass2() {
        var patrn = /^[0-9a-zA-Z]+$/;
        if (patrn.exec($("#new_class2").val()) == null) {
            alert('请输入数字或英文字母');

            $("#new_class2").val("1");

            $("#new_class2").focus();
        } else {
            console.log('true');
        }
    };

</script>
</body>