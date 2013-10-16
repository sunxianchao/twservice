package com.gamephone.common.type;


public enum OP implements EnumTyper{
    READ(1,"READ"), WRITE(2, "WRITE"), READANDWRITE(3, "READ AND WRITE");

    private Integer id;

    private String name;
    
    private OP(Integer id, String name){
        this.id=id;
        this.name=name;
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
