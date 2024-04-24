package com.mingink.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingink.common.core.exception.BusinessException;
import com.mingink.common.core.exception.ErrorCode;
import com.mingink.common.core.utils.id.SnowFlakeFactory;
import com.mingink.order.domain.dto.GoodsRequest;
import com.mingink.order.domain.entity.Goods;
import com.mingink.order.mapper.GoodsMapper;
import com.mingink.order.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author ZenSheep
 * @since 2024-03-21
 */
@Slf4j
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * @Cacheable注解是 通过 Spring AOP机制进行的，因此类内的调用将无法触发缓存操作，必须由外部进行调用，之前也算是踩了一遍坑，特别提醒一下
     */

    /**
     * cacheNames/value：缓存组件的名字，即cacheManager中缓存的名称。
     * key：缓存数据时使用的key。默认使用方法参数值，也可以使用SpEL表达式进行编写。
     * keyGenerator：和key二选一使用。
     * cacheManager：指定使用的缓存管理器。
     * condition：在方法执行开始前检查，在符合condition的情况下，进行缓存
     * unless：在方法执行完成后检查，在符合unless的情况下，不进行缓存
     * sync：是否使用同步模式。若使用同步模式，在多个线程同时对一个key进行load时，其他线程将被阻塞。
     */
    @Override
    @Cacheable(value = "goodsCache", key = "#p0")
    public Goods getOrderById(String goodsId) {
        log.info("从 DB 中获取ID为[{}]的商品", goodsId);
        return goodsMapper.selectById(goodsId);
    }

    @Override
    public Boolean addNewGoods(GoodsRequest goodsRequest) {
        Goods goods = new Goods();
        goods.setId(SnowFlakeFactory.getSnowFlakeFromCache().nextId());
        goods.setSubject(goodsRequest.getSubject());
        goods.setAmount(goodsRequest.getAmount());
        goods.setInventory(goodsRequest.getInventory());
        goods.setRoleId(goodsRequest.getRoleId());
        goods.setEffectiveDuration(goodsRequest.getEffectiveDuration());
        goods.setStatus(0);
        goods.setCreateTime(LocalDateTime.now());

        log.info("新增商品请求参数: {}", goodsRequest);
        Boolean isSuccess = goodsMapper.insert(goods) > 0;
        if (isSuccess) {
            log.info("新增商品成功: {}", goods);
        } else {
            log.info("新增商品失败");
        }

        return isSuccess;
    }

    @Override
    public Boolean updateGoods(GoodsRequest goodsRequest, String goodsId, Integer status) {
        if (status < 0 || status > 2) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "商品状态范围为0~2，不能为" + status);
        }

        Goods goods = goodsMapper.selectById(goodsId);
        goods.setSubject(goodsRequest.getSubject());
        goods.setAmount(goodsRequest.getAmount());
        goods.setInventory(goodsRequest.getInventory());
        goods.setRoleId(goodsRequest.getRoleId());
        goods.setEffectiveDuration(goodsRequest.getEffectiveDuration());
        goods.setStatus(status);
        if (status == 0) {
            goods.setCreateTime(LocalDateTime.now());
        } else if (status == 1) {
            goods.setExhaustedTime(LocalDateTime.now());
        } else if (status == 2) {
            goods.setTakeDownTime(LocalDateTime.now());
        }

        log.info("更新商品信息参数: {}", goodsRequest);
        Boolean isSuccess = goodsMapper.updateById(goods) > 0;
        if (isSuccess) {
            log.info("更新商品信息成功: {}", goods);
        } else {
            log.info("更新商品信息失败");
        }

        return isSuccess;
    }

}
