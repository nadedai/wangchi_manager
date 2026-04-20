package com.ruoyi.wg.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.domain.vo.SysOssVo;
import com.ruoyi.system.repository.SysOssRepository;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysOssService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ruoyi.wg.commom.Constants.*;


/**
 * @author hht
 * @date 2025/8/18 16:37
 * @description:
 */
@RestController
@RequestMapping("/index")
@RequiredArgsConstructor
public class IndexController {
    private final ISysConfigService sysConfigService;
    private final ISysOssService sysOssService;

    @GetMapping("/swiperPhoto")
    @SaIgnore
    public R<List<String>> getSwiperPhoto(){
        String s = sysConfigService.selectConfigByKey(SWIPER_PHOTO_KEY);
        if(StringUtils.isNotEmpty(s)){
            return R.ok(sysOssService.listByIds(Arrays.stream(s.split(","))
                    .map(Long::parseLong).collect(Collectors.toList())).stream()
                .map(SysOssVo::getUrl).collect(Collectors.toList()));
        }
        return R.ok();
    }

    @GetMapping("/photoCover")
    @SaIgnore
    public R<String> getPhotoCover(){
        String s = sysConfigService.selectConfigByKey(PHOTO_COVER_KEY);
        if(StringUtils.isNotEmpty(s)){
            return R.ok(sysOssService.listByIds(Arrays.stream(s.split(","))
                    .map(Long::parseLong).collect(Collectors.toList())).stream()
                .map(SysOssVo::getUrl).collect(Collectors.joining(",")));
        }
        return R.ok();
    }

    @GetMapping("/shareCover")
    @SaIgnore
    public R<String> getShareCover(){
        String s = sysConfigService.selectConfigByKey(SHARE_COVER_KEY);
        if(StringUtils.isNotEmpty(s)){
            return R.ok(sysOssService.listByIds(Arrays.stream(s.split(","))
                    .map(Long::parseLong).collect(Collectors.toList())).stream()
                .map(SysOssVo::getUrl).collect(Collectors.joining(",")));
        }
        return R.ok();
    }

    @PutMapping("/swiperPhoto")
    public R<Void> setSwiperPhoto(String imgs){
        SysConfig config = sysConfigService.selectByKey(SWIPER_PHOTO_KEY);
        config.setConfigValue(imgs);
        sysConfigService.updateConfig(config);
        return R.ok();
    }

    @PutMapping("/photoCover")
    public R<Void> setPhotoCover(String img){
        SysConfig config = sysConfigService.selectByKey(PHOTO_COVER_KEY);
        config.setConfigValue(img);
        sysConfigService.updateConfig(config);
        return R.ok();
    }

    @PutMapping("/shareCover")
    public R<Void> setshareCover(String img){
        SysConfig config = sysConfigService.selectByKey(SHARE_COVER_KEY);
        config.setConfigValue(img);
        sysConfigService.updateConfig(config);
        return R.ok();
    }
}
