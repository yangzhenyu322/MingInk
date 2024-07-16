package com.mingink.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mingink.common.core.domain.R;
import com.mingink.system.api.domain.entity.SignRecord;
import com.mingink.system.api.domain.vo.SignRecordVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author huangliangxing
* @description 针对表【sign_record】的数据库操作Service
* @createDate 2024-07-09 15:04:54
 * 用户打卡相关服务
*/
public interface ISignRecordService extends IService<SignRecord> {

    /**
     * 每日签文
     * @param request
     * @return
     */
    R<SignRecordVO> getDailySignature(HttpServletRequest request);

    /**
     * 获取所有签文记录
     * @param request
     * @return
     */
    R<List<SignRecordVO>> getAll(HttpServletRequest request);
}
