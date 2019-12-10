
<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!-- 保存新增站点 -->
 <%-- <portlet:actionURL name="${submitCommand }" var="submitForm"/> --%>
  <portlet:actionURL name="/hg/postSubmissions" var="submitForm"/> 

 <portlet:resourceURL id="/hg/getPublicObject" var="getPublicObject"/>
 <portlet:resourceURL id="/hg/getPlace" var="getPlace"/>
 <!--获取常用组  -->
 <portlet:resourceURL id="/hg/getGroup" var="getGroup"/>
 <portlet:resourceURL id="/form/uploadImage" var="uploadimageUrl" />
<!-- 视频上传 -->
<portlet:resourceURL id="/form/uploadVideo" var="uploadvideoUrl" />
<!-- 附件上传 -->
<portlet:resourceURL id="/form/uploadFile" var="uploadfileUrl" />
<html>
	<head>
	<style type="text/css">
	    input#button1 {
		    float: right;
		}
		textarea {
		    width: 100%;
		    height: 100px;
		}
		
		input#button2 {
        float: left;
       }
       
       /* 常用人员弹窗样式 */
          .member_title {
           font-size: 14px;
           line-height: 46px;
            height: 46px;
           }
        #commonStaff thead {
            border-bottom: 1px solid #d8d8d8;
        }
        #commonStaff .first_thead{
            background: #DEDEDE;
            font-size: 14px;
        }
        
        #commonStaff .modal-body {
            max-height: 500px;
        }
        
        #commonStaff .common_list {
            border-right: none;
        }
        
        #commonStaff .common_list tr td {
            cursor: pointer;
        }
        
        #commonStaff .common_list tr td:nth-child(2) {
            cursor: default;
        }
        
        #commonStaff table td {
            font-size: 13px;
        }
        
        #commonStaff th,
        #commonStaff .common_list td {
            border: none;
        }
        
        #commonStaff .modal-body>div {
            padding: 0;
        }
        
        #commonStaff .modal-body>div table {
            width: 100%;
        }
        
        #commonStaff .add_btn {
            height: auto;
            background: #f5f5f5;
            border: 1px solid #d8d8d8;
            border-top: none;
        }
        
        #commonStaff .add_btn button {
            margin: 15px;
            font-size: 16px;
        }
        
        #commonStaff .add_btn img {
            margin-right: 5px;
        }
        
        .table_container {
            height: 200px;
            overflow-y: auto;
            border: 1px solid #d8d8d8;
        }
        
        .common_list_container .table_container {
            /* border-right: none; */
        }
        
        #commonStaff .table_title {
            text-align: center;
            font-size: 18px;
        }
        
        .add_btn form {
            width: 80%;
            background: #fff;
            border: 1px solid #d8d8d8;
            border-radius: 6px;
            padding: 10px 5px;
            padding-bottom: 0;
        }
        
        #commonStaff .add_btn form {
            margin: 0 0 10px 10px;
            display: none;
        }
        
        #commonStaff .add_btn form .btn {
            margin: 0;
        }
        
        #commonStaff .form-group {
            margin-bottom: 0;
        }
        
        .form_btn {
            text-align: right;
        }
        
        .common_member_list tr td:nth-child(1) {
            border-left: none;
        }
        
        .common_member_list tr td:nth-child(2) {
            border-right: none;
        }
        .member_title{
            height: 30px;
            line-height: 30px;
            background: #f5f5f5;
            color: #333;
            padding-left: 20px;
        }
        .member_content{
            padding: 18px 20px;
        }
        .member_content span{
             margin-top: 10px;
             margin-right: 10px;
            display: inline-block;
            padding: 5px 10px;
            border: 1px solid #D8D8D8;
            border-radius: 4px;
            font-size: 16px;
        }
        .member_content span img{
            vertical-align: baseline;
            cursor: pointer;
        }
        .all_member_list{
            max-height: 177px;
            min-height: 61px;
            height: auto;
            overflow-y: auto;
            background: #f5f5f5;
            padding: 15px;
            border: 1px solid #d8d8d8;
            border-top: none;
        }
        .all_member_list p{
            color: #333;
            display: inline-block;
        }
        .all_member_list >.list_item_box span{
            width: 20%;
            margin-top: 10px;
            display: inline-block;
            padding-top: 2px;
            padding-bottom: 2px;
            border: 1px solid #d8d8d8;
            text-align: center;
            margin-right:2.5%;
            background: #fff;
        }
      
        #commonStaff .modal-footer{
            border-top: 0;
        }
        #commonStaff .modal-footer button{
            margin-top: 24px;
        }
        .all_member_list::-webkit-scrollbar {
            width: 4px;
            height: 4px;
        }
        .all_member_list::-webkit-scrollbar-thumb {
            border-radius: 5px;
            -webkit-box-shadow: inset 0 0 5px transparent;
            background: transparent;
        }
        .all_member_list::-webkit-scrollbar-track {
            -webkit-box-shadow: inset 0 0 5px transparent;
            border-radius: 0;
            background: #dedede;
        }
        .member_list_title div.right{
            height: 18px;
            display: inline-block;
            width: 110px;
        }
        .member_list_title div.right .select_choice{
            font-size: 12px;
            float: left;
        }
        .first_select_box{
            margin-right:10px; 
        }
        .divide_img{
            text-align: center;
            margin: 150px 0;
        }
      /*  */
        .common_member_list tr td:nth-child(1) {
            border-left: none;
        }
        
        .common_member_list tr td:nth-child(2) {
            border-right: none;
        }
        
         label.error{
           position: absolute;
		   color: #ce0000;
		   font-size: 12px;
		   font-weight: normal;
           }
        /* 
         .control-label::before{
            content: "*";
            position: absolute;
            top: 0;
            left: -5px;
            color: #ce0000;
            line-height:40px;
        	} */
        	.member_content span {
    display: inline-block;
    padding: 5px 10px;
    border: 1px solid #D8D8D8;
    border-radius: 4px;
}
span.branch_person {
    width: 16%;
    margin-top: 10px;
    display: inline-block;
    padding-top: 2px;
    padding-bottom: 2px;
    border: 1px solid #d8d8d8;
    text-align: center;
    margin-right: 2.5%;
    background: #fff;
}   

