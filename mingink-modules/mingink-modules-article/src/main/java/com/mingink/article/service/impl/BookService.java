package com.mingink.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.article.api.domain.dto.BookRequest;
import com.mingink.article.api.domain.dto.GorseFeedbackRequest;
import com.mingink.article.api.domain.dto.GorseItemRequest;
import com.mingink.article.api.domain.entity.Book;
import com.mingink.article.api.domain.entity.GorseFeedback;
import com.mingink.article.api.domain.entity.GorseItem;
import com.mingink.article.mapper.AuthorMapper;
import com.mingink.article.mapper.BookMapper;
import com.mingink.article.mapper.BookTagMapper;
import com.mingink.article.service.IBookService;
import com.mingink.article.service.IBookTagService;
import com.mingink.article.service.IGorseService;
import com.mingink.common.core.exception.BusinessException;
import com.mingink.common.core.exception.ErrorCode;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Book 服务实现类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Slf4j
@Service
public class BookService extends ServiceImpl<BookMapper, Book> implements IBookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private BookTagMapper bookTagMapper;

    @Autowired
    private IGorseService gorseService;

    @Autowired
    private IBookTagService bookTagService;

    @Override
    @GlobalTransactional
    public List<Book> searchBooksByName(String name) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);

        return bookMapper.selectList(queryWrapper);
    }

    @Override
    @GlobalTransactional
    public Boolean insertBook(BookRequest bookRequest) {
        if (StringUtils.isEmpty(bookRequest.getAuthorId())) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "作家ID为空:" + bookRequest);
        }
        if (authorMapper.selectById(bookRequest.getAuthorId()) == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "ID为" + bookRequest.getAuthorId() + "的作家不存在:" + bookRequest);
        }
        if (StringUtils.isEmpty(bookRequest.getName())) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "小说名(标题)为空:" + bookRequest);
        }
        // 允许不同作者创建同名小说，不允许作者创建两次同名小说
        Map<String, Object> bookNameSelectMap = new HashMap<>();
        bookNameSelectMap.put("author_id", bookRequest.getAuthorId());
        bookNameSelectMap.put("name", bookRequest.getName());
        if (bookMapper.selectByMap(bookNameSelectMap).size() > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR , "允许不同作者创建同名小说，不允许作者创建两次同名小说:" + bookRequest);
        }
        if (StringUtils.isEmpty(bookRequest.getDescription())) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "小说简介(描述)为空:" + bookRequest);
        }

        Book book = new Book();
        book.setAuthorId(bookRequest.getAuthorId());
        book.setName(bookRequest.getName());
        book.setDescription(bookRequest.getDescription());
        book.setPicUrl(bookRequest.getPicUrl());
        book.setStatus(0);
        book.setReadCount(0L);
        book.setStarCount(0L);
        book.setLikeCount(0L);
        book.setWordCount(0);
        book.setCommentCount(0);
        book.setScore(0.0f);
        book.setCreateTime(LocalDateTime.now());
        book.setUpdateTime(LocalDateTime.now());
        boolean isAddBookSuccess = bookMapper.insert(book) > 0;
        book = bookMapper.selectByMap(bookNameSelectMap).get(0);

        GorseItemRequest gorseItemRequest = new GorseItemRequest();
        gorseItemRequest.setItemId(String.valueOf(book.getId()));
        gorseItemRequest.setLabels("[]");
        gorseItemRequest.setComment(book.getDescription());
        gorseItemRequest.setCategories("[]");
        gorseService.addNewItem(gorseItemRequest);

        return isAddBookSuccess;
    }

    @Override
    @GlobalTransactional
    // seata不支持更新主键或外键
    public Boolean updateBookInfo(BookRequest bookRequest, Long bookId) {
        if (StringUtils.isEmpty(String.valueOf(bookId))) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "小说Id不能为空");
        }
        Book book = bookMapper.selectById(bookId);
        if (book == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不存在的小说：" + bookId);
        }
        if (StringUtils.isEmpty(bookRequest.getAuthorId())) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "作者Id不能为空");
        }
        if (!book.getAuthorId().equals(bookRequest.getAuthorId())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "小说id与作者id不匹配(不能修改作者id)");
        }
        if (StringUtils.isEmpty(bookRequest.getName())) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "小说名(标题)不能为空:" + bookRequest);
        }
        if (StringUtils.isEmpty(bookRequest.getDescription())) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "小说简介(描述)为空:" + bookRequest);
        }

        book.setId(bookId);
        book.setAuthorId(bookRequest.getAuthorId());
        book.setName(bookRequest.getName());
        book.setDescription(bookRequest.getDescription());
        book.setPicUrl(bookRequest.getPicUrl());
        book.setUpdateTime(LocalDateTime.now());
        boolean isUpdateBookSuccess = bookMapper.updateById(book) > 0;

        // 同时更新GorseItem的信息
        GorseItem gorseItem = new GorseItem();
        gorseItem.setItemId(String.valueOf(book.getId()));
        // 从book_tag查找tags
        bookTagMapper.selectById(book.getId());
        gorseItem.setLabels(bookTagService.getTagNamesStrByBookId(book.getId()));
        gorseItem.setCategories(gorseItem.getLabels());
        gorseItem.setIsHidden(false);
        gorseItem.setComment(book.getDescription());
        gorseItem.setTimeStamp(LocalDateTime.now());
        gorseService.updateGorseItem(gorseItem);

        return isUpdateBookSuccess;
    }

    @Override
    public boolean updateBookStatus(Long bookId, int status) {
        if (status < 0 || status > 2) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "小说状态为0~2的整数");
        }

        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", bookId);
        updateWrapper.set("status", status);
        boolean isUpdateBookSuccess = bookMapper.update(null, updateWrapper) > 0;

        // 更新Gorse Item
        gorseService.updateGorseItemHidden(String.valueOf(bookId), status == 2);

        return isUpdateBookSuccess;
    }


    @Override
    @GlobalTransactional
    public Boolean addBookFeedback(GorseFeedbackRequest gorseFeedbackRequest) {
        if (!Arrays.stream(GorseFeedback.FEEDBACK_TYPES).toList().contains(gorseFeedbackRequest.getFeedbackType())) {
            // 非法反馈类型
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "非法反馈类型(不是read,like或star):" + gorseFeedbackRequest);
        }

        Book book = bookMapper.selectById(Long.parseLong(gorseFeedbackRequest.getItemId()));
        String feedbackType = gorseFeedbackRequest.getFeedbackType();
        if ("read".equals(feedbackType)) {
            book.setReadCount(book.getReadCount() + 1);
        } else if ("like".equals(feedbackType)) {
            book.setLikeCount(book.getLikeCount() + 1);
        } else {
            book.setStarCount(book.getStarCount() + 1);
        }
        Boolean isUpdateReadSuccess = bookMapper.updateById(book) > 0;

        // 新增Gorse Feedback
        gorseService.addNewFeedBack(gorseFeedbackRequest);

        return isUpdateReadSuccess;
    }

    @Override
    @GlobalTransactional
    public Boolean substrateBookFeedback(GorseFeedbackRequest gorseFeedbackRequest) {
        // 减少Gorse Feedback
        gorseService.removeFeedBack(gorseFeedbackRequest);

        // 减少book的feedback number
        Book book = bookMapper.selectById(Long.parseLong(gorseFeedbackRequest.getItemId()));
        String feedbackType = gorseFeedbackRequest.getFeedbackType();
        if ("read".equals(feedbackType)) {
            if (book.getReadCount() == 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "小说阅读量为0，不能继续减少");
            }
            book.setReadCount(book.getReadCount() - 1);
        } else if ("like".equals(feedbackType)) {
            if (book.getLikeCount() == 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "小说喜欢量为0，不能继续减少");
            }
            book.setLikeCount(book.getLikeCount() - 1);
        } else {
            if (book.getStarCount() == 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "小说收藏量为0，不能继续减少");
            }
            book.setStarCount(book.getStarCount() - 1);
        }

        return bookMapper.updateById(book) > 0;
    }

    @Override
    public Boolean removeBookById(Long bookId) {
        // 删除book_tag

        // TODO删除book_comment

        return bookMapper.deleteById(bookId) > 0;
    }
}