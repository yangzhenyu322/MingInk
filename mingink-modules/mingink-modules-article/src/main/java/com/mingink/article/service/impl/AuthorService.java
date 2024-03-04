package com.mingink.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.article.api.domain.dto.AuthorRequest;
import com.mingink.article.api.domain.entity.Author;
import com.mingink.article.mapper.AuthorMapper;
import com.mingink.article.service.IAuthorService;
import com.mingink.common.core.exception.BusinessException;
import com.mingink.common.core.exception.ErrorCode;
import com.mingink.common.core.utils.id.SnowFlakeFactory;
import com.mingink.system.api.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author 服务实现类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Slf4j
@Service
public class AuthorService extends ServiceImpl<AuthorMapper, Author> implements IAuthorService {
    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public Author getAuthorByUserId(String userId) {
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("user_id", userId);

        return authorMapper.selectByMap(selectMap).get(0);
    }

    @Override
    public Author getAuthorById(String authorId) {
        return authorMapper.selectById(authorId);
    }

    @Override
    public List<Author> getAuthorsByPenName(String penName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("pen_name", penName);

        return authorMapper.selectMaps(queryWrapper);
    }

    @Override
    public Boolean registerAuthor(AuthorRequest authorRequest) {
        if (remoteUserService.getUserByUserId(authorRequest.getUserId()) == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不存在ID为" + authorRequest.getUserId() + "的用户");
        }
        Map<String, Object> selectAuthorMap = new HashMap<>();
        selectAuthorMap.put("user_id", authorRequest.getUserId());
        if (authorMapper.selectByMap(selectAuthorMap).size() > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该用户已注册为作者，不能重复注册");
        }

        Author author = new Author();
        author.setId(SnowFlakeFactory.getSnowFlakeFromCache().nextId()); // 雪花算法生成作者ID
        author.setUserId(authorRequest.getUserId());
        author.setPenName(authorRequest.getPenName());
        author.setStatus(0);
        author.setCreateTime(LocalDateTime.now());
        author.setUpdateTime(LocalDateTime.now());

        return authorMapper.insert(author) > 0;
    }

    @Override
    public Boolean updatePenNameById(String authorId, String penName) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", authorId);
        updateWrapper.set("pen_name", penName);
        return authorMapper.update(null, updateWrapper) > 0;
    }

    @Override
    public Boolean updateStatusById(String authorId, int status) {
        if (status < 0 || status > 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "作者状态只能为0或1,不能为" + status);
        }

        //TODO 修改作者名下作品为不可见状态

        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", authorId);
        updateWrapper.set("status", status);
        return authorMapper.update(null, updateWrapper) > 0;
    }

    @Override
    public Boolean removeAuthor(String authorId) {
        // TODO:删除与作者相关信息

        return authorMapper.deleteById(authorId) > 0;
    }
}