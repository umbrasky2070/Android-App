<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%> 
<%@ page import="java.sql.*, java.util.*, com.cj.bbs.*, java.io.*" %>
<%!
	private void tree(List<Article> articles, Connection conn, int id, int grade) {
		String sql = "Select * from article where pid ="+id;
		Statement stmt = DB.getStmt(conn);
		ResultSet rs = DB.executeQuery(stmt, sql);
		try{
			while(rs.next()){
				Article a = new Article();
				a.initFromRs(rs);
				a.setGrade(grade);
				articles.add(a);
				if(!a.isLeaf()){
					tree(articles,conn,a.getId(),grade+1);
				}
			}
		
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DB.close(stmt);
			DB.close(rs);
			DB.close(conn);   //可以不加
		}
	}
 %>
 
 <%
 	List<Article> articles = new ArrayList<Article>();
 	Connection conn= DB.getConn();
 	tree(articles,conn,0,0);
 	DB.close(conn);
  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0032)http://bbs.csdn.net/forums/Java/ -->
<html class="csdn-bbs" xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><script src="./article_files/expansion_embed.js"></script><script async="" src="http://a.pongo.com.cn/Job/GetAdForCallBack?id=4&UrlAdParam=http%3A%2F%2Fbbs.csdn.net%2Fforums%2FJava%2F&&CallBack=pongoad4&_=1456638733914"></script>
      <script async="" type="text/javascript" src="./article_files/gpt.js"></script><script type="text/javascript" async="" src="./article_files/acom"></script><script id="allmobilize" charset="utf-8" src="./article_files/allmobilize.min.js"></script><style type="text/css"></style>
<meta http-equiv="Cache-Control" content="no-transform">
<link rel="alternate" media="handheld" href="http://bbs.csdn.net/forums/Java/#">

      
      <title>Java论坛-CSDN论坛-CSDN.NET-中国最大的IT技术社区</title>
      <link href="./article_files/index-d4be409d127e37381f8dd32f3df7a29d.css" media="screen" rel="stylesheet" type="text/css">
      <link href="./article_files/btn.css" media="screen" rel="stylesheet" type="text/css">
      <script src="./article_files/application-98fca89914df17e1543be29fef780be0.js" type="text/javascript"></script>


      <meta content="Java, Java教程，Java论坛" name="keywords">
      <meta content="CSDNJava论坛，是国内最好的Java论坛，提供Java论坛，Java技术交流等。CSDN论坛，中国最大的IT技术社区" name="description">
      <link href="./article_files/main-65345c2929e9c99952ae23b373382977.css" media="screen" rel="stylesheet" type="text/css">
      <script src="./article_files/AreaCounter.js" type="text/javascript" charset="utf-8"></script>


      <link href="http://c.csdnimg.cn/public/favicon.ico" rel="SHORTCUT ICON">
<link rel="stylesheet" href="http://c.csdnimg.cn/public/common/toolbar/css/index.css">

      <meta content="authenticity_token" name="csrf-param">
<meta content="0E5LG/vuYEoTHICzesFKxQ+6snH8S0xfBzK8hQouRrk=" name="csrf-token">
      <link href="http://bbs.csdn.net/forums/Java.atom" rel="alternate" title="ATOM" type="application/atom+xml">
      
      <!--  js for ask-->
      <link href="./article_files/ask_float_block.css" rel="stylesheet" type="text/css">

      <script type="text/javascript" src="./article_files/wmd.js"></script>

      <script type="text/javascript" src="./article_files/showdown.js"></script>

      <script type="text/javascript" src="./article_files/prettify.js"></script>

      <script type="text/javascript" src="./article_files/ask_float_block.js"></script>
      <!--  js for ask  end-->
    <script type="text/javascript" async="" src="./article_files/p_a_clk.js"></script><script type="text/javascript" charset="utf-8" src="./article_files/tracking.js"></script><script type="text/javascript" charset="utf-8" src="./article_files/main.js"></script><script src="./article_files/pubads_impl_81.js" async=""></script><link rel="stylesheet" type="text/css" href="./article_files/style.css"><script type="text/javascript" src="./article_files/osd.js"></script></head>
    <body id="forums-show" class="topic-list open"><div id="BAIDU_DUP_fp_wrapper" style="position: absolute; left: -1px; bottom: -1px; z-index: 0; width: 0px; height: 0px; overflow: hidden; visibility: hidden; display: none; "><iframe id="BAIDU_DUP_fp_iframe" src="./article_files/o.htm" style="width: 0px; height: 0px; visibility: hidden; display: none; "></iframe></div>
        <!-- <script type="text/javascript" src="http://c.csdnimg.cn/pubnav/js/pub_topnav_2011.js"></script> -->

