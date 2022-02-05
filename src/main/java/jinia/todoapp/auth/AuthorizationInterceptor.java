package jinia.todoapp.auth;

import jinia.todoapp.exception.NotAuthorizedException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationInterceptor implements HandlerInterceptor {

    private String API_KEY = "123";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("GET")){
            return HandlerInterceptor .super.preHandle(request, response, handler);
        }

        String userKey = request.getParameter("apikey");
        if(!API_KEY.equals(userKey)){
           throw new NotAuthorizedException("Not Authorized");
        }
        return HandlerInterceptor .super.preHandle(request, response, handler);
    }
}