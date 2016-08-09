package dao;

import model.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by romandmitriev on 05.08.16.
 */
public interface UserDAO {
    User createUser(String firstName, String lastName, String email, String password, LocalDate birthDate) throws SQLException;
    void updateUser(User user) throws SQLException;
    User getUser(String email, String password) throws SQLException;
    User getUserById (HttpServletRequest request, int friending, int friended) throws SQLException;
}
