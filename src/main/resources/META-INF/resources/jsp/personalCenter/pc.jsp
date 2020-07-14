<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<portlet:resourceURL id="/hg/personal/page" var="meetings" />
<portlet:resourceURL id="/hg/personalCheck/page" var="check" />



<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>个人中心</title>
	<link rel="stylesheet" href="${basePath}/cqu/css/party_branch_layout.css"/>
	<link rel="stylesheet" href="${basePath}/cqu/css/summary.min.css"/>
	<style>
		.personal_center_container{
			background: #fff;
			position: relative;
		}
		.personal_center_container .img_content_container .icon_container img{
			width: 100%;
			height: 100%;
			position: relative;
			z-index: 2;
		}
		.personal_center_container .img_content_container .icon_container .right_line{
			left: 100%!important;
		}
		.personal_center_container .img_content_container .icon_container .outer_line{
			width: 1px;
			background: #FFD8C9;
			position: absolute;
			height: 81.5%;
			top: 9.5%;
			left: 0;
		}
		.personal_center_container .img_content_container .icon_container .line{
			height: 1px;
			background: #FFD8C9;
			position: absolute;
			top: 50%;
			left: 0;
			width: 100%;
			z-index: 1;
		}
		.personal_center_container .img_content_container .icon_container .person_name{
			position: absolute;
			bottom: -38px;
			text-align: center;
			width: 100%;
			font-size: 18px;
			color: #333;
			font-weight: 600;
		}
		.personal_center_container .img_content_container .icon_container{
			width: 36.8%;
			height: 69.6%;
			position: absolute;
			top: 15.2%;
			left: 31.6%;
		}
		.personal_center_container .text_container .text_title{
			font-size: 24px;
			color: #333;
			line-height: 1;
			font-weight: 600;
			margin-bottom: 16px;
			padding: 0 13.3%;
		}
		.personal_center_container .text_container .text_line{
			height: 1px;
			background: #FFD8C9;
			position: absolute;
			right: -21.6%;
			width: 21.6%;
			top: 50%;
		}
		.personal_center_container .text_container .text_content{
			overflow-y: auto;
			padding: 0 13.3%;
			height: calc(100% - 36px);
		}
		.personal_center_container .text_container p{
			font-size: 18px;
			color: #666;
			line-height: 24px;
			font-weight: 600;
			margin-bottom: 8px;
		}
		.personal_center_container .right_text_container{
			left: unset;
			right: 0;
		}
		.personal_center_container .right_bottom{
			bottom: 0;
		}
		.personal_center_container .right_top{
			top: 0;
		}
		.personal_center_container .left_bottom{
			bottom: 0;
			left: 0;
		}
		.personal_center_container .left_top{
			top: 0;
			left: 0;
		}
		.personal_center_container .text_container.right_text_container .text_line{
			right: unset;
			left: -21.6%;
		}
		.personal_center_container .text_container{
			width: 26.2%;
			height: 43.2%;
			background: #FFFEF3;
			border: 1px solid #FFAA17;
			padding: 3.5% 0;
			position: absolute;
		}
		.personal_center_container .img_content_container{
			width: 72.8%;
			height: 80.4%;
			position: absolute;
			top: 9.8%;
			left: 13.6%;
		}
		.text_content p{
			cursor: pointer;
		}
		@media screen and (max-width: 1536px){
			.personal_center_container .text_container p{
				font-size: 14px;
				line-height: 20px;
			}
			.personal_center_container .text_container .text_title{
				font-size: 20px;
			}
			.personal_center_container .img_content_container .icon_container .person_name{
				font-size: 14px;
			}
		}
	</style>

</head>
<body>
	<div class="activity_manage_page personal_center_container" style="height: 85vh;">
		<div class="img_content_container">
			<div class="icon_container">
				<img src="../images/person-icon1.png"/>
				<p class="line"></p>
				<p class="outer_line left_line"></p>
				<p class="outer_line right_line"></p>
				<p class="person_name">党员：${userInfo.member_name}</p>
			</div>
			<div class="text_container left_top">
				<p class="text_title">组织生活</p>
				<div class="text_content" id="meeting">
					<p>暂无数据</p>
				</div>
				<p class="text_line"></p>
			</div>
			<div class="text_container left_bottom">
				<p class="text_title">党课学习</p>
				<div class="text_content">
					<p>学习课程：我读《习近平谈治国理政</p>
					<p>缺会情况：2次</p>
					<p>党费缴纳：266.79元</p>
				</div>
				<p class="text_line"></p>
			</div>
			<div class="text_container right_text_container right_top">
				<p class="text_title" style="cursor: pointer;" onclick="window.location.href='/personal_info'">我的基础信息</p>
				<div class="text_content">
					<p>姓名：${userInfo.member_name}</p>
					<p>性别：${userInfo.member_sex}</p>
					<p>入党时间：${userInfo.member_join_date}</p>
					<p>联系电话：${userInfo.member_phone_number}</p>
				</div>
				<p class="text_line"></p>
			</div>
			<div class="text_container right_text_container right_bottom">
				<p class="text_title">我的任务</p>
				<div class="text_content" id="checks">
					<p>暂无数据</p>
				</div>
				<p class="text_line"></p>
			</div>
		</div>
	</div>
	<script>
		$(function () {
			$.post('${meetings}', {page: 0, limit: 10}, function (res) {
				var $meetings = $('#meeting');
				if (res.data.length > 0){
					$meetings.html("");
					for (var i in res.data){
						var p = $("<p></p>");
						p.html('<a href="/approvaldetails?meetingId='+res.data[i].meeting_id+'">'+res.data[i].meeting_theme+'</a>');
						$meetings.append(p);
					}
				}


			})
			$.post('${check}', {page: 0, limit: 10}, function (res) {
				var $checks = $('#checks');
				if (res.data.length > 0){
					$checks.html("");
					for (var i in res.data){
						var p = $("<p></p>");
						p.html(res.data[i].meeting_theme);
						$checks.append(p);
					}
				}

			})
		})
	</script>
</body>
</html>