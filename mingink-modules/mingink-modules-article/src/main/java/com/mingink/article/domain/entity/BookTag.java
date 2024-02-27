package com.mingink.article.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * BookTag 实体类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Data
@TableName("book_tag")
@ApiModel(value="BookTag对象", description="BookTag实体对象")
public class BookTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "小说ID")
    @TableId(value = "book_id", type = IdType.NONE)
    private Long bookId;

    @ApiModelProperty(value = "标签ID")
    @TableField("tag_id")
    private Long tagId;
}