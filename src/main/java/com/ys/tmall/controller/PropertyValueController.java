package com.ys.tmall.controller;

import com.ys.tmall.bean.Propertyvalue;
import com.ys.tmall.common.api.CommonResult;
import com.ys.tmall.service.PropertyValueService;
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
@Api(description = "产品属性值")
@RestController
@RequestMapping(value = "/propertyvalue")
public class PropertyValueController {

        @Autowired
        private PropertyValueService productValueService;

        private Logger logger = LoggerFactory.getLogger(PropertyValueController.class);

        @ApiOperation(value = "更新属性值", notes = "根据id更新属性值")
        @PutMapping(value = "/{id}")
        public CommonResult updateProductValue(@RequestBody Propertyvalue propertyvalue){

            CommonResult commonResult;

            int count = productValueService.update(propertyvalue);
            if (count == 1) {
                commonResult = CommonResult.success(propertyvalue);
                logger.debug("updateProductValue success:{}", propertyvalue);
            } else {
                commonResult = CommonResult.failed();
                //显示类的日志信息
                logger.debug("updateProductValue failed:{}", propertyvalue);
            }

            return commonResult;
        }


        @ApiOperation(value = "获取产品属性值", notes = "根据id获取产品属性值信息")
        @GetMapping(value = "/{id}")
        public CommonResult<Propertyvalue> getProductValueImage(@PathVariable("id") int id) {

            Propertyvalue propertyvalue = productValueService.list(id);
            return CommonResult.success(propertyvalue);
        }

        @ApiOperation(value = "查询某一产品对应的全部产品属性值", notes = "根据产品id查询")
        @GetMapping(value = "/listAll")
        public CommonResult<List<Propertyvalue>> getProductValueImages(@RequestParam("pid") int pid) {
            List<Propertyvalue> listALL = productValueService.listByPid(pid);
            return CommonResult.success(listALL);
        }

}
