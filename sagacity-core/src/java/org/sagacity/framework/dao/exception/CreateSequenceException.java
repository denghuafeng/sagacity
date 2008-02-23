/**
 * 
 */
package org.sagacity.framework.dao.exception;

import org.sagacity.framework.exception.BaseException;
/**
 *@project sagacity-core 
 *@description:$<p>创建主键异常</p>$
 *@author zhongxuchen $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:CreateSequenceException.java,Revision:v1.0,Date:Oct 19, 2007 4:06:04 PM $
 */
public class CreateSequenceException extends BaseException {
	/**
	 * 消息编号
	 */
	String msgId = null;
	
	/**
	 * 消息内容
	 */
    String msgcontent = null;

    public CreateSequenceException() {
            super();
    }

    public CreateSequenceException(String s) {
            super(s);
            msgId = s;
    }

    public CreateSequenceException(Exception e) {
            super(e);
    }

    public CreateSequenceException(String s, Exception e) {
            super(e);
            msgId = s;
    }

    public CreateSequenceException(String s, String s2) {
            this(s);
            msgcontent = s2;
    }

    public CreateSequenceException(String msgid, String content, Exception e) {
            this(msgid, e);
            this.msgcontent = content;
    }

    public String toString() {
            return "ServiceException Generated!\n MsgId is " + this.msgId
                    + " MsgContent is " + this.msgcontent;
    }

    public String getMsgcontent() {
            return msgcontent;
    }

    public String getMsgId() {
            return msgId;
    }
}
