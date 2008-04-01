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
<link rel="stylesheet" rev="stylesheet" href="../resources/styles/content-import.css" type="text/css" media="screen" charset="utf-8" />
<script type="text/javascript" src="../resources/scripts/sagacity.js"></script>
<script type="text/javascript">
	//定义在页面上只允许同时打开MAX_PAGES个页面
	var MAX_PAGES=10;
	
	/**
	*通过日期取得一个随机数
	*/
  function getNowTime()
  {
  	var dateTime=new Date();
  	return ''+dateTime.getFullYear()+(dateTime.getMonth()+1)+dateTime.getDate()+dateTime.getHours()+dateTime.getMinutes()+dateTime.getSeconds()+dateTime.getMilliseconds();
  }
  
  /**
   *pageUrl新打开页面的url
   *functionName 业务操作名称
   *navigationTitle导航，
   *isSingle是否独占方式打开页面
   *
   */
  function addPagePannel(pageUrl,functionName,navigationTitle,isSingle)
  {
  	var pagePannels=document.getElementById("pagePannels");
  	var pageFrames=document.getElementById("pageFrames");
  	var nowTimeStr=getNowTime();
  	var frameHeight=top.document.body.offsetHeight-top.frames["topFrame"].document.body.offsetHeight-top.frames["footFrame"].document.body.offsetHeight-pageFrames.offsetTop;
  	//打开新页面时将分屏模式转为单屏模式
  	pagePannels.className="half";
  	//独占方式打开页面，删除所有工作台上的页面
  	if(isSingle)
  	{
	  		pagePannels.innerHTML="";
	  		pageFrames.innerHTML="";
  	}else
  	{
	  		//将已有的pannel设置未选中，并将已有的frame设置为隐藏
	  		for(i=0;i<pagePannels.childNodes.length;i++)
	  		{
	  				pagePannels.childNodes[i].className=null;
	  				pageFrames.childNodes[i].style.display="none";
	  				pagePannels.childNodes[i].childNodes[1].src="../resources/images/close2.gif";	
	  		}		
    }
    
    var pannelId="pannel_"+nowTimeStr;
	  var frameId="frame_"+nowTimeStr;
	  var pagePannel=document.createElement("li");
	  pagePannel.setAttribute("id",pannelId);
	  pagePannel.setAttribute("className","check");
	  pagePannel.setAttribute("class","check");
	  pagePannel.innerHTML="<a href='javascript:pannelSelected(\""+pannelId+"\",\""+frameId+"\",\""+navigationTitle+"\")' title='"+functionName+"'>"+functionName+"</a><img id='img_"+pannelId+"' src='../resources/images/close.gif' alt='关闭标签' onclick='destoryPage(\""+pannelId+"\",\""+frameId+"\")'/>"
	  pagePannels.appendChild(pagePannel);
   
	  var pageFrame=document.createElement("iframe");
	  pageFrame.setAttribute("id",frameId);
	  pageFrame.setAttribute("width","100%");
	 	pageFrame.setAttribute("height",frameHeight);
	  pageFrame.setAttribute("src",pageUrl);
	  pageFrame.setAttribute("scrolling","auto");
	  pageFrame.setAttribute("frameborder","0");
	  pageFrames.appendChild(pageFrame);
   	document.getElementById("splitScreen").src="../resources/images/toolbar1.gif";
  }
  
  /**
   *页面选中
   */
  function pannelSelected(pannelId,frameId,navigationTitle)
  {
  	if(document.getElementById("splitScreen").src.indexOf("toolbar2.gif")!=-1)
  	{
  		return;
  	}
  	
  	updateNavigationTitle(navigationTitle);
 		
  	var pagePannels=document.getElementById("pagePannels");
  	var pageFrames=document.getElementById("pageFrames");
  	var frameHeight=top.document.body.offsetHeight-top.frames["topFrame"].document.body.offsetHeight-top.frames["footFrame"].document.body.offsetHeight-pageFrames.offsetTop;
  	//将已有的pannel设置未选中，并将已有的frame设置为隐藏
		for(i=0;i<pagePannels.childNodes.length;i++)
		{
				pagePannels.childNodes[i].className=null;
				pagePannels.childNodes[i].childNodes[1].src="../resources/images/close2.gif";
				pageFrames.childNodes[i].style.display="none";
		}
  	document.getElementById(pannelId).className="check";
  	document.getElementById("img_"+pannelId).src="../resources/images/close.gif";
  	document.getElementById(frameId).style.display="block";
  	document.getElementById(frameId).style.width="100%";
  	document.getElementById(frameId).style.height=frameHeight;
  }
  
  /**
   *分屏
   *
   */
  function averageScreen(obj)
  {
  	//判断是否已经分屏
  	var isFull;
  	if(obj.src.indexOf("toolbar1")!=-1)
  			isFull=true;
  	else
  			isFull=false;
  
  	var pagePannels=document.getElementById("pagePannels");
  	var pageFrames=document.getElementById("pageFrames");
  	var frameHeight=top.document.body.offsetHeight-top.frames["topFrame"].document.body.offsetHeight-top.frames["footFrame"].document.body.offsetHeight-pageFrames.offsetTop;
  	if(pagePannels.childNodes.length!=2)
  	{
  			alert("对不起，本系统只提供两个页面的情况下分屏对比!");
  			return;
    }
    pagePannels.className="normal";
    //图片切换,分屏
    if(isFull)
    {
    	obj.src="../resources/images/toolbar2.gif";
    	pagePannels.childNodes[0].childNodes[1].src="../resources/images/close.gif";
	    pagePannels.childNodes[1].childNodes[1].src="../resources/images/close.gif";
	    
	    pageFrames.childNodes[0].className="float-l";
	    pageFrames.childNodes[0].style.width=document.body.offsetWidth/2;
	    pageFrames.childNodes[0].style.height=frameHeight;
	    pageFrames.childNodes[0].style.display="block";
	    
	    pageFrames.childNodes[1].className="float-r";
	    pageFrames.childNodes[1].style.width=document.body.offsetWidth/2;
	    pageFrames.childNodes[1].style.height=frameHeight;
	    pageFrames.childNodes[1].style.display="block";
    }
    else
    {
    	obj.src="../resources/images/toolbar1.gif";
    	pagePannels.className="half";
    	pagePannels.childNodes[1].childNodes[0].click();
    }
  }
  
  /**
   *关闭页面
   */
  function destoryPage(pagePannelId,pageFrameId)
  {
  	var pagePannels=document.getElementById("pagePannels");
  	var pageFrames=document.getElementById("pageFrames");
  	
  	if(pagePannels.childNodes.length==1)
  	{
  		alert("单个窗口不允许关闭");
  		return;
  	}
  	
  	document.getElementById("splitScreen").src="../resources/images/toolbar1.gif";
  	for(i=0;i<pagePannels.childNodes.length;i++)
  	{
  		if(pagePannels.childNodes[i].getAttribute("id")==pagePannelId)
  		{	
  			pagePannels.removeChild(pagePannels.childNodes[i]);
  			
  			if(i==0)
  				pagePannels.childNodes[0].childNodes[0].click();
  			else
  				pagePannels.childNodes[i-1].childNodes[0].click();
  			break;
  		}
  	}
  	
  	for(i=0;i<pageFrames.childNodes.length;i++)
  	{
  		if(pageFrames.childNodes[i].getAttribute("id")==pageFrameId)
  		{	
  			pageFrames.removeChild(pageFrames.childNodes[i]);
  			break;
  		}
  	}
  	
  }
  
  /**
  *当左边菜单做隐藏和显示操作时调整分屏的页面宽度
  */
  function adjustPageWidth()
  {
  	if(document.getElementById("splitScreen").src.indexOf("toolbar2.gif")!=-1)
  	{
  		var pageFrames=document.getElementById("pageFrames");
  		pageFrames.childNodes[0].style.width=document.body.offsetWidth/2;
  		pageFrames.childNodes[1].style.width=document.body.offsetWidth/2;
  	}
  }
