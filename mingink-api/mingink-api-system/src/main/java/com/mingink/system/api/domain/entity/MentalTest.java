package com.mingink.system.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 记录心理测评
 * @TableName mental_test
 */
@TableName(value ="mental_test")
@Data
@Schema(description = "心理测评")
public class MentalTest implements Serializable {
    @TableId(type = IdType.AUTO)
    @Schema(description = "测评记录ID")
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_name")
    @Schema(description = "用户名")
    private String username;

    /**
     * 测试表单编号
     */
    @TableField("test_id")
    @Schema(description = "测试表单编号")
    private Integer testId;

    /**
     * 测试结果编号ID
     */
    @TableField("result_id")
    @Schema(description = "测试结果编号")
    private Integer resultId;

    /**
     * 测试时间
     */
    @TableField("create_time")
    @Schema(description = "测试时间")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}