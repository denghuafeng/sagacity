//打开新窗口
function openWindow(theURL,winName,features) { 
  window.open(theURL,winName,features);
}

//在中心位置打开窗口,参数都为默认值，不显示任何菜单，按钮，状态等信息
function openCenterWindow(theURL,winName,widths,heights) { 
  window.open(theURL,winName,'width='+widths+',height='+heights+',left='+(window.screen.width-widths)/2+",top="+(window.screen.height-heights)/2);
}

//在中心位置打开窗口,可以传入打开参数
function openCenterWindow(theURL,winName,widths,heights,otherFeatures) { 
  window.open(theURL,winName,'width='+widths+',height='+heights+',left='+(window.screen.width-widths)/2+',top='+(window.screen.height-heights)/2+',screenX='+(window.screen.width-widths)/2+',screenY='+(window.screen.height-heights)/2+','+otherFeatures);
}

function selectList(selectName,selectedValue){
  for(var i=0;i<selectName.options.length;i++){
    if(selectName.options[i].value==selectedValue)
      selectName.options.selectedIndex = i;
  }
}
//获取对象的数目
function getObjectLength(checkBoxElement){
 if(checkBoxElement == null )
 	return 0;
 if(checkBoxElement.length == null){
	return 1;
  }else{
  	 return checkBoxElement.length;
  }
}

//获取多行对象的一行中某对象的值
function getOjbectValue(checkBoxElement,elementIndex){
 if(checkBoxElement.length == null){
	return checkBoxElement.value;
  }else{
  	 return checkBoxElement[elementIndex].value;
  }
}

//获取多行对象的一行中某对象的值
function isOjbectSelected(checkBoxElement,elementIndex){ 
 if(checkBoxElement == null)
  	return false;
  	
 if(checkBoxElement.length == null){
 	if(checkBoxElement.checked == true)
		return true;
	else 
		return false;
  }else{
  	 if(checkBoxElement[elementIndex].checked == true)
  	 	return true;
  	 else
  	 	return false;
  }
}

//全选
function selectAll(allCheckBox,singleCheckBox){
 var checkAll = false;
 if(allCheckBox.checked==true)
 	checkAll = true;
 if(singleCheckBox !=null){
 if(singleCheckBox.length == null){
	singleCheckBox.checked = checkAll;
  }else{
  	  var num = -1;
  	  for(var i = 0;i < singleCheckBox.length;i++){
  		singleCheckBox[i].checked = checkAll;
  	  }
  }
}
}

//多选框是否选中
function isNotSelected(checkBoxName){
 if(checkBoxName.length == null){
		 if(checkBoxName.checked==false){
       return true;     
  	 }
  }else{
  	  var num = -1;
  		for(var i = 0;i < checkBoxName.length;i++){
  			if(checkBoxName[i].checked){
  				num = num + 1;
        }
  	  }
  	  if(num==-1){
        return true; 
  	  }
  }
  return false;
}

//是否为空
function isNullOrBlank(inputName){
  if(inputName==null || inputName=="" || inputName=="null")
   return true;
  else
    return false;
}

//是否数值
function isNumber ( js_value )
{
	var	re ;
	re = /^\s*$/ ;

	if ( js_value.match(re) )
	{
		return true ;
	}
	if ( isNaN(js_value) )
	{
		return false ;
	}
	return true ;
}
//数组中的值是否都为数值型
function isNotNumber(arrayName){
  for(var i=0;i<arrayName.length;i++){
    if(isNaN(arrayName[i].value)){
      alert("请按正确的数据格式输入!");
      arrayName[i].focus();
      return false;
    }
  }
}

function formatInputValue(sourceValue){
	if (sourceValue == null || sourceValue == "" || sourceValue == "undefined")	
	return " ";
else
	return sourceValue;
	
}


