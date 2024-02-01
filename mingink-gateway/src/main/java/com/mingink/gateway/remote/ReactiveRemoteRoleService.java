package com.mingink.gateway.remote;

import com.mingink.system.api.RemoteRoleService;
import com.mingink.system.api.domain.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @Author: ZenSheep
 * @Date: 2024/1/7 12:19
 */
@Slf4j
@Component
public class ReactiveRemoteRoleService {
    @Lazy
    @Autowired
    private RemoteRoleService remoteRoleService;

    @Async
    public Future<Role> getSysRoleById(Long id) {
        Role role = remoteRoleService.getSysRoleById(id);

        return new AsyncResult<>(role);
    }
}
