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
<link rel="stylesheet" rev="stylesheet" href="../resources/styles/menu-import.css" type="text/css" media="screen" charset="UTF-8" />
<script  language="javascript" type="text/javascript" src="../resources/scripts/xmlMenuLoad.js"></script>
<script  language="javascript" type="text/javascript" src="../resources/scripts/sagacity.js"></script>
<script type="text/javascript">
	function openWindow(pageUrl,functionName,navigationTitle,isSingle)
	{ 
		updateNavigationTitle(navigationTitle);
		if(top.document.frames.workbench.location.href.indexOf("workbench.html")==-1)
		{
			top.document.frames.workbench.location.href="workbench.html";	
		}
		top.document.frames.workbench.addPagePannel(pageUrl,functionName,navigationTitle,isSingle);
	}
</script>
<title>江苏邮政</title>

</head>
<body id="left_menu" onload="pageOnloadSubSys();">


<!-- 收缩按钮 op 因为采用定位 建议用onclick写法触发js -->
     
     <img id="leftMenuBtn" src="../resources/images/left_menu_close.gif" alt="关闭左侧菜单" onclick="javascript:openOrCloseMenu(this,'../resources/images');" class="left_menu_tool" />
     
<!-- 收缩按钮 ed -->


<!-- 卡片菜单 op 请程序根据情况自己调节文字及个数 check标识当时选择选项 -->
		<ul id="left_menu_card" class="left_menu_card">
			 <li class="check"><a href="####" title="" id="sub_title"></a></li>
	   <!--
       <li class="check"><a href="####" title="生产管理">生产管理</a></li>
       <li><a href="####" title="行政管理">行政管理</a></li>
	   -->
    </ul>
<!-- 卡片菜单 ed -->

<!-- 收缩菜单 op -->
    <div id="left_menu_main" class="left_menu_main">
              
    </div>
<!-- 收缩菜单 ed -->


</body>
</html>
