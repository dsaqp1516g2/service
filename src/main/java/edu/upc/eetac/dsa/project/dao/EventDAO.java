package edu.upc.eetac.dsa.project.dao;

import edu.upc.eetac.dsa.project.entity.Event;
import edu.upc.eetac.dsa.project.entity.EventCollection;

import java.sql.SQLException;

/**
 * Created by carlos on 21/04/16.
 */
public interface EventDAO {
    public Event createEvent() throws SQLException;
    public boolean deleteEvent() throws SQLException;
    public Event getEventById() throws SQLException;
    public EventCollection getEventsFromProject() throws SQLException;
}
