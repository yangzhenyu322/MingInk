package com.mingink.article.api.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * GorseUsers 用户实体类
 * @author ZenSheep
 * @since 2024-02-29
 */
@Data
@TableName("gorse_users")
@ApiModel(value="GorseUsers对象", description="GorseUsers实体对象")
public class GorseUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "user_id", type = IdType.NONE)
    private String userId;

    @ApiModelProperty(value = "标签Key集合，例[\"玄幻\", \"现代\"]")
    @TableField("labels")
    private String labels;

    @ApiModelProperty(value = "个人简介")
    @TableField("comment")
    private String comment;

    @ApiModelProperty(value = "订阅")
    @TableField("subscribe")
    private String subscribe;
}