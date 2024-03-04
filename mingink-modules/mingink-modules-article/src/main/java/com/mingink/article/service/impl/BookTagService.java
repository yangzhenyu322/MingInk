package com.mingink.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.article.api.domain.entity.BookTag;
import com.mingink.article.api.domain.entity.GorseItem;
import com.mingink.article.api.domain.entity.Tag;
import com.mingink.article.mapper.BookTagMapper;
import com.mingink.article.service.IBookTagService;
import com.mingink.article.service.IGorseService;
import com.mingink.article.service.ITagService;
import com.mingink.article.utils.GorseUtils;
import io.seata.spring.annotation.GlobalTransactional;
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

    @Autowired
    private IGorseService gorseService;

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

        return String.valueOf(GorseUtils.tagNamesToStr(tagNames));
    }

    /**
     * 获取小说标签名列表
     * @param bookId
     * @return
     */
    @Override
    public List<Tag> getBookTagsById(Long bookId) {
        Map<String, Object> map = new HashMap<>();
        map.put("book_id", bookId);
        List<Tag> tags = bookTagMapper.selectByMap(map).stream().map(bookTag -> {
            return tagService.getTagById(bookTag.getTagId());
        }).collect(Collectors.toList());

        return tags;
    }

    @Override
    @GlobalTransactional
    public Boolean addNewBookTag(BookTag bookTag) {
        Boolean isInsertBookTagSuccess = bookTagMapper.insert(bookTag) > 0;

        // 更新Gorse Items
        GorseItem gorseItem = gorseService.getGorseItemById(String.valueOf(bookTag.getBookId()));

        Map<String, Object> map = new HashMap<>();
        map.put("book_id", bookTag.getBookId());
        List<String> tagNames = bookTagMapper.selectByMap(map).stream().map(bookTagItem -> {
            return tagService.getTagNameById(bookTagItem.getTagId());
        }).collect(Collectors.toList());
        String tagNamesStr = GorseUtils.tagNamesToStr(tagNames);
        gorseItem.setLabels(tagNamesStr);
        gorseItem.setCategories(tagNamesStr);
        gorseService.updateGorseItem(gorseItem);

        return isInsertBookTagSuccess;
    }

    @Override
    @GlobalTransactional
    public Boolean removeBookTag(BookTag bookTag) {
        Map<String, Object> deleteBookTagMap = new HashMap<>();
        deleteBookTagMap.put("book_id", bookTag.getBookId());
        deleteBookTagMap.put("tag_id", bookTag.getTagId());
        Boolean isRemoveBookTagSuccess = bookTagMapper.deleteByMap(deleteBookTagMap) > 0;

        // 更新Gorse Item
        GorseItem gorseItem = gorseService.getGorseItemById(String.valueOf(bookTag.getBookId()));

        Map<String, Object> updateGorseItemMap = new HashMap<>();
        updateGorseItemMap.put("book_id", bookTag.getBookId());
        List<String> tagNames = bookTagMapper.selectByMap(updateGorseItemMap).stream().map(bookTagItem -> {
            return tagService.getTagNameById(bookTagItem.getTagId());
        }).collect(Collectors.toList());
        String tagNamesStr = GorseUtils.tagNamesToStr(tagNames);
        gorseItem.setLabels(tagNamesStr);
        gorseItem.setCategories(tagNamesStr);
        gorseService.updateGorseItem(gorseItem);

        return isRemoveBookTagSuccess;
    }
}
