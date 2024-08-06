package com.mingink.system.api.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/8/6 14:59
 * @description
 */
@Data
public class SmsLoginReq implements Serializable {
    private static final long serialVersionUID = -4376557038698278650L;

    private String phoneNumber;

    private String requestId;

    private String inputCode;

}
