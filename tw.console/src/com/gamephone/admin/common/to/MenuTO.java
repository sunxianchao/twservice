/**
 * $Id: MenuTO.java,v 1.2 2012/03/28 02:58:13 xianchao.sun Exp $
 */
package com.gamephone.admin.common.to;

/**
 * @author xianchao.sun@downjoy.com
 * @date 2012-3-23
 */
public class MenuTO {

    private Long id;// 菜单ID

    private String name;// 菜单名称

    private MenuTO parent=null; // 父级菜单

    private String parentIdPath;// 菜单父编码序列

    private String url;// 链接地址

    private String remark;// 链接说明

    private Integer sortOrderNo;// 排序

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public MenuTO getParent() {
        return parent;
    }

    public void setParent(MenuTO parent) {
        this.parent=parent;
    }

    public String getParentIdPath() {
        return parentIdPath;
    }

    public void setParentIdPath(String parentIdPath) {
        this.parentIdPath=parentIdPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url=url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark=remark;
    }

    public Integer getSortOrderNo() {
        return sortOrderNo;
    }

    public void setSortOrderNo(Integer sortOrderNo) {
        this.sortOrderNo=sortOrderNo;
    }
}
