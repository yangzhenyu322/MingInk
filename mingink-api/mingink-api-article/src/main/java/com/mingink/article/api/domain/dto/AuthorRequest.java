package com.mingink.article.api.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/4 17:02
 */
@Data
@ApiModel(value="Author Request对象", description="Author Request实体类")
public class AuthorRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "笔名")
    private String penName;
}