<!--全屏-->
<script type="text/javascript" ads-src="http://csdnim.qtmojo.com/main/s?user=allyestest|allyestest|allyestest0410&amp;db=csdnim&amp;border=0&amp;local=yes&amp;js=ie" src="./article_files/AllyesDeliver.min.js" charset="gbk" id="allyes_ins_1d1109b5b" ads-outputs="0" ads-tid="2320d8cb7_0"></script>	
<!--全屏-->

<!--浮标-->
<script type="text/javascript" ads-src="http://csdnim.qtmojo.com/main/s?user=csdn|homepage|floating_1&amp;db=csdnim&amp;border=0&amp;local=yes&amp;js=ie" src="./article_files/AllyesDeliver.min.js" charset="gbk" id="allyes_ins_1e042f2f8" ads-outputs="0" ads-tid="1640f9fa0_0"></script>	
<!--浮标-->

<script id="toolbar-tpl-scriptId" skin="black" prod="bbs" fixed="true" src="./article_files/html.js" type="text/javascript"></script>
<!--
<div class="csdn-toolbar csdn-toolbar-skin-black ">        <div class="container row center-block ">          <div class="col-md-3 pull-left logo clearfix"><a href="http://www.csdn.net/?ref=toolbar" title="CSDN首页" target="_blank" class="icon"></a><a title="频道首页" href="http://bbs.csdn.net/?ref=toolbar_logo" class="img bbs-icon"></a></div>          <div class="pull-right login-wrap unlogin">            <ul class="btns">              <li class="loginlink"><a href="https://passport.csdn.net/account/login?ref=toolbar" target="_top">登录&nbsp;</a>|<a target="_top" href="http://passport.csdn.net/account/mobileregister?ref=toolbar&action=mobileRegister">&nbsp;注册</a></li>              <li class="search">                <div class="icon on-search-icon">                  <div class="wrap">                    <div class="curr-icon-wrap">                      <div class="curr-icon"></div>                    </div>                    <form action="http://so.csdn.net/search" method="get" target="_blank">                      <input type="hidden" value="toolbar" name="ref" accesskey="2">                      <div class="border">                        <input placeholder="搜索" type="text" value="" name="q" accesskey="2"><span class="icon-enter-sm"></span>                      </div>                    </form>                  </div>                </div>              </li>              <li class="favor">                <div class="icon on-favor-icon">                  <div class="wrap">                    <div class="curr-icon-wrap">                      <div class="curr-icon"></div>                    </div>                    <div style="display:none;" class="favor-success"><span class="msg">收藏成功</span>                      <div class="btns"><span class="btn btn-primary ok">确定</span></div>                    </div>                    <div style="display:none;" class="favor-failed"><span class="icon-danger-lg"></span><span class="msg">收藏失败，请重新收藏</span>                      <div class="btns"><span class="btn btn-primary ok">确定</span></div>                    </div>                    <form role="form" class="form-horizontal favor-form">                      <div class="form-group">                        <div class="clearfix">                          <label for="input-title" class="col-sm-2 control-label">标题</label>                          <div class="col-sm-10">                            <input id="inputTitle" type="text" placeholder="" class="title form-control">                          </div>                        </div>                        <div class="alert alert-danger"><strong></strong>标题不能为空</div>                      </div>                      <div class="form-group">                        <label for="input-url" class="col-sm-2 control-label">网址</label>                        <div class="col-sm-10">                          <input id="input-url" type="text" placeholder="" class="url form-control">                        </div>                      </div>                      <div class="form-group">                        <label for="input-tag" class="col-sm-2 tag control-label">标签</label>                        <div class="col-sm-10">                          <input id="input-tag" type="text" class="form-control tag">                        </div>                      </div>                      <div class="form-group">                        <label for="input-description" class="description col-sm-2 control-label">摘要</label>                        <div class="col-sm-10">                          <textarea id="input-description" class="form-control description"></textarea>                        </div>                      </div>                      <div class="form-group">                        <div class="col-sm-offset-2 col-sm-10 ft">                          <div class="col-sm-4 pull-left">                            <div class="checkbox">                              <label>                                <input type="checkbox" name="share" checked="checked" class="share">公开                              </label>                            </div>                          </div>                          <div class="col-sm-8 pull-right favor-btns">                            <button type="button" class="cancel btn btn-default">取消</button>                            <button type="submit" class="submit btn btn-primary">收藏</button>                          </div>                        </div>                      </div>                    </form>                  </div>                </div>              </li>              <li class="notify">                <div style="display:none" class="number"></div>                <div style="display:none" class="icon-hasnotes-sm"></div>                <div id="header_notice_num"></div>                <div class="icon on-notify-icon">                  <div class="wrap">                    <div class="curr-icon-wrap">                      <div class="curr-icon"></div>                    </div>                    <div id="note1" class="csdn_note">                      <div class="box"></div>                    <iframe src="about:block" frameborder="0" allowtransparency="true" style="z-index:-1;position:absolute;top:0;left:0;width:100%;height:100%;background:transparent"></iframe></div>                  </div>                </div>              </li>              <li class="ugc">                <div class="icon on-ugc-icon">                  <div class="wrap clearfix">                    <div class="curr-icon-wrap">                      <div class="curr-icon"></div>                    </div>                    <dl>                      <dt><a href="http://geek.csdn.net/news/expert?ref=toolbar" target="_blank" class="p-news clearfix" style="display:none;"><em class="icon"></em><span>分享资讯</span></a></dt>                      <dt style="border: none;"><a href="http://u.download.csdn.net/upload?ref=toolbar" target="_blank" class="p-doc clearfix"><em class="icon"></em><span>传PPT/文档</span></a></dt>                      <dt><a href="http://bbs.csdn.net/topics/new?ref=toolbar" target="_blank" class="p-ask clearfix"><em class="icon"></em><span>提问题</span></a></dt>                      <dt><a href="http://write.blog.csdn.net/postedit?ref=toolbar" target="_blank" class="p-blog clearfix"><em class="icon"></em><span>写博客</span></a></dt>                      <dt><a href="http://u.download.csdn.net/upload?ref=toolbar" target="_blank" class="p-src clearfix"><em class="icon"></em><span>传资源</span></a></dt>                      <dt><a href="https://code.csdn.net/projects/new?ref=toolbar" target="_blank" class="c-obj clearfix"><em class="icon"></em><span>创建项目</span></a></dt>                      <dt><a href="https://code.csdn.net/snippets/new?ref=toolbar" target="_blank" class="c-code clearfix"><em class="icon"></em><span>创建代码片</span></a></dt>                    </dl>                  </div>                </div>              </li>              <li class="profile">                <div class="icon on-profile-icon"><img src="./article_files/100x100.jpg" class="curr-icon-img">                  <div class="wrap clearfix">                    <div class="curr-icon-wrap">                      <div class="curr-icon"></div>                    </div>                    <div class="bd">                      <dl class="clearfix">                        <dt class="pull-left img"><a target="_blank" href="http://my.csdn.net/?ref=toolbar" class="avatar"><img src="./article_files/100x100.jpg"></a></dt>                        <dd class="info" style="border: none;"><a target="_blank" href="http://my.csdn.net/?ref=toolbar" class="nickname"></a><a class="set-nick" href="https://passport.csdn.net/account/profile">设置昵称<span class="write-icon"></span></a><span class="dec"><a class="fill-dec" href="http://my.csdn.net/" target="_blank">编辑自我介绍，让更多人了解你<span class="write-icon"></span></a></span></dd>                      </dl>                    </div>                    <div class="ft clearfix"><a target="_blank" href="http://my.csdn.net/my/account/changepwd?ref=toolbar" class="pull-left"><span class="icon-cog"></span>帐号设置</a><a href="https://passport.csdn.net/account/logout?ref=toolbar" target="_top" class="pull-left" style="margin-left:132px; width:18px; height:27px; white-space:nowrap; overflow:hidden;"><span class="icon-signout"></span><span class="out">退出</span></a></div>                  </div>                </div>              </li>              <li class="apps">                <div id="chasnew123" class="hasnew" style="display: none; "></div>                <div id="cappsarea123" class="icon on-apps-icon">                  <div class="wrap clearfix">                    <div class="curr-icon-wrap">                      <div class="curr-icon"></div>                    </div>                  <div class="detail">                    <dl>                      <dt>                        <h5>社区</h5>                      </dt>                      <dd> <a href="http://blog.csdn.net/?ref=toolbar" target="_blank">博客</a></dd>                      <dd> <a href="http://bbs.csdn.net/?ref=toolbar" target="_blank">论坛</a></dd>                      <dd> <a href="http://download.csdn.net/?ref=toolbar" target="_blank">下载</a></dd>                      <dd><a href="http://ask.csdn.net/?ref=toolbar" target="_blank">技术问答</a></dd>                      <dd><a href="http://geek.csdn.net/?ref=toolbar" target="_blank">极客头条</a></dd>                      <dd style="display:none"> <a href="http://hero.csdn.net/?ref=toolbar" target="_blank">英雄会</a></dd>                    </dl>                  </div>                  <div class="detail">                    <dl>                      <dt>                        <h5>服务</h5>                      </dt>                      <dd style="display:none"> <a href="http://job.csdn.net/?ref=toolbar" target="_blank">JOB<img src="./article_files/new.gif" style="display: none; margin-top: -26px; width: 23px;"></a></dd>                      <dd> <a href="http://edu.csdn.net/?ref=toolbar" target="_blank">学院<img src="./article_files/new.gif" style="display: none; margin-top: -26px; width: 23px;"></a></dd>                      <dd> <a href="https://code.csdn.net/?ref=toolbar" target="_blank">CODE</a></dd>                      <dd> <a href="http://huiyi.csdn.net/?ref=toolbar" target="_blank">活动</a></dd>                      <dd> <a href="http://www.csto.com/?ref=toolbar" target="_blank">CSTO</a></dd>                      <dd> <a href="http://mall.csdn.net/?ref=toolbar" target="_blank">C币兑换<img src="./article_files/new.gif" style="display: none; margin-top: -26px; width: 23px;"></a></dd>                    </dl>                  </div>                  <div class="detail last">                    <dl>                      <dt>                        <h5>俱乐部</h5>                      </dt>                      <dd> <a href="http://cto.csdn.net/?ref=toolbar" target="_blank">CTO俱乐部</a></dd>                      <dd> <a href="http://student.csdn.net/?ref=toolbar" target="_blank">高校俱乐部</a></dd>                    </dl>                  </div>                </div>              </div>            </li>            </ul>          </div>        </div>    </div>
-->
      <!-- 统计代码 -->
