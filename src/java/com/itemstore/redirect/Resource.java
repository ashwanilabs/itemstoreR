/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itemstore.redirect;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author ashwanilabs
 */
@Path("{path: .+}")
public class Resource {

    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    private final static String SERVER_PROPERTY_FILE = "server.properties";
    private static String[] SERVERS;
    private static int SERVER_NOS;

    /** Creates a new instance of Resource */
    public Resource() {
        InputStream inputStream = getClass().getResourceAsStream("../properties/" + SERVER_PROPERTY_FILE);
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        SERVERS = p.getProperty("SERVERS").split(":");
        SERVER_NOS = Integer.parseInt(p.getProperty("SERVER_NOS"));
    }

    /**
     * Retrieves representation of an instance of com.infistore.resources.Resource
     * @return an instance of java.lang.String
     */
    @GET
    public Response redirectGetRequest() {
        String uri = uriInfo.getAbsolutePathBuilder().host(getRandServer()).build().toString();
        uri = uri.replaceFirst("itemstoreR", "itemstore");
        URI redirect = null;
        try {
            redirect = new URI(uri);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.temporaryRedirect(redirect).status(Response.Status.TEMPORARY_REDIRECT).build();
    }

    @PUT
    public Response redirectPutRequest() {
        String uri = uriInfo.getAbsolutePathBuilder().host(getRandServer()).build().toString();
        uri = uri.replaceFirst("itemstoreR", "itemstore");
        URI redirect = null;
        try {
            redirect = new URI(uri);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.temporaryRedirect(redirect).status(Response.Status.TEMPORARY_REDIRECT).build();
    }

    @POST
    public Response redirectPostRequest() {
        String uri = uriInfo.getAbsolutePathBuilder().host(getRandServer()).build().toString();
        uri = uri.replaceFirst("itemstoreR", "itemstore");
        URI redirect = null;
        try {
            redirect = new URI(uri);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.temporaryRedirect(redirect).status(Response.Status.TEMPORARY_REDIRECT).build();
    }

    @DELETE
    public Response redirectDeleteRequest() {
        String uri = uriInfo.getAbsolutePathBuilder().host(getRandServer()).build().toString();
        uri = uri.replaceFirst("itemstoreR", "itemstore");
        URI redirect = null;
        try {
            redirect = new URI(uri);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.temporaryRedirect(redirect).status(Response.Status.TEMPORARY_REDIRECT).build();
    }

    String getRandServer() {
        Random r = new Random();
        int i = r.nextInt(SERVER_NOS);
        int k = 0;
        while (k < SERVER_NOS) {
            if (i == SERVER_NOS) {
                i = 0;
            }
            try {
                URL getUrl = new URL("http://" + SERVERS[i] + ":8080/itemstore/resources/buckets");
                HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
                connection.setRequestMethod("GET");
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    System.out.println("ItemStoreR::Redirecting request "
                            + uriInfo.getAbsolutePath() + " to " + SERVERS[i]);
                    return SERVERS[i];
                }

            } catch (IOException ex) {
                Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
            }
            i++;
            k++;
        }
        return SERVERS[0];

//        System.out.println("ItemStoreR::Redirecting request "
//                + uriInfo.getAbsolutePath() + " to " + SERVERS[i]);
//        return SERVERS[i];
    }
}
