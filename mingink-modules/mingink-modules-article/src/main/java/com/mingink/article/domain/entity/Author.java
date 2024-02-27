package com.mingink.article.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Author 实体类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Data
@TableName("author")
@ApiModel(value="Author对象", description="Author实体类")
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "作家id")
    @TableId
    private String id;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "笔名")
    @TableField("pen_name")
    private String penName;

    @ApiModelProperty(value = "作家状态：0-正常、1-禁用")
    @TableField("status")
    private Boolean status;

    @ApiModelProperty(value = "注册时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "个人信息更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
