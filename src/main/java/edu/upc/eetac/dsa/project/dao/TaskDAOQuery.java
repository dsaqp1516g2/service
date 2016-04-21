package edu.upc.eetac.dsa.project.dao;

/**
 * Created by carlos on 21/04/16.
 */
public interface TaskDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_TASK = "insert into " +
            "tasks (id, creator_userid, title, description, due_timestamp, label) " +
            "values (unhex(?), unhex(?), ?, ? , ?, ?, ?";
    public final static String UPDATE_TASK = "update tasks set title=?, description=?, due_timestamp=?, label=? where id=unhex(?)";
}
