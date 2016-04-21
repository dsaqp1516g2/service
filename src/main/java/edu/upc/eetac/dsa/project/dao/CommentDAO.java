package edu.upc.eetac.dsa.project.dao;

import edu.upc.eetac.dsa.project.entity.Comment;
import edu.upc.eetac.dsa.project.entity.CommentCollection;

import java.sql.SQLException;

/**
 * Created by carlos on 21/04/16.
 */
public interface CommentDAO {
    //Hay que incluir el parent type como parametro para poder gestionar los dos tipos de comentarios de la misma forma
    public Comment createComment() throws SQLException;
    public Comment updateComment() throws SQLException;
    public boolean deleteComment() throws SQLException;
    public Comment getCommentById() throws SQLException;
    public CommentCollection getCommentsFromParent() throws SQLException;
}
