package com.mingink.common.cache.model.base;

import java.io.Serializable;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/2 20:48
 * @description 通用缓存模型
 */
public class CommonCache implements Serializable {
    private static final long serialVersionUID = 2448735813082442223L;
    /**
     * 缓存数据是否存在 true存在 false不存在
     * 遇到查询数据库中不存在的值时，为防止缓存穿透，设置"空缓存"
     * 并用于后续分桶扩展
     */
    protected boolean exist;
    /**
     * 缓存版本号
     * 传递的版本号小于等于缓存中的版本号，则说明缓存中的数据比客户端的数据新，直接返回本地缓存中的数据
     * 传递的版本号大于缓存中的版本号，说明缓存中的数据比较落后，从分布式缓存获取数据并更新到本地缓存
     */
    protected Long version;
    /**
     * 在抢夺分布式锁失败时，设置重试，下次查询到该缓存需要重试
     * 并用于后续分桶扩展
     */
    protected boolean retryLater;

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isRetryLater() {
        return retryLater;
    }

    public void setRetryLater(boolean retryLater) {
        this.retryLater = retryLater;
    }

}

