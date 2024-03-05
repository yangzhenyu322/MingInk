package com.mingink.article.api.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/5 20:15
 */
@Data
@ApiModel(value="分页查询章节目录对象", description="分页查询章节目录实体对象")
public class PageChapterCatalogRequest {
    @ApiModelProperty(value = "小说id")
    private Long bookId;
    @ApiModelProperty(value = "章节状态：0-创建（草稿）、1-发布、2-下架")
    private Integer status;
    @ApiModelProperty(value = "查询页")
    private Integer page;
    @ApiModelProperty(value = "分页大小")
    private Integer size;
}
