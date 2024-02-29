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
 * Gorse Feedback 反馈实体类
 * @author ZenSheep
 * @since 2024-02-29
 */
@Data
@TableName("gorse_feedback")
@ApiModel(value="GorseFeedback对象", description="GorseFeedback实体对象")
public class GorseFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "反馈类型，包含like、star、read等")
    @TableId(value = "feedback_type", type = IdType.NONE)
    private String feedbackType;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "物品(小说)ID")
    @TableField("item_id")
    private String itemId;

    @ApiModelProperty(value = "时间戳")
    @TableField("time_stamp")
    private LocalDateTime timeStamp;

    @ApiModelProperty(value = "反馈的注释信息")
    @TableField("comment")
    private String comment;


}
