<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="author" content="江苏邮政" />
<meta name="copyright" content="江苏邮政" />
<meta name="url" content="http://www.xxx.com" />

<!-- <meta name="robots" content="all" /> -->
<!-- <meta name="MSSmartTagsPreventParsing" content="TRUE" />  -->
<!-- <meta http-equiv="MSThemeCompatible" content="No" /> -->

<meta name="Description" content="江苏邮政" />
<meta name="Keywords" content="江苏邮政" />

<!-- CSS为保证多平台兼容的统一和可读采用utf编码，不影响页面，请程序员勿动 -->
<link rel="stylesheet" rev="stylesheet" href="../resources/styles/head-import.css" type="text/css" media="screen" charset="UTF-8" />
<script  language="javascript" type="text/javascript" src="../resources/scripts/sagacity.js"></script>
<script type="text/javascript">
	function setChecked(obj)
	{
		var subSys=obj.parentNode.childNodes;
		for(i=0;i<subSys.length;i++)
		{
			subSys[i].className=null;
		}
		obj.className="check";
  }	
</script>
<title>江苏邮政</title>

</head>


<body id="head">

<!-- 菜单 op -->
<div id="head_menu">

   <h1><img src="../resources/images/logo.gif" alt="江苏邮政" /></h1>
   
   <!-- menu op -->
       <ul>
          <li name="subSys" class="check" onclick="setChecked(this);"><a href="./menu.jsp?subsysTitle='收藏夹'&subsysName=favorite" target="leftFrame"><img src="../resources/images/menu_ico1.png" alt="" /><p>收藏夹</p></a></li>
          <li name="subSys" onclick="setChecked(this);"><a href="./menu.jsp?subsysTitle=营销管理&subsysName=tradeManager" target="leftFrame"><img src="../resources/images/menu_ico3.png" alt="" /><p>营销管理</p></a></li>
          <li name="subSys" onclick="setChecked(this);"><a href="./menu.jsp?subsysTitle=工作管理&subsysName=workManager" target="leftFrame">
		  <img src="../resources/images/menu_ico4.png" alt="" /><p>工作管理</p></a></li>
          <li name="subSys" onclick="setChecked(this);"><a href="./menu.jsp?subsysTitle=知识管理" target="leftFrame"><img src="../resources/images/menu_ico5.png" alt="" /><p>知识管理</p></a></li>
          <li name="subSys" onclick="setChecked(this);"><a href="./menu.jsp?subsysTitle=客户关系管理" target="leftFrame"><img src="../resources/images/menu_ico2.png" alt="" /><p>客户关系管理</p></a></li>
          <li name="subSys" onclick="setChecked(this);"><a href="./menu.jsp?subsysTitle=数据中心" target="leftFrame"><img src="../resources/images/menu_ico6.png" alt="" /><p>数据中心</p></a></li>
          <li name="subSys" onclick="setChecked(this);"><a href="./menu.jsp?subsysTitle=统计分析" target="leftFrame"><img src="../resources/images/menu_ico7.png" alt="" /><p>统计分析</p></a></li>         
       </ul>
   <!-- menu ed -->

</div>
<!-- 菜单 ed -->


<!-- 功能菜单 op -->
<div id="gn_menu">

    <!-- search op -->
        <div class="search">
           搜索：<input name="" type="text" class="input" /><input name="" type="button" class="button" />  
        </div>
    <!-- search ed -->
    
    <!-- address op -->
        <div id="navigationTitle"  class="address">
           你当前的位置:
           <a href="####">工作管理</a>
           <img src="../resources/images/address-arrow2.gif" alt="" />
           <a href="####">市场活动</a>
        </div>
    <!-- address ed -->
    
    <!-- tool op -->
        <ul class="tool">
		   		 <li><a href="workPannel.html" target="workbench"><img src="../resources/images/workbech.gif" alt="工作台" />工作台</a></li>
           <li><a href="####"><img src="../resources/images/tool1.gif" alt="注销" />注销</a></li>
           <li><a href="####"><img src="../resources/images/tool2.gif" alt="个性化设置" />个性化设置</a></li>
           <li><a href="####"><img src="../resources/images/tool3.gif" alt="帮助" />帮助</a></li>
        </ul>
    <!-- tool ed -->


</div>
<!-- 功能菜单 ed -->




</body>
</html>
