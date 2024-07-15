package com.mingink.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.system.api.domain.entity.SignRecord;
import com.mingink.system.service.ISignRecordService;
import com.mingink.system.mapper.SignRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author huangliangxing
* @description 针对表【sign_record】的数据库操作Service实现
* @createDate 2024-07-09 15:04:54
*/
@Service
public class SignRecordService extends ServiceImpl<SignRecordMapper, SignRecord>
    implements ISignRecordService {

}




