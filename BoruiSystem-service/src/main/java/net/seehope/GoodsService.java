package net.seehope;

import net.seehope.pojo.Goods;

import java.util.List;

public interface GoodsService {
    //增加商品
    void addGoods(Goods goods);

    //删除商品
    void deleteGoods(String goodsName);

    //查找全部商品
    List getAllGoods();

    //上/下架商品
    void updateGoods(String goodsName);
}
