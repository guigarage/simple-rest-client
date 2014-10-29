package com.guigarage.rest.client;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Provider
public class DebugFilter implements ClientResponseFilter {

    @Override
    public void filter(ClientRequestContext clientRequestContext, ClientResponseContext clientResponseContext) throws IOException {
        System.out.println("Request URI: " + clientRequestContext.getUri());

        try {
            System.out.println("Response:");
            byte[] data = IOUtils.toByteArray(clientResponseContext.getEntityStream());

            try {
                clientResponseContext.setEntityStream(new ByteArrayInputStream(data));
                if (new String(data).trim().startsWith("[")) {
                    System.out.println(new JSONArray(new String(data)).toString(2));
                } else {
                    System.out.println(new JSONObject(new String(data)).toString(2));
                }

            } catch (Exception e) {
                System.out.println("No JSON Response!");
                System.out.println(new String(data));
            }
        } catch (Exception e) {
            System.out.println("Unknown Error!");
        }
    }
}
