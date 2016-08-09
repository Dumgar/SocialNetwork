package stuff;

import dao.implimentation.FriendsDAOImplimentation;
import dao.implimentation.MessageDAOImplimentation;
import dao.implimentation.SearchImplimentation;
import dao.implimentation.UserDAOImplimentation;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by romandmitriev on 06.08.16.
 */
@WebListener
public class ContextListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        servletContext.setAttribute("userDAO", new UserDAOImplimentation());
        servletContext.setAttribute("search", new SearchImplimentation());
        servletContext.setAttribute("friendsDAO", new FriendsDAOImplimentation());
        servletContext.setAttribute("messageDAO", new MessageDAOImplimentation());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
