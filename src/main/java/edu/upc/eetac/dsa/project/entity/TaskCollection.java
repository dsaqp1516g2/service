package edu.upc.eetac.dsa.project.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos on 21/04/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskCollection {
    @InjectLinks({})
    private List<Link> links;

    private List<Task> tasks = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
