package com.example.springbootplus.controller;

import com.example.springbootplus.common.Result;
import com.example.springbootplus.entity.Menu;
import com.example.springbootplus.entity.User;
import com.example.springbootplus.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Menu)表控制层
 *
 * @author makejava
 * @since 2023-07-01 23:14:23
 */
@RestController
@RequestMapping("menu")
public class MenuController {
    /**
     * 服务对象
     */
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public Result list(@RequestParam String roleId){
        List list =  menuService.lambdaQuery().like(Menu::getMenuright,roleId).list();
        return Result.suc(list);
    }

}

