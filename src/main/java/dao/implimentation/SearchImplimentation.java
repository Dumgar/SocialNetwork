package dao.implimentation;

import dao.Search;
import model.Message;
import model.User;
import stuff.DBConnectionManager;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by romandmitriev on 07.08.16.
 */
public class SearchImplimentation implements Search {
    @Override
    public List doSearchByName(String name) {
        List<User> users = new ArrayList<>();
        try (Connection connection = DBConnectionManager.getConnection();
            PreparedStatement preparedStatement = createPSSearchByName(connection, name);
             ResultSet resultSet = preparedStatement.executeQuery()){

                while (resultSet.next()) {
                    User user = new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("firstname"),
                            resultSet.getString("lastname"), resultSet.getDate("birthdate").toLocalDate(),
                            resultSet.getString("country"), resultSet.getString("city"), resultSet.getInt("photoid"));
                    users.add(user);
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public List doMessageSearch(HttpServletRequest request, String message, int sender) {
        List<Message> list = new ArrayList<>();
        String firstName = null;
        String lastName = null;
        try (Connection connection = DBConnectionManager.getConnection();
             PreparedStatement ps = createPSMessage(connection, message, sender);
             ResultSet resultSet = ps.executeQuery()){
            if (resultSet.next()) {
                firstName = resultSet.getString("firstname");
                lastName = resultSet.getString("lastname");
            }
            resultSet.beforeFirst();
            while (resultSet.next()) {
                Message newMessage = new Message(resultSet.getInt(1), resultSet.getString("message"), resultSet.getInt("sender"), resultSet.getInt("receiver"));
                list.add(newMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (firstName != null && lastName != null) {
            request.setAttribute("firstname", firstName);
            request.setAttribute("lastname", lastName);
        }
        return list;
    }

    private PreparedStatement createPSSearchByName(Connection connection, String name) throws SQLException {
        name = name.replaceAll("\\W|\\d", "");
//        name = name.replace("%", "");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE MATCH (firstname, lastname) AGAINST (? in BOOLEAN MODE );");
        preparedStatement.setString(1, name + "*");
        return preparedStatement;
    }
    private PreparedStatement createPSMessage(Connection connection, String message, int sender) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("SELECT m.sender, m.receiver, m.message, u.firstname, u.lastname\n" +
                " FROM messages m JOIN Users u ON m.sender=u.id WHERE m.receiver=? AND MATCH (message) AGAINST (?)\n" +
                "UNION\n" +
                "SELECT m.sender, m.receiver, m.message, u.firstname, u.lastname\n" +
                " FROM messages m JOIN Users u ON m.receiver=u.id WHERE m.sender=? AND MATCH (message) AGAINST (?)");
        ps.setInt(1, sender);
        ps.setString(2, message);
        ps.setInt(3, sender);
        ps.setString(4, message);
        return ps;
    }
}
