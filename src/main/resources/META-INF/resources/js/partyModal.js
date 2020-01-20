//自定义封装的tip 消息提示框
 jQuery.extend({
    tip: function (msg) {
        if ($("body").find(".tip_dialog").length === 0) {
            var i = '<div class="tip_dialog">'+
                        '<p>'+ msg +'</p>'+
                        '<div class="tip_bg"></div>'+
                    '</div>';
            $("body").append(i);
            var s = $(".tip_dialog");
            var _height = $(window).height()/2;
            var _selfWidth = $(".tip_dialog").width();
            s.css("margin-left",-(_selfWidth/2));
            
            s.animate({
                top:_height
            },500)
            
            //1.5秒后自动关闭窗口  
            setTimeout(function () {
                s.remove();
            }, 1500);
        } else {
            $(".tip_dialog p").text(msg);
            $(".tip_dialog").show();
            setTimeout(function () {
                $(".tip_dialog").remove();
            }, 1500);
        }
    },
    
    hgConfirm: function(title,msg){
        $("#hg_confirm #confirmLabel").text(title);
        $("#hg_confirm .modal-body").text(msg);
        $("#hg_confirm .btn_main").unbind("click");
        /*$("#hg_confirm").modal("show");*/
    }

})


 $(function(){
	//footer  置于底部
    var _width = $(window).width();
    if(_width >= 768){
        var _H1 = $(".container-fluid").height();
        var _H2 = $(window).height();
        if(_H1 < _H2){
            $(".container-fluid").height(_H2);
            $("footer").css({
                "position":"relative",
                "bottom":"0",
                "left":"0",
                "width":"100%"
            })
        }
    }
    
    //清除bootstrap 弹窗出现的过渡效果
    $(".modal").each(function(){
    	$(this).removeClass("fade");
    });
    
  //ie9 网页白屏 刷新页面
  //   var browser=navigator.appName
  //   var b_version=navigator.appVersion
  //   var version=b_version.split(";");
  //   if(version[1]){
  //       var trim_Version=version[1].replace(/[ ]/g,"");
  //   }
  //   if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE9.0"){
  //   	if($("body").html().indexOf("logo_container") == -1){
  //       	window.location.reload();
  //       }
  //   }
    
})





