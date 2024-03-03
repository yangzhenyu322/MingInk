package com.mingink.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.article.api.domain.entity.BookTag;
import com.mingink.article.mapper.BookTagMapper;
import com.mingink.article.service.IBookTagService;
import com.mingink.article.service.ITagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * BookTag 服务实现类
 * @author ZenSheep
 * @since 2024-02-27
 */
@Slf4j
@Service
public class BookTagService extends ServiceImpl<BookTagMapper, BookTag> implements IBookTagService {
    @Autowired
    private BookTagMapper bookTagMapper;

    @Autowired
    private ITagService tagService;

    /**
     * 获取小说标签名列表
     * @param bookId
     * @return
     */
    @Override
    public List<String> getTagNamesByBookId(Long bookId) {
        Map<String, Object> map = new HashMap<>();
        map.put("book_id", bookId);
        List<String> tagNames = bookTagMapper.selectByMap(map).stream().map(bookTag -> {
            return tagService.getTagNameById(bookTag.getTagId());
        }).collect(Collectors.toList());

        return tagNames;
    }

    /**
     * 获取小说当前的标签名字符串, 如["悬疑", "无限流"]
     * @param bookId
     * @return
     */
    @Override
    public String getTagNamesStrByBookId(Long bookId) {
        Map<String, Object> map = new HashMap<>();
        map.put("book_id", bookId);
        List<String> tagNames = bookTagMapper.selectByMap(map).stream().map(bookTag -> {
            return tagService.getTagNameById(bookTag.getTagId());
        }).collect(Collectors.toList());

        StringBuilder tagNamesStr = new StringBuilder("[");
        for (int i = 0; i < tagNames.size(); i++) {
            tagNamesStr.append("\"").append(tagNames.get(i)).append("\"");
            if (i != tagNames.size() - 1) {
                tagNamesStr.append(",");
            }
        }
        tagNamesStr.append("]");

        return String.valueOf(tagNamesStr);
    }
}
