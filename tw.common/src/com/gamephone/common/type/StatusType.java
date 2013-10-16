/**
 * $Id: StatusType.java,v 1.3 2012/03/29 03:10:47 jiayu.qiu Exp $
 */
package com.gamephone.common.type;

/**
 * @author jiayu.qiu@downjoy.com
 */
public enum StatusType implements EnumTyper {
    ACTIVE(1, "有效"), INACTIVE(0, "停用");

    private Integer id;

    private String name;

    private StatusType(Integer id, String name) {
        this.id=id;
        this.name=name;
    }

    public static StatusType getById(Integer id) {
        if(null == id) {
            return null;
        }
        for(StatusType tmp: StatusType.values()) {
            if(tmp.id.intValue() == id.intValue()) {
                return tmp;
            }
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

}
