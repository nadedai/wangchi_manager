package com.ruoyi.wg.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.wg.domain.ProductType;
import com.ruoyi.wg.repository.ProductTypeRepository;
import com.ruoyi.wg.vo.ProductTypeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hht
 * @date 2025/7/31 15:32
 * @description:
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/productType")
public class ProductTypeController {
    private final ProductTypeRepository productTypeRepository;

    @GetMapping("/list")
    public R<List<ProductTypeVo>> list(){
        List<ProductType> list = productTypeRepository
            .lambdaQuery()
            .orderByAsc(ProductType::getOrderNum)
            .list();
        return R.ok(buildTreeList(list));
    }

    @GetMapping("/info/{id}")
    public R<ProductType> info(@PathVariable("id") Long id){
        return R.ok(productTypeRepository.getById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody ProductType type){
        productTypeRepository.save(type);
        return R.ok();
    }

    @PutMapping
    public R<Void> update(@RequestBody ProductType type){
        productTypeRepository.updateById(type);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable("id") Long id){
        productTypeRepository.removeById(id);
        return R.ok();
    }



    private List<ProductTypeVo> buildTreeList(List<ProductType> productTypeList){
        List<ProductTypeVo> vos = productTypeList
            .stream()
            .filter(p -> p.getParentId() == 0)
            .map(p -> BeanUtil.copyProperties(p, ProductTypeVo.class))
            .collect(Collectors.toList());
        for(ProductTypeVo vo : vos){
            vo.setChildren(productTypeList.stream().filter(v->v.getParentId().equals(vo.getId())).collect(Collectors.toList()));
        }
        return vos;
    }
}
