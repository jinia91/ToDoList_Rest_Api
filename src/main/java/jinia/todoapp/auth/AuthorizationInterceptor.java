package jinia.todoapp.auth;

import jinia.todoapp.exception.NotAuthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 인가 처리 인터셉터
 */
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Value("${auth.key}")
    private String API_KEY;

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
