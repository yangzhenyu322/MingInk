package com.mingink.article.api.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/5 20:15
 */
@Data
@Schema(description = "页查询章节目录对象")
public class PageChapterCatalogRequest {
    @Schema(description = "小说id")
    private Long bookId;
    @Schema(description = "章节状态：0-创建（草稿）、1-发布、2-下架")
    private Integer status;
    @Schema(description = "查询页")
    private Integer page;
    @Schema(description = "分页大小")
    private Integer size;
}
