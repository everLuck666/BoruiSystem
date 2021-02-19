package net.seehope.impl;

import net.seehope.GoodsService;
import net.seehope.mapper.GoodsMapper;
import net.seehope.pojo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public void addGoods(Goods goods) {

        goodsMapper.insert(goods);
    }

    @Override
    public void deleteGoods(String goodsName) {
        Goods goods = new Goods();
        goods.setProductName(goodsName);
        goodsMapper.delete(goods);
    }

    @Override
    public List getAllGoods() {
        return goodsMapper.selectAll();
    }

    @Override
    public void updateGoods(String goodsName) {
        Goods goods = new Goods();
        goods.setProductName(goodsName);
        Goods goods1 = goodsMapper.selectOne(goods);
        String status = Integer.valueOf(goods1.getStatus())== 1 ? "0" : "1";
        goodsMapper.delete(goods1);
        goods1.setStatus(status);
        goodsMapper.insert(goods1);

    }
}
