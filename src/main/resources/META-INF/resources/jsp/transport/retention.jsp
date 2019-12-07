<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<portlet:resourceURL id="/retention/save" var="save"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/organ-relation-retention.css" />
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css" />
    <link rel="stylesheet" href="${basePath}/css/jquery.dropdown.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery.dropdown.js?v=11"></script>

    <style type="text/css">
        .main_content{
            overflow-y: auto;
        }
        .organ_retention_container .organ_retention_page .form_content .table_form .custom_form .layui-inline .layui-form-label {
            margin-bottom: 0;
        }
    </style>
    <script type="text/javascript" >
        $(function() {
            layui.use('form', function(){
                var form = layui.form;
                //表单提交
                form.on('submit(retentionForm)', function(data){
                    // layer.alert(JSON.stringify(data.field), {
                    //     title: '最终的提交信息'
                    // });
                    console.log(JSON.stringify(data.field));
                    $.post("${save}", data.field, function (res) {
                        if(res.result){
                            alert("提交成功");
                        }else {
                            alert(res.message);
                        }
                    });
                    return false;
                });

            });
            //时间选择器
            layui.use('laydate', function(){
                var laydate = layui.laydate;
                //执行一个laydate实例
                laydate.render({
                    elem: '#date1', //指定元素
                });
                laydate.render({
                    elem: '#date2', //指定元素
                });
                laydate.render({
                    elem: '#date3', //指定元素
                });
                laydate.render({
                    elem: '#date4', //指定元素
                });
            });


        });
    </script>
</head>
<body>
<div class="organ_retention_container table_form_content">
    <!-- 右侧盒子内容 -->
    <div class="organ_retention_page">
        <div class="breadcrumb_group">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                        <a href="javascript:;">组织关系转接</a>
                        <a href="javascript:;">组织关系保留</a>
                    </span>
        </div>
        <div class="form_content">
            <p class="form_title">出国保留组织关系信息填写说明</p>
            <div class="table_form">
                <div class="title_label">
                    <p>基本信息</p>
                    <p>详细信息</p>
                </div>
                <form class="layui-form custom_form" id="retentionForm">
                    <div class="layui-inline">
                        <label class="layui-form-label">姓名：</label>
                        <div class="layui-input-inline">
                            <input name="name" type="text" maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">性别：</label>
                        <div class="layui-input-inline">
                            <select name="sex" lay-filter="">
                                <option value="男">男</option>
                                <option value="女" selected="">女</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">民族：</label>
                        <div class="layui-input-inline">
                            <input name="ethnicity" type="text" maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">党员类型：</label>
                        <div class="layui-input-inline">
                            <select name="memberType" lay-filter="">
                                <option value="正式党员">正式党员</option>
                                <option value="预备党员" selected="">预备党员</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline datepicker_item">
                        <label class="layui-form-label">入党日期：</label>
                        <div class="layui-input-inline">
                            <input name="joinDate" type="text" id="date1" lay-verify="date" autocomplete="off" class="layui-input custom_input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">现所在支部：</label>
                        <div class="layui-input-inline">
                            <input name="org" type="text" maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">编入支部名称：</label>
                        <div class="layui-input-inline">
                            <input name="to_org_name" type="text" maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">编入支部联系人：</label>
                        <div class="layui-input-inline">
                            <input name="to_org_contact" type="text" maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline datepicker_item mrt8">
                        <label class="layui-form-label">出生日期：</label>
                        <div class="layui-input-inline">
                            <input name="birthday" type="text" id="date2" lay-verify="date" autocomplete="off" class="layui-input custom_input">
                        </div>
                    </div>
                    <div class="layui-inline mrt8">
                        <label class="layui-form-label">籍贯：</label>
                        <div class="layui-input-inline">
                            <input name="birthplace" type="text" maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">联系方式：</label>
                        <div class="layui-input-inline">
                            <input name="contact" type="text" maxlength="20" lay-verify="required|phone" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">QQ号码：</label>
                        <div class="layui-input-inline">
                            <input name="qq" type="text" maxlength="20" lay-verify="required|number" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">微信号：</label>
                        <div class="layui-input-inline">
                            <input name="wechat" type="text" maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">常用邮箱：</label>
                        <div class="layui-input-inline">
                            <input name="email" type="text" maxlength="20" lay-verify="required|email" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">出国期限：</label>
                        <div class="layui-input-inline">
                            <input name="foreignLimit" type="text" maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline datepicker_item">
                        <label class="layui-form-label">出境日期：</label>
                        <div class="layui-input-inline">
                            <input name="aboardDate" type="text" id="date3" lay-verify="date" autocomplete="off" class="layui-input custom_input">
                        </div>
                    </div>
                    <div class="layui-inline datepicker_item">
                        <label class="layui-form-label">返境日期：</label>
                        <div class="layui-input-inline">
                            <input name="returnDate" type="text" id="date4" lay-verify="date" autocomplete="off" class="layui-input custom_input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">前往国家及学习单位：</label>
                        <div class="layui-input-inline">
                            <input name="toCountry" type="text" maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">攻读学位：</label>
                        <div class="layui-input-inline">
                            <input name="studyDegree" type="text" maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">学历：</label>
                        <div class="layui-input-inline">
                            <select name="currentDegree" lay-filter="">
                                <option value="本科">本科</option>
                                <option value="研究生" selected="">研究生</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">学习方式：</label>
                        <div class="layui-input-inline">
                            <select name="studyType" lay-filter="">
                                <option value="自费">自费</option>
                                <option value="公派" selected="">公派</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">国内家庭住址：</label>
                        <div class="layui-input-inline">
                            <input name="domestic" type="text" maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">国内联系人：</label>
                        <div class="layui-input-inline">
                            <input name="domesticContactPerson" type="text" maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">国内联系人手机号：</label>
                        <div class="layui-input-inline">
                            <input name="domesticContactPhone" type="text" maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline table_text_container">
                        <label class="layui-form-label">备注：</label>
                        <div class="layui-input-inline">
                            <input name="extra" type="text" maxlength="20" lay-verify="required" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline btn_group" style="border-bottom: none;">
                        <label class="layui-form-label"></label>
                        <div class="layui-input-inline">
                            <button type="submit" class="layui-btn" lay-submit="" lay-filter="retentionForm">提交</button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
</body>
</html>