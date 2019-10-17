$(function() {

	$('input').iCheck({
		checkboxClass : 'icheckbox_square-blue',
		radioClass : 'iradio_square-blue',
		increaseArea : '20%' // optional
	})

	$('#btn_login').on('click', function() {
		var username = $.trim($("#username").val());
		var password = $.trim($("#password").val());
		// var vcode = $.trim($("#vcode").val());
		var rememberMe = $("#rememberMe").is(":checked") ? 1 : 0;

		if (username == "") {
			alert("用户登录帐号不能为空，请重新输入！");
		} else if (password == "") {
			alert("密码不能为空，请重新输入！");
		} else if (password.length < 6) {
			alert("密码长度不能小于6，请重新输入！");
		} else {
			var mpass = hex_md5(password);
			$.ajax({
				url : ctx + "/login",
				type : 'post',
				cache : false,
				dataType : 'json',
				contentType : "application/json; charset=utf-8",
				data : JSON2.stringify({
					loginName : username,
					password : mpass,
					// vcode : vcode,
					rememberMe : rememberMe
				}),
				async : false, // 默认为true 异步
				success : function(data) {
					if (data.error) {
						alert(data.message);
					} else {
						window.location.href = data.message;
					}
				}
			});
		}
	});

});
