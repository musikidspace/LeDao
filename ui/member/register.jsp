<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<!-- 不可缩放 -->
<!-- <meta name="viewport" content="width=device-width, initial-scale=1">-->
<!-- 可缩放 -->
<link rel="stylesheet" href="../common/bootstrap/css/bootstrap.min.css">
<script src="../common/jquery/jquery.min.js"></script>
<script src="../common/bootstrap/js/bootstrap.min.js"></script>
<script src="register.js"></script>
<title>会员注册</title>
</head>
<body style="padding-left: 20px; padding-right: 20px; padding-top: 50px">
	<form action="" role="form" method="post" id="fm">
		<div class="form-group">
			<div class="input-group input-group-lg btn-block">
				<span class="input-group-addon ">账号</span> <input type="text"
					class="form-control" placeholder="用户名" name="user" />
			</div>
		</div>
		<div class="form-group">
			<div class="input-group input-group-lg btn-block">
				<span class="input-group-addon ">手机</span> <input type="text"
					class="form-control" placeholder="手机号" name="usercode" />
			</div>
		</div>
		<div class="form-group">
			<div class="input-group input-group-lg btn-block">
				<span class="input-group-addon ">密码</span> <input type="password"
					class="form-control" placeholder="密码" name="userpassword" />
			</div>
		</div>

		<button type="button" id="btn_submit"
			class="form-group btn btn-success btn-lg
			btn-block">注册</button>
	</form>
</body>
</html>
