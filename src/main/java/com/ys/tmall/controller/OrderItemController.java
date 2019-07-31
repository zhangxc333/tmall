package com.ys.tmall.controller;

import com.ys.tmall.bean.Orderitem;
import com.ys.tmall.common.api.CommonResult;
import com.ys.tmall.service.OrderItemService;
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
@Api(description = "订单项信息")
@RestController
@RequestMapping(value = "/orderitem")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    private Logger logger = LoggerFactory.getLogger(OrderItemController.class);

    @ApiOperation(value = "获取订单项信息", notes = "根据id获取订单项信息")
    @GetMapping(value = "/{id}")
    public CommonResult<Orderitem> getOrderItem(@PathVariable("id") int id) {

        Orderitem orderitem = orderItemService.list(id);
        return CommonResult.success(orderitem);
    }

    @ApiOperation(value = "查询全部订单项信息", notes = "查询全部订单项信息")
    @GetMapping(value = "/listAll")
    public CommonResult<List<Orderitem>> getOrderItems(@RequestParam("oid") int oid) {
        List<Orderitem> listALL = orderItemService.listAllByOid(oid);
        return CommonResult.success(listALL);
    }

}
