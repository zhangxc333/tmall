package com.ys.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ys.tmall.bean.Category;
import com.ys.tmall.common.api.CommonResult;
import com.ys.tmall.common.util.ImageUtil;
import com.ys.tmall.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
@Api(description = "分类信息")
@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    //图片保存地址
    private String imageDiskPath =
            "E:\\project idea\\tmall\\tmall 2.0\\tmall-01\\src\\main\\resources\\static\\img\\category";

    @Autowired
    private CategoryService categoryService;

    private Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @ApiOperation(value = "新建分类", notes = "根据请求体新建分类")
    @PostMapping(value = "/create")
    public CommonResult createCategory(Category category, MultipartFile image) throws IOException {

        CommonResult commonResult;

        int count = categoryService.create(category);
        writeToDisk(category.getId(), image, imageDiskPath);
        if (count == 1) {
            commonResult = CommonResult.success(category);
            logger.debug("createCategory success:{}", category);
        } else {
            commonResult = CommonResult.failed();
            //显示类的日志信息
            logger.debug("createCategory failed:{}", category);
        }

        return commonResult;
    }

    /**
     * 保存文件，直接以multipartFile形式
     * @param categoryId  用于拿到id，生成文件名
     * @param multipartFile  图片信息
     * @param imageDiskPath 指定的保存地址
     * @throws IOException
     */
    private void writeToDisk(int categoryId, MultipartFile multipartFile, String imageDiskPath) throws IOException {

        File imageFolder= new File(imageDiskPath);
        File file = new File(imageFolder,categoryId+".jpg");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        multipartFile.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);

    }

    @ApiOperation(value = "更新分类", notes = "根据id更新分类信息")
    @PutMapping(value = "/{id}")
    public CommonResult updateCategory(Category category, MultipartFile image, String imageDiskPath) throws IOException {

        CommonResult commonResult;

        int count = categoryService.update(category);
        if (count == 1) {
            commonResult = CommonResult.success(category);
            if(image!=null) {
                writeToDisk(category.getId(), image, imageDiskPath);
            }
            logger.debug("updateCategory success:{}", category);
        } else {
            commonResult = CommonResult.failed();
            //显示类的日志信息
            logger.debug("updateCategory failed:{}", category);
        }

        return commonResult;
    }

    @ApiOperation(value = "删除分类", notes = "根据id删除分类信息")
    @DeleteMapping(value = "/{id}")
    public CommonResult deleteCategory(@PathVariable("id") int id) {

        CommonResult commonResult;

        int count = categoryService.delete(id);
        if (count == 1) {
            commonResult = CommonResult.success(id);
            File  imageFolder= new File(imageDiskPath);
            File file = new File(imageFolder,id+".jpg");
            file.delete();
            logger.debug("deleteCategory success:{}");
        } else {
            commonResult = CommonResult.failed();
            //显示类的日志信息
            logger.debug("deleteCategory failed:{}");
        }

        return commonResult;
    }

    @ApiOperation(value = "获取分类", notes = "根据id获取分类信息")
    @GetMapping(value = "/{id}")
    public CommonResult<Category> getCategory(@PathVariable("id") int id) {

        Category category = categoryService.list(id);
        return CommonResult.success(category);
    }

    @ApiOperation(value = "查询全部分类", notes = "查询全部分类信息")
    @GetMapping(value = "/listAll")
    public CommonResult<List<Category>> getCategorys() {
        List<Category> listALL = categoryService.listAll();
        return CommonResult.success(listALL);
    }

    //利用pagehelper插件分页查询
    @ApiOperation(value = "分页查询分类信息", notes = "根据请求参数（pn=页面）查询对应页面分类信息")
    @GetMapping(value = "/listPage")
    public CommonResult<PageInfo> getCategory4Page(@RequestParam(value = "pn", defaultValue = "1") int pn) {
        PageHelper.startPage(pn, 5);
        List<Category> list = categoryService.listAll();
        PageInfo info = new PageInfo(list);
        return CommonResult.success(info);
    }

}