function isDate(dateStr)
{
	if (dateStr == null || dateStr == "")	return false;
	if (isNaN(dateStr))	return false;
	if (dateStr.length != 8) return false;
	var yearStr = dateStr.substring(0,4);
	var monthStr = dateStr.substring(4,6);
	var dayStr = dateStr.substring(6);
	var	month_val, year_val, day_val ;

	year_val	= parseInt (yearStr, 10);
	month_val	= parseInt (monthStr, 10);
	day_val		= parseInt (dayStr, 10);
		
	if ( year_val<1900 || year_val>3000 ) return false ;
	if ( month_val<1 || month_val>12 ) return false ;
	switch ( month_val )
	{
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if(day_val<1 || day_val>31) return false ;
			break ;
		case 4:
		case 6:
		case 9:
		case 11:
			if(day_val<1 || day_val>30) return false ;
			break ;
		case 2 :
			if( (year_val%4==0 && year_val%100!=0) || year_val%400==0 ) 
			{
				if (day_val<1 || day_val>29 ) return false ;
			}
			else
			{
				if (day_val<1 || day_val>28 ) return false ;
			}
			break ;
		default :
	}
		return true;
}
function	check_number ( field, len1, len2 )
{
	var	re	= /^[0-9]{1,}\.{0,1}[0-9]{0,}0*$/;
	var js_value = field.value;
	if ( js_value.length == 0)
	{
		return true ;
	}
	if ( js_value.match(re) )
	{
		var dot_position = js_value.indexOf('.');
		var int_value, dec_value;
		if (dot_position >= 0)
		{
			int_value = js_value.substring(0,dot_position);
			dec_value = js_value.substring(dot_position + 1);
		}
		else
		{
			int_value = js_value;
			dec_value = "";
		}
		if (int_value.length > len1)
		{
			alert("The integer digits are too long! It should be " + len1 + "digits");
			field.value = "";
			field.focus();
			return false;
		}
		if (dec_value.length > len2)
		{
			alert("The decimal digits are too long! It should be " + len2 + "digits");
			field.value = "";
			field.focus();
			return false;
		}
		return true ;
	}
	alert("Please input a number!");
	field.value = "";
	field.focus();
	return false;
}

//判断输入值是否不为空
function isNotNull( js_obj )
{
	var	re, i;
	re = /^\s*$/ ;

  	if ( js_obj.length )
  	{
		if ( js_obj[0].type == "checkbox" )
		{
			for ( i=0; i<js_obj.length; i++ )
				if (js_obj[i].checked == true)
					return true ;
			return false ;
		}
		else if ( js_obj[0].type == "radio" )
		{
			for ( i=0; i<js_obj.length; i++ )
				if (js_obj[i].checked == true)
					return true ;
			return false ;
		}
  	}
 	else
  	{
		if ( js_obj.type == "select-multiple" )
		{
			if ( js_obj.selectedIndex < 0 )
				return false ;
		}
		else if ( js_obj.type == "select-one" )
		{
			if ( js_obj.selectedIndex < 0 )
				return false ;
		}
		else if ( js_obj.type == "text" )
		{
			if ( js_obj.value.match(re) )
				return false ;
		}
		else if ( js_obj.type == "checkbox" ) 
		{
			if ( js_obj.checked == false )
				return false ;
		}
		else if ( js_obj.type == "radio" )
		{
			if ( js_obj.checked == false )
				return false ;
		}
		else
			if (js_obj.value.match (re))
				return false ;
  	}
	return true ;
}

function	is_number ( js_value )
{
	var	re ;
	re = /^\s*$/ ;

	if ( js_value.match(re) )
	{
		return true ;
	}
	if ( isNaN(js_value) )
	{
		return false ;
	}
	return true ;
}

function	is_integer ( js_value )
{
	var	re ;
	re = /^\s*$/ ;

	if ( js_value.match(re) )
	{
		return true ;
	}
	if ( isNaN(js_value) || js_value.indexOf('.',0) >= 0 )	
	{
		return false ;
	}
	return true ;
}

function	is_phone (js_value)
{
	var	re = /^[0-9\*\-( )]*$/;

	if (js_value.match (re))
		return	true;
	return	false;
}

function	is_natural ( js_value )
{
	var	re ;
	re = /^\s*$/ ;

	if ( js_value.match(re) )
	{
		return true ;
	}

	re = /^\+{0,1}[0-9]*$/ ;
	if ( !js_value.match(re) ) return false ;
	return true ;
}

function	is_zip ( js_value )
{
	var	re ;
	re = /^\s*$/ ;

	if ( js_value.match(re) )
	{
		return true ;
	}
	if ( !is_natural(js_value) || js_value.length!=6 )
	{
		return false ;
	}
	return true ;
}

function	is_id_card ( js_value )
{
	var	re ;
	re = /^\s*$/ ;

	if ( js_value.match(re) )
	{
		return true ;
	}
	if ( !is_natural(js_value) || (js_value.length!=15&&js_value.length!=18) )
	{
		return false ;
	}
	return true ;
}

