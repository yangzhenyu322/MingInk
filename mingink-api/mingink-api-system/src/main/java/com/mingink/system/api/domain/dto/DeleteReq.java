package com.mingink.system.api.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/7/15 17:36
 * @description 删除请求体
 */
@Data
public class DeleteReq implements Serializable {

    private static final long serialVersionUID = -286824627027336531L;

    /**
     * id
     */
    private Long id;
}
