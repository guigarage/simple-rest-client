package com.guigarage.rest.client;

public class QueryParam {

    String name;

    Object param;

    public QueryParam(String name, Object param) {
        this.name = name;
        this.param = param;
    }

    public String getName() {
        return name;
    }

    public Object getParam() {
        return param;
    }
}
