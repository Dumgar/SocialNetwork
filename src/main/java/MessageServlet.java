import dao.FriendsDAO;
import dao.MessageDAO;
import model.Message;
import model.User;
import org.apache.log4j.Logger;
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
 * Servlet for making messages dialogues
 *
 * @author Roman Dmitriev
 */
@WebServlet(name = "messages", urlPatterns = "/messages")
public class MessageServlet extends HttpServlet {

    /**
     * log4j logger
     */
    private static Logger logger = Logger.getLogger(MessageServlet.class);
    /**
     * MessageDAO object
     */
    private MessageDAO messageDAO;

    /**
     * Initialize MessageDAO object
     * @param config ServletConfig object
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        messageDAO = (MessageDAO) config.getServletContext().getAttribute("messageDAO");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        int sender = user.getId();
        int id = Helper.check(req);

        try {
            if (id != 0) {
                List<Message> list = messageDAO.getMessages(req, sender, id);
                logger.info("Have got list of user's messages with sender id=" + sender);
                req.setAttribute("id", id);
                req.setAttribute("messages", list);
            } else {
                List<User> list = messageDAO.getDialogues(sender);
                logger.info("Have got list of dialogues for user id=" + sender);
                req.setAttribute("dialogues", list);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Problems getting lists of messages or dialogues");
        }
        req.getRequestDispatcher("messages.jsp").forward(req, resp);
    }

    /**
     * {@inheritDoc}
     */
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
                    logger.info("New message have been created: " + newMessage);
                } catch (SQLException e) {
                    e.printStackTrace();
                    logger.error("Problems with creating new message");
                    resp.sendRedirect(resp.encodeRedirectURL("messages"));
                    return;
                }
            }
            resp.sendRedirect(resp.encodeRedirectURL("messages?id=" + id));
        } else {
            try {
                messageDAO.deleteDialogue(sender, id);
                logger.info("Dialogue have been deleted between " + sender + " and " + id);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Problems with deleting dialogue");
            }
            resp.sendRedirect(resp.encodeRedirectURL("messages"));
        }
    }
}
