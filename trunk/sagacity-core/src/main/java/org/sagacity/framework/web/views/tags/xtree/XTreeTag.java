/**
 * 
 */
package org.sagacity.framework.web.views.tags.xtree;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.sagacity.framework.utils.StringUtil;
import org.sagacity.framework.web.views.tags.BaseTagSupport;
import org.sagacity.framework.web.views.tags.TagUtil;
import org.sagacity.framework.web.views.tags.xtable.util.XTableUtil;

/**
 * <abc:tree styleId="orgStaff" property="" datasource="" dyn=""
 * 
 * @project abchina
 * @description:$
 *          <p>
 *          请在此说明此文件的功能
 *          </p>$
 * @author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 * @version $id:XTreeTag.java,Revision:v1.0,Date:May 24, 2008 10:05:07 AM $
 */
public class XTreeTag extends BaseTagSupport {

	/**
	 * 树id
	 */
	private String treeId = "tree";

	/**
	 * 树标题
	 */
	private String treeName = "菜单";

	/**
	 * 是否是checkbox
	 */
	private String checkbox = "false";

	/**
	 * div宽度
	 */
	private String width = "200";

	/**
	 * div高度
	 */
	private String height = "600";

	/**
	 * 根节点id
	 */
	private String rootId = "0";

	/**
	 * 根节点标题
	 */
	private String rootName = "菜单";

	/**
	 * 根节点url
	 */
	private String rootUrl = "";

	/**
	 * 
	 */
	private String rootTarget = "";

	/**
	 * 动态树
	 */
	private String dynamical = "false";

	/**
	 * 调用后台service方法
	 */
	private String call = "";

	/**
	 * 动态树调用方法
	 */
	private String dataService = "";

	/**
	 * 树的层是否隐藏
	 */
	private String hidden = "";

	/**
	 * 节点id
	 */
	private String id;

	/**
	 * 父节点id
	 */
	private String pid;

	/**
	 * 是否创建层
	 */
	private String hasDiv="true";

	/**
	 * 节点显示名称
	 */
	private String nodeName;

	/**
	 * 节点提示
	 */
	private String tooltip = "";

	/**
	 * 节点开启
	 */
	private String isOpen = "false";

	/**
	 * 节点有子节点
	 */
	private String hasChild = "false";

	/**
	 * 节点url
	 */
	private String nodeUrl = "''";

	/**
	 * 节点调用url目标
	 */
	private String target = "";

	/**
	 * 动态树点击节点方法
	 */
	private String method = "doOpen";
	
	/**
	 * 多树标记 多树时设置true 默认false
	 */
	private String multiTree = "false" ;
	
	/**
	 * 多树标记 树id标志 多树时设置  ['','']
	 */
	private String treeIdList = "" ;
	
	/**
	 * 多树时设置 methodList = "a!b"
	 */
	private String methodList = "";
	
	/**
	 * 多树时设置 paramsList = "a!b"
	 */
	private String paramsList = "id,a!id,a";
	
	/**
	 * 设置treeO方法 false 不写
	 */
	private String treeOpen = "true";

	/**
	 * 动态树点击节点传递参数
	 */
	private String params = "id,a";

	/**
	 * 图片路径
	 */
	private String img_path = "resources/";

	/**
	 * 节点关闭图片
	 */
	private String icon = "''";

	/**
	 * 节点打开图片
	 */
	private String iconOpen = "''";

	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName
	 *            the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return the checkbox
	 */
	public String getCheckbox() {
		return checkbox;
	}

	/**
	 * @param checkbox
	 *            the checkbox to set
	 */
	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	/**
	 * @return the dynamical
	 */
	public String getDynamical() {
		return dynamical;
	}

	/**
	 * @param dynamical
	 *            the dynamical to set
	 */
	public void setDynamical(String dynamical) {
		this.dynamical = dynamical;
	}

	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param style
	 *            the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsOpne() {
		return isOpen;
	}

