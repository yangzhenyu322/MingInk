package com.mingink.article.api.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/1 10:27
 */
@Data
@Schema(description = "Gorse 反馈请求实体对象")
public class GorseFeedbackRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "反馈类型，包含like、star、read")
    private String feedbackType;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "物品(小说)ID")
    private String itemId;
}