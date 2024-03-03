package com.mingink.article.api.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/3/1 17:31
 * @description 章节状态信息请求实体类
 */
@Data
@ApiModel(value="BaseChapter Request对象", description="BaseChapter Request实体对象")
public class BaseChapterRequest implements Serializable {
    private static final long serialVersionUID = 6843690759540840669L;

    @ApiModelProperty(value = "小说章节id")
    private Long id;

    @ApiModelProperty(value = "章节状态：0-创建（草稿）、1-发布、2-删除下架")
    private int status;
}
