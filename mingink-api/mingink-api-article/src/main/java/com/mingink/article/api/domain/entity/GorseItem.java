package com.mingink.article.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Gorse物品(小说)对象")
public class GorseItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "item_id", type = IdType.NONE)
    @Schema(description = "物品(小说)的唯一ID(BookId)，不能包含斜线")
    private String itemId;

    @TableField("time_stamp")
    @Schema(description = "物品(小说)的时间戳，用于判断物品的新鲜度")
    private LocalDateTime timeStamp;

    @TableField("labels")
    @Schema(description = "物品(小说)的标签信息，用于向推荐系统描述物品的特征")
    private String labels;

    @TableField("comment")
    @Schema(description = "物品(小说)的注释信息，有助于在仪表盘浏览物品和推荐结果")
    private String comment;

    @TableField("is_hidden")
    @Schema(description = "隐藏选项，设置为true后，该物品(小说)不再出现在推荐结果中")
    private Boolean isHidden;

    @TableField("categories")
    @Schema(description = "物品(小说)所属的品类，用于多品类推荐")
    private String categories;
}
