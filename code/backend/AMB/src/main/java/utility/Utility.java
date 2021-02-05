package utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Request;
import java.io.IOException;
import java.util.List;

public class Utility {

    /*public static boolean paramExists(HttpRequest req, List<String> params) throws IOException {
        Map<String,String[]> parameterMap = req.getParameterMap();
        if(params.stream().anyMatch(parameter -> !parameterMap.containsKey(parameter))){
            resp.sendError(Response.Status.BAD_REQUEST);
            return false;
        }
        return true;
    }
    public static boolean paramExists(HttpServletRequest req, HttpServletResponse resp, String param) throws IOException {
        if(!req.getParameterMap().containsKey(param)){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter " + param + " not found");
            return false;
        }
        return true;
    }


    public static boolean paramIsEmpty(Request req , List<String> params) throws IOException{
        for (String param : params) {
            req.getMethod().
            if (req.getParameter(param).isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter " + param + " is empty");
                return true;
            }
        }
        return false;
    }
    public static boolean paramIsEmpty(HttpServletRequest req, HttpServletResponse resp, String param) throws IOException{
        if(req.getParameter(param).isEmpty()){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter " + param + " is empty");
            return true;
        }
        return false;
    }*/
}