span.branch_person {
    width: auto;
    min-width: 16%;
} 	
span.branch_person.person_class {
    color: wheat;
}

tbody.use_group th, td {
    text-align: center;
    padding-top: 14px;
}
@media (min-width: 768px){
 .col-sm-7 {
    width: 48.3333%;
  }
}
/*滚动条  */
.table_container::-webkit-scrollbar {
    width: 4px;
    height: 4px;
}
.table_container::-webkit-scrollbar-thumb {
    border-radius: 5px;
    -webkit-box-shadow: inset 0 0 5px transparent;
    background: transparent;
}
.table_container::-webkit-scrollbar-track {
    -webkit-box-shadow: inset 0 0 5px transparent;
    border-radius: 0;
    background: #dedede;
}
/*滚动条  */
.content_info .content_form .form-group>div {
    margin-bottom: 20px;
}
thead.first_thead th {
    text-align: center;
}
.table_title {
    padding-bottom: 10px;
}

.col-sm-offset-2.col-sm-10.form_btn {
    margin-top: 7px;
    margin-bottom: 7px;
}

           thead.first_thead th {
    height: 30px;
}
div#hg-form-container {
    margin-top: 15px;
}
</style>
		    <link rel="stylesheet" href="${basePath}/css/publish.css" />
			<script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
			<script type="text/javascript" src="${basePath}/js/form.js?version=98"></script>
			<script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.config-thumb.js?v=4"></script>
    	    <script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.all.js"></script>
    	    <script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/lang/zh-cn/zh-cn.js"></script>
		<link rel="stylesheet" href="${basePath }/js/utf8-jsp/third-party/codemirror/codemirror.css" />
	    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/themes/iframe.css" />
	    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/themes/default/css/ueditor.css" />
	    <script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/third-party/codemirror/codemirror.js"></script>
	    <script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/third-party/zeroclipboard/ZeroClipboard.js"></script>
		<script type="text/javascript" src="${basePath}/js/jquery-validation.min.js"></script>
		<script type="text/javascript" src="${basePath}/js/validation-message-zh.js?v=2"></script>
	</head>
	<body>
		<input type="hidden" class="mapValue" value='${mapNew }'>
		<input type="hidden" class="mapOld" value='${mapold }'>
		<input type="hidden" class="mapedit" value='${mapedit }'>
		<input type="hidden" class="planContent" value='${planContent }'>
		<div class="">
        	<div class="content_title">
           		${title }
        	</div>
        	<div class="content_form">
	        	<form class="form-horizontal" role="form" action="${submitForm }" method="post" enctype="multipart/form-data">
	                <div id="hg-form-container" class="form-group">
	                    <input id="content_id" type="hidden" name="content"/>
	                    <input id="submitFrom" type="submit" style="display:none;"/>  
						<input id="hiddenstate" type="hidden" name="state"/>
						<input id="infrom_id" type="hidden" name="infrom_id"/>
						<input id="newAndOld" type="hidden" name="newAndOld"/>
						<input id="meeting_id" type="hidden" name="meeting_id"/>
                        <input type="hidden" name="formId" value="${formId}"/>
						<input  class="btn btn-default" id="button2" type="button" value="取消" onclick="formsubmitgraft();"/>
						<input  class="btn btn_main" id="button1" type="button" value="发布" onclick="formsubmit();"/>
          	         </div>
	            </form>
			</div>
		</div>
		
		  <!--   <a href="javascript:;" data-toggle="modal" data-target="#commonStaff">常用人员</a> -->
    <!-- 模态框（Modal） -->
   <div class="modal fade check" id="commonStaff" tabindex="-1" role="dialog" aria-labelledby="commonStaffLabel" aria-hidden="true">
        <div class="modal-dialog" style="width:40%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                      &times;
                  </button>
                    <h4 class="modal-title" id="commonStaffLabel" style="font-size: 20px;">常用人员</h4>
                </div>
                <div class="modal-body">
                    <div class="common_list_container col-sm-5 col-xs-12 ">
                        <div class="table_title">常用列表</div>
                        <div class="table_container">
                            <table class="common_list">
                                <thead class="first_thead">
                                    <tr>
                                        <th> 选择</th>
                                        <th>名称</th>
                                        <th> 操作</th>
                                    </tr>
                                </thead>
                                <tbody class="use_group">
                                </tbody>
                            </table>
                        </div>

                        <div class="add_btn">
                            <button class="btn btn-sm btn-default">
                                <img src="/images/add_icon.png"/>添加
                            </button>
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <input type="text" class="form-control add_member_input groupName" placeholder="请输入新建小组名字">
                                    </div>
                                    <div class="col-sm-offset-2 col-sm-10 form_btn">
                                        <button type="button" class="btn btn-sm btn-default form_hide_btn">取消</button>
                                        <button type="button" class="btn btn-sm btn_main form_add_btn addGroup">确定</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                     <div class="divide_img col-sm-1 hidden-xs">
                        <img src="/images/modal_arrow.png"/>
                    </div>
                    <div class="common_member_container col-sm-7 col-xs-12">
                        <div class="table_title">常用人员</div>
                        <div class="table_container">
                            <div class="common_member_list">
                                <div class="member_title">
                                                                                                        已添加人员
                                </div>
                                <div class="member_content">
                                   <!--  <span>
                                                                                                                                 张三
                                            <img src="/images/X-icon.png"/>
                                            
                                    </span> -->
                                </div>
                            </div>
                        </div>
                        <div class="all_member_list">
                               <!--  <p>党支部人员</p> -->
                             <div class="member_list_title">
                                <p>党支部人员</p>
                                <div class="right">
                                    <div class="select_choice first_select_box">
                                        <img class="choice_all" src="/images/not_check_icon.png" />
                                        <input type="hidden" />
                                        <span>全选</span>
                                    </div>
                                    <div class="select_choice">
                                        <img class="against_choice" src="/images/not_check_icon.png" />
                                        <input type="hidden" />
                                        <span>反选</span>
                                    </div>
                                </div>
                            </div>
                                
                                <div class="list_item_box">
                                 
                                </div>
                        </div>
                </div>
                <div class="modal-footer">
                    <!-- <button type="button" class="btn btn-default" data-dismiss="modal">取消</button> -->
                    <button type="button" class="btn btn_main sure_add" style="float: right;">确定添加</button>
                </div>
            </div>
        </div>
    </div>
	
	<!-- 常用人员 -->
	
	<!-- 常用人员，组的增加，修改，删除 -->
	 <portlet:resourceURL id="/hg/paersonAndGroupAddDelete" var="paersonAndGroupAddDelete"/>
	 <portlet:resourceURL id="/hg/assignedAddPerson" var="assignedAddPerson"/>
	 	 <portlet:resourceURL id="/hg/getBranchAllPersons" var="getBranchAllPersons"/>
	     <portlet:resourceURL id="/hg/deletePerson" var="deletePerson"/>
	 
	 <script type="text/javascript" >
	 function getPlace(){
		 $.ajax({  
             url: '${getPlace}',  
             type: 'POST',  
             data: {start: $('input[name="timeDuring"]').val(), last: $('input[name="timeLasts"]').val()},
             dataType:'json',
             async: false,   
             success: function (data) {  
	                    console.log(data);
	                    var opts = "";
	                    opts += '<option value="" selected="selected">请选择</option>';
	                    for(var i = 0; i < data.length; i++){
	                    	 var c = data[i];
	                    	 if($(document).data("place")&&$(document).data("place")==c.place){
	                    		 opts += '<option selected="selected" value="'+c.place+'">' + c.place + '</option>'; 
	                    	 }else if(c.selected == true && c.place !== '其他'){
	                    		 opts += '<option class="already_select" value="'+c.place+'" style="color: red;">'+c.place+'(已被占用)</option>';
	                    	 }else{
	                    		 opts += '<option value="'+c.place+'">' + c.place + '</option>';
	                    	 }
	                    }
                 	 $("select[name='location']").html(opts);
             },  
             error: function (returndata) {  
                 alert("获取数据为空");  
             }  
        });
	 }
            $(function() {	
              console.log(11);
            var form = ${design};
            var uploadUrls = {
            		file:  '${uploadfileUrl}',
            		image: '${uploadimageUrl}',
            		video: '${uploadvideoUrl}'
            }
            var hgDoms = new HgDoms(form, uploadUrls, 'hg-form-container');
            var rules = {};
            console.log(JSON.stringify(form));
            for(var k in form.columns){
            	var column = form.columns[k];
            	if(column.validation || column.required){
            		var r = {};
            		if(column.validation){
            			var v = column.validateParam;
            			if(v){
                 			try{
	            				v = eval(v);
	            			}catch(e){
	            				console.log(e);
	            			}
            			}else{
            				v = true;
            			}
            			r[column.validation] = v;
            		}
            		if(column.required){
            			r.required = true;
            		}
            		rules[column.id] = r;
            	}
            }
            var canSubmit = true;
            $("#hg-form-container").parent().validate({             
                submitHandler: function(form) {
                	if(canSubmit){
                		canSubmit = false;
                		form.submit();
                        return false;
                	}
			    },
                rules: rules
            });
        
            $('input[name="timeDuring"], input[name="timeLasts"]').change(getPlace);
            /*开展地点弹框 提示 */
            $("select[name='location']").change(function(){
            	var place=$("select[name='location']").val();
            	$(".already_select").each(function(){            		
            		if($(this).attr("value")===place){
             		   $.hgConfirm("提示","该地点已经被占用了");
     		    	   $("#hg_confirm").modal("show");
     		           $("#hg_confirm .btn_main").click(function(){
     		           	  $("#hg_confirm").modal("hide");
     		           	   return;
     		           });
             	    }
            	});
            });
            if($(".mapValue").val()){
                var data=$(".mapValue").val();
    	    	data=data.substring(0,data.length);
    	    	console.log(data);
    	    	var j= JSON.parse(data); 
    	       $("select[name='secondaryCommittee']").prepend('<option  value="'+j.sconedId+'">'+j.sconedName+'</option>');
    	       $("select[name='secondaryCommittee']").attr("disabled","disabled");
    	         $("select[name='secondaryCommittee']").val(j.sconedId);
    	       $("select[name='branch']").prepend('<option  value="'+j.branchId+'">'+j.branchName+'</option>');
    	       $("select[name='branch']").attr("disabled","disabled");
    	         $("select[name='branch']").val(j.branchId);
    	       $("select[name='conferenceType']").prepend('<option value="'+j.meetingType+'">'+j.meetingType+'</option>');
    	         $("select[name='conferenceType']").val(j.meetingType);
    	       $("select[name='conferenceType']").attr("disabled","disabled");
    	       $("select[name='subject']").prepend('<option  value="'+j.meetingTheme+'">'+j.meetingTheme+'</option>');
    	         $("select[name='subject']").val(j.meetingTheme);
    	       $("select[name='subject']").attr("disabled","disabled");
    	       $("#infrom_id").val(j.infromid);
    	       $("#newAndOld").val(j.state);
    	       $(document).data("start_time",j.start_time);
    	       $(document).data("end_time",j.end_time);
            }
        	if($(".mapOld").val()){
        		console.log("ok");
                var data=$(".mapOld").val();
    	    	data=data.substring(0,data.length);
    	    	console.log(data);
    	    	var j= JSON.parse(data); 
    	    	console.log(j);
    	       $("select[name='secondaryCommittee']").prepend('<option  value="'+j.sconedParty+'">'+j.sconedParty+'</option>');      
    	           $("select[name='secondaryCommittee']").val(j.sconedParty);
    	       $("select[name='secondaryCommittee']").attr("disabled","disabled");   	      
    	       $("select[name='branch']").prepend('<option value="'+j.organization_id+'">'+j.org_name+'</option>');
    	           $("select[name='branch']").val(j.organization_id);
    	       $("select[name='branch']").attr("disabled","disabled");
    	       $("select[name='conferenceType']").prepend('<option  value="'+j.meeting_type+'">'+j.meeting_type+'</option>');
    	           $("select[name='conferenceType']").val(j.meeting_type);
    	       $("select[name='conferenceType']").attr("disabled","disabled");
    	       // $("select[name='subject']").prepend('<option  value="'+j.meeting_theme+'">'+j.meeting_theme+'</option>');
    	           $("input[name='subject']").val(j.meeting_theme);
    	       $("input[name='subject']").attr("disabled","disabled");
    	       $("input[name='timeDuring']").val(j.start_time);
    	        $("input[name='timeLasts']").val(j.total_time);
    	        $("input[name='host']").val(j.host);
    	        $("input[name='contact']").val(j.contact);
    	        $("input[name='phoneNumber']").val(j.contact_phone);
    	        $("input[name='sit']").val(j.sit); 
    	        $("input[name='customTheme']").val(j.meeting_theme_secondary);
    	        if(j.attachment_name){
    	           $("input[name='attachment']").parent().append("<span>"+j.attachment_name+"</span>");
    	           }
    	       $("#infrom_id").val(j.inform_id);
    	       $("#newAndOld").val(j.state);
    	       $("#meeting_id").val(j.meeting_id);
    	          hgDoms.ueditor.ready(function() {
           		  hgDoms.ueditor.setContent($(".planContent").val());
       		     });
    	          
    	          $(document).data("start_time",j.info_start);
       	          $(document).data("end_time",j.info_end);
       	          $(document).data("place",j.place);
       	          $(document).data("participant_group",j.participant_group);
       	         /*  $(document).data("organization_id",j.organization_id); */
        	}
        	/*计划编辑  */
        	if($(".mapedit").val()){
        		console.log("ok");
                var data=$(".mapedit").val();
    	    	data=data.substring(0,data.length);
    	    	console.log(data);
    	    	var j= JSON.parse(data); 
    	    	console.log(j);
    	       $("select[name='secondaryCommittee']").prepend('<option  value="'+j.sconedParty+'">'+j.sconedParty+'</option>');      
    	           $("select[name='secondaryCommittee']").val(j.sconedParty);
    	       $("select[name='secondaryCommittee']").attr("disabled","disabled");   	      
    	       $("select[name='branch']").prepend('<option  value="'+j.organization_id+'">'+j.org_name+'</option>');
    	           $("select[name='branch']").val(j.organization_id);
    	       $("select[name='branch']").attr("disabled","disabled");
    	       // $("select[name='conferenceType']").prepend('<option value="'+j.meeting_type+'">'+j.meeting_type+'</option>');
    	           $("select[name='conferenceType']").val(j.meeting_type);
    	       $("select[name='conferenceType']").attr("disabled","disabled");
    	       // $("select[name='subject']").prepend('<option  value="'+j.meeting_theme+'">'+j.meeting_theme+'</option>');
    	           $("input[name='subject']").val(j.meeting_theme);
    	       $("input[name='subject']").attr("disabled","disabled");
    	       $("input[name='timeDuring']").val(j.start_time);
    	        $("input[name='timeLasts']").val(j.total_time);
    	        $("input[name='host']").val(j.host);
    	        $("input[name='contact']").val(j.contact);
    	        $("input[name='phoneNumber']").val(j.contact_phone);
    	        $("input[name='sit']").val(j.sit); 
    	        $("input[name='customTheme']").val(j.meeting_theme_secondary);
    	        if(j.attachment_name){
    	           $("input[name='attachment']").parent().append("<span>"+j.attachment_name+"</span>");
    	           }
    	       $("#infrom_id").val(j.inform_id);
    	       $("#newAndOld").val(j.state);
    	       $("#meeting_id").val(j.meeting_id);
    	          hgDoms.ueditor.ready(function() {
           		  hgDoms.ueditor.setContent($(".planContent").val());
       		     });
    	          
    	          $(document).data("start_time",j.info_start);
       	          $(document).data("end_time",j.info_end);
       	          $(document).data("place",j.place);
    	          $(document).data("participant_group",j.participant_group);
    	          $(document).data("organization_id",j.organization_id);
        	}
            /*   获取开展地点  */
           getPlace();	       
	       var div='<div class="col-sm-6 col-xs-12"> '+
				       '<div class="col-sm-3 col-xs-3" >'+
			           '<span class="control-label form-label-required">参会人员</span> '+
			           '</div>'+
					 '<div class="col-sm-9 col-xs-9"> '+                               
					   '<select class="form-control ${class}" name="participate"></select> '+
			             '<a style="position:absolute;font-size:13px;"  href="javascript:;" class="_commonStaff"><img class="often_png" src="/images/often.png">常用人员</a> '+
					 '</div> '+                            
					'</div>'; 
	       if(!$(".mapedit").val()){
	    	   $("#hg-form-container > div").eq(7).replaceWith(div);
			}else{
				 $("select[name='participate']").attr("disabled","disabled");
			}
	       //获取常用组
	       var branchId=$(document).data("organization_id");
	       $.ajax({  
               url: '${getGroup}',  
               type: 'POST',
               data: {orgId:branchId},
               dataType:'json',
               async: false,   
               success: function (returndata) {  
	                    console.log(returndata)
	                    for(var i=0;i<returndata.length;i++){
	                    	if($(document).data("participant_group")&& $(document).data("participant_group")==returndata[i].group_id){
		                    	 $("select[name='participate']").append('<option  value="'+returndata[i].group_id+'">'+returndata[i].group_name+'</option>');
		                    	 $("select[name='participate']").val(returndata[i].group_id);
	                    	}else{
		                    	 $("select[name='participate']").append('<option value="'+returndata[i].group_id+'">'+returndata[i].group_name+'</option>');
	                    	}
	                    	/*  $("select[name='participate']").append('<option value="'+returndata[i].group_id+'">'+returndata[i].group_name+'</option>'); */
	                    	 var tr=$('<tr> '+
	                                   '<td class="check_td">'+                                  
	                                       '<img class="chang_img" src="/images/radio.png" />'+
	                                       '<input type="hidden" value="'+returndata[i].group_id+'"/> '+
	                                  '</td> '+
	                                  '<td>'+returndata[i].group_name+'</td> '+
	                                   '<td> '+
	                                      '<img class="delete_gruop" src="/images/delete_icon.png"/> '+
	                                      '<input type="hidden" value="'+returndata[i].group_id+'"/> '+
	                                  '</td> '+
	                               '</tr>');
	 	                           
		                    	 $(".use_group").append(tr);                   
	                    }
               },  
               error: function (returndata) {  
            	   console.log("cuowu");  
               }  
          });
	      
	       /*获取支部的说有人员  */
	       $.ajax({  
               url: '${getBranchAllPersons}',  
               type: 'POST', 
               data:{orgId:$(document).data("organization_id")},
               dataType:'json',
               async: false,   
               success: function (data) { 
            	        for(var i=0;i<data.length;i++){
            		     var span=$('<span class="branch_person">'+data[i].user_name+'</span>'+
            		                '<input type="hidden" value="'+data[i].user_id+'"/>');
            		     $(".list_item_box").append(span);
            	   }
               }
               
	       });
  });
        	
