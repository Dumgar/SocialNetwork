package dao;

import model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Defines methods for all implementations
 *
 * @author Roman Dmitriev
 */
public interface FriendsDAO {
    /**
     * Create firend request
     * @param friending User that send request
     * @param friended Requested user
     * @throws SQLException
     */
    void addFriend(int friending, int friended) throws SQLException;

    /**
     * Deletes user from friends
     * @param friending User that send request, current user
     * @param friended Requested user, friend who will be deleted
     * @throws SQLException
     */
    void deleteFriend(int friending, int friended) throws SQLException;

    /**
     * Declines request
     * @param friending User that send request, current user
     * @param friended Requested user
     * @throws SQLException
     */
    void declineStatus(int friending, int friended) throws SQLException;

    /**
     * Accepting request
     * @param friending User that send request
     * @param friended Requested user
     * @throws SQLException
     */
    void acceptStatus(int friending, int friended) throws SQLException;

    /**
     * Make a list of users
     * @param friending current user
     * @return List of users with friend status
     * @throws SQLException
     */
    List<User> getFriends(int friending) throws SQLException;

    /**
     * Make a list of user's incoming requests
     * @param friending current user
     * @return List of requests
     * @throws SQLException
     */
    List<User> getStatusList(int friending) throws SQLException;
}
