<%@ page pageEncoding="GB18030"%>
<%@ page import="java.sql.*,com.cj.bbs.*"%>

<%
	request.setCharacterEncoding("GBK");
	String action = request.getParameter("action");
	String username = "";
	if (action != null && action.trim().equals("login")) {
		username = request.getParameter("username");
		//check username whether valid or not!
		String password = request.getParameter("password");
		if(username == null || !username.trim().equals("admin")) {
			out.println("username not correct!");
		} else if(password == null || !password.trim().equals("admin")) {
			out.println("password not correct!");
		} else {
			session.setAttribute("adminLogined" , "true");
			response.sendRedirect("articleFlat.jsp");
		}
	}
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<title>����Ա��¼</title>

	</head>
	<body>

		<form action="login.jsp" method="post">
			<input type="hidden" name="action" value="login"/>
			�û�����
			<input type="text" name="username" value="<%=username %>"/>
			<br>
			���룺
			<input type="password" name="password" />
			<br>
			<input type="submit" value="login" />
		</form>


	</body>
</html>