<script type="text/javascript">
  var protocol = window.location.protocol;
  document.write('<script type="text/javascript" src="' +protocol+ '//csdnimg.cn/pubfooter/js/repoAddr2.js?v=' + Math.random() + '"></'+'script>');
</script><script type="text/javascript" src="./article_files/repoAddr2.js"></script>



        <script src="./article_files/left_menu-60e0b7ca9e89de4807950d883e53419f.js" type="text/javascript"></script>
<script type="text/javascript" src="./article_files/cnick.js"></script>
<script src="./article_files/left_menu.js" type="text/javascript" charset="utf-8"></script>
<div class="wrap">
          <div class="chal">
  <div class="conts">
    <dl>
      <dt class="red">
        <h1>Java论坛</h1>
      </dt>
      <dd><strong class="color_004797">版主：</strong>
        <dfn class="font_weight_bold"><a href="http://my.csdn.net/fangmingshijie" rel="nofollow" target="_blank" title="fangmingshijie">fangmingshijie</a></dfn>
        <dfn class="font_weight_bold"><a href="http://my.csdn.net/defonds" rel="nofollow" target="_blank" title="defonds">defonds</a></dfn>
        <dfn class="font_weight_bold"><a href="http://my.csdn.net/oh_Maxy" rel="nofollow" target="_blank" title="oh_Maxy">oh_Maxy</a></dfn>
        <dfn class="font_weight_bold"><a href="http://my.csdn.net/rui888" rel="nofollow" target="_blank" title="rui888">rui888</a></dfn>
      <a href="http://bbs.csdn.net/forums/Java/apply_moderator/new" class="button">
        <img alt="Ico_application" src="./article_files/ico_application-f58b92e7ee095657c9c3abc94bb253c2.png">
