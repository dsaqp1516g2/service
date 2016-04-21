package edu.upc.eetac.dsa.project.dao;

import edu.upc.eetac.dsa.project.entity.Task;
import edu.upc.eetac.dsa.project.entity.TaskCollection;

import java.sql.SQLException;

/**
 * Created by carlos on 21/04/16.
 */
public interface TaskDAO {
    public Task createTask() throws SQLException;
    public Task updateTask() throws SQLException;
    public boolean deleteTask() throws SQLException;
    public TaskCollection getTasksFromProject() throws SQLException;
    public Task getTaskById() throws SQLException;
    public Task updateTaskState() throws SQLException;
}
