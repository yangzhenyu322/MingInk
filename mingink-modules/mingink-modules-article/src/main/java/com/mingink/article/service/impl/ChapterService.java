package com.mingink.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.article.api.domain.dto.BaseChapterRequest;
import com.mingink.article.api.domain.dto.AddChapterRequest;
import com.mingink.article.api.domain.entity.Book;
import com.mingink.article.api.domain.entity.Chapter;
import com.mingink.article.mapper.BookMapper;
import com.mingink.article.mapper.ChapterMapper;
import com.mingink.article.service.IChapterService;
import com.mingink.common.core.domain.R;
import com.mingink.common.core.exception.BusinessException;
import com.mingink.common.core.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Chapter 服务实现类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Slf4j
@Service
public class ChapterService extends ServiceImpl<ChapterMapper, Chapter> implements IChapterService {

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public Boolean addChapter(AddChapterRequest addChapterRequest) {
        Chapter chapter = new Chapter();
        if(addChapterRequest.getId() != 0L) {
            chapter = chapterMapper.selectById(addChapterRequest.getId());
            if(chapter == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
            }
        }
        if(StringUtils.isEmpty(addChapterRequest.getContent())) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "小说内容为空");
        }
        if(StringUtils.isEmpty(addChapterRequest.getTitle())) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "小说标题为空");
        }
        if(StringUtils.isEmpty(addChapterRequest.getAuthorId())) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "作家ID为空");
        }
        if(addChapterRequest.getBookId() == null || addChapterRequest.getBookId() == 0L) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "书籍ID异常");
        }
        if(addChapterRequest.getWordCount() == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "章节字数为空");
        }
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author_id",addChapterRequest.getAuthorId());
        List<Book> bookList = bookMapper.selectList(queryWrapper);
        List<Long> bookIdList = bookList.stream().map(Book::getId).collect(Collectors.toList());
        if(!bookIdList.contains(addChapterRequest.getBookId())) {
            throw new BusinessException(ErrorCode.NO_AUTH, "该作家无权限操作该小说");
        }
        chapter.setStatus(addChapterRequest.getStatus());
        chapter.setContent(addChapterRequest.getContent());
        chapter.setUpdateTime(LocalDateTime.now());
        chapter.setTitle(addChapterRequest.getTitle());
        chapter.setBookId(addChapterRequest.getBookId());
        chapter.setIsVip(addChapterRequest.getIsVip());
        chapter.setWordCount(addChapterRequest.getWordCount());
        //TODO 可以分为实时发布，定时发布，后续添加吧
        chapter.setPublishTime(addChapterRequest.getPublishTime());
        chapter.setSerialNumber(addChapterRequest.getSerialNumber());
        if(addChapterRequest.getId() != 0L) return chapterMapper.updateById(chapter) > 0;
        return chapterMapper.insert(chapter) > 0;
    }

    @Override
    public Boolean updateChapterStatus(BaseChapterRequest baseChapterRequest) {
        if(baseChapterRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Chapter chapter = chapterMapper.selectById(baseChapterRequest.getId());
        chapter.setStatus(baseChapterRequest.getStatus());
        return chapterMapper.updateById(chapter) > 0;
    }

}