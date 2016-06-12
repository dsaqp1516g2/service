package edu.upc.eetac.dsa.project;

import edu.upc.eetac.dsa.project.dao.*;
import edu.upc.eetac.dsa.project.entity.*;
import edu.upc.eetac.dsa.project.exceptions.GithubServiceException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
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
            Project project = projectDAO.getProjectById(projectId);
            User user = (new UserDAOImpl()).getUserById(securityContext.getUserPrincipal().getName());
            task = taskDAO.createTask(projectId, title, userid, description);
            (new GithubExternalService()).createIssue(project.getRepoOwner(), project.getRepoName(), task, user.getGithubAuth());

        } catch (SQLException e){
            throw new InternalServerErrorException();
        }

        //TODO: coger exceptions del checkmembership
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + task.getId());
        return Response.created(uri).type(ProjectMediaType.PROJECT_TASK).entity(task).build();
    }

    @GET
    @Produces(ProjectMediaType.PROJECT_TASK_COLLECTION)
    public TaskCollection getProjectTasks(@PathParam("projectid") String projectid) {
        if(projectid == null)
            throw new BadRequestException("projectid not speficied");
        TaskCollection tasks = null;
        try {
            tasks = (new TaskDAOImpl()).getTasksFromProject(projectid);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return tasks;
    }

    @Path("/{id}")
    @GET
    @Produces(ProjectMediaType.PROJECT_TASK)
    public Task getTaskById(@PathParam("id") String taskid) {
        if(taskid == null)
            throw new BadRequestException("id parameter not speficied");

        Task task = null;
        try {
            task = (new TaskDAOImpl()).getTaskById(taskid);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return task;
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(ProjectMediaType.PROJECT_TASK)
    public Task updateTaskState(@PathParam("id") String taskid, @FormParam("state") String state) {
        if(state == null)
            throw new BadRequestException("id parameter not speficied");
        TaskState taskState = null;
        switch(state){
            case "in_process":
                taskState = TaskState.in_process;
                break;
            case "completed":
                taskState = TaskState.completed;
                break;
            case "proposal":
                taskState = TaskState.proposal;
                break;
        }
        if(taskState == null)
            throw new BadRequestException("invalid state");

        Task task = null;
        try {
            task = (new TaskDAOImpl()).updateTaskState(taskState, taskid);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return task;
    }
}
