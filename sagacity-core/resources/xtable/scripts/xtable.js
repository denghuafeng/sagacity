var trDefaultClass;

/**
 * xtable 行选中
 * 
 * @param row
 * @return
 */
function xtable_selected(row) {
	var tbody = row.parentNode;
	var image = "url("+ctxPath+"/resources/components/xtable/images/nextPage.gif)";
	for (i = 0; i < tbody.childNodes.length; i++) {
		tbody.childNodes[i].childNodes[0].style.backgroundImage = "";
	}
	row.childNodes[0].style.backgroundPosition = "left";
	row.childNodes[0].style.backgroundRepeat = "no-repeat";
	row.childNodes[0].style.backgroundImage = image;
}

/**
 * 
 * @param obj
 * @return
 */
function xtableTrMouseOver(obj) {
	// var elt = event.srcElement;
	obj.style.cursor = "hand";
	trDefaultClass = obj.className;
	obj.className = "sag_trover";
}

/**
 * 
 * @param obj
 * @return
 */
function xtableTrMouseOut(obj) {
	obj.style.cursor = "default";
	obj.className = trDefaultClass;
}

/**
 * common javascript function for abchina-framework
 * create by chenrenfei 2008.5.28
 */
function submitGoToPage() {
	document.forms[0].submit();
}

function gotoPage(nowPageNo, pageNoElement, totalPage, toPageNo) {
	if (toPageNo == nowPageNo) {
		return;
	}
	if(totalPage<=1)
		return;
	document.getElementsByName(pageNoElement)[0].value = toPageNo;
	var pageNo = document.getElementsByName(pageNoElement)[0].value;
	if (pageNo != -1) {
		if (isNaN(document.getElementsByName(pageNoElement)[0].value)) {
			alert("\u9875\u6570\u5fc5\u987b\u662f\u6570\u5b57\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165!" + document.getElementsByName(pageNoElement)[0].value);
			return false;
		} else {
			if (parseInt(pageNo) < 1) {
				alert("\u8d85\u51fa\u9875\u6570\u6700\u5c0f\u8303\u56f4\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165!");
				return;
			} else {
				if (parseInt(pageNo) > totalPage) {
					alert("\u8d85\u51fa\u9875\u6570\u6700\u5927\u8303\u56f4\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165!");
					return;
				}
			}
		}
	}
	submitGoToPage();
}

/**
 * 验证跳转页输入框的输入合法性 
 * @param id
 * @param pageNo
 * @param totalPage
 * @return
 */
function validateGotoPageNumber(id,pageNo,totalPage) {
	var value = id.value;
	var reg=/^[1-9][0-9]*$/;
	if( !reg.test(value) ){
		if(isNaN(parseInt(value))){
			id.value=pageNo;
		}else{
			if(parseInt(value)>totalPage)
				id.value=pageNo;
			else
				id.value=parseInt(value);
		}
		return;
	}
}