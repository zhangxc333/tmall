package com.ys.tmall.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @author yaosh
 * @data 2019/7/8
 */
@Controller
public class LogoutContorller {

//    @PostMapping("/login")
//    public String login(@RequestParam("username") String username,
//                      @RequestParam("password") String password,
//                      Map<String,String> map,
//                      HttpSession session){
//
//        if(!StringUtils.isEmpty(username) && "f".equals(password)){
//            session.setAttribute("username",username);
//            return "redirect:listCategory";
//
//        }else {
//            map.put("msg","账号密码错误");
//            return "index";
//        }
//    }

    @GetMapping("/forelogout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:index";
    }
}
