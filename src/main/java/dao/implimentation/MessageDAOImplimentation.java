package dao.implimentation;

import dao.MessageDAO;
import model.Message;
import model.User;
import stuff.DBConnectionManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImplimentation implements MessageDAO {
    @Override
    public List<User> getDialogues(int id) throws SQLException {
        List<User> list = new ArrayList<>();
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = createPSDialogues(con, id);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("firstname"),
                        resultSet.getString("lastname"), resultSet.getDate("birthdate").toLocalDate(),
                        resultSet.getString("country"), resultSet.getString("city"), resultSet.getInt("photoid"));
                list.add(user);
            }
        }
        return list;
    }

    @Override
    public void deleteDialogue(int sender, int receiver) throws SQLException {
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = createPSDelete(con, sender, receiver)) {
            ps.executeUpdate();
        }
    }

    @Override
    public List<Message> getMessages(HttpServletRequest request, int sender, int receiver) throws SQLException {
        List<Message> list = new ArrayList<>();
        String firstName = null;
        String lastName = null;
        try (Connection connection = DBConnectionManager.getConnection();
            PreparedStatement ps = createPSGetMessages(connection, sender, receiver);
            ResultSet resultSet = ps.executeQuery()){
            if (resultSet.next()) {
                firstName = resultSet.getString("firstname");
                lastName = resultSet.getString("lastname");
            }
            resultSet.beforeFirst();
            while (resultSet.next()) {
                Message message = new Message(resultSet.getInt(1), resultSet.getString("message"), resultSet.getInt("sender"), resultSet.getInt("receiver"));
                list.add(message);
            }
        }
        if (firstName != null && lastName != null) {
            request.setAttribute("firstname", firstName);
            request.setAttribute("lastname", lastName);
        }
        return list;
    }

    @Override
    public Message createMessage(String message, int sender, int receiver) throws SQLException {

        Message newMessage = null;
        try(Connection connection = DBConnectionManager.getConnection();
            PreparedStatement ps = createPSNewMessage(connection, message, sender, receiver);
            ResultSet resultSet = ps.getGeneratedKeys()) {
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                newMessage = new Message(id, message, sender, receiver);
            }
        }
        return newMessage;
    }

    private PreparedStatement createPSDialogues(Connection connection, int sender) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("SELECT m.sender AS id, u.firstname, u.lastname, u.email,\n" +
                " u.birthdate, u.country, u.city, u.photoid FROM messages m JOIN Users u ON m.sender = u.id WHERE m.receiver=? \n" +
                "UNION\n" +
                "SELECT m.receiver AS id, u.firstname, u.lastname, u.email, u.birthdate, u.country, u.city, u.photoid FROM messages m\n " +
                "JOIN Users u ON m.receiver = u.id WHERE m.sender=? ORDER BY firstname");
        ps.setInt(1, sender);
        ps.setInt(2, sender);
        return ps;
    }
    private PreparedStatement createPSDelete(Connection connection, int sender, int receiver) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("DELETE FROM messages WHERE (sender=? AND receiver=?) \n" +
                "OR (sender=? AND receiver=?)");
        ps.setInt(1, sender);
        ps.setInt(2, receiver);
        ps.setInt(3, receiver);
        ps.setInt(4, sender);
        return ps;
    }
    private PreparedStatement createPSGetMessages(Connection connection, int sender, int receiver) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("SELECT m.id, m.message, m.sender, m.receiver, u.firstname, u.lastname\n" +
                "FROM messages m JOIN users u ON m.receiver = u.id WHERE m.sender=? AND m.receiver=?\n" +
                "UNION\n" +
                "SELECT m.id, m.message, m.sender, m.receiver, u.firstname, u.lastname FROM messages m\n" +
                "JOIN users u ON m.sender = u.id WHERE m.sender=? AND m.receiver=? ORDER BY id");
        ps.setInt(1, sender);
        ps.setInt(2, receiver);
        ps.setInt(3, receiver);
        ps.setInt(4, sender);
        return ps;
    }

    private PreparedStatement createPSNewMessage(Connection connection, String message, int sender, int receiver) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("INSERT INTO messages(message, sender, receiver) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, message);
        ps.setInt(2, sender);
        ps.setInt(3, receiver);
        ps.executeUpdate();
        return ps;
    }
}
