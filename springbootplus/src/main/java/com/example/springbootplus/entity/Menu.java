package com.example.springbootplus.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Menu)实体类
 *
 * @author makejava
 * @since 2023-07-01 23:14:23
 */
@Data
public class Menu implements Serializable {


    private Integer id;
    /**
     * 菜单编码
     */
    private String menucode;
    /**
     * 菜单名字
     */
    private String menuname;
    /**
     * 菜单级别
     */
    private String menulevel;
    /**
     * 菜单的父code
     */
    private String menuparentcode;
    /**
     * 点击触发的函数
     */
    private String menuclick;
    /**
     * 权限 0超级管理员 1表示管理员  2表示用户 ，可以用逗号组合使用
     */
    private String menuright;

    private String menucomponent;

    private String menuicon;




}

