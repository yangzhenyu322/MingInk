package com.mingink.article.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Gorse Item 物品(小说)实体对象
 * @author ZenSheep
 * @since 2024-02-29
 */
@Data
@TableName("gorse_items")
@ApiModel(value="GorseItems对象", description="GorseItems实体对象")
public class GorseItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "物品(小说)的唯一ID(BookId)，不能包含斜线")
    @TableId(value = "item_id", type = IdType.NONE)
    private String itemId;

    @ApiModelProperty(value = "物品(小说)的时间戳，用于判断物品的新鲜度")
    @TableField("time_stamp")
    private LocalDateTime timeStamp;

    @ApiModelProperty(value = "物品(小说)的标签信息，用于向推荐系统描述物品的特征")
    @TableField("labels")
    private String labels;

    @ApiModelProperty(value = "物品(小说)的注释信息，有助于在仪表盘浏览物品和推荐结果")
    @TableField("comment")
    private String comment;

    @ApiModelProperty(value = "隐藏选项，设置为true后，该物品(小说)不再出现在推荐结果中")
    @TableField("is_hidden")
    private Boolean isHidden;

    @ApiModelProperty(value = "物品(小说)所属的品类，用于多品类推荐")
    @TableField("categories")
    private String categories;
}
