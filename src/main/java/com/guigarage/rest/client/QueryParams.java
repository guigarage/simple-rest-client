package com.guigarage.rest.client;

import java.util.ArrayList;
import java.util.List;

public class QueryParams {

    List<QueryParam> params;

    public QueryParams() {
        params = new ArrayList<>();
    }

     public QueryParams with(QueryParam param) {
         params.add(param);
         return this;
     }

    public QueryParams with(String name, Object param) {
        return with(new QueryParam(name, param));
    }

    public static QueryParams create() {
        return new QueryParams();
    }

    public static QueryParams create(QueryParam param) {
        return new QueryParams().with(param);
    }

    public static QueryParams create(String name, Object param) {
        return create(new QueryParam(name, param));
    }

    public Iterable<QueryParam> getParams() {
        return params;
    }
}
