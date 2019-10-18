<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>



<!-- 保存新增站点 -->
 <portlet:actionURL name="${submitCommand }" var="submitForm"/>

 <portlet:resourceURL id="/hg/getPublicObject" var="getPublicObject"/>
 <portlet:resourceURL id="/hg/getMeetingTypeAndTheme" var="getMeetingTypeAndTheme"/>
 
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
		    margin-right: 10px;
		}
		textarea {
		    width: 100%;
		    height: 100px;
		}
		input#button2 {
            float: left;
            margin-left: 10px;
        }
        label.error{
           position: absolute;
		   color: #ce0000;
		   font-size: 12px;
		   font-weight: normal;
        }
           /* .control-label::before{
            content: "*";
            position: absolute;
            top: 0;
            left: -5px;
            color: #ce0000;
            line-height:40px;
        }
        .remove_star::BEFORE {
	        content: "";
          }  */
	</style>
		    <link rel="stylesheet" href="${basePath}/css/publish.css" />
			<script type="text/javascript" src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
			<script type="text/javascript" src="${basePath}/js/form.js?version=5"></script>
			<script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.config-thumb.js?v=3"></script>
    	    <script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.all.js"></script>
    	    <script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/lang/zh-cn/zh-cn.js"></script>
		<link rel="stylesheet" href="${basePath }/js/utf8-jsp/third-party/codemirror/codemirror.css" />
	    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/themes/iframe.css" />
	    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/themes/default/css/ueditor.css" />
	    <script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/third-party/codemirror/codemirror.js"></script>
	    <script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/third-party/zeroclipboard/ZeroClipboard.js"></script>
		<script type="text/javascript" src="${basePath}/js/jquery-validation.min.js"></script>
		<script type="text/javascript" src="${basePath}/js/validation-message-zh.js?v=2"></script>
		
		<script type="text/javascript" >
            $(function() {		
            var form = ${design};
            var uploadUrls = {
            		file: '${uploadfileUrl}',
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
            $('.datetime').attr('onClick', "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})");
            $("select[name='isComment']").append('<option value="t">启用评价</option>');
            $("select[name='isComment']").append('<option value="f">不启用评价</option>');
          /* $("select[name='isComment']").append('<option value="m">必须评价</option>'); */
           /*  $("select[name='note']").append('<option value="secondParty">二级党委</option>');
            $("select[name='note']").append('<option value="partyBranch">党支部</option>');  */         
             var div= '<div class="col-sm-6 col-xs-12 publish_obj_container">' +
             '<div class="col-sm-3 col-xs-3">' +
               '<span class="control-label">发布对象</span>' +
             '</div>'+
            '<div class="col-sm-9 col-xs-9 publish_obj">' +
           '<div class="publish_obj_content">' +
          '<div class="publish_obj_title">' +
                 '二级党组织' +
           '<div class="right">' +
              ' <div class="select_choice all_select">' +
                  ' <img src="/images/not_check_icon.png" />' +
                  ' <input type="hidden" />' +
                  ' <span>全选</span>' +
               '</div>'+
              ' <div class="select_choice oppsite_select">' +
                  ' <img src="/images/not_check_icon.png" />' +
                   '<input type="hidden" />' +
                   '<span>反选</span>' +
              ' </div>' +
           '</div>' +
      ' </div>' +
       '<div class="publish_obj_info container_scroll_hidden">' +
           '<ul class="list-group">' +
           '</ul>' +
       '</div>' +
  ' </div>' +
'</div>' +
'</div>' +
'<div class="col-sm-6 col-xs-12 has_select_div">' +
'<div class="has_select_container">' +
  ' <div class="has_select_title">' +
      ' <span>已选goo</span>' +
       '<div class="right">' +
           '<span>已选择</span>' +
           '<span class="select_num"></span>' +
          '<span>个党委</span>' +
       '</div>' +
  ' </div>' +
  ' <div class="has_select_content container_scroll_hidden">' +
      ' <ul class="has_select_list">' +
      ' </ul>' +
   '</div>' +
'</div>' +
'</div>'; 
            var title="${publicObjectTitle}";
            div=div.replace("二级党组织",title);
            div=div.replace("已选goo",title);
            div=div.replace("个党委",title);
			$("#hg-form-container > div").eq(4).replaceWith(div);
            $("#hg-form-container > div").eq(4).find(".control-label").addClass("form-label-required");
            $.ajax({
                url: '${getPublicObject}',  
                type: 'POST',  
                data: "",
                dataType:'json',
                async: false,   
                success: function (returndata) {                     
	                    for(var i=0;i<returndata.length;i++){
	                    	var li='<li class="list-group-item table_info">' +
	         	           '<span title="'+ returndata[i].org_name +'">'+returndata[i].org_name+'</span>' +
	        	           '<input type="hidden" value="'+returndata[i].org_id+'"/>' +
	        	           '<img class="right" src="/images/radio.png" />' +
	        	           '</li>' ;
	                    	 $(".list-group").append(li);
	                    }
                },  
                error: function (returndata) {  
                       alert("获取数据失败");  
                }  
           }); 
        /*   获取会议类型 */
        $.ajax({
	            url: '${getMeetingTypeAndTheme}',  
	            type: 'POST',  
	            data: "",
	            dataType:'json',
	            async: false,   
	            success: function (data) {  
	                    for(var i=0;i<data.length;i++){
	                         var c=data[i];
	                    	if(c.type=='meetingType'){
	                    		 $("select[name='meetingType']").append('<option value="'+c.resources_value+'">'+c.resources_value+'</option>');
	                    	}	
	                    	if(c.type=='news'){
	                    		 $("select[name='theme']").append('<option value="'+c.resources_value+'">'+c.resources_value+'</option>');
	                    	}	
	                    }
	            },  
	            error: function (data) {  
	                   alert("获取数据失败");  
	            }  
       });  
                   /*  设置默认发布时间 */
        var date = new Date();
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
        
        $("input[name='publicDate']").val(defaultTime);
       /*  把会议内容放到标签上 */
        var infromData=$(".informDat").val();
        if(infromData){
         infromData=infromData.substring(0,infromData.length);
         var c= JSON.parse(infromData); 
       	 $("select[name='meetingType']").prepend('<option  value="'+c.meeting_type+'">'+c.meeting_type+'</option>');
       	 $("select[name='meetingType']").val(c.meeting_type);
       	 $("input[name='theme']").val(c.meeting_theme);
            $("input[name='startDate']").val(c.start_time);
            $("input[name='publicDate']").val(c.release_time);
            if(c.enable_comment=='t'){$("select[name='isComment'] option:eq(1)").prop("selected", 'selected');}
            if(c.enable_comment=='m'){$("select[name='isComment'] option:eq(3)").prop("selected", 'selected');}
            if(c.enable_comment=='f'){$("select[name='isComment'] option:eq(2)").prop("selected", 'selected');}
            $("input[name='end_time']").val(c.end_time);      
            $("input[name='deadline_time']").val(c.deadline_time);      
            hgDoms.ueditor.ready(function() {
          		hgDoms.ueditor.setContent($(".informationContent").val());
      		}); 
           $("#editState").val(c.graftEditState);
           
           if(c.graftEditState === "resend"){
        	   /*
        	   	TODO: 转派时判断开始结束时间
        	   */
               $('select[name="meetingType"]').attr('disabled', 'disabled');
               $('input[name="theme"]').attr('readonly', 'readonly');
               $('select[name="isComment"]').attr('disabled', 'disabled');
               $('input[name="publicDate"]').attr('disabled', 'disabled');

           }else if(c.graftEditState === "orgEdit"){
        	   $('select[name="meetingType"]').attr('disabled', 'disabled');
               $('input[name="theme"]').attr('readonly', 'readonly');
        	   $(".publish_obj_container").css("display","none");
        	  
           }
           $("#infrom_id").val(c.inform_id);
           if(c.attachment_name){
               $("input[name='ajaxFileName']").parent().append("<span>"+c.attachment_name+"</span>");
             }
        }
         
   });
	</script>
	</head>
	<body>
		
		    <input class="informDat" type="hidden" value='${informData }'> 
		    <input id="isresend" type="hidden" value='${resend }'> 
		    <input id="orgEdit" type="hidden" value='${orgEdit }'> 
		    <input id="orgType" type="hidden" value='${orgType }'> 
		    <input class="informationContent" type="hidden" value='${informationContent }'> 
        	<div class="content_title">	          
       		<c:choose>
       		    <c:when test="${resend == 'resend'}">
       		               <a href="/backlogtwo" style="color: #ce0000;">待办事项</a>>部署
				</c:when>
				 <c:when test="${orgEdit == 'orgEdit'}">
       		                                                   编辑
				</c:when>
				<c:otherwise>
				    新发布   
				</c:otherwise>
			</c:choose>       
        	</div>
        	<div class="content_form">
	        	<form  class="form-horizontal new_publish_form" role="form" action="${submitForm }" method="post" enctype="multipart/form-data">
	                <div id="hg-form-container" class="form-group" style="padding-top: 20px;">
	                    <input id="editState" type="hidden" name="editState">
	                    <input id="infrom_id" type="hidden" name="infrom_id">
	                    <input id="submitFrom" type="submit" style="display:none;"/>  
						<input id="hiddenstate" type="hidden" name="state"/>
						<input id="hiddenPublicObject" type="hidden" name="publicObject"/>
						<input type="hidden" name="formId" value="${formId}"/>
						<c:choose>
							<c:when test="${resend == 'resend'}">
								<input class="btn btn_main" id="button1" type="button" value="部署" onclick="formsubmit();"/>
							</c:when>
							<c:when test="${orgEdit == 'orgEdit'}">
								<input class="btn btn_main" id="button1" type="button" value="编辑" onclick="formsubmit();"/>
							</c:when>
							<c:otherwise>
								<input class="btn btn-default" id="button2" type="button" value="保存为草稿" onclick="formsubmitgraft();"/>
								<input class="btn btn_main" id="button1" type="button" value="发布" onclick="formsubmit();"/>
							</c:otherwise>
						</c:choose>
          	         </div>
	            </form>
			</div>
		 <script>
		/*  保存草稿 */
		 function formsubmitgraft(){
			    var deadline_time=$("input[name='deadline_time']").val().replace(" ","T");
	         	var startDate=$("input[name='startDate']").val().replace(" ","T");	
	         	var nowDate=getNowDate();
	         	console.log(nowDate);
	         	if(deadline_time>startDate){
	                 showConfirm("截止时间应该小于开始时间 !");	
	                return;
	         	}
			
		    	$("#hiddenstate").val(0); 
		    	var imgs=$(".table_info img[src='/images/radio_on.png']");          
	            var resourcesId = new Array("");
	            for(var i=0;i<imgs.length;i++){
	            	var resourceId=$(imgs[i]).prev().val();
	            	resourcesId.push(resourceId);
	                } 
	            var resources = resourcesId.join(",").substring(1)+"";
	            $("#hiddenPublicObject").val(resources); 
	            if(!$("#hiddenPublicObject").val()){
	            	 showConfirm("请选择发布对象！");	            	
	            	return;  }
	            var startDateStr=$("input[name='startDate']").val().replace(" ","T");
	            var endDateStr=$("input[name='end_time']").val().replace(" ","T");
	        	var timestampStartDate=Date.parse(new Date(startDateStr));
	        	var timestampEndDate=Date.parse(new Date(endDateStr));
	        	if(timestampEndDate<timestampStartDate){
	        		showConfirm("请选择正确时间！ 结束时间小于了开始时间？");	 
	        		return;
	        		}
	            /*主题党日  */
	        	var startDate=new Date($("input[name='startDate']").val().replace(" ","T")).getTime();
	        	var end_time=new Date($("input[name='end_time']").val().replace(" ","T")).getTime();
	        	var minutes=(end_time-startDate)/1000/60;
	        	var meetingType=$("select[name='meetingType']").val();
	        	if(meetingType==='主题党日' && minutes<80){
	        		showConfirm("主题党日的时间必须大于等于80分钟！");		        	
	        		return;
	        	}
	     	   /*校验截止时间  checkDedetime(); */
	        	var deadline_time=$("input[name='deadline_time']").val();
	         	var startDate=$("input[name='startDate']").val();	
	         	var nowDate=getNowDate();
	         	console.log(nowDate);
	         	if(deadline_time>startDate){
	                 showConfirm("截止时间应该小于开始时间 !");		        	
	                 return;
	         	}
	         	
	            $.hgConfirm("提示","确定要保存为草稿吗！ ");
	            $("#hg_confirm").modal("show");
	            $("#hg_confirm .btn_main").click(function(){
	            $("#hg_confirm").modal("hide");
	               $('#submitFrom').click(); 
	            });

		     }		 
		     /* 发布 */
		     var _pathName = window.location.pathname;
		     function  formsubmit(){		  
		    	    $("#hiddenstate").val(1); 
		    	    var imgs=$(".table_info img[src='/images/radio_on.png']");          
		            var resourcesId = new Array("");
		            for(var i=0;i<imgs.length;i++){
		            	var resourceId=$(imgs[i]).prev().val();
		            	resourcesId.push(resourceId);
		                } 
		            var resources = resourcesId.join(",").substring(1)+"";
		            $("#hiddenPublicObject").val(resources); 
		            if($("#orgEdit").val()!="orgEdit" && !$("#hiddenPublicObject").val()){
		            	 showConfirm("请选择发布对象！");	            	
		            	return;  }
		            var startDateStr=$("input[name='startDate']").val();
		            var endDateStr=$("input[name='end_time']").val();
		        	//var timestampStartDate=Date.parse(new Date(startDateStr));
		        	//var timestampEndDate=Date.parse(new Date(endDateStr));	
		        	//console.log(startDateStr+";"+endDateStr+";"+timestampStartDate+";"+timestampEndDate);
		        	if(endDateStr<startDateStr){
		        		showConfirm("请选择正确时间！ 结束时间小于了开始时间？");		        	
		        		return;
		        	}else if($('#isresend').val() === 'resend'){
		        		 var infromData=$(".informDat").val();
		        		     infromData=infromData.substring(0,infromData.length);
				         var c= JSON.parse(infromData); 
		        		if(startDateStr < c.start_time || endDateStr > c.end_time){
			        	/* 	$.tip("请选择正确时间！必须在组织指定的时间范围内"); */
			        	console.log("start_time "+c.start_time+";"+"end_time "+c.end_time);
			        	showConfirm("请选择正确时间！必须在组织指定的时间范围内");		
			        		return;
		        		}
		        		var deadline=$("input[name='deadline_time']").val();
		        		if(c.deadline_time<deadline){
		        			showConfirm("截止时间应该小于开始时间 !"+c.deadline_time);	
		        			return;
		        		}
		        	}
                      /*主题党日  */
		        	var startDate=	new Date($("input[name='startDate']").val().replace(" ","T")).getTime();
		        	var end_time=new Date($("input[name='end_time']").val().replace(" ","T")).getTime();
		        	var minutes=(end_time-startDate)/1000/60;
		        	var meetingType=$("select[name='meetingType']").val();
		        	if(meetingType==='主题党日' && minutes<80){
		        		showConfirm("主题党日的时间必须大于等于80分钟！");		        	
		        		return;
		        	}
		        	   /*校验截止时间  checkDedetime(); */
		        	var deadline_time=$("input[name='deadline_time']").val();
		         	var startDate=$("input[name='startDate']").val();	
		         	var nowDate=getNowDate();
		         	console.log(nowDate);
		         	if(deadline_time>startDate){
		                 showConfirm("截止时间应该小于开始时间 !");		        	
		                 return;
		         	}
		        	
                      var hint=$("#button1").val();
		              $.hgConfirm("提示","确定要"+hint+"吗?");
		              $("#hg_confirm").modal("show");
		              $("#hg_confirm .btn_main").click(function(){
		            	  $(this).attr("disabled",true);
		              $("#hg_confirm").modal("hide");
                        $('select[name="isComment"]').removeAttr('disabled');
                        $('select[name="meetingType"]').removeAttr('disabled');
                        $('input[name="publicDate"]').removeAttr('disabled');
                        $('#submitFrom').click();
   		               if(window.location.pathname == _pathName){
   		            	 $("#hg_confirm .btn_main").attr("disabled",false);
   	                    }
		            });		            
		     }
		     
	     function showConfirm(info){
	    	  $.hgConfirm("提示",info);
              $("#hg_confirm").modal("show");
              $("#hg_confirm .btn_main").click(function(){
              $("#hg_confirm").modal("hide");
                  return;            	              
            });	
	     }
      /*   二级党委的选中及取消选中 */
        $("#hg-form-container").on("click", ".publish_obj_info li", function() {
            $(".publish_obj_content .select_choice").find("img").attr("src", "/images/not_check_icon.png")
            var oldSrc = $(this).find(".right").attr("src");
            var newSrc = "";
            if (oldSrc.indexOf("on") > 0) {
                newSrc = oldSrc.replace(/_on.png/, ".png");
            } else {
                newSrc = oldSrc.replace(/.png/, "_on.png");
            }
            $(this).find(".right").attr("src", newSrc);
            var sum = $(".publish_obj_info").find("img.right").length;
            var imgNum = $(".publish_obj_info").find("img[src='/images/radio_on.png']").length;
            if (sum == imgNum) {
                $(".all_select").find("img").attr("src", "/images/checked_icon.png");
                $(".oppsite_select").find("img").attr("src", "/images/not_check_icon.png");
            } else {
                $(".all_select").find("img").attr("src", "/images/not_check_icon.png");
            }
            renderSelected();
        })

      /*   二级党委全选 */
        $("#hg-form-container").on("click",".all_select",function(){
        	$(this).find("img").attr("src", "/images/checked_icon.png");
            $(this).siblings(".oppsite_select").find("img").attr("src", "/images/not_check_icon.png");
            $(".publish_obj_info").find("img.right").each(function() {
                $(this).attr("src", "/images/radio_on.png");
            })
            renderSelected();
        })

      /*   二级党委反选 */
        $("#hg-form-container").on("click",".oppsite_select",function(){
        	$(this).find("img").attr("src", "/images/checked_icon.png");
            $(this).siblings(".all_select").find("img").attr("src", "/images/not_check_icon.png");
            $(".publish_obj_info").find("img.right").each(function() {
                var Src = $(this).attr("src");
                if (Src.indexOf("on") > 0) {
                    $(this).attr("src", "/images/radio.png")
                } else {
                    $(this).attr("src", "/images/radio_on.png");
                }
            })
            renderSelected();
        })

       /*  渲染已选中的党委 */
        function renderSelected() {
        	console.log("test")
            var _target = $(".publish_obj_info .list-group-item");
            var num = 0;
            $(".has_select_list").html("");
            $(".has_select_div").css("display", "block");
            _target.each(function() {
                var _src = $(this).find("img").attr("src");
                if (_src.indexOf("on") > 0) {
                    var _text = $(this).find("span").html();
                    var item = "<li>" + _text + "</li>";
                    $(".has_select_list").append(item);
                    num += 1;
                }
            })
            $(".has_select_title .select_num").html(num);
            if (num == 0) {
                $(".has_select_div").css("display", "none");
            }
        }
        /* 校验结束时间 ：结束时间应该大于开始时间 
          $("#hg-form-container").on("blur","input[name='end_time']",function(){
        	var startDateStr=$("input[name='startDate']").val();
        	var endDateStr=$("input[name='end_time']").val();
        	var timestampStartDate=Date.parse(new Date(startDateStr));
        	var timestampEndDate=Date.parse(new Date(endDateStr));
        	if(timestampEndDate<timestampStartDate){
        		showConfirm("请选择正确时间！ 结束时间小于了开始时间？");
        		}
        });*/
         /*     校验发布主题 */
             $("#hg-form-container").on("blur","input[name='theme']",function(){
        	 var theme=$("input[name='theme']").val();
        	 if(!theme){
        		/*  $.tip("请输入主题！"); */
        		 showConfirm("请输入主题！");
        		 }
        });

         function getNowDate(){
        	var date = new Date();
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
    </script>
	</body>
</html>