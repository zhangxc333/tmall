package com.ys.tmall.config;

import com.ys.tmall.component.OtherHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yaosh
 * @data 2019/7/6
 */
@Configuration
public class MyWebMvcConfig {

    @Autowired
    OtherHandlerInterceptor otherHandlerInterceptor;

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){

        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer(){

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                //后台页面跳转逻辑
                //分类页面
                registry.addViewController("/admin").setViewName("admin/listCategory");
                registry.addViewController("/listCategory").setViewName("admin/listCategory");
                registry.addViewController("/editCategory").setViewName("admin/editCategory");
                //属性页面
                registry.addViewController("/listProperty").setViewName("admin/listProperty");
                registry.addViewController("/editProperty").setViewName("admin/editProperty");
                //产品页面
                registry.addViewController("/editProduct").setViewName("admin/editProduct");
                registry.addViewController("/listProduct").setViewName("admin/listProduct");
                //产品图片
                registry.addViewController("/listProductIamge").setViewName("admin/listProductIamge");
                //产品属性值
                registry.addViewController("/editPropertyValue").setViewName("admin/editPropertyValue");
                //用户信息
                registry.addViewController("/listUser").setViewName("admin/listUser");
                //订单信息
                registry.addViewController("/listOrder").setViewName("admin/listOrder");

                //前台页面跳转逻辑
                //主页跳转
                registry.addViewController("/").setViewName("index");
                registry.addViewController("/index").setViewName("index");
                //注册页面
                registry.addViewController("/register").setViewName("fore/register");
                registry.addViewController("/registerSuccess").setViewName("fore/registerSuccess");
                //登入页面
                registry.addViewController("/login").setViewName("fore/login");
                //登出页面、见LoginContorller
                //产品页面
                registry.addViewController("/product").setViewName("fore/product");
                //分类页面
                registry.addViewController("/category").setViewName("fore/category");
                //搜索页面
                registry.addViewController("/search").setViewName("fore/search");

                //结算页面
                registry.addViewController("/buy").setViewName("fore/buy");
                //购物车页面
                registry.addViewController("/cart").setViewName("fore/cart");
                //支付页面
                registry.addViewController("/alipay").setViewName("fore/alipay");
                //支付完成页面
                registry.addViewController("/payed").setViewName("fore/payed");
                //订单页面
                registry.addViewController("/bought").setViewName("fore/bought");
                //确认收货
                registry.addViewController("/confirmPay").setViewName("fore/confirmPay");
                //确认收货成功
                registry.addViewController("/orderConfirmed").setViewName("fore/orderConfirmed");
                //评论
                registry.addViewController("/review").setViewName("fore/review");


            }



            //拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {

//                String[] path = new String[]{
//                        //不需要登入，页面跳转
//                        "/", "/index", "/register", "/registerSuccess", "/login","/product","/category","/search",
//                        //不需要登入，json数据
//                        "/forehome", "/foreregister", "/forelogin","/forelogout", "/foreproduct/**", "/forecheckLogin", "/forecategory/**", "/foresearch",
//                };
//
//                registry.addInterceptor(new MyLoginHandlerInterceptor()).addPathPatterns("/**")
//                        .excludePathPatterns(path)
//                        .excludePathPatterns("/css/**","/img/**","/js/**");

                //拦截器中使用了注解的方式自动注入，所以拦截器也需要通过注解的方式注入，而不能使用new的方式
                registry.addInterceptor(otherHandlerInterceptor).addPathPatterns("/**");
            }

        };



        return webMvcConfigurer;
    }
}
