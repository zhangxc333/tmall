package com.ys.tmall.service;

import com.ys.tmall.bean.User;

import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
public interface UserService {

    User list(int id);

    int listUserID(User user);

    List<User> listAll();

    boolean checkUserName(User user);

    boolean checkUserNameAndPwd(User user);

    void add(User user);

}
