<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<portlet:resourceURL id="/hg/memberEdit/page" var="transport"/>
<portlet:resourceURL id="/hg/memberEdit/approval" var="approval"/>
<portlet:resourceURL id="/hg/memberEdit/detail" var="getMemberEditInfo" />
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/activity-manage1.css?v=1" />
    <link rel="stylesheet" type="text/css" href="${basePath}/cqu/css/common.min.css" />

    <style type="text/css">
        .table_outer_box > table thead, tbody tr {
            display: table-row !important;
            width: 100%;
            table-layout: fixed;
        }
        .layui-form-checked[lay-skin=primary] i {
            border-color: #FFB800 !important;
            background-color: #FFB800;
            color: #fff;
        }

        #searchForm .layui-form-item .layui-inline .keyword {
            width: 300px;
            margin-right: 0px;
        }
        #personInfo .layui-form-item .layui-input-inline{
            width:200px
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
        $(function() {
            layui.use(['table','layer','form'], function(){
                var table = layui.table,
                    layer = layui.layer,
                    form = layui.form;
                var pageInfo = {
                    page:1,
                    size:10
                };
                renderTable(1,pageInfo.size);
                form.on('submit(searchForm)', function(data){
                    renderTable(1,pageInfo.size);
                })
                function renderTable(page,size){
                    var  where = {
                        keyword: $("#searchForm input[name=keyword]").val()
                    };
                    var ins = table.render({
                        elem: '#transportTable',
                        where: where,
                        height:560,
                        url: '${transport}', //数据接口
                        method: 'post',
                        page: {
                            limit:size,   //每页条数
                            limits:[10,15,20],
                            curr:page,
                            prev:'&lt;上一页',
                            next:'下一页&gt;',
                            theme: '#FFB800',
                            groups:4
                        },
                        cols: [[ //表头
                            {field: 'member_name', align:'center', title: '姓名'},
                            {field: 'org_name', align:'center', title: '所在支部',width:400},
                            {field: 'submit_time', align:'center', title: '申请时间'},
                            {field: 'status', align:'center', title: '任务状态'},
                            {field: 'status', align:'center', title: '操作',width:120,toolbar: '#transportBtns'},
                            {field: 'reason', align:'center', title: '备注'}
                        ]],
                        done: function(res, curr, count){
                            pageInfo.page = curr;
                            pageInfo.size = ins.config.limit;
                        }
                    });
                    $(".layui-table-view .layui-table-page").addClass("layui-table-page-center");
                    $(".layui-table-view .layui-table-page").removeClass("layui-table-page");
                    //监听事件
                    table.on('tool(transportTable)', function(obj){
                        switch(obj.event){
                            case 'check':
                                renderDetail('check',obj);
                                break;
                            case 'detail':
                                renderDetail('detail',obj);
                                break;
                        };
                    });
                }
                function renderDetail(action,obj){
                    $.ajax({
                        type : "post",
                        url : "${getMemberEditInfo}",
                        data : {id : obj.data.id},
                        dataType : "json",
                        success : function(res) {
                            if(res.code == 200){
                                $("#personInfo label[name='jobNumber']").text(res.data.job_number);
                                $("#personInfo label[name='authNumber']").text(res.data.auth_number);
                                $("#personInfo label[name='memberName']").text(res.data.member_name);
                                $("#personInfo label[name='memberSex']").text(res.data.member_sex);
                                $("#personInfo label[name='memberEthnicity']").text(res.data.member_ethnicity);
                                $("#personInfo label[name='memberJG']").text(res.data.member_province+'-'+res.data.member_city);
                                $("#personInfo label[name='memberBirthday']").text(res.data.member_birthday);
                                $("#personInfo label[name='memberJoinDate']").text(res.data.member_join_date);
                                $("#personInfo label[name='memberFomalFate']").text(res.data.member_fomal_date);
                                $("#personInfo label[name='memberPhoneNumber']").text(res.data.member_phone_number);
                                $("#personInfo label[name='memberIdentity']").text(res.data.member_identity);
                                $("#personInfo label[name='memberDegree']").text(res.data.member_degree);
                                $("#personInfo label[name='memberType']").text(res.data.member_type);
                                $("#personInfo label[name='memberAddress']").text(res.data.member_address);
                                $("#personInfo label[name='memberJob']").text(res.data.member_job);
                                $("#personInfo label[name='memberMailbox']").text(res.data.member_mailbox);
                                $("#personInfo label[name='memberMarriage']").text(res.data.member_marriage);
                                $("#personInfo label[name='memberMajorTitle']").text(res.data.member_major_title+" 元/月");
                                $("#personInfo label[name='unitName']").text(res.data.unit_name);
                                $("#personInfo label[name='memberIsLeader']").text(res.data.member_is_leader);
                                if(action == 'check'){
                                    renderCheckModal(obj);
                                } else if(action == 'detail'){
                                    renderDetailModal();
                                }

                            }else if(res){
                                layer.msg(res.message, {icon:7});
                            }else{
                                layer.msg("请刷新后再试。", {icon:7});
                            }
                        }
                    });

                }
                function renderCheckModal(obj){
                    layer.open({
                        type: 1,
                        area: ['840px', '720px'], //宽高
                        btn: ['通过','拒绝','关闭'],
                        content: $("#personInfo"),
                        shadeClose: true,
                        btn1: function(index, layero){
                            transportApprove(obj.data.id, 1,index);
                        },
                        btn2: function(index, layero){
                            transportApprove(obj.data.id, 2,index);
                            return false;
                        },
                        btn3: function(index, layero){
                        },
                    });
                }
                function renderDetailModal(){
                    layer.open({
                        type: 1,
                        area: ['840px', '680px'], //宽高
                        btn: ['关闭'],
                        content: $("#personInfo"),
                        shadeClose: true,
                        btn1: function(index, layero){
                            layer.close(index);
                        },
                    });
                }
                function transportApprove(id, status,index){
                    if(status ==1){
                        layer.confirm('您确认通过审批吗？', {
                            btn: ['确定','取消'] //按钮
                        }, function(){
                            $.post("${approval}", {id: id, type: 'transport', status: status},function (res) {
                                if (res.code == 200){
                                    layer.close(index);
                                    layer.msg('审批通过成功。')
                                    window.location.reload();
                                }
                            },"json")
                        });
                    }else if(status == 2){
                        layer.prompt({title: '拒绝原因', formType: 2}, function(text, index1){
                            $.post("${approval}", {id: id, type: 'transport', status: status,reason:text},function (res) {
                                layer.close(index1);
                                if (res.code == 200){
                                    layer.close(index);
                                    layer.msg('审批拒绝成功。')
                                    window.location.reload();
                                }
                            },"json");
                        });
                    }
                }
            });
        });


    </script>
