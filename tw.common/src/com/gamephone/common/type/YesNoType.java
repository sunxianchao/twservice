/**
 * $Id: YesNoType.java,v 1.1 2012/03/26 07:14:32 qinchao.zhang Exp $
 */
package com.gamephone.common.type;

/**
 * @author qinchao.zhang@downjoy.com
 */
public enum YesNoType implements EnumTyper {
    NO(0, "否"), YES(1, "是");

    private Integer id;

    private String name;

    private YesNoType(Integer id, String name) {
        this.id=id;
        this.name=name;
    }

    public static YesNoType getById(Integer id) {
        if(null == id) {
            return null;
        }
        for(YesNoType tmp: YesNoType.values()) {
            if(tmp.id.intValue() == id.intValue()) {
                return tmp;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.downjoy.app.billing.common.type.EnumTyper#getId()
     */
    public Integer getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * @see com.downjoy.app.billing.common.type.EnumTyper#getName()
     */
    public String getName() {
        return name;
    }

}
