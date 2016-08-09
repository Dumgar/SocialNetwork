package dao;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by romandmitriev on 07.08.16.
 */
public interface Search {

    List doSearchByName(String firstName);

    List doMessageSearch(HttpServletRequest request, String message, int sender) throws SQLException;

}
