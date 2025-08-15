package com.ruoyi.wg.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.wg.domain.ProductAttr;
import com.ruoyi.wg.mapper.ProductAttrMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hht
 * @date 2025/8/13 9:01
 * @description:
 */

@Repository
public class ProductAttrRepository extends ServiceImpl<ProductAttrMapper, ProductAttr> {
    public List<ProductAttr> listByProductId(Long productId){
       return lambdaQuery().eq(ProductAttr::getProductId, productId).list();
    }
}
