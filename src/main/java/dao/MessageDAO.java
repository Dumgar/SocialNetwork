package dao;

import model.Message;
import model.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * Defines methods for all DAO implementations
 *
 * @author Roman Dmitriev
 */
public interface MessageDAO {
    /**
     * Make a list of dialogues
     * @param id current user
     * @return List of dialogues
     * @throws SQLException
     */
    List<User> getDialogues(int id) throws SQLException;

    /**
     * Deletes dialogue
     * @param sender Current user
     * @param receiver Interlocutor
     * @throws SQLException
     */
    void deleteDialogue(int sender, int receiver) throws SQLException;

    /**
     * Make list of messages with user
     * @param sender Current user
     * @param receiver Interlocutor
     * @return List of messages
     * @throws SQLException
     */
    List<Message> getMessages(HttpServletRequest request, int sender, int receiver) throws SQLException;

    /**
     * Creating message
     * @param message Message
     * @param sender current user that creates the message
     * @param receiver Interlocutor
     * @return Message Object
     * @throws SQLException
     */
    Message createMessage(String message, int sender, int receiver) throws SQLException;
    
}
