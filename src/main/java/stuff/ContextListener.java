package stuff;

import dao.implimentation.FriendsDAOImplimentation;
import dao.implimentation.MessageDAOImplimentation;
import dao.implimentation.SearchImplimentation;
import dao.implimentation.UserDAOImplimentation;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

/**
 * ContextListener for creating and setting context attributes and logging configuration
 *
 * @author Roman Dmitriev
 */
@WebListener
public class ContextListener implements ServletContextListener{

    /**
     * Creating and setting DAO as context attributes, configures log4j at the web application initialization
     * @param servletContextEvent Object
     */

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        servletContext.setAttribute("userDAO", new UserDAOImplimentation());
        servletContext.setAttribute("search", new SearchImplimentation());
        servletContext.setAttribute("friendsDAO", new FriendsDAOImplimentation());
        servletContext.setAttribute("messageDAO", new MessageDAOImplimentation());



        String log4jConfig = servletContext.getInitParameter("log4j-config");
        if (log4jConfig == null) {
            System.err.println("No log4j-config init param, initializing log4j with BasicConfigurator");
            BasicConfigurator.configure();
        } else {
            String webAppPath = servletContext.getRealPath("/");
            String log4jProp = webAppPath + log4jConfig;
            File log4jConfigFile = new File(log4jProp);
            if (log4jConfigFile.exists()) {
                System.out.println("Initializing log4j with: " + log4jProp);
                DOMConfigurator.configure(log4jProp);
            } else {
                System.err.println(log4jProp + " file not found, initializing log4j with BasicConfigurator");
                BasicConfigurator.configure();
            }
        }
        System.out.println("log4j configured properly");
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
