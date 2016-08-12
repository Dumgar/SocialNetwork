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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;

import static stuff.Helper.validate;

/**
 * Servlet for registration form
 *
 * @author Roman Dmitriev
 */
@WebServlet(name = "registration", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet{
    /**
     * Log4j logger
     */
    private static Logger logger = Logger.getLogger(RegistrationServlet.class);
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
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        LocalDate birthDate = LocalDate.parse(req.getParameter("birthDate"));
        String incorrectEmailMsg = null;
        if (!validate(email)) {
            incorrectEmailMsg = "Enter real email";
        }
        if (incorrectEmailMsg != null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/registration.jsp");
            PrintWriter out = resp.getWriter();
            out.println("<font color=red>" + incorrectEmailMsg + "</font>");
            rd.include(req, resp);
            out.close();
        } else {

            try {
                User user = userDAO.createUser(email, password, firstName, lastName, birthDate);
                logger.info("User have been registered: " + user);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Problems with user's registration. Probably this email already exist");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/registration.jsp");
                PrintWriter out = resp.getWriter();
                String duplicate = "User with this email already exist";
                out.println("<font color=red>" + duplicate + "</font>");
                rd.include(req, resp);
                out.close();
            }
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.jsp");
        requestDispatcher.forward(req, resp);

    }

}