</a>      </dd>
      <dd><strong class="color_004797">版面简介：</strong>讨论Java相关技术</dd>
    </dl>
  </div>
<div class="bread_nav">
  <a href="http://www.csdn.net/" target="_blank">CSDN</a> <em>&gt;</em>
  <a href="http://bbs.csdn.net/" target="_blank">CSDN论坛</a> <em>&gt;</em>
  <a href="http://bbs.csdn.net/forums/Java">Java</a>
</div>

 
<div class="page_nav">
  <div class="fr">
    <a href="http://bbs.csdn.net/topics/new?forum_id=Java" target="_blank" class="btn_1"><span>发帖</span></a><a href="javascript:void(0)" onclick="window.location.reload()" class="btn_2"><span>刷新</span></a><a href="http://bbs.csdn.net/forums/Java.atom" class="rss" target="_blank">RSS</a>
  </div>

 <ul>
    <li class="select">
  <a href="http://bbs.csdn.net/forums/Java">首页</a>
</li>

    
        <li class="select">
  <a href="http://bbs.csdn.net/forums/Java">1</a>
</li>

        <li class="">
  <a href="http://bbs.csdn.net/forums/Java?page=2">2</a>
</li>

        <li class="">
  <a href="http://bbs.csdn.net/forums/Java?page=3">3</a>
