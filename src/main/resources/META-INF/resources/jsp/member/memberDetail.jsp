<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<head>
    <title>党员信息</title>
    <style>
        .content_info .content_form .form-group > div {
            margin-bottom: 20px;
        }
        .layui-form-label.layui-required:before{
            content: "*";
            color: red;
            top: 5px;
            right: 2px;
            position: relative;
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
        .org-path{
            background-color: #ffab33;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="table_form_content">
    <div class="activity_manage_page">
        <div class="breadcrumb_group" style="margin-bottom: 20px;">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                <a href="javascript:;">基础数据管理</a>
                    <a href="javascript:;">党员信息管理</a>
                        <a href="javascript:;">个人信息</a>
            </span>
        </div>
        <div class="bg_white_container">
            <div class="content_form form_container">
                <div class="layui-card">
                    <div class="layui-card-header org-path">所属党组织：<c:if test="${info != null}">
                        ${info.o_org_name}>${info.s_org_name}>${info.org_name}
                    </c:if>
                    </div>
                </div>
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
                                <label class="layui-form-label-text">${info.member_mailbox }</label>
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
                                <label class="layui-form-label-text">
                                    <c:if test="${info.member_major_title ==null || info.member_major_title=='' }"></c:if>
                                    <c:if test="${info.member_major_title !=null && info.member_major_title!='' }">${info.member_major_title } 元/月</label></c:if>
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
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.job_number }</label>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">统一身份认证号:</label>
                            <div class="layui-input-inline">
                                <label class="layui-form-label-text">${info.auth_number }</label>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item" style="text-align: center;margin-top: 40px;">
                        <button type="button" class="layui-btn layui-btn-radius layui-btn-warm" onclick="window.history.back();">返回</button>
                    </div>
                    <input type="hidden" name="id" value="${info.id }"/>
                </div>
            </div>
        </div>
    </div>

</div>
</body>