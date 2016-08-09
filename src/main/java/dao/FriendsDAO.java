package dao;

import model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by romandmitriev on 08.08.16.
 */
public interface FriendsDAO {
    void addFriend(int friending, int friended) throws SQLException;
    void deleteFriend(int friending, int friended) throws SQLException;
    void declineStatus(int friending, int friended) throws SQLException;
    void acceptStatus(int friending, int friended) throws SQLException;
    List<User> getFriends(int friending) throws SQLException;
    List<User> getStatusList(int friending) throws SQLException;
}
