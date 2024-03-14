package com.mingink.article.api.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/4 17:02
 */
@Data
@Schema(description = "作者请求对象")
public class AuthorRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "笔名")
    private String penName;
}
