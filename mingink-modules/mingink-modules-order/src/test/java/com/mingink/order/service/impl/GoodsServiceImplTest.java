package com.mingink.order.service.impl;

import com.mingink.order.domain.entity.Goods;
import com.mingink.order.mapper.GoodsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: ZenSheep
 * @Date: 2024/4/18 10:41
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodsServiceImplTest {
    @Autowired
    private GoodsMapper goodsMapper;

    @Test
    public void testFindGoodById() {
        Goods goods = goodsMapper.selectById("21808839485689856");
        System.out.println(goods.toString());
    }
}