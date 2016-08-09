package dao;

import model.Message;
import model.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by romandmitriev on 09.08.16.
 */
public interface MessageDAO {
    
    List<User> getDialogues(int id) throws SQLException;
    void deleteDialogue(int sender, int receiver) throws SQLException;
    List<Message> getMessages(HttpServletRequest request, int sender, int receiver) throws SQLException;
    Message createMessage(String message, int sender, int receiver) throws SQLException;
    
}
