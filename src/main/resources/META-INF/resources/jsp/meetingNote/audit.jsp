<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/init.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<portlet:resourceURL id="/org/meetingNote/audit/page" var="NoteAuditPage" />
<!DOCTYPE html>
<html>
<head>
	<title>组织生活管理 - 会议纪要审核</title>
	<style>
		#tBody .color_blue{
			color:#CE0000;
		}
		#tBody .color_blue_1{
			color:#FF0000;
		}

		@media(min-width:768px){
			.table_info .PublishTime{
				min-width:200px;
			}
			.table_info .LaunchTime{
				min-width:200px;
			}
			li.delete_li {
				background: #ce0000;
				color: white;
			}
		}
		.table_outer_box > table thead, tbody tr {
			display: table-row !important;
			width: 100%;
			table-layout: auto;
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
</head>
<body class="front">
<div class="table_form_content">
	<!-- 右侧盒子内容 -->
	<div class="activity_manage_page">
		<div class="breadcrumb_group" style="margin-bottom: 20px;">
			当前位置：
			<span class="layui-breadcrumb" lay-separator=">">
							<a href="javascript:;">组织生活管理</a>
							<a href="javascript:;">会议纪要审核</a>
						</span>
		</div>
		<div class="bg_white_container">
			<form class="layui-form" id="searchForm">
				<div class="layui-form-item">
					<div class="layui-inline">
						<div class="layui-input-inline keyword">
							<input type="text" name="keyword"  placeholder="请输入组织名称、主题关键字" class="layui-input">
						</div>
						<button type="button"  class="layui-btn layui-btn-warm"  lay-submit="" lay-filter="searchForm"><icon class="layui-icon layui-icon-search"></icon>搜索</button>
					</div>
					<button type="button" id="export" class="layui-btn layui-btn-warm" style="float: right;height: 38px;">导出pdf </button>
				</div>
			</form>
			<table id="noteAuditTable" lay-filter="noteAuditTable"></table>
		</div>
	</div>
</div>

<script type="text/html" id="noteAuditTableBtns">
	{{#  if(d.note_status == "2"){ }}
	<a class="layui-btn layui-btn-xs" lay-event="audit">驳回</a>
	{{#  } }}
	<a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
</script>
<script type="text/javascript">
	layui.use(['table','layer','form'], function() {
		var all;
		var checked = {};
		var table = layui.table,
				layer = layui.layer,
				form = layui.form;
		var pageInfo = {
			page:1,
			size:30
		};
		renderTable(1,pageInfo.size);
		form.on('submit(searchForm)', function (data) {
			renderTable(1,pageInfo.size);
		})
		function renderTable(page,size){
			var  where = {
				keyword: $("#searchForm input[name=keyword]").val()
			};
			var ins = table.render({
				elem: '#noteAuditTable',
				where: where,
				height:560,
				url: '${NoteAuditPage}', //数据接口
				method: 'post',
				page: {
					limit:size,   //每页条数
					curr:page,
					limits:[30,20,10],
					prev:'&lt;上一页',
					next:'下一页&gt;',
					theme: '#FFB800',
					groups:4
				},
				cols: [[ //表头
					{type:'checkbox'},
					{field: 'org_name', align:'center',width:'12.14%', title: '党组织'},
					{field: 'meeting_type', align:'center',width:'12.14%', title: '会议类型'},
					{field: 'meeting_theme', align:'center',width:'12.14%', title: '开展主题'},
					{field: 'start_time', align:'center', title: '开始时间',width:'12.14%',templet: function(d){return new Date(d.start_time).format("yyyy-MM-dd hh:mm:ss");}},
					{field: 'member_name', align:'center', title: '联系人',width:'12.14%'},
					{field: 'member_phone_number', align:'center', title: '联系电话',width:'12.14%'},
					{field: 'note_status', align:'center', title: '审核状态',width:'12.14%',minWidth:'200px',templet: function(d){
							var status = '';
							switch(parseInt(d.note_status)){
								case 1:status = '待审核';break;
								case 2:status = '已通过';break;
								case 3:status = '已驳回';break;
							}
							return status;
						}},
					{field: 'operation', align:'center', title: '操作',width:'15%',toolbar: '#noteAuditTableBtns'},

				]],
				done: function(res, curr, count){
					pageInfo.page = curr;
					pageInfo.size = ins.config.limit;
					all = res.data;
				}
			});
			table.on('checkbox(noteAuditTable)', function(obj){
				if (obj.checked) {
					if (obj.type == 'all') {
						checked = {};
						for (var i = 0; i < all.length; i++) {
							var a = all[i];
							checked[a.note_id] = a;
						}
					} else {
						checked[obj.data.note_id] = obj.data;
					}
				} else {
					if (obj.type == 'all') {
						checked = {};
					} else {
						delete checked[obj.data.note_id];
					}
				}
				console.log(obj.checked); //当前是否选中状态
				console.log(obj.data); //选中行的相关数据
				console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
			});

			$(".layui-table-view .layui-table-page").addClass("layui-table-page-center");
			$(".layui-table-view .layui-table-page").removeClass("layui-table-page");
			//监听事件
			table.on('tool(noteAuditTable)', function(obj){
				switch(obj.event){
					case 'audit':
						window.location.href='/noteDetailAudit?meetingId='+obj.data.meeting_id;
						break;
					case 'detail':
						window.location.href='/noteDetail?meetingId='+obj.data.meeting_id;
						break;
				};
			});
		}

		$('#export').on('click', function () {
			var ids = Object.keys(checked);
			if (ids.length === 0) {
				layuiModal.alert("请先勾选需要导出的纪要。");
				return;
			}
			window.open(sessionStorage.getItem("feeUrl") + '/fee/school/note/export?id=' + ids.join(','));
		})
	});
	Date.prototype.format = function (fmt) {
		var o = {
			"M+": this.getMonth() + 1, //月份
			"d+": this.getDate(), //日
			"h+": this.getHours(), //小时
			"m+": this.getMinutes(), //分
			"s+": this.getSeconds(), //秒
			"q+": Math.floor((this.getMonth() + 3) / 3), //季度
			"S": this.getMilliseconds() //毫秒
		};
		if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for (var k in o)
			if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}
</script>
</body>
</html>