</head>
<body>
<div class="table_form_content">
<%--    <div class="activity_manage_container">--%>

    <!-- 右侧盒子内容 -->
    <div class="activity_manage_page">
        <div class="breadcrumb_group" style="margin-bottom: 20px;">
            当前位置：
            <span class="layui-breadcrumb" lay-separator=">">
                <a href="javascript:;">基础数据管理</a>
                <a href="javascript:;">审批任务</a>
            </span>
        </div>
        <div class="bg_white_container">
            <form class="layui-form" id="searchForm">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <div class="layui-input-inline keyword">
                            <input type="text" name="keyword"  placeholder="请输入姓名、组织关键字" class="layui-input">
                        </div>
                        <button type="button"  class="layui-btn layui-btn-warm"  lay-submit="" lay-filter="searchForm"><icon class="layui-icon layui-icon-search"></icon>搜索</button>
                    </div>
                </div>
            </form>
            <table id="transportTable" lay-filter="transportTable"></table>
        </div>
    </div>
    <!-- 右侧盒子内容 -->
</div>
<script type="text/html" id="transportBtns">
    {{#  if(d.status == "待审批"){ }}
    <a class="layui-btn layui-btn-xs" lay-event="check">审批</a>
    {{#  } }}
    <a class="layui-btn layui-btn-xs red_text" lay-event="detail">查看</a>
</script>
<!-- 弹窗 -->
<div style="display: none"  id="personInfo">
    <div class="layui-form custom_form">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="jobNumber"></label>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">统一身份认证号:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="authNumber"></label>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberName"></label>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberSex"></label>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberEthnicity"></label>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">籍&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贯:</label>
                <div class="layui-input-inline" style="">
                    <label class="layui-form-label-text" name="memberJG"></label>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">出生年月:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberBirthday"></label>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">入党时间:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberJoinDate"></label>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">转正时间:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberFomalDate"></label>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">联系电话:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberPhoneNumber"></label>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">身份证号:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberIdentity"></label>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">文化程度:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberDegree"></label>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">党员类型:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberType"></label>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">家庭住址:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberAddress"></label>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">人员类别:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberJob"></label>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberMailbox"></label>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">婚姻状况:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberMarriage"></label>
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">党费标准:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberMajorTitle"></label>
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">行政机构:</label>
                <div class="layui-input-inline" name="unitName"></div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">是否处级以上干部:</label>
                <div class="layui-input-inline">
                    <label class="layui-form-label-text" name="memberIsLeader"></label>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>