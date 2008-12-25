/*
 * sagacity.js 0.1 - common Javascript for enterprise information system
 *
 * Copyright (c) 2007 zhongxuchen (sagacity.com)
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 * $Date: 2008/08/19 01:21:44 $
 * $Rev: 3353 $
 */

/*
 *字符串全局替换
 */
String.prototype.replaceAll =function stringReplaceAll(AFindText, ARepText) {
	raRegExp = new RegExp(AFindText, "g");
	return this.replace(raRegExp, ARepText)
}

/*
 * 页面html传参数
 */
var htmlRequest = {
	QueryString : function(val) {
		var uri = window.location.search;
		var re = new RegExp("" + val + "=([^&?]*)", "ig");
		return ((uri.match(re)) ? (uri.match(re)[0].substr(val.length + 1))
				: null);
	}
}

/**
 * 
 * @param imagePath
 * @return
 */
function sagacity(imagePath) {
	this.imagePath = imagePath;
	if (imagePath == null || imagePath == "")
		this.imagePath = "../images";
}

/**
 * 菜单开合
 * @param menuId
 * @return
 */
sagacity.prototype.menuOpenClose = function(menuId) {
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
sagacity.prototype.favoriteOpenClose = function(menuId, imageId, k, subLen) {
	var menuItems = document.getElementById(menuId);
	if (menuItems != null) {
		if (menuItems.className == "show") {
			menuItems.className = "un_show";
			document.getElementById(imageId).src = this.imagePath
					+ "/menu/left-menu-arrow-dw.gif";
		} else {
			for (j = 0; j < subLen; j++) {
				var sag_menu = "sag_menu_0" + k + j;
				var sag_menu_image = "sag_menu_image_0" + k + j;
				document.getElementById(sag_menu).className = "un_show";
				document.getElementById(sag_menu_image).src = this.imagePath
						+ "/menu/left-menu-arrow-dw.gif";
			}
			menuItems.className = "show";
			document.getElementById(imageId).src = this.imagePath
					+ "/menu/left-menu-arrow-up.gif";
		}
	}
};

/**
 * 点击子系统图标，菜单页面加载子系统名称 以及检测菜单开合的状态，设置默认值
 * @param subSysTitle
 * @param subSysName
 * @return
 */
sagacity.prototype.loadSubSys=function(subSysTitle,subSysName) {
	var subtitle = htmlRequest.QueryString(subSysTitle);
	var subName = htmlRequest.QueryString(subSysName);
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

	initializeMenu("left_menu_main", "../data/menuData.xml",
			subName);
}

sagacity.prototype.showOrHidenMenu=function(obj,path)
{ 
	 var parentFrame = top.document.getElementById("mainframe");
	 
	 if(parentFrame.cols=="9,*")
	 {
	 		parentFrame.cols="200,*";
	 		if(currentShowTab!=-1)
	 		{
	 			document.getElementById("left_menu_main"+currentShowTab).style.display="block";
	 			document.getElementById("left_menu_main"+currentShowTab).style.width="160px";
	 			document.getElementById("left_menu_main"+currentShowTab).style.margin="4px 0 0 5px";
	 		}
	 		document.getElementById("left_menu_card").style.display="block";
	 		document.getElementById("left_menu_card").style.width="21px";
	 		document.getElementById("left_menu_card").style.margin="14px 0 0 2px";
	 	
	 		obj.style.left="191px"; 
	 	 	obj.src="../images/menu/left_menu_close.gif";
	 }
	 else
	 {	 	   
	 	  parentFrame.cols="9,*";
	 	  if(currentShowTab!=-1)
	 	  {
	 	  	  document.getElementById("left_menu_main"+currentShowTab).style.display="none";
	 	      document.getElementById("left_menu_main"+currentShowTab).style.width="0px";
	 	      document.getElementById("left_menu_main"+currentShowTab).style.margin="0px 0 0 0px";
	 	  }
	 	  document.getElementById("left_menu_card").style.display="none";
	 	  document.getElementById("left_menu_card").style.width="0px";
	 	  document.getElementById("left_menu_card").style.margin="0px 0 0 0px";
	 	  
	 	  obj.style.left="0px";
	 	 	
	 	  obj.src="../images/menu/left_menu_open.gif";
	 }
	 
	 //如果处于分屏状态，修改页面宽度
	 top.document.frames.workbench.adjustPageWidth();
}

/**
 * 修改页面导航
 * @param navigationTitle
 * @return
 */
sagacity.prototype.updateNavigationTitle=function(navigationTitle) {
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
			naviagtionStr += "<img src=\'resources/sagacity/images/toolbar/address-arrow2.gif' alt='' />\n";
		naviagtionStr += "<a href='####'>" + navigationTitle.substr(0, index)
				+ "</a>\n";
		navigationTitle = navigationTitle.substr(index + 1);
		count++;
	}
	header.innerHTML = naviagtionStr;
}

/**
 *产生列表的select 下拉框
 */
sagacity.prototype.createSelect=function(name,sourceStr,splitSign,sltValue,onchange)
{
	document.write("<select name="+name+"");
	if(onchange!="")
		document.write(" onchange="+onchange+"");
	document.write(" style='font-size:8pt;'>");
	var options=sourceStr.split(splitSign);
	for(i=0;i<options.length;i++)
	{
		if(options[i]!=null && options[i].trim()!="")
			document.write("<option value="+options[i]+((options[i]==sltValue)?" selected":"")+">"+options[i]+"</option>");
	}
	document.write("</select>");	
}