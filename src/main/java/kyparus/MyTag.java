package kyparus;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by yurii on 06.12.15.
 */
public class MyTag extends SimpleTagSupport {

    StringWriter sw = new StringWriter();

    public void doTag()
            throws JspException, IOException {
        getJspBody().invoke(sw);
        String out = null;
        if (sw.toString().equals("ua")) {
            out = "Українська";
        } else if ((sw.toString().equals("en"))) {
            out = "English";
        }
        getJspContext().getOut().println(out);
    }
}
