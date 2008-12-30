package org.sagacity.exoteric.security.resourcedetails;

import java.io.Serializable;

import org.springframework.security.GrantedAuthority;

/**
 * 提供资源信息
 *@project sagacity-core 
 *@description:$<p>在此说明类的功能</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:ResourceDetails.java,Revision:v1.0,Date:2008-12-10 下午07:53:17 $
 */
public interface ResourceDetails extends Serializable {

    /**
     * 资源串
     */
    public String getResString();

    /**
     * 资源类型,如URL,FUNCTION
     */
    public String getResType();

    /**
     * 返回属于该resource的authorities
     */
    public GrantedAuthority[] getAuthorities();

}
