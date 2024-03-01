package com.mingink.article.api;

import com.mingink.article.api.config.DefaultFeignConfiguration;
import com.mingink.article.api.domain.dto.GorseUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/1 15:52
 */
@FeignClient(name = "mingink-article", contextId = "remote-gorse", configuration = DefaultFeignConfiguration.class)
public interface RemoteGorseService {
    @PostMapping("/gorse/user")
    public Boolean addNewGorseUser(@RequestBody GorseUserRequest gorseUserRequest);

    @DeleteMapping("/gorse/user/userId/{userId}")
    public Boolean removeGorseUser(@PathVariable("userId") String userId);

}
