package com.mingink.article.api.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Book Request对象
 * @Author: ZenSheep
 * @Date: 2024/2/29 11:21
 */
@Data
@Schema(description = "小说请求对象")
public class BookRequest {
    @Schema(description = "作家id")
    private String authorId;

    @Schema(description = "小说名（标题）")
    private String name;

    @Schema(description = "描述（简介）")
    private String description;

    @Schema(description = "封面图片url")
    private String picUrl;
}