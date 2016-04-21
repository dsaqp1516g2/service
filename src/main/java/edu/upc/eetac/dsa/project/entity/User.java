package edu.upc.eetac.dsa.project.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by carlos on 16/04/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @InjectLinks({})
    private List<Link> links;

    private String id;
    private String loginid;
    private String email;
    private String fullname;
    //TODO (no actualizado en DB) codigo para autentificar al usuario creado a partirde su usuario y contraseña
    private String githubAuth;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGithubAuth() {
        return githubAuth;
    }

    public void setGithubAuth(String githubAuth) {
        this.githubAuth = githubAuth;
    }
}
