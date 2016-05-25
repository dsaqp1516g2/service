package edu.upc.eetac.dsa.project;

import edu.upc.eetac.dsa.project.dao.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.project.exceptions.UserAlreadyExistsException;
import edu.upc.eetac.dsa.project.dao.UserDAO;
import edu.upc.eetac.dsa.project.dao.UserDAOImpl;
import edu.upc.eetac.dsa.project.entity.AuthToken;
import edu.upc.eetac.dsa.project.entity.ProjectMediaType;
import edu.upc.eetac.dsa.project.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by carlos on 4/05/16.
 */
@Path("users")
public class UserResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(ProjectMediaType.PROJECT_AUTH_TOKEN)
    public Response registerUser(@FormParam("loginid") String loginid, @FormParam("password") String password, @FormParam("email") String email, @FormParam("fullname") String fullname, @FormParam("githubauth") String githubauth, @Context UriInfo uriInfo) throws URISyntaxException {
        if(loginid == null || password == null || email == null || fullname == null || githubauth == null)
            throw new BadRequestException("all parameters are mandatory");
        UserDAO userDAO = new UserDAOImpl();
        User user = null;
        AuthToken authenticationToken = null;
        try{
            if(!(new GithubExternalService()).checkUserAndPass(githubauth))
            {
                throw new BadRequestException("Invalid github auth");
            }
            user = userDAO.createUser(loginid, password, email, fullname, githubauth);
            authenticationToken = (new AuthTokenDAOImpl()).createAuthToken(user.getId());
        }catch (UserAlreadyExistsException e){
            throw new WebApplicationException("loginid already exists", Response.Status.CONFLICT);
        }catch(SQLException e){
            throw new InternalServerErrorException();
        }
        catch(IOException e){
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + user.getId());
        return Response.created(uri).type(ProjectMediaType.PROJECT_AUTH_TOKEN).entity(authenticationToken).build();
    }

    @Path("/{id}")
    @GET
    @Produces(ProjectMediaType.PROJECT_USER)
    public User getUser(@PathParam("id") String id) {
        User user = null;
        try {
            user = (new UserDAOImpl()).getUserById(id);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if(user == null)
            throw new NotFoundException("User with id = "+id+" doesn't exist");
        return user;
    }

    /* De momento no hacemos actualización de usuarios

    @Path("/{id}")
    @PUT
    @Consumes(ProjectMediaType.PROJECT_USER)
    @Produces(ProjectMediaType.PROJECT_USER)
    public User updateUser(@PathParam("id") String id, User user) {
        if(user == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(user.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(id))
            throw new ForbiddenException("operation not allowed");

        UserDAO userDAO = new UserDAOImpl();
        try {
            user = userDAO.updateProfile(userid, user.getEmail(), user.getFullname(), user.getGithubAuth());
            if(user == null)
                throw new NotFoundException("User with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return user;
    }
    */
}
