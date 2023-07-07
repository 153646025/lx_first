package com.example.springbootplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootplus.common.QueryPageParam;
import com.example.springbootplus.common.Result;
import com.example.springbootplus.entity.Goodstype;
import com.example.springbootplus.service.GoodstypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-07-02
 */
@RestController
@RequestMapping("/goodstype")
public class GoodstypeController {

    @Autowired
    private GoodstypeService goodstypeService;
    
    @PostMapping("/update")
    public Result update(@RequestBody Goodstype goodstype){
        return goodstypeService.updateById(goodstype)?Result.suc():Result.fail();
    }
    @PostMapping("/save")
    public Result save(@RequestBody Goodstype goodstype){
        System.out.println(goodstype.toString());
        return goodstypeService.save(goodstype)?Result.suc():Result.fail();
    }
    //页面修改功能
    @PostMapping("/mod")
    public boolean mod(@RequestBody Goodstype goodstype){
        return goodstypeService.updateById(goodstype);
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Goodstype goodstype){
        return goodstypeService.saveOrUpdate(goodstype);
    }
    //页面删除功能
    @GetMapping("/delete")
    public Result delete(@RequestParam String id){
        return goodstypeService.removeById(id)?Result.suc():Result.fail();
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

        Page<Goodstype> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goodstype> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Goodstype::getName,name);
        }


        IPage result = goodstypeService.pageC(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }

    @GetMapping("/list")
    public Result list(){
        List list =  goodstypeService.lambdaQuery().list();
        return Result.suc(list);
    }


}
