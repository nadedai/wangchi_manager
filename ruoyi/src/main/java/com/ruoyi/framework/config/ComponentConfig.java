package com.ruoyi.framework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author hht
 * @date 2025/7/31 15:15
 * @description:
 */
@Configuration
@ComponentScan( {"com.gitee.sunchenbin.mybatis.actable.manager.*", "com.ruoyi.*"})
public class ComponentConfig {
}
