package dao;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * Defines methods for all DAO implementations
 *
 * @author Roman Dmitriev
 */
public interface Search {

    /**
     * Make a search by given name
     * @param firstName Search string, given name
     * @return List of users, which names contains search string
     */
    List doSearchByName(String firstName);

    /**
     * Make a search by given string
     * @param request HTTPServletRequest
     * @param message Search String
     * @param sender Current user
     * @return List of found messages
     * @throws SQLException
     */
    List doMessageSearch(HttpServletRequest request, String message, int sender) throws SQLException;

}