</li>

        <li class="">
  <a href="http://bbs.csdn.net/forums/Java?page=4">4</a>
</li>

        <li class="">
  <a href="http://bbs.csdn.net/forums/Java?page=5">5</a>
</li>

        <li class="">
  <a href="http://bbs.csdn.net/forums/Java?page=6">6</a>
</li>

        <li class="page gap">
  ...
</li>

    <li>
  <a href="http://bbs.csdn.net/forums/Java?page=2" class="next">下一页</a>
</li>

    <li class="">
  <a href="http://bbs.csdn.net/forums/Java?page=2696">尾页</a>
</li>

    <li><span>总数：134789，</span><span>共2696页</span></li>
  </ul>
</div>

<div class="content">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table_list parent_forum ">
    <colgroup>
      <col>
      <col width="60px">
      <col width="120px">
      <col width="60px">
      <col width="120px">
      <col width="60px">
    </colgroup>
    <tbody><tr class="zebra">
      <th width="64%">标题</th>
      <th width="3%" class="tc">分数</th>
      <th width="13%" class="tc">提问人</th>
      <th width="4%" class="tc">回复数</th>
      <th width="13%" class="tc">最后更新时间</th>
      <th width="3%" class="tc">功能</th>
    </tr>
    <%
    	for(Iterator<Article> it = articles.iterator();it.hasNext();){
    		Article a = it.next();
    		String str="";
    		for(int i=0;i<a.getGrade();i++){
    			str+="---";
    		}
     %>
          <tr>
    <td class="title">
    <!-- 
      <strong class="green">？</strong>
        <span class="red">[置顶]</span>
    -->
      <a href="articledetail.jsp?id=<%=a.getId() %>" target="_blank" ><%= str+a.getTitle()%></a>
    </td>
    <td class="tc">300</td>
    <td class="tc">
      <a href="http://my.csdn.net/defonds" rel="nofollow" target="_blank" >ChenJie</a><br>
      <span class="time"><%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(a.getPdate()) %></span></td>
    <td class="tc">20</td>
    <td class="tc">
      <a href="http://my.csdn.net/ensey" rel="nofollow" target="_blank" title="ensey">ABCD</a><br>
      <span class="time"><%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(a.getPdate()) %></span>
    </td>
    <td class="tc">
      <a href="http://bbs.csdn.net/topics/391889586/close" target="_blank">管理</a>
    </td>
  </tr>

  <tr class="zebra">
    <td class="title">
    <!--
      <strong class="green">？</strong>
        <span class="red">[置顶]</span>
     -->
      <a href="articledetail.jsp?id=<%=a.getId() %>" target="_blank" ><%= str+a.getTitle()%></a>
    </td>
    <td class="tc">100</td>
    <td class="tc">
      <a href="http://my.csdn.net/soledadzz" rel="nofollow" target="_blank" title="soledadzz">soledadzz</a><br>
      <span class="time">01-13 17:12</span></td>
    <td class="tc">104</td>
    <td class="tc">
      <a href="http://my.csdn.net/jojoku2008" rel="nofollow" target="_blank" title="jojoku2008">jojoku2008</a><br>
      <span class="time">02-27 14:38</span>
    </td>
    <td class="tc">
      <a href="http://bbs.csdn.net/topics/391891455/close" target="_blank">管理</a>
    </td>
  </tr>

	<% } %>
    <tr>
      <th>标题</th>
      <th class="tc">分数</th>
      <th class="tc">提问人</th>
      <th class="tc">回复数</th>
      <th class="tc">最后更新时间</th>
      <th class="tc">功能</th>
    </tr>
  </tbody></table>
