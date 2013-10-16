package com.gamephone.common.jackjson;

import org.codehaus.jackson.annotate.JsonIgnore;

public class ResponseBodyWrapper {

    private Object object;

    private Class<?> view;

    public ResponseBodyWrapper(Object object, Class<?> view) {
        this.object=object;
        this.view=view;
    }
    
    public ResponseBodyWrapper(Object object) {
        this.object=object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object=object;
    }

    @JsonIgnore
    public Class<?> getView() {
        return view;
    }

    @JsonIgnore
    public void setView(Class<?> view) {
        this.view=view;
    }
}
