package com.mingink.article.api.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/1 14:25
 */
@Data
//@ApiModel(value="GorseItems Request对象", description="GorseItems Request实体对象")
public class GorseItemRequest implements Serializable {

    private static final long serialVersionUID = 1L;

//    @ApiModelProperty(value = "物品(小说)的唯一ID(BookId)，不能包含斜线")
    private String itemId;

//    @ApiModelProperty(value = "物品(小说)的标签信息，用于向推荐系统描述物品的特征")
    private String labels;

//    @ApiModelProperty(value = "物品(小说)的注释信息(description)，有助于在仪表盘浏览物品和推荐结果")
    private String comment;

//    @ApiModelProperty(value = "物品(小说)所属的品类，用于多品类推荐")
    private String categories;
}