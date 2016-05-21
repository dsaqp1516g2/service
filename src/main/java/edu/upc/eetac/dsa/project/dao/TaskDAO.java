package edu.upc.eetac.dsa.project.dao;

import edu.upc.eetac.dsa.project.entity.Label;
import edu.upc.eetac.dsa.project.entity.Task;
import edu.upc.eetac.dsa.project.entity.TaskCollection;

import java.sql.SQLException;

/**
 * Created by carlos on 21/04/16.
 */
public interface TaskDAO {
    public Task createTask(String projectid, String title, String creatorId, String description) throws SQLException;
    public Task updateTask() throws SQLException;
    public boolean deleteTask() throws SQLException;
    public TaskCollection getTasksFromProject(String projectid) throws SQLException;
    public Task getTaskById(String id) throws SQLException;
    public Task updateTaskState() throws SQLException;
}
