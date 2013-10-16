package com.gamephone.acs.model;

import java.io.Serializable;


public class Channel implements Serializable{

    private static final long serialVersionUID=1L;

    private Integer id;
    
    private Integer pid;
    
    private String name;
    
    private Integer status;

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id=id;
    }

    
    public Integer getPid() {
        return pid;
    }

    
    public void setPid(Integer pid) {
        this.pid=pid;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name=name;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status=status;
    }

    
}
