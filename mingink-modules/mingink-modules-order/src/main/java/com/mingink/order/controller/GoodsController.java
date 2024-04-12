package com.mingink.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.mingink.common.core.domain.R;
import com.mingink.order.domain.dto.GoodsRequest;
import com.mingink.order.domain.entity.Goods;
import com.mingink.order.service.IGoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ZenSheep
 * @Date: 2024/3/21 19:45
 */
@RestController
@RequestMapping("/goods")
@Tag(name = "商品接口")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;

    @GetMapping("/all")
    @Operation(summary = "查询所有商品信息")
    public R<List<Goods>> getAllGoods() {
        return R.ok(goodsService.list());
    }

    @GetMapping("/goodsId/{goodsId}")
    @SentinelResource("hot")
    @Operation(summary = "通过商品ID查询单个商品信息")
    public R<Goods> getGoodsById(@PathVariable("goodsId") String goodsId) {
        return R.ok(goodsService.getById(goodsId));
    }

    @PostMapping("/add")
    @Operation(summary = "添加新商品")
    public R<Boolean> addNewGoods(@RequestBody GoodsRequest goodsRequest) {
        return R.ok(goodsService.addNewGoods(goodsRequest));
    }

    @PutMapping("/goodsId/{goodsId}/status/{status}")
    @Operation(summary = "更新商品信息")
    public R<Boolean> updateGoods(@RequestBody GoodsRequest goodsRequest,
                                  @PathVariable("goodsId") String goodsId,
                                  @PathVariable("status") Integer status) {
        return R.ok(goodsService.updateGoods(goodsRequest, goodsId, status));
    }
}