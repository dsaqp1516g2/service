package edu.upc.eetac.dsa.project;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Main class.
 *
 */
public class Main {

    private static String baseURI;


    public static String getBaseURI() {
        if (baseURI == null) {
            PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("project");
            baseURI = prb.getString("project.context");
        }
        return baseURI;
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in edu.upc.eetac.dsa.project package
        final ProjectResourceConfig rc = new ProjectResourceConfig();

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(getBaseURI()), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", getBaseURI()));
        System.in.read();
        server.shutdownNow();
    }
}

