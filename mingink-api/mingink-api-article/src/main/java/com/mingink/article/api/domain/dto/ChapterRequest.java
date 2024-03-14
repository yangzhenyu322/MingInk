package com.mingink.article.api.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/3/1 17:25
 * @description 发布小说章节内容请求实体类
 */
@Data
@Schema(description = "发布小说章节内容请求实体类")
public class ChapterRequest implements Serializable {
    private static final long serialVersionUID = 4381327608707544303L;

    @Schema(description = "小说id")
    private Long bookId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "章节状态：0-创建（草稿）、1-发布、2-下架")
    private int status;

    @Schema(description = "是否VIP专属阅读：0-否、1-是")
    private int isVip;
}
