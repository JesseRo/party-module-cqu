<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 	党费计算-->
<portlet:resourceURL id="/hg/part/duesCalculate" var="duesCal" />
<!-- 	岗位类别树获取-->
<portlet:resourceURL id="/hg/part/jobLevelTree" var="jobLevelTreeUrl" />
<html>
	<head>
<%--		<link rel="stylesheet" href="${basePath }/js/layui/css/layui.css" />--%>
<%--		<link rel="stylesheet" href="${basePath }/js/layui/css/layui.css" />--%>
<%--		<link rel="stylesheet" href="${basePath }/js/layui/css/modules/layer/default/layer.css" />--%>
<%--		<link rel="stylesheet" href="${basePath }/css/common.min.css" />--%>
<%--		<link rel="stylesheet" href="${basePath }/css/layout.min.css" />--%>
<%--		<script type="text/javascript" src="${basePath}/js/jquery-3.2.1.js"></script>--%>
<%--		<script type="text/javascript" src="${basePath}/js/layui/layui.js"></script>--%>
<%--		<script type="text/javascript" src="${basePath}/js/layui/lay/modules/tree.js"></script>--%>
<%--		<script type="text/javascript" src="${basePath}/js/layui/lay/modules/form.js"></script>--%>
<%--		<script type="text/javascript" src="${basePath}/js/layui/lay/modules/layer.js"></script>--%>
<%--		<script type="text/javascript" src="${basePath}/js/layui/lay/modules/util.js"></script>--%>
<%--		<script type="text/javascript" src="${basePath}/js/layui/lay/modules/util.js"></script>--%>
<%--		<script type="text/javascript" src="${basePath}/js/layui/module/treeSelect/treeSelect.js"></script>--%>
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
			.layui-form-item .layui-input{
				background-color: #f5f5f5;
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
				background-color: #f7f7f7;
				height: 100%;
				margin: 0;
			}
			.party_fee_container .party_fee_content{
				padding: 40px 0 20px 0;
				height: 100%;
			}
			.layui-treeSelect .ztree li .ico_docu {
				display: none;
			}
			.layui-form-label.layui-required:before{
				content: "*";
				color: red;
				top: 5px;
				right: 2px;
				position: relative;
			}
		</style>
	</head>
	<body class="front">
	<div class="table_form_content">
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
						<li class="layui-this" partyType="1" style="display: none;">月薪制党员</li>
						<li partyType="2" style="display: none;">年薪制党员</li>
						<li partyType="3" style="display: none;">企业员工/其他协议工资党员</li>
						<li partyType="4" style="display: none;">离退休教职工党员</li>
						<li partyType="5" style="display: none;">学生党员</li>
						<li partyType="6" style="display: none;">在职就读硕士/博士党员</li>
					</ul>
					<div class="layui-tab-content layui-form">

						<!--月薪制算法-->
						<div class="layui-tab-item monthCal fee-container1"  id="monthCal" style="display: none;">
							<form class="layui-form" >
							<div class="layui-form fee_form">
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">岗位工资</label>
									<div class="layui-input-block">
										<input type="number" name="basicSalary" min="0" lay-verify="inputNumber" autocomplete="off" class="layui-input" value="0" >
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">薪级工资</label>
									<div class="layui-input-block">
										<input type="number" name="levelSalary" min="0" lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">工 改</label>
									<div class="layui-input-block">
										<input type="number" name="wageReform" min="0" lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label  class="layui-form-label">岗位名称</label>
									<div class="layui-input-block tree2-div">
										<input type="text" class="tree2"  lay-filter="jobLevelPerformance" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">绩效工资理论值</label>
									<div class="layui-input-block">
										<input type="number" name="performance" min="0" lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">房公积金</label>
									<div class="layui-input-block">
										<input type="number" name="housingFund" min="0" lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">失业个人</label>
									<div class="layui-input-block">
										<input type="number" name="unemployedInsurance" min="0" lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">医保</label>
									<div class="layui-input-block">
										<input type="number" name="treatmentInsurance" min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item" id="pensionInsurance-div">
									<label class="layui-form-label layui-required">养老个人</label>
									<div class="layui-input-block">
										<input type="number"  name="pensionInsurance"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item" id="occupationalAnnuities-div" >
									<label class="layui-form-label layui-required">职业年金</label>
									<div class="layui-input-block">
										<input type="number" name="occupationalAnnuities"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">个人所得税:</label>
									<div class="layui-input-block">
										<span class="personalTax">0 元</span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费计算基数:</label>
									<div class="layui-input-block">
										<span class="basicDues">0 元</span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费交纳比例:</label>
									<div class="layui-input-block">
										<span class="percentDues">0 %</span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">月应交党费:</label>
									<div class="layui-input-block dues">
										<span class="duesPerMonth"></span>
									</div>
								</div>
								<div class="layui-form-item" style="margin-left: 132px;clear: both;">
									<div class="layui-input-block">
										<button type="submit" class="layui-btn custom_btn" lay-submit="" lay-filter="monthCalForm">计算党费</button>
										<button type="button" class="layui-btn layui-btn-primary" onclick="setFeeConfig(this, 1);">设置党费</button>
									</div>
								</div>
								<div class="layui-form-item" style="margin-left: 46px;width: 90%; display: inline-block;">
									<p>
										(1)目前党建信息化平台仅能抓取月薪制党员的工资信息。由于工资数据较为复杂，系统自动提取的工资数据仅供参考，请认真核对。如果数据有偏差，请修改相关数据后再计算党费。
									</p>
									<p>
										(2)年薪制党员、企业员工/其他协议工资党员、离退休教职工党员、学生党员、在职就读硕士博士党员，请参考《重庆大学党费收缴、使用和管理规定》，根据相关数据进行党费计算。
									</p>
									<p>
										(3)党员提交党费类型和党费数据，完成党费设置后，需要党支部书记和二级党组织管理员审核。
									</p>
								</div>
							</div>
							</form>
						</div>
						<!--年薪制算法-->
						<div class="layui-tab-item  yearCal fee-container2" id="yearCal" style="display: none;">
							<form class="layui-form">
							<div class="layui-form fee_form">
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">年薪月标准</label>
									<div class="layui-input-block">
										<input type="number" name="basicSalary"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">房公积金</label>
									<div class="layui-input-block">
										<input type="number" name="housingFund"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">失业个人</label>
									<div class="layui-input-block">
										<input type="number" name="unemployedInsurance"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">医保</label>
									<div class="layui-input-block">
										<input type="number" name="treatmentInsurance"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item" >
									<label class="layui-form-label layui-required">养老个人</label>
									<div class="layui-input-block">
										<input type="number"  name="pensionInsurance"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item" >
									<label class="layui-form-label layui-required">职业年金</label>
									<div class="layui-input-block">
										<input type="number" name="occupationalAnnuities"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">个人所得税:</label>
									<div class="layui-input-block">
										<span class="personalTax">0 元</span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费计算基数:</label>
									<div class="layui-input-block">
										<span class="basicDues">0 元</span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费交纳比例:</label>
									<div class="layui-input-block">
										<span class="percentDues">0 %</span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">月应交党费:</label>
									<div class="layui-input-block dues">
										<span class="duesPerMonth"></span>
									</div>
								</div>
								<div class="layui-form-item" style="width: 100%;">
									<label class="layui-form-label">备注:</label>
									<div class="layui-input-block dues" style="width: 88%;">
										<span style="width: auto;color: red;">系统无法从财务数据中自动提取年薪制党员类型，因此无法自动抓取工资数据，请结合各自情况，自己输入工资结构数据，计算党费。
										</span>
									</div>
								</div>
								<div class="layui-form-item" style="margin-left: 132px;clear: both;">
									<div class="layui-input-block">
										<button type="submit" class="layui-btn custom_btn" lay-submit="" lay-filter="yearCalForm">计算党费</button>
										<button type="button" class="layui-btn layui-btn-primary" onclick="setFeeConfig(this, 2);">设置党费</button>
									</div>
								</div>
								<div class="layui-form-item" style="margin-left: 46px;width: 90%; display: inline-block;">
									<p>
										(1)目前党建信息化平台仅能抓取月薪制党员的工资信息。由于工资数据较为复杂，系统自动提取的工资数据仅供参考，请认真核对。如果数据有偏差，请修改相关数据后再计算党费。
									</p>
									<p>
										(2)年薪制党员、企业员工/其他协议工资党员、离退休教职工党员、学生党员、在职就读硕士博士党员，请参考《重庆大学党费收缴、使用和管理规定》，根据相关数据进行党费计算。
									</p>
									<p>
										(3)党员提交党费类型和党费数据，完成党费设置后，需要党支部书记和二级党组织管理员审核。
									</p>
								</div>
							</div>
							</form>
						</div>
						<!--企业员工算法-->
						<div class="layui-tab-item  companyCal fee-container3" id="companyCal" style="display: none;">
							<form class="layui-form" >
							<div class="layui-form fee_form">
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">岗位工资</label>
									<div class="layui-input-block">
										<input type="number" name="basicSalary"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">薪级工资</label>
									<div class="layui-input-block">
										<input type="number" name="levelSalary"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">工 改</label>
									<div class="layui-input-block">
										<input type="number" name="wageReform"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label  class="layui-form-label">岗位名称</label>
									<div class="layui-input-block tree2-div">
										<input type="text" class="tree2"  lay-filter="jobLevelPerformance" class="layui-input">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">绩效工资理论值</label>
									<div class="layui-input-block">
										<input type="number" name="performance"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">房公积金</label>
									<div class="layui-input-block">
										<input type="number" name="housingFund"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">失业个人</label>
									<div class="layui-input-block">
										<input type="number" name="unemployedInsurance"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item" >
									<label class="layui-form-label layui-required">职业年金</label>
									<div class="layui-input-block">
										<input type="number" name="occupationalAnnuities"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">医保</label>
									<div class="layui-input-block">
										<input type="number" name="treatmentInsurance"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item" id="birthInsurance-div">
									<label class="layui-form-label layui-required">生育保险</label>
									<div class="layui-input-block">
										<input type="number"  name="birthInsurance"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item" id="employmentInjuryInsurance-div">
									<label class="layui-form-label layui-required">工伤保险</label>
									<div class="layui-input-block">
										<input type="number"  name="employmentInjuryInsurance"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item" >
									<label class="layui-form-label layui-required">养老个人</label>
									<div class="layui-input-block">
										<input type="number"  name="pensionInsurance"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">个人所得税:</label>
									<div class="layui-input-block">
										<span class="personalTax">0 元</span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费计算基数:</label>
									<div class="layui-input-block">
										<span class="basicDues">0 元</span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费交纳比例:</label>
									<div class="layui-input-block">
										<span class="percentDues">0 %</span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">月应交党费:</label>
									<div class="layui-input-block dues">
										<span class="duesPerMonth"></span>
									</div>
								</div>
								<div class="layui-form-item" style="margin-left: 132px;clear: both;">
									<div class="layui-input-block">
										<button type="submit" class="layui-btn custom_btn" lay-submit="" lay-filter="companyCalForm">计算党费</button>
										<button type="button" class="layui-btn layui-btn-primary" onclick="setFeeConfig(this, 3);">设置党费</button>
									</div>
								</div>
								<div class="layui-form-item" style="margin-left: 46px;width: 90%; display: inline-block;">
									<p>
										(1)目前党建信息化平台仅能抓取月薪制党员的工资信息。由于工资数据较为复杂，系统自动提取的工资数据仅供参考，请认真核对。如果数据有偏差，请修改相关数据后再计算党费。
									</p>
									<p>
										(2)年薪制党员、企业员工/其他协议工资党员、离退休教职工党员、学生党员、在职就读硕士博士党员，请参考《重庆大学党费收缴、使用和管理规定》，根据相关数据进行党费计算。
									</p>
									<p>
										(3)党员提交党费类型和党费数据，完成党费设置后，需要党支部书记和二级党组织管理员审核。
									</p>
								</div>
							</div>
							</form>
						</div>

						<!--离退休教职工党费-->
						<div class="layui-tab-item retireEmployeeCal fee-container4" id="retireEmployeeCal" style="display: none;">
							<form class="layui-form">
							<div class="layui-form fee_form">
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">退休工资</label>
									<div class="layui-input-block">
										<input type="number" name="basicSalary"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0">
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费计算基数:</label>
									<div class="layui-input-block">
										<span class="basicDues">0 元</span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费交纳比例:</label>
									<div class="layui-input-block">
										<span class="percentDues">0 %</span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">月应交党费：</label>
									<div class="layui-input-block dues">
										<span class="duesPerMonth"></span>
									</div>
								</div>
								<div class="layui-form-item" style="margin-left: 132px;clear: both;">
									<div class="layui-input-block">
										<button type="submit" class="layui-btn custom_btn" lay-submit="" lay-filter="retireEmployeeCalForm">计算党费</button>
										<button type="button" class="layui-btn layui-btn-primary" onclick="setFeeConfig(this, 4);">设置党费</button>
									</div>
								</div>
								<div class="layui-form-item" style="margin-left: 46px;width: 90%; display: inline-block;">
									<p>
										(1)目前党建信息化平台仅能抓取月薪制党员的工资信息。由于工资数据较为复杂，系统自动提取的工资数据仅供参考，请认真核对。如果数据有偏差，请修改相关数据后再计算党费。
									</p>
									<p>
										(2)年薪制党员、企业员工/其他协议工资党员、离退休教职工党员、学生党员、在职就读硕士博士党员，请参考《重庆大学党费收缴、使用和管理规定》，根据相关数据进行党费计算。
									</p>
									<p>
										(3)党员提交党费类型和党费数据，完成党费设置后，需要党支部书记和二级党组织管理员审核。
									</p>
								</div>
							</div>
							</form>
						</div>

						<!-- 学生党费-->
						<div class="layui-tab-item fee-container5" id="studentCal" style="display: none;">
							<div class="layui-form fee_form">
								<div class="layui-form-item">
									<label class="layui-form-label">月应交党费：</label>
									<div class="layui-input-block dues">
										<span class="duesPerMonth"></span>
									</div>
								</div>
							</div>
							<div class="layui-form-item" style="margin-left: 132px;clear: both;">
								<div class="layui-input-block">
									<button type="button" class="layui-btn layui-btn-primary" onclick="setFeeConfig(this, 5);">设置党费</button>
								</div>
							</div>
							<div class="layui-form-item" style="margin-left: 46px;width: 90%; display: inline-block;">
								<p>
									(1)目前党建信息化平台仅能抓取月薪制党员的工资信息。由于工资数据较为复杂，系统自动提取的工资数据仅供参考，请认真核对。如果数据有偏差，请修改相关数据后再计算党费。
								</p>
								<p>
									(2)年薪制党员、企业员工/其他协议工资党员、离退休教职工党员、学生党员、在职就读硕士博士党员，请参考《重庆大学党费收缴、使用和管理规定》，根据相关数据进行党费计算。
								</p>
								<p>
									(3)党员提交党费类型和党费数据，完成党费设置后，需要党支部书记和二级党组织管理员审核。
								</p>
							</div>
						</div>

						<!--硕士博士党费-->
						<div class="layui-tab-item masterCal fee-container6" id="masterCal" style="display: none;">
							<form class="layui-form">
							<div class="layui-form fee_form">
								<div class="layui-form-item">
									<label class="layui-form-label layui-required">实际收入工资</label>
									<div class="layui-input-block">
										<input type="number" name="basicSalary"  min="0"  lay-verify="inputNumber" placeholder="" autocomplete="off" class="layui-input" value="0" >
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费计算基数:</label>
									<div class="layui-input-block">
										<span class="basicDues">0 元</span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">党费交纳比例:</label>
									<div class="layui-input-block">
										<span class="percentDues">0 %</span>
									</div>
								</div>
								<div class="layui-form-item">
									<label class="layui-form-label">月应交党费：</label>
									<div class="layui-input-block dues">
										<span class="duesPerMonth"></span>
									</div>
								</div>
								<div class="layui-form-item" style="margin-left: 132px;clear: both;">
									<div class="layui-input-block">
										<button type="submit" class="layui-btn custom_btn" lay-submit="" lay-filter="masterCalForm">计算党费</button>
										<button type="button" class="layui-btn layui-btn-primary custom_btn" onclick="setFeeConfig(this, 6);">设置党费</button>
									</div>
								</div>
								<div class="layui-form-item" style="margin-left: 46px;width: 90%; display: inline-block;">
									<p>
										(1)目前党建信息化平台仅能抓取月薪制党员的工资信息。由于工资数据较为复杂，系统自动提取的工资数据仅供参考，请认真核对。如果数据有偏差，请修改相关数据后再计算党费。
									</p>
									<p>
										(2)年薪制党员、企业员工/其他协议工资党员、离退休教职工党员、学生党员、在职就读硕士博士党员，请参考《重庆大学党费收缴、使用和管理规定》，根据相关数据进行党费计算。
									</p>
									<p>
										(3)党员提交党费类型和党费数据，完成党费设置后，需要党支部书记和二级党组织管理员审核。
									</p>
								</div>
							</div>
							</form>
						</div>
