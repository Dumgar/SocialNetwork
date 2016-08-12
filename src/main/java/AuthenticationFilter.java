import model.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter for forbidding direct page access
 * @author Roman Dmitriev
 */
@WebFilter(filterName = "authenticationfilter", urlPatterns = "/*")
public class AuthenticationFilter implements Filter {
    /**
     * log4j Logger object.
     */
    private Logger logger = Logger.getLogger(AuthenticationFilter.class);

    /**
     * Writes into log file about Filter initializing .
     *
     * @param fConfig FilterConfig object
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        logger.info("AuthenticationFilter initialized");
    }

    /**
     * Checks if session contains User attribute.
     * If true, allows the Filter to pass on the
     * request and response to the next entity in the chain.
     * Otherwise, redirects to login page.
     *
     * @param request  ServletRequest object
     * @param response ServletResponse object
     * @param chain    FilterChain object
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String url = req.getServletPath();

        logger.info("Requested Resource:" + url);

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null && !(url.endsWith("login") || url.endsWith("registration.jsp"))) {
            logger.error("Unauthorized access request");
            res.sendRedirect("login");
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        //close any resources here
    }
}
