import dao.Search;
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
import java.util.List;

/**
 * Servlet for searching people
 *
 * @author Roman Dmitriev
 */
@WebServlet(name = "peoplesearch", urlPatterns = "/peoplesearch")
public class PeopleSearchServlet extends HttpServlet {

    /**
     * log4j logger
     */
    private static Logger logger = Logger.getLogger(PeopleSearchServlet.class);
    /**
     * Search object
     */
    private Search search;

    /**
     * Initialize Search object
     * @param config ServletConfig object
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        search = (Search) config.getServletContext().getAttribute("search");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        String searchString = req.getParameter("peoplesearch");
        List users = search.doSearchByName(searchString);
        logger.info("Users have been found by name: " + searchString);
        req.setAttribute("users", users);
        req.getRequestDispatcher("people.jsp").forward(req, resp);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("people.jsp").forward(req, resp);
    }
}
