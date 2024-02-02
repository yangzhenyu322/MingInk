package com.mingink.common.cache.model;

import com.mingink.common.cache.model.base.CommonCache;

/**
 * @author hulx
 * @version 1.0.0
 * @date 2024/2/2 20:47
 * @description
 */
public class BusinessCache<T> extends CommonCache {
    private T data;

    public BusinessCache<T> with(T data){
        this.data = data;
        this.exist = true;
        return this;
    }

    public BusinessCache<T> withVersion(Long version){
        this.version = version;
        return this;
    }

    public BusinessCache<T> retryLater(){
        this.retryLater = true;
        return this;
    }

    public BusinessCache<T> notExist(){
        this.exist = false;
        this.version = -1L;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
