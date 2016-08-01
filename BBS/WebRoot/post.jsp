<%@ page pageEncoding="GB18030"%> 
<%@ page import="java.sql.*, com.cj.bbs.*, java.util.*" %>
<%

	request.setCharacterEncoding("GBK");
	String action = request.getParameter("action");
	if (action != null && action.trim().equals("post")) {
		String title = request.getParameter("title");
		System.out.println(title);
		String cont = request.getParameter("cont");
		System.out.println(cont);
		Connection conn = DB.getConn();

		boolean autoCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		
		int rootid = -1;
		
		String sql = "insert into article values (null, ?, ?, ?, ?, now(), ?)";
		PreparedStatement PreStmt = DB.getpreStmt(conn, sql, Statement.RETURN_GENERATED_KEYS);
		PreStmt.setInt(1, 0);
		PreStmt.setInt(2, rootid);
		PreStmt.setString(3, title);
		PreStmt.setString(4, cont);
		PreStmt.setInt(5, 0);
		PreStmt.executeUpdate();
		
		ResultSet rsKey = PreStmt.getGeneratedKeys();
		rsKey.next();
		rootid = rsKey.getInt(1);
	
		Statement stmt = DB.getStmt(conn);
		stmt.executeUpdate("update article set rootid = " + rootid + " where id = "	+ rootid);

		conn.commit();
		conn.setAutoCommit(autoCommit);
		DB.close(PreStmt);
		DB.close(stmt);
		DB.close(conn);
		
		response.sendRedirect("articleFlat.jsp");

	
}
 %>
 	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>Java|Java世界_中文论坛|ChinaJavaWorld技术论坛 : 初学java遇一难题！！望大家能帮忙一下 ...</title>
<meta http-equiv="content-type" content="text/html; charset=utf8">
<link rel="stylesheet" type="text/css" href="articledetail_files/style.css" title="Integrated Styles">
<script language="JavaScript" type="text/javascript" src="articledetail_files/global.js"></script>
<link rel="alternate" type="application/rss+xml" title="RSS" href="http://bbs.chinajavaworld.com/rss/rssmessages.jspa?threadID=744236">


<script type="text/javascript" src="fckeditor/fckeditor.js"></script>
<script type="text/javascript">

window.onload = function()
{
	// Automatically calculates the editor base path based on the _samples directory.
	// This is usefull only for these samples. A real application should use something like this:
	// oFCKeditor.BasePath = '/fckeditor/' ;	// '/fckeditor/' is the default value.
	// var sBasePath = document.location.pathname.substring(0,document.location.pathname.lastIndexOf('_samples')) ;
	var sBasePath = "<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/fckeditor/" %>";
//alert(sBasePath);
	var oFCKeditor = new FCKeditor( 'cont' ) ;
	oFCKeditor.BasePath	= sBasePath ;
	oFCKeditor.ReplaceTextarea() ;
}

</script>
	
	
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tbody>
    <tr>
      <td width="140"><img src="articledetail_files/header-stretch.gif" alt="" border="0" height="57" width="100%"></td>
      <td><img src="articledetail_files/header-stretch.gif" alt="" border="0" height="57" width="100%"></td>
      <td width="1%"><img src="articledetail_files/header-right.gif" alt="" border="0"></td>
    </tr>
  </tbody>
</table>
<br>
<div id="jive-flatpage">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tbody>
      <tr valign="top">
        <td width="99%"><p class="jive-breadcrumbs"> <a href="http://bbs.chinajavaworld.com/index.jspa">首页</a> &#187; <a href="http://bbs.chinajavaworld.com/forumindex.jspa?categoryID=1">CSDN_Java论坛</a> &#187; <a href="http://bbs.chinajavaworld.com/category.jspa?categoryID=2">Java 2 Platform, Standard Edition (J2SE)</a> &#187; <a href="http://bbs.chinajavaworld.com/forum.jspa?forumID=20&amp;start=0">Java语言*初级版</a> </p>
          <p class="jive-page-title"> 发表新主题  </p></td>
        <td width="1%"><div class="jive-accountbox"></div></td>
      </tr>
    </tbody>
  </table>
  <br>
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tbody>
      <tr valign="top">
        <td width="99%"><div id="jive-message-holder">
            <div class="jive-message-list">
              <div class="jive-table">
                <div class="jive-messagebox">
                  	<form action="post.jsp" method="post">
                  			<input type="hidden" name="action" value="post"> 
                  		标题:<input type="text" name="title"><br>
                  		内容:<textarea name="cont" rows="15" cols="80"></textarea><br>
                  			<input type="submit" value="提交">
                  	</form>
                </div>
              </div>
            </div>
            <div class="jive-message-list-footer">
              <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tbody>
                  <tr>
                    <td nowrap="nowrap" width="1%"></td>
                    <td align="center" width="98%"><table border="0" cellpadding="0" cellspacing="0">
                        <tbody>
                          <tr>
                            <td><a href="http://bbs.chinajavaworld.com/forum.jspa?forumID=20"><img src="articledetail_files/arrow-left-16x16.gif" alt="返回到主题列表" border="0" height="16" hspace="6" width="16"></a> </td>
                            <td><a href="http://bbs.chinajavaworld.com/forum.jspa?forumID=20">返回到主题列表</a> </td>
                          </tr>
                        </tbody>
                      </table></td>
                    <td nowrap="nowrap" width="1%">&nbsp;</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div></td>
        <td width="1%"></td>
      </tr>
    </tbody>
  </table>
</div>
</body>
</html>
