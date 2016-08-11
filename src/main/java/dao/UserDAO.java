package dao;

import model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Defines methods for all DAO implementations
 *
 * @author Roman Dmitriev
 */
public interface UserDAO {
    /**
     * Creating a user by given information
     * @param firstName First name
     * @param lastName Last name
     * @param email Email
     * @param password Password
     * @param birthDate Date of birth
     * @return User Object
     * @throws SQLException
     */
    User createUser(String firstName, String lastName, String email, String password, LocalDate birthDate) throws SQLException;

    /**
     * Updating user information
     * @param user User Object
     * @throws SQLException
     */
    void updateUser(User user) throws SQLException;

    /**
     * Getting user by email and password
     * @param email Email
     * @param password Password
     * @return User Object
     * @throws SQLException
     */
    User getUser(String email, String password) throws SQLException;

    /**
     * Give user object by id
     * @param friending Current user
     * @param friended User which profile will shown
     * @return User Object
     * @throws SQLException
     */
    User getUserById (HttpServletRequest request, int friending, int friended) throws SQLException;
}
