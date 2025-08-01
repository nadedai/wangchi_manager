package com.ruoyi.wg.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hht
 * @date 2025/7/31 15:20
 * @description:
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName
public class ProductAttr extends BaseEntity {
    @Column
    private Long id;

    @Column
    private Long productId;

    @Column
    private String attrName;

    @Column
    private String attrValue;

    @TableLogic
    @Column(defaultValue = "0")
    private String delFlag;
}
