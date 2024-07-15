package com.mingink.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.common.core.domain.R;
import com.mingink.common.core.exception.BusinessException;
import com.mingink.common.core.exception.ErrorCode;
import com.mingink.system.api.domain.dto.SignMessageAddReq;
import com.mingink.system.api.domain.entity.SignMessage;
import com.mingink.system.service.ISignMessageService;
import com.mingink.system.mapper.SignMessageMapper;
import com.mingink.system.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huangliangxing
 * @description 针对表【sign_message】的数据库操作Service实现
 * @createDate 2024-07-09 15:04:54
 */
@Service
public class SignMessageService extends ServiceImpl<SignMessageMapper, SignMessage> implements ISignMessageService {

    @Autowired
    private SignMessageMapper signMessageMapper;

    @Autowired
    private IUserService userService;


    @Override
    public R<Boolean> insert(SignMessageAddReq signMessageAddReq, HttpServletRequest request) {
        if (!userService.isAdmin(request)) throw new BusinessException(ErrorCode.NO_AUTH, "非管理员无权限");
        if (StringUtils.isBlank(signMessageAddReq.getContent())) throw new BusinessException(ErrorCode.NULL_ERROR);
        if (StringUtils.isBlank(signMessageAddReq.getType())) throw new BusinessException(ErrorCode.NULL_ERROR);
        SignMessage signMessage = new SignMessage();
        signMessage.setContent(signMessageAddReq.getContent());
        signMessage.setType(signMessageAddReq.getType());
        signMessage.setDescription(signMessageAddReq.getDescription());
        if (signMessageMapper.insert(signMessage) > 0) return R.ok(Boolean.TRUE);
        return R.ok(Boolean.FALSE, "插入失败");
    }

    @Override
    public R<Boolean> delete(Long signId, HttpServletRequest request) {
        if (!userService.isAdmin(request)) throw new BusinessException(ErrorCode.NO_AUTH, "非管理员无权限");
        if (signId == null || signId <= 0L) throw new BusinessException(ErrorCode.NULL_ERROR);
        if (signMessageMapper.deleteById(signId) > 0) return R.ok(Boolean.TRUE);
        return R.ok(Boolean.FALSE, "删除失败");

    }

    @Override
    public R<Boolean> update(SignMessage signMessage, HttpServletRequest request) {
        if (!userService.isAdmin(request)) throw new BusinessException(ErrorCode.NO_AUTH, "非管理员无权限");
        if (signMessage.getId() == null || signMessage.getId() <= 0L) throw new BusinessException(ErrorCode.NULL_ERROR);
        if (StringUtils.isBlank(signMessage.getContent())) throw new BusinessException(ErrorCode.NULL_ERROR);
        if (StringUtils.isBlank(signMessage.getType())) throw new BusinessException(ErrorCode.NULL_ERROR);
        if (signMessageMapper.updateById(signMessage) > 0) return R.ok(Boolean.TRUE);
        return R.ok(Boolean.FALSE, "更新失败");
    }

    @Override
    public R<List<SignMessage>> getAll(HttpServletRequest request) {
        if (!userService.isAdmin(request)) throw new BusinessException(ErrorCode.NO_AUTH, "非管理员无权限");
        List<SignMessage> signMessages = signMessageMapper.selectList(null);
        return R.ok(signMessages);
    }
}




