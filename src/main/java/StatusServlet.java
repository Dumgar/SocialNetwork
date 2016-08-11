import dao.FriendsDAO;
import model.User;
import org.apache.log4j.Logger;

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
 * Servlet for friends requests
 *
 * @author Roman Dmitriev
 */
@WebServlet(name = "status", urlPatterns = "/status")
public class StatusServlet extends HttpServlet {
    /**
     * Log4j logger
     */
    private static Logger logger = Logger.getLogger(StatusServlet.class);
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
        User user = (User) req.getSession().getAttribute("user");
        int id = user.getId();

        try {
            List<User> list = friendsDAO.getStatusList(id);
            logger.info("Have gotten list of requests");
            req.setAttribute("statuslist", list);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Problems with getting list of requests");
        }
        req.getRequestDispatcher("status.jsp").forward(req, resp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        User loggedUser = (User) req.getSession().getAttribute("user");
        int loggedUserID = loggedUser.getId();
        String status = req.getParameter("status");
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            if (status.equals("accept")) {
                friendsDAO.acceptStatus(loggedUserID, id);
                logger.info("Accepted status from: " + id);
            } else if (status.equals("decline")) {
                friendsDAO.declineStatus(loggedUserID, id);
                logger.info("Declined status from: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Problems with status processing");
        }
        resp.sendRedirect(resp.encodeRedirectURL("status"));
    }
}
