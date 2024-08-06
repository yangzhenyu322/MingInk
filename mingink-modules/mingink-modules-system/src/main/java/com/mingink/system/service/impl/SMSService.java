package com.mingink.system.service.impl;

import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.model.SendMessageV3Request;
import com.baidubce.services.sms.model.SendMessageV3Response;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mingink.common.core.domain.R;
import com.mingink.common.redis.service.RedisService;
import com.mingink.system.api.domain.entity.User;
import com.mingink.system.config.BaiDuSMSConfiguration;
import com.mingink.system.mapper.UserMapper;
import com.mingink.system.service.ISMSService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * SMS服务业务
 * @Author: ZenSheep
 * @Date: 2024/2/1 20:31
 */
@Slf4j
@Service
public class SMSService implements ISMSService {
    @Autowired
    private SmsClient smsClient;

    @Autowired
    private RedisService redisService;

    /**
     * 异步发送手机验证码
     * @param phoneNumber 手机号码
     * @param codeLength 验证码长度
     * @param validTime 验证码有效时间（单位：秒）
     * @return R.(requestId)，客服端可通过requestId查询redis来验证inputCode是否正确
     */
    @Override
    public R<?> sendPhoneCode(String phoneNumber, int codeLength, Long validTime) {
        // 长度为11的数字字符串
        if (phoneNumber.length() != 11 || !StringUtils.isNumeric(phoneNumber)) {
            log.error("手机号码格式有误");
            return R.fail("手机号码【" + phoneNumber + "】格式有误，请重新输入");
        }

        if (codeLength < 4 || codeLength > 6 ) {
            log.error("验证码长度范围为4~6位有效数字或字母，【{}】格式有误", codeLength);
            return R.fail("验证码长度范围为4~6位有效数字或字母，[" + codeLength + "]格式有误");
        }

        String randomCode = generateRandomCode(codeLength); // 随机验证码
        SendMessageV3Request request = new SendMessageV3Request();
        request.setMobile(phoneNumber);
        request.setSignatureId(BaiDuSMSConfiguration.getSignatureId());
        request.setTemplate(BaiDuSMSConfiguration.getTemplate());
        Map<String, String> contentVar = new HashMap<>(); // 填充短信模板的变量内容
        contentVar.put("code", randomCode);
        request.setContentVar(contentVar);

        SendMessageV3Response response = smsClient.sendMessage(request);
        if (response != null && response.isSuccess()) {
            //  submit success
            String requestId = response.getRequestId();
            // 缓存<requestId, randomCode>以验证用户输入的inputCode, 并设置有效时间（单位：秒）
            redisService.setCacheObject(requestId, randomCode, validTime, TimeUnit.SECONDS);

            // 输出响应结果到日志
            log.info("RequestId:{}", requestId);
            log.info("code:{}", randomCode);

            return R.ok(requestId);
        } else {
            //  fail
            return R.fail(response.getMessage());
        }
    }

    /**
     * 验证用户输入的验证码是否正确
     * @param requestId
     * @param inputCode
     * @return
     */
    @Override
    public R<?> verifyCode(String requestId, String inputCode) {
//        String redisCode = (String) redisTemplate.opsForValue().get(requestId);
        String redisCode = redisService.getCacheObject(requestId);
        if (redisCode == null) {
            // 验证码过期
            log.error("[{}]验证失败:{}", requestId, "验证码已过期");
            return R.fail("验证码已过期");
        }
        if (!redisCode.equals(inputCode)) {
            // 验证码不正确
            log.error("[{}]验证失败:{}", requestId, "验证码有误");
            return R.fail("验证码有误");
        }

        log.info("[{}]验证成功", requestId);
        return R.ok("验证成功");
    }

    /**
     * 生成随机数字验证码
     * @param codeLength 字符串长度
     * @return 指定长度首位数字不为0的数字字符串
     */
    public static String generateRandomCode(int codeLength) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(codeLength);

        // 生成第一个数字，确保不为0
        int firstDigit = random.nextInt(9) + 1; // 生成1到9之间的数字
        stringBuilder.append(firstDigit);

        for (int i = 1; i < codeLength; i++) {
            int digit = random.nextInt(10); // 生成0到9之间的数字
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }
}
