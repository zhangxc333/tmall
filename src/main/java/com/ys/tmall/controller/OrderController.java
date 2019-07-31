package com.ys.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ys.tmall.bean.Order;
import com.ys.tmall.common.api.CommonResult;
import com.ys.tmall.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
@Api(description = "订单信息")
@RestController
@RequestMapping(value = "/order")
public class OrderController {

        @Autowired
        private OrderService orderService;

        private Logger logger = LoggerFactory.getLogger(OrderController.class);

        @ApiOperation(value = "获取订单信息", notes = "根据id获取订单信息")
        @GetMapping(value = "/{id}")
        public CommonResult<Order> getOrder(@PathVariable("id") int id) {

            Order order = orderService.list(id);
            return CommonResult.success(order);
        }

        @ApiOperation(value = "更新订单信息", notes = "更新订单信息")
        @PutMapping(value = "/create")
        public CommonResult<Order> getOrder(@RequestBody Order order) {
            order.setDeliverydate(new Date());
            order.setStatus("waitConfirm");
            orderService.update(order);
            return CommonResult.success(order);
        }

        @ApiOperation(value = "查询全部订单信息", notes = "查询全部订单信息")
        @GetMapping(value = "/listAll")
        public CommonResult<List<Order>> getOrders() {
            List<Order> listALL = orderService.listAll();
            return CommonResult.success(listALL);
        }

        //利用pagehelper插件分页查询
        @ApiOperation(value = "分页查询订单信息", notes = "根据请求参数（pn=页面）查询对应页面订单信息")
        @GetMapping(value = "/listPage")
        public CommonResult<PageInfo> getOrder4Page(@RequestParam(value = "pn", defaultValue = "1") int pn) {
            PageHelper.startPage(pn, 5);
            List<Order> list = orderService.listAll();
            PageInfo info = new PageInfo(list);
            return CommonResult.success(info);
        }


}
