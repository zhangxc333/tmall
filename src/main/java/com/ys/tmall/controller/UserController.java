package com.ys.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ys.tmall.bean.User;
import com.ys.tmall.common.api.CommonResult;
import com.ys.tmall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
@Api(description = "用户信息")
@RestController
@RequestMapping(value = "/user")
public class UserController {

        @Autowired
        private UserService userService;

        private Logger logger = LoggerFactory.getLogger(UserController.class);

        @ApiOperation(value = "获取用户信息", notes = "根据id获取用户信息")
        @GetMapping(value = "/{id}")
        public CommonResult<User> getUser(@PathVariable("id") int id) {

            User user = userService.list(id);
            return CommonResult.success(user);
        }

        @ApiOperation(value = "查询全部用户信息", notes = "查询全部用户信息")
        @GetMapping(value = "/listAll")
        public CommonResult<List<User>> getUsers() {
            List<User> listALL = userService.listAll();
            return CommonResult.success(listALL);
        }

        //利用pagehelper插件分页查询
        @ApiOperation(value = "分页查询用户信息", notes = "根据请求参数（pn=页面）查询对应页面用户信息")
        @GetMapping(value = "/listPage")
        public CommonResult<PageInfo> getUser4Page(@RequestParam(value = "pn", defaultValue = "1") int pn) {
            PageHelper.startPage(pn, 5);
            List<User> list = userService.listAll();
            PageInfo info = new PageInfo(list);
            return CommonResult.success(info);
        }


}
