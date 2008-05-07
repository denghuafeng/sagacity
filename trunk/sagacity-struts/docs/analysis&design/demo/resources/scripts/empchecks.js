function checkit(em)
{
var bgcheck="#EDDDF3";
 	
if (em.checked==true)
{
while (em.tagName!="TR")
{em=em.parentNode;}
em.style.backgroundColor=bgcheck;
}
else
{
while (em.tagName!="TR")
{em=em.parentNode;}
em.style.backgroundColor="";

}
}

function allselect(em,name)
{
var form = em.form;
var bgcheck="#F8CAA9";
var bguncheck1="wsd_trover";
var bguncheck2="wsd_trover";
var boxes;
var  checkable= true ;
if (form.allcheck.checked==true)
	  for (var loop = 0;loop < form.elements.length;loop++) 
	    {
	    	if (form.elements[loop].name == name) 
	        {
	        form.elements[loop].checked = true ; 
	        boxes=form.elements[loop];
	        while (boxes.tagName!="TR")
                {boxes=boxes.parentNode;}
                
                boxes.style.backgroundColor=bgcheck;
	        }	
	    
	    }
	 
else
          for (var loop = 0;loop < form.elements.length;loop++) 	{  
          	if(checkable) {checkable = false ; bguncheck = bguncheck1;}
	 			else { checkable = true ; bguncheck =bguncheck2;}
	 		if (form.elements[loop].name == name)  {
	        form.elements[loop].checked = false ;
	        boxes=form.elements[loop];
	        while (boxes.tagName!="TR")
                {boxes=boxes.parentNode;}
                boxes.style.backgroundColor="";
                boxes.classNamer=bguncheck;
	        }
	 }


}

function buttonMouseOver(obj) {
        //var elt = event.srcElement;
        obj.style.cursor = "hand";
        obj.className = "wsd_trover";
}

function buttonMouseOut(obj,color) {
        //var elt = event.srcElement;
        obj.style.cursor = "default";
        obj.className =  color ;
}

function spanMouseOver(obj) {
        //var elt = event.srcElement;
        obj.style.cursor = "hand";
        obj.className = "input2";
}

function spanMouseOut(obj) {
        //var elt = event.srcElement;
        obj.style.cursor = "default";
        obj.className = "wor";
}