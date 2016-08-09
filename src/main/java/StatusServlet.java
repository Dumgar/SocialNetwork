import dao.FriendsDAO;
import model.User;

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
 * Created by romandmitriev on 08.08.16.
 */
@WebServlet(name = "status", urlPatterns = "/status")
public class StatusServlet extends HttpServlet {
    private FriendsDAO friendsDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        friendsDAO = (FriendsDAO) config.getServletContext().getAttribute("friendsDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        int id = user.getId();

        try {
            List<User> list = friendsDAO.getStatusList(id);
            req.setAttribute("statuslist", list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("status.jsp").forward(req, resp);
    }

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
            } else if (status.equals("decline")) {
                friendsDAO.declineStatus(loggedUserID, id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect(resp.encodeRedirectURL("status"));
    }
}
