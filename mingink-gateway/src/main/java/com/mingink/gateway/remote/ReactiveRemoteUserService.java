package com.mingink.gateway.remote;

import com.mingink.system.api.RemoteUserService;
import com.mingink.system.api.domain.entiry.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * 将feign client包装，做异步处理
 * 参考：https://blog.csdn.net/itsjustforyou/article/details/121687459
 *
 * @Author: ZenSheep
 * @Date: 2024/1/7 12:12
 */
@Slf4j
@Component
public class ReactiveRemoteUserService {
    @Lazy
    @Autowired
    private RemoteUserService remoteUserService;

    // 必须在异步线程中执行，执行结果返回Future
    @Async
    public Future<User> findUserByUserName(String username) {
        User user = remoteUserService.getUserByUserName(username);
        return new AsyncResult<>(user);
    }
}
