package com.mingink.system.api.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/7/15 17:57
 * @description
 */
@Data
public class SignMessageAddReq implements Serializable {

    private static final long serialVersionUID = -5853518084321528943L;

    /**
     * 签文内容
     */
    private String content;

    /**
     * 签文类型
     */
    private String type;

    /**
     * 签文解读
     */
    private String description;
}
