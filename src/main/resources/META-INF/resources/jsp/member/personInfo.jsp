<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<head>
    <title>抽查统计</title>
    <style>
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
        .layui-form-label-text{
            float: left;
            display: block;
            padding: 0 10px;
            width: 260px;
            font-weight: 400;
            line-height: 40px;
            font-size: 16px;
            text-align: left;
        }
        #personInfo .layui-form-item{
            margin-bottom: 0px;
        }
        #personInfo .layui-inline{
            margin-bottom: 0px;
        }
        #personInfo .layui-form-label{
            font-weight: bold;
        }
        #personInfo .layui-btn-radius{
            border-radius:100px;
        }
    </style>
    <script type="text/javascript" src="${basePath}/js/jquery.jqprint-0.3.js"></script>
    <link rel="stylesheet" href="${basePath}/css/print_div.css">
    <portlet:renderURL var="applyUpdatePersonInfo">
        <portlet:param name="mvcRenderCommandName" value="/org/user/applyUpdate" />
    </portlet:renderURL>
</head>
<body>
<div class="table_form_content">
    <div class="activity_manage_page">
        <div class="breadcrumb_group" style="margin-bottom: 20px;">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">个人信息</a>
            </span>
        </div>
        <div class="bg_white_container">
            <div class="content_form form_container">
                <div class="layui-form custom_form"  id="personInfo"
                     style="width: 960px;">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.member_name }</label>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.member_sex }</label>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.member_ethnicity }</label>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">籍&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贯:</label>
                            <div class="layui-input-inline" style="">
                                <label class="layui-form-label-text">${info.member_province}-${info.member_city}</label>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">出生年月:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.member_birthday }</label>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">入党时间:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.member_join_date }</label>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">转正时间:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.member_fomal_date }</label>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">联系电话:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.member_phone_number }</label>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">身份证号:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.member_identity }</label>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">文化程度:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.member_degree }</label>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">党员类型:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.member_type }</label>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">家庭住址:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.member_address }</label>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">人员类别:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.member_job }</label>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.email }</label>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">婚姻状况:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.member_marriage }</label>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">党费标准:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.member_major_title } 元/月</label>
                            </div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">行政机构:</label>
                            <div class="layui-input-inline">
                                <c:forEach items="${units}" var="u">
                                    <c:if test="${info.member_unit == u.id }"> <label class="layui-form-label-text">${u.unit_name}</label></c:if>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">是否处级以上干部:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.member_is_leader }</label>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item" style="text-align: center;margin-top: 40px;">
                        <button type="button" class="layui-btn layui-btn-radius layui-btn-warm" onclick="applyUpdate()">申请修改个人信息</button>
                        <button type="button"  class="layui-btn layui-btn-radius layui-btn-warm" onclick="printInfo()">打印个人信息表</button>
                        <div id="div_print">
                             <style type="text/css">@page {size: auto; margin-top:0; margin-bottom: 0;}</style>
                            <p style="white-space: normal; text-align: center;"><span style="font-family: 黑体, SimHei; font-size: 24px;"><strong>党员基本信息采集表</strong></span><span style="font-size: 36px; font-family: 黑体, SimHei;"><br/></span></p><p style="white-space: normal;"><br/></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">1.姓名：</span></strong><span style="font-family: 黑体, SimHei;">${info.member_name}&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <strong>2.性别：</strong>&nbsp;${info.member_sex}&nbsp; &nbsp; &nbsp; &nbsp;<strong>3.民族：</strong>${info.member_ethnicity}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">4.公民身份证号</span></strong><span style="font-family: 黑体, SimHei;">：${info.member_identity}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">5.出生日期：</span></strong><span style="font-family: 黑体, SimHei;">${info.member_birthday}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">6.学历</span></strong><span style="font-family: 黑体, SimHei;">（<span style="font-family: 楷体, 楷体_GB2312, SimKai;">参照《学习代码》填写相应代码</span>）：<strong style="white-space: normal;"><span style="font-family: 黑体, SimHei;">□□</span></strong><strong style="white-space: normal;"><span style="font-family: 黑体, SimHei;"></span></strong><strong style="white-space: normal;"></strong></span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">7.人员类别：</span></strong><span style="font-family: 黑体, SimHei;">${info.member_type}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">8.所在党支部：</span></strong><span style="font-family: 黑体, SimHei;">${info.org_name}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">9.加入党组织日期：</span></strong><span style="font-family: 黑体, SimHei;">${info.member_join_date}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong style="font-size: 18px;"><span style="font-family: 黑体, SimHei;">10.转为正式党员日期：</span></strong><span style="font-family: 黑体, SimHei;">${info.member_formal_Date}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">11.工作岗位</span></strong><span style="font-family: 黑体, SimHei;">（<span style="font-family: 楷体, 楷体_GB2312, SimKai;">参照《工作岗位代码》填写相应代码</span>）：<strong style="white-space: normal;"><span style="font-family: 黑体, SimHei;">□□□□</span></strong><strong style="white-space: normal;"><span style="font-family: 黑体, SimHei;"></span></strong><strong style="white-space: normal;"><span style="font-family: 黑体, SimHei;"></span></strong><strong style="white-space: normal;"></strong></span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">12.联系电话（手机号）：${info.member_phone_number}</span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">（固定电话）：（区号）□□□□+（电话）□□□□□</span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong><strong><span style="font-family: 黑体, SimHei;">□□□</span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">13.家庭住址（具体到门牌号）：</span></strong><span style="font-family: 黑体, SimHei;">${info.member_address}</span></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">14.党籍状态： 正常□&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;停止党籍□</span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">15.是否为失联党员： 是□&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;失去联系日期<span style="text-decoration-line: underline;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span>年</span><span style="font-family: 黑体, SimHei; text-decoration-line: underline;">&nbsp; &nbsp;&nbsp;</span><span style="font-family: 黑体, SimHei;">月</span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;否□</span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">16.是否为流动党员（由流出地党组织负责采集）： 是□&nbsp; &nbsp; 否□</span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong><strong><span style="font-family: 黑体, SimHei;"></span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 外出流向：<span style="font-family: 黑体, SimHei; text-decoration: underline;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span></span><span style="font-family: 黑体, SimHei; text-decoration: none;">省（区、市）</span><span style="font-family: 黑体, SimHei; text-decoration: underline;">&nbsp; &nbsp; &nbsp; &nbsp; </span><span style="font-family: 黑体, SimHei; text-decoration: none;">市（地、</span></strong><strong><span style="font-family: 黑体, SimHei; text-decoration: none;"></span></strong></span></p><p style="white-space: normal;"><span style="font-size: 18px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong><span style="font-family: 黑体, SimHei; text-decoration: none;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 州、盟）</span><span style="font-family: 黑体, SimHei; text-decoration: underline;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span style="font-family: 黑体, SimHei; text-decoration: none;">县（市、区、旗）</span></strong><strong><span style="font-family: 黑体, SimHei; text-decoration: none;"></span></strong><strong><span style="font-family: 黑体, SimHei; text-decoration: none;"></span></strong><strong><span style="font-family: 黑体, SimHei; text-decoration: none;"></span></strong></span></p><p style="white-space: normal;"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;<span style="font-size: 18px; font-family: 黑体, SimHei; text-decoration: underline;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span></strong></p><p style="white-space: normal;"><span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei; font-size: 24px;"></span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">党员（签字）：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;信息采集员（签字）：</span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong style="font-size: 18px;"><span style="font-family: 黑体, SimHei;">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 年&nbsp; &nbsp;月&nbsp; &nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp; &nbsp; 月&nbsp; &nbsp; 日</span></strong></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-family: 黑体, SimHei; font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">党支部书记（签字）：&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;基层党委（盖章）：</span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-family: 黑体, SimHei; font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">&nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 年&nbsp; &nbsp;月&nbsp; &nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp; &nbsp; 月&nbsp; &nbsp; 日</span></strong></span></p><p style="white-space: normal;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-family: 黑体, SimHei; font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">&nbsp; &nbsp; &nbsp;</span></strong></span></p><p style="white-space: normal;"><span style="font-family: 黑体, SimHei; font-size: 18px;"><strong><span style="font-family: 黑体, SimHei;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：此表由党员本人确认，党支部（党总支、党委）初核，基层党委</span></strong></span><strong style="font-family: 黑体, SimHei; font-size: 18px;">复核。</strong></p><p style="text-align: center;"><br/></p>
                        </div>
                    </div>
                    <input type="hidden" name="id" value="${info.id }"/>
                </div>
            </div>
        </div>
    </div>

</div>
<script>
    function applyUpdate(){
        window.location.href='${applyUpdatePersonInfo}';
    }
    function printInfo(){
        $('#div_print').jqprint(
            {
                debug: false, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
                importCSS: false, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
                printContainer: true, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
                operaSupport: false//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
            }
        )
    }
</script>
</body>