import dao.FriendsDAO;
import dao.UserDAO;
import model.User;
import org.apache.log4j.Logger;
import stuff.Helper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet responsible for friendships
 *
 * @author Roman Dmitriev
 */
@WebServlet(name = "friends", urlPatterns = "/friends")
public class FriendsServlet extends HttpServlet {
    /**
     * Log4j logger
     */
    private static Logger logger = Logger.getLogger(FriendsServlet.class);
    /**
     * FriendsDAO object
     */
    private FriendsDAO friendsDAO;

    /**
     * Initialize FriendsDAO object
     * @param config ServletConfig object
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        friendsDAO = (FriendsDAO) config.getServletContext().getAttribute("friendsDAO");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Helper.check(req);
        if (id == 0) {
            User user = (User) req.getSession().getAttribute("user");
            id = user.getId();
            req.setAttribute("statusbutton", 1);
        }
        try {
            List<User> list = friendsDAO.getFriends(id);
            logger.info("List of user's friends have gotten");
            req.setAttribute("friends", list);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error while getting list of friends");
            throw new ServletException("Error getting list of friends.");
        }
        req.getRequestDispatcher("friends.jsp").forward(req, resp);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        User loggedUser = (User) req.getSession().getAttribute("user");
        int loggedUserId = loggedUser.getId();
        String param = req.getParameter("status");
        int id = Helper.check(req);

        try {
            switch (param) {
                case "add":
                    friendsDAO.addFriend(loggedUserId, id);
                    logger.info("Friend request have sent to user id=" + id);
                    break;
                case "delete":
                    friendsDAO.deleteFriend(loggedUserId, id);
                    logger.info("Friend with id=" + id + " have been deleted");
                    break;
                case "decline":
                    friendsDAO.declineStatus(loggedUserId, id);
                    logger.info("Decline friend request from user id=" + id);
                    break;
                case "accept":
                    friendsDAO.acceptStatus(loggedUserId, id);
                    logger.info("Accept friend request from user id=" + id);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Problems in friend request process");
            throw new ServletException("Friend request process problem.");
        }
        resp.sendRedirect(resp.encodeRedirectURL("profile?id=" + id));
    }
}
