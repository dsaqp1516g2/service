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
public class ChecklistitemCollection {
    @InjectLinks({})
    private List<Link> links;

    private List<ChecklistItem> checklistItems = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<ChecklistItem> getChecklistItems() {
        return checklistItems;
    }

    public void setChecklistItems(List<ChecklistItem> checklistItems) {
        this.checklistItems = checklistItems;
    }
}
