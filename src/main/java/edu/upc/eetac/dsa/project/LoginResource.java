package edu.upc.eetac.dsa.project;

import edu.upc.eetac.dsa.project.dao.AuthTokenDAO;
import edu.upc.eetac.dsa.project.dao.AuthTokenDAOImpl;
import edu.upc.eetac.dsa.project.dao.UserDAO;
import edu.upc.eetac.dsa.project.dao.UserDAOImpl;
import edu.upc.eetac.dsa.project.entity.AuthToken;
import edu.upc.eetac.dsa.project.entity.ProjectMediaType;
import edu.upc.eetac.dsa.project.entity.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.sql.SQLException;

/**
 * Created by carlos on 11/05/16.
 */
@Path("login")
public class LoginResource {
    @Context
    SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(ProjectMediaType.PROJECT_AUTH_TOKEN)
    public AuthToken login(@FormParam("login") String loginid, @FormParam("password") String password) {
        if(loginid == null || password == null)
            throw new BadRequestException("all parameters are mandatory");

        User user = null;
        AuthToken authToken = null;
        try {
            UserDAO userDAO = new UserDAOImpl();
            user = userDAO.getUserByLoginid(loginid);
            if(user == null)
                throw new BadRequestException("loginid " + loginid + " not found.");
            if(!userDAO.checkPassword(user.getId(), password))
                throw new BadRequestException("incorrect password");

            AuthTokenDAO authTokenDAO = new AuthTokenDAOImpl();
            authTokenDAO.deleteToken(user.getId());
            authToken = authTokenDAO.createAuthToken(user.getId());
        } catch(SQLException e) {
            throw new InternalServerErrorException();
        }
        return authToken;
    }

    @DELETE
    public void logout(){
        String userid = securityContext.getUserPrincipal().getName();
        AuthTokenDAO authTokenDAO = new AuthTokenDAOImpl();
        try {
            authTokenDAO.deleteToken(userid);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}
