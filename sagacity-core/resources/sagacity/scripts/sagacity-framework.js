/*
 * sagacity.js 0.1 - common Javascript for enterprise information system
 *
 * Copyright (c) 2007 zhongxuchen (sagacity.com)
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 * $Date: 2007-09-16 23:42:06 -0400 (Sun, 16 Sep 2007) $
 * $Rev: 3353 $
 */

/**
 * 
 * @param imagePath
 * @return
 */
function sagacityFramework(imagePath) {
	this.imagePath = imagePath;
	if (imagePath == null || imagePath == "")
		this.imagePath = "../images";
}

/**
 * 菜单开合
 * @param menuId
 * @return
 */
sagacityFramework.prototype.menuOpenClose = function(menuId) {
	var menuItems = document.getElementById(menuId);
	if (menuItems != null) {
		if (menuItems.className == "show")
			menuItems.className = "un_show";
		else
			menuItems.className = "show";
	}

};

/**
 * 收藏夹菜单开合
 * @param menuId
 * @param imageId
 * @param k
 * @param subLen
 * @return
 */
sagacityFramework.prototype.favoriteOpenClose = function(menuId, imageId) {
	var menuItems=document.getElementById(menuId);
	 if(menuItems!=null)
	 {
	 		if(menuItems.className=="show")
	 		{
	 			menuItems.className="un_show";
	 			document.getElementById(imageId).src= this.imagePath+"/menu/menu-arrow-dw.gif";
	 		}
	 		else
	 		{
	 			menuItems.className="show";
	 			document.getElementById(imageId).src=this.imagePath+"/menu/menu-arrow-up.gif";
	 		}
	 }
};

/**
 * 点击子系统图标，菜单页面加载子系统名称 以及检测菜单开合的状态，设置默认值
 * @param subSysTitle
 * @param subSysName
 * @return
 */
sagacityFramework.prototype.loadSubSys=function(menuDataXml) {
	var subtitle = htmlRequest.QueryString("subsysTitle");
	var subName = htmlRequest.QueryString("subSysName");
	if (subtitle != null && subtitle != "") {
		document.getElementById("sub_title").title = subtitle.replaceAll("\'",
				"");
		document.getElementById("sub_title").innerText = subtitle.replaceAll(
				"\'", "");
	}
	var parentFrame = top.document.getElementById("mainframe");
	if (parentFrame.cols == "9,*") {
		parentFrame.cols = "200,*";
		document.getElementById("left_menu_main").style.display = "block";
		document.getElementById("left_menu_main").style.width = "160px";
		document.getElementById("left_menu_main").style.margin = "4px 0 0 5px";

		document.getElementById("left_menu_card").style.display = "block";
		document.getElementById("left_menu_card").style.width = "21px";
		document.getElementById("left_menu_card").style.margin = "14px 0 0 6px";

		document.getElementById("leftMenuBtn").style.left = "191px";
		document.getElementById("leftMenuBtn").src = "../images/menu/left_menu_close.gif";
	}

	this.initializeMenu("left_menu_main", menuDataXml,
			subName);
};

/**
 * 修改页面导航
 * @param navigationTitle
 * @return
 */
sagacityFramework.prototype.updateNavigationTitle=function(navigationTitle) {
	if (navigationTitle == null || navigationTitle == "")
		return;
	var header = top.frames["topFrame"].document
			.getElementById("navigationTitle");

	if (navigationTitle.indexOf(navigationTitle.length - 1) != ",")
		navigationTitle += ",";
	var naviagtionStr = "你当前的位置:";
	var count = 0;
	var index = 0;

	while (navigationTitle != ""
			&& (index = navigationTitle.indexOf(",")) != -1) {
		if (count > 0)
			naviagtionStr += "<img src=\'../resources/sagacity/images/toolbar/address-arrow2.gif' alt='' />\n";
		naviagtionStr += "<a href='####'>" + navigationTitle.substr(0, index)
				+ "</a>\n";
		navigationTitle = navigationTitle.substr(index + 1);
		count++;
	}
	header.innerHTML = naviagtionStr;
};

/**
 *加载菜单xml数据
 */
