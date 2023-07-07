package com.example.springbootplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootplus.common.QueryPageParam;
import com.example.springbootplus.common.Result;
import com.example.springbootplus.entity.Goods;
import com.example.springbootplus.entity.Record;
import com.example.springbootplus.service.GoodsService;
import com.example.springbootplus.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-07-04
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private GoodsService goodsService;

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
        String roleId = (String)param.get("roleId");
        String userid = (String)param.get("userid");

        Page<Record> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<Record> queryWrapper = new QueryWrapper();
        queryWrapper.apply("a.goods=b.id AND b.`storage`=c.id AND b.goodstype=d.id");
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            queryWrapper.like("b.name",name);

        }
        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            queryWrapper.eq("c.id",storage);
        }
        if (StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){
            queryWrapper.eq("d.id",goodstype);
        }
        if ("2".equals(roleId)){
            queryWrapper.apply("a.userId="+userid);
        }


        IPage result = recordService.pageC(page,queryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }

    @PostMapping("/save")
    public Result save(@RequestBody Record record){
       Goods goods = goodsService.getById(record.getGoods());
       int n = record.getCount();
       if ("2".equals(record.getAction())){
           n=-n;
           record.setCount(n);
           System.out.println(n);
       }
       int num = goods.getCount()+n;
       goods.setCount(num);
       goodsService.updateById(goods);

       return recordService.save(record)?Result.suc():Result.fail();

    }


}
