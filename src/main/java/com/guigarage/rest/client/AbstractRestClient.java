package com.guigarage.rest.client;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public abstract class AbstractRestClient {

    private String baseUrl;


    private Client client;

    public AbstractRestClient(String host, int port) {
        this("http://" + host + ":" + port);
    }

    public AbstractRestClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private synchronized Client getClient() {
        if (client == null) {
            client = ClientBuilder.newClient();
        }
        return client;
    }

    private Invocation.Builder createPrepairedTarget(String path, QueryParams queryParams, MediaType requestMediaType) {
        WebTarget base = getClient().target(baseUrl).path(path);
        if(queryParams != null) {
            for(QueryParam queryParam : queryParams.getParams()) {
                base = base.queryParam(queryParam.getName(), queryParam.getParam());
            }
        }
        return base.register(DebugFilter.class).request(requestMediaType).accept(MediaType.APPLICATION_JSON_TYPE);
    }

    private Invocation.Builder createPrepairedTarget(String path, QueryParams queryParams) {
        return createPrepairedTarget(path, queryParams, MediaType.APPLICATION_JSON_TYPE);
    }

    protected <T> T callGet(String path, Class<T> objectClass) {
        return createPrepairedTarget(path, null).get(objectClass);
    }

    protected void callPost(String path, QueryParams queryParams) {
        createPrepairedTarget(path, queryParams).post(Entity.json(""), String.class);
    }

    protected void callDelete(String path, QueryParams queryParams) {
        createPrepairedTarget(path, queryParams).delete();
    }

    protected <U> void callPost(String path, QueryParams queryParams, U object) {
        createPrepairedTarget(path, queryParams).post(Entity.json(object), String.class);
    }

    protected <U> void callPost(String path, QueryParams queryParams, U object, MediaType requestMediaType) {
        createPrepairedTarget(path, queryParams).post(Entity.json(object), String.class);
    }

    protected void callPost(String path) {
        createPrepairedTarget(path, null).post(Entity.json(""), String.class);
    }

    protected <T, U> T callPost(String path, QueryParams queryParams, U object, Class<T> objectClass) {
        return createPrepairedTarget(path, null).post(Entity.json(object), objectClass);
    }

    protected <T> T callGet(String path, QueryParams queryParams, Class<T> objectClass) {
        return createPrepairedTarget(path, queryParams).get(objectClass);
    }

    protected <T> T callGet(String path, GenericType<T> genericType) {
        return createPrepairedTarget(path, null).get(genericType);
    }

    protected <T> T callGet(String path, QueryParams queryParams, GenericType<T> genericType) {
        return createPrepairedTarget(path, queryParams).get(genericType);
    }
}
