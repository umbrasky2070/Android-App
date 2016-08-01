<%@ page pageEncoding="GB18030"%>
<%@ page import="java.sql.*,com.cj.bbs.*"%>

<%
	request.setCharacterEncoding("GBK");
	String action = request.getParameter("action");
	
	int id = Integer.parseInt(request.getParameter("id"));
	if (action != null && action.trim().equals("modify")) {
		Connection conn = DB.getConn();
		String title = request.getParameter("title");
		System.out.println(title);
		String cont = request.getParameter("cont");
		System.out.println(cont);
		
		PreparedStatement pstmt = DB.getpreStmt(conn, "update article set title = ? , cont = ? where id = ?");		
		pstmt.setString(1, title);
		pstmt.setString(2, cont);
		pstmt.setInt(3, id);
		pstmt.executeUpdate();
		
		DB.close(pstmt);
		DB.close(conn);
		
		response.sendRedirect("articleFlat.jsp");
		return;

	}
%>


<%

Connection conn = DB.getConn();
Statement stmt = DB.getStmt(conn);
ResultSet rs = DB.executeQuery(stmt, "select * from article where id = " + id);
if(!rs.next()) return;
Article a = new Article();
a.initFromRs(rs);
DB.close(rs);
DB.close(stmt);
DB.close(conn);
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
		<title>发表新主题</title>
		<meta http-equiv="content-type" content="text/html; charset=GBK">
		<link rel="stylesheet" type="text/css" href="images/style.css"
			title="Integrated Styles">
		<script language="JavaScript" type="text/javascript"
			src="images/global.js"></script>
		<!-- fckeditor -->
		<!-- 为了速度而没有使用fckeditor -->
		<!-- end of fckeditor -->

		<link rel="alternate" type="application/rss+xml" title="RSS"
			href="http://bbs.chinajavaworld.com/rss/rssmessages.jspa?threadID=744236">
	</head>
	<body>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tbody>
				<tr>
					<td width="40%">
						<img src="images/header-stretch.gif" alt="" border="0" height="57"
							width="100%">
					</td>
					<td width="1%">
						<img src="images/header-right.gif" alt="" height="57" border="0">
					</td>
				</tr>
			</tbody>
		</table>
		<br>
		<div id="jive-flatpage">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tbody>
					<tr valign="top">
						<td width="99%">
							<p class="jive-breadcrumbs">
								<a href="http://bbs.chinajavaworld.com/index.jspa">首页</a> &#187;
								<a
									href="http://bbs.chinajavaworld.com/forumindex.jspa?categoryID=1">ChinaJavaWorld技术论坛|Java世界_中文论坛</a>
								&#187;
								<a
									href="http://bbs.chinajavaworld.com/category.jspa?categoryID=2">Java
									2 Platform, Standard Edition (J2SE)</a> &#187;
								<a
									href="http://bbs.chinajavaworld.com/forum.jspa?forumID=20&amp;start=0">Java语言*初级版</a>
							</p>
							<p class="jive-page-title">
								修改
								<br>
							</p>
						</td>
						<td width="1%"><br>
							
						<br></td>
					</tr>
				</tbody>
			</table>
			<div class="jive-buttons">
				<br>
			</div>
			<br>
			<table border="0" cellpadding="0" cellspacing="0" width="930"
				height="61">
				<tbody>
					<tr valign="top">
						<td width="99%">
							<div id="jive-message-holder">
								<div class="jive-message-list">
									<div class="jive-table">
										<div class="jive-messagebox">

											<form action="modify.jsp" method="post">
												<input type="hidden" name="action" value="modify" />
												<input type="hidden" name="id" value="<%=id %>"/>
												
												标题：
												<input type="text" name="title" value="<%=a.getTitle() %>">
												<br>
												内容：
												<textarea name="cont" rows="15" cols="80"><%=a.getCont() %></textarea>
												<br>
												<input type="submit" value="submit" />
											</form>

										</div>
									</div>
								</div>
								<div class="jive-message-list-footer">
									<br>
								</div>
							</div>
						</td>
						<td width="1%">
							&nbsp;
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</body>
</html>