sagacityFramework.prototype.loadMenuXML= function(xmlFile)
{
    var xmlDoc;
    if(window.ActiveXObject)
    {
        xmlDoc= new ActiveXObject('Microsoft.XMLDOM');
        xmlDoc.async    = false;
        xmlDoc.load(xmlFile);
    }
    else if (document.implementation&&document.implementation.createDocument)
    {
        xmlDoc    = document.implementation.createDocument('', '', null);
        xmlDoc.load(xmlFile);
    }
    else
    {
        return null;
    }   
    return xmlDoc;
};


/**
 * 判断xml对象，看是否能够解析
 * @param xmlFile
 * @return
 */
sagacityFramework.prototype.checkXMLDocObj= function(xmlFile)
{
    var xmlDoc= this.loadMenuXML(xmlFile);
    if(xmlDoc==null)
    {
        alert('您的浏览器不支持xml文件读取,于是本页面禁止您的操作,推荐使用IE5.0以上可以解决此问题!');
    }  
    return xmlDoc;
};

/**
 * 初始化菜单
 * @param oid
 * @param xmlFile
 * @param subSysId
 * @return
 */
sagacityFramework.prototype.initializeMenu= function(oid, xmlFile,subSysId)
{		
	var xmlDoc = this.checkXMLDocObj(xmlFile);
	var isMultPannel=xmlDoc.getElementsByTagName("multPannel")[0].firstChild.nodeValue;
	var subsysModules  = xmlDoc.selectSingleNode("/project/subsys[@id='"+subSysId+"']");
	var displayStyle=subsysModules.getAttribute("style");
	var pageElement=document.getElementById("left_menu_main");
  	if(displayStyle=="default")
  		pageElement.className="left_menu_main";
  	else
  		pageElement.className="left_menu_fav";
    if(pageElement!=null && subsysModules!=null)
    {       
		if(subsysModules.childNodes.length>0)
		{
			var moduleCnt   = subsysModules.childNodes.length;
			var menuStr="";
			var subSysName=subsysModules.getAttribute("name");
			var navigationTitle;
			var module;
			var menu;
			var moduleName;
			var menuName;
			var menuUrl;
			for(var i=0;i<moduleCnt;i++)
			{         
				navigationTitle=subSysName;
				module=subsysModules.childNodes[i];
				moduleName=module.getAttribute("name");
				
				if(displayStyle=="favorite")
					menuStr+="<dl><dt><a href='javascript:sagacity.favoriteOpenClose(\"sag_menu_0"+(i+1)+"\",\"sag_menu_image_0"+(i+1)+"\");' title=\""+moduleName+"\"><img id=\"sag_menu_image_0"+(i+1)+"\" src=\"../resources/images/left-menu-arrow-up.gif\" alt=\"收缩菜单\" /><p>"+moduleName+"</p></a></dt>";
				else
					menuStr+="<dl><dt><a href='javascript:sagacity.menuOpenClose(\"sag_menu_0"+(i+1)+"\");'>"+moduleName+"</a></dt>";
				if(module.childNodes.length>0)
				{
					menuStr+="<dd id=\"sag_menu_0"+(i+1)+"\" class=\"un_show\">";
					for(var j=0;j<module.childNodes.length;j++)
					{
						menu=module.childNodes[j];
						menuUrl=menu.getAttribute("url");
						menuName=menu.getAttribute("name");
						menuStr+="<span><img src=\"../resources/images/window-contrl.gif\" onclick=\"openWindow('"+menuUrl+"','"+menuName+"','"+subSysName+","+moduleName+","+menuName+"',false);\"/></span>";
				  	menuStr+="<a href=\"javascript:openWindow('"+menuUrl+"\','"+menuName+"','"+subSysName+","+moduleName+","+menuName+"',true);\" title='"+menuName+"'>"+menuName+"</a>";
					}
					menuStr+="</dd>";
				}
				menuStr+="</dl>";
			}
		}
		
		pageElement.innerHTML=menuStr;
    }
    
}

/**
 * 选中子系统
 * @param obj
 * @return
 */
sagacityFramework.prototype.selectSubSys=function(obj)
{
	var subSys=obj.parentNode.childNodes;
	for(i=0;i<subSys.length;i++)
	{
		subSys[i].className=null;
	}
	obj.className="check";
}
  
var sagacity=new sagacityFramework('../resources/sagacity/images');
