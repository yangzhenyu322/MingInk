package com.mingink.system.service;

import com.mingink.common.core.domain.R;
import com.mingink.system.api.domain.dto.MentalTestInsReq;
import com.mingink.system.api.domain.entity.MentalTest;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author hulx
 * @description 针对表【mental_test】的数据库操作Service
 * @createDate 2024-07-07 12:33:42
 */
public interface IMentalTestService extends IService<MentalTest> {
    R<Boolean> insertMentalTest(MentalTestInsReq mentalTestInsReq, HttpServletRequest request);

    R<List<MentalTest>> getMentalTestsByUser(HttpServletRequest request);
}
