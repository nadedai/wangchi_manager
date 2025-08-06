package com.ruoyi.wg.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.wg.domain.PhotoType;
import com.ruoyi.wg.repository.PhotoTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hht
 * @date 2025/8/4 9:26
 * @description:
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/photoType")
public class PhotoTypeController {
    private final PhotoTypeRepository photoTypeRepository;

    @GetMapping("/list")
    @SaIgnore
    public R<List<PhotoType>> list(PhotoType photoType){
        return R.ok(photoTypeRepository
            .lambdaQuery()
            .eq(photoType.getEnabled() != null, PhotoType::getEnabled, photoType.getEnabled())
            .orderByAsc(PhotoType::getOrderNum)
            .list());
    }

    @GetMapping("/info/{id}")
    public R<PhotoType> info(@PathVariable Long id){
        return R.ok(photoTypeRepository.getById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody PhotoType photoType){
        photoTypeRepository.save(photoType);
        return R.ok();
    }

    @PutMapping
    public R<Void> update(@RequestBody PhotoType photoType){
        photoTypeRepository.updateById(photoType);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> del(@PathVariable Long id){
        photoTypeRepository.removeById(id);
        return R.ok();
    }

}
