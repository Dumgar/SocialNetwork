import dao.UserDAO;
import model.User;
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
 * Created by romandmitriev on 06.08.16.
 */
@WebServlet(name = "profile", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet{
    private UserDAO userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDAO = (UserDAO) config.getServletContext().getAttribute("userDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Helper.check(req);

        User loggedUser = (User) req.getSession().getAttribute("user");
        int loggedUserId = loggedUser.getId();

        if (loggedUserId != id) {
            User user = null;
            try {
                user = userDAO.getUserById(req, loggedUserId, id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (user != null) {
                req.setAttribute("id", user);
            }
        }

        req.getRequestDispatcher("profile.jsp").forward(req, resp);

    }
}
