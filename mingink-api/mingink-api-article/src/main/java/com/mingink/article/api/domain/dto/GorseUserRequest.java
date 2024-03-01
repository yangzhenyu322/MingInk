package com.mingink.article.api.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/1 14:17
 */
@Data
@ApiModel(value="GorseUsers Request对象", description="GorseUsers Request实体对象")
public class GorseUserRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "标签Key集合，例[\"玄幻\", \"现代\"]")
    private String labels;
}
