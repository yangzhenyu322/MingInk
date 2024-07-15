package com.mingink.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.system.api.domain.entity.SignMessage;
import com.mingink.system.service.ISignMessageService;
import com.mingink.system.mapper.SignMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author huangliangxing
* @description 针对表【sign_message】的数据库操作Service实现
* @createDate 2024-07-09 15:04:54
*/
@Service
public class SignMessageServiceImpl extends ServiceImpl<SignMessageMapper, SignMessage>
    implements ISignMessageService {

}




