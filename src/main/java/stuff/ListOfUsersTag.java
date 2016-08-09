package stuff;

import model.User;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Created by romandmitriev on 08.08.16.
 */
public class ListOfUsersTag extends TagSupport{
    private List<User> list;

    public void setList(List<User> list) {
        this.list = list;
    }

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        try {
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size() - 1; i++) {
                    out.print(getString(list.get(i)) + "<hr>");
                }
                out.print(getString(list.get(list.size() - 1)));
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    private String getString(User user) {

        HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
        String encodeURL = response.encodeURL("profile");

        return "<a href=\"" + encodeURL + "?id=" + user.getId() +
                "\" class=\"names\">" + user.getFirstName() + " " + user.getLastName() +
                "</a>";
    }
}
