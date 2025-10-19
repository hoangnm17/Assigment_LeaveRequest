//package controller.auth;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import models.User;
//
//public class BaseRequiredAuthorizedController extends BaseRequiredAuthenticationController {
//
//    private boolean isAuthorized(HttpServletRequest request, User user) {
//        if (user.getRoles().isEmpty())//check if not yet fetch roles from db to user
//        {
//            RoleDBContext db = new RoleDBContext();
//            user.setRoles(db.getByUserId(user.getId()));
//            req.getSession().setAttribute("auth", user);
//        }
//        String url = req.getServletPath();
//        for (Role role : user.getRoles()) {
//            for (Feature feature : role.getFeatures()) {
//                if (feature.getUrl().equals(url)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    protected abstract void processPost(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException;
//
//    protected abstract void processGet(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException;
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
//        if (isAuthorized(request, user)) {
//            processPost(request, response, user);
//        } else {
//            response.getWriter().println("access denied!");
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
//        if (isAuthorized(request, user)) {
//            processPost(request, response, user);
//        } else {
//            response.getWriter().println("access denied!");
//        }
//    }
//
//}
