package stuff;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Utill class with some supplementary methods
 *
 * @author Roman Dmitriev
 */
public class Helper {

    /**
     * Null and empty string checker
     * @param request HTTPServletRequest object
     * @return Int id. 0 if id parameter does not exist or null or empty string
     */
    public static int check(HttpServletRequest request) {
        String param = request.getParameter("id");
        if (param != null && !param.equals("")) {
            int id;
            try {
                id = Integer.parseInt(param);
            } catch (NumberFormatException e) {
                id = 0;
            }
            return id;
        }
        return 0;
    }
    public static boolean validate(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }
}
