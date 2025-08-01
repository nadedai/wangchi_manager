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
 * @date 2025/7/31 14:41
 * @description:
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName
public class PhotoType extends BaseEntity {
    @TableId(value = "id")
    @Column
    private Long id;

    @Column
    private String name;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

}
