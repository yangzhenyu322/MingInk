package com.mingink.article.controller;


import com.mingink.article.api.domain.dto.BookRequest;
import com.mingink.article.api.domain.dto.GorseFeedbackRequest;
import com.mingink.article.api.domain.entity.Book;
import com.mingink.article.service.IBookService;
import com.mingink.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小说前端控制器
 * @author ZenSheep
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/book")
@Api(value = "小说接口功能", tags = "BookController", description = "小说接口相关介绍")
public class BookController {
    @Autowired
    private IBookService bookService;

    /**
     * 通过book id查询小说
     * @return
     */
    @GetMapping("/id/{id}")
    @ApiOperation("通过book id查询小说")
    public R<Book> getBookById(@PathVariable("id") Long id) {
       Book book = bookService.getById(id);

       if (book == null) {
           return R.fail("不存在id为[" + id + "]的小说");
       }
       return R.ok(book);
    }

    /**
     * 通过作者id获取其创作的所有小说
     * @return
     */
    @GetMapping("/authorId/{authorId}")
    @ApiOperation("通过作者id获取其创作的所有小说")
    public R<List<Book>> getBooksByAuthorId(@PathVariable("authorId") Long authorId) {
        Map<String, Object> map = new HashMap<>();
        map.put("author_id", authorId);
        List<Book> books = bookService.listByMap(map);

        if (books == null || books.size() == 0) {
            return R.fail("id为[" + authorId + "]的作者没有创作的小说");
        }
        return R.ok(books);
    }

    /**
     * 通过小说名模糊查询小说
     * @return
     */
    @GetMapping("/name/{name}")
    @ApiOperation("通过小说名模糊查询小说")
    public R<List<Book>> getBooksByBookName(@PathVariable("name") String name) {
        if (StringUtils.isBlank(name)) {
            return R.fail("小说名不能为空");
        }

        List<Book> books = bookService.searchBooksByName(name);
        return R.ok(books);
    }

    /**
     * 新增小说
     * @param bookRequest
     * @return
     */
    @PostMapping("/new")
    @ApiOperation("新增小说")
    public R<String> addNewBook(@RequestBody BookRequest bookRequest) {
        Boolean isSuccess = bookService.insertBook(bookRequest);
        if (!isSuccess) {
            return R.fail("新增小说失败");
        }
        return R.ok("新增小说成功");
    }

    /**
     * 更新小说
     * @param bookRequest
     * @return
     */
    @PutMapping("/bookId/{bookId}")
    @ApiOperation("更新小说基本信息")
    public R<String> updateBookInfo(@RequestBody BookRequest bookRequest, @PathVariable("bookId") Long bookId) {
        Boolean isSuccess = bookService.updateBookInfo(bookRequest, bookId);
        if (!isSuccess) {
            return R.fail("更新小说信息失败");
        }
        return R.ok("更新小说信息成功");
    }

    /**
     * 更新小说状态
     * @param bookId
     * @param status
     * @return
     */
    @PutMapping("/bookId/{bookId}/status/{status}")
    @ApiOperation("更新小说状态：0-连载(默认)、1-已完结、2-下架")
    public R<String> updateBookStatus(@PathVariable("bookId") Long bookId,
                                      @PathVariable("status") int status) {
        boolean isSuccess = bookService.updateBookStatus(bookId, status);
        if (!isSuccess) {
            return R.fail("更新小说状态失败");
        }
        return R.ok("更新小说状态成功");
    }

    /**
     * 更新小说阅读量
     * @param gorseFeedbackRequest
     * @return
     */
    @PostMapping("/feedback")
    @ApiOperation("小说反馈(包含read,like,star类型)加一")
    public R<String> addBookRead(@RequestBody GorseFeedbackRequest gorseFeedbackRequest) {
        Boolean isSuccess = bookService.addBookRead(gorseFeedbackRequest);
        if (!isSuccess) {
            return R.fail("增加小说反馈失败");
        }
        return R.ok("增加小说反馈成功");
    }

    @DeleteMapping("/feedback")
    @ApiOperation("小说反馈减一")
    public R<String> substrateBookRead(@RequestBody GorseFeedbackRequest gorseFeedbackRequest) {
        Boolean isSuccess = bookService.substrateBookRead(gorseFeedbackRequest);
        if (!isSuccess) {
            return R.fail("减少小说阅读量失败");
        }
        return R.ok("减少小说阅读量成功");
    }

    @DeleteMapping("/bookId/{bookId}")
//    @ApiOperation("删除小说")
    // TODO
    public R<String> removeBook(@PathVariable("bookId") Long bookId) {
        Boolean isSuccess = bookService.removeBookById(bookId);
        if (!isSuccess) {
            return R.fail("删除小说失败");
        }
        return R.ok("删除小说成功");
    }
}