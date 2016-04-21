package edu.upc.eetac.dsa.project.dao;

import edu.upc.eetac.dsa.project.auth.UserInfo;
import edu.upc.eetac.dsa.project.entity.AuthToken;

import java.sql.SQLException;

/**
 * Created by carlos on 21/04/16.
 */
public interface AuthTokenDAO {
    public UserInfo getUserByAuthToken(String token) throws SQLException;
    public AuthToken createAuthToken(String userid) throws SQLException;
    public void deleteToken(String userid) throws  SQLException;
}
