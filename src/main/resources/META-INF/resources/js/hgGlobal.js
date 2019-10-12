// 手机验证
function global_isPoneAvailable(str) {
	if("" == str){
		return false;
	}
	var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
	if (!myreg.test(str)) {  
		return false;  
	} else {  
		return true;  
	}  
}  

// 邮箱验证
function global_isEmailAvailable(str) {
	if("" == str){
		return false;
	}
	//var myreg = "/^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$/";
　　var myreg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); //正则表达式
	if (!myreg.test(str)) {
		return false;  
	} else {  
		return true;  
	}  
}

//密码验证
function global_isPasswdAvailable(str){
	if("" == str){
		return false;
	}
	var myreg = /^[A-Za-z0-9]{6,50}$/;
	if (!myreg.test(str)) {
		return false;  
	} else {  
		return true;  
	}  
}