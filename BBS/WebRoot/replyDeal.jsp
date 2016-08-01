<%@ page language="java" import="java.util.*,java.sql.*,com.cj.bbs.*" pageEncoding="GB18030"%>
<%
int pid = Integer.parseInt(request.getParameter("pid"));
int rootid = Integer.parseInt(request.getParameter("rootid"));

request.setCharacterEncoding("GBK");
String title = request.getParameter("title");
System.out.println(title);
String cont = request.getParameter("cont");
System.out.println(cont);
Connection conn = DB.getConn();

boolean autoCommit =  conn.getAutoCommit();
conn.setAutoCommit(false);

String sql = "insert into article values(null, ?, ?, ?, ?, now(), ?)";
PreparedStatement PreStmt = DB.getpreStmt(conn,sql);
PreStmt.setInt(1,pid);
PreStmt.setInt(2,rootid);
PreStmt.setString(3, title);
PreStmt.setString(4,cont);
PreStmt.setInt(5,0);
PreStmt.executeUpdate();

Statement stmt = DB.getStmt(conn);
stmt.executeUpdate("update article set isleaf=1 where pid ="+pid);

conn.commit();
conn.setAutoCommit(autoCommit);

DB.close(conn);
DB.close(stmt);
DB.close(PreStmt);
 %>
 <span id="time" style="background:red">5</span>秒钟后，自动跳转
 <script language="JavaScript1.2" type="text/javascript">

function delayURL(url) {
	var delay = document.getElementById("time").innerHTML;
	if(delay>0){
		delay--;
		document.getElementById("time").innerHTML = delay;
	}else{
		window.top.location.href = url;
	}
    setTimeout("delayURL('"+url+"')",1000);
}
</script>

<a href="article.jsp">主题列表</a>
<script type="text/javascript">
	delayURL("articleFlat.jsp");
</script>

