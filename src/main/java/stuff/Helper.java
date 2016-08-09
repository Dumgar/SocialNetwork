package stuff;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by romandmitriev on 08.08.16.
 */
public class Helper {

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
}
