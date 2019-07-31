package com.ys.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ys.tmall.bean.Product;
import com.ys.tmall.common.api.CommonResult;
import com.ys.tmall.service.ProductService;
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
@Api(description = "产品信息")
@RestController
@RequestMapping(value = "/product")
public class ProductController {

        @Autowired
        private ProductService productService;

        private Logger logger = LoggerFactory.getLogger(ProductController.class);

        @ApiOperation(value = "新建产品", notes = "根据请求新建产品")
        @PostMapping(value = "/create")
        public CommonResult createProduct(@RequestBody Product product) {

            CommonResult commonResult;

            int count = productService.create(product);
            if (count == 1) {
                commonResult = CommonResult.success(product);
                logger.debug("createProduct success:{}", product);
            } else {
                commonResult = CommonResult.failed();
                //显示类的日志信息
                logger.debug("createProduct failed:{}", product);
            }

            return commonResult;
        }


        @ApiOperation(value = "更新产品", notes = "根据id更新产品信息")
        @PutMapping(value = "/{id}")
        public CommonResult updateProduct(@RequestBody Product product){

            CommonResult commonResult;

            int count = productService.update(product);
            if (count == 1) {
                commonResult = CommonResult.success(product);
                logger.debug("updateProduct success:{}", product);
            } else {
                commonResult = CommonResult.failed();
                //显示类的日志信息
                logger.debug("updateProduct failed:{}", product);
            }

            return commonResult;
        }

        @ApiOperation(value = "删除产品", notes = "根据id删除产品信息")
        @DeleteMapping(value = "/{id}")
        public CommonResult deleteProduct(@PathVariable("id") int id) {

            CommonResult commonResult;

            int count = productService.delete(id);
            if (count == 1) {
                commonResult = CommonResult.success(id);
                logger.debug("deleteProduct success:{}", id);
            } else {
                commonResult = CommonResult.failed();
                //显示类的日志信息
                logger.debug("deleteProduct failed:{}", id);
            }

            return commonResult;
        }

        @ApiOperation(value = "获取产品", notes = "根据id获取产品信息")
        @GetMapping(value = "/{id}")
        public CommonResult<Product> getProduct(@PathVariable("id") int id) {

            Product product = productService.list(id);
            return CommonResult.success(product);
        }

        @ApiOperation(value = "查询某一分类对应的全部产品", notes = "根据产品id查询")
        @GetMapping(value = "/listAll")
        public CommonResult<List<Product>> getProducts(@RequestParam("cid") int cid) {
            List<Product> listALL = productService.listAllByCid(cid);
            return CommonResult.success(listALL);
        }

        //利用pagehelper插件分页查询
        @ApiOperation(value = "分页查询产品信息", notes = "根据请求参数（pn=页面，cid=外键，连接分类id）查询对应页面,对应分类的产品信息")
        @GetMapping(value = "/listPage")
        public CommonResult<PageInfo> getProduct4Page(@RequestParam(value = "pn", defaultValue = "1") int pn,
                                                       @RequestParam(value = "cid") int cid) {
            PageHelper.startPage(pn, 5);
            List<Product> list = productService.listAllByCid(cid);
            PageInfo info = new PageInfo(list);
            return CommonResult.success(info);
        }


}
