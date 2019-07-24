package com.lang.api.controller.product;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.lang.common.enums.StatusEnum;
import com.lang.common.response.R;
import com.lang.inter.product.ProductService;
import com.lang.model.entity.Product;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getProductById/{id}")
    @ResponseBody
    public R getProductById(@PathVariable String id) {
        try {
            //参数校验
            if (StrUtil.isBlank(id) || !NumberUtil.isNumber(id)) {
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
     * 添加产品并返回主键
     * @param product
     * @return
     */
    @PostMapping("/addProduct")
    @ResponseBody
    public R addProduct(@RequestBody Product product) {
        try {
            Integer primaryId = productService.addProduct(product);
            return R.ok(primaryId);
        } catch (Exception e) {
            logger.error("添加产品并返回主键失败！",e);
            return R.error(StatusEnum.FAIL);
        }
    }

}
