package com.mingink.article.api;

import com.mingink.article.api.config.DefaultFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/6 21:52
 */
@FeignClient(name = "mingink-article", contextId = "remote-book", configuration = DefaultFeignConfiguration.class)
public interface RemoteAuthorService {
    @DeleteMapping("/author/userId/{userId}")
    Boolean removeAuthorByUserId(@PathVariable("userId") String userId);
}
