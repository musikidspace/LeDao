/**
 * 注册页面js
 */
$(function() {
	$("#btn_submit").click(function() {
		$.ajax({
			url : "../register",
			type : "post",
			dataType : "json",
			timeout : 5000,
			data : $("#fm").serializeArray(),
			beforeSend : function() {
				if (fm.user.value == "") {
					alert("用户名不能为空！");
					return false;
				}
				if (!validPhone(fm.usercode.value)) {
					alert("手机号不符合规范！");
					return false;
				}
				if (fm.userpassword.value.length < 6) {
					alert("密码不小于6位！");
					return false;
				}
			},
			success : function(data) {
				if (data.success) {
					alert("注册成功并已与微信绑定！\n您现在可以返回公众号享受便捷服务。");
				} else {
					alert("注册失败！\n原因是:" + data.data.msg);
				}

			},
			error : function(jqXHR, textStatus, errorMsg) {
				// jqXHR 是经过jQuery封装的XMLHttpRequest对象
				// textStatus 可能为null、 'timeout'、 'error'、
				// 'abort'和'parsererror'等
				// errorMsg 是错误信息字符串(响应状态的文本描述部分，例如'Not Found'或'Internal Server
				// Error')
				alert("请求失败！\n原因是:" + errorMsg);
			}
		});
	});
});

/**
 * 手机号正则校验
 * 
 * @param phone
 * @returns {Boolean}
 */
function validPhone(phone) {
	var regex = "^1[3-8]{1}[0-9]{9}$";
	if (phone.match(regex)) {
		return true;
	} else {
		return false;
	}
}
