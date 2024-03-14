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
 * Gorse Feedback 反馈实体类
 * @author ZenSheep
 * @since 2024-02-29
 */
@Data
@TableName("gorse_feedback")
@Schema(description = "Gorse反馈")
public class GorseFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "feedback_type", type = IdType.NONE)
    @Schema(description = "反馈类型，包含like、star、read等")
    private String feedbackType;

    @TableField("user_id")
    @Schema(description = "用户ID")
    private String userId;

    @TableField("item_id")
    @Schema(description = "物品(小说)ID")
    private String itemId;

    @TableField("time_stamp")
    @Schema(description = "时间戳")
    private LocalDateTime timeStamp;

    @TableField("comment")
    @Schema(description = "反馈的注释信息")
    private String comment;

    public final static String[] FEEDBACK_TYPES = new String[]{"read", "like", "star"};
}
