package kyparus.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class CommandFactory {


    private static final HashMap<String, Command> commands = new HashMap<String, Command>();

    private CommandFactory() {
    }

    public static void addCommand(String name, Command command) {
        commands.put(name, command);
    }

    public static String executeCommand(HttpServletRequest request, HttpServletResponse response) {
        String forward = "/index.jsp";
        if (request.getParameter("submitLogin") != null) {
            forward = executeCommand("login", request, response);

        } else if (request.getParameter("submitRegister") != null) {
            forward = executeCommand("register client", request, response);

        } else if (request.getParameter("openRestTours") != null) {
            forward = executeCommand("get rest tours", request, response);

        } else if (request.getParameter("openExTours") != null) {
            forward =  executeCommand("get ex tours", request, response);

        } else if (request.getParameter("openShopTours") != null) {
            forward = executeCommand("get shop tours", request, response);

        } else if (request.getParameter("openClientTours") != null) {
            forward =  executeCommand("get client tours", request, response);

        } else if (request.getParameter("submitID") != null) {
            forward =  executeCommand("make tour hot", request, response);

        } else if (request.getParameter("openTour") != null) {
            forward = executeCommand("open tour", request, response);

        } else if(request.getParameter("order") != null){
            forward = executeCommand("add order",request,response);
        } else if(request.getParameter("submitDiscount") != null){
            forward = executeCommand("update discount",request,response);
        }

        return forward;
    }

    public static String executeCommand(String name, HttpServletRequest request, HttpServletResponse response) {
        //System.out.println("trying to exec " + name );
        if (commands.containsKey(name)) {
           return commands.get(name).apply(request, response);
        }
        return null;
    }


    static {
        addCommand("register client", new RegisterClient());
        addCommand("add order", new AddOrderCmd());
        addCommand("get client tours", new GetClientToursCmd());
        addCommand("get ex tours", new GetExToursCmd());
        addCommand("get rest tours", new GetRestToursCmd());
        addCommand("get shop tours", new GetShopToursCmd());
        addCommand("make tour hot", new MakeTourHotCmd());
        addCommand("login", new LoginCmd());
        addCommand("open tour", new OpenTourCmd());
        addCommand("update discount", new DiscountCmd());

    }


}
