package edu.upc.eetac.dsa.project.dao;

import edu.upc.eetac.dsa.project.entity.ChecklistItem;
import edu.upc.eetac.dsa.project.entity.ChecklistitemCollection;

import java.sql.SQLException;

/**
 * Created by carlos on 21/05/16.
 */
public interface ChecklistItemDAO {
    public ChecklistItem createItem(String taskid, String title) throws SQLException;
    public ChecklistItem checkItem(String itemid, String userid) throws SQLException;
    public ChecklistItem getChecklistItemById(String id) throws SQLException;
    public ChecklistitemCollection getChecklistItems(String taskid) throws SQLException;
}