function	is_date ( y_val, m_val, d_val, h_val, mi_val, s_val )
{	
	var	month_val, year_val, day_val ;
	var	hour_val, minute_val, second_val;
	var	re ;
	re = /^\s*$/ ;
	
	if ( y_val.match(re) && m_val.match(re) && d_val.match(re)
		&& h_val.match (re) && mi_val.match (re) && s_val.match (re))
	{
		return true ;
	}

	if ( !is_natural(y_val) || !is_natural(m_val) || !is_natural(d_val) ) return false ;
	if (!is_natural (h_val) || !is_natural (mi_val) || !is_natural (s_val))	return	false;
	year_val	= parseInt (y_val, 10);
	month_val	= parseInt (m_val, 10);
	day_val		= parseInt (d_val, 10);
	hour_val	= parseInt (h_val, 10);
	minute_val	= parseInt (mi_val, 10);
	second_val	= parseInt (s_val, 10);
	
	if ( isNaN(year_val)  || isNaN(month_val) || isNaN(day_val)) return false;	
	
	if ( year_val<1900 || year_val>3000 ) return false ;
	if ( month_val<1 || month_val>12 ) return false ;
	switch ( month_val )
	{
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if(day_val<1 || day_val>31) return false ;
			break ;
		case 4:
		case 6:
		case 9:
		case 11:
			if(day_val<1 || day_val>30) return false ;
			break ;
		case 2 :
			if( (year_val%4==0 && year_val%100!=0) || year_val%400==0 ) 
			{
				if (day_val<1 || day_val>29 ) return false ;
			}
			else
			{
				if (day_val<1 || day_val>28 ) return false ;
			}
			break ;
		default :

	}
	if (hour_val < 0 || hour_val > 23)	return	false;
	if (minute_val < 0 || minute_val > 59)	return	false;
	if (second_val < 0 || second_val > 59)	return	false;

	return true ;
}

function	is_age ( js_value )
{
	var	re ;
	re = /^\s*$/ ;

	if ( js_value.match(re) )
	{
		return true ;
	}
	if ( ! is_natural(js_value) ) return false ;
	if ( parseInt(js_value, 10)<=0 || parseInt(js_value, 10)>=200 )
	{
		return false ;
	}
	return true ;
}

function	is_price ( js_value )
{
	var	re	= /^\s*$/ ;
	var	re1	= /^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/;

	if ( js_value.match(re) )
	{
		return true ;
	}
	if (js_value.match (re1))
		return	true;
	return false;
}

function	is_double ( js_value )
{
	var	re	= /^\s*$/ ;
	var	re1	= /^[0-9]{1,}\.{0,1}[0-9]{0,4}0*$/;

	if ( js_value.match(re) )
	{
		return true ;
	}
	if (js_value.match (re1))
		return	true;
	return false;
}

function	is_email ( js_value )
{
	var	pos ;
	var	re ;
	re = /^\s*$/ ;

	if ( js_value.match(re) )
	{
		return true ;
	}

	pos = js_value.indexOf( '@',0 ) ;
	if ( js_value.length <= 5 ) return false ;
	if ( pos==-1 || pos==0 || pos==(js_value.length-1) ) return false ;

	pos = js_value.indexOf( '.',0 ) ;
	if ( pos<=0 || pos==(js_value.length-1) ) return false ;

	return true ;
}

function	is_gendre ( js_value )
{
	return true ;
	var	re ;
	re = /^\s*$/ ;

	if ( js_value.match(re) )
	{
		return true ;
	}
	if ( js_value!='N' && js_value!='M' && js_value!='F' ) return false ;
	return true ;
}

function	is_url ( js_value )
{
	var pos, posdot ;
	var	re ;
	re = /^\s*$/ ;

	if ( js_value.match(re) )
	{
		return true ;
	}

	pos = js_value.indexOf('://',0) ;
	if ( pos<0 ) return false ;
	posdot = js_value.lastIndexOf('.') ;
	if ( posdot<pos ) return false ;
	if ( posdot == js_value.length-1 ) return false ;
	return true ;
}

function	is_equal (js_value, confm)
{
	if (js_value != confm.value)	return	false;
	return	true;
}

function	is_date_period ( s_y_val, s_m_val, s_d_val, s_h_val, s_mi_val, s_s_val, 
				 e_y_val, e_m_val, e_d_val, e_h_val, e_mi_val, e_s_val )
{	
	
	if ( !( is_date(s_y_val, s_m_val, s_d_val, s_h_val, s_mi_val, s_s_val) &&
	       is_date(e_y_val, e_m_val, e_d_val, e_h_val, e_mi_val, e_s_val)))
	       return false;
	       
	if ( parseInt( s_y_val, 10) > parseInt( e_y_val, 10))
	 	{	
	       return false; 
	       }
	       
	else if (parseInt( s_y_val, 10) == parseInt( e_y_val, 10))
			if (parseInt( s_m_val, 10) > parseInt( e_m_val, 10))
			{
			  	return false; 
			} 	
			
	     else if (parseInt( s_m_val, 10) == parseInt( e_m_val, 10))
			 
			if (parseInt( s_d_val, 10) > parseInt( e_d_val, 10))
			{	
		  		    return false;
		  	}	    
			 else if (parseInt( s_d_val, 10) == parseInt( e_d_val, 10))
				 
				if (parseInt( s_h_val, 10) > parseInt( e_h_val, 10))
				{	
						return false;
				}	    
				 else if (parseInt( s_h_val, 10) == parseInt( e_h_val, 10))
					 
					if (parseInt( s_mi_val, 10) > parseInt( e_mi_val, 10))
					{	
							return false;
					}	    
					 else if (parseInt( s_mi_val, 10) == parseInt( e_mi_val, 10))
						 
						if (parseInt( s_s_val, 10) > parseInt( e_s_val, 10))
						{	
								return false;
						}	    
		  	
	return true;	  	
				
}				   

