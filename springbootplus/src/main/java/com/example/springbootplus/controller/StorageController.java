package com.example.springbootplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootplus.common.QueryPageParam;
import com.example.springbootplus.common.Result;
import com.example.springbootplus.entity.Menu;
import com.example.springbootplus.entity.Storage;
import com.example.springbootplus.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Storage storage){
        return storageService.updateById(storage)?Result.suc():Result.fail();
    }
    @PostMapping("/save")
    public Result save(@RequestBody Storage storage){
        System.out.println(storage.toString());
        return storageService.save(storage)?Result.suc():Result.fail();
    }
    //页面修改功能
    @PostMapping("/mod")
    public boolean mod(@RequestBody Storage storage){
        return storageService.updateById(storage);
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Storage storage){
        return storageService.saveOrUpdate(storage);
    }
    //页面删除功能
    @GetMapping("/delete")
    public Result delete(@RequestParam String id){
        return storageService.removeById(id)?Result.suc():Result.fail();
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

        Page<Storage> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Storage> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Storage::getName,name);
        }


        IPage result = storageService.pageC(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }

    @GetMapping("/list")
    public Result list(){
        List list =  storageService.lambdaQuery().list();
        return Result.suc(list);
    }


}
