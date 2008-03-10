<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="./taglibs.jsp"%>
<html>
<head>
<title></title>
<%@ include file="./meta.jsp"%>
<link href="../resources/styles/style.css" rel="stylesheet" type="text/css">
<script>
var imagePath="../resources/images/menu/favorite";
var menus=[["target","image","openImage","closeImage"],
            ["hotBody","hotImg","/fmenurd_up.gif","/fmenurd_down.gif"],
            ["otherBody","otherImg","/fmenuqt_up.gif","/fmenuqt_down.gif"],
            ["promptBody","promptImg","/fmenuyq_up.gif","/fmenuyq_down.gif"]];
            
function openOrClose(bakImage,target)
{
	for(i=1;i<menus.length;i++)
	{		
		if(target==menus[i][0])
		{
			var obj=document.getElementById(target);
			if(document.images[bakImage].src.indexOf(menus[i][2])!=-1)
			{
				document.images[bakImage].src=imagePath+menus[i][3];
				obj.style.display="none";
			}else
			{
				document.images[bakImage].src=imagePath+menus[i][2];
				obj.style.display="block";
			}
			break;
		}
	}
}
</script>
</head>
<body class="wsd_master_11" ondragstart="return false;"
		oncontextmenu="return false;" onselectstart="return false"> 
<table id="wsd_masterhead">
<tr><td height="30" valign="top" class="hothead"><a href="#" onClick="openOrClose('hotImg','hotBody');"><img name="hotImg" border="0" src="../resources/images/menu/favorite/fmenurd_up.gif"></a></td></tr>
<tr><td  id="hotbody" class="hotbody">
				<table id="wsd_hotmenu">
					<tr>
						<td class="linebg"><img border="0" src="../resources/images/menu/lx.gif">
							<a href="./templates/searchListDemo.html" target="workbench">查询列表演示</a>
						</td>
					</tr>
					<tr>
						<td class="linebg"><img border="0" src="../resources/images/menu/lx.gif">
							<a href="./templates/searchListAddDemo.html" target="workbench">查询列表增加演示</a>
					  </td>
					</tr>
					<tr>
						<td class="linebg"><img  border="0" src="../resources/images/menu/lx.gif">&nbsp;运输方信息</td>
					</tr>
					<tr>
						<td class="linebg"><img  border="0" src="../resources/images/menu/lx.gif">&nbsp;运输方信息</td>
					</tr>
					<tr>
						<td class="linebg"><img  border="0" src="../resources/images/menu/lx.gif">&nbsp;运输方信息</td>
					</tr>
					<tr>
						<td class="linebg"><img  border="0" src="../resources/images/menu/lx.gif">&nbsp;运输方信息</td>
					</tr>
					<tr>
						<td class="linebg"><img border="0" src="../resources/images/menu/lx.gif">&nbsp;运输方信息</td>
					</tr>
					<tr>
						<td class="linebg"><img border="0" src="../resources/images/menu/lx.gif">&nbsp;运输方信息</td>
					</tr>
					<tr>
						<td class="linebg"><img  border="0" src="../resources/images/menu/lx.gif">&nbsp;运输方信息</td>
					</tr>
					<tr>
						<td class="linebg"><img  border="0" src="../resources/images/menu/lx.gif">&nbsp;运输方信息</td>
					</tr>
					<tr>
						<td class="linebg"><img  border="0" src="../resources/images/menu/lx.gif">&nbsp;运输方信息</td>
					</tr>
					<tr>
						<td class="linebg"><img  border="0" src="../resources/images/menu/lx.gif">&nbsp;运输方信息</td>
					</tr>
					<tr>
						<td class="linebg"><img border="0" src="../resources/images/menu/lx.gif">&nbsp;其他服务信息2</td>
					</tr>
					<tr>
						<td class="linebg"><img border="0" src="../resources/images/menu/lx.gif">&nbsp;其他服务信息3</td>
					</tr>
				</table>
			</td></tr>
		<tr><td class="hotbottom"></td></tr>
	</table>
	<table id="wsd_otherhead">
		<tr><td height="29" valign="top" class="hothead"><a href="#" onClick="openOrClose('otherImg','otherBody');"> <img name="otherImg" border=0 src="../resources/images/menu/favorite/fmenuqt_up.gif"></a></td></tr>
		<tr><td id="otherbody" class="hotbody">
			<table id="wsd_hotmenu">
					<tr>
						<td class="linebg"><img border="0" src="../resources/images/menu/lx.gif">&nbsp;其他服务信息1</td>
					</tr>
					<tr>
						<td class="linebg"><img border="0" src="../resources/images/menu/lx.gif">&nbsp;其他服务信息2</td>
					</tr>
					<tr>
						<td class="linebg"><img border="0" src="../resources/images/menu/lx.gif">&nbsp;其他服务信息3</td>
					</tr>
					<tr>
						<td class="linebg"><img border="0" src="../resources/images/menu/lx.gif">&nbsp;其他服务信息2</td>
					</tr>
					<tr>
						<td class="linebg"><img border="0" src="../resources/images/menu/lx.gif">&nbsp;其他服务信息3</td>
					</tr>
				</table>	
			</td>	</tr>
		<tr><td class="hotbottom "></td></tr>
	</table>
	<table id="wsd_otherhead">
		<tr><td height="29" valign="top" class="hothead"><a href="#" onClick="openOrClose('promptImg','promptBody');"> <img name="promptImg" border=0 src="../resources/images/menu/favorite/fmenuyq_up.gif"></a></td></tr>
		<tr><td id="promptBody" class="hotbody">
			<table id="wsd_hotmenu">
					<tr>
						<td class="linebg"><img border="0" src="../resources/images/menu/lx.gif">&nbsp;其他服务信息1</td>
					</tr>
					<tr>
						<td class="linebg"><img border="0" src="../resources/images/menu/lx.gif">&nbsp;其他服务信息2</td>
					</tr>
					
				</table>	
			</td>	</tr>
		<tr><td class="hotbottom"></td></tr>
	</table>
</body>
</html>
