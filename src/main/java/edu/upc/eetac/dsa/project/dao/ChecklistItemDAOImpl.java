package edu.upc.eetac.dsa.project.dao;

import edu.upc.eetac.dsa.project.entity.ChecklistItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by carlos on 21/05/16.
 */
public class ChecklistItemDAOImpl implements ChecklistItemDAO {
    @Override
    public ChecklistItem createItem(String taskid, String title) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ChecklistItemDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);

            stmt.close();
            stmt = connection.prepareStatement(ChecklistItemDAOQuery.CREATE_ITEM);
            stmt.setString(1, id);
            stmt.setString(2, taskid);
            stmt.setString(3, title);

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
        return getChecklistItemById(id);
    }

    @Override
    public ChecklistItem checkItem(String itemid, String userid) throws SQLException {
        ChecklistItem item = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ChecklistItemDAOQuery.CHECK_ITEM);
            stmt.setString(1, userid);
            stmt.setString(2, itemid);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                item = getChecklistItemById(itemid);

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return item;
    }

    @Override
    public ChecklistItem getChecklistItemById(String id) throws SQLException {
        ChecklistItem item = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ChecklistItemDAOQuery.GET_ITEM_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                item = new ChecklistItem();
                item.setId(rs.getString("id"));
                item.setTaskid(rs.getString("taskid"));
                item.setTitle(rs.getString("title"));
                item.setChecked((rs.getBoolean("checked")));
                item.setUser_checked(rs.getString("user_checked"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return item;
    }
}
