package edu.upc.eetac.dsa.project.dao;

import edu.upc.eetac.dsa.project.entity.Project;
import edu.upc.eetac.dsa.project.entity.ProjectCollection;

import java.sql.SQLException;

/**
 * Created by carlos on 21/04/16.
 */
public interface ProjectDAO {
    public Project createProject() throws SQLException;
    public Project updateProject() throws SQLException;
    public boolean deleteProject() throws SQLException;
    public Project getProjectById() throws SQLException;
    public ProjectCollection getUserProjects() throws SQLException;
}
