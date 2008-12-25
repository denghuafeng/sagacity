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
 
/*
 *字符串全局替换
 */
String.prototype.replaceAll =function stringReplaceAll(AFindText, ARepText) {
	raRegExp = new RegExp(AFindText, "g");
	return this.replace(raRegExp, ARepText)
};

/**
 * 字符串两边去空白
 * @return
 */
String.prototype.trim = function () {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};

function sagacityUtils()
{
	
}

/**
 * 产生列表的select 下拉框
 * @param name
 * @param sourceStr
 * @param splitSign
 * @param sltValue
 * @param onchange
 * @return
 */
sagacityUtils.prototype.createSelect=function(name,sourceStr,splitSign,sltValue,onchange)
{
	document.write("<select name="+name+"");
	if(onchange!="")
		document.write(" onchange="+onchange+"");
	document.write(" style='font-size:8pt;'>");
	var options=sourceStr.split(splitSign);
	for(i=0;i<options.length;i++)
	{
		document.write("<option value="+options[i]+((options[i]==sltValue)?" selected":"")+">"+options[i]+"</option>");
	}
	document.write("</select>");	
}

/**
 * checkbox 全选或单选操作 create by chenrenfei
 * 2008-06-16
 */
function allOrNoneCheck(target,allChoose,self) {	
		var items=document.getElementsByName(target);
		var allslt=document.getElementById(allChoose);
		//单选
		if(self.getAttribute("name")==target)
		{
			if(self.checked==false)
				allslt.checked=false;
			else
			{
				var chkcnt=0;
				for (i = 0; i < items.length; i++) {
					if(items[i].checked)
						chkcnt++;
				}
				if(chkcnt==items.length)
					allslt.checked=true;
			}
	  }	else
	  {
			if (items.length > 0) {
				for (i = 0; i < items.length; i++) {
					items[i].checked = self.checked;
				}
			} else {
				items.checked = self.checked;
			}
	}
}

/**
 * 正整数验证
 * @param id
 * @return
 */
function positiveInt(id){
	var value = id.value;

	var reg=/^[1-9][0-9]*$/;

	if( !reg.test(value) ){
		if(isNaN(parseInt(value))){
			id.value="";
		}else{
			id.value=parseInt(value);
		}
		return;
	}
}

/**
 * 正浮点数验证
 * @param id
 * @return
 */
function positiveFloat(id){
	var value = id.value;
	var reg=/^\d+(\.\d+)?$/;
	
	if( !reg.test(value)  ){
		if( value.substr(0, value.length-1).indexOf('.')==-1  &&  value.substr(value.length-1, value.length)=='.'){
			return;
		}
		if(isNaN(parseFloat(value))){
			id.value="";
		}else {
			id.value=parseFloat(value);
		}
		return;
	}
}

/**
 * 获取html页面元素的顶部坐标
 * @param e
 * @return
 */
function getTop(e) {
	var offset = e.offsetTop;
	if (e.offsetParent != null) {
		offset += getTop(e.offsetParent);
	}
	return offset;
}
			
/**
 * 获取html页面元素的横坐标
 * @param e
 * @return
 */
function getLeft(e) {
	var offset = e.offsetLeft;
    if (e.offsetParent != null) {
	  offset += getLeft(e.offsetParent);
    }
    return offset;
}

/**
 * 按钮鼠标移上去变成手型
 * @param obj
 * @return
 */
function buttonMouseOver(obj) {
    obj.style.cursor = "hand";
    obj.className = "sag_trover";
}

function buttonMouseOut(obj,color) {
    //var elt = event.srcElement;
    obj.style.cursor = "default";
    obj.className =  color ;
}
var sagUtils=new sagacityUtils();