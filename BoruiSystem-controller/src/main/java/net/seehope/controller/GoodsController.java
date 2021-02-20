package net.seehope.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.seehope.GoodsService;
import net.seehope.IndexService;
import net.seehope.common.RestfulJson;
import net.seehope.pojo.Goods;
import net.seehope.pojo.vo.GoodsInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@RestController
@Api(tags="后台商品管理接口",value = "GoodsController")
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    IndexService indexService;

    @ApiOperation("添加商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productName", value = "商品名称", dataType = "String"),
            @ApiImplicitParam(name = "describe", value = "商品描述", dataType = "String"),
            @ApiImplicitParam(name = "productPrice",value = "商品价格", dataType = "Double"),
            @ApiImplicitParam(name = "status", value = "商品状态", dataType = "String")
    })
    @PutMapping(value = "goods",produces="application/json;charset=UTF-8")
    public RestfulJson addGoods( GoodsInfoVo goodsInfoVo, HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        File tempFile = new File("AcupunctureAndMoxibustionSystem-controller");
        String path = "/src/main/resources/static/images/";
        String fileName = indexService.update(files, path);
        Goods goods = new Goods();
        goods.setProductName(goodsInfoVo.getProductName());
        goods.setDescribe(goodsInfoVo.getDescribe());
        goods.setProductPrice(String.valueOf(goodsInfoVo.getProductPrice()));
        goods.setImageUrl(fileName);
        System.out.println("商品状态----" + goodsInfoVo.getStatus());
        goods.setStatus(goodsInfoVo.getStatus());
        goodsService.addGoods(goods);
        return RestfulJson.isOk("添加商品成功！");
    }

    @ApiOperation("删除商品")
    @ApiImplicitParam(name = "goodsName", value = "商品名称", dataType = "String")
    @DeleteMapping(value = "goods",produces="application/json;charset=UTF-8")
    public RestfulJson deleteGoods(String goodsName){
        if (null!=goodsName){
            goodsService.deleteGoods(goodsName);
            return RestfulJson.isOk("删除商品成功！");
        }
        return RestfulJson.errorMsg("商品名称为null，删除失败!");

    }

    @ApiOperation("获取所有商品")
    @GetMapping(value = "goods",produces="application/json;charset=UTF-8")
    public RestfulJson getGoods(){
        return RestfulJson.isOk(goodsService.getAllGoods());
    }

    @ApiOperation("上/下架商品")
    @ApiImplicitParam(name = "goodsName", value = "商品名称", dataType = "String")
    @PostMapping(value = "goods",produces="application/json;charset=UTF-8")
    public RestfulJson updateGoods(String goodsName){
        System.out.println("商品名称-------" + goodsName);
        goodsService.updateGoods(goodsName);
        return RestfulJson.isOk("修改成功！");
    }
}

