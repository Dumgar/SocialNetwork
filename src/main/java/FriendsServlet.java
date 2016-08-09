import dao.FriendsDAO;
import dao.UserDAO;
import model.User;
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
 * Created by romandmitriev on 08.08.16.
 */
@WebServlet(name = "friends", urlPatterns = "/friends")
public class FriendsServlet extends HttpServlet {
    private FriendsDAO friendsDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
         friendsDAO = (FriendsDAO) config.getServletContext().getAttribute("friendsDAO");
    }

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
            req.setAttribute("friends", list);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error getting list of user's friends.");
        }
        req.getRequestDispatcher("friends.jsp").forward(req, resp);

    }

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
                    break;
                case "delete":
                    friendsDAO.deleteFriend(loggedUserId, id);
                    break;
                case "decline":
                    friendsDAO.declineStatus(loggedUserId, id);
                    break;
                case "accept":
                    friendsDAO.acceptStatus(loggedUserId, id);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Friend request process problem.");
        }
        resp.sendRedirect(resp.encodeRedirectURL("profile?id=" + id));
//        req.getRequestDispatcher("friends.jsp").forward(req, resp);
    }
}