	public void setIsOpne(String isOpne) {
		this.isOpen = isOpne;
	}

	public String getNodeUrl() {
		return nodeUrl;
	}

	public void setNodeUrl(String nodeUrl) {
		this.nodeUrl = nodeUrl;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public String getRootUrl() {
		return rootUrl;
	}

	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getTreeName() {
		return treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public String getDataService() {
		return dataService;
	}

	public void setDataService(String dataService) {
		this.dataService = dataService;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public int doStartTag() throws JspException {
		if (this.isExportData())
		{
			this.release();
			return SKIP_BODY;
		}
		
		try {

			// 非动态树获取数据源
			Object dataSource = this.getDataResource();
			HashMap paramHash = TagUtil.getInstance().parseParams(this.id + this.pid
					+ this.nodeName + this.nodeUrl + this.isOpen + this);
			if (this.img_path.indexOf("/") != 0)
				this.img_path = "/" + this.img_path;
			if (this.img_path.lastIndexOf("/") != this.img_path.length()-1)
				this.img_path = this.img_path + "/";
			
			this.img_path = ((HttpServletRequest) this.pageContext.getRequest())
					.getContextPath()
					+ this.img_path;
			/**
			 * 0 id 1 pid 2 name 3 url 4 isOpen
			 */
			List nodeList = TagUtil.getInstance().reflactList(dataSource, TagUtil.getInstance()
					.parseParamHash(paramHash), false, 1);
			/* 创建树头 */
			this.printTreeRoot(this.pageContext.getOut());
			if (this.dynamical.equals("false")) {
				this.printTree(this.pageContext.getOut(), nodeList, paramHash);
			} else {
				this.printDynTree(this.pageContext.getOut(), nodeList,
						paramHash);
			}
			/* 创建树尾 */
			this.printTreeEnd(this.pageContext.getOut());
			return this.SKIP_BODY;
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			this.releaseSource();
		}
		return this.SKIP_BODY;
	}

	/**
	 * @todo release Memery Source
	 */
	public void release() {
		this.releaseSource();
	}

	/**
	 * 创建树头部
	 */
	private void printTreeRoot(JspWriter writer) throws IOException {
		if (this.hasDiv.equalsIgnoreCase("true")) {
			/* 创建层 */
			writer
					.println("<div id=\""
							+ this.treeId
							+ "\" style=\"overflow:auto;width:"
							+ this.width
							+ ";height:"
							+ this.height
							+ ";"
							+ (StringUtil.isNotNullAndBlank(this.hidden) ? "display:none;"
									: "") + "\">");
			writer.println("</div>");
		}
		/* 创建树 */
		writer.println("<script language=\"JavaScript\">");
		String type = "nomal";
		if (this.checkbox.equals("true"))
			type = "checkbox";
		writer.println("var " + this.treeId + " = new dTree('" + this.treeId
				+ "','" + type + "','" + this.img_path + "');");
		writer.println(this.treeId + ".add(" + this.rootId + ",-1,'"
				+ this.rootName + "','" + this.rootUrl + "','" + this.rootName
				+ "','" + this.rootTarget + "')");
	}

	/**
	 * 创建树结尾
	 */
	private void printTreeEnd(JspWriter writer) throws IOException {
		writer.println("</script>");
	}

	/**
	 * 创建静态树
	 */
	private void printTree(JspWriter writer, List nodeList, HashMap paramHash)
			throws IOException {
		/* 创建节点 */
		node(writer, nodeList, paramHash);
		writer.println("document.getElementById('" + this.treeId
				+ "').innerHTML=" + this.treeId + ";");
	}

	/**
	 * 静态树节点
	 */
	private void node(JspWriter writer, List nodeList, HashMap paramHash)
			throws IOException {
		if (null != nodeList && 0 < nodeList.size()) {
			List item;
			String id, pid, name, url, hasChild, isOpen;
			for (int i = 0; i < nodeList.size(); i++) {
				item = (List) nodeList.get(i);
				id = XTableUtil.replaceHoler(this.id, paramHash, item);
				pid = XTableUtil.replaceHoler(this.pid, paramHash, item);
				name = XTableUtil.replaceHoler(this.nodeName, paramHash, item);
				url = XTableUtil.replaceHoler(this.nodeUrl, paramHash, item);
				isOpen = XTableUtil.replaceHoler(this.isOpen, paramHash, item);
				hasChild = XTableUtil.replaceHoler(this.hasChild, paramHash,
						item);
				writer.println(this.treeId + ".add('" + id + "','" + pid
						+ "','" + name + "','" + url + "','" + name + "','"
						+ this.target + "','',''," + isOpen + "," + hasChild
						+ ");");
			}
		}
	}

	/**
	 * 创建动态树
	 */
	private void printDynTree(JspWriter writer, List nodeList, HashMap paramHash)
			throws IOException {
		/* ajax方法 */
		writer.println("	function " + this.method + "()			");
		writer.println("	{						");
		writer.println("	var args = new Array();				");
		writer.println("	for(var i=0;i<arguments.length;i++)		");
		writer.println("	{						");
		writer.println("		args[i] = arguments[i];			");
		writer.println("	}						");
		writer.println("	var argParam1=null;				");
		writer.println("	var argParam2=null;				");
		writer.println("	if(arguments.length>=3)				");
		writer.println("		argParam1=args[2];			");
		writer.println("	if(arguments.length>=4)				");
		writer.println("		argParam2=args[3];			");
		writer.println("	switchToTheId = args[0];			");
		writer.println("	" + this.call + "('" + this.dataService
				+ "',args[0], args[1], argParam1,argParam2,function(result)");
		writer.println("	{												");
		writer.println("		try{											");
		writer.println("		for(var i=0;i<result.length;i++)							");
		writer.println("		{											");
		/* 创建节点 */
		writer.println(this.treeId + ".add(" + this.id + "," + this.pid + ","
				+ this.nodeName + "," + this.nodeUrl + "," + this.nodeName
				+ ",'" + this.target + "'," + this.icon + "," + this.iconOpen
				+ "," + this.isOpen + "," + this.hasChild + ");");
		writer.println("		}											");
		writer.println("		document.getElementById('" + this.treeId
				+ "').innerHTML=" + this.treeId + ";		");
		writer.println(this.treeId+"_fun();  ");
		writer.println("		}catch(e)										");
		writer.println("		{											");
		writer.println("		}											");
		writer.println("	}												");
		writer.println("	);												");
		writer.println("	}												");
		/* 创建节点触发方法 */
		if(treeOpen.equals("true"))
    		{
		    	writer.println("dTree.prototype.o = function(id) {									");
    			writer.println("	var cn = this.aNodes.get(id);									");
        		writer.println("	try{												");
        		writer.println("	if(!cn._io)											");
        		writer.println("	{												");
        		writer.println("		var n = cn;										");
        		writer.println("		var a = new Array();									");
        		writer.println("		var i=0;");
        		writer.println("		while(n.pid != -1)									");
        		writer.println("		{											");
        		writer.println("			a[i++] = n.id;									");
        		writer.println("			n = this.aNodes.get(n.pid); 							");
        		writer.println("		}											");
        		/**
        		 * 单树
        		 */
        		if(this.multiTree.equals("false"))
        		{
        		    writer.println(this.method + "(" + this.params + ");								");
        		}
        		else
        		{
        		    /*调用方法数组*/
        		    String [] methodListArray = methodList.split("!");
        		    /*调用参数数组*/
        		    String [] paramsListArray = paramsList.split("!");
        		    writer.println("var treeIdList = "+this.treeIdList+";							");
        		    writer.println("for(var i=0;i<treeIdList.length;i++)							");
        		    writer.println("{												");
        		    for(int i=0;i<methodListArray.length;i++)
        		    {
        			writer.println("	if(this.obj==treeIdList["+i+"])							");
        			writer.println("	{										");
        			writer.println(methodListArray[i]+"("+paramsListArray[i]+");						");
        			writer.println("		break;										");
        			writer.println("	}											");
        		    }
        		    writer.println("}												");
        		}
        		writer.println("		}											");
        		writer.println("		}catch(e)										");
        		writer.println("		{											");
        		writer.println("		alert(e.message);									");
        		writer.println("	}												");
        		writer.println("	this.nodeStatus(!cn._io, id, cn._ls);");
        		writer.println("	cn._io = !cn._io;");
        		writer.println("	if(cn._io)");
        		writer.println("	{");
        		writer.println("		var tmp  = cn;");
        		writer.println("		while(tmp.pid != " + this.rootId + ")");
        		writer.println("		{");
        		writer.println("			tmp = this.aNodes.get(tmp.pid);");
        		writer.println("			this.nodeStatus(true, tmp.id, tmp._ls);	");
        		writer.println("			tmp._io = true;	");
        		writer.println("		}		");
        		writer.println("	}");
        		writer.println("	if (this.config.closeSameLevel) ");
        		writer.println("		this.closeLevel(cn);");
        		writer.println("	if (this.config.useCookies) ");
        		writer.println("		this.updateCookie();");
        		writer.println("};");									
    		}
	}

	/**
	 * 
	 */
	public int doAfterBody() {
		return this.SKIP_BODY;
	}

	/**
	 * 
	 */
	public int doEndTag() throws JspException {
		releaseSource();
		return this.EVAL_PAGE;
	}

	/**
	 * 
	 */
	public void releaseSource() {
		treeId = "tree";
		treeName = "菜单";
		checkbox = "false";
		width = "200";
		height = "600";
		rootId = "0";
		rootName = "菜单";
		rootUrl = "";
		rootTarget = "";
		dynamical = "false";
		call = "";
		dataService = "";
		hidden = "";
		id = null;
		pid = null;
		nodeName = null;
		tooltip = "";
		isOpen = "false";
		hasChild = "false";
		nodeUrl = "''";
		target = "";
		method = "doOpen";
		params = "id,a";
		img_path = "resources/";
		icon = "''";
		iconOpen = "''";
		multiTree = "false";
		methodList = "";
		paramsList = "id,a!id,a";
		treeIdList = "";
		treeOpen = "true";
		this.hasDiv="true";
	}

	public String getHasChild() {
		return hasChild;
	}

	public void setHasChild(String hasChild) {
		this.hasChild = hasChild;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public String getRootTarget() {
		return rootTarget;
	}

	public void setRootTarget(String rootTarget) {
		this.rootTarget = rootTarget;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconOpen() {
		return iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	/**
	 * @return the hidden
	 */
	public String getHidden() {
		return hidden;
	}

	/**
	 * @param hidden
	 *            the hidden to set
	 */
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	/**
	 * @return the hasDiv
	 */
	public String getHasDiv() {
		return hasDiv;
	}

	/**
	 * @param hasDiv
	 *            the hasDiv to set
	 */
	public void setHasDiv(String hasDiv) {
		this.hasDiv = hasDiv;
	}

	public String getMethodList() {
	    return methodList;
	}

	public void setMethodList(String methodList) {
	    this.methodList = methodList;
	}

	public String getMultiTree() {
	    return multiTree;
	}

	public void setMultiTree(String multiTree) {
	    this.multiTree = multiTree;
	}

	public String getParamsList() {
	    return paramsList;
	}

	public void setParamsList(String paramsList) {
	    this.paramsList = paramsList;
	}

	public String getTreeIdList() {
	    return treeIdList;
	}

	public void setTreeIdList(String treeIdList) {
	    this.treeIdList = treeIdList;
	}

	public String getTreeOpen() {
	    return treeOpen;
	}

	public void setTreeOpen(String treeOpen) {
	    this.treeOpen = treeOpen;
	}
}
