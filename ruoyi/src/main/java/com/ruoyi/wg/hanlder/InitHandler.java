package com.ruoyi.wg.hanlder;

import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.wg.commom.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author hht
 * @date 2025/8/26 10:25
 * @description:
 */
@Component
@RequiredArgsConstructor
public class InitHandler implements ApplicationRunner {
    private final ISysConfigService sysConfigService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initSwiper();
        initPhotoCover();
        initShareCover();
    }

    private void  initSwiper(){
        SysConfig config = new SysConfig();
        config.setConfigName("轮播图配置");
        config.setConfigType("Y");
        config.setConfigKey(Constants.SWIPER_PHOTO_KEY);
        if(sysConfigService.checkConfigKeyUnique(config)){
            sysConfigService.insertConfig(config);
        }
    }

    private void  initPhotoCover(){
        SysConfig config = new SysConfig();
        config.setConfigName("图册封面配置");
        config.setConfigType("Y");
        config.setConfigKey(Constants.PHOTO_COVER_KEY);
        if(sysConfigService.checkConfigKeyUnique(config)){
            sysConfigService.insertConfig(config);
        }
    }

    private void  initShareCover(){
        SysConfig config = new SysConfig();
        config.setConfigName("分享封面配置");
        config.setConfigType("Y");
        config.setConfigKey(Constants.SHARE_COVER_KEY);
        if(sysConfigService.checkConfigKeyUnique(config)){
            sysConfigService.insertConfig(config);
        }
    }
}
