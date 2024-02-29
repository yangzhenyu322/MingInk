package com.mingink.article.api.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Book Request对象
 * @Author: ZenSheep
 * @Date: 2024/2/29 11:21
 */
@Data
@ApiModel(value="Book Request对象", description="Book Request小说实体对象")
public class BookRequest {
    @ApiModelProperty(value = "作家id")
    private String authorId;

    @ApiModelProperty(value = "小说名（标题）")
    private String name;

    @ApiModelProperty(value = "描述（简介）")
    private String description;

    @ApiModelProperty(value = "封面图片url")
    private String picUrl;
}
