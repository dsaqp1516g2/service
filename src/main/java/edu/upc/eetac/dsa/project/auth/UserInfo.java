package edu.upc.eetac.dsa.project.auth;

import edu.upc.eetac.dsa.project.entity.Role;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos on 21/04/16.
 */
public class UserInfo implements Principal {

    private String userid;
    private List<Role> roles = new ArrayList<>();

    public UserInfo() {
    }

    public UserInfo(String name) {
        this.userid = name;
    }

    @Override
    public String getName() {
        return userid;
    }

    public void setName(String name) {
        this.userid = name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
