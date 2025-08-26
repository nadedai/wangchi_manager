package com.ruoyi.wg.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.toolkit.MPJWrappers;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.service.OssService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.wg.domain.Product;
import com.ruoyi.wg.domain.ProductAttr;
import com.ruoyi.wg.repository.ProductAttrRepository;
import com.ruoyi.wg.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

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
    private final OssService ossService;
    private final ProductAttrRepository productAttrRepository;

    @GetMapping("/list")
    @SaIgnore
    public R<IPage<Product>> list(PageQuery pageQuery,Product product){
        if(StringUtils.contains(pageQuery.getOrderByColumn(), "price")){
            pageQuery.setOrderByColumn(StringUtils.replace(pageQuery.getOrderByColumn(), "price", "price+0"));
        }
        return R.ok(
            productRepository
                .lambdaQuery()
                .like(StringUtils.isNotEmpty(product.getGoodsSn()),Product::getGoodsSn, product.getGoodsSn())
                .like(StringUtils.isNotEmpty(product.getName()),Product::getName, product.getName())
                .likeRight(StringUtils.isNotEmpty(product.getProductType()),Product::getProductType, product.getProductType())
                .eq(product.getIsSale() != null, Product::getIsSale, product.getIsSale())
                .page( pageQuery.build(StringUtils.contains(pageQuery.getOrderByColumn(), "price")))
                .convert(this::fullUrl)
        );
    }

    @PostMapping
    public R<Void> add(@RequestBody Product productType){
        Assert.isTrue(!productRepository.findByGoodsSn(productType.getGoodsSn()).isPresent(), "产品编号已存在");
       // 保存产品属性
        productRepository.save(productType);
        if(CollUtil.isNotEmpty(productType.getAttrs())){
            validAttr(productType.getAttrs());
            productType.getAttrs().forEach(e->e.setProductId(productType.getId()));
            productAttrRepository.saveBatch(productType.getAttrs());
        }
        return R.ok();
    }

    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public R<Void> update(@RequestBody Product productType){
        Assert.isTrue(!productRepository.findByGoodsSn(productType.getGoodsSn(), productType.getId()).isPresent(), "产品编号已存在");
        if(CollUtil.isNotEmpty(productType.getAttrs())){
            validAttr(productType.getAttrs());
            productAttrRepository.removeByProductId(productType.getId());
            productType.getAttrs().forEach(e->e.setProductId(productType.getId()));
            productAttrRepository.saveBatch(productType.getAttrs());
        }
        productRepository.updateById(productType);
        return R.ok();
    }

    private void validAttr(List<ProductAttr> attrs){
        Assert.isTrue(attrs.stream()
            .noneMatch(item -> Collections.frequency(attrs, item.getAttrName()) > 1), "重复产品参数名"
        );
    }

    @GetMapping("/info/{id}")
    @SaIgnore
    public R<Product> info(@PathVariable Long id){
        Product product = productRepository.getById(id);
        product.setAttrs(productAttrRepository.listByProductId(id));
        return R.ok(fullUrl(product));
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id){
        productRepository.removeById(id);
        return R.ok();
    }

    private Product fullUrl(Product product){
        product.setPicRealUrl(ossService.selectUrlByIds(product.getPicUrl()));
        product.setGalleryRealUrl(ossService.selectUrlByIds(product.getGalleryRealUrl()));
        return product;
    }
}
