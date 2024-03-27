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
