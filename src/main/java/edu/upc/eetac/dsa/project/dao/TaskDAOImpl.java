package edu.upc.eetac.dsa.project.dao;

import edu.upc.eetac.dsa.project.entity.Label;
import edu.upc.eetac.dsa.project.entity.Task;
import edu.upc.eetac.dsa.project.entity.TaskCollection;
import edu.upc.eetac.dsa.project.entity.TaskState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by carlos on 12/05/16.
 */
public class TaskDAOImpl implements TaskDAO {
    @Override
    public Task createTask(String title, String creatorId, String description) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TaskDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);

            stmt.close();
            stmt = connection.prepareStatement(TaskDAOQuery.CREATE_TASK);
            stmt.setString(1, id);
            stmt.setString(2, creatorId);
            stmt.setString(3, title);
            stmt.setString(4, description);
            stmt.setString(5, null);
            stmt.setString(6, null);
            stmt.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getTaskById(id);
    }

    @Override
    public Task updateTask() throws SQLException {
        return null;
    }

    @Override
    public boolean deleteTask() throws SQLException {
        return false;
    }

    @Override
    public TaskCollection getTasksFromProject() throws SQLException {
        return null;
    }

    @Override
    public Task getTaskById(String id) throws SQLException {
        Task task = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TaskDAOQuery.GET_TASK_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                task = new Task();
                task.setId(rs.getString("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                TaskState state = null;
                switch(rs.getString("state")){
                    case "in_proces":
                        state = TaskState.in_process;
                        break;
                    case "completed":
                        state = TaskState.completed;
                        break;
                    case "proposal":
                        state = TaskState.proposal;
                        break;
                }
                task.setState(state);
                Label label = null;
                //TODO: bug con este switch
                /*
                switch(rs.getString("label")){
                    case "bug":
                        label = Label.bug;
                        break;
                    case "enhancement":
                        label = Label.enhancement;
                        break;
                }
                */
                task.setLabel(label);
                task.setCreationTimestamp(rs.getString("creation_timestamp"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return task;
    }

    @Override
    public Task updateTaskState() throws SQLException {
        return null;
    }
}