</div>


<div class="page_nav">
  <div class="fr">
    <a href="http://bbs.csdn.net/topics/new?forum_id=Java" target="_blank" class="btn_1"><span>发帖</span></a><a href="javascript:void(0)" onclick="window.location.reload()" class="btn_2"><span>刷新</span></a><a href="http://bbs.csdn.net/forums/Java.atom" class="rss" target="_blank">RSS</a>
  </div>

    <ul>
    <li class="select">
  <a href="http://bbs.csdn.net/forums/Java">首页</a>
</li>

    
        <li class="select">
  <a href="http://bbs.csdn.net/forums/Java">1</a>
</li>

        <li class="">
  <a href="http://bbs.csdn.net/forums/Java?page=2">2</a>
</li>

        <li class="">
  <a href="http://bbs.csdn.net/forums/Java?page=3">3</a>
</li>

        <li class="">
  <a href="http://bbs.csdn.net/forums/Java?page=4">4</a>
</li>

        <li class="">
  <a href="http://bbs.csdn.net/forums/Java?page=5">5</a>
</li>

        <li class="">
  <a href="http://bbs.csdn.net/forums/Java?page=6">6</a>
</li>

        <li class="page gap">
  ...
</li>

    <li>
  <a href="http://bbs.csdn.net/forums/Java?page=2" class="next">下一页</a>
</li>

    <li class="">
  <a href="http://bbs.csdn.net/forums/Java?page=2696">尾页</a>
</li>

    <li><span>总数：134789，</span><span>共2696页</span></li>
  </ul>

</div>

