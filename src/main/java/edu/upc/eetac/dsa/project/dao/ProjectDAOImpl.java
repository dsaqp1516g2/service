package edu.upc.eetac.dsa.project.dao;

import edu.upc.eetac.dsa.project.entity.Project;
import edu.upc.eetac.dsa.project.entity.ProjectCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by carlos on 4/05/16.
 */
public class ProjectDAOImpl implements ProjectDAO {
    @Override
    public Project createProject(String name, String description, String repoUrl, String adminId) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ProjectDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);

            stmt.close();
            stmt = connection.prepareStatement(ProjectDAOQuery.CREATE_PROJECT);
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, description); //TODO: probarque pasa si description es null
            stmt.setString(4, repoUrl);
            stmt.setString(5, adminId);
            stmt.executeUpdate();

            //TODO: cuando se crea el projecto hay que meter al admin como miembro


        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getProjectById(id);
    }

    @Override
    public Project updateProject() throws SQLException {
        return null;
    }

    @Override
    public boolean deleteProject() throws SQLException {
        return false;
    }

    @Override
    public Project getProjectById(String id) throws SQLException {
        Project project = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ProjectDAOQuery.GET_PROJECT_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                project = new Project();
                project.setId(rs.getString("id"));
                project.setName(rs.getString("name"));
                project.setCreationTimestamp(rs.getString("creation_timestamp"));
                project.setRepoUrl(rs.getString("repo_url"));
                project.setDescription((rs.getString("description")));
                project.setAdminId(rs.getString("adminId"));
                project.setAdminName(rs.getString("adminName"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return project;
    }

    @Override
    public ProjectCollection getUserProjects() throws SQLException {
        return null;
    }
}
