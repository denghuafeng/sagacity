<script type="text/javascript">
	var messagesContents="";  
 	function alertOperateMessages(width,height)   
  	{   
  		messagesContents+="</ul>";
	  	var pop=showModalDialog("${contextPath}/resources/html/alertMessage.html",window,"dialogWidth:"+width+"px;dialogHeight:"+height+"px;status:no;help:no"); 
	  	pop.moveTo((screen.width - width) / 2,(screen.height - height) / 2);  
  		messagesContents="";
  	}   
	
	function addMessage(content)
	{
		if(messagesContents=="")
			messagesContents="<ul>";
		messagesContents+="<li>"+content+"</li>";
		
	}
</script>
<#if (actionMessages?exists && actionMessages?size > 0)>
	<script type="text/javascript">
		<#list actionMessages as message>
			addMessage("${message}");
		</#list>
		alertOperateMessages();
	</script>
</#if>