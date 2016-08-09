import dao.Search;
import model.Message;
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
 * Created by romandmitriev on 09.08.16.
 */
@WebServlet(name = "messagesearch", urlPatterns = "/messagesearch")
public class MessageSearchServlet extends HttpServlet {

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
        User user = (User) req.getSession().getAttribute("user");
        int sender = user.getId();
        List messages = null;

        String searchString = req.getParameter("messagesearch");
        try {
            messages = search.doMessageSearch(req, searchString, sender);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("messagesearch.jsp").forward(req, resp);
    }
}
