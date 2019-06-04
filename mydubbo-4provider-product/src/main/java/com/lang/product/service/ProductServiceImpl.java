package com.lang.product.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.lang.common.util.HttpUtil;
import com.lang.inter.product.ProductService;
import com.lang.model.dto.ProductType;
import com.lang.model.entity.Product;
import com.lang.model.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

/**
 * @author faraway
 * @date 2019/3/4 15:44
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product getProductById(String id) {
        logger.info("产品id是：{}", id);
        Product product = productMapper.selectByPrimaryKey(Integer.valueOf(id));
        logger.info("id为{}的产品详情：{}", id, JSONUtil.toJsonStr(product));
        return product;
    }

    @Override
    public List<ProductType> getDataStrByHttpClient(Map paramMap) {
        //System.out.println("如果服务一秒钟还没响应，我就重试2次。。。。。。");
        //Thread.sleep(2000);
        String url = "http://10.3.2.148:8086/product/product/getProductTypeList";
        String postResult = HttpUtil.postJson(url, JSONUtil.toJsonStr(paramMap));
        if (StringUtils.isEmpty(postResult)) {
            return Lists.newArrayList();
        }
        // 字符串转JSON对象
        JSONObject jsonObject = JSONUtil.parseObj(postResult);
        // 获取JSON数组对象
        JSONArray jsonArray = jsonObject.getJSONArray("result");
        // JSON数组对象转集合
        List<ProductType> productTypeList = JSONUtil.toList(jsonArray, ProductType.class);

        //List<ProductType>转List<Map> 练习
        //List<Map<String,Object>> mapList = Lists.newArrayList();
        //for (ProductType productType : productTypeList) {
        //    Map<String,Object> map = BeanUtil.beanToMap(productType);
        //    mapList.add(map);
        //}

        return productTypeList;
    }

}
