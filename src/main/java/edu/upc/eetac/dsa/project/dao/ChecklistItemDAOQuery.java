package edu.upc.eetac.dsa.project.dao;

/**
 * Created by carlos on 21/05/16.
 */
public interface ChecklistItemDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_ITEM = "insert into checklist_items (id, taskid, title) "+
            "values (unhex(?), unhex(?), ?)";
    public final static String CHECK_ITEM = "update checklist_items set checked=TRUE WHERE id=unhex(?)";
    public final static String GET_ITEM_BY_ID = "select hex(i.id) as id, hex(i.taskid) as taskid, i.user_checked as user_checked, i.title, i.checked from checklist_items i where i.id=unhex(?)";
    public final static String GET_ITEMS_FROM_TASK = "select hex(i.id) as id, hex(i.taskid) as taskid, i.user_checked as user_checked, i.title, i.checked from checklist_items i where i.taskid=unhex(?)";
}
