package com.mingink.system.service;

import com.mingink.common.core.domain.R;

/**
 * @Author: ZenSheep
 * @Date: 2024/2/1 20:31
 */
public interface ISMSService {
    R<?> sendPhoneCode(String phoneNumber, int codeLength, Long validTime);

    R<?> verifyCode(String requestId, String inputCode);
}