<%--						<div class="layui-form-item" style="color: red;"><span style="margin-left: 20px;">当前党费设置：</span><span id="fee_config"></span></div>--%>
<%--						<div class="layui-form-item" style="color: red;">--%>
<%--							<span style="margin-left: 20px;">审核状态：</span>--%>
<%--							<span id="fee_audit_state"></span>--%>
<%--							<span style="display: none;" id="fee_warning">请重新提交设置</span>--%>
<%--						</div>--%>
					</div>
				</div>

			</div>
		</div>
		<!-- 右侧盒子内容 -->
	</div>
	<script>
		function getQueryVariable(variable) {
			var query = window.location.search.substring(1);
			var vars = query.split("&");
			for (var i = 0; i < vars.length; i++) {
				var pair = vars[i].split("=");
				if (pair[0] == variable) {
					return pair[1];
				}
			}
		}
		var configStatus = {0: "待审核",1: "已通过", 2:"已驳回",3: "已过期"}
		$(function () {
			var feeType = getQueryVariable("feeType");
			if (!feeType) {
				window.history.back();
			}
			$('[partyType=' + feeType + ']').show();
			$('.fee-container' + feeType).show().addClass('layui-show');
			$.get(sessionStorage.getItem("feeUrl") + '/fee/member/fee-calculate', function (res) {
				if (res.code === 0) {
					var container = $('#monthCal');
					for (var k in res.data) {
						var _input = container.find("[name=" + k + "]");
						if (_input.length > 0) {
							_input.val(res.data[k]);
						}
					}
				} else if (res.code === -2) {
					layuiModal.alert("未能获取到您的工资数据");
				} else {
					layuiModal.alert(res.message)
				}
			})
		})
		function setFeeConfig(e, type) {
			if(!type) {
				return
			}
			var el = $(e);
			var _fee = fee[type];
			if (_fee) {
				layuiModal.confirm("你的党费设置如下：\n" +
						"党费类型：\n" + $('[partyType=' + type + ']').text() + ";\n党费金额："  + _fee.toFixed(2) + "元/月", function () {
					$.post(sessionStorage.getItem("feeUrl") + '/fee/member/fee-config', {
						type: type,
						fee: (_fee * 100).toFixed(2)
					}, function (res) {
						if (res.code === 0) {
							layuiModal.alert("你已提交党费设置，请等待支部书记、二级党组织审核。", function () {
								window.location.href = "/member/config";
							});
						} else {
							layuiModal.alert(res.message);
						}
					})
				});
			} else {
				layuiModal.alert("请先计算党费");
			}
		}
		layui.config({
			base: '${basePath}/js/layui/module/'
		}).extend({
			treeSelect: 'treeSelect/treeSelect'
		});
		layui.use(['form'],function(){
			var form = layui.form;
			//表单提交
			form.on('submit(monthCalForm)', function(data){
				monthCal();
				return false;
			});
			form.on('submit(yearCalForm)', function(data){
				yearCal();
				return false;
			});
			form.on('submit(companyCalForm)', function(data){
				companyCal();
				return false;
			});
			form.on('submit(retireEmployeeCalForm)', function(data){
				retireEmployeeCal();
				return false;
			});
			form.on('submit(masterCalForm)', function(data){
				masterCal();
				return false;
			});
			form.verify({
				inputNumber: function (value, item) {
					if(value == '' ||value == null ){
						return '必填项不能为空哦....';
					}
					if (!/^\d+(\.\d+)?$/.test(value)){
						return '请输入>0或0的数值';
					}
				},
			});

		})
		initAllTree();
		function initAllTree(){
			initTree('monthCal');
			initTree('companyCal');
		}
		function initTree(id){
			layui.use(['treeSelect'], function () {
				var treeSelect= layui.treeSelect,
						$ = layui.jquery;
				treeSelect.render({
					// 选择器
					elem: '#'+id+' .tree2',
					// 数据
					data: '${jobLevelTreeUrl}',
					// 异步加载方式：get/post，默认get
					type: 'get',
					// 占位符
					placeholder: '请选择岗位',
					// 是否开启搜索功能：true/false，默认false
					search: true,
					style: {
						folder: {
							enable: false
						},
						line: {
							enable: true
						}
					},
					// 点击回调
					click: function(d){
						if(d.current.isParent){
							$("#"+id+" .tree2-div .layui-treeSelect.layui-unselect.layui-form-select").addClass("layui-form-selected");
							$("#"+id+" .tree2-div .layui-treeSelect .layui-select-title input").val("");
							$("#"+d.current.tId+"_switch").click()
						}else{
							if(d.current.data.jobPerformance != null && d.current.data.jobPerformance!= ''){
								$("#"+id+" .layui-form-item input[name='performance']").val( d.current.data.jobPerformance);
							}
							$("#"+id+" .tree2-div .layui-treeSelect.layui-unselect.layui-form-select").removeClass("layui-form-selected");
						}
					},
					// 加载完成后的回调函数
					success: function (d) {
						//console.log(d);
					}
				});
			});
		}



		$(".layui-tab ul li").on("click",function(){
			$(".layui-tab-content .layui-tab-item .layui-input-block .basicDues").text("0元");
			$(".layui-tab-content .layui-tab-item .layui-input-block .percentDues").text("0%");
			$(".layui-tab-content .layui-tab-item .layui-input-block .personalTax").text("0元");
			$(".layui-tab-content .layui-tab-item .layui-input-block .duesPerMonth").text("0元");
			var partyType = $(this).attr("partyType");
			var lastType = $(".layui-tab ul .layui-this").attr("partyType");
			if(5 == partyType){ //学生党费固定
				studentCal();
			}
			if(partyType != lastType){//切换tab清空数据
				$(".layui-form-item input[name='basicSalary']").val(0)
				$(".layui-form-item input[name='levelSalary']").val(0);
				$(".layui-form-item input[name='wageReform']").val(0);
				$(".layui-form-item input[name='housingFund']").val(0);
				$(".layui-form-item input[name='unemployedInsurance']").val(0);
				$(".layui-form-item input[name='treatmentInsurance']").val(0);
				$(".layui-form-item input[name='pensionInsurance']").val(0);
				$(".layui-form-item input[name='birthInsurance']").val(0);
				$(".layui-form-item input[name='employmentInjuryInsurance']").val(0);
				$(".layui-form-item input[name='occupationalAnnuities']").val(0);
				$(".layui-form-item input[name='occupationalAnnuities']").val(0);
				$(".tree2-div .layui-treeSelect .layui-select-title input").val("");
				$(".layui-form-item input[name='performance']").val(0);
			}
		})
		//月薪党费计算
		function monthCal(){
			normalCal("monthCal",1);
		}
		//年薪党费计算
		function yearCal(){
			normalCal("yearCal",2);
		}
		//企业党费计算
		function companyCal(){
			normalCal("companyCal",3);
		}
		/*离退休*/
		function retireEmployeeCal(){
			var basicSalary = $("#retireEmployeeCal .layui-form-item input[name='basicSalary']").val();
			if(basicSalary== null ||  (basicSalary!=null && basicSalary.trim()=="")){
				return;
			}
			var data = {
				partyType:4,
				basicSalary:basicSalary
			}
			cal(data);
		}
		/*学生党费*/
		function studentCal(){
			var data = {
				partyType:5
			}
			cal(data);
		}
		/*硕士博士党费*/
		function masterCal(){
			var basicSalary = $("#masterCal .layui-form-item input[name='basicSalary']").val();
			if(basicSalary== null ||  (basicSalary!=null && basicSalary.trim()=="")){
				return;
			}
			var data = {
				partyType:6,
				basicSalary:basicSalary
			}
			cal(data);
		}

		var fee = {};

		/*党费计算*/
		function cal(_data){
			var url = "${duesCal}";
			$.ajax({
				url:url,
				data:_data,
				type:"POST",
				dataType:'json',
				async:false,
				success:function(data){
					if(data.code == 200){
						var duesPerMonth = data.data.duesPerMonth;
						if(_data.partyType!=5){
							var basicDues = data.data.basicDues;
							var percentDues = data.data.percentDues*100;
							var personalTax = data.data.personalTax;
							$(".layui-tab-content .layui-tab-item .layui-input-block .basicDues").text(basicDues.toFixed(2)+"元");
							$(".layui-tab-content .layui-tab-item .layui-input-block .percentDues").text(percentDues.toFixed(2)+"%");
							if( _data.partyType!=6){
								$(".layui-tab-content .layui-tab-item .layui-input-block .personalTax").text(personalTax.toFixed(2)+"元");
							}

						}
						fee[_data.partyType] = duesPerMonth;
						$(".layui-tab-content .layui-tab-item .layui-input-block .duesPerMonth").text(duesPerMonth.toFixed(2)+"元");
					}

				}
			});
		}

		function normalCal(id,partType){
			if(partType == 1 || partType == 3) {
				if (!$('.fee-container' + partType + ' .tree2').val()) {
					layuiModal.alert("请先选择岗位");
					return;
				}
			}
			var basicSalary = $("#"+id+" .layui-form-item input[name='basicSalary']").val();
			if(basicSalary== null ||  (basicSalary!=null && basicSalary.trim()=="") || basicSalary <0){
				return;
			}

			var levelSalary = $("#"+id+" .layui-form-item input[name='levelSalary']").val();
			if(levelSalary < 0){
				return;
			}

			var wageReform = $("#"+id+" .layui-form-item input[name='wageReform']").val();
			if(wageReform < 0){
				return;
			}
			var performance = $("#"+id+" .layui-form-item input[name='performance']").val();
			if(performance < 0){
				return;
			}

			var housingFund = $("#"+id+" .layui-form-item input[name='housingFund']").val();
			if(housingFund < 0){
				return;
			}

			var unemployedInsurance = $("#"+id+" .layui-form-item input[name='unemployedInsurance']").val();
			if(unemployedInsurance < 0){
				return;
			}

			var treatmentInsurance = $("#"+id+" .layui-form-item input[name='treatmentInsurance']").val();
			if(treatmentInsurance < 0){
				return;
			}

			var pensionInsurance = $("#"+id+" .layui-form-item input[name='pensionInsurance']").val();
			if(pensionInsurance < 0){
				return;
			}

			var birthInsurance = $("#"+id+" .layui-form-item input[name='birthInsurance']").val();
			if(levelSalary < 0){
				return;
			}

			var employmentInjuryInsurance = $("#"+id+" .layui-form-item input[name='employmentInjuryInsurance']").val();
			if(employmentInjuryInsurance < 0){
				return;
			}
			var occupationalAnnuities = $("#"+id+" .layui-form-item input[name='occupationalAnnuities']").val();
			if(occupationalAnnuities < 0){
				return;
			}
			pensionInsurance = pensionInsurance==undefined?0:pensionInsurance;
			birthInsurance = birthInsurance==undefined?0:birthInsurance;
			employmentInjuryInsurance = employmentInjuryInsurance==undefined?0:employmentInjuryInsurance;
			occupationalAnnuities = occupationalAnnuities==undefined?0:occupationalAnnuities;
			var data = {
				partyType:partType,
				basicSalary:basicSalary,
				levelSalary:levelSalary,
				wageReform:wageReform,
				performance:performance,
				housingFund:housingFund,
				unemployedInsurance:unemployedInsurance,
				treatmentInsurance:treatmentInsurance,
				pensionInsurance:pensionInsurance,
				birthInsurance:birthInsurance,
				employmentInjuryInsurance:employmentInjuryInsurance,
				occupationalAnnuities:occupationalAnnuities
			}
			cal(data);
		}

	</script>
	</body>
</html>
