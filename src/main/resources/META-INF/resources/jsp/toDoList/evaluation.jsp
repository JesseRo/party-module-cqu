<%@ include file="/init.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
	<head>
		<style>
	        .comment_score p {
	            color: #333;
	            padding: 40px 0 20px 0;
	            text-align: center;
	        }
	        
	        .score_img_container {
	            text-align: center;
	            position: relative;
	        }
	        
	        .score_img_container>span {
	            cursor: pointer;
	            margin: 0 5px;
	            width: 34px;
	            height: 32px;
	            display: inline-block;
	            background-image: url('/images/default_comment.png');
	        }
	        
	        .score_img_container .hover_comment {
	            background-image: url('/images/hover_comment.png');
	        }
	        
	        .thanks_container {
	            display: flex;
	            position: absolute;
	            top: 19px;
	            right: 27%;
	            margin-top: -13px;
	            visibility: hidden;
	        }
	        
	        .score_img_container .right_border {
	            width: 0;
	            height: 0;
	            border-top: 6px solid transparent;
	            border-bottom: 6px solid transparent;
	            border-right: 6px solid #e1e1e1;
	            background: none;
	            display: inline-block;
	            margin: 5px 0;
	        }
	        
	        .thanks_title {
	            display: inline-block;
	            padding: 2px 5px;
	            border-radius: 4px;
	            border: 1px solid #e1e1e1;
	            font-size: 12px;
	            color: #999;
	        }
	        
	        
	        .content_info1{
	        	padding-bottom: 30px;
	        }
	    </style>
	    <link rel="stylesheet" href="${basePath }/css/details.css" />
		<link rel="stylesheet" href="${basePath }/js/utf8-jsp/third-party/codemirror/codemirror.css" />
	    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/themes/iframe.css" />
	    <link rel="stylesheet" href="${basePath }/js/utf8-jsp/themes/default/css/ueditor.css" />
	    <script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.config-thumb.js?v=3"></script>
    	<script type="text/javascript" src="${basePath}/js/utf8-jsp/ueditor.all.js"></script>
		<script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/third-party/codemirror/codemirror.js"></script>
	    <script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/third-party/zeroclipboard/ZeroClipboard.js"></script>
		<script type="text/javascript" charset="utf-8" src="${ basePath }/js/utf8-jsp/lang/zh-cn/zh-cn.js"></script>
	
	</head>
	<body>
		<portlet:resourceURL id="/form/uploadImage" var="uploadimageUrl" />
		<!-- 视频上传 -->
		<portlet:resourceURL id="/form/uploadVideo" var="uploadvideoUrl" />
		<!-- 附件上传 -->
		<portlet:resourceURL id="/form/uploadFile" var="uploadfileUrl" />
         
        <portlet:actionURL name="/party/uploadEvaluation" var="uploadEvaluationUrl">
		</portlet:actionURL>
		<div class="content_form">
			<!-- 提交数据存库 -->
			<form action="${uploadEvaluationUrl }" method="post" id="evaluation_content" class="form-horizontal">
				<input type="hidden" value="${meetingId }" name="meetingId">
				<input type="hidden" value="${userId }" name="userId">
				<input type="hidden" value="${formSeeion }" name="formSeeion">
				<!-- <input type="hidden" class="comment" id="commentNo" name="score"/>
	         	<input type="hidden" class="comment" id="commentNo1" name="score1"/>
	         	<input type="hidden" class="comment" id="commentNo2" name="score2"/> -->
	         	
		         <div class="content_info1">
		             <div class="content_title hidden-xs">
		                	 编辑评价
		             </div>
		             <div class="details_container">
						<p class="details_title">${meetingTheme }</p>
						<div class="details_content">
						    <div class="details_content_title">
						        <p class="col-sm-6 col-xs-12"><span>会议类型</span>${type }</p>
								<p class="col-sm-6 col-xs-12"><span>开展时间</span>${time }</p>
							</div>
					    </div>
					</div>
		         </div>
		   		 <script>
			        $(function(){
			        	var text = ["政治性强：","主题鲜明：","灵活创新：","实效性强："];
			        	var nameArr = ["score","score1","score2","score3"];
			            var _dom = '<div class="comment_container">'+
						    '<div class="comment_score">'+
						        /* '<p>您觉得这次组织生活会开展的怎么样：</p>'+ */
						        '<input type="hidden" class="comment"/>'+
						        '<div class="score_img_container">'+
						            '<span></span>'+
						            '<span></span>'+
						            '<span></span>'+
						            '<span></span>'+
						            '<span></span>'+
						            '<div class="thanks_container">'+
						                '<span class="right_border"></span>'+
						                '<span class="thanks_title">谢谢您的支持</span>'+
						            '</div>'+
						        '</div>'+
						    '</div>'+
						'</div>';
						for(var i =0;i < 4;i++){
							$(".content_info1").append(_dom);
							var _p = '<p>'+ text[i] +'</p>';
							var _target = $(".content_info1").find(".comment_container").eq(i);
							_target.find(".comment_score").prepend(_p);
							$(".content_info1").find(".comment_container:eq("+i+") input.comment").attr("name",nameArr[i]); 
						}
						
					})
			        $(".content_info").on("click", ".score_img_container span", function() {
			        	var _parentDom = $(this).parents(".score_img_container");
			        	_parentDom.find("span").removeClass("hover_comment");
			            var _index = $(this).index();
			            for (var i = 0; i < _parentDom.find("span").length; i++) {
			                if (i <= _index) {
			                    var _target = _parentDom.find("span")[i];
			                    $(_target).addClass("hover_comment")
			                }
			            };
			
			            //获取评分
			            $(this).parents(".score_img_container").siblings("input.comment").val(_index + 1);
			           	
			            //悬浮框的出现与隐藏
			            var _thanksDom = $(this).siblings(".thanks_container");
			            _thanksDom.css("visibility", "visible");
			            setTimeout(function() {
			            	_thanksDom.css("visibility", "hidden");
			            }, 3000);
					})
			    </script>
		   	
				<div class="btn_group" style="margin-top: 15px;">
					<button type="button" onclick="javascript:window.history.back(-1);" class="btn btn-default">取消</button>
					<span class="col-xs-1 visible-xs"></span>
					<button type="button" class="btn btn_main right" style="color: white;" onclick="submit_score();">评价</button>
					<script type="text/javascript">
					   function submit_score(){
						   var form = document.getElementById('evaluation_content');					
						   var score = $("input[name='score']").val();
						   var score1 = $("input[name='score1']").val();
						   var score2 = $("input[name='score2']").val();
						   var score3 = $("input[name='score3']").val();
						   if(!score || !score2 || !score3 || !score1){
							    $.hgConfirm("提示"," 评价不能为空！ ");
					            $("#hg_confirm").modal("show");
					            $("#hg_confirm .btn_main").click(function(){
					            $("#hg_confirm").modal("hide");
					              return;
					            });

						   }else{
							   form.submit(); 
						   }
					   }
					</script>
				</div>
			</form>
		</div>
	</body>
</html>