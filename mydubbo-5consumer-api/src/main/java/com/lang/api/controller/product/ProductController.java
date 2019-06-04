package com.lang.api.controller.product;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.lang.api.service.UploadService;
import com.lang.common.enums.StatusEnum;
import com.lang.common.response.R;
import com.lang.inter.product.ProductScheduler;
import com.lang.inter.product.ProductService;
import com.lang.model.dto.ProductType;
import com.lang.model.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author faraway
 * @date 2019/6/3 18:39
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    //@Reference(loadbalance = "random") //基于权重的随机负载均衡  [默认]
    //@Reference(loadbalance = "leastactive") //响应速度越快接受越多请求
    //@Reference(loadbalance = "roundrobin") //基于权重的轮询负载均衡

    @Reference
    private ProductService productService;

    //本地服务
    @Autowired
    private UploadService uploadService;

    @Reference
    private ProductScheduler productScheduler;

    @GetMapping("/getProductById/{id}")
    @ResponseBody
    public R getProductById(@PathVariable String id) {
        try {
            //参数校验
            if (StringUtils.isBlank(id) || !NumberUtil.isNumber(id)) {
                return R.error(StatusEnum.INVALID_PARAM);
            } else {
                Product product = productService.getProductById(id);
                if (product == null) {
                    return R.error(StatusEnum.NULL);
                }
                return R.ok(product);
            }
        } catch (Exception e) {
            logger.error("获取产品详情异常！", e);
            return R.error(StatusEnum.FAIL);
        }
    }

    /**
     * HttpClient测试
     */
    @GetMapping("/test/httpClient")
    @ResponseBody
    public R httpClient() {
        try {
            Map paramMap = Maps.newHashMap();
            paramMap.put("productType", "PXZCGL16");
            List<ProductType> productTypeList = productService.getDataStrByHttpClient(paramMap);
            if (productTypeList == null) {
                return R.error(StatusEnum.TIMEOUT);
            }
            return R.ok(productTypeList);
        } catch (Exception e) {
            logger.error("HttpClient测试失败！", e);
            return R.error(StatusEnum.FAIL);
        }
    }

    /**
     * 一个可以点击执行还能定时执行的任务
     *
     * @return
     */
    @GetMapping("scheduler/start")
    @ResponseBody
    public R start() {
        try {
            productScheduler.updProductStatus();
            return R.ok();
        } catch (Exception e) {
            logger.error("执行定时任务失败！", e);
            return R.error(StatusEnum.FAIL);
        }
    }

    /**
     * 上传产品图片到七牛云
     */
    @PostMapping("/uploadProductImg")
    @ResponseBody
    public R uploadImg(MultipartHttpServletRequest request) {
        try {
            MultipartFile imageFile = request.getFile("image");
            if (imageFile == null) {
                return R.error(StatusEnum.NULL);
            }
            String fileName = imageFile.getOriginalFilename();
            String type = "product";
            uploadService.uploadProductImg(fileName, type, imageFile);
            return R.ok();
        } catch (Exception e) {
            logger.error("图片上传失败！", e);
            return R.error(StatusEnum.FAIL);
        }
    }

}
