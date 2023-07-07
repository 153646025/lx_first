package com.example.springbootplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootplus.common.QueryPageParam;
import com.example.springbootplus.common.Result;
import com.example.springbootplus.entity.Menu;
import com.example.springbootplus.entity.User;
import com.example.springbootplus.service.MenuService;
import com.example.springbootplus.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

//    @GetMapping
//    public String hell(){
//        return "hello word";
//    }

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }

    //确定账号唯一
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no){
       List list =  userService.lambdaQuery().eq(User::getNo,no).list();
       return list.size()>0?Result.suc(list):Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        return userService.updateById(user)?Result.suc():Result.fail();
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List list = userService.lambdaQuery()
                .eq(User::getNo,user.getNo())
                .eq(User::getPassword,user.getPassword()).list();
        if (list.size()>0){
            User user1  = (User)list.get(0);
            List menulist =  menuService.lambdaQuery().like(Menu::getMenuright,user1.getRoleId()).list();
            HashMap res = new HashMap();
            res.put("user",user1);
            res.put("menu",menulist);
            return Result.suc(res);
        }
        return Result.fail();
    }

    //页面删除功能
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return userService.removeById(id)?Result.suc():Result.fail();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        System.out.println(user.toString());
        return userService.save(user)?Result.suc():Result.fail();
    }
    //页面修改功能
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user){
        return userService.updateById(user);
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }
    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return userService.removeById(id);
    }

    //查询（模糊、匹配）
    @PostMapping("/listP")
    public List<User> listP(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,user.getName());
        return userService.list(lambdaQueryWrapper);
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
        String sex = (String)param.get("sex");//搜索性别
        String roleId = (String)param.get("roleId");

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getName,name);
        }
        if (StringUtils.isNotBlank(sex)){
            lambdaQueryWrapper.eq(User::getSex,sex);
        }
        if (StringUtils.isNotBlank(roleId)){
            lambdaQueryWrapper.eq(User::getRoleId,roleId);
        }

        IPage result = userService.page(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }

}
