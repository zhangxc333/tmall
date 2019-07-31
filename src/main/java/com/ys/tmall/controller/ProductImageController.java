package com.ys.tmall.controller;

import com.ys.tmall.bean.Productimage;
import com.ys.tmall.common.api.CommonResult;
import com.ys.tmall.common.util.ImageUtil;
import com.ys.tmall.service.ProductImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * @author yaosh
 * @data 2019/7/6
 */
@Api(description = "产品图片")
@RestController
@RequestMapping(value = "/productimage")
public class ProductImageController {

    private String productImageDiskPath =
            "E:\\project idea\\tmall\\tmall 2.0\\tmall-01\\src\\main\\resources\\static\\img\\productSingle";
    private String productImageDiskPath_middle =
            "E:\\project idea\\tmall\\tmall 2.0\\tmall-01\\src\\main\\resources\\static\\img\\productSingle_middle";
    private String productImageDiskPath_small =
            "E:\\project idea\\tmall\\tmall 2.0\\tmall-01\\src\\main\\resources\\static\\img\\productSingle_small";
    private String productDetailImageDiskPath =
            "E:\\project idea\\tmall\\tmall 2.0\\tmall-01\\src\\main\\resources\\static\\img\\productDetail";

    @Autowired
    private ProductImageService productImageService;

    private Logger logger = LoggerFactory.getLogger(ProductImageController.class);

    @ApiOperation(value = "新建产品图片", notes = "根据请求新建产品图片")
    @PostMapping(value = "/create")
    public CommonResult createProductImage(Productimage productimage, MultipartFile image) throws IOException {

        CommonResult commonResult;

        int count = productImageService.create(productimage);
        if (count == 1) {
            commonResult = CommonResult.success(productimage);

            if(productimage.getType().equals("single")){
                writeToDisk(productimage, image, productImageDiskPath);
            }
            if(productimage.getType().equals("detail")){
                writeToDisk(productimage, image, productDetailImageDiskPath);
            }

            logger.debug("createProductImage success:{}", productimage);
        } else {
            commonResult = CommonResult.failed();
            //显示类的日志信息
            logger.debug("createProductImage failed:{}", productimage);
        }

        return commonResult;
    }

    /**
     * 保存文件，直接以multipartFile形式
     * @param productimage 用于拿到id，生成文件名; 拿到type，用于分别保存
     * @param multipartFile 图片信息
     * @param imageDiskPath 指定的保存地址
     * @throws IOException
     */
    private void writeToDisk(Productimage productimage, MultipartFile multipartFile, String imageDiskPath) throws IOException {

        File imageFolder= new File(imageDiskPath);
        String fileName = productimage.getId()+".jpg";
        File file = new File(imageFolder, fileName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        multipartFile.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);

        if (productimage.getType().equals("single")){
            File f_small = new File(productImageDiskPath_small, fileName);
            File f_middle = new File(productImageDiskPath_middle, fileName);
            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }

    }


    @ApiOperation(value = "删除产品图片", notes = "根据id删除产品图片信息")
    @DeleteMapping(value = "/{id}")
    public CommonResult deleteProductImage(@PathVariable("id") int id,
                                           @RequestParam("type")String type) {

        CommonResult commonResult;

        int count = productImageService.delete(id);
        if (count == 1) {
            commonResult = CommonResult.success(id);
            if("single".equals(type)){
                File imageFolder= new File(productImageDiskPath);
                File imageFolder2= new File(productImageDiskPath_middle);
                File imageFolder3= new File(productImageDiskPath_small) ;

                deleteFromDisk(imageFolder,id);
                deleteFromDisk(imageFolder2,id);
                deleteFromDisk(imageFolder3,id);
            }
            if ("detail".equals(type)){
                File imageFolder= new File(productDetailImageDiskPath);
                deleteFromDisk(imageFolder,id);
            }

            logger.debug("createProductImage success:{}", id);
        } else {
            commonResult = CommonResult.failed();
            //显示类的日志信息
            logger.debug("createProductImage failed:{}", id);
        }

        return commonResult;
    }

    private void deleteFromDisk(File imageFolder, int id){
        File file = new File(imageFolder,id+".jpg");
        file.delete();
    }

    @ApiOperation(value = "获取产品图片", notes = "根据id获取产品图片信息")
    @GetMapping(value = "/{id}")
    public CommonResult<Productimage> getProductImage(@PathVariable("id") int id) {

        Productimage productimage = productImageService.list(id);
        return CommonResult.success(productimage);
    }

    @ApiOperation(value = "查询某一产品对应的全部产品图片，并根据图片类型（'single'、'detail'）细分图片", notes = "根据产品id查询")
    @GetMapping(value = "/listAll")
    public CommonResult<List<Productimage>> getProductImages(@RequestParam("pid") int pid,
                                                             @RequestParam("type") String type) {
        List<Productimage> listALL = productImageService.listAllByPidAndType(pid, type);
        return CommonResult.success(listALL);
    }

}
