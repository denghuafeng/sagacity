var workBase = "";
var minimizebar = workBase + "/images/minimize.gif";   
var minimizebar2 = workBase + "/images/minimize2.gif"; 
var closebar = workBase + "/images/close.gif";         
var closebar2 = workBase + "/images/close2.gif";       
var icon = workBase + "/images/icon.gif";              

var IE6 = false;
var allMessage = "";
var msg_num = 0;
var oPopup;
if (msieversion() > 5) {
    oPopup = window.createPopup();
    IE6 = true;
}

function msieversion()
// return Microsoft Internet Explorer (major) version number, or 0 for others
// This function works by finding the "MSIE " string and extracting the version number
// following the space, up to the decimal point for the minor version which is ignored.
{
	var ua = window.navigator.userAgent
	var msie = ua.indexOf ( "MSIE " )
	if ( msie > 0 )		// is Microsoft Internet Explorer; return version number
		return parseInt ( ua.substring ( msie+5, ua.indexOf ( ".", msie ) ) )
	else
		return 0	// is other browser
}

function messageAdd(message) {
    msg_num++;
    //for popwin
    //allMessage += msg_num + ". " + message + "\n<br>";

	//for alert
	/*
	if (msg_num == 1) {
		allMessage = message;
		return;
	} else if (msg_num == 2) {
		allMessage = "1. " + allMessage + "\n";
	}
	allMessage += msg_num + ". " + message + "\n";
	*/
	allMessage += message + "\n";
}

function messageWrite() {
	//for popwin
	/*
    if (IE6) {
        noBorderWinForIE6(allMessage,'400','240','#000000','12px');
    } else {
        noBorderWin(allMessage,'400','240','#000000','#333333','#CCCCCC','消息');
    }
	*/
	if (msg_num > 0) {
		alert(allMessage);
	}
	//clear
	allMessage = "";
	msg_num = 0;	
}

function noBorderWin(message,w,h,titleBg,moveBg,titleColor,titleWord) {
    var contents = 
        "<html>\n" +
        "<head>\n" +
        "<title>" + titleWord + "</title>\n" +
        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n" +
		"<object id=hhctrl type='application/x-oleobject' classid='clsid:adb880a6-d8ff-11cf-9377-00aa003b7a11'><param name='Command' value='minimize'></object>";

    if (document.all) {//
        contents +=
            "<style type=text/css>\n" +
            "body,td,p  { font-size:12px; }\n" +
            "</style>\n" +
            "<script language=\"javascript\">\n" +
            "<!--\n" +
            "function clock() {\n" +
            "   i = i - 1;\n" +
            "   document.title=\"本窗口将在\"+i+\"秒后自动关闭!\";\n" +
            "   if (i > 0) setTimeout(\"clock();\",1000);\n" +
            "   else window.parent.close();\n" +
            "}\n" +
            "var i = 3\n" +
            "//clock();\n" +
            "//-->\n" +
            "</script>\n";
    } 
    
    contents +=
        "</head>\n" +
        "<body topmargin=0 leftmargin=0 scroll=no onselectstart='return false' ondragstart='return false'>\n" +
        "  <table height=100% width=100% cellpadding=0 cellspacing=1 bgcolor=" + titleBg + " id=mainTab>\n" +
        "    <tr height=\"18\" style=cursor:default; onmousedown='x=event.x;y=event.y;setCapture();mainTab.bgColor=\"" + moveBg + "\";' onmouseup='releaseCapture();mainTab.bgColor=\"" + titleBg + "\";' onmousemove='if(event.button==1)self.moveTo(screenLeft+event.x-x,screenTop+event.y-y);'>\n" + 
        "      <td width=18 align=center><img height=12 width=12 border=0 src=" + icon + "></td>\n" + 
        "      <td width=" + w + "><span style=font-size:12px;color:" + titleColor + ";font-family:宋体;position:relative;top:1px;>" + titleWord + "</span></td>\n" + 
        "      <td width=14><img border=0 width=12 height=12 alt=最小化 src=" + minimizebar + " onmousedown=hhctrl.Click(); onmouseover=this.src='" + minimizebar2 + "' onmouseout=this.src='" + minimizebar + "'></td>\n" + 
        "      <td width=13><img border=0 width=12 height=12 alt=关闭 src=" + closebar + " onmousedown=self.close(); onmouseover=this.src='" + closebar2 + "' onmouseout=this.src='" + closebar + "'></td>\n" + 
        "    </tr>\n" +
        "    <tr height=* bgcolor=#EEEEEE>\n" +
        "      <td colspan=4 align=\"center\">" + message + "\n" +
        "      </td>\n" +
        "    </tr>\n" +
        "  </table>\n" +
        "</body>\n" +
        "</html>";
    
    var pop = window.open("","_blank","fullscreen=yes");
    pop.resizeTo(w,h);
    pop.moveTo((screen.width - w) / 2,(screen.height - h) / 2);
    pop.document.write(contents);
    pop.document.close();
    pop.focus();
}

function noBorderWinForIE6(message,width,height,titleBg,fontsize) {
    var contents =
        "  <table height=100% width=100% cellpadding=0 cellspacing=1 bgcolor=" + titleBg + ">\n" +
        "    <tr height=* bgcolor=#EEEEEE>\n" +
        "      <td colspan=4 align=\"center\" style=\"font-size:" + fontsize + "\">" + message + "\n" +
        "      </td>\n" +
        "    </tr>\n" +
        "  </table>\n";
    
    var oPopBody = oPopup.document.body;
    oPopBody.innerHTML = contents;
    var yc = (document.body.clientHeight - height) / 2;
    var xc = (document.body.clientWidth - width) / 2;
    oPopup.show(xc, yc, width, height);

}