</script>
<title>江苏邮政</title>

</head>


<body id="content">
    <!-- 工具菜单 op -->
     <div class="cont_toolbar">
          <span><a href="####"><img id="splitScreen" src="../resources/images/toolbar1.gif" alt="分栏式预览" onclick="averageScreen(this);"/></a>
          	<!--<a href="####"><img src="../resources/images/toolbar2.gif" alt="叠加式预览" /></a>-->
          </span>
          
          <!-- 对分操作模式 op -->
          
          <ul id="pagePannels" class="half">
              
          </ul>
          
          <!-- 对分操作模式 ed -->
          
          <!-- 并行操作模式 op -->
          <!-- 暂时隐藏
          <ul class="half">
              <li class="check"><a href="####" title="入库详单">入库详单</a><img src="../global/img/close.gif" alt="关闭标签" onclick="" /></li>
              <li><a href="####" title="假期退料单">假期退料单</a><img src="../global/img/close.gif" alt="关闭标签" onclick="" /></li>
              <li><a href="####" title="假期退料单">假期退料单</a><img src="../global/img/close.gif" alt="关闭标签" onclick="" /></li>
          </ul>
          -->
          <!-- 并行操作模式 ed -->
          
     </div>
    <!-- 工具菜单 ed -->
    
    <div id="pageFrames">
    	
    <!-- iframe 内容 op -->
    
    	  <!-- 对分操作模式 op -->
         
          <!-- 对分操作模式 ed -->
    
          <!-- 并行操作模式 op -->
          <!-- 暂时隐藏
          <iframe src="并行操作table.html"; width="100%" height="700" scrolling="auto" frameborder="0"></iframe> 
           -->
          <!-- 并行操作模式 ed -->
    
    
     <!-- iframe 内容 ed -->
	   </div>
</body>
</html>
