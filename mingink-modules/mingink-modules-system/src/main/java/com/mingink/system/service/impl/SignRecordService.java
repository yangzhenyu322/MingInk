package com.mingink.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.common.core.domain.R;
import com.mingink.common.core.exception.BusinessException;
import com.mingink.common.core.exception.ErrorCode;
import com.mingink.common.core.utils.jwt.JWTUtils;
import com.mingink.system.api.domain.entity.SignMessage;
import com.mingink.system.api.domain.entity.SignRecord;
import com.mingink.system.api.domain.vo.SignRecordVO;
import com.mingink.system.mapper.SignMessageMapper;
import com.mingink.system.mapper.SignRecordMapper;
import com.mingink.system.service.ISignRecordService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author huangliangxing
 * @description 针对表【sign_record】的数据库操作Service实现
 * @createDate 2024-07-09 15:04:54
 */
@Service
@Slf4j
public class SignRecordService extends ServiceImpl<SignRecordMapper, SignRecord> implements ISignRecordService {

    @Autowired
    private SignMessageMapper signMessageMapper;

    @Autowired
    private SignRecordMapper signRecordMapper;


    @Override
    public R<SignRecordVO> getDailySignature(HttpServletRequest request) {
        String username = getUserNameByToken(request);
        QueryWrapper<SignRecord> qw = new QueryWrapper<>();
        qw.eq("user_name", username);
        qw.ge("create_time", LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
        if (signRecordMapper.selectOne(qw) != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "今日已抽签");
        }
        Long maxId = signMessageMapper.selectMaxId();
        Random random = new Random();
        // 随机签文ID
        int signId = random.nextInt(maxId.intValue()) + 1;
        SignMessage signMessage = signMessageMapper.selectById(signId);

        while (signMessage == null) {
            signId = signId % maxId.intValue() + 1;
            signMessage = signMessageMapper.selectById(signId);
        }
        SignRecord signRecord = new SignRecord();
        signRecord.setSignId(Long.valueOf(signId));
        signRecord.setUserName(username);
        signRecordMapper.insert(signRecord);
        signRecord = signRecordMapper.selectById(signRecord.getId());
        SignRecordVO signRecordVO = getSignRecordVO(signRecord, signMessage);
        return R.ok(signRecordVO);
    }

    @Override
    public R<List<SignRecordVO>> getAll(HttpServletRequest request) {
        String username = getUserNameByToken(request);
        QueryWrapper<SignRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        List<SignRecord> signRecords = signRecordMapper.selectList(queryWrapper);
        List<SignRecordVO> signRecordVOList = signRecords.stream().map(signRecord ->
                        getSignRecordVO(signRecord, signMessageMapper.selectById(signRecord.getSignId())))
                .collect(Collectors.toList());
        return R.ok(signRecordVOList);
    }

    private SignRecordVO getSignRecordVO(SignRecord signRecord, SignMessage signMessage) {
        SignRecordVO signRecordVO = new SignRecordVO();
        signRecordVO.setId(signRecord.getId());
        signRecordVO.setUserName(signRecord.getUserName());
        signRecordVO.setType(signMessage.getType());
        signRecordVO.setContent(signMessage.getContent());
        signRecordVO.setDescription(signMessage.getDescription());
        signRecordVO.setStatus(signRecord.getStatus());
        signRecordVO.setCreateTime(signRecord.getCreateTime());
        signRecordVO.setUpdateTime(signRecord.getUpdateTime());
        return signRecordVO;
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
