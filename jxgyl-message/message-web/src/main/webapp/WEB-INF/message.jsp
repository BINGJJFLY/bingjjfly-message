<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="<%=path %>/produce" method="post">
		目标邮箱：<input name="to" /></br> 
		用户名：<input name="usercode" /></br> 
		用户编码：<input name="username" /></br>
		密码：<input name="uuid" /></br>
		模板：<select name="template">
			<option value="RESET_PASSWORD">RESET_PASSWORD</option>
			<option value="ADMIN_RESET_PASSWORD">ADMIN_RESET_PASSWORD</option>
		</select></br>
		<button type="submit">Submit</button>
	</form>
</body>
</html>