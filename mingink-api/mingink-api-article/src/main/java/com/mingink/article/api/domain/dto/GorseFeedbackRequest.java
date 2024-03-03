package com.mingink.article.api.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/1 10:27
 */
@Data
@ApiModel(value="GorseFeedback Request对象", description="GorseFeedback Request实体对象")
public class GorseFeedbackRequest {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "反馈类型，包含like、star、read")
    private String feedbackType;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "物品(小说)ID")
    private String itemId;
}