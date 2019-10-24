package com.lang.product.service;

import cn.hutool.json.JSONUtil;
import com.lang.inter.product.ProductService;
import com.lang.model.entity.Product;
import com.lang.model.mapper.ProductMapper;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author faraway
 * @date 2019/3/4 15:44
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private static int a = 4;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product getProductById(String id) {
        logger.info("产品id是：{}", id);
        Product product = productMapper.selectByPrimaryKey(Integer.valueOf(id));
        logger.info("id为{}的产品详情：{}", id, JSONUtil.toJsonStr(product));
        return product;
    }

    /**
     * 添加产品并返回主键
     *
     * @param product
     * @return
     */
    @Override
    public Integer addProduct(Product product) {
        productMapper.insertSelective(product);
        Integer primaryId = product.getId();
        return primaryId;
    }
}