</script>
	
	
	
			<script type="text/javascript">
			

			   $("input[name='host']").blur(function(){
			  	 var host = $("input[name='host']").val();
			  	 var reg = /^[\u4E00-\u9FA5]{2,5}$/;
			  	 if(!reg.test(host)){
			  		  $.hgConfirm("提示","请输入正确人名。 ");
				    	   $("#hg_confirm").modal("show");
				           $("#hg_confirm .btn_main").click(function(){
				           	  $("#hg_confirm").modal("hide");	
				           });
			  	 }else{
			  		  $("input[name='contact']").focus();
			  	 }
			   });
			   
			   
			   
			   $("input[name='contact']").blur(function(){
			  	 var contact = $("input[name='contact']").val();
			  	 var reg = /^[\u4E00-\u9FA5]{2,5}$/;
			  	 if(!reg.test(contact)){
			  		   $.hgConfirm("提示","请输入正确人名。 ");
				    	   $("#hg_confirm").modal("show");
				           $("#hg_confirm .btn_main").click(function(){
				           	  $("#hg_confirm").modal("hide");	
				           });
			  	 }else{
			  		  $("input[name='sit']").focus();
			  	 }
			   });
			   
			   $("input[name='phoneNumber']").blur(function(){
				  	 var phoneNumber = $("input[name='phoneNumber']").val();		  	 
				  	 var mobile = /^\d{8,11}$/;
				  	 if(!mobile.test(phoneNumber)){
				  		  $.hgConfirm("提示","请输入正确电话号码。");
				    	  $("#hg_confirm").modal("show");
				          $("#hg_confirm .btn_main").click(function(){
				           	 $("#hg_confirm").modal("hide");	
				          });
				  	 }
				   });

				   
			/* 	   $("input[name='phoneNumber']").blur(function(){
					  	 var phoneNumber = $("input[name='phoneNumber']").val();
					  	 var phone = /^\d{8,11}$/;
					  	 if(phone.test(phoneNumber)){
					  		  console.log("输入号码类型正确");
					  	 }else{
					  		  $.hgConfirm("提示","请输入正确电话号码。");
					    	  $("#hg_confirm").modal("show");
					          $("#hg_confirm .btn_main").click(function(){
					           	 $("#hg_confirm").modal("hide");	
					          });
					  	 }
					   });  */   
		
			/* 取消 */
		     function formsubmitgraft(){ 
		    	   /* $.hgConfirm("提示","确定取消吗？");
		    	   $("#hg_confirm").modal("show");
		           $("#hg_confirm .btn_main").click(function(){
		           $("#hg_confirm").modal("hide");
		                $("#hiddenstate").val(0);
		                window.location.href="/task";
		                $.tip("取消成功！");
		          }); */
		    	 window.history.back();
		     }
		 
		     /*提交  */
		     var _pathName = window.location.pathname;
		     function  formsubmit(){
		    	     var totalTime= $("input[name='timeLasts']").val();
		    	     var regu = /^[1-9]\d*$/;		    	     
		    	     if (!regu.test(totalTime)) { 
		    	    	// $.tip("请输入正确的数字！");
		    	    	 showConfirm("请输入正确的时长分钟数（如：30）！");
		    	    	 return false;
		    	     }
		    	     var bool=showConfirmDate();
		    	     if(!bool){return;}
		    	     
		    	     var phoneNumber = $("input[name='phoneNumber']").val();		  	 
				  	 var mobile = /^\d{8,11}$/;
				  	 if(!mobile.test(phoneNumber)){
				          showConfirm("请输入正确电话号码。");
				          return;
				  	 }
		    	     
		    	     $.hgConfirm("提示","确定发布吗？");
		    	     $("#hg_confirm").modal("show");
		             $("#hg_confirm .btn_main").click(function(){
		             $(this).attr("disabled",true);
		             $("#hg_confirm").modal("hide");
		             $("select[name='subject'],select[name='secondaryCommittee']").removeAttr("disabled");
		             $("select[name='conferenceType'],select[name='branch'],select[name='participate']").removeAttr("disabled");
		             $('#submitFrom').click();
		             if(window.location.pathname == _pathName){
		            	 $("#hg_confirm .btn_main").attr("disabled",false);
	                 }
		          });		    	
		     }
		     	
		       //常用人员弹窗显示
		      $("#hg-form-container").on("click","._commonStaff",function(){
			         $(".chang_img").attr("src","/images/radio.png");
			         $(".add_btn").css("display","block");
			         $(".member_content").empty();
			    	 $("#commonStaff").modal("show");
			     });
		       /*常用人员弹窗关闭  */
		        $(".close_modal").click(function(){
    	             $("#commonStaff").modal("hide");
                   });
		        /* 常用人员 添加 */
		        $(".add_btn").on("click", ">.btn", function() {
		        	 $(this).css("display","none");
		            $(this).siblings("form").css("display", "inline-block");
		        })

		       /*  取消添加弹窗 */
		        $(".add_btn").on("click", ".form_hide_btn", function() {
		            $(this).parents("form").css("display", "none");
		            $(this).parents("form").siblings(".btn").css("display", "inline-block");
		            $(this).parents(".form-group").find(".add_member_input").val("");
		        })

		        /* 常用人员  添加按钮消失 */
		        $(".use_group").on("click",".check_td",function(){
		       	 if($(this).children("img").attr("src").indexOf("_on") > -1){
		                $(this).children("img").attr("src","/images/radio.png");
		                /*   $(this).parents(".table_container").siblings(".add_btn").css("display","block");*/
		            }else{
		                $(".check_td img").each(function(){
		                    // console.log($(this).attr("src"));
		                    $(this).attr("src","/images/radio.png");
		                })
		                $(this).children("img").attr("src","/images/radio_on.png");
		             /*   $(this).parents(".table_container").siblings(".add_btn").css("display","none");*/
		            } 
		       	        $(".member_content").empty();
		                var groupId=$(this).find("input").val();
		      	         $.ajax({  
		                     url: '${paersonAndGroupAddDelete}',  
		                     type: 'POST',  
		                     data: {groupId:groupId,path:"getGroupPersons"},
		                     dataType:'json',
		                     async: false,   
		                     success: function (data) {
		                  	   console.log("图片变换"+data);
		                  	  /*  $(".member_content").empty(); */
		                  	   for(var i=0;i<data.length;i++){
		                  		   var c=data[i];		
		                  		   var tr=$('<span>'+c.member_name+'<img class="delete_img" src="/images/X-icon.png"/>'+
		                  				    '<input type="hidden" value="'+c.participant_id+'"/> '+
		                  		            '</span>');
		                             $(".member_content").append(tr);
		                  	      }
		                     }
		      	           });
		       	 
		        });

		//选择按钮图片变化        
	/* 	$(".use_group").on("click","img",function(){
			 $(".use_group img").attr("src","/images/radio.png");  
			 $(this).attr("src","/images/radio_on.png");
			 var groupId=$(this).next().val();
			  $.ajax({  
		               url: '${paersonAndGroupAddDelete}',  
		               type: 'POST',  
		               data: {groupId:groupId,path:"getGroupPersons"},
		               dataType:'json',
		               async: false,   
		               success: function (data) {
		            	   $(".group_members").empty();
		            	   for(var i=0;i<data.length;i++){
		            		   var c=data[i];		
		            		   var tr=$('<tr><td>'+c.group_name+'</td><td>'+c.member_name+'</td></tr>');
		                       $(".group_members").append(tr);
		            	   }
		               }
			  })
        }); */
		       
        /* 删除小组 */
        $(".use_group").on("click",".delete_gruop",function(){	
       	    var self = this;
             	var groupId=$(this).next().val();
             		$.hgConfirm("提示","你确认要删除吗？");
          	        $("#hg_confirm").modal("show");
                   $("#hg_confirm .btn_main").click(function(){
                   $("#hg_confirm").modal("hide");
             	 $(self).parent().parent().remove();
             	 $.ajax({  
                     url: '${paersonAndGroupAddDelete}',  
                     type: 'POST',  
                     data: {groupId:groupId,path:"deleteGroup"},
                     dataType:'json',
                     async: false,   
                     success: function (data) {
                   	     $("select[name='participate'] option").each(function(){
       	            			if($(this).attr("value") == groupId){
       	            			$(this).remove();
       	            		        }	        
       	                     	});
                     }
             	   });
             	   /*   $.tip("删除成功！"); */
             	 }); 
             });
        /*  添加小组 */
        $(".addGroup").click(function(){
           	var groupName=$(".groupName").val();
           	var branchId=$("select[name='branch']").val();
           	if(!groupName){  showConfirm("小组名不能为空！");return;}
            $(this).parents(".form-horizontal").siblings("button.btn").css("display","inline-block");
            $(this).parents(".form-horizontal").css("display","none");
            	$.ajax({  
                   url: '${paersonAndGroupAddDelete}',  
                   type: 'POST',  
                   data: {groupName:groupName,branchId:branchId,path:"addGroup"},
                   dataType:'json',
                   async: false,   
                   success: function (data) {
                                  var tr=$(' <tr> '+
                                           '<td class="check_td"> '+                                 
                                               '<img class="chang_img" src="/images/radio.png" />'+
                                               '<input type="hidden" value="'+data.uuid+'"/> '+
                                          '</td> '+
                                          ' <td>'+groupName+'</td> '+
                                           '<td> '+
                                               '<img class="delete_gruop" src="/images/delete_icon.png"/> '+
                                              '<input type="hidden" value="'+data.uuid+'"/> '+
                                          ' </td> '+
                                       '</tr>');
                        	 $(".use_group").append(tr);
                        	 $(".groupName").val("");
                        	 $("select[name='participate']").append('<option value="'+data.uuid+'">'+groupName+'</option>');
                                
                            }
              });
           });  		 
		     //添加常用人员
		     $(".add_person").on("change",function(){	       
		             var url="${assignedAddPerson}"
		             var userName=$(".add_person").val();
		             var data={userName:userName,path:"find"};
		             $(".addPersons").empty();
		             $.ajax({
			    		     url:url,
			    		     data:data,
			    		     dataType:"json",
			    		     success:function(result){
			    		    	     console.log(result); 
					    		     if(result.state=='succeed'){
					    		    	 for(var i=0;i<result.data.length;i++){
					    		    		 var c=result.data[i];
					    		    		 $(".addPersons").append('<label><input name="Fruit" type="radio" value="'+c.user_id+'"/>'+c.user_name+'</label>');
					    		    	  }
					    		      }
					    		    if(result.state=='fail'){
					    		    	    $(".addPersons").append('<p style="color:red;">'+result.message+'</p>');
					    		          }
			    		       }
		    	           });
		     
		     });
		     
		     /*添加人员  */ 
		     $(".sure_add").click(function(){
		     	 var groupId=$("img[src='/images/radio_on.png']").next().val();
		     	 var assigne_user_id=$('input:radio:checked').val();
		     	 
		     	   var userIds=$(".person_class");          
		           var resourcesId = new Array("");
		            for(var i=0;i<userIds.length;i++){
		            	var resourceId=$(userIds[i]).next().val();
		              	resourcesId.push(resourceId);
		                } 
		          var assigne_user_id = resourcesId.join(",").substring(1)+"";
		     	 if(!groupId){
		     		 showConfirm("请先选择小组！");
		     		 return;}
		     	 if(!assigne_user_id){
		     		  showConfirm("请选择人员 ！");
		     		 return;}
		     	 var url="${paersonAndGroupAddDelete}"
		          var data={participant_id:assigne_user_id,groupId:groupId,path:"addPerson"};            
		              $.ajax({
		     		     url:url,
		     		     data:data,
		     		     dataType:"json",
		     		     success:function(result){
		     		    if("ok"==result.state){
		     		    	var arr = [];
		     		    	$(".member_content input").each(function(){
		     		    		var id = $(this).val();
		     		    		arr.push(id);
		     		    	})
		     		    	var arr2 = [];
		     		    	$(".person_class").each(function(){
		     		    	    var name= $(this).html();
		     		    	    var id= $(this).next().val();
		     		    		arr2.push({"name":name,"id":id})
		     		    	})

		     		    	for(var i = 0; i<arr2.length;i++){
		     		    	    var repeat = false;
		     		    		for(var j=0;j<arr.length;j++){
		     		    			 if(arr2[i].id == arr[j]){
		     		    				repeat = true;
		     		    	　　　　　　 break;
		     		    	        }
		     		    		}
		     		    	   
		     		    		if(!repeat){
		     		    	  		var tr=$('<span>'+arr2[i].name+'<img class="delete_img" src="/images/X-icon.png"/>'+
		     		    	                                            '<input type="hidden" value="'+arr2[i].id+'"/> '+
		     		    	                                            '</span>');
		     		    	          $(".member_content").append(tr);
		     		    		}
		     		    	}
		     		    	   $.hgConfirm("提示","添加人员成功！");
	    			    	   $("#hg_confirm").modal("show");
	    			           $("#hg_confirm .btn_main").click(function(){
	    			        	    $("#hg_confirm").modal("hide"); 
	    			        	    $("#commonStaff").modal("hide");        
	    			            }); 
	    			          /*  showConfirm("添加人员成功！"); */
		     		    	}else{
		     		    		// alert(result.message);
		     		    	}
		     		     }
		              });
		        
		     });
		    				     
				     //格式化日期	     
				     function getDateByLong(dateLong){
				    	 var date = new Date(dateLong);
				         var moth=date.getMonth()+1;
				         if(moth<10){moth="0"+moth;}
				         var day=date.getDate();
				         if(day<10){day="0"+day;}
				         var hour=date.getHours();
				         if(hour<10){hour="0"+hour;}
				         var minuts=date.getMinutes();
				         if(minuts<10){minuts="0"+minuts;}
				         var ss=date.getSeconds();
				         if(ss<10){ss="0"+ss;}
				         var defaultTime=date.getFullYear()+'-'+moth+'-'+day+' '+hour+':'+minuts+':'+ss; 
				    	 return defaultTime;
				    }  
				     

			  
			  
		     function showConfirmDate(){
		    		var startTimeLong=new Date($("input[name='timeDuring']").val().replace(" ","T")+'+08:00').getTime(); 
			    	var endTimeLong=($("input[name='timeLasts']").val())*60*1000+startTimeLong;
			    	var sDate=getDateByLong($(document).data("start_time"));
			    	var eDate=getDateByLong($(document).data("end_time"));
			    	var boolem=true;
			    /* 	if($("select[name='conferenceType']").val()==='主题党日'){
			    		if($("input[name='timeLasts']").val()<80){
			    			   boolem=false;
			    			   $.hgConfirm("提示","主题党日必须大于80分钟");
					    	   $("#hg_confirm").modal("show");
					           $("#hg_confirm .btn_main").click(function(){
					           $("#hg_confirm").modal("hide");	
					          
					              });
			    		}
			    	} */
			    	console.log(startTimeLong+" ;"+endTimeLong+" ;"+sDate+" ;"+eDate);
		            if(!startTimeLong){
		            	   boolem=false;
		            	   $.hgConfirm("提示","请选择在规定时间范围内：   "+sDate+" —— "+eDate);
				    	   $("#hg_confirm").modal("show");
				           $("#hg_confirm .btn_main").click(function(){
				           $("#hg_confirm").modal("hide");			       
				              });	
			    	}
			    	if(startTimeLong>$(document).data("end_time")){	
			    		   boolem=false;
			    		   $.hgConfirm("提示","请选择在规定时间范围内：   "+sDate+" —— "+eDate);
				    	   $("#hg_confirm").modal("show");
				           $("#hg_confirm .btn_main").click(function(){
				           $("#hg_confirm").modal("hide");			       
				              });
			    	}else if(endTimeLong>$(document).data("end_time")){
			    		   boolem=false; 
			    		   $.hgConfirm("提示","请选择在规定时间范围内：   "+sDate+" —— "+eDate);
				    	   $("#hg_confirm").modal("show");
				           $("#hg_confirm .btn_main").click(function(){
				           $("#hg_confirm").modal("hide");			       
				              });
			    	}else if(startTimeLong<$(document).data("start_time")){
			    		   boolem=false;
			    		   $.hgConfirm("提示","请选择在规定时间范围内：   "+sDate+" —— "+eDate);
				    	   $("#hg_confirm").modal("show");
				           $("#hg_confirm .btn_main").click(function(){
				           $("#hg_confirm").modal("hide");			       
				              });
			    	}
			    	
			    	return boolem;
		      }
				     
				     
				     
			     function showConfirm(info){
			    	  $.hgConfirm("提示",info);
		              $("#hg_confirm").modal("show");
		              /* $("#hg_confirm .btn_main").unbind("") */
		              $("#hg_confirm .btn_main").click(function(){
		              $("#hg_confirm").modal("hide");
		                  return;            	              
		            });	
			     }
			     
			     
			     $("input[name='timeLasts']").attr('placeholder','请输入正确的会议时长分钟数（如：30）');
			     $("#hg-form-container").on("blur","input[name='timeLasts']",function(){
			    	 $('select[name="conferenceType"]').removeAttr("disabled");
					 var type = $('select[name="conferenceType"]').val();
					 var timelasts = $('input[name="timeLasts"]').val();
					 $('select[name="conferenceType"]').attr("disabled","disabled");
					 if(!isNaN(timelasts)){
						 if (type=='主题党日'){
							 if(timelasts < 80){
								 showConfirm("主题党日类型活动时长需要>= 80 分钟。");
							 }else{
								 showConfirmDate(); 
							 }
						  }else{
							 if(timelasts < 1){
								 showConfirm('请输入正确的会议时长分钟数（如：30）');
							 }else{
							 	showConfirmDate(); 
							 }
						  }
		                }else{
		                	showConfirm('请输入正确的会议时长分钟数（如：30）');
		                }
			     });
			     
			     /* 支部人员点击效果切换 */
			     $(".list_item_box").on("click",".branch_person",function(){	         
			          if($(this).hasClass("person_class")){
			        	  $(this).css("background","white"); 
			        	  $(this).removeClass("person_class");
			          } else{
			        	  $(this).css("background","#CE0000"); 
			        	  $(this).addClass("person_class");
			          }
			      });
			     /* 删除常用组人员 */
			     $(".member_content").on("click",".delete_img",function(){
			    	 var self = this;
			    	 var groupId=$("img[src='/images/radio_on.png']").next().val();
			    	 var user_id=$(this).next().val();	 
			    	 if(!groupId){
			    		 showConfirm("请先选择小组！");
			    		 return;}
			    	 if(!user_id){
			    		  showConfirm("请选择人员 ！");
			    		 return;}
			    	 var url="${deletePerson}";
			    	 var data={groupId:groupId,user_id:user_id};
			    	  $.hgConfirm("提示","你确定删除吗！");
			          $("#hg_confirm").modal("show");
			          $("#hg_confirm .btn_main").click(function(){
			          $("#hg_confirm").modal("hide");
			          $.ajax({
				   		     url:url,
				   		     data:data,
				   		     dataType:"json",
				   		     success:function(result){
		 		    	         if(result.state==="ok"){
		 		    	        	$(self).parent().remove(); 
		 		    	         }else{
		 		    	        	 //showConfirm("删除失败！"); 
		 		    	         }
		 		             }
			    	  });    	              
			        });	
			     });
			     
			     
			     
			     /*全选*/
			     $(".choice_all").click(function(){
		             $(this).attr("src","/images/checked_icon.png");
			    	 $(".branch_person").css("background","#CE0000"); 
			    	 $(".list_item_box").find(".person_class").removeClass("person_class");
			    	 $(".branch_person").addClass("person_class");
			    	 $(".against_choice").attr("src","/images/not_check_icon.png");

			     });
			     /* 反选 */
			     $(".against_choice").click(function(){
		             $(this).attr("src","/images/checked_icon.png");
		             $(".branch_person").each(function(){
		            	 if($(this).hasClass("person_class")){
		            		 $(this).css("background","white"); 
		   	        	     $(this).removeClass("person_class"); 
		            	 }else{
		            		 $(this).css("background","#CE0000"); 
		   	        	     $(this).addClass("person_class");
		            	 }
		             });
			    	 $(".choice_all").attr("src","/images/not_check_icon.png");
			     });
		 
		</script>
	</body>
</html>