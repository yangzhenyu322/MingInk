package com.mingink.system.api.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/7/16 10:54
 * @description
 */
@Data
public class SignRecordVO implements Serializable {

    private static final long serialVersionUID = -7388206924385146173L;

    /**
     * 记录ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 签文内容
     */
    private String content;

    /**
     * 签文解读
     */
    private String description;

    /**
     * 签文类型
     */
    private String type;

    /**
     * 记录状态
     */
    private Integer status;

    /**
     * 抽签时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
