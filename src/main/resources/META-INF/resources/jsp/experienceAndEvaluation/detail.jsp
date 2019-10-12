<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/init.jsp" %>
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
	        .modal-backdrop.fade.in {
              display: none;
            }
		</style>
		
		<link rel="stylesheet" href="${basePath }/css/details.css" />
	</head>
	<body>
		 <div class="content_title hidden-xs">
		    评价详情
		</div>
		<div class="details_container">
			<%-- <p class="details_title">${evaluation.meeting_theme }</p> --%>
			<div class="details_content">
			    <div class="">
			       <%--  <p class="col-sm-6 col-xs-12"><span>会议类型</span>${evaluation.meeting_type }details_content_title</p> --%>
					<%-- <p class="col-sm-6 col-xs-12"><span>上传时间</span>${evaluation.comments_time }</p> --%>
				</div>
				<div class="details_content_info"></div>
		        <div class="btn_group">
		        	<a href="javaScript:window.history.back();">
		        		<button class="btn btn-default btn-lg main_color_btn">返回</button>
		        	</a>
		        </div>
		    </div>
		</div>
		 <script>
	        $(function(){
	        	var scoreArr = [${evaluation.comments_aspects_one},${evaluation.comments_aspects_two},${evaluation.comments_aspects_three},${evaluation.comments_aspects_four}];
	        	var text = ["政治性强：${evaluation.comments_aspects_one}","主题鲜明：${evaluation.comments_aspects_two}","灵活创新：${evaluation.comments_aspects_three}","实效性强：${evaluation.comments_aspects_four}"];
	            var _dom = '<div class="comment_container">'+
				    '<div class="comment_score">'+
				        '<div class="score_img_container">'+
				            '<span></span>'+
				            '<span></span>'+
				            '<span></span>'+
				            '<span></span>'+
				            '<span></span>'+
				            '<div class="thanks_container">'+
				                '<span class="right_border"></span>'+
				            '</div>'+
				        '</div>'+
				    '</div>'+
				'</div>';
				for(var i =0;i < 4;i++){
					$(".details_content_info").append(_dom);
					var _p = '<p>'+ text[i] +'</p>';
					var _target = $(".details_content_info").find(".comment_container").eq(i);
					_target.find(".comment_score").prepend(_p);
					var _star = _target.find(".score_img_container span");
					_star.removeClass("hover_comment");
		            for (var j = 0; j < _star.length; j++) {
		                if (j < scoreArr[i]) {
		                    $( _star[j]).addClass("hover_comment")
		                }
		            };
				}
				/* 隐藏星星 */
			   var display="${display}";
	           console.info("display="+display);
	           if(display=="none"){
	        	 $(".details_content_info .score_img_container").css("display","none");  
	           }
			})
	    </script>
	</body>
</html>