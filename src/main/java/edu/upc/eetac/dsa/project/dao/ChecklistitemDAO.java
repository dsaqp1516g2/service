package edu.upc.eetac.dsa.project.dao;

import edu.upc.eetac.dsa.project.entity.ChecklistItem;
import edu.upc.eetac.dsa.project.entity.ChecklistitemCollection;

import java.sql.SQLException;

/**
 * Created by carlos on 21/04/16.
 */
public interface ChecklistitemDAO {
    public ChecklistItem createChecklistitem() throws SQLException;
    public void checkChecklistitem() throws SQLException;
    public ChecklistitemCollection getChecklistitemsFromTask() throws SQLException;
}