function is_password ( js_value )				
{
	var pass_length, i;
	var parsed_char;
		
	pass_length = js_value.length ;
	
	//	check length first
	
	if ( pass_length < 4 || pass_length > 30)
		return false;
	
	
	//	check characters should be alphabet or numeric or under_score
	
	
	
	for ( i = 0; i < pass_length; i ++)
	{
		parsed_char = js_value.toUpperCase().charAt(i);
		
		if ( parsed_char >= 'A' && parsed_char <= 'Z' )
			continue;
		
		if ( parsed_char >= '0' && parsed_char <= '9' )
			continue;
		
//		if ( parsed_char == '_' )			
//			continue;
			
		if ( parsed_char == '-' )
			continue;	
				
			return false;
		
	}
			
	return true;			
}		

function is_loginname ( js_value )				
{
	var login_length, i;
	var parsed_char;
		
	login_length = js_value.length ;
	
	//	check length first
	
	if ( login_length < 4 || login_length > 30)
		return false;
	
	
	//	check characters should be alphabet or numeric or under_score
	
	
	for ( i = 0; i < login_length; i ++)
	{
		parsed_char = js_value.toUpperCase().charAt(i);

		if ( parsed_char >= 'A' && parsed_char <= 'Z' )
			continue;
		
		if ( parsed_char >= '0' && parsed_char <= '9' )
			continue;
		
//		if ( parsed_char == '_' )
//			continue;
			
		if ( parsed_char == '-' )
			continue;	
				
	return false;		
	}
			
	return true;			
}

function	select_all ( js_obj )
{
	var	i;

	if ( typeof(js_obj) == "object")
	{	
		for ( i=0; i<js_obj.length; i++ )
			 js_obj[i].checked = true;
	}	
	return true;
}	

function	cancel_all ( js_obj )
{
	var	i;
	
	if ( typeof(js_obj) == "object")
	{	
		for ( i=0; i<js_obj.length; i++ )
			 js_obj[i].checked = false;
	}		 
		
	return true;
}	

function window_open (url, win_name, win_prop)
{
	tmp	= window.open (url, win_name, win_prop);
	tmp.opener	= window;
	tmp.focus ();
}


function changeorder (obj) {
	document.all.order1.style.display="none";
	document.all.order2.style.display="none";
	if (obj.select3.value == 1) {
		document.all.order1.style.display="";
	} else if (obj.select3.value == 2) {
		document.all.order2.style.display="";
	};
}
function turnon(obj1){
obj1.style.background="#F1E9D6";
}
function turnoff(obj1){
obj1.style.background="#F8F3E9";
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v3.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


var click_no

function Config_Submit_Page ( p_cmd , inform )
{
	if ( click_no == "2" )
	{
		alert("已经按过一次了!");
	}
	else
	{
		inform.cmd.value = p_cmd ;
		if ( check_valid(inform))
		{
			click_no = "2";
			inform.submit();
		}
	}
}

function open_help(url)
{
	window.open(url,'help','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=190,width=350');
}

//用于获取地区信息
var selectedProvinceId="";
var selectedAreaId="";
function setProvince(){
var provinceId = document.getElementById("provinceId");
provinceId[provinceId.length] = new Option("请选择","");
for(i=0;i<provinces.length;i++){
  var option = new Option(provinces[i][1],provinces[i][0]);
  provinceId[provinceId.length]=option;
  if(selectedProvinceId==provinces[i][0]){
  option.selected=true;
  } 
    
 }
}
function setArea(){
  var areaId = document.getElementById("areaId");
  //alert(areaId.length);
  while(areaId.length>0){
  areaId.remove(areaId.length-1);
  }
  areaId[areaId.length] = new Option("请选择","");
  for(i=0;i<provinces.length;i++){
  if(provinces[i][0]==selectedProvinceId){
    for(j=2;j<provinces[i].length;j=j+2){
      var option = new Option(provinces[i][j+1],provinces[i][j]);
      areaId[areaId.length]=option;
        if(selectedAreaId==provinces[i][j]){
            option.selected=true;
         }       
      }
    }
  }
}
function chooseProvince(){
var provinceId = document.getElementById("provinceId");
selectedProvinceId=provinceId.value;
setArea();
}

//删除字符串中的空格
function trim(x){
	while((x.length>0) && (x.charAt(0)==' '))
		x = x.substring(1,x.length);
	while((x.length>0) && (x.charAt(x.length-1)==' '))
		x = x.substring(0,x.length-1);
	return x;
}


