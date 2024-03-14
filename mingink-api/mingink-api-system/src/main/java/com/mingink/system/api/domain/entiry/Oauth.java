package com.mingink.system.api.domain.entiry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ZenSheep
 * @since 2024-03-07
 */
@Data
@TableName("oauth")
@Schema(description = "第三方登录授权")
public class Oauth implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.NONE)
    @Schema(description = "授权ID")
    private Long id;

    @Schema(description = "授权类型key")
    private String typeKey;

    @Schema(description = "授权类型名称")
    private String typeName;
}
