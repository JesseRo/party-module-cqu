		//一级菜单下拉
        $(".first_menu").click(function() {
            var _parent = $(this).parents("li");
            if(_parent.hasClass("height_auto")){
            	_parent.removeClass("height_auto");
            }else{
            	_parent.addClass("height_auto");
            }
            console.log(_parent);
            var _target = $(this).find(".first_drop");
            if (_target.hasClass("dropdown_up")) {
                _target.removeClass("dropdown_up").addClass("dropdown_down");
                _target.attr("src", "/images/dropdown_icon.png");
            } else {
                _target.removeClass("dropdown_down").addClass("dropdown_up");
                _target.attr("src", "/images/second_menu_up.png");
            }
        })
	
		$(".second_menu li").click(function() {
		    $(".second_menu li").removeClass("second_menu_on");
		    $(this).addClass("second_menu_on");
		    $(this).parents("li").addClass("height_auto");
		})
		
		$(function(){
			var _pathName = window.location.pathname.toLowerCase();
			console.log(_pathName);
			$(".party_organization_list").find("a").each(function(){
				var _url = $(this).attr("href").toLowerCase();
				if(_pathName.indexOf(_url) > 0 || _pathName == _url){
					$(this).addClass("page_on_sm");
					$("#organization").addClass("page_on");
					if($(this).parents(".second_menu")){
						var _target = $(this).parents(".second_menu");
						_target.parent("li").addClass("height_auto");
					}
				}
			})
			
			$(".nav_list > li>a").each(function(){
				var _href = $(this).attr("href").toLowerCase();
				if(_pathName.indexOf(_href) > 0 || _pathName == _href){
					$(this).addClass("page_on");
				}
			})
			
		})
