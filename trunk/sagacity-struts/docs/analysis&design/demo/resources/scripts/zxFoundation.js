
<!--################### Copyright 2005. create by chenrenfei ########### --->
///set Form for struts dispatch Method 
function confirmDispatch(method) {
	var form = document.forms[0];
	form.method.value = method;
	if (confirm("\u64cd\u4f5c\u63d0\u793a!\u60a8\u662f\u5426\u786e\u8ba4\u60a8\u7684\u64cd\u4f5c?")) {
		form.submit();
	} else {
		return false;
	}
	return false;
}
function goBackConfirm(condition) {
	if (eval(condition)) {
		return confirm("\u64cd\u4f5c\u63d0\u793a!\u60a8\u662f\u5426\u8981\u653e\u5f03\u5f53\u524d\u7684\u64cd\u4f5c?");
	} else {
		return true;
	}
}
///number data check
function onkeyNumberFilter(obj) {
	if (obj.value.indexOf(".") != -1 && event.keyCode == 190) {
		event.returnValue = false;
		return false;
	}
	if (event.keyCode == 190 || event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || (event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105)) {
		event.returnValue = true;
		return true;
	} else {
		event.returnValue = false;
		return false;
	}
}
function onkeyIntFilter(obj) {
	if (event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || (event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105)) {
		event.returnValue = true;
		///if(trim(obj.value)=="")
			///obj.value="0";
	} else {
		event.returnValue = false;
	}
}
function validateNumber(obj) {
	if (isNaN(String(obj.value))) {
		if (String(obj.value).length > 0) {
			obj.value = obj.value.substr(0, String(obj.value).length - 1);
		}
		obj.focus();
	}
}
function validateInt(obj) {
	if (isNaN(String(obj.value))) {
		if (String(obj.value).length > 0) {
			obj.value = obj.value.substr(0, String(obj.value).length - 1);
		}
		obj.focus();
	}
}
function allOrNoneChoose(target, self) {
	if (target.length > 0) {
		for (i = 0; i < target.length; i++) {
			target[i].checked = self.checked;
		}
	} else {
		target.checked = self.checked;
	}
}
function submitConfirm() {
	return confirm("\xe5\x8f\x8b\xe6\x83\x85\xe6\x8f\x90\xe7\xa4\xba,\xe6\x98\xaf\xe5\x90\xa6\xe7\xa1\xae\xe8\xae\xa4\xe4\xbd\xa0\xe7\x9a\x84\xe6\x93\x8d\xe4\xbd\x9c?");
}
function dispatch(todo) {
	var frm = document.forms[0];
	frm.method.value = todo;
	frm.submit();
}
function validateDispatch(validate, todo) {
	var frm = document.forms[0];
	if (validate) {
		frm.method.value = todo;
		frm.submit();
	} else {
		return false;
	}
}
function update(id, value, method) {
	var frm = document.forms[0];
	frm.method.value = method;
	var target = document.getElementById(id);
	if (target == null) {
		target = document.getElementsByName(id);
	}
	target.value = value;
	frm.submit();
}
function validateUpdate(validate, id, value, method) {
	var frm = document.forms[0];
	if (validate) {
		frm.method.value = method;
		var target = document.getElementById(id);
		if (target == null) {
			target = document.getElementsByName(id);
		}
		target.value = value;
		//alert(target.value);
		frm.submit();
	} else {
		return false;
	}
}
function onchangeDispatch(target, value) {
	var selObj = target.options[target.selectedIndex].value;
	if (selObj == null || trim(selObj) == "" || trim(selObj) == "-1") {
		return false;
	}
	var frm = document.forms[0];
	frm.method.value = value;
	frm.submit();
}
			
			//全选
function checkAll(objName, obj) {
	var form = document.forms[0];
	for (var i = 0; i < form.elements.length; i++) {
		if (form.elements[i].name == objName && !form.elements[i].disabled) {
			form.elements[i].checked = obj.checked;
		}
	}
}

	  		//行取消选择时去除全选
function notCheckedAll(obj, checkedObj) {
	if (checkedObj.checked == false) {
		obj.checked = checkedObj.checked;
	}
}	
	  	//跳转到某个Action的某个方法，可传递一个参数
function transferAction(action, method, name, value) {
	location.href = action + ".do?method=" + method + "&" + name + "=" + value;
}
function goto(url) {
	location.href = url;
}
function getChkBoxChkCount(checkbox) {
	var count = 0;
	var checkboxTarget = document.getElementsByName(checkbox);
	for (i = 0; i < checkboxTarget.length; i++) {
		if (checkboxTarget[i].checked) {
			count++;
		}
	}
	return count;
}

