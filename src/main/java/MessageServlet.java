import dao.FriendsDAO;
import dao.MessageDAO;
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
@WebServlet(name = "messages", urlPatterns = "/messages")
public class MessageServlet extends HttpServlet {

    private MessageDAO messageDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        messageDAO = (MessageDAO) config.getServletContext().getAttribute("messageDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        int sender = user.getId();
        int id = Helper.check(req);

        try {
            if (id != 0) {
                List<Message> list = messageDAO.getMessages(req, sender, id);
                req.setAttribute("id", id);
                req.setAttribute("messages", list);
            } else {
                List<User> list = messageDAO.getDialogues(sender);
                req.setAttribute("dialogues", list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("messages.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String status = req.getParameter("status");
        User user = (User) req.getSession().getAttribute("user");
        int sender = user.getId();
        int id = Integer.parseInt(req.getParameter("id"));
        if (status.equals("send")) {
            String message = req.getParameter("message");
            if (message != null && !message.equals("")) {
                try {
                    Message newMessage = messageDAO.createMessage(message, sender, id);
                } catch (SQLException e) {
                    e.printStackTrace();
                    resp.sendRedirect(resp.encodeRedirectURL("messages"));
                    return;
                }
            }
            resp.sendRedirect(resp.encodeRedirectURL("messages?id=" + id));
        } else {
            try {
                messageDAO.deleteDialogue(sender, id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resp.sendRedirect(resp.encodeRedirectURL("messages"));
        }
    }
}
