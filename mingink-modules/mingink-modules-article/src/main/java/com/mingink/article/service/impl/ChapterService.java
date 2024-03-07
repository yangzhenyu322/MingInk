package com.mingink.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.article.api.domain.dto.ChapterRequest;
import com.mingink.article.api.domain.dto.PageChapterCatalogRequest;
import com.mingink.article.api.domain.entity.Book;
import com.mingink.article.api.domain.entity.Chapter;
import com.mingink.article.api.domain.vo.ChapterCatalog;
import com.mingink.article.api.domain.vo.PageChapterCatalog;
import com.mingink.article.mapper.BookMapper;
import com.mingink.article.mapper.ChapterMapper;
import com.mingink.article.service.IChapterService;
import com.mingink.common.core.exception.BusinessException;
import com.mingink.common.core.exception.ErrorCode;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public List<ChapterCatalog> getChapterCatalogsByBookIdAndStatus(Long bookId, Integer status) {
        Map<String, Object> selectMap = new HashMap<>();
        selectMap.put("book_id", bookId);
        selectMap.put("status", status);
        List<ChapterCatalog> chapterCatalogs = chapterMapper.selectByMap(selectMap).stream()
                .map(ChapterCatalog::chapterToChapterCatalog).collect(Collectors.toList());
        return chapterCatalogs;
    }

    @Override
    public PageChapterCatalog getPageChapterCatalogsByBookId(PageChapterCatalogRequest pageChapterCatalogRequest) {
        Page pageObject = new Page(pageChapterCatalogRequest.getPage(), pageChapterCatalogRequest.getSize());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("book_id", pageChapterCatalogRequest.getBookId());
        queryWrapper.eq("status", pageChapterCatalogRequest.getStatus());
        queryWrapper.orderByAsc("serial_number"); // 根据章节序号升序排序
        IPage<Chapter> chapterPage = chapterMapper.selectPage(pageObject, queryWrapper);

        return new PageChapterCatalog(chapterPage.getTotal(), chapterPage.getRecords().stream()
                .map(ChapterCatalog::chapterToChapterCatalog).collect(Collectors.toList()));
    }

    @Override
    public Chapter getChapterById(Long chapterId) {
        return chapterMapper.selectById(chapterId);
    }

    @Override
    @GlobalTransactional
    public Boolean addChapter(ChapterRequest chapterRequest) {
        if (StringUtils.isEmpty(String.valueOf(chapterRequest.getBookId()))) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "请求参数BookId不能为空");
        }
        if (bookMapper.selectById(chapterRequest.getBookId()) == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不存在ID为" + chapterRequest.getBookId() + "的小说");
        }
        if(StringUtils.isEmpty(chapterRequest.getTitle())) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "小说标题为空");
        }
        if(StringUtils.isEmpty(chapterRequest.getContent())) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "小说内容为空");
        }
        if (StringUtils.isEmpty(String.valueOf(chapterRequest.getIsVip()))) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "VIP专享不能为空");
        }
        if (chapterRequest.getIsVip() < 0 || chapterRequest.getIsVip() > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "VIP专享只能设置为0或1，不能设置为" + chapterRequest.getIsVip());
        }

        Chapter chapter = new Chapter();
        chapter.setBookId(chapterRequest.getBookId());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("book_id", chapterRequest.getBookId());
        chapter.setSerialNumber(chapterMapper.selectCount(queryWrapper) + 1);
        chapter.setTitle(chapterRequest.getTitle());
        chapter.setContent(chapterRequest.getContent());
        chapter.setWordCount(chapterRequest.getContent().length());
        chapter.setStatus(chapterRequest.getStatus());
        chapter.setIsVip(chapterRequest.getIsVip());
        chapter.setCreateTime(LocalDateTime.now());
        chapter.setUpdateTime(LocalDateTime.now());
        //TODO 可以分为实时发布，定时发布，后续添加吧
        chapter.setPublishTime(LocalDateTime.now());

        // 修改小说总字数
        Book book = bookMapper.selectById(chapter.getBookId());
        if (chapter.getStatus() == 1) {
            book.setWordCount(book.getWordCount() + chapter.getWordCount());
            bookMapper.updateById(book);
        }

        return chapterMapper.insert(chapter) > 0;
    }

    @Override
    @GlobalTransactional
    public Boolean updateChapter(ChapterRequest chapterRequest, Long chapterId) {
        Chapter chapter = chapterMapper.selectById(chapterId);
        Book book = bookMapper.selectById(chapter.getBookId());
        // 修改小说总字数
        if (chapter.getStatus() == 1 && chapterRequest.getStatus() == 1){
            Integer increment = chapterRequest.getContent().length() - chapter.getWordCount();
            book.setWordCount(book.getWordCount() + increment);
        }
        if (chapter.getStatus() != 1 && chapterRequest.getStatus() == 1) {
            book.setWordCount(book.getWordCount() + chapterRequest.getContent().length());
        }
        if (chapter.getStatus() == 1 && chapterRequest.getStatus() != 1) {
            book.setWordCount(book.getWordCount() - chapter.getWordCount());
        }
        bookMapper.updateById(book);

        chapter.setTitle(chapterRequest.getTitle());
        chapter.setContent(chapterRequest.getContent());
        chapter.setWordCount(chapterRequest.getContent().length());
        chapter.setStatus(chapterRequest.getStatus());
        chapter.setIsVip(chapterRequest.getIsVip());
        chapter.setUpdateTime(LocalDateTime.now());
        //TODO 可以分为实时发布，定时发布，后续添加吧
        if (chapterRequest.getStatus() == 1) {
            chapter.setPublishTime(LocalDateTime.now());
        }

        return chapterMapper.updateById(chapter) > 0;
    }

    @Override
    @GlobalTransactional
    public Boolean updateChapterStatus(Long chapterId, Integer status) {
        Chapter chapter = chapterMapper.selectById(chapterId);
        Book book = bookMapper.selectById(chapter.getBookId());
        // 修改小说总字数
        if (chapter.getStatus() != 1 && status == 1) {
            // 发布小说
            book.setWordCount(book.getWordCount() + chapter.getWordCount());
        }
        if (chapter.getStatus() == 1 && status != 1) {
            // 下架小说
            book.setWordCount(book.getWordCount() - chapter.getWordCount());
        }
        bookMapper.updateById(book);

        if (chapter == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章ID" + chapterId + "不存在");
        }
        if (status < 0 || status > 2) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文章状态参数不能设置为" + status);
        }
        chapter.setStatus(status);

        return chapterMapper.updateById(chapter) > 0;
    }

    @Override
    public Boolean removeChapterById(Long chapterId) {
        Chapter chapter = chapterMapper.selectById(chapterId);
        // 修改小说总字数
        Book book = bookMapper.selectById(chapter.getBookId());
        book.setWordCount(book.getWordCount() - chapter.getWordCount());
        bookMapper.updateById(book);

        return chapterMapper.deleteById(chapterId) > 0;
    }


    @Override
    @GlobalTransactional
    public Boolean removeAllChapterByBookId(Long bookId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("book_id", bookId);
        return chapterMapper.delete(queryWrapper) > 0;
    }
}