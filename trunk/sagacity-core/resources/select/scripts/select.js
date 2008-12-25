function onSltDropDown(sltObj,divId){	
	var showDiv=document.getElementById(divId); 
    showDiv.style.display ="";
    showDiv.style.top= getTop(sltObj)+sltObj.offsetHeight;
    showDiv.style.left= getLeft(sltObj);
    if(showDiv.offsetWidth<sltObj.offsetWidth)
     		showDiv.style.width=sltObj.offsetWidth;  
    showDiv.focus();
}

function dropDownSlt(objId, objValue, display) {
	document.getElementById("Name_" + objId).value = display;
	document.getElementById(objId).value = objValue;
}