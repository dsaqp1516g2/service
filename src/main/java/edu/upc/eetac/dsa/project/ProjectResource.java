package edu.upc.eetac.dsa.project;

import edu.upc.eetac.dsa.project.dao.ProjectDAO;
import edu.upc.eetac.dsa.project.dao.ProjectDAOImpl;
import edu.upc.eetac.dsa.project.entity.Project;
import edu.upc.eetac.dsa.project.entity.ProjectCollection;
import edu.upc.eetac.dsa.project.entity.ProjectMediaType;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
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

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(ProjectMediaType.PROJECT_PROJECT)
    public Response createProject(@FormParam("name") String name, @FormParam("description") String description, @FormParam("repoOwner") String repoOwner, @FormParam("repoName") String repoName, @Context UriInfo uriInfo) throws URISyntaxException {
        if(name == null || repoOwner == null || repoName == null) // la descripción es opcional
            throw new BadRequestException("all parameters are mandatory");

        ProjectDAO projectDAO = new ProjectDAOImpl();
        Project project = null;

        try {
            project = projectDAO.createProject(name, description, repoOwner, repoName, securityContext.getUserPrincipal().getName()); //TODO rehacer con parametros
            //el que crea el proyecto se añade como miembro automaticamente
            projectDAO.joinProject(securityContext.getUserPrincipal().getName(), project.getId());
        } catch (SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + project.getId());
        return Response.created(uri).type(ProjectMediaType.PROJECT_PROJECT).entity(project).build();
    }

    @Path("/{id}")
    @GET
    @Produces(ProjectMediaType.PROJECT_USER)
    public Project getProject(@PathParam("id") String id) {
        Project project = null;
        try {
            project = (new ProjectDAOImpl()).getProjectById(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(project == null)
            throw new NotFoundException("Project with id = "+id+" doesn't exist");
        return project;
    }

    @GET
    @Produces(ProjectMediaType.PROJECT_PROJECT_COLLECTION)
    public ProjectCollection getMyProjects() {
        ProjectCollection projects = null;
        try {
            projects = (new ProjectDAOImpl()).getMemberProjects(securityContext.getUserPrincipal().getName());
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return projects;
    }

    @Path("/{id}/members")
    @POST
    @Produces(ProjectMediaType.PROJECT_PROJECT)
    public Response addMember(@PathParam("id") String id, @Context UriInfo uriInfo) throws URISyntaxException{
        String userid = securityContext.getUserPrincipal().getName();
        Project project = null;
        ProjectDAO projectDAO = new ProjectDAOImpl();

        try {
            project = projectDAO.getProjectById(id);
            if(project == null)
                throw new BadRequestException("project id doesn't exist");
            if(projectDAO.checkMembership(userid,id))
                throw new WebApplicationException("user is already a member", Response.Status.CONFLICT);
            projectDAO.joinProject(userid, id);

        } catch (SQLException e){
            throw new InternalServerErrorException();
        }

        URI uri = new URI(uriInfo.getBaseUri().toString() + "/projectos/" + project.getId());
        return Response.created(uri).type(ProjectMediaType.PROJECT_PROJECT).entity(project).build();
    }
}
