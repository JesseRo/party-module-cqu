<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 	党费计算-->
<portlet:resourceURL id="/hg/part/duesCalculate" var="duesCal" />
<html>
	<head>
		<link rel="stylesheet" href="${basePath }/js/layui/css/layui.css" />
		<link rel="stylesheet" href="${basePath }/js/layui/css/modules/layer/default/layer.css" />
		<link rel="stylesheet" href="${basePath }/css/common.min.css" />
		<link rel="stylesheet" href="${basePath }/css/layout.min.css" />
		<script type="text/javascript" src="${basePath}/js/jquery-3.2.1.js"></script>
		<script type="text/javascript" src="${basePath}/js/layui/layui.js"></script>
		<script type="text/javascript" src="${basePath}/js/layui/lay/modules/tree.js"></script>
		<script type="text/javascript" src="${basePath}/js/layui/lay/modules/form.js"></script>
		<script type="text/javascript" src="${basePath}/js/layui/lay/modules/layer.js"></script>
		<script type="text/javascript" src="${basePath}/js/layui/lay/modules/util.js"></script>
		<style>
			.fee_form .layui-form-item .layui-input-block{
				margin-left: 0;
			}
			.fee_form .layui-form-item .layui-form-label{
				padding: 9px 10px;
				width: 132px;
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
			.fee_form .layui-form-item  span{
				float: left;
				display: block;
				padding: 9px 15px;
				width: 130px;
				font-weight: 400;
				line-height: 20px;

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

						<!--月薪制党员-->
						<div class="layui-tab-item layui-show" id="monthCal">
							<div class="layui-form fee_form">
								<div class="layui-form-item">
									<label class="layui-form-label">岗位工资</label>
									<div class="layui-input-block">
										<input type="number" name="basicSalary" min="0" placeholder="" autocomplete="off" class="layui-input" >
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">薪级工资</label>
									<div class="layui-input-block">
										<input type="number" name="levelSalary" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">物价补贴</label>
									<div class="layui-input-block">
										<input type="number" name="priceSubsidy" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">地方补贴</label>
									<div class="layui-input-block">
										<input type="number" name="placeSubsidy" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">岗位名称</label>
									<div class="layui-input-block">
										<select name="city" lay-verify="required" placeholder="请选择岗位名称">
											<option value="0">正厅级</option>
											<option value="1">副厅级</option>

										</select>

									</div>

								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">绩效工资理论值</label>
									<div class="layui-input-block">
										<input type="number" name="performance" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">住房公积金</label>
									<div class="layui-input-block">
										<input type="number" name="housingFund" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">失业保险</label>
									<div class="layui-input-block">
										<input type="number" name="unemployedInsurance" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">医疗保险</label>
									<div class="layui-input-block">
										<input type="number" name="treatmentInsurance" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">养老保险</label>
									<div class="layui-input-block">
										<input type="number" name="pensionInsurance" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">预扣职业年金</label>
									<div class="layui-input-block">
										<input type="number" name="occupationalAnnuities" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">个人所得税:</label>
									<div class="layui-input-block">
										<span class="personalTax"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费计算基数:</label>
									<div class="layui-input-block">
										<span class="basicDues"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费交纳比例:</label>
									<div class="layui-input-block">
										<span class="percentDues"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">月应交党费:</label>
									<div class="layui-input-block dues">
										<span class="duesPerMonth"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<div class="layui-input-block">
										<button class="layui-btn custom_btn" onclick="monthCal()">计算党费</button>
										<!-- <button type="reset" class="layui-btn layui-btn-primary">重置</button> -->
									</div>
								</div>
							</div>
						</div>

						<!--年薪制党员-->
						<div class="layui-tab-item" id="yearCal">
							<div class="layui-form fee_form">
								<div class="layui-form-item">
									<label class="layui-form-label">岗位工资</label>
									<div class="layui-input-block">
										<input type="number" name="basicSalary" min="0" placeholder="" autocomplete="off" class="layui-input" >
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">薪级工资</label>
									<div class="layui-input-block">
										<input type="number" name="levelSalary" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">物价补贴</label>
									<div class="layui-input-block">
										<input type="number" name="priceSubsidy" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">地方补贴</label>
									<div class="layui-input-block">
										<input type="number" name="placeSubsidy" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">岗位名称</label>
									<div class="layui-input-block">
										<select name="city" lay-verify="required" placeholder="请选择岗位名称">

										</select>
									</div>

								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">绩效工资理论值</label>
									<div class="layui-input-block">
										<input type="number" name="performance" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">住房公积金</label>
									<div class="layui-input-block">
										<input type="number" name="housingFund" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">失业保险</label>
									<div class="layui-input-block">
										<input type="number" name="unemployedInsurance" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">医疗保险</label>
									<div class="layui-input-block">
										<input type="number" name="treatmentInsurance" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">养老保险</label>
									<div class="layui-input-block">
										<input type="number" name="pensionInsurance" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">预扣职业年金</label>
									<div class="layui-input-block">
										<input type="number" name="occupationalAnnuities" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">个人所得税:</label>
									<div class="layui-input-block">
										<span class="personalTax"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费计算基数:</label>
									<div class="layui-input-block">
										<span class="basicDues"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费交纳比例:</label>
									<div class="layui-input-block">
										<span class="percentDues"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">月应交党费:</label>
									<div class="layui-input-block dues">
										<span class="duesPerMonth"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<div class="layui-input-block">
										<button class="layui-btn custom_btn" onclick="yearCal()">计算党费</button>
										<!-- <button type="reset" class="layui-btn layui-btn-primary">重置</button> -->
									</div>
								</div>
							</div>
						</div>

						<!--企业协议公司党员-->
						<div class="layui-tab-item" id="companyCal">
							<div class="layui-form fee_form">
								<div class="layui-form-item">
									<label class="layui-form-label">岗位工资</label>
									<div class="layui-input-block">
										<input type="number" name="basicSalary" min="0" placeholder="" autocomplete="off" class="layui-input" >
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">薪级工资</label>
									<div class="layui-input-block">
										<input type="number" name="levelSalary" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">物价补贴</label>
									<div class="layui-input-block">
										<input type="number" name="priceSubsidy" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">地方补贴</label>
									<div class="layui-input-block">
										<input type="number" name="placeSubsidy" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">岗位名称</label>
									<div class="layui-input-block">
										<select name="city" lay-verify="required" placeholder="请选择岗位名称">
											<option value="0">正厅级</option>
											<option value="1">副厅级</option>
										</select>
									</div>

								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">绩效工资理论值</label>
									<div class="layui-input-block">
										<input type="number" name="performance" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">住房公积金</label>
									<div class="layui-input-block">
										<input type="number" name="housingFund" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">失业保险</label>
									<div class="layui-input-block">
										<input type="number" name="unemployedInsurance" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">医疗保险</label>
									<div class="layui-input-block">
										<input type="number" name="treatmentInsurance" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">个人所得税:</label>
									<div class="layui-input-block">
										<span class="personalTax"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费计算基数:</label>
									<div class="layui-input-block">
										<span class="basicDues"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费交纳比例:</label>
									<div class="layui-input-block">
										<span class="percentDues"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">月应交党费:</label>
									<div class="layui-input-block dues">
										<span class="duesPerMonth"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<div class="layui-input-block">
										<button class="layui-btn custom_btn" onclick="companyCal()">计算党费</button>
										<!-- <button type="reset" class="layui-btn layui-btn-primary">重置</button> -->
									</div>
								</div>
							</div>
						</div>
						<!--离退休教职工党费-->
						<div class="layui-tab-item" id="retireEmployeeCal">
							<div class="layui-form fee_form">
								<div class="layui-form-item">
									<label class="layui-form-label">退休工资</label>
									<div class="layui-input-block">
										<input type="number" name="basicSalary" min="0" placeholder="" autocomplete="off" class="layui-input" >
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">月应交党费：</label>
									<div class="layui-input-block dues">
										<span class="duesPerMonth"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<div class="layui-input-block">
										<button class="layui-btn custom_btn" onclick="retireEmployeeCal()">计算党费</button>
										<!-- <button type="reset" class="layui-btn layui-btn-primary">重置</button> -->
									</div>
								</div>
							</div>
						</div>

						<!-- 学生党费-->
						<div class="layui-tab-item">
							<div class="layui-form fee_form">
								<div class="layui-form-item">
									<label class="layui-form-label">月应交党费：</label>
									<div class="layui-input-block dues">
										<span class="duesPerMonth"></span>
									</div>
								</div>
							</div>
						</div>

						<!-- 硕士 博士在职党费-->
						<div class="layui-tab-item" id="masterCal">
							<div class="layui-form fee_form">
								<div class="layui-form-item">
									<label class="layui-form-label">岗位工资</label>
									<div class="layui-input-block">
										<input type="number" name="basicSalary" min="0" placeholder="" autocomplete="off" class="layui-input" >
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">薪级工资</label>
									<div class="layui-input-block">
										<input type="number" name="levelSalary" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">物价补贴</label>
									<div class="layui-input-block">
										<input type="number" name="priceSubsidy" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">地方补贴</label>
									<div class="layui-input-block">
										<input type="number" name="placeSubsidy" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">岗位名称</label>
									<div class="layui-input-block">
										<select name="city" lay-verify="required" placeholder="请选择岗位名称">

										</select>
									</div>

								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">绩效工资理论值</label>
									<div class="layui-input-block">
										<input type="number" name="performance" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">住房公积金</label>
									<div class="layui-input-block">
										<input type="number" name="housingFund" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">失业保险</label>
									<div class="layui-input-block">
										<input type="number" name="unemployedInsurance" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">医疗保险</label>
									<div class="layui-input-block">
										<input type="number" name="treatmentInsurance" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">养老保险</label>
									<div class="layui-input-block">
										<input type="number" name="pensionInsurance" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">预扣职业年金</label>
									<div class="layui-input-block">
										<input type="number" name="occupationalAnnuities" min="0" placeholder="" autocomplete="off" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">个人所得税:</label>
									<div class="layui-input-block">
										<span class="personalTax"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费计算基数:</label>
									<div class="layui-input-block">
										<span class="basicDues"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费交纳比例:</label>
									<div class="layui-input-block">
										<span class="percentDues"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">月应交党费:</label>
									<div class="layui-input-block dues">
										<span class="duesPerMonth"></span>
									</div>
								</div>
								<div class="layui-form-item">
									<div class="layui-input-block">
										<button class="layui-btn custom_btn" onclick="masterCal()">计算党费</button>
										<!-- <button type="reset" class="layui-btn layui-btn-primary">重置</button> -->
									</div>
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
			$(".layui-tab-content .layui-tab-item .layui-input-block .basicDues").text("0元");
			$(".layui-tab-content .layui-tab-item .layui-input-block .percentDues").text("0%");
			$(".layui-tab-content .layui-tab-item .layui-input-block .personalTax").text("0元");
			$(".layui-tab-content .layui-tab-item .layui-input-block .duesPerMonth").text("0元");
			let index = $(this).index();
			$(".layui-tab ul li").removeClass("layui-this");
			$(".layui-tab-content .layui-tab-item").removeClass("layui-show");
			$(this).addClass("layui-this");
			$($(".layui-tab-content .layui-tab-item")[index]).addClass("layui-show");
			if(5 == $(this).attr("partyType")){ //学生党费固定
				cal(null);
			}
		})
		//月薪党费计算
		function monthCal(){
			let basicSalary = $("#monthCal .layui-form-item input[name='basicSalary']").val();
			if(basicSalary== null ||  (basicSalary!=null && basicSalary.trim()=="")){
				return;
			}
			let data = {
				basicSalary:basicSalary
			}
			cal(data);
		}
		//年薪党费计算
		function yearCal(){
			let basicSalary = $("#yearCal .layui-form-item input[name='basicSalary']").val();
			if(basicSalary== null ||  (basicSalary!=null && basicSalary.trim()=="")){
				return;
			}
			let data = {
				basicSalary:basicSalary
			}
			cal(data);
		}
		//年薪党费计算
		function companyCal(){
			let basicSalary = $("#companyCal .layui-form-item input[name='basicSalary']").val();
			if(basicSalary== null ||  (basicSalary!=null && basicSalary.trim()=="")){
				return;
			}
			let data = {
				basicSalary:basicSalary
			}
			cal(data);
		}
		//离退休
		function retireEmployeeCal(){
			let basicSalary = $("#retireEmployeeCal .layui-form-item input[name='basicSalary']").val();
			if(basicSalary== null ||  (basicSalary!=null && basicSalary.trim()=="")){
				return;
			}
			let data = {
				basicSalary:basicSalary
			}
			cal(data);
		}
		//硕士博士
		function masterCal(){
			let basicSalary = $("#masterCal .layui-form-item input[name='basicSalary']").val();
			if(basicSalary== null ||  (basicSalary!=null && basicSalary.trim()=="")){
				return;
			}
			let data = {
				basicSalary:basicSalary
			}
			cal(data);
		}
		//党费计算
		function cal(data){
			console.log(data)
			let partyType = $(".layui-tab ul li.layui-this").attr("partyType");
			let url = "${duesCal}";
			$.ajax({
				url:url+"&partyType="+partyType,
				data:data,
				type:"POST",
				dataType:'json',
				async:false,
				success:function(data){
					if(data.code == 200){
						console.log(data);
						let duesPerMonth = data.data.duesPerMonth;
						if(partyType!=5 && partyType!=5){
							let basicDues = data.data.basicDues;
							let percentDues = data.data.percentDues*100;
							let personalTax = data.data.personalTax;
							$(".layui-tab-content .layui-tab-item .layui-input-block .basicDues").text(basicDues.toFixed(2)+"元");
							$(".layui-tab-content .layui-tab-item .layui-input-block .percentDues").text(percentDues.toFixed(2)+"%");
							$(".layui-tab-content .layui-tab-item .layui-input-block .personalTax").text(personalTax.toFixed(2)+"元");
						}
						$(".layui-tab-content .layui-tab-item .layui-input-block .duesPerMonth").text(duesPerMonth.toFixed(2)+"元");
					}

				}
			});
		}

	</script>
	</body>
</html>