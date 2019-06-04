package com.lang.inter.product;

import com.lang.model.dto.ProductType;
import com.lang.model.entity.Product;

import java.util.List;
import java.util.Map;

/**
 * @author faraway
 * @date 2019/5/21 16:12
 */
public interface ProductService {

    Product getProductById(String id);

    List<ProductType> getDataStrByHttpClient(Map paramMap);

}
