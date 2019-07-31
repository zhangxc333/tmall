package com.ys.tmall.component;

import com.ys.tmall.bean.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author yaosh
 * @data 2019/7/8
 */
public class MyLoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        if(user == null){
            request.getRequestDispatcher("login").forward(request,response);
            return false;
        }else {
            return true;
        }


    }
}
