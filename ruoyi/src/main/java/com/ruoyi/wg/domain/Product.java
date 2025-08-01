package com.ruoyi.wg.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
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
public class Product extends BaseEntity {
    @TableId(value = "id")
    @Column
    private Long id;

    /**
     * 商品编号
     */
    @Column
    private String goodsSn;

    /**
     * 产品类别
     */
    @Column
    private String productType;

    /**
     * 详情
     */
    @Column(type = MySqlTypeConstant.TEXT)
    private String detail;

    /**
     * 商品图片
     */
    @Column
    private String picUrl;

    /**
     * 商品画廊
     */
    @Column
    private String gallery;

    /**
     * 商品简介
     */
    @Column
    private String name;

    /**
     * 价格
     */
    @Column
    private String price;

    /**
     * 专柜价格
     */
    @Column
    private String counterPrice;

    /**
     * 是否在售
     */
    @Column
    private Boolean isSale;

    /**
     * 是否新品
     */
    @Column
    private Boolean isNew;

    @TableLogic
    @Column(defaultValue = "0")
    private String delFlag;
}
