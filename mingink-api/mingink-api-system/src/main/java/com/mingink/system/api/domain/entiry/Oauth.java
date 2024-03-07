package com.mingink.system.api.domain.entiry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ZenSheep
 * @since 2024-03-07
 */
@Data
@TableName("oauth")
@ApiModel(value="Oauth对象", description="Oauth实体对象")
public class Oauth implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "授权ID")
    @TableId(value = "id", type = IdType.NONE)
    private Long id;

    @ApiModelProperty(value = "授权类型key")
    private String typeKey;

    @ApiModelProperty(value = "授权类型名称")
    private String typeName;
}
