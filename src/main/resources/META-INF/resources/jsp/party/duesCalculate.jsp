<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 	党费计算-->
<portlet:resourceURL id="/hg/part/duesCalculate" var="duesCal" />
<html>
	<head>
		<link rel="stylesheet" href="${basePath }/css/layui.css" />
		<link rel="stylesheet" href="${basePath }/css/common.min.css" />
		<link rel="stylesheet" href="${basePath }/css/layout.min.css" />
		<style>
			.fee_form .layui-form-item .layui-input-block{
				margin-left: 0;
			}
			.fee_form .layui-form-item .layui-form-label{
				padding: 9px 10px;
				width: 112px;
				font-size: 16px;
			}
			.fee_form .layui-form-item:last-child{
				clear: both;
				margin-left: 132px;
			}
			.fee_form .layui-form-item{
				width: 33.33%;
				float: left;
				clear: none;
				display: flex;
			}
			.party_fee_container .party_fee_content .fee_main_content .layui-this{
				background-color: #ffab33;
				color: #fff;
			}
			.party_fee_container .party_fee_content .fee_main_content .layui-tab-content{
				height: calc(100% - 40px);
				overflow-y: auto;
			}
			.party_fee_container .party_fee_content .fee_main_content{
				background-color: #fff;
				height: 100%;
				margin: 0;
			}
			.party_fee_container .party_fee_content{
				padding: 40px 0 20px 0;
				height: 100%;
			}
		</style>
	</head>
	<body class="front">
	<div class="main_content table_form_content activity_manage_container">
		<!-- 右侧盒子内容 -->
		<div class="activity_manage_page party_fee_container">
			<div class="breadcrumb_group">
				当前位置：
				<span>
                        <a href="javascript:;">党费计算</a>
				</span>
			</div>
			<div class="party_fee_content">
				<div class="layui-tab layui-tab-card fee_main_content">
					<ul class="layui-tab-title">
						<li class="layui-this" partyType="1">月薪制党员</li>
						<li partyType="2">年薪制党员</li>
						<li partyType="3">企业员工/其他协议工资党员</li>
						<li partyType="4">离退休教职工党员</li>
						<li partyType="5">学生党员</li>
						<li partyType="6">在职就读硕士/博士党员</li>
					</ul>
					<div class="layui-tab-content">
						<div class="layui-tab-item layui-show">
							<div class="layui-form fee_form">
								<div class="layui-form-item">
									<label class="layui-form-label">岗位工资</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">薪级工资</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">物价补贴</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">地方补贴</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">岗位名称</label>
									<div class="layui-input-block">
										<select name="city" lay-verify="required" placeholder="请选择岗位名称">
											<option value="0">北京</option>
											<option value="1">上海</option>
											<option value="2">广州</option>
											<option value="3">深圳</option>
											<option value="4">杭州</option>
										</select>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">绩效工资理论值</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">绩效工资理论值</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">住房公积金</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">失业个人</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">预扣职业年金</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">个人所得税</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费计算基数</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费交纳比例</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">月应交党费：</label>
									<div class="layui-input-block">
										¥ </span><span class="duesPerMonth"></span> 元
									</div>
								</div>
								<div class="layui-form-item">
									<div class="layui-input-block">
										<button class="layui-btn custom_btn" onclick="cal()">计算党费</button>
										<!-- <button type="reset" class="layui-btn layui-btn-primary">重置</button> -->
									</div>
								</div>
							</div>
						</div>
						<div class="layui-tab-item">
							<div class="layui-form fee_form">
								<div class="layui-form-item">
									<label class="layui-form-label">年薪月标准</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">住房公积金</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">失业个人</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">预扣职业年金</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">个人所得税</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费计算基数</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费交纳比例</label>
									<div class="layui-input-block">
										<input type="text" name="title" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">月应交党费：</label>
									<div class="layui-input-block">
										¥ </span><span class="duesPerMonth"></span> 元
									</div>
								</div>
								<div class="layui-form-item">
									<div class="layui-input-block">
										<button class="layui-btn custom_btn" onclick="cal()">计算党费</button>
										<!-- <button type="reset" class="layui-btn layui-btn-primary">重置</button> -->
									</div>
								</div>
							</div>
						</div>
						<div class="layui-tab-item">
							<div class="layui-form fee_form">
								<label class="layui-form-label">月应交党费：</label>
								<div class="layui-input-block">
									¥ </span><span class="duesPerMonth"></span> 元
								</div>
							</div>
						</div>
						<div class="layui-tab-item">
							<div class="layui-form fee_form">
								<label class="layui-form-label">月应交党费：</label>
								<div class="layui-input-block">
									¥ </span><span class="duesPerMonth"></span> 元
								</div>
							</div>
						</div>
						<div class="layui-tab-item">
							<div class="layui-form fee_form">
								<label class="layui-form-label">月应交党费：</label>
								<div class="layui-input-block">
									¥ </span><span class="duesPerMonth"></span> 元
								</div>
							</div>
						</div>
						<div class="layui-tab-item">
							<div class="layui-form fee_form">
								<label class="layui-form-label">月应交党费：</label>
								<div class="layui-input-block">
									¥ </span><span class="duesPerMonth"></span> 元
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 右侧盒子内容 -->
	</div>
	<script type="text/javascript">
		$(".layui-tab ul li").on("click",function(){
			$(".layui-tab-content .layui-tab-item .layui-input-block .duesPerMonth").text("  ");
			let index = $(this).index();
			$(".layui-tab ul li").removeClass("layui-this");
			$(".layui-tab-content .layui-tab-item").removeClass("layui-show");
			$(this).addClass("layui-this");
			$($(".layui-tab-content .layui-tab-item")[index]).addClass("layui-show");
			if(5 == $(this).attr("partyType")){ //学生党费固定
				cal();
			}
		})
		//录入保存
		function cal(){
			let partyType = $(".layui-tab ul li.layui-this").attr("partyType");
			let url = "${duesCal}";
			$.ajax({
				url:url,
				data:{"partyType":partyType},
				dataType:'json',
				async:false,
				success:function(data){
					if(data.code == 200){
						$(".layui-tab-content .layui-tab-item .layui-input-block .duesPerMonth").text(data.data);
					}

				}
			});
		}

	</script>
	</body>
</html>