package com.mingink.system.api.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/7/7 16:26
 * @description 心理测评记录插入DTO
 */
@Data
@Schema(description = "心理测评记录插入DTO")
public class MentalTestInsReq implements Serializable {

    @Serial
    private static final long serialVersionUID = -6717288139563228161L;

    /**
     * 测试表单编号
     */
    @Schema(description = "测试表单编号")
    private Integer testId;

    /**
     * 测试结果编号ID
     */
    @Schema(description = "测试结果编号")
    private Integer resultId;
}
