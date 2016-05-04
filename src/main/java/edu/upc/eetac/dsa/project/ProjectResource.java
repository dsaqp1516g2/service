package edu.upc.eetac.dsa.project;

import edu.upc.eetac.dsa.project.dao.ProjectDAO;
import edu.upc.eetac.dsa.project.dao.ProjectDAOImpl;
import edu.upc.eetac.dsa.project.entity.Project;
import edu.upc.eetac.dsa.project.entity.ProjectMediaType;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.FormParam;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by carlos on 4/05/16.
 */
@Path("projects")
public class ProjectResource {
    @Context
    private SecurityContext securityContext;

    public Response createProject(@FormParam("name") String name, @FormParam("description") String description, @FormParam("repourl") String repourl, @Context UriInfo uriInfo) throws URISyntaxException {
        if(name == null || repourl == null) // la descripción es opcional
            throw new BadRequestException("all parameters are mandatory");

        ProjectDAO projectDAO = new ProjectDAOImpl();
        Project project = null;

        try {
            project = projectDAO.createProject(name, description, repourl); //TODO rehacer con parametros
        } catch (SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + project.getId());
        return Response.created(uri).type(ProjectMediaType.PROJECT_PROJECT).entity(project).build();
    }
}
