package dao.implimentation;

import dao.UserDAO;
import model.User;

import java.sql.*;
import java.time.LocalDate;
import stuff.DBConnectionManager;

import javax.servlet.http.HttpServletRequest;

import static java.lang.Integer.parseInt;

/**
 * Created by romandmitriev on 06.08.16.
 */
public class UserDAOImplimentation implements UserDAO {
    @Override
    public User createUser(String email, String password, String firstName, String lastName, LocalDate birthDate) throws SQLException {
        User user = null;

        try (Connection connection = DBConnectionManager.getConnection();
        PreparedStatement preparedStatement = createPSCreateUser(connection, email, password, firstName, lastName, birthDate);
        ResultSet resultSet = preparedStatement.getGeneratedKeys()){

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                user = new User(id, email, firstName, lastName, birthDate, null, null, 0);
            }
        }
        return user;
    }

    @Override
    public void updateUser(User user) throws SQLException {

    }

    @Override
    public User getUser(String email, String password) throws SQLException {
        User user = null;
        try (Connection connection = DBConnectionManager.getConnection();
            PreparedStatement preparedStatement = createPSUserByLogin(connection, email, password);
            ResultSet resultSet = preparedStatement.executeQuery()){
            if (resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("firstname"),
                        resultSet.getString("lastname"), resultSet.getDate("birthdate").toLocalDate(),
                        resultSet.getString("country"), resultSet.getString("city"), resultSet.getInt("photoid"));
            }
        }
        return user;
    }

    @Override
    public User getUserById(HttpServletRequest request, int friending, int friended) throws SQLException{
        User user = null;
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = createPreparedStatementId(con, friending, friended);
             ResultSet resultSet = ps.executeQuery()) {
            if (resultSet.next()) {
                user = new User(resultSet.getInt("id"), resultSet.getString("email"), resultSet.getString("firstname"),
                        resultSet.getString("lastname"), resultSet.getDate("birthdate").toLocalDate(),
                        resultSet.getString("country"), resultSet.getString("city"), resultSet.getInt("photoid"));
                String status = resultSet.getString("status");
                if (status != null) {
                    int attribute = parseInt(status);
                    request.setAttribute("status", attribute);
                    if (attribute == 0) {
                        request.setAttribute("senderId", parseInt(resultSet.getString("friending")));
                    }
                }
            }
        }
        return user;
    }

    private PreparedStatement createPSCreateUser(Connection connection, String email, String password, String firstName, String lastName, LocalDate birthDate) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users(email,password,firstname,lastname,birthdate) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,email);
        preparedStatement.setString(2,password);
        preparedStatement.setString(3,firstName);
        preparedStatement.setString(4,lastName);
        preparedStatement.setDate(5, Date.valueOf(birthDate));
        preparedStatement.execute();
        return preparedStatement;
    }

    private PreparedStatement createPSUserByLogin(Connection connection, String email, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, email, firstname, lastname, birthdate, country, city, photoid FROM Users WHERE email=? AND password=?");
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        return preparedStatement;
    }

    private PreparedStatement createPreparedStatementId(Connection con, int friending, int friended) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT u.id, u.firstname, u.lastname, u.email, u.birthdate, u.country, u.city, u.photoid, f.status, f.friending FROM Users u LEFT JOIN friends f ON (f.friended=u.id AND f.friending=?) OR (f.friended=? AND f.friending=u.id) WHERE u.id=?");
        ps.setInt(1, friending);
        ps.setInt(2, friending);
        ps.setInt(3, friended);
        return ps;
    }
}
