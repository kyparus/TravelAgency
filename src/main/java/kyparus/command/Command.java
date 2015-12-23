package kyparus.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Created by yurii on 10.12.15.
 */

public interface Command {
    Logger logger = Logger.getLogger(Command.class);
    String apply(HttpServletRequest request, HttpServletResponse response);
}
