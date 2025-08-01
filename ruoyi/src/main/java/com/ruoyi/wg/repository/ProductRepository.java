package com.ruoyi.wg.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.wg.domain.Product;
import com.ruoyi.wg.mapper.ProductMapper;
import org.springframework.stereotype.Repository;

/**
 * @author hht
 * @date 2025/8/1 11:21
 * @description:
 */

@Repository
public class ProductRepository extends ServiceImpl<ProductMapper, Product> {
}
