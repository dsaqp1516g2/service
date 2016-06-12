package edu.upc.eetac.dsa.project.dao;

/**
 * Created by carlos on 4/05/16.
 */
public interface ProjectDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_PROJECT = "insert into " +
            "projects (id, name, description, repo_owner, repo_name, admin_userid) " +
            "values (unhex(?), ?, ?, ?, ?, unhex(?))";
    public final static String GET_PROJECT_BY_ID = "select hex(p.id) as id, p.name, p.description, p.repo_owner, p.repo_name, p.creation_timestamp, hex(p.admin_userid) as adminId, u.loginid as adminName from projects p, users u where p.id=unhex(?) and u.id=p.admin_userid";
    public final static String CHECK_MEMBERSHIP = "select hex(p.userid) as userid from user_projects as p where p.userid=UNHEX(?) and p.projectid=UNHEX(?)";
    public final static String ADD_MEMBER = "insert into user_projects (userid, projectid) values (UNHEX(?), UNHEX(?))";
    public final static String GET_PROJECTS_FROM_MEMBER = "select hex(p.id) as id, p.name, p.description, p.repo_owner, p.repo_name, p.creation_timestamp, hex(p.admin_userid) as adminId, u.loginid as adminName from projects p, users u, user_projects m where m.userid=unhex(?) and p.id=m.projectid and u.id=p.admin_userid";
}
