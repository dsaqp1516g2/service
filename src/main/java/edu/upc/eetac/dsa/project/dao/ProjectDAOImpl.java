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
    public Project createProject(String name, String description, String repoOwner, String repoName, String adminId) throws SQLException {
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
            stmt.setString(3, description);
            stmt.setString(4, repoOwner);
            stmt.setString(5, repoName);
            stmt.setString(6, adminId);
            stmt.executeUpdate();


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
                project.setRepoOwner(rs.getString("repo_owner"));
                project.setRepoName(rs.getString("repo_name"));
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
    public ProjectCollection getMemberProjects(String userid) throws SQLException {

        Connection connection = null;
        PreparedStatement stmt = null;
        ProjectCollection projects = new ProjectCollection();
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ProjectDAOQuery.GET_PROJECTS_FROM_MEMBER);
            stmt.setString(1, userid);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Project project = new Project();
                project.setId(rs.getString("id"));
                project.setName(rs.getString("name"));
                project.setCreationTimestamp(rs.getString("creation_timestamp"));
                project.setRepoOwner(rs.getString("repo_owner"));
                project.setRepoName(rs.getString("repo_name"));
                project.setDescription((rs.getString("description")));
                project.setAdminId(rs.getString("adminId"));
                project.setAdminName(rs.getString("adminName"));
                projects.getProjects().add(project);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return projects;
    }

    @Override
    public boolean checkMembership(String userId, String projectId) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ProjectDAOQuery.CHECK_MEMBERSHIP);
            stmt.setString(1, userId);
            stmt.setString(2, projectId);

            ResultSet rs = stmt.executeQuery();
            return(rs.next());
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public void joinProject(String userid, String projectid) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ProjectDAOQuery.ADD_MEMBER);
            stmt.setString(1, userid);
            stmt.setString(2, projectid);

            int rows = stmt.executeUpdate();
            //TODO: control de errores??

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }
}
