package com.mingink.article.api.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/3/1 17:25
 * @description 发布小说章节内容请求实体类
 */
@Data
@ApiModel(value="PublishChapter Request对象", description="PublishChapter Request实体对象")
public class ChapterRequest implements Serializable {
    private static final long serialVersionUID = 4381327608707544303L;

    @ApiModelProperty(value = "小说id")
    private Long bookId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "章节状态：0-创建（草稿）、1-发布、2-下架")
    private int status;

    @ApiModelProperty(value = "是否VIP专属阅读：0-否、1-是")
    private int isVip;
}
