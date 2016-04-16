package edu.upc.eetac.dsa.project;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by carlos on 16/04/16.
 */
public class ProjectResourceConfig extends ResourceConfig {

    public ProjectResourceConfig() {
        packages("edu.upc.eetac.dsa.project");
        packages("edu.upc.eetac.dsa.project.auth");
        packages("edu.upc.eetac.dsa.project.cors");
        register(RolesAllowedDynamicFeature.class);
        register(DeclarativeLinkingFeature.class);
    }
}
