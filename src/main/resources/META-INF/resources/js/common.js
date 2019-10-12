 // 移动端导航出现
 $(".small_header .menu_icon").click(function() {
     var _height = $(window).height() - 40;
     $(".mobile_nav_container").animate({
         "height": _height + "px"
     });
 })

 // 移动端导航消失
 $(".mobile_nav_container .close_nav_icon").click(function() {
     $(".mobile_nav_container").animate({
         "height": 0
     });
 })

 //判断用户是否登录
 $(".small_header .user_icon").click(function() {
     var hasLogin = true;
     if (hasLogin) {

     } else {
         $(this).siblings(".dropdown-menu").css("display", "none");
         window.location.href = "./login.html";
     }
 })

 //用户退出操作
 $(".login_out_btn").on("click", function() {
     //退出登录
 })

 //返回上一页
 $(".back_icon").on("click", function() {
     window.history.back();
 })

 //移动端 动态设置二级导航的高度
 $(function() {
     function setScreenHeight() {
         var screenWidth = $(window).width();
         var screenHeight = $(window).height();
         if (screenWidth <= 768) {
             $(".main_content").css("height", (screenHeight - 40) + "px");
         }
     }

     setScreenHeight();


     //窗口大小重置时改变高度
     $(window).resize(function() {
         setScreenHeight();
     })
 })