package com.ys.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ys.tmall.bean.Property;
import com.ys.tmall.common.api.CommonResult;
import com.ys.tmall.service.PropertyService;
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
@Api(description = "属性信息")
@RestController
@RequestMapping(value = "/property")
public class PropertyController {

        @Autowired
        private PropertyService propertyService;

        private Logger logger = LoggerFactory.getLogger(PropertyController.class);

        @ApiOperation(value = "新建属性", notes = "根据请求新建属性")
        @PostMapping(value = "/create")
        public CommonResult createProperty(@RequestBody Property property) {

            CommonResult commonResult;

            int count = propertyService.create(property);
            if (count == 1) {
                commonResult = CommonResult.success(property);
                logger.debug("createProperty success:{}", property);
            } else {
                commonResult = CommonResult.failed();
                //显示类的日志信息
                logger.debug("createProperty failed:{}", property);
            }

            return commonResult;
        }


        @ApiOperation(value = "更新属性", notes = "根据id更新属性信息")
        @PutMapping(value = "/{id}")
        public CommonResult updateProperty(@RequestBody Property property){

            CommonResult commonResult;

            int count = propertyService.update(property);
            if (count == 1) {
                commonResult = CommonResult.success(property);
                logger.debug("updateProperty success:{}", property);
            } else {
                commonResult = CommonResult.failed();
                //显示类的日志信息
                logger.debug("updateProperty failed:{}", property);
            }

            return commonResult;
        }

        @ApiOperation(value = "删除属性", notes = "根据id删除属性信息")
        @DeleteMapping(value = "/{id}")
        public CommonResult deleteProperty(@PathVariable("id") int id) {

            CommonResult commonResult;

            int count = propertyService.delete(id);
            if (count == 1) {
                commonResult = CommonResult.success(id);
                logger.debug("deleteProperty success:{}");
            } else {
                commonResult = CommonResult.failed();
                //显示类的日志信息
                logger.debug("deleteProperty failed:{}");
            }

            return commonResult;
        }

        @ApiOperation(value = "获取属性", notes = "根据id获取属性信息")
        @GetMapping(value = "/{id}")
        public CommonResult<Property> getProperty(@PathVariable("id") int id) {

            Property property = propertyService.list(id);
            return CommonResult.success(property);
        }

        @ApiOperation(value = "查询某一分类对应的全部属性", notes = "根据分类id查询")
        @GetMapping(value = "/listAll")
        public CommonResult<List<Property>> getPropertys(@RequestParam("cid") int cid) {
            List<Property> listALL = propertyService.listAllByCid(cid);
            return CommonResult.success(listALL);
        }

        //利用pagehelper插件分页查询
        @ApiOperation(value = "分页查询属性信息", notes = "根据请求参数（pn=页面，cid=外键，连接分类id）查询对应页面,对应分类的属性信息")
        @GetMapping(value = "/listPage")
        public CommonResult<PageInfo> getProperty4Page(@RequestParam(value = "pn", defaultValue = "1") int pn,
                                                       @RequestParam(value = "cid") int cid) {
            PageHelper.startPage(pn, 5);
            List<Property> list = propertyService.listAllByCid(cid);
            PageInfo info = new PageInfo(list);
            return CommonResult.success(info);
        }


}
