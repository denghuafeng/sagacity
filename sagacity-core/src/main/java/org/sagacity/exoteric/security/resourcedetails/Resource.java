/**
 * 
 */
package org.sagacity.exoteric.security.resourcedetails;

import org.acegisecurity.GrantedAuthority;
import org.springframework.util.Assert;

/**
 *@project sagacity-core 
 *@description:$<p>在此说明类的功能</p>$
 *@author Administrator $<a href="mailto:zhongxuchen@hotmail.com">联系作者</a>$
 *@version $id:Resource.java,Revision:v1.0,Date:2008-12-10 下午07:45:29 $
 */
public class Resource implements ResourceDetails {
	/**
	 * 
	 */
    private String resString;

    private String resType;
    
    private GrantedAuthority[] authorities;

    public Resource(String resString, String resType, GrantedAuthority[] authorities)
            throws IllegalArgumentException {
        if (resString == null || "".equals(resString)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to resource string");
        }
        if (resType == null || "".equals(resType)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to resource type");
        }
        this.resString = resString;
        this.resType = resType;
        setAuthorities(authorities);
    }

    public boolean equals(Object rhs) {
        if (rhs == null || !(rhs instanceof Resource))
            return false;
        Resource resauth = (Resource) rhs;
        if (resauth.getAuthorities().length != getAuthorities().length)
            return false;
        for (int i = 0; i < getAuthorities().length; i++) {
            if (!getAuthorities()[i].equals(resauth.getAuthorities()[i]))
                return false;
        }
        return (getResString().equals(resauth.getResString()) && getResType().equals(resauth.getResType()));
    }

    public int hashCode() {
        int code = 168;
        if (getAuthorities() != null) {
            for (int i = 0; i < getAuthorities().length; i++)
                code *= getAuthorities()[i].hashCode() % 7;
        }
        if (getResString() != null)
            code *= getResString().hashCode() % 7;
        return code;
    }

    public String getResString() {
        return resString;
    }

    public void setResString(String resString) {
        this.resString = resString;
    }

    public GrantedAuthority[] getAuthorities() {
        return authorities;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    protected void setAuthorities(GrantedAuthority[] authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority array");
        for (int i = 0; i < authorities.length; i++) {
            Assert.notNull(authorities[i],
                    "Granted authority element " + i + " is null - GrantedAuthority[] cannot contain any null elements");
        }
        this.authorities = authorities;
    }
}
