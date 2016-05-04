package edu.upc.eetac.dsa.project.dao;

/**
 * Created by carlos on 4/05/16.
 */
public interface ProjectDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_PROJECT = "insert into " +
            "projects (id, name, description, repo_url) " +
            "values (unhex(?), ?, ?, ?)";
    public final static String GET_PROJECT_BY_ID = "select hex(p.id) as id, p.name, p.description, p.repo_url, p.creation_timestamp from projects p where p.id=unhex(?)";
}
