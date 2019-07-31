package com.ys.tmall.component;

import com.ys.tmall.bean.Category;
import com.ys.tmall.bean.Orderitem;
import com.ys.tmall.bean.User;
import com.ys.tmall.service.CategoryService;
import com.ys.tmall.service.OrderItemService;
import com.ys.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/23
 */
@Component
public class OtherHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    CategoryService categoryService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        HttpSession session = request.getSession();

        //购物车总数量
        User user = (User) session.getAttribute("user");

        int totalNumber = 0;
        if(user != null){
            int userID = userService.listUserID(user);
            List<Orderitem> orderitems = orderItemService.listAllByUid(userID);
            for(Orderitem orderitem : orderitems){
                totalNumber += orderitem.getNumber();
            }
        }
        //分类
        List<Category> categories = categoryService.listAll();
        //servlet环境路径
        String contextPath = request.getServletContext().getContextPath();

        session.setAttribute("cartTotalItemNumber", totalNumber);
        request.getServletContext().setAttribute("categories_below_search", categories);
        request.getServletContext().setAttribute("contextPath", contextPath);


    }
}

