package edu.upc.eetac.dsa.project;

import edu.upc.eetac.dsa.project.dao.ChecklistItemDAO;
import edu.upc.eetac.dsa.project.dao.ChecklistItemDAOImpl;
import edu.upc.eetac.dsa.project.dao.TaskDAO;
import edu.upc.eetac.dsa.project.dao.TaskDAOImpl;
import edu.upc.eetac.dsa.project.entity.ChecklistItem;
import edu.upc.eetac.dsa.project.entity.ProjectMediaType;
import edu.upc.eetac.dsa.project.entity.Task;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by carlos on 21/05/16.
 */
@Path("projects/{projectid}/tasks/{taskid}/checklist")
public class ChecklistResource {
    @Context
    SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(ProjectMediaType.PROJECT_CHECKLIST_ITEM)
    public Response addItem(@PathParam("taskid") String taskid, @FormParam("title") String title,@Context UriInfo uriInfo) throws URISyntaxException {
        if(title == null)
            throw new BadRequestException("title parameter is mandatory");

        ChecklistItemDAO itemDAO = new ChecklistItemDAOImpl();
        ChecklistItem item = null;
        TaskDAO taskDAO = new TaskDAOImpl();

        String userid = securityContext.getUserPrincipal().getName();

        try {
            Task task = taskDAO.getTaskById(taskid);
            if(!task.getCreator_id().equals(userid))
                throw new ForbiddenException("operation not allowed");

            item = itemDAO.createItem(taskid, title);
        } catch (SQLException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + item.getId());
        return Response.created(uri).type(ProjectMediaType.PROJECT_CHECKLIST_ITEM).entity(item).build();
    }
}
