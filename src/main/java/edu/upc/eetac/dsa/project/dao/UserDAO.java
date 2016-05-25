package edu.upc.eetac.dsa.project.dao;

import edu.upc.eetac.dsa.project.entity.User;
import edu.upc.eetac.dsa.project.exceptions.UserAlreadyExistsException;

import java.sql.SQLException;

/**
 * Created by carlos on 21/04/16.
 */
public interface UserDAO {

    public User createUser(String loginid, String password, String email, String fullname, String githubAuth) throws SQLException, UserAlreadyExistsException;

    public User updateProfile(String id, String email, String fullname, String githubAuth) throws SQLException;

    public User getUserById(String id) throws SQLException;

    public User getUserByLoginid(String loginid) throws SQLException;

    public boolean checkPassword(String id, String password) throws SQLException;
}
