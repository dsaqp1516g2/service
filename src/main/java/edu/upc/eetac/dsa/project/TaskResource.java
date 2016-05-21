package edu.upc.eetac.dsa.project;

import edu.upc.eetac.dsa.project.dao.ProjectDAO;
import edu.upc.eetac.dsa.project.dao.ProjectDAOImpl;
import edu.upc.eetac.dsa.project.dao.TaskDAO;
import edu.upc.eetac.dsa.project.dao.TaskDAOImpl;
import edu.upc.eetac.dsa.project.entity.ProjectMediaType;
import edu.upc.eetac.dsa.project.entity.Task;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by carlos on 12/05/16.
 */
@Path("projects/{projectid}/tasks")
public class TaskResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(ProjectMediaType.PROJECT_TASK)
    public Response createTask(@PathParam("projectid") String projectId, @FormParam("title") String title, @FormParam("description") String description, @FormParam("label") String label, @FormParam("dueTimestamp") String dueTimestamp, @Context UriInfo uriInfo) throws URISyntaxException {
        if(title == null) {
            throw new BadRequestException("title parameter is mandatory");
        }

        TaskDAO taskDAO = new TaskDAOImpl();
        Task task = null;
        ProjectDAO projectDAO = new ProjectDAOImpl();

        String userid = securityContext.getUserPrincipal().getName();

        try {
            //Solo pueden crear tareas los miembros del grupo
            if(!projectDAO.checkMembership(userid, projectId))
                throw new ForbiddenException("operation not allowed");
            //TODO: de momento no introducimos label (bug o enhancement) y el dueTimestamp
            task = taskDAO.createTask(title, userid, description);
        } catch (SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + task.getId());
        return Response.created(uri).type(ProjectMediaType.PROJECT_TASK).entity(task).build();
    }
}
