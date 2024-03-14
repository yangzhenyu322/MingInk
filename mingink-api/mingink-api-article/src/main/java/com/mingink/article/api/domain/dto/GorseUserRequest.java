package com.mingink.article.api.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/1 14:17
 */
@Data
@Schema(description = "Gorse用户请求实体对象")
public class GorseUserRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "标签Key集合，例[\"玄幻\", \"现代\"]")
    private String labels;
}
