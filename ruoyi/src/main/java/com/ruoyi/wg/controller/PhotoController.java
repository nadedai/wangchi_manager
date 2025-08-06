package com.ruoyi.wg.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.service.OssService;
import com.ruoyi.wg.domain.Photo;
import com.ruoyi.wg.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author hht
 * @date 2025/8/4 9:54
 * @description:
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/photo")
public class PhotoController {
    private final PhotoRepository photoRepository;
    private final OssService ossService;

    @GetMapping("/list")
    @SaIgnore
    public R<IPage<Photo>> list(PageQuery pageQuery, Photo photo){
        return R.ok(
            photoRepository.lambdaQuery()
                .like(StringUtils.hasText(photo.getName()), Photo::getName, photo.getName())
                .eq(photo.getType() != null, Photo::getType, photo.getType())
                .eq(photo.getEnabled() != null, Photo::getEnabled, photo.getEnabled())
                .page(pageQuery.build())
                .convert(this::fullUrl)
        );
    }

    @PostMapping
    public R<Void> add(@RequestBody Photo photo){
        photoRepository.save(photo);
        return R.ok();
    }

    @PutMapping
    public R<Void> update(@RequestBody Photo photo){
        photoRepository.updateById(photo);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> del(@PathVariable Long id){
        photoRepository.removeById(id);
        return R.ok();
    }

    @GetMapping("/info/{id}")
    public R<Photo> info(@PathVariable Long id){
        return R.ok(photoRepository.getById(id));
    }

    private Photo fullUrl(Photo photo){
        photo.setRealUrl(ossService.selectUrlByIds(photo.getUrl()));
        return photo;
    }
}
