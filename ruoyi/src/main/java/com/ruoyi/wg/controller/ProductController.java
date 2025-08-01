package com.ruoyi.wg.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.wg.domain.Product;
import com.ruoyi.wg.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author hht
 * @date 2025/8/1 11:21
 * @description:
 */
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping("/list")
    public R<IPage<Product>> list(PageQuery pageQuery,Product product){
        return R.ok(
            productRepository
                .lambdaQuery()
                .like(StringUtils.isNotEmpty(product.getGoodsSn()),Product::getGoodsSn, product.getGoodsSn())
                .like(StringUtils.isNotEmpty(product.getName()),Product::getName, product.getName())
                .eq(StringUtils.isNotEmpty(product.getProductType()),Product::getProductType, product.getProductType())
                .page(pageQuery.build())
        );
    }

    @PostMapping
    public R<Void> add(@RequestBody Product productType){
        productRepository.save(productType);
        return R.ok();
    }

    @PutMapping
    public R<Void> update(@RequestBody Product productType){
        productRepository.updateById(productType);
        return R.ok();
    }

    @GetMapping("/info/{id}")
    public R<Product> info(@PathVariable Long id){
        return R.ok(productRepository.getById(id));
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id){
        productRepository.removeById(id);
        return R.ok();
    }
}
