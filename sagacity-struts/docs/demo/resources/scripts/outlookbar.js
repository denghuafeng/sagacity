function showitem(id,name)
{
	return ("<span><a href='"+id+"' target="+outlookbar.target+"><img src='../resources/images/menu/lx.gif' border=0>&nbsp;"+name+"&nbsp;</a></span><br>");
}

function switchoutlookBar(number)
{
	var i = outlookbar.opentitle;
	outlookbar.opentitle=number;
	var id1,id2,id1b,id2b;
	if (number!=i && outlooksmoothstat==0){
		if (number!=-1)
		{
			if (i==-1){
				id2="blankdiv";
				id2b="blankdiv";
			}
			else{
				id2="outlookdiv"+i;
				id2b="outlookdivin"+i;
				document.all("outlooktitle"+i).style.border="0px none navy";
				document.all("outlooktitle"+i).style.background=outlookbar.maincolor;
				document.all("outlooktitle"+i).style.color="#000000";
				document.all("outlooktitle"+i).style.textalign="center";
			}
			id1="outlookdiv"+number
			id1b="outlookdivin"+number
			document.all("outlooktitle"+number).style.border="0px none white";
			document.all("outlooktitle"+number).style.background=outlookbar.maincolor; //title
			document.all("outlooktitle"+number).style.color="#000000";
			document.all("outlooktitle"+number).style.textalign="center";
			smoothout(id1,id2,id1b,id2b,0);
		}
		else
		{
			document.all("blankdiv").style.display="";
			document.all("blankdiv").sryle.height="100%";
			document.all("outlookdiv"+i).style.display="none";
			document.all("outlookdiv"+i).style.height="0%";
			document.all("outlooktitle"+i).style.border="0px none navy";
			document.all("outlooktitle"+i).style.background=outlookbar.maincolor;
			document.all("outlooktitle"+i).style.color="#000000";
			document.all("outlooktitle"+i).style.textalign="center";
		}
	}
}
function smoothout(id1,id2,id1b,id2b,stat)
{
	if(stat==0){
		tempinnertext1=document.all(id1b).innerHTML;
		tempinnertext2=document.all(id2b).innerHTML;
		document.all(id1b).innerHTML="";
		document.all(id2b).innerHTML="";
		outlooksmoothstat=1;
		document.all(id1b).style.overflow="hidden";
		document.all(id2b).style.overflow="hidden";
		document.all(id1).style.height="0%";
		document.all(id1).style.display="";
		setTimeout("smoothout('"+id1+"','"+id2+"','"+id1b+"','"+id2b+"',"+outlookbar.inc+")",outlookbar.timedalay);
	}
	else
	{
		stat+=outlookbar.inc;
		if (stat>100)
			stat=100;
		document.all(id1).style.height=stat+"%";
		document.all(id2).style.height=(100-stat)+"%";
		if (stat<100) 
			setTimeout("smoothout('"+id1+"','"+id2+"','"+id1b+"','"+id2b+"',"+stat+")",outlookbar.timedalay);
		else
		{
			document.all(id1b).innerHTML=tempinnertext1;
			document.all(id2b).innerHTML=tempinnertext2;
			outlooksmoothstat=0;
			document.all(id1b).style.overflowY="auto";
			document.all(id1b).style.overflowX="hidden";
			document.all(id2).style.display="none";
		}
	}
}
function getOutLine()
{
	outline="<table "+outlookbar.otherclass+">";
	for (i=0;i<(outlookbar.titlelist.length);i++)
	{
		outline+="<tr><td name=outlooktitle"+i+" id=outlooktitle"+i+" "; 
		if (i!=outlookbar.opentitle) 
			outline+=" nowrap align=center style='cursor:hand;padding-right:10px;background-color:"+outlookbar.maincolor+";color:#ff0000;height:20;border:0 none navy' ";
		else
			outline+=" nowrap align=center style='cursor:hand;padding-right:10px;background-color:"+outlookbar.maincolor+";color:black;height:20;border:0 none white' ";
		outline+=outlookbar.titlelist[i].otherclass
		outline+=" onclick='switchoutlookBar("+i+")'><span class=smallFont>";
		if(outlookbar.titlelist[i].icon!=null && outlookbar.titlelist[i].icon!="")
			outline+="<IMG height=26 src='"+outlookbar.titlelist[i].icon+"' width=164>";
		else
			outline+=outlookbar.titlelist[i].title;
		outline+="</span></td></tr>";
		outline+="<tr><td name=outlookdiv"+i+" valign=top align=left id=outlookdiv"+i+" style='width:100%;"
		if (i!=outlookbar.opentitle) 
			outline+=";display:none;height:0%;";
		else
			outline+=";display:;height:100%;";
		outline+="'><div name=outlookdivin"+i+" id=outlookdivin"+i+" style='overflow:hidden;width:95%;height:100%'>";
		for (j=0;j<outlookbar.itemlist[i].length;j++)
			outline+=showitem(outlookbar.itemlist[i][j].key,outlookbar.itemlist[i][j].title);
		outline+="</div></td></tr>"
	}
	outline+="</table>"
	return outline
}
function show()
{
	var outline;
	outline="<div id=outLookBarDiv name=outLookBarDiv style='width=100%;height:100%'>"
	outline+=outlookbar.getOutLine();
	outline+="</div>"
	document.write(outline);
}
function theitem(intitle,instate,inkey,icon)
{
	this.state=instate;
	this.otherclass=" nowrap ";
	this.key=inkey;
	this.title=intitle;
	this.icon=icon;
}
function addtitle(intitle,icon)
{
	outlookbar.itemlist[outlookbar.titlelist.length]=new Array();
	outlookbar.titlelist[outlookbar.titlelist.length]=new theitem(intitle,1,0,icon);
	return(outlookbar.titlelist.length-1);
}
function additem(intitle,parentid,inkey,icon)
{
	if (parentid>=0 && parentid<=outlookbar.titlelist.length)
	{
		outlookbar.itemlist[parentid][outlookbar.itemlist[parentid].length]=new theitem(intitle,2,inkey,icon);
		outlookbar.itemlist[parentid][outlookbar.itemlist[parentid].length-1].otherclass=" nowrap align=left style='height:5';";
		return(outlookbar.itemlist[parentid].length-1);
	}
	else
		additem=-1;
}

function outlook()
{
	this.titlelist=new Array();
	this.itemlist=new Array();
	this.divstyle="style='height:100%;width:100%;overflow:auto' align=right";
	this.otherclass="border=0 cellspacing='0' cellpadding='0' style='height:100%;width:100%'valign=middle align=center ";
	this.addtitle=addtitle;
	this.additem=additem;
	this.starttitle=-1;
	this.show=show;
	this.getOutLine=getOutLine;
	this.opentitle=this.starttitle;
	this.reflesh=outreflesh;
	this.timedelay=500;
	this.inc=10;
	this.maincolor = "#0000ff";
	this.target="mainFrame";
}

function outreflesh()
{
	document.all("outLookBarDiv").innerHTML=outlookbar.getOutLine();
}
function locatefold(foldname)
{
	if (foldname=="")
		foldname = outlookbar.titlelist[0].title;
	for (var i=0;i<outlookbar.titlelist.length;i++)
	{
		if(foldname==outlookbar.titlelist[i].title)
		{
			outlookbar.starttitle=i;
			outlookbar.opentitle=i;
		}
	}
}