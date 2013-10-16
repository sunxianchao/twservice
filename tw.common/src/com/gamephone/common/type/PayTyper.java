package com.gamephone.common.type;


public enum PayTyper implements EnumTyper{
    INGAME(1, "ingame"), BILLING(2, "billing"), GOOGLE(3, "google");

    private Integer id;

    private String name;
    
    private PayTyper(Integer id, String name) {
        this.id=id;
        this.name=name;
    }
    
    
    public static PayTyper getById(Integer id) {
        if(null == id) {
            return null;
        }
        for(PayTyper tmp: PayTyper.values()) {
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
