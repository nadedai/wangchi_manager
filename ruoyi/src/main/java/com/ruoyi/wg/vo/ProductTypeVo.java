package com.ruoyi.wg.vo;

import com.ruoyi.wg.domain.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hht
 * @date 2025/7/31 16:29
 * @description:
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductTypeVo extends ProductType {
    private List<ProductType> children = new ArrayList<>();
}
