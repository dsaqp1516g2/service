package edu.upc.eetac.dsa.project.dao;

/**
 * Created by carlos on 4/05/16.
 */
public interface ProjectDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_PROJECT = "insert into " +
            "projects (id, name, description, repo_url, admin_userid) " +
            "values (unhex(?), ?, ?, ?, unhex(?))";
    public final static String GET_PROJECT_BY_ID = "select hex(p.id) as id, p.name, p.description, p.repo_url, p.creation_timestamp, hex(p.admin_userid) as adminId, u.fullname as adminName from projects p, users u where p.id=unhex(?) and u.id=p.admin_userid";
}
