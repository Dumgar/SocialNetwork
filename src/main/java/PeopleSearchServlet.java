import dao.Search;
import dao.UserDAO;
import model.User;

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
 * Created by romandmitriev on 07.08.16.
 */
@WebServlet(name = "peoplesearch", urlPatterns = "/peoplesearch")
public class PeopleSearchServlet extends HttpServlet {

    private Search search;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        search = (Search) config.getServletContext().getAttribute("search");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        String searchString = req.getParameter("peoplesearch");
        List users = search.doSearchByName(searchString);
        req.setAttribute("users", users);
        req.getRequestDispatcher("people.jsp").forward(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("people.jsp").forward(req, resp);
    }
}
