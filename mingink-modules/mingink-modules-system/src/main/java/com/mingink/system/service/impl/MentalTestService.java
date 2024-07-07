package com.mingink.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.common.core.domain.R;
import com.mingink.common.core.exception.BusinessException;
import com.mingink.common.core.exception.ErrorCode;
import com.mingink.common.core.utils.jwt.JWTUtils;
import com.mingink.system.api.domain.dto.MentalTestInsReq;
import com.mingink.system.api.domain.entity.MentalTest;
import com.mingink.system.service.IMentalTestService;
import com.mingink.system.mapper.MentalTestMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author huangliangxing
 * @description 针对表【mental_test】的数据库操作Service实现
 * @createDate 2024-07-07 12:33:42
 */
@Service
@Slf4j
public class MentalTestService extends ServiceImpl<MentalTestMapper, MentalTest>
        implements IMentalTestService {

    @Autowired
    private MentalTestMapper mentalTestMapper;

    @Override
    public R<Boolean> insertMentalTest(MentalTestInsReq mentalTestInsReq, HttpServletRequest request) {
        if (mentalTestInsReq.getTestId() == null ||
                mentalTestInsReq.getResultId() == null)
            throw new BusinessException(ErrorCode.NULL_ERROR);
        String username = getUserNameByToken(request);
        MentalTest mentalTest = new MentalTest();
        mentalTest.setTestId(mentalTestInsReq.getTestId());
        mentalTest.setResultId(mentalTestInsReq.getResultId());
        mentalTest.setUsername(username);
        boolean isSuccess = mentalTestMapper.insert(mentalTest) > 0;
        return R.ok(isSuccess, "保存成功");
    }

    @Override
    public R<List<MentalTest>> getMentalTestsByUser(HttpServletRequest request) {
        String username = getUserNameByToken(request);
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        List<MentalTest> list = mentalTestMapper.selectList(queryWrapper);
        if(list.size() == 0) throw new BusinessException(ErrorCode.NULL_ERROR);
        return R.ok(list, "查询成功");
    }


    private String getUserNameByToken(HttpServletRequest request) {
        //获取token
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("token: {}", token);
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        //解析token
        Map<String, Object> userMap = JWTUtils.getTokenInfo(token);
        return (String) userMap.get("username");
    }
}




