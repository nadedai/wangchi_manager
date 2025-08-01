package com.ruoyi.wg.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hht
 * @date 2025/7/31 12:11
 * @description:
 */
@EqualsAndHashCode(callSuper = true)
@TableName
@Data
public class ProductType extends BaseEntity {
    @TableId(value = "id")
    @Column
    private Long id;


    /**
     * 名称
     */
    @Column
    private String name;

    /**
     * 是否启用
     */
    @Column
    private Boolean enabled;

    /**
     * 父级ID
     */
    @Column
    private Long parentId;

    @Column
    private Integer orderNum;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    @Column(defaultValue = "0")
    private String delFlag;
}
