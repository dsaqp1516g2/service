package edu.upc.eetac.dsa.project.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by carlos on 16/04/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {
    @InjectLinks({})
    private List<Link> links;

    private String id;
    private String projectId;
    private long dateTimestamp;
    private String title;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public long getDateTimestamp() {
        return dateTimestamp;
    }

    public void setDateTimestamp(long dateTimestamp) {
        this.dateTimestamp = dateTimestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
