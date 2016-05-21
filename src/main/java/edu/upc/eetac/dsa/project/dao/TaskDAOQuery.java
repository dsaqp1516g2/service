package edu.upc.eetac.dsa.project.dao;

/**
 * Created by carlos on 21/04/16.
 */
public interface TaskDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_TASK = "insert into " +
            "tasks (id, creator_userid, title, description, due_timestamp, label) " +
            "values (unhex(?), unhex(?), ?, ? , ?, ?)";
    public final static String UPDATE_TASK = "update tasks set title=?, description=?, due_timestamp=?, label=? where id=unhex(?)";
    public final static String GET_TASK_BY_ID = "select hex(t.id) as id, t.title, t.description, t.state, t.creation_timestamp, t.due_timestamp, t.label, u.fullname from tasks t, users u where t.id=unhex(?) and u.id=t.creator_userid";
}
