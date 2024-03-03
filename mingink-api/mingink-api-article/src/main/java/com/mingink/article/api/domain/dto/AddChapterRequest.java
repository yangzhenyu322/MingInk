package com.mingink.article.api.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/3/1 17:25
 * @description 发布小说章节内容请求实体类
 */
@Data
@ApiModel(value="PublishChapter Request对象", description="PublishChapter Request实体对象")
public class AddChapterRequest extends BaseChapterRequest implements Serializable {
    private static final long serialVersionUID = 4381327608707544303L;

    @ApiModelProperty(value = "作家id")
    private String authorId;

    @ApiModelProperty(value = "小说id")
    private Long bookId;

    @ApiModelProperty(value = "章节序号")
    private Integer serialNumber;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "总字数")
    private Integer wordCount;

    @ApiModelProperty(value = "是否VIP专属：0-否、1-是")
    private int isVip;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishTime;
}
