<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>角色选择</title>
    <%--  <link rel="stylesheet" href="${basePath }/css/common.css" /> --%>
    <link rel="stylesheet" href="${basePath }/css/login.css"/>
    <link rel="stylesheet" href="${basePath }/css/bootstrap.min.css"/>
</head>

<body>
<div class="col-sm-6 col-sm-offset-3" style="height:660px;margin-top:100px;">
    <form class="form-horizontal" role="form">
        <div class="form-group">
            <div class="col-sm-12 select_role" style="text-align: center;">

            </div>
        </div>
        <div class="form-group login_btn">
            <div class="col-sm-2 col-sm-offset-5 ">
                <button type="button" onclick="ajaxLogin();" style="background-color: #ce0000; color: white;"
                        class="btn btn-default form-control">确认
                </button>
            </div>
        </div>
    </form>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<portlet:resourceURL id="/hg/ajaxLogin" var="ajaxLogin"/>
<portlet:resourceURL id="/hg/getRole" var="getRole"/>
<script>

    $(function () {
        var userName = "${userName}";
        var url = "${getRole}";
        var input;
        $.ajax({
            url: url,
            data: {"userName": userName, "type": "cas"},
            dataType: "text",
            success: function (result) {
                if (result.length > 0) {
                    if (result == "-1") {
                        $(".select_role").empty();
                        input = $("<span>该用户不存在，请确认账号信息！</span>");
                        $(".select_role").append(input);
                    } else {
                    	var roles=result.split(/[，,]/); 
                        $(".select_role").empty();
                        $(".select_role").append("<p>检测到该账号下存在多种角色，请选择登录</font></p>");
                        for (i = 0; i < roles.length; i++) {
                            var str = '<div class="col-sm-6 col-sm-offset-5" style="text-align: left;">' +
                                '<input type="hidden" value="' + roles[i] + '"/>' +
                                '<img class="form-groupimg" id="img" src="/images/not_check_icon.png" style="margin: 5px 0px;"/>' + roles[i] + '' +
                                '</div>';
                            input = $(str);
                            $(".select_role").append(input);
                        }
                    }
                } else {
                    $(".select_role").empty();
                    input = $("<span>该用户不存在，请确认账号信息！</span>");
                    $(".select_role").append(input);
                }
                change_icon();
            }
        })
    });

    function ajaxLogin() {
        var role = null;
        role = $("img[src='/images/checked_icon.png']").prev().val();
        var img = document.getElementById('img');
        if (img != null) {
            var url = "${ajaxLogin }";
            $.ajax({
                url: url,
                data: {"role": role, "userName": "${userName}", "type": "cas"},
                type: "post",
                dataType: "text",
                async: false,
                success: function (result) {
                    if (result == "1") {
                        $("#gmb_span").html("用户不存在");
                    } else if (result == "0") {
                        $("#gmb_span").html("权限不匹配");
                    } else if (result == "2") {
                        $("#gmb_span").html("密码错误");
                    } else if (result.substring(0, 1) == "3") {
                        $(".gmb_login").html("退出");
                        $(".gmb_name").html(result.substring(1));
                        //window.location.href='/home';
                        window.location.href = result.substring(1);
                    }
                }
            });
        } else {
          	var domain = /^https?:\/\/.*?\//.exec(window.location.href);
           	window.location.href = 'https://uaaap.swu.edu.cn/cas/logout?service=' + encodeURI(domain[0]);
        }
    }

    function change_icon() {
        $(".form-groupimg").on("click", function () {
            $(".form-groupimg").attr('src', '/images/not_check_icon.png');
            $(this).attr('src', '/images/checked_icon.png');

        });
    }
</script>
</body>

</html>
