package dao.implimentation;

import dao.FriendsDAO;
import model.User;
import stuff.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC DAO implementations
 *
 * @author Roman Dmitriev
 */
public class FriendsDAOImplimentation implements FriendsDAO {
    /**
     * {@inheritDoc}
     */
    @Override
    public void addFriend(int friending, int friended) throws SQLException {
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = createPSAdd(con, friending, friended)) {
            ps.executeUpdate();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFriend(int friending, int friended) throws SQLException {
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = createPSDelete(con, friending, friended)) {
            ps.executeUpdate();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void declineStatus(int friending, int friended) throws SQLException {

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = createPSDecline(con, friending, friended)) {
            ps.executeUpdate();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void acceptStatus(int friending, int friended) throws SQLException {

        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = createPSAccept(con, friending, friended)) {
            ps.executeUpdate();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getFriends(int friending) throws SQLException {
        List<User> list = new ArrayList<>();
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = createPSFriends(con, friending);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getStatusList(int friending) throws SQLException {

        List<User> list = new ArrayList<>();
        try (Connection con = DBConnectionManager.getConnection();
             PreparedStatement ps = createPSStatus(con, friending);
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

    /**
     * Creates Prepared Statement for new friends request
     * @param connection SQL connection
     * @param friending Current user
     * @param friended requested user
     * @return PreparedStatement Object
     * @throws SQLException
     */
    private PreparedStatement createPSAdd(Connection connection, int friending, int friended) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("INSERT INTO friends (friending, friended, status) VALUES (?, ?, 0)");
        ps.setInt(1, friending);
        ps.setInt(2, friended);
        return ps;
    }

    /**
     * Creates Prepared Statement for deleting user
     * @param connection SQL connection
     * @param friending Current user
     * @param friended Deleting user
     * @return Prepared Statement Object
     * @throws SQLException
     */
    private PreparedStatement createPSDelete(Connection connection, int friending, int friended) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM friends WHERE (friending=? AND friended=?) " +
                "OR (friending=? AND friended=?)");
        ps.setInt(1, friending);
        ps.setInt(2, friended);
        ps.setInt(3, friended);
        ps.setInt(4, friending);
        return ps;
    }

    /**
     * Creates Prepared Statement for declining friend request
     * @param connection SQL connection
     * @param friending Current user
     * @param friended Requesting user
     * @return Prepared Statement Object
     * @throws SQLException
     */
    private PreparedStatement createPSDecline(Connection connection, int friending, int friended) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("DELETE FROM friends WHERE friending=? AND friended=?");
        ps.setInt(1, friended);
        ps.setInt(2, friending);
        return ps;
    }

    /**
     * Creats Prepared Statement for accepting friend request
     * @param connection SQL connection
     * @param friending Current user
     * @param friended Requesting user
     * @return Prepared Statement Object
     * @throws SQLException
     */
    private PreparedStatement createPSAccept(Connection connection, int friending, int friended) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("UPDATE friends SET status=1 WHERE friending=? AND friended=?");
        ps.setInt(1, friended);
        ps.setInt(2, friending);
        return ps;
    }

    /**
     * Creates Prepared Statement for getting list of friends
     * @param connection SQL connection
     * @param friending Current user
     * @return Prepared Statement Object
     * @throws SQLException
     */
    private PreparedStatement createPSFriends(Connection connection, int friending) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT f.friending AS id, u.firstname, u.lastname, u.email, u.birthdate, u.country, u.city, u.photoid FROM friends f JOIN Users u ON f.friending = u.id WHERE f.friended=? AND f.status=1\n" +
                "UNION\n" +
                "SELECT f.friended, u.firstname, u.lastname, u.email, u.birthdate, u.country, u.city, u.photoid FROM friends f JOIN Users u ON f.friended = u.id WHERE f.friending=? AND f.status=1 ORDER BY firstname");
        ps.setInt(1, friending);
        ps.setInt(2, friending);
        return ps;
    }

    /**
     * Creates Prepared Statement for getting list of friend requests
     * @param connection SQL connection
     * @param friending Current user
     * @return Prepared Statement Object
     * @throws SQLException
     */
    private PreparedStatement createPSStatus(Connection connection, int friending) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("SELECT f.friending AS id, u.firstname, u.lastname, u.email, u.birthdate, u.country, u.city, u.photoid FROM friends f JOIN Users u ON f.friending = u.id WHERE f.friended=? AND f.status=0 ORDER BY firstname");
        ps.setInt(1, friending);
        return ps;
    }
}
