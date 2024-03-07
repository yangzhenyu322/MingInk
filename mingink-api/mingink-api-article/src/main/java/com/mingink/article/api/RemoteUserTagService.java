package com.mingink.article.api;

import com.mingink.article.api.config.DefaultFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/6 22:27
 */
@FeignClient(name = "mingink-article", contextId = "remote-userTag", configuration = DefaultFeignConfiguration.class)
public interface RemoteUserTagService {
    @DeleteMapping("/user-tag/userId/{userId}")
    public Boolean removeUserTagByUserId(@PathVariable("userId") String userId);
}
