package com.example.springbootplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootplus.entity.Menu;
import com.example.springbootplus.mapper.MenuMapper;
import com.example.springbootplus.service.MenuService;
import org.springframework.stereotype.Service;



@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
}
