package com.mingink.article.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Tag 实体类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Data
@TableName("tag")
@ApiModel(value="Tag对象", description="Tag实体对象")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标签名字")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "父级标签id")
    @TableField("upper_level_id")
    private Long upperLevelId;

    @ApiModelProperty(value = "标签描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
