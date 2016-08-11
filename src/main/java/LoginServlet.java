import dao.UserDAO;
import model.User;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Servlet for authentication
 *
 * @author Roman Dmitriev
 */
@WebServlet(name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    /**
     * log4j logger
     */
    private static Logger logger = Logger.getLogger(LoginServlet.class);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html; charset=utf-8");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userDAO.getUser(email, password);
            if (user != null) {
                logger.info("User found " + user);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(response.encodeRedirectURL("profile"));
            } else {
                logger.error("User not found. Email=" + email);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
                PrintWriter out = response.getWriter();
                logger.error("User not found with email=" + email);
                String userNotFoundMessage = "No user found with this email";
                out.println("<font color=red>" + userNotFoundMessage + "</font>");
                rd.include(request, response);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Getting user by login info error");
        }

    }


}