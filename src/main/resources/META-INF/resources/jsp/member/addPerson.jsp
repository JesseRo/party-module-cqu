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
        .layui-form-label.layui-required:after{
            content:"*";
            color:red;
            position: absolute;
            top:5px;
            right:2px;
        }
        .table_form_content .custom_form .layui-form-label{
            padding: 0 10px;
            width: 160px;
        }
        .layui-form-item .layui-input-inline{
            width: 260px;
        }
    </style>
    <script type="text/javascript" src="${basePath}/js/jquery.jqprint-0.3.js"></script>
    <script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${basePath}/js/pagination.js"></script>
    <script type="text/javascript" src="${basePath}/js/ChineseCities.min.js" charset="utf-8"></script>
    <link rel="stylesheet" href="${basePath}/css/print_div.css">
    <portlet:resourceURL id="/hg/assigned" var="assigned"/>
    <portlet:resourceURL id="/hg/getMeetingTypeAndTheme" var="getMeetingTypeAndTheme"/>
    <portlet:resourceURL id="/hg/orgCheckCountExport" var="orgCheckCountExport"/>
    <portlet:resourceURL id="/hg/org/exist" var="orgexist"/>
    <portlet:resourceURL id="/org/add/user" var="addUser"/>
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

                <form class="layui-form custom_form"  id="addPersonForm"
                      style="width: 960px;">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" name="userName" id="userName" value="${info.member_name }" lay-verify="required">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</label>
                            <div class="layui-input-inline">
                                <select  name="sex" id="sex" lay-verify="select" >
                                    <option value="" >-请选择-</option>
                                    <option value="男">男</option>
                                    <option value="女">女</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族</label>
                            <div class="layui-input-inline">
                                <select class="layui-input sconed_party" name="ethnicity" id="ethnicity" lay-verify="required">
                                    <option value="" disabled>请选择</option>
                                    <c:forEach var="n" items="${nationalArr }">
                                        <option value="${n}">${n}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">籍&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贯</label>
                            <div class="layui-input-inline" style="">
                                <div class="col-sm-6 col-xs-6" style="padding-left: 0;padding-right: 6px">
                                    <select id="province" class="layui-input" name="province"  lay-ignore lay-verify="select">
                                        <option value="" disabled>-请选择-</option>
                                    </select>
                                </div>
                                <div class="col-sm-6 col-xs-6" style="padding-left: 6px;padding-right: 0">
                                    <select id="city" class="layui-input" name="city" lay-ignore lay-verify="select">
                                        <option value="" disabled="">请选择城市</option>
                                    </select>
                                </div>
                            </div>
                            <input id="birth_place" type="hidden">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">出生年月</label>
                            <div class="layui-input-inline">
                                <input type="text" name="birthday" id="labCheckDate" value="${info.member_birthday }" class="layui-input start_date" lay-verify="required">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">入党时间</label>
                            <div class="layui-input-inline">
                                <input type="text" name="join_party_time" id="labCheckEndDate" value="${info.member_join_date }"
                                       class="layui-input start_date" lay-verify="required">
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">转正时间</label>
                            <div class="layui-input-inline">
                                <input type="text" name="turn_Time" id="turn_labCheckEndDate" value="${info.member_fomal_date }"
                                        class="layui-input start_date" lay-verify="required">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">联系电话</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" placeholder="手机号码" name="telephone" id="telephone" lay-verify="contactNumber"
                                       value="${info.member_phone_number }">
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">身份证号</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" name="ID_card" id="ID_card" lay-verify="idCard"
                                       value="${info.member_identity }">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">文化程度</label>
                            <div class="layui-input-inline">
                                <%--  <input type="text" class="layui-input"  name="educational_level" id="educational_level" value="${info.member_degree }"> --%>
                                <select class="layui-input" name="educational_level" id="educational_level" lay-verify="select">
                                    <option value="" disabled>-请选择-</option>
                                    <option value="本科">本科</option>
                                    <option value="硕士研究生">硕士研究生</option>
                                    <option value="博士研究生">博士研究生</option>
                                    <option value="其他">其他</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">党员类型</label>
                            <div class="layui-input-inline">
                                <select class="layui-input" name="party_type" id="party_type" lay-verify="select">
                                    <option value="" disabled>-请选择-</option>
                                    <option value="正式党员">正式党员</option>
                                    <option value="预备党员">预备党员</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">家庭住址</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" name="home_addrss" id="home_addrss" lay-verify="required"
                                       value="${info.member_address }">
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">人员类别</label>
                            <div class="layui-input-inline">
                                <select class="layui-input" name="job" id="job" lay-verify="select">
                                    <option value="" disabled>-请选择-</option>
                                    <c:forEach var="j" items="${jobArr }">
                                        <option value="${j}">${j}</option>
                                    </c:forEach>
                                </select>
                                <%--  <input type="text" class="layui-input"  name="job" id="job" value="${info.member_job }"> --%>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" name="email" id="email" value="${info.email }" lay-verify="partyEmail">
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">婚姻状况</label>
                            <div class="layui-input-inline">
                                <select class="layui-input" name="marriage" id="marriage" lay-verify="select">
                                    <option value="" disabled>-请选择-</option>
                                    <option value="已婚">已婚</option>
                                    <option value="未婚">未婚</option>
                                    <option value="离异">离异</option>
                                    <option value="丧偶">丧偶</option>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">党费标准</label>
                            <div class="layui-input-inline">
                                <input type="text" class="layui-input" name="major_title" id="major_title" onblur="check(this)"
                                       onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" placeholder="（元/月）" value="${info.member_major_title }">
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">行政机构</label>
                            <div class="layui-input-inline">
                                <select class="layui-input" name="unit" id="unit" lay-verify="select">
                                    <option value="" disabled selected>-请选择-</option>
                                    <c:forEach items="${units}" var="u">
                                        <option value="${u.id}">${u.unit_name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label layui-required">是否处级以上干部</label>
                            <div class="layui-input-inline">
                                <select class="layui-input" name="isLeader" id="isLeader" lay-verify="select">
                                    <option value="" disabled>-请选择-</option>
                                    <option value="是">是</option>
                                    <option value="否">否</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" name="seconedName" value="${seconedName }"/>
                    <input type="hidden" name="state" id="state" value="${state }"/>
                    <input type="hidden" name="prevID_card" value="${info.member_identity }"/>
                    <input type="hidden" name="id" value="${info.id }"/>
                    <input type="hidden" name="orgId" id="org_id" value="${orgId }"/>
                    <input type="hidden" name="addPersonFormId" id="addPersonFormId" value="${addpersonformid}"/>
                    <div class="layui-form-item">
                        <div class="layui-inline btn_group" style="width: 100%;margin: 0;margin-top: 10px;">
                            <label class="layui-form-label"></label>
                            <div class="layui-input-inline">
                                <button  type="button" class="layui-btn layui-btn-warm" lay-submit="" lay-filter="addPersonForm">保 存</button>
                                <button onclick="window.history.back();" type="button" id="button2" class="layui-btn layui-btn-primary" style="background-color: transparent;color: #666;padding: 0 20px;font-size: 16px;height: 40px;line-height: 40px;border-radius: 4px;">
                                    取消
                                </button>
                            </div>
                            <c:if test="${portlet_name == 'personal'}">
                                <button type="button" id="button3" class="layui-btn layui-btn-primary" style="background-color: transparent;color: #666;padding: 0 20px;font-size:
                                    16px;height: 40px;line-height: 40px;border-radius: 4px;float: right;">
                                    打印个人信息表
                                </button>
                                <div id="div_print">
                                     <style type="text/css">@page {size: auto; margin-top:0; margin-bottom: 0;}</style>
                                    <p style="white-space: normal; text-align: center;"><span style="font-family: 黑体, SimHei; font-size: 24px;"><strong>党员基本信息采集表</strong></span><span style="font-size: 36px; font-family: 黑体, SimHei;"><br/></span></p><p style="white-space: normal;"><br/></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">1.姓名：</span></strong><span style="font-family: 黑体, SimHei;">${info.member_name}&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <strong>2.性别：</strong>&nbsp;${info.member_sex}&nbsp; &nbsp; &nbsp; &nbsp;<strong>3.民族：</strong>${info.member_ethnicity}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">4.公民身份证号</span></strong><span style="font-family: 黑体, SimHei;">：${info.member_identity}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">5.出生日期：</span></strong><span style="font-family: 黑体, SimHei;">${info.member_birthday}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">6.学历</span></strong><span style="font-family: 黑体, SimHei;">（<span style="font-family: 楷体, 楷体_GB2312, SimKai;">参照《学习代码》填写相应代码</span>）：<strong style="white-space: normal;"><span style="font-family: 黑体, SimHei;">□□</span></strong><strong style="white-space: normal;"><span style="font-family: 黑体, SimHei;"></span></strong><strong style="white-space: normal;"></strong></span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">7.人员类别：</span></strong><span style="font-family: 黑体, SimHei;">${info.member_type}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">8.所在党支部：</span></strong><span style="font-family: 黑体, SimHei;">${info.org_name}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">9.加入党组织日期：</span></strong><span style="font-family: 黑体, SimHei;">${info.member_join_date}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong style="font-size: 18px;"><span style="font-family: 黑体, SimHei;">10.转为正式党员日期：</span></strong><span style="font-family: 黑体, SimHei;">${info.member_formal_Date}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">11.工作岗位</span></strong><span style="font-family: 黑体, SimHei;">（<span style="font-family: 楷体, 楷体_GB2312, SimKai;">参照《工作岗位代码》填写相应代码</span>）：<strong style="white-space: normal;"><span style="font-family: 黑体, SimHei;">□□□□</span></strong><strong style="white-space: normal;"><span style="font-family: 黑体, SimHei;"></span></strong><strong style="white-space: normal;"><span style="font-family: 黑体, SimHei;"></span></strong><strong style="white-space: normal;"></strong></span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">12.联系电话（手机号）：${info.member_phone_number}</span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">（固定电话）：（区号）□□□□+（电话）□□□□□</span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong><strong><span style="font-family: 黑体, SimHei;">□□□</span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">13.家庭住址（具体到门牌号）：</span></strong><span style="font-family: 黑体, SimHei;">${info.member_address}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">14.党籍状态： 正常□&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;停止党籍□</span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">15.是否为失联党员： 是□&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;失去联系日期<span style="text-decoration-line: underline;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span>年</span><span style="font-family: 黑体, SimHei; text-decoration-line: underline;">&nbsp; &nbsp;&nbsp;</span><span style="font-family: 黑体, SimHei;">月</span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;否□</span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">16.是否为流动党员（由流出地党组织负责采集）： 是□&nbsp; &nbsp; 否□</span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 外出流向：<span style="font-family: 黑体, SimHei; text-decoration: underline;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span></span><span style="font-family: 黑体, SimHei; text-decoration: none;">省（区、市）</span><span style="font-family: 黑体, SimHei; text-decoration: underline;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="font-family: 黑体, SimHei; text-decoration: none;">市（地、</span></strong><strong><span style="font-family: 黑体, SimHei; text-decoration: none;"></span></strong></span></p><p style="white-space: normal;"><span style="font-size: 18px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong><span style="font-family: 黑体, SimHei; text-decoration: none;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 州、盟）</span><span style="font-family: 黑体, SimHei; text-decoration: underline;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="font-family: 黑体, SimHei; text-decoration: none;">县（市、区、旗）</span></strong><strong><span style="font-family: 黑体, SimHei; text-decoration: none;"></span></strong><strong><span style="font-family: 黑体, SimHei; text-decoration: none;"></span></strong><strong><span style="font-family: 黑体, SimHei; text-decoration: none;"></span></strong></span></p><p style="white-space: normal;"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;<span style="font-size: 18px; font-family: 黑体, SimHei; text-decoration: underline;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span></strong></p><p style="white-space: normal;"><span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei; font-size: 24px;"></span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">党员（签字）：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;信息采集员（签字）：</span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong style="font-size: 18px;"><span style="font-family: 黑体, SimHei;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 年&nbsp; &nbsp;月&nbsp; &nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp; &nbsp; 月&nbsp; &nbsp; 日</span></strong></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-family: 黑体, SimHei; font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">党支部书记（签字）：&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;基层党委（盖章）：</span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-family: 黑体, SimHei; font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">&nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 年&nbsp; &nbsp;月&nbsp; &nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp; &nbsp; 月&nbsp; &nbsp; 日</span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-family: 黑体, SimHei; font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">&nbsp; &nbsp; &nbsp;</span></strong></span></p><p style="white-space: normal;"><span style="font-family: 黑体, SimHei; font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：此表由党员本人确认，党支部（党总支、党委）初核，基层党委</span></strong></span><strong style="font-family: 黑体, SimHei; font-size: 18px;">复核。</strong></p><p style="text-align: center;"><br/></p>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

</div>
<script type="text/javascript">
    $(function () {
        console.log("${nationalArr}");
        layui.use(['form', 'laydate','layer'], function() {
            var laydate = layui.laydate,
                layer = layui.layer,
                form = layui.form;
            laydate.render({elem: '#labCheckDate'});
            laydate.render({elem: '#labCheckEndDate'});
            laydate.render({elem: '#turn_labCheckEndDate'});
            form.on('submit(addPersonForm)', function (data) {

                var postData = data.field;
                $.post("${addUser}", postData, function (res) {
                    if(res.code==200){
                        layer.msg(res.message);
                    }else if(res.code == 402) {
                        layer.msg(res.message);
                    }else if(res){
                        layer.msg(res.message);
                    }else{
                        layer.msg("请刷新后再试。", {icon:7});
                    }
                },"json");
                return false;
            });
            form.verify({
                idCard: function (value, item) {
                    if (validateIdCard(value) != 1) {
                        return '请填入正确的身份证号。';
                    }
                },
                partyEmail: function (value, item) {
                    var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                    if (value == '' || value == null || !reg.test(value)) {
                        return "邮箱格式不正确。";
                    }
                },
                contactNumber: function (value, item) {
                    var regPhone = /^1\d{10}$/;
                    var reg = /^((\d{3,4}-)|\d{3.4}-)?\d{7,8}$/;
                    if (value == '' || value == null || (!regPhone.test(value) && !reg.test(value))) {
                        return "联系电话格式不正确。";
                    }
                },
                select: function (value, item) {
                    if (value == '' || value == null) {
                        return "请选择必填项。";
                    }
                }
            });

            /*
             * 身份证15位编码规则：dddddd yymmdd xx p
             * dddddd：6位地区编码
             * yymmdd: 出生年(两位年)月日，如：910215
             * xx: 顺序编码，系统产生，无法确定
             * p: 性别，奇数为男，偶数为女
             *
             * 身份证18位编码规则：dddddd yyyymmdd xxx y
             * dddddd：6位地区编码
             * yyyymmdd: 出生年(四位年)月日，如：19910215
             * xxx：顺序编码，系统产生，无法确定，奇数为男，偶数为女
             * y: 校验码，该位数值可通过前17位计算获得
             *
             * 前17位号码加权因子为 Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ]
             * 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]
             * 如果验证码恰好是10，为了保证身份证是十八位，那么第十八位将用X来代替
             * 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )
             * i为身份证号码1...17 位; Y_P为校验码Y所在校验码数组位置
            */
            function validateIdCard(idCard) {
                var res = 0;
                //15位和18位身份证号码的正则表达式
                var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;

                //如果通过该验证，说明身份证格式正确，但准确性还需计算
                if (regIdCard.test(idCard)) {
                    if (idCard.length == 18) {
                        var idCardWi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); //将前17位加权因子保存在数组里
                        var idCardY = new Array(1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2); //这是除以11后，可能产生的11位余数、验证码，也保存成数组
                        var idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
                        for (var i = 0; i < 17; i++) {
                            idCardWiSum += idCard.substring(i, i + 1) * idCardWi[i];
                        }
                        var idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
                        var idCardLast = idCard.substring(17);//得到最后一位身份证号码
                        //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
                        if (idCardMod == 2) {
                            if (idCardLast == "X" || idCardLast == "x") {
                                res = 1;
                            }
                        } else {
                            //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                            if (idCardLast == idCardY[idCardMod]) {
                                res = 1;
                            }
                        }
                    }
                }
                return res;
            }
            $('#button3').on('click', function () {

                $('#div_print').jqprint(
                    {
                        debug: false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
                        importCSS: false, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
                        printContainer: true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
                        operaSupport: false//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
                    }
                )
            });
            var error = "${error}";
            if (error) {
                alert(error);
            }

            var sex = "${info.member_sex}";
            var myJob = "${info.member_job }";
            var partyPositior = "${info.member_party_position}";
            console.log(partyPositior);
            var role = "${role};";
            /* var jobs=${jobs}; */
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

            for (var i = 0; i < a.length; i++) {
                var option;
                if (partyPositior == a[i]) {
                    option = $('<option selected ="selected" value="' + a[i] + '">' + a[i] + '</option>');
                } else {
                    option = $('<option value="' + a[i] + '">' + a[i] + '</option>');
                }
                $("#positior").append(option);
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
                    var h = $('<span class="layui-form-label layui-required">学生宿舍</span><div class="layui-input-inline" ><select class="layui-input class_select"  name="new_class1" id="new_class1" ><option value="">-请选择-</option></select><span class="class_span">园区</span> <input type="text" class="layui-input class_input" onblur="nclass2()" name="new_class2" id="new_class2" value="' + classnew2 + '"><span class="class_span">栋</span><input type="text" class="layui-input class_input" onblur="nclass3()" name="new_class3" id="new_class3" value="' + classnew3 + '"><span class="class_span">室</span></div>');
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


            var nationa = "${info.member_ethnicity }";
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
            form.render('select')
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
        })


</script>
</body>