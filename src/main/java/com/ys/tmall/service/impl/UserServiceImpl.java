package com.ys.tmall.service.impl;

import com.ys.tmall.bean.User;
import com.ys.tmall.bean.UserExample;
import com.ys.tmall.mapper.UserMapper;
import com.ys.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User list(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int listUserID(User user) {
        String name = user.getName();

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(userExample);

        return users.get(0).getId();
    }

    @Override
    public List<User> listAll() {
        return userMapper.selectByExample(null);
    }

    @Override
    public boolean checkUserName(User user) {
        String userName = user.getName();

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameEqualTo(userName);

        return 0 == userMapper.countByExample(userExample);
    }

    @Override
    public boolean checkUserNameAndPwd(User user) {
        String userName = user.getName();
        String password = user.getPassword();

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameEqualTo(userName).andPasswordEqualTo(password);

        return 0 == userMapper.countByExample(userExample);
    }

    @Override
    public void add(User user) {
        userMapper.insertSelective(user);
    }

}
