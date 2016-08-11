import dao.UserDAO;
import model.User;
import org.apache.log4j.Logger;
import stuff.Helper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet for user's profile processing
 *
 * @author Roman Dmitriev
 */
@WebServlet(name = "profile", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet{
    /**
     * log4j logger
     */
    private static Logger logger = Logger.getLogger(ProfileServlet.class);
    /**
     * UserDAO object
     */
    private UserDAO userDAO;

    /**
     * Initialize UserDAO object
     * @param config ServletConfig object
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDAO = (UserDAO) config.getServletContext().getAttribute("userDAO");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Helper.check(req);

        User loggedUser = (User) req.getSession().getAttribute("user");
        int loggedUserId = loggedUser.getId();

        if (loggedUserId != id) {
            User user;
            try {
                user = userDAO.getUserById(req, loggedUserId, id);
                if (user != null) {
                    logger.info("User have been found: " + user);
                    req.setAttribute("id", user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Problems with getting user by id");
            }
        }

        req.getRequestDispatcher("profile.jsp").forward(req, resp);

    }
}
