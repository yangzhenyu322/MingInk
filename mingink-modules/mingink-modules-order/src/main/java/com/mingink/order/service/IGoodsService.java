package com.mingink.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mingink.order.domain.dto.GoodsRequest;
import com.mingink.order.domain.entity.Goods;

/**
 * @author ZenSheep
 * @since 2024-03-21
 */
public interface IGoodsService extends IService<Goods> {

    Boolean addNewGoods(GoodsRequest goodsRequest);

    Boolean updateGoods(GoodsRequest goodsRequest, String goodsId, Integer status);
}
