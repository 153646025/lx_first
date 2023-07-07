package com.example.springbootplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootplus.common.QueryPageParam;
import com.example.springbootplus.common.Result;
import com.example.springbootplus.entity.Goods;
import com.example.springbootplus.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-07-02
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    
    @Autowired
    private GoodsService goodsService;
    
    @PostMapping("/update")
    public Result update(@RequestBody Goods goods){
        return goodsService.updateById(goods)?Result.suc():Result.fail();
    }
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods){
        System.out.println(goods.toString());
        return goodsService.save(goods)?Result.suc():Result.fail();
    }
    //页面修改功能
    @PostMapping("/mod")
    public boolean mod(@RequestBody Goods goods){
        return goodsService.updateById(goods);
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Goods goods){
        return goodsService.saveOrUpdate(goods);
    }
    //页面删除功能
    @GetMapping("/delete")
    public Result delete(@RequestParam String id){
        return goodsService.removeById(id)?Result.suc():Result.fail();
    }

    //分页查询
    @PostMapping("/listPage")

    /**未封装的方法
     *  public List<User> listPage(@RequestBody HashMap map)
     *  system.out.println("num==="+(String)map.get("pageSize"))
     *
     */

    public Result listPage(@RequestBody QueryPageParam query){
//        System.out.println("num==="+query.getPageNum());
//        System.out.println("size==="+query.getPageNum());
//        return null;
        HashMap param = query.getParam();
        String name = (String)param.get("name");//搜索姓名
        String storage = (String)param.get("storage");
        String goodstype = (String)param.get("goodstype");

        Page<Goods> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Goods::getName,name);
        }
        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            lambdaQueryWrapper.eq(Goods::getStorage,storage);
        }
        if (StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){
            lambdaQueryWrapper.eq(Goods::getGoodstype,goodstype);
        }


        IPage result = goodsService.pageC(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }

}
