package com.mingink.system.service;

import com.mingink.common.core.domain.R;
import com.mingink.system.api.domain.dto.SignMessageAddReq;
import com.mingink.system.api.domain.entity.SignMessage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author huangliangxing
* @description 针对表【sign_message】的数据库操作Service
* @createDate 2024-07-09 15:04:54
*/
public interface ISignMessageService extends IService<SignMessage> {

    /**
     * 添加数据
     * @param signMessageAddReq
     * @param request
     * @return
     */
    R<Boolean> insert(SignMessageAddReq signMessageAddReq, HttpServletRequest request);

    /**
     * 删除数据
     * @param signId
     * @param request
     * @return
     */
    R<Boolean> delete(Long signId, HttpServletRequest request);

    /**
     * 更新数据
     * @param signMessage
     * @param request
     * @return
     */
    R<Boolean> update(SignMessage signMessage, HttpServletRequest request);

    /**
     * 获取所有数据
     * @param request
     * @return
     */
    R<List<SignMessage>> getAll(HttpServletRequest request);

